<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%
	String codigoPersonal	= request.getAttribute("codigoPersonal")==null?"-":(String)request.getAttribute("codigoPersonal");
%>
<head>
<script type="text/javascript" src="../js/prototype-1.6.js"></script>
<script type="text/javascript">
	function guardar(){
		if(document.formaEnviar.archivo.value != ""){
			document.formaEnviar.btnGuardar.value = "Guardando...";
			document.formaEnviar.submit();
		}else
			alert("Selecciona el archivo a subir");
	}
</script>
</head>
<body>
<div class="container-fluid">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="nuevo?CodigoPersonal=<%=codigoPersonal%>">Regresar</a>
	</div>
	<div>
		<div class="row">
			<div class="span3">
				<h2>Elije la foto</h2>	
				<form name="formaEnviar" id="formaEnviar" enctype="multipart/form-data" action="guardaFoto" method="post">
					<input name="CodigoPersonal" type="hidden" value="<%=codigoPersonal%>">
					<input type="file" id="archivo" name="archivo" /><br>
					<div class="alert alert-info">
<!-- 						<a id="btnGuardar" class="btn btn-primary" onclick="guardar();"><i class="icon-ok icon-white"></i> Guardar</a> -->
						<input type="submit" class="btn btn-primary" value="guardar">
					</div>
					
				</form>	
			</div>
		</div>
	</div>
</div>
</body>