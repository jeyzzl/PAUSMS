<%@page import="java.util.List"%>

<%@page import="aca.admision.spring.AdmCarta"%>
<%@page import="aca.admision.spring.AdmSolicitud"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
</head>
<%
	String folio = (String) request.getAttribute("folio");

	List<AdmCarta> lisCondiciones 	= (List<AdmCarta>) request.getAttribute("lisCondiciones");
	AdmSolicitud admSolicitud 		= (AdmSolicitud) request.getAttribute("admSolicitud");
	
	int con = 1;
%>
<body>
	<div class="container-fluid">
		<h1>Condiciones <small>(<%=folio%> - <%=admSolicitud.getNombre()+" "+(admSolicitud.getApellidoMaterno()==null?"":admSolicitud.getApellidoMaterno())+" "+admSolicitud.getApellidoPaterno()%>)</small></h1>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="admitido?Folio=<%=folio%>">
				<spring:message code="aca.Regresar" />
			</a>
			&nbsp;
			<a class="btn btn-success" href="editarCondicion?Folio=<%=folio%>">Nuevo</a>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>#</th>
					<th>Opc.</th>
					<th>Condici�n</th>
				</tr>	
			</thead>
			<tbody>
<% 			for(AdmCarta carta : lisCondiciones){%>
				<tr>
					<td><%=con++%></td>
					<td>
						<a href="editarCondicion?Folio=<%=folio%>&CondicionId=<%=carta.getCondicionId()%>" ><i class="fas fa-pencil-alt"></i></a>
						<a href="javaScript:borrar('<%=folio%>','<%=carta.getCondicionId()%>');"><i class="fas fa-trash-alt"></i></a>
					</td>
					<td><%=carta.getCondicionNombre()%></td>
				</tr>
<% 			}%>
			</tbody>
		</table>
		<br>
	</div>
</body>
<script type="text/javascript">
	function borrar(folio,condicionId){
		if(confirm("�Desea borrar esta condici�n?")){
			document.location.href='borrarCondicion?Folio='+folio+'&CondicionId='+condicionId;
		}
	}
</script>
</html>