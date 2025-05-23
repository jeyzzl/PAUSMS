<%@page import="aca.admision.spring.AdmSolicitud"%>
<%@page import="aca.admision.spring.AdmComentario"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
</head>
<%
	String folio 	= (String) request.getAttribute("folio");
	String mensaje 	= (String) request.getAttribute("mensaje");
	
	AdmSolicitud admSolicitud = (AdmSolicitud) request.getAttribute("admSolicitud");
	AdmComentario comentario = (AdmComentario) request.getAttribute("comentario");
	
%>
<body>
<div class="container-fluid">
	<h2>Comments <small class="text-muted fs-5">(<%=folio%> - <%=admSolicitud.getNombre()+" "+(admSolicitud.getApellidoMaterno()==null?"":admSolicitud.getApellidoMaterno())+" "+admSolicitud.getApellidoPaterno()%>)</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="comentarios?Folio=<%=folio%>">
			<i class="fas fa-arrow-left"></i>
		</a>
	</div>
<% 		if(mensaje.equals("1")){%>
	<div class="alert alert-success">Saved</div>
<% 		}else if(mensaje.equals("2")){%>
	<div class="alert alert-danger">Not saved</div>
<% 		}%>
	<div style="width:500px;">	
	<form class="form-group" name="frmComentario" action="grabarComentario" method="post">
		<input type="hidden" name="Folio" value="<%=folio%>">
		<input type="hidden" name="ComentarioId" value="<%=comentario.getComentarioId()%>">
		<h3>Type:</h3>
		<select class="form-control" name="Tipo">
			<option <%=comentario.getTipo().equals("0") ? "selected" : "" %> value="0">General</option>
			<option <%=comentario.getTipo().equals("1") ? "selected" : "" %> value="1">Personal Data</option>
			<option <%=comentario.getTipo().equals("2") ? "selected" : "" %> value="2">Application Form</option>
			<option <%=comentario.getTipo().equals("3") ? "selected" : "" %> value="3">Sent Documents</option>
			<option <%=comentario.getTipo().equals("4") ? "selected" : "" %> value="4">Payment</option>
			<option <%=comentario.getTipo().equals("5") ? "selected" : "" %> value="5">Test Venue</option>
			<option <%=comentario.getTipo().equals("6") ? "selected" : "" %> value="6">Test</option>
			<option <%=comentario.getTipo().equals("7") ? "selected" : "" %> value="7">Results</option>
			<option <%=comentario.getTipo().equals("8") ? "selected" : "" %> value="8">Validation</option>
			<option <%=comentario.getTipo().equals("9") ? "selected" : "" %> value="9">Acceptance Letter</option>
		</select><br>
		<h3>Status:</h3>
		<select class="form-control" name="Estado">
			<option <%=comentario.getEstado().equals("I") ? "selected" : "" %> value="I">Inactive</option>
			<option <%=comentario.getEstado().equals("A") ? "selected" : "" %> value="A">Active</option>
		</select><br>
		<h3>Comment:</h3>
		<textarea class="form-control" name="Comentario" rows="4"><%=comentario.getComentario()%></textarea><br>
		<button type="submit" class="btn btn-primary">Save</button>
	</form>
	</div>
</div>
</body>
</html>