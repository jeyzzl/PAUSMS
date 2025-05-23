<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="tipoBecaU" scope="page" class="aca.afe.AfeTipoBecaUtil"/>

<%@ page import= "java.util.HashMap"%>

<html>
<head>
	<link rel="stylesheet" href="../../js/jtip/css/jtip.css">
	<script src='../../js/jtip/js/jtip.js'></script>

	<script type="text/javascript">
		function cambiarTipo(){
			location.href="detalles?tipos="+document.forma.Tipos.value;
		}
	</script>

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

<%
		java.text.DecimalFormat getformato	= new java.text.DecimalFormat("#,##0.00;-#,##0.00");	
	
		ArrayList<aca.afe.AfeTipoBeca> becas = tipoBecaU.getListAll(conEnoc, "ORDER BY 1");
	
		//QUIEN DEBE USAR EL TOPE EN VEZ DEL IMPORTE
		ArrayList<String> BECAS = new ArrayList<String>();				
		
		for(aca.afe.AfeTipoBeca beca: becas){
			
		}
		
		for(int i=0; i<becas.size(); i++){
			aca.afe.AfeTipoBeca actual = becas.get(i);
			
			if(BECAS.contains(actual.getIdCtamayor()))continue;
			
			for(int j=0; j<becas.size(); j++){
				if(i==j)continue;	
				aca.afe.AfeTipoBeca actual2 = becas.get(j);
				
				if(BECAS.contains(actual2.getIdCtamayor()))continue;
				
				if(actual.getIdCtamayor().equals(actual2.getIdCtamayor())) BECAS.add(actual2.getIdCtamayor());
			}
		}

		String ejercicioId = "001-2013";		
		
		String nombreBecas = "";
		String importes = "";
		for(aca.afe.AfeTipoBeca beca: becas){
				nombreBecas+= "'"+beca.getIdCtamayor()+"<br>"+beca.getDescripcion()+"',";
				
				if(BECAS.contains(beca.getIdCtamayor())){
					//System.out.println(beca.getDescripcion()+":"+beca.getTope()+":"+beca.getIdCtamayor());
					importes += beca.getTope()+",";
				}else{
					importes += aca.afe.AfeTipoBecaUtil.getImporte(conEnoc, ejercicioId, beca.getIdCtamayor())+",";	
				}
				
		}
		nombreBecas = nombreBecas.substring(0, nombreBecas.length()-1);
		importes = importes.substring(0, importes.length()-1);
		
		String importe = "{name: 'Presupuesto', data: ["+importes+"]}";
		
		String resultado = "";
		
		resultado+=importe;
		
		//TRAER LO QUE SE HA USADO DE LAS BECAS
		
		HashMap<String, String> map = tipoBecaU.getUsadoBecas(conEnoc, ejercicioId);
		String usados = "";
		
		for(aca.afe.AfeTipoBeca beca: becas){
			
			if(beca.getIdCtamayor().equals("1.3.05")){
				usados+= aca.afe.AfeTipoBecaUtil.getUsadoBecGeneral(conEnoc, ejercicioId)+",";
			}else if(beca.getIdCtamayor().equals("1.3.19")) {
				String gastoTmp = map.get( beca.getId() );
			    String gastoMaestria = aca.afe.AfeTipoBecaUtil.getUsadoMaestria(conEnoc, ejercicioId);
				
			    usados += (Double.parseDouble(gastoTmp) + Double.parseDouble(gastoMaestria))+","; 
				
			}else if( map.containsKey( beca.getId() ) ){
				usados+= map.get( beca.getId() )+",";
			}else{
				usados+= "0,";
			}
	
		}
		
		usados = usados.substring(0, usados.length()-1);
		
		String usado = "{name: 'Gasto', data: ["+usados+"]}";
		
		resultado+=","+usado;
		
		//DISPONIBLE
		String disponibles = "";
		
		for(aca.afe.AfeTipoBeca beca: becas){				
				//IMPORTE
				String imp = "";
				if(BECAS.contains(beca.getIdCtamayor())){
					imp = beca.getTope();
				}else{
					imp = aca.afe.AfeTipoBecaUtil.getImporte(conEnoc, ejercicioId, beca.getIdCtamayor());	
				}
				
				//GASTO
				String gasto = "";
				if(beca.getIdCtamayor().equals("1.3.05")){
					gasto = aca.afe.AfeTipoBecaUtil.getUsadoBecGeneral(conEnoc, ejercicioId);
				}else if(beca.getIdCtamayor().equals("1.3.19")) {
					String gastoTmp = map.get( beca.getId() );
				    String gastoMaestria = aca.afe.AfeTipoBecaUtil.getUsadoMaestria(conEnoc, ejercicioId);
					
				    gasto = (Double.parseDouble(gastoTmp) + Double.parseDouble(gastoMaestria))+""; 
					
				}else if( map.containsKey( beca.getId() ) ){
					gasto = map.get( beca.getId() );
				}else{
					gasto = "0";
				}
				
				disponibles += (Double.parseDouble(imp)-Double.parseDouble(gasto))+","; 
		}
		disponibles = disponibles.substring(0, disponibles.length()-1);
		
		String disponible = "{name: 'Disponible', data: ["+disponibles+"]}";
		
		resultado+=","+disponible;
				
%>

	<table style="margin: 0 auto;  width:95%">
		<tr>
			<td>
				<h3>Temas:</h3>
				<button <%if(tema.equals(""))out.print("disabled='disabled'");%> class="btn" onclick="location.href='reporte?tema='">Default</button>
				<button <%if(tema.equals("grid.js"))out.print("disabled='disabled'");%> class="btn" onclick="location.href='reporte?tema=grid.js'">Red</button>
				<button <%if(tema.equals("gray.js"))out.print("disabled='disabled'");%> class="btn" onclick="location.href='reporte?tema=gray.js'">Gris</button>
				<button <%if(tema.equals("dark-blue.js"))out.print("disabled='disabled'");%> class="btn" onclick="location.href='reporte?tema=dark-blue.js'">Azul</button>
				<button <%if(tema.equals("dark-green.js"))out.print("disabled='disabled'");%> class="btn" onclick="location.href='reporte?tema=dark-green.js'">Verde</button>
			</td>
		</tr>
	</table>
	<br>
	<div style="width:100%;text-align:center;" class="buttons">
		<b>Gráfica: </b>&nbsp; 
		<button data-grafic="grafica1" class="btn btn-primary" disabled="disabled">Apilada</button>
		<button data-grafic="grafica2" class="btn btn-primary">Básica</button>
		<button data-grafic="grafica3" class="btn btn-primary">Horizontal</button>
		<button data-grafic="grafica4" class="btn btn-primary">Área</button>
		<button data-grafic="grafica5" class="btn btn-primary">Lineas</button>
	</div>
	<br>
	<div style="height:500px;" class="graficasInsc">
		
		<table style="width:95%" class="grafic grafica1" align="center">
				<tr>
					<td>
						<script>columnStacked('Fondo de Becas', '','inscritos', 'Inscritos',[<%=nombreBecas%>], [<%=resultado%>]);</script>
						<div id="inscritos" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
					</td>
				</tr>
		</table>
		
		<table style="width:95%" class="grafic grafica2" align="center">
				<tr>
					<td>
						<script>columnBasic('Fondo de Becas', '','inscritosColumn', 'Inscritos',[<%=nombreBecas%>], [<%=resultado%>]);</script>
						<div id="inscritosColumn" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
					</td>
				</tr>
		</table>
		
		<table style="width:95%" class="grafic grafica3" align="center">
				<tr>
					<td>
						<script>barStacked('Fondo de Becas', '','inscritosBar', 'Inscritos',[<%=nombreBecas%>], [<%=resultado%>]);</script>
						<div id="inscritosBar" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
					</td>
				</tr>
		</table>
		
		<table style="width:95%" class="grafic grafica4" align="center">
				<tr>
					<td>
						<script>areaStacked('Fondo de Becas', '','inscritosArea', 'Inscritos',[<%=nombreBecas%>], [<%=resultado%>]);</script>
						<div id="inscritosArea" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
					</td>
				</tr>
		</table>
		
		<table style="width:95%" class="grafic grafica5" align="center">
				<tr>
					<td>
						<script>Puntos('Fondo de Becas', '','inscritosPuntos', 'Inscritos',[<%=nombreBecas%>], [<%=resultado%>]);</script>
						<div id="inscritosPuntos" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
					</td>
				</tr>
		</table>
		
		<br>
		
		<table class="table" align="center">
			<tr>
				<th>Id</th>
				<th><spring:message code='aca.Descripcion'/></th>
				<th>Presupuesto</th>
				<th>Gasto</th>
				<th>Disponible</th>
			</tr>
<%
			for(aca.afe.AfeTipoBeca beca: becas){				
				//IMPORTE
				String imp = "";
				if(BECAS.contains(beca.getIdCtamayor())){
					imp = beca.getTope();
				}else{
					imp = aca.afe.AfeTipoBecaUtil.getImporte(conEnoc, ejercicioId, beca.getIdCtamayor());	
				}
				
				//GASTO
				String gasto = "";
				if(beca.getIdCtamayor().equals("1.3.05")){
					gasto = aca.afe.AfeTipoBecaUtil.getUsadoBecGeneral(conEnoc, ejercicioId);
				}else if( map.containsKey( beca.getId() ) ){
					gasto = map.get( beca.getId() );
				}else{
					gasto = "0";
				}
%>
				<tr>
					<td class="jtip" data-position="top" data-text="<%=getformato.format( Double.parseDouble(aca.afe.AfeTipoBecaUtil.getImporte(conEnoc, ejercicioId, beca.getIdCtamayor()) ))%>"><%=beca.getIdCtamayor() %></td>
					<td><%=beca.getDescripcion() %></td>
					<td style="text-align:right;"><%=getformato.format( Double.parseDouble(imp) )%></td>
					<td style="text-align:right;"><%=getformato.format( Double.parseDouble(gasto) ) %></td>
					<td style="text-align:right;"><%=getformato.format(Double.parseDouble(imp)-Double.parseDouble(gasto)) %></td>
				</tr>
<%	
			}
%>		
		</table>
		
		
		<br><br>
	</div>
	<br>
	<script>
	(function($){
		var graficas = $('.graficasInsc').find('.grafic').hide();
		var grafica1 = $('.grafica1').show();
		$('.buttons').on('click','button',function(){
			graficas.hide();
			$this = $(this).attr('disabled', 'disabled').siblings().removeAttr('disabled').end();
			$('.'+$this.data('grafic')).fadeIn();
		})
	})(jQuery);
	</script>
</body>
</html>

<%@ include file= "../../cierra_enoc.jsp" %>