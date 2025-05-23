<html>
<head>
<style type="text/css">
#cuadrotrans{
	position: absolute;
	z-index:999;
}
#tablaLogin{
	position: absolute;
	z-index:1000;
}
.contenido1{
	position: absolute;
	bottom: 200px;
	left: 7px;
}
.contenido2{
	position: absolute;
	bottom: 200px;
	left: 353px;
}
.listaW{
	position: relative;
	float:right;
	z-index: 10000000000;
}
body{
	margin:0scroll:no;
}
html{
	overflow:hidden;
}
</style>

<script type="text/javascript" src="js/prototype.js"></script>
<script type="text/javascript">
	var theWidth, theHeight;
	function inicio(){
		dimensiones();
		Event.observe(window, 'resize', dimensiones);
		document.datos.Usuario.focus();
	}
	
	function dimensiones(){
		if (window.innerWidth){
		  theWidth = window.innerWidth 
		  theHeight = window.innerHeight 
		} 
		else if (document.documentElement && document.documentElement.clientWidth){
		  theWidth = document.documentElement.clientWidth 
		  theHeight = document.documentElement.clientHeight 
		} 
		else if (document.body){
		  theWidth = document.body.clientWidth 
		  theHeight = document.body.clientHeight 
		}

		var cuadrotrans = $("cuadrotrans");
		cuadrotrans.style.left = (theWidth/2 - cuadrotrans.offsetWidth/2)+"px";
		cuadrotrans.style.bottom = "36px";
		
		var tablaLogin = $("tablaLogin");
		tablaLogin.style.left = (theWidth/2 - cuadrotrans.offsetWidth/2)+"px";
		tablaLogin.style.bottom = "38px";
	
	}
</script>
</head>
<body onLoad="dimensiones();" scroll="no" oncontextmenu="return false" ondragstart="return false" onselectstart="return false">
		
	<div style="position: absolute; bottom: 0; left: 0; width: 100%; height: 100%; background:url(imagenes/rellenoVirtual.jpg)0 0; "></div>
	<div style="position: absolute; bottom: 0; left: 0; width: 100%; height: 966px; background:url(imagenes/rellenoVirtual.jpg)0 0; "></div>
	<div style="position: absolute; bottom: 0; left: 0; width: 1278px; height: 966px; background:url(imagenes/fondoVirtual.jpg); "></div>
	
	
	<img id="cuadrotrans" src="imagenes/cuadrosVirtual.png" width="580px" height="480px" />
	<div id="tablaLogin"  align="center" class="login">
		<div class="contenido1">
			<table>
				<tr>
					<td align="center" style="letter-spacing:4; font-family:Verdana;"><font color="white" size="5">Sistema</font></td>
				</tr>
				<tr>
					<td align="center" style="letter-spacing:4; font-family:Verdana;"><font color="white" size="5"><spring:message code="aca.Institucion"/>al</font></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="center"><a href="https://virtual-um.um.edu.mx/financiero/"><img src="imagenes/flechitaVerde.png" width="65" height="65" onmouseover="this.src='imagenes/flechitaVerdeOver.png'" onmouseout="this.src='imagenes/flechitaVerde.png'"/></a></td>
				</tr>
			</table>
		</div>
		<div class="contenido2">
			<table>
				<tr>
					<td align="center" style="letter-spacing:4; font-family:Verdana;"><font color="white" size="5">Gestión</font></td>
				</tr>
				<tr>
					<td align="center" style="letter-spacing:4; font-family:Verdana;"><font color="white" size="5">Educativa</font></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="center" ><a href="https://academico.um.edu.mx/academico"><img src="imagenes/flechita.png" width="65" height="65" onmouseover="this.src='imagenes/flechitaOver.png'" onmouseout="this.src='imagenes/flechita.png'"/></a></td>
				</tr>
			</table>
		</div>
	</div>
<div class="listaW" width="100%">
<table  align="center">
	<tr>
		<td align="right"><font color="white" size="1" face="Verdana">
			<b>Intranet de la Universidad de Montemorelos
			</b><br>
			Todos los derechos reservados.<br>
			Mantenimiento: Dirección de Sistemas.<br>
			</font>
			<br>
			<a href="http://pulso.um.edu.mx/green-locker" target="on_blank">
				<img src="imagenes/textgreenlocker.jpg" width="150px" height="40px" />
			</a>
		</td>
	</tr>
</table>
</div>
<script type="text/javascript">
	inicio();
</script>
</body>
</html>