<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<%
	ResultSet rset				= null;
	Statement stmt	 			= conEnoc.createStatement();
	String COMANDO 				= "";
	
	String s_documento = 	request.getParameter("f_documento");
	
	COMANDO ="Delete from ENOC.leg_documentos " + 
			"where iddocumentos = to_number('"+s_documento+"') ";
	rset = stmt.executeQuery(COMANDO);
	if (rset.next()){
%>			
		<div align="center"><b><font size="3">¡¡.. La operación tuvo exito..!!</font></b> </div>		
<%  } 
   else {
%>
<div align="center"><b><font color="#000000" size="3">¡¡.. No se realizo la operación ..!! </font></b> </div>
<%
   }
	
	if (stmt != null) stmt.close();
	if (rset != null) rset.close();
%>

<head>
	<meta http-equiv='REFRESH' content='0; url=documentos'>
</head>	
<%@ include file= "../../cierra_enoc.jsp" %>
