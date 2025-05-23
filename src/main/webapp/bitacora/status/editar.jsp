<%@ page import="aca.bitacora.spring.BitEstado"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	
	String estadoId 	= request.getParameter("EstadoId")==null?"0":request.getParameter("EstadoId");
	BitEstado estado	= (BitEstado)request.getAttribute("estado"); 
%>

<div class="container-fluid">
	<h2>Modificar Estado</h2>
	<div class="alert alert-info">
		<a href="estado" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
	</div>
	<form name="formEstado" action="grabar" method="post" >
		<div class="row container-fluid">
			<div class="span3">
				<input type="hidden" name=EstadoId value="<%=estadoId%>">
				<label for="nombre">Nombre</label>
				<input type="text" class="text form-control" style="width:350px" name="Nombre" size="50" maxlength="50" value="<%=estado.getEstadoNombre()%>">
			</div>
		</div>
		<br>
	<div class="alert alert-info">
		<input class="btn btn-primary" type="submit" value="Grabar">
	</div>
	</form>
</div>
