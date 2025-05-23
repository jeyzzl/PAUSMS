<%@ page import= "java.util.ArrayList"%>
<%@ page import= "java.util.HashMap"%>
<%@page import="aca.tit.spring.TitFirma"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
	String codigoPersonal		= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
	String tramite				= request.getParameter("Tramite")==null?"0":request.getParameter("Tramite");
	String institucion  		= request.getParameter("Institucion")==null?"UM":request.getParameter("Institucion");
%>
<div class="container-fluid">
	<h2>Sellado de títulos electrónicos<small class="text-muted fs-4"> ( <%=codigoPersonal%> )</small></h2>
	<div class="alert alert-info">		
		<a class="btn btn-primary" href="firmar?Tramite=<%=tramite%>"><i class="fas fa-arrow-left"></i></a>&nbsp;
	</div>
	<form name="frmFirmar" id="frmFirmar" enctype="multipart/form-data" action="grabarSello" method="post">
	<input type="hidden" name="Institucion" value="<%=institucion%>"/>
	<input type="hidden" name="CodigoPersonal" value="<%=codigoPersonal%>" />
	<input type="hidden" name="Tramite" value="<%=tramite%>" />	
	<div class="row">
		<div class="span5">
		<label for="Clave"><b>Clave de acceso:</b></label>
		<input type="password" name="Clave" class="form-control"/>
		<br>
		<label for="Llave"><b>Llave privada (archivo .key):</b></label>	
			<div class="fileupload fileupload-new" data-provides="fileupload">
<!-- 			  <div class="fileupload-new thumbnail" style="width: 100px; height: 100px;"><img src="http://www.placehold.it/100x100/EFEFEF/AAAAAA" /></div> -->
			  <div class="fileupload-preview fileupload-exists thumbnail" style="width: 100px; height: 100px;"></div>
			  			  
				<span class="btn  btn-default btn-file">
			    	<span class="fileupload-new">llave privada</span>
			    	<span class="fileupload-exists">Cambiar</span>
			    	<input type="file" id="llave" name="llave" />
				</span>					
				<span class="fileupload-exists pul-right">
					<a href="#" class="btn fileupload-exists" data-dismiss="fileupload"> <button class="btn btn-primary btn-large" >Quitar</button></a>
				</span>					
				<span class="fileupload-exists" >
					<button class="btn btn-primary btn-large" id="btnGuardar" value="Enviar" onclick="grabarSello();" class="enviar" ><i class="icon-arrow-up icon-white"></i> Enviar</button>
				</span>
			</div>
		</div>
	</div>		
	</form>		
</div>

<link rel="stylesheet" href="../../js/bootstrap-fileupload/bootstrap-fileupload.min.css" />
<script src="../../js/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>