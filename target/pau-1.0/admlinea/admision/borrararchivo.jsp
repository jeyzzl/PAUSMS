<%@ include file= "../../conectaadm.jsp" %>

<jsp:useBean id="pgAdmArchivos" scope="page" class="aca.admision.PgAdmArchivos" />
<jsp:useBean id="pgAdmArchivosU" scope="page" class="aca.admision.PgAdmArchivosUtil" />
<%	
	String folio		= (String) session.getAttribute("folio");	
	String documentoId	= request.getParameter("documentoId");
	
	boolean borroPg = false;
	try{
		conAdm.setAutoCommit(false);
		
		pgAdmArchivos.setFolio(folio);
		pgAdmArchivos.setDocumentoId(documentoId);
		if(pgAdmArchivosU.existeReg(conAdm, folio, documentoId)){
			if(pgAdmArchivosU.deleteReg(conAdm, folio, documentoId))
				borroPg = true;
		}else{
			borroPg = false;
	   	}		
	    conAdm.setAutoCommit(true);
	    
	    if(borroPg){
	    	//response.sendRedirect("documentos?documentoId="+documentoId);
	    	out.print("<font size='4'><b>¡ Borrado ! <a class='btn btn-primary' href='documentos?documentoId="+documentoId+"'>Regresar</a></font>");
	    }else{
%>
		<font size="4" color="red"><b>Ocurri&oacute; un error al borrar</b> <a href="documentos?documentoId=<%=documentoId%>">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></font><br>
<%
	    }
	}catch(Exception e){
		System.out.println("Error al subir el archivo: "+e);
%>
		<font size="4"><b>¡No se pudo borrar el archivo!</b> <a href="documentos?documentoId=<%=documentoId%>">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></font>
<%
	}
%>
<%@ include file= "../../cierraadm.jsp" %>