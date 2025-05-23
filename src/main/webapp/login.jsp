<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Academic Portal</title>
	<link rel="SHORTCUT ICON" href="imagenes/icoAc.png">
	<link href="loginStyle.css" rel="STYLESHEET" type="text/css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap5.1/css/bootstrap.min.css">
	<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>	
	<script src="<%=request.getContextPath()%>/bootstrap5.1/js/bootstrap.min.js"></script>	
	<style type="text/css">
	#fixed-bar {
		bottom: 0;
        height: 240px;
        padding: 13px 0 4px;
        position: fixed;
        width: 100%;
        z-index: 5000;
	}
	</style>
</head>
<%			
	String errorLogin 	= request.getParameter("error")==null?"0":request.getParameter("error");
	String mensaje 		= "-";
	if (errorLogin.equals("1")){ mensaje = "Incorrect Username or Password!";}
%>
<body>	
	<form name="frmLogin" action="<%=request.getContextPath()%>/login" method="post">
		<div class="fondo d-flex flex-column justify-content-between" style="widht:100%; height:100%; margin:0 auto"><br><br>
			<div class="cuadroLogin" style="margin:0 auto; width:350px;">
				<div style="text-align: center;" class="mb-3">
					<img src="imagenes/logoUni.png" height="30px">
				</div>
				Username<br>
				<input class="form-control" id="usuario" name="usuario" maxlength="20" type="text" autocomplete="off" onKeyDown="funcionEnter(event);" tabindex=1>		
				Password<br>		
				<input class="form-control" id="password" name="password" maxlength="40" type="password" autocomplete="off" onKeyDown="funcionEnter(event);" tabindex=2>
<% 				if(errorLogin.equals("1")){%>
					<label style="color: red;"><%=mensaje%></label><br>
<% 				}%>
				<br>
				<button id="entrar" type="submit" class="btn btn-primary">Login</button>&nbsp;&nbsp;			 	
				<a href="recuperarPassword" style="color:black">Forgot your password?</a>
			</div>
			<!--<div id="fixed-bar">
				<div align="center">
					<a target="_blank" href="Agenda2223.pdf">
						<img alt="" src="imagenes/imagenAgenda.png" style="height: 100px;">
					</a>
				</div>-->
				<br>
				<div class = "pb-4"align="center" >			
					 <%-- <a target="_blank" href="pau.png"> --%>
						<img alt="" src="imagenes/pau.png" style="height: 100px;">
					<%-- </a> --%>
				<div class = "pb-4"align="center" >
					<%-- <b>Pacific Adventist University</b>			 --%>
					<!--  <br><a target="_blank" href="AvisoDePrivacidad.pdf">Aviso de privacidad</a> -->
				</div>
			</div>
		</div>
	</form>
</body>
<script>
	document.frmLogin.usuario.focus();
	
	function funcionEnter(evento){ 
		//para IE 
		if (window.event) { 
			if (window.event.keyCode==13){ 
				document.frmLogin.submit();
			} 
		} 
		else{ 
		//Firefox y otros navegadores 
			if (evento){ 
				if(evento.which==13) {
					document.frmLogin.submit();					
				} 
			} 
		} 
	}

	function Entrar(evento){		 
		document.frmLogin.submit();		
	}
</script>
</html>