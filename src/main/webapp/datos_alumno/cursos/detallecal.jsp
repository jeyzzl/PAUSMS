<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaGrupoEvaluacion"%>
<%@page import="aca.carga.spring.CargaGrupoActividad"%>
<%@page import="aca.kardex.spring.KrdxAlumnoEval"%>
<%@page import="aca.kardex.spring.KrdxAlumnoActiv"%>
<%@page import="aca.vista.spring.AlumnoEficiencia"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<% 
	java.text.DecimalFormat getFormato = new java.text.DecimalFormat("###,##0.00;(##0.00)");
	
	String matricula 			= (String) session.getAttribute("codigoAlumno");
	
	String cursoCargaId 		= request.getParameter("CursoCargaId")==null?"":request.getParameter("CursoCargaId");	
	String tipo	 				= request.getParameter("Tipo")==null?"":request.getParameter("Tipo");
	
	String materiaNombre 									= (String) request.getAttribute("materiaNombre");
	String maestroNombre 									= (String) request.getAttribute("maestroNombre");
	String alumnoNombre 									= (String) request.getAttribute("alumnoNombre");
	List<CargaGrupoEvaluacion> lisEvaluaciones 				= (List<CargaGrupoEvaluacion>) request.getAttribute("lisEvaluaciones");
	List<CargaGrupoActividad> lisActividades 				= (List<CargaGrupoActividad>) request.getAttribute("lisActividades");
	HashMap<String, AlumnoEficiencia> mapaEvaluaciones		= (HashMap<String, AlumnoEficiencia>) request.getAttribute("mapaEvaluaciones");
	HashMap<String, KrdxAlumnoActiv> mapaActividades		= (HashMap<String, KrdxAlumnoActiv>) request.getAttribute("mapaActividades");
	HashMap<String,String> mapPuntosEvaluados 				= (HashMap<String,String>) request.getAttribute("mapPuntosEvaluados");	
	HashMap<String,String> mapPuntosEvaluaciones			= (HashMap<String,String>) request.getAttribute("mapPuntosEvaluaciones");	
	
	String colorPortal 		= (String)session.getAttribute("colorPortal");
	if(colorPortal==null) colorPortal="";	
%>
<html>
<head>	
</head>
<body>
<div class="container-fluid">
	<h2>Evaluaciones <small class="text-muted fs-4"> ( <%=matricula%> - <%=alumnoNombre%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary btn-sm" href="materias"><spring:message code="aca.Regresar"/></a>
	</div>
	<div class="alert alert-info">
		<h4>Acta: [ <b><%=cursoCargaId%></b> ] &nbsp;&nbsp; Materia: [ <b><%=materiaNombre%></b> ] &nbsp;&nbsp; Profesor: [ <b><%=maestroNombre%></b> ]&nbsp;&nbsp;&nbsp;</h4>
	</div>	
			
	<table class="table table-nobordered table-sm table-fontsmall table-bordered">
	<tr>
		<th width="15%"><spring:message code="aca.Fecha"/></th>
		<th width="40%"><spring:message code="aca.Nombre"/></th>
		<th width="5%">Enviar</th>
		<th width="10%">Avance</th>
		<th width="10%" class="right">Valor</th>		
		<th width="10%" class="right"><spring:message code="aca.Nota"/></th>		
		<th width="10%" class="right">Pts/Ganados</th>
	</tr>								
<%
	float totalFinal 	= 0f;
	float valorFinal 	= 0f;
	float totPuntos		= 0; 
	float sumaValorEval = 0;
	
	for (CargaGrupoEvaluacion evaluacion : lisEvaluaciones){
		String nota 			= "0";
		String evaluados		= "0";
		String puntos 			= "0";
		String totActividades	= "0";
		if (mapaEvaluaciones.containsKey(matricula+evaluacion.getEvaluacionId())){
			nota 			= mapaEvaluaciones.get(matricula+evaluacion.getEvaluacionId()).getNota();
			evaluados		= mapaEvaluaciones.get(matricula+evaluacion.getEvaluacionId()).getEvaluadas();
			puntos 			= mapaEvaluaciones.get(matricula+evaluacion.getEvaluacionId()).getPuntos();
			totActividades	= mapaEvaluaciones.get(matricula+evaluacion.getEvaluacionId()).getTotActividades();
		}
		float ganados = 0;
		if (!totActividades.equals("0")){
			if (!evaluados.equals("0")){
				ganados = (Float.valueOf(evaluacion.getValor())*Float.valueOf(evaluados) / 100) * (Float.valueOf(puntos)/Float.valueOf(evaluados));
				puntos 	= getFormato.format(ganados);
			}else{
				puntos = "0";
			}			
		}
		
		float puntosEvaluacion = 0;
		if (mapPuntosEvaluaciones.containsKey(matricula+evaluacion.getEvaluacionId())){
			puntosEvaluacion = Float.valueOf(mapPuntosEvaluaciones.get(matricula+evaluacion.getEvaluacionId()));
			totPuntos += puntosEvaluacion; 
		}
		
		sumaValorEval 	+= Float.valueOf(evaluacion.getValor());
		
		String textoAvance = "";
		if (Double.valueOf(evaluados) >= 100){			
			textoAvance = "progress-bar-success";
		}else if (Double.valueOf(evaluados) >= 40 && Double.valueOf(evaluados) < 100){		
			textoAvance = "progress-bar-warning";
		}else{		
			textoAvance = "progress-bar-danger";
		}	
%>		
		<tr class="tr2">
			<td><h4><%=evaluacion.getFecha()%></h4></td>
			<td><h4><%=evaluacion.getEstrategiaId()%> - <%=evaluacion.getNombreEvaluacion()%></h4></td>
			<td width="10%">
<%		if (!evaluacion.getEnviar().equals("N")){ %>			
				<a title="Subir archivo" class="btn btn-info" href="subirArchivo?CursoCargaId=<%=cursoCargaId%>&Evaluacion=<%=evaluacion.getEvaluacionId()%>&Enviar=<%=evaluacion.getEnviar()%>"><i class="icon-upload"></i></a>
<% 		}%>				
			</td>
			<td>
				<div class="progress progress-striped">
  					<div class="progress-bar <%=textoAvance%>" role="progressbar" aria-valuenow="<%=evaluados%>" aria-valuemin="0" aria-valuemax="100" style="width: <%=evaluados%>%">
  					<span class="sr-only"><%=evaluados%>% evaluado</span>
  				 	</div>
  				 	<%=evaluados%>%
				</div>				
			</td>			
			<td class="right"><h4><%=evaluacion.getValor()%><%=evaluacion.getTipo()%></h4></td>			
			<td class="right"><h4><%=nota%></h4></td>			
			<td class="right"><h4><%=puntosEvaluacion%></h4></td>
		</tr>
<%		
		double sumaValorAct = 0;
		for (CargaGrupoActividad act : lisActividades){
		  	if (act.getEvaluacionId().equals(evaluacion.getEvaluacionId())){
		  		
		  		String notaActividad = "";
		  		if (mapaActividades.containsKey(matricula+act.getActividadId())){
		  			notaActividad = mapaActividades.get(matricula+act.getActividadId()).getNota();
		  		}
%>
		<tr class="tr2">
	    	<td style="text-align: center;"><em><%= act.getFecha() %><em></td>
	    	<td>
	      		<em>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= act.getNombre() %><em>
	    	</td>
	    	<td>
<%		if (!act.getEnviar().equals("N")){ %>			
				<a title="Subir archivo" class="btn btn-info" href="subirArchivo?CursoCargaId=<%=cursoCargaId%>&Actividad=<%=act.getActividadId()%>&Enviar=<%=act.getEnviar()%>"><i class="icon-upload"></i></a>
<% 		}%>	
	    	</td>
	    	<td>&nbsp;</td>
	    	<td class="right"><em><%= act.getValor() %><em></td>	    	
	    	<td class="right"><em><%= notaActividad %><em></td>
	    	<td>&nbsp;</td>
		</tr>
<%				
				sumaValorAct += Double.parseDouble(act.getValor());
			}		  
		}	// fin de for de actividades
		
		if (sumaValorAct > 0.00){
%>
<!-- 		<tr> -->
<!-- 			<td colspan="3" class="right" style="font-size:9pt;">&nbsp;</td> -->
<%-- 			<td class="right" style="font-size:9pt;"><em>Suma: [<%= getFormato.format(sumaValorAct) %>]</em></td> --%>
<!-- 			<td colspan="3">&nbsp;</td>			 -->
<!-- 		</tr>	 -->
<%	
		}
	} // Termina for de lisEvaluaciones
	
	float puntosEvaluados = 0;
	if (mapPuntosEvaluados.containsKey(cursoCargaId)){
		puntosEvaluados = Float.valueOf(mapPuntosEvaluados.get(cursoCargaId));
	}	
	
	float eficiencia = 0;
	if (puntosEvaluados > 0){
		eficiencia = (totPuntos*100) /puntosEvaluados;
	}
	
	String textoEficiencia = "";
	if (eficiencia >= 85){
		textoEficiencia = "<span class='badge' style='background-color:#0EE8B0; color:black;'>"+getFormato.format(eficiencia)+"</span>";
	}else if  (eficiencia >=70 && eficiencia < 85){
		textoEficiencia = "<span class='badge' style='background-color:#FFFF85; color:black;'>"+getFormato.format(eficiencia)+"</span>";
	}else{
		textoEficiencia = "<span class='badge' style='background-color:#FF6A4D; color:black;'>"+getFormato.format(eficiencia)+"</span>";
	}	
	
	float notaMaxima = (100-puntosEvaluados) + totPuntos;
	
	String textoMaximo = ""; 
	if (notaMaxima >= 85){
		textoMaximo = "<span class='badge' style='background-color:#0EE8B0; color:black;'>"+getFormato.format(notaMaxima)+"</span>";
	}else if  (notaMaxima >=70 && notaMaxima < 85){
		textoMaximo = "<span class='badge' style='background-color:#FFFF85; color:black;'>"+getFormato.format(notaMaxima)+"</span>";
	}else{
		textoMaximo = "<span class='badge' style='background-color:#EBE61D; color:black;'>"+getFormato.format(notaMaxima)+"</span>";
	}	
%>	
	<tr class="alert alert-info">
		<td colspan="3" class="right"><h4>Suma:</h4></td>
		<td class="right"><h4><%= getFormato.format(puntosEvaluados) %></h4></td>
		<td class="right"><h4><%= getFormato.format(sumaValorEval) %></h4></td>			
		<td class="right"><h4><%=textoEficiencia%></h4></td>		
		<td class="right"><h4><span class="badge bg-dark" title="Puntos totales ganados en la materia"><%= getFormato.format(totPuntos) %></span></h4></td>
	</tr>
<%	if (tipo.equals("I")){ %>
	<tr>
		<td colspan="6" class="right"><h4>Nota máxima<small class="text-muted fs-6">(Formula= 100 - Puntos evaluados(<%=getFormato.format(puntosEvaluados)%>) + Puntos ganados(<%=getFormato.format(totPuntos)%>))</small></h4></td>
		<td class="right"><%=textoMaximo%></td>			
	</tr>
<%	} %>	
</table>
<div class="alert alert-success">		
	<span><b>Nota:</b></span> Promedio calculado en base a los puntos que ya han sido evaluados</span>&nbsp;&nbsp;&nbsp;
	<span><b>Puntos ganados:</b></span> Puntos netos alcanzados en la materia</span>&nbsp;&nbsp;&nbsp;
	<span><b>Nota maxima:</b></span> Es la calificación máxima que podría obtener</span>
</div>
</div>