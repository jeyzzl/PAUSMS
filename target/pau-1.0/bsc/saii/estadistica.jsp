<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CatPeriodoU" scope="page" class="aca.catalogo.CatPeriodoUtil"/>
<jsp:useBean id="CargaU" scope="page" class="aca.carga.CargaUtil"/>
<jsp:useBean id="MapaPlan" scope="page" class="aca.plan.MapaPlan"/>
<jsp:useBean id="MapaPlanU" scope="page" class="aca.plan.PlanUtil"/>
<jsp:useBean id="PlanU" scope="page" class="aca.plan.PlanUtil"/>
<jsp:useBean id="EstadisticaU" scope="page" class="aca.vista.EstadisticaUtil"/>
<jsp:useBean id="CargaAcademica" scope="page" class="aca.vista.CargaAcademica"/>

<script></script>
<%
	String periodoSesion 		= (String)session.getAttribute("periodo");
	String cargaSesion 			= (String)session.getAttribute("cargaId");
	String periodoId			= request.getParameter("PeriodoId")==null?periodoSesion:request.getParameter("PeriodoId");
	String cargaId				= request.getParameter("CargaId")==null?cargaSesion:request.getParameter("CargaId");
	String carreraId			= request.getParameter("carreraId")==null?"":request.getParameter("carreraId");
	String planId				= request.getParameter("planId")==null?"":request.getParameter("planId");

	// actualizar el valor del periodo en sesion
	if (periodoId!=null){
		session.setAttribute("periodo",periodoId);
	} 
	
	// actualizar el valor del periodo en sesion
	if (cargaId!=null){
		session.setAttribute("cargaId",cargaId);
	}
	
	MapaPlan = MapaPlanU.mapeaRegId(conEnoc, planId);
	
	String rvoe					= MapaPlan.getRvoe();
	CargaAcademica.mapeaRegId(conEnoc, cargaId, MapaPlan.getPlanId());	
%>
<body>
<div class="container-fluid">
	<h1>Estadística SAii</h1>
	<form name="forma" action="reporte" method="post">
	<div class="alert alert-info">
		<a href="plan_rvoe?cargaId=<%=cargaId%>&carreraId=<%=carreraId%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i><spring:message code='aca.Atras'/></a>
	</div>
	</form>
	<table class="table table-condensed">
	<tr>
		<td>Nivel</td>
		<td><%=aca.catalogo.CatNivelUtil.getNivelNombre(conEnoc, aca.plan.PlanUtil.getNivelId(conEnoc, planId)) %></td>
	</tr>	
	<tr>
		<td>Modalidad</td>
		<td></td>
	</tr>	
	<tr>
		<td>Tipo de  RVOE</td>
		<td></td>
	</tr>	
	<tr>
		<td>Entidaod o institución</td>
		<td></td>
	</tr>	
	<tr>
		<td>Numero de RVOE</td>
		<td><%=MapaPlan.getRvoe() %></td>
	</tr>	
	<tr>
		<td>Campus al que pertenece</td>
		<td></td>
	</tr>	
	<tr>
		<td>Localidad</td>
		<td></td>
	</tr>	
	<tr>
		<td>Duración del programa</td>
		<td></td>
	</tr>	
	<tr>
		<td>Tipo de enfoque</td>
		<td></td>
	</tr>	
	<tr>
		<td>Área de conocimiento </td>
		<td></td>
	</tr>	
<%
	String numMujeres = EstadisticaU.getNumGeneroPorPlan(conEnoc, "F", planId, cargaId);
	String numHombres = EstadisticaU.getNumGeneroPorPlan(conEnoc, "M", planId, cargaId);
%>
	<tr>
		<td>Numero de alumnos Inscritos</td>
		<td><%=Integer.parseInt(numMujeres)+Integer.parseInt(numHombres) %></td>
	</tr>	
	<tr>
		<td>Numero de alumnos Inscritos hombres</td>
		<td><%=numHombres %></td>
	</tr>	
	<tr>
		<td>Numero de alumnos Inscritos mujeres</td>
		<td><%=numMujeres %></td>
	</tr>	
	<tr>
		<td>Numero de alumnos de 19 años o menor</td>
		<td><%=EstadisticaU.getAlumBetween(conEnoc, cargaId, planId, "0", "19") %></td>
	</tr>	
	<tr>
		<td>Numero de alumnos de 20 a 24 años</td>
		<td><%=EstadisticaU.getAlumBetween(conEnoc, cargaId, planId, "20", "24") %></td>
	</tr>	
	<tr>
		<td>Numero de alumnos de 25 a 29 años</td>
		<td><%=EstadisticaU.getAlumBetween(conEnoc, cargaId, planId, "25", "29") %></td>
	</tr>	
	<tr>
		<td>Numero de alumnos de 30 años o mas</td>
		<td><%=EstadisticaU.getAlumBetween(conEnoc, cargaId, planId, "30", "100") %></td>
	</tr>	
	<tr>
		<td>Numero de alumnos extranjeros</td>
		<td><%=EstadisticaU.getNumExtranjeros(conEnoc, cargaId, planId) %></td>
	</tr>	
	
	</table>
</div>	
</body>
<%@ include file= "../../cierra_enoc.jsp" %>