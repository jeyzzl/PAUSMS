<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<%
	String s_usuario_baja		= request.getParameter("f_codigo_usuario");
	String s_estado				= "I";
	String s_codigo_personal	= request.getParameter("f_codigo_personal");
	String s_folio				= request.getParameter("f_folio");
	
	Statement stmt = conEnoc.createStatement();
	ResultSet rset = null;
	
	String COMANDO = "Update ENOC.leg_permisos "+
			  "set usuario_baja = '"+s_usuario_baja+"', status = '"+s_estado+"' " +
  		      "where codigo = '"+s_codigo_personal+"'" +
			  "and folio = to_number('"+s_folio+"') ";
			  
    rset = stmt.executeQuery (COMANDO);
	if (rset.next()){
		
%>
		<div align="center"><b><font size="3">¡¡.. La operación tuvo exito..!!</font></b> </div>
			
<%
	
	}else{
%>
	  <div align="center"><b><font color="#000000" size="3">¡¡.. No se realizo la operación ..!! </font></b> </div>
<%
	}
	
	if (stmt != null) stmt.close();
	if (rset != null) rset.close();
%>
<meta http-equiv='REFRESH' content='0;URL=permiso.jsp?f_codigo_personal=<%=s_codigo_personal%>'>
<%@ include file= "../../cierra_enoc.jsp" %>  
