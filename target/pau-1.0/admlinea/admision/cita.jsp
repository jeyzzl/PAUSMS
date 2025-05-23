<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.admision.spring.AdmEvento"%>
<%@page import="aca.admision.spring.AdmReservada"%>

<html>
<head>	
</head>
<%	
	String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String nombreAlumno 	= (String)request.getAttribute("nombreAlumno");
	AdmEvento evento 		= (AdmEvento)request.getAttribute("admEvento");
	AdmReservada reservada 	= (AdmReservada)request.getAttribute("admReservada");
	String estadoCita		= "";
	
	if (reservada.getEstado().equals("A")) estadoCita = "Reservada";
	if (reservada.getEstado().equals("C")) estadoCita = "Confirmada";
	if (reservada.getEstado().equals("I")) estadoCita = "Inactiva";
%>
<body>
<div class="container-fluid">
	<h2>Sede del alumno<small>( <%=folio%> - <%=nombreAlumno%>)</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="mostrarProceso?Folio=<%=folio%>"><spring:message code="aca.Regresar"/></a>
	</div>
	<table class="table table-bordered">
	<tr>
		<td width="10%"><b>Sede:</b></td>
		<td width="90%"><%=evento.getEventoNombre()%></td>
	</tr>
	<tr>
		<td><b>Lugar:</b></td>
		<td><%=evento.getLugar()%></td>
	</tr>
	<tr>
		<td><b>Fecha/Registro:</b></td>
		<td><%=reservada.getFecha()%></td>
	</tr>
	
	<tr>
		<td><b>Estado:</b></td>
		<td><%=estadoCita%></td>
	</tr>
	
	<tr>
		<td colspan="2">
		<% if (reservada.getEstado().equals("A")){ %>
			<a class="btn btn-primary" href="cambiarEstado?Folio=<%=folio%>&Estado=C">Confirmar</a> &nbsp;
		<% }else{ %>	 
			<a class="btn btn-primary" href="cambiarEstado?Folio=<%=folio%>&Estado=A">Liberar</a>
		<% } %>	
		</td>		
	</tr>
	
	</table>	
</div>
</body>
</html>