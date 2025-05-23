<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">	
	function Mostrar(){	
		document.forma.submit();
	}
</script>
<%	
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;-###,##0.00");	
	String year				= aca.util.Fecha.getHoy().substring(6);	
	String fechaIni			= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	
	List<CatCarrera> lisCarreras 					= (List<CatCarrera>)request.getAttribute("lisCarreras");
	HashMap<String,CatFacultad> mapaFacultades		= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String,String> mapaTotalAlumnos			= (HashMap<String,String>)request.getAttribute("mapaTotalAlumnos");
	HashMap<String,String> mapaMovimientos			= (HashMap<String,String>)request.getAttribute("mapaMovimientos");
%>
<head>	
	<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
</head>
<body>
<div class="container-fluid">
	<h2>Inscritos por carreras</h2>
	<form id="forma" name="forma" action="inscritosgcas" method="post">
	<div class="alert alert-info">
		<a href="menu" class="btn btn-primary">Menú</a>
		Fecha Inicio: <input id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
		Fecha Final: <input id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
		<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>
	</div>
	</form>
	<table class="table table-sm table-bordered table-striped">
		<tr class="table-info">
			<th>#</th>
			<th>Facultad</th>
			<th>Clave</th>
			<th>Carrera</th>
			<th class="right">#Alum.</th>
			<th class="right">$Matricula</th>
			<th class="right">$Enseñanza</th>
			<th class="right">$Internado</th>
			<th class="right">$Pagaré</th>
			<th class="right">$Extemporanea</th>
			<th class="right">$Total</th>
		</tr>
<%
	int row = 0;
	int totAlumnos=0;
	double totMatricula = 0, totEnsenanza=0, totInternado=0, totPagare=0, totExtemporanea=0, totGeneral=0;
	for (CatCarrera carrera : lisCarreras){	
		row++;
		
		String facultadNombre = "-";
		if (mapaFacultades.containsKey(carrera.getFacultadId())){
			facultadNombre = mapaFacultades.get(carrera.getFacultadId()).getNombreCorto();
		}
		
		String total = "0";
		if (mapaTotalAlumnos.containsKey(carrera.getCarreraId())){
			total = mapaTotalAlumnos.get(carrera.getCarreraId());
			totAlumnos += Integer.parseInt(total);
		}
		
		String costoMatricula = "0";
		if (mapaMovimientos.containsKey(carrera.getCarreraId()+"01")){
			costoMatricula 	= mapaMovimientos.get(carrera.getCarreraId()+"01");
			totMatricula	+= Double.valueOf(costoMatricula);
		}
		
		String costoEnsenanza = "0";
		if (mapaMovimientos.containsKey(carrera.getCarreraId()+"02")){
			costoEnsenanza 	= mapaMovimientos.get(carrera.getCarreraId()+"02");
			totEnsenanza	+= Double.valueOf(costoEnsenanza);
		}
		
		String costoInternado = "0";
		if (mapaMovimientos.containsKey(carrera.getCarreraId()+"03")){
			costoInternado 	= mapaMovimientos.get(carrera.getCarreraId()+"03");
			totInternado	+= Double.valueOf(costoInternado);
		}
		
		String costoCuarto = "0";
		if (mapaMovimientos.containsKey(carrera.getCarreraId()+"11")){
			costoCuarto 	= mapaMovimientos.get(carrera.getCarreraId()+"11");
			totInternado	+= Double.valueOf(costoCuarto);
		}
		
		String costoComida = "0";
		if (mapaMovimientos.containsKey(carrera.getCarreraId()+"12")){
			costoComida 	= mapaMovimientos.get(carrera.getCarreraId()+"12");
			totInternado	+= Double.valueOf(costoComida);
		}		
		
		String costoPagare1 = "0";
		if (mapaMovimientos.containsKey(carrera.getCarreraId()+"08")){
			costoPagare1 	= mapaMovimientos.get(carrera.getCarreraId()+"08");
			totPagare 		+= Double.valueOf(costoPagare1);
		}
		
		String costoPagare2 = "0";
		if (mapaMovimientos.containsKey(carrera.getCarreraId()+"31")){
			costoPagare2 	= mapaMovimientos.get(carrera.getCarreraId()+"31");
			totPagare 		+= Double.valueOf(costoPagare2);
		}		
		
		String costoExtemporanea = "0";
		if (mapaMovimientos.containsKey(carrera.getCarreraId()+"24")){
			costoExtemporanea 	= mapaMovimientos.get(carrera.getCarreraId()+"24");
			totExtemporanea 	+= Double.valueOf(costoPagare1);
		}
		
		double totCarrera = Double.parseDouble(costoMatricula)+Double.parseDouble(costoEnsenanza)+Double.parseDouble(costoInternado)+Double.parseDouble(costoCuarto)+Double.parseDouble(costoComida)+
				Double.parseDouble(costoPagare1)+Double.parseDouble(costoPagare2)+Double.parseDouble(costoExtemporanea);
		totGeneral += totCarrera; 
%>
		<tr>
			<td><%=row%></td>
			<td><%=facultadNombre%></td>
			<td><%=carrera.getCarreraId()%></td>	
			<td><%=carrera.getNombreCarrera()%></td>			
			<td class="right"><%=total%></td>
			<td class="right"><%=getFormato.format(Double.parseDouble(costoMatricula))%></td>
			<td class="right"><%=getFormato.format(Double.parseDouble(costoEnsenanza))%></td>
			<td class="right"><%=getFormato.format(Double.parseDouble(costoInternado)+Double.parseDouble(costoCuarto)+Double.parseDouble(costoComida))%></td>
			<td class="right"><%=getFormato.format(Double.parseDouble(costoPagare1)+Double.parseDouble(costoPagare2))%></td>
			<td class="right"><%=getFormato.format(Double.parseDouble(costoExtemporanea))%></td>
			<td class="right"><%=getFormato.format(totCarrera)%></td>
		</tr>
<%				
	}
%>		
		<tr>
			<th colspan="4" style="text-align:right"> T O T A L &nbsp;</th>
			<th class="right"><%=totAlumnos%></th>
			<th class="right"><%=getFormato.format(totMatricula)%></th>
			<th class="right"><%=getFormato.format(totEnsenanza)%></th>
			<th class="right"><%=getFormato.format(totInternado)%></th>
			<th class="right"><%=getFormato.format(totPagare)%></th>
			<th class="right"><%=getFormato.format(totExtemporanea)%></th>
			<th class="right"><%=getFormato.format(totGeneral)%></th>
		</tr>
	</table>	
</div>
</body>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>