<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatModalidad"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<html>
<head>
	<script src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>   
	<script src="https://cdn.datatables.net/1.12.1/js/dataTables.bootstrap5.min.js"></script>
	<link rel="stylesheet" href="https://cdn.datatables.net/1.12.1/css/dataTables.bootstrap5.min.css">
</head>
<%	
	//Lista de modalidades 
	List<CatModalidad> lisModalidad 		= (List<CatModalidad>) request.getAttribute("lista");
	HashMap<String,String> mapaModalidades	= (HashMap<String,String>) request.getAttribute("mapaModalidades");
%>
<body>
<div class="container-fluid">
	<h2><spring:message code="catalogos.modalidades.Titulo"/></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="accion?Accion=1"><spring:message code="aca.Anadir"/></a>
	</div>
	<table class="table table-sm table-bordered" id="tabla"> 
	<thead>
		<tr>
			<th width="17%">Op</th>
			<th width="17%"><spring:message code="aca.Clave"/></th>
			<th><spring:message code="aca.Modalidad"/></th>
			<th><spring:message code="catalogos.modalidades.EnLinea"/></th>
			<th><spring:message code="catalogos.modalidades.Admisible"/></th>
			<th class="text-end">Records</th>
		</tr>
	</thead>
	<tbody>	
<%				
	for (CatModalidad modalidad: lisModalidad){
		String total = "0";
		if (mapaModalidades.containsKey(modalidad.getModalidadId())){
			total 	= mapaModalidades.get(modalidad.getModalidadId());
		}
%>
		<tr>
			<td style="text-align: left">
				<a href="accion?Accion=5&ModalidadId=<%=modalidad.getModalidadId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>
<%		if (total.equals("0")){%>
				&nbsp;<a href="javascript:Borrar('<%=modalidad.getModalidadId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash-alt"></i></a>
<%		} %>
			</td>
			<td align="left"><%=modalidad.getModalidadId()%></td>
			<td><%=modalidad.getNombreModalidad()%></td>
			<td><%=modalidad.getEnLinea()!=null&&modalidad.getEnLinea().equals("S")?"Yes" : "No" %></td>
			<td><%=modalidad.getAdmisible()!=null&&modalidad.getAdmisible().equals("S")?"Yes" : "No" %></td>
			<td class="text-end"><%=total.equals("0")?"<span class='badge bg-warning rounded-pill'>0</span>":"<span class='badge bg-dark rounded-pill'>"+total+"</span>"%></td>
		</tr>
<%
	}	
%>
	</tbody>	
	</table>
</div>
<script type="text/javascript">
	function Borrar(ModalidadId) {
		if (confirm("<spring:message code="aca.JSEliminar"/>" ) == true) {
			document.location = "borrar?ModalidadId="+ ModalidadId;
		}
	}
</script>
</body>
</html>