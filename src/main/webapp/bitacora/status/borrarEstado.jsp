<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="Estado" scope="page" class="aca.bitacora.BitEstado"/>
<jsp:useBean id="EstadoU" scope="page" class="aca.bitacora.BitEstadoUtil"/>

<%
	String estadoId = request.getParameter("EstadoId");
	EstadoU.deleteReg(conEnoc, estadoId);
%>
	<META HTTP-EQUIV="REFRESH" CONTENT="1; URL=estado.jsp"> 
<%@ include file="../../cierra_enoc.jsp"%>