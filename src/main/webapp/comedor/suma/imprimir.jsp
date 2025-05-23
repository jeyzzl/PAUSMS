<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.saum.spring.SaumComida"%>
<%@page import="aca.saum.spring.SaumReceta"%>
<%@page import="aca.saum.spring.SaumEtapa"%>
<%@page import="aca.saum.spring.SaumIngrediente"%>
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

	String fechaIni				=  request.getParameter("FechaIni")==null?aca.util.Fecha.getHoy():request.getParameter("FechaIni");
	String fechaFin				=  request.getParameter("FechaFin")==null?aca.util.Fecha.getHoy():request.getParameter("FechaFin");
	
	String rendimientoComida	=  request.getParameter("Rendimiento")==null?"0":request.getParameter("Rendimiento");
	String tipo					=  request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		
	SaumReceta receta			= (SaumReceta)request.getAttribute("receta");
	ArrayList<SaumEtapa> lisEtapas 				= (ArrayList<SaumEtapa>)request.getAttribute("lisEtapas");
	ArrayList<SaumIngrediente> lisIngredientes	= (ArrayList<SaumIngrediente>)request.getAttribute("lisIngredientes");
	HashMap<String,String> mapaIngrediente		= (HashMap<String,String>)request.getAttribute("mapaIngrediente");
	HashMap<String,SaumMateria> mapaMateria		= (HashMap<String,SaumMateria>)request.getAttribute("mapaMateria");
%>
<body>
<div class="container-fluid">
	<h2><%=receta.getNombre()%><small class="text-muted fs-4">( Rendimiento: <%=rendimientoComida%>)</small></h2>
	<form id="frmSuma" name="frmSuma" method="post" action="receta">	
	</form>	
	<div class="alert alert-info">
		<h4>
			<a href="receta?FechaIni=<%=fechaIni%>&FechaFin=<%=fechaFin%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
			<%=tipo%>
		</h4>
	</div>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>			
			<th width="15%">Etapa</th>
			<th width="50%">Procedimiento</th>
			<th width="35%">Ingredientes</th>
		</tr>
		</thead>
		<tbody>
<%
	int row = 0;
	for (SaumEtapa etapa : lisEtapas){
		row++;	
%>	
		<tr>			
			<td><%=etapa.getNombre()%></td>
			<td><%=etapa.getProcedimiento()%></td>
			<td>
<%
		for (SaumIngrediente ingrediente : lisIngredientes){
			
			if (ingrediente.getEtapaId().equals(etapa.getId())){
				
				String nombreMateria = "-";
				if (mapaMateria.containsKey(ingrediente.getMateriaId())){
					nombreMateria = mapaMateria.get(ingrediente.getMateriaId()).getNombre();
				}
				
				String valor = "*";
				if (mapaIngrediente.containsKey(etapa.getId()+ingrediente.getMateriaId())){
					float cantidad 	= 0;
					valor 	= mapaIngrediente.get(etapa.getId()+ingrediente.getMateriaId());
					
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
				}
				
				out.print(nombreMateria+" <span class='badge'>"+valor+"</span><br>");
			}	
		}

%>
			</td>
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