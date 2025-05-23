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
	<h2>Evaluaciones <small class="text-muted fs-5">(Acta: <b><%=cursoCargaId%></b> - Materia: <b><%=materiaNombre%></b> - Profesor: <b><%=maestroNombre%></b> )</small></h2>
	<h4>Número de Alumnos: <span class='badge badge bg-dark'><%=numAlumnos%></span> &nbsp; &nbsp; Puntos evaluados: <span class='badge badge bg-dark'><%=avanceMateria%></span></h4>	
	<br>
	<table class="table table-sm table-fontsmall table-bordered table-noover">
	<thead class="table-info">
	<tr>
		<th width="3%"><h4>#</h4></th>
		<th width="15%"><h4><spring:message code="aca.Fecha"/></h4></th>
		<th width="35%"><h4><spring:message code="aca.Nombre"/></h4></th>		
		<th width="10%" class="right"><h4>#Eval.</h4></th>
		<th width="10%" class="right"><h4>#Notas</h4></th>
		<th width="10%" class="right"><h4>Valor(%)</h4></th>
		<th width="10%" class="right"><h4>Avance/Pts</h4></th>
		<th width="10%" class="right"><h4>Avance(%)</h4></th>
	</tr>	
	</thead>							
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
			<td><h4><span class="<%=badge%>"><%=row%></span></h4></td>
			<td><h4><%=evaluacion.getFecha()%></h4></td>
			<td><h4><%=evaluacion.getEstrategiaId()%> - <%=evaluacion.getNombreEvaluacion()%></h4></td>			
			<td class="right"><h4><%=numEvaluaciones%></h4></td>
	    	<td class="right"><h4><%=notasEvaluaciones%></h4></td>
	    	<td class="right"><h4><%=evaluacion.getValor()%><%=evaluacion.getTipo()%></h4></td>
	    	<td class="right"><h4><span class='badge'><%=getFormato.format(Double.parseDouble(avanceEvaluaciones))%></span></h4></td>
	    	<td class="right"><h4><%=getFormato.format(Double.parseDouble(avancePorc))%>%</h4></td>  
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