<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>

<script type="text/javascript">
	function eliminar(periodoId){
		if (confirm("¿Estas seguro de borrar este registro?")){
			location.href="eliminar?PeriodoId="+periodoId;
		}	
	}
	
	function ModificarPeriodo( periodoId ){		
		document.location.href="accion.jsp?Accion=5&PeriodoId="+periodoId;
			
	}
</script>

<%	
	List<aca.emp.spring.EmpPeriodo> lisPeriodos	= (List<aca.emp.spring.EmpPeriodo>)request.getAttribute("lista");
%>
<body >
<div class="container-fluid">
	<h1>Periodos</h1>
	<form name="forma" action="catalogo.jsp" method='post' id="noayuda">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="nuevo"><i class="icon-white icon-plus"></i> <spring:message code='aca.Añadir'/></a>
	</div>

	<table  class="table table-sm table-bordered table-striped">
		<tr class="table-info">
			<th width="10%"><spring:message code="aca.Operacion"/></th>
			<th width="5%"><spring:message code="aca.Numero"/></th>			
			<th width="55%"><spring:message code="aca.Periodo"/></th>
			<th width="10%">Fecha Inicial</th>			
			<th width="10%">Fecha Final</th>
		</tr>
<%
	int row = 0;
	for (aca.emp.spring.EmpPeriodo periodo : lisPeriodos){
		row++;
%>
		<tr>
			<td>
				<a class="fas fa-trash-alt" href="javascript:eliminar('<%=periodo.getPeriodoId()%>')"></a>&nbsp;
				<a href="nuevo?PeriodoId=<%=periodo.getPeriodoId()%>" title="Modificar"><i class="fas fa-pencil-alt"></i></a>
			</td>
			<td><%=row%></td>			
			<td><%=periodo.getPeriodoNombre()%></td>
			<td><%=periodo.getFechaIni()%></td>
			<td><%=periodo.getFechaFin()%></td>
		</tr>		
<%		
	}
%>		
	</table>
	</form>
</div>
</body>
