<%@page import="java.io.IOException"%>
<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
String codigoEmpleado = (String) session.getAttribute("codigoPersonal");
String dir = application.getRealPath("/WEB-INF/portafolio/") + "/";

	System.out.println("prueba de que entra a generar archivo");
	java.io.FileInputStream instreamA = null;
	java.io.File fA =new java.io.File(dir + request.getParameter("NombreArchivo"));
	byte[] buffA = null;
	if(fA.exists()){
		buffA = new byte[(int)fA.length()];
		instreamA = new java.io.FileInputStream(dir + request.getParameter("NombreArchivo"));
		instreamA.read(buffA,0,(int) fA.length());
		response.setContentType("application/"+request.getParameter("extension"));
		response.setHeader("Content-Disposition", "inline; filename="+request.getParameter("NombreArchivo")); 
		response.getOutputStream().write(buffA);
		response.flushBuffer();
		instreamA.close();
	}
%>
<%@ include file="../../cierra_enoc.jsp"%>