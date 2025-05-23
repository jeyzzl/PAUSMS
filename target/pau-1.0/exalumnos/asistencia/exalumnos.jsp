<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.exa.spring.ExaEvento"%>
<%@page import="aca.exa.spring.ExaAlumEvento"%>
<%@page import="aca.exa.spring.ExaCorreo"%>
<%@page import="aca.exa.spring.ExaTelefono"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguroJquery.jsf" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="evento" scope="page" class="aca.exa.ExaAlumEvento"/>
<jsp:useBean id="eventoU" scope="page" class="aca.exa.ExaAlumEventoUtil"/>
<jsp:useBean id="AlumPersonal" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="CorreoU" scope="page" class="aca.exa.ExaCorreoUtil"/>
<jsp:useBean id="TelefonoU" scope="page" class="aca.exa.ExaTelefonoUtil"/>

<%
	String eventoId 		= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
	String matricula 		= (String) session.getAttribute("codigoAlumno");
	String nombreAlumno 	= (String) request.getAttribute("nombreAlumno");
	String eventoNombre 	= (String) request.getAttribute("eventoNombre");

	String accion 			= request.getParameter("Accion")==null?"":request.getParameter("Accion");
	String msj 				= "";
	
	List<ExaAlumEvento> lisAlumnos 				= (List<ExaAlumEvento>) request.getAttribute("lisAlumnos");
	
	//Mapa de correos
	HashMap<String, ExaCorreo> mapCorreos 		= (HashMap<String, ExaCorreo>) request.getAttribute("mapCorreos");
	
	//Mapa de telefonos
	HashMap<String, ExaTelefono> mapTelefonos 	= (HashMap<String, ExaTelefono>) request.getAttribute("mapTelefonos");
	
	HashMap<String, AlumPersonal> mapAlumnos 	= (HashMap<String, AlumPersonal>) request.getAttribute("mapAlumnos");
%>
<style>
	.icon-remove{
		cursor: pointer;
	}
</style>
<body>
	<div class="container-fluid">
	<h2><%=eventoNombre%></h2>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary btn-sm" href="evento"><spring:message code="aca.Regresar"/></a>&nbsp;
		<%=matricula%> &nbsp;<%=nombreAlumno%>&nbsp;&nbsp;
		<a href="agregar?EventoId=<%=eventoId %>" class="btn btn-primary btn-sm">Agregar</a>		
	</div>
	<table  class="table table-condensed table-fontsmall table-bordered table-striped">
	
		<%if(!msj.equals("")){ %>
		<tr>
			<td style="text-align:center;" colspan="7"><font color="#0070C1" size="4"><%=msj %></font></td>
		</tr>
		<%} %>
		<tr>
			<th width="5%"><spring:message code="aca.Numero"/></th>
			<th width="3%"><spring:message code="aca.Eliminar"/>:</th>
			<th width="15%"><spring:message code="aca.Matricula"/></th>
			<th width="35%"><spring:message code="aca.Nombre"/></th>
			<th width="10%"><spring:message code="aca.FechaNac"/></th>
			<th width="20%"><spring:message code="aca.Correo"/></th>
			<th width="15%"><spring:message code="aca.Telefono"/></th>
		</tr>
<%
	int row = 0;
	for(ExaAlumEvento alum : lisAlumnos){		 
		row++;
		String fechaNacimiento = "-";
		String alumnoNombre = "-";
		if (mapAlumnos.containsKey(alum.getMatricula())){
			alumnoNombre 	=  mapAlumnos.get(alum.getMatricula()).getNombre()+" "+mapAlumnos.get(alum.getMatricula()).getApellidoPaterno()+" "+mapAlumnos.get(alum.getMatricula()).getApellidoMaterno();
			fechaNacimiento = mapAlumnos.get(alum.getMatricula()).getFNacimiento();
		}
%>
			<tr>
				<td align="center"><%=row%></td>
				<td style="text-align:center;"><i onclick="if(confirm('¿Esta seguro que desea quitar este alumno?')){location.href='borrar?EventoId=<%=eventoId%>&AlumEventoId=<%=alum.getAlumEventoId()%>';}" class="fas fa-trash-alt"></i></td>
				<td><%= alum.getMatricula() %></td>
				<td><%= alumnoNombre %> </td>
				<td><%= fechaNacimiento%></td>
				<td><%= mapCorreos.get(alum.getMatricula())==null?"&nbsp;":mapCorreos.get(alum.getMatricula()).getCorreo()%></td>
				<td><%= mapTelefonos.get(alum.getMatricula())==null?"&nbsp;":mapTelefonos.get(alum.getMatricula()).getTelefono() %></td>
			</tr>
<%	} %>
	</table>
	</div>
</body>