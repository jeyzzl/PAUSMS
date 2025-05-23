<%@ page import= "java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.spring.Usuarios"%>
<%@page import="aca.log.spring.LogOperacion"%>


<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.alumno.AlumPersonal"%>
<%
	String codigoAlumno					= (String) session.getAttribute("codigoAlumno");	
	List<LogOperacion> lisHistorial 	= (List<LogOperacion>) request.getAttribute("lisHistorial");
	HashMap<String,String> mapaUsuarios	= (HashMap<String,String>) request.getAttribute("mapaUsuarios");
%>
<body>	
	<div class="container-fluid">
		<h2><spring:message code='aca.Historial'/></h2>
		<div class="alert alert-info" text-align="left">
			<a class="btn btn-primary" href="residencia?codigoAlumno=<%=codigoAlumno%>"><i class="fas fa-arrow-left"></i></a>
		</div>
			<hr>
		<table id="table" class="table table-sm table-bordered">
			<thead class="table-info">	 
				<tr>
					<th>User</th>
					<th><spring:message code="aca.Fecha"/></th>
					<th><spring:message code="aca.Residencia"/></th>					
				</tr>
			</thead>
			<tbody>
				<%
					for(LogOperacion historial : lisHistorial){
						String usuario = "-";
						if (mapaUsuarios.containsKey(historial.getUsuario())){
							usuario = mapaUsuarios.get(historial.getUsuario());
						}						
				%>
			
			<tr>
				<td><%=usuario%></td>
				<td><%=historial.getFecha() %></td>
				<td><%=historial.getDatos().split("-")[1] %></td>
				<%
					}
				%>
			</tr>
			</tbody>
		</table>
	</div>
</body>
<script>
</script>
