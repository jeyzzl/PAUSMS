<%@page import="java.util.HashMap"%>
<%@page import= "aca.plan.MapaPlan"%>
<%@page import= "aca.plan.spring.MapaArchivo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<head>
	<link rel="stylesheet" href="../../js-plugins/bootstrap-fileupload/bootstrap-fileupload.min.css" />
	<script src="../../js-plugins/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>
	<style type="text/css">
		.file {
			width: 20%;
		}
	</style>
</head>	

<%
	String planId 	  		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
	String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String titulo			= "-";
	
	boolean tieneArchivo	= (boolean) request.getAttribute("tieneArchivo");
	String facultadId		= (String) request.getAttribute("facultadId");
	MapaArchivo archivo		= (MapaArchivo) request.getAttribute("archivo");
	
	if(folio.equals("1")){
		titulo = "Rvoe Archive";
		
	}
	else{
		titulo = "Plan Contents";
		
	}
%>
<body>
<div class="container-fluid">
	<h2><%=titulo%></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="plan?facultad=<%=facultadId%>"><spring:message code="aca.Regresar"/></a>
	</div>
	<form name="formaEnviar" id="formaEnviar" enctype="multipart/form-data" action="saveFile?PlanId=<%=planId%>&Folio=<%=folio%>" method="post">
		<label class="control-label"><h4><spring:message code="aca.Archivo"/><small class="text-muted fs-6">( <%=archivo.getNombre()%> )</h4></strong></label>
		<div class="fileupload fileupload-new" data-provides="fileupload">
		  <div class="fileupload-new thumbnail file" style="height:30px;"></div>
		  <div class="fileupload-preview fileupload-exists thumbnail" style="height: 30px;"></div>
		  <span class="btn btn-file btn-primary">
		  	<span class="fileupload-new"><i class="glyphicon glyphicon-open"></i><spring:message code="aca.Subir"/></span>
		  	<span class="fileupload-exists"><spring:message code="aca.Cambiar"/> <i class="glyphicon glyphicon-random"></i></span>
		  	<input type="file" id="archivo" name="archivo" />
		  </span>
		  <a href="#" class="btn btn-danger fileupload-exists" data-dismiss="fileupload"><spring:message code="aca.Quitar"/> <i class="glyphicon glyphicon-trash"></i></a>
		</div>
		<button type="button" id="btnGuardar" class="btn btn-success" onclick="guardar();"><i class="glyphicon glyphicon-save"></i> <spring:message code="aca.Guardar"/></button>
	<%	if (tieneArchivo){%>
		<a href="bajarArchivo?PlanId=<%=planId%>&Folio=<%=folio%>" class="btn btn-info" title="Descargar el formato"><i class="glyphicon glyphicon-download-alt"></i> <spring:message code="aca.Descargar"/></a>
		&nbsp;
		<a href="borrarArchivo?PlanId=<%=planId%>&Folio=<%=folio%>" class="btn btn-danger" title="Borrar"><i class="glyphicon glyphicon-trash"></i> <spring:message code="aca.Borrar"/></a>
	<%	} %>	
	</form>		
</div>
<script>
	function guardar(){
		if(document.formaEnviar.archivo.value != ""){
			document.formaEnviar.btnGuardar.disabled = true;
			document.formaEnviar.btnGuardar.value = "<spring:message code='aca.Guardando'/>";			
			document.getElementById("formaEnviar").submit();
		}else
			alert("Select a file!");
	}	
</script>
</body>