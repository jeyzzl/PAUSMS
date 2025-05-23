<%@page import="java.util.List"%>
<%@page import="aca.vigilancia.spring.VigDocumento"%>
<%@page import="aca.vigilancia.spring.VigDocAuto"%>
<%@page import="aca.vigilancia.spring.VigAuto"%>

<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	String autoId	 			= (String) request.getAttribute("autoId");	
	String documentoId 			= (String) request.getAttribute("documentoId");	
	String documentoNombre		= (String) request.getAttribute("documentoNombre");
	String hoja		 			= (String) request.getAttribute("hoja");
	String mensaje 				= (String) request.getAttribute("mensaje");
	boolean existeDocumento		= (boolean) request.getAttribute("existeDocumento");
	boolean existeImagen		= (boolean) request.getAttribute("existeImagen");
	
	VigAuto vigAuto			 	= (VigAuto) request.getAttribute("vigAuto");
	VigDocAuto vigDocAuto	 	= (VigDocAuto) request.getAttribute("vigDocAuto");	
	VigDocumento vigDocumento 	= (VigDocumento) request.getAttribute("vigDocumento");
%>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<div class="container-fluid">
	<h2>Documentos <small class="text-muted fs-6">(<b>Auto:</b> <%=vigAuto.getMarca()%> - <%=vigAuto.getModelo()%> - <%=vigAuto.getColor()%> <b>Documento:</b> <%=documentoNombre%> <b>Hoja:</b> <%=hoja%> )</small></h2>
	
	<form name="form" class="form-control" enctype="multipart/form-data" action="grabarDocumento?DocId=<%=vigDocumento.getDocumentoId()%>&AutoId=<%=vigDocumento.getAutoId()%>" method="post">
	<input type="hidden" name="AutoId" value="<%=autoId%>">
	<input type="hidden" name="DocId" value="<%=documentoId%>">
	<input type="hidden" name="Hoja" value="<%=hoja%>">
	
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="listado"><i class="fas fa-arrow-left"></i></a>&nbsp;
		Vigencia: <input id="Vigencia" name="Vigencia" type="text" value="<%=vigDocAuto.getVigencia()%>" class="form-control" data-date-format="dd/mm/yyyy" style="width:120px">&nbsp; &nbsp;
		Comentario: <input id="Comentario" name="Comentario" type="text" value="<%=vigDocAuto.getComentario()%>" class="form-control" style="width:300px">
	</div>
<% 	if(mensaje.equals("1")){%>
	<div class="alert alert-success d-flex align-items-center">
		Grabado
	</div>
<% 	}else if(mensaje.equals("2")){%>
	<div class="alert alert-danger d-flex align-items-center">
		No pudo grabar
	</div>
<% 	}else if(mensaje.equals("3")){%>
	<div class="alert alert-success d-flex align-items-center">
		Borrado
	</div>
<%	}else if(mensaje.equals("4")){%>
	<div class="alert alert-danger d-flex align-items-center">
		No pudo borrar
	</div>
<% 	}	%>	
<%	
	String nombreFile = vigDocumento.getNombre().toUpperCase();	
	if (nombreFile.contains(".JPG") || nombreFile.contains(".JPEG")|| nombreFile.contains(".PNG") || nombreFile.contains(".BMP") || nombreFile.contains(".GIF")){ %>
		<div id="sombra"><img src="imagen?AutoId=<%=autoId%>&DocId=<%=documentoId%>&Hoja=<%=hoja%>" width="250" border="1"></div>
<%	}else if(existeImagen){ %>
		<div id="sombra"><a href="bajar?AutoId=<%=autoId%>&DocId=<%=documentoId%>&Hoja=<%=hoja%>"><i class="fas fa-file-download fa-2x"> <%=vigDocumento.getNombre()%></i></a></div>		
<%	}else{ %>
		<div id="sombra"><i class="far fa-file fa-2x"></i> <span class="fs-5">Elige el documento que deseas registrar.</span></div>
<%	}%>
 		<div class="mb-3 row">
		    <div class="col-sm-3">
		    	<label for="staticEmail" class="col-sm-2 col-form-label">Archivo</label>
		      	<input type="file" id="archivo" name="archivo"/>
		    </div>
	  	</div>
 		<div class="mb-3 row">
		    <div class="col-sm-2">
		      	<button class="btn btn-primary" type="submit">Grabar</button>&nbsp;
<%		if(existeImagen){ %>		      	
		      	<a href="javascript:BorrarDocumento('<%=autoId%>','<%=documentoId%>','<%=hoja%>');" class="btn btn-danger" title="<spring:message code='aca.Eliminar'/>">Borrar</a>
<%		} %>		      		
		    </div>
	  	</div>	  	
	</form>
</div>
<script>
	jQuery('#Vigencia').datepicker();
	function BorrarDocumento(autoId,documentoId,hoja){
		if (confirm("¿Estás seguro de borrar el documento?")){
			document.location.href="borrarDocumento?AutoId="+autoId+"&DocId="+documentoId+"&Hoja="+hoja;
		}
	}
</script>