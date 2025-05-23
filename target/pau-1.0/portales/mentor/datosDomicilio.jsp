<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.alumno.spring.AlumUbicacion"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatEstado"%>
<%@page import="aca.catalogo.spring.CatCiudad"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<head>
</head>
<body>
<%
	String matricula 			= (String) request.getAttribute("matricula");
	String mensaje 				= (String) request.getAttribute("mensaje");
	String paisId 				= (String) request.getAttribute("paisId");
	String estadoId 			= (String) request.getAttribute("estadoId");
	String ciudadId 			= (String) request.getAttribute("ciudadId");
	String edoCd 				= (String) request.getAttribute("edoCd");
	String edo 					= (String) request.getAttribute("edo");
	String cd 					= (String) request.getAttribute("cd");

	AlumUbicacion alumUbicacion		= (AlumUbicacion) request.getAttribute("alumUbicacion");
	List<CatPais> paises 			= (List<CatPais>) request.getAttribute("paises");
	List<CatEstado> estados			= (List<CatEstado>) request.getAttribute("estados");
	List<CatCiudad> ciudades		= (List<CatCiudad>) request.getAttribute("ciudades");
	
	String comunica = alumUbicacion.gettComunica() == null ? "" : alumUbicacion.gettComunica();
%>
<div class="container-fluid">
	<h2>Address Data</h2>
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
	</div>
<% 	}%>
	<ul class="nav nav-tabs">
  		<li class="nav-item">
<!--     		<a class="nav-link" href="datosPersonales">Personales</a> -->
    		<a class="nav-link" href="mentor_opciones?matricula=<%=matricula%>">Personal</a>
  		</li>
  		<li class="nav-item">
    		<a class="nav-link active" aria-current="page" href="#">Address</a>
	  	</li>
	  	<li class="nav-item">
		    <a class="nav-link" href="datosResidencia?matricula=<%=matricula%>">Residence</a>
	  	</li>
  		<li class="nav-item">
		     <a class="nav-link" href="datosAcademicos?matricula=<%=matricula%>">Academic</a>
	  	</li>
	</ul><br>
	<form name="forma" action="grabarDatosDomicilio">
		<input type="hidden" id="matricula" name="matricula" value="<%=matricula%>" />
		<div class="row">
			<div class="col-4">
				<label class="form-label"><b>Address</b></label>
				<input name="TDireccion" type="text" id="TDireccion" value="<%=alumUbicacion.gettDireccion()%>" size="35" maxlength="60" class="form-control">
			</div>
			<div class="col-4">
				<label class="form-label"><b>Reference</b></label>
				<input name="TApartado" type="text" id="TApartado" value="<%=alumUbicacion.gettApartado()%>" size="8" maxlength="20" class="form-control">
			</div>
			<div class="col-4">
				<label class="form-label"><b>Neighborhood</b></label>
				<input name="TColonia" type="text" id="TColonia" value="<%=alumUbicacion.gettColonia()%>" size="20" maxlength="30" class="form-control">
			</div>
		</div><br>
		<div class="row">
			<div class="col-4">
				<label class="form-label"><b>Country:</b></label>
				<select id="nacPais" name="nacPais" onchange="javaSript:muestraEstados(nacPais);" class="form-select">
					<option value="0" Selected>Choose Country</option>
<%				for (CatPais pais : paises) {%>
					<option value="<%=pais.getPaisId()%>" <%=pais.getPaisId().equals(paisId) ? "Selected" : ""%>><%=pais.getNombrePais()%></option>
<%				}%>
				</select>
			</div>
<%		if (paisId.equals("153")) {%>
			<div class="col-4">
				<label class="form-label"><b>Province:</b></label>
				<select id="nacEdo" name="nacEdo" onchange="muestraCiudades('nacCiudad', $('nacPais').value, $(nacEdo).value);" class="form-select">
					<option value="0" SELECTED>Select Province</option>
<%				for (CatEstado estado : estados) {%>
					<option value="<%=estado.getEstadoId()%>" <%=estado.getEstadoId().equals(estadoId) ? "Selected" : ""%>><%=estado.getNombreEstado()%></option>
<%				}%>
				</select>
			</div>
			<div class="col-4">
				<label class="form-label"><b>Village:</b></label>
				<select id="nacCiudad" name="nacCiudad" class="form-select">
					<option value="0" SELECTED>Select Village</option>
<%				for (CatCiudad ciudad : ciudades) {%>
					<option value="<%=ciudad.getCiudadId()%>" <%=ciudad.getCiudadId().equals(ciudadId) ? "Selected" : ""%>><%=ciudad.getNombreCiudad()%></option>
<%				}%>
				</select>
			</div>
<%		}%>
		</div><br>
<%		if (!paisId.equals("153")) {%>
		<div class="row">
			<div class="col-6">
				<label class="form-label"><b>Province/State:</b></label>
				<input name="otroEstado" type="text" id="otroEstado" size="31" maxlength="40" value="<%=edo%>" class="form-control">
			</div>
			<div class="col-6">
				<label class="form-label"><b>Village/City:</b></label>
				<input name="otraCiudad" type="text" id="otraCiudad" size="31" maxlength="40" value="<%=cd%>" class="form-control">
			</div>
		</div><br>
<%		}%>
		<div class="row">
			<div class="col-2">
				<label class="form-label"><b>ZIP Code:</b></label>
				<input name="TCodigo" type="text" id="TCodigo" value="<%=alumUbicacion.gettCodigo()%>" size="10" maxlength="10" class="form-control">
			</div>
			<div class="col-4">
				<label class="form-label"><b>Email:</b></label>
				<input name="TEmail" type="text" id="TEmail" value="<%=alumUbicacion.gettEmail()%>" size="30" maxlength="50" class="form-control">
			</div>
			<div class="col-3">
				<label class="form-label"><b>Phone:</b></label>
				<input name="TTelefono" type="text" id="TTelefono" value="<%=alumUbicacion.gettTelefono()%>" size="20" maxlength="20" class="form-control">
			</div>
			<div class="col-3">
				<label class="form-label"><b>Cell phone:</b></label>
				<input name="TCelular" type="text" id="TCelular" value="<%=alumUbicacion.gettCelular()%>" size="20" maxlength="20" class="form-control">
			</div>
		</div><br>
		<div class="row">
			<div class="col-3">
				<label class="form-label"><b>Preferred Contact Method:</b></label>
				<select id="TComunica" name="TComunica" class="form-select">
					<option value="0" SELECTED>Select method</option>
					<option value="C" <%=comunica.equals("C") ? "Selected" : ""%>>Letter</option>
					<option value="E" <%=comunica.equals("E") ? "Selected" : ""%>>Email</option>
					<option value="T" <%=comunica.equals("T") ? "Selected" : ""%>>Phone</option>
					<option value="L" <%=comunica.equals("L") ? "Selected" : ""%>>Cell Phone</option>
				</select>
			</div>
		</div>
		<div align="center">
			<button type="submit" class="btn btn-primary">Save</button>
		</div>
	</form>
</div>
<script type="text/javascript">
	function muestraEstados(paisId){
		var paisId = paisId.value;
		var matricula = document.getElementById("matricula").value;
		
		location.href = "datosDomicilio?matricula="+matricula+"&nacPais="+paisId;
	}
	
	function muestraCiudades(estadoId){
		var estadoId 	= nacEdo.value;
		var paisId 		= document.getElementById("nacPais").value;
		var matricula 	= document.getElementById("matricula").value;
		
		location.href = "datosDomicilio?matricula="+matricula+"&nacPais="+paisId+"&nacEdo="+estadoId;
	}
</script>
</body>