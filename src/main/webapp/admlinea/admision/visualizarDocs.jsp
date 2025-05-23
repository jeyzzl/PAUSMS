<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "../../conectaadm.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.admision.AdmDocumento"%>
<jsp:useBean id="admDocAlum" scope="page" class="aca.admision.AdmDocAlum" />
<jsp:useBean id="pgAdmDocAlum" scope="page" class="aca.admision.PgAdmDocAlum" />
<jsp:useBean id="pgAdmDocAlumU" scope="page" class="aca.admision.PgAdmDocAlumUtil" />
<jsp:useBean id="solicitud" scope="page" class="aca.admision.AdmSolicitud" />
<%
	String documentoId	= request.getParameter("documentoId");
	String folio 		= request.getParameter("folio");
	String hoja 		= request.getParameter("hoja");
	
	String respuesta	= "";
	int accion			= request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
	
	admDocAlum.mapeaRegId(conEnoc, folio, documentoId);
	solicitud.mapeaRegId(conEnoc, folio);
	
	switch(accion){
		case 1:{//Aceptar documento
			admDocAlum.setEstado("A");
			if(admDocAlum.updateReg(conEnoc)){
				out.print("<div class='alert alert-success'><b>Grabado...<a class='btn btn-primary' href='documentos?folio="+folio+"'>Regresar</a></div>");
				//response.sendRedirect("documentos?folio="+folio);				
			}else{
				respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error y no pudo aceptarse el documento. Int&eacute;ntelo nuevamente</b></font>";
			}
		}break;
		case 2:{//NO aceptar documento
			conEnoc.setAutoCommit(false);
			conAdm.setAutoCommit(false);
			admDocAlum.setEstado("R");
			if(admDocAlum.updateReg(conEnoc)){
				pgAdmDocAlumU.mapeaRegId(conAdm, folio, documentoId, hoja);
				if(pgAdmDocAlumU.deleteReg(conAdm, folio, documentoId, hoja)){
					conEnoc.commit();
					conAdm.commit();
					out.print("<div class='alert alert-success'><b>Grabado...<a class='btn btn-primary' href='documentos?folio="+folio+"'>Regresar</a></div>");
					//response.sendRedirect("documentos?folio="+folio);
				}else{
					conEnoc.rollback();
					conAdm.rollback();
					respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error y no pudo ser borrado el documento. Int&eacute;ntelo nuevamente</b></font>";
				}
			}else{
				respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error y no pudo ser rechazado el documento. Int&eacute;ntelo nuevamente</b></font>";
			}
			conEnoc.setAutoCommit(true);
			conAdm.setAutoCommit(true);
		}break;
	}
%>
<head>
<script type="text/javascript">
	function aceptaDocumento(){
		if(confirm("El documento mostrado es el correcto?")){
			document.location = "visualizarDocs?Accion=1&folio=<%=folio %>&documentoId=<%=documentoId %>";
		}
	}
	
	function noAceptaDocumento(){
		if(confirm("Esta seguro de que el documento no es el correcto?")){
			document.location = "visualizarDocs?Accion=2&folio=<%=folio %>&documentoId=<%=documentoId%>&hoja=<%=hoja%>";			
		}
	}
</script>
</head>
<body>
<table class="goback">
	<tr>
		<td><a href="documentos?folio=<%=folio %>">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
	</tr>
</table>
<%
	if(!respuesta.equals("")){
%>
<table style="margin: 0 auto;">
	<tr>
		<td><%=respuesta %></td>
	</tr>
</table>
<%
	}
%>
<table style="width:80%; margin:0 auto;">
	<tr>
		<td class="titulo"><spring:message code='aca.VisualizarDocumento'/></td>
	</tr>
	<tr>
		<td class="titulo2"><%=AdmDocumento.getNombreDocumento(conEnoc, documentoId) %></td>
	</tr>
</table>
<br>
<table style="width:80%; margin:0 auto;" class="tabla">
	<tr>
		<td width="20%" valign="top">&nbsp;</td>
		<td width="60%"><img src="imagenDoc.jsp?documentoId=<%=documentoId %>&folio=<%=folio%>&hoja=<%=hoja%>" width="500px" /></td>
		<td width="20%" valign="top">
<%
	if(admDocAlum.getEstado().equals("E") && (solicitud.getEstado().equals("4")||solicitud.getEstado().equals("5") ) ){
%>
			<input type="button" class="button" value="Aceptar Documento" onclick="aceptaDocumento();" /><br>
			<input type="button" class="button" value="NO Aceptar Documento" onclick="noAceptaDocumento();" />
<%
	}
%>
			&nbsp;
		</td>
	</tr>
</table>
</body>
<%@ include file= "../../cierraadm.jsp" %>
<%@ include file= "../../cierra_enoc.jsp" %>