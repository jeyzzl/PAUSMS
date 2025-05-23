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
	String s_codigo_personal 	= request.getParameter("f_codigo_personal");
	String s_nombre			 	= null;
	String s_usuario_alta	 	= null;
	String s_usuario_baja	 	= null;
	String s_fecha_ini		 	= null;
	String s_fecha_lim		 	= null;
	String s_estado			 	= null;
	String s_folio			 	= null;
	String s_autoriza 			= "FALSE";
	String s_status 			= "";
	
		COMANDO = "select " +
		 		"codigo_personal codigo , " +
				"dato_nombres||' '||dato_apellido_paterno||' '||dato_apellido_materno nombre  " +
				"from datos_personales " +
				"where codigo_personal= '"+s_codigo_personal+"'";				
	  	rset = stmt.executeQuery(COMANDO);
  	   	if(rset.next()){
		s_codigo_personal		= rset.getString("codigo");
		s_nombre 				= rset.getString("nombre");	
%>
  <table style="width:75%"  align="center">
    <tr> 
      <td align="center"><b><font size="4">CATALOGO DE PERMISOS</font></b></td>
    </tr>
    <tr>
    <td align="center"><a href="buscar.jsp">Buscar</a></td>
    </tr>
    <tr>
      <td align="center">&nbsp;</td>
    </tr>
  </table>
  
<table style="width:60%; margin:0 auto;" class="table table-fullcondensed table-nohover">
  <tr>
    <td>
      <table style="width:100%"  align="center">
        <tr align="center"> 
          <td colspan="2"><b><font color="#000099"><spring:message code='aca.DatosPersonales'/></font></b></td>
        </tr>
        <tr> 
          <td width="28%"><b><spring:message code="aca.Matricula"/></b></td>
          <td width="72%"><%=s_codigo_personal%></td>
        </tr>
        <tr> 
          <td><b><spring:message code="aca.Nombre"/></b></td>
          <td><%=s_nombre%></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<table style="width:75%"  align="center">
  <tr>
    <td align="center"><a href="add_permiso.jsp?f_codigo_personal=<%=s_codigo_personal%>"><spring:message code='aca.Añadir'/></a></td>
  </tr>
</table>
<table style="width:75%"  align="center">
  <tr> 
    <th width="13%"> 
      <div align="center"><b><font face="Arial, Helvetica, sans-serif" size="2"><spring:message code="aca.Operacion"/></font></b></div>
    </th>
    <th width="8%"> 
      <div align="center"><b><font face="Arial, Helvetica, sans-serif" size="2"><spring:message code="aca.Folio"/></font></b></div>
    </th>
    <th width="19%"> 
      <div align="left"><b><font face="Arial, Helvetica, sans-serif" size="2">Us. 
        Alta</font></b></div>
    </th>
    <th width="19%"> 
      <div align="center"><b><font face="Arial, Helvetica, sans-serif" size="2">Us. 
        Baja</font></b></div>
    </th>
    <th width="17%"> 
      <div align="center"><b><font face="Arial, Helvetica, sans-serif" size="2">F. 
        Inicial</font></b></div>
    </th>
    <th width="15%"> 
      <div align="center"><b><font face="Arial, Helvetica, sans-serif" size="2">F. 
        Limite</font></b></div>
    </th>
    <th width="9%"> 
      <div align="center"><b><font face="Arial, Helvetica, sans-serif" size="2"><spring:message code="aca.Estado"/></font></b></div>
    </th>
  </tr>
</table>
<table style="width:75%"  align="center">
<%
	COMANDO ="Select " +
			"COALESCE(usuario_alta, 'No encontro')  alta , " +
			"COALESCE(usuario_baja, 'Ninguno') baja ,  " +
			"COALESCE(to_char(fecha_ini,'DD/MM/YYYY'), 'No encontro') inicial , " +
			"COALESCE(to_char(fecha_lim,'DD/MM/YYYY'), 'No encontro' ) limite , " +
			"COALESCE(status, 'No encontro') status,  " +
			"COALESCE(to_char(folio), 'No encontro') folio " +
			"from ENOC.leg_permisos " + 
			"where codigo= '"+s_codigo_personal+"'";				
				
	  	rset = stmt.executeQuery(COMANDO);
  	   	while(rset.next()){
		  s_usuario_alta	 	= rset.getString("alta");
		  s_usuario_baja	 	= rset.getString("baja");
		  s_fecha_ini		 	= rset.getString("inicial");
		  s_fecha_lim		 	= rset.getString("limite");
		  s_estado			 	= rset.getString("status");
		  s_folio			 	= rset.getString("folio");		
%>
  <tr> 
    <td width="13%"><a href="actualiza_permiso.jsp?f_codigo_personal=<%=s_codigo_personal%>&f_codigo_usuario=<%=codigo_usuario%>&f_folio=<%=s_folio%>"> 
      <font face="Arial, Helvetica, sans-serif" size="1">Desabilitar</font></a></td>
    <td width="8%" align="center"><%=s_folio%></td>
    <td width="19%"><%=s_usuario_alta%></td>
    <td width="19%"><%=s_usuario_baja%></td>
    <td width="17%"><%=s_fecha_ini%></td>
    <td width="15%"><%=s_fecha_lim%></td>
    <td align = "center" width="9%"><%=s_estado%></td>
  </tr>
<%		
			}
%>
</table>
<%			

		COMANDO ="Select codigo "+
			"From ENOC.Leg_Permisos "+			
			"where codigo= '"+s_codigo_personal+"' "+
			"and status = 'A' "+			
			"and to_date(now(),'dd-mm-yy') between fecha_ini and Fecha_Lim";
	  	rset = stmt.executeQuery(COMANDO);
  	   	if (rset.next()){
			s_autoriza = "TRUE";
		}
		COMANDO ="Select matricula "+
				"from Aron.Cand_alumnos " +
				"where matricula = '"+s_codigo_personal+"' and "+
				"tipo_usuario = '05' and Cand_clave = '01'";
		rset = stmt.executeQuery(COMANDO);		
		if (rset.next()){
			if (s_autoriza.equals("FALSE")){ 
				s_status = "A";
			}else{
				s_status  = "I";
			}
			COMANDO ="Update Aron.Cand_alumnos " +
					"Set Status = '"+s_status+"' "+
					"Where matricula = '"+s_codigo_personal+"' and "+
					"tipo_usuario = '05' and Cand_clave = '01'";
			rset = stmt.executeQuery(COMANDO);
		}
		
	} // if de busqueda del alumno.
  	   	
	if (stmt != null) stmt.close();
	if (rset != null) rset.close();
%>	
<%@ include file= "../../cierra_enoc.jsp" %>