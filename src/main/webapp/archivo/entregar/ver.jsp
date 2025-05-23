<%@ page import="java.io.*" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.archivo.spring.ArchEntrega"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	ArchEntrega archEntrega = (ArchEntrega) request.getAttribute("archEntrega");
	String imagen	 		= (String) request.getAttribute("imagen");
%>
<div class="container-fluid">
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="documentos"><i class="fas fa-arrow-left"></i></a>
	</div>
	<img class="rounded border border-dark" src="muestraFoto?Folio=<%=archEntrega.getFolio()%>&Imagen=<%=imagen%>" width="520">
</div>
