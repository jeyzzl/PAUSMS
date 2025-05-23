<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList"%>
<%
	aca.conecta.Conectar conectar = new aca.conecta.Conectar();
	Connection  conEnoc 	= null;
	String saltoPag			= "X";
	try{
		conEnoc 			= (java.sql.Connection)request.getAttribute("conEnoc");
		String rutaInicio 	= ((jakarta.servlet.http.HttpServletRequest)request).getRequestURI();
		String userOpenFile = (String) session.getAttribute("codigoPersonal"); 
		String alumOpenFile = (String) session.getAttribute("codigoAlumno");
		System.out.println("Open:"+rutaInicio+":"+userOpenFile+":"+alumOpenFile);
%>