<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	ResultSet rset				= null;
	Statement stmt	 			= conEnoc.createStatement();
	String COMANDO 				= "";
	
	String s_grupo_ant		= "X";
	String s_grupo			= null;
	String s_maximo			= "0";
	String s_fecha			= null;
	String s_descripcion	= null;
	String s_documento		= null;	
	
	COMANDO =	"SELECT COALESCE(MAX(GRUPO)+ 1,1) AS MAXIMO FROM ENOC.LEG_CONDICIONES";
	rset = stmt.executeQuery(COMANDO);
    if(rset.next()){ 
		s_maximo = rset.getString("MAXIMO");
	}	
%>

<!-- inicio de estructura -->
<div class="container-fluid">
	<h1>Catalogo de condiciones</h1>
	<div class="alert alert-info d-flex align-items-center"">
		<a class="btn btn-primary" href="grabar.jsp?f_grupo=<%=s_maximo%>">A&ntilde;adir Grupo (+)</a>	
	</div>
	<table class="table table-sm table-bordered">
  	<tr> 
    	<th class="text-center" width="10%" height="18"><font face="Arial, Helvetica, sans-serif" size="2"><spring:message code="aca.Operacion"/></font></th>
    	<th class="text-center" width="5%" height="18"><font  face="Arial, Helvetica, sans-serif" size="2">Gpo.</font></th>
    	<th class="text-center" width="4%" height="18"><font face="Arial, Helvetica, sans-serif" size="2"><spring:message code="aca.Numero"/></font></th>
    	<th width="62%" height="18"><font face="Arial, Helvetica, sans-serif" size="2"><spring:message code='aca.Descripcion'/></font></th>
    	<th class="text-center" width="25%" height="18">Valida Fecha</th>
  	</tr>
<%
	COMANDO ="SELECT " +
			"COALESCE(TO_CHAR(LC.grupo,'99'),'No Encontro') grupo, " +
	 		"COALESCE(TO_CHAR(LC.iddocumento,'99'),'No Encontro') documento, " +
			"COALESCE(LD.descripcion, 'No Encontro') descripcion , " +
			"CASE LC.valida_fecha WHEN 'S' THEN 'SI' ELSE 'NO' END AS FECHA  " +
			"FROM ENOC.leg_condiciones LC, ENOC.leg_documentos LD " + 
			"WHERE iddocumento = LD.iddocumentos ORDER BY 1,2";
	rset = stmt.executeQuery(COMANDO);
    while(rset.next()){
		s_grupo			= rset.getString("grupo");
		s_documento		= rset.getString("documento");
		s_descripcion   	= rset.getString("descripcion");
		s_fecha			= rset.getString("fecha");
		
  		if (!s_grupo_ant.equals(rset.getString("grupo"))){
			   s_grupo_ant = rset.getString("grupo");
	   	}
%>
  	<tr class="tr2"> 
    	<td width="13%" align="center"> <font face="Arial, Helvetica, sans-serif" size="1"> 
      	<a href="grabar.jsp?f_grupo=<%=s_grupo%>" class="fas fa-edit"></a></td>
    	<td width="5%" align="center"><font color="#000000" size="1"><%=s_grupo%></font></td>
    	<td width="5%" align="center"><font color="#000000" size="1"><%=s_documento%></font></td>
    	<td width="61%" align="left"><font color="#000000" size="1"><%=s_descripcion%></font></td>
    	<td width="16%" align="center"><font color="#000000" size="1"><%=s_fecha%></font></td>
  	</tr>
<%
	}
    
    if (stmt != null) stmt.close();
	if (rset != null) rset.close();
%>
	</table>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>