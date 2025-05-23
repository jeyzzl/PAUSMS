<%@ include file= "../../con_enoc.jsp" %>

<%
	String codigoPersonal = request.getParameter("codigoPersonal");
	if(aca.alumno.AlumUtil.existeAlumno(conEnoc, codigoPersonal)){
		%>existe<%
	}else{
		%>noExiste<%
	}

%>

<%@ include file= "../../cierra_enoc2.jsf" %>