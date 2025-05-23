<%@page import="aca.util.Fecha"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaGrupoEvaluacion"%>
<%@page import="aca.carga.spring.CargaGrupoActividad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%-- <jsp:include page="../menu.jsp" /> --%>
<% 
	java.text.DecimalFormat getFormato = new java.text.DecimalFormat("###,##0.00;(##0.00)");
	Date date = new Date();
	Date fechaHoy = aca.util.Fecha.getFechaHoy();
	
	String codigoPersonal		= (String) session.getAttribute("codigoEmpleado");
	
	String cursoCargaId 		= request.getParameter("CursoCargaId")==null?"":request.getParameter("CursoCargaId");	
	
	int numAlumnos	 								= (int) request.getAttribute("numAlumnos");
	String avanceMateria	 						= (String) request.getAttribute("avanceMateria");
	String materiaNombre 							= (String) request.getAttribute("materiaNombre");
	String maestroNombre 							= (String) request.getAttribute("maestroNombre");

	List<CargaGrupoEvaluacion> lisEvaluaciones 		= (List<CargaGrupoEvaluacion>) request.getAttribute("lisEvaluaciones");
	List<CargaGrupoActividad> lisActividades 		= (List<CargaGrupoActividad>) request.getAttribute("lisActividades");
	
	HashMap<String,String> mapaNotasEvaluaciones 			= (HashMap<String,String>) request.getAttribute("mapaNotasEvaluaciones");
	HashMap<String,String> mapaNotasActividadesPorEval 		= (HashMap<String,String>) request.getAttribute("mapaNotasActividadesPorEval");
	HashMap<String,String> mapaAvanceEvaluaciones 			= (HashMap<String,String>) request.getAttribute("mapaAvanceEvaluaciones");
	HashMap<String,String> mapaNotasActividades 			= (HashMap<String,String>) request.getAttribute("mapaNotasActividades");
	HashMap<String,String> mapaAvanceActividades 			= (HashMap<String,String>) request.getAttribute("mapaAvanceActividades");
	HashMap<String,String> mapaActividadesPorEvaluacion		= (HashMap<String,String>) request.getAttribute("mapaActividadesPorEvaluacion");
	
	String colorPortal 		= (String)session.getAttribute("colorPortal");
	if(colorPortal==null) colorPortal="";	
%>

<html>
<head>
	<link href="css/portalAlumno.css" rel="STYLESHEET" type="text/css">
	<style> .P{border:solid 1px white;} </style>
</head>
<body>
<div class="container-fluid">
	<h2><spring:message code="portal.maestro.avance.Evaluaciones"/> <small class="text muted h6">(<spring:message code="portal.maestro.avance.Acta"/>: <b><%=cursoCargaId%></b> - <spring:message code="portal.maestro.avance.Materia"/>: <b><%=materiaNombre%></b> - <spring:message code="portal.maestro.avance.Profesor"/>: <b><%=maestroNombre%></b> )</small></h2>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="cursos"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<h6><spring:message code="portal.maestro.avance.NumeroAlumnos"/>: <span class='badge badge bg-dark'><%=numAlumnos%></span> &nbsp; &nbsp; <spring:message code="portal.maestro.avance.PuntosEvaluados"/>: <span class='badge badge bg-dark'><%=avanceMateria%></span></h6>
	</div>	
	<table class="table table-sm table-fontsmall table-bordered table-noover">
	<tr>
		<th width="3%"><h4>#</h4></th>
		<th width="15%"><h5><spring:message code="aca.Fecha"/></h5></th>
		<th width="35%"><h5><spring:message code="aca.Nombre"/></h5></th>		
		<th width="10%" class="right"><h5>#<spring:message code="portal.maestro.avance.Eval"/></h5></th>
		<th width="10%" class="right"><h5>#<spring:message code="portal.maestro.avance.Notas"/></h5></th>
		<th width="10%" class="right"><h5><spring:message code="portal.maestro.avance.Valor"/>(%)</h5></th>
		<th width="10%" class="right"><h5><spring:message code="portal.maestro.avance.AvancePts"/></h5></th>
		<th width="10%" class="right"><h5><spring:message code="portal.maestro.avance.Avance"/>(%)</h5></th>
	</tr>								
<%	int row = 0;
	for (CargaGrupoEvaluacion evaluacion : lisEvaluaciones){
		row++;	
		
		String notasEvaluaciones = "0";
		if (mapaNotasEvaluaciones.containsKey(evaluacion.getEvaluacionId())){
			notasEvaluaciones = mapaNotasEvaluaciones.get(evaluacion.getEvaluacionId());
		}
		
		String avanceEvaluaciones = "0";
		if (mapaAvanceEvaluaciones.containsKey(evaluacion.getEvaluacionId())){
			avanceEvaluaciones = mapaAvanceEvaluaciones.get(evaluacion.getEvaluacionId());
		}
		
		String numActividades = "0";
		if (mapaActividadesPorEvaluacion.containsKey(evaluacion.getEvaluacionId())){
			numActividades = mapaActividadesPorEvaluacion.get(evaluacion.getEvaluacionId());
		}		
		
		if (!numActividades.equals("0")){
			notasEvaluaciones = "0";
			if ( mapaNotasActividadesPorEval.containsKey(evaluacion.getEvaluacionId()) ){
				notasEvaluaciones = mapaNotasActividadesPorEval.get(evaluacion.getEvaluacionId());
			}			
		}
		
		String numEvaluaciones = "-";
		if(numActividades.equals("0")){
			numEvaluaciones = Integer.toString(numAlumnos);
		}else{
			numEvaluaciones = Integer.toString(Integer.parseInt(numActividades)*numAlumnos);
		}
		
		String avancePorc = "0";
		if(!avanceEvaluaciones.equals("0")){
			avancePorc = String.valueOf((Double.parseDouble(avanceEvaluaciones)*100)/Double.parseDouble(evaluacion.getValor()));
		}
		
		String badge = "badge bg-dark";
		if( Float.valueOf(notasEvaluaciones) > 0 && Float.valueOf(notasEvaluaciones) >= Float.valueOf(numEvaluaciones)/2 ){
			badge = "badge bg-info";
		}else if ( aca.util.Fecha.getDiasEntreFechas(evaluacion.getFecha(), aca.util.Fecha.getHoy()) >= 1 ){
			badge = "badge bg-warning";
		}
%>	
		<tr class="alert alert-success">
			<td><h5><span class="<%=badge%>"><%=row%></span></h5></td>
			<td><h5><%=evaluacion.getFecha()%></h5></td>
			<td><h5><%=evaluacion.getEstrategiaId()%> - <%=evaluacion.getNombreEvaluacion()%></h5></td>			
			<td class="right"><h5><%=numEvaluaciones%></h5></td>
	    	<td class="right"><h5><%=notasEvaluaciones%></h5></td>
	    	<td class="right"><h5><%=evaluacion.getValor()%><%=evaluacion.getTipo()%></h5></td>
	    	<td class="right"><h5><span class='badge'><%=getFormato.format(Double.parseDouble(avanceEvaluaciones))%></span></h5></td>
	    	<td class="right"><h5><%=getFormato.format(Double.parseDouble(avancePorc))%>%</h5></td>  
		</tr>
<%		
		for (CargaGrupoActividad act : lisActividades){
		  	if (act.getEvaluacionId().equals(evaluacion.getEvaluacionId())){
		  		
		  		String notasActividades = "0";
				if (mapaNotasActividades.containsKey(act.getActividadId())){
					notasActividades = mapaNotasActividades.get(act.getActividadId());
				}
				
				String avanceActividades = "0";
				if (mapaAvanceActividades.containsKey(act.getActividadId())){
					avanceActividades = mapaAvanceActividades.get(act.getActividadId());
				}
				
				String avanceActividadesPorc = "0";
				if(!avanceActividades.equals("0")){
					avanceActividadesPorc = String.valueOf((Double.parseDouble(avanceActividades)*100)/Double.parseDouble(act.getValor()));
				}
%>
		<tr class="tr2">
<%     	Date fechaAct = aca.util.Fecha.getStrToDateddMMyyyy(act.getFecha());
		int noEval = numAlumnos/2;
		int noNotas = Integer.parseInt(notasActividades);
		if(fechaHoy.after(fechaAct) && noNotas < noEval){%>
			<td class="right"><span class='badge bg-warning'><i class="fas fa-bullseye"></i></span></td>
<% 		}else if ( noNotas > 0 && noNotas >= noEval){%>
			<td class="right"><span class='badge bg-info'> <i class="fas fa-bullseye"></i></span></td>
<% 		}else{ %>
			<td class="right"><i class="fas fa-bullseye"></i></td>
<%		}%>
	    	<td><em><%= act.getFecha()%><em></td>
	    	<td>
	      		<em>&nbsp;<%= act.getNombre() %><em>
	    	</td>    	
	    	<td class="right"><em><%=numAlumnos%><em></td>	    	
	    	<td class="right"><em><%=notasActividades%><em></td>
	    	<td class="right"><em><%= act.getValor() %><em></td>
	    	<td class="right"><em><%=getFormato.format(Double.parseDouble(avanceActividades))%><em></td>	
	    	<td class="right"><em><%=getFormato.format(Double.parseDouble(avanceActividadesPorc))%><em></td>    	
		</tr>
<%				
			}		  
		}	// fin de for de actividades
	}		
%>
</table>
<script>
	$('.nav-tabs').find('.materias').addClass('active');
</script>
</div>