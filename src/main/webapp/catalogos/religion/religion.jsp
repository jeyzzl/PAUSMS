<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="aca.catalogo.spring.CatReligion" %>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<html>
	<head>
		<script type="text/javascript">
			function Borrar(ReligionId) {
				if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
					document.location = "borrar?ReligionId=" + ReligionId;
				}
			}
		</script>
	</head>
<%
	List<CatReligion> lisReligion = (List<CatReligion>) request.getAttribute("lista");
	HashMap<String,String> mapaUsadas		= (HashMap<String,String>) request.getAttribute("mapaUsadas");	
%>
	<body>
<div class="container-fluid">
	<h2><spring:message code="catalogos.religion.Titulo"/></h2>
	<div class="alert alert-info">
		<a href="editar?ReligionId=" class="btn btn-primary"><spring:message code="aca.Anadir"/></a>		
	</div>
	<table class="table table-sm table-bordered">
	<thead>  
	<tr>
		<th width="10%"><spring:message code="aca.Operacion"/></th>
		<th width="15%"><spring:message code="aca.Numero"/></th>
		<th width="65%"><spring:message code="aca.Religion"/></th>
		<th width="10%" class="text-end">#Reg.</th>
	</tr>
	</thead>
<%
	for (aca.catalogo.spring.CatReligion religion : lisReligion){
		String total = "0";
		if (mapaUsadas.containsKey(religion.getReligionId())){
			total 	= mapaUsadas.get(religion.getReligionId());
		}
%>
	<tr>
		<td>
			<a href="editar?ReligionId=<%=religion.getReligionId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>
			&nbsp;
<%		if (total.equals("0")){%>				
			&nbsp;<a href="javascript:Borrar('<%=religion.getReligionId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash-alt"></i></a>
<%		} %>						</td>
		<td><%=religion.getReligionId()%></td>
		<td><%=religion.getNombreReligion()%></td>
		<td class="text-end"><%=total.equals("0")?"<span class='badge bg-warning rounded-pill'>0</span>":"<span class='badge bg-dark rounded-pill'>"+total+"</span>"%></td>
	</tr>
<%	} %>
	</table>
</div>
</body>
</html>