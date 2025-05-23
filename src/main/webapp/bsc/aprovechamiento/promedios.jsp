<%@ include file= "../../con_enoc.jsp"%> 
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../body.jsp"%> 
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="cursoU" scope="page" class="aca.plan.CursoUtil" />
<head>
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
	
	<script>
		function cambiarTema(tema){
			document.frmCargas.tema.value = tema;
			document.frmCargas.Accion.value = "1";
			document.frmCargas.submit();
		}
	</script>
			
	<style>
		.grafic{
			border:1px solid black;
		}
	</style>
	<%
		String periodoId = request.getParameter("periodoId")==null?(String)session.getAttribute("periodoGraficas"):request.getParameter("periodoId");
		ArrayList<String> periodos = aca.carga.CargaUtil.getPeriodos(conEnoc, "ORDER BY 1");
		if(periodoId==null){
			periodoId = periodos.get(periodos.size()-1);
		}
		
		session.setAttribute("periodoGraficas", periodoId);
	%>
</head>
<body>
	<table style="margin: 0 auto; width:95%;">
			<tr>
				<td>
					<h3>Temas:</h3>
					<button <%if(tema.equals(""))out.print("disabled='disabled'");%> class="btn" onclick="cambiarTema('')">Default</button>
					<button <%if(tema.equals("grid.js"))out.print("disabled='disabled'");%> class="btn" onclick="cambiarTema('grid.js')">Red</button>
					<button <%if(tema.equals("gray.js"))out.print("disabled='disabled'");%> class="btn" onclick="cambiarTema('gray.js')">Gris</button>
					<button <%if(tema.equals("dark-blue.js"))out.print("disabled='disabled'");%> class="btn" onclick="cambiarTema('dark-blue.js')">Azul</button>
					<button <%if(tema.equals("dark-green.js"))out.print("disabled='disabled'");%> class="btn" onclick="cambiarTema('dark-green')">Verde</button>
				</td>
			</tr>
		</table>
		<form name="forma">
		<table style="width:80%; margin: 0 auto;">
			<tr>
				<td align="center">Periodo:
					<select name="Periodo" onchange="document.location.href='promedios?periodoId='+document.forma.Periodo.value+'&tema=<%=tema%>';">
					<%
						for(String periodo: periodos){
							String nombrePeriodos = "20"+periodo.substring(0, 2)+" - 20"+periodo.substring(2, 4)+"";
					%>
							<option <%if(periodo.equals(periodoId))out.print("selected");%> value="<%=periodo%>"><%=nombrePeriodos %></option>
					<%
						} 
					%>
					</select>
				</td>
			</tr>
		</table>
		</form>
		<div style="width:100%;text-align:center;" class="buttonsBSC">
			<b>Gráfica: </b>&nbsp;
			<button data-grafic="grafic2" class="btn btn-primary">Básica</button>
			<button data-grafic="grafic3" class="btn btn-primary">Horizontal</button>
			<button data-grafic="grafic4" class="btn btn-primary">Área</button>
			<button data-grafic="grafic1" class="btn btn-primary" disabled="disabled">Lineas</button>
		</div>
		<br>
		
		<table style="width:95%; margin: 0 auto; height:500px;">
			<tr>
				<td align="center" valign="top">
					<%
					String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion");
					
					java.text.DecimalFormat getformato = new java.text.DecimalFormat("###,##0.00; -###,##0.00");
				
					//PROMEDIOS--------------------------------------------------------->
					//Notas por carrera multiplicadas por su credito
					ArrayList<String> notasxcreditos 	= cursoU.getNotasxCredtios(conEnoc, periodoId);
					
					if(notasxcreditos.size()>0){//Si este periodo tiene notas
						
						//Creditos totales por carrera
						ArrayList<String> totalcreditos = cursoU.getTotalCredtios(conEnoc, periodoId);
						
						//Cargas en el periodo seleccionado---------------------------------->
						ArrayList<String> cargasSeleccionadas = new ArrayList<String>();
						ArrayList<String> cargas = new ArrayList<String>();
						String cargasGrafica 	 = "";//El formato de las cargas para la grafica
						
						String cargaTmp = "";
						for(String str : notasxcreditos){//Buscar todas las cargas diferentes en el arreglo
							String carga 	= str.split("%")[0];
							if(!cargaTmp.equals(carga)){
								cargaTmp = carga;

								cargas.add(carga);
								
								if(accion.equals("1")){
									if(request.getParameter(carga)==null){
										continue;
									}
								}
								cargasSeleccionadas.add(carga);
								cargasGrafica+= "\""+carga+"\",";
								
							}
						}
						cargasGrafica = cargasGrafica.substring(0, cargasGrafica.length()-1);
						
						//Total de (notas X credito) de cada carga---------------------------->
						//Total de notas por carga multiplicadas por su credito dividido entre el total de creditos de la carga
						String promedioGrafica = "";
						String promedioSeleccionado = "";
						double totalGeneralNotas = 0;
						double totalGeneralCreditos = 0;
						for(String carga : cargas){
							
							double totalNotas = 0;
							for(String str2 : notasxcreditos){//Sumas las (notas X credito) de las carreras que corresponden a cierta carga
								if(str2.split("%")[0].equals(carga)){
									totalNotas += Double.parseDouble(str2.split("%")[2]);
									totalGeneralNotas+= Double.parseDouble(str2.split("%")[2]);
								}
							}
							
							double totalCreditos = 0;
							for(String str2 : totalcreditos){//Sumas los creditos totales de las carreras que corresponden a cierta carga
								if(str2.split("%")[0].equals(carga)){
									totalCreditos += Double.parseDouble(str2.split("%")[2]);
									totalGeneralCreditos+= Double.parseDouble(str2.split("%")[2]);
								}
							}
							String promedio = "";
							if(totalCreditos>0){
								promedio = getformato.format(totalNotas/totalCreditos);//division para sacar el promedio
							}else{
								promedio = "0";
							}
							
							promedioGrafica += promedio+",";
							
							if(accion.equals("1")){
								if(request.getParameter(carga)==null){
									continue;
								}
							}
							
							promedioSeleccionado += promedio+",";

						}
						promedioGrafica = promedioGrafica.substring(0, promedioGrafica.length()-1);
						promedioSeleccionado = promedioSeleccionado.substring(0, promedioSeleccionado.length()-1);
					
						String serieFinal = "{name: 'Cargas Académicas', data: ["+promedioSeleccionado+"]}";
					%>
					
					<form id="frmCargas" name="frmCargas" action="promedios?periodoId=<%=periodoId%>" method="get">
						<input type="hidden" name="tema">
						<input type="hidden" name="Accion" id="Accion">
						<table class="table table-condensed table-nohover" style="border:1px solid gray;">
							<tr>
								<td>
									<div class="SeleccionCargas">
									<%
										String checked = "";
										String tipoTmp = "";
										for(String carga: cargas){
											
											String tipo = carga.substring(carga.length()-2,carga.length()-1);
											if(!tipoTmp.equals(tipo)){
												if(tipo.equals("1")){
													out.print("<h4>Universitarios</h4>");
												}else if(tipo.equals("2")){
													out.print("<h4>Nocturna</h4>");
												}else if(tipo.equals("3")){
													out.print("<h4>Preparatoria</h4>");
												}else if(tipo.equals("4")){
													out.print("<h4>No Formal</h4>");
												}else if(tipo.equals("5")){
													out.print("<h4>Distancia</h4>");
												}
												
												tipoTmp = tipo;
											}
											
											checked = "checked";
											if(!cargasSeleccionadas.contains(carga))checked = "";
									%>
										<input type="checkbox" name="<%=carga%>" <%=checked%>><%=carga%> &nbsp;&nbsp;&nbsp;
									<%
										}
									%>
										<br>
										<input style="float:right;" type="button" value="Filtrar Gráfica" class="btn btn-primary" onclick="filtrar();" />
									</div>
								</td>
							</tr>
						</table>
						<div style="height:500px;" class="graficasBSC">	
							<div class="grafic grafic1">
								<script>Puntos('Promedio por Cargas', 'Promedio General: <%=getformato.format(totalGeneralNotas/totalGeneralCreditos )%>', 'bscUm', 'Promedio',[<%=cargasGrafica%>],  [<%=serieFinal%>]);</script>
							  	<div id="bscUm" style="width:100%; height:100%; margin: auto"></div>
						  	</div>
						  	 
						  	<div class="grafic grafic2">
							  	<script>columnStacked('Promedio por Cargas', 'Promedio General: <%=getformato.format(totalGeneralNotas/totalGeneralCreditos )%>','bscUmColumn', 'Promedio',[<%=cargasGrafica%>], [<%=serieFinal%>]);</script>
								<div id="bscUmColumn" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
							</div>
							
							<div class="grafic grafic3">
								<script>barStacked('Promedio por Cargas', 'Promedio General: <%=getformato.format(totalGeneralNotas/totalGeneralCreditos )%>','bscUmBar', 'Promedio',[<%=cargasGrafica%>], [<%=serieFinal%>]);</script>
								<div id="bscUmBar" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
							</div>
							
							<div class="grafic grafic4">
								<script>areaStacked('Promedio por Cargas', 'Promedio General: <%=getformato.format(totalGeneralNotas/totalGeneralCreditos )%>','bscUmArea', 'Promedio',[<%=cargasGrafica%>], [<%=serieFinal%>]);</script>
								<div id="bscUmArea" style="min-width: 95%; height: 500px; margin: 0 auto"></div> 
							</div>
						</div>
						<br>
						<table style="width:45%; margin: 0 auto;" class="table">
							<tr>
								<th><spring:message code="aca.Clave"/></th>
								<th><spring:message code="aca.Nombre"/></th>
								<th><spring:message code="aca.Promedio"/></th>
							</tr>
							<%
								String [] promedios = promedioGrafica.split(",");
								int cont = 0;
								for(String carga :cargas) {%>
								<tr style="cursor:pointer;" onclick="location.href='promediosDetalle?carga=<%=carga%>'">
									<td><%=carga %></td>
									<td><%=aca.carga.CargaUtil.getNombreCarga(conEnoc, carga) %></td>
									<td><%=promedios[cont] %></td>
								</tr>
							<%
								cont++;
								} %>
						</table>
					</form>
				<%}else{ %>
					<h2>En este periodo no hay notas registradas</h2>
				<%} %>
			</td>
		</tr>
	</table>
	<br>
</body>
<script>
function filtrar(){
	var AlgunSeleccionado = false;
	jQuery.each(jQuery('.SeleccionCargas').find('input[type="checkbox"]'), function(){
		if(jQuery(this).prop('checked', true)){
			AlgunSeleccionado = true;
		}	
	});
	if(AlgunSeleccionado){
		document.frmCargas.Accion.value = "1";
		document.frmCargas.tema.value = "<%=tema%>";
		document.frmCargas.submit();	
	}else{
		alert('Debe seleccionar al menos 1 carga');
	}
}

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