<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@ page import= "aca.archivos.spring.ArchivosProfesor"%>
<% 
	String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	String folio		 	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String profesor		 	= request.getParameter("Profesor")==null?"0":request.getParameter("Profesor");

	ArchivosProfesor archivosProfesor = (ArchivosProfesor) request.getAttribute("archivosProfesor");
%>
<body>
<div class="container-fluid">
	<h3>Edit Comment<small></h3>
	<div class="alert alert-success">
		<a class="btn btn-primary" href="filesMaestro?CursoCargaId=<%=cursoCargaId%>"><spring:message code="aca.Regresar"/></a>
	</div>	
	<div class="alert alert-info" role="alert">
		<form name="frmComentario" action="grabarComentario" method="post">
			<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
			<input type="hidden" name="Folio" value="<%=folio%>">
			<input type="hidden" name="Profesor" value="<%=profesor%>">
			<strong>Comment:</strong><br>
			<textarea name="Comentario" class="form-control" cols="50" rows="3"><%=archivosProfesor.getComentario()%></textarea><br>			
			<input type="submit" class="btn btn-primary" value="Edit">
		</form>
	</div>
</div>
</body>