<%@ page import= "aca.plan.MapaPlan"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="planUtil"  class="aca.plan.PlanUtil" scope="page"/>
<jsp:useBean id="carreraUtil"  class="aca.catalogo.CarreraUtil" scope="page"/>
<jsp:useBean id="FesCcobroU"  class="aca.financiero.FesCcobroUtil" scope="page"/>

<%
	java.text.DecimalFormat getFormato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String facultad 	= request.getParameter("facultad");
	String cargaId      = request.getParameter("CargaId")==null ? "000000" : request.getParameter("CargaId");
	String planId		= request.getParameter("planId")==null ? "0" : request.getParameter("planId");
	
	String creditosPlan = aca.plan.MapaAvanceUtil.getCreditosPlan(conEnoc, planId);
	 
	ArrayList<String> lisCargas 	= FesCcobroU.getCargaIdIngreso(conEnoc, facultad, " ORDER BY 1");
	
	// Coloca por default la primera cargaId
	if (cargaId.equals("000000")&&lisCargas.size()>0) cargaId= lisCargas.get(0);
	
	ArrayList<aca.financiero.FesCcobro> lisAlumnos	= FesCcobroU.getAlumnosCargaPlan(conEnoc, cargaId, facultad, planId, " ORDER BY NOMBRE");
	
	/* HashMap de los nombres de las carreras */
	java.util.HashMap<String,String> mapPromedio = FesCcobroU.getPromedioAlumno(conEnoc, cargaId, facultad);
	
	/* HashMap de la última fecha de inscripción de los alumnos */
	java.util.HashMap<String,String> mapUltimaFecha	= aca.alumno.PlanUtil.mapAlumUltimaInsc(conEnoc, planId);
	
	/* HashMap de los créditos de los alumnos en el plan */
	java.util.HashMap<String,String> mapCreditos 	= aca.alumno.PlanUtil.mapAlumPlanCreditos(conEnoc, planId);
	
	String sBgcolor		= "";
	int cont			= 0;
%>
<div class="container-fluid">
<h1>Alumnos de primer ingreso en la carga <%=cargaId%> del plan <%=planId%></h1>
<div class="alert alert-info">
	<a class="btn btn-primary" href="cohorte?facultad=<%=facultad%>&planId=<%=planId%>&CargaId=<%=cargaId%>"><i class="fas fa-arrow-left"></i></a>
</div>
<table class="table table-bordered">
	<thead class="table-info">
	<tr>
		<th width="5%"><h4><spring:message code="aca.Numero"/></h4></th>
		<th width="10%"><h4>Matrícula.</h4></th>
		<th width="30%"><h4><spring:message code="aca.Nombre"/></h4></th>
		<th width="7%" class='center'><h4>Primer Insc.</h4></th>
		<th width="7%" class='center'><h4>Ultima Insc.</h4></th>
		<th width="7%" class='center'><h4>Cred.Plan</h4></th>
		<th width="7%" class='center'><h4>Cred.Alum.</h4></th>
		<th width="7%" class='center'><h4><spring:message code="aca.Avance"/></h4></th>
		<th width="7%" class='center'><h4><spring:message code="aca.Promedio"/></h4></th>
	</tr>	
	</thead>	
<%
	for (aca.financiero.FesCcobro alumnos : lisAlumnos){
		cont++;		
		
		// Buscar la ultima fecha de inscripción de los alumnos
		String fecha = "";
		if (mapUltimaFecha.containsKey(alumnos.getMatricula())) fecha = mapUltimaFecha.get(alumnos.getMatricula());
		
		// Busca el promedio de los alumnos en el plan de estudios
		String promedio = "0";
		if (mapPromedio.containsKey(alumnos.getMatricula()+","+alumnos.getPlanId()))  promedio 	= mapPromedio.get(alumnos.getMatricula()+","+alumnos.getPlanId());
		
		// Buscar los creditos aprobados del alumno en el plan de estudios
		String creditosAlumno = "0";
		if (mapCreditos.containsKey(alumnos.getMatricula())) creditosAlumno = mapCreditos.get(alumnos.getMatricula());
		
		double avance = (Double.valueOf(creditosAlumno)*100)/Double.valueOf(creditosPlan);
%>  
		<tr class="tr2" <%=sBgcolor%>>
			<td class="center"><%=cont%></td>
			<td class="center"><%=alumnos.getMatricula()%></td>
			<td><%=alumnos.getNombre()%></td>
			<td class="center"><%=alumnos.getFecha() %></td>
			<td class="center"><%=fecha%></td>
			<td class="right"><%=creditosPlan%></td>
			<td class="right"><%=creditosAlumno%></td>
			<td class="right"><%=getFormato.format(avance)%></td>
			<td class="right"><strong><%=getFormato.format(Double.parseDouble(promedio))%></strong></td>
		</tr>
<%	} %>
</table>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>