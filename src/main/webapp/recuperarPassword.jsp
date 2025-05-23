<!doctype html>
<html lang="en">
  <head>
   	<title>Recover</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

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
	String mensaje 		= (String) request.getAttribute("mensaje");	
%>
  <body>
	<div class="container-fluid">
		<h1 align="center" style="color:white;">Recover Password</h1><br>
		<form id="formMatricula" name="formMatricula" action="enviaLink" method="POST">
			<div align="center">
				<div class="card bg-light mb-3" style="max-width: 20rem;">
					<div align="left" class="card-header">
						<h5>
							<span class="badge bg-pill badge bg-dark"></span> Student ID # / Employee ID #
						</h5>
					</div>
					<div align="left" class="card-body">
						<p style="font-size: 14px">
							<em><u>Instructions:</u></em>
						</p>
						<p class="card-text">Enter your Student or Employee ID, click on the button below and we will send you a recovery url address. If your email is not up to date, please contact the IT department.</p>
						<input class="form-control" name="Matricula" placeholder="Student or Employee ID Number" required="required">
					</div>
					<div align="left" class="card-footer bg-transparent border-success">
						<button class="btn btn-success" onclick="enviar();">Send</button>
					</div>
				</div>
			</div>
		</form>
<% 		if(mensaje.equals("1")){%>
		<div class="alert alert-success" role="alert">
			<h5>All Set!</h5>
			<p>We have sent you a link to the email address you have registered in your account.</p>
		</div>
<% 		}else if(mensaje.equals("2")){%>
		<div class="alert alert-danger" role="alert">
			<h3>This email is not valid!</h3>
			<p>There was an issue upon sending to the email.</p>
			<p>Please contact the respective IT department. </p>
		</div>
<% 		}else if(mensaje.equals("3")){%>
		<div class="alert alert-danger" role="alert">
			<h5>Expired Access!</h5>
			<h5>This link is no longer valid, please generate a new link.</h5>
		</div>
<% 		}else if(mensaje.equals("4")){%>
		<div class="alert alert-danger" role="alert">
			<h5>Different Passwords!</h5>
			<h5>The passwords you have registered do not match.</h5>
		</div>
<% 		}else if(mensaje.equals("5")){%>
		<div class="alert alert-danger" role="alert">
			<h3>Error sending email</h3>
			<p>There was an issue upon sending to the email.</p>
			<p>Please contact the respective IT department. </p>
		</div>
<% 		}%>

	</div>

	<script type="text/javascript">
		function enviar(){
			document.getElementById("formMatricula").submit(); 
		}
		
		function verificar(){
			document.getElementById("formCodigo").submit(); 
		}
	</script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
  </body>
</html>

