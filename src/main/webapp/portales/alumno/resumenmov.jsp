<%@ page import="java.util.Date,java.text.SimpleDateFormat,java.text.DecimalFormat" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.vista.Inscritos"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumAcademico"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%		
	String codigoPersonal 			= (String) session.getAttribute("codigoPersonal");	
	String codigoSesion 			= (String) session.getAttribute("codigoLogeado");
	String codigoAlumno 			= session.getAttribute("codigoAlumno")==null?"0":(String)session.getAttribute("codigoAlumno");
	boolean esInscrito 				= (boolean) request.getAttribute("esInscrito");
	
	AlumPersonal alumPersonal 		= (AlumPersonal) request.getAttribute("alumPersonal");
	AlumAcademico alumAcademico 	= (AlumAcademico) request.getAttribute("alumAcademico");
	String estadoCivil 				= "Soltero";
	if (alumPersonal.getEstadoCivil().equals("C")) estadoCivil = "Casado";
	if (alumPersonal.getEstadoCivil().equals("V")) estadoCivil = "Viudo";
	if (alumPersonal.getEstadoCivil().equals("D")) estadoCivil = "Divorciado";	
%>
<%@ include file= "menumov.jsp" %>
<html>
<head>
<style type="text/css">	
	.alert-alto-m{	height:35px; }	
</style>
</head>
<body>
<div class="container-fluid">
	<div class="row d-flex mt-1">
		<div class="col-6">
			<img class="rounded border border-dark" src="../../foto?Codigo=<%=codigoAlumno%>&Tipo=O" width="100%">
		</div>
		<div class="col-6 d-flex justify-content-center align-items-center">
			<a href="modificarMovil?Movil=N" class="badge badge-primary"><i class="fa fa-mobile fa-5x" aria-hidden="true"></i></a>
		</div>		
	</div>
	<div class="row border">
		<div class="col-3"><p class="font20"><spring:message code="aca.Matricula"/>:</p></div>
		<div class="col-9"><p class="font20"><%=alumPersonal.getCodigoPersonal()%></p></div>		
	</div>
	<div class="row border">
		<div class="col-3"><p class="font20"><spring:message code="aca.Nombre"/>:</p></div>
		<div class="col-9"><p class="font20"><%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno()%> <%=alumPersonal.getApellidoMaterno()%></p></div>
	</div>
	<div class="row border">
		<div class="col-3"><p class="font20"><spring:message code="aca.Inscrito"/>:</p></div>
		<div class="col-9"><p class="font20"><%=esInscrito?"Inscrito":"NO Inscrito"%></p></div>		
	</div>	
	<div class="row border">
		<div class="col-3"><p class="font20"><spring:message code="aca.Modalidad"/>:</p></div>
		<div class="col-9"><p class="font20"><%=alumAcademico.getModalidadId()%></p></div>		
	</div>	
	<div class="row border mt-2">
		<div class="col-3"><p class="font20"><spring:message code="aca.EstadoCivil"/>:</p></div>
		<div class="col-9"><p class="font20"><%=estadoCivil%></p></div>
	</div>
	<div class="row border">
		<div class="col-3"><p class="font20"><spring:message code="aca.Genero"/>:</p></div>
		<div class="col-9"><p class="font20"><%=alumPersonal.getSexo().equals("M")?"Masculino":"Femenino"%></p></div>
	</div>
	<div class="row border">
		<div class="col-3"><p class="font20"><spring:message code="aca.Fecha"/> <spring:message code="aca.Nacimiento"/>:</p></div>
		<div class="col-9"><p class="font20"><%=alumPersonal.getFNacimiento()%></p></div>
	</div>
	<div class="row border">
		<div class="col-3"><p class="font20"><spring:message code="aca.Telefono"/>:</p></div>
		<div class="col-9"><p class="font20"><%=alumPersonal.getTelefono()%></p></div>
	</div>	
	<div class="row border">
		<div class="col-3"><p class="font20"><spring:message code="aca.Email"/>:</p></div>
		<div class="col-9"><p class="font20"><%=alumPersonal.getEmail()%></p></div>
	</div>
</div>
</body>
</html>