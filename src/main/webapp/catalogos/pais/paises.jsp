<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatPais"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<head>
	<meta charset="iso-8859-1">			
</head>
<%	
	List<CatPais> lisPaises 					= (List<CatPais>) request.getAttribute("lisPaises");
	HashMap<String,String> mapaEstadosPorPais 	= (HashMap<String,String>) request.getAttribute("mapaEstadosPorPais");
%>
<body>
<div class="container-fluid">
	<h2><spring:message code="catalogos.paises.Titulo"/></h2>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="editarPais"><spring:message code="aca.Anadir"/></a>&nbsp;&nbsp;
		<input type="text" class="form-control search-query" placeholder="Search.." id="buscar" style="width:170px;">
	</div>	
	<table id="table" class="table table-sm table-bordered">
	<thead>  
	<tr>
		<th width="10%">Op.</th>
		<th width="9%"><spring:message code="aca.Numero"/></th>
		<th width="37%"><spring:message code="aca.Pais"/></th>
		<th width="37%"><spring:message code="aca.Nacionalidad"/></th>
		<th width="37%"><spring:message code="catalogos.paises.Interamericana"/></th>			
		<th width="5%"><spring:message code="catalogos.paises.Estados"/>/States</th>
	</tr>
	</thead>	
	<tbody>		
<%	
	for(CatPais catPais: lisPaises){
		String total = "0";
		if (mapaEstadosPorPais.containsKey(catPais.getPaisId())){
			total = mapaEstadosPorPais.get(catPais.getPaisId());			
		}	
%>	
	<tr>
		<td style="text-align: left">				
			<a href="editarPais?PaisId=<%=catPais.getPaisId()%>"><i class="fas fa-edit"></i></a>
<% 		if ( total.equals("0") ){%>
			&nbsp; <a href="javascript:BorrarPais('<%=catPais.getPaisId()%>');"><i class="fas fa-trash-alt" style="color:red"></i></a>
<% 		}%>				
		</td>
		<td align="left"><%=catPais.getPaisId()%></td>
		<td><a href="estados?PaisId=<%=catPais.getPaisId()%>"><%=catPais.getNombrePais()%></a></td>
		<td><%=catPais.getNacionalidad()%></td>
		<td><%=catPais.getInteramerica()%></td>			
		<td class="text-center">
			<%=total.equals("0")?"<span class='badge bg-warning rounded-pill'>0</span>":"<span class='badge bg-dark rounded-pill'>"+total+"</span>"%>		
		</td>
	</tr>	
<% 	}%>		
	</tbody>	
	</table>	
</div>
<script type="text/javascript" src="../../js/search.js"></script>
<script type="text/javascript">
	/*jQuery('#buscar').focus().search({table:jQuery("#table")});*/
	$('#buscar').search();   
	
	function BorrarPais(paisId) {
		if (confirm("<spring:message code="aca.JSEliminar"/>") == true){
			document.location.href = "deletePais?PaisId="+paisId;
		}
	}	
</script>
</body>