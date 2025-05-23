<%@ include file= "../../conectaadm.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileInputStream"%>

<jsp:useBean id="pgAdmArchivos" scope="page" class="aca.admision.PgAdmArchivos" />
<jsp:useBean id="pgAdmArchivosU" scope="page" class="aca.admision.PgAdmArchivosUtil" />
<%
	String ruta = application.getRealPath("/admlinea/admision/");
	String documentoId	= "";	
	String folio		= "";
	String nombre		= "";
	boolean guardoPg = false;
	try{
		conAdm.setAutoCommit(false);
		com.oreilly.servlet.MultipartRequest multi = new com.oreilly.servlet.MultipartRequest(request, ruta, 5*1024*1024);
	    
		folio		= (String) session.getAttribute("folio");
		documentoId	= multi.getParameter("documentoId");	    
	    nombre		= multi.getFilesystemName("archivo");
		
	    File fi = new File(ruta+"/"+nombre);
		FileInputStream fis = new FileInputStream(fi);
		org.postgresql.largeobject.LargeObjectManager lom = ((org.postgresql.PGConnection)conAdm).getLargeObjectAPI();
		long oid = lom.createLO(org.postgresql.largeobject.LargeObjectManager.READ | org.postgresql.largeobject.LargeObjectManager.WRITE);
		org.postgresql.largeobject.LargeObject obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.WRITE);
		byte buf[] = new byte[(int)fi.length()];
		int le; 
		while ((le=fis.read(buf)) !=-1){
			obj.write(buf,0,le);
		}
		pgAdmArchivos.setFolio(folio);
		pgAdmArchivos.setDocumentoId(documentoId);				
		pgAdmArchivos.setArchivo(oid);
		pgAdmArchivos.setNombre(nombre);
		
		if(pgAdmArchivosU.existeReg(conAdm, folio, documentoId)){
			if(pgAdmArchivosU.updateReg(conAdm, pgAdmArchivos))
				guardoPg = true;
		}else if(pgAdmArchivosU.insertReg(conAdm, pgAdmArchivos)){
			guardoPg = true;
	   	}	
	   	
		fis.close();
		fi.delete();	
		
		conAdm.setAutoCommit(true);
	    
	    if(guardoPg){

	    	out.print("<font size='4'><b>¡ Grabado ! <a class='btn btn-primary' href='documentos?documentoId="+documentoId+"'>Regresar</a></font>");
	    	//response.sendRedirect("documentos?documentoId="+documentoId);
	    }else{
%>
		<font size="4" color="red"><b>Ocurri&oacute; un error al subir</b> <a href="documentos">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></font><br>
<%
	    }
	}catch(Exception e){
		System.out.println("Error al subir el archivo: "+e);
%>
		<font size="4"><b>Puede que el archivo que intenta subir mida mas de 5 MB. Intente con uno mas peque&ntilde;o</b> <a href="subirarchivo?documentoId=<%=documentoId%>">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></font>
<%
	}
%>
<%@ include file= "../../cierraadm.jsp" %>