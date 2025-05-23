<html>
<%
	if (request.getAttribute("conEnoc")!=null){
		java.sql.Connection conEnoc = (java.sql.Connection)request.getAttribute("conEnoc");
		request.removeAttribute("conEnoc");	
		if (conEnoc!=null) conEnoc.close();
	}
%>
<body>
	<table style="margin: 0 auto;">
		<tr>
			<td><font size="4" color="orange">Ocurri&oacute; un error al cargar la p&aacute;gina. Int&eacute;ntelo de nuevo y si persiste la falla reportelo a sistemas Ext. 1059</font></td>
		</tr>
	</table>
</body>
</html>