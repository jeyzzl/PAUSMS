<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	ResultSet rset				= null;
	Statement stmt	 			= conEnoc.createStatement();
	String COMANDO 				= "";
	
	String s_grupo				= request.getParameter("f_grupo");
  	String s_documento			= request.getParameter("f_documento");	

	COMANDO = "Delete From ENOC.leg_condiciones "+
             "where grupo = to_number('"+s_grupo+"') and "+
		  "iddocumento = to_number('"+s_documento+"') ";
	rset = stmt.executeQuery (COMANDO);
	if (rset.next()){
%>
			<center><b>Se realizó con éxito la operación.</b></center>
<%		
	}else{
%>
			<center><b>Error..¡¡  No se pudo realizar la operación</b></center>
<%	}
	if (stmt != null) stmt.close();
	if (rset != null) rset.close();
%>
<meta http-equiv='REFRESH' content='0; url=grabar.jsp?f_grupo=<%=s_grupo%>'>
<%@ include file= "../../cierra_enoc.jsp" %>
