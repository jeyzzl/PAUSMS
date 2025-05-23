<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.calcula.spring.CalConcepto"%>

<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function borrar(conceptoId){
		if (confirm("¿Estás seguro de borrar este concepto?")){
			document.location.href = "borrar?ConceptoId=" + conceptoId;
		}
	}	
</script>

<%	
	List<CalConcepto> lista	= (List<CalConcepto>)request.getAttribute("lista");

	HashMap<String,String> mapaConceptos	= (HashMap<String,String>)request.getAttribute("mapaConceptos");
%>
<div class="container-fluid">
	<h1>Concept List</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary btn-sm" href="editar">Add</a>
	</div>
	<table class="table">
		<thead class="">
			<tr>
				<th>#</th>
				<th>Name</th>
				<th>Type</th>
			</tr>
		</thead>
		<tbody>
<% 		for(CalConcepto objeto : lista){%>
			<tr>
				<td><%=objeto.getConceptoId()%> 
					<a href="editar?ConceptoId=<%=objeto.getConceptoId()%>" title="Edit"><i class="fas fa-edit"></i></a>&nbsp;&nbsp;
<% 				if(!mapaConceptos.containsKey(objeto.getConceptoId())){%>
					<a onclick="borrar('<%=objeto.getConceptoId()%>')" title="Delete"><i class="fas fa-times"></i></a>
<% 				}%>
				</td>
				<td><%=objeto.getConceptoNombre()%></td>
				<td><%=objeto.getTipo().equals("D")?"<span class='badge bg-danger rounded-pill'>Debit</span>":"<span class='badge bg-success rounded-pill'>Credit</span>"%></td>
			</tr>
<% 		}%>
		</tbody>
	</table>
</div>
