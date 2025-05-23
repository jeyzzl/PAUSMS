<html>
<head>
  <title>Credencial</title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body bgcolor='#999999' leftmargin=0 topmargin=0>
<table id="noayuda">
	<tr>
	<td >
	<applet name='credencial' code="credencial.CredencialAlumno" width="415" height="295" alt="Se necesita Java para mostrar la credencial.">
			<param name= "matricula" value="<%=session.getAttribute("matricula")%>">	
			<param name= "nombre" value="<%=session.getAttribute("nombre")%>">	
			<param name= "apellidos" value="<%=session.getAttribute("apellidos")%>">	
			<param name= "carrera" value="<%=session.getAttribute("carrera")%>">	
		</applet>
	</td>
	</tr>
</table>
</body>
</html>