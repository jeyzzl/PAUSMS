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
	
	String s_descripcion		= request.getParameter("f_descripcion");
	String s_imagen				= request.getParameter("f_imagen");
	String s_documento			= request.getParameter("f_documento");
	String s_chek				= request.getParameter("f_chek");
	
	if(s_chek==null){s_chek = "X"; }
//	if(s_documento==null){s_documento = "X"; }
	if(s_chek.equals("X")){
		COMANDO = "Select max(iddocumentos)+1 docto "+
        	      "from ENOC.leg_documentos "; 
	    rset = stmt.executeQuery (COMANDO);
		rset.next();
		s_documento = rset.getString("docto");		
	}
	if (!s_documento.equals("")){	
		COMANDO =	"Insert into ENOC.leg_documentos "+ 
					"(iddocumentos, descripcion, imagen) "+
					"values (to_number('"+
					s_documento+"'), upper('"+
					s_descripcion+"'),upper('"+
					s_imagen+"'))";
		rset = stmt.executeQuery(COMANDO);
		if (rset.next()){
%>
		<div align="center"><b><font size="3">¡¡.. La operación tuvo exito..!!</font></b> </div>
  <%
		}else{
%>
		<div align="center"><b><font color="#000000" size="3">¡¡.. No se realizo la operación ..!! </font></b> </div>
  <%
		}		
	}else{
%>
		<div align="center"><b><font color="#000000" size="3">¡¡.. Clave de documento no valida..!! </font></b> </div>
<%	
	}
	
	if (stmt != null) stmt.close();
	if (rset != null) rset.close();
%>
<meta http-equiv='REFRESH' content='0; url=documentos'>
<%@ include file= "../../cierra_enoc.jsp" %>
