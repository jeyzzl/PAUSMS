<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import= "java.util.Base64"%>

<link rel="stylesheet" href="../../js/bootstrap-fileupload/bootstrap-fileupload.min.css" />
<script src="../../js/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>
<script type="text/javascript">
	function borrarImagen(id){
		if(confirm("¿Desea eliminar la imagen?")){
			document.location.href = "borrarImagen?id="+id;
		}
	}	
</script>
<%	
	boolean tieneImagen	 		= (boolean) request.getAttribute("tieneImagen");
	byte imagenByte[]			= (byte[])request.getAttribute("imagenByte");
	String imagenStr 			= Base64.getEncoder().encodeToString(imagenByte);
	String id					= request.getParameter("id")==null?"0":request.getParameter("id");
%>
<body marginwidth="0" marginheight="0" leftmargin="0" topmargin="0">
<div class="container-fluid">
	<h2></h2>
	<form name="frmDatos" id="frmDatos" action="nuevo" method="post">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="lista"><i class="fas fa-arrow-left"></i></a>		
	</div>
	</form>
	<form name="frmImagen" id="frmImagen" enctype="multipart/form-data" action="guardarimagen" method="post">
	<div class="row">
		<div class="span5">		
			<img id="img" width="90%" src="data:image/jpg;base64,<%=imagenStr%>" />
			<br><br>	
					
			<div class="wel">	
			<a href="javascript:borrarImagen('<%=id%>')" class="btn btn-danger btn-sm"><i class="fas fa-trash-alt"></i></a>&nbsp;	
			</div>			
		</div>							
	</div>
	</form>	
</div>
</body>