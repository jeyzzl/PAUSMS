<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="BscUmUtil" scope="page" class="aca.bsc.BscUmUtil" />

<html>
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
		
				
		<style>
			.grafic{
				border:1px solid black;
			}
		</style>
	</head>
<%
	ArrayList<aca.bsc.BscUm> listaBscUm = BscUmUtil.getListAll(conEnoc, "ORDER BY PERIODO_ID");
%>
	<body>
	<div class="container-fluid">
		<h2>Histórico de inscritos</h2>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="um"><spring:message code="aca.Regresar"/></a>
			&nbsp;&nbsp;
			Temas:
			<button <%if(tema.equals(""))out.print("disabled='disabled'");%> class="btn" onclick="location.href='umEst?tema='">Default</button>
			<button <%if(tema.equals("grid.js"))out.print("disabled='disabled'");%> class="btn" onclick="location.href='umEst?tema=grid.js'">Rojo</button>
			<button <%if(tema.equals("gray.js"))out.print("disabled='disabled'");%> class="btn" onclick="location.href='umEst?tema=gray.js'">Gris</button>
			<button <%if(tema.equals("dark-blue.js"))out.print("disabled='disabled'");%> class="btn" onclick="location.href='umEst?tema=dark-blue.js'">Azul</button>
			<button <%if(tema.equals("dark-green.js"))out.print("disabled='disabled'");%> class="btn" onclick="location.href='umEst?tema=dark-green.js'">Verde</button>
		</div>		
		<div style="width:100%;text-align:center;" class="buttonsBSC">
			<b>Gráfica: </b>&nbsp;
			<button data-grafic="grafic2" class="btn btn-primary">Básica</button>
			<button data-grafic="grafic3" class="btn btn-primary">Horizontal</button>
			<button data-grafic="grafic4" class="btn btn-primary">Área</button>
			<button data-grafic="grafic1" class="btn btn-primary" disabled="disabled">Lineas</button>
		</div>
		<br>
		<table style="width:95%" style="height:500px;" class="" align="center">
			<tr>
				<td>
					<%
						String categorias = "";
						String inscritosSerie = "";
						for(aca.bsc.BscUm bscUm : listaBscUm){
							String periodo = bscUm.getPeriodoId().substring(0, 4).substring(2, 4)+"-"+bscUm.getPeriodoId().substring(4, 8).substring(2, 4);
							
							categorias+="'"+periodo+"<br><b>"+bscUm.getCiclo()+"°</b>',";
							inscritosSerie+=bscUm.getInscritos()+",";
						}
						categorias = categorias.substring(0, categorias.length()-1);
						inscritosSerie = inscritosSerie.substring(0, inscritosSerie.length()-1);
						
						String serieFinal = "{name: 'Inscritos', data: ["+inscritosSerie+"]},";
					%>
					<div style="height:500px;" class="graficasBSC">	
						<div class="grafic grafic1">
							<script>Puntos('Ciclos escolares', '', 'bscUm', 'Inscritos',[<%=categorias%>],  [<%=serieFinal%>]);</script>
						  	<div id="bscUm" style="width:100%; height:100%; margin: auto"></div>
					  	</div>
					  	 
					  	<div class="grafic grafic2">
						  	<script>columnBasic('Inscritos por años', '','bscUmColumn', 'Inscritos',[<%=categorias%>], [<%=serieFinal%>]);</script>
							<div id="bscUmColumn" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
						</div>
						
						<div class="grafic grafic3">
							<script>barStacked('Inscritos por años', '','bscUmBar', 'Inscritos',[<%=categorias%>], [<%=serieFinal%>]);</script>
							<div id="bscUmBar" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
						</div>
						
						<div class="grafic grafic4">
							<script>areaStacked('Inscritos por años', '','bscUmArea', 'Inscritos',[<%=categorias%>], [<%=serieFinal%>]);</script>
							<div id="bscUmArea" style="min-width: 95%; height: 500px; margin: 0 auto"></div> 
						</div>
					</div>
				</td>
			</tr>
		</table>
		<br>
		<!--  --------------- INSCRITOS DESDE EL AÑO 2002 EN ADELANTE --------------- -->
		<%
		//Periodos
		String Periodos = "";
		String nombrePeriodos = "";
		
		ArrayList<String> periodos = aca.carga.CargaUtil.getPeriodos(conEnoc, "ORDER BY 1");
		for(String periodo: periodos){
			Periodos += "'"+periodo+"',";
			
			nombrePeriodos += "' 20"+periodo.substring(0, 2)+" - 20"+periodo.substring(2, 4)+" ',";
		}
		Periodos 		= Periodos.substring(0, Periodos.length()-1);
		nombrePeriodos  = nombrePeriodos.substring(0, nombrePeriodos.length()-1);

		//Listas por tipo
		String serie = "";
		
		//universitarios
		String univ = "";
		ArrayList<String> universitarios = aca.vista.EstadisticaUtil.getInscritos(conEnoc, Periodos, "'1A','1B','1C','1D'", "ORDER BY 1");
		for(String str : universitarios){
			univ += ""+str.split("\\$")[1]+",";
		}
		univ = univ.substring(0, univ.length()-1);
				
		serie += "{name: 'Universitarios', data: ["+univ+"]},";
		
		// nocturna
		String noc = "";
		ArrayList<String> nocturna = aca.vista.EstadisticaUtil.getInscritos(conEnoc, Periodos, "'2A','2B','2C','2D'", "ORDER BY 1");
		for(String str : nocturna){
			noc += ""+str.split("\\$")[1]+",";
		}
		noc = noc.substring(0, noc.length()-1);
				
		serie += "{name: 'Nocturna', data: ["+noc+"]},";
		
		// BACHILLERATO
		String prepa = "";
		ArrayList<String> bachillerato = aca.vista.EstadisticaUtil.getInscritos(conEnoc, Periodos, "'3A','3B','3C','3D'", "ORDER BY 1");
		for(String str : bachillerato){
			prepa += ""+str.split("\\$")[1]+",";
		}
		prepa = prepa.substring(0, prepa.length()-1);
				
		serie += "{name: 'Bachillerato', data: ["+prepa+"]},";
		
		// NO FORMAL
		String noFormal = "";
		ArrayList<String> NOFRMAL = aca.vista.EstadisticaUtil.getInscritos(conEnoc, Periodos, "'4A','4B','4C','4D'", "ORDER BY 1");
		for(String str : NOFRMAL){
			noFormal += ""+str.split("\\$")[1]+",";
		}
		noFormal = noFormal.substring(0, noFormal.length()-1);
				
		serie += "{name: 'No Formal', data: ["+noFormal+"]},";
		
		
		// DISTANCIA
		String distancia = "";
		ArrayList<String> DISTANCIA = aca.vista.EstadisticaUtil.getInscritos(conEnoc, Periodos, "'5A','5B','5C','5D'", "ORDER BY 1");
		for(String str : DISTANCIA){
			distancia += ""+str.split("\\$")[1]+",";
		}
		distancia = distancia.substring(0, distancia.length()-1);
				
		serie += "{name: 'Distancia', data: ["+distancia+"]},";
		
		// ROTACIONES Y OFTALMOLOGIA
		String rotaciones = "";
		ArrayList<String> ROTACIONES = aca.vista.EstadisticaUtil.getInscritos(conEnoc, Periodos, "'1E','1F','1G'", "ORDER BY 1");
		for(String str : ROTACIONES){
			rotaciones += ""+str.split("\\$")[1]+",";
		}
		rotaciones = rotaciones.substring(0, rotaciones.length()-1);
				
		serie += "{name: 'Rotaciones y Oftalmología', data: ["+rotaciones+"]}";

		
		%>
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
						<script>columnStacked('Inscritos por años', '','inscritos', 'Inscritos',[<%=nombrePeriodos%>], [<%=serie%>]);</script>
						<div id="inscritos" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
					</td>
				</tr>
		</table>
		
		<table style="width:95%" class="grafic grafica2" align="center">
				<tr>
					<td>
						<script>columnBasic('Inscritos por años', '','inscritosColumn', 'Inscritos',[<%=nombrePeriodos%>], [<%=serie%>]);</script>
						<div id="inscritosColumn" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
					</td>
				</tr>
		</table>
		
		<table style="width:95%" class="grafic grafica3" align="center">
				<tr>
					<td>
						<script>barStacked('Inscritos por años', '','inscritosBar', 'Inscritos',[<%=nombrePeriodos%>], [<%=serie%>]);</script>
						<div id="inscritosBar" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
					</td>
				</tr>
		</table>
		
		<table style="width:95%" class="grafic grafica4" align="center">
				<tr>
					<td>
						<script>areaStacked('Inscritos por años', '','inscritosArea', 'Inscritos',[<%=nombrePeriodos%>], [<%=serie%>]);</script>
						<div id="inscritosArea" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
					</td>
				</tr>
		</table>
		
		<table style="width:95%" class="grafic grafica5" align="center">
				<tr>
					<td>
						<script>Puntos('Inscritos por años', '','inscritosPuntos', 'Inscritos',[<%=nombrePeriodos%>], [<%=serie%>]);</script>
						<div id="inscritosPuntos" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
					</td>
				</tr>
		</table>
		
		</div>
		<br><br>
	
		<script>
		(function($){
			var graficas = $('.graficasInsc').find('.grafic').hide();
			$('.grafica1').show();
			$('.buttons').on('click','button',function(){
				graficas.hide();
				$this = $(this).prop('disabled', true).siblings().prop('disabled', false).end();
				$('.'+$this.data('grafic')).fadeIn();
			})
			var grafics = $('.graficasBSC').find('.grafic').hide();
			$('.grafic1').show();
			$('.buttonsBSC').on('click','button',function(){
				grafics.hide();
				$this = $(this).prop('disabled', true).siblings().prop('disabled', false).end();
				$('.'+$this.data('grafic')).fadeIn();
			});
		})(jQuery);
		</script>
		</div>
	</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>