<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	ResultSet rset				= null;
	Statement stmt	 			= conEnoc.createStatement();
	String COMANDO 				= "";

	String s_codigo_personal	= request.getParameter("f_codigo_personal");
	String s_sector		 	 	= null;
	String s_autorizado		 	= "";
	String s_nombre	 			= null;
	String s_pais			 	= null;
	int contador				= 0;
%>
<!-- inicio de estructura -->
  <table style="width:80%"  align="center" id="noayuda">
    <tr>      
    <td height="25" align="center"><b><font color="#000066" size="4">Datos de 
      Extranjeros</font></b></td>
    </tr>
    <tr>      
    <td height="25" align="center">&nbsp;</td>
    </tr>
  </table>
<table style="width:80%"  align="center">
  <form name="form1" method="post" action="add_extranjero?f_codigo_personal=<%=s_codigo_personal%>">
    <tr> 
      <th width="6%"> 
        <div align="center"><b><font face="Arial, Helvetica, sans-serif" size="2"><spring:message code="aca.Numero"/></font></b></div>
      </th>
      <th width="12%"><b><font face="Arial, Helvetica, sans-serif" size="2"><spring:message code="aca.Matricula"/></font></b></th>
      <th width="48%"><b><font face="Arial, Helvetica, sans-serif" size="2"><spring:message code="aca.Nombre"/></font></b></th>
      <th width="34%"><b><font face="Arial, Helvetica, sans-serif" size="2"><spring:message code="aca.Pais"/></font></b></th>
    </tr>
    <%
	COMANDO = "SELECT " +
	 		"AP.codigo_personal AS codigo , " +
			"AP.apellido_paterno||' '||AP.apellido_materno||' '||AP.nombre AS nombre, " +
			"CP.NOMBRE_PAIS AS pais " +
			"from ENOC.ALUM_PERSONAL AP, ENOC.Cat_pais CP " + 
			"where AP.pais_ID != 91 and AP.pais_ID != 0 "+
			"and AP.pais_ID = CP.pais_ID "+
			"Order by nombre";
	rset = stmt.executeQuery(COMANDO);
  	while(rset.next()){	
		contador++;
		s_codigo_personal	= rset.getString("codigo");
		s_nombre 			= rset.getString("nombre");
		s_pais 				= rset.getString("pais");
%>
    <tr> 
      <td width="6%" height="19"> 
        <div align="right"><b><%=contador%></b> </div>
      </td>
      <td width="12%" height="19"> <div align="center"><%=s_codigo_personal%></div></td>
      <td width="48%" height="19"><%=s_nombre%></td>
      <td width="34%" height="19"><%=s_pais%></td>
    </tr>
    <%	
  }
  	
  	if (stmt != null) stmt.close();
	if (rset != null) rset.close();
%>
  </form>
</table>
<%@ include file= "../../cierra_enoc.jsp" %>