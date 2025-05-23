<%@ include file= "../../con_enoc.jsp" %>

<jsp:useBean id="Programacion" scope="page" class="aca.rotaciones.RotProgramacion"/>

<%
	String programacionId = request.getParameter("programacionId");
	
	Programacion.setProgramacionId(programacionId);
	
	if(Programacion.deleteReg(conEnoc)){
		%>borro<%
	}else{
		%>error<%
	}
%>
<%@ include file= "../../cierra_enoc2.jsf" %>