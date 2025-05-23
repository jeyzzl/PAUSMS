<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatTipoAlumno"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<html>
<head>
	<script type="text/javascript">
		function Borrar(tipoId) {
			if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
				document.location = "borrarTipo?TipoId="+tipoId;
			}
		}
	</script>
</head>
<%
	List<CatTipoAlumno> lisTipos		= (List<CatTipoAlumno>)request.getAttribute("lisTipos");
	HashMap<String,String> mapaUsados	= (HashMap<String,String>) request.getAttribute("mapaUsados");
%>
<body>
<div class="container-fluid">
	<h1><spring:message code="catalogos.tipoAlumno.Titulo"/></h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="editarTipo?TipoId="><spring:message code="aca.Anadir"/></a>	
	</div>	
	<table class="table table-sm table-bordered">  
		<tr>
			<th width="17%"><spring:message code="aca.Operacion"/></th>
			<th width="15%"><spring:message code="aca.Numero"/></th>
			<th width="68%">Student Type</th>
			<th width="68%">#Reg.</th>
		</tr>
<%
	for (CatTipoAlumno tipo : lisTipos){
		String total = "0";
		if (mapaUsados.containsKey(tipo.getTipoId())){
			total 	= mapaUsados.get(tipo.getTipoId());
		}
%>
		<tr>
			<td style="text-align: center">
				<a href="editarTipo?TipoId=<%=tipo.getTipoId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>
				&nbsp;
<%		if (total.equals("0")){%>				
				&nbsp;<a href="javascript:Borrar('<%=tipo.getTipoId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash-alt"></i></a>
<%		} %>
			</td>
			<td align="center"><%=tipo.getTipoId()%></td>
			<td><%=tipo.getNombreTipo()%></td>
			<td class="text-end"><%=total.equals("0")?"<span class='badge bg-warning rounded-pill'>0</span>":"<span class='badge bg-dark rounded-pill'>"+total+"</span>"%></td>		
		</tr>
<%
	}
%>
	</table>
</div>
</body>
</html>