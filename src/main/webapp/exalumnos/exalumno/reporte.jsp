<%@ page import="java.util.List"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.exa.spring.ExaDireccion"%>
<%@page import="aca.exa.spring.ExaIglesia"%>
<%@page import="aca.exa.spring.ExaCorreo"%>
<%@page import="aca.exa.spring.ExaTelefono"%>
<%@page import="aca.exa.spring.ExaRed"%>
<%@page import="aca.exa.spring.ExaFamilia"%>
<%@page import="aca.exa.spring.ExaEmpleo"%>
<%@page import="aca.exa.spring.ExaEstudio"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguroJquery.jsf" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.util.Date"%>
<% 
	String matricula 	= "";
	String year			= " ";
	boolean anioEgreso  = false;
	if(request.getParameter("codigoAlumno")!=null){
		year		= request.getParameter("Year");
		matricula 	= request.getParameter("codigoAlumno");
		session.setAttribute("codigoAlumno",matricula);
		anioEgreso	= true;
	}else{
		matricula 		= (String) session.getAttribute("codigoAlumno");
	}
	
	AlumPersonal alumPersonal 		= (AlumPersonal)request.getAttribute("alumPersonal");
	ExaDireccion exaDireccion 		= (ExaDireccion)request.getAttribute("exaDireccion");	
	ExaCorreo exaCorreo 			= (ExaCorreo)request.getAttribute("exaCorreo");	
	ExaIglesia exaIglesia 			= (ExaIglesia)request.getAttribute("exaIglesia");
	ExaTelefono exaTelefono			= (ExaTelefono)request.getAttribute("exaTelefono");
	String alumnoNombre 			= (String)request.getAttribute("alumnoNombre");
	String paisNombre 				= (String)request.getAttribute("paisNombre");
	
	List<ExaFamilia> lisFamilias	= (List<ExaFamilia>)request.getAttribute("lisFamilias"); 
	List<ExaEstudio> lisEstudios 	= (List<ExaEstudio>) request.getAttribute("lisEstudios");
	List<ExaEmpleo> lisEmpleos 		= (List<ExaEmpleo>)request.getAttribute("lisEmpleos");	
	List<ExaRed> lisRedes 			= (List<ExaRed>) request.getAttribute("lisRedes");
	List<ExaTelefono> lisTelefonos 	= (List<ExaTelefono>) request.getAttribute("lisTelefonos");
%>
<div class="container-fluid">
<%
	if(anioEgreso){%>
		<table class="goback">
		<tr>
			<td>
				<a class="btn btn-primary " href="../reportes/anioEgreso?Year=<%=year%>">&lsaquo;&lsaquo;<spring:message code="aca.Regresar"/></a>
			</td>
		</tr>
		</table>
<%	}%>
<div class="container-fluid">
	<h2>Reporte Completo</h2>
	<div class="alert alert-info">	
	<a class="btn btn-primary" href="datos"><i></i><spring:message code='aca.Regresar'/></a>
	</div>
	<table class="table table-bordered" style="margin-top:5px;">
	<tr>
		<td align='center' width="129" rowspan="30" nowrap style="border-bottom:0;" >
    		<div id="sombra"><img src="../../foto?Codigo=<%=matricula%>&Tipo=O" width="150"></div>
       </td>
       <td width="30%">
       		<b><spring:message code="aca.Matricula"/>:</b> 
       </td>
       <td><%=matricula%></td>
	</tr>
	<tr>
		<td><b><spring:message code="aca.Nombre"/>:</b></td>
		<td>
			<%=alumnoNombre%>
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
			<b>Fecha de Nacimiento:</b> 
		</td>
		<td><%=alumPersonal.getFNacimiento()%></td>
	</tr>
	<tr>
		<td><b>Curp:</b> </td>
		<td><%=alumPersonal.getCurp() %></td>
	</tr>
	<tr>
		<td style="border-bottom:0;">
			<b><spring:message code="aca.Nacionalidad"/>:</b> 
		</td>
		<td style="border-bottom:0;">
			<%=paisNombre%>
		</td>
	</tr>
	<tr>
		<td>
			<b><spring:message code="aca.Correo"/>:</b>
		</td>
		<td>
			<%=exaCorreo.getCorreo() %>
		</td>
	</tr>
	<tr>
		<td>
			<b>Dirección:</b>
		</td>
		<td>
			<%=exaDireccion.getCiudad() %> <%=exaDireccion.getDireccion() %> <%=exaDireccion.getCp() %>
		</td>
	</tr>
	<%
		for(ExaFamilia fam: lisFamilias){	
	%>
	<tr>
		<td>
			<b>Familia:</b>
		</td>
		<td>	
			<%=fam.getNombre() %>
		</td>
	</tr>
	<% } %>
	<%
		for(ExaEmpleo emp: lisEmpleos){	
	%>
	<tr>
		<td>
			<b>Empleo:</b>
		</td>
		<td>
			<%=emp.getEmpresa() %>	
		</td>
	</tr>
	<% } %>
	<tr>
		<td>
			<b>Iglesia:</b>
		</td>
		<td>
			<%=exaIglesia.getIglesia()%>
		</td>
	</tr>
	
	<%
		for(ExaTelefono tel: lisTelefonos){	
	%>
	<tr><td><b><spring:message code='aca.Telefono'/>:</b></td>
		<td><%=tel.getTelefono()%></td>
	</tr>
	<% } %>
	<%
		for(ExaRed soc: lisRedes){	
	%>
	<tr>
		<td>
			<b>Red Social:</b>
		</td>
		<td>
			<%=soc.getRed() %>	
		</td>
	</tr>
	<% } %>	
	</table>
</div>