<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<title>Iniciar Sesi&oacute;n - Activos</title>
	<link rel="shortcut icon" href="<%=request.getContextPath()%>/imagenes/icon.png">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css">
<%
	String errorLogin 		= request.getParameter("error")==null?"0":request.getParameter("error");
	String mensaje 			= "";
	if (errorLogin.equals("1")){ mensaje = " ¡Usuario o contraseña incorrecta!";}
%>
</head>
<body>
	<div class="logo">
		<h1>Sistema de Activos</h1>
	</div>
	<div class="well-white">
        <div class="page-header">
            <h1>¡Bienvenido!<small class="text-muted"><%=mensaje%></small></h1>
        </div>
        <form name="frmLogin" action="loginActivos" method="post">        
        	<p>
        		<div class="control-group">
        			<input id="usuario" name="usuario" type="text" placeholder="Usuario">
        		</div>
        	</p>        	
        	<p>
        		<div class="control-group">
        			<input id="password" name="password" type="password" placeholder="Password">
        		</div>
			</p>			
			<p>
				<span class="loginError"></span>
			</p>			
			<p>
        		<button id="entrar" class="btn btn-success">Iniciar Sesi&oacute;n</button>&nbsp;
        		<a href="javascript:Entrar();" class="btn btn-success">Entrar</a> 
        	</p>
        </form>
    </div>
    <div class="footer">
		<a target="_blank" href="https://virtual-um.um.edu.mx/academico/AvisoDePrivacidad.pdf" style="color: #D8D8D8;">Aviso de privacidad</a>
	</div>
</body>
<script>
	function Entrar(evento){
		alert("Entre a funcion:"+document.frmLogin.action+":"+document.frmLogin.usuario.value+":"+document.frmLogin.password.value);
		document.frmLogin.submit();		
	}
</script>
</html>