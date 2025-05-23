<%@ include file= "../../con_enoc.jsp" %>

<jsp:useBean id="HospitalEspecialidad" scope="page" class="aca.rotaciones.RotHospitalEspecialidad"/>
<%
	String hospitalId 	  = request.getParameter("hositalId");
	String especialidadId = request.getParameter("especialidadId");
	String cPrincipal 	  = request.getParameter("cPrincipal");
	String pPrincipal 	  = request.getParameter("pPrincipal");
	String cSecundario 	  = request.getParameter("cSecundario");
	String pSecundario 	  = request.getParameter("pSecundario");
	String estado		  = request.getParameter("estado");
	
	HospitalEspecialidad.setHospitalId(hospitalId);
	HospitalEspecialidad.setEspecialidadId(especialidadId);
	HospitalEspecialidad.setContactoPrincipal(cPrincipal);
	HospitalEspecialidad.setPuestoPrincipal(pPrincipal);
	HospitalEspecialidad.setContactoSecundario(cSecundario);
	HospitalEspecialidad.setPuestoSecundario(pSecundario);
	HospitalEspecialidad.setEstado(estado);
	
	if(HospitalEspecialidad.updateReg(conEnoc)){
		%>actualizo<%
	}else{
		%>error<%
	}
	
%>
<%@ include file= "../../cierra_enoc2.jsf" %>