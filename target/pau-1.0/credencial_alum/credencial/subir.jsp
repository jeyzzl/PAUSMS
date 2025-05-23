<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CredPersonal" scope="page" class="aca.cred.CredPersonal"/>
<jsp:useBean id="CredPersonalU" scope="page" class="aca.cred.CredPersonalUtil"/>
<%
	//String matricula = (String) session.getAttribute("mat");
String matricula	= (String) request.getParameter("Matricula");
String nombre		= (String) request.getParameter("Nombre");
String eventoId		= (String) request.getParameter("EventoId");
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
<link href="../../academico.css" rel="STYLESHEET" type="text/css">
</head>
<body>
<table class="goback">
	<tr>
		<td><a href="generaCredencial?Matricula=<%=matricula%>&Nombre=<%=nombre%>&EventoId=<%=eventoId%>" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i>Regresar </a></td>
	</tr>
</table>
<table style="width:80%; margin:0 auto;"   >
  <tr>
	<td align="center" class="titulo2" style="font-size:14pt;">Subir Foto del Alumno</td>
  </tr>	
  <tr>
	<td align="center" class="titulo2" style="font-size:10pt;">Alumno: [<%=matricula%>] - <%= CredPersonalU.getNombre(conEnoc, matricula)%></td>
  </tr>
</table>
<br>
<form name="formaEnviar" id="formaEnviar" enctype="multipart/form-data" action="guardar?Matricula=<%=matricula%>&Nombre=<%=nombre%>&EventoId=<%=eventoId%>" method="post">
<table style="width:80%; margin:0 auto;" class="tabla">
	<tr>
		<td><b>Elige la foto</b></td>
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