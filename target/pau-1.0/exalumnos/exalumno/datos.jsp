<%@ page import="java.util.Date"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguroJquery.jsf" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String matricula 	= "";
	String year			= "";
	boolean anioEgreso  = false;
	if(request.getParameter("codigoAlumno")!=null){
		year		= request.getParameter("Year");
		matricula 	= request.getParameter("codigoAlumno");
		session.setAttribute("codigoAlumno",matricula);
		anioEgreso	= true;
	}else{
		matricula 		= (String) session.getAttribute("codigoAlumno");
	}
	
	AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");

	String nacionalidad 		= (String) request.getAttribute("nacionalidad");
	boolean existeCorreo 		= (boolean) request.getAttribute("existeCorreo");
	boolean existeDireccion  	= (boolean) request.getAttribute("existeDireccion");
	boolean existeFamilia 		= (boolean) request.getAttribute("existeFamilia");
	boolean existeEgreso 		= (boolean) request.getAttribute("existeEgreso");
	boolean existeEmpleo 		= (boolean) request.getAttribute("existeEmpleo");
	boolean existeTelefono 		= (boolean) request.getAttribute("existeTelefono");
	boolean existeIglesia 		= (boolean) request.getAttribute("existeIglesia");
	boolean existeRed	 		= (boolean) request.getAttribute("existeRed");
	String totCorreo 		= (String) request.getAttribute("totCorreo");
	String totDireccion  	= (String) request.getAttribute("totDireccion");
	String totFamilia 		= (String) request.getAttribute("totFamilia");	
	String totEstudio		= (String) request.getAttribute("totEstudio");
	String totEmpleo 		= (String) request.getAttribute("totEmpleo");
	String totTelefono 		= (String) request.getAttribute("totTelefono");
	String totIglesia 		= (String) request.getAttribute("totIglesia");
	String totRed	 		= (String) request.getAttribute("totRed");
	
	if(anioEgreso){
%>
		<table class="goback">
			<tr>
				<td><a class="btn btn-primary" href="../reportes/anioEgreso?Year=<%=year%>">&lsaquo;&lsaquo;<spring:message code="aca.Regresar"/></a></td>
			</tr>
		</table>
<%	}	%>
<%@ include file= "menu.jsf" %>

<table class="table table-sm" align="center" style="margin-top:1px; width:80%">
	<tr>
		<td align='center' width="129" rowspan="11" nowrap style="border-bottom:0;">
    		<div id="sombra"><img src="../../foto?Codigo=<%=matricula%>&Tipo=O" width="150"></div></td>
       <td><b><spring:message code="aca.Matricula"/>:</b> </td>
       <td><%=matricula%></td>
	</tr>
	<tr>
		<td>
			<b><spring:message code="aca.Nombre"/>:</b> 
		</td>
		<td>
			<%=alumPersonal.getNombre() +" " +alumPersonal.getApellidoPaterno()+" " +alumPersonal.getApellidoMaterno()%>
		</td>
	</tr>
	<tr>
		<td>
			<b><spring:message code='aca.Genero'/>:</b> 
		</td>
		<td>
			<%=alumPersonal.getSexo().equals("M")?"Masculino":"Femenino"%>
		</td>
	</tr>
	<tr>
		<td>
			<b>Birthdate:</b> 
		</td>
		<td>
			<%=alumPersonal.getFNacimiento() %>
		</td>
	</tr>
	<tr>
		<td><b>Curp:</b></td>
		<td><%=alumPersonal.getCurp()%></td>
	</tr>
	<tr>
		<td style="border-bottom:0;">
			<b><spring:message code="aca.Nacionalidad"/>:</b> 
		</td>
		<td style="border-bottom:0;">
			<%=nacionalidad%>
		</td>
	</tr>
	<tr>
		<td>
			<b>Complete Report:</b>
		</td>
		<td>
			<a href="reporte" class="btn btn-primary"><i></i>Report</a>
			<a href="eventos" class="btn btn-primary"><i></i>Events</a>
		</td>
	</tr>
</table>
<%-- <%@ include file= "grafica.jsf" %> --%>
