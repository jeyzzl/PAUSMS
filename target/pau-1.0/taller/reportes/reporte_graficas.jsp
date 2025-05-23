<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="becTipoU" scope="page" class="aca.bec.BecTipoUtil"/>

<%@ page import= "java.util.HashMap"%>

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
		body{
			background: white;
		}
	</style>
</head>
<body>

<%
		java.text.DecimalFormat getformato		= new java.text.DecimalFormat("#,##0.00;-#,##0.00");	
		

		String idEjercicio 						= (String) session.getAttribute("ejercicioId");
		String nombreTipoBeca 					= "";
		
		String presupuesto 						= "";
		ArrayList<aca.bec.BecTipo> tipoBecas 	= becTipoU.getListAllEjercicio(conEnoc, idEjercicio, " ORDER BY 2");
		for(aca.bec.BecTipo obj: tipoBecas){
			//TRAER EL NOMBRE DE CADA TIPO DE BECA
			nombreTipoBeca+= "'"+obj.getNombre() +"',";
			
			//TRAER EL PRESUPUESTO DE CADA TIPO DE BECA
			String meses = obj.getMeses();
			if(meses!=null && !meses.equals("null")){
				meses = meses.substring(1);
				meses = meses.replace('-', ',');
			}else{
				meses = "";
			}
			String var = aca.financiero.CCP_PresupuestoUtil.getSuma(conEnoc, obj.getPorcentaje(), meses, obj.getCuenta(), idEjercicio);
			presupuesto+=var+",";
		}
		
		
		String importe = "{name: 'Presupuesto', data: ["+presupuesto+"]}";
		
		String resultado = "";
		
		resultado+=importe;
		
				
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
						<script>columnStacked('Fondo de Becas', '','fondoBecas', 'Dinero',[<%=nombreTipoBeca%>], [<%=resultado%>]);</script>
						<div id="fondoBecas" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
					</td>
				</tr>
		</table>
		
		<table style="width:95%" class="grafic grafica2" align="center">
				<tr>
					<td>
						<script>columnBasic('Fondo de Becas', '','fondoBecasColumn', 'Dinero',[<%=nombreTipoBeca%>], [<%=resultado%>]);</script>
						<div id="fondoBecasColumn" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
					</td>
				</tr>
		</table>
		
		<table style="width:95%" class="grafic grafica3" align="center">
				<tr>
					<td>
						<script>barStacked('Fondo de Becas', '','fondoBecasBar', 'Dinero',[<%=nombreTipoBeca%>], [<%=resultado%>]);</script>
						<div id="fondoBecasBar" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
					</td>
				</tr>
		</table>
		
		<table style="width:95%" class="grafic grafica4" align="center">
				<tr>
					<td>
						<script>areaStacked('Fondo de Becas', '','fondoBecasArea', 'Dinero',[<%=nombreTipoBeca%>], [<%=resultado%>]);</script>
						<div id="fondoBecasArea" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
					</td>
				</tr>
		</table>
		
		<table style="width:95%" class="grafic grafica5" align="center">
				<tr>
					<td>
						<script>Puntos('Fondo de Becas', '','fondoBecasPuntos', 'Dinero',[<%=nombreTipoBeca%>], [<%=resultado%>]);</script>
						<div id="fondoBecasPuntos" style="min-width: 95%; height: 500px; margin: 0 auto"></div>
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