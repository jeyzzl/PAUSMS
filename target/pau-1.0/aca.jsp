<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%
	//String colorUsuario 			= session.getAttribute("colorTablas")==null?"#cccccc":(String)session.getAttribute("colorTablas");	
	String menuUsuario				= (String) session.getAttribute("menu")==null?"0":(String) session.getAttribute("menu");
	String paginaBusca				= (String) session.getAttribute("paginaBusca")==null?"X":(String) session.getAttribute("paginaBusca");
	String idiomaUsuario			= (String) session.getAttribute("lenguaje")==null?"es":(String)session.getAttribute("lenguaje");
	String usr						= (String) session.getAttribute("usuario")==null?"0":(String)session.getAttribute("usuario");		

	boolean esEmpleado 				= (boolean) request.getAttribute("esEmpleado");
	boolean esColaborador 			= (boolean) request.getAttribute("esColaborador");
	boolean esMaestro 				= (boolean) request.getAttribute("esMaestro");
	boolean esMentor 				= (boolean) request.getAttribute("esMentor");
	int tieneInternado				= (int) request.getAttribute("tieneAcceso");
	boolean esEmpleadoValida 		= (boolean) request.getAttribute("esEmpleadoValida");
	aca.mensaje.spring.Mensaje msj	= (aca.mensaje.spring.Mensaje) request.getAttribute("mensaje");
	boolean existeMensaje 			= (boolean) request.getAttribute("existeMensaje");
	boolean esEditor				= (boolean) request.getAttribute("esEditor");
	String nombreCorto 				= (String) request.getAttribute("nombreCorto");
	String colorUsuario 			= (String) request.getAttribute("colorUsuario");	
%>	
	<title>Academic Manager</title>
  	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  	<meta name="viewport" content="width=device-width, initial-scale=1.0">
  	<link rel="SHORTCUT ICON" href="imagenes/icoAc.png">  
  	<link href='print.css' rel='STYLESHEET' type='text/css' media='print'>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap4.4/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap5.1/css/bootstrap.min.css">
	<script src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/bootstrap4.4/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/bootstrap5.1/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/fontawesome.min.css">
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/solid.min.css"> 
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/regular.min.css">	
	<!-- Include SockJS and Stomp.js -->
	<script src="https://cdn.jsdelivr.net/npm/sockjs-client/dist/sockjs.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/stompjs/lib/stomp.min.js"></script>
<%  
	String usuario					= session.getAttribute("usuario")==null?"0":(String)session.getAttribute("usuario");  
  	String usuarioOriginal			= session.getAttribute("codigoLogeado")==null?"0":(String)session.getAttribute("codigoLogeado");
	String codigoPersonal 			= session.getAttribute("codigoPersonal")==null?"0":(String)session.getAttribute("codigoPersonal");
	String opcionesValida 			= session.getAttribute("opciones")==null?"0":(String)session.getAttribute("opciones");
  	
	String paginaInicial			= paginaBusca.equals("X")?"empleado?IdiomaUsuario="+idiomaUsuario:paginaBusca+"?IdiomaUsuario="+idiomaUsuario;

	if (paginaInicial.substring(0,1).equals("/") && paginaInicial.length()>1) paginaInicial = paginaInicial.substring(1,paginaInicial.length());

	java.util.List<aca.menu.spring.Menu> lisMenu				= (java.util.ArrayList<aca.menu.spring.Menu>)session.getAttribute("lisMenu");
	java.util.List<aca.menu.spring.Modulo> lisModulo			= (java.util.ArrayList<aca.menu.spring.Modulo>)session.getAttribute("lisModulo");
	java.util.List<aca.menu.spring.ModuloOpcion> lisOpcion		= (java.util.ArrayList<aca.menu.spring.ModuloOpcion>)session.getAttribute("lisOpcion");

	int numeroOpciones = 0;
	if (codigoPersonal.substring(0,1).equals("0") || codigoPersonal.substring(0,1).equals("1") || codigoPersonal.substring(0,1).equals("2")){
		numeroOpciones++;
	}else if(tieneInternado > 0 || esMaestro|| esMentor){
		numeroOpciones++;
	}

	int numOpciones =  lisMenu==null?0:lisMenu.size();
	numeroOpciones	+= numOpciones;
	
	String topIcon				= "77px";
	
	if ((codigoPersonal.substring(0,1).equals("0") || codigoPersonal.substring(0,1).equals("1") || codigoPersonal.substring(0,1).equals("2")) && paginaBusca.equals("X")){
		paginaInicial = "portales/portalAlumno/portal?IdiomaUsuario="+idiomaUsuario;
	}
%>
	<style>
		/* Notification container */
		#notifications-container {
			position: fixed;
			top: 20px;
			right: 20px;
			z-index: 1000;
		}

		/* Individual notification */
		.notification {
			background-color: #ffffff;
			color: #333333;
			padding: 10px 20px;
			margin-bottom: 10px;
			border-radius: 5px;
			box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
			border-left: 4px solid #007bff;
			animation: slideIn 0.5s ease-out;
		}

		/* Animation for sliding in notifications */
		@keyframes slideIn {
			from {
				transform: translateX(100%);
				opacity: 0;
			}
			to {
				transform: translateX(0);
				opacity: 1;
			}
		}

		/* Close button for notifications */
		.notification .close {
			float: right;
			cursor: pointer;
			font-weight: bold;
			color: #999999;
		}

		.notification .close:hover {
			color: #333333;
		}
	</style>
	<style>
	    #secondFrame{    	
	    	overflow:hidden;
	    	padding: 0 0 0 0;
	    }  	
		
		html { overflow-y:hidden; }
	  	
	  	li{
	  		line-height: 1.1;
	  	}
	  	 	
		.menusistema{
			position:absolute;
			right:73px;
			top:<%=topIcon%>;	
			cursor:pointer;
		}
		
		.print{
			position:absolute;
			right:50px;
			top:<%=topIcon%>;	
			cursor:pointer;
		}
			
		.refresh{
			position:absolute;
			right:27px;
			top:<%=topIcon%>;		
			cursor:pointer;
		}		
		
		.oculto{
			position: absolute;
			visibility: hidden;
		}	

	  	@media (min-width: 392px){
			.dropdown-menu .dropdown-toggle:after{
				border-top: .3em solid transparent;
			    border-right: 0;
			    border-bottom: .3em solid transparent;
			    border-left: .3em solid;
			}
			.dropdown-menu .dropdown-menu{
				margin-left:0; margin-right: 0;			
			}
			.dropdown-menu li{
				position: relative;			
			}
			.nav-item .submenu{ 
				display: none;
				position: absolute;			
				left:100%; top:-7px;
			}
			.nav-item .submenu-left{ 
				right:100%; left:auto;
			}
			.dropdown-menu > li:hover{ background-color: #cccccc}
			.dropdown-menu > li:hover > .submenu{
				display: block;
			}
		}		
	</style>
	<style>

		:root {
  			--primColor: #eeeeee;
  			--secoColor: #555555;
  			--cornerRad: 4px;
		}
		body {  
		}
		details {
		;
		}
		summary {
			padding: 2.5px 30px;
			color: var(--secoColor);
			cursor: pointer;
			user-select: none;
			outline: none;
			transition: transform 200ms ease-in-out 0s;
		}
		summary::before,
		summary::after {
			position: static;
			top: 0;
			left: 0;
			right: 0;
		}
		summary::before {
			content: "";
		}
		summary::after {
		}
		summary:hover {
		}
		summary::marker {
		font-size: 0;
		}
		summary::-webkit-details-marker {
			position:absolute;
		}
		details[open] .menu {
			animation-name: menuAnim;
		}
		details[open] summary::before {
			content: "";
		}
		details[open] summary::after {
			content: "";
		}
		
		.menu  {
			width: 30px;
			margin-left:35px;
			position:absolute; z-index:1; ;
			border-radius: var(--cornerRad);
			background-color: var(--primColor);
			box-shadow: 0 4px 10px 0 rgba(0, 0, 0, 0.2);
			display: flex;
			flex-direction: column;
			justify-content: space-around;
			overflow: hidden;
			animation: closeMenu 300ms ease-in-out forwards;
			/*opacity:0.5;*/
			
		}
		.menu a {
			color: var(--secoColor);
			text-decoration: none;
			text-align: center;
			transition: filter 200ms linear 0s;
		}
		.menu a:nth-of-type(1) {
			padding-top: 10px;
		}
		.menu a:hover {
			filter: brightness(200%);
		}
		details::before {
			color: var(--secoColor);
			position: absolute;
			opacity: 0.4;
		}
		details[open]::before {
			animation: fadeMe 300ms linear forwards;
		}
		
		@keyframes menuAnim {
			0% {
				height: 0;
			}
			100% {
				height: 100px;
			}
		}

		@keyframes fadeMe {
			0% {
				opacity: 0.4;
			}
			100% {
				opacity: 0;
			}
		}	
		
		.radio{
				width: 40px;
				height: 40px;
				border-radius: 50%;
			}
		
	
	</style>
	<script type="text/javascript">
			
		var timerNotificacion;
		var veoNotificacion = false;
	
		// Prevent closing from click inside dropdown
		$(document).on('click', '.dropdown-menu', function (e) {
			e.stopPropagation();
		});
		
		/*Cierra el menu cuanndo se hace clic en una opcion del tercer nivel(submenu)*/
		$(document).on('click', '.cerrarmenu', function (e) {
			$('.navbar').click(); 
		});
		
		// make it as accordion for smaller screens	
		if ($(window).width() < 392) {
		$('.dropdown-menu a').click(function(e){
			e.preventDefault();
			if($(this).next('.submenu').length){
				$(this).next('.submenu').toggle();
			}
			/*
			$('.dropdown').on('hide.bs.dropdown', function () {
				$(this).find('.submenu').hide();
			})
			*/
		});
		}	
  	</script>
	<script type="text/javascript">
		// Establish WebSocket connection
		const socket = new SockJS('<%=request.getContextPath()%>/ws');
		const stompClient = Stomp.over(socket);
		
		function connectWebSocket() {

			stompClient.debug = function (message) {
				console.log('STOMP Debug:', message);
			};

			stompClient.connect({}, function (frame) {
				console.log('Connected to WebSocket:', frame);

				// Subscribe to the user's notification queue
				stompClient.subscribe('/user/<%=usr%>/queue/notifications', function (notification) {
					const notificationData = JSON.parse(notification.body);
					console.log('New notification:', notificationData);

					// Display the notification in the UI
					displayNotification(notificationData.message);
				});
			}, function (error) {
				console.error('WebSocket connection error:', error);
				// Attempt to reconnect after a delay
				setTimeout(connectWebSocket, 5000); // Retry after 5 seconds
			});
		}

		$(document).ready(connectWebSocket);

		function displayNotification(message) {
			// Create a notification element and append it to the DOM
			const notificationElement = document.createElement('div');
			notificationElement.className = 'notification';
			notificationElement.innerText = message;

			// Append to the notifications container
			const notificationsContainer = document.getElementById('notifications-container');
			if (notificationsContainer) {
				notificationsContainer.appendChild(notificationElement);
			}

			// Optional: Auto-remove the notification after a few seconds
			setTimeout(() => {
				notificationsContainer.removeChild(notificationElement);
			}, 10000); // Remove after 5 seconds
		} 

		function disconnectWebSocket() {
			if (stompClient && stompClient.connected) {
				stompClient.disconnect(function () {
					console.log('WebSocket disconnected');
				});
			}
		}
		
		function salir() {
			disconnectWebSocket();
			top.location.href="salir";
		}
	</script>
</head>
<body onLoad="document.getElementById('parametroBusqueda').focus();">
	<div id="container">	
		<nav id="navMenu" class="navbar navbar-expand-lg navbar-dark" style="background:<%=colorUsuario%>">	
			<!-- Home Logo/Button -->
			<a class="navbar-brand mx-0" target="secondFrame" href="<%=esColaborador?"empleado":"portales/alumno/resumen"%>">
				<img src="imagenes/logoUni.png" width="30px"><!--  style="position:relative;top:-5px;" -->
			</a>
			<!-- Notification Div -->
			<div id="notifications-container"></div>
			
			<!-- Nav -->
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#main_nav">
				<span class="navbar-toggler-icon"></span>
			</button>	
			<div class="collapse navbar-collapse" id="main_nav">	
				<ul class="navbar-nav">
	<%
		for (int k = 0; k<lisMenu.size(); k++){
			aca.menu.spring.Menu menu = (aca.menu.spring.Menu) lisMenu.get(k);	
			
			String nombreMenu = menu.getMenuNombre();
			
			if(idiomaUsuario.equals("en")){
				nombreMenu = menu.getNombreIngles();
			}
	%>
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"><%=nombreMenu%></a>
						<ul class="dropdown-menu">			
	<%
			for (int i = 0; i<lisModulo.size(); i++){
				
				aca.menu.spring.Modulo modulo = (aca.menu.spring.Modulo) lisModulo.get(i);
				if (menu.getMenuId().equals(modulo.getMenuId())){
					String nombreModulo = modulo.getNombreModulo();
					if(idiomaUsuario.equals("en")){
						nombreModulo = modulo.getNombreIngles();
					}
	%>	
							<li style="width:177px;"><a class="dropdown-item" href="#"><%=nombreModulo%></a>
								<ul class="submenu dropdown-menu">			
	<%
					for (int j=0; j<lisOpcion.size(); j++){
						aca.menu.spring.ModuloOpcion menuOpcion = (aca.menu.spring.ModuloOpcion) lisOpcion.get(j);
						String nombreOpcion = menuOpcion.getNombreOpcion();
								
						if(idiomaUsuario.equals("en")){
							nombreOpcion = menuOpcion.getNombreIngles();
						}
						String colorOpcion = "color:black;";
						if (menuOpcion.getIcono().equals("R")){
							colorOpcion = "color:blue";
						}
						if(menuOpcion.getModuloId().equals(modulo.getModuloId())){
				%>
									<li><a class="dropdown-item cerrarmenu" href="<%=modulo.getUrl()%><%=menuOpcion.getUrl()%>?moduloId=<%=modulo.getModuloId()%>&carpeta=<%=modulo.getUrl()%>" target="secondFrame" style="<%=colorOpcion%>"><%=nombreOpcion%></a></li>				
				<%
						}
					}
				%>
								</ul>
							</li>
	<%
				}
			}
	%>
						</ul>
					</li>
	<%
		}	
	%>	
				</ul>	
			</div> <!-- navbar-collapse.// -->
			<!-- Div para Separar la busqueda y los iconos hacia el lado derecho -->
			<div class="d-flex align-items-center">	
	<%	if( esEmpleadoValida || opcionesValida.contains("290") || usuarioOriginal.equals("9800308") ||usuarioOriginal.equals("9800706")){ %>		
				<div class="input-group">
	<%		if(usuarioOriginal.equals("9800308")||usuarioOriginal.equals("9800058")||usuarioOriginal.equals("9800706")){%>
					<button style="height:40px; margin:0 0 0 0; border-color: white;" class="btn btn-outline-dark my-2 my-sm-0" type="button" onclick="if(jQuery('#parametroBusqueda').val().length == 7) parent.cambiaCodigoPersonal(jQuery('#parametroBusqueda').val()); else parent.cambiaCodigoPersonal(jQuery('#parametroBusqueda').val()+'-');"><i class="fas fa-user-tie" style="color:white"></i></button>
	<%		}%>
					<input class="form-control my-2 my-sm-0" placeholder="Search" type="search" aria-label="Search" id="parametroBusqueda" onkeypress="parent.checkKey(event, jQuery('#parametroBusqueda').val());" size="10">
					<button style="height:40px; margin:0 0 0 0; border-color: white;" class="btn btn-outline-dark my-2 my-sm-0" type="button" onclick="parent.busca(jQuery('#parametroBusqueda').val());"><i class="fas fa-search" style="color:white;"></i></button>
				</div> 			
	<%	} %>
			
				<details>
					<summary>
						<img  class="radio" src="fotoMenu?Codigo=<%=codigoPersonal%>">
					</summary>
				<nav class="menu">
					<a href="configuracion" target="secondFrame"><i class="fas fa-cog"></i></a>
					<%-- <a href="salir"><i class="fas fa-sign-out-alt"></i></a> --%>
					<a onclick="salir();">
						<i class="fas fa-sign-out-alt"></i>
					</a>
				</nav>
				</details>
			</div>	
		</nav>	
		<i class="fas fa-list menusistema" onclick="mostrarMenu();"></i>
		<i class="fas fa-sync-alt refresh" onclick="refrescarFrame();"></i>
		<i class="fas fa-print print" onclick="imprimir();"></i>	
		<iframe id="secondFrame" name="secondFrame"  width="100%" src="<%=paginaInicial%>" scrolling="auto" frameborder="0"></iframe>
	</div>	
</body>
</html>
<script type="text/javascript">
	
	$(function() {
	
		/* Permite mantener el men� en la parte superior y ajusta la altura para eliminar el scroll vertical*/
		$("#secondFrame").height("calc(100vh - 57px)");
			
	    var $body = $(document);
	    $body.bind('scroll', function() {	        
	        if ($body.scrollLeft() !== 0) {
	        	// "Desactivar" el scroll horizontal
	            //$body.scrollLeft(0);
	            $("#secondFrame").height("calc(100vh-67px)");
	        }
	    });
	
	});
	
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
		document.getElementById("txt").innerHTML="an error ocurred, try again later";
	}
	
	function cargando(){
		document.getElementById("txt").innerHTML="<img style='position:relative;top:2px;' height='15px' src=\"imagenes/loading.gif\" /> <font size=2>Wait for a moment while it loads...</font>";
	}
	
	function actualizar(){
		top.location.href = "inicio";
	}
	
	//alert("Pagina:"+$("#secondFrame").attr('src'));
	
	// Detectar tipo de dispositivo
	$(function(){
		var tipo = "N";
		if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|Windows Phone|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
			tipo = "S";
		}		
		jQuery.get("esMovil?Tipo="+tipo, function(r){
			//ok	
		});
	});	
	
</script>