<head>
<style type="text/css">
<!--
.style1 {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-weight: bold;
	font-size: 12px;
}
-->
</style>
<title>Fotos de alumnos</title>
</head>
<%
	String dir = request.getParameter("dir");
	java.io.File dirLista = new java.io.File(application.getRealPath("/WEB-INF/fotos/")+"/");
	String lista[] = dirLista.list();
%>
<table style="width:100%">
	<tr height="60">
		<td height="23" bgcolor="#0099FF">
			<div align="center" class="style1">Preparado para descargar <%=lista.length%> fotos a <%=dir%></div></td>
	</tr>
	<tr>
		<td align="center">
			<applet code=bajarFotos.class width=350 height=150>
				<param name='total' value='<%=lista.length%>'>
				<param name='dir' value='<%=dir%>'>
<%
				for (int i=0;i<lista.length;i++){
%>					<param name='foto<%=i%>' value='<%=lista[i].substring(0,7)%>'>
<%				}
%>
			</applet>
		</td>
	</tr>
</table>