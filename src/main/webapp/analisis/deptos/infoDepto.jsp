<%@page import="aca.est.spring.EstCcosto"%>
<%@page import="aca.est.spring.EstMaestro"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="AlumPlanU" scope="page" class="aca.alumno.PlanUtil"/>

<%
	java.text.DecimalFormat getFormato = new java.text.DecimalFormat("##0.0000;-##0.0000");
	java.text.DecimalFormat getFormato2 = new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	String departamento 				= (String)request.getAttribute("nombreDepto");
		
	List<String> listMaestrosDepto 		= (List<String>) request.getAttribute("listMaestrosDepto");
	
	HashMap<String,String> mapaMaestros 		= (HashMap<String,String>) request.getAttribute("mapaMaestros");
	HashMap<String,String> mapaDeptos 			= (HashMap<String,String>) request.getAttribute("mapaDeptos");
	HashMap<String,String> mapaImporte 			= (HashMap<String,String>) request.getAttribute("mapaImporte");
	HashMap<String,EstMaestro> mapaEstMaestro	= (HashMap<String,EstMaestro>) request.getAttribute("mapaEstMaestro");
	HashMap<String,String> mapaPorMaestro		= (HashMap<String,String>) request.getAttribute("mapaPorMaestro");
%>
<div class="container-fluid">
	<h2>Empleados departamento <small class="text-muted fs-4">(<%=departamento%>)</small></h2> 
	<div class="alert alert-info">
		<a href="deptos" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
	</div>
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
		<th width="5%" class="center">#</th>
		<th width="5%" class="left">Nomina</th>
		<th width="40%" class="left">Nombre Empleado</th>
		<th width="5%" class="left">Tipo</th>
		<th width="5%" class="right">Porcentaje</th>
		<th width="5%" class="right">Importe</th>		
	</tr>
	</thead>
<%		
	String alert = "class='alert alert-success'";
	int row = 0;
	double totalTotal = 0;
	
	for(String maestro : listMaestrosDepto){
		row++;
		
		String nombreMaestro = "-";
		if (mapaMaestros.containsKey(maestro)){
			nombreMaestro = mapaMaestros.get(maestro);
		}	
		
		String importe = "-";
		if (mapaImporte.containsKey(maestro)){
			importe = mapaImporte.get(maestro);
		}
		
		totalTotal = totalTotal+Double.parseDouble(importe);	
		
		String tipo = "-";
		if (mapaEstMaestro.containsKey(maestro)){
			tipo = mapaEstMaestro.get(maestro).getTipo();
			if (tipo.equals("Z")) tipo = "Visitante";
			if (tipo.equals("E")) tipo = "Externo";
			if (tipo.equals("C")) tipo = "HLC";
			if (tipo.equals("V")) tipo = "Virtual";
			if (tipo.equals("H")) tipo = "Horas/Contrato";
			if (tipo.equals("I")) tipo = "Intercambio";
			if (tipo.equals("A")) tipo = "UM Pres.";
			if (tipo.equals("X")) tipo = "No clasificado";
			if (tipo.equals("N")) tipo = "Nomina";
			if (tipo.equals("B")) tipo = "Visitante";
			if (tipo.equals("J")) tipo = "Jubilado";
			if (tipo.equals("G")) tipo = "Gratuito";
		}		
		
		String porcentaje = "0";
		if (mapaPorMaestro.containsKey(maestro)){
			porcentaje = mapaPorMaestro.get(maestro);
		}
%>
	<tr>
		<td class="center"><%=row%></td>		
		<td class="left"><%=maestro%></td>		
		<td class="left"><%=nombreMaestro%></td>
		<td class="left"><%=tipo%></td>
		<td class="right"><%=getFormato2.format(Double.valueOf(porcentaje))%></td>
		<td class="right"><%=getFormato2.format(Double.parseDouble(importe))%></td>				
		
	</tr>	
<%	
	}
%>
	<tr>
		<th class="right" colspan="5"><strong>Total</strong></th>		
		<th class="right"><%=getFormato2.format(totalTotal)%></th>		
	</tr>	
	</table>
</div>