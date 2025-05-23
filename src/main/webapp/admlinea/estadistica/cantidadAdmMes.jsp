<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmSolicitud"%>
<%@page import="aca.admision.spring.AdmAcademico"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatEstado"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<html>
<head>
	<link rel="stylesheet" href="../../js/tablesorter/themes/blue/style.css"/>
</head>
<%	
	String year = (String) request.getAttribute("year");

	// Lista de alumnos admitidos
	List<AdmSolicitud> lisAlumnos =  (List<AdmSolicitud>) request.getAttribute("lisAlumnos");
	
	// Map de fecha de aceptación
	HashMap<String,String> mapFecha	= (HashMap<String,String>) request.getAttribute("mapFecha");
	
	// Map de datos academicos
	HashMap<String,AdmAcademico> mapAcademico	= (HashMap<String,AdmAcademico>) request.getAttribute("mapAcademico");
	
	HashMap<String,CatPais> mapaPais 		= (HashMap<String,CatPais>)  request.getAttribute("mapaPais");
	HashMap<String,CatEstado> mapaEstado 	= (HashMap<String,CatEstado>)  request.getAttribute("mapaEstado");
	HashMap<String,CatCarrera> mapaCarrera  = (HashMap<String,CatCarrera>)  request.getAttribute("mapaCarrera");
%>
<body>
<div class="container-fluid">
	<h1>List of Students<small class="text-muted fs-4" >( Admitted)</small></h1>
	<div class="alert alert-info">
		<a href="estadistica?Year=<%=year%>" class="btn btn-primary">Return</a>
	</div>
	<table id="tableAceptados" class="table table-bordered table-striped">
		<thead>
			<tr>
				<th width="2%">#</th>
				<th width="3%">No.</th>
				<th width="6%">Student ID</th>
				<th width="16%">Surnames</th>			
				<th width="10%">Name</th>
				<th width="24%">Degree</th>
				<th width="13%">Email</th>
				<th width="8%">Country</th>
				<th width="12%">State</th>
				<th width="6%">Acceptance Date</th>
			</tr>
		</thead>
<%
	int row=0;	
	for (AdmSolicitud solicitud :  lisAlumnos){
		row++;
		String fecha = ""; 
		if (mapFecha.containsKey(solicitud.getFolio())){
			fecha = mapFecha.get(solicitud.getFolio());
		}

		String nombrePais 	= "";
		if (mapaPais.containsKey(solicitud.getPaisId())){
			nombrePais = mapaPais.get(solicitud.getPaisId()).getNombrePais();
		}
		
		String nombreEstado = "";
		if (mapaEstado.containsKey(solicitud.getPaisId()+solicitud.getEstadoId())){
			nombreEstado = mapaEstado.get(solicitud.getPaisId()+solicitud.getEstadoId()).getNombreEstado();
		}
		
		String carrera = "0";
		if (mapAcademico.containsKey( solicitud.getFolio() )){
			carrera = mapAcademico.get(solicitud.getFolio()).getCarreraId();
		}
		String nombreCarrera = "";
		if (mapaCarrera.containsKey(carrera)){
			nombreCarrera = mapaCarrera.get(carrera).getNombreCarrera();
		}
%>
		<tr>
			<td><%=row%></td>
			<td><%=solicitud.getFolio()%></td>
			<td><%=solicitud.getMatricula()%></td>
			<td><%=solicitud.getApellidoPaterno() %>&nbsp;<%=solicitud.getApellidoMaterno()%>
			<td><%=solicitud.getNombre()%></td>
			<td><%=nombreCarrera%></td>
			<td><%=solicitud.getEmail()%></td>
			<td><%=nombrePais%></td>
			<td><%=nombreEstado%></td>
			<td><%=fecha%></td>
		</tr>
<%
	}
%>
	</table>
</div>
</body>
<script src="../../js/tablesorter/jquery.tablesorter.js"></script>
<script type="text/javascript">
	jQuery('#tableAceptados').tablesorter();
</script>
</html>