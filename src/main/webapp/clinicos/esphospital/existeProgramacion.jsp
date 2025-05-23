<%@ include file= "../../con_enoc.jsp" %>

<jsp:useBean id="Programacion" scope="page" class="aca.rotaciones.RotProgramacion"/>
<%
	String especialidadId = request.getParameter("especialidadId");
	String hospitalId 	  = request.getParameter("hospitalId");
	
	if(Programacion.existeProgramacion(conEnoc, hospitalId, especialidadId)){
		%>existe<%
	}else{
		%>noExiste<%
	}

%>
<%@ include file= "../../cierra_enoc2.jsf" %>