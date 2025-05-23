<%@ page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="../alumno/menu.jsp"%>

<head></head>
<%
	AlumPersonal alumPersonal = (AlumPersonal) request.getAttribute("alumPersonal");
%>
<body>
<div class="container-fluid">
	<h3>Formatos para solicitar externado</h3>
</div>
<div class="container-fluid mt-1">
	<div class="alert alert-success">
		<a href="previos"><i class="fas fa-arrow-left"></i></a>&nbsp;
		<span class="badge rounded-pill bg-dark">1D</span>
		Asuntos previos - Residencia <small class="text-muted">( <%=alumPersonal.getCodigoPersonal()%> - <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno()%> <%=alumPersonal.getApellidoMaterno()%> )</small>
	</div>
</div>
<div class="container-fluid">
	<a href="img/residenciaMayores24.pdf" target="_blank"><i class="fas file-alt-pdf-o fa-1x" ></i></a> Estudiantes mayores de 24 años &nbsp;&nbsp;&nbsp;
	<a href="img/residenciaConLicenciatura.pdf" target="_blank"><i class="fas file-alt-pdf-o fa-1x" ></i></a> Con licenciatura concluida&nbsp;&nbsp;&nbsp;
	<a href="img/residenciaPadreFamiliar.pdf" target="_blank"><i class="fas file-alt-pdf-o fa-1x" ></i></a> Con padres o familiares&nbsp;&nbsp;&nbsp;
	<a href="img/residenciaCasados.pdf" target="_blank"><i class="fas file-alt-pdf-o fa-1x" ></i></a> Estudiantes casados&nbsp;&nbsp;&nbsp;
	<a href="img/residenciaEmpleado.pdf" target="_blank"><i class="fas file-alt-pdf-o fa-1x" ></i></a> Responsabilidad del empleado
</div>
</body>