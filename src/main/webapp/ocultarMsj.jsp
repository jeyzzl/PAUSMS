<%
	String codigoPersonal = session.getAttribute("codigoPersonal")==null?"0":(String)session.getAttribute("codigoPersonal");
	System.out.println("ocultarMensaje"+codigoPersonal);	
%>