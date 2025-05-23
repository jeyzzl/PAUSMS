<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.trabajo.spring.TrabInforme"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar(informeId) {
		if(confirm("<spring:message code="aca.JSEliminar"/> " ) == true) {
			document.location.href = "borrarInforme?informeId="+informeId;
		} 
	}
</script>
<%
	List<TrabInforme> lisInformes	= (List<TrabInforme>) request.getAttribute("lisInformes");
	HashMap<String,String> mapaPeriodoNombre 	= (HashMap<String,String>) request.getAttribute("mapaPeriodoNombre");
	HashMap<String,String> mapaHorasRegistradas	= (HashMap<String,String>) request.getAttribute("mapaHorasRegistradas");
%>
<body>
<div class="container-fluid">
	<h2>CTP Reports Catalog</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="editarInforme"><spring:message code="aca.Anadir"/></a>
	</div>
	
	<table class="table table-sm table-bordered">  
	<thead class="table-info">	 
	<tr>
		<th width="5%"><spring:message code="aca.Operacion"/></th>
		<th width="5%"><spring:message code="aca.Numero"/></th>
		<th width="30%">Period</th>
		<th width="50%">Report Name</th>
		<th width="10%">Status</th>
	</tr>
	</thead>
<%
	for (TrabInforme informe : lisInformes){
		
		String horasRegistradas = "0";
		if(mapaHorasRegistradas.containsKey(informe.getInformeId())){
			horasRegistradas = mapaHorasRegistradas.get(informe.getInformeId());
		}
		
		String estado = informe.getEstado();
		
%>
	<tr>
		<td style="text-align: left">
			<a  href="editarInforme?informeId=<%=informe.getInformeId()%>" title="<spring:message code="aca.Editar"/>"><i class="fas fa-edit"></i></a>&nbsp;
<%	
	if(horasRegistradas.equals("0")){
%>
	    	<a href="javascript:Borrar('<%=informe.getInformeId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash-alt"></i></a>
<% } %>
		</td>
		<td align="left"><%=informe.getInformeId()%></td>
		<td><%=mapaPeriodoNombre.get(informe.getPeriodoId())%></td>
		<td><%=informe.getNombreInforme()%></td>
		<td><%=estado.equals("A")?"Active":"Inactive"%></td>
	</tr>
<%
	}				
%>
	</table>
</div>
</body>