<%@ include file= "../../con_enoc.jsp" %>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String matricula 	= request.getParameter("matricula");
	
	String nombre 		= aca.alumno.AlumUtil.getNombreAlumno(conEnoc, matricula, "NOMBRE");
	
	if(nombre.equals("0000000"))nombre = "Numero de Matrícula No Válido";
	
	out.print(nombre);
%>

<%@ include file= "../../cierra_enoc2.jsf" %>