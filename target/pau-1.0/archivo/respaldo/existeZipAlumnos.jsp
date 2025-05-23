<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>

<%@ page import="java.io.*" %>
<%@ page import="java.util.zip.*" %>
<%@ page import="java.sql.*" %>
<%
	boolean error 		= false;
	String dir			= application.getRealPath("/archivo/respaldo")+"/";	
	try{		 
		File f = new File(dir+"respaldoAlumnos.zip");
		if(f.exists() && !f.isDirectory()) { 
 			//Everything cool man :D
		}else{
			error = true;
		}	
	}catch(Exception e){	
		e.printStackTrace();
		error = true;	
	}
	if(error){
		out.print("<div class='error'>Error</div>");
	}
%>
