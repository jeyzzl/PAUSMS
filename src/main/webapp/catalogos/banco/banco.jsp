<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="aca.catalogo.spring.CatBanco" %>
<%@ page import="aca.catalogo.spring.CatPais" %>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<html>
	<head>
		<script type="text/javascript">
			function Borrar(BancoId) {
				if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
					document.location = "borrar?BancoId=" + BancoId;
				}
			}
		</script>
	</head>
<%
	List<CatBanco> lista = (List<CatBanco>) request.getAttribute("lista");
	HashMap<String,String> mapaUsadas		= (HashMap<String,String>) request.getAttribute("mapaUsadas");	
	HashMap<String,CatPais> mapaPais		= (HashMap<String,CatPais>) request.getAttribute("mapaPais");	
%>
	<body>
<div class="container-fluid">
	<h2>Bank Catalog</h2>
	<div class="alert alert-info">
		<a href="editar?BancoId=" class="btn btn-primary"><spring:message code="aca.Anadir"/></a>		
	</div>
	<table class="table table-sm table-bordered">
	<thead>  
	<tr>
		<th width="5%">No.</th>
		<th width="5%">ID</th>
		<th width="">Name</th>
		<th width="">Short Name</th>
		<th width="">Country</th>
		<th width="">Swift</th>
		<th width="10%" class="text-end">#Reg.</th>
	</tr>
	</thead>
<%
	for (CatBanco banco : lista){
		String total = "0";
		if (mapaUsadas.containsKey(banco.getBancoId())){
			total 	= mapaUsadas.get(banco.getBancoId());
		}

		String nombrePais = "";
		if (mapaPais.containsKey(banco.getPaisId())){
			nombrePais = mapaPais.get(banco.getPaisId()).getNombrePais();
		}
%>
	<tr>
		<td>
			<a href="editar?BancoId=<%=banco.getBancoId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>
			&nbsp;
<%		if (total.equals("0")){%>				
			&nbsp;<a href="javascript:Borrar('<%=banco.getBancoId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash-alt"></i></a>
<%		} %>						</td>
		<td><%=banco.getBancoId()%></td>
		<td><%=banco.getNombre()%></td>
		<td><%=banco.getNombreCorto()%></td>
		<td><%=nombrePais%></td>
		<td><%=banco.getSwift()%></td>
		<td class="text-end"><%=total.equals("0")?"<span class='badge bg-warning rounded-pill'>0</span>":"<span class='badge bg-dark rounded-pill'>"+total+"</span>"%></td>
	</tr>
<%	} %>
	</table>
</div>
</body>
</html>