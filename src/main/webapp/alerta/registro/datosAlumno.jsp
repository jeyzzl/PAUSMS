<%@ include file= "../../con_enoc.jsp" %>
<%@ include file="../../idioma.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:useBean id="AlumnoUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="AlumAcademico" scope="page" class="aca.alumno.AlumAcademico"/>
<jsp:useBean id="AcademicoU" scope="page" class="aca.alumno.AcademicoUtil"/>
<jsp:useBean id="AlumPersonal" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<%
	String matricula 	= request.getParameter("matricula");
	String carrera 		= AlumnoUtil.getCarrera(conEnoc, matricula);
	
	AlumAcademico = AcademicoU.mapeaRegId(conEnoc, matricula);
	AlumPersonal = AlumUtil.mapeaRegId(conEnoc, matricula);
	
	String residencia	="";
	if(AlumAcademico.getResidenciaId().equals("E")) residencia  = "Externo"; else residencia  = "Interno"; 
	
	String genero		= aca.alumno.AlumUtil.getGenero(conEnoc, matricula);
	String edad  = Integer.toString(aca.alumno.AlumUtil.getEdad(conEnoc, matricula));	
%>
	<div class="alert alert-info">
		<strong><spring:message code='aca.Matricula'/>:</strong> <%=matricula %>&nbsp;&nbsp;&nbsp;
		<strong><spring:message code='aca.Carrera'/>:</strong>  <%=carrera %>&nbsp;&nbsp;&nbsp;
		<strong><spring:message code='aca.Residencia'/>:</strong><%=residencia %>&nbsp;&nbsp;&nbsp;
		<strong><spring:message code='aca.Genero'/>:</strong><%=genero %>&nbsp;&nbsp;&nbsp;
		<strong><spring:message code='aca.Edad'/>:</strong><%=edad %>
	</div>
	
<%@ include file= "../../cierra_enoc2.jsf" %>