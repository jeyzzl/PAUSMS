<%@ include file= "../../con_enoc.jsp" %>
<%@ include file="../../conectaadm.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.admision.AdmDocumento"%>

<jsp:useBean id="admDocAlum" scope="page" class="aca.admision.AdmDocAlum"/>
<jsp:useBean id="pgAdmDocAlum" scope="page" class="aca.admision.PgAdmDocAlum"/>
<jsp:useBean id="pgAdmDocAlumU" scope="page" class="aca.admision.PgAdmDocAlumUtil"/>
<jsp:useBean id="Solicitud" scope="page" class="aca.admision.AdmSolicitud" />
<jsp:useBean id="pgAdmArchivos" scope="page" class="aca.admision.PgAdmArchivos" />
<jsp:useBean id="pgAdmArchivosU" scope="page" class="aca.admision.PgAdmArchivosUtil" />

<%
	String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String documentoId	= request.getParameter("documentoId")==null?"0":request.getParameter("documentoId");
	String hoja 		= request.getParameter("hoja")==null?"0":request.getParameter("hoja");
	int accion			= request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
	String pagina		= request.getParameter("pag")==null?"Proceso":request.getParameter("pag");
	
	String respuesta	= "";
		
	admDocAlum.mapeaRegId(conEnoc, folio, documentoId);
	Solicitud.mapeaRegId(conEnoc, folio);
	
	switch(accion){
		case 1:{//Aceptar documento
			admDocAlum.setEstado("A");
			if(admDocAlum.updateReg(conEnoc)){
				out.print("<div class='alert alert-success'><b>Grabado...<a class='btn btn-primary' href='documentos.jsp'>Regresar</a></div>");
				//response.sendRedirect("documentos");				
			}else{
				respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error y no pudo aceptarse el documento. Int&eacute;ntelo nuevamente</b></font>";
			}
		}break;
		case 2:{//NO aceptar documento
			
			conAdm.setAutoCommit(false);
			pgAdmDocAlumU.mapeaRegId(conAdm, folio, documentoId, hoja);
			if(pgAdmDocAlumU.deleteReg(conAdm, folio, documentoId, hoja)){
				conAdm.commit();
				out.print("<div class='alert alert-success'><b>Grabado...<a class='btn btn-primary' href='enviar.jsp?documentoId="+documentoId+"'>Enviar</a></div>");
				//response.sendRedirect("enviar.jsp?documentoId="+documentoId);				
			}else{
				conAdm.rollback();
				respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error y no pudo ser borrado el documento. Int&eacute;ntelo nuevamente</b></font>";
			}
			conAdm.setAutoCommit(true);
			
			if (!pgAdmDocAlumU.existeReg(conAdm, folio, documentoId)){
				pgAdmArchivos.setDocumentoId(documentoId);
				pgAdmArchivos.setFolio(folio);
				if(!pgAdmDocAlumU.existeDocumentos(conAdm, folio, documentoId) && !pgAdmArchivosU.existeReg(conAdm, folio, documentoId) ){
					if (admDocAlum.deleteReg(conEnoc)){				
					}else{
						conEnoc.rollback();
					}
				}
			}
		}break;
	}
%>
<head>
<link href="../admision.css" rel="STYLESHEET" type="text/css">
<script type="text/javascript">	
	function noAceptaDocumento(){
		if(confirm("Desea eliminar el documento?")){
			document.location = "visualizar?Accion=2&documentoId=<%=documentoId%>&hoja=<%=hoja%>";			
		}
	}
</script>
<style>
	#enviar{
		 width:156px;
         height:30px;
         background-color:#A9A9A9;
         border: 2px solid black;
         border-radius: 7px;
         cursor:hand;
         cursor:pointer;
         color:black;
         font-size:14px;
	}
</style>
</head>
<body>
<table class="goback">
	<tr>
		<td><a class="btn btn-primary" href="documentosEnviado?Folio=<%=folio %>&documentoId=<%=documentoId %>&pag=<%= pagina %>" style="text-decoration:none;">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
	</tr>
</table>
<table style="width:100%" align="center">
	<tr>
		<td class="titulo"><spring:message code='aca.VisualizarDocumento'/></td>
	</tr>
	<tr>
		<td class="titulo2">Documento: <%=AdmDocumento.getNombreDocumento(conEnoc, documentoId) %></td>
	</tr>
	<tr>
		<td align="center"><font size="2"><b><%=Solicitud.getNombre()%> <%=Solicitud.getApellidoPaterno()%> <%=Solicitud.getApellidoMaterno()%></b></font></td>
	</tr>
</table>
<br>
<table style="width:80%; margin:0 auto;">
	<tr>
		<td align="center"><img src="imagenDoc.jsp?Folio=<%=folio%>&documentoId=<%=documentoId %>&hoja=<%=hoja%>" width="700px" /></td>
	</tr>
</table>
</body>
<%@ include file="../../cierraadm.jsp"%>
<%@ include file= "../../cierra_enoc.jsp" %>