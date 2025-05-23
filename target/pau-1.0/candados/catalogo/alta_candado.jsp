<%@ page import="java.util.List "%>
<%@ page import="aca.candado.spring.Candado"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Borrar( ){
		if(document.frmcandado.id.value!=""){
			if(confirm("Are you sure you want to delete this record?")==true){
				document.frmcandado.submit();
			}			
		}else{
			alert("Type in the key");
			document.frmcandado.id.focus(); 
	  	}
	}
</script>

<%
	String tipoId				= (String) request.getParameter("TipoId");
	String nombreTipo			= (String) request.getAttribute("nombreTipo");
	List<Candado> lisCandado	= (List<Candado>)request.getAttribute("lisCandado");
	
%>
<div class="container-fluid">
<h2>Lock Catalog <small class="text-muted fs-4">(<b>Category:</b> <%=nombreTipo%>)</small></h2>
<div class="alert alert-info">
	<a class="btn btn-primary" href="candados"><i class="fas fa-arrow-left"></i></a>
	<a class="btn btn-primary" href="editarCandado?TipoId=<%=tipoId%>"><spring:message code="aca.Añadir"/></a>
</div>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
  		<tr> 
  			<th width="14%">Op</th>
    		<th width="14%">Id</th>
    		<th width="72%"><spring:message code="aca.Nombre"/></th>
  		</tr>
  	</thead>
<%			
		for (Candado candado : lisCandado){
%>
  		<tr> 
  			<td><a href="editarCandado?CandadoId=<%=candado.getCandadoId()%>&TipoId=<%=tipoId%>" class="fas fa-edit" title="Editar"></a></td>
   			<td><%=candado.getCandadoId()%></td>
   			<td><%=candado.getNombreCandado()%></td>
  		</tr>
<%
		}
%>
</table>
</div>