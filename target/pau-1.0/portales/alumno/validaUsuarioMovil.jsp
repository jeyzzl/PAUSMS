<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.*"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="../alumno/menu.jsp"%>

<head></head>
<%
	AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");
	String codigoAlumno 		= (String) request.getAttribute("codigoAlumno");
	String mensaje		 		= (String) request.getAttribute("mensaje");
	
	boolean existeCodigo 		= (boolean) request.getAttribute("existeCodigo");
%>
<div class="container-fluid">	
	<div class="alert alert-info">		
		<h2><a href="resumen"><i class="fas fa-arrow-left"></i></a>&nbsp;<%=alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno()%><small class="text-muted fs-5"> ( <%=codigoAlumno%> )</small></h2>
	</div>
<% 	if(mensaje.equals("1")){%>
	<div class="alert alert-danger" role="alert">
		<h3>Ocurrio un error. No se pudo registrar tu cuenta.</h3>
	</div>
<% 	}%>
<% 	if(mensaje.equals("2")){%>
	<div class="alert alert-danger" role="alert">
		<h3>Ocurrio un error. No se pudo grabar tu nueva contraseña.</h3>
	</div>
<% 	}%>
<% 	if(mensaje.equals("3")){%>
	<div class="alert alert-success" role="alert">
		<h3>Se grabo correctamente.</h3>
	</div>
<% 	}%>
	<div class="row">
		<div class="col-12">
<% 		if(!existeCodigo){%>
			<form action="registrarUsuarioMovil" method="post">
				<div class="alert alert-info" role="alert">
					Registrar cuenta movil  (Minimo 6 caracteres y maximo 12 caracteres)
				</div>
				<input name="Pass" id="password" type="password" placeholder="Contraseña" required="required">
				<input name="Confirm" id="confirm" type="password" placeholder="Confirmación" required="required">
				<br><br>
				<button class="btn btn-primary" type="submit">Registrar</button>
			</form>
<% 		}else{%>
			<div class="alert alert-success" role="alert">
				Tu cuenta móvil ya está registrada
			</div>
<% 		}%>
		</div>
		<div class="col-12">
<% 		if(existeCodigo){%>
			<div class="alert alert-info" role="alert">
				Cambiar contraseña cuenta móvil (Minimo 6 caracteres y maximo 12 caracteres)
			</div>
			<form action="cambiarPasswordMovil" method="post">
				<input name="Pass" id="password" type="password" placeholder="Contraseña" required="required">
				<input name="Confirm" id="confirm" type="password" placeholder="Confirmación" required="required">
				<br><br>
				<button class="btn btn-primary" type="submit">Grabar</button>
			</form>
<% 		}%>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">
	var password = document.getElementById("password")
	var confirm = document.getElementById("confirm");
	
	function validatePassword(){
		if(password.value != confirm.value) {
			confirm.setCustomValidity("La contraseña y la confirmación no coincide");
		} else {
			confirm.setCustomValidity('');
		}
		if(password.value.length < 6 || password.value.length > 12) {
			confirm.setCustomValidity("La contraseña no cumple con la cantidad de caracteres");
		}
	}
	
	password.onchange = validatePassword;
	confirm.onkeyup = validatePassword;
</script>
