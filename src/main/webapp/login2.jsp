<!DOCTYPE html> 
<html>
<head>
<script src="https://apis.google.com/js/platform.js" async defer></script>
<meta name="google-signin-client_id" content="65151421968-02p2kc8h5t02ldaojiflteb8k1buaqtk.apps.googleusercontent.com">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Virtual UM</title>
<link rel="SHORTCUT ICON" href="imagenes/icoAc.png">
<link href="loginStyle.css" rel="STYLESHEET" type="text/css">
<%
	String focus = "document.getElementById('Usuario').focus();";
	if(session.getAttribute("usuario")!=null)response.sendRedirect("inicio");
	String refrescar = request.getParameter("refrescar")==null?"":request.getParameter("refrescar");
%>

<script type="text/javascript" src="js/prototype-1.6.js"></script>
<script type="text/javascript">
//Mobile Detection and Redirecting
var device = navigator.userAgent

if (device.match(/Iphone/), device.match(/Ipod/), device.match(/Ipad/), device.match(/Android/), device.match(/J2ME/), device.match(/HTC/)) {
window.location = "AQUI DIRECCION WEB MOVIL";
}
else
{

}
//
</script>
<%
boolean movil = false;
String ua	= request.getHeader("User-Agent").toLowerCase();
if(ua.matches(".*(android.+mobile|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|symbian|treo|up\\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino).*")||ua.substring(0,4).matches("1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|e\\-|e\\/|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(di|rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|xda(\\-|2|g)|yas\\-|your|zeto|zte\\-")) {
	movil = true;
%>
	<style type="text/css">
		body{
			background: #D1E2F4;
		}
		
		.cuadroLogin{
			padding: 2px 10px 2px 10px;
		}
		.cuadroLogin td{
			text-align:left;
		}
		.input{
			height:25px;
			width:160px;
		}
		.fondo{
			background: url(movil/portalAlumno/imagenes/bg.png);
		}
		.button, .button:hover{
		
			height:28px;
			 display: inline-block;
			  *display: inline;
			  /* IE7 inline-block hack */
			
			  *zoom: 1;
			  padding: 4px 10px 4px;
			  margin-bottom: 0;
			  font-size: 13px;
			  line-height: 18px;
			  color: #333333;
			  text-align: center;
			  text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
			  vertical-align: middle;
			  background-color: #f5f5f5;
			  background-image: -moz-linear-gradient(top, #ffffff, #e6e6e6);
			  background-image: -ms-linear-gradient(top, #ffffff, #e6e6e6);
			  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#ffffff), to(#e6e6e6));
			  background-image: -webkit-linear-gradient(top, #ffffff, #e6e6e6);
			  background-image: -o-linear-gradient(top, #ffffff, #e6e6e6);
			  background-image: linear-gradient(top, #ffffff, #e6e6e6);
			  background-repeat: repeat-x;
			  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffff', endColorstr='#e6e6e6', GradientType=0);
			  border-color: #e6e6e6 #e6e6e6 #bfbfbf;
			  border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
			  filter: progid:dximagetransform.microsoft.gradient(enabled=false);
			  border: 1px solid #cccccc;
			  border-bottom-color: #b3b3b3;
			  -webkit-border-radius: 4px;
			  -moz-border-radius: 4px;
			  border-radius: 4px;
			  -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
			  -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
			  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
			  cursor: pointer;
			  *margin-left: .3em;
			  
			   background-color: #0074cc;
			  background-image: -moz-linear-gradient(top, #0088cc, #0055cc);
			  background-image: -ms-linear-gradient(top, #0088cc, #0055cc);
			  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#0088cc), to(#0055cc));
			  background-image: -webkit-linear-gradient(top, #0088cc, #0055cc);
			  background-image: -o-linear-gradient(top, #0088cc, #0055cc);
			  background-image: linear-gradient(top, #0088cc, #0055cc);
			  background-repeat: repeat-x;
			  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#0088cc', endColorstr='#0055cc', GradientType=0);
			  border-color: #0055cc #0055cc #003580;
			  border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
			  filter: progid:dximagetransform.microsoft.gradient(enabled=false);
			  
			  color:white;
	
		}		
	</style>

<%
}
%>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-57675745-1', 'auto');
  ga('send', 'pageview');

</script>

</head>
<body onLoad="<%=focus%>" scroll="no" oncontextmenu="return false" ondragstart="return false" onselectstart="return false">

<div class="greenLocker" width="50%">
<table style="margin: 0 auto;">
	<tr>
		<td align="right">
<!-- 		  <font color="white" size="1" face="Verdana"> -->
<!-- 			<a href="http://pulso.um.edu.mx/green-locker" target="on_blank"> <img src="imagenes/textgreenlocker.jpg" width="150px" height="40px" /></a> -->
<!-- 		  </font>	 -->
			<img src="imagenes/questionPro.png" height="30px"/>&nbsp;&nbsp;
			</br>
			<a href="https://www.questionpro.com/es" target="_blank">Encuestas Online</a>&nbsp;&nbsp;
		</td>
	</tr>
</table>
</div>


<form id="forma" name="forma" action="login.jsp" method="post">
<div class="fondo">
<!--  <div class="g-signin2" data-onsuccess="onSignIn" width="50%"></div>-->

	<table class="tb" widht="100%" height="100%" align="center">
		<tr>
			<td height="30px">
			</td>
		</tr>
		<tr>
			<td valign="middle" align="left">
				
				<table widht="100%" height="100px" align="center" class="cuadroLogin">
					<tr>
						<td colspan="2" class="um" valign="bottom"><img src="imagenes/soloLogo.png" height="30px"></td>
					</tr>
					<tr>
						<td align="right">Usuario &nbsp;&nbsp;</td>
					<%if(movil)out.print("</tr><tr>"); %>
						<td><input onKeyDown="funcionEnter(event);" class="input" id="Usuario" name="Usuario" maxlength="20" type="text" tabindex=1></td>
					</tr>
					<tr><td style="height:14px;"></td></tr>
					
					<tr>
						<td align="right">Contraseña &nbsp;&nbsp;</td>
					<%if(movil)out.print("</tr><tr>"); %>
						<td><input onKeyDown="funcionEnter(event);" class="input" id="Clave" name="Clave" maxlength="40" type="password" tabindex=2></td>
					</tr>
					<tr><td style="height:14px;"></td></tr>
					<tr>
						<td align="left" colspan="2">
							<p id="txt" valign="middle" style=" letter-spacing:1px;"></p>
							<input onclick="validar();" class="button" type="button" value="Entrar"><br>&nbsp;
						</td>
						<td align="left" colspan="2">
							<p id="txt" valign="middle" style=" letter-spacing:1px;"></p>
							
						</td>
					</tr>					
				</table>
				<table widht="100%" height="50px">
					<tr>
						<td>
							
						</td>
					</tr>
				</table>
				
				<div style="height:15px;">
				</div>
			</td>
		</tr>
		<tr>
			<td height="30px" align="center">
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
	<META HTTP-EQUIV="Refresh" CONTENT="0; URL=login.jsp">;
<%
	}
%>

<script>

function onSignIn(googleUser) {
	  var profile = googleUser.getBasicProfile();
	  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
	  console.log('Name: ' + profile.getName());
	  console.log('Image URL: ' + profile.getImageUrl());
	  console.log('Email: ' + profile.getEmail());
	}
</script>
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
	
	jQuery.preloadImages = function(){
			for(var i = 0; i<arguments.length; i++){
			jQuery("<img>").attr("src", arguments[i]);
			}
	}
	$.preloadImages("imagenes/loading.gif");
</script>
</body>
</html>