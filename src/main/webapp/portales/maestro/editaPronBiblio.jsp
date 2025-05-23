<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@ page import= "aca.plan.spring.MapaNuevoBibliografia"%>
<% 
	aca.pron.spring.PronBiblio pronBiblio = (aca.pron.spring.PronBiblio)request.getAttribute("pronBiblio");

	String cursoCargaId = request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
%>
<body>
	<div class="container-fluid">
		<h3>Edit<small></h3>
		<div class="alert alert-success">
			<a class="btn btn-primary" href="pronBiblio?CursoCargaId=<%=cursoCargaId%>"><spring:message code="aca.Regresar"/></a>
		</div>	
		<div class="alert alert-info" role="alert">
			<form action="editaPronBiblio" method="POST">
				<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
				<input type="hidden" name="BiblioId" value="<%=pronBiblio.getBiblioId()%>">
				<strong>Order:</strong>&nbsp;<input type="text" size="2" name="Orden" value="<%=pronBiblio.getOrden()%>">&nbsp;
				<strong>Bibliography:</strong>&nbsp;<input size="140" name="BiblioNombre" maxlength="500" value="<%=pronBiblio.getBiblioNombre()%>"><br><br>
				<input type="submit" class="btn btn-primary" value="Edit">
			</form>
		</div>
	</div>
</body>