<%@page import="aca.util.Fecha"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.emp.spring.EmpContrato"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	 
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");	
	
	String yearActual			= aca.util.Fecha.getHoy().substring(6,10);
	String year 				= request.getParameter("Year")==null?yearActual:request.getParameter("Year");
	String empleadoId 			= request.getParameter("EmpleadoId")==null?"0":request.getParameter("EmpleadoId");	
	String empleadoNombre 		= (String)request.getAttribute("empleadoNombre");
	
	List<EmpContrato> lisContratos 			= (List<EmpContrato>)request.getAttribute("lisContratos");
	HashMap<String,String> mapaMaterias		= (HashMap<String,String>)request.getAttribute("mapaMaterias");	
%>
<head>	
</head>
<body>
<div class="container-fluid">
	<h2>Contratos del maestro <small class="text-muted fs-4">( <%=empleadoId%>-<%=empleadoNombre%>-<%=year%>)</small></h2>
	<form name="forma" action="contratos" method="post">
	<div class="alert alert-info">	
		<a class="btn btn-primary" href="lista?Year=<%=year%>">Regresar</a>&nbsp;&nbsp;
		<a class="btn btn-primary" href="editar?Year=<%=year%>">Nuevo</a>
	</div>
	</form>	
	<table class="table table-sm" style="width:100%">
	<thead>
	<tr>
		<th width="3%">Op.</th>
		<th width="5%">Folio</th>
		<th width="5%">Firma</th>
		<th width="5%">Alta</th>
		<th width="5%">#Materias</th>
		<th width="5%">F.Inicio</th>
		<th width="5%">F.Fin</th>
		<th width="5%">Institución</th>
		<th width="46%">Comentario</th>
		<th width="5%">Importe</th>				
		<th width="5%">Días</th>
		<th width="5%">Salario</th>
		<th width="5%">Estado</th>
	</tr>
	</thead>
	<tbody>
<%		
	for(EmpContrato contrato : lisContratos){
		
		String numMat = "0";
		String textoMaterias = "<span class='badge bg-warning'>0"+"<span>";
		if (mapaMaterias.containsKey(contrato.getContratoId())){
			numMat 	= mapaMaterias.get(contrato.getContratoId());
		}
		
		if (!numMat.equals("0")){
			textoMaterias = "<span class='badge bg-success'>"+numMat+"<span>";
		}
		
		float dias 		= aca.util.Fecha.diasEntreFechas(contrato.getFechaIni(), contrato.getFechaFin()) + 1;  
		float salario 	= 0;
		if (Float.valueOf(contrato.getCosto()) > 0 && dias > 0){
			salario	= Float.valueOf(contrato.getCosto()) / dias;
		}
		
		String estado = "Activo";
		if (contrato.getEstado().equals("F")) estado = "Firma";
		if (contrato.getEstado().equals("P")) estado = "Pagado";
		
%>	
	<tr>
		<td>
			<a href="editar?Year=<%=year%>&ContratoId=<%=contrato.getContratoId()%>" title="Modificar"><i class="fas fa-pencil-alt"></i></a>
			&nbsp;&nbsp;
<%		if (numMat.equals("0")){ %>
			<a href="borrar?Year=<%=year%>&ContratoId=<%=contrato.getContratoId()%>" title="Borrar"><i class="fas fa-trash-alt"></i></a>
<%		} %>			
		</td>
		<td><%=contrato.getContratoId()%></td>	
		<td><%=contrato.getFirma().equals("S")?"SI":"NO"%></td>
		<td><%=contrato.getFecha()%></td>
		<td>
			<a href="materias?Year=<%=year%>&ContratoId=<%=contrato.getContratoId()%>">
			<%=textoMaterias%>
			</a>
		</td>
		<td><%=contrato.getFechaIni() %></td>
		<td><%=contrato.getFechaFin() %></td>
		<td class="center"><%=contrato.getInstitucion() %></td>		
		<td><%=contrato.getComentario()%></td>
		<td><%=formato.format(Double.valueOf(contrato.getCosto()))%></td>	
		<td><%=dias%></td>
		<td><%=formato.format(Double.valueOf(salario))%></td>
		<td><%=estado%></td>
	</tr>
<%	} %>		
	</tbody>
	</table>
</div>
</body>