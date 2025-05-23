<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.rol.spring.Rol"%>
<%@ page import= "aca.rol.spring.RolOpcion"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	String mensaje 		= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
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
	<h2>Upload Student or Professor Photo</h2>
	<hr>
<%	if (!mensaje.equals("-")){%>	
	<div class="alert alert-info"><%=mensaje%></div>
<%	} %>	
	<div class="row">
		<div class="col-5">		
			<form name="frmImagen" id="frmImagen" enctype="multipart/form-data" action="grabar" method="post">
			<select name="Tipo" class="form-select">
				<option value="E">Employee</option>
				<option value="A">Student</option>
				<option value="C">Small Photo</option>
			</select>
			<br>
			<label class="form-label">Key:</label>	
			<input name="Codigo" type="text" class="form-select" size="10px"/>
			<br>			
			<label class="form-label">Image:</label>			    
			<input type="file" id="imagen" name="imagen"/>
			<br>
			<a href="javascript:Grabar()" class="btn btn-primary">Save</a>						
			</form>
		</div>		
	</div>
</div>		
</body>
</html>
<script>
	function Grabar(){
		document.frmImagen.submit();
	}
</script>