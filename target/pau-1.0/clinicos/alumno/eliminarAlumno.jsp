<%@ include file= "../../con_enoc.jsp" %>

<jsp:useBean id="Programacion" scope="page" class="aca.rotaciones.RotProgramacion"/>

<%
	String programacionId = request.getParameter("programacionId");
	
	Programacion.setProgramacionId(programacionId);
	Programacion.mapeaRegId(conEnoc, programacionId);
	Programacion.setCodigoPersonal("");
	Programacion.setAnual("0");
	Programacion.setInscripcion("0");
	Programacion.setIntegrada("0");
	Programacion.setPago("0");
	
	if(Programacion.updateReg(conEnoc)){
		%>borro<%
	}else{
		%>error<%
	}

%>
<%@ include file= "../../cierra_enoc2.jsf" %>