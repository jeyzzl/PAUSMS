<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.residencia.spring.ResDatos"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<head>
</head>
<body>
<%
	String matricula 	= (String) request.getAttribute("matricula");
	String cuarto 		= (String) request.getAttribute("cuarto");
	int comidas 		= (int) request.getAttribute("comidas");
	String nombreRazon 	= (String) request.getAttribute("nombreRazon");

	AlumAcademico alumAcademico	= (AlumAcademico) request.getAttribute("alumAcademico");
	ResDatos datos				= (ResDatos) request.getAttribute("datos");
	
%>
<div class="container-fluid">
	<h2>Residence Data</h2>
	<div class="alert alert-success">
		<a href="portal"><i class="fas fa-arrow-left"></i></a>
	</div>
	<ul class="nav nav-tabs">
  		<li class="nav-item">
<!--     		<a class="nav-link" href="datosPersonales">Personales</a> -->
    		<a class="nav-link" href="mentor_opciones?matricula=<%=matricula%>">Personal</a>
  		</li>
  		<li class="nav-item">
    		<a class="nav-link" href="datosDomicilio?matricula=<%=matricula%>">Address</a>
	  	</li>
	  	<li class="nav-item">
		     <a class="nav-link active" href="#">Residence</a>
	  	</li>
  		<li class="nav-item">
		    <a class="nav-link" href="datosAcademicos?matricula=<%=matricula%>">Academic</a>
	  	</li>
	</ul><br>
	<div class="row">
		<div class="col-12">
			<label class="form-label"><b>Residence:</b> <%=alumAcademico.getResidenciaId().equals("E") ? "Day Student" : "Boarding"%></label>
		</div>
	</div>
<%	if (alumAcademico.getResidenciaId().equals("I")) {%>
	<div class="row">
		<div class="col-12">
			<label class="form-label"><b>Dormitory:</b></label>
			<%="D - " + alumAcademico.getDormitorio()%>
		</div><br>
		<div class="col-12">
			<label class="form-label"><b>Room:</b></label>
			<%="No." + " " + cuarto%>
		</div><br>
		<div class="col-12">
			<label class="form-label"><b>Meals:</b></label>
			<%=comidas + " " + "meals"%>
		</div><br>
	</div><br>
<%	} else {%>
	<div class="row">
		<div class="col-12">
			<label class="form-label"><b>Reason:</b></label>
			<%=nombreRazon%>
		</div><br>
		<div class="col-12">
			<label class="form-label"><b>Lives with:</b></label>
			<%=datos.getNombreTut() + " " + datos.getApellidoTut()%>
		</div><br>
		<div class="col-12">
			<label class="form-label"><b>Address:</b></label>
			<%=datos.getColonia() + " " + datos.getCalle()%>
		</div><br>
		<div class="col-12">
			<label class="form-label"><b>Phone:</b></label>
			<%=datos.getTelNum()%>
		</div><br>
	</div><br>
<%	}%>
</div>
</body>