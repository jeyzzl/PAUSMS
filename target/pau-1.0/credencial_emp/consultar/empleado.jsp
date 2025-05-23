<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
	ResultSet rset			= null;
	ResultSet rset2			= null;
	Statement stmt			= conEnoc. createStatement();
	Statement stmt2			= conEnoc.createStatement();
	String COMANDO			= "";
	
	String s_nomina 		= (String)session.getAttribute("codigoEmpleado");
	String s_empleado		= "";
	String s_folio			= "";
	String s_nombre 		= "x";
	String s_apellidoP 		= "x";
	String s_apellidoM		= "x";
	String s_puesto 		= "x";
	String s_depto 			= "x";
	String s_puestoRH		= "x";
	String s_deptoRH		= "x";	
	String s_status			= "x";
	String s_coteja			= "x";
	String s_check1			= "";
	String s_check2			= "";
	String s_check3			= "";
	String nombreCredencial = "";

	String sE 				= "";
    int NumArch			    = 1;
	boolean Salir 			= false;
	boolean emp_academico 	= false;
	
	if (aca.emp.EmpleadoUtil.existeRegClave(conEnoc,  s_nomina )) {		
	
		COMANDO = "SELECT PUESTO, DEPARTAMENTO, STATUS, COTEJADO, NOMBRE"+
				" FROM ENOC.EMP_DATOS" + 
				" WHERE ID_EMPLEADO = '"+s_nomina+"' ";
		rset = stmt.executeQuery(COMANDO);
		if (rset.next()){
			emp_academico = true;
			s_puesto 			= rset.getString("PUESTO");
			s_depto				= rset.getString("DEPARTAMENTO");
			s_status			= rset.getString("STATUS");
			s_coteja			= rset.getString("COTEJADO");
			nombreCredencial	= rset.getString("NOMBRE");
		}

		COMANDO = "SELECT ID, NOMBRE, APPATERNO, APMATERNO,"+
				" ENOC.EMP_CCOSTO(ID) AS DEPARTAMENTO, ENOC.EMP_PUESTO2(ID) AS PUESTO"+	
			 	" FROM ARON.EMPLEADO WHERE CLAVE = '"+s_nomina+"' ";
		rset2 = stmt2.executeQuery(COMANDO);
		if (rset2.next()){
			s_empleado			= rset2.getString("ID");
			s_nombre 			= rset2.getString("NOMBRE");
			s_apellidoP 		= rset2.getString("APPATERNO");
			s_apellidoM			= rset2.getString("APMATERNO");
			s_puestoRH			= rset2.getString("PUESTO");
			s_deptoRH			= rset2.getString("DEPARTAMENTO");
			if (emp_academico==false){
				s_puesto 			= rset2.getString("PUESTO");
				s_depto				= rset2.getString("DEPARTAMENTO");
			}			
	
			if (s_status.equals("A")){ 
				s_check1 = "checked"; s_check2 = ""; s_check3="";
			}else if (s_status.equals("J")||s_nomina.substring(0,3).equals("988")){
				s_check1 = ""; s_check2 = ""; s_check3="checked";
			}else{ 
				s_check1 = ""; s_check2 = "checked"; s_check3="";
			}
			session.setAttribute("matricula",s_nomina);
			session.setAttribute("nombre",s_nombre);
			session.setAttribute("apellidos",s_apellidoP+" "+s_apellidoM);
			session.setAttribute("puesto",s_puesto);
			session.setAttribute("depto",s_depto);
			session.setAttribute("mat",s_nomina);			
%>
<div class="container-fluid">
<h1>Datos del empleado</h1>
<div class="alert alert-info"></div>
<form name="form1" method="post" action="add.jsp" class="table table-condensed">
<input name="Accion" type="hidden">
<table   width="80%">  
  <tr>
  	<th colspan="3"></th>
  </tr>  
  <tr valign="top">		
    <td width="92" height="260"> 
      <table>
		<tr>
		  <td><img src="../../foto?Codigo=<%=s_nomina%>&Tipo=O" width="200" border="1"></td>
		</tr>
	  </table>
	</td>		
    <td width="516" valign="middle"> 
      <table style="width:100%">		  
          <tr> 
            <td><b><font size="2">N° Empleado</font></b></td>
            <td><input name="f_empleado" type="text" class="text" id="f_empleado" value="<%=s_nomina%>" size="8" maxlength="7" readonly></td>
          </tr>
          <tr> 
            <td><font size="2"><b><spring:message code="aca.Nombre"/>:</b></font></td>
            <td><%=s_nombre%></td>
          </tr>
          <tr> 
            <td><font size="2"><b>Ap. Paterno:</b></font></td>
            <td><%=s_apellidoP%></td>
          </tr>
          <tr> 
            <td><font size="2"><b>Ap. Materno:</b></font></td>
            <td><%=s_apellidoM%></td>
          </tr>
          <tr> 
            <td><font size="2"><b>Puesto:</b></font></td>
            <td><%=s_puesto%> &nbsp; ( R.H.&nbsp; <%=s_puestoRH%> )</td>
          </tr>
          <tr> 
            <td><font size="2"><b>Departamento:</b></font></td>
            <td><%=s_depto%> ( &nbsp;R.H.&nbsp; <%=s_deptoRH%>)</td>
          </tr>
          <tr> 
            <td><font size="2"><b>Status:</b></font> </td>
            <td>
              Activo 
              <input name="f_activo" type="radio" value="A" <%=s_check1%> disabled> &nbsp;&nbsp;&nbsp;&nbsp;
              Jubilado 
              <input name="f_activo" type="radio" value="J" <%=s_check3%> disabled> &nbsp;&nbsp;&nbsp;&nbsp;
			  Inactivo
              <input name="f_activo" type="radio" value="I"  <%=s_check2%> disabled>
            </td>
	      </tr>
          <tr>
            <td align="LEFT"><strong>Cotejado:</strong></td>
<%		if(s_coteja.equals("S")){%>
			<td align="LEFT"><input name="f_cotejado" type="text" class="text" id="f_cotejado" value="<%=s_coteja%>" size="2" maxlength="1" readonly></td>
<%  	}else {%>
            <td align="LEFT"><input name="f_cotejado" type="text" class="text" id="f_cotejado" value="<%=s_coteja%>" size="2" maxlength="1" readonly></td>
<%  	} %>
          </tr>
      </table>
	</td>   
  </tr>
 </table>
</form>
<table style="width:70%">
  <tr> 
    <td colspan="5"><h4>Dependientes del empleado</h4></td>
  </tr>
  <tr> 
    <th width="8%"><spring:message code="aca.Folio"/></th>
	<th width="30%"><spring:message code="aca.Nombre"/></th>
	<th width="13%">Relación</th>
	<th width="11%">F. Nac.</th>
  </tr>
<%	
			COMANDO = "SELECT ID, NOMBRE, "+
					"DEP_RELACION(EMPLEADO_ID,ID) AS RELACION, "+
					"TO_CHAR(BDAY,'DD/MM/YYYY') AS FECHA " +
					"FROM ARON.EMPLEADO_DEPENDIENTES " +
					"WHERE EMPLEADO_ID = '"+s_empleado+"' "+
					"ORDER BY BDAY";
			rset = stmt.executeQuery(COMANDO);
			while (rset.next()){
				s_folio= rset.getString("ID");
%>
  <tr> 
    <td><%=s_folio%></td>
	<td><a href="dato_dep.jsp?f_empleado=<%=s_empleado%>&f_folio=<%=s_folio%>"><%=rset.getString("NOMBRE")%></a></td>
	<td><%=rset.getString("RELACION")%></td>
	<td><%=rset.getString("FECHA")%></td>
  </tr>
<%			}%>
</table>
<%  	}else{ 
			out.println("<p align='center'>¡No existe el empleado!</p>");
		}
		if (rset!=null) rset.close();
		if (rset2!=null) rset2.close();
		try { stmt.close(); } catch (Exception ignore) { }
		if (stmt2!=null) stmt2.close();
	
	}else{ 
		out.println("<p align='center'>¡No existe el empleado!</p>");
	}
	
	try { rset.close(); } catch (Exception ignore) { }
	try { rset2.close(); } catch (Exception ignore) { }
	try { stmt.close(); } catch (Exception ignore) { }
	try { stmt2.close(); } catch (Exception ignore) { }	
%> 
</div>
<%@ include file= "../../cierra_enoc.jsp" %>