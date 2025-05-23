<%@ page buffer= "none" %>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<%
	ResultSet rset			= null;
	Statement stmt			= conEnoc. createStatement();
	String COMANDO			= "";

	String s_idempleado 	= request.getParameter("f_empleado");
	String s_folio			= request.getParameter("f_folio");
	String s_nomina			= "";	
	
	String s_nombre 		= "";
	String s_bday			= "";
	String s_empleado 		= "";
	String s_relacion		= "";
	String s_puesto 		= "";
	String s_depto			= "";
	String s_cotejado 		= "";
	boolean ok 				= false;


	COMANDO = "SELECT EMP_CLAVE(ID_EMPLEADO) AS NOMINA, "+
			"NOMBRE, TO_CHAR(BDAY,'DD/MM/YYYY') AS FECHA " +
			"FROM ARON.DEPENDIENTE " +
			"WHERE ID_EMPLEADO = TO_NUMBER('"+s_idempleado+"') "+
			"AND FOLIO = TO_NUMBER('"+s_folio+"')";
	rset = stmt.executeQuery(COMANDO);
	if (rset.next()){ ok = true;
			s_nomina 			= rset.getString("NOMINA");
			s_nombre 			= rset.getString("NOMBRE");
			s_bday				= rset.getString("FECHA");
  	}
  	COMANDO = "SELECT RELACION, "+
  			"EMP_NOMBRE('"+s_nomina+"') AS EMPLEADO, "+
  			"EMP_PUESTO(ID_EMPLEADO) AS PUESTO, "+
  			"EMP_DEPTO(ID_EMPLEADO) AS DEPARTAMENTO, "+
  			"COTEJADO "+
			"FROM ENOC.EMP_DEPENDIENTE " + 
			"WHERE ID_EMPLEADO = TO_NUMBER('"+s_idempleado+"') "+
			"AND FOLIO = TO_NUMBER('"+s_folio+"')";
	rset = stmt.executeQuery(COMANDO);
	if (rset.next()){ ok = true;
			s_empleado 			= rset.getString("EMPLEADO");
			s_relacion			= rset.getString("RELACION");
			s_puesto 			= rset.getString("PUESTO");
			s_depto				= rset.getString("DEPARTAMENTO");
			s_cotejado			= rset.getString("COTEJADO");
  	}
  	
	if (ok==true){

%>
<br>
<form name="form1" method="post" action="add_dep.jsp">
<input name="Accion" type="hidden">
<table style="margin: 0 auto; width:90%">
  <tr>		
    <td colspan="3"><a href="empleado?f_codigo_personal=<%=s_nomina%>">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
  </tr>
  <tr align="CENTER" valign="TOP">		
      <th colspan="3" align="CENTER"> <b><font size="2" face="Arial, Helvetica, sans-serif">Datos 
        del Dependiente</font></b></th>
  </tr>			
  <tr align="CENTER" valign="TOP">		
      <td width="92" height="260" align="CENTER"> <table style="margin: 0 auto;">
          <tr> 
           <%session.setAttribute("mat",s_nomina+"-"+s_folio);%>
		  <td><img src='../../foto?Codigo=<%=s_nomina%>&Tipo=<%=s_folio%>' width="250" border="1"></td>
          </tr>
        </table></td>		
    <td align="CENTER" width="516"> 
      <table style="margin: 0 auto; width:100%">
          <tr> 
            <td align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><strong>N° 
              Depen:</strong></font></td>
            <td align="LEFT">
               <input name="f_dep" type="text" class="text" id="f_dep" value="<%=s_nomina+"-"+s_folio%>" size="9" maxlength="9" readonly>
               <input name="f_empleado" type="hidden" value="<%=s_idempleado%>">
            </td>
          </tr>          
          <tr> 
            <td height="27" align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><b><spring:message code="aca.Nombre"/>:</b></font></td>
            <td align="LEFT"> <input name="f_nombre" type="text" class="text" id="f_nombre" value="<%=s_nombre%>" size="60" maxlength="60" readonly></td>
          </tr>
          <tr> <% if (s_relacion == null){ s_relacion = ""; }%>
            <td align="LEFT"><b><font size="2" face="Arial, Helvetica, sans-serif">Relacion:</font></b></td>
            <td align="LEFT"><input name="f_relacion" type="text" class="text" id="f_relacion" value="<%=s_relacion%>" size="60" maxlength="20" readonly></td>
          </tr>
          <tr> 
            <td align="LEFT"><b><font size="2" face="Arial, Helvetica, sans-serif"><spring:message code="aca.Fecha"/>:</font></b></td>
            <td align="LEFT"><input name="f_fecha" type="text" class="text" id="f_fecha" value="<%=s_bday%>" size="10" maxlength="10" readonly></td>
          </tr>       
          <tr> <% if (s_empleado == null){ s_empleado = ""; }%>
            <td align="LEFT"><b><font size="2" face="Arial, Helvetica, sans-serif">Empleado</font></b></td>
            <td align="LEFT"><%=s_empleado%></td>
          </tr>
          <tr>  <% if (s_puesto == null){ s_puesto = ""; }%>
            <td align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><b>Puesto: 
              </b></font></td>
            <td align="LEFT"> <%=s_puesto%></td>
          </tr>
          <tr> <% if (s_depto == null){ s_depto = ""; }%>
            <td align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><b>Departamento: 
              </b></font></td>
            <td align="LEFT"><%=s_depto%></td>
          </tr>
          <tr> 
            <td align="LEFT"><b><font size="2" face="Arial, Helvetica, sans-serif">Cotejado:</font></b></td>
            <td align="LEFT"><input name="f_cotejado" type="text" class="text" id="f_cotejado" value="<%=s_cotejado%>" size="3" maxlength="1" readonly></td>
          </tr>
        </table>
	</td>   
  </tr>
 </table>
</form>
<%
  }
	if (stmt != null) stmt.close();
	if (rset != null) rset.close();	
%> 
<!-- fin de estructura -->
<%@ include file= "../../cierra_enoc.jsp" %>