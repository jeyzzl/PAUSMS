<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmSolicitud"%>
<%@page import="aca.admision.spring.AdmAcademico"%>
<%@page import="aca.admision.spring.AdmDocAlum"%>
<%@page import="aca.admision.spring.AdmDocumento"%>
<%@page import="aca.admision.spring.AdmRequisito"%>
<%@page import="aca.admision.spring.AdmFormato"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.admision.spring.AdmArchivo"%>
<%@page import="aca.admision.spring.AdmImagen"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<head>
	<script>
			function aprobar(documento, folio, pag){							
				window.location.href="documentos?Accion=1&Folio="+folio+"&DocumentoId="+documento+"&pag="+pag;				
			}
			
			function noAprobar(documento, folio, pag){
				window.location.href="documentos?Accion=2&Folio="+folio+"&DocumentoId="+documento+"&pag="+pag;
			}
			
			function aprobarSinEnvio(documento, folio, pag){				
				window.location.href="documentos?Accion=8&Folio="+folio+"&DocumentoId="+documento+"&pag="+pag;
			}
			
			function noAprobarSinEnvio(documento, folio, pag){
				window.location.href="documentos?Accion=9&Folio="+folio+"&DocumentoId="+documento+"&pag="+pag;
			}
			function cancelar(documento, folio, pag){
				window.location.href="documentos?Accion=3&Folio="+folio+"&DocumentoId="+documento+"&pag="+pag;
			}
			
			function comentar(documento, folio, comentario, pag){
				jQuery("#popupContact").css({
					"height": "50%",
					"width": "50%"
				});
				window.popup.location="documentos?Comentar=1&Folio="+folio+"&DocumentoId="+documento+"&Comentario="+comentario+"&pag="+pag;
			}
			
			function revisarLongitud(){
				var com = document.getElementById('Comentario');
				if(event.keyCode==8) return true;
				if(com.value.length==200) return false;
				else return true;
			}
			
			function refrescarLongitud(){
				var com = document.getElementById('Comentario');
				var cant = document.getElementById('CantComentario');
				cant.innerHTML = 200-com.value.length;
				if(com.value.length>200){
					com.value = com.value.substr(0, 200);
				}
			}
			
			function enviarComentario(documento, folio, pag){
				var texto = document.getElementById('Comentario').value;
				if(texto!=null && texto!="" && texto!=" "){
					window.parent.location.href="documentos?Accion=4&Folio="+folio+"&DocumentoId="+documento+"&pag="+pag+"&Comentario="+document.getElementById('Comentario').value;
				}
				else{
					alert("Write a comment for the student");
				}
			}

			function borrar(folio){
				if (confirm("Do you want to delete the files and pictures from this student?")) { 
					document.location.href="borrar?Folio="+folio;
				}
			}
			
			function borrarExtras(folio, carreraId){
				if (confirm("Do you want to delete the files and pictures that are not required by the course for the applicant?")) { 
					document.location.href="borrarExtras?Folio="+folio+"&CarreraId="+carreraId;
				}
			}
		</script>
</head>
<%	
	AdmDocAlum docAlum		  			= (AdmDocAlum)request.getAttribute("docAlum");
	AdmSolicitud admSolicitud  			= (AdmSolicitud)request.getAttribute("admSolicitud");
	AdmAcademico admAcademico  			= (AdmAcademico)request.getAttribute("admAcademico");	
	CatCarrera catCarrera	  			= (CatCarrera)request.getAttribute("catCarrera");
	String nivelNombre	  				= (String)request.getAttribute("nivelNombre");
	String modalidadNombre				= (String)request.getAttribute("modalidadNombre");
	String asesorNombre					= (String)request.getAttribute("asesorNombre");
	String documentoNombre				= (String)request.getAttribute("documentoNombre");
	boolean existeAsesor				= (boolean)request.getAttribute("existeAsesor");
	boolean existeAdmin					= (boolean)request.getAttribute("existeAdmin");
	boolean existebety					= true;
	boolean docAutorizados 				= (boolean)request.getAttribute("docAutorizado");

	int documentosExtras				= (int)request.getAttribute("documentosExtras");
	
	List<AdmRequisito> lisRequisitos				= (List<AdmRequisito>)request.getAttribute("lisRequisitos");
	HashMap<String, String> mapImagen				= (HashMap<String,String>)request.getAttribute("mapImagen");
	HashMap<String, String> mapArchivo				= (HashMap<String,String>)request.getAttribute("mapArchivo");	
	HashMap<String,AdmDocumento> mapDocumentos		= (HashMap<String,AdmDocumento>)request.getAttribute("mapDocumentos");
	HashMap<String,AdmFormato> mapFormatos			= (HashMap<String,AdmFormato>)request.getAttribute("mapFormatos");
	HashMap<String,AdmDocAlum> mapDocAlum			= (HashMap<String,AdmDocAlum>)request.getAttribute("mapDocAlum");
	HashMap<String, AdmArchivo> mapaArchivo 		= (HashMap<String,AdmArchivo>)request.getAttribute("mapaArchivo");
	HashMap<String,AdmImagen> mapaImagen 			= (HashMap<String,AdmImagen>)request.getAttribute("mapaImagen");
	
	String codigoPersonal 		= session.getAttribute("codigoPersonal").toString();
	String folio 				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String pagina				= request.getParameter("pag")==null?"Proceso":request.getParameter("pag");	
	String accion 				= request.getParameter("Accion")==null ? "" : request.getParameter("Accion");

	// Subir a sesion el folio
	if (!folio.equals("0")){
		session.setAttribute("folio", folio);
	}
	
	String colorBien 	= "green";
	String colorMal 	= "#AE2113";
	String color23 		= "#B0B02F";	
	
	String carreraId = admAcademico.getCarreraId();
	String modalidad = admAcademico.getModalidad();	 
%>
<body>
<div class="container-fluid">
	<h2>Application Requirements<small class="text-muted fs-5"> (Applicant: <%=admSolicitud.getNombre()%> <%=admSolicitud.getApellidoMaterno()==null?"":admSolicitud.getApellidoMaterno()%> <%=admSolicitud.getApellidoPaterno()%>)</small></h2>
	<div class="alert alert-info">
<% 	if(request.getParameter("Comentar")==null){ %>
<% 		if(pagina.equals("Alumnos")){%>
   		<a class="btn btn-primary" href="../proceso/alumnos?Folio=<%=folio %>"><i class="fas fa-arrow-left"></i></a>
<% 		}else if(pagina.equals("Proceso") || pagina.equals("Documentos")){%>
    	<a class="btn btn-primary" href="mostrarProceso?Folio=<%=folio %>"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;    				
<%		}%>
<%-- <% 		if(existeAdmin || existeAsesor || existebety){%> 
			<a class="btn btn-danger" onclick="borrar('<%=folio%>');"><spring:message code="aca.Borrar"/></a>&nbsp;&nbsp;
<% 			if(documentosExtras >= 1){%>			
			Uploaded documents that are not required: <%=documentosExtras%>&nbsp;&nbsp;  				
    			<a class="btn btn-danger" onclick="borrarExtras('<%=folio%>', '<%=admAcademico.getCarreraId()%>');"><spring:message code="aca.Borrar"/> extras</a>
<% 			}
		}
%> --%>
	</div>
	<div class="alert alert-info">
		<b>Level:</b> <%=nivelNombre%>
		&nbsp;&nbsp;
		<b><spring:message code='aca.Carrera'/>:</b> <%=catCarrera.getNombreCarrera()%>
		&nbsp;&nbsp;
		<b>Modality:</b> <%=modalidadNombre%>
		&nbsp;&nbsp;
		<b>Advisor:</b> <font size="2"><b><%=asesorNombre%></b></font>
		&nbsp;&nbsp;
		<b>Status:</b>
	<%	if ( (admSolicitud.getEstado().equals("4") || admSolicitud.getEstado().equals("5")) ) {
			out.print("Authorized"); 
		}else {
			out.print("Processing");
		}%>
	</div>	
	<table class="table table-bordered">
	<thead class="table-info">
		<tr>
			<th width="2%"><b><spring:message code="aca.Numero"/></b></th>
			<th width="26%"><b><spring:message code="aca.Documento"/></b></th>
			<th width="10%"><b><spring:message code="aca.Imagenes"/></b></th>
			<th width="10%"><b><spring:message code="aca.Archivos"/></b></th>
			<th width="14%"><b><spring:message code="aca.Status"/></b></th>
			<th width="12%"><b><spring:message code="aca.Comentario"/></b></th>
			<th width="18%"><b><spring:message code="aca.Autorizacion"/></b></th>
<%-- 			<th width="11%"><strong><spring:message code="aca.Carta"/></strong></th> --%>
		</tr>
	</thead>
<%	

		int cont = 0;
		for(AdmRequisito requisito : lisRequisitos){
			System.out.println(requisito.getDocumentoId());
			boolean existeModalidad = false; 
			String [] modalidades = requisito.getModalidades().split("-");
			for(String mod : modalidades){
				if(mod.equals(modalidad)){
					existeModalidad = true;	
					break;
				}
			}
			if(!existeModalidad) continue;
			else cont++;
			
			boolean coordinador = false;
			boolean mostrar = false;
			if(requisito.getAutorizar().equals("A") && (codigoPersonal.equals(admSolicitud.getAsesorId()) || codigoPersonal.equals(admSolicitud.getAsesorSec()) || codigoPersonal.equals("9800308"))){
				mostrar = true;
			} else if(requisito.getAutorizar().equals("C")){
				if(catCarrera.getCodigoPersonal().equals(codigoPersonal) || codigoPersonal.equals(admSolicitud.getAsesorId()) || codigoPersonal.equals(admSolicitud.getAsesorSec()) || codigoPersonal.equals("9800308")){
					mostrar = true;
				}
				coordinador = true;
			}	
			
			boolean existeDocAlumno = false;
			AdmDocAlum admDocAlum 	= new AdmDocAlum();
			if (mapDocAlum.containsKey(requisito.getDocumentoId())){
				admDocAlum = mapDocAlum.get(requisito.getDocumentoId());
				existeDocAlumno = true;
			}
			
			AdmDocumento admDocumento 	= new AdmDocumento();
			AdmFormato admFormato 		= new AdmFormato();
			boolean existeFormato 		= false;
			if(mapDocumentos.containsKey(requisito.getDocumentoId())){
				admDocumento = mapDocumentos.get(requisito.getDocumentoId());
				if (mapFormatos.containsKey(admDocumento.getFormatoId())){
					admFormato = mapFormatos.get(admDocumento.getFormatoId());
					existeFormato = true;
				}
			}			
		
			boolean enviar = true;
			if(existeDocAlumno && admDocAlum.getEstado().equals("A")){
				enviar = false;
			}
			
			boolean envioDocumento = existeDocAlumno;
			
			String etiquetaCont = "<span class='badge bg-warning rounded-pill'>"+cont+"</span>";
			if (admDocAlum.getEstado().equals("A"))	etiquetaCont = "<span class='badge bg-success rounded-pill'>"+cont+"</span>";
			String fechaImagen = "";
			if(mapaImagen.containsKey(requisito.getDocumentoId()) && mapaImagen.get(requisito.getDocumentoId()).getFecha() != null){
				fechaImagen = mapaImagen.get(requisito.getDocumentoId()).getFecha();
			}

			String fechaArchivo = "";
			if(mapaArchivo.containsKey(requisito.getDocumentoId()) && mapaArchivo.get(requisito.getDocumentoId()).getFecha() != null){
				fechaArchivo = mapaArchivo.get(requisito.getDocumentoId()).getFecha();
			}

	%>
		<tr class="tr2">
			<td align="center"><%=etiquetaCont%></td>
			<td class="ayuda mensaje <%=admDocumento.getComentario()!=null?admDocumento.getComentario():"-" %>"><%=admDocumento.getDocumentoNombre()%></td>
			<td>	
				<!-- IMAGENES -->
		<%					
						
							
			if (mapImagen.containsKey(folio+admDocAlum.getDocumentoId()+"1")){%>
				<a href="imagen?documentoId=<%=admDocAlum.getDocumentoId()%>&hoja=1&pag=Documentos"><span class="badge bg-primary rounded-pill">1</span></a>
<%			}
			if (mapImagen.containsKey(folio+admDocAlum.getDocumentoId()+"2")){%>
				<a href="imagen?documentoId=<%=admDocAlum.getDocumentoId()%>&hoja=2&pag=Documentos"><span class="badge bg-primary rounded-pill">2</span></a>
<%			}
			if (mapImagen.containsKey(folio+admDocAlum.getDocumentoId()+"3")){%>
				<a href="imagen?documentoId=<%=admDocAlum.getDocumentoId()%>&hoja=3"><span class="badge bg-primary rounded-pill">3</span></a>
<%			}
			if (mapImagen.containsKey(folio+admDocAlum.getDocumentoId()+"4")){%>	
				<a href="imagen?documentoId=<%=admDocAlum.getDocumentoId()%>&hoja=4"><span class="badge bg-primary rounded-pill">4</span></a>		
<% 			}
			if (mapImagen.containsKey(folio+admDocAlum.getDocumentoId()+"5")){%>	
				<a href="imagen?documentoId=<%=admDocAlum.getDocumentoId()%>&hoja=5"><span class="badge bg-primary rounded-pill">5</span></a>
<%			}
		 	if (mapImagen.containsKey(folio+admDocAlum.getDocumentoId()+"6")){%>	
				<a href="imagen?documentoId=<%=admDocAlum.getDocumentoId()%>&hoja=6"><span class="badge bg-primary rounded-pill">6</span></a>
<%			}
		 	if (mapImagen.containsKey(folio+admDocAlum.getDocumentoId()+"7")){%>	
				<a href="imagen?documentoId=<%=admDocAlum.getDocumentoId()%>&hoja=7"><span class="badge bg-primary rounded-pill">7</span></a>
<% 			}		
			if(!envioDocumento) out.print("&nbsp;");
			
			if(!fechaImagen.equals("")){
%>
				(<%=fechaImagen%>)
<%				
			}
%>
			</td>
			<td>
			<!-- DOCUMENTO -->
			<%	
										
			if (admSolicitud.getEstado().equals("2")||admSolicitud.getEstado().equals("3")||admSolicitud.getEstado().equals("4")||admSolicitud.getEstado().equals("5")){
				if (mapArchivo.containsKey(folio+admDocAlum.getDocumentoId())){
%>
				<a href="archivo?documentoId=<%= admDocAlum.getDocumentoId() %>">
					<i class="fas fa-download"></i>							
				</a>						
<%				}
			}		
			if(!fechaArchivo.equals("")){
%>
				(<%=fechaArchivo%>)
<%				
			}			 
%>
			</td>					
			<td align="center">
		   		<%	if(!envioDocumento && mostrar){ %>
					<button type="button" class="btn btn-primary" onclick="aprobarSinEnvio('<%=requisito.getDocumentoId()%>', '<%=folio %>', '<%=pagina %>');">Approve</button>
			   		<button type="button" class="btn btn-danger" onclick="noAprobarSinEnvio('<%=requisito.getDocumentoId()%>', '<%=folio %>', '<%=pagina %>');">Disapprove</button>
				<%	}else if(!envioDocumento && !mostrar){
				%>
					<font size="3"><b>-</b></font>
				<%
					}
		   			else if(admDocAlum.getEstado().equals("E") && mostrar){ %>
					<font size="3" color="<%=color23 %>"><b>New</b></font><br>
					<button type="button" class="btn btn-primary" onclick="aprobar('<%=admDocAlum.getDocumentoId() %>', '<%=folio %>', '<%=pagina %>');">Approve</button>
			   		<button type="button" class="btn btn-danger" onclick="noAprobar('<%=admDocAlum.getDocumentoId() %>', '<%=folio %>', '<%=pagina %>');">Disapprove</button>
				<%	}
		   			else if(admDocAlum.getEstado().equals("E")){ %>
						<font size="3" color="<%=color23 %>"><b>New</b></font>
				<%	}
		   			else if(admDocAlum.getEstado().equals("N") && mostrar){ %>
					<table style="width:100%">
						<tr>
							<td align="center" style="border:0px;">
								<font size="3" color="<%=colorMal %>"><b>Not Approved</b></font>
							</td>
							<td style="border:0px;">
								<button type="button" class="btn btn-warning" onclick="cancelar('<%=admDocAlum.getDocumentoId() %>', '<%=folio %>', '<%=pagina %>');">Cancel</button>
							</td>
						</tr>
					</table>
				<%	}
		   			else if(admDocAlum.getEstado().equals("N")){ %>
						<font size="3" color="<%=colorMal %>"><b>Not Approved|</b></font>
				<%	}
	 				else if(admDocAlum.getEstado().equals("A") && mostrar && admSolicitud.getEstado().equals("3")){ %>
					<table style="width:100%">
						<tr>
							<td align="center" style="border:0px;">
								<font size="3" color="<%=colorBien %>"><b>Approved</b></font>
							</td>
							<td style="border:0px;">
								<button type="button" class="btn btn-warning" onclick="cancelar('<%=admDocAlum.getDocumentoId() %>', '<%=folio %>', '<%=pagina %>');">Cancel</button>
							</td>
						</tr>
					</table>
				<%	}
	 				else if(admDocAlum.getEstado().equals("A") && admSolicitud.getEstado().equals("4")){ %>
					<font size="3" color="<%=colorBien %>"><b>Approved</b></font>
				<%	} %>						
			</td>
			<form name="com<%=admDocAlum.getDocumentoId()%>" action="addMensaje" method="post">
			<input name="Folio" type="hidden" value="<%=folio %>" />
			<input name="DocumentoId" type="hidden" value="<%=admDocAlum.getDocumentoId()%>" />
				<td align="center">
				<% 	if(envioDocumento){ %>
				 		<textarea class="form-control" name="Comentario"><%=admDocAlum.getComentario()%></textarea>
				<%		if(mostrar){%>
				 		<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-save"></i></button>
				<%		}%>
				<%	}
					else{
						out.print("&nbsp;");
					}
				%>	
				</td>
			</form>
			<td align="center">
					<b><%=admDocAlum.getUsuario()%></b>
			</td>
		</tr>
<%		}				
%>
		</table>
		<br>
	<%		
		if((!accion.equals("5") && Integer.parseInt(admSolicitud.getEstado())<=3)){ %>
			<table style="margin: 0 auto;">
				<tr>
			    	<td colspan="3" align="center">				    		
			    		<input type="button" class="btn btn-success btn-large" value="Pre-Authorize" onclick="window.location.href='documentos?Folio=<%=folio %>&Accion=10';">	
	<%	if(docAutorizados){
	%>				
			    		<input type="button" class="btn btn-primary btn-large" value="Authorize" onclick="window.location.href='documentos?Folio=<%=folio %>&Accion=5';">					
	<%
		}else{
	%>
						<input type="button" disabled class="btn btn-primary btn-large" value="Authorize">
	<%	}%>
					</td>
			  	</tr>
			</table>
	<%	}
			
	}else{%>
		<script>document.getElementById('clickButton').style.display='none';</script> 
		<table class="table table-condensed" style="width:80%">
			<tr><th colspan="2"><spring:message code='aca.Comentario'/></th></tr>
			<tr><td align="center" colspan="2"><b><%=documentoNombre%></b></td></tr>
			<tr><td>Write a comment: (<b><i><%=admSolicitud.getNombre()%> <%=(admSolicitud.getApellidoPaterno()==null?"":admSolicitud.getApellidoPaterno())%> <%=(admSolicitud.getApellidoMaterno()==null?"":admSolicitud.getApellidoMaterno())%></i></b>)</td>
			<td><b>(<font color="#AE2113" id="CantComentario">200</font>)</b></td></tr>
			<tr><td colspan="2"><textarea class="input" id="Comentario" name="Comentario" cols="50" rows="6" onKeyUp="refrescarLongitud();" onkeyDown="return revisarLongitud();"><%=(request.getParameter("Comentario")==null||request.getParameter("Comentario").equals("null")||request.getParameter("Comentario").trim().equals("-")) ? "" : request.getParameter("Comentario") %></textarea></td></tr>
			<tr><td colspan="2" style="text-align:right;"><input class="btn btn-info" type="button" value="Send" onclick="enviarComentario('<%=request.getParameter("DocumentoId") %>', '<%=folio %>', '<%= pagina %>');"></td></tr>
		</table>
<%	} %>
	<br>
</div>
</body>
<%	if(accion.equals("5")){ %>
		<br>
		<table style="margin: 0 auto;"><tr><td><b>Documents have been authorized correctly!</b></td></tr></table>
<%-- 		<meta http-equiv="refresh" content="1;url=mostrarProceso?Folio=<%=folio %>" /> --%>
<%	} %>
</html>