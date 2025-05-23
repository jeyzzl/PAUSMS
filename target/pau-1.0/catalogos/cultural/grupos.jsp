<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatCultural"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%	
	List<CatCultural> lisCulturales 					= (List<CatCultural>) request.getAttribute("lisCulturales");
	HashMap<String,String> mapaRegionPorCultural 		= (HashMap<String,String>) request.getAttribute("mapaRegionPorCultural");
%>

<body>
	<div class="container-fluid">
		<h2>Cultural Groups Catalog</h2>
		<div class="alert alert-info d-flex align-items-center">
			<a class="btn btn-primary" href="editarGrupos"><spring:message code="aca.Anadir"/></a>&nbsp;&nbsp;
			<input type="text" class="form-control search-query" placeholder="Search.." id="buscar" style="width:170px;">
		</div>
		<table id="table" class="table table-sm table-bordered">
			<thead>  
				<tr>
					<th width="5%">Op.</th>
					<th width="10%"><spring:message code="aca.Numero"/></th>
					<th width="20%">Main Cultural Group</th>
					<th width="40%">Regional Group</th>
					<th width="20%">Provincial Groups</th>
				</tr>
			</thead>
			<tbody>
<%	
			for(CatCultural cultural: lisCulturales){
				String total = "0";
				if (mapaRegionPorCultural.containsKey(cultural.getCulturalId())){
					total = mapaRegionPorCultural.get(cultural.getCulturalId());	
				}	
%>			
				<tr>
					<td style="text-align: left">				
						<a href="editarGrupos?CulturalId=<%=cultural.getCulturalId()%>"><i class="fas fa-edit"></i></a>
<% 				if ( total.equals("0") ){ %>
				&nbsp;  <a href="javascript:BorrarGrupo('<%=cultural.getCulturalId()%>');"><i class="fas fa-trash-alt" style="color:red"></i></a>
<% 				} %>
					</td>
					<td align="left"><%=cultural.getCulturalId()%></td>
					<td><%=cultural.getPrincipal()%></td>
					<td><%=cultural.getNombreCultural()%></td>	
					<td class="text-center">
						<a href="regiones?CulturalId=<%=cultural.getCulturalId()%>">
							<%=total.equals("0")?"<span class='badge bg-warning rounded-pill'>0</span>":"<span class='badge bg-dark rounded-pill'>"+total+"</span>"%>
						</a>		
					</td>
				</tr>
<% 			} %>			
			</tbody>
		</table>
	</div>
<script type="text/javascript" src="../../js/search.js"></script>
<script type="text/javascript">
	/*jQuery('#buscar').focus().search({table:jQuery("#table")});*/
	$('#buscar').search();   
	
	function BorrarGrupo(culturalId) {
		if (confirm("<spring:message code="aca.JSEliminar"/>") == true){
			document.location.href = "deleteGrupo?CulturalId="+culturalId;
		}
	}	
</script>
</body>