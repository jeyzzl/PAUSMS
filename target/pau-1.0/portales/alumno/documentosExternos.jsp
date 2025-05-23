<%@ page import= "java.io.File"%>
<%@ page import= "java.util.Base64"%>
<%@ page import= "java.util.ArrayList"%>

<%@ include file= "../../conectadbp.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

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
			
			function guardar(){
				if($("archivo").value != ""){
					$("archivo").hidden = true;
					$("btnGuardar").disabled = true;
					$("btnGuardar").value = "Saving...";
					document.formaEnviar.submit();
				}else
					alert("Select at least one document");
			}
		</script>
	</head>
<%
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String matricula 		= (String) session.getAttribute("codigoAlumno");
	File carpeta 			= new File(application.getRealPath("residencia/docext/"+matricula));
	
	ArrayList<aca.pg.archivo.ArchResidencia> lisImagenes = imagenU.getLista(conn2, matricula, " ORDER BY FOLIO");
	
	// Borrar imagen
	if(accion.equals("1")){
		String folio = request.getParameter("Folio");
		if (imagenU.deleteReg(conn2, matricula, folio)){
			
			// actualizar la lista
			lisImagenes = imagenU.getLista(conn2, matricula, " ORDER BY FOLIO");
		}
	}
	
	// Mostrar galeria
	if(!accion.equals("2")){ %>
		<body>
		<div class="container-fluid">
			<h2>Residence documents</h2>
			<div class="alert alert-info">
			<a class="btn btn-primary" href="documentos"><spring:message code="aca.Regresar"/></a>
			</div>			
		<%	
			if( lisImagenes.size() > 0){
		%>
				<div id="gallery" style="width:80%;">
					<div style="padding-left:50px;position:relative;">
						<div id="gallery_nav">
						<%	
							int row = 0;
							for(aca.pg.archivo.ArchResidencia archivo : lisImagenes){
								byte[] img = archivo.getImagen();
								String foto = Base64.getEncoder().encodeToString(img);
						%>								
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
							for(aca.pg.archivo.ArchResidencia archivo : lisImagenes){
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
		<%	}
			else{ %>
				<div align="center"><h3><b>The student has no documents</b></h3></div>
		<%	} %>
		</div>
		</body>
<%	}	 %>
</html>
<%@ include file= "../../cierradbp.jsp" %>