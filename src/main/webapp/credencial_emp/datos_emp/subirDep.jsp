<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%	
	String codigoEmpleado 	= (String) session.getAttribute("codigoEmpleado");
	String empleadoId 		= (String) session.getAttribute("empleadoId");
	String dependienteId 	= (String) session.getAttribute("dependienteId");
	String nombreDep 		= (String) request.getAttribute("nombreDep");
%>
<head>
<script type="text/javascript" src="../js/prototype-1.6.js"></script>
<script type="text/javascript">
	function guardar(){
		if(document.formaEnviar.archivo.value != ""){
			document.formaEnviar.btnGuardar.disabled = true;
			document.formaEnviar.btnGuardar.value = "Guardando...";
			document.formaEnviar.submit();
		}else
			alert("Selecciona el archivo a subir");
	}
</script>
</head>
<body>
<div class="container-fluid">
	<h2>Subir Foto de Dependiente <small class="text-muted fs-4"> ( <%=codigoEmpleado + "-" + dependienteId%>] - <%=nombreDep%>)</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="dato_dep?f_empleado=<%=empleadoId%>&f_folio=<%=dependienteId%>"><spring:message code="aca.Regresar" /></a>
	</div>	
	<form name="formaEnviar" id="formaEnviar" enctype="multipart/form-data" action="guardarDep" method="post">
	<table class="table table-border">
	<tr>
		<td><h3>Elije la foto</h3></td>
	</tr>
	<tr>
		<td><input type="file" id="archivo" name="archivo" /></td>
	</tr>
	<tr>
		<td><input type="button" class="btn btn-primary" id="btnGuardar" value="Guardar" onclick="guardar();" /></td>
	</tr>
	</table>
	</form>
</div>	
</body>