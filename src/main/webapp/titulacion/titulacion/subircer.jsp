<%@ page import= "java.util.ArrayList"%>
<%@ page import= "java.util.HashMap"%>
<%@page import="aca.tit.spring.TitFirma"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
	String nombreUsuario 			= (String)request.getAttribute("nombreUsuario");
	String codigoPersonal			= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
	String institucion				= request.getParameter("Institucion")==null?"UM":request.getParameter("Institucion");
	String folio					= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
%>

<div class="container-fluid">
	<h2>Subir certificado<small class="text-muted fs-4"> ( <%=codigoPersonal%> - <%=nombreUsuario%> )</small></h2>
	<div class="alert alert-info">		
		<a class="btn btn-primary" href="firma?Institucion=<%=institucion%>&Folio=<%=folio%>"><i class="fas fa-arrow-left"></i></a>&nbsp;
	</div>
	<form name="frmCertificado" id="frmCertificado" enctype="multipart/form-data" action="grabarCer" method="post">
	<input type="hidden" name="CodigoPersonal" value="<%=codigoPersonal%>" />
	<input type="hidden" name="Institucion" value="<%=institucion%>" />
	<input type="hidden" name="Folio" value="<%=folio%>" />
	<div class="row">
		<div class="span5">
		<label>Imagen:</label>
			<div class="fileupload fileupload-new" data-provides="fileupload" style="border:solid 1pt">
			  <div class="fileupload-new thumbnail" style="width: 100px; height: 100px;"><img src="http://www.placehold.it/100x100/EFEFEF/AAAAAA" /></div>
			  <div class="fileupload-preview fileupload-exists thumbnail" style="width: 100px; height: 100px;"></div>
			  			  
				<span class="btn  btn-default btn-file">
			    	<span class="fileupload-new btn btn-primary">Selecciona el certificado</span>
			    	<span class="fileupload-exists btn btn-primary btn-sm">Cambiar </span>
			    	<input type="file" id="certificado" name="certificado"/>
				</span>					
				<span class="fileupload-exists pul-right">
					<a href="#" class="btn fileupload-exists btn btn-danger btn-sm" data-dismiss="fileupload"> <button class="btn btn-danger btn-sm" >Quitar</button></a>
				</span>					
				<span class="fileupload-exists" >
					<button class="btn btn-dark" id="btnGuardar" value="Enviar" onclick="grabarCer();" class="enviar" ><i class="icon-arrow-up icon-white"></i> Enviar</button>
				</span>
			</div>
		</div>
	</div>
</div>
<link rel="stylesheet" href="../../js/bootstrap-fileupload/bootstrap-fileupload.min.css" />
<script src="../../js/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>