<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "../../conectadbp.jsp" %>
<%@ include file= "id.jsp" %>

<!-- inicio de estructura -->
<% 
	String codigoPersonal	= request.getParameter("fmatricula");
	String idDocumento		= request.getParameter("fdocumento");
	String s_nhoja			= request.getParameter("fhoja");
	stmt2 = conn2.createStatement();
	String comando = "DELETE FROM ARCH_DOCALUM WHERE MATRICULA = '"+codigoPersonal+"' AND IDDOCUMENTO = '"+idDocumento+"' AND HOJA = "+s_nhoja;
	stmt2.executeUpdate(comando);
	if (stmt2!=null) stmt2.close();
	if (conn2!=null) conn2.close();
%>
<table style="margin: 0 auto;"><tr><td><font size="3" color="blue"><b>Image Deleted</b></td></tr></table>
<head>
<meta http-equiv='REFRESH' content='0; url=verimagen.jsp?f_codigo_personal=<%=codigoPersonal%>&f_documento=0'>
</head>
<%@ include file= "../../cierradbp.jsp" %>
<%@ include file= "../../cierra_enoc.jsp" %>
