<%@ include file= "../../con_enoc.jsp"%> 
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../body.jsp"%> 
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="cursoU" scope="page" class="aca.plan.CursoUtil" />

<head>
<%
	String cargaId = request.getParameter("carga")==null?"0":request.getParameter("carga");
	String periodo = (String) session.getAttribute("periodoGraficas");

	ArrayList<String> notasxcreditos = cursoU.getNotasxCredtiosCarga(conEnoc, cargaId);
	ArrayList<String> totalcreditos = cursoU.getTotalCredtiosCarga(conEnoc, cargaId);
%>

<%	
	String tema = request.getParameter("tema")==null?"":request.getParameter("tema");
%>

	<script type="text/javascript" src="../../js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="../../js/highcharts.js"></script>
	<script type="text/javascript" src="../../js/highChart/modules/exporting.js"></script>
	<script type="text/javascript" src="../../js/highChart/puntos.js"></script>  
	<script type="text/javascript" src="../../js/highChart/column-stacked.js"></script> 
	<script type="text/javascript" src="../../js/highChart/column-basic.js"></script>  
	<script type="text/javascript" src="../../js/highChart/bar-stacked.js"></script>   
	<script type="text/javascript" src="../../js/highChart/area-stacked.js"></script>  
	<script type="text/javascript" src="../../js/highChart/themes/<%=tema%>"></script>

	<style>
		.grafic{
			border:1px solid black;
		}
	</style>
	
</head>
<body>
<table style="margin: 0 auto; width:95%">
	<tr>
		<td>
			<h3>Temas:</h3>
			<button <%if(tema.equals(""))out.print("disabled='disabled'");%> class="btn" onclick="location.href='promediosDetalle?tema=&carga=<%=cargaId%>'">Default</button>
			<button <%if(tema.equals("grid.js"))out.print("disabled='disabled'");%> class="btn" onclick="location.href='promediosDetalle?tema=grid.js&carga=<%=cargaId%>'">Red</button>
			<button <%if(tema.equals("gray.js"))out.print("disabled='disabled'");%> class="btn" onclick="location.href='promediosDetalle?tema=gray.js&carga=<%=cargaId%>'">Gris</button>
			<button <%if(tema.equals("dark-blue.js"))out.print("disabled='disabled'");%> class="btn" onclick="location.href='promediosDetalle?tema=dark-blue.js&carga=<%=cargaId%>'">Azul</button>
			<button <%if(tema.equals("dark-green.js"))out.print("disabled='disabled'");%> class="btn" onclick="location.href='promediosDetalle?tema=dark-green.js&carga=<%=cargaId%>'">Verde</button>
		</td>
	</tr>
</table>
<table style="width:80%; margin: 0 auto;">
	<tr>
		<td align="center"><h2><%=aca.carga.CargaUtil.getNombreCarga(conEnoc, cargaId) %> <a href="promedios?periodoId=<%=periodo %>" class="btn">regresar</a></h2></td>
	</tr>
</table>
<br>
<div style="width:100%;text-align:center;" class="buttonsBSC">
	<b>Gráfica: </b>&nbsp;
	<button data-grafic="grafic2" class="btn btn-primary">Básica</button>
	<button data-grafic="grafic3" class="btn btn-primary">Horizontal</button>
	<button data-grafic="grafic4" class="btn btn-primary">Área</button>
	<button data-grafic="grafic1" class="btn btn-primary" disabled="disabled">Lineas</button>
</div>
<br>
<%

	java.text.DecimalFormat getformato = new java.text.DecimalFormat("###,##0.00; -###,##0.00");

	ArrayList<String> facultades = new ArrayList<String>();
	String facultadesGrafica = "";	

	String facultadesTmp = "";
	for(String str: notasxcreditos){
		String fac = str.split("%")[1];
		
		if(!facultadesTmp.equals(fac)){
			
			facultades.add(fac);
			facultadesGrafica += "'"+aca.catalogo.CatFacultadUtil.getNombreCorto(conEnoc, fac)+"',";
			
			facultadesTmp = fac;
		}
	}
	facultadesGrafica = facultadesGrafica.substring(0, facultadesGrafica.length()-1);
	
	String promedioGrafica = "";
	double totalGeneralNotas = 0;
	double totalGeneralCreditos = 0;
	
	for(String facultad : facultades){
		
		double totalNotas = 0;
		for(String str2 : notasxcreditos){//Sumas las (notas X credito) de las carreras que corresponden a cierta facultad
			if(str2.split("%")[1].equals(facultad)){
				totalNotas += Double.parseDouble(str2.split("%")[3]);
				totalGeneralNotas+= Double.parseDouble(str2.split("%")[3]);
			}
		}
		
		double totalCreditos = 0;
		for(String str2 : totalcreditos){//Sumas los creditos totales de las carreras que corresponden a cierta facultad
			if(str2.split("%")[1].equals(facultad)){
				totalCreditos += Double.parseDouble(str2.split("%")[3]);
				totalGeneralCreditos+= Double.parseDouble(str2.split("%")[3]);
			}
		}
		String promedio = "";
		if(totalCreditos>0){
			promedio = getformato.format(totalNotas/totalCreditos);//division para sacar el promedio
		}else{
			promedio = "0";
		}
		
		promedioGrafica += promedio+",";

	}
	
	promedioGrafica = promedioGrafica.substring(0, promedioGrafica.length()-1);

	String serieFinal = "{name: 'Inscritos', data: ["+promedioGrafica+"]}";
	
%>
<table style="width:95%; margin: 0 auto; height:500px;">
			<tr>
				<td align="center" valign="top">
					<div style="height:500px;" class="graficasBSC">	
						<div class="grafic grafic1">
							<script>Puntos('Promedio por Facultades', 'Promedio General: <%=getformato.format(totalGeneralNotas/totalGeneralCreditos )%>', 'bscUm', 'Inscritos',[<%=facultadesGrafica%>],  [<%=serieFinal%>]);</script>
						  	<div id="bscUm" style="width:100%; height:100%; margin: auto"></div>
					  	</div>
					  	 
					  	<div class="grafic grafic2">
						  	<script>columnStacked('Promedio por Facultades', 'Promedio General: <%=getformato.format(totalGeneralNotas/totalGeneralCreditos )%>','bscUmColumn', 'Inscritos',[<%=facultadesGrafica%>], [<%=serieFinal%>]);</script>
							<div id="bscUmColumn" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
						</div>
						
						<div class="grafic grafic3">
							<script>barStacked('Promedio por Facultades', 'Promedio General: <%=getformato.format(totalGeneralNotas/totalGeneralCreditos )%>','bscUmBar', 'Inscritos',[<%=facultadesGrafica%>], [<%=serieFinal%>]);</script>
							<div id="bscUmBar" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
						</div>
						
						<div class="grafic grafic4">
							<script>areaStacked('Promedio por Facultades', 'Promedio General: <%=getformato.format(totalGeneralNotas/totalGeneralCreditos )%>','bscUmArea', 'Inscritos',[<%=facultadesGrafica%>], [<%=serieFinal%>]);</script>
							<div id="bscUmArea" style="min-width: 95%; height: 500px; margin: 0 auto"></div> 
						</div>
					</div>
				</td>
			</tr>
</table>
<br>
<table style="width:50%; margin: 0 auto;" class="table">
	<%
	  int cont = 0;
	  String facTmp = "";
	  for(String str: notasxcreditos){ 
		if(!str.split("%")[1].equals(facTmp)){
	%>
		</table>
		<table style="width:50%; margin: 0 auto;">
			<tr>
				<td><h3><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, str.split("%")[1]) +" ("+aca.catalogo.CatFacultadUtil.getNombreCorto(conEnoc,str.split("%")[1])+")"%></h3></td>
			</tr>
		</table>
		<table style="width:50%; margin: 0 auto;" class="table">
		<tr>
			<th width="80%"><spring:message code="aca.Carrera"/></th>
			<th width="20%"><spring:message code="aca.Promedio"/></th>
		</tr>
	<%
			facTmp = str.split("%")[1];
		}
		String carreraId = str.split("%")[2];
		String nombreCarrera = aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc,str.split("%")[2]);
		String promedio = getformato.format( Double.parseDouble(str.split("%")[3])/Double.parseDouble(totalcreditos.get(cont).split("%")[3]) );
	
	%>
		<tr style="cursor:pointer;" onclick="location.href='promedioPorAlumnos?cargaId=<%=cargaId%>&carreraId=<%=carreraId%>&nombreCarrera=<%=nombreCarrera%>&promedio=<%=promedio%>'">
			<td><%=nombreCarrera %></td>
			<td><%=promedio%></td>
		</tr>
	<%
	  	cont++;
	  } %>
</table>
</body>
<script>
(function($){
	var grafics = $('.graficasBSC').find('.grafic').hide();
	$('.grafic1').show();
	$('.buttonsBSC').on('click','button',function(){
		grafics.hide();
		$this = $(this).prop('disabled', true).siblings().prop('disabled', false).end();
		$('.'+$this.data('grafic')).fadeIn();
	});
})(jQuery);
</script>
<%@ include file= "../../cierra_enoc.jsp"%>