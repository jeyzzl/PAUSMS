<%@ include file= "../../con_enoc.jsp" %>

<jsp:useBean id="Programacion" scope="page" class="aca.rotaciones.RotProgramacion"/>

<%
	String programacionId = request.getParameter("programacionId");
	String codigoPersonal = request.getParameter("codigoPersonal");

	Programacion.mapeaRegId(conEnoc, programacionId);
	Programacion.setCodigoPersonal(codigoPersonal);	
	
	String nombre = aca.alumno.AlumUtil.getNombreAlumno(conEnoc, codigoPersonal, "NOMBRE");
	if(Programacion.updateReg(conEnoc)){
		out.print(nombre);
	}else{
		%>error<%
	}

%>
<%@ include file= "../../cierra_enoc2.jsf" %>