<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%@page import="aca.saum.spring.SaumReceta"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.emp.spring.EmpDocumento"%>
<%@page import="aca.emp.spring.EmpDocEmp"%>
<%@ page import= "java.util.Base64"%>

<link rel="stylesheet" href="../../js/bootstrap-fileupload/bootstrap-fileupload.min.css" />
<script src="../../js/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>
<script type="text/javascript">
	function guardarImagen(){		
		document.frmImagen.submit();
	}

	function borrarImagen(documentoId,hoja){
		if(confirm("¿Desea eliminar el documento?")){
			document.location.href = "borrarimagen?documentoId="+documentoId+"&hoja="+hoja;
		}
	}	
</script>
<%	
	SaumReceta receta 			= (SaumReceta)request.getAttribute("receta");
	String id 					= request.getParameter("Id")==null?"0":request.getParameter("Id");
	
// 	boolean tieneImagen	 		= (boolean) request.getAttribute("tieneImagen");
 // byte imagenByte[]			= (byte[])request.getAttribute("imagenByte");
//	String imagenStr 			= Base64.getEncoder().encodeToString(imagenByte);
%>
<div class="container-fluid">
	<h2>Subir Imagen<small class="text-muted fs-4"> ( <%=receta.getNombre()%> ) </small></h2>
	<div class="alert alert-info"><a class="btn btn-primary" href="editar?Accion=1&RecetaId=<%=id%>"><i class="fas fa-arrow-left"></i></a></div>
	<form name="frmImagen" id="frmImagen" enctype="multipart/form-data" action="guardarImagen" method="post">
	<input type="hidden" name="Id" value="<%=id%>" />	
		<div class="col-5">		
<%-- 			<img id="img" width="90%" src="data:image/jpg;base64,<%=0%>" /> --%>			
			<br><br>
			<label for="FechaFin">Imagen:</label>						
			<div class="fileupload fileupload-new" data-provides="fileupload">
			  <div class="fileupload-new thumbnail" style="width: 100px; height: 100px;"><img src="http://www.placehold.it/100x100/EFEFEF/AAAAAA" /></div>
			  <div class="fileupload-preview fileupload-exists thumbnail" style="width: 100px; height: 100px;"></div>
			  			  
				<span class="btn  btn-default btn-file">
			    	<span class="fileupload-new">Selecciona Imagen</span>
			    	<span class="fileupload-exists">Cambiar </span>
			    	<input type="file" id="imagen" name="imagen" />
				</span>					
				<span class="fileupload-exists pul-right">
					<a href="#" class="btn fileupload-exists" data-dismiss="fileupload"> <button class="btn btn-primary btn-large" >Quitar</button></a>
				</span>					
				<span class="fileupload-exists" >
					<a class="btn btn-primary btn-large" id="btnGuardar" value="Enviar" onclick="guardarImagen();" class="enviar" ><i class="icon-arrow-up icon-white"></i> Enviar</a>
				</span>
			</div>							
			<br><br>
		</div>
	</form>	
	</div>
