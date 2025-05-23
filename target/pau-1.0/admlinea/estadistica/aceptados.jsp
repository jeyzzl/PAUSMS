<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="AdmSolicitudU"  class="aca.admision.AdmSolicitudUtil" scope="page"/>
<jsp:useBean id="AdmAcademicoU"  class="aca.admision.AdmAcademicoUtil" scope="page"/>
<jsp:useBean id="InscritosU"  class="aca.vista.InscritosUtil" scope="page"/>

<html>
<head>
	<link rel="stylesheet" href="../../js/tablesorter/themes/blue/style.css"/>
</head>
<%	
	String year = request.getParameter("Year")==null?"0":request.getParameter("Year");

	// Lista de alumnos admitidos
	ArrayList<adm.alumno.AdmSolicitud> lisAlumnos =  AdmSolicitudU.lisAlumnosEnProceso(conEnoc, year, "WHERE FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_PROCESO WHERE F_ADMISION IS NOT NULL AND TO_CHAR(F_ADMISION,'YYYY')= '"+year+"')"," ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
	
	// Map de fecha de aceptación
	java.util.HashMap<String,String> mapFecha	= AdmSolicitudU.mapFechaAceptado(conEnoc, year);
	
	// Map de datos academicos
	java.util.HashMap<String,aca.admision.AdmAcademico> mapAcademico	= AdmAcademicoU.mapAlumnos(conEnoc, " WHERE FOLIO IN(SELECT FOLIO FROM SALOMON.ADM_PROCESO WHERE F_ADMISION IS NOT NULL AND TO_CHAR(F_ADMISION,'YYYY')= '"+year+"')");
	
	// Map de datos academicos
	java.util.HashMap<String,String> mapAlumnosTelefono	= AdmSolicitudU.mapAlumnosTelefono(conEnoc, year);
	
	// Map de alumnos inscritos
	java.util.HashMap<String, String> mapaInscritos = InscritosU.getMapaInscritos(conEnoc);
%>
<body>
<div class="container-fluid">
	<h1>List of Students<small class="text-muted fs-4">( Admitted)</small></h1>
	<div class="alert alert-info">
		<a href="estadistica?Year=<%=year%>" class="btn btn-primary">Return</a>
	</div>
	<table id="tableAceptados" class="table table-bordered table-striped">
		<thead>
			<tr>
				<th width="2%">#</th>
				<th width="3%">No.</th>
				<th width="6%">Student ID</th>
				<th width="12%">Surnames</th>			
				<th width="10%">Name</th>
				<th width="10%">Modality</th>
				<th width="20%">Degree</th>
				<th width="10%">Email</th>
				<th width="6%">Phone Number</th>
				<th width="7%">Country</th>
				<th width="7%">State</th>
				<th width="6%">Acceptance Date</th>
				<th width="3%">Enrolled</th>
			</tr>
		</thead>
<%
	int row=0;	
	for (adm.alumno.AdmSolicitud solicitud :  lisAlumnos){
		row++;
		String fecha = ""; 
		if (mapFecha.containsKey(solicitud.getFolio())){
			fecha = mapFecha.get(solicitud.getFolio());
		}
		String nombrePais 	= aca.catalogo.PaisUtil.getNombrePais(conEnoc, solicitud.getPaisId());
		String nombreEstado = aca.catalogo.EstadoUtil.getNombreEstado(conEnoc, solicitud.getPaisId(), solicitud.getEstadoId());
		
		String carrera 		= "0";
		String modalidad	= "0";
		if (mapAcademico.containsKey( solicitud.getFolio() )){
			carrera 		= mapAcademico.get(solicitud.getFolio()).getCarreraId();
			modalidad		= mapAcademico.get(solicitud.getFolio()).getModalidad();
		}
		String nombreCarrera 	= aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, carrera);
		String nombreModalidad	= aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, modalidad);
		
		String inscrito = "No";
		if (mapaInscritos.containsKey( solicitud.getMatricula())){
			inscrito = "Si";
		}
		
		String telefono = "-";
		if (mapAlumnosTelefono.containsKey( solicitud.getMatricula())){
			if(mapAlumnosTelefono.get(solicitud.getMatricula()) != null)
			telefono = mapAlumnosTelefono.get(solicitud.getMatricula());
		}
%>
		<tr>
			<td><%=row%></td>
			<td><%=solicitud.getFolio()%></td>
			<td><%=solicitud.getMatricula()%></td>
			<td><%=solicitud.getApellidoPaterno() %>&nbsp;<%=solicitud.getApellidoMaterno()%>
			<td><%=solicitud.getNombre()%></td>
			<td><%=nombreModalidad%></td>
			<td><%=nombreCarrera%></td>
			<td><%=solicitud.getEmail()%></td>
			<td><%=telefono%></td>
			<td><%=nombrePais%></td>
			<td><%=nombreEstado%></td>
			<td><%=fecha.substring(6, 10)+"/"+fecha.substring(3, 5)+"/"+fecha.substring(0, 2)%></td>
			<td><%=inscrito%></td>
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
<%@ include file= "../../cierra_enoc.jsp" %>