<%@ page import="java.sql.*" %>
<%@ include file= "../../conectaadm.jsp" %>
<jsp:useBean id="pgAdmDocAlum" scope="page" class="aca.admision.PgAdmDocAlum" />
<jsp:useBean id="pgAdmDocAlumU" scope="page" class="aca.admision.PgAdmDocAlumUtil" />
<%
	String folio 		= request.getParameter("Folio");
	String documentoId 	= request.getParameter("documentoId");
	String hoja 		= request.getParameter("hoja");	
	
	try{
		
		conAdm.setAutoCommit(false);	
		
		pgAdmDocAlum.setFolio(folio);
		pgAdmDocAlum.setDocumentoId(documentoId);
		pgAdmDocAlum.setHoja(hoja);
		
		if (pgAdmDocAlumU.existeReg(conAdm, folio, documentoId, hoja)){			
			pgAdmDocAlum = pgAdmDocAlumU.mapeaRegId(conAdm, folio, documentoId, hoja);
			
			org.postgresql.largeobject.LargeObjectManager lom = ((org.postgresql.PGConnection)conAdm).getLargeObjectAPI();
			org.postgresql.largeobject.LargeObject obj;
			long oid = pgAdmDocAlum.getImagen();
				obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.READ);
		    byte buf[] = new byte[obj.size()];
		    //obj.read(buf, 0, obj.size());
		    buf = obj.read(obj.size());
		    response.setContentType("image/jpeg");
			response.getOutputStream().write(buf);
			response.flushBuffer();
			obj.close();
		}else{
			System.out.println("No existe la imagen...");
		}
	}catch(Exception ex){
		System.out.println("Error $$$:"+ex);
	}	
	conAdm.setAutoCommit(true);
%>
<%@ include file= "../../cierraadm.jsp" %>