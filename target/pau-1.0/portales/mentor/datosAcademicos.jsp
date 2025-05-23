<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.vista.spring.PlanCiclo"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<head>
</head>
<body>
<%
	String matricula 	= (String) request.getAttribute("matricula");
	int totalM 			= (int) request.getAttribute("totalM");
	int totalMR 		= (int) request.getAttribute("totalMR");
	String ponderado 	= (String) request.getAttribute("ponderado");
	float avance 		= (float) request.getAttribute("avance");
	String infoCiclos 	= (String) request.getAttribute("infoCiclos");
	
%>
<div class="container-fluid">
	<h2>Academic Background</h2>
	<div class="alert alert-success">
		<a href="portal"><i class="fas fa-arrow-left"></i></a>
	</div>
	<ul class="nav nav-tabs">
  		<li class="nav-item">
<!--     		<a class="nav-link" href="datosPersonales">Personales</a> -->
    		<a class="nav-link" href="mentor_opciones?matricula=<%=matricula%>">Personal</a>
  		</li>
  		<li class="nav-item">
    		<a class="nav-link" aria-current="page" href="datosResidencia?matricula=<%=matricula%>">Address</a>
	  	</li>
	  	<li class="nav-item">
		    <a class="nav-link" href="datosResidencia?matricula=<%=matricula%>">Residence</a>
	  	</li>
  		<li class="nav-item">
		    <a class="nav-link active" href="#">Academic</a>
	  	</li>
	</ul><br>
	<div class="row">
		<div class="col-3">
			<label class="form-label"><b>Credited Subjects:</b></label>
			<%=totalM%>
		</div>
		<div class="col-3">
			<label class="form-label"><b>Failed Subjects:</b></label>
			<%=totalMR%>
		</div>
		<div class="col-3">
			<label class="form-label"><b>Weighted Average:</b></label>
			<%=ponderado%>
		</div>
		<div class="col-3">
			<label class="form-label"><b>Progress for Graduation:</b></label>
			<%=avance%> 
		</div>
	</div><br>
	<div class="row">
		<div class="col-10">
			<label class="form-label"><b>Pending Subjects:</b></label>
			<%=infoCiclos%> 
		</div>
		<div class="col-2">
 			<a href="mat_pendientes?matricula=<%=matricula%>" class="btn btn-primary"> <b>See Details</b></a>
		</div>
	</div>
</div>
</body>