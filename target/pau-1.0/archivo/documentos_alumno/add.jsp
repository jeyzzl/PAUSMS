<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<table style="margin: 0 auto;">
	<tr>
		<td>
<% 	
	String mensaje		= (String)request.getAttribute("mensaje");
	String color		= (String)request.getAttribute("color");
%>
			<font size="3" color="<%=color%>"><b><%=mensaje%></b></font>
		</td>
	</tr>
</table>
<head>
<meta http-equiv='REFRESH' content='0.5; url=documentos'>
</head>
