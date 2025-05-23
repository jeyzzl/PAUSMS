<%@page import="aca.archivo.spring.ArchEntrega"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String codigoAlumno		= (String) request.getAttribute("codigoAlumno");	
	String mensaje			= (String) request.getAttribute("mensaje");
	String folio			= (String) request.getAttribute("folio");
	String imagen			= (String) request.getAttribute("imagen");
	String opcion			= (String) request.getAttribute("opcion");	
	ArchEntrega archEntrega	= (ArchEntrega) request.getAttribute("archEntrega");
%>
<script type="text/javascript">
	function SubirImagenes(){	
		 document.forma.submit();
	}
	
	function borrar(folio,opcion,imagen){
		if(confirm("¿Estás Seguro que Deseas Eliminar Esta Imagen?")){
			location.href = "borrarImagen?Folio="+folio+"&Imagen="+imagen+"&Opc="+opcion; 
		}
	}
</script>
<div class="container-fluid">
	<h1>Subir <%=imagen%></h1>
	<div class="alert alert-info d-flex align-items-center">	
		<a class="btn btn-primary" href="documentos"><i class="fas fa-arrow-left"></i></a>
	</div>
<% 	if(mensaje.equals("1")){%>
	<div class="alert alert-success">Guardada</div>
<%  }else if(!mensaje.equals("-")){%>
	<div class="alert alert-alert"><%=mensaje%></div>        
<%  } %>
	<div class="span3">
		<div class="thumbnail">
<%				if(imagen.equals("Identificacion")){
					if (archEntrega.getIdentificacion() != null){%>
						<img src="imagen?Folio=<%=folio%>&Opcion=1" style="width: 100%;">
<%					} 
				}else if(imagen.equals("Poder")){
					if (archEntrega.getPoder() != null){%>
						<img src="imagen?Folio=<%=folio%>&Opcion=2" style="width: 100%;">
<%					} 
				}else if(imagen.equals("Envio")){
					if (archEntrega.getEnvio() != null){%>
						<img src="imagen?Folio=<%=folio%>&Opcion=3" style="width: 100%;">
<%					} 
				}else if(imagen.equals("Firma")){
					if (archEntrega.getFirma() != null){%>
						<img src="imagen?Folio=<%=folio%>&Opcion=4" style="width: 100%;">
<%					} 
				}else if(imagen.equals("Extra")){
					if (archEntrega.getExtra() != null){%>
						<img src="imagen?Folio=<%=folio%>&Opcion=5" style="width: 100%;">
<%					} 
				}%>	
		 	<div class="caption">
	            <p style="margin-bottom:0;">
<%				if(imagen.equals("Identificacion")){
					if (archEntrega.getIdentificacion() != null){%>
						<a href="javascript:borrar('<%=folio%>','<%=opcion%>','<%=imagen%>');" class="btn btn-primary btn-sm"><i class="fas fa-trash-alt icon-white"></i></a>
<%					} 
				}else if(imagen.equals("Poder")){
					if (archEntrega.getPoder() != null){%>
						<a href="javascript:borrar('<%=folio%>','<%=opcion%>','<%=imagen%>');" class="btn btn-primary btn-sm"><i class="fas fa-trash-alt icon-white"></i></a>
<%					} 
				}else if(imagen.equals("Envio")){
					if (archEntrega.getEnvio() != null){%>
						<a href="javascript:borrar('<%=folio%>','<%=opcion%>','<%=imagen%>');" class="btn btn-primary btn-sm"><i class="fas fa-trash-alt icon-white"></i></a>
<%					} 
				}else if(imagen.equals("Firma")){
					if (archEntrega.getFirma() != null){%>
						<a href="javascript:borrar('<%=folio%>','<%=opcion%>','<%=imagen%>');" class="btn btn-primary btn-sm"><i class="fas fa-trash-alt icon-white"></i></a>
<%					} 
				}else if(imagen.equals("Extra")){
					if (archEntrega.getExtra() != null){%>
						<a href="javascript:borrar('<%=folio%>','<%=opcion%>','<%=imagen%>');" class="btn btn-primary btn-sm"><i class="fas fa-trash-alt icon-white"></i></a>
<%					} 
				}%>	
	            </p>
	    	</div>
		</div>
		<form name="forma" enctype="multipart/form-data" action="accion?Folio=<%=folio%>&Opc=<%=opcion%>&Imagen=<%=imagen%>&CodigoAlumno=<%=codigoAlumno%>" method="post">
			<input type="hidden" id="folder" name="folder" value="test"/>	
<%				if(imagen.equals("Identificacion")){
					if (archEntrega.getIdentificacion() == null){%>
						<div class="input-group"> 
<%  					if(opcion.equals("1")){%>
							<input type="file" name="files[]" id="files" multiple="multiple"><br>
							<a href="javascript:SubirImagenes();" class="btn btn-primary"><i class="icon-arrow-up icon-white"  ></i> Subir</a>
<% 						}else if(opcion.equals("2")){%>
							<input type="file" accept="image/*" capture="camera" name="files[]" id="files" multiple="multiple"><br>
							<a href="javascript:SubirImagenes();" class="btn btn-primary">Guardar</a>
<% 						}%>
						</div>
<%					} 
				}else if(imagen.equals("Poder")){
					if (archEntrega.getPoder() == null){%>
						<div class="input-group"> 
<%  					if(opcion.equals("1")){%>
							<input type="file" name="files[]" id="files" multiple="multiple"><br>
							<a href="javascript:SubirImagenes();" class="btn btn-primary"><i class="icon-arrow-up icon-white"  ></i> Subir</a>
<% 						}else if(opcion.equals("2")){%>
							<input type="file" accept="image/*" capture="camera" name="files[]" id="files" multiple="multiple"><br>
							<a href="javascript:SubirImagenes();" class="btn btn-primary">Guardar</a>
<% 						}%>
						</div>
<%					} 
				}else if(imagen.equals("Envio")){
					if (archEntrega.getEnvio() == null){%>
						<div class="input-group"> 
<%  					if(opcion.equals("1")){%>
							<input type="file" name="files[]" id="files" multiple="multiple"><br>
							<a href="javascript:SubirImagenes();" class="btn btn-primary"><i class="icon-arrow-up icon-white"  ></i> Subir</a>
<% 						}else if(opcion.equals("2")){%>
							<input type="file" accept="image/*" capture="camera" name="files[]" id="files" multiple="multiple"><br>
							<a href="javascript:SubirImagenes();" class="btn btn-primary">Guardar</a>
<% 						}%>
						</div>
<%					} 
				}else if(imagen.equals("Firma")){
					if (archEntrega.getFirma() == null){%>
						<div class="input-group">
<%  					if(opcion.equals("1")){%>
							<input type="file" name="files[]" id="files" multiple="multiple"><br>
							<a href="javascript:SubirImagenes();" class="btn btn-primary"><i class="icon-arrow-up icon-white"  ></i> Subir</a>
<% 						}else if(opcion.equals("2")){%>
							<input type="file" accept="image/*" capture="camera" name="files[]" id="files" multiple="multiple"><br>
							<a href="javascript:SubirImagenes();" class="btn btn-primary">Guardar</a>
<% 						}%>
						</div>
<%					} 
				}else if(imagen.equals("Extra")){
					if (archEntrega.getExtra() == null){%>
						<div class="input-group"> 
<%  					if(opcion.equals("1")){%>
							<input type="file" name="files[]" id="files" multiple="multiple"><br>
							<a href="javascript:SubirImagenes();" class="btn btn-primary"><i class="icon-arrow-up icon-white"></i> Subir</a>
<% 						}else if(opcion.equals("2")){%>
							<input type="file" accept="image/*" capture="camera" name="files[]" id="files" multiple="multiple"><br>
							<a href="javascript:SubirImagenes();" class="btn btn-primary">Guardar</a>
<% 						}%>
						</div>
<%					} 
				}%>				
		</form>
	</div>
</div>