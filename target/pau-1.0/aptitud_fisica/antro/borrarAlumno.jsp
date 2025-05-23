<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="AfisRegistro" scope="page" class="aca.afis.AfisRegistro"/>
<jsp:useBean id="AfisRegistroUtil" scope="page" class="aca.afis.AfisRegistroUtil"/>

<%
	String periodoId 			= request.getParameter("PeriodoId");
	String codigoPersonal 		= request.getParameter("CodigoPersonal");	
	AfisRegistroUtil.deleteReg(conEnoc, periodoId, codigoPersonal);	
%>
	<META HTTP-EQUIV="Refresh" CONTENT="0; URL=listado?Accion=2">

<%@ include file= "../../cierra_enoc.jsp" %>