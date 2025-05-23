<%@page import="java.util.List"%>

<%@page import="aca.admision.spring.AdmComentario"%>
<%@page import="aca.admision.spring.AdmSolicitud"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
</head>
<%
	String folio = (String) request.getAttribute("folio");

	List<AdmComentario> lista = (List<AdmComentario>) request.getAttribute("comentarios");
	AdmSolicitud admSolicitud = (AdmSolicitud) request.getAttribute("admSolicitud");
	
	int con = 1;
%>
<body>
	<div class="container-fluid">
		<h2>Comments <small class="text-muted fs-5">(<%=folio%> - <%=admSolicitud.getNombre()+" "+(admSolicitud.getApellidoMaterno()==null?"":admSolicitud.getApellidoMaterno())+" "+admSolicitud.getApellidoPaterno()%>)</small></h2>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="mostrarProceso?Folio=<%=folio%>">
				<i class="fas fa-arrow-left"></i>
			</a>
			&nbsp;&nbsp;&nbsp;
			<a class="btn btn-success" href="editarComentario?Folio=<%=folio%>">New</a>
		</div>
		<table class="table table-bordered">
		<thead class="table-info">
				<tr>
					<th>#</th>
					<th>Op..</th>
					<th>Type</th>
					<th>Date</th>
					<th>Comment</th>
					<th>Status</th>
				</tr>	
			</thead>
			<tbody>
<% 			for(AdmComentario comentario : lista){
				String tipo = "-";
				
				if(comentario.getTipo().equals("0")){
					tipo = "General";
				}else if(comentario.getTipo().equals("1")){
					tipo = "Personal Data";
				}else if(comentario.getTipo().equals("2")){
					tipo = "Application Form";
				}else if(comentario.getTipo().equals("3")){
					tipo = "Sent Documents";
				}else if(comentario.getTipo().equals("4")){
					tipo = "Payment";
				}else if(comentario.getTipo().equals("5")){
					tipo = "Test Venue";
				}else if(comentario.getTipo().equals("6")){
					tipo = "Test";
				}else if(comentario.getTipo().equals("7")){
					tipo = "Results";
				}else if(comentario.getTipo().equals("8")){
					tipo = "Validation";
				}else if(comentario.getTipo().equals("9")){
					tipo = "Acceptance Letter";
				}
					
%>
				<tr>
					<td><%=con++%></td>
					<td>
						<a href="editarComentario?Folio=<%=folio%>&ComentarioId=<%=comentario.getComentarioId()%>" ><i class="fas fa-pencil-alt"></i></a>
						<a href="javaScript:borrar('<%=folio%>','<%=comentario.getComentarioId()%>');"><i class="fas fa-trash-alt"></i></a>
					</td>
					<td><%=tipo%></td>
					<td><%=comentario.getFecha().substring(0, 10).replace("-", "/")%></td>
					<td><%=comentario.getComentario()%></td>
					<td><%=comentario.getEstado().equals("A") ? "Active" : "Inactive"%></td>
				</tr>
<% 			}%>
			</tbody>
		</table>
		<br>
	</div>
</body>
<script type="text/javascript">
	function borrar(folio,comentario){
		if(confirm("Do you want to delete this comment?")){
			document.location.href='borrarComentario?Folio='+folio+'&ComentarioId='+comentario;
		}
	}
</script>
</html>