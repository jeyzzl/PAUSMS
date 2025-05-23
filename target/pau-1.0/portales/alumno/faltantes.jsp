<%@ include file= "../../con_enoc.jsp" %>
<jsp:useBean id="apFisicaGrupo" scope="page" class="aca.apFisica.ApFisicaGrupo"/>
<jsp:useBean id="apFisicaGrupoU" scope="page" class="aca.apFisica.ApFisicaGrupoUtil"/>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%


	int registrados = aca.apFisica.ApFisicaAlumnoUtil.registrados(conEnoc, request.getParameter("grupoId"));
	apFisicaGrupoU.mapeaRegId(conEnoc, request.getParameter("grupoId"));

	int faltantes = Integer.parseInt(apFisicaGrupo.getCupo()) - registrados;
	
	out.print(faltantes);
%>

<%@ include file= "../../cierra_enoc2.jsf" %>