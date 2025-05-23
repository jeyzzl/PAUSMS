<%@ include file="../../conectaadm.jsp"%>
<%@ include file="../../conectadbp.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<head>
<%
	String folio			= (String) session.getAttribute("Folio"); 
	String documentoId 		= request.getParameter("documentoId")==null?"0":request.getParameter("documentoId");	
	boolean existeArchivo	= (boolean)request.getAttribute("existeArchivo");
	String estado 			= (String)request.getAttribute("estado");
	String nombreArchivo	= (String)request.getAttribute("nombreArchivo");
	String iconoArchivo		= "X";
	if (nombreArchivo.contains(".doc")||nombreArchivo.contains(".docx")) 
		iconoArchivo = "<img src='../imagenes/word.png' style='height:30px'> "+nombreArchivo;
	else if (nombreArchivo.contains(".xls")||nombreArchivo.contains(".xlsx")) 
			iconoArchivo = "<img src='../imagenes/word.png' style='height:30px'> "+nombreArchivo;
	else if (nombreArchivo.contains(".ppt")||nombreArchivo.contains(".pptx")) 
				iconoArchivo = "<img src='../imagenes/word.png' style='height:30px'> "+nombreArchivo;		
	else if (nombreArchivo.contains(".pdf")) 
		iconoArchivo = "<img src='../imagenes/word.png' style='height:30px'> "+nombreArchivo;			
	else
		iconoArchivo = "<span class='glyphicon glyphicon-folder-open' > "+nombreArchivo+"</span>";
%>
<script type="text/javascript">
	function guardarArchivo(){
		if($("archivo").value != ""){	
			document.frmImagen.submit();
		}else
			alert("Selecciona el archivo a subir");
	}
	
	function borrarArchivo(documentoId){
		if(confirm("¿Desea eliminar el documento?")){
			document.location.href = "borrararchivo?documentoId="+documentoId;
		}
	}
</script>


</head>
<body style="height: 97%;background:url(../imagenes/um3.png) no-repeat bottom right fixed;background-color:#F7EBEB;">

<div class="container">		
	<nav class="navbar-right">
	  <ul class="pager">
			<li><a href="documentos"><span class="glyphicon glyphicon-menu-left" ></span>Regresar</a></li>
	  </ul>
	</nav>	
	<div class="page-header">
	  <h2><%=adm.documento.AdmDocumento.getNombreDocumento(conAdm,documentoId) %></h2>
	</div>
	<div class="alert alert-info">
		<div class="row">
			<div class="col-md-4">
			<%if (existeArchivo && !estado.equals("A")){%>
				<h4>
				<%=iconoArchivo%>	
				<a href="javascript:borrarArchivo('<%=documentoId%>')" class="btn btn-danger btn-sm"><i class="fas fa-trash-alt"></i></a>
				</h4>				
			<%}%>	
			</div>
			<div class="col-md-4">
			<%if (!estado.equals("A")){%> 
				<h2 align="center" colspan="2"><b><font size="5">Subir archivo:</font></b></h2>
				<br>								
				<!-- ----------------------IMAGENES-------------------- -->
				<form name="frmImagen" id="frmImagen" enctype="multipart/form-data" action="guardararchivo?documentoId=<%=documentoId%>" method="post">				
				<div class="fileupload fileupload-new" data-provides="fileupload">
				  <div class="fileupload-new thumbnail" style="width: 100px; height: 100px;"><img src="http://www.placehold.it/100x100/EFEFEF/AAAAAA" /></div>
				  <div class="fileupload-preview fileupload-exists thumbnail" style="width: 100px; height: 100px;"></div>
				  
					<span class="btn  btn-default btn-file">
				    	<span class="fileupload-new">Selecciona un archivo</span>
				    	<span class="fileupload-exists">Cambiar </span>
				    	<input type="file" id="archivo" name="archivo" />
					</span>					
					<span class="fileupload-exists pul-right">
						<a href="#" class="btn fileupload-exists" data-dismiss="fileupload"> <button class="btn btn-primary btn-large" >Quitar</button></a>
					</span>					
					<span class="fileupload-exists" >
						<button class="btn btn-primary btn-large" id="btnGuardar" value="Enviar" onclick="guardarImagen();" class="enviar" ><i class="icon-arrow-up icon-white"></i> Enviar</button>
					</span>
				</div>	
				</form>
			<% } %>	
			</div>			
		</div>
	</div>
</div>	
</body>
<%@ include file="../../cierradbp.jsp"%>
<%@ include file="../../cierraadm.jsp"%>