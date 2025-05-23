<%@ page import="com.oreilly.servlet.MultipartRequest"%> 
<%
	//String matricula=request.getParameter("matricula");
	String matricula = (String) session.getAttribute("codigoAlumno");
	String dir=application.getRealPath("/WEB-INF/fotos/");
	MultipartRequest multi = new MultipartRequest(request, dir, 5*1024*1024);
%>