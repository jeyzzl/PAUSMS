<%@ page import= "java.io.File"%>
<%@ page import= "java.util.Base64"%>
<%@ page import= "java.util.List"%>
<%@ page import= "aca.pg.archivo.spring.PosArchResidencia"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
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
			function borrarImagen(folio){
				if(confirm("Do you want to delete this document?")){										
					document.location.href = "borrarArchivo?Folio="+folio;
				}
			}

			function guardar(){
				if($("archivo").value != ""){
					$("archivo").hidden = true;
					$("btnGuardar").disabled = true;
					$("btnGuardar").value = "Saving...";
					document.formaEnviar.submit();
				}
			}
	
		</script>
	</head>
<%
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String matricula 		= (String) session.getAttribute("codigoAlumno");
	File carpeta 			= new File(application.getRealPath("residencia/docext/"+matricula));
	String folio 			= request.getParameter("Folio");
	
	List<PosArchResidencia> lisImagenes = (List<PosArchResidencia>)request.getAttribute("lisImagenes");	
 %>
		<body>
		<div class="container-fluid">
			<h2>Residence Documents</h2>
			<div class="alert alert-info">
			<form name="formaEnviar" id="formaEnviar" enctype="multipart/form-data" action="guardarArchivo" method="post">
			<a class="btn btn-primary" href="residencia"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<span class="btn btn-file">
		  		<input type="file" id="archivo" name="archivo" required="required"/>
		 	</span>&nbsp;&nbsp;	
		 	<input class="btn btn-primary" type="button" id="btnGuardar" value="Save" onclick="guardar();"/> 
	 		</form>	
			</div><br>			
		<%	
			if( lisImagenes.size() > 0){
		%>
				<div id="gallery" style="width:80%;">
					<div style="padding-left:50px;position:relative;">
						<div id="gallery_nav">
						<%	
							int row = 0;
							for(PosArchResidencia archivo : lisImagenes){
								byte[] img = archivo.getImagen();
								String foto = Base64.getEncoder().encodeToString(img);
						%>
								<a href="javascript:borrarImagen('<%=archivo.getFolio()%>')"><img title="Delete" src="../../imagenes/no.png" class="btn btn-danger" title="Delete image" /></a>
								<a rel="img<%=row%>" href="javascript:;"><img width="95%" style="border:2px solid #000;border-radius:6px;" src="data:image/jpg;base64,<%=foto%>"/></a>
								<table><tr><td></td></tr></table>
						<%		row++;
							} 
						%>
						</div>
					</div>
					<div style="text-align:center;width:730px;top:-20px;padding-left:320px;position:relative;">						
						<div id="gallery_output" style="border:2px solid;border-radius:7px;">
						<%	
							row = 0;
							for(PosArchResidencia archivo : lisImagenes){
								byte[] img = archivo.getImagen();
								String foto = Base64.getEncoder().encodeToString(img);
						 %>
								<img id="img<%=row%>" width="100%" src="data:image/jpg;base64,<%=foto%>" />
						<%
							row++;
						}
%>
				 	</div>
				 </div>
			</div>
<%		}else{ %>
			<div align="center"><h3><b>The student has no residence documents</b></h3></div>
<%		} %>
	</div>
	</body>
</html>