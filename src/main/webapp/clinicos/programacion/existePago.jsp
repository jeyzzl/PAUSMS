<%@ include file= "../../con_enoc.jsp" %>

<jsp:useBean id="Programacion" scope="page" class="aca.rotaciones.RotProgramacion"/>

<%
	String programacionId = request.getParameter("programacionId");
	
	Programacion.setProgramacionId(programacionId);
	Programacion.mapeaRegId(conEnoc, programacionId);
	
	if(Programacion.getPago().equals("0")){
		%>borrar<%
	}else{
		%>noBorrar<%
	}

%>
<%@ include file= "../../cierra_enoc2.jsf" %>