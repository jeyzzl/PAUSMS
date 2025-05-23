<%@ include file="../../idioma.jsp"%>

<%@ page import="java.io.File"%>
<%
	String archivo = request.getParameter("archivo");
	if(archivo==null)archivo="";
	File fi = new File(archivo);
	if(fi.exists()){
		if(fi.length()>(10*1024*1024))
			out.print("<br><font color=red><b>The file is over 10 Mb.</b></font>");
		else
			out.print("Sending...");
	}else out.print("File not found");
%>
