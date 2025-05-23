<%@ page import= "aca.menu.spring.ModuloOpcion"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String menuId				= (String) request.getAttribute("menuId");
	ModuloOpcion moduloOpcion	= (ModuloOpcion) request.getAttribute("moduloOpcion");
	String mensaje				= (String) request.getAttribute("mensaje");
	
%>
<div class="container-fluid">
	<h3>Edit option</h3>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="modulos?id=<%=menuId%>"><i class="fas fa-arrow-left"></i></a>
	</div>
<% 	if(mensaje.equals("1")){%>
	<div class="alert alert-success">
		Saved
	</div>
<%  }%>
	<form name="frmOpcion" action="grabarOpcion" method="post">
		<input type="hidden" name="id" value="<%=menuId%>">
		<input type="hidden" name="ModuloId" value="<%=moduloOpcion.getModuloId()%>">
		<input type="hidden" name="OpcionId" value="<%=moduloOpcion.getOpcionId()%>">
		<label for="Español">Spanish:</label>
		<input type="text" name="Espanol" class="form-control" value="<%=moduloOpcion.getNombreOpcion()%>">		        
		<br>
		<label for="Ingles">English:</label>
		<input type="text" name="Ingles" class="form-control" value="<%=moduloOpcion.getNombreIngles()%>">
		<br>
		<label for="Comentario">Comment:</label>
		<textarea id="Usuarios" name="Usuarios" class="form-control" rows="2" cols="50"><%=moduloOpcion.getUsuarios()%></textarea>
		<br>		
		<label for="Ingles">Status:</label>
		<select name="Icono" class="form-select" style="width:140px;">
			<option value="A" <%=moduloOpcion.getIcono().equals("A")?"selected":""%>>Active</option>
			<option value="I" <%=moduloOpcion.getIcono().equals("I")?"selected":""%>>Inactive</option>
			<option value="R" <%=moduloOpcion.getIcono().equals("R")?"selected":""%>>In review</option>
		</select>
		<br>		
		<div class="alert alert-info">
			<button type="submit" class="btn btn-primary">Save</button>
		</div>		
	</form>
</div>