<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaGrupoEvaluacion"%>
<%@page import="aca.carga.spring.CargaGrupoActividad"%>
<%@page import="aca.kardex.spring.KrdxAlumnoEval"%>
<%@page import="aca.kardex.spring.KrdxAlumnoActiv"%>
<%@page import="aca.vista.spring.AlumnoEficiencia"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%@ include file= "menu.jsp" %>
<% 
	java.text.DecimalFormat getFormato = new java.text.DecimalFormat("###,##0.00;(##0.00)");
	
	String matricula 			= (String) session.getAttribute("codigoAlumno");
	String esMovil				= (String) session.getAttribute("esMovil");
	
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
	HashMap<String,String> mapaActividadEnviada 			= (HashMap<String, String>)request.getAttribute("mapaActividadEnviada");
	// Mapa de letras para calificacion
	HashMap<String, String> mapaLetrasDeNotas	= (HashMap<String,String>) request.getAttribute("mapaLetrasDeNotas");

	String colorPortal 		= (String)session.getAttribute("colorPortal");
	if(colorPortal==null) colorPortal="";	
%>
<html>
<head>	
</head>
<body>
<div class="container-fluid">
	<h2>Evaluations <small class="text-muted" style="font-size:1rem;"> ( <%=matricula%> - <%=alumnoNombre%> )</small></h2>
	<div class="alert alert-info">
		<a href="materias" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		<a class="btn btn-outline-primary btn-sm" href="filesMaestro?CursoCargaId=<%=cursoCargaId%>&Tipo=<%=tipo%>">Files</a>
	</div>
	<div class="alert alert-info">
		<h5>Record: <b><%=cursoCargaId%></b> &nbsp;&nbsp; Subject: <b><%=materiaNombre%></b> &nbsp;&nbsp; Professor: <b><%=maestroNombre%></b>&nbsp;&nbsp;&nbsp;</h5>
	</div>	
			
	<table class="table table-condensed table-bordered">
	<tr class="bg-dark">
		<th width="3%" class="text-center text-white"><h6>#</h6></th>
		<th width="10%" class="text-start text-white"><h6><spring:message code="aca.Fecha"/></h6></th>
		<th width="47%" class="text-start text-white"><h6><spring:message code="aca.Nombre"/></h6></th>
		<th width="5%" class="text-center text-white"><h6>Upload</h6></th>
		<th width="5%" class="text-center text-white"><h6>Value</h6></th>		
		<th width="15%" class="text-center text-white"><h6>Progress</h6></th>			
		<th width="5%" class="text-center text-white"><h6><spring:message code="aca.Nota"/></h6></th>		
		<th width="5%" class="text-center text-white" title="Graded Points"><h6>Pts/Grad.</h6></th>	
		<th width="5%" class="text-center text-white" title="Obtained Points"><h6>Pts/Obtd.</h6></th>		
	</tr>								
<%

	float totalFinal 	= 0f;
	float valorFinal 	= 0f;
	float totPuntos		= 0; 
	float sumaValorEval = 0;

	int rowEval=0;
	for (CargaGrupoEvaluacion evaluacion : lisEvaluaciones){
		rowEval++;
		String nota 			= "0";
		String notaGPA 			= "0";
		String evaluados		= "0";
		String puntos 			= "0";
		String totActividades	= "0";
		String nota100			= "0";
		String letraProm 		= "";
		if (mapaEvaluaciones.containsKey(matricula+evaluacion.getEvaluacionId())){
			nota 			= mapaEvaluaciones.get(matricula+evaluacion.getEvaluacionId()).getNota();
			evaluados		= mapaEvaluaciones.get(matricula+evaluacion.getEvaluacionId()).getEvaluadas();
			puntos 			= mapaEvaluaciones.get(matricula+evaluacion.getEvaluacionId()).getPuntos();
			totActividades	= mapaEvaluaciones.get(matricula+evaluacion.getEvaluacionId()).getTotActividades();
			if(evaluacion.getTipo().equals("%")) {
				nota100 = nota;
			}else{
				nota100 = String.valueOf(Double.valueOf(nota) * 100 / Double.valueOf(evaluacion.getValor())); System.out.println("TEST 6");
			}
			System.out.println(nota+" "+nota100);
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

		if(mapaLetrasDeNotas.containsKey(nota100)){
			letraProm = mapaLetrasDeNotas.get(nota100);
		}else{
			letraProm = "F";			
		}
		
		float evaluadosEval = Float.valueOf(evaluados) * Float.valueOf(evaluacion.getValor()) / 100;
		
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
		<tr>
			<td class="center"><h4 title="<%=evaluacion.getEvaluacionId()%>"><%=rowEval%></h4></td>
			<td class="d-flex-inline"><h5><%=evaluacion.getFecha().substring(0,10)%></h5><small><b><%=evaluacion.getFecha().substring(10,19)%></b></small></td>
			<td><h4><%=evaluacion.getNombreEvaluacion()%></h4></td>
			<td>
<%		if (!evaluacion.getEnviar().equals("N")){ %>			
				<a title="Upload File" class="btn btn-info" href="subirArchivo?CursoCargaId=<%=cursoCargaId%>&Evaluacion=<%=evaluacion.getEvaluacionId()%>&Enviar=<%=evaluacion.getEnviar()%>"><i class="fas fa-upload"></i></a>
<% 		}%>				
			</td>
			<td class="text-center align-content-center"><h5><%=evaluacion.getValor()%></h5></td>
			<td class="align-content-center">
				<div class="progress progress-striped">
  					<div class="progress-bar <%=textoAvance%>" role="progressbar" aria-valuenow="<%=evaluados%>" aria-valuemin="0" aria-valuemax="100" style="width: <%=evaluados%>%">
  					<span class="sr-only"><%=evaluados%>% Graded</span>
  				 	</div>
  				 	<%=evaluados%>%
				</div>				
			</td>	
			<td class="text-center align-content-center"><h4><%=letraProm%></h4></td>		
			<td class="text-center align-content-center"><h4><%=evaluadosEval%></h4></td>						
			<td class="text-center align-content-center"><h4><%=puntosEvaluacion%></h4></td>
		</tr>
<%		
		double sumaValorAct = 0;
		int rowAct = 0;
		String letraPromAct = "";
		for (CargaGrupoActividad act : lisActividades){
			
		  	if (act.getEvaluacionId().equals(evaluacion.getEvaluacionId())){
		  		rowAct++;
		  		String notaActividad = "";
		  		if (mapaActividades.containsKey(matricula+act.getActividadId())){
		  			notaActividad = mapaActividades.get(matricula+act.getActividadId()).getNota();
		  		}
				if(mapaLetrasDeNotas.containsKey(notaActividad)){
					letraPromAct = mapaLetrasDeNotas.get(notaActividad);
				}else{
					letraPromAct = "F";			
				}
%>
		<tr class="tr2">
			<td style="text-align: center; font-size:0.9rem;" title="<%=act.getActividadId()%>"><%=rowEval%>.<%=rowAct%></td>
	    	<td style="text-align: center; font-size:0.9rem;"><em><%= act.getFecha() %></em></td>
	    	<td style="font-size:0.9rem;">
	      		<em><%= act.getNombre() %></em>
	    	</td>
	    	<td style="font-size:0.9rem; text-align: center;">
<%
	String btnColor = "btn-info";
	if (!act.getEnviar().equals("N")){
		if(mapaActividadEnviada.containsKey(act.getActividadId())){
			btnColor = "btn-success";
		}else{
			btnColor = "btn-secondary";
		}
%>
				<a title="Upload file" class="btn <%=btnColor%>" href="subirArchivo?CursoCargaId=<%=cursoCargaId%>&Actividad=<%=act.getActividadId()%>&Evaluacion=<%=evaluacion.getEvaluacionId()%>&Enviar=<%=act.getEnviar()%>"><i class="fas fa-upload"></i></a>
<%
	}%>
	    	</td>	    	
	    	<td class="text-center" style="font-size:0.9rem;"><em><%= act.getValor() %></em></td>
	    	<td style="font-size:0.9rem;">&nbsp;</td>
	    	<td class="text-center" style="font-size:0.9rem;"><em><%= letraPromAct %></em></td>
	    	<td style="font-size:0.9rem;">&nbsp;</td>
	    	<td style="font-size:0.9rem;">&nbsp;</td>
		</tr>
<%				
				sumaValorAct += Double.parseDouble(act.getValor());
			}		  
		}	// fin de for de actividades
		
		if (sumaValorAct > 0.00){
%>
<!-- 		<tr> -->
<!-- 			<td colspan="3" class="text-end" style="font-size:9pt;">&nbsp;</td> -->
<%-- 			<td class="text-end" style="font-size:9pt;"><em>Suma: [<%= getFormato.format(sumaValorAct) %>]</em></td> --%>
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

	String letraEficiencia = "";
	int iEficiencia 		= Math.round(eficiencia);
	String sEficiencia 		= String.valueOf(iEficiencia);
	if(mapaLetrasDeNotas.containsKey(sEficiencia)){
		letraEficiencia = mapaLetrasDeNotas.get(sEficiencia);
	}else{
		letraEficiencia = "";			
	}
	
	String textoEficiencia = "";
	if (eficiencia >= 85){
		textoEficiencia = "<span class='badge' style='background-color:#0EE8B0; color:black;'>"+letraEficiencia+"</span>";
	}else if  (eficiencia >=70 && eficiencia < 85){
		textoEficiencia = "<span class='badge' style='background-color:#FFFF85; color:black;'>"+letraEficiencia+"</span>";
	}else{
		textoEficiencia = "<span class='badge' style='background-color:#FF6A4D; color:black;'>"+letraEficiencia+"</span>";
	}	
	
	float notaMaxima = (100-puntosEvaluados) + totPuntos;

	String letraMaxima = "";
	int iMaxima 		= Math.round(notaMaxima);
	String sMaxima 		= String.valueOf(iMaxima);
	if(mapaLetrasDeNotas.containsKey(sMaxima)){
		letraMaxima = mapaLetrasDeNotas.get(sMaxima);
	}else{
		letraMaxima = "";			
	}
	
	String textoMaximo = ""; 
	if (notaMaxima >= 85){
		textoMaximo = "<span class='badge' style='background-color:#0EE8B0; color:black;'>"+letraMaxima+"</span>";
	}else if  (notaMaxima >=70 && notaMaxima < 85){
		textoMaximo = "<span class='badge' style='background-color:#FFFF85; color:black;'>"+letraMaxima+"</span>";
	}else{
		textoMaximo = "<span class='badge' style='background-color:#E81A0C; color:black;'>"+letraMaxima+"</span>";
	}	
%>	
	<tr class="bg-light">
		<th colspan="4" class="text-end"><h4>Sum:</h4></th>
		<th class="text-end"><h4 class="text-black"><%= getFormato.format(sumaValorEval) %></h4></th>
		<th class="text-end"><h4>&nbsp;</h4></th>			 		
		<th class="text-center"><h4><%=textoEficiencia%></h4></th>
		<th class="text-end"><h4><%= getFormato.format(puntosEvaluados) %></h4></th>		
		<th class="text-end"><h4 title="Total points earned in the subject"><%=getFormato.format(totPuntos) %></h4></th>
	</tr>
<%	if (tipo.equals("I")){ %>
	<tr>
		<td colspan="9" class="center"><h5>Max Grade = <%=textoMaximo%> &nbsp; Calculation: <small class="text-muted fs-4"> 100 - Course Points(<%=getFormato.format(puntosEvaluados)%>) + Obtained Points(<%=getFormato.format(totPuntos)%>)</small></h5></td>				
	</tr>
<%	} %>	
</table>
<%	if (esMovil.equals("S")){%>
	<div class="alert alert-success">		
		<span><b>Note:</b> <i class="fas fa-question-circle" data-bs-toggle="tooltip" data-bs-placement="top" title="Average calculated based on the points that have already been evaluated."></i></span>&nbsp;&nbsp;
		<span><b>Gained Points:</b> <i class="fas fa-question-circle" data-bs-toggle="tooltip" data-bs-placement="top" title="Net points achieved in the subject"></i></span>&nbsp;&nbsp;&nbsp;
		<span><b>Max grade:</b> <i class="fas fa-question-circle" data-bs-toggle="tooltip" data-bs-placement="top" title="It is the highest grade that could be obtained"></i></span>
	</div>
<%	}else{%>
	<div class="alert alert-success">		
		<span><b>Note:</b></span> Average calculated based on the points that have already been graded.</span>&nbsp;&nbsp;&nbsp;
		<%-- <span><b>Gained Points:</b></span> Net points achieved in the subject</span>&nbsp;&nbsp;&nbsp;
		<span><b>Max grade:</b></span> It is the highest grade that could be obtained</span> --%>
	</div>
<% }%>
</div>
<script>	
	$(function () {
  		$('[data-bs-toggle="tooltip"]').tooltip();
  		$('[data-bs-toggle="popover"]').popover();
	});
</script>