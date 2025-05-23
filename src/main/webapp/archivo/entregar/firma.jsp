<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String codigoAlumno		= (String)session.getAttribute("codigoAlumno");
	
	String mensaje 		 	= request.getParameter("Mensaje")==null?"x":request.getParameter("Mensaje");
	String folio    		= request.getParameter("Folio");	
%>
<head>
<style>
	.wrapper {
		position: relative;
		width: 400px;
		height: 200px;
		-moz-user-select: none;
		-webkit-user-select: none;
		-ms-user-select: none;
		user-select: none;
	}

	.signature-pad {
  		position: absolute;
  		left: 0;
  		top: 0;
  		width:400px;
  		height:200px;
  		background-color: white;
	}
</style>
<script type="text/javascript">
	function signatureCapture() {
		var canvas = document.getElementById("newSignature");
		var context = canvas.getContext("2d");
		canvas.width = 276;
		canvas.height = 180;
		context.fillStyle = "#fff";
		context.strokeStyle = "#444";
		context.lineWidth = 1.5;
		context.lineCap = "round";
		context.fillRect(0, 0, canvas.width, canvas.height);
		var disableSave = true;
		var pixels = [];
		var cpixels = [];
		var xyLast = {};
		var xyAddLast = {};
		var calculate = false;
		{   
			//functions
			function remove_event_listeners() {
				canvas.removeEventListener('mousemove', on_mousemove, false);
				canvas.removeEventListener('mouseup', on_mouseup, false);
				canvas.removeEventListener('touchmove', on_mousemove, false);
				canvas.removeEventListener('touchend', on_mouseup, false);

				document.body.removeEventListener('mouseup', on_mouseup, false);
				document.body.removeEventListener('touchend', on_mouseup, false);
			}

			function get_coords(e) {
				var x, y;

				if(e.changedTouches && e.changedTouches[0]){
					var offsety = canvas.offsetTop || 0;
					var offsetx = canvas.offsetLeft || 0;

					x = e.changedTouches[0].pageX - offsetx;
					y = e.changedTouches[0].pageY - offsety;
				}else if (e.layerX || 0 == e.layerX){
					x = e.layerX;
					y = e.layerY;
				}else if (e.offsetX || 0 == e.offsetX){
					x = e.offsetX;
					y = e.offsetY;
				}

				return {
					x : x, y : y
				};
			};

			function on_mousedown(e) {
				e.preventDefault();
		      	e.stopPropagation();

		      	canvas.addEventListener('mouseup', on_mouseup, false);
		      	canvas.addEventListener('mousemove', on_mousemove, false);
		      	canvas.addEventListener('touchend', on_mouseup, false);
		      	canvas.addEventListener('touchmove', on_mousemove, false);
		      	document.body.addEventListener('mouseup', on_mouseup, false);
		      	document.body.addEventListener('touchend', on_mouseup, false);
		      	
		      	empty = false;
		      	var xy = get_coords(e);
		      	context.beginPath();
		      	pixels.push('moveStart');
		      	context.moveTo(xy.x, xy.y);
		      	pixels.push(xy.x, xy.y);
		      	xyLast = xy;
			};
			
		    function on_mousemove(e, finish) {
			    e.preventDefault();
		      	e.stopPropagation();
		      	
				var xy = get_coords(e);
		      	var xyAdd = {
		        	x : (xyLast.x + xy.x) / 2,
		        	y : (xyLast.y + xy.y) / 2
		      	};

		      	if(calculate){
			      	var xLast = (xyAddLast.x + xyLast.x + xyAdd.x) / 3;
		        	var yLast = (xyAddLast.y + xyLast.y + xyAdd.y) / 3;
		        	pixels.push(xLast, yLast);
		      	}else{
		        	calculate = true;
		        }
		        
		      	context.quadraticCurveTo(xyLast.x, xyLast.y, xyAdd.x, xyAdd.y);
		      	pixels.push(xyAdd.x, xyAdd.y);
		      	context.stroke();
		      	context.beginPath();
		      	context.moveTo(xyAdd.x, xyAdd.y);
		     	xyAddLast = xyAdd;
		      	xyLast = xy;
		    };

		    
		    function on_mouseup(e) {
			    remove_event_listeners();
		      	disableSave = false;
		      	context.stroke();
		      	pixels.push('e');
		      	calculate = false;
		    };
		  }
		  canvas.addEventListener('touchstart', on_mousedown, false);
		  canvas.addEventListener('mousedown', on_mousedown, false);
		}

	function signatureSave() {
		
		// save canvas image as data url (png format by default)
		var canvas 		= document.getElementById("newSignature");
		alert("Entre...");
		var dataURL 	= canvas.toDataURL("image/jpeg",0.95);
		alert(dataURL);
		jQuery.get("saveFirma?Firma="+dataURL, function(r){
			var respuesta = jQuery.trim(r);	
			alert(respuesta);
		});
		
 		
		// Convert Base64 image to binary
		//var file = dataURItoBlob(img);
		//document.getElementById("saveSignature").src 	= dataURL;
		document.getElementById("imagen") 		= dataURItoBlob(dataURL);
	};
	
	function postCanvasToURL() {
		  // Convert canvas image to Base64
		  var img = snap.toDataURL();
		  // Convert Base64 image to binary
		  var file = dataURItoBlob(img);
	}

	function dataURItoBlob(dataURI) {
		// convert base64/URLEncoded data component to raw binary data held in a string
		var byteString;
		if (dataURI.split(',')[0].indexOf('base64') >= 0)
		    byteString = atob(dataURI.split(',')[1]);
		else
		    byteString = unescape(dataURI.split(',')[1]);
		// separate out the mime component
		var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
		// write the bytes of the string to a typed array
		var ia = new Uint8Array(byteString.length);
		for (var i = 0; i < byteString.length; i++) {
		    ia[i] = byteString.charCodeAt(i);
		}
		return new Blob([ia], {type:mimeString});
	}

	function signatureClear() {
		var canvas = document.getElementById("newSignature");
		
		document.getElementById("imagen").files[0] = canvas.getImageData();
		
		var context = canvas.getContext("2d");
		context.clearRect(0, 0, canvas.width, canvas.height);
		context.beginPath();
		context.rect(0, 0, canvas.width, canvas.height);
		context.fillStyle = "white";
		context.fill();
	}

	function SubirImagenes(){	
		 document.forma.submit();
	}
</script>
</head>

<body>
<div class="container-fluid">
	<h1>Firma</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="documentos">Regresar</a>
	</div>
<% 	if(mensaje.equals("1")){%>
	<div class="alert alert-success">Guardada</div>
<%  }else if(!mensaje.equals("x")){%>
	<div class="alert alert-alert"><%=mensaje%></div>
<%  } %>	
	<div id="canvas">
      <canvas class="roundCorners" id="newSignature" style="position: relative; margin: 0; padding: 0; border: 1px solid #c4caac;"></canvas>
    </div>
    <script>signatureCapture();</script>
    <button type="button" onclick="signatureSave()">Copiar firma</button>
    <button type="button" onclick="signatureClear()">limpiar firma</button>
    <br>    
    <form name="forma" enctype="multipart/form-data" action="grabarFirma?Folio=<%=folio%>" method="post">
	    <input type="hidden" id="Imagen" name="Imagen">
	    <input type="hidden" id="Folio" name="Folio" value="<%=folio%>"/>
	    <input type="file" name="imagen" id="imagen"><br>
		<img id="saveSignature"/>    
		<a href="javascript:SubirImagenes();" class="btn btn-primary"><i class="icon-arrow-up icon-white"></i> Subir</a>
	</form>	
</div>
</body>