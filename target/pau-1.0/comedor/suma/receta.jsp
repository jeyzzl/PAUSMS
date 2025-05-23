<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.saum.spring.SaumComida"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<link rel="stylesheet" href="../../js/chosen/chosen.css" />
</head>	
<% 	
	String fechaIni		=  request.getParameter("FechaIni")==null?"01"+aca.util.Fecha.getHoy().substring(2,10):request.getParameter("FechaIni");
	String fechaFin		=  request.getParameter("FechaFin")==null?aca.util.Fecha.getHoy():request.getParameter("FechaFin");
	
	ArrayList<SaumComida> lisComidas 		= (ArrayList<SaumComida>)request.getAttribute("lisComidas");	
	HashMap<String,String> mapaRecetas		= (HashMap<String,String>)request.getAttribute("mapaRecetas");	
%>
<body>
<div class="container-fluid">
	<h1>Suma Recetas</h1>
	<form id="frmSuma" name="frmSuma" method="post" action="receta">
	<div class="alert alert-info">
		Fecha Inicio: <input type="text" class="input input-medium" data-date-format="dd/mm/yyyy" id="FechaIni" name="FechaIni" value="<%=fechaIni%>" required/>&nbsp;
		Fecha Final: <input type="text" class="input input-medium" data-date-format="dd/mm/yyyy" id="FechaFin" name="FechaFin" value="<%=fechaFin%>" required/>&nbsp;
		<a href="javascript:Refrescar()" class="btn btn-primary"><i class="fas fa-sync-alt"></i></a>&nbsp;
		<a href="editar?FechaIni=<%=fechaIni%>&FechaFin=<%=fechaFin%>" class="btn btn-primary"><i class="fas fa-plus"></i></a>&nbsp;
		<a href="calcular?FechaIni=<%=fechaIni%>&FechaFin=<%=fechaFin%>" class="btn btn-primary"><i class="fas fa-plus"></i> Calcular</a>
	</div>
	</form>
	<table id="table" class="table table-sm table-bordered">
		<thead class="table-info">	 
		<tr>
			<th>#</th>
			<th>Opción</th>
			<th>Fecha</th>
			<th>Tiempo</th>
			<th>Receta</th>
			<th>Rendimiento</th>
		</tr>
		</thead>
		<tbody>
<% 
	int row = 0;
	for (SaumComida comida : lisComidas){	
		row++;
		
		String tipo = comida.getComida();
		if (tipo.equals("1")) tipo = "Desayuno";
		if (tipo.equals("2")) tipo = "Comida";
		if (tipo.equals("3")) tipo = "Cena";
		
		String nombreReceta = "-";
		if (mapaRecetas.containsKey(comida.getRecetaId())){
			nombreReceta = mapaRecetas.get(comida.getRecetaId());
		}
%>		
		<tr>
			<td><%=row%></td>
			<td>
				<a href="javascript:Borrar('<%=comida.getFolio()%>','<%=fechaIni%>','<%=fechaFin%>')"><i class="fas fa-times fa-1x"></i></a>&nbsp;
			</td>
			<td><%=comida.getFecha()%></td>
			<td><%=tipo%></td>
			<td>
				<a href="calcular?RecetaId=<%=comida.getRecetaId()%>&FechaIni=<%=fechaIni%>&FechaFin=<%=fechaFin%>&Rendimiento=<%=comida.getRendimiento()%>">
				<%=nombreReceta%>
				</a>
			</td>
			<td>
				<a href="imprimir?RecetaId=<%=comida.getRecetaId()%>&Rendimiento=<%=comida.getRendimiento()%>&FechaIni=<%=fechaIni%>&FechaFin=<%=fechaFin%>&Tipo=<%=tipo%>">
				<%=comida.getRendimiento()%>
				</a>
			</td>
		</tr>
<%	} %>		
		</tbody>
	</table>
</div>		
</body>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script type="text/javascript">
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();
	
	function Refrescar(){
		document.frmSuma.submit();		
	}
	
	function Borrar(folio,fechaIni,fechaFin){
		if (confirm("¿Estás seguro de borrar?")){
			document.location.href="borrar?Folio="+folio+"&FechaIni="+fechaIni+"&FechaFin="+fechaFin;
		}
	}
</script>
</html>