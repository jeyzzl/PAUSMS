<%@ page buffer= "none" %>
<html>
  <title>Camara</title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<head>
	<script>
		function refrescar(){
			window.
			window.opener.refrescar();
		}
		function setMatricula(m){
			document.camara.setMatricula(m);
		}
	</script>
</head>
<body bgcolor='#999999' leftmargin=0 topmargin=0>
<table id="noayuda">
	<tr>
	<td >
		<applet name='cam' code="camaraWeb.ventana" width="900" height="600" alt="Se necesita Java para mostrar la camara.">
			<param name= "matricula" value="<%=session.getAttribute("mat")%>">
		</applet>
	</td>
	</tr>
</table>
</body>
</html>