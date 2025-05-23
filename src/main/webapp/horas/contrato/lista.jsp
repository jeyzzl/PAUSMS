<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.emp.spring.EmpContrato"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	String yearActual		= aca.util.Fecha.getHoy().substring(6,10);
	String year 			= request.getParameter("Year")==null?yearActual:request.getParameter("Year");
	
	List<String> lisEmpleados 					= (List<String>)request.getAttribute("lisEmpleados");
	HashMap<String,String> mapaMaestros 		= (HashMap<String,String>)request.getAttribute("mapaMaestros");
	HashMap<String,String> mapaNumContratos 	= (HashMap<String,String>)request.getAttribute("mapaNumContratos");
	HashMap<String,String> mapaImporteContratos = (HashMap<String,String>)request.getAttribute("mapaImporteContratos");
	HashMap<String,String> mapaMaterias 		= (HashMap<String,String>)request.getAttribute("mapaMaterias");
	HashMap<String, String> mapaRegistradas		= (HashMap<String,String>)request.getAttribute("mapaRegistradas");
	HashMap<String,String> mapaPendientes 		= (HashMap<String,String>)request.getAttribute("mapaPendientes");
	HashMap<String,String> mapaFirmados 		= (HashMap<String,String>)request.getAttribute("mapaFirmados");
%>
<head>	
</head>
<body>
<div class="container-fluid">
	<h2>Contratos Maestros</h2>		
	<form name="frmYear" action="lista" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<select name="Year" class="form-select" style="width:120px" onchange="document.frmYear.submit();">
			<option value="2023" <%=year.equals("2023")?"selected":""%>>2023</option>
			<option value="2022" <%=year.equals("2022")?"selected":""%>>2022</option>
			<option value="2021" <%=year.equals("2021")?"selected":""%>>2021</option>
			<option value="2020" <%=year.equals("2020")?"selected":""%>>2020</option>
			<option value="2019" <%=year.equals("2019")?"selected":""%>>2019</option>			
		</select>&nbsp;&nbsp;&nbsp;	
		<input type="text" class="form-control" style="width:120px"  placeholder="Buscar..." id="buscar">&nbsp;	
		<a class="btn btn-primary" href="javascript:Refrescar()">Refrescar</a>
	</div>
	</form>	
	<table id="table" class="table table-sm table-bordered">
	<thead>
	<tr class="table-info">
		<th>Op.</th>
		<th>N° Nómina</th>
		<th>Maestro</th>
		<th class="text-end">#Contratos</th>
		<th class="text-end">#Firmados</th>
		<th class="text-end">#Materias</th>
		<th class="text-end">#Mat.Reg.</th>
		<th class="text-end">#Mat.Pend.</th>
		<th class="text-end">Importe</th>
	</tr>
	</thead>
	<tbody>
<%
	int row = 0;
	for(String emp : lisEmpleados){
		
		String maestroNombre = "-";
		if (mapaMaestros.containsKey(emp)){
			maestroNombre = mapaMaestros.get(emp);
		}
		
		String numContratos = "0";
		if(mapaNumContratos.containsKey(emp)){			
			numContratos = mapaNumContratos.get(emp);
		}
		String textoContrato = "<span class='badge bg-warning'>"+numContratos+"</span>";
		if (!numContratos.equals("0")) textoContrato = "<span class='badge bg-success'>"+numContratos+"</span>";
		
		String importe = "0.00";
		if(mapaImporteContratos.containsKey(emp)){
			importe = mapaImporteContratos.get(emp);
		}
		
		String numMaterias = "0";
		if(mapaMaterias.containsKey(emp)){
			numMaterias = mapaMaterias.get(emp);
		}
		String textoMaterias = "<span class='badge bg-warning'>"+numMaterias+"</span>";
		if(!numMaterias.equals("0")) textoMaterias = "<span class='badge bg-dark'>"+numMaterias+"</span>";
		
		String numPendientes = "0";
		if(mapaPendientes.containsKey(emp)){
			numPendientes = mapaPendientes.get(emp);
		}
		String textoPendientes = "<span class='badge bg-success'>"+numPendientes+"</span>";
		if(!numPendientes.equals("0")) textoPendientes = "<span class='badge bg-warning'>"+numPendientes+"</span>";
		
		String numRegistradas = "0";
		if(mapaRegistradas.containsKey(emp)){
			numRegistradas = mapaRegistradas.get(emp);
		}
		
		String textoRegistradas = "<span class='badge bg-success'>"+numRegistradas+"</span>";
		if(numRegistradas.equals("0")) textoRegistradas = "<span class='badge bg-warning'>"+numRegistradas+"</span>";
		
		String numFirmados = "0";
		if (mapaFirmados.containsKey(emp)){
			numFirmados = mapaFirmados.get(emp);
		}
		
		row++;
%>
	<tr>
		<td><%=row%></td>
		<td><%=emp%></td>
		<td><%=maestroNombre%></td>
		<td class="text-end">
			<a href="contratos?EmpleadoId=<%=emp%>&Year=<%=year%>"><%=textoContrato%></a>
		</td>
		<td class="text-end"><%=numFirmados%></td>
		<td class="text-end">
			<a href="listaMaterias?CodigoEmpleado=<%=emp%>&Year=<%=year%>"><%=textoMaterias%></a>
		</td>
		<td class="text-end">
			<a href="listaMaterias?CodigoEmpleado=<%=emp%>&Year=<%=year%>"><%=textoRegistradas%></a>
		</td>
		<td class="text-end">
			<a href="listaMaterias?CodigoEmpleado=<%=emp%>&Year=<%=year%>"><%=textoPendientes%></a>
		</td>
		<td class="text-end"><%=formato.format(Double.valueOf(importe))%></td>
	</tr>
<%
	}
%>
	</tbody>
	</table>
</div>
</body>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#table")});

	function Refrescar(){
		document.frmYear.submit();
	}
</script>