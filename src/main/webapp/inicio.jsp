<%@page import="aca.acceso.Acceso"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.opcion.spring.Opcion"%>

<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ include file="idioma.jsp"%>
<jsp:useBean id="colorAlum" scope="page" class="aca.portal.Alumno"/>
<%
	String codigoPersonal		= session.getAttribute("codigoPersonal")==null?"0":(String)session.getAttribute("codigoPersonal");
	String usuarioOriginal		= (String)session.getAttribute("codigoLogeado");
	
	String alertaSesion			= (String) session.getAttribute("alertaSesion");
	boolean existeAlumno		= (boolean) request.getAttribute("existeAlumno");
	AlumPersonal alumno			= (AlumPersonal) request.getAttribute("alumno");
	Opcion opcion				= (Opcion) request.getAttribute("opcion");
	String cambiarPassword		= (String) request.getAttribute("cambiarPassword");	
	boolean movil 				= false;
	
	String colorMenu			= ((String)session.getAttribute("colorMenu"))==null?"":((String)session.getAttribute("colorMenu")).toUpperCase();
	String colorCerrar			= "";
	String colorResultado		= "";
	String colorAyudaTexto		= "";
	
	if(colorMenu.equals("DEFAULT") || colorMenu.equals("")){		
		colorCerrar 	= "#A886BC";
		colorResultado 	= "#F8EEF8";
		colorAyudaTexto	= "#442F63";
	}else{
		colorCerrar 	= colorAlum.modificarColor(colorMenu, 80);
		colorResultado	= colorAlum.modificarColor(colorCerrar, 60);
		colorAyudaTexto = colorMenu;
	}
	
	if (cambiarPassword.equals("S")){
		response.sendRedirect("cambiarPassword");
	}

	if(!codigoPersonal.equals("0")){		
%>
<html>
  <!-- bootstrap -->
  
<!--   <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" type="text/css" media="screen" /> -->
  
  <!-- end bootstrap -->

<head>
	<title>Academic Manager</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="SHORTCUT ICON" href="imagenes/icoAc.png">
<!-- 	<link href="bootstrap/css/bootstrap.min.css" rel="STYLESHEET" type="text/css">  -->
	<style type="text/css">
		h6 {
			position: absolute;
			top: -26px;
			left: 190px;		
		}
		body{
			margin-left: 0px;
			margin-top: 0px;
			margin-right: 0px;
			margin-bottom: 0px;
			scroll: no;
			font-family:"Helvetica Neue",Helvetica,Arial,sans-serif;
		}
		
		html{
			overflow: hidden;
		}
	
		iframe#frmInicio{
			width: 100%;
			height: 100%;
			border: none;
		}
		
		div#divMensaje{
			position: absolute;
			z-index: 1000;
			height: 0%;
			-moz-opacity:0.8;
			filter: alpha(opacity=80);
			border: solid 1px orange;
			font-family: arial, "lucida console", sans-serif;
			font-size: 12px;
			font-style: oblique;
			visibility: hidden;
		}
		
		div#mensaje{
			/* background-color: #d9f1ff; */
			background-color: #f5f35c;
		}
		
		div#divSesion{
			position: absolute;
			z-index: 1001;
			height: 0%;
			border: solid 1px blue;
			font-family: arial, "lucida console", sans-serif;
			font-size: 12px;
			font-style: oblique;
			visibility: hidden;
		}
		
		div#mensajeSesion{
			background-color: white;
		}
		
		.cerrar{
			cursor: pointer;
			border-right: 1px solid #666666;
			border-bottom: 1px solid #666666;
			border-left: 1px solid #CCCCCC;
			border-top: 1px solid #CCCCCC;
		}
		
		.cerrar:hover{
			border-right: 1px solid #CCCCCC;
			border-bottom: 1px solid #CCCCCC;
			border-left: 1px solid #666666;
			border-top: 1px solid #666666;
			background-color: #442F63;
		}
		
		div#cerrar,
		div#cerrarResultado{
			width: 98%;
			height: 20px;
			align: center;
			border: 1px solid gray;				
			background-color: #E6E6E6;
			color:white;
			font-weight:300;
			border-bottom-right-radius:7px;
			-moz-border-radius-bottomright:7px;
			border-bottom-left-radius:7px;
			-moz-border-radius-bottomleft:7px;
		}
		
		div#divBusqueda{
			position: absolute;
			z-index: 100000;
			-moz-opacity: 0.8;
			filter: alpha(opacity=80);
			top: 25px;
			color: white;
		}
		
		table#tableResultado{
			
		}
		
		table#tableResultado tr td{
			font-size: 12px;
		}
		
		div#divResultado{
			position: absolute;
			z-index: 1000;
			-moz-opacity:0.9;
			filter: alpha(opacity=90);
		}
		
		div#resultado{
			background-color: white;
			border: solid gray 1px;
			width: 98%;
			
			-moz-box-shadow: 0 2px 6px 0px #6E6E6E;
		    -webkit-box-shadow: 0 2px 6px 0px #6E6E6E;
			box-shadow: 0 2px 6px 0px #6E6E6E;
		}
		
		img.button{
			cursor: pointer;
			padding: 1px 1px 1px 1px;
		}
		
		img.button:hover{
			padding: 0px 0px 0px 0px;
			border-right: 1px solid #666666;
			border-bottom: 1px solid #666666;
			border-left: 1px solid #CCCCCC;
			border-top: 1px solid #CCCCCC;
		}
		</style>
		<style type="text/css">
/*-------------------Ayuda---------------------*/
		.ayudaDiv{
			position: absolute;
			z-index: 1001;
			visibility: hidden;
			-moz-opacity: 0.90;
			filter: alpha(opacity=90);
		}
		
		.ayudaTexto{
			background: white;
			border-radius:7px;
			border: 1px solid gray;
			
			-moz-box-shadow: 0 2px 6px 0px #6E6E6E;
		    -webkit-box-shadow: 0 2px 6px 0px #6E6E6E;
			box-shadow: 0 2px 6px 0px #6E6E6E;
		}
		
		ul{
			padding: 0px 0px 0px 15px;
			margin: 0px 5px 0px 0px;
		}
	</style>
	<style type="text/css">
	/*--------------------Nifty Corners - http://www.html.it/articoli/nifty/index.html--------------------------*/
		.rtop, .rbottom{display:block}
		.rtop *, .rbottom *{display: block; height: 1px; overflow: hidden}
		.r1{margin: 0 5px}
		.r2{margin: 0 3px}
		.r3{margin: 0 2px}
		.r4{margin: 0 1px; height: 2px}
		
		.rs1{margin: 0 2px}
		.rs2{margin: 0 1px}
	</style>
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/prototype-1.6.js"></script>
	<script type="text/javascript" src="js/messages.js"></script>
	<script type="text/javascript">
		__messageTop = 70;
	</script>
	<script type="text/javascript">
		var $j = jQuery;
		var theHeight;			//tama�o de la pantalla (alto)	
		var theWidth;			//tama�o de la pantalla (ancho)
		var t1;					//Tiempo para revisar la recarga de una pagina
		
		function inicio(){
			//self.scroll(0,0);
			if (window.parent.innerWidth){
				theWidth = window.parent.innerWidth 
				theHeight = window.parent.innerHeight 
			} 
			else if (parent.document.documentElement && parent.document.documentElement.clientWidth){
				theWidth = parent.document.documentElement.clientWidth 
				theHeight = parent.document.documentElement.clientHeight 
			} 
			else if (parent.document.body){
				theWidth = parent.document.body.clientWidth 
				theHeight = parent.document.body.clientHeight 
			}
		
			var frame = $("frmInicio");
			frame.style.height = theHeight+"px";
			
			var m = $("divMensaje");
			m.style.top = theHeight+"px";
			m.style.width = (theWidth-137-30)+"px";
			m.style.left = "145px";
			
			var s = $("divSesion");
			s.style.top = theHeight+"px";
			s.style.width = "300px";
			s.style.left = (theWidth-300-100)+"px";
			
			var r = $("divResultado");
			r.style.top = (-r.offsetHeight)+"px";
			r.style.width = (theWidth-300-$("divBusqueda").offsetWidth)+"px";
			r.style.left = (300+$("divBusqueda").offsetWidth)+"px";
			
			var b = $("divBusqueda");
			if(document.all){
				b.style.position = "absolute";
			}else{
				b.style.position = "fixed";
			}
			//jQuery("#parametroBusqueda").focus();
			
			b.style.left = (theWidth - b.getWidth() - 25)+"px";
			//Para la ayuda de la pagina de inicio
			revisaSiSeSolicitaLaAyuda(document);
			
			//Event.observe(window, 'unload', invalidaSesion);
		}
		//------------------ Seccion de Fin de Sesion -------------------------------------------------
		var contFin = 0;
		var tiempoValidaSesion;
		var tiempoMensajeSesion;
		var milisegundosEspera = 5*60*1000;	//La cantidad de milisegundos que espera antes de hacer otra validacion
			
		function invalidaSesion(event){
			//alert(event.type);
			new Ajax.Request("sesion?Accion=3",{
				method: "get",
				parameters:  {timestamp:new Date().getTime()},
				onFailure: function(req){
					alert("Unable to invalidate session. Contact the System's Department");
				},
				onSuccess: function(req){//Si se realiza, se cambia la variable finalizo a true
					eval(req.responseText);
				}
			});
		}
			
		function guardaSesion(){
			contFin++;
			new Ajax.Request("sesion?Accion=1",{
				method: "get",
				parameters:  {timestamp:new Date().getTime()},
				onFailure: function(req){
					if(contFin <= 5){
						guardaSesion();
					}
				},
				onSuccess: function(req){//Si se realiza, se cambia la variable finaliz� a true
					eval(req.responseText);
				}
			});
			return false;
		}
			
		function validaSesionUnica(){
			guardaSesion();
			new Ajax.Request("sesion?Accion=2",{
				method: "get",
				parameters:  {timestamp:new Date().getTime()},
				onFailure: function(req){
					muestraMensaje("Error validating session");
					setTimeout("ocultaMensaje();",4000);
				},
				onSuccess: function(req){//Si se realiza, se cambia la variable finalizo a true
					if(tiempoValidaSesion)
						clearTimeout(tiempoValidaSesion);
					//alert(req.responseText);
					eval(req.responseText);
					tiempoValidaSesion = setTimeout("validaSesionUnica();", milisegundosEspera);
				}
			});
		}
			
		function expulsaOtrasSesiones(){
			new Ajax.Request("sesion?Accion=4",{
				method: "get",
				parameters:  {timestamp:new Date().getTime()},
				onFailure: function(req){
					muestraMensaje("Error expelling");
					setTimeout("ocultaMensaje();",4000);
				},
				onSuccess: function(req){//Si se realiza, se cambia la variable finalizo a true
					eval(req.responseText);
				}
			});
		}
			
		function muestraMensajeSesion(frase){				
			var s = $("divSesion");
			s.style.visibility = "visible";
			$("celMensajeSesion").innerHTML = frase;
			s.style.height = $("mensajeSesion").offsetHeight+"px";
			jQuery("#divSesion").animate({top: (theHeight - s.offsetHeight)});
		}
		
		function ocultaMensajeSesion(){
			jQuery("#divSesion").animate({top: theHeight});
		}
			
		tiempoValidaSesion = setTimeout("validaSesionUnica();",20000);

		//------------------ Seccion de Mensaje -------------------------------------------------
		function muestraMensaje(frase){
			alert(frase);
			var m = $("divMensaje");
			m.style.visibility = "visible";
			$("celMensaje").innerHTML = frase;
			m.style.height = ($("tableMensaje").getHeight())+"px";
			jQuery("#divMensaje").animate({top: (theHeight-$("tableMensaje").getHeight())});
		}
		
		function ocultaMensaje(){
			jQuery("#divMensaje").animate({top: theHeight});
			$("divMensaje").style.visibility = "hidden";
		}
		//-------------------------- Seccion de Ayuda ----------------------------------
		var t2;					//Tiempo para ocultar la ayuda 2000
		var contAyuda = 0;		//Contador de la cantidad de ayudas que se han mostrado. Es para mostrar la ayuda correcta y no una anterior
		var head = 90;
		var extra = 40;

		function ocurrioResize(){
			inicio();
			window.frames[0].frames[1].inicio();
		}
		
		function ocurrioScroll(){
			inicio();
			//window.frames[0].frames[1].inicio();
		}
		
		Event.observe(window, 'resize', ocurrioResize);
		Event.observe(window, 'scroll', ocurrioScroll);
		
		function revisaSiSeSolicitaLaAyuda(documento, ayudaObjs){
			if(window.frames[0]){
				if(window.frames[0].frames[1]){
					window.frames[0].frames[1].scroll(0,0);
				}
			}
			ocultaAyuda();
			ocultaMensaje();
			//Event.observe(documento, 'unload', function(){ocultaAyuda();ocultaMensaje();Event.stopObserving(documento, 'unload', function(){ocultaAyuda();ocultaMensaje();});});
			if(ayudaObjs){
				for(var i = 0; i < ayudaObjs.length; i++){
					if(!$(".ayudaObjs["+i+"].className"+i)){
						var objClass = ayudaObjs[i].className.split(" ");
						var my_img = document.createElement('img');
						if(objClass[1].length == 3){
							var mydiv = document.createElement("div");
							Element.extend(my_img);
							/*my_img.src = "/academico/imagenes/BolaInterrogacion.png";
							my_img.style.width="20px";
							my_img.style.height="20px";
							my_img.style.position="absolute";
							my_img.style.left = (Position.positionedOffset(ayudaObjs[i])[0]+ayudaObjs[i].offsetWidth-20)+"px";
							my_img.style.top = Position.positionedOffset(ayudaObjs[i])[1]+"px";
							my_img.addClassName("-----");
							my_img.addClassName(ayudaObjs[i].className.substring(6,9));
							my_img.addClassName(ayudaObjs[i].className.substring(10,13));
							my_img.addClassName("oculto");
							my_img.id = ayudaObjs[i].className+i;
							my_img.style.filter = "alpha(opacity=" + (40) + ")";
							my_img.style.opacity = 0.4;
							mydiv.appendChild(my_img);*/
							try{
								if(ayudaObjs[i].tagName == "TD" || ayudaObjs[i].tagName == "TH"){
									new Insertion.Bottom(ayudaObjs[i], mydiv.innerHTML);
								}else if(ayudaObjs[i].tagName == "TR"){
									new Insertion.Bottom(ayudaObjs[i].firstChild.nextSibling, mydiv.innerHTML);
								}else{
									new Insertion.After(ayudaObjs[i], mydiv.innerHTML);
								}
							}catch(e){alert("Error recording - "+e);}
							my_img = $(documento.getElementById(ayudaObjs[i].className+i));
						}
					
						/*if(document.all){
							my_img.onmouseover = ayudaObjs[i].onmouseover = ayudaMouseOver;
							my_img.onmouseout = ayudaObjs[i].onmouseout = ayudaMouseOut;
						}else{*/
							Event.observe(my_img, "mouseover", ayudaMouseOver);
							Event.observe(ayudaObjs[i], "mouseover", ayudaMouseOver);
							
							Event.observe(my_img, "mouseout", ayudaMouseOut);
							Event.observe(ayudaObjs[i], "mouseout", ayudaMouseOut);
						//}
					}else{
						i = ayudaObjs.length;
					}
				}
			}
		}
			
		function ayudaMouseOver(event){
			obj = Event.element(event);
			clearTimeout(t2);
			var ayudaClass;
			
			if(!document.all){
				if(this.tagName)
					ayudaClass = this.className.split(" ");
				else
					ayudaClass = obj.className.split(" ");
			}else{
				ayudaClass = Event.element(event).className.split(" ");
			}
			
			var var1 = ayudaClass[1]; //opcionId   |   "alumno"
			var var2 = ayudaClass[2]; //ayudaId    |   matricula
			var url, url2, url3;
			var necesitaBD = false;

			if(ayudaClass[0] == "ayuda"){
			
				contAyuda++;
				muestraAyuda('<table style="margin: 0 auto;"><tr><td><img src="imagenes/cargando.gif" /></td></tr></table>');

				posicionaAyuda(event);
				//posiciona ayuda debajo del objeto-------
				/*if(Event.element(event).viewportOffset){
					var element = Event.element(event);
					$("ayuda").style.left = (element.positionedOffset()[0])+"px";
					$("ayuda").style.top = (element.positionedOffset()[1] + element.getHeight() + head) + "px";
				}*/
				//----------------------------------------
					
				if(var1.length == 3){//Si es ayuda normal
					url = "ayuda?Accion=5&contAyuda="+contAyuda+"&opcion="+var1+"&ayuda="+var2;
					necesitaBD = true;
				}else if(var1 == "alumno"){
					url = "ayuda?Accion=6&contAyuda="+contAyuda+"&matricula="+var2;
					necesitaBD = true;
				}else if(var1 == "mensaje"){
					var mensaje = "";
					for(var i=2; i<ayudaClass.length; i++){
						mensaje += ayudaClass[i]+" ";
					}
					muestraAyuda(mensaje);
					necesitaBD = false;
				}
				
				Event.observe(obj, 'mousemove', posicionaAyuda);
				
				if(necesitaBD){
					new Ajax.Request(url,{
						method: "get",
						parameters:  {timestamp:new Date().getTime()},
						onSuccess: function(req){
							eval(req.responseText);
						},
						onFailure: function(req){
							errorMessage("Error requesting help","");								
						}
					});
				}
			}
		}
			
		function ayudaMouseOut(event){
			obj = Event.element(event);
			
			Event.stopObserving(obj, 'mousemove', posicionaAyuda);
			t2 = setTimeout("ocultaAyuda();", 1000);
			ocultaAyuda();
		}
			
		function posicionaAyuda(event){
			$("ayuda").style.left = (event.screenX+5)+"px";
			//alert("a");
			var left = event.screenX+5;
			if(left < 0)
				$("ayuda").style.left = "0px";
			//alert("b");
			var right = event.screenX+$("ayuda").offsetWidth+5;
			if(right > theWidth)
				$("ayuda").style.left = (theWidth-$("ayuda").offsetWidth)+"px";
			//alert("c");
			var bottom = event.screenY + $("ayuda").offsetHeight - head + extra;
			if(bottom > theHeight)
				$("ayuda").style.top = (event.screenY-$("ayuda").offsetHeight-head-extra)+"px";
			else
				$("ayuda").style.top = (event.screenY-head+extra)+"px";
			//alert("d");
		}
			
		function toUnicode(theString) {
			var unicodeString = '';
			for (var i=0; i < theString.length; i++) {
				var theUnicode = theString.charCodeAt(i).toString(16).toUpperCase();
				while (theUnicode.length < 4) {
					theUnicode = '0' + theUnicode;
				}
				theUnicode = '\\u' + theUnicode;
				unicodeString += theUnicode;
			}
			return unicodeString;
		}
		
		function muestraAyuda(frase){
			var w = 18;
			var m = $("ayuda");
			$("ayudaTexto").innerHTML = frase;
			m.style.visibility = "visible";
			m.style.width = w+"em";
			
			while(m.getHeight() > (theHeight/3*2)){
				w+=2;
				m.style.width = w+"em";
			}
		}
		
		function ocultaAyuda(){
			$("ayuda").style.visibility = "hidden";
		}
		
		//----------------------- Seccion de Busqueda ---------------------------
		function busca(frase){				
			console.log("Entre...");
			frase = frase.replace(/^\s*([\S\s]*?)\s*$/, '$1');
			if(frase==""){
				warningMessage('Type a valid parameter','');
			}else{
				$('parametroBusqueda').value = frase;
				var url = "";
				muestraResultado("<img src=\"imagenes/cargando.gif\" />");
									
				if (frase.length == 7 && (frase.includes("0") || frase.includes("1") || frase.includes("2") || frase.includes("3") || frase.includes("4") || 
					frase.includes("5") || frase.includes("6") || frase.includes("7") || frase.includes("8") || frase.includes("9") )){						
					url = "busca?Accion=1&matricula="+frase;
				}else{//Si es por nombre
					url = "busca?Accion=2&frase="+frase;
				}
				new Ajax.Request(url, {
					method: "get",
					parameters:  {timestamp:new Date().getTime()},
					onSuccess: function(req){
						eval(req.responseText);
					},
					onFailure: function(req){
						errorMessage("Error requesting help","Try again");
					} 
				});
			}
		}
		
		function cambiaCodigoPersonal(frase){
			var url = "";
			muestraResultado("<img src=\"imagenes/loading.gif\" />");
			
			//Es Ery
			var ery = "etorres"
			if(frase==ery){
				frase="9800308";
			}
			
			url = "busca?Accion=3&matricula="+frase;
			
			new Ajax.Request(url, {
				method: "get",
				parameters:  {timestamp:new Date().getTime()},  
				onSuccess: function(req){
					eval(req.responseText);
				},
				onFailure: function(req){
					errorMessage('Error updating user ID',"Try again");
				}
			});
		}
		
		function muestraResultado(frase){
			var m = $("divResultado");
			$("celdaResultado").innerHTML = frase.strip();
			m.style.width = "370px";
			if(m.getHeight() > theHeight){
				m.style.height = (theHeight-50)+"px";
				m.style.overflow = "hidden";
				$("resultado").style.overflow = "auto";
				//$("resultado").style.height = (theHeight-70)+"px";
				
				jQuery("#resultado").animate({height: (theHeight-70)});
			}else{
				m.style.overflow = "hidden";
				m.style.height = "";
				$("resultado").style.height = "";
				$("resultado").style.overflow = "hidden";
			}
			
			//centrar busqueda
			document.getElementById("divResultado").style.left = ((jQuery(window).width()-jQuery("#divResultado").width())/2)+"px";				
			jQuery("#divResultado").animate({top: 0});
		}
		
		function ocultaResultado(){				
			$("resultado").style.overflow = "hidden";
			jQuery("#divResultado").animate({top: -$("divResultado").offsetHeight});
		}
			
		function checkKey(event, parametroBusqueda){
			if(event.ctrlKey){//Si esta presionado el ctrl
		<%					
				String usuario = (String)session.getAttribute("usuario")==null?"0":(String)session.getAttribute("usuario");

				if(usuarioOriginal.equals("9800308")||usuarioOriginal.equals("9800058")||usuarioOriginal.equals("9800706")){
		%>
					parametroBusqueda = parametroBusqueda.replace(/^\s*([\S\s]*?)\s*$/, '$1');
					if(event.keyCode == 13){//y si es enter
						if(parametroBusqueda.length == 7){
								cambiaCodigoPersonal(parametroBusqueda);
						}
						else{
								cambiaCodigoPersonal(parametroBusqueda+'-');
						}
							//cambiaCodigoPersonal($('parametroBusqueda').value);
					}
					if(event.keyCode == 10){//y si es enter
						if(parametroBusqueda.length == 7){
							cambiaCodigoPersonal(parametroBusqueda);
						}
						else{
							cambiaCodigoPersonal(parametroBusqueda+'-');
						}
							//cambiaCodigoPersonal($('parametroBusqueda').value);
					}
		<%	
				}
		%>
			}else{
				if(event.keyCode == 13){
					busca(parametroBusqueda);
				}
			}
		}
		
		function reposicionaBusquedaArriba(){
			var b = $("divBusqueda");
			b.style.top = "5px";
		}
		
		function reposicionaBusquedaAbajo(){
			var b = $("divBusqueda");
			b.style.top = "85px";
		}
		
		function refrescarPaginaPorBusqueda(tipoUsuarioNombre, nombrecompleto, codigoPersonal){			
			var ref = window.frames[0].secondFrame.document.location.href;
			//alert("LigaX:"+ref);			
			if(!ref.include("Accion")){					
				//alert("Entre 1:"+codigoPersonal+":"+ref);												
				window.frames[0].secondFrame.document.location.href = ref;
				noticeMessage(tipoUsuarioNombre+' Loaded', nombrecompleto+'<br>'+codigoPersonal, codigoPersonal);
				t2 = setTimeout("ocultaMensaje()", 1500);
			}else if (window.frames[0].secondFrame.document.getElementById('idPagina').value!='---'){
				//alert("Entre 2:"+codigoPersonal);
				var paginaModulo = window.frames[0].secondFrame.document.getElementById('PaginaModulo').value;
				var carpeta = paginaModulo.split('carpeta=')[1];
				ref = ref.split(carpeta+'/')[0];
				window.frames[0].secondFrame.document.location.href = ref+carpeta+paginaModulo;					
				noticeMessage(tipoUsuarioNombre+' cargado', nombrecompleto+'<br>'+codigoPersonal, codigoPersonal);
				t2 = setTimeout("ocultaMensaje()", 1500);
			}else{
				//alert("Entre 3:"+tipoUsuarioNombre);
				warningMessage(tipoUsuarioNombre+' cargado', 'Error refreshing page.</b> Clear cache and try again');
				t2 = setTimeout("ocultaMensaje()", 6500);
			}
		}
	</script>
</head>
<body onbeforeunload="invalidaSesion(event)" onload="frames['frmInicio'].focus();">
	<div id="divMensaje" name="divMensaje" onmouseover="ocultaMensaje();">
		<div id="mensaje" name="mensaje" style="width: 100%;" align="left">
			<table id="tableMensaje" width="100%">
				<tr>
					<td id="celMensaje"></td>
				</tr>
			</table>
		</div>
	</div>
		
	<div id="divSesion">
		<div id="mensajeSesion" style="width: 100%;" align="right">
			<div class="cerrar" style="width: 12px; position: relative; left: -1px;" onclick="ocultaMensajeSesion();">X&nbsp;</div>
			<table style="width:100%">
				<tr>
					<td id="celMensajeSesion" align="left"></td>
				</tr>
			</table>
		</div>
	</div>
	
	<div id="ayuda" onmouseover="ocultaAyuda();" class="ayudaDiv">
		<b class="spiffy">
		<b class="spiffy1"><b></b></b>
		<b class="spiffy2"><b></b></b>
		<b class="spiffy3"></b>
		<b class="spiffy4"></b>
		<b class="spiffy5"></b></b>
		
		<div id="ayudaTexto" style="padding: 0px 5px 0px 5px; overflow: hidden" class="ayudaTexto">
		  Things
		</div>
		
		<b class="spiffy">
		<b class="spiffy5"></b>
		<b class="spiffy4"></b>
		<b class="spiffy3"></b>
		<b class="spiffy2"><b></b></b>
		<b class="spiffy1"><b></b></b></b>
	</div>
<%
		java.util.ArrayList lisModulo	= (java.util.ArrayList)session.getAttribute("lisModulo");
		if(lisModulo.size() > 0 || usuarioOriginal.equals("9800308")){
%>
	<div style="position:absolute;right:20px;top:10px;z-index:-1;visibility:hidden;" class="ayuda INI 001">
		<div style="float:right;" class="<% if (usuarioOriginal.equals("9800308")||usuarioOriginal.equals("9800058")||usuarioOriginal.equals("9800706")){%>input-prepend<%  } %> input-append">
		<%	if(usuarioOriginal.equals("9800308")||usuarioOriginal.equals("9800058")||usuarioOriginal.equals("9800706")){ %>
            	<button style="height:23px" onclick="if($('parametroBusqueda').value.length == 7) cambiaCodigoPersonal($('parametroBusqueda').value); else cambiaCodigoPersonal($('parametroBusqueda').value+'-');" class="btn btn-primary" type="button"><i class="icon-user"></i></button>
        <%	}%>
            	<input style="height:13px;width:120px;"class="span2" placeholder="Buscar otro"  type="text" id="parametroBusqueda" onkeypress="checkKey(event);" size="16"/>
            	<button style="height:23px" onclick="busca($('parametroBusqueda').value);" class="btn btn-primary" type="button"><i class="icon-ok"></i></button>
    	</div> 
	</div>
	<div id="divResultado" onmouseout="ocultaAyuda();">
		<div id="resultado">
			<table id="tableResultado" width="100%">
				<tr>
					<td id="celdaResultado" width="100%" align="center"></td>
				</tr>
			</table>
		</div>
		<div id="cerrarResultado" onclick="ocultaResultado();" style="cursor:pointer;" align="center" >
			<font color="black">Close</font>
		</div>
	</div>
<%		}
%>
	<iframe id="frmInicio" name="frmInicio" src="aca" frameborder="0"></iframe>
	<script type="text/javascript">		
		inicio();
		ayuda();
	</script>
</body>
</html>
<script type="text/javascript" src="menuDelay/js/jquery-1.2.6.min.js"></script>
<script type="text/javascript">
		jQuery.noConflict();
		
  		var windowHeight 	= jQuery(window).height();
  		var windowWidth		= jQuery(window).width();
  		
  		jQuery("#frmInicio").css({
				"height": windowHeight,
				"width" : windowWidth
		});
  		
  		jQuery(window).resize(function(){			
  			
  			windowHeight 	= jQuery(window).height();
  			windowWidth 	= jQuery(window).width();
  			
  			jQuery("#frmInicio").css({
  				"height": windowHeight,
				"width" : windowWidth
  			});
  		});
  </script>
<%	} %>