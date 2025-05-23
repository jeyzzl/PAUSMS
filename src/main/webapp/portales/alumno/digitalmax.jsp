<%@ include file="../../idioma.jsp"%>

<%
String imgScr				= request.getParameter("imgscr");
%>
<title>Sistema Academico - Tu Documento Digitalizado</title>
<table style="width:100%"  >
  <tr><td width="100%"><img name="iDoc" src="<%=imgScr%>" ><td></tr>
</table>