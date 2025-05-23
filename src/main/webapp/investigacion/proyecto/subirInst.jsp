<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
// 	String accion 	  = request.getParameter("Accion")==null?"":request.getParameter("Accion");
	String proyectoId = request.getParameter("proyectoId")==null?"":request.getParameter("proyectoId");
%>

<script>
	function guardar(){
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
	<h2>Subir Documento<small class="text-muted fs-4">( Proyecto: <%= proyectoId %> )</small></h2>
	<div class="alert alert-info">
		<a href="permisos?Accion=3&proyectoId=<%=proyectoId%>" class="btn btn-primary"><i class="glyphicon glyphicon-chevron-left"></i> <spring:message code='aca.Regresar'/></a>
	</div>
	<div class="panel panel-info">
		<div class="panel-heading"><h3>Instrumento</h3></div>
  		<div class="panel-body">
			<form name="formaEnviar" id="formaEnviar" enctype="multipart/form-data" action="saveFile?proyectoId=<%=proyectoId%>&folio=1" method="post">
					<label class="control-label"><strong>¡Elige tu documento!</strong></label>
					<div class="fileupload fileupload-new" data-provides="fileupload">
					  <div class="fileupload-new thumbnail" style="width: 400px; height: 30px;"></div>
					  <div class="fileupload-preview fileupload-exists thumbnail" style="width: 400px; height: 30px;"></div>
					  <span class="btn btn-file btn-primary"><span class="fileupload-new"><i class="glyphicon glyphicon-open"></i> Subir</span><span class="fileupload-exists">Cambiar <i class="glyphicon glyphicon-random"></i></span><input type="file" id="archivo" name="archivo" /></span>
					  <a href="#" class="btn btn-danger fileupload-exists" data-dismiss="fileupload">Quitar <i class="glyphicon glyphicon-trash"></i></a>
					</div>
					<button type="button" id="btnGuardar" class="btn btn-success" onclick="guardar();"><i class="glyphicon glyphicon-save"></i> Guardar</button>
		
			</form>	
		</div>
	</div>
</div>