<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>

<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.alumno.spring.AlumEstado"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.vista.spring.CargaHorario"%>
<%@page import="aca.vista.spring.CargaAcademica"%>
<%@page import="aca.kardex.spring.KrdxCursoAct"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.vista.spring.FinTabla"%>
<%@page import="aca.plan.spring.MapaCursoPre"%>
<%@page import="aca.conva.spring.ConvMateria"%>
<%@page import="aca.carga.spring.CargaPron"%>
<%@page import="aca.carga.spring.CargaGrupo"%>
<%@page import="aca.carga.spring.CargaGrupoCurso"%>
<%@page import="aca.carga.spring.CargaPractica"%>
<%@page import="aca.carga.spring.CargaPracticaAlumno"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.carga.spring.CargaFinanciero"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.admision.spring.AdmEvaluacionNota"%>
<%@page import="aca.attache.spring.AttacheCustomer"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
<script type="text/javascript">
	function recargar(){
		document.frmCarga.submit();
	}

	function HorarioMateria(cursoCargaId){		
		jQuery.get('consultaHorario?CursoCargaId='+cursoCargaId+'&PlanId='+plan, function(data){			
			jQuery("#respuesta").html(data);
			//Se coment� el OK para actualizar la fecha si es que el alumno solicita de nuevo
			//if (data=="OK"){
				document.location.href = "solicitudInscripcion?EventoId="+evento+"&PlanId="+plan;
			//}
		});
	}	
</script>
</head>
<body>
<%
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String codigoAlumno			= (String)session.getAttribute("codigoAlumno");
	String nombreAlumno 		= (String)request.getAttribute("nombreAlumno");
	String periodoId 			= (String)request.getAttribute("periodoId");
	String cargaId	 			= (String)request.getAttribute("cargaId");
	String bloqueId 			= (String)request.getAttribute("bloqueId");
	String periodoNombre		= (String)request.getAttribute("periodoNombre");
	String cargaNombre			= (String)request.getAttribute("cargaNombre");
	String bloqueNombre			= (String)request.getAttribute("bloqueNombre");
	String tipoAlumno			= (String)request.getAttribute("tipoAlumno");
	String exceptoPron			= (String)request.getAttribute("exceptoPron");
	String alumnoEnLinea		= (String)request.getAttribute("alumnoEnLinea"); 
	String mensaje				= (String)request.getAttribute("mensaje"); 
	boolean practicaRegistrada	= (boolean)request.getAttribute("practicaRegistrada");
	
	String planAlumno			= (String)request.getAttribute("plan");
	AlumPersonal personal		= (AlumPersonal)request.getAttribute("personal");
	AlumAcademico academico		= (AlumAcademico)request.getAttribute("academico");
	AlumEstado alumEstado		= (AlumEstado)request.getAttribute("alumEstado");
	MapaPlan mapaPlan			= (MapaPlan)request.getAttribute("mapaPlan");	
	CargaAlumno cargaAlumno		= (CargaAlumno)request.getAttribute("cargaAlumno");	
	CargaFinanciero financiero  = (CargaFinanciero)request.getAttribute("financiero");
	CatPais	pais				= (CatPais)request.getAttribute("pais");
	AttacheCustomer customer 	= (AttacheCustomer)request.getAttribute("customer");
		
	ArrayList<MapaCurso> lisMaterias					= (ArrayList<MapaCurso>)request.getAttribute("lisMaterias");
	ArrayList<String> lisCiclos							= (ArrayList<String>)request.getAttribute("lisCiclos");
	ArrayList<CargaAcademica> lisOferta					= (ArrayList<CargaAcademica>)request.getAttribute("lisOferta");
	ArrayList<KrdxCursoAct> lisCurso					= (ArrayList<KrdxCursoAct>)request.getAttribute("lisCurso");
	ArrayList<MapaCursoPre> lisPre						= (ArrayList<MapaCursoPre>)request.getAttribute("lisPre");
	List<AdmEvaluacionNota> lisReprobadas				= (List<AdmEvaluacionNota>)request.getAttribute("lisReprobadas");
	List<CargaPractica> lisFechas		 				= (List<CargaPractica>)request.getAttribute("lisFechas");
	List<CargaPracticaAlumno> lisPracticas 				= (List<CargaPracticaAlumno>)request.getAttribute("lisPracticas");
		
	HashMap<String,AlumnoCurso> mapaCursosAcreditados	= (HashMap<String,AlumnoCurso>)request.getAttribute("mapaCursosAcreditados");
	HashMap<String,String> mapaMaestros					= (HashMap<String,String>)request.getAttribute("mapaMaestros");
	HashMap<String,CatModalidad> mapaModalidades		= (HashMap<String,CatModalidad>)request.getAttribute("mapaModalidades");
	HashMap<String,FinTabla> mapaCostos 				= (HashMap<String,FinTabla>)request.getAttribute("mapaCostos");
	HashMap<String,KrdxCursoAct> mapaKardexCurso 		= (HashMap<String,KrdxCursoAct>)request.getAttribute("mapaKardexCurso");
	HashMap<String,MapaCurso> mapaCurso 				= (HashMap<String,MapaCurso>)request.getAttribute("mapaCurso");
	HashMap<String,String> mapaHorasMateria				= (HashMap<String,String>)request.getAttribute("mapaHorasMateria");
	HashMap<String,String> mapaSalonesMateria			= (HashMap<String,String>)request.getAttribute("mapaSalonesMateria");
	HashMap<String,String> mapaMateriaCosto				= (HashMap<String,String>)request.getAttribute("mapaMateriaCosto");
	HashMap<String,ConvMateria> mapaMatConv				= (HashMap<String,ConvMateria>)request.getAttribute("mapaMatConv");
	HashMap<String,CargaPron> mapaCargaPron				= (HashMap<String,CargaPron>)request.getAttribute("mapaCargaPron");
	HashMap<String,String> mapaCicloTerminado			= new HashMap<String,String>();
	HashMap<String,CargaGrupo> mapaCargaGrupo			= (HashMap<String,CargaGrupo>)request.getAttribute("mapaCargaGrupo");
	HashMap<String,String> mapaRemediales				= (HashMap<String,String>)request.getAttribute("mapaRemediales");
	HashMap<String,String> mapaPracticas				= (HashMap<String,String>)request.getAttribute("mapaPracticas");
	HashMap<String,CargaGrupoCurso> mapaCargaGrupoCurso	= (HashMap<String,CargaGrupoCurso>)request.getAttribute("mapaCargaGrupoCurso");
	HashMap<String,String> mapaNombresMapaCurso			= (HashMap<String,String>)request.getAttribute("mapaNombresMapaCurso");
	HashMap<String,List<CargaHorario>> mapaListaCargaHorario = (HashMap<String,List<CargaHorario>>)request.getAttribute("mapaListaCargaHorario");
	
	String arregloCiclo[] 		= new String[lisCiclos.size()+1];
	
	String cicloId = (String)session.getAttribute("cicloId");
	
	String chocaHorario			= request.getParameter("ChocaHorario")==null?"-":request.getParameter("ChocaHorario");
	
	String colorBloque			= "";	
	// System.out.println(mapaCursosAcreditados.size());
	
	// EVALUACION DE CICLOS TERMINADOS
	for (String ciclo : lisCiclos){
		boolean completo = true;
		for ( MapaCurso curso : lisMaterias){
			if (curso.getCiclo().equals(ciclo)){
				if (curso.getObligatorio().equals("S") && !mapaCursosAcreditados.containsKey(curso.getCursoId())){
					completo = false;
				}
			}			
		}		
					
		if (completo == true){
			mapaCicloTerminado.put(ciclo, "S");
		}else{
			mapaCicloTerminado.put(ciclo, "N");		
		}
			
	}
	// internado, legales y prefijoCarrera checan en FIN_TABLA
	String internado 		= "0";
	String legales			= "0";
	String prefijoCarrera	= mapaPlan.getVersionId().equals("3")?"PD":""; 
	if ( mapaCostos.containsKey(cargaId+prefijoCarrera+mapaPlan.getCarreraId()+academico.getModalidadId()) ){
		internado = mapaCostos.get(cargaId+prefijoCarrera+mapaPlan.getCarreraId()+academico.getModalidadId()).getInternado();
		legales	= mapaCostos.get(cargaId+prefijoCarrera+mapaPlan.getCarreraId()+academico.getModalidadId()).getLegales();
	}
	
	String modo = "PAU Campus"; 
	if(cargaAlumno.getModo().equals("V")){
		modo = "Home/Virtual";
	}
	
	String estadoDelAlumno = alumEstado.getEstado();
	if (alumEstado.getEstado().equals("M")){
		estadoDelAlumno = "Assigned";
	}else{
		estadoDelAlumno = "Enrrolled";
	}

	NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
	String formattedOpenBal = "$0";
	if(customer.getOpenbal() != null){
		formattedOpenBal = currencyFormatter.format(customer.getOpenbal());
	}
%>
<div class="container-fluid">
	<h2>Subject Loading <small class="text-muted fs-5">( <b><%=codigoAlumno%></b> - <%=nombreAlumno%> | <b>Load:</b>
		<%=cargaId%> <%=cargaNombre%> | <b>Block:</b> <%=bloqueId%> - <%=bloqueNombre%> )</small>
	</h2>
	<div class="alert alert-success">
		<a class="btn btn-primary" href="alumno">Return</a>&nbsp;&nbsp;
		<a class="btn btn-success" href="radiografia?carga=<%=cargaId%>&planAlumno=<%=planAlumno%>"><i class="fas fa-clipboard-list"></i></a>&nbsp;&nbsp;		
		<a class="btn btn-success" href="costos"><i class="fas fa-calculator" ></i></a>&nbsp;&nbsp;
		<a class="btn <%=financiero.getStatus().equals("A")?"btn-success":"btn-warning"%>" href="verCostos" data-bs-toggle="tooltip" title="<%=financiero.getComentario() %>" ><i class="fas fa-file-invoice-dollar"></i></a>&nbsp;&nbsp;
			[ <b><%=academico.getResidenciaId().equals("E")?"Day Student":"Boarding Student"%></b> ]
<%-- <% 		if (academico.getResidenciaId().equals("I")){ %>
			<%=academico.getRequerido().equals("S")?"Permanent ":"Temporary"%> ]&nbsp;&nbsp;
<%		}else{
			out.print("]&nbsp;&nbsp;");
		}
%> --%>
			[ <b>Year:</b> <%= academico.getGrado() %> ] 
			[ <b>Semester:</b> <%= academico.getSemestre() %> ] 
			[ <b><%=academico.getModalidadId().equals("1")?"Face to Face":"Online"%></b> ]
			[ <b><%=personal.getNacionalidad().equals(pais.getPaisId())? pais.getNacionalidad():"Foreigner"%></b> ]		
			[ <b><%=tipoAlumno %></b> ]
			[ <b><%=academico.getClasFin().equals("1")?"SDA":"NOT SDA"%></b> ]
			[ <b>Open Balance:</b> <%=formattedOpenBal%> ]

<!-- Checha los remediales -->
<%
			String remediales	= "";
			String evalCursoId	= "";
			for(AdmEvaluacionNota admEvalNota : lisReprobadas){
				
				if(admEvalNota.getEvaluacionId().equals("1")){
					evalCursoId += "REMPOD20REMM102";
				}
				else if(admEvalNota.getEvaluacionId().equals("3")){
					evalCursoId += "REMPOD20REME101";
				}
				else if(admEvalNota.getEvaluacionId().equals("5")){
					evalCursoId += "REMPOD20REMI103";
				}
				
				if(mapaRemediales.containsKey(evalCursoId)){
					evalCursoId = "";
				}else{
					if(admEvalNota.getEvaluacionId().equals("1")) remediales += "Math remedial, ";
					else if(admEvalNota.getEvaluacionId().equals("3")) remediales += "Spanish, ";
					else if(admEvalNota.getEvaluacionId().equals("5")) remediales += "English, ";
				}			
			}
			
			if(!remediales.equals("")){
				remediales = remediales.substring(0, remediales.length() - 2);
%>
<%-- 			<span class="badge badge-warning"><%=remediales%></span> --%>
<%
			}
%>		
			[ <b><%=modo%></b> ]
			[ <b><%=cargaAlumno.getConfirmar().equals("S")?"Confirmed Load":"NOT Confirmed"%></b> ]
			[ <b title="Financial Clearance"><%=financiero.getStatus().equals("A")?"Cleared":"NOT Cleared"%></b> ]
		</div>	
		<div class="row ">
			<div class="col-md-6">
				<div class="alert alert-info">
					<b>Plan:</b>&nbsp;&nbsp;<%=planAlumno%>&nbsp;&nbsp;[				 
<%
					String colorCiclo = "badge-warning";
					String colorCicloAct = "badge-dark";
					for (String ciclo : lisCiclos){
						if (mapaCicloTerminado.containsKey(ciclo)){
							if (mapaCicloTerminado.get(ciclo).equals("S")) colorCiclo = "bg-primary"; else colorCiclo = "bg-warning";					
						}
						
						if (ciclo.equals(cicloId)){
							out.print("<a href='materias?Ciclo="+ciclo+"'><span class='badge bg-dark "+colorCicloAct+"'>"+ciclo+"</span></a> ");
						}else{
							out.print("<a href='materias?Ciclo="+ciclo+"'><span class='badge "+colorCiclo+"'>"+ciclo+"</span></a> ");
						}					
					}
%>
]
				</div>
				<table class="table table-condensed">
					<tr>
						<th style="text-align:center">Type</th>
						<th>Subject</th>
						<th style="text-align:center">Cr.</th>
						<th style="text-align:center">Grade</th>
						<th>Date</th>				
					</tr>
<%		
				for ( MapaCurso curso : lisMaterias){
					String colorMateria = "bg-info";
					String tipoMateria	= "-";
					if (curso.getCiclo().equals(cicloId)){
						if (mapaCursosAcreditados.containsKey(curso.getCursoId())){						
							tipoMateria 	= "C"; 
						}else{
							if (curso.getObligatorio().equals("S")){							
								tipoMateria = "M";
							}
						}
						String conva = "";
						if (mapaMatConv.containsKey(curso.getCursoId())) {
							conva = "<span class='badge rounded-pill bg-warning' title='Validated Subject'>C</span>";
						}
						String esPractica 	= "";
						if (mapaPracticas.containsKey(curso.getCursoId())){
							esPractica 		= "<span class='badge rounded-pill bg-warning'>P</span>";
						}
%>
					<tr>
						<td style="text-align:center"><span title="<%=tipoMateria.equals("M")?"Mandatory":"Elective"%>" class="badge rounded-pill <%=colorMateria%>"><%=tipoMateria%></span></td>
						<td>
							<b><%=esPractica%> <%=curso.getNombreCurso()%></b>&nbsp;<%=conva%>&nbsp;<a href="similares?CursoId=<%=curso.getCursoId()%>" target="_blank"><i class="far fa-question-circle fa-lg"></i></a>
<%					
					// Oferta de materias para el alumno en el periodo elegido
					for (CargaAcademica carga : lisOferta){
						if(carga.getCursoId().equals(curso.getCursoId())){
							// Nombre del maestro
							String nombreMaestro = carga.getCodigoPersonal();
							String colorMaestro = "bg-warning";
							String tip = "";
							boolean tieneMaestro = false;
							if (mapaMaestros.containsKey(carga.getCodigoPersonal())){
								nombreMaestro = mapaMaestros.get(carga.getCodigoPersonal());
								colorMaestro = "bg-success";
								tieneMaestro = true;
							}else{
								nombreMaestro = "X";
								tip = "Lecturer N/A";
							}
							
							// Nombre de la modalidad
							String nombreModalidad = carga.getModalidadId();
							if (mapaModalidades.containsKey(carga.getModalidadId())){
								nombreModalidad = mapaModalidades.get(carga.getModalidadId()).getNombreModalidad();
							}
							
							// Precio
							String costo 		= "0";
							String porcentaje 	= "0";
							float precio 		= 0;
							if (mapaPlan.getPrecio().equals("N")){
								if (mapaCostos.containsKey(carga.getCargaId()+prefijoCarrera+carga.getCarreraId()+carga.getModalidadId())){
									if(academico.getClasFin().equals("1")){
										costo = mapaCostos.get(carga.getCargaId()+prefijoCarrera+carga.getCarreraId()+carga.getModalidadId()).getAcfe();
										porcentaje = mapaCostos.get(carga.getCargaId()+prefijoCarrera+carga.getCarreraId()+carga.getModalidadId()).getPorCredito();
										precio = Float.valueOf(costo) * Float.valueOf(curso.getCreditos()) * Float.valueOf(porcentaje);
									}else{
										costo = mapaCostos.get(carga.getCargaId()+prefijoCarrera+carga.getCarreraId()+carga.getModalidadId()).getNoAcfe();
										porcentaje = mapaCostos.get(carga.getCargaId()+prefijoCarrera+carga.getCarreraId()+carga.getModalidadId()).getPorCredito();
										precio = Float.valueOf(costo) * Float.valueOf(curso.getCreditos()) * Float.valueOf(porcentaje);
									}
								}
							}else{
								// Busaca el precio de las materias del conservatorio
								if (mapaMateriaCosto.containsKey(carga.getCursoCargaId())){
									precio = Float.valueOf(mapaMateriaCosto.get(carga.getCursoCargaId()));
								} 
							}
							
							// Validar el horario
							int horasRequeridas = 0;
							int horasMateria	= 0;
							int salonMateria	= 0;
							String colorHorario = "badge bg-success";
							String colorSalon 	= "badge bg-success";
							if (mapaCurso.containsKey(carga.getCursoId())){
								horasRequeridas = Integer.parseInt(mapaCurso.get(carga.getCursoId()).getHh());
								if (mapaHorasMateria.containsKey(carga.getCursoCargaId())){
									horasMateria = Integer.parseInt(mapaHorasMateria.get(carga.getCursoCargaId()));
								}
							}							
							if (horasMateria < horasRequeridas && alumnoEnLinea.equals("N")){
								colorHorario = "badge bg-warning";
							}
							
							if ( mapaSalonesMateria.containsKey(carga.getCursoCargaId()) ){
								salonMateria 	= Integer.parseInt(mapaSalonesMateria.get(carga.getCursoCargaId()));
							}
							if (carga.getModo().equals("V")){
								colorSalon = "badge";
							}else if (salonMateria < horasRequeridas && alumnoEnLinea.equals("N")){
								colorSalon = "badge bg-warning";
							}
								
							// Validar prerrequisitos
							String preMateria 	= "0";	
							boolean validaPre 	= true;
							String colorPre		= "badge bg-success";
							String info 		= "";
							
							for (MapaCursoPre pre : lisPre){
								if (pre.getCursoId().equals(carga.getCursoId())){
									preMateria = pre.getCursoIdPre();
									if (!mapaCursosAcreditados.containsKey(preMateria) && !mapaMatConv.containsKey(preMateria)){
										validaPre = false;
									}
									
					        		if (mapaCurso.containsKey(pre.getCursoIdPre())){			        			
							        	info += " ([Cycle: "+mapaCurso.get(pre.getCursoIdPre()).getCiclo()+"] - "+mapaCurso.get(pre.getCursoIdPre()).getCursoClave()+"-"+mapaCurso.get(pre.getCursoIdPre()).getNombreCurso()+") -";
					        		}			        		
								}
							}
					        
				            int cantidadElimina = 2;

					        if(info.endsWith(" -")){
					            int m = Math.max(0, info.length() - cantidadElimina); 
					            info = info.substring(0, info.length()-cantidadElimina);
					        }
					        
							if (!validaPre){
								colorPre 	= "badge bg-warning";
							}
							
							boolean tieneProntuario = true;
							String colorPron = "badge bg-success";
							if (mapaCargaPron.containsKey(carga.getCursoCargaId()) || exceptoPron.contains(planAlumno)){
								tieneProntuario = true;
								colorPron = "badge bg-success";								
							}
							
							String comentario = "";
							if (mapaCargaGrupo.containsKey(carga.getCursoCargaId())){
								comentario = "( "+mapaCargaGrupo.get(carga.getCursoCargaId()).getComentario()+" )";															
							}		
							
							
							List<CargaHorario> listaHorarios = new ArrayList<CargaHorario>();
							
							if(mapaListaCargaHorario.containsKey(carga.getCursoCargaId())){
								listaHorarios = mapaListaCargaHorario.get(carga.getCursoCargaId());
							}
							
							HashMap<String, String> mapaDias = new HashMap<String,String>();
							
							mapaDias.put("1","Sunday");
							mapaDias.put("2","Monday");
							mapaDias.put("3","Tuesday");
							mapaDias.put("4","Wednesday");
							mapaDias.put("5","Thursday");
							mapaDias.put("6","Friday");
							mapaDias.put("7","Saturday");
							
							String dia = "";
							String colorModo = "dark";

							String nombreOptativa = carga.getOptativa();
							
							// Busca si esta la materia tiene un curso de origen
							String materiaOrigen = "";
							String materiaOrigenNombre = "";
							if(mapaCargaGrupoCurso.containsKey(carga.getCursoCargaId())){
								if(!curso.getCursoId().equals(mapaCargaGrupoCurso.get(carga.getCursoCargaId()).getCursoId())){
									materiaOrigen = mapaCargaGrupoCurso.get(carga.getCursoCargaId()).getCursoId();
									materiaOrigenNombre = mapaNombresMapaCurso.get(materiaOrigen);
								}
							}
%>
							<span><br>
<%							
							if ( !mapaCursosAcreditados.containsKey(carga.getCursoId()) && !mapaKardexCurso.containsKey(carga.getCursoId()) 
									&& ( carga.getHorario().equals("N") || horasMateria >= horasRequeridas || alumnoEnLinea.equals("S")) 
									&& ( carga.getSalon().equals("N") || salonMateria >= horasRequeridas || alumnoEnLinea.equals("S") || carga.getModo().equals("V"))
									&& validaPre && tieneMaestro && tieneProntuario ){								
								if(cargaAlumno.getModo().equals("P") || carga.getModo().equals("H") || carga.getModo().equals("V")){
%>
									<a href="grabaMateria?CursoCargaId=<%=carga.getCursoCargaId()%>&CursoId=<%=carga.getCursoId()%>&Ciclo=<%=cicloId%>&Bloque=<%=bloqueId%>"><i class="fas fa-plus-circle fa-1.5x" ></i></a>&nbsp;
<%								
								}else{
									colorModo = "warning";
								}
							} 
%>
<%								
								if(!carga.getModo().equals("R")){
%>
<%								
								}else {
									out.print("M");						
								}
%>
<%-- <%							if(){%> --%>
							<b><%=nombreOptativa.equals("-")?"":"<i>("+nombreOptativa+")</i>"%></b>
							<b><%=materiaOrigenNombre.equals("")?"":"<i class='text-secondary' data-bs-toggle='tooltip' title='"+materiaOrigen+"'>("+materiaOrigenNombre+")</i>"%></b>&nbsp;
<%-- <% 							}		%> --%>
							<b><span class="badge <%=colorMaestro%>" data-bs-toggle="tooltip" title="Lecturer"><%=nombreMaestro%></span></b>&nbsp;
							
							   <span class="badge bg-secondary" data-bs-toggle="tooltip" title="<%=comentario%>"><i class="fas fa-comment fa-sm"></i></span>&nbsp;
							
							<b><span class="badge bg-secondary" data-bs-toggle="tooltip" title="Sub-Load ID"><%=carga.getCursoCargaId()%></span></b>&nbsp;
							
							<b><span class="badge bg-secondary" data-bs-toggle="tooltip" title="Block"><%=carga.getBloqueId()%></span></b>&nbsp;
							
							<b><span class="badge bg-secondary" data-bs-toggle="tooltip" title="<%=mapaModalidades.containsKey(carga.getModalidadId())?mapaModalidades.get(carga.getModalidadId()).getNombreModalidad():"No modality"%>">Modality</span></b>&nbsp;
							
							<b><span class="badge bg-secondary" data-bs-toggle="tooltip" title="Group"><%=carga.getGrupo()%></span></b>&nbsp;&nbsp;
							
							<b>Hours:</b>
<%						
						// for(CargaHorario horario : listaHorarios){
						// 	if(mapaDias.containsKey(horario.getDia())){
						// 		dia = mapaDias.get(horario.getDia());
						// 	}
						// 	out.println(dia+" "+horario.getHoraInicio()+":"+horario.getMinutoInicio()+" - "+horario.getHoraFinal()+":"+horario.getMinutoFinal());
						// 	dia = "";
						// }
%>	
							<b><span class="<%=colorHorario%>"><%=horasMateria%> / <%=horasRequeridas%></span></b>&nbsp;
							<b>Class room: </b>
							<b><span class="<%=colorSalon%>"><%=salonMateria%> / <%=horasRequeridas%></b></span>
							<b>Pre: </b>
							<span title="<%=info%>" class="<%=colorPre%>">
<%
						if(validaPre) {						
%>								
								<i class="fas fa-check"></i>
<%
						}else {
%>								
							<i class="fas fa-times"></i>
<%
						}					
%>			
							</span>&nbsp;
<%
						if (tieneProntuario) 
							out.print("<b>Plan:</b> "+"<span class='"+colorPron+"'><i class='fas fa-check' ></i></span>&nbsp;");
						else
							out.print("<b>Plan:</b> "+"<span class='"+colorPron+"'><i class='fas fa-times' ></i></span>&nbsp;");		
%>
<%-- 						<b>$ </b><%= formato.format(precio)%>&nbsp; --%>
						<br>
						<span class="badge bg-info" data-bs-toggle="tooltip" title="Start Date"><%=carga.getfInicio()%></span>
						<span class="badge bg-warning" data-bs-toggle="tooltip" title="End Date"><%=carga.getfFinal()%></span>
						</span>
<%
						}
					}
					// Busca la nota acreditada de la materia					
					int notaExtra 		= 0;
					String notaMateria	= "0";
					String fechaMateria = "";
					if (mapaCursosAcreditados.containsKey(curso.getCursoId())){
						AlumnoCurso alumnoCurso = mapaCursosAcreditados.get(curso.getCursoId());						
						notaExtra	= Integer.parseInt(alumnoCurso.getNotaExtra().trim());
						if (alumnoCurso.getConvalidacion().equals("S")){
							notaMateria 	= alumnoCurso.getNotaConva();
							fechaMateria 	= alumnoCurso.getFEvaluacion();
						}else if (notaExtra >= 70){ 
							notaMateria 	= alumnoCurso.getNotaExtra();
							fechaMateria	= alumnoCurso.getFExtra();
						}else{
							notaMateria 	= alumnoCurso.getNota();
							fechaMateria 	= alumnoCurso.getFEvaluacion();
						}						
					}
%>					
					</td>
					<td style="text-align:center"><%=curso.getCreditos()%></td>
					<td style="text-align:center"><%=notaMateria%></td>
					<td><%=fechaMateria%></td>				
				</tr>
<%		
				}
			}
%>	
			</table>
		</div>			
		<div class="col-md-6">
			<div class="alert alert-info">
				<b>Assigned Subjects</b> <small class="text-muted fs-6">( Status: <%=estadoDelAlumno %> )
<% 
			if (!chocaHorario.equals("-")){
%>				<span class="label label-warning"><%=chocaHorario%></span><%
			}
%>
			</div>
			<form name="frmSemanas" action="semanas" method="post">
				<table class="table table-condensed">
					<tr>
						<th style="text-align:left">Type</th>
						<th>Subject</th>
						<th style="text-align:center">Cr.</th>
						<th>St.</th>
					</tr>
<%
			float totPrecio 	= 0;
			float totCreditos	= 0;
			int numMaterias 	= 0;
			int row			 	= 0;
			int practicas		= 0;
			int dias			= 0;
			for(KrdxCursoAct kardex : lisCurso){
				row++;
				String colorMateria = "bg-info";
				String tipoMateria	= "-";
				if (kardex.getConfirmar().equals("S")) colorMateria = "bg-info";
				
				String fechasPracticas = "";
				for (CargaPractica practica : lisFechas){
					if (practica.getCursoCargaId().equals(kardex.getCursoCargaId())){
						if (fechasPracticas.length()==0 ) 
							fechasPracticas += aca.util.Fecha.getFechaConMesCorto(practica.getFechaIni())+" / "+aca.util.Fecha.getFechaConMesCorto(practica.getFechaFin());
						else
							fechasPracticas += ", "+aca.util.Fecha.getFechaConMesCorto(practica.getFechaIni())+" / "+aca.util.Fecha.getFechaConMesCorto(practica.getFechaFin());						 
					}					
				}
				
				String esPractica 	= "";				
				if (mapaPracticas.containsKey(kardex.getCursoId())){
					esPractica 		= "<span class='badge bg-warning' title='"+fechasPracticas+"'>P</span>";
					// Deshabilitar captura de fechas pr�cticas
					//practicas++;
				}
				
				// Buscar los datos de la materia
				if (mapaCurso.containsKey(kardex.getCursoId())){
					MapaCurso curso = mapaCurso.get(kardex.getCursoId());
					
					if (mapaCursosAcreditados.containsKey(curso.getCursoId())){							
						tipoMateria 	= "C"; 
					}else{
						if (curso.getObligatorio().equals("S")){								
							tipoMateria = "M";
						}
					}
					
					for (CargaAcademica carga : lisOferta){
						if(carga.getCursoCargaId().equals(kardex.getCursoCargaId())){
							numMaterias++;
							// Precio
							String costo 		= "0";
							String porcentaje 	= "0";
							float precio 		= 0;
							if (mapaPlan.getPrecio().equals("N")){
								if (mapaCostos.containsKey(carga.getCargaId()+prefijoCarrera+carga.getCarreraId()+carga.getModalidadId())){
									if(academico.getClasFin().equals("1")){
										costo = mapaCostos.get(carga.getCargaId()+prefijoCarrera+carga.getCarreraId()+carga.getModalidadId()).getAcfe();
										porcentaje = mapaCostos.get(carga.getCargaId()+prefijoCarrera+carga.getCarreraId()+carga.getModalidadId()).getPorCredito();
										precio = Float.valueOf(costo) * Float.valueOf(curso.getCreditos()) * Float.valueOf(porcentaje);
									}else{
										costo = mapaCostos.get(carga.getCargaId()+prefijoCarrera+carga.getCarreraId()+carga.getModalidadId()).getNoAcfe();
										porcentaje = mapaCostos.get(carga.getCargaId()+prefijoCarrera+carga.getCarreraId()+carga.getModalidadId()).getPorCredito();
										precio = Float.valueOf(costo) * Float.valueOf(curso.getCreditos()) * Float.valueOf(porcentaje);
									}
								}								
							}else{
								// Busaca el precio de las materias del conservatorio
								if (mapaMateriaCosto.containsKey(carga.getCursoCargaId())){
									costo = "0";
									precio = Float.valueOf(mapaMateriaCosto.get(carga.getCursoCargaId())) * (Float.valueOf(kardex.getCantidad()) * Float.valueOf(kardex.getFrecuencia()));
								} 
							}	
							totPrecio += precio;
							totCreditos += Float.valueOf(curso.getCreditos());	
							
							String colorModo = "primary";
							
							if(!cargaAlumno.getModo().equals("P") && !carga.getModo().equals("H") && !carga.getModo().equals("V")){
								colorModo = "secondary";
							}

							String nombreOptativa = carga.getOptativa();

							String materiaOrigenNombre = "";
							String materiaOrigen = "";
							if(mapaCargaGrupoCurso.containsKey(carga.getCursoCargaId())){
								if(!curso.getCursoId().equals(mapaCargaGrupoCurso.get(carga.getCursoCargaId()).getCursoId())){
									materiaOrigen = mapaCargaGrupoCurso.get(carga.getCursoCargaId()).getCursoId();
									materiaOrigenNombre = mapaNombresMapaCurso.get(materiaOrigen);
								}
							}
%>
					<tr>
						<td style="text-align:center"><span class="badge <%=colorMateria%>"><%=tipoMateria%></span></td>
						<td>
							<b><%=esPractica%> <span class="badge" title="<%=carga.getCursoCargaId()%>"><%=curso.getCiclo()%></span>&nbsp;<%=curso.getNombreCurso()%></b>&nbsp;<%=nombreOptativa.equals("-")?"":"<small>("+nombreOptativa+")</small>"%>&nbsp;<%=materiaOrigenNombre.equals("")?"":"<small>("+materiaOrigenNombre+")</small>"%>&nbsp;<b>(</b> <span class="<%=!bloqueId.equals(carga.getBloqueId())?"label label-warning":""%>"> <b>Block: </b><%=carga.getBloqueId()%></span>&nbsp; <b>Group: </b><%=carga.getGrupo()%><b> )</b>&nbsp;
							<b>
								<span class="badge bg-<%=colorModo%>">
<%								
								if(!carga.getModo().equals("R")){
%>
									<%=carga.getModo()%>
<%								
								}else {
									out.print("M");						
								}
%>
								</span>
							</b>
<%							if(mapaPlan.getPrecio().equals("S")){ %>
								<input name="CursoCargaId-<%=row%>" id="CursoCargaId-<%=row%>" type="hidden" value="<%=kardex.getCursoCargaId()%>"/>
								<b>Weeks</b>&nbsp;
								<input name="Semanas-<%=row%>" id="Semanas-<%=row%>" type="text" style="width: 30px" maxlength="2" value="<%=kardex.getCantidad()%>"/>&nbsp;
								<b>Frequency</b>&nbsp;
								<select name="Frecuenacia-<%=row%>" id="Frecuenacia-<%=row%>" style="width: 55px">
									<option value="0.5" <%=kardex.getFrecuencia().equals("0.5")?"selected":""%>>0.5</option>
									<option value="1" <%=kardex.getFrecuencia().equals("1")?"selected":""%>>1</option>
									<option value="1.5" <%=kardex.getFrecuencia().equals("1.5")?"selected":""%>>1.5</option>
									<option value="2" <%=kardex.getFrecuencia().equals("2")?"selected":""%>>2</option>
									<option value="2.5" <%=kardex.getFrecuencia().equals("2.5")?"selected":""%>>2.5</option>
									<option value="3" <%=kardex.getFrecuencia().equals("3")?"selected":""%>>3</option>
								</select>					
								<a class="btn btn-primary btn-sm" href="javascript:document.frmSemanas.submit();"><i class="fas fa-save"></i></a>					
<%							}%>
						</td>
						<td style="text-align:center"><%=curso.getCreditos()%></td>
						<td style="text-align:center">
<%						if (kardex.getTipoCalId().equals("M")){%>
							<a href="borraMateria?CursoCargaId=<%=kardex.getCursoCargaId()%>&BloqueId=<%=bloqueId%>"><i class="fas fa-trash" ></i></a>
<%						}else{ %>
							<span class="badge bg-success"><%= kardex.getTipoCalId().equals("I")?"E":"A"%></span>
<% 						}%>	
						</td>
					</tr>
		<%						
						}			
					}
				}
			}	
%>
					<tr>
						<td style="text-align:center" colspan="2"><b>Total in Credits</b></td>				
						<td style="text-align:center"><%=totCreditos%></td>
						<td style="text-align:center">&nbsp;</td>
						<td style="text-align:center">&nbsp;</td>
					</tr>				
				</table>
<%			boolean exonerar = true;
			if (practicas >=1  && practicaRegistrada == false){%>
			 	<div class="alert alert-danger"><h3>� The students is required to do <%=practicas%> practicums <%=practicas>=2?"s":""%> in PAU !</h3></div>
<%			}%>			 
				<div class="alert alert-info">
<%			if (numMaterias >= 1){ %>				
<!-- 					<a href="resumen" class="btn btn-primary"><i class="fas fa-arrow-circle-right"></i> Continuar</a> -->
					<a href="javascript:Inscribir('<%=planAlumno%>')" class="btn btn-primary"><i class="fas fa-arrow-circle-right"></i> Enroll Student</a>&nbsp;
<%				if (!alumEstado.getEstado().equals("M")){%>					
					<a href="javascript:Desinscribir('<%=planAlumno%>')" class="btn btn-danger"><i class="fas fa-arrow-circle-right"></i> Drop</a>					
<%				}
			}
			if(mapaPlan.getPrecio().equals("S") && lisCurso.size() > 1){ %>
					<a class="btn btn-primary" href="javascript:document.frmSemanas.submit();"><i class="fas fa-save"></i> Weeks</a>
<% 			}		
			if (practicas >=1 ){%>
					<a class="btn btn-success" href="practicas"><i class="fas fa-plus"></i> Enroll Practicum</a>
<%			} %>			 
				</div>
			</form>
			
			<form name="formComentario" action="grabarComentario">	
				<label><strong>Comment:</strong></label>
				<br>
				<textarea name="Comentario" rows="" cols="100"><%=cargaAlumno.getComentario() == null ? "" : cargaAlumno.getComentario()%></textarea><br><br>
				<button type="submit" class="btn btn-primary">Save</button>
<%			if (mensaje.equals("1")){ %>
				<span style="color: green;"><strong>Saved</strong></span>
<%			} else if (mensaje.equals("2")){%>			 
				<span style="color: red;"><strong>Error saving</strong></span>
<%			} %>			 
			</form>
			
			
<%			if (practicaRegistrada){ %>


			<table class="table">
			<thead>
			<tr><td colspan="5"><h3>Practices Dates</h3></td></tr>
			<tr>
				<th>#</th>
				<th>Start</th>
				<th>End</th>
				<th>Comment</th>
				<th>Status</th>
				<th>Days</th>
			</tr>
			</thead>
			<tbody>
<%
				row = 0;
				for (CargaPracticaAlumno practica : lisPracticas){
					row++;
					String estadoPractica = "Active";										
					if (practica.getEstado().equals("L")) estadoPractica = "Release";
					dias = aca.util.Fecha.diasEntreFechas(practica.getFechaIni(), practica.getFechaFin())+1;				
%>
			<tr>
				<td><%=row%></td>
				<td><%=aca.util.Fecha.getFechaConMesCorto(practica.getFechaIni())%></td>
				<td><%=aca.util.Fecha.getFechaConMesCorto(practica.getFechaFin())%></td>
				<td><%=practica.getComentario()%></td>
				<td><%=estadoPractica%></td>				
				<td><%=dias%></td>				
<% 				}%>	
			</tr>			
			</tbody>
			</table>				
<%			}%>
			<div >		
		</div> 
	</div>	
</div>
</body>
</html>
<script>	
	$(function () {
  		$('[data-bs-toggle="tooltip"]').tooltip();
  		$('[data-bs-toggle="popover"]').popover();
	});
	
	function Inscribir(planId){
		if (confirm("This operation enrolls the student's subjects. Do you want to continue?")){
			document.location.href="inscribir?PlanId="+planId;
		}		
	}
	
	function Desinscribir(planId){
		if (confirm("This operation removes the enrolled status of the student's subjects. Do you wan to continue?")){
			document.location.href="desinscribir?PlanId="+planId;
		}		
	}
</script>