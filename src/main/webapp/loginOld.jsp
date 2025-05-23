<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Virtual UM</title>
	<link rel="SHORTCUT ICON" href="imagenes/icoAc.png">
	<link href="loginStyle.css" rel="STYLESHEET" type="text/css">
	<script type="text/javascript" src="js/prototype-1.6.js"></script>	
	<style>	
	.zoom-image-hover
	{
	  max-width:400px;
	  margin: auto;
	}
	.zoom-image-hover img{
	  width:100%;
	  box-shadow: 0 0 8px #000;
	  transition:all 1s;
	  transform: scale(0.9);
	}
	
	.zoom-image-hover a:hover img{
	  box-shadow: 0 0 40px #000;
	  transform: scale(2.5);
	}
</style>
<%
	String focus 		= "document.getElementById('Usuario').focus();";	
	String refrescar 	= request.getParameter("refrescar")==null?"":request.getParameter("refrescar");
	String mensaje 		= request.getAttribute("mensaje")==null?"0":(String)request.getAttribute("mensaje");	
	String email 		= (String) request.getAttribute("email");	
%>
</head>
<body onLoad="<%=focus%>" oncontextmenu="return false" ondragstart="return false">	
	<form id="forma" name="forma" action="loginOld" method="post">
	<div class="fondo">
	<table style="widht:100%; height:100%; margin:0 auto">		
	<tr>		
		<td valign="middle" align="left" width="100%">		
			<table style="margin:0 auto" class="cuadroLogin">
				<tr>
					<td colspan="2" class="um" valign="bottom"><img src="imagenes/soloLogo.png" height="30px"></td>
				</tr>
				<tr>
					<td align="right">Usuario&nbsp;</td>		
					<td><input onKeyDown="funcionEnter(event);" class="input" id="Usuario" name="Usuario" maxlength="20" type="text" tabindex=1></td>
				</tr>
				<tr><td colspan="2" style="height:14px;"></td></tr>					
				<tr>
					<td align="right">Contraseña&nbsp;</td>		
					<td><input onKeyDown="funcionEnter(event);" class="input" id="Clave" name="Clave" maxlength="40" type="password" tabindex=2></td>
				</tr>
				<tr><td colspan="2" style="height:14px;"></td></tr>
				<tr>
					<td colspan="2" align="left">
						<p id="txt" style="letter-spacing:1px; vertical-align:middle;"></p>
						<div>
							<input onclick="validar();" class="button" type="button" value="Entrar"><br>&nbsp;
							<br>
<!-- 							<a target="_blank" href="recuperarContra" id="txt2" style="letter-spacing:1px; vertical-align:middle; text-align:right;"></p> -->
<!-- 			        		<a href="intentaRecuperar" style="color:black">¿Ovidaste tu contraseña?</a> -->
			        		<a href="recuperarPassword" style="color:black">¿Ovidaste tu contraseña?</a>
						</div>
					</td>
				</tr>					
			</table>			
			<div style="height:15px;">&nbsp;</div>
		</td>
	</tr>
<%	if (mensaje.equals("2")){%>
	<tr>
		<td align="right" style="color: red;">No se pudo enviar el correo, comunicate al departamento de  Sistemas a la extensión 1550 o 1551.</td>		
	</tr>
<%	} else if (mensaje.equals("1")){%>
	<tr>
<%-- 		<td align="right">Se envió un correo con tu nueva clave a <%=email%>. Si no usas este correo actualmente llama al departamento de  Sistemas a la extensión 1550 o 1551.</td> --%>
		<td align="right">Tu clave nueva es el código de recuperación. Te recomendamos cambiarlo en cuanto entres.</td>
	</tr>
<%	} %>		
	<tr>		
		<td align="center">
<!-- 			<a target="_blank" href="https://forms.gle/F13cqcThwpQqqWTt6"> -->
<!-- 				<img alt="" src="imagenes/misiones.png" style="height: 150px;"> -->
<!-- 			</a>	 -->
			<a target="_blank" href="Agenda2122.pdf">
				<img alt="" src="imagenes/imagenAgenda.jpg" style="height: 150px;">
			</a>
		</td>
	</tr>
	<tr>		
		<td height="30px" align="center" colspan="2">
			<font size="2">
				<b>Universidad de Montemorelos</b><br>Todos los derechos reservados. <br>Mantenimiento: Dirección de Sistemas.<br><a target="_blank" href="AvisoDePrivacidad.pdf">Aviso de privacidad</a>
			</font>
		</td>
	</tr>
	</table>	
	</div>
	</form>
	<form name="Ruta" method="post" action="inicio">
		<input type="hidden" name="ruta" value="<%=request.getParameter("ruta") %>"/>
	</form>
<%
	if(refrescar.equals("1")){
%>
	<META HTTP-EQUIV="Refresh" CONTENT="0; URL=loginOld.jsp">;
<%
	}
%>
<script>
	
	function validar(){
		
		new Ajax.Request(  
			  "valida?Usuario="+document.getElementById("Usuario").value+"&Clave="+document.getElementById("Clave").value, {  
				   method:'get',
				   parameters:  {timestamp:new Date().getTime()},  
				   
				   onSuccess: function(req){
						eval(req.responseText);
					},
				   
				   onFailure: function(req){
					   error();
					},					
					onLoading:cargando()
			   }  
		);
	}
	
	function funcionEnter(evento){ 
		//para IE 
		if (window.event) { 
			if (window.event.keyCode==13){ 
				validar();
			} 
		} 
		else{ 
		//Firefox y otros navegadores 
			if (evento){ 
				if(evento.which==13) { 
					validar();
				} 
			} 
		} 
	}
	
	function error(){
		document.getElementById("txt").innerHTML="error en la pagina";
	}
	
	function cargando(){
		document.getElementById("txt").innerHTML="<img style='position:relative;top:2px;' height='15px' src=\"imagenes/loading.gif\" /> <font size=2>Un momento porfavor</font> ";
	}
	
	function usuarioIncorrecto(){
		document.getElementById("txt").innerHTML = "<font size=2 color=#FC3E3E>Usuario incorrecto</font>";
		document.getElementById("Usuario").style.border = "1px solid #FC3E3E";
		document.getElementById("Clave").style.border = "0px solid #FC3E3E";
		document.getElementById('Usuario').focus();
	}
	
	function claveIncorrecto(){
		document.getElementById("txt").innerHTML = "<font size=2 color=#FC3E3E>Contraseña incorrecta</font>";
		document.getElementById("Clave").style.border = "1px solid #FC3E3E";
		document.getElementById("Usuario").style.border = "0px solid #FC3E3E";
		document.getElementById("Clave").value = "";
		document.getElementById('Clave').focus();
	}

	function recuperarContra(){
		document.getElementById("txt2").innerHTML=" <font size=2>Recuperar contraseña</font> ";
	}
	
	function camposRequeridos(accion){
		document.getElementById("txt").innerHTML = "<font size=2 color=red>Todos los campos son requeridos</font>";
		if(accion=='1'){
			document.getElementById("usuario").innerHTML = "<font size=4 color=red>*</font>";
			document.getElementById('Usuario').focus();
		}else{
			document.getElementById("clave").innerHTML = "<font size=4 color=red>*</font>";
			document.getElementById('Clave').focus();
		}
	}
	
	function entrar(){
		document.Ruta.submit();
	}

	function privacidad(){
		document.location.href = "avisoPrivacidad";
	}
	
	function cambiaClave(){
		document.location.href = "configuracion?Primera=1";
	}

	function cambiarPassword(){
		document.location.href = "cambiarPassword";
	}
	
	jQuery.preloadImages = function(){
			for(var i = 0; i<arguments.length; i++){
			jQuery("<img>").attr("src", arguments[i]);
			}
	}
	$.preloadImages("imagenes/loading.gif");
</script>
</body>
</html>