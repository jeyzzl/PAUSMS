<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	String codigoPersonal 	= (String)	session.getAttribute("codigoPersonal");
	String codigoEmpleado	= (String)	request.getAttribute("codigoEmpleado");
	String empleadoNombre 	= (String)	request.getAttribute("empleadoNombre");
%>
<head>
<script type="text/javascript" src="../js/prototype-1.6.js"></script>
<script type="text/javascript">
	function guardar(){
		if(document.formaEnviar.archivo.value != ""){
			document.formaEnviar.btnGuardar.disabled = true;
			document.formaEnviar.btnGuardar.value = "Saving...";
			document.formaEnviar.submit();
		}else
			alert("Select a file to upload");
	}
</script>
</head>
<body>
<div class="container-fluid">
	<h2>Upload Picture <small class="text-muted fs-4">( Employee: <%=codigoEmpleado%> - <%=empleadoNombre%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="fotocredencial">Return</a>
	</div>
	<form name="formaEnviar" id="formaEnviar" enctype="multipart/form-data" action="guardar" method="post">
	<input type="hidden" id="codigoEmpleado" name="codigoEmpleado" value="<%=codigoEmpleado%>">
	<div class="alert">				
			<b>Select picture</b>&nbsp;&nbsp;
			<br>
			<input type="file" id="archivo" name="archivo" />
			<br><br>
			<input type="button" id="btnGuardar" value="Save" onclick="guardar();" class="btn btn-primary"/>
		</tr>			
	</div>
	</form>
</div>
</body>