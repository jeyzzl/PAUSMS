<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.residencia.spring.ResPeriodo"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<head>
</head>	
<%	
	List<ResPeriodo> lisPeriodo					= (List<ResPeriodo>)request.getAttribute("lisPeriodo");	
	HashMap<String,String> mapaRegistrados		= (HashMap<String,String>)request.getAttribute("mapaRegistrados");
	HashMap<String,String> mapaActualizados		= (HashMap<String,String>)request.getAttribute("mapaActualizados");
%>
<body>
<div class="container-fluid">
	<h1>Cycle List</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary btn-small" title="Nuevo periodo" href="nuevo">New</a>
		<a class="btn btn-primary btn-small" title="Copiar periodo" href="traspaso">Transfer</a>
	</div>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">
	<tr>
		<th width="5%">&nbsp;</th>
		<th width="5%">#</th>			
		<th width="20%">Cycle Name</th>
		<th width="10%">Start Date</th>
		<th width="10%">End Date</th>	
		<th width="5">Status</th>	
		<th width="10%" class="left">Students</th>
		<th width="10%" class="left">Updated</th>
		<th width="10%" class="left">Pending</th>
	</tr>
	</thead>				
<%
	int con = 1;
	for (ResPeriodo periodo : lisPeriodo) {
		
		String alumnos 			= "0";
		String etiquetaAlumnos 	= "<span class='badge bg-warning'>0</span>";
		if (mapaRegistrados.containsKey(periodo.getPeriodoId())){
			alumnos 			= mapaRegistrados.get(periodo.getPeriodoId());
			etiquetaAlumnos		= "<span class='badge bg-dark'>"+alumnos+"</span>";
		}
		
		String actualizados			= "0";
		String etiquetaActualizados	= "<span class='badge bg-warning'>0</span>";
		if (mapaActualizados.containsKey(periodo.getPeriodoId())){
			actualizados 			= mapaActualizados.get(periodo.getPeriodoId());
			etiquetaActualizados	= "<span class='badge bg-info'>"+alumnos+"</span>";
		}
		
		int pendientes 		= Integer.parseInt(alumnos)-Integer.parseInt(actualizados);
		String etiquetaPendientes 	= "<span class='badge bg-warning'>"+pendientes+"</span>";
		if (pendientes==0){
			etiquetaPendientes = "<span class='badge bg-dark'>"+pendientes+"</span>";
		}
%>
		<tr>
			<td ><a href="nuevo?PeriodoId=<%=periodo.getPeriodoId()%>" class="fas fa-edit"></a></td>
			<td ><%=con++%></td>
			<td ><%=periodo.getPeriodoNombre()%></td>
			<td ><%=periodo.getFechaIni()%></td>
			<td ><%=periodo.getFechaFin()%></td>
			<td><%=periodo.getEstado().equals("A")?"Active":"Inactive"%></td>
			<td class="left"><a href="alumnos?PeriodoId=<%=periodo.getPeriodoId()%>"><%=etiquetaAlumnos%></td>
			<td class="left"><%=etiquetaActualizados%></td>
			<td class="left"><%=etiquetaPendientes%></td>
		</tr>
		<%
	}	
	%>
	</table>
</div>
</body>
<script type="text/javascript"> 
</script>
