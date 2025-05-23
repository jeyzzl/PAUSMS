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
	
	String s_codigo_personal	= request.getParameter("f_codigo_personal");
	String s_estado				= request.getParameter("f_estado");
	String s_fecha_ini			= request.getParameter("f_fecha_ini");
	String s_fecha_lim			= request.getParameter("f_fecha_lim");
	String s_usuario_alta		= request.getParameter("f_codigo_usuario");
	String s_folio				= request.getParameter("f_folio");
	
	
		
		COMANDO ="Select codigo, folio " +
				"from ENOC.leg_permisos " + 
				"where codigo = '"+s_codigo_personal+"' " +
				"and folio = '"+s_folio+"' ";
		rset = stmt.executeQuery(COMANDO);
		if (rset.next()){
		
		  COMANDO ="Update ENOC.leg_permisos " +
		  		"Set fecha_lim = to_date('"+s_fecha_lim+"','dd/mm/yyyy'), " +
				"fecha_ini = to_date('"+s_fecha_ini+"','dd/mm/yyyy') " +
				"where codigo = '"+s_codigo_personal+"' " +
				"and folio = '"+s_folio+"' ";
		  rset = stmt.executeQuery(COMANDO);
		  if (rset.next()){
%>			
		    
<div align="center"><b><font size="3">¡¡..Los Datos fueron Actualizados con exito..!!</font></b> 
</div>	
<%
		  }else{
%>		  
		    <div align="center"><b><font color="#000000" size="3">¡¡.. No se realizo la operación ..!! </font></b> </div>		
<%
		  }	
%>			
<%	  }else{
		
		COMANDO =	"Insert into ENOC.leg_permisos "+ 
					"(codigo, usuario_alta , " +
					"fecha_ini,fecha_lim, status, folio) " +
					"values ('"+
					s_codigo_personal+"','"+
					s_usuario_alta+"', to_date('"+
					s_fecha_ini+"','dd/mm/yyyy'), to_date('"+
					s_fecha_lim+"','dd/mm/yyyy'), '"+
					s_estado+"', to_number('"+
					s_folio+"'))";
		rset = stmt.executeQuery(COMANDO);
		if (rset.next()){
%>
		  <div align="center"><b><font size="3">¡¡.. Los datos fueron insertados con exito..!!</font></b> </div>
  <%
		}else{
%>
		  <div align="center"><b><font color="#000000" size="3">¡¡.. No se realizo la operación ..!! </font></b> </div>
<%
		}
	}	
		
	if (stmt != null) stmt.close();
	if (rset != null) rset.close();
%>
	
<meta http-equiv='REFRESH' content='0;URL=permiso.jsp?f_codigo_personal=<%=s_codigo_personal%>'>
<%@ include file= "../../cierra_enoc.jsp" %>
