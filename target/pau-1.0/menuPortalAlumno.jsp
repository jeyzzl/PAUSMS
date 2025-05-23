<%@ include file= "con_enoc.jsp" %>
<%@ page import= "aca.menu.*"%>
<script src="https://apis.google.com/js/platform.js" async defer></script>
<meta name="google-signin-client_id" content="65151421968-02p2kc8h5t02ldaojiflteb8k1buaqtk.apps.googleusercontent.com">
<%	
	String sUsuario				= (String)session.getAttribute("usuario");
	String sCodigoPersonal 		= (String)session.getAttribute("codigoPersonal");
	ArrayList<Modulo> lisModulo	= (ArrayList<Modulo>)session.getAttribute("lisModulo");
	ArrayList<Opcion> lisOpcion	= (ArrayList<Opcion>)session.getAttribute("lisOpcion");
	ModuloUtil moduloU			= new ModuloUtil();
	String colorPortal 			= (String)session.getAttribute("colorPortal");
	
	int menuWidth				= 200;
	
	if(colorPortal==null)colorPortal="";
%>

<html>
<head>
<title>Gesti�n educativa</title>
	<link href="print.css" rel="stylesheet" type="text/css" media="print">
	<link href="portales/alumno/css/pa<%=colorPortal%>.css" rel="STYLESHEET" type="text/css">
	<style>
		.encabezadoVV {
			font-family: Arial, Helvetica, sans-serif;
			font-size:11px;
			font-weight:bold;
			color:white;
			background-color:#3899C3;
			border:solid;
			border-top-width:0px;
			border-left-width:0px;
			border-right-width:0px;
			border-bottom-width:1px;
			border-color:#d5d9e2;
		}
	
		body {
			//behavior:url("csshover.htc");
		}
		
		iframe
		{
			border: none;
		}
		
		div#menu
		{
			position: absolute;
			z-index: 1000;
			top: 0px;
			left: -<%=menuWidth+2 %>px;
			width: <%=menuWidth+2 %>px;
		}
		
		div#flechaArriba
		{
			position: absolute;
			z-index: 1010;
			left: 3px;
			visibility: hidden;
		}
		
		div#flechaAbajo
		{
			position: absolute;
			z-index: 1010;
			left: 3px;
			visibility: hidden;
		}
		
		div#showMenu{
			position:absolute;
			left: 0px;
			width: 23px;
			height: 100%;
		}
		
		* {
			font-family:arial,tahoma,verdana,helvetica;
			font-size:12px;
		}
		
		/* the menu */
	
		ul,li,a {
			display:block;
			margin:0px 0px 0px 0px;
			padding:0px 0px 0px 0px;
			border-top:0px;
			border-right:0px;
			border-bottom:0px;
			border-left:0px;
		}
		
		ul {
			width:<%=menuWidth %>px;
			border:1px solid #9d9da1;
			background:white;
			list-style:none;
		}
		
		li{
			background-image: url(imagenes/sub.gif);
			background-position: 2px 5px;
			background-repeat: no-repeat;
			padding-left: 8px;
		}
		
		li li{
			padding-left: 5px;
			background-color: #CCCCCC;
			background-image: none;
		}
		
		li li a:hover{
			color: black;
			background-color: white;
		}
		
		li ul{
			width: <%=menuWidth-10 %>px;
		}
				
		a {
			padding:0px;
			border:1px solid white;
			text-decoration:none;
			/*color:gray;*/
			font-weight:bold;
			width:100%; /* IE */
		}
		
			li>a { width:auto; } /* others */
		
		/* regular hovers */
	
		a:hover {
			border-color: black;
			color: white;
			text-decoration: none;
			background-color: #172b5c;/*#bbb7c7*/
		}
		
		input#imprimir{
			position: absolute;
			z-index: 1001;
			top: 20px;
			padding: 0px 0px 0px 0px;
		}
	</style>
	<script type="text/javascript" src="js/prototype-1.6.js"></script>
	<script type="text/javascript">
		var seleccionAnterior = '';
		var opcionAnterior = '';
		var entroPrimeroAOpcion = false;
		function marca(nombreModulo, nombreOpcion){
			if(!entroPrimeroAOpcion && nombreOpcion != "-1"){
				if(document.getElementById(seleccionAnterior)){
					document.getElementById(seleccionAnterior).style.backgroundColor = "white";
				}
				if(document.getElementById(opcionAnterior)){
					document.getElementById(opcionAnterior).style.backgroundColor = "#CCCCCC";
				}
				
				if(document.getElementById(nombreModulo)){//#bbb7c7
					document.getElementById(nombreModulo).style.backgroundColor = "#c6d6ff";
					document.getElementById(nombreModulo).style.color = "black";
				}
				if(document.getElementById(nombreOpcion))
					document.getElementById(nombreOpcion).style.backgroundColor = "#a0bbff";
				seleccionAnterior = nombreModulo;
				opcionAnterior = nombreOpcion;
				entroPrimeroAOpcion = true;
			}
			if(nombreOpcion == "-1"){
				entroPrimeroAOpcion = false;
			}
		}
		
		function submenu(numModulo){
			$("submenu"+numModulo).toggle();
			if($("modulo"+numModulo).subVisible == false){
				$("modulo"+numModulo).style.backgroundImage = "url(imagenes/sub.gif)";
				$("modulo"+numModulo).style.backgroundPosition = "2px 5px";
				$("modulo"+numModulo).style.backgroundRepeat = "no-repeat";
				$("modulo"+numModulo).subVisible = true;
			}else{
				$("modulo"+numModulo).style.backgroundImage = "url(imagenes/subDown.gif)";
				$("modulo"+numModulo).style.backgroundPosition = "0px 6px";
				$("modulo"+numModulo).style.backgroundRepeat = "no-repeat";
				$("modulo"+numModulo).subVisible = false;
			}
			revisaPosicionMenu();
		}
		
		var theHeight;			//tama�o de la pantalla (alto)
		var theWidth;			//tama�o de la pantalla (ancho)
		var rowHeight = 22;		//tama�o de cada fila del menu
		var menu;				//objeto menu
		var t;					//tiempo
		var menuOculto = true;
		
		function inicio(){
			menu = document.getElementById("menu");
			
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
			
			revisaPosicionMenu();
			$("flechaArriba").style.top = "0px";
			$("flechaAbajo").style.top = (theHeight-$("flechaAbajo").offsetHeight)+"px";
			$("flechaArriba").style.left = "-<%=menuWidth+10 %>px";
			$("flechaAbajo").style.left = "-<%=menuWidth+10 %>px";
			
			document.getElementById("imprimir").style.left = (theWidth - 80)+"px";
			
			Event.observe($("mainFrame"), 'mouseover', ocultaMenu);
		}
		
		function revisaPosicionMenu(){
			var menuTop = menu.offsetTop;
			if(menuTop > (theHeight-menu.offsetHeight)){
				document.getElementById("flechaAbajo").style.visibility = "visible";
				$("flechaAbajo").style.left = "0px";
			}
			
			if(menuTop < 0){
				document.getElementById("flechaArriba").style.visibility = "visible";
				$("flechaArriba").style.left = "0px";
			}
			
			if((theHeight-menu.offsetHeight) >= menuTop){
				document.getElementById("flechaAbajo").style.visibility = "hidden";
				cancelaTiempo();
			}
			
			if(menuTop >= 0){
				document.getElementById("flechaArriba").style.visibility = "hidden";
				cancelaTiempo();
			}
		}
		
		function mueveMenuHaciaArriba(){
			var menuTop = menu.offsetTop;
			if((theHeight-menu.offsetHeight) < menuTop){
				menu.style.top = menuTop - 15+"px";
			}
			revisaPosicionMenu();
			t = setTimeout("mueveMenuHaciaArriba()",50);
		}
		
		function mueveMenuHaciaAbajo(){
			var menuTop = menu.offsetTop;
			if(menuTop < 0){
				menu.style.top = menuTop + 15+"px";
			}
			revisaPosicionMenu();
			t = setTimeout("mueveMenuHaciaAbajo()",50);
		}
		
		function muestraMenu(){
			$("menu").style.left = "0px";
			//$("showMenu").toggle();
			$("showMenu").style.height = "100px";
			$("flechaArriba").style.left = "0px";
			$("flechaAbajo").style.left = "0px";
			Event.observe(document, 'mousemove', observaMenu);
			menuOculto = false;
		}
		
		function observaMenu(event){
			if(Event.pointerX(event) >= <%=menuWidth %>){
				ocultaMenu();
			}
		}
		
		function ocultaMenu(){
			if(!menuOculto){
				Event.stopObserving(document, 'mousemove', observaMenu);
				$("menu").style.left = "-<%=menuWidth+10 %>px";
				//$("showMenu").toggle();
				$("showMenu").style.height = "100%";
				$("flechaArriba").style.left = "-<%=menuWidth+10 %>px";
				$("flechaAbajo").style.left = "-<%=menuWidth+10 %>px";
				menuOculto = true;
			}
		}
		
		function cancelaTiempo(){
			clearTimeout(t);
		}
		
		function imprimir(){
			if(window.mainFrame.contenidoP){
				if(document.all){
					window.mainFrame.contenidoP.document.execCommand('print', false, null);
				}else{
					window.mainFrame.contenidoP.print();
				}
			}else{
				//window.mainFrame.print();
				if(window.mainFrame.frames[0]){
					if(document.all){
						window.mainFrame.frames[0].document.execCommand('print', false, null);
					}else{
						window.mainFrame.frames[0].print();
					}
				}else{
					if(document.all){
						window.mainFrame.document.execCommand('print', false, null);
					}else{
						window.mainFrame.print();
					}
				}
			}
			//var d = document.getElementById("mainFrame").contentDocument;
		}
		
		//--------------------- Scroll por el mouse -----------------------------------
		function handle(delta) {
			if (delta < 0){
				mueveMenuHaciaArriba();
				cancelaTiempo();
			}else{
				mueveMenuHaciaAbajo();
				cancelaTiempo();
			}
		}

		function wheel(event){
			if(document.all)
				event = window.event;
			
			if(Event.element(event).tagName != "IFRAME"){
				var delta = 0;
				if (!event) event = window.event;
				if (event.wheelDelta) {
					delta = event.wheelDelta/120;
					if (window.opera) delta = -delta;
				} else if (event.detail) {
					delta = -event.detail/3;
				}
				if (delta)
					handle(delta);
			}
		}
	
		/* Llamar al c�digo. */
		if (window.addEventListener)
			window.addEventListener('DOMMouseScroll', wheel, false);
		window.onmousewheel = document.onmousewheel = wheel;
	</script>
</head>
<body onload="inicio();">
<div id="showMenu" align="center" onmouseover="muestraMenu();" class="encabezadoVV">
	<table height="100%">
		<tr>
			<td valign="middle" style="color: white; margin: 0px 0px 0px 0px; padding: 0px 0px 0px 0px;">
				<!-- b style="font-size: 16pt;">M<br><br><br>E<br><br><br>N<br><br><br>&Uacute;</b -->
				<img src="imagenes/menu.png" />
			</td>
		</tr>
	</table>
</div>
<table style="width:100%" height="100%">
	<tr>
		<td width="17px">
			<div id="menu">
				<table style="width:<%=menuWidth %>px"    align="left">
					<tr> 
				    	<th>
							<table style="width:100%"  >
								<tr valign="top">
									<th class="encabezadoVV">
										<font size="2">M   E   N   &Uacute;</font>
									</th>
								</tr>
							</table>
						</th>
					</tr>  
				    <tr>
				    	<td>
				    		<div id="ul">
				    			<ul>
<% 
					if (sCodigoPersonal.substring(0,1).equals("0") || sCodigoPersonal.substring(0,1).equals("1") || sCodigoPersonal.substring(0,1).equals("2")){ %>
	        						<li id="alumno" onclick="marca('alumno','1');"><a href="portales/portalAlumno/portal" target="mainFrame" style="font-size: 9pt;">Portal del Alumno</a></li>
<%
					}
				//Verificar si es preceptor
					if (aca.internado.AccesoUtil.tieneAcceso(conEnoc,sCodigoPersonal)>0){ 
						session.setAttribute("dormitorioId",String.valueOf(aca.internado.AccesoUtil.tieneAcceso(conEnoc,sCodigoPersonal)));
						session.setAttribute("Admin","Preceptor");
				%>
				      				<li id="preceptor" onclick="marca('preceptor','1');"><a href="portales/preceptor/portal" target="mainFrame" style="font-size: 9pt;">Portal del Preceptor</a></li>
				<% 	}
					//Verifica si es Alumno para poner portal del padre
					if (sCodigoPersonal.substring(0,1).equals("P")){ %>
				     				<li id="padre" onclick="marca('padre','1');"><a href="portales/padre/padre.jsp" target="mainFrame" style="font-size: 9pt;">Portal del Padre</a></li>
				<% 	}
					//Verifica si es Profesor
					if (moduloU.getEsMaestro(conEnoc, sCodigoPersonal)){%>
				      				<li id="profesor"  onclick="marca('profesor','1');"><a href="portales/maestro/cursos" target="mainFrame" style="font-size: 9pt;">Portal del Profesor</a></li>
				<% 	}
					//Verifica si es Mentor
					if (moduloU.getEsMentor(conEnoc, sCodigoPersonal)){%>
				      				<li id="mentor" onclick="marca('mentor','1');"><a href="portales/mentor/portal" target="mainFrame" style="font-size: 9pt;">Portal del Mentor</a></li>
				<%  }
					if (sCodigoPersonal.substring(0,1).equals("9")){%>
				      				<li id="cumple" onclick="marca('cumple','1');"><a href="portales/cumple/form.jsp" target="mainFrame" style="font-size: 9pt;">Cumplea�os</a></li>
				<% }
%>
									<li id="directorio" onclick="marca('directorio','1');"><a href="directorio.jsp" target="mainFrame">Directorio telef&oacute;nico y<br />Horario</a></li>
<%
					for (int i = 0; i<lisModulo.size(); i++){
						Modulo modulo = (Modulo) lisModulo.get(i);
				%>
					  				<li id="modulo<%=i %>" onclick="marca('modulo<%=i %>','-1');">
					  					<a onclick="submenu(<%=i %>);" style="cursor: pointer;"><%=modulo.getNombreModulo()%></a>
				  						<ul id="submenu<%=i %>" style="display: none;">
<%
						for(int j = 0; j < lisOpcion.size(); j++){
							Opcion opcion = (Opcion) lisOpcion.get(j);
							if(opcion.getModuloId().equals(modulo.getModuloId())){
%>
											<li id="opcion<%=i %>-<%=j %>" onclick="marca('modulo<%=i %>','opcion<%=i %>-<%=j %>');">
												<a href="<%=modulo.getUrl() %><%=opcion.getUrl()%>?moduloId=<%=modulo.getModuloId() %>&carpeta=<%=modulo.getUrl() %><%=opcion.getIcono() %>" target="mainFrame"><%=opcion.getNombreOpcion() %></a>
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
							</div>
						</td>
					</tr>
				  <tr>
				  <form action="salir" method="POST" name="datos" target="_top">
				    <td align="center" class="encabezadoVV">
				    	<input type="submit" name="Submit" value="Finalizar">
				    </td>
				  </form>	
				  </tr>
				  <tr><td>&nbsp;</td></tr>
				</table>
			</div>
		</td>
		<td>
			<iframe width="100%" height="100%" src="portales/portalAlumno/portal" name="mainFrame" id="mainFrame" style="overflow: auto;" frameborder="0"></iframe>
		</td>
	</tr>
</table>
<div id="flechaArriba" class="oculto">
	<img src="imagenes/arriba.png" onmouseover="mueveMenuHaciaAbajo();" onmouseout="cancelaTiempo();" width="<%=menuWidth %>px" class="oculto" />
</div>
<div id="flechaAbajo" class="oculto">
	<img src="imagenes/abajo.png" onmouseover="mueveMenuHaciaArriba();" onmouseout="cancelaTiempo();" width="<%=menuWidth %>px" class="oculto" />
</div>
<input id="imprimir" type="button" value="Imprimir" onclick="imprimir();" class="oculto" />
</body>
</html> 
<%
	lisModulo	= null;
	moduloU 	= null;
%>
<%@ include file= "cierra_enoc2.jsf" %>