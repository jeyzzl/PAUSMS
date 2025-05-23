<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp"%>

<%@page import="aca.vista.spring.Maestros"%>
<%
	String codigoPersonal 	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
	Maestros maestro 		= (Maestros)request.getAttribute("maestro");	
%>
<head>	
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">	
</head>
<body>
<div class="container-fluid">
	<h2>Tomar Foto <small class="text-muted fs-6">( <%=maestro.getCodigoPersonal()%> - <%=maestro.getNombre()+" "+maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno()%> )</small></h2>
	<hr>	
    <div class="row">
    	<div class="col-4 col-sm-2 d-flex align-items-center">
    		<a href="fotocredencial?CodigoPersonal=<%=codigoPersonal%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;    		
			<button class="btn btn-success" id="btnCapture"><i class="fas fa-camera-retro"></i></button>&nbsp;&nbsp;
			<button class="btn btn-info" id="btnSaveDataBase" disabled="true"><i class="fas fa-save"></i></button>&nbsp;&nbsp;
			<button class="btn btn-warning" id="btnDownloadImage" disabled="true"><i class="fas fa-download"></i></button>			
      	</div>
    </div>		
    <div class="row mt-1" style="margin-left:2px">
     	<div class="col-4 col-sm-4 d-flex align-items-center" style="border-width:2px;border-style:solid;border-color:black; width:660px;">
	    	<video id="theVideo" controls autoplay></video>
      	</div>
     	<div class="col-4 col-sm-4 d-flex align-items-center" style="border-width:2px;border-style:solid;border-color:black; width:660px;">
	    	<canvas id="theCanvas"></canvas>
      	</div>
	</div>
	<form name="frmFoto" action="addFoto" method="post" enctype="multipart/form-data">
	<input type="hidden" id="codigoMaestro" name="codigoMaestro" value="<%=maestro.getCodigoPersonal()%>">
	<textarea id="foto" name="foto" cols="2" style="height:100; width:200px" hidden></textarea>	
	<div class="row mt-1" style="margin-left:2px">
		<img id="imagen" name="imagen" style="display:none; border:1px solid #ccc; width:100px"/>		
	</div>
	</form>
</div>
<script type="text/javascript">	

	navigator.mediaDevices.getUserMedia({audio:false,video:true})
	
	var canvasWidth 		= 640;
    var canvasHeight 		= 480;
	var videoWidth 			= 640;
    var videoHeight 		= 480;
    var videoTag 			= document.getElementById('theVideo');
    var canvasTag 			= document.getElementById('theCanvas');
    var btnCapture 			= document.getElementById("btnCapture");
    var btnDownloadImage 	= document.getElementById("btnDownloadImage");
    var btnSaveDataBase 	= document.getElementById("btnSaveDataBase");
	var codigoMaestro		= document.getElementById("codigoMaestro").value;
		        
    videoTag.setAttribute('width', videoWidth);
    videoTag.setAttribute('height', videoHeight);
    canvasTag.setAttribute('width', canvasWidth);
    canvasTag.setAttribute('height', canvasHeight);
    window.onload = () => {
		
    	navigator.mediaDevices.getUserMedia({
              audio: false,
              video: {
                  width: videoWidth,
                  height: videoHeight
              }
        }).then(stream => {
              videoTag.srcObject = stream;
        }).catch(e => {
              document.getElementById('errorTxt').innerHTML = 'ERROR: ' + e.toString();
        });
    	
        var canvasContext = canvasTag.getContext('2d');
        btnCapture.addEventListener("click", () => {
           canvasContext.drawImage(videoTag, 0, 0, videoWidth, videoHeight);
           $("#btnSaveDataBase").prop("disabled", false);
           $("#btnDownloadImage").prop("disabled", false);
        });        
        
        /* Grabar imagen en base de datos*/
        btnSaveDataBase.addEventListener("click", () => {              
        var base64 = canvasTag.toDataURL('image/jpeg',1);
           $("#imagen").attr("src",base64);
           $("#imagen").show();
           $("#foto").val(base64);              
           document.frmFoto.submit();
        });
        
        /* Descargar ka imagen */
        btnDownloadImage.addEventListener("click", () => {
            var link = document.createElement('a');
            link.download = codigoMaestro+'.jpg';
            link.href = canvasTag.toDataURL('image/jpeg',1);
        	link.click();
		});
	};
</script>
</body>