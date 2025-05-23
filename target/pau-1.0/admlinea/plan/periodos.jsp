<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmIngreso"%>
<%@page import="aca.admision.spring.AdmIngresoPlan"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>
<html>
<head></head>
<script type="text/javascript">
	function cambioPeriodo(){		
		document.frmPeriodo.submit();
	}
	function Delete(periodoId){
		if(confirm("¿Estás seguro de borrar este item?")){
			document.location.href="deletePeriodo?PeriodoId="+periodoId;
		}		
	}
</script>
<%		

	List<AdmIngreso> lisPeriodos 	= (List<AdmIngreso>)request.getAttribute("lisPeriodos");
HashMap<String,String> mapCursos  	= (HashMap<String, String>)request.getAttribute("mapCursos");
	String mensaje 					= "-";
	
%>
<body>
<div class="container-fluid">
	<h2><spring:message code='aca.Periodos'/> <spring:message code='aca.Listado'/>: </h2>
	<form name="frmPeriodo" action="ingreso" method="post">
	<div class="alert alert-info">
		<a class="btn btn-primary btn-small" href="ingreso"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		<a href="editaPeriodos" class="btn btn-primary"><i class="icon-white fas fa-plus"></i> <spring:message code='aca.Añadir'/></a>
		<%if(!mensaje.equals("-")) out.print(mensaje); %>
	</div>	
	</form>	
	<table class="table table-bordered table-sm" style="width:100%">
	<thead class="table-dark">
	<tr>
		<th width="3%">#</th>		
		<th width="5%">Id</th>
		<th width="30%"><spring:message code='aca.Nombre'/></th>
		<th width="15%"><spring:message code='aca.Estado'/></th>
		<th width="15%"><spring:message code='aca.FechaInicial'/></th>
		<th width="10%"><spring:message code='aca.NumeroPlanes'/></th>
	</tr>
	</thead>	
<%	int cont = 1;
	for (AdmIngreso periodo : lisPeriodos){
		
		String badgeCursos 	= "<span class='badge bg-warning rounded-pill'>0</span>";
		String numCursos 	= "0";
		if(mapCursos.containsKey(periodo.getPeriodoId())){
			numCursos = mapCursos.get(periodo.getPeriodoId());
			badgeCursos 	= "<span class='badge bg-dark rounded-pill'>"+numCursos+"</span>" ;
			
		}
		
%>
	<tr>
		<td><%=cont++%></td>		
		<td><%=periodo.getPeriodoId() %>
		<a href="editaPeriodos?PeriodoId=<%=periodo.getPeriodoId()%>"><i class="fas fa-pen-square"></i></a>
		  <%if ( numCursos.equals("0") || numCursos.equals(null)){%>
		   <a href="javascript:Delete('<%=periodo.getPeriodoId()%>')" title="<spring:message code="aca.Borrar"/>" style="color:red;">
			<i class="fas fa-trash-alt"></i></a>
		</td>
<%}%>
		<td><%=periodo.getPeriodoNombre() %></td>
		<td><%=periodo.getEstado().equals("I")?"<span class='badge bg-warning rounded-pill'>Inactive</span>":"<span class='badge bg-dark rounded-pill'>Active</span>"%></td>
		<td><%=periodo.getFecha() %></td>
		<td><%=badgeCursos%></td>
	</tr>	
<% }%>
</table>		
</div>
</body>
</html>