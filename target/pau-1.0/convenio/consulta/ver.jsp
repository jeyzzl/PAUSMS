<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.convenio.spring.ConArchivo"%>
<%@page import= "java.util.Base64"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	String convenioId					= request.getParameter("ConvenioId")==null?"0":request.getParameter("ConvenioId");
    String folio			     		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
    String convenioNombre				= (String)request.getAttribute("convenioNombre");
	ConArchivo conArchivo				= (ConArchivo)request.getAttribute("conArchivo");
	String pdfStr 						= Base64.getEncoder().encodeToString(conArchivo.getArchivo());
	List<ConArchivo> lisArchivos		= (List<ConArchivo>)request.getAttribute("lisArchivos");	
%>
<body>
<div class="container-fluid">
	<h3>Convenio <small class="text-muted fs-6">( <b>Nombre:</b> <%=convenioNombre%> - <b>Folio:</b> <%=folio%> )</small></h3>
	<div class="alert alert-info d-flex align-items-center">
<!-- 		<a href="listado" class="btn btn-primary">Regresar</a>&nbsp; -->
<%	for (ConArchivo archivo : lisArchivos){%>
		&nbsp;<a href="ver?ConvenioId=<%=convenioId%>&Folio=<%=archivo.getFolio()%>"><span class="badge bg-dark rounded-pill"><%=archivo.getFolio()%></span></a>
<% 	}%>				
	</div>
	<embed src="data:application/pdf;base64,<%=pdfStr%>" type="application/pdf" width="730px;" height="830px;"/><br>	
</div>
</body>
<script src="../../js/search.js"></script>
<script>	
	jQuery('#buscar').focus().search({table:jQuery("#table")});
	
	function Borrar(id){
		if (confirm("¿Estás seguro de borrar el convenio?")){
			document.location.href="borrar?Id="+id;
		}
	}
</script>
</html>