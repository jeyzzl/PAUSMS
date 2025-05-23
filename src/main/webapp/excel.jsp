<%	
	byte[] buff 	= request.getParameter("datos_a_enviar").getBytes("ISO-8859-1");
	String archivo 	= request.getParameter("archivo");
	response.setHeader("Content-type","application/xls");
	response.setHeader("Content-Disposition","inline;filename="+archivo);
	response.getOutputStream().write(buff);
%>