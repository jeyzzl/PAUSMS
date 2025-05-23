<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.*" %>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.alumno.spring.CancelaEstudio"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.plan.spring.MapaCredito"%>
<%@page import="aca.plan.spring.MapaAvance"%>
<%@page import="aca.plan.spring.MapaMayorMenor"%>
<%@page import="aca.catalogo.spring.CatTipoCurso"%>
<%@page import="aca.catalogo.spring.CatTipoCal"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.vista.spring.Maestros"%>
<%@page import="aca.parametros.spring.Parametros"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../idioma.jsp"%>
<html>
<%
	session.setAttribute("IngresoAlumno", "false");
	session.setAttribute("SaldoVencido", 0.0);	
%>
<%@ include file= "menu.jsp" %>

<script type="text/javascript">
	function recarga(){
		document.forma.submit();
	}	
</script>
<%		
	DecimalFormat getFormato		= new DecimalFormat("###,##0.00;(###,##0.00)");
	
	String codigoPersonal 			= (String) session.getAttribute("codigoPersonal");	
	String matricula				= (String) session.getAttribute("codigoAlumno");	
	String institucion 				= (String) session.getAttribute("institucion");	
	String colorPortal 				= session.getAttribute("colorPortal")==null?"":session.getAttribute("colorPortal").toString();
	String cargaId 					= (String) session.getAttribute("cargaId");	
	
	String ver						= request.getParameter("ver")==null?"T":request.getParameter("ver");
	String planId					= request.getParameter("plan")==null?"0":request.getParameter("plan");

	boolean esBusiness 		= false;
	boolean esArtes			= false;
	
	String ultimaInscripcion 		= (String)request.getAttribute("ultimaInscripcion");
	String modalidadUsuario 		= (String)request.getAttribute("modalidadUsuario");
	String modalidadAlumno 			= (String)request.getAttribute("modalidadAlumno");	
	boolean mostrar 				= (boolean)request.getAttribute("mostrar");
	AlumPersonal alumPersonal		= (AlumPersonal) request.getAttribute("alumPersonal");
	AlumPlan alumPlan				= (AlumPlan) request.getAttribute("alumPlan");
	String tipoProm					= (String)request.getAttribute("tipoProm");
	boolean esEmpleado 				= (boolean)request.getAttribute("esEmpleado");
	boolean tienePlan				= (boolean) request.getAttribute("tienePlan");
	boolean tienePlanActivo			= (boolean) request.getAttribute("tienePlanActivo");
	boolean tieneRvoe				= (boolean) request.getAttribute("tieneRvoe");
	CancelaEstudio cancelaEstudio	= (CancelaEstudio) request.getAttribute("cancelaEstudio");
	boolean cancelado 				= (boolean) request.getAttribute("cancelado");
	Parametros parametros 			= (Parametros)request.getAttribute("parametros");
	AlumnoCurso alumnoCurso 		= new AlumnoCurso();
	Carga carga 					= (Carga)request.getAttribute("carga");
	
	String fondo 					= "";
	String avance 					= "";
	String sFecha					= "";
	String gradePointNombre			= "";
	String gradePointValor			= "";
	String escala 					= alumPlan.getEscala();
	String planActual				= alumPlan.getPlanId();
	String cicloSep					= alumPlan.getCicloSep();
	
	double ponderado 				= 0;
	double pglobal					= 0;
	double pglobalF     			= 0;
	
	float gcreditos=0, creditos=0, notasxcreditos=0;
	int cursosCiclo=0, cursosAc=0;
	float creditosCiclo=0, creditosOb=0, creditosEl=0;
	float creditosAC=0, creditosACO=0, creditosACE=0;
	float faltan=0, faltanOb=0, faltanEl=0;
	float creditosConvalidados 		= 0;
	
	boolean buscaCurso 				= false;
	boolean tieneConvalidadas 		= false;
	
	String colorNota 				= "";	
	String tipoCurso				= "";
	String tipoCursoId 				= "";
	
	List<AlumnoCurso> lisCursosAlumno 					= (List<AlumnoCurso>) request.getAttribute("lisCursosAlumno");
	List<MapaCurso> lisCursosCarrera 					= (List<MapaCurso>) request.getAttribute("lisCursosCarrera");
	List<AlumPlan> lisPlanes 							= (List<AlumPlan>) request.getAttribute("lisPlanes");
	List<MapaAvance> lisAvances 						= (List<MapaAvance>) request.getAttribute("lisAvances");
	List<CatTipoCurso> lisTiposCurso					= (List<CatTipoCurso>) request.getAttribute("lisTiposCurso");
	
	HashMap<String, MapaPlan> mapaPlanes				= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String, MapaCurso> mapaCursos				= (HashMap<String,MapaCurso>) request.getAttribute("mapaCursos");
	HashMap<String, MapaCredito> mapaCreditos			= (HashMap<String,MapaCredito>) request.getAttribute("mapaCreditos");
	HashMap<String, CatTipoCurso> mapaTipoCurso			= (HashMap<String,CatTipoCurso>) request.getAttribute("mapaTipoCurso");
	HashMap<String, CatTipoCal> mapaTipoCal				= (HashMap<String,CatTipoCal>) request.getAttribute("mapaTipoCal");
	
	HashMap<String, MapaAvance> mapaAvances				= (HashMap<String,MapaAvance>) request.getAttribute("mapaAvances");	
	HashMap<String, String> mapaCreditosCicloyTipo		= (HashMap<String,String>) request.getAttribute("mapaCreditosCicloyTipo");
	HashMap<String, String> mapaTipoPorCiclo 			= (HashMap<String,String>) request.getAttribute("mapaTipoPorCiclo");
	HashMap<String, String> mapaCreditosPorCiclo 		= (HashMap<String,String>) request.getAttribute("mapaCreditosPorCiclo");
	
	HashMap<String, String> mapaCreditosPorCicloAvance 	= (HashMap<String,String>) request.getAttribute("mapaCreditosPorCicloAvance");
	HashMap<String, String> mapaCreditoObligatorio		= (HashMap<String,String>) request.getAttribute("mapaCreditoObligatorio");
	HashMap<String, String> mapaMateriasConv			= (HashMap<String,String>) request.getAttribute("mapaMateriasConv");
	HashMap<String, String> mapaCreditosAlumno			= (HashMap<String,String>) request.getAttribute("mapaCreditosAlumno");	
	HashMap<String, Maestros> mapaMaestros				= (HashMap<String,Maestros>) request.getAttribute("mapaMaestros");
	HashMap<String, AlumnoCurso> mapaCursosDelAlumno	= (HashMap<String,AlumnoCurso>) request.getAttribute("mapaCursosDelAlumno");	
	HashMap<String, String> mapaGradePoint				= (HashMap<String,String>) request.getAttribute("mapaGradePoint");
	HashMap<String, MapaMayorMenor> mapaMayores 		= (HashMap<String,MapaMayorMenor>) request.getAttribute("mapaMayores");
	HashMap<String, MapaMayorMenor> mapaMenores 		= (HashMap<String,MapaMayorMenor>) request.getAttribute("mapaMenores");
	
	// CHECA SI ES UN PLAN DE ARTES
	if(planActual.equals("BAENGE23") || planActual.equals("BAENHI23") || planActual.equals("BAGEHI23")) esArtes = true;

	String nombreMayor = "n/a";
	String nombreMenor = "n/a";

	String nombreOptativa = "-";
%>

<html>
<head>
	<link href="../../print.css" rel="STYLESHEET" type="text/css" media="print">
</head>
<div class="container-fluid">	
<%	
	if(tienePlanActivo){
	if(mostrar){%>
	<div class="alert alert-danger"><strong>Modality</strong></div>	      		
<%	}%>

<%	if(cancelaEstudio.getEstado().equals("A")){%>
	<div class="position-fixed" style="width:100%; padding-top: 200px; transform: rotate(-45deg)" align="center">
	   <span style="color: red; font-size: 95px; opacity: 0.5;">Canceled Studies</span>
	</div>
<%	}%>
	<table style="width:99%; border-spacing: 0px; margin:0 auto">
	<form name="forma" action="calificaciones" method='post'>
	<tr>
		<td valign="top" align="center" style="font-size:0.8rem;">
				<span class="oculto">Plan:&nbsp;</span>
				<select class="oculto input input-xxlarge" name="plan" onchange="javascript:recarga()">
				<%	
					for( AlumPlan planAlum : lisPlanes){
						String planNombre = "-";
						if (mapaPlanes.containsKey(planAlum.getPlanId()) ){
							planNombre	= mapaPlanes.get(planAlum.getPlanId()).getNombrePlan();
						}
				%>
	  				<option value='<%=planAlum.getPlanId()%>' <%=planActual.equals(planAlum.getPlanId())?"Selected":""%>>
				  			[<%=planAlum.getFInicio()%>]-[<%=planAlum.getPlanId()%>] - <%=planNombre%>
				  		</option>
				<%	}%>
 				</select>
 				<span class="oculto">&nbsp;&nbsp; View:</span>
 				<select class="oculto input input-medium" name="ver" onchange="javascript:recarga()">
 					<option value="A" <% if(ver.equals("A")) out.print("Selected");%>>Credited</option>
					<option value="R" <% if(ver.equals("R")) out.print("Selected");%>>Failed</option>
					<option value="D" <% if(ver.equals("D")) out.print("Selected");%>>Deferred</option>					
					<option value="T" <% if(ver.equals("T")) out.print("Selected");%>>All</option>
 				</select>
<%
		if(tienePlan){
%>
 				<span class="oculto">&nbsp;&nbsp; Plan:</span>&nbsp;
 				<a href="bajarPlan?PlanId=<%=planActual%>&Folio=2" style="color:black" title="Download"><i class="fas fa-file-pdf"></i></a>
<% 
		}
		if(tieneRvoe){
%>
				<span class="oculto">&nbsp;&nbsp; RVOE:</span>&nbsp;
				<a class="oculto" href="bajarPlan?PlanId=<%=planActual%>&Folio=1" style="color:black" title="Download"><i class="fas fa-file-pdf"></i></a>	
<% 
		}
%>	
 				<br>
<% 			if(parametros.getInstitucion().equals("Pacific Adventist University")){ %>
 				<a href="pauPdf?PlanId=<%=planActual%>">Transcript</a>
<% 			} %>
<% 			if(parametros.getInstitucion().equals("Sonoma")){ %>
 				<a href="sonomaPdf?PlanId=<%=planActual%>">Sonoma</a>
<% 			} %>
<% 			if(parametros.getInstitucion().equals("Fulton")){ %>
 				<a href="fultonPdf?PlanId=<%=planActual%>">Fulton</a>
<% 			} %>
				<h5><%=institucion.toUpperCase()%> - GRADES</h5>
				<h6><i>Student</i>: <%=matricula%>&nbsp;  <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoMaterno().equals("-")?"":alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%> &nbsp; <i>Degree</i>: <%=mapaPlanes.get(planActual).getCarreraSe()%></h6>				
			</td>
		</tr>
	</form>	
	<%				
		if(!cancelado || cancelaEstudio.equals("I") || (codigoPersonal.substring(0,1).equals("9") && !cancelaEstudio.equals("A")) ){
			if(mapaMayores.containsKey(alumPlan.getMayor())){
				nombreMayor = mapaMayores.get(alumPlan.getMayor()).getNombre();
			}
			if(mapaMenores.containsKey(alumPlan.getMenor())){
				nombreMenor = mapaMenores.get(alumPlan.getMenor()).getNombre();
			}
	%>
  			<tr valign="top">
				<td align="center">
					<table style="width:100%" style=" border-spacing: 0px;" >
					<!-- DATOS DE PERIODO-->
				  		<tr valign="top">
							<td colspan="3" style="text-align:center; font-size:0.8rem;">
								Plan: <strong>[<%=planActual%>]&nbsp; </strong>
								First Enrollment: <strong>[<%=alumPlan.getPrimerMatricula()%>]</strong>&nbsp; &nbsp;
								Last Enrollment: <strong>[<%=ultimaInscripcion%>]</strong>&nbsp; &nbsp; 
								Major: <strong>[<%=nombreMayor%>]</strong>&nbsp; &nbsp;
								<%=esArtes?"Major":"Minor"%> <strong>[<%=nombreMenor%>]</strong>&nbsp; &nbsp; 
								Upcoming Subjects: <a href="oferta?PlanId=<%=planActual%>&Origen=Calificaciones"><i class="fas fa-search"></i></a>
							</td>
				  		</tr>
				  		<tr><td>&nbsp;</td></tr>
<%	
						
						int i,j,contCurso=0;
						String semestre="0";
						String semestreGuardado = "";
						String titulo="N";
						boolean nuevaFila = true, OKc=false, OKe=false, OKd=false, Salir=false;
						MapaCurso curso 	= new MapaCurso();
						if(lisCursosCarrera.size()>0) curso = (MapaCurso) lisCursosCarrera.get(contCurso);
						semestre = curso.getCiclo();							
						while (contCurso< lisCursosCarrera.size()){
							HashMap<String, Float> mapaCred = new HashMap<String, Float>();
							HashMap<String, Float> mapaCred2 = new HashMap<String, Float>();
							for(int tipCurso=1; tipCurso<=lisTiposCurso.size(); tipCurso++){
								MapaAvance mapAvance = new MapaAvance();
								if (mapaAvances.containsKey(planId+semestre+tipCurso)){
									mapAvance = mapaAvances.get(planId+semestre+tipCurso);
								}
								float creditosOblig = 0;
								if(mapAvance!=null) creditosOblig = Float.parseFloat(mapAvance.getCreditos());
								mapaCred.put(tipCurso+"", creditosOblig);
								mapaCred2.put(tipCurso+"", 0f);
							}
							boolean completoPorEstado = true;
							
							semestreGuardado = semestre;
							
							if (nuevaFila){ 
								nuevaFila = false; 
%>
			  	  				<tr valign="top">
<%							
							}
							
							String TipoCursoId = mapaCursos.get(curso.getCursoId()).getTipoCursoId();
							String cicloNombre = "-";							
							if (mapaCreditos.containsKey(planActual+semestre)){
								cicloNombre = mapaCreditos.get(planActual+semestre).getTitulo();								
							}
							
							String semestreOficial = "";
							if(mapaCreditos.containsKey(alumPlan.getPlanId()+semestreGuardado)){
								if(mapaCreditos.get(alumPlan.getPlanId()+semestreGuardado).getEstado().equals("O")){
									semestreOficial = "<i class='fas fa-certificate' style='color:#cda434'></i>"; 
								}
							}	
%>
							<td width="50%" valign="top">
					  	  		<table style="border-spacing:2px;" class="table table-fullcondensed table-fontsmall">
									<tr>
								  	  	<th width="60%" style="padding:1px;"><h6 class="font-weight-bold"><%=semestreOficial%> <%=cicloNombre%></h6></th>
								  	  	<th width="7%"  style="padding:1px; text-align:center" class="text-center"><h6 class="font-weight-bold"><spring:message code="aca.Tipo"/></h6></th>
								  	  	<th width="4%"  style="padding:1px; text-align:center" class="text-center"><h6 class="font-weight-bold"><spring:message code="aca.Crd"/></h6></th>
								  	  	<th width="8%"  style="padding:1px; text-align:center" class="text-center"><h6 class="font-weight-bold">Grade</h6></th>
								  	  	<th width="8%"  style="padding:1px; text-align:center" class="text-center"><h6 class="font-weight-bold"><spring:message code="aca.Fecha"/></h6></th>
								  	  	<th width="8%"  style="padding:1px; text-align:center"><h6 class="font-weight-bold"><spring:message code="aca.Status"/></h6></th>
									</tr>
									<%	
										creditos 		= 0; notasxcreditos 	= 0; 
										cursosAc 		= 0; cursosCiclo 		= 0;
										creditosCiclo	= 0; creditosOb 		= 0; creditosEl 	= 0;
										creditosAC 		= 0; creditosACO 		= 0; creditosACE 	= 0;
										sFecha					= " "; 
										buscaCurso 				= false; 
										tieneConvalidadas 		= false;
										creditosConvalidados 	= 0;						
										alumnoCurso 			= new AlumnoCurso();
										boolean siTipoCal; 
										boolean bloqueoCarga = false;
										boolean ComponentesAprobadosEnSemestre = true;	

										while(curso.getCiclo().equals(semestre) && contCurso < lisCursosCarrera.size()){
											OKc=OKe=OKd=false; 
											cursosCiclo++;
											siTipoCal=false; 
											buscaCurso= false;											
											for(i=0; i<lisCursosAlumno.size(); i++){
												sFecha 		= " ";
												alumnoCurso = (AlumnoCurso) lisCursosAlumno.get(i);	
												buscaCurso 	= false;				
											
												if(alumnoCurso.getCursoId().equals(curso.getCursoId())){
													buscaCurso = true;												
													mapaCred2.put(curso.getTipoCursoId(), mapaCred2.get(curso.getTipoCursoId())+Float.parseFloat(alumnoCurso.getCreditos()));												
													
													//Recupera Fecha 
																										//Trae fecha si la materia esta titulada
													if(alumnoCurso.getTitulo().equals("S") && alumnoCurso.getFTitulo()!=null){
														sFecha = alumnoCurso.getFTitulo().substring(0,6) + alumnoCurso.getFTitulo().substring(8,10);
													}else if(alumnoCurso.getTipoCalId().equals("1")){ 	// Trae fecha de materia si esta aprobada
														if(Float.valueOf(alumnoCurso.getNota()) >= Float.valueOf(alumnoCurso.getNotaAprobatoria()) || alumnoCurso.getCreditos().equals("0.00")){
															if(alumnoCurso.getFEvaluacion().length()>=10){
																sFecha = alumnoCurso.getFEvaluacion().substring(0,6) + alumnoCurso.getFEvaluacion().substring(8,10);
															}
														}else{														
															if(alumnoCurso.getFExtra()!=null && alumnoCurso.getFExtra().length()>=10){
																sFecha = alumnoCurso.getFExtra().substring(0,6) + alumnoCurso.getFExtra().substring(8,10);
															}
															else if(alumnoCurso.getFEvaluacion()!=null && alumnoCurso.getFEvaluacion().length()>=10){
																sFecha = alumnoCurso.getFEvaluacion().substring(0,6) + alumnoCurso.getFEvaluacion().substring(8,10);
															}
														}
													}else if(alumnoCurso.getFEvaluacion() != null){ 	// Trae fecha de materia si esta evaluada
														sFecha = alumnoCurso.getFEvaluacion().substring(0,6) + alumnoCurso.getFEvaluacion().substring(8,10);
													}
													
													siTipoCal=true;
													lisCursosAlumno.remove(i);
													if(!alumnoCurso.getNota().equals("0") || alumnoCurso.getConvalidacion().equals("S") || Float.parseFloat(alumnoCurso.getCreditos()) > 0 ) OKc=true;
													if(!alumnoCurso.getNotaExtra().equals("0")) OKe=true;
													if(OKc||OKe) OKd = true;
													if(alumnoCurso.getTipoCalId().equals("1")) cursosAc++; // SUMA CURSOS ACTIVOS	
													
													tipoCursoId 	= "0";
													if (mapaCursos.containsKey(alumnoCurso.getCursoId())){
														tipoCursoId = mapaCursos.get(alumnoCurso.getCursoId()).getTipoCursoId();
													}
													// OBTIENE EL PROMEDIO POR CICLO [ FORMULA = SUMA(CREDITOS X NOTAS) / SUMA(CREDITOS)]
													// SE CALCULA SOLAMENTE SOBRE MATERIAS OBLIGATORIAS, ELECTIVAS
						  	    					if (mapaGradePoint.containsKey(alumnoCurso.getNota())){
						  	    						gradePointNombre 	= mapaGradePoint.get(alumnoCurso.getNota());
						  	    						gradePointValor 	= gradePointNombre.split(";")[1];
						  	    						gradePointNombre	= gradePointNombre.split(";")[0];
						  	    					}
						  	    					
													if(tipoCursoId.equals("1")||tipoCursoId.equals("2")){
														// Si es tipo PA
														if(alumnoCurso.getTipoCalId().equals("1")){
															//Cuenta los creditos obligatorios y los optativos
															if(tipoCursoId.equals("1")){ 
																creditosACO+= Float.parseFloat(alumnoCurso.getCreditos().trim()); // CREDITOS OBLIGATORIOS
															}
															else if(tipoCursoId.equals("2")){
																creditosACE+= Float.parseFloat(alumnoCurso.getCreditos().trim()); // CREDITOS ELECTIVOS
															}
															else{
																creditosAC+= Float.parseFloat(alumnoCurso.getCreditos().trim());  // CREDITOS TOTALES
															}
															
															if((OKc == true || OKe == true) && (alumnoCurso.getConvalidacion().equals("N") || alumnoCurso.getConvalidacion().equals("I"))){	
																// suma los creditos acreditados.
																creditos += Float.parseFloat(alumnoCurso.getCreditos().trim());
																// Excluye del promedio las materias de fase culminante y las convalidaciones que no sean internas																	
																if (OKe) {
																	notasxcreditos = notasxcreditos + Float.parseFloat(alumnoCurso.getCreditos())*Float.valueOf(alumnoCurso.getNotaExtra());
																} else {
																	notasxcreditos = notasxcreditos + Float.parseFloat(alumnoCurso.getCreditos())*Float.valueOf(gradePointValor);
																}
															}
															else{
																// CUENTA CREDITOS CONVALIDADOS Y CREDITOS DE MATERIAS AC/NA ..
																tieneConvalidadas = true;
																creditosConvalidados += Float.parseFloat(alumnoCurso.getCreditos().trim());
															}
														}
													}													
													// fin de for cuando encuentra el curso
													break;
												}
											}											
											// Verifica si la convalidacion de la materia esta en tramite
											boolean convEnTramite = false;
											String tipoConvalida = "-";
											if (mapaMateriasConv.containsKey(curso.getCursoId()) ){												
												convEnTramite = true;												
											}
											
											// GENERA EL COLOR DE LA NOTA Y LA MATERIA
											String colorMateria = "black";											
											if(buscaCurso && alumnoCurso.getConvalidacion().equals("I") && convEnTramite == false){
												colorNota 		= "color=#4CAF50";
												colorMateria 	= "#4CAF50";												
											}else if (buscaCurso && alumnoCurso.getConvalidacion().equals("S") && convEnTramite == false){
												colorNota 		= "color=#ff9800";
												colorMateria	= "#ff9800";												
											}else if (convEnTramite){
												colorNota 		= "color=purple";
												colorMateria	= "#6836e2";												
											}else{										
												colorNota 		= "";												
											}
											
											// NOMBRE DEL TIPO DE CURSO
											tipoCurso = "-";
											if (mapaTipoCurso.containsKey(curso.getTipoCursoId())){
												tipoCurso = mapaTipoCurso.get(curso.getTipoCursoId()).getNombreTipoCurso();
											}
											
											// NOMBRE DEL MAESTRO DEL CURSO
											String maestroNombre = "-";
											if (mapaMaestros.containsKey(alumnoCurso.getMaestro())){
												maestroNombre = mapaMaestros.get(alumnoCurso.getMaestro()).getNombre()+" "+mapaMaestros.get(alumnoCurso.getMaestro()).getApellidoPaterno()+" "+mapaMaestros.get(alumnoCurso.getMaestro()).getApellidoMaterno();
											}
											
											// SI ES EMPLEADO
											String nota = "0";
											// if(codigoPersonal.substring(0,1).equals("9")){ 
%>
										<tr class="tr2">
<% 										if(!curso.getEstado().equals("0")){				 	 %>
<%  										if(tipoCurso.equalsIgnoreCase("Elective")){ 	 %> <!-- SI LA MATERIA ES ELECTIVA-->
						  	  				<td style="padding:1px;" title="Type: <%=tipoCurso%> &nbsp; Lecturer: <%=maestroNombre%> Sub-Load ID: <%=alumnoCurso.getCursoCargaId()%>">
						  	  					<div> <!-- data-bs-toggle='tooltip' data-placement='top' title="Record:<%=alumnoCurso.getCursoCargaId()%> | Subject ID: <%=alumnoCurso.getCursoId()%> | Type: <%=tipoCurso%> | Origin Sub.: <%=alumnoCurso.getNombreCurso2()%> | Lecturer: <%=alumnoCurso.getMaestro()%>" -->					  	  						
<%													if( alumnoCurso.getConvalidacion().equals("I") || alumnoCurso.getConvalidacion().equals("S") || convEnTramite){ %>
														<span style="font-size:0.8rem; font-style:italic; color:<%=colorMateria%>;"><strong><%=curso.getNombreCurso()%></strong></span>
<%													}else{ 
														nombreOptativa="-";
														if (mapaCursosDelAlumno.containsKey(alumnoCurso.getCursoCargaId())){
															nombreOptativa = mapaCursosDelAlumno.get(alumnoCurso.getCursoCargaId()).getOptativa();
														}
%>
														<span style="font-size:0.8rem;">
															<%=curso.getNombreCurso()%>
<%
															if(buscaCurso==true && !nombreOptativa.equals("*") && !nombreOptativa.equals("-") && !curso.getNombreCurso().equals(nombreOptativa)){
																out.print("("+nombreOptativa+")");	
															}											
%>
														</span>
<%													}
%>						  	  						
						  	  					</div>
						  	  				</td>
						  	  				<% }else{ %> 										<!-- SI LA MATERIA ES OBLIGATORIA-->
						  	  				<td style="padding:1px;" title="Type: <%=tipoCurso%> &nbsp; Lecturer: <%=maestroNombre%>">
							  	  				<div>
							  	  			<%
													if(alumnoCurso.getConvalidacion().equals("I") || alumnoCurso.getConvalidacion().equals("S") || convEnTramite){
%>
														<span style="font-size:0.8rem; font-style: italic; color:<%=colorMateria%>;"><strong><%=curso.getNombreCurso()%></strong></span>
<%
													}else{
														nombreOptativa="-";
														if (mapaCursosDelAlumno.containsKey(alumnoCurso.getCursoCargaId())){
															nombreOptativa = mapaCursosDelAlumno.get(alumnoCurso.getCursoCargaId()).getOptativa();
														}														

%>
														<span style="font-size:0.8rem;">
															<%=curso.getNombreCurso()%>
<%
														if(buscaCurso==true && !nombreOptativa.equals("*") && !nombreOptativa.equals("-") && !curso.getNombreCurso().equals(nombreOptativa)){														
															out.print("("+nombreOptativa+")");	
														}														
%>
														</span>
<% 													} %>
							  	  				</div>
						  	  				</td>
<% 												}
												String tipoCorto = "-";
												if(mapaTipoCurso.containsKey(curso.getTipoCursoId())){
													tipoCorto = mapaTipoCurso.get(curso.getTipoCursoId()).getCorto();
												}
%>
						  	  				<td style="padding:1px; text-align:center;" title="Type: <%=curso.getTipoCursoId()%>-<%=tipoCorto%>"> 	<!-- TYPE FIELD-->
					  	  	  					<span style="font-size:0.8rem;"><%=tipoCorto%></span>					  
					  	  	  				</td>
					  	  	  				<td style="padding:1px; text-align:center;" data-bs-toggle='tooltip' data-placement='top' title="Mandatory: <%=curso.getObligatorio().equals("S")?"YES":"NO"%>"> <!-- CREDIT VALUE FIELD-->
					  	  	  					<span style="font-size:0.8rem"><%=curso.getCreditos()%></span>
					  	  	  				</td>
						  	  				<td  style="padding:1px; text-align:center; font-size:0.8rem;"> 										<!-- GRADE FIELD-->
					  							<%	
													if(!alumnoCurso.getCargaId().equals(carga.getCargaId()) || (alumnoCurso.getCargaId().equals(carga.getCargaId()) && carga.getBloqueo().equals("0"))){
														if(OKe==true && !curso.getTipoCursoId().equals("3") && buscaCurso==true){
															if(escala.equals("10")){
																out.print(Double.parseDouble(alumnoCurso.getNotaExtra())/10);
															}
															else{
																out.print(alumnoCurso.getNotaExtra());
															}
														}
														else if(OKc==true && !alumnoCurso.getTipoCalId().equals("I") && buscaCurso==true){
															if(alumnoCurso.getTipoCalId().equals("M")){
																out.print("&nbsp;");
															}else if(	alumnoCurso.getTipoCalId().equals("3") 
																	|| 	alumnoCurso.getTipoCalId().equals("4")
																	|| 	alumnoCurso.getTipoCalId().equals("5")
																	|| 	alumnoCurso.getTipoCalId().equals("6")
																	|| 	alumnoCurso.getTipoCalId().equals("7")
																	// || 	alumnoCurso.getTipoCalId().equals("8")
															){
																out.print(mapaTipoCal.get(alumnoCurso.getTipoCalId()).getNombreCorto());
															}else{
																out.print(gradePointNombre);
															}
														}
														else{

															if(alumnoCurso.getTipoCalId().equals("4")) {
																out.print("I");
															}else
															if(alumnoCurso.getTipoCalId().equals("6")) {
																out.print("W");
															}else {
																out.print("&nbsp;");
															}
														}
													}else if(alumnoCurso.getCargaId().equals(carga.getCargaId()) && carga.getBloqueo().equals("1")){
														out.print("");
													}
													%>
						  	  				</td>
						  	  				<td style="padding:1px; text-align:center; font-size:0.8rem;">												<!-- DATE FIELD-->
											<% 
												if(!alumnoCurso.getCargaId().equals(carga.getCargaId()) || (alumnoCurso.getCargaId().equals(carga.getCargaId()) && carga.getBloqueo().equals("0"))){
													if (siTipoCal) {out.print(sFecha);}
												}else if(alumnoCurso.getCargaId().equals(carga.getCargaId()) && carga.getBloqueo().equals("1")){
													out.print("");
												}
											%>
											</td>
						  	  				<td style="padding:1px; text-align:center; font-size:0.8rem;"> 												<!-- STATUS FIELD-->
					  	  						<%	boolean oblig = curso.getEstado().equals("1");
													if(!alumnoCurso.getCargaId().equals(carga.getCargaId()) || (alumnoCurso.getCargaId().equals(carga.getCargaId()) && carga.getBloqueo().equals("0"))){
														if(siTipoCal){
															// ASIGNA EL COLOR AL ESTADO DE LA MATERIA
															String color = "style='color:black'";
															if (alumnoCurso.getTipoCalId().equals("2") || alumnoCurso.getTipoCalId().equals("4") || alumnoCurso.getTipoCalId().equals("7")){
																color = "style='color:red;'";
															}
															if (alumnoCurso.getTipoCalId().equals("3") || alumnoCurso.getTipoCalId().equals("5") || alumnoCurso.getTipoCalId().equals("6")){
																color = "style='color:orange;'";
															}
															if (alumnoCurso.getTipoCalId().equals("8")){
																color = "style='color:green;'";
															}
															if(mapaTipoCal.get(alumnoCurso.getTipoCalId()).getNombreCorto().equals("EN")){
																out.print("<span class='badge bg-primary rounded-pill' style='font-size: 0.8em;' data-bs-toggle='tooltip' data-bs-placement='right' title='"+alumnoCurso.getCursoCargaId()+"'>"+mapaTipoCal.get(alumnoCurso.getTipoCalId()).getNombreCorto()+"</span>");
															}else{
																out.print("<span "+color+" data-bs-toggle='tooltip' data-placement='top' title='"+alumnoCurso.getCursoCargaId()+"'><b>"+mapaTipoCal.get(alumnoCurso.getTipoCalId()).getNombreCorto()+"</b></span>");
															}
															if(completoPorEstado && oblig && !alumnoCurso.getTipoCalId().equals("1")){
																completoPorEstado = false;
															}
														}
														else{
															if(completoPorEstado && oblig) completoPorEstado = false;
														}
													}else if(alumnoCurso.getCargaId().equals(carga.getCargaId()) && carga.getBloqueo().equals("1")){
														out.print("");
													}
		  	  									%>
				  	  						</td>
										<%		}%>
				    					</tr>
				    						
									<%	
										// }										
											
										if(curso.getTipoCursoId().equals("3") && siTipoCal == false){    
											ComponentesAprobadosEnSemestre = false;
										}	
											
											if(contCurso < lisCursosCarrera.size()){
												boolean aunExiste=false;
												for(i=0;i<lisCursosAlumno.size();i++){
													alumnoCurso = (AlumnoCurso) lisCursosAlumno.get(i);
													if(alumnoCurso.getCursoId().equals(curso.getCursoId())){
														aunExiste=true;
														break;
													}
												}
												if(!aunExiste){
													contCurso++;
													if(contCurso < lisCursosCarrera.size()){
														curso = (MapaCurso) lisCursosCarrera.get(contCurso);
													}
												}
											}
											else{
												semestre = "x";
											}
										}
										// fin de while (es el mismo semestre)
				
										if(semestre.equals("x")){
											semestre = curso.getCiclo();
										}
										
										if(mapaCreditos.containsKey(alumPlan.getPlanId()+semestreGuardado)){
											MapaAvance mapaAvance = new MapaAvance();
											if (mapaAvances.containsKey(alumPlan.getPlanId()+semestre+curso.getTipoCursoId())){
												mapaAvance = mapaAvances.get(alumPlan.getPlanId()+semestre+curso.getTipoCursoId());	
											}									
											String electivos = "0";
											if (mapaCreditosCicloyTipo.containsKey(semestre+"7")){
												electivos = mapaCreditosCicloyTipo.get(semestre+"7");
											}
											creditosOb = Float.parseFloat(mapaAvance.getCreditos().trim().equals("")?"0":mapaAvance.getCreditos().trim());
											creditosEl = Float.parseFloat(electivos.trim());
											creditosCiclo = creditosOb + creditosEl;
										}

										semestre = curso.getCiclo();
										if (creditos > 0){
											ponderado = (double)notasxcreditos / creditos;
										}else{
											ponderado = 0;
										}
										pglobal+=notasxcreditos;
										gcreditos+=creditos;
				
										boolean bolFalta = false;
										
										faltanOb 	= creditosOb-creditosACO; if (faltanOb< 0) faltanOb = 0;
										faltanEl 	= creditosEl-creditosACE; if (faltanEl< 0) faltanEl = 0;	
										faltan 		= faltanOb+faltanEl;		
										
										if(faltan>0) bolFalta = true;	
				
										boolean semCompleto = true;
										if(completoPorEstado){
											for(int tipCurso=1; tipCurso<=lisTiposCurso.size(); tipCurso++){
												if(mapaCred.get(tipCurso)!=mapaCred2.get(tipCurso)){
													semCompleto = false;
													break;
												}
											}
										}
										else{
											semCompleto = true;
										}
										
										// Define la Leyenda del avance		
										if(semCompleto && ((bolFalta==false && creditos+creditosConvalidados>0 && cursosAc>0) || (cursosCiclo==cursosAc) || (faltanOb<1&&faltanEl<1) ) && (cursosAc > 0 && creditos+creditosConvalidados > 0) ){
													avance = "Complete!";
										}
										else{
											avance = "Incomplete!";
										}	
				
										// Define el color del texto
										if(semCompleto && (bolFalta==false && creditos+creditosConvalidados > 0) || (cursosCiclo==cursosAc&&creditos<1) ){										
											fondo = "#A200FF";											
											avance = "Complete!";
										}
										else{
											fondo = "#999999";
										}
		
										if(Integer.parseInt(curso.getCiclo()) % 2==1) nuevaFila = true;
										
										/* ====== NUEVO ====== */
										
										float crdAlcanzados = 0;
										if(mapaCreditosPorCiclo.containsKey(semestreGuardado) ){
											crdAlcanzados = Float.parseFloat( mapaCreditosPorCiclo.get(semestreGuardado) );
										}
										
										float crdTotal = 0;
										if(mapaCreditosPorCicloAvance.containsKey(semestreGuardado)){
											crdTotal = Float.parseFloat( mapaCreditosPorCicloAvance.get(semestreGuardado) );
										}
										
										float materiasFaltantes = 0;
										if(mapaCreditoObligatorio.containsKey(semestreGuardado)){
											materiasFaltantes = Float.parseFloat(mapaCreditoObligatorio.get(semestreGuardado));
										}
										
										String fondoFaltante = "#999999";//colorAlum.modificarColor(colorTablas, -10);
										String texto 		 = "COMPLETE";
										if(crdAlcanzados < crdTotal || ComponentesAprobadosEnSemestre==false || materiasFaltantes > 0){
											texto = "INCOMPLETE";
											fondoFaltante = "#999999";
										}	
										
										String corto 	= "";
										String cre 		= "";
										String tipoCurId= "";
										String creFal	= "0";
%>		 								
		 							<tr>
						  				<td style="padding:1px" colspan="7">
					  	  					<font <% if(texto.equals("INCOMPLETE")){out.print("style='color:red; font-size:0.9rem;'");}else{out.print("style='color:"+fondoFaltante+"; font-size:0.9rem;'"); }%>>
						  	  			<%	
						  	  				String colorPonderado = "badge bg-warning rounded-pill";
						  	  				if ( escala.equals("10")) ponderado = ponderado/10;
						  	  				if (  ponderado > 0) colorPonderado =  "badge bg-dark rounded-pill";
						  	  			%>
				  	  						Weighted: <span class="bg <%=colorPonderado%>" style="font-size:1em"><%=getFormato.format(ponderado)%></span>&nbsp;

<%
									for (MapaAvance mapaAvance : lisAvances ){
										if (mapaAvance.getCiclo().equals(semestreGuardado)){
											if (mapaAvances.containsKey(alumPlan.getPlanId()+semestreGuardado+mapaAvance.getTipocursoId())){												
												cre = mapaAvances.get(alumPlan.getPlanId()+semestreGuardado+mapaAvance.getTipocursoId()).getCreditos();
												if(mapaTipoCurso.containsKey(mapaAvance.getTipocursoId())){
													corto = mapaTipoCurso.get(mapaAvance.getTipocursoId()).getCorto();
												}
											}
											if (mapaCreditosAlumno.containsKey(semestreGuardado+mapaAvance.getTipocursoId())){
												creFal = mapaCreditosAlumno.get(semestreGuardado+mapaAvance.getTipocursoId());
											}											
											
											if(!corto.equals("")){
												out.print(corto+"["+creFal+"/"+cre+"] &nbsp;");
											}
										  	
										}
									}
%> 
										  	&nbsp;Missing Crd: <%=crdTotal-crdAlcanzados %>
										  	&nbsp; 
										  	[ <%=texto %> ]
											</font>
										</td>
									</tr>
				  				</table>
				  		<%
				  			}						
						%>
					  		</td>
							<%	if(nuevaFila){%>
				  		</tr>
						<%						
							}							
							// Fin del while principa 
						%>
					</table>
		
					<table class="ayuda PAL 005">
		  		  		<tr>
				    		<td class="text-center" style="font-size:1.2rem;">Grade Point Average:&nbsp;&nbsp;</td>
				    		<td class="text-center" style="font-size:1.2rem;">
				    		<% if(escala.equals("10")){ pglobalF = (pglobal/gcreditos)/10; }else{ pglobalF = pglobal/gcreditos;}%>
				  			<font size="4"><%=getFormato.format(pglobalF)%></font>&nbsp;&nbsp;&nbsp;
				  			<% if(tipoProm.equals("0")||tipoProm.equals("1")){%>
				  			<a class="btn btn-info btn-sm" href="detallesPromedio?PlanId=<%=planActual%>">View calc.</a>
				  			<% }else if(tipoProm.equals("2")){ %>
				  			<a class="btn btn-info btn-sm" href="detallesPromedioLib.jsp?PlanId=<%=planActual%>">View calc.</a>
				  			<% } %>
							</td>
			  			</tr>
				  		<tr>
				  			<td class="text-center" style="font-size:0.8rem;">
				  				Formula: &sum;(Credits*Grade Point Value) / &sum;(Credits)
				  			</td>
				  		</tr>
					</table>
				</td>
			</tr>
			<tr><td align="left">Note: *The convalidated subjects are not taken into account in the calculation of the average.</td></tr>
	</table>		
<%
		}else{%>
	<table style="width:100%; margin:0 auto">
		<tr>
			<td align="center">
				<br><br><br>
				<font size="3">
					<spring:message code="datosAlumno.portal.Nota15"/>:<br><% out.print(cancelaEstudio.getComentario()); %>
				</font>
			</td>
		</tr>
	</table>
<%		}
	}else{
		out.println("This student don't have a study plan");
	}%>	
</div>	
</html>
<script>	
	$(function () {
  		$('[data-bs-toggle="tooltip"]').tooltip();  		
	});
</script>