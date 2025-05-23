<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
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

		//PERIODOS DESDE EL 2002
		String Periodos = "";
		String nombrePeriodos = "";
		
		ArrayList<String> periodos = aca.carga.CargaUtil.getPeriodos(conEnoc, "ORDER BY 1");
		for(String periodo: periodos){
			Periodos += "'"+periodo+"',";
			
			nombrePeriodos += "' 20"+periodo.substring(0, 2)+" - 20"+periodo.substring(2, 4)+" ',";
		}
		Periodos 		= Periodos.substring(0, Periodos.length()-1);
		nombrePeriodos  = nombrePeriodos.substring(0, nombrePeriodos.length()-1);
		
		String [] tipos = (request.getParameter("tipos")==null?"1A,1B,1C,1D":request.getParameter("tipos")).split(",");
		
		//Listas por tipo
		String serie = "";
		for(String tipo: tipos){
			
			//totales por cada carga
			String totales = "";
			ArrayList<String> arr = aca.vista.EstadisticaUtil.getInscritos(conEnoc, Periodos, "'"+tipo+"'", "ORDER BY 1");
			for(String str : arr){
				totales += ""+str.split("\\$")[1]+",";
			}
			totales = totales.substring(0, totales.length()-1);
					
			serie += "{name: '"+tipo.substring(1,tipo.length())+"', data: ["+totales+"]},";
		}
		
		serie = serie.substring(0, serie.length()-1);
		
%>
<div class="container-fluid">
	<h2>Inscritos por ciclos escolares</h2>
	<form name="forma" action="detalles" target="_self">	
	<div class="alert alert-info">
		<a class="btn btn-primary" href="um"><spring:message code="aca.Regresar"/></a>
		&nbsp;&nbsp;
		<select onchange="cambiarTipo();" name="Tipos" id="Tipos">
			<option <%if(tipos[0].equals("1A"))out.print("selected");%> value="1A,1B,1C,1D">Universitarios</option>
			<option <%if(tipos[0].equals("2A"))out.print("selected");%> value="2A,2B,2C,2D">Nocturna</option>
			<option <%if(tipos[0].equals("3A"))out.print("selected");%> value="3A,3B,3C,3D">Bachillerato</option>
			<option <%if(tipos[0].equals("4A"))out.print("selected");%> value="4A,4B,4C,4D">No Formal</option>
			<option <%if(tipos[0].equals("5A"))out.print("selected");%> value="5A,5B,5C,5D">Distancia</option>
			<option <%if(tipos[0].equals("1E"))out.print("selected");%> value="1E,1F,1G">Rotaciones y Oftalmología</option>
		</select>
		&nbsp;&nbsp;
		Temas:
		<button <%if(tema.equals(""))out.print("disabled='disabled'");%> class="btn" onclick="location.href='detalles?tema='">Default</button>
		<button <%if(tema.equals("grid.js"))out.print("disabled='disabled'");%> class="btn" onclick="location.href='detalles?tema=grid.js'">Rojo</button>
		<button <%if(tema.equals("gray.js"))out.print("disabled='disabled'");%> class="btn" onclick="location.href='detalles?tema=gray.js'">Gris</button>
		<button <%if(tema.equals("dark-blue.js"))out.print("disabled='disabled'");%> class="btn" onclick="location.href='detalles?tema=dark-blue.js'">Azul</button>
		<button <%if(tema.equals("dark-green.js"))out.print("disabled='disabled'");%> class="btn" onclick="location.href='detalles?tema=dark-green.js'">Verde</button>
	</div>
	</form>	
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
						<script>columnStacked('Inscripciones Anuales', 'Por Carga','inscritos', 'Inscritos',[<%=nombrePeriodos%>], [<%=serie%>]);</script>
						<div id="inscritos" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
					</td>
				</tr>
		</table>
		
		<table style="width:95%" class="grafic grafica2" align="center">
				<tr>
					<td>
						<script>columnBasic('Inscripciones Anuales', 'Por Carga','inscritosColumn', 'Inscritos',[<%=nombrePeriodos%>], [<%=serie%>]);</script>
						<div id="inscritosColumn" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
					</td>
				</tr>
		</table>
		
		<table style="width:95%" class="grafic grafica3" align="center">
				<tr>
					<td>
						<script>barStacked('Inscripciones Anuales', 'Por Carga','inscritosBar', 'Inscritos',[<%=nombrePeriodos%>], [<%=serie%>]);</script>
						<div id="inscritosBar" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
					</td>
				</tr>
		</table>
		
		<table style="width:95%" class="grafic grafica4" align="center">
				<tr>
					<td>
						<script>areaStacked('Inscripciones Anuales', 'Por Carga','inscritosArea', 'Inscritos',[<%=nombrePeriodos%>], [<%=serie%>]);</script>
						<div id="inscritosArea" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
					</td>
				</tr>
		</table>
		
		<table style="width:95%" class="grafic grafica5" align="center">
				<tr>
					<td>
						<script>Puntos('Distribución por Carga', '','inscritosPuntos', 'Inscritos',[<%=nombrePeriodos%>], [<%=serie%>]);</script>
						<div id="inscritosPuntos" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
					</td>
				</tr>
		</table>
		
	</div>
	<br>
	<script>
	(function($){
		var graficas = $('.graficasInsc').find('.grafic').hide();
		var grafica1 = $('.grafica1').show();
		$('.buttons').on('click','button',function(){
			graficas.hide();
			$this = $(this).prop('disabled', true).siblings().prop('disabled', false).end();
			$('.'+$this.data('grafic')).fadeIn();
		})
	})(jQuery);
	</script>
	</div>
</body>
</html>

<%@ include file= "../../cierra_enoc.jsp" %>