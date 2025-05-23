<%@page import="java.util.List"%>
<%@page import="aca.conva.spring.ConvPeriodo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<script type="text/javascript">
	function BorrarPeriodo( periodoId ){	
		if ( confirm("¿Está seguro de borrar este periodo?") ){
			document.location.href="borrar?PeriodoId="+periodoId;
		}	
	}
	function ModificarPeriodo( periodoId ){		
		document.location.href="accion?Accion=5&PeriodoId="+periodoId;
			
	}
</script>
<%		
	String codigo						= (String) session.getAttribute("Codigo");	
	List<ConvPeriodo> 	lisPeriodos		= (List<ConvPeriodo>) request.getAttribute("lisPeriodos");
%>
<body>
<div class="container-fluid">
	<h1>Periodos de convalidación</h1>	
	<div class="alert alert-info">
		<a class="btn btn-primary" href="accion?Accion=1"><i class="icon-white icon-plus"></i> <spring:message code='aca.Añadir'/></a>
	</div>
	<table class="table table-sm table-striped table-bordered">
	<thead class="table-info">
		<tr>
			<th width="10%"><spring:message code="aca.Operacion"/></th>
			<th width="5%"><spring:message code="aca.Numero"/></th>
			<th width="55%"><spring:message code="aca.Periodo"/></th>
			<th width="10%">Fecha Inicial</th>
			<th width="10%">Fecha Final</th>
			<th width="10%">Carreras</th>
		</tr>
	</thead>
	<tbody>	
<%
	int row = 0;
	for (ConvPeriodo periodo : lisPeriodos){
		row++;
		String[] carreras 	= periodo.getCarrera().split("-");
		int total = carreras.length;
%>
		<tr>
			<td>
<%		if (total==0){%>			
				<a class="fas fa-trash-alt" href="javascript:BorrarPeriodo('<%=periodo.getPeriodoId()%>')"></a>&nbsp;
<%		} %>				
				<a class="fas fa-edit" href="javascript:ModificarPeriodo('<%=periodo.getPeriodoId()%>')"></a>
			</td>
			<td><%=row%></td>			
			<td><a href="listaCarreras?PeriodoId=<%=periodo.getPeriodoId()%>">[<%=periodo.getPeriodoId()%>] <%=periodo.getPeriodoNombre()%></a></td>
			<td><%=periodo.getFechaIni()%></td>
			<td><%=periodo.getFechaFin()%></td>
			<td><%=total%></td>
		</tr>		
<%	} %>
	</tbody>
	</table>
</div>
</body>