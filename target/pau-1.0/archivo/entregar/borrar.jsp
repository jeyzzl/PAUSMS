<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "../../conectadbp.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="ArchEntregaU" scope="page" class="aca.archivo.ArchEntregaUtil"/>
<jsp:useBean id="ArchEntrega" scope="page" class="aca.archivo.ArchEntrega"/>
<jsp:useBean id="ArchDocAlumUtil" scope="page" class="aca.archivo.ArchDocAlumnoUtil"/>

<%	
	String codigoAlumno		= (String)session.getAttribute("codigoAlumno");
	String folio   			= request.getParameter("Folio");	
	String documentos 		= ArchEntregaU.getDocumentos(conEnoc, codigoAlumno, folio);	
	ArrayList<aca.archivo.ArchDocAlumno> lisDocumentos = ArchDocAlumUtil.getListAlumno(conEnoc, codigoAlumno,"0,1,2,3,4,5,6,7,9,10","ORDER BY ENOC.DOC_ORDEN(iddocumento)");
	
	// Cambiar el estado de entregados
	for (aca.archivo.ArchDocAlumno docAlum : lisDocumentos){		
		if (documentos.contains("-"+docAlum.getIdDocumento()+"-") && docAlum.getUbicacion().equals("8")){
			ArchDocAlumUtil.updateUbicacion(conEnoc, docAlum.getMatricula(), docAlum.getIdDocumento(), "1");			
		}
	}
	
	if(ArchEntregaU.deleteReg(conEnoc, codigoAlumno, folio)){
		out.print("<div class='alert alert-danger'><b>Borrado...<a class='btn btn-primary' href='documentos'>Regresar</a></div>");
	}
%>
<%@ include file= "../../cierradbp.jsp" %>
<%@ include file= "../../cierra_enoc.jsp" %>