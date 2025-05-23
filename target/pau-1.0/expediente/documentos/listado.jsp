<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.exp.spring.ExpDocumento"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Borrar(id){
		if(confirm("Are you sure you want to delete this record?")){
			document.location="borrar?Id="+id;
		}
	}
</script>
<%
	List<ExpDocumento> lista				= (List<ExpDocumento>) request.getAttribute("lista");
	HashMap<String,String> mapaDocEmpleado	= (HashMap<String,String>) request.getAttribute("mapaDocEmpleado");

	int i = 1;
%>
<div class="container-fluid">
	<h2>Document Catalog</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="editar"><i class="fas fa-plus"></i> New Document</a>
	</div>
	<table class="table table-fullcondensed table-nobordered">
		<thead>
	  		<tr> 
			    <th width="5%">No.</th>
			    <th width="40%">Document</th>
			    <th width="15%">Short Name</th>
			    <th width="10%">Order</th>
		  	</tr>
		</thead>
		<tbody>
<%		for(ExpDocumento documento : lista){%>
	  		<tr> 
			    <td align="left">
			    	<%=i++%>
					<a href="editar?Id=<%=documento.getDocumentoId()%>" title="Editar"><i class="fas fa-edit"></i></a>
<% 				if(!mapaDocEmpleado.containsKey(documento.getDocumentoId())){%>
					<a onclick="Borrar('<%=documento.getDocumentoId()%>')" title="Borrar"><i class="fas fa-times"></i></a>
<% 				}%>
			    </td>
			    <td align="left"><%=documento.getDocumentoNombre()%></td>
			    <td align="left"><%=documento.getCorto()%></td>
			    <td align="left"><%=documento.getOrden()%></td>
	  		</tr>
<%		}	%>
		</tbody>
	</table>
</div>
