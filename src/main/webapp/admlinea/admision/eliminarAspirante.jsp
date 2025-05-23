<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="AdmSolicitud" scope="page" class="aca.admision.AdmSolicitud" />
<jsp:useBean id="AdmAcademico" scope="page" class="aca.admision.AdmAcademico" />
<jsp:useBean id="AdmDocAlum" scope="page" class="aca.admision.AdmDocAlum" />
<jsp:useBean id="AdmDocAlumU" scope="page" class="aca.admision.DocAlumUtil" />
<jsp:useBean id="AdmEncuesta" scope="page" class="aca.admision.AdmEncuesta" />
<jsp:useBean id="AdmEstudio" scope="page" class="aca.admision.AdmEstudio" />
<jsp:useBean id="AdmEstudioU" scope="page" class="aca.admision.AdmEstudioUtil" />
<jsp:useBean id="AdmPadres" scope="page" class="aca.admision.AdmPadres" />
<jsp:useBean id="AdmPadresU" scope="page" class="aca.admision.AdmPadresUtil" />
<jsp:useBean id="AdmRecomienda" scope="page" class="aca.admision.AdmRecomienda" />
<jsp:useBean id="AdmSalud" scope="page" class="aca.admision.AdmSalud" />
<jsp:useBean id="AdmSaludU" scope="page" class="aca.admision.AdmSaludUtil" />
<jsp:useBean id="AdmTutor" scope="page" class="aca.admision.AdmTutor" />
<jsp:useBean id="AdmTutorU" scope="page" class="aca.admision.AdmTutorUtil" />
<jsp:useBean id="AdmUbicacion" scope="page" class="aca.admision.AdmUbicacion" />
<jsp:useBean id="AdmUbicacionU" scope="page" class="aca.admision.AdmUbicacionUtil" />
<jsp:useBean id="AdmProceso" scope="page" class="aca.admision.AdmProceso" />

<%
	String folio = request.getParameter("Folio")==null?"0":request.getParameter("Folio");

	AdmAcademico.setFolio(folio);
	AdmAcademico.deleteReg(conEnoc);
	
	ArrayList<aca.admision.AdmDocAlum> arr = AdmDocAlumU.getLista(conEnoc, folio, "");
	AdmDocAlum.setFolio(folio);
	for(int i=0; i<arr.size(); i++){
		AdmDocAlum.setDocumentoId(arr.get(i).getDocumentoId());
		AdmDocAlum.deleteReg(conEnoc);
	}

	AdmEncuesta.setFolio(folio);
	for(int i=1; i<=5; i++){
		AdmEncuesta.setRecomendacionId(i+"");
		AdmEncuesta.deleteReg(conEnoc);
	}
	
	ArrayList<aca.admision.AdmEstudio> arr2 = AdmEstudioU.getListAFolio(conEnoc, folio, "");
	AdmEstudio.setFolio(folio);
	for(int i=0; i<arr2.size(); i++){
		AdmEstudio.setId(arr2.get(i).getId());
		AdmEstudio.deleteReg(conEnoc);
	}
	
	AdmPadres.setFolio(folio);
	AdmPadresU.deleteReg(conEnoc, folio);

	AdmRecomienda.setFolio(folio);
	for(int i=1; i<=5; i++){
		AdmRecomienda.setRecomendacionId(i+"");
		AdmRecomienda.deleteReg(conEnoc);
	}
	
	AdmSalud.setFolio(folio);
	AdmSaludU.deleteReg(conEnoc, folio);
	
	AdmTutor.setFolio(folio);
	AdmTutorU.deleteReg(conEnoc, folio);
	
	AdmUbicacion.setFolio(folio);
	AdmUbicacionU.deleteReg(conEnoc, folio);
	
	AdmProceso.setFolio(folio);
	AdmProceso.deleteReg(conEnoc);
	
	AdmSolicitud.setFolio(folio);
	AdmSolicitud.deleteReg(conEnoc);
%>
<%@ include file= "../../cierra_enoc2.jsf" %>