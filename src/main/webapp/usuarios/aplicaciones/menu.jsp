<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.menu.spring.Menu"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String admin		 					= (String) request.getAttribute("admin");
	int alumnosSinPortal 					= (int) request.getAttribute("alumnosSinPortal");
	int maestrosSinPortal 					= (int) request.getAttribute("maestrosSinPortal");
	int mentorSinPortal 					= (int) request.getAttribute("mentorSinPortal");
	int maestrosSinPortafolio 				= (int) request.getAttribute("maestrosSinPortafolio");
	int maestrosSinPlanDiamante 			= (int) request.getAttribute("maestrosSinPlanDiamante");
	
	List<Menu> lisMenus 					= (List<Menu>) request.getAttribute("lisMenus");
	HashMap<String,String> mapaModulosMenu	= (HashMap<String,String>) request.getAttribute("mapaModulos");	
%>
<div class="container-fluid">
	<h2>Main Menu</h2>
	<div class="alert alert-info">
<% 	if (admin.equals("S") && alumnosSinPortal >= 1){%>	
		<a class="btn btn-primary" href="grabarAlumnos">Student Portal (<%=alumnosSinPortal%>)</a>
<%	} %>		
<% 	if (admin.equals("S") && maestrosSinPortal >= 1){%>	
		&nbsp;<a class="btn btn-primary" href="grabarMaestros">Professor (<%=maestrosSinPortal%>)</a>
<%	} %>
<% 	if (admin.equals("S") && mentorSinPortal >= 1){%>	
		&nbsp;<a class="btn btn-primary" href="grabarMentores">Mentor Portal (<%=mentorSinPortal%>)</a>
<%	} %>
<% 	if (admin.equals("S") && maestrosSinPortafolio >= 1){%>	
		&nbsp;<a class="btn btn-primary" href="grabarPortafolios">Portfolio (<%=maestrosSinPortafolio%>)</a>
<%	} %>
<% 	if (admin.equals("S") && maestrosSinPlanDiamante >= 1){%>	
		&nbsp;<a class="btn btn-primary" href="grabarPlanDiamante">Editor/Plans (<%=maestrosSinPlanDiamante%>)</a>
<%	} %>
	</div>
	<table class="table table-bordered">
	<thead class="table-info">	
	<tr>
		<th width="3%">Op.</th>
		<th width="3%">#</th>
	 	<th>Menu Title</th>
	 	<th>English Title</th>
	 	<th>Order</th>
	 	<th class="text-center">Modules</th>	 	
	</tr>
	</thead>
<% 
	int row = 0;
	for(Menu menu: lisMenus){
		row++;
		String modulos = "0";
		if (mapaModulosMenu.containsKey(menu.getMenuId())) modulos = mapaModulosMenu.get(menu.getMenuId());
%>
	<tr>
		<td><a title="Edit name" href="editarMenu?id=<%= menu.getMenuId()%>"><i class="fas fa-edit"></i></a></td>
    	<td><%=row %></td>
        <td><a href="modulos?id=<%= menu.getMenuId()%>"><%= menu.getMenuNombre()%></a></td>
        <td>&nbsp;<%= menu.getNombreIngles()%></td>
        <td><%= menu.getOrden()%></td>
        <td class="text-center"><%= modulos %></td>
    </tr>
<%	} %>	 
	</table>
</div>