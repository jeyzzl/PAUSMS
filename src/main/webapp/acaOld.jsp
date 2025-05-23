<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import= "aca.menu.spring.*"%>
<%@ page import= "java.util.List"%>
<%@ page import= "java.util.ArrayList"%>
<html>
<head>
<%
	String currentColor 			= session.getAttribute("colorTablas")==null?"default":(String)session.getAttribute("colorTablas");
	String menuUsuario				= (String) session.getAttribute("menu")==null?"0":(String) session.getAttribute("menu");
	
	boolean esEmpleado 				= (boolean) request.getAttribute("esEmpleado");
	boolean esMaestro 				= (boolean) request.getAttribute("esMaestro");
	boolean esMentor 				= (boolean) request.getAttribute("esMentor");
	int tieneInternado				= (int) request.getAttribute("tieneAcceso");
	boolean esEmpleadoValida 		= (boolean) request.getAttribute("esEmpleadoValida");
	aca.mensaje.spring.Mensaje msj	= (aca.mensaje.spring.Mensaje) request.getAttribute("mensaje");
	boolean existeMensaje 			= (boolean) request.getAttribute("existeMensaje");
	boolean esEditor				= (boolean) request.getAttribute("esEditor");
	String nombreCorto 				= (String) request.getAttribute("nombreCorto");	
%>	
	<title>Gestión educativa</title>
	<meta name="google-signin-client_id" content="65151421968-02p2kc8h5t02ldaojiflteb8k1buaqtk.apps.googleusercontent.com">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	<script src='js/jquery-1.7.1.min.js'></script>
  	<link rel="SHORTCUT ICON" href="imagenes/icoAc.png">  
  	<link href='print.css' rel='STYLESHEET' type='text/css' media='print'>
	<!--   <link rel="stylesheet" href="bootstrap/css/fix-input.css" />   -->
<%-- 	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap3/css/bootstrap.min.css"> --%>		
<%-- 	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap4/css/bootstrap.min.css">		 --%>
<%-- 	<script src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script> --%>
<%-- 	<script src="<%=request.getContextPath()%>/js/popper.min.js"></script> --%>
<%-- 	<script src="<%=request.getContextPath()%>/bootstrap4/js/bootstrap.min.js"></script> --%>
	
  	<link href="bootstrap/css/bootstrap.min.css" rel="STYLESHEET" type="text/css"> 
  	<link rel="stylesheet" type="text/css" href="superfishmenu/css/superfish.css" media="screen">
<%--   	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap3/js/bootstrap.min.js"> --%>
  	
<%  
	String usuario					= session.getAttribute("usuario")==null?"0":(String)session.getAttribute("usuario");  
  	String usuarioOriginal			= session.getAttribute("codigoLogeado")==null?"0":(String)session.getAttribute("codigoLogeado");
	String codigoPersonal 			= session.getAttribute("codigoPersonal")==null?"0":(String)session.getAttribute("codigoPersonal");
	String opcionesValida 			= session.getAttribute("opciones")==null?"0":(String)session.getAttribute("opciones");
  	
	String paginaInicial			= "empleado";
	List<Menu> lisMenu				= (ArrayList<Menu>)session.getAttribute("lisMenu");
	List<Modulo> lisModulo			= (ArrayList<Modulo>)session.getAttribute("lisModulo");
	List<ModuloOpcion> lisOpcion	= (ArrayList<ModuloOpcion>)session.getAttribute("lisOpcion");	
	int numeroOpciones = 0;
	if (codigoPersonal.substring(0,1).equals("0") || codigoPersonal.substring(0,1).equals("1")){
		numeroOpciones++;
	}else if(tieneInternado > 0 || esMaestro|| esMentor){
		numeroOpciones++;
	}
	int numOpciones =  lisMenu==null?0:lisMenu.size();
	numeroOpciones	+= numOpciones;
	
	String topIcon				= "80px";
	if (menuUsuario.equals("2")||numeroOpciones<=3) topIcon = "60px";	
	
	if (codigoPersonal.substring(0,1).equals("0") || codigoPersonal.substring(0,1).equals("1")){
		paginaInicial = "portales/portalAlumno/portal";
	}	
%>
  <style>
  	body{
  		margin:0;padding:0;
  		background: url(imagenes/bg.png);
		font-family:"HelveticaNeue-Light","Helvetica Neue Light","Helvetica Neue",sans-serif;
  	}
    #secondFrame{
    	min-width: 1023px;
    	overflow:hidden;
    	padding: 0 0 0 0;
    }
  	#header{
  		min-width: 1023px;
  		width:100%;
  		height:70px;
  		background: #396CB8;
  		background: url(imagenes/bg-header.png);
  		
  		margin: 0 0 4px 0;
  		
  		-moz-box-shadow: 0 1px 6px 0px #6E6E6E;
   		-webkit-box-shadow: 0 1px 6px 0px #6E6E6E;
		box-shadow: 0 1px 6px 0px #6E6E6E;
  	}
  	
	a.menu:after, .dropdown-toggle:after {
    	content: none;
	}
  	
  	li{
  		line-height: .9;
  	}
  	.icon-arr{
  		position:absolute;
  		top:-8px;
  		left:3px;
  		
  		height: 15px;
  		width: 31px;
  		background: url(imagenes/arr.png);
  	}
  	
  	.icon-arr-left{
  		position:absolute;
  		top:-6px;
  		right:18px;
  		
  		height: 15px;
  		width: 31px;
  		background: url(imagenes/arr.png);
  	}
  	
  	.sf-menu{
  		padding: 0 0 0 1px;
  	}
  
  	
  	.logoUm{
  		position:absolute;
  		top:4px;
  		left:20px;
  	}
  	#menu{
  		color: #F2F2F2;
  		letter-spacing:0 px;
  	}
  	#menu:hover{
  		color: white;
  	}
  	.usuario{
  		padding: 2px 18px 0 0;
  		color:white;
  		font-size:12px;
  		
  	}
  	
  	.main-menu li:hover ul,
	.main-menu li.sfHover ul {
		left:			0;
		top:			2.5em; /* match top ul list item height */
		z-index:		99;
	}
	
	.user-menu li:hover ul,
	.user-menu li.sfHover ul {
		right:			0;
		top:			2.5em; /* match top ul list item height */
		z-index:		99;
		width: 130px;
	}
	
	.liga{
		text-decoration:none;
		color:white;
	}
	.liga:hover{
		text-decoration:none;
		color:white;
	}
	
	.titulo{
		padding: 10px 0px 0px 13px;
  		font-size:20px;
  		color:white;
  		letter-spacing: 1px;
  		position:relative;
  		top:2px;
	}
	
	.refresh{
		position:absolute;
		right:27px;
		top:<%=topIcon%>;		
		cursor:pointer;
	}
	
	.print{
		position:absolute;
		right:50px;
		top:<%=topIcon%>;	
		cursor:pointer;
	}
	
	.menu{
		position:absolute;
		right:73px;
		top:<%=topIcon%>;	
		cursor:pointer;
	}
	
	.oculto{
		position: absolute;
		visibility: hidden;
	}	
	
	div#cerrarNotificacion{
		width: 98%;
		height: 20px;
		align: center;
		border: 1px solid gray;				
		background-color: #E6E6E6;
		color:white;
		font-weight:300;
		border-bottom-right-radius:9px;
		-moz-border-radius-bottomright:9px;
		border-bottom-left-radius:9px;
		-moz-border-radius-bottomleft:9px;
	}
	
	table#tableNotificacion{
		
	}
	
	table#tableNotificacion tr td{
		font-size: 12px;
		cursor: pointer;
		border-bottom: solid 1px #D8D8D8;
	}
	
	div#divNotiMensajes{
		position: absolute;
		z-index: 1000;
		-moz-opacity:0.9;
		filter: alpha(opacity=90);
		width: 170px;
		left: 229px;
		top: -300px;
	}
	
	div#notificacion{
		background-color: white;
		border: solid gray 1px;
		width: 98%;
		
		-moz-box-shadow: 0 2px 6px 0px #6E6E6E;
	    -webkit-box-shadow: 0 2px 6px 0px #6E6E6E;
		box-shadow: 0 2px 6px 0px #6E6E6E;
		
		border-top-right-radius:9px;
		-moz-border-radius-topright:9px;
		border-top-left-radius:9px;
		-moz-border-radius-topleft:9px;
	}
	<%
  		//Cambiar color hader y hover del menu
  		
		if(!currentColor.equals("default")){
			out.print("	#header{background: "+currentColor+";}");
			out.print("	.sf-menu a:hover{background: "+currentColor+";}");
		}		
  	%>	
  </style>
  <script type="text/javascript">
  var timerNotificacion;
  var veoNotificacion = false;
  
  	function sinNotificaciones(){
  		$("#divNoti").css({opacity:"0.2"});
  		$("#divNotiCount").html("0");
  	}
  	
  	function conNotificaciones(cuantas){
  		$("#divNoti").css({opacity:"1"});
  		$("#divNotiCount").html(cuantas);
  	}
  
    function actualizaNotificaciones(){
    	$.get("notificacionList", function(data){
    		//console.log("Dentro de $.get()");
    		//console.log(data);
    		if(data.length == 0){
    			sinNotificaciones();
    			$("#tableNotificacion").html("<tr style='background-color: #E6E6E6;'><td style='cursor:auto;'>No hay notificaciones</td></tr>");
    		}else{
    			var table = $("#tableNotificacion");
    			var contenido="";
    	 		conNotificaciones(data.length);
    	 		var i = 0;
    	 		for(i = 0; i < data.length; i++){
    	 			contenido+="<tr onmouseover=\"this.style.backgroundColor='#E6E6E6';\" onmouseout=\"this.style.backgroundColor='white';\">"+
    	 				"<td onclick='cargaPagina(\""+data[i].id+"\",\""+data[i].url+"\");' title='"+data[i].fecha+"'>"+data[i].mensaje+"</td>"+
    	 			"</tr>";
    	 		}
    	 		table.html(contenido);
    	 		if(!veoNotificacion){
        	 		var dnm = $("#divNotiMensajes");
        	 		dnm.css({top: (0-dnm.height())+"px"});	
    	 		}
    		}
    		clearTimeout(timerNotificacion);
    		timerNotificacion = setTimeout(actualizaNotificaciones, 300000);
    	});
    }
    
    function muestraNotificaciones(){
    	//Falta el código para mostrar el div con las notificaciones
    	$("#divNotiMensajes").animate({top: "10px", opacity: "1"});
    	veoNotificacion = true;
    	clearTimeout(timerNotificacion);
		timerNotificacion = setTimeout(actualizaNotificaciones, 300);
    }
    
    function ocultaNotificacion(){
    	var dnm = $("#divNotiMensajes");
 		dnm.animate({top: (0-dnm.height())+"px", opacity: "0"});
 		veoNotificacion = false;
    }
    
    function cargaPagina(id, url){
    	if(url.startsWith("/academico")){
	    	$("#secondFrame").attr('src', url);
    	}else{//Si no inicia con academico entonces inicia con matricula del alumno
    		var matricula = url.substring(0, 7);
    		var url = url.substring(7, url.length);
    		parent.busca(matricula);
    		setTimeout(function(x){$("#secondFrame").stop();$("#secondFrame").attr('src', x);}, 1000, url);
    	}
    	//console.log("cargarPagina (simulacion)");
    	$("#tableNotificacion").html("<tr><td>Cargando</td></tr>");
    	ocultaNotificacion();
    	//Borrar la notificación
    	$.get("borraNotificacion?id="+id, function(data){
    		//console.log(data);
    		clearTimeout(timerNotificacion);
    		timerNotificacion = setTimeout(actualizaNotificaciones, 1000);
    	});
    }
  
 	function inicializaNotificaciones( jQuery){
 		divNoti = $("#divNoti");
 		divNoti.css({left: "230px", top: "10px"});
 		sinNotificaciones();
 		
 		$("#divNotiCount").css({left: "25px", top: "0px"});
 		
 		var dnm = $("#divNotiMensajes");
 		dnm.css({top: (0-dnm.height())+"px"});
 		
 		timerNotificacion = setTimeout(actualizaNotificaciones, 500);
 	}
 	$(document).ready(inicializaNotificaciones);
  </script>
</head>

<body onLoad="document.getElementById('parametroBusqueda').focus();">
	<div id="header" <%if(numeroOpciones<=4 || menuUsuario.equals("2")){%>style="height:50px;"<%}%>>
		
		<div style="float:left;">
			
			<div class="titulo" style="height:25px;<%if(numeroOpciones<=4 || menuUsuario.equals("2")){%>float:left;<%}%>">
			<%	if (menuUsuario.equals("1")){	%>	
				<a <%if(numeroOpciones<=4 || menuUsuario.equals("1")){%>style="position:relative;top:3px;"<%}%> class="liga" href="aca">
				<img src="imagenes/logoUni.png" width="26px" style="position:relative;top:-5px;">&nbsp;Gestión educativa
				</a>
			<%	}else{ %>
				<a <%if(numeroOpciones<=4 || menuUsuario.equals("2")){%>style="position:relative;top:3px;"<%}%> class="liga" href="menuUno" target="secondFrame">
				<img src="imagenes/logoUni.png" width="26px" style="position:relative;top:-5px;">&nbsp;Gestión educativa
				</a>
			<%	}%>
				
			</div>
			<div id="divNoti" style="position:absolute;" class="btn" onclick="muestraNotificaciones();">
				<img src="https://upload.wikimedia.org/wikipedia/commons/c/ca/Bell_font_awesome.svg" width="20" height="20" />
				<div id="divNotiCount" style="position:absolute;">0</div>
			</div>
			<div id="divNotiMensajes">
				<div id="notificacion">
					<table id="tableNotificacion" width="100%">
						<tr>
							<td>&nbsp;</td>
						</tr>
					</table>
				</div>
				<div id="cerrarNotificacion" onclick="ocultaNotificacion();" style="cursor:pointer;" align="center" >
					<font color="black">Cerrar</font>
				</div>
			</div>
						
			<%if(menuUsuario.equals("1")){%>				
			<%@ include file= "menuHeader.jsf" %>
			<%} %>
			
		</div>
				
		<div class="usuario" style="float:right;<%if(numeroOpciones<=4 || menuUsuario.equals("2")){%>padding:10px 12px 0 0;<%}%>">
					 
			<%if(numeroOpciones>4 && menuUsuario.equals("1")){%>
			<table>
				<tr>
					<td>
						<ul class="sf-menu user-menu" style="float:right;margin:0 0 3px 0;">
							<li class="click">
									<a href="#" class="principal" id="menu" style="cursor:pointer;min-width:95px;text-align:right;">&nbsp;&nbsp;&nbsp;<%=nombreCorto%></a>
									<ul class="drpmenu">
										<i class="icon-arr-left" ></i>
										<%
										if(codigoPersonal.equals("9800308")){
										%>
										<li><a target="secondFrame" href="utilerias/admin/tareas.jsp"><i class="icon-cog"></i> Tareas</a></li>
										<%
										}
										%>
										<li class="changeColor"><a target="secondFrame" href="cambiaColor" style="cursor:pointer;"><i class="icon-picture"></i> Cambiar color</a></li>
										<li><a target="secondFrame" href="configuracion"><i class="icon-cog"></i> Configuraci&oacute;n</a></li>
										 <li><a target="_top" href="salir"><i class="icon-off"></i> Salir</a></li>
									</ul>
							</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td>
						<%
						if( esEmpleadoValida || opcionesValida.contains("290") || usuarioOriginal.equals("9800308") ||codigoPersonal.equals("9800140")){
						%>
						<div style="position:relative;top:-7px;float:right;margin:3px 0 0 0;" class="<%	if(usuarioOriginal.equals("9800308")||usuario.equals("ecollins")||codigoPersonal.equals("9800140")){%>input-prepend<%} %> input-append">
							<%	if(usuarioOriginal.equals("9800308")||usuario.equals("ecollins")||codigoPersonal.equals("9800140")){%>
			            	<button style="height:23px" onclick="if(jQuery('#parametroBusqueda').val().length == 7) parent.cambiaCodigoPersonal(jQuery('#parametroBusqueda').val()); else parent.cambiaCodigoPersonal(jQuery('#parametroBusqueda').val()+'-');" class="btn" type="button"><i class="icon-user"></i></button>
			            	<%	}%>
			            	<input style="height:13px;width:120px;" placeholder="Buscar" type="text" id="parametroBusqueda" onkeypress="parent.checkKey(event, jQuery('#parametroBusqueda').val());" size="16" /><button style="height:23px" onclick="parent.busca(jQuery('#parametroBusqueda').val());" class="btn" type="button"><i class="icon-search"></i></button>
			    		</div>
						<%} %>
					</td>
				</tr>
			</table>
			<%}else{%>
				<ul class="sf-menu user-menu" style="float:right;margin:0 0 3px 0;">
					<li class="click">
							<a  class="principal" id="menu" style="cursor:pointer;min-width:95px;text-align:right;">&nbsp;&nbsp;&nbsp;<%=nombreCorto%></a>
							<ul class="drpmenu">
								<i class="icon-arr-left"></i>
								<%
								if(codigoPersonal.equals("9800308")){
								%>
								<li><a target="secondFrame" href="utilerias/admin/tareas.jsp"><i class="icon-cog"></i> Tareas</a></li>
								<%
								}
								%>
								<li class="changeColor"><a target="secondFrame" href="cambiaColor" style="cursor:pointer;"><i class="icon-picture"></i> Cambiar color</a></li>
								<li><a target="secondFrame" href="configuracion"><i class="icon-cog"></i> Configuraci&oacute;n</a></li>
								 <li><a target="_top" href="salir"><i class="icon-off"></i> Salir</a></li>
							</ul>
					</li>
				</ul>
				<% 
				if( esEmpleadoValida || opcionesValida.contains("290") || usuarioOriginal.equals("9800308") || codigoPersonal.equals("9800140") ){
				%>
				<div style="float:right;margin:3px 0 0 0;" class="<%if(usuarioOriginal.equals("9800308")||usuario.equals("ecollins")||codigoPersonal.equals("9800140")){%>input-prepend<%} %> input-append">
					<%	if(usuarioOriginal.equals("9800308")||usuario.equals("ecollins")||codigoPersonal.equals("9800140") ){%>
	            	<button style="height:23px" onclick="if(jQuery('#parametroBusqueda').val().length == 7) parent.cambiaCodigoPersonal(jQuery('#parametroBusqueda').val()); else parent.cambiaCodigoPersonal(jQuery('#parametroBusqueda').val()+'-');" class="btn" type="button"><i class="icon-user"></i></button>
	            	<%	}%>
	            	<input style="height:13px;width:120px;" placeholder="Buscar" type="text" id="parametroBusqueda" onkeypress="parent.checkKey(event, jQuery('#parametroBusqueda').val());" size="16" /><button style="height:23px" onclick="parent.busca(jQuery('#parametroBusqueda').val());" class="btn" type="button"><i class="icon-search"></i></button>
	    		</div>
			<%	} %>
		<% 	}%>
		</div>
	</div>
	
	<!-- 
	<div class="print">
		<input id="imprimir" type="image" src="imagenes/imprimir.png" value="Imprimir" onclick="imprimir();" class="oculto" onmouseover="this.src='imagenes/imprimirOver.png'" onmouseout="this.src='imagenes/imprimir.png'"/>
	</div> -->
<%	if (menuUsuario.equals("2")){%>
		<i class="icon-list menu" onclick="mostrarMenu();"></i>
<%	} %>
	<i class="icon-refresh refresh" onclick="refrescarFrame();"></i>
	<i class="icon-print print" onclick="imprimir();"></i>
<%	String ruta = request.getParameter("ruta"); if(ruta!=null&&!ruta.contains("aca")&&!ruta.toLowerCase().contains("accion")&&!ruta.equals("null")&&!ruta.trim().equals("")) paginaInicial = ruta.replaceAll("-->", "&"); %>
	<iframe style="height:100%;" id="secondFrame" name="secondFrame"  width="100%" src="<%=paginaInicial%>" scrolling="auto" frameborder="0"></iframe>	
</body>
</html>

<script type="text/javascript" src="superfishmenu/js/hoverIntent.js"></script>
<script type="text/javascript" src="superfishmenu/js/superfish.js"></script>
<script type="text/javascript">

jQuery(function(){
	jQuery('ul.sf-menu').superfish();
	
});

</script>

<script type="text/javascript">	

	function imprimir(){
		  var printWindow = window.open("","PrintWindow","width=800,height=600,menubar=1,resizable=1,toolbar=no,scrollbars=yes,menubar=yes,location=no");
	      var html = jQuery("#secondFrame").contents().find("html").html();
	      printWindow.document.open();
	      
	     //realpath
	      var ruta = '';
	      var arr = frames['secondFrame'].location.pathname.split("/");
	      
	      for(var i=0; i<arr.length; i++){
	    	  if(i!=arr.length-1){
	    		  ruta += arr[i]+'/';
	    	  }
	      }
	      
		  html=html.replace(new RegExp('(src=")',"g"),'$1'+ruta);
		  html=html.replace(new RegExp("(src=')","g"),'$1'+ruta);
		  html=html.replace(new RegExp('(href=")',"g"),'$1'+ruta);
		  html=html.replace(new RegExp("(href=')","g"),'$1'+ruta);

		  html=html.replace(/(href=["']).+(?=javascript:sorter\.showall\(\)["'])/g, "$1");
	      printWindow.document.write(html+"<link href='print.css' rel='STYLESHEET' type='text/css'>");
	      printWindow.document.close();
	      //printWindow.onload = function() {
	      printWindow.focus();
		  printWindow.print();
	      //}
	      
	}
	
	function refrescarFrame(){
		frames['secondFrame'].location = frames['secondFrame'].location;
	}
	
	function mostrarMenu(){		
		frames['secondFrame'].location = "menuUno";
	}
</script>
  
<script type="text/javascript">
	(function($){
		$('#secondFrame').load(function(){
	        $('#secondFrame').contents().find('body').on('click', function(){
	        	$('.principal').parent('li').children('ul').hide().css('visibility','hidden');
	        });
	    });
		var menuIcons = $('.user-menu').find('ul').find('li').find('a');
		for(var k=0; k<menuIcons.length; k++){
			$(menuIcons[k]).on('mouseover', function(){
				$(this).children('i').addClass('icon-white');
			});
			
			$(menuIcons[k]).on('mouseout', function(){
				$(this).children('i').removeClass('icon-white');
			});	
		}
		
		var padding = 2;
		var navegador = navigator.appName
		if (navegador == "Microsoft Internet Explorer") {
			padding = 6;
		}
		$(document).ready(function() {
			var windowHeight 	= $(window).height();
	  		$("#secondFrame").css({
					"height": windowHeight-$("#header").height()-4-padding
			});
		});
  		$(window).resize(function(){	
  			
  			var windowHeight 	= $(window).height();
  			
  			$("#secondFrame").css({
  				"height": windowHeight-$("#header").height()-4-padding
  			});
  		});
  		$('.dropdown-toggle').dropdown();
  		
		function mostrar(event) {
			if(event.ctrlKey){
				if(event.keyCode==82){
					refrescarFrame();
				}
			}
		}
		window.onkeydown = mostrar;
	})(jQuery);

</script>
<script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
<script>
function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
  }

  function onLoad() {
    gapi.load('auth2', function() {
      gapi.auth2.init();
    });
  }
</script>
<script>
	
	function ocultar(){
		var url = "ocultarMsj";
		new Ajax.Request(  
			url, {  
				method:'get',
				parameters:  {timestamp:new Date().getTime()},  		   
				onSuccess: function(req){
				//eval(req.responseText);
				}
			}  
		);  	
	}	
	
	function error(){
		document.getElementById("txt").innerHTML="ocurrio un error, favor de intentarlo mas tarde";
	}
	
	function cargando(){
		document.getElementById("txt").innerHTML="<img style='position:relative;top:2px;' height='15px' src=\"imagenes/loading.gif\" /> <font size=2>Un momento por favor</font>";
	}
	
	function actualizar(){		
		top.location = "inicio";
	}
	
</script>