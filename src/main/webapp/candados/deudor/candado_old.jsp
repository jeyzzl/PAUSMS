<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.sql.PreparedStatement,java.sql.ResultSet"%>

<jsp:useBean id="CandAlumnoUtil" scope="page" class="aca.candado.CandAlumnoUtil" />
<jsp:useBean id="alumUtil" scope="page" class="aca.alumno.AlumUtil" />

<% 	
	DecimalFormat dmf 	= new DecimalFormat("###,##0.00;(###,##0.00)");
	String sCodigo 		= (String) session.getAttribute("codigoPersonal");
	String sBgcolor 	= "";
	int cont=0;	

	String codigo		= "";
	String ejercicio	= "";
	double saldo 		= 0;	

	ArrayList<String> lisDeudor	= new ArrayList<String>();
	
	ArrayList<String>	lisAlum = alumUtil.getListAlumnos(conEnoc);
%>

<table style="width:75%; margin:0 auto;" id="noayuda">
  <tr> 
    <td align="center"><font size="1"><strong>Lista de Alumnos con Candado de Saldo</strong></font></td>
  </tr>
  <tr> 
    <th><spring:message code="aca.Numero"/></th>
    <th><spring:message code="aca.Matricula"/></th>
    <th><spring:message code="aca.Nombre"/></th>
    <th><spring:message code="aca.Carrera"/></th>
    <th><spring:message code="aca.Estado"/></th>
  </tr>
<%
	for (cont=0;cont<lisAlum.size();cont++){
		codigo= (String)lisAlum.get(cont);
		saldo = alumUtil.getSaldo(conEnoc,codigo);	
		
	
	//for (cont=0;cont<lisDeudor.size();cont++){
		if((cont%2)== 0){sBgcolor = sColor; }else {sBgcolor = "";}
%>		
  <tr <%=sBgcolor%>> 
    <td><%=cont%></td>
    <td><%=codigo%></td>
    <td></td>
    <td></td>
    <td></td>
  </tr>
<%	} 
%>
</table>
<%@ include file= "../../cierra_enoc.jsp" %>