<%@page import="aca.est.spring.EstMaestro"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<head>	
</head>
<%
	java.text.DecimalFormat getFormato = new java.text.DecimalFormat("##0.0000;-##0.0000");
	java.text.DecimalFormat getFormato2 = new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	List<EstMaestro> lisMaestros 			= (List<EstMaestro>) request.getAttribute("lisMaestros");	
	List<String> lisDepartamentos 			= (List<String>) request.getAttribute("lisDepartamentos");
	HashMap<String,String> mapaMaestros		= (HashMap<String,String>) request.getAttribute("mapaMaestros");
	HashMap<String,String> mapaMaterias		= (HashMap<String,String>) request.getAttribute("mapaMaterias");
	HashMap<String,String> mapaAlumnos		= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapaRango1		= (HashMap<String,String>) request.getAttribute("mapaRango1");
	HashMap<String,String> mapaRango2		= (HashMap<String,String>) request.getAttribute("mapaRango2");	
	HashMap<String,String> mapaPorcentaje	= (HashMap<String,String>) request.getAttribute("mapaPorcentaje");
	HashMap<String,String> mapaIniciales	= (HashMap<String,String>) request.getAttribute("mapaIniciales");
%>
<body>
<div class="container-fluid">
	<h2>Maestros/Departamentos</h2>
	<hr>
	<table class="table table-bordered">
	<thead class="table-info">
	<tr class="header">				
		<th width="1%" class="center">#</th>
		<th width="5%" class="left"><spring:message code="aca.Nomina"/></th>
		<th width="30%" class="left"><spring:message code="aca.Maestro"/></th>
		<th width="3%" class="right">Hrs</th>
		<th width="3%" class="right">Mat.</th>
		<th width="3%" class="right">Alum.</th>
		<th width="3%" class="right">Mat./1</th>
		<th width="3%" class="right">Mat./2-9</th>
<%	
	for(String depto : lisDepartamentos){
		String inicial = depto;
		if (mapaIniciales.containsKey(depto)){
			inicial = mapaIniciales.get(depto);
		}
%>
		<th class="center"><span title="<%=depto%>"><%=inicial%></span></th>
<%	} %>
		<th width="3%" class="right">Total</th>
	</tr>
	</thead>
	<tbody>
<%
	int row = 0;
	for (EstMaestro maestro : lisMaestros){
		row++;
		String nombreMaestro = "-";
		if (mapaMaestros.containsKey(maestro.getCodigoPersonal())){
			nombreMaestro = mapaMaestros.get(maestro.getCodigoPersonal()); 
		}
		
		String materias = "0";
		if (mapaMaterias.containsKey(maestro.getCodigoPersonal())){
			materias = mapaMaterias.get(maestro.getCodigoPersonal()); 
		}
		
		String alumnos = "0";
		if (mapaAlumnos.containsKey(maestro.getCodigoPersonal())){
			alumnos = mapaAlumnos.get(maestro.getCodigoPersonal()); 
		}
		
		String rango1 = "0";
		if (mapaRango1.containsKey(maestro.getCodigoPersonal())){
			rango1 = mapaRango1.get(maestro.getCodigoPersonal()); 
		}
		
		String rango2 = "0";
		if (mapaRango2.containsKey(maestro.getCodigoPersonal())){
			rango2 = mapaRango2.get(maestro.getCodigoPersonal()); 
		}
		
%>	
	<tr>
		<td><%=row%></td>
		<td><%=maestro.getCodigoPersonal()%></td>
		<td><%=nombreMaestro%></td>
		<td class="right"><%=maestro.getHoras()%></td>
		<td class="right"><%=materias%></td>
		<td class="right"><%=alumnos%></td>
		<td class="right"><%=rango1%></td>
		<td class="right"><%=rango2%></td>
<%		
		float total = 0;
		for(String depto : lisDepartamentos){
			String tot = "0";
			if (mapaPorcentaje.containsKey(maestro.getCodigoPersonal()+depto)){
				tot = mapaPorcentaje.get(maestro.getCodigoPersonal()+depto);
			}
			total += Float.valueOf(tot);
%>
		<td class="center"><%=getFormato.format(Float.valueOf(tot))%></td>
<%		} %>
		<td class="right"><%=getFormato2.format(total)%></td>
	</tr>	
<%	}%>	
	</table>	
</div>
</body>