<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="AdmSolicitudU"  class="aca.admision.AdmSolicitudUtil" scope="page"/>
<jsp:useBean id="AdmAcademicoU"  class="aca.admision.AdmAcademicoUtil" scope="page"/>

<html>
<head>
	<link rel="stylesheet" href="../../js/tablesorter/themes/blue/style.css"/>
</head>
<%	
	String year = request.getParameter("Year")==null?"0":request.getParameter("Year");

	// Lista de alumnos admitidos
	ArrayList<adm.alumno.AdmSolicitud> lisAlumnos =  AdmSolicitudU.lisAlumnosEnProceso(conEnoc, year, "WHERE FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_PROCESO WHERE F_DOCUMENTOS IS NOT NULL AND TO_CHAR(F_DOCUMENTOS,'YYYY')= '"+year+"')"," ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");	
	
	// Map de fecha de aceptación
	java.util.HashMap<String,String> mapFecha	= AdmSolicitudU.mapFechaDocumentos(conEnoc, year);
	
	// Map de datos academicos
	java.util.HashMap<String,aca.admision.AdmAcademico> mapAcademico	= AdmAcademicoU.mapAlumnos(conEnoc, " WHERE FOLIO IN(SELECT FOLIO FROM SALOMON.ADM_PROCESO WHERE F_DOCUMENTOS IS NOT NULL AND TO_CHAR(F_DOCUMENTOS,'YYYY')= '"+year+"')");
%>
<body>
<div class="container-fluid">
	<h2>List of Students<small class="text-muted fs-5">( Authorized Documents)</small></h2>
	<div class="alert alert-info">
		<a href="estadistica?Year=<%=year%>" class="btn btn-primary">Return</a>
	</div>
	<table id="tableDocumentos" class="table table-condensed">
		<thead>
			<tr>
				<th width="2%">#</th>
				<th width="3%">No.</th>
				<th width="16%">Surnames</th>
				<th width="12%">Name</th>
				<th width="24%">Degree</th>
				<th width="13%">Email</th>
				<th width="8%">Country</th>
				<th width="14%">State</th>
				<th width="8%">Document Date</th>
			</tr>
		</thead>
<%
	int row=0;
	int valor=5;
	for (adm.alumno.AdmSolicitud solicitud :  lisAlumnos){
		row++;
		String fecha = "";
		if (mapFecha.containsKey(solicitud.getFolio())){
			fecha = mapFecha.get(solicitud.getFolio());
		}
		String nombrePais 	= aca.catalogo.PaisUtil.getNombrePais(conEnoc, solicitud.getPaisId());
		String nombreEstado = aca.catalogo.EstadoUtil.getNombreEstado(conEnoc, solicitud.getPaisId(), solicitud.getEstadoId());
		
		String carrera = "0";
		if (mapAcademico.containsKey( solicitud.getFolio() )){
			carrera = mapAcademico.get(solicitud.getFolio()).getCarreraId();
		}
		String nombreCarrera = aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, carrera);
%>
		<tr>
			<td><%=row%></td>
			<td><%=solicitud.getFolio()%></td>
			<td><%=solicitud.getApellidoPaterno() %>&nbsp;<%=solicitud.getApellidoMaterno()%>
			<td><%=solicitud.getNombre()%></td>
			<td><%=nombreCarrera%></td>
			<td><%=solicitud.getEmail()%></td>
			<td><%=nombrePais%></td>
			<td><%=nombreEstado%></td>
			<td><%=fecha.substring(6, 10)+"/"+fecha.substring(3, 5)+"/"+fecha.substring(0, 2)%></td>
		</tr>
<%
	}
%>
	</table>
</div>
</body>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script src="../../js/tablesorter/jquery.tablesorter.js"></script>
<script type="text/javascript">
	jQuery('#tableDocumentos').tablesorter();
</script>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>