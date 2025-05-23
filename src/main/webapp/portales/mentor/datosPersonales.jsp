<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.mentores.spring.MentAlumnoDatos"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<head>
</head>
<body>
<%
	String matricula 			= (String) request.getAttribute("matricula");
	String nombreAlumno 		= (String) request.getAttribute("nombreAlumno");
	String nombreCarrera 		= (String) request.getAttribute("nombreCarrera");
	String nombrePais 			= (String) request.getAttribute("nombrePais");
	String fechaVenceFM3 		= (String) request.getAttribute("fechaVenceFM3");
	String nombreTipo 			= (String) request.getAttribute("nombreTipo");
	String nombreReligion 		= (String) request.getAttribute("nombreReligion");
	String mensaje 				= (String) request.getAttribute("mensaje");
	
	AlumPersonal alumPersonal 		= (AlumPersonal) request.getAttribute("alumPersonal");
	AlumPlan alumPlan 				= (AlumPlan) request.getAttribute("alumPlan");
	MentAlumnoDatos mentAlumnoDatos = (MentAlumnoDatos) request.getAttribute("mentAlumnoDatos");
%>
<div class="container-fluid">
	<h2>Personal Data</h2>
	<div class="alert alert-success">
		<a href="portal"><i class="fas fa-arrow-left"></i></a>
	</div>
<% 	if(mensaje.equals("1")){%>
	<div class="alert alert-success">
		Saved
	</div>
<% 	}else if(mensaje.equals("2")){%>
	<div class="alert alert-danger">
		Error saving
<% 	}%>
	<ul class="nav nav-tabs">
  		<li class="nav-item">
    		<a class="nav-link active" aria-current="page" href="#">Personal</a>
  		</li>
  		<li class="nav-item">
    		<a class="nav-link" href="datosDomicilio?matricula=<%=matricula%>">Address</a>
	  	</li>
	  	<li class="nav-item">
		    <a class="nav-link" href="datosResidencia?matricula=<%=matricula%>">Residence</a>
	  	</li>
  		<li class="nav-item">
		     <a class="nav-link" href="datosAcademicos?matricula=<%=matricula%>">Academic</a>
	  	</li>
	</ul><br>
	<form name="forma" action="grabarDatosPersonales" target="_self">
		<input type="hidden" name="matricula" value="<%=matricula%>" />
		<div class="row">
			<div class="col-2">
				<img width="250" src="../../foto?Codigo=<%=matricula%>" />
			</div>
			<div class="col-8">
				<div class="row">
					<div class="col-4">
						<label class="form-label"><b>Name:</b></label>
						<%=nombreAlumno%>
					</div>
					<div class="col-3">
						<label class="form-label"><b>Student ID:</b></label>
						<%=matricula%>
					</div>
					<div class="col-5">
						<label class="form-label"><b>Course:</b></label>
						<%=nombreCarrera%>
					</div>
				</div><br>
				<div class="row">
					<div class="col-1">
						<label class="form-label"><b>Year:</b></label>
						<%=alumPlan.getGrado()%>
					</div>
					<div class="col-3">
						<label class="form-label"><b>Semester:</b></label>
						<%=alumPlan.getCiclo()%>
					</div>
					<div class="col-3">
						<label class="form-label"><b>Nationality:</b></label>
						<%=nombrePais%>
					</div>
					<div class="col-5">
<%-- <%   					if (!alumPersonal.getNacionalidad().equals("91"))out.println("<b>[ Vence FM3: " + fechaVenceFM3 + " ]</b>"); %> --%>
						<label class="form-label"><b>Student Type:</b></label>
						<%=nombreTipo%>
					</div>
				</div><br>
				<div class="row">
					<div class="col-4">
						<label class="form-label"><b>Denomination:</b></label>
						<%=nombreReligion%>
					</div>
					<div class="col-4">
						<label class="form-label"><b>Church attended to:</b></label>
						<input type="text" name="iglesia" value="<%=mentAlumnoDatos.getIglesia()%>" maxlength="95" class="form-control"/>
					</div>
					<div class="col-4">
						<label class="form-label"><b>Sabbath School Class</b></label>
						<input type="text" name="claseEs" value="<%=mentAlumnoDatos.getClaseEs()%>" maxlength="35" class="form-control"/>
					</div>
				</div><br>
				<div class="row">
					<div class="col-4">
						<label class="form-label"><b>Phone</b></label>
						<input type="text" name="telefono" value="<%=alumPersonal.getTelefono()%>" maxlength="35" class="form-control"/>
					</div>
					<div class="col-4">
						<label class="form-label"><b>Email</b></label>
						<input type="text" name="email"	value="<%=alumPersonal.getEmail()%>" maxlength="35" class="form-control"/>
					</div>
				</div>
			</div>
		</div><br>
		<div align="center">
			<button type="submit" class="btn btn-primary">Save</button>
		</div>
	</form>
</div>
</body>