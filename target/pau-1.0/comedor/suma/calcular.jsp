<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.saum.spring.SaumComida"%>
<%@page import="aca.saum.spring.SaumReceta"%>
<%@page import="aca.saum.spring.SaumMateria"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<link rel="stylesheet" href="../../js/chosen/chosen.css" />
	<link rel="stylesheet" href="../../js/tablesorter/themes/blue/style.css"/>
</head>	
<% 	
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String recetaId		= request.getParameter("RecetaId")==null?"0":request.getParameter("RecetaId");
	String fechaIni		= request.getParameter("FechaIni")==null?aca.util.Fecha.getHoy():request.getParameter("FechaIni");
	String fechaFin		= request.getParameter("FechaFin")==null?aca.util.Fecha.getHoy():request.getParameter("FechaFin");
	String rendimiento	= request.getParameter("Rendimiento")==null?"0":request.getParameter("Rendimiento");
	String recetaNombre	= (String)request.getAttribute("recetaNombre");
	
	ArrayList<SaumComida> lisComidas 			= (ArrayList<SaumComida>)request.getAttribute("lisComidas");	
	HashMap<String,SaumReceta> mapaRecetas		= (HashMap<String,SaumReceta>)request.getAttribute("mapaRecetas");	
	HashMap<String,String> mapaIngrediente		= (HashMap<String,String>)request.getAttribute("mapaIngrediente");
	HashMap<String,SaumMateria> mapaMateria		= (HashMap<String,SaumMateria>)request.getAttribute("mapaMateria");
	int row	= 0;
%>
<body>
<div class="container-fluid">
<%	
	if (recetaId.equals("0")){
		out.print("<h2>Recetas e ingredientes</h2>");
	}else{
		out.print("<h2>Receta e ingredientes <small class='text-muted fs-4'>( "+recetaNombre+" - rendimiento:"+rendimiento+")</small></h2>");
	}
%>
	
	<form id="frmSuma" name="frmSuma" method="post" action="receta">
	<div class="alert alert-info">		
		<a href="receta?FechaIni=<%=fechaIni%>&FechaFin=<%=fechaFin%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	</form>
<% 	if (recetaId.equals("0")){%>	
	<div class="alert alert-info"><h4>Lista de Recetas del <%=fechaIni%> al <%=fechaFin%></h4></div>
	<table id="table" class="table table-sm table-bordered">
		<thead class="table-info">	 
		<tr>
			<th width="3%">#</th>
			<th width="5%">Fecha</th>
			<th width="5%">Tipo</th>
			<th width="50%">Receta</th>
			<th width="5%">Rendimiento</th>
			<th width="30%">&nbsp;</th>
		</tr>
		</thead>
		<tbody>
<% 
		row = 0;	
		for (SaumComida comida : lisComidas){	
			row++;
			
			String tipo = comida.getComida();
			if (tipo.equals("1")) tipo = "Desayuno";
			if (tipo.equals("2")) tipo = "Comida";
			if (tipo.equals("3")) tipo = "Cena";
			
			String nombreReceta = "-";
			if (mapaRecetas.containsKey(comida.getRecetaId())){
				nombreReceta = mapaRecetas.get(comida.getRecetaId()).getNombre();
			}
%>		
		<tr>
			<td><%=row%></td>
			<td><%=comida.getFecha()%></td>
			<td><%=tipo%></td>
			<td><%=nombreReceta%></td>
			<td><%=comida.getRendimiento()%></td>
			<td>&nbsp;</td>
		</tr>
<%		} %>		
		</tbody>
	</table>
	<br>
<%	} %>	
	<div class="alert alert-info"><h4>Ingredientes requeridos</h4></div>
	<table id="tableIngrediente" class="table table-condensed">
		<thead>
		<tr>
			<th width="3%">#</th>
			<th width="30%">Ingrediente</th>
			<th width="10%">Cantidad</th>
			<th width="50%">&nbsp;</th>
		</tr>
		</thead>
		<tbody>
<% 
	row = 0;
	String llave 	= "0";
	String valor 	= "0";
	float cantidad 	= 0;
	for (java.util.Map.Entry<String, String> entry : mapaIngrediente.entrySet()) {
		
		llave 	= entry.getKey(); 
		valor 	= entry.getValue();
		
		if (valor.contains("g")){
			cantidad = Float.valueOf(valor.replace("g", ""));
			// Convertir a kilogramos
			if (cantidad >= 1000){
				cantidad = cantidad / 1000;
				valor = formato.format(cantidad)+" kg";
			}else{
				// Dejar en gramos
				valor = formato.format(cantidad)+" g";
			}
		}else if (valor.contains("ml")){
			cantidad = Float.valueOf(valor.replace("ml", ""));
			// Convertir a litros
			if (cantidad >= 1000){
				cantidad = cantidad / 1000;
				valor = formato.format(cantidad)+" l";
			}else{
				// Dejar en mililitros
				valor = formato.format(cantidad)+" ml";
			}
		}else{
			cantidad = Float.valueOf(valor.replace("pza", ""));
			valor = formato.format(cantidad)+" pza";
		}
		
		row++;
		
		String nombreMateria = "-";
		if (mapaMateria.containsKey(llave)){
			nombreMateria = mapaMateria.get(llave).getNombre();
		}
%>		
		<tr>
			<td><%=row%></td>
			<td><%=nombreMateria%></td>
			<td><b><%=valor%></b></td>
			<td>&nbsp;</td>
		</tr>
<%	} %>		
		</tbody>
	</table>
</div>		
</body>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script src="../../js/tablesorter/jquery.tablesorter.js"></script>
<script type="text/javascript">
	jQuery('#Fecha').datepicker();	
	jQuery('#tableReceta').tablesorter();
	jQuery('#tableIngrediente').tablesorter();
	
	function Refrescar(){
		document.frmSuma.submit();		
	}
</script>
</html>