<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.convenio.spring.ConTipo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	List<ConTipo> lista 	= (List<ConTipo>)request.getAttribute("lista");	
	
	HashMap<String,String> mapaConvenioConTipo 	= (HashMap<String,String>)request.getAttribute("mapaConvenioConTipo");	
%>
<body>
<div class="container-fluid">
	<h2>Tipo convenio</h2>
	<form name="frmConvenio" action="listado" method="post">	
		<div class="alert alert-info d-flex align-items-center">
			<a class="btn btn-primary" href="editar"><spring:message code='aca.Nuevo'/></a>&nbsp;&nbsp;
			<input type="text" class="form-control search-query" placeholder="Buscar" id="buscar" style="width:200px">
		</div>
	</form>
	<table id="table" class="table table-sm table-bordered">
		<thead class="table-dark">	 
			<tr>
				<th width="2%" class="text-center">#</th>
				<th width="2%" class="text-center">Op.</th>
		    	<th width="17%" class="text-start">Nombre</th>
			</tr>
		</thead>
		<tbody>
<%	
	int row = 0;
	String tipoNombre = "";
	for (ConTipo convenio : lista){
		row++;		
%>
			<tr>
		    	<td class="text-center"><%=row%></td>    	
				<td class="center">
					<a href="editar?TipoId=<%=convenio.getTipoId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-pencil-alt"></i></a>&nbsp;&nbsp;
<% 				if(!mapaConvenioConTipo.containsKey(convenio.getTipoId())){%>
					<a href="javascript:Borrar('<%=convenio.getTipoId()%>')" title="<spring:message code="aca.Borrar"/>"><i class="fas fa-trash" ></i></a>
<% 				}%>
				</td>
		    	<td class="text-start"><%=convenio.getTipoNombre() %></td>
			</tr>	
<%	} %>
		</tbody>
	</table>
</div>
</body>
<script src="../../js/search.js"></script>
<script>	
	jQuery('#buscar').focus().search({table:jQuery("#table")});
	
	function Borrar(id){
		if (confirm("¿Estás seguro de borrar el tipo?")){
			document.location.href="borrar?TipoId="+id;
		}
	}
</script>
</html>