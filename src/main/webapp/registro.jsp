<%@page import="java.util.List"%>	
<%@page import="aca.plan.spring.MapaPlan"%>

<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Virtual UM</title>	
	<link rel="stylesheet" href="fontawesome/css/font-awesome.min.css">
	<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.css" type='text/css'>
	<link rel="stylesheet" href="bootstrap4/css/bootstrap.min.css">	
	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/popper.min.js"></script>
	<script src="bootstrap4/js/bootstrap.min.js"></script>
</head>
<%
	String focus 			= "document.getElementById('Usuario').focus();";	
	String mensaje 			= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
	String correo 			= request.getParameter("Correo")==null?"0":request.getParameter("Correo");
	
	List<MapaPlan> listPlanesRegistro = (List<MapaPlan>) request.getAttribute("listPlanesRegistro");
%>
<body onLoad="<%=focus%>">
<div class="container-fluid">
	<h2>Registro de Ingreso</h2>
	<div class="alert alert-info">Paso 1: Clic en registrar datos. Paso 2: Clic en Login </div>
	<div class="modal" id="myModal" tabindex="-1" role="dialog">
  		<div class="modal-dialog">
		    <div class="modal-content">
		      	<div class="modal-header">
			        <h4 class="modal-title">Registro exitoso</h4>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span >&times;</span>
			        </button>
		      	</div>
		     	<div class="modal-body">
		        	<p>Se envio el codigo de activación al correo <%=correo%></p>
		      	</div>
		      	<div class="modal-footer">
		        	<button type="button" class="btn btn-secondary" data-dismiss="modal">Entendido</button>
		      	</div>
		    </div>
	  	</div>
	</div>
	<div class="modal" id="modalMatricula" tabindex="-1" role="dialog">
  		<div class="modal-dialog">
		    <div class="modal-content">
		      	<div class="modal-header">
			        <h4 class="modal-title">Matrícula</h4>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span >&times;</span>
			        </button>
		      	</div>
		     	<div class="modal-body">
		        	<p>Se envio el número de matrícula al correo <%=correo%></p>
		      	</div>
		      	<div class="modal-footer">
		        	<button type="button" class="btn btn-secondary" data-dismiss="modal">Entendido</button>
		      	</div>
		    </div>
	  	</div>
	</div>
	<div align="center">
		<div class="row">
			<div class="col-6" align="right">
			  	<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalRegistro">
					Registrar datos
				</button>
			</div>
			<div class="col-6" align="left">
			  		<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalActivar">
  						Activar
					</button>
			</div>
		</div>
	</div>
	<div class="modal" id="modalRegistro" tabindex="-1" role="dialog">
  		<div class="modal-dialog modal-lg">
        	<form id="frmRegistro" name="forma" action="grabarRegistro" method="post">
			    <div class="modal-content">
			      	<div class="modal-header">
				        <h4 class="modal-title">Datos</h4>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          <span >&times;</span>
				        </button>
			      	</div>
			     	<div class="modal-body">
						<div class="row">
							<div class="col-4" align="left">
								<label>Nombre</label>
								<input name="Nombre" type="text" class="form-control" required="required">
							</div>
							<div class="col-4" align="left">
								<label>A. Paterno</label>
								<input name="Paterno" type="text" class="form-control" required="required">
							</div>
							<div class="col-4" align="left">
								<label>A. Materno</label>
								<input name="Materno" type="text" class="form-control" required="required">
							</div>
						</div>
						<div class="row">
							<div class="col-4" align="left">
								<label>Email <%=mensaje.equals("2") ? "" : ""%></label>
<%								if(mensaje.equals("2")){%>
								<label style="color: red;">ya existe un registro con este email</label>
<%								}%>
								<input name="Correo" type="text" class="form-control" required="required">
							</div>
							<div class="col-3" align="left">
								<label>Teléfono</label>
								<input name="Telefono" type="text" class="form-control">
							</div>
							<div class="col-5" align="left">
								<label>Plan</label>
								<select name="Plan" class="form-control">
<% 								for(MapaPlan mapaPlan : listPlanesRegistro){%>
									<option value="<%=mapaPlan.getPlanId()%>"><%=mapaPlan.getNombrePlan()%></option>
<% 								}%>
								</select>
							</div>
						</div>
			      	</div>
			      	<div class="modal-footer">
						<button class="btn btn-primary" type="submit">
						    Registrar
					  	</button>
			      	</div>
			    </div>
			</form>	
	  	</div>
	</div>
	<div class="modal" id="modalActivar" tabindex="-1" role="dialog">
  		<div class="modal-dialog">
        	<form id="frmLogin" name="forma" action="activarCuenta" method="post">
			    <div class="modal-content">
			      	<div class="modal-header">
				        <h4 class="modal-title">Activar cuenta</h4>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          <span >&times;</span>
				        </button>
			      	</div>
			     	<div class="modal-body">
						<div class="row">
							<div class="col-12" align="left">
								<label>Email</label>
								<input name="Correo" type="text" class="form-control" required="required">
							</div>
							<div class="col-12" align="left">
								<label>Codigo</label>
								<input name="Codigo" type="password" class="form-control" required="required">
							</div>
							<div class="col-12" align="left">
<%								if(mensaje.equals("3")){%>
								<label style="color: red;">ya existe un registro con este email</label>
<%								}%>
<%								if(mensaje.equals("5")){%>
								<label style="color: red;">Código incorrecto</label>
<%								}%>
								
							</div>
						</div><br>
			      	</div>
			      	<div class="modal-footer">
						<button class="btn btn-primary" type="submit">
						    Activar
					  	</button>
			      	</div>
			    </div>
			</form>
	  	</div>
	</div>
</div>
</body>
	<script type="text/javascript">
<% 	if(mensaje.equals("1")){%>
		window.onload = function() {
			$('#myModal').modal('show');
		};
<%  }

 	if(mensaje.equals("2")){%>
		window.onload = function() {
			$('#modalRegistro').modal('show');
		};
<%  }
 	if(mensaje.equals("3")){%>
		window.onload = function() {
			$('#modalActivar').modal('show');
		};
<%  }
 	if(mensaje.equals("4")){%>
		window.onload = function() {
			$('#modalMatricula').modal('show');
		};
<%  }%>
	</script>
</html>