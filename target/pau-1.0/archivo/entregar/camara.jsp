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
%>
<head>

<style>
 	body { 
 	    font-family: sans-serif; 
 /* 	    text-align: center;  */
     	background-color: aliceblue; 
 	} 
 	#video_wrap { 
 	    position: absolute; 
 	} 
 	#video_overlays { 
 	    position: absolute; 
 	} 
 	#video_overlays img { 
 	    width: 850px; 
 	    height: 450px; 
 	} 
 	#video_frame { 
 	    width: 720px; 
 	} 
 	#canvas_frame { 
 	    width: 320px; 
 	    height: 200px; 
 	    float: right; 
 	} 
 	.button_controller { 
 	    float: right; 
 	    border: 1px solid gray; 
 	    padding: 10px; 
 	} 
</style>
</head>
<body>
<div class="container-fluid">
	<h1>Capturar foto <small class="text-muted fs-5">( <%=codigoAlumno%> - <%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%> )</small></h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="documentos"><i class="fas fa-arrow-left"></i></a>
	</div>
 	<!-- Mensaje de error -->
	<span name="errorMsg"></span>
	
 	<!-- Cargar Frame de TV y cargar el video de la webcam -->
	<div id="video_wrap">
	    <div id="video_overlays">
 <!-- 	        <img src="tv.png"></img> --> 
	    </div> 
	    <video id="video_frame" playsinline autoplay></video>
	</div>
	
 	<!-- Capturamos la imagen a través de la API web y lo mostramos en el canvas --> 
	<div class="button_controller">
	    Botón para capturar
	    <button type="button" id="snap_frame">Capturar Imagen</button>
	    <br/><br/>
	    Imagen de la Webcam
	    <canvas id="canvas_frame"></canvas>
	    <img id="imgTmp" src="">
	</div>
	<!-- Cargamos el Javascript -->
	<script src="webcam.js" async></script>
</div>

<script type="text/javascript">	
	
	const btnDisplay =  document.querySelector("#snap_frame");
	const btnDownload =  document.querySelector("#btnDownload");
	const imgConverted =  document.querySelector("#imgConverted");
	const myCanvas =  document.querySelector("#canvas_frame");
	
	btnDisplay.addEventListener("click", function (){
		if(window.navigator.msSaveBlob){
			window.navigator.msSaveBlob(myCanvas.msToBlob(), "canvas-image.png")
		}else {
			const a = document.createElement("a");
			
			document.body.appendChild(a);
			a.href = myCanvas.toDataURL("image/jpeg", 1.0);
			a.download = "canvas-image.jpg";
			a.click();
			document.body.removeChild(a);
			
		}
	});

</script>
</body>