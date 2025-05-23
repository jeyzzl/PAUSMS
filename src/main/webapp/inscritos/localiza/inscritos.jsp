<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">	
	function Mostrar(){	
		document.forma.submit();
	}
</script>
<%	
	String fechaIni		= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin		= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	String accion 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	if (request.getParameter("FechaIni")!=null) session.setAttribute("fechaIni", request.getParameter("FechaIni"));
	if (request.getParameter("FechaFin")!=null) session.setAttribute("fechaFin", request.getParameter("FechaFin"));
	
	// Lista de cargas
	List<String> lisCargas 					= (List<String>) request.getAttribute("lisCargas");	
	// Lista de cargas
	List<CatModalidad> lisModalidades 		= (List<CatModalidad>) request.getAttribute("lisModalidades");
	// Alumnos por carga
	HashMap<String,String> mapAlumCarga 	= (HashMap<String,String>) request.getAttribute("mapaAlumnos");	
	//mapaAlumMod
	HashMap<String,String> mapAlumMod 		= (HashMap<String,String>) request.getAttribute("mapaAlumMod");
%>
<head>	
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
</head>
<body>
<div class="container-fluid">
	<h2>Locate Enrolled by Date</h2>
	<form id="forma" name="forma" action="inscritos?Accion=1" method="post">
	<div class="alert alert-info d-flex align-items-center">
		Strat Date: <input id="FechaIni" name="FechaIni" type="text" class="form-control" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10" style="width:120px"/>&nbsp;&nbsp;
		End Date: <input id="FechaFin" name="FechaFin" type="text" class="form-control" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10" style="width:120px"/>&nbsp;&nbsp;
		<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync"></i></a>
	</div>
	</form>
	<table class="table table-sm table-bordered table-striped">
	<thead>
	<tr class="table-info">
		<th>#</th>
		<th>Load ID</th>
		<th>Load Name</th>
		<th>Modality</th>
		<th class="right">Total Modality</th>
		<th class="right">Total</th>
	</tr>
	</thead>
	<tbody>
<%
	int row = 0;
	for (String carga : lisCargas){
		row++;
		String total = "0";
		if (mapAlumCarga.containsKey(carga)){
			total = mapAlumCarga.get(carga);
		}			
%>
	<tr class="table-dark">
		<td><%=row%></td>
		<td><%=carga%></td>
		<td><%=carga%></td>
		<td></td>
		<td></td>
		<td class="right"><%=total%></td>
	</tr>
<%				
		for (CatModalidad modalidad: lisModalidades) {
			boolean existe = false;
			String totModalidad = "0";
			if (mapAlumMod.containsKey(carga+modalidad.getModalidadId())){
				existe = true;
				totModalidad = mapAlumMod.get(carga+modalidad.getModalidadId());
			}
			if (existe){
%>							
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td><%=modalidad.getNombreModalidad()%> </td>
				<td class="right"><%=totModalidad%></td>
				<td></td>
			</tr>					
<%
			}
		}	
	}
%>			
	</tbody>	
	</table>	
</div>
</body>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>