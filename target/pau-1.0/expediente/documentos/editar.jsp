<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.exp.spring.ExpDocumento"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Borrar(codigo, idDocumento){
		if(confirm("Are you sure you want to delete this record?")){
			document.location="borrar?Codigo="+codigo+"&IdDocumento="+idDocumento;
		}
	}
</script>

<%
	ExpDocumento documento	= (ExpDocumento) request.getAttribute("documento");
	String mensaje			= (String) request.getAttribute("mensaje");

	int i = 0;
%>
<div class="container-fluid">
	<h2>Document Catalog</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="listado"><i class="fas fa-arrow-left"></i></a> 
	</div>
<% if(mensaje.equals("1")){%>
	<div class="alert alert-success">
		Saved 
	</div>
<% }else if(mensaje.equals("2")){%>
	<div class="alert alert-danger">
		Error Saving
	</div>
<% }%>
	<form name="form" action="grabar" method="post">
		<input type="hidden" name="Id" value="<%=documento.getDocumentoId()%>">
		<div class="row">
			<div class="col-3">
				<label class="form-label"><b>Name</b></label>
				<input type="text" name="Nombre" value="<%=documento.getDocumentoNombre()%>" class="form-control">
				<br>
				<label class="form-label"><b>Short Name</b></label>
				<input type="text" name="Corto" value="<%=documento.getCorto()%>" class="form-control">
				<br>			
				<label class="form-label"><b>Order</b></label>
				<input type="text" name="Orden" value="<%=documento.getOrden()%>" class="form-control"><br>
			</div>
		</div>
		<button type="submit" class="btn btn-primary">Save</button>
	</form>
</div>
