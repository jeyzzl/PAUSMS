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
	String s_fecha				= null;
	String s_descripcion		= null;
	String s_documento			= null;
%>

<!-- inicio de estructura -->
<div class="container-fluid">
	<h2>Catalogo de Condiciones</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="condiciones.jsp">&lsaquo;&lsaquo; Regresar </a>
	</div>
	<form name="datos" method="post" action="add.jsp">
		<div class="row">
			<div class="span3">
				<label for="f_grupo">Grupo</label>			
				<input type="text" class="text" name="f_grupo" size="5" maxlength="3" value="<%=s_grupo%>">
				<br><br>
				<label for="f_documento"><spring:message code="aca.Documento"/></label>			
				<select name="f_documento">
<%
				COMANDO = "select " +
						"iddocumentos, descripcion " +
						"from ENOC.leg_documentos Order by 2"; 
				rset = stmt.executeQuery(COMANDO);
				while( rset.next()){
						out.print(" <option value='"+rset.getString("iddocumentos")+"'");
						out.print(" >"+ rset.getString("descripcion")+"</option>");
				}
%>
              	</select>
				<br><br>
				<label for="f_fecha">Valida Fecha</label>			
				<select name="f_fecha">
                	<option value="S" selected><spring:message code='aca.Si'/></option>
                	<option value="N"><spring:message code='aca.No'/></option>
              </select>
			</div>
		</div>
		<br>
		<div class="alert alert-info">
			<input class="btn btn-primary" type="submit" name="Submit" value="Grabar">
		</div>
  	</form>
<table style="width:70%"  dwcopytype="CopyTableRow" class="table table-fullcondensed">
  <tr> 
    <th width="13%" height="18"><spring:message code="aca.Operacion"/></th>
    <th width="6%" height="18">Gpo.</th>
    <th width="7%" height="18"><spring:message code="aca.Numero"/></th>
    <th width="56%" height="18"><spring:message code='aca.Descripcion'/></th>
    <th width="20%" height="18">Valida Fecha </th>
  </tr>
<%
		COMANDO ="Select " +
				"COALESCE(to_char(LC.grupo),'No Encontro') grupo, " +
		 		"COALESCE(to_char(LC.iddocumento),'No Encontro') documento, " +
				"COALESCE(LD.descripcion,'No Encontro') descripcion , " +
				"CASE LC.valida_fecha WHEN 'S' THEN 'SI' ELSE 'NO' END fecha  " +
				"from ENOC.leg_condiciones LC, ENOC.leg_documentos LD " + 
				"where iddocumento = LD.iddocumentos " +
				"and  LC.grupo = '"+s_grupo+"' order by 1,2";
	    rset = stmt.executeQuery(COMANDO);
        while(rset.next()){
		  s_grupo			= rset.getString("grupo");
		  s_documento		= rset.getString("documento");
		  s_descripcion   	= rset.getString("descripcion");
		  s_fecha			= rset.getString("fecha");
		
%>
  <tr> 
    <td width="14%" align="center"> <font face="Arial, Helvetica, sans-serif" size="1"> 
      <a href="pregunta.jsp?f_grupo=<%=s_grupo%>&f_documento=<%=s_documento%>"> <img src="../../imagenes/no.png" title="Borrar" /></a></font></td>
    <td width="6%" align="center"><font color="#000000" size="1"><%=s_grupo%></font></td>
    <td width="5%" align="center"><font color="#000000" size="1"><%=s_documento%></font></td>
    <td width="56%" align="left"><font color="#000000" size="1"><%=s_descripcion%></font></td>
    <td width="19%" align="center"><font color="#000000" size="1"><%=s_fecha%></font></td>
  </tr>
<%
       }
        
        if (stmt != null) stmt.close();
		if (rset != null) rset.close();
%>

</table>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>