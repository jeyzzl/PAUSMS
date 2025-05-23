<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%
	String matricula 		= (String) session.getAttribute("codigoAlumno");
	String alumnoNombre 	= (String) request.getAttribute("alumnoNombre");
%>
<head>
<script type="text/javascript" src="../js/prototype-1.6.js"></script>
<script type="text/javascript">
	function guardar(){
		if(document.formaEnviar.archivo.value != ""){
			document.formaEnviar.btnGuardar.disabled = true;
			document.formaEnviar.btnGuardar.value = "Guardando...";
			document.formaEnviar.submit();
		}else
			alert("Selecciona el archivo a subir");
	}
</script>
</head>
<body>
<div class="container-fluid">
	<h2>Subir Foto <small class="text-muted fs-4">( Alumno: <%=matricula%> - <%=alumnoNombre%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="datos">Regresar</a>
	</div>
	<form name="formaEnviar" id="formaEnviar" enctype="multipart/form-data" action="guardar" method="post">
	<div class="alert">				
			<b>Elije la foto</b>&nbsp;&nbsp;
			<br>
			<input type="file" id="archivo" name="archivo" />
			<br>
			<input type="button" id="btnGuardar" value="Guardar" onclick="guardar();" class="btn btn-primary"/>
		</tr>			
	</div>
	</form>
</div>
</body>