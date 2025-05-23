<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
	String proyectoId = request.getParameter("ProyectoId");	
%>

<script>
	function guardarInfo1(){
		if(document.formaEnviar.archivo.value != ""){
			document.formaEnviar.btnGuardar.disabled = true;
			document.formaEnviar.btnGuardar.value = "Guardando...";
			document.formaEnviar.submit();
		}else
			alert("¡Selecciona un documento!");
	}
</script>

<link rel="stylesheet" href="../../js-plugins/bootstrap-fileupload/bootstrap-fileupload.min.css" />
<script src="../../js-plugins/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>

<div class="container-fluid">
	
	<h2>Subir Informe 1</h2>
		
	<div class="alert alert-info">
		<a href="proyecto" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
	</div>

	<form name="formaEnviar" id="formaEnviar" enctype="multipart/form-data" action="guardarInfo1?ProyectoId=<%=proyectoId%>" method="post">
		
		<div class="alert alert-info">
			<h3>Informe de actividad 1</h3>
			<div class="fileupload fileupload-new" data-provides="fileupload">
			  <div class="fileupload-new thumbnail" style="width: 150px; height: 50px;"><img src="http://www.placehold.it/150x50/EFEFEF/AAAAAA" /></div>
			  <div class="fileupload-preview fileupload-exists thumbnail" style="width: 150px; height: 50px;"></div>
			  <span class="btn btn-file"><span class="fileupload-new" type="file">¡Elige el documento!</span><span class="fileupload-exists">Cambiar</span><input type="file" id="archivo" name="archivo" /></span>
			  <a href="#" class="btn fileupload-exists" data-dismiss="fileupload">Quitar</a>
			</div>			
		</div>
		
		<div class="alert alert-info">
			<button type="button" id="btnGuardar" class="btn btn-primary btn-large" onclick="guardarInfo1();"><i class="icon-ok icon-white"></i> Guardar</button>
		</div>

	</form>	
</div>