<%
	String mensaje = (String) request.getAttribute("mensaje");
	out.print(mensaje);
	
	if (request.getAttribute("conEnoc")!=null){
		java.sql.Connection conEnoc = (java.sql.Connection)request.getAttribute("conEnoc");
		request.removeAttribute("conEnoc");	
		if (conEnoc!=null) conEnoc.close();
	}	
%>