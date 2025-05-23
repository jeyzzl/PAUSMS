<%@ include file= "../../con_enoc.jsp" %>
<%
	String matricula 	= request.getParameter("matricula");
	String periodoId		= (String) session.getAttribute("periodoSanitaria");
	
	boolean existe		= aca.alerta.AlertaDatosUtil.existeAlumno(conEnoc, periodoId, matricula);
	String mensaje		= "";
	if(existe==true)mensaje = "¡El alumno ya está registrado!";
	else mensaje ="";
		
	out.print(mensaje);
%>

<%@ include file= "../../cierra_enoc2.jsf" %>