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
	
	//declaracion de variables
	String codigo_usuario 		= (String) session.getAttribute("codigoPersonal");
	String s_login 				= (String) session.getAttribute("usuario");
	String s_codigo_personal 	= request.getParameter("f_codigo_personal");	
	String s_estado 			= "A";
	String s_folio 				= null;
	String s_fecha				= "";
	String s_fecha2				= "";

	COMANDO = "Select codigo_personal, to_char(now(),'dd/mm/yyyy') as fecha, to_char(now()+30,'dd/mm/yyyy') as f2 " +
			"from datos_personales "+
			"where codigo_personal = '"+codigo_usuario+"'";
	rset = stmt.executeQuery(COMANDO);
	if (rset.next()){
		codigo_usuario = rset.getString("codigo_personal");
		s_fecha 	= rset.getString("fecha");
		s_fecha2	= rset.getString("f2");
	}
%>
<form name="form1" method="post" action="insert_permiso.jsp?f_codigo_usuario=<%=codigo_usuario%>&f_codigo_personal=<%=s_codigo_personal%>&f_estado=<%=s_estado%>">
  <table style="width:60%"  align="center">
    <tr>
      <td align="center"><a href="permiso.jsp?f_codigo_personal=<%=s_codigo_personal%>">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
  <table style="width:60%"  align="center">
    <tr> 
      <td width="25%" height="22"><b><spring:message code="aca.Matricula"/></b></td>
      <td width="75%" height="22"><%=s_codigo_personal%></td>
    </tr>
<%
	COMANDO = "Select COALESCE(max(folio) + 1,1) folio " +
			"from ENOC.leg_permisos " + 
			"where codigo = '"+s_codigo_personal+"' ";
	rset = stmt.executeQuery(COMANDO);
	if (rset.next()){
		s_folio = rset.getString("folio");
	}
%>
    <tr> 
      <td width="25%" height="23"><b><spring:message code="aca.Folio"/></b></td>
      <td width="75%" height="23"> 
        <input type="text" class="text" name="f_folio" size="5" maxlength="2" value="<%=s_folio%>">
      </td>
    </tr>
    <tr> 
      <td width="25%"><b>Usuario Alta</b></td>
      <td width="75%"><%=s_login%>(<%=codigo_usuario%>) </td>
    </tr>
    <tr> 
      <td width="25%"><b>Usuario Baja</b></td>
      <td width="75%">&nbsp;</td>
    </tr>
    <tr> 
      <td width="25%"><b>Fencha Inicial</b></td>
      <td width="75%"> 
        <input type="text" class="text" name="f_fecha_ini" value="<%=s_fecha%>">
        <b>(DD/MM/AAAA) </b> </td>
    </tr>
    <tr> 
      <td width="25%"><b>Fecha Limite</b></td>
      <td width="75%"> 
        <input type="text" class="text" name="f_fecha_lim" value="<%=s_fecha2%>">
        <b>(DD/MM/AAAA)</b> </td>
    </tr>
    <tr> 
      <td width="25%"><b><spring:message code="aca.Estado"/></b></td>
      <td width="75%"><%=s_estado%> </td>
    </tr>
    <tr> 
      <td width="25%"><b></b></td>
      <td width="75%"> 
        <input type="submit" name="f_grabar" value="Grabar">
      </td>
    </tr>
  </table>
</form>
<%	
	if (stmt != null) stmt.close();
	if (rset != null) rset.close();
%>
<%@ include file= "../../cierra_enoc.jsp" %>