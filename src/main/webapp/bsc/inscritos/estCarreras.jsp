<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CatFacultad"  class="aca.catalogo.CatFacultad" scope="page"/>
<jsp:useBean id="CatFacultadU"  class="aca.catalogo.CatFacultadUtil" scope="page"/>
<jsp:useBean id="CatCarreraUtil"  class="aca.catalogo.CatCarreraUtil" scope="page"/>

<html>
<%
	String tema = request.getParameter("tema")==null?"":request.getParameter("tema");
%>
	<head>
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
			<style>
				.grafic{
					border:1px solid black;
				}
			</style>
		</style>  
	</head>
<%
	String facultadId = request.getParameter("FacultadId");
	CatFacultad = CatFacultadU.mapeaRegId(conEnoc, facultadId);

	ArrayList<aca.catalogo.CatCarrera> listaCarreras = CatCarreraUtil.getListCarrera(conEnoc, facultadId, " ORDER BY NOMBRE_CARRERA");
	String carreraId = request.getParameter("CarreraId")==null?"":request.getParameter("CarreraId");
	
	//PERIODOS DESDE EL 2002
	String Periodos = "";
	String nombrePeriodos = "";
	
	ArrayList<String> periodos = aca.carga.CargaUtil.getPeriodos(conEnoc, " ORDER BY 1");
	for(String periodo: periodos){
		Periodos += "'"+periodo+"',";
		nombrePeriodos += "' 20"+periodo.substring(0, 2)+" - 20"+periodo.substring(2, 4)+" ',";
	}
	Periodos 		= Periodos.substring(0, Periodos.length()-1);
	nombrePeriodos  = nombrePeriodos.substring(0, nombrePeriodos.length()-1);
	
	String [] tipos = "A,B,C,D".split(",");
	
	String serie = "";
	
	ArrayList<String> arr = aca.vista.EstadisticaUtil.getInscritosCarrera(conEnoc, Periodos, carreraId, " ORDER BY 2,1");
	for(String tipo : tipos){
		String totales = "";
		for(String str : arr){
			String [] arrTot = str.split("\\$")[1].split("-->");
			if(arrTot[1].equals(tipo)){
				totales += ""+arrTot[0]+",";
			}
		}
		serie += "{name: '"+tipo+"', data: ["+totales+"]},";
	}
	
	serie = serie.substring(0, serie.length()-1);
%>
	<body>
	<div class="container-fluid">
		<h2>Inscritos de la <%=CatFacultad.getTitulo()%> de <%=CatFacultad.getNombreFacultad()%></h2>
	<form>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="facultad"><spring:message code="aca.Regresar"/></a>
			&nbsp;&nbsp;
				<input type="hidden" name="FacultadId" value="<%=facultadId%>">
				<select name="CarreraId" onchange="submit();" class="input input-large">
						<option value="" <%=carreraId.equals("")?"Selected":""%>><spring:message code='aca.SeleccioneUnaCarrera'/></option>
						<optgroup label='------------------------------------------------------------------------------------'></optgroup>
					<%	for(aca.catalogo.CatCarrera carrera : listaCarreras){%>
							<option value="<%=carrera.getCarreraId()%>" <%=carreraId.equals(carrera.getCarreraId())?"Selected":""%>><%=carrera.getNombreCarrera()%></option>
					<%	}%>
				</select>
			&nbsp;&nbsp;
			Temas:
				<button <%if(tema.equals(""))out.print("disabled='disabled'");%> class="btn" onclick="location.href='estCarreras?FacultadId=<%=facultadId%>&CarreraId=<%=carreraId%>&tema='">Default</button>
				<button <%if(tema.equals("red.js"))out.print("disabled='disabled'");%> class="btn" onclick="location.href='estCarreras?FacultadId=<%=facultadId%>&CarreraId=<%=carreraId%>&tema=grid.js'">Rojo</button>
				<button <%if(tema.equals("gray.js"))out.print("disabled='disabled'");%> class="btn" onclick="location.href='estCarreras?FacultadId=<%=facultadId%>&CarreraId=<%=carreraId%>&tema=gray.js'">Gris</button>
				<button <%if(tema.equals("dark-blue.js"))out.print("disabled='disabled'");%> class="btn" onclick="location.href='estCarreras?FacultadId=<%=facultadId%>&CarreraId=<%=carreraId%>&tema=dark-blue.js'">Azul</button>
				<button <%if(tema.equals("dark-green.js"))out.print("disabled='disabled'");%> class="btn" onclick="location.href='estCarreras?FacultadId=<%=facultadId%>&CarreraId=<%=carreraId%>&tema=dark-green.js'">Verde</button>
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
						<script>Puntos('Inscripciones Anuales', 'Por Carga','inscritosPuntos', 'Inscritos',[<%=nombrePeriodos%>], [<%=serie%>]);</script>
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