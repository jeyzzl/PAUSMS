<%@ page import= "aca.menu.spring.Menu"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String mensaje		= (String) request.getAttribute("mensaje");
	Menu menu		 	= (Menu) request.getAttribute("menu");
	
%>
<div class="container-fluid">
	<h2>Edit menu title</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="menu"><i class="fas fa-arrow-left"></i></a>
	</div>
<% 	if(mensaje.equals("1")){%>
	<div class="alert alert-success">
		Saved
	</div>
<%  }%>
	<form action="grabarMenu">
		<input type="hidden" name="id" value="<%=menu.getMenuId()%>">
		<label for="Espanol">Spanish:</label>
		<input type="text" name="Espanol" class="form-control" value="<%=menu.getMenuNombre()%>">	        
		<br>
		<label for="Ingles">English:</label>
		<input type="text" name="Ingles" class="form-control" value="<%=menu.getNombreIngles()%>">
		<br>
		<label for="Orden">Order:</label>
		<input type="text" name="Orden" class="form-control" value="<%=menu.getOrden()%>">
		<br>
		<div class="alert alert-info">		
			<button type="submit" class="btn btn-primary">Save</button>
		</div>
	</form>
</div>