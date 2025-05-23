<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatNivel"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>	
<%
	List<CatNivel> lisNiveles 	= (List<CatNivel>) request.getAttribute("lisNiveles");
	HashMap<String,String> mapaUsados	= (HashMap<String,String>) request.getAttribute("mapaUsados");
%>
<body>
<div id="app" class="container-fluid">
	<h1><spring:message code="catalogos.niveles.Titulo"/></h1>
	<div class="alert alert-info" :class="'d-flex align-items-center'">
		<a class="btn btn-primary" href="editarNivel"><spring:message code="aca.Anadir"/></a>&nbsp;		
	</div>
	<table class="table table-sm table-bordered"> 
	<tr>
		<th width="17%"><spring:message code="aca.Operacion"/></th>
		<th width="15%"><spring:message code="aca.Clave"/></th>
		<th width="68%"><spring:message code="catalogos.niveles.Niveles"/></th>
		<th width="24%">Used</th>
	</tr>
<%			
	for (CatNivel nivel : lisNiveles) {	
		String total = "0";
		if (mapaUsados.containsKey(nivel.getNivelId())){
			total 	= mapaUsados.get(nivel.getNivelId());
		}
%>
	<tr>
		<td style="text-align: left">
			<a href="editarNivel?NivelId=<%=nivel.getNivelId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>
			&nbsp;
<%		if (total.equals("0")){%>			
			&nbsp;<a href="javascript:Borrar('<%=nivel.getNivelId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash-alt"></i></a>
<%		} %>
		</td>
		<td><%=nivel.getNivelId()%></td>
		<td><%=nivel.getNombreNivel()%></td>
		<td class="text-end"><%=total.equals("0")?"<span class='badge bg-warning rounded-pill'>0</span>":"<span class='badge bg-dark rounded-pill'>"+total+"</span>"%></td>
	</tr>
<%
	}
%>
	</table>
</div>
<script type="text/javascript">
	function Borrar(nivelId) {
		if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
			document.location.href = "borrarNivel?NivelId=" + nivelId;
		}
	}
</script>
</body>
</html>