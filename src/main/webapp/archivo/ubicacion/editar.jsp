<%@ page import="aca.archivo.spring.ArchUbicacion"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	ArchUbicacion ubicacion = (ArchUbicacion) request.getAttribute("ubicacion");
%>
<div class="container-fluid">
	<h2>Location</h2>
	<div class="alert alert-info d-flex align-items-center">
		<a href="lista" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
	</div>
	<form name="formUbicacion" method="post" action="grabar">
	<div class="row container-fluid">
		<div class="span3">
			<input type="hidden" name="UbicacionId" value="<%=ubicacion.getUbicacionId()%>">
			<label for="ubicacion"><b>Location:</b></label>
			<input type="text" class="form-control" style="width:350px" name="UbicacionNombre" size="40" maxlength="40" value="<%=ubicacion.getUbicacionNombre()%>"> 
		</div>
	</div>
	<br>
	<div class="alert alert-info d-flex align-items-center">
		<input class="btn btn-primary" type="submit" value="Save">
	</div>
	</form>
</div>