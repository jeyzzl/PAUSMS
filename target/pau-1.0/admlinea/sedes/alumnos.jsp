<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmSolicitud"%>
<%@page import="aca.admision.spring.AdmUsuario"%>
<%@page import="aca.admision.spring.AdmAcademico"%>
<%@page import="aca.admision.spring.AdmReservada"%>
<%@page import="aca.admision.spring.AdmTutor"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatEstado"%>
<%@page import="aca.catalogo.spring.CatCiudad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<head>		
</head>
<%
	List<AdmReservada> lisReservados 		= (List<AdmReservada>)request.getAttribute("lisReservados");

	HashMap<String,AdmSolicitud> mapaSolicitudes 	= (HashMap<String,AdmSolicitud>)request.getAttribute("mapaSolicitudes");
	HashMap<String,AdmUsuario> mapaUsuarios 		= (HashMap<String,AdmUsuario>)request.getAttribute("mapaUsuarios");
	HashMap<String,AdmAcademico> mapaAcademico 		= (HashMap<String,AdmAcademico>)request.getAttribute("mapaAcademico");
	HashMap<String,CatCarrera> mapaCarreras			= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,AdmTutor> mapaUbicacion 			= (HashMap<String,AdmTutor>)request.getAttribute("mapaUbicacion");
	HashMap<String,CatPais> mapaPais 				= (HashMap<String,CatPais>)request.getAttribute("mapaPais");
	HashMap<String,CatEstado> mapaEstado 			= (HashMap<String,CatEstado>)request.getAttribute("mapaEstado");
	HashMap<String,CatCiudad> mapaCiudad 			= (HashMap<String,CatCiudad>)request.getAttribute("mapaCiudad");
%>
<body>
<div class="container-fluid">
	<h1>Students</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="lista"><spring:message code="aca.Regresar"/></a>
	</div>
	<table class="table table-bordered table-sm">
	<tr>
		<th>#</th>
		<th>Date</th>
		<th>Key</th>
		<th>Student</th>
		<th>Email</th>
		<th>Phone</th>
		<th>Course</th>
		<th>Status</th>
		<th>Country</th>
		<th>Province</th>
		<th>Village</th>
	</tr>
<%		
	int cont = 1;
	for(AdmReservada reservada: lisReservados){
		String nombreAlumno = "-";
		String correo 		= "-";
		String paisId 		= "-";
		String estadoId	 	= "-";
		String ciudadId	 	= "-";
		String usuarioId	= "0";
		if (mapaSolicitudes.containsKey(reservada.getFolio())){			
			usuarioId = mapaSolicitudes.get(reservada.getFolio()).getUsuarioId();
			if (mapaUsuarios.containsKey(usuarioId)){
				AdmUsuario usuario 	= mapaUsuarios.get(usuarioId);
				nombreAlumno 		= usuario.getNombre()+" "+usuario.getApellidoPaterno()+" "+usuario.getApellidoMaterno();
				correo 				= usuario.getEmail();
			}			
		}
		
		String nombrePais = "-";
		if(mapaUbicacion.containsKey(reservada.getFolio())){
			paisId = mapaUbicacion.get(reservada.getFolio()).getPaisId();
			estadoId = mapaUbicacion.get(reservada.getFolio()).getEstadoId();
			ciudadId = mapaUbicacion.get(reservada.getFolio()).getCiudadId();
			
			if(mapaPais.containsKey(paisId)){
				nombrePais = mapaPais.get(paisId).getNombrePais();
			}
		}
			
		String carrera = "-";
		if (mapaAcademico.containsKey(reservada.getFolio())){
			carrera = mapaAcademico.get(reservada.getFolio()).getCarreraId();
			if (mapaCarreras.containsKey(carrera)){
				carrera = mapaCarreras.get(carrera).getNombreCarrera();
			}
		}
		
		String status = "-";
		if (reservada.getEstado().equals("A")){
			status = "Booked";
		}
		if (reservada.getEstado().equals("C")){
			status = "Confirmed";
		}
		
		String nombreEstado = "-";
		if (mapaEstado.containsKey(paisId+estadoId)){
			nombreEstado = mapaEstado.get(paisId+estadoId).getNombreEstado();
		}
		
		String nombreCiudad = "-";
		if (mapaCiudad.containsKey(paisId+estadoId+ciudadId)){
			nombreCiudad = mapaCiudad.get(paisId+estadoId+ciudadId).getNombreCiudad();
		}
		
		String telefono = "0";
		if(mapaUbicacion.containsKey(reservada.getFolio())){
			telefono = mapaUbicacion.get(reservada.getFolio()).getTelefono();
		}
		
%>
	<tr>
		<td><%=cont++%></td>
		<td><%=reservada.getFecha()%></td>
		<td><%=reservada.getFolio()%></td>
		<td><%=nombreAlumno%></td>
		<td><%=correo%></td>
		<td><%=telefono%></td>
		<td><%=carrera%></td>
		<td><%=status%></td> 
		<td><%=nombrePais%></td>
		<td><%=nombreEstado%></td>
		<td><%=nombreCiudad%></td>
	</tr>
<% 	
	} 
%>
	</table>
</div>
</body>
</html>