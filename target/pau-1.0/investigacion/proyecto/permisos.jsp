<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
	String proyectoId 		= request.getParameter("proyectoId")==null?"":request.getParameter("proyectoId");
	String accion 			= request.getParameter("Accion")==null?"":request.getParameter("Accion");
	
	String nombreArchivo	= (String)request.getAttribute("nombreArchivo");
	String nombreArchivo2	= (String)request.getAttribute("nombreArchivo2");
	String nombreArchivo3	= (String)request.getAttribute("nombreArchivo3");
	String nombreArchivo4	= (String)request.getAttribute("nombreArchivo4");
	String nombreArchivo5	= (String)request.getAttribute("nombreArchivo5");
	String nombreArchivo6	= (String)request.getAttribute("nombreArchivo6");
	String nombreArchivo7	= (String)request.getAttribute("nombreArchivo7");
	String nombreArchivo8	= (String)request.getAttribute("nombreArchivo8");
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="../../js-plugins/bootstrap-fileupload/bootstrap-fileupload.min.css" />
	<script src="../../js-plugins/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>		
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		
	<style type="text/css">
		.file {
			width: 80%;
		}
	</style>
</head>
<body>
<div class="container-fluid">
	<h2>Formato para el registro y evaluación de proyectos</h2>
	<div class="alert alert-info">		
		<a href="proyecto" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
<%
				int temp = 0;
				if(request.getParameter("archivoC") != "" 
				&& request.getParameter("archivoA") != ""){
					temp = 1;
				}
%>
	</div> 	
		<hr>
		<ul class="nav nav-tabs">
	  		<li class="nav-item">
		    	<a class="nav-link" href="accion?Accion=3&proyectoId=<%=proyectoId%>">Registro</a>
		  	</li>
		  	<li class="nav-item">
		    	<a class="nav-link active" aria-current="page" href="#">Documentos</a>
		  	</li>
		</ul>
		<hr>
		<h3><div class="alert alert-warning">A continuación se encuentra la guía para la elaboración de proyectos y formatos de sometimientos para los comités, los cuáles deberán entregarse en físico con firma del investigador principal así como los formatos de consentimiento informado para adultos, niños y padres e hijos.</div></h3>
  			<div class="alert alert-info">
  				<div><h4>Formato de Proyecto</h4>
  					<a href="archivos/FormatoProyecto.docx" target="blank" title="Descargar el formato"><i class="fas file-alt-word-o" style="font-size:48px;color:blue"s></i>Formato Proyecto</a>&nbsp;&nbsp;&nbsp;&nbsp;
  					<a href="archivos/EjemploProyecto.docx" target="blank" title="Descargar el formato"><i class="fas file-alt-word-o" style="font-size:48px;color:blue"></i>Ejemplo Proyecto</a>&nbsp;&nbsp;&nbsp;&nbsp;  				
  				</div><br>
  				<div><h4>Formato de Protocolo</h4>
  					<a href="archivos/FormatoProtocolo.docx" target="blank" title="Descargar el formato"><i class="fas file-alt-word-o" style="font-size:48px;color:blue"s></i>Formato Protocolo</a>&nbsp;&nbsp;&nbsp;&nbsp;
  				</div>
  				<br><br>
  				<div><h4>Sometimientos a Comités</h4>
	  				<a href="archivos/SometimientoCII.docx" target="blank" title="Descargar el formato"><i class="fas file-alt-word-o" style="font-size:48px;color:blue"></i>Sometimiento CII</a>&nbsp;&nbsp;&nbsp;&nbsp;
	  				<a href="archivos/SometimientoCEI-A.docx" target="blank" title="Descargar el formato"><i class="fas file-alt-word-o" style="font-size:48px;color:blue""></i>Sometimiento CEI-A</a>&nbsp;&nbsp;&nbsp;&nbsp;
	  				<a href="archivos/SometimientoCEI-B.docx" target="blank" title="Descargar el formato"><i class="fas file-alt-word-o" style="font-size:48px;color:blue"></i>Sometimiento CEI-B</a>&nbsp;&nbsp;&nbsp;&nbsp;
  				</div>
  				<br><br> 				
  				<div><h4>Formatos de Consentimiento Informado</h4>
	  				<a href="archivos/ConsentimientoMayorEdad.docx" target="blank" title="Descargar el formato"><i class="fas file-alt-word-o" style="font-size:48px;color:blue"></i>Consentimiento-mayor de edad</a>&nbsp;&nbsp;&nbsp;&nbsp;
	  				<a href="archivos/AsentimientoMenoresEdad.docx" target="blank" title="Descargar el formato"><i class="fas file-alt-word-o" style="font-size:48px;color:blue"></i>Asentimiento-menor de edad</a>&nbsp;&nbsp;&nbsp;&nbsp;	  				
	  				<a href="archivos/AsentimientoNinos.docx" target="blank" title="Descargar el formato"><i class="fas file-alt-word-o" style="font-size:48px;color:blue"></i>Asentimiento-niños</a>&nbsp;&nbsp;&nbsp;&nbsp;	  				
	  				<a href="archivos/ConsentimientoPadresHijos.docx" target="blank" title="Descargar el formato"><i class="fas file-alt-word-o" style="font-size:48px;color:blue"s></i>Consentimiento-Padres/Hijos</a>&nbsp;&nbsp;&nbsp;&nbsp;
  				</div>
  				<br><br> 				
  				<div><h4>Rúbricas de Evaluación</h4>
  					<a href="archivos/RubricasCEI-Consentimiento.pdf" target="blank" title="Descargar el formato"><i class="fas file-alt-pdf-o" style="font-size:48px;color:red"></i>Rúbricas CEI-Consentimiento</a>&nbsp;&nbsp;&nbsp;&nbsp;  				
  					<a href="archivos/RubricasCEI-Protocolo.pdf" target="blank" title="Descargar el formato"><i class="fas file-alt-pdf-o" style="font-size:48px;color:red"></i>Rúbricas CEI-Protocolo</a>&nbsp;&nbsp;&nbsp;&nbsp;  				
  					<a href="archivos/RubricasCII-Protocolo.pdf" target="blank" title="Descargar el formato"><i class="fas file-alt-pdf-o" style="font-size:48px;color:red"></i>Rúbricas CII- Protocolo</a>&nbsp;&nbsp;&nbsp;&nbsp;  				
  				</div>
  			</div>
  			<div class="panel panel-info">
				<div class="panel-heading"><h3>Documento en extenso</h3></div>
 				<div class="panel-body">
	 				<div class="col-sm-4">
		  				<form name="frmDocumento" id="frmDocumento" enctype="multipart/form-data" action="saveFile?proyectoId=<%=proyectoId%>&folio=8" method="post">
		  					<label class="control-label"><h4>Documento<small class="text-muted">( <%=nombreArchivo8%> )</h4></strong></label>
							<div class="fileupload fileupload-new" data-provides="fileupload">
							  	<div class="fileupload-new thumbnail file" style="height:30px;"></div>
							  	<div class="fileupload-preview fileupload-exists thumbnail" style="height: 30px;"></div>
							  	<span class="btn btn-file btn-primary">
								  	<span class="fileupload-new"><i class="fas fa-upload"></i> Subir</span>
								  	<span class="fileupload-exists">Cambiar <i class="fas fa-random"></i></span>
								  	<input type="file" id="archivo" name="archivo" />
							  	</span>
						  		<a href="#" class="btn btn-danger fileupload-exists" data-dismiss="fileupload">Quitar <i class="fas fa-times"></i></a>
							</div>
							<button type="button" id="btnGuardar" class="btn btn-success" onclick="guardarDoc();"><i class="fas fa-save"></i> Guardar</button>
						<% if (!nombreArchivo8.equals("-")){%>							
 		  					<a href="borrarDoc?proyectoId=<%=proyectoId%>&folio=8" class="btn btn-danger" title="Borrar archivo"><i class="fas fa-trash-alt"></i> Borrar</a>
 		  				<%	} %>	
		  				</form>
		  			</div>
 				</div>
 			</div>			
 			
		</div>
</body>
<script>
	function guardarDoc(){
		if(document.frmDocumento.archivo.value != ""){
			document.frmDocumento.btnGuardar.disabled = true;
			document.frmDocumento.btnGuardar.value = "Guardando...";			
			document.getElementById("frmDocumento").submit();
		}else
			alert("¡Selecciona un documento!");
	}
	
	function guardar(){
		if(document.formaEnviar.archivo.value != ""){
			document.formaEnviar.btnGuardar.disabled = true;
			document.formaEnviar.btnGuardar.value = "Guardando...";			
			document.getElementById("formaEnviar").submit();
		}else
			alert("¡Selecciona un documento!");
	}
	
	function guardarPadres(){		
		if(document.formaPadres.archivo.value != ""){			
			document.formaPadres.btnPadre.disabled = true;			
			document.formaPadres.btnPadre.value = "Guardando...";		
			document.formaPadres.submit();			
		}else
			alert("¡Selecciona un documento!");
	}
	
	function guardarConOral(){
		if(document.formaConO.archivo.value != ""){
			
			document.formaConO.btnOral.disabled = true;			
			document.formaConO.btnOral.value = "Guardando...";			
			document.formaConO.submit();			
		}else
			alert("¡Selecciona un documento!");
	}
	
	function guardarAsen(){
		if(document.formaAsen.archivo.value != ""){
			document.formaAsen.btnGuardarA.disabled = true;
			document.formaAsen.btnGuardarA.value = "Guardando...";
			document.formaAsen.submit();
		}else 
			alert("¡Selecciona un documento!");
	}	
	
	function guardarConf(){
		if(document.formaConfi.archivo.value != ""){
			document.formaConfi.guardaConf.disabled = true;
			document.formaConfi.guardaConf.value = "Guardando...";
			document.formaConfi.submit();
		}else
			alert("¡Selecciona un documento!");
	}
	
	function guardarFV(){
		if(document.formaConFV.archivo.value != ""){
			document.formaConFV.guardarFotos.disabled = true;
			document.formaConFV.guardarFotos.value = "Guardando...";
			document.formaConFV.submit();
		}else
			alert("¡Selecciona un documento!");
	}
</script>

</html>