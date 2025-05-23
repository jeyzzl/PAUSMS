<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmOpciones"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<% 
	AdmOpciones opcion  = (AdmOpciones) request.getAttribute("opcion");
%>
<body>
<div class="container-fluid">
	<h2><spring:message code='aca.Nuevo'/> <spring:message code='aca.Opcion'/></h2>
	<div class="alert alert-primary" role="alert">
	<a href="lista" class="btn btn-primary"><i class="fas fa-arrow-left"></i> <spring:message code='aca.Regresar'/></a>
	</div>
	<form action="grabar" method="POST">
		<input type="hidden" name="OpcionId" value="<%=opcion.getOpcionId()%>">
		<label class="form-label"><spring:message code='aca.Nombre'/></label><br>
		<input type="text" class="form-control" name="Nombre" value="<%=opcion.getNombre()%>"><br>
		<button type="submit" class="btn btn-primary"><spring:message code='aca.Grabar'/></button>
	</form>	
</div> 

</body>
<script src="../../js/search.js"></script>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>