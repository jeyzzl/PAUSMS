<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import="aca.bec.spring.BecCategoria"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<html>
<head>
	<script type="text/javascript">	
	
		function Grabar() {
			if (document.frmcategoria.CategoriaId.value != "" && document.frmcategoria.CategoriaNombre != "" && document.frmcategoria.Estado != "" && document.frmcategoria.PDF != "") {				
				document.frmcategoria.submit();
			} else {
				alert("Llene correctamente el formulario");
			}
		}		
	</script>
</head>
<%
	String codigoPersonal			= (String) session.getAttribute("codigoPersonal");
	String mensaje 					= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	BecCategoria becCategoria		= (BecCategoria)request.getAttribute("becCategoria"); 
%>
<style>
	body{
		background:white;
	}
</style>
<body>
<div class="container-fluid">
	<h1>Categorias</h1>
	<div class="alert alert-info">
		<a href="categoria" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
<%	if (!mensaje.equals("-")){%>
	<div class="alert alert-info"><%=mensaje%></div>
<%	} %>	
	<form action="grabar" method="post" name="frmcategoria">			
	<div class="row container-fluid">
		<div class="span2 col">
			<label for="CategoriaId">Id:</label>			
			<input name="CategoriaId" type="text" id="CategoriaId" class="form-control" style="width:200px" size="3" maxlength="3" value="<%=becCategoria.getCategoriaId()%>">
			<br><br>
			<label for="CategoriaNombre"><spring:message code="aca.Nombre"/>:</label>					
			<input name="CategoriaNombre"  type="text" id="CategoriaNombre" class="form-control" style="width:200px" size="50" maxlength="70" value="<%=becCategoria.getCategoriaNombre()%>">
			<br><br>
			
		
			<label for="Estado">Estado:</label>
           	<select name="Estado" id="Estado" class="form-select" style="width:200px" >
				<option value="A" <%if(becCategoria.getEstado().equals("A"))out.print("selected");%>>Activo</option>
				<option value="I" <%if(becCategoria.getEstado().equals("I"))out.print("selected");%>>Inactivo</option>
			</select>
			<br><br>
			</div>
				<div class="span2 col">
			<label for="PDF">PDF:</label>
			<input name="PDF" type="text" id="PDF" class="form-control"style="width:200px" size="50" maxlength="70" value="<%=becCategoria.getPdf() %>">
			<br><br>
			<label for="CategoriaUsuario"><spring:message code="aca.Usuario"/>:</label>
			<input name="CategoriaUsuario" type="text" class="form-control" style="width:200px" id="CategoriaUsuario" size="40" maxlength="40" value="<%=codigoPersonal%>"readonly>		
		</div>
	</div>
	<br>
	<div class="alert alert-info">			
		<a class="btn btn-primary" href="javascript:Grabar()">Guardar</a> &nbsp;			
	</div>
	</form>
</div>
</body>
</html>