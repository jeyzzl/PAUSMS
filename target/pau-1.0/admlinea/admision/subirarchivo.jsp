<%@ include file= "../../con_enoc.jsp" %>
<%@page import="aca.admision.AdmDocumento"%>
<%
	String documentoId 	= request.getParameter("documentoId");
%>

<script type="text/javascript" src="../../js/prototype-1.6.js"></script>
<script type="text/javascript">
	function guardar(){
		if($("archivo").value != ""){
			$("btnGuardar").disabled = true;
			$("btnGuardar").value = "Guardando...";
			document.formaEnviar.submit();
		}else
			alert("Selecciona el archivo a subir");
	}
</script>
</head>
<body>
<table class="goback">
	<tr>
		<td><a href="documentos">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
	</tr>
</table>
<table style="width:80%; margin:0 auto;">
	<tr>
		<td class="titulo"><spring:message code='aca.EnviarDocumento'/></td>
	</tr>
	<tr>
		<td class="titulo2"><%=AdmDocumento.getNombreDocumento(conEnoc, documentoId) %></td>
	</tr>
</table>
<br>
<form name="formaEnviar" id="formaEnviar" enctype="multipart/form-data" action="guardararchivo.jsp?documentoId=<%=documentoId%>" method="post">
<table style="width:80%; margin:0 auto;">
	<tr>
		<td><b><spring:message code='aca.EligeElArchivo'/></b></td>
	</tr>
	<tr>
		<td><input type="file" id="archivo" name="archivo" /></td>
	</tr>
	<tr>
		<td><input type="button" id="btnGuardar" value="Guardar" onclick="guardar();" /></td>
	</tr>
</table>
</form>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>