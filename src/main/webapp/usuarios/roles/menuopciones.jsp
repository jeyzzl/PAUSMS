<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String rolId							= request.getParameter("rolId");
	String rolNombre						= request.getParameter("rolNombre");	
	
	List<aca.menu.spring.ModuloOpcion> lisOpciones 		= (List<aca.menu.spring.ModuloOpcion>)request.getAttribute("lisOpciones");
	HashMap<String, String> mapaMenus 					= (HashMap<String, String>)request.getAttribute("mapaMenus");
	HashMap<String, aca.menu.spring.Modulo> mapaMod		= (HashMap<String, aca.menu.spring.Modulo>)request.getAttribute("mapaModulos");
%>
<html>
<head>
<style>		
	body{
		background : white;
	}
</style>
<body>
<div class="container-fluid" >
	<h1>Roles for <%=rolNombre %></h1>
	<div class="alert alert-info" align="left">
		<a href="roles" class="btn btn-info"><i class="fas fa-arrow-left"></i></a>
	</div>	
	<table class="table table-sm table-bordered table-striped">
	<thead class="table-info">
	<tr>
		<th>#</th>
		<th>Id</th>
		<th>Menu</th>
		<th>Module Option</th>
		<th>Option Name</th>
		
	</tr>
	</thead>
<%
	int row = 0;	
	for(aca.menu.spring.ModuloOpcion opcion :lisOpciones){
		row++;
		
		String menuId 		= "0";
		String moduloNombre	="-";
		if (mapaMod.containsKey(opcion.getModuloId())){
			moduloNombre 	= mapaMod.get(opcion.getModuloId()).getNombreIngles();
			menuId 			= mapaMod.get(opcion.getModuloId()).getMenuId();
		}
		
		String menuNombre	= "-";
		if (mapaMenus.containsKey(menuId)){
			menuNombre 	= mapaMenus.get(menuId);
		}	
		
		String active = "<span class='m-1 badge bg-dark rounded-pill'>Active</span> ";
		if(opcion.getIcono().equals("I")){
			active = "<span class='m-1 badge bg-danger rounded-pill'>Inactive</span>";
		}else if (opcion.getIcono().equals("R")){
			active = "<span class='m-1 badge bg-primary rounded-pill'>In Review</span>";
		}
%>
	<tr>
		<td><%=row%></td>
		<td><%=opcion.getOpcionId()%></td>
		<td><%=menuNombre%></td>
		<td><%=moduloNombre%></td>
		<td><%=opcion.getNombreIngles() + active%></td>
		
	</tr>
<% 	} %>
	</table>
</div>		
</body>
</html>