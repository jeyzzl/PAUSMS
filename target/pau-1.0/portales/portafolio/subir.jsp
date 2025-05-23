<%@page import="java.io.File"%>

<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="menuPortal.jsp"%>
<STYLE TYPE="text/css">
.tabbox {
	background: #eeeeee;
	border-left: 0pt gray solid;
	border-right: 0pt gray solid;
	border-bottom: 1pt gray solid;
}

#sombra {
	float: center;
	/*padding:0 5px 5px 0; Esta es la profundidad de nuestra sombra, sí haces más grandes estos valores, el efecto de sombra es mayor también */
	/*background: url(../../imagenes/sombra.gif) no-repeat bottom right; Aquí es donde ponemos la imagen como fondo colocando su ubicación*/
	height: 185px;
	position: relative;
}

#sombra img {
	display: block;
	position: relative;
	top: 0px; /* Desfasamos la imagen hacia arriba */
	left: 0px; /*Desfasamos la imagen hacia la izquierda */
	padding: 5px;
	background: #FFFFFF; /*Definimos un color de fondo */
	border: 1px solid;
	border-radius: .4em;
	border-color: #CCCCCC #666666 #666666 #CCCCCC Creamos un marco para
		acentuar el efecto*/
}
</STYLE>
<%
	int cont = 1;
	String origen 			= (String)request.getAttribute("origen");
	String folio 			= (String)request.getAttribute("folio");
	String nombreArchivo 	= (String)request.getAttribute("nombreArchivo");
	boolean tieneArchivo	= (boolean)request.getAttribute("tieneArchivo");
	String paramOrigen  	= "";
	
	if(!origen.equals("")){
		paramOrigen = "&origen="+origen;
	}
		String codigoEmpleado = (String)request.getAttribute("codigoPersonal");


		String documentoId = (String)request.getAttribute("documentoId");
		String periodoId = (String)request.getAttribute("periodoId");
	
			
%>
<link rel="stylesheet" href="../bootstrap-fileupload.min.css" />
<div class="container-fluid">
	<h2>Subir <%=folio.contains("4")?"Archivo":"Imagen"%></h2>
	<hr />
	<% if(origen.equals("")){ %>
		<a class="btn btn-primary" href="documentos"><i class="fas fa-arrow-left"></i> <spring:message code='aca.Regresar' /></a> <br> <br>
	<% }else{ %>
		<a class="btn btn-primary" href="<%= origen %>"><i class="fas fa-arrow-left"></i> <spring:message code='aca.Regresar' /></a> <br> <br>
	<% } %>		
	<form id="formImagen" enctype="multipart/form-data" action="guardarArchivo?DocumentoId=<%=documentoId%>&PeriodoId=<%=periodoId%>&Folio=<%=folio%>&origen=<%=origen%>" method="post">
		<div class="fileupload fileupload-new" data-provides="fileupload">
<% 			if(!tieneArchivo){%>
			<div class="fileupload-new thumbnail" style="width: 100px; height: 100px;">
				<img src="100.gif" />
			</div>
<% 			}%>
			<div class="fileupload-preview fileupload-exists thumbnail" style="width: 100px; height: 100px;"></div>
			<span class="btn btn-file">
				<span class="fileupload-new">Selecciona documento</span>
				<span class="fileupload-exists">Cambiar</span>
				<input type="file" id="archivo" name="archivo"/>
			</span> 
			<a href="#"	class="btn fileupload-exists" data-dismiss="fileupload">Quitar</a>
<% 			if(!origen.equals("")){ %>
				<input type="hidden" name="origen" value="<%= origen %>">
<% 			} %>
		</div>
		<hr />
		<div class="alert alert-info" style="margin-top: 20px;">
			<a href="javaScript:validaFoto();" class="btn btn-primary btn-large"><i class="icon-arrow-up icon-white"></i> Subir</a>
			<a href="documentos" class="btn btn-large"><i class="fas fa-trash-alt"></i> Cancelar</a>
		</div>
	</form>
<%	if(tieneArchivo){	
		if (!folio.equals("4")) {%>
			<img src="imagenArchivo?DocumentoId=<%=documentoId%>&Folio=<%=folio%>" alt="Imagen corrompida" width="150px"/>
		<a class="btn btn-primary" href="javascript:borrarFoto('<%=documentoId%>','<%=origen%>','<%=folio%>')" title="Borrar la Foto"><i class="fas fa-trash-alt icon-white"></i> Borrar</a>
<%		} else if (nombreArchivo.contains("pdf")){	%>
			<a class="btn btn-info" href="mostrarPdf?DocumentoId=<%=documentoId%>&Folio=<%=folio%>" target="_blank">Mostrar PDF</a>
			<a class="btn btn-primary" href="javascript:borrarArchivo('<%=documentoId%>','<%=origen%>','<%=folio%>')" title="Borrar el Archivo"><i class="fas fa-trash-alt icon-white"></i> Borrar</a>
<% 		}else{%>
			<a class="btn btn-info" href="descargaArchivo?DocumentoId=<%=documentoId%>&Folio=<%=folio%>">Descargar Archivo</a>
			<a class="btn btn-primary" href="javascript:borrarArchivo('<%=documentoId%>','<%=origen%>','<%=folio%>')" title="Borrar el Archivo"><i class="fas fa-trash-alt icon-white"></i> Borrar</a>
<% 		}%>
<% 	}%>
</div>
<script src="../bootstrap-fileupload.min.js"></script>
<script>
	jQuery('.documentos').addClass('active');

	function borrarFoto(documentoId,origen,folio) {
		if (confirm("¿Estas seguro de borrar la imagen?")) {
			location.href = "borrarArchivo?DocumentoId="+documentoId+"&origen="+origen+"&Folio="+folio;
		}
	}
	function borrarArchivo(documentoId,origen,folio) {
		if (confirm("¿Estas seguro de borrar el archivo?")) {
			location.href = "borrarArchivo?DocumentoId="+documentoId+"&origen="+origen+"&Folio="+folio;
		}
	}

	function validaFoto() {
		if(!document.getElementById("archivo").value){
 			alert("Selecciona un documento para subir");
 		}else{
 			document.getElementById("formImagen").submit(); 
 	 	}
	}
</script>
<%@ include file="../../cierra_enoc.jsp"%>