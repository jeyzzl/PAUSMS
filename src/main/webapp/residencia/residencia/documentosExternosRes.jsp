<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "java.io.File"%>

<jsp:useBean id="imagen" scope="page" class="aca.pg.archivo.ArchResidencia"/>
<jsp:useBean id="imagenU" scope="page" class="aca.pg.archivo.ArchResidenciaUtil"/>

<html>
	<head>
		<link href="../../archivo/subir_documentos/digital/jquery.si.css" rel="stylesheet" type="text/css" />
		<script src="../../archivo/subir_documentos/digital/FILEjquery-1.3.1.min.js" type="text/javascript"></script>
		<script src="../../archivo/subir_documentos/digital/jquery.si.js" type="text/javascript"></script>
		<style>
			#gallery_nav {
				float: left;
				width: 250px;
				height: 92%;
				text-align: center;
				overflow: auto;
			}
			#gallery_output {
				float: left;
			 	width: 730;
				overflow: auto;
			}
			#gallery_output img {
				display: block;
				margin: 0px auto 0 auto;
			}
		</style>
		<script type="text/javascript">
			jQuery.noConflict();
			jQuery(document).ready(function() {
				jQuery("#gallery_output img").not(":first").hide();
				jQuery("#gallery a").click(function() {
					if ( jQuery("#" + this.rel).is(":hidden") ) {
						jQuery("#gallery_output img").slideUp();
						jQuery("#" + this.rel).slideDown();
					}
				});
			});
			function borrarImagen(){
				if(confirm("Do you want to delete this document?")){
					var arr = jQuery("#gallery_output img");
					for(var i=0; i<arr.length; i++){
						if(arr[i].style.display!="none"){
							var arr2 = arr[i].src.split('/');
							document.location.href = "documentosExternos?Accion=1&NombreImg="+(arr2[arr2.length-1].split('%20')[1]);
						}
					}
				}
			}
			function activarSubir(numSiguiente){
				jQuery("#popupContact").css({
					"height": "25%",
					"width": "30%"
				});
				window.popup.location="documentosExternos?Accion=2&NumSiguiente="+numSiguiente;
			}
			jQuery(document).ready(function() {
				jQuery("input.file").si();
			});
			function guardar(){
				if($("archivo").value != ""){
					$("archivo").hidden = true;
					$("btnGuardar").disabled = true;
					$("btnGuardar").value = "Saving...";
					document.formaEnviar.submit();
				}else
					alert("Select at least one file");
			}
		</script>
	</head>
<%
	String accion = request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String matricula = (String) session.getAttribute("codigoAlumno");
	File carpeta = new File(application.getRealPath("residencia/docext/"+matricula));
	
	// Numero de archivo a grabar
	int numSiguiente = 0;	
	
	// Busca el numero siguiente del archivo en la carpeta 
	if (carpeta.exists()&&carpeta.list().length>0){
		String[] listaArchivos = carpeta.list();
	    for(int j=0; j<listaArchivos.length; j++){
	    	if (numSiguiente < Integer.parseInt(listaArchivos[j].substring(8,11)))
	    		numSiguiente = Integer.parseInt(listaArchivos[j].substring(8,11));	
	    }
	    numSiguiente = numSiguiente+1;    
	}
		
	
	int numTmp = 0;
	
	if(accion.equals("1")){
		String nombreImg = request.getParameter("NombreImg");
		if(carpeta.exists()&&carpeta.list().length!=0){
			if(!new File(carpeta.getPath()+"//"+matricula+" "+nombreImg).delete()){
				%><script>alert("Error deleting document, try again")</script><%
			}
			if(carpeta.list().length==0) carpeta.delete();
		}
	}
	
	if(!accion.equals("2")){ %>
		<body>
		<div class="container-fluid">
			<h2>Residence documents</h2>
			<div class="alert alert-info">
			<a class="btn btn-primary" href="residencia"><spring:message code="aca.Regresar"/></a>
			</div>			
			<p>Upload: <img title="Upload" src="../../imagenes/upload.gif" class="btn clickButton2" title="Upload Image" onclick="activarSubir('<%=numSiguiente%>');" /></p>
		<%	if(carpeta.exists()&&carpeta.list().length!=0){
				String [] nombreArchivos = carpeta.list(); %>
				<div id="gallery" style="width:80%;">
					<div style="padding-left:50px;position:relative;">
						<div id="gallery_nav">
						<%	
							for(int i=0; i<nombreArchivos.length; i++){
								String nombre = nombreArchivos[i];
								int num = Integer.parseInt(nombre.replaceAll(matricula,"").trim().split("\\.")[0]);
								if(num>numTmp)numTmp = num+1;
						%>
								<a rel="img<%=i%>" href="javascript:;"><img width="95%" style="border:2px solid #000;border-radius:6px;" src="../docext/<%=matricula%>/<%=nombre%>" /></a>
								<table><tr><td></td></tr></table>
						<%	} %>
						</div>
					</div>
					<div style="text-align:center;width:730px;top:-20px;padding-left:320px;position:relative;">
						<img title="Delete" src="../../imagenes/no.png" style="position:relative;top:-6px;height:16px;" class="btn btn-danger" title="Delete Image" onclick="borrarImagen();" />
						<a href="javascript:borrar()">Delete</a>
						<br>
						<div id="gallery_output" style="border:2px solid;border-radius:7px;">
						<%	for(int i=0; i<nombreArchivos.length; i++){
								String nombre = nombreArchivos[i]; %>
								<img id="img<%=i%>" width="100%" src="../docext/<%=matricula%>/<%=nombre%>" />
						<%	} %>
						</div>
				 	</div>
				</div>
		<%	}
			else{ %>
				<div align="center"><h3><b>The student has no residence documents</b></h3></div>
		<%	} %>
		</div>
		</body>
<%	}
	else{ %>
		<body>
		<div class="container-fluid">
		<h2>Residence Documents</h2>
			<div class="alert alert-info">&nbsp;
			</div>
			<form name="formaEnviar" id="formaEnviar" enctype="multipart/form-data" action="guardarArchivo?NumSiguiente=<%=request.getParameter("NumSiguiente")%>" method="post">
				<br>
				<table style="width:95%" align="center" class="table table-condensed table-nohover">
					<tr>
						<th>Upload files in batch </th>
					</tr>
					<tr>
						<td style="text-align:center;">
							<input style="position:absolute;cursor:pointer;" type="file" multiple accept="image/jpeg" class="file" id="archivo" name="archivo" />
						</td>
					</tr>
					<tr>
						<td style="text-align:center;">
							<input class="btn btn-primary" type="button" id="btnGuardar" value="Send documents" onclick="guardar();"/>
						</td>
					</tr>
				</table>
			</form>
			</div>
		</body>
<%	} %>
</html>