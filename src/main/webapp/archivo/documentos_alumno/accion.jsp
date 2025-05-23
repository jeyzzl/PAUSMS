<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String mensaje 		= (String) request.getAttribute("mensaje");
%>
	<div class="alert alert-info"><%=mensaje%></div>
<head><meta http-equiv='REFRESH' content='1.5; url=documentos'></head>