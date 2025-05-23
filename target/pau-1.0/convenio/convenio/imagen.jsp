<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.convenio.spring.ConConvenio"%>
<%@page import="aca.convenio.spring.ConArchivo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	 
	String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String idConvenio		= request.getParameter("Id")==null?"0":request.getParameter("Id");
	String mensaje	 		= (String)request.getAttribute("mensaje");

	ConConvenio convenio 	= (ConConvenio)request.getAttribute("convenio");
	ConArchivo conArchivo 	= (ConArchivo)request.getAttribute("conArchivo");
	boolean tieneArchivo 	= conArchivo.getArchivo() == null ?false:true;
%>
<script type="text/javascript">
	function guardarImagen(){
		if($("archivo").value != ""){	
			document.frmArchivo.submit();
		}else{
			alert("Selecciona el archivo a subir");
		}
	}
	
	function borrarImagen(convenioId,folio){
		if(confirm("¿Desea eliminar el documento?")){
			document.location.href = "borrarImagen?IdConvenio="+convenioId+"&Folio="+folio;
		}
	}
</script>
<body>
<div class="container">
	<div class="page-header">
  		<h2>Cargar Archivo <small class="text-muted fs-4"> ( <%=folio%> )</small></h2>
	  	<div class="alert alert-info">
	  		<a class="btn btn-primary" href="listado">Regresar</a>
	  	</div>
	</div>
<% 	if(!mensaje.equals("0")){%>
	<div class="alert alert-success">
		Archivo guardado.
	</div>
<% 	}%>
	<div class="alert alert-info">
		<div class="row">
			<div class="col-md-12">
				<h2 align="left">
					Archivo actual<small class="text-muted fs-4"><%=tieneArchivo?" ("+conArchivo.getNombre()+")":""%></small>
<% 			if(tieneArchivo){%>							
				&nbsp;<a class="btn btn-success" href="descargaImagen?IdConvenio=<%=idConvenio%>&Folio=<%=folio%>"><i class="fas fa-download"></i></a>
				&nbsp;<a class="btn btn-danger" id="btnEliminar" onclick="borrarImagen('<%=idConvenio%>','<%=folio%>');" class="enviar" ><i class="fas fa-trash-alt"></i></a>		
<% 			}%>
				</h2>							
				<form name="frmArchivo" id="frmArchivo" enctype="multipart/form-data" action="guardarImagen" method="post">	
			    	<input type="hidden" name="Folio" value="<%=folio%>"/>
			    	<input type="hidden" name="IdConvenio" value="<%=idConvenio%>"/>
					<span class="btn  btn-default btn-file">
				    	<input type="file" id="archivo" name="archivo" />
					</span><br><br>
					<a class="btn btn-primary" href="imagen?Id=<%=idConvenio%>&Folio=<%=folio%>">Quitar</a>
					<a class="btn btn-primary" id="btnGuardar" onclick="guardarImagen();" class="enviar" >Guardar</a>					
				</form>
			</div>			
		</div>
	</div>
</div>	
</body>