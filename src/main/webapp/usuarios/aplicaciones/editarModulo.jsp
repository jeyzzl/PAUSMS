<%@ page import= "aca.menu.spring.Modulo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String mensaje		= (String) request.getAttribute("mensaje");
	Modulo modulo		 	= (Modulo) request.getAttribute("modulo");
	
%>
<div class="container-fluid">
	<h2>Edit module title</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="modulos?id=<%=modulo.getMenuId()%>"><i class="fas fa-arrow-left"></i></a>
	</div>
<% 	if(mensaje.equals("1")){%>
	<div class="alert alert-success">
		Saved
	</div>
<%  }%>
	<form action="grabarModulo">
		<input type="hidden" name="id" value="<%=modulo.getModuloId()%>">
		<label for="Espanol">Spanish:</label>
		<input type="text" name="Espanol" class="form-control" value="<%=modulo.getNombreModulo()%>">	        
		<br>
		<label for="Ingles">English:</label>
		<input type="text" name="Ingles" class="form-control" value="<%=modulo.getNombreIngles()%>">
		<br>
		<div class="alert alert-info">		
			<button type="submit" class="btn btn-primary">Save</button>
		</div>		
	</form>
</div>