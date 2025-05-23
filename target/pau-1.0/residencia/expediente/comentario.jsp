
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<head>
</head>	
<% String folio				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
   String mensaje		 	= request.getParameter("Mensaje")==null?"-": request.getParameter("Mensaje");
   String comentario 		= (String) request.getAttribute("comentario");
%>
<body>
<div class="container-fluid">
	<h1>Comment</h1>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="listado">Return</a>
	</div>
		<form action="grabaComentario" method="post">
			<input type="hidden" name="Folio" value="<%=folio%>">
			<textarea id="Comentario" name="Comentario" class="form-control" style="width:600px; height:350px"><%=comentario%></textarea>
			<br>
			<button class="btn btn-success" type="submit">Save</button>
			<span><%=mensaje%></span>
		</form>		
</div>	
</body>