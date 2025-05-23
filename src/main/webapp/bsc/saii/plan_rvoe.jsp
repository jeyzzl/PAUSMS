<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CatPeriodoU" scope="page" class="aca.catalogo.CatPeriodoUtil"/>
<jsp:useBean id="CargaU" scope="page" class="aca.carga.CargaUtil"/>
<jsp:useBean id="PlanU" scope="page" class="aca.plan.PlanUtil"/>
<jsp:useBean id="EstadisticaU" scope="page" class="aca.vista.EstadisticaUtil"/>


<script>
	
</script>

<%
	String periodoSesion 		= (String)session.getAttribute("periodo");
	String cargaSesion 			= (String)session.getAttribute("cargaId");
	String periodoId			= request.getParameter("PeriodoId")==null?periodoSesion:request.getParameter("PeriodoId");
	String carreraId			= request.getParameter("carreraId")==null?"":request.getParameter("carreraId");
	String cargaId				= request.getParameter("CargaId")==null?cargaSesion:request.getParameter("CargaId");
	

	
	// Lista de carreras
	ArrayList<aca.plan.MapaPlan> lisPlanes = PlanU.getLista(conEnoc, carreraId, "ORDER BY PLAN_ID");
	
	// Map de alumnos por Plan
	java.util.HashMap <String, String> mapAlumPlan = EstadisticaU.mapInscritosPlan(conEnoc, carreraId, cargaId);
%>
<body>
<div class="container-fluid">
	<h1>Estadística SAii <small>(<%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, carreraId) %>)</small></h1>
	<form name="forma" action="reporte" method="post">
	<div class="alert alert-info">
		<a href="reporte" class="btn btn-primary"><spring:message code='aca.Regresar'/></a>
	</div>
	</form>
	<table class="table table-condensed">	

	<tr>
		<th>#</th>
		<th><spring:message code="aca.Clave"/></th>
	  	<th><spring:message code="aca.Plan"/> Nombre</th>
	  	<th>RVOE</th>
	  	<th># Alumnos</th>
	  	
	
	</tr> 
	
	<%		
	int cont = 1;
	for(aca.plan.MapaPlan plan: lisPlanes){
		String alumnos 	= "-";
		
		if(mapAlumPlan.containsKey(plan.getPlanId())){
			alumnos = mapAlumPlan.get(plan.getPlanId());
		} 
%>			
	<tr>
		<td><%= cont++%></td>
		<td><%= plan.getPlanId() %></td>
		<td><%if (!alumnos.equals("-"))out.print("<a href=estadistica?carreraId="+carreraId+"&cargaId="+cargaId+"&planId="+plan.getPlanId()+">");%><%= plan.getNombrePlan() %><%if (!alumnos.equals("-"))out.print("</a>");%></td>
	  	<td align="center"><%=aca.plan.PlanUtil.getRvoe(conEnoc, plan.getPlanId())%></td>  	
	  	<td align="center"><%=alumnos%></td>  	
	</tr> 
<%		
	}
%>	
	
	</table>
</div>	
</body>
<%@ include file= "../../cierra_enoc.jsp" %>