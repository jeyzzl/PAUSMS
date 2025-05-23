<%@ page import="java.sql.*" %>

<%@ include file= "../../conectadbp.jsp" %>

<jsp:useBean id="archGeneral" scope="page" class="aca.pg.archivo.ArchGeneral"/>

<%
	String codigoPersonal	= (String)session.getAttribute("codigoPersonal")==null?"-":(String)session.getAttribute("codigoPersonal");
	String matricula 		= request.getParameter("matricula");
	String folio 			= request.getParameter("folio");
	
	//Si tiene una sesión activa
	if (codigoPersonal.substring(0,1).equals("9")||codigoPersonal.substring(0,1).equals("0")||codigoPersonal.substring(0,1).equals("1")||codigoPersonal.substring(0,1).equals("2")){	
	
		conn2.setAutoCommit(false);	
		archGeneral.setMatricula(matricula);
		archGeneral.setFolio(folio);
		if ( archGeneral.existeReg(conn2) ){
			archGeneral.mapeaRegId(conn2,matricula,folio);
			
			org.postgresql.largeobject.LargeObjectManager lom = ((org.postgresql.PGConnection)conn2).getLargeObjectAPI();
			org.postgresql.largeobject.LargeObject obj;
			long oid = archGeneral.getImagen();
				obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.READ);
		    byte buf[] = new byte[obj.size()];
		    //obj.read(buf, 0, obj.size());
		    buf = obj.read(obj.size());
		    response.setContentType("image/jpeg");
			response.getOutputStream().write(buf);
			response.flushBuffer();
			obj.close();
		}
		conn2.setAutoCommit(true);
	}	
%>
<%@ include file= "../../cierradbp.jsp" %>