
<jsp:useBean id="colorAlum" scope="page" class="aca.portal.Alumno"/>
<%
	String sCodigoPersonal 	= (String)session.getAttribute("codigoPersonal");
	
	String colorTab = (String)session.getAttribute("colorMenu");

	
	if(colorTab.equals("default") || colorTab.equals("")){
		colorTab="#51308B";
	}
	String	color1 = colorAlum.modificarColor(colorTab, 90);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
  <title><spring:message code='aca.SistemaAcademico'/></title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style type="text/css">
	.input{
		 color:white;
		text-shadow: 0 1px 0 black;
		
	}
	html,body { width:100%; height:100%; padding:0px; margin:0px; overflow: hidden;}
	.esconderEncabezado{
		position: absolute;
		bottom: -1px;
		right: 19px;
		z-index: 99999999999999;
		width: 90px;
		height:15px;
		
	}
	.fondoTab{
		position: absolute;
		bottom: 0px;
		right: 20px;
		height:20px;
		width: 70px;
		border-left:10px solid transparent; 
		border-right:10px solid transparent; 
		border-bottom:18px solid <%=colorTab%>; 
	}
	a{
		border: none;
	}
</style>
<script type="text/javascript" src="js/prototype-1.6.js"></script>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.14.custom.min.js"></script>
<style>
	.toggler { width: 500px; height: 200px; }
	#button { padding: .5em 1em; text-decoration: none; }
	</style>
	<script>
	jQuery.noConflict();
	jQuery(function() {
		// run the currently selected effect
		function runEffect() {
			// get effect type from 
			var selectedEffect = "blind";
			
			// most effect types need no options passed by default
			var options = {};
			
			// run the effect
			jQuery( "#effect" ).toggle( selectedEffect, options, 500 );
		};
		
		// set effect from select menu value
		jQuery( "#button" ).click(function() {
			runEffect();
			return false;
		});
	});
	</script>
	<script type="text/javascript">
	var theHeight;
	function inicio(){
		dimensiones();
		Event.observe(window, 'resize', dimensiones);
	}
	
	function dimensiones(){
		/*if( theHeight=="101"){
			theHeight = "92";
		}else{
			theHeight = "101";
		}
		$("menu").style.height = theHeight+"%";*/
		
		if (window.innerWidth){
		  theHeight = window.innerHeight 
		} 
		else if (document.documentElement && document.documentElement.clientWidth){
		  theHeight = document.documentElement.clientHeight 
		} 
		else if (document.body){
		  theHeight = document.body.clientHeight 
		}
					
		obj = document.getElementById("menu");
		if(obj.offsetTop=='0'){
			parent.reposicionaBusquedaAbajo();
			theHeight = theHeight - "81";
			document.getElementById('txt').value = "Expandir";
		}else{
			parent.reposicionaBusquedaArriba();
			theHeight = theHeight - "1";
			document.getElementById('txt').value = "Contraer";
		}
		
		$("menu").style.height = theHeight+"px";
	}
	function iluminar(){
		document.getElementById('tab').style.borderBottom='18px solid <%=color1%>'
	}
	function oscurecer(){
		document.getElementById('tab').style.borderBottom='18px solid <%=colorTab%>'
	}
</script>
</head>
<body>
	<div class="esconderEncabezado" onmouseover="iluminar();" onmouseout="oscurecer();">
		 <div style="position: absolute; top:-7px; left:-12px;"><a  href="#" id="button" onclick="javascript:inicio();"><input class="input" type="button" id="txt" value="Expandir" size=9 style="cursor: hand;cursor: pointer;text-align:left;background-color:transparent; border-width:0; font-size:13px; font-family:Verdana; font-weight: bold;" /></a>
		</div>
	</div>
	<div class="fondoTab" id="tab" name="tab">
		<!-- <img id="fondo" name="fondo" src="imagenes/tab.png" width="96px" height="20px"/>-->
	</div>
	
	<div id="effect" class="ui-widget-content ui-corner-all" >
		<iframe src="head.jsp" id="topFrame" name="topFrame" width="100%" height="80px" marginheight="0" marginwidth="0" noresize scrolling="No" frameborder="0"></iframe>
	</div>
	<iframe src="menu.jsp" id="menu" name="menu" width="100%" height="80%" style="width: 100%; height: 92%" marginheight="0" marginwidth="0" noresize scrolling="No" frameborder="0"></iframe>	
</body>
<!-- 
<frameset rows="80,*,0" cols="*" frameborder="no" border="0" framespacing="0">
	<frame src="head.jsp" name="topFrame" valign="top" marginheight="0" marginwidth="0" scrolling="NO" noresize >
	<frameset rows="*" cols="*" frameborder="no" border="0" framespacing="0">
         <frame src="menu.jsp" name="leftFrame" id="leftFrame" valign="top" marginheight="0" marginwidth="0" scrolling="NO" noresize >
	</frameset>
</frameset>
 -->
<script type="text/javascript">
	var theHeight;

	if (window.innerWidth){
	  theHeight = window.innerHeight 
	} 
	else if (document.documentElement && document.documentElement.clientWidth){
	  theHeight = document.documentElement.clientHeight 
	} 
	else if (document.body){
	  theHeight = document.body.clientHeight 
	}
		
	$("menu").style.height = theHeight-"81"+"px";

</script>
</html>