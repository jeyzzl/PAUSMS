<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.emp.spring.EmpContacto"%>
<%@page import="aca.vista.spring.Maestros"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String codigoPersonal				= (String)session.getAttribute("codigoPersonal");	
	List<Maestros> lisDirectores		= (List<Maestros>) request.getAttribute("lisDirectores");
	List<Maestros> lisCoordinadores		= (List<Maestros>) request.getAttribute("lisCoordinadores");
	HashMap<String,EmpContacto> mapaContactos	= (HashMap<String,EmpContacto>) request.getAttribute("mapaContactos");	
	
	//List<CargaAlumno> lisCargas 				= (List<CargaAlumno>)request.getAttribute("lisCargasAlumnos");	
	//HashMap<String,CatModalidad> mapaModalidad	= (HashMap<String,CatModalidad>)request.getAttribute("mapaModalidad");	
%>
<div class="container-fluid">
	<h3>Deans and Directors List</h3>
	<hr>
	<table class="table table-sm table-bordered">
	<thead class="table-dark">
		<tr>
			<th>#</th>
			<th>ID</th>
			<th>Name</th>
			<th>Phone</th>
			<th>Email</th>
		</tr>
	</thead>
	<tbody>	
<%
	int row = 1;	
	for(Maestros directores : lisDirectores){
		
		String directorTelefono = "-";
		String directorCorreo   = "-";

		if (mapaContactos.containsKey(directores.getCodigoPersonal())){
			directorTelefono = mapaContactos.get(directores.getCodigoPersonal()).getTelefono();
			directorCorreo = mapaContactos.get(directores.getCodigoPersonal()).getCorreo();
		}
		
%>
	<tr> 
	       <td><%=row++%></td>
           <td><%=directores.getCodigoPersonal() %></td>
           <td><%=directores.getNombre()%> <%=directores.getApellidoPaterno()%> <%=directores.getApellidoMaterno()%></td>
           <td><%=directorTelefono%></td>         
		   <td><%=directorCorreo %></td>           
     </tr> 
<%
	}
%>
	</tbody>
	</table>
	<h3>Contact List</h3>
	<hr>
	<table class="table table-sm table-bordered">
	<thead class="table-dark">
		<tr>
			<th>#</th>
			<th>ID</th>
			<th>Name</th>
			<th>Phone</th>
			<th>Email</th>
		</tr>
	</thead>
	<tbody>	
<%
	for(Maestros coordinadores : lisCoordinadores){
		
		String coordinadorTelefono = "-";
		String coordinadorCorreo   = "-";
		if (mapaContactos.containsKey(coordinadores.getCodigoPersonal())){
			coordinadorTelefono = mapaContactos.get(coordinadores.getCodigoPersonal()).getTelefono();
			coordinadorCorreo = mapaContactos.get(coordinadores.getCodigoPersonal()).getCorreo();

		}
		
%>
	<tr> 
	       <td><%=row++%></td>
           <td><%=coordinadores.getCodigoPersonal() %></td>
           <td><%=coordinadores.getNombre()%> <%=coordinadores.getApellidoPaterno()%> <%=coordinadores.getApellidoMaterno()%></td>
           <td><%=coordinadorTelefono%></td>         
		   <td><%=coordinadorCorreo%></td>           
     </tr> 
<%
	}
%>
	</tbody>
	</table>
</div>