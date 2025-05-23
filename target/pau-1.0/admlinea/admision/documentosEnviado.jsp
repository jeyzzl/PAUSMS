<%@ include file= "../../con_enoc.jsp" %>
<%@ include file="../../conectaadm.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="RequisitoU" scope="page" class="aca.admision.RequisitoUtil" />
<jsp:useBean id="AdmDocAlum" scope="page" class="aca.admision.AdmDocAlum" />
<jsp:useBean id="pgAdmDocAlum" scope="page" class="aca.admision.PgAdmDocAlum" />
<jsp:useBean id="pgAdmDocAlumU" scope="page" class="aca.admision.PgAdmDocAlumUtil" />
<jsp:useBean id="pgAdmArchivos" scope="page" class="aca.admision.PgAdmArchivos" />
<jsp:useBean id="pgAdmArchivosU" scope="page" class="aca.admision.PgAdmArchivosUtil" />
<jsp:useBean id="Solicitud" scope="page" class="aca.admision.AdmSolicitud" />

<%@page import="aca.admision.AdmDocumento"%>

<head></head>
<%
	String folio 		= request.getParameter("Folio"); 
	String documentoId 	= request.getParameter("documentoId");
	String pagina		= request.getParameter("pag")==null?"Proceso":request.getParameter("pag");
	
	Solicitud.mapeaRegId(conEnoc, folio);
%>
<body>
	<table class="goback"><tr><td><a class="btn btn-primary" href="documentos?Folio=<%=folio %>&pag=<%= pagina %>">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td></tr></table>
	<br>
	<table style="margin: 0 auto;  width:100%">
		<tr>
			<td class="titulo">Docummento <%=aca.admision.AdmDocumento.getNombreDocumento(conEnoc, documentoId) %></td>
		</tr>
		<tr>
			<td align="center"><font size="2"><b><%=Solicitud.getNombre()%> <%=Solicitud.getApellidoPaterno()%> <%=Solicitud.getApellidoMaterno()%></b></font></td>
		</tr>
	</table>
	<br>
	<table  align="center" class="tabla" width="400px">
		<tr>
			<td align="center"><b><font size="5"><spring:message code="aca.Documento"/>:</font></b></td>
		</tr>
		<!-- ----------------------DOCUMENTO -------------------- -->
		<tr><td>&nbsp;</td></tr>
	<%	pgAdmArchivos.setFolio(folio);
		pgAdmArchivos.setDocumentoId(documentoId);
		if(pgAdmArchivosU.existeReg(conAdm, folio, documentoId)){
			pgAdmArchivosU.mapeaRegId(conAdm, folio, documentoId);%>
			<tr>
				<td align="center">
					<a style="text-decoration:none;" href="bajar?Folio=<%=folio %>&documentoId=<%=documentoId %>"title="Descargar archivo">
						<table style="width:150px">
							<tr>
								<td width="10%"><img src="../../imagenes/document.png" alt="descargar" width="30px"></td>
								<td><font size="5"><spring:message code="aca.Descargar"/></font></td>
							</tr>
						</table>
					</a> 
				</td>
			</tr>
	<%	} %>
		<tr><td>&nbsp;</td></tr>
	</table>
	<br>
	<br>
	<table style="margin: 0 auto;" class="tabla" width="400px">
		<tr>
			<td align="center" colspan="2"><b><font size="5"><spring:message code="aca.Imagenes"/>:</font></b></td>
		</tr>
	<%	
		int contador = 0;
		for(int i=1; i<=7; i++){
			pgAdmDocAlum.setFolio(folio);
			pgAdmDocAlum.setDocumentoId(documentoId);
			pgAdmDocAlum.setHoja(i+"");
			
			if (pgAdmDocAlumU.existeReg(conAdm, folio, documentoId, String.valueOf(i))) {
				contador = Integer.parseInt(pgAdmDocAlum.getHoja());
			}
		}
		boolean activar = false;
		
		String SnumImg = request.getParameter("num")==null?"2":request.getParameter("num");
		int numImg = Integer.parseInt(SnumImg);
		if(contador>numImg){
			numImg=contador;
			activar=true;
		}
		if(request.getParameter("num")==null) activar=true;
		if(contador==7) activar=false;
		
		for(int i=1; i<=numImg; i++){ %>
		<!-- ----------------------IMAGENES-------------------- -->
			<tr>
				<td style="padding-left:40px;" align="center"><font size="4"><%=i%></font></td>
				<td colspan="2">
				<%	pgAdmDocAlum.setFolio(folio);
					pgAdmDocAlum.setDocumentoId(documentoId);
					pgAdmDocAlum.setHoja(i+"");
					
					if(pgAdmDocAlumU.existeReg(conAdm, folio, documentoId)){ %>
						<a href="visualizar?Folio=<%=folio %>&documentoId=<%=documentoId%>&hoja=<%=i%>&pag=<%= pagina %>" title="Mostrar">
							<img src="../../imagenes/img.png"alt="Borrar" width="30px" >
							&nbsp;
							<font size="5">Ver</font>
						</a>
				<%  } %>
				</td>
			</tr>
	<%	} %>
		<tr><td></td></tr>
	</table>
	<br>
</body>
<%@ include file="../../cierraadm.jsp"%>
<%@ include file= "../../cierra_enoc.jsp" %>