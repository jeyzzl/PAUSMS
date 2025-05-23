<%@page import="java.util.List" %>
<%@page import="java.util.Date" %>
<%@page import="aca.alumno.spring.AlumPersonal" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<head></head>
<%
	List<AlumPersonal> lista = (List<AlumPersonal>)request.getAttribute("lista");

	int counter = 0;
	String fechaNacimiento = "";
%>
<body>
	<div class="container-fluid">
		<h2>Registered students</h2>
		<div class="alert alert-info" role="alert">
			<a href="menu" class="btn btn-primary">Return</a>
		</div>
	</div>
	<table id="table" class="table" >
		<thead>
			<tr>
				<th>No.</th>
				<th>Code</th>
				<th>Name</th>
				<th>Birthday</th>
				<th>Email</th>
				<th>Status</th>
			</tr>
		</thead>
		<tbody>
<%		for(AlumPersonal alumno : lista){ 
			counter++;
			// Acortar fecha YYYY-MM-DD HH-MM-SS --> YYYY-MM-DD
			fechaNacimiento = alumno.getFNacimiento().substring(0,10);
%>
			<tr>
				<td><%=counter%></td>
				<td><%=alumno.getCodigoPersonal()%></td>
				<td><%=alumno.getNombreLegal()%></td>
				<td><%=fechaNacimiento%></td>
				<td><%=alumno.getEmail()%></td>
				<td><%=alumno.getEstado()%></td>
			</tr>
<% }%>
		</tbody>
	</table>
</body>