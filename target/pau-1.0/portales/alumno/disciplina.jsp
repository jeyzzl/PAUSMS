<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.disciplina.spring.CondAlumno"%>
<%@page import="aca.disciplina.spring.CondReporte"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%> 
<%@page import="aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ include file= "menu.jsp" %>
<%
	String matricula 			= (String) session.getAttribute("codigoAlumno");
	String nombreAlumno 		= (String) request.getAttribute("nombreAlumno");
	String planId				= (String) request.getAttribute("planId");
	String carreraNombre		= (String) request.getAttribute("carreraNombre");	
	
	List<CondAlumno> lisAlumno 					= (List<CondAlumno>)request.getAttribute("lisAlumno");
	// Lista de conducta
	List<CondAlumno> lisConducta 				= (List<CondAlumno>) request.getAttribute("lisConducta");	
	// Lista de planes activos del alumno
	List<CargaAlumno> lisPlanes 				= (List<CargaAlumno>) request.getAttribute("lisPlanes");	
	// Mapa de Reportes
	HashMap<String,CondReporte> mapaReportes 	= (HashMap<String, CondReporte>) request.getAttribute("mapaReportes");	
	// Mapa de Lugares
	HashMap<String, String> mapaLugares 		= (HashMap<String, String>) request.getAttribute("mapaLugar");	
	// Mapa de Jueces
	HashMap<String, String> mapaJueces 			= (HashMap<String, String>) request.getAttribute("mapaJuez");	
	HashMap<String,MapaPlan> mapaPlanes 		= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	
	int tU=0,tE=0;
	String colorPortal = (String)session.getAttribute("colorPortal");
	if(colorPortal==null)colorPortal="";
%>
<html>
<head></head>
<body onload='muestraPagina();'>
<script>
	parent.tabs.document.body.style.backgroundColor=parent.contenidoP.document.bgColor;
</script>	
<div class="container-fluid">
	<h3>
		Behavioral Record:<small class="text-muted fs-6">( <%=matricula%> - <%=nombreAlumno%> )</small>
	</h3>
	<div class="alert alert-info">	
		<b><spring:message code="portal.alumno.disciplica.PlanPrincipal"/>; <%=planId%> <%=carreraNombre%></b>
	</div>
	<table class="table table-sm" style="width:100%">
		<tr valign="top">
			<th width="3%"><h4><spring:message code="aca.Folio"/></h4></th>
			<th width="21%"><h4>Report</h4></th>
			<th width="21%"><h4>Location</h4></th>
			<th width="20%"><h4>Reported by</h4></th>
			<th width="5%"><h4><spring:message code="aca.Fecha"/></h4></th>
			<th width="5%" class="right"><h4>Fault</h4></th>
			<th width="5%" class="right"><h4>Commendation</h4></th>
		</tr>
<%	
	for (int i=0; i<lisAlumno.size(); i++){
		CondAlumno con = lisAlumno.get(i);

		
		String tipo 			= "0";
		String nombreReporte	= "-";
		if (mapaReportes.containsKey(con.getIdReporte())){
			tipo 			= mapaReportes.get(con.getIdReporte()).getTipo();
			nombreReporte 	= mapaReportes.get(con.getIdReporte()).getNombre();
		}
		
		String nombreLugar = "-";
		if (mapaLugares.containsKey(con.getIdLugar()) ){
			nombreLugar = mapaLugares.get(con.getIdLugar());
		}		
		
		String nombreJuez = "-";
		if (mapaJueces.containsKey(con.getIdJuez()) ){
			nombreJuez = mapaJueces.get(con.getIdJuez());
		}		
%>		
		<tr valign="top">
			<td width="3%"  align="center"><h6><%=con.getFolio()%></h6></td>
			<td width="5%" ><h6><%=nombreReporte%></h6></td>
			<td width="5%" ><h6><%=nombreLugar%></h6></td>
			<td width="5%" ><h6><%=nombreJuez%></h6></td>			
			<td width="5%" align="center"><h6><%=con.getFecha()%></h6></td>
			<td width="5%"  align="right"><h6><%if (tipo.equals("D")){tU+=Integer.parseInt(con.getCantidad()); out.print(con.getCantidad());}%></h6></td>
			<td width="5%"  align="right"><h6><%if (tipo.equals("C")){tE+=Integer.parseInt(con.getCantidad()); out.print(con.getCantidad());}%></h6></td>
		</tr>
<%	} %>		
		<tr valign="top"> 
			<td width="3%" >&nbsp;</td>
			<td width="21%" >&nbsp;</td>
			<td width="21%">&nbsp;</td>
			<td width="20%" align="right"><h6><b><spring:message code="aca.Subtotal"/></b></h6></td>
			<td width="5%">&nbsp;</td>
			<td width="5%" align="right"><h6><b><%=tU%></b></h6></td>
			<td width="5%" align="right"><h6><b><%=tE%></b></h6></td>
		</tr>
		<tr valign="top"> 
			<td width="3%">&nbsp;</td>
			<td width="21%">&nbsp;</td>
			<td width="21%">&nbsp;</td>
			<td width="20%" class="right"><h6><b><spring:message code="aca.Total"/></b></h6></td>
			<td width="5%">&nbsp;</td>
			<td width="5%" align="right"><h6><b><%=tU-tE%></b></h6></td>
			<td width="5%">&nbsp;</td>
		</tr>	
	</table>
</div>
</body>	
<script>
	$('.nav-tabs').find('.disciplina').addClass('active');
</script>