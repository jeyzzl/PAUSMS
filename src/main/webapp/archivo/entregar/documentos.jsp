<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import="aca.archivo.spring.ArchEntrega"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
	String codigoAlumno		= (String)request.getAttribute("codigoAlumno");
	AlumPersonal alumno		= (AlumPersonal)request.getAttribute("alumno");
	List<ArchEntrega> lisEntregas				= (List<ArchEntrega>)request.getAttribute("lisEntregas");
	HashMap<String,String> mapDescripcion		= (HashMap<String,String>)request.getAttribute("mapDescripcion");
	HashMap<String,String> mapIdentificacion	= (HashMap<String,String>)request.getAttribute("mapIdentificacion");
	HashMap<String,String> mapPoder				= (HashMap<String,String>)request.getAttribute("mapPoder");
	HashMap<String,String> mapEnvio				= (HashMap<String,String>)request.getAttribute("mapEnvio");
	HashMap<String,String> mapExtra				= (HashMap<String,String>)request.getAttribute("mapExtra");
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
	function borrar(folio){
		if(confirm("¿Are you sure you want to delete this record?")){
			location.href = "borrar?Folio="+folio; 
		}
	}

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
		var canvas = document.getElementById("newSignature");// save canvas image as data url (png format by default)
		var dataURL = canvas.toDataURL("image/png");
		document.getElementById("saveSignature").src = dataURL;
	};

	function signatureClear() {
		var canvas = document.getElementById("newSignature");
		var context = canvas.getContext("2d");
		context.clearRect(0, 0, canvas.width, canvas.height);
		context.beginPath();
		context.rect(0, 0, canvas.width, canvas.height);
		context.fillStyle = "white";
		context.fill();
	}
</script>
</head>

<body>
<div class="container-fluid">
	<h2>Documents delivered <small class="text-muted">( <%=codigoAlumno%> - <%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="retirar">New deliver</a>
	</div>
	<table class="table table-sm">
		<tr>
			<td width="5%"><strong>Opc.</strong></td>
			<td width="2%"><strong>Folio</strong></td>
			<td width="43%"><strong>Documents</strong></td>
			<td width="10%"><strong>ID</strong></td>
			<td width="10%"><strong>Power of Attorney</strong></td>
			<td width="10%"><strong>Send</strong></td>
<!-- 			<td width="10%"><strong>Firma</strong></td> -->
			<td width="10%"><strong>Extra</strong></td>
			<td><strong>Signature</strong></td>
		</tr>
<%	
		for(ArchEntrega entrega : lisEntregas){		
%>
		<tr>
			<td>
				<a href="javascript:borrar('<%=entrega.getFolio()%>');" title="Eliminar"><i class="fas fa-trash-alt"></i></a>&nbsp;&nbsp;&nbsp;
				<a href="editarRetirar?Folio=<%=entrega.getFolio()%>" title="Editar"><i class="fas fa-pencil-alt"></i></a>
			</td>
			<td><span class="badge"><%=entrega.getFolio()%></span></td>
			<td>
<% 			
			String[] arrayDoc = entrega.getDocumentos().split("-");
			String descripcion = "-";
			for(String docId : arrayDoc){
				if(mapDescripcion.containsKey(docId)){
					descripcion = mapDescripcion.get(docId);
				}
			%>
				<span class="badge bg-success"><%=descripcion%></span>
<% 			}%>
			</td>
			<td>
				<a class="btn btn-primary btn-xs" href="subir?Folio=<%=entrega.getFolio()%>&Imagen=Identificacion&Opc=1"><i class="far fa-arrow-alt-circle-up"></i></a>
				<a class="btn btn-success btn-xs" href="camara?Folio=<%=entrega.getFolio()%>&Imagen=Identificacion&Opc=1"><i class="fas fa-camera-retro"></i></a>				
<%				if(mapIdentificacion.containsKey(entrega.getFolio())){%>
					<a class="btn btn-success btn-xs" href="ver?Folio=<%=entrega.getFolio()%>&Imagen=Identificacion"><i class="far fa-image"></i></a>
<% 				}%>
			</td>
			<td>
				<a class="btn btn-primary btn-xs" href="subir?Folio=<%=entrega.getFolio()%>&Imagen=Poder&Opc=1"><i class="far fa-arrow-alt-circle-up"></i></a>
<% 				if(mapPoder.containsKey(entrega.getFolio())){%>
					<a class="btn btn-success btn-xs" href="ver?Folio=<%=entrega.getFolio()%>&Imagen=Poder"><i class="far fa-image"></i></a>
<% 				}%>
			</td>
			<td>
				<a class="btn btn-primary btn-xs" href="subir?Folio=<%=entrega.getFolio()%>&Imagen=Envio&Opc=1"><i class="far fa-arrow-alt-circle-up"></i></a>
<% 				if(mapEnvio.containsKey(entrega.getFolio())){%>
					<a class="btn btn-success btn-xs" href="ver?Folio=<%=entrega.getFolio()%>&Imagen=Envio"><i class="far fa-image"></i></a>
<% 				}%>
			</td>
<!-- 			<td> -->
<%-- 				<a class="btn btn-primary btn-xs" href="subir?Folio=<%=entrega.getFolio()%>&Imagen=Firma&Opc=1"><i class="icon-arrow-up icon-white"></i></a> --%>
<%-- 				<a class="btn btn-primary btn-xs" href="firma?Folio=<%=entrega.getFolio()%>"><i class="fas fa-pencil-alt"></i></a> --%>
<%-- <% 				if( ArchEntregaU.tieneFirma(conEnoc, codigoAlumno, entrega.getFolio()) ){%> --%>
<%-- 					<a class="btn btn-success btn-xs" href="ver?Folio=<%=entrega.getFolio()%>&Imagen=Firma"><i class="far fa-image"></i></a> --%>
<%-- <% 				}%> --%>
<!-- 			</td> -->
			<td>
				<a class="btn btn-primary btn-xs" href="subir?Folio=<%=entrega.getFolio()%>&Imagen=Extra&Opc=1"><i class="far fa-arrow-alt-circle-up"></i></a>
<% 				if( mapExtra.containsKey(entrega.getFolio()) ){%>
					<a class="btn btn-success btn-xs" href="ver?Folio=<%=entrega.getFolio()%>&Imagen=Extra"><i class="far fa-image"></i></a>
<% 				}%>
			</td>
			<td>
				<a class="btn btn-primary btn-xs" href="firmados"><i class="fas fa-pen-square"></i></a>
			</td>
			
		</tr>
<% 		}%>
	</table>
<!-- 	<div id="canvas"> -->
<!--       <canvas class="roundCorners" id="newSignature" -->
<!--       style="position: relative; margin: 0; padding: 0; border: 1px solid #c4caac;"></canvas> -->
<!--     </div> -->
<!--     <script>signatureCapture();</script> -->
<!--     <button type="button" onclick="signatureSave()">Save signature</button> -->
<!--     <button type="button" onclick="signatureClear()">Clear signature</button> -->
<!--     <br> -->
<!--     <img id="saveSignature"/> -->		
</div>
</body>