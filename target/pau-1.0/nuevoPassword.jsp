<%@page import="aca.acceso.spring.AccesoClave"%>

<!doctype html>
<html lang="en">
<%@ include file= "head.jsp"%>
<head>
	<style type="text/css">
		.alert-success{
				margin:0 auto;
	}
			
	@media (min-width: 320px) {
		.alert-success {
				width: 550px;
				margin:0 auto;
				margin-bottom: 50px;
				padding-left: 5px;
				padding-right: 5px;
			}
		}
		
		body{
			background-image: url("fondoRecuperar.jpeg");
			/* Para dejar la imagen de fondo centrada, vertical y horizontalmente */
			background-position: center center;
			/* La imagen se fija en la ventana de visualizaci�n para que la altura de la imagen no supere a la del contenido */
			background-attachment: fixed;
			background-repeat: no-repeat;
			/* La imagen de fondo se reescala autom�ticamente con el cambio del ancho de ventana del navegador */
			background-size: cover;
			
		}
	</style>
  </head>  
<%
	AccesoClave accesoClave		= (AccesoClave) request.getAttribute("accesoClave");	
%>
<body>
<div class="container-fluid">
	<h1 align="center" style="color:white;">Recover Password</h1><br>
	<form name="frmPassword" action="grabarNuevoPassword" method="post">
		<div align="center">
			<div class="card bg-light mb-3" style="max-width: 20rem;">
				<div align="left" class="card-header">
					<h5>
						<span class="badge bg-pill badge bg-dark"></span> Student ID / Employee ID
					</h5>
				</div>
				<div align="left" class="card-body">
					<p style="font-size: 14px">
						<em><u>Instructions</u></em>
					</p>
					<p class="card-text">Enter and confirm your new password</p>
					<input type="hidden" name="Matricula" value=<%=accesoClave.getCodigoPersonal()%>>
					<label class="form-label">Password</label>
					<input class="form-control" id="Clave" name="Clave" type="password" required="required">
					<label class="form-label">Confirm password</label>
					<input class="form-control" id="Confirma" name="Confirma" type="password" required="required" onkeyup="verificar();">
				    <div id="validationServer03Feedback" class="invalid-feedback">
				      Passwords don't match.
				    </div>
				</div>
				<div id="ShowEnviar" style="display: none;" align="left" class="card-footer bg-transparent border-success">
					<a href="javascript:Enviar()" class="btn btn-success">Send</a>
				</div>
			</div>
		</div>
	</form>
</div>
<script type="text/javascript">
	function Enviar(){		
		document.frmPassword.submit();
	}
			
	function verificar(){
		var clave 		= document.getElementById("Clave").value; 
		var confirma 	= document.getElementById("Confirma").value; 
		const element 	= document.getElementById("Confirma");
		
		if(clave === confirma){
			document.getElementById("ShowEnviar").style.display = "block"; 
			element.className = "form-control";
		}else{
			document.getElementById("ShowEnviar").style.display = "none"; 
			element.className = "form-control  is-invalid";
		}
	}
</script>
</body>
</html>

