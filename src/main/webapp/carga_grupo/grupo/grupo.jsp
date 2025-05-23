<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "java.text.SimpleDateFormat"%>
<%@ page import= "java.util.Date"%>
<%@ page import= "java.text.DateFormat"%>
<%@ page import= "aca.acceso.spring.Acceso"%>
<%@ page import= "aca.carga.spring.CargaBloque"%>
<%@ page import= "aca.vista.spring.CargaAcademica"%>
<%@ page import= "aca.carga.spring.CargaBloque"%>
<%@ page import= "aca.carga.spring.CargaGrupo"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.carga.spring.CargaPractica"%>
<%@page import="aca.objeto.spring.Hora"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.vista.spring.Maestros"%>


<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<head>
<script type="text/javascript">
	function BorrarGrupo( CarreraId, CursoCargaId, planId){
		if(confirm("<spring:message code="aca.JSEliminar"/>")){
	 		document.location="borrarGrupo?CarreraId="+CarreraId+"&CursoCargaId="+CursoCargaId+"&PlanId="+planId;
	 	}
	}
	
	function GrabaBloque(bloqueId){		
		document.frmBloque.submit();
	}
</script>
<link rel="stylesheet" href="../../js/tablesorter/themes/blue/style.css"/>
</head>
<%
	String codigoPersonal	 = (String) session.getAttribute("codigoPersonal");
	String sCargaId 		 = (String) session.getAttribute("cargaId");
	String bloqueId 		 = (String) session.getAttribute("bloqueId");
	String sCarreraId 		 = (String) session.getAttribute("carreraId");
	String planId 		     = (String) session.getAttribute("planId");
	String mensaje			 = request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	boolean esAdmin 		 = Boolean.parseBoolean(session.getAttribute("admin")+"");
	String carreraNombre 	 = (String)request.getAttribute("carreraNombre");
	
	Acceso acceso			 = (Acceso) request.getAttribute("acceso");

	List<Hora> lisHoras		 							= (List<Hora>)request.getAttribute("lisHoras");
	HashMap<String,MapaCurso> mapaCursos				= (HashMap<String,MapaCurso>) request.getAttribute("mapaCursos");
	List<CargaBloque> lisBloques 						= (List<CargaBloque>) request.getAttribute("lisBloques");
	List<CargaAcademica> lisMaterias					= (List<CargaAcademica>) request.getAttribute("lisMaterias");	
	HashMap<String,CargaGrupo> mapaGrupos				= (HashMap<String,CargaGrupo>) request.getAttribute("mapaGrupos");
	HashMap<String,CatModalidad> mapaModalidades		= (HashMap<String,CatModalidad>) request.getAttribute("mapaModalidades");
	HashMap<String,CatCarrera> mapaCarreras				= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String, String> mapaCursosOrigen			= (HashMap<String,String>) request.getAttribute("mapaCursosOrigen");
	HashMap<String, String> mapaCuentas 				= (HashMap<String,String>) request.getAttribute("mapaCuentas");
	HashMap<String, String> mapaHorarios 				= (HashMap<String,String>) request.getAttribute("mapaHorarios");
	HashMap<String, String> mapaAlumnos 				= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
	HashMap<String, String> mapaAsignados 				= (HashMap<String,String>) request.getAttribute("mapaAsignados");
	HashMap<String, String> mapaInscritos 				= (HashMap<String,String>) request.getAttribute("mapaInscritos");
	HashMap<String, String> mapaHorasMateria 			= (HashMap<String,String>) request.getAttribute("mapaHorasMateria");
	HashMap<String, String> mapaSalonesFaltan 			= (HashMap<String,String>) request.getAttribute("mapaSalonesFaltan");
	HashMap<String, String> mapaNumEvalauciones			= (HashMap<String,String>) request.getAttribute("mapaNumEvalauciones");
	HashMap<String, CargaPractica> mapaPracticas		= (HashMap<String,CargaPractica>) request.getAttribute("mapaPracticas");
	HashMap<String, String> mapaMaestros				= (HashMap<String,String>) request.getAttribute("mapaMaestros");
	HashMap<String, String> mapaMateriasUnidas			= (HashMap<String,String>) request.getAttribute("mapaMateriasUnidas");
	HashMap<String, String> mapaUsuariosMaterias		= (HashMap<String,String>) request.getAttribute("mapaUsuariosMaterias");
	
	int duracion 			= 0;
	int i = 0;	
%>
<body>
<div class="container-fluid">
	<h2>Subjects List <small class="text-muted fs-5">( <b>LOAD:</b> <%=sCargaId%>&nbsp; <b>PLAN:</b> <%=planId%>&nbsp; <b>COURSE:</b> <%=carreraNombre%> )</small></h2>
	<form name="frmBloque" action="grupo" method="post">
	<input name = "CarreraId" type="hidden" value="<%=sCarreraId%>">
	<input name = "PlanId" type="hidden" value="<%=planId%>">	
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="elegir"><i class="fas fa-arrow-left"></i></a>&nbsp; &nbsp;
		<a class="btn btn-primary" href="horarioSemestre?PlanId=<%=planId%>"><i class="far fa-calendar-alt"></i> Semester</a>&nbsp;
		<a class="btn btn-primary" href="horarioSalon?PlanId=<%=planId%>"><i class="far fa-calendar-alt"></i> Classroom</a>&nbsp;
		<a class="btn btn-primary" href="alta?CarreraId=<%=sCarreraId%>&PlanId=<%=planId%>"><i class="far fa-plus-square"></i> Subject</a>
		&nbsp; &nbsp;&nbsp;
		Block:&nbsp;
		<select class="form-select" onchange="javascript:GrabaBloque();" name="BloqueId" style="width:200px;">
			<option value='0'>All</option>
<%
	for (CargaBloque bloque : lisBloques){
		if (bloqueId.equals(bloque.getBloqueId())) 
			out.print("<option value ='"+bloque.getBloqueId()+"' selected>["+bloque.getBloqueId()+"] "+bloque.getNombreBloque()+"</option>");
		else
			out.print("<option value ='"+bloque.getBloqueId()+"'>["+bloque.getBloqueId()+"] "+bloque.getNombreBloque()+"</option>");				
	}
%>	
		</select>
		&nbsp;&nbsp;
		Filter:&nbsp;<input id="buscar" type="text" class="form-control search-query" placeholder="Search..." style="width:250px;">
	</div>
<%	if(!mensaje.equals("-")){ %>
	<div class="alert alert-info" role="alert"><%=mensaje%></div>
<% 	} %>
	</form>				
	<table id="tableGrupo" class="table table-condensed">
		<thead>		     
		<tr>
			<th>#</th>
			<th class="text-center">Op.</th>
			<th class="text-center">Sem.</th>
			<th class="text-center"><spring:message code="aca.Modalidad"/></th>
			<th>Group ID</th>
			<th>Block</th>
			<th>Type</th>
			<th><spring:message code="aca.Materia"/></th>						
			<th>Group</th>
			<th><spring:message code="aca.Inicio"/> Date</th>
			<th><spring:message code="aca.Final"/> Date</th>
			<th>Lab.</th>						
			<th>Days</th>
			<th>Credits</th>		
			<th title="Required Hours">RH</th>
			<th title="Group Hours">GH</th>
			<th title="Missing Hours">MH</th>
			<th>Lecturer</th>
			<th title="Evaluation">Eval.</th>
			<th title="Academic Loads"><span class="badge bg-warning rounded-pill">AL</span></th>
			<th title="Enrolled Students"><span class="badge bg-success rounded-pill">ES</span></th>
		</tr>
		</thead>
		<tbody>			
		<%			
			for (CargaAcademica  cargaAca :lisMaterias){
				i++;
				if(cargaAca.getModalidadId().trim().equals(acceso.getModalidad().trim()) || acceso.getModalidad().trim().equals("0")){
					
					String cursoOrigen = "0";
					if(mapaCursosOrigen.containsKey(cargaAca.getCursoCargaId())){
						cursoOrigen = mapaCursosOrigen.get(cargaAca.getCursoCargaId());						
					}		
					
					String inicioPractica 	= "";
					String finPractica		= "";
					boolean existePractica	= false;					
					if(mapaPracticas.containsKey(cargaAca.getCursoCargaId()+"1")){
						inicioPractica		= mapaPracticas.get(cargaAca.getCursoCargaId()+"1").getFechaIni();
						finPractica 		= mapaPracticas.get(cargaAca.getCursoCargaId()+"1").getFechaFin();
						existePractica 		= true;
					}
					
					String esPractica	= "<span class='badge bg-warning rounded-pill'>NO</span>";
					boolean requiereHorario	= false;
					boolean requiereSalon 	= false;
					MapaCurso mapaCurso = new MapaCurso();
					if (mapaCursos.containsKey(cursoOrigen)){
						mapaCurso = mapaCursos.get(cursoOrigen);
						
						if (mapaCurso.getHorario().equals("S")) requiereHorario = true;
						if (mapaCurso.getSalon().equals("S")) requiereSalon = true;
						
						if (mapaCurso.getLaboratorio().equals("S")){
							if (existePractica) 
								esPractica = "<span class='badge bg-success rounded-pill' title='"+inicioPractica+"-"+finPractica+"'>YES</span>"; 
							else
								esPractica = "<span class='badge bg-warning rounded-pill'>YES</span>";
						}						
					}
					
					String cargaUsuario ="-";
					String fechaUsuario = "-";
					String UsarioMateria ="-";
					CargaGrupo cargaGrupo = new CargaGrupo();
					if (mapaGrupos.containsKey(cargaAca.getCursoCargaId()) ){
						cargaGrupo = mapaGrupos.get(cargaAca.getCursoCargaId());
						cargaUsuario = cargaGrupo.getUsuario();
						fechaUsuario = cargaGrupo.getFecha();
						UsarioMateria=	mapaUsuariosMaterias.get(cargaGrupo.getUsuario());

					}
				
					String carreraOrigen = "-";
					if (mapaCarreras.containsKey(cargaGrupo.getCarreraId())){
						carreraOrigen = mapaCarreras.get(cargaGrupo.getCarreraId()).getNombreCarrera();
				
					}
					
					String modalidadNombre = "-";
					if (mapaModalidades.containsKey(cargaAca.getModalidadId())){
						modalidadNombre = mapaModalidades.get(cargaAca.getModalidadId()).getNombreModalidad();
					
					}
					
					String horarioId = "0";
					if (mapaHorarios.containsKey(cargaAca.getCursoCargaId())){
						horarioId = mapaHorarios.get(cargaAca.getCursoCargaId());
					}
					
					String numAlumnos = "0";					
					if (mapaAlumnos.containsKey(cargaAca.getCursoCargaId())){
						numAlumnos = mapaAlumnos.get(cargaAca.getCursoCargaId());
					} 
					
					String numAsignados = "<span class='badge bg-secondary rounded-pill'>0</span>";
					if (mapaAsignados.containsKey(cargaAca.getCursoCargaId())){
						numAsignados = "<span class='badge bg-dark rounded-pill'>"+mapaAsignados.get(cargaAca.getCursoCargaId())+"</span>";
					} 
					
					String numInscritos = "0";
					if (mapaInscritos.containsKey(cargaAca.getCursoCargaId())){
						numInscritos = mapaInscritos.get(cargaAca.getCursoCargaId());
					}
					
					String horasMateria = "0";
					if (mapaHorasMateria.containsKey(cargaAca.getCursoCargaId())){
						horasMateria 	=  mapaHorasMateria.get(cargaAca.getCursoCargaId());
					}

					String evaluaciones = "0";
					if (mapaNumEvalauciones.containsKey(cargaAca.getCursoCargaId())){
						evaluaciones 	=  mapaNumEvalauciones.get(cargaAca.getCursoCargaId());
					}
					
					// Determina el numero de dias de duracion de la materia
					duracion = 0;
					if (cargaAca.getfInicio()!=null && cargaAca.getfFinal() != null)
						duracion = aca.util.Fecha.getDiasEntreFechas(cargaAca.getfInicio(),cargaAca.getfFinal());
					
					String colorUnidas = "style='color:gray'";
					if (mapaMateriasUnidas.containsKey(cargaAca.getCursoCargaId())){						
						if ( Integer.parseInt(mapaMateriasUnidas.get(cargaAca.getCursoCargaId())) >= 2){
							colorUnidas = "style='color:black'";
						}
					}
					
					%>  
					<tr class="tr2"> 
						<td class="align-content-center"><%=i%></td>
					<% 	if(cargaAca.getOrigen().equals("O")){ %>
							<td class="align-content-center">
								<div class="d-flex">
								<a href="javascript:location.href='fechas?CursoCargaId=<%=cargaAca.getCursoCargaId()%>&PlanId=<%=planId%>';" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>
					<%		if (numAlumnos.equals("0")){ 	
								if (evaluaciones.equals("0")){ %>			
									&nbsp;
									<a href="javascript:BorrarGrupo('<%=cargaAca.getCarreraId()%>','<%=cargaAca.getCursoCargaId()%>','<%=planId %>');" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash" style="color:red"></i></a>
					<%			}			
							} %>
								</div>			
							</td>
					<%  }else{ %>
							<td>&nbsp;</td>
					<%	}
						String icono 	= "";
						if(mapaCuentas.containsKey(sCarreraId+"@@"+cargaAca.getModalidadId())){
							icono = " / <i class='icon-thumbs-up' title='Tabla Financiera'></i>";
						}
					%>
						<td class="align-content-center text-center"><%=cargaAca.getCiclo()%></td>
						<td class="align-content-center"><%=modalidadNombre%></td>
						<td data-bs-toggle="tooltip" title="<%=UsarioMateria==null?"":UsarioMateria%>-<%=fechaUsuario%>" class="align-content-center"><%=cargaAca.getOrigen().equals("O")?"<span class='label label-dark'>":"<span class='label label-warning'>"%>
						<%=cargaAca.getCursoCargaId().split("-")[1]%></span>
						</td>
						<td title="<%=cargaAca.getCursoCargaId()%>" class="align-content-center text-center">
						<%  // Permite cambiar el bloque si no hay alumnos inscritos y la materia es de base o el usuario es administrador..  
							if((numInscritos.equals("0") && cargaAca.getOrigen().equals("O")) ||(esAdmin && cargaAca.getOrigen().equals("O")) ){ %>			
								<a href="cambiaBloque?CarreraId=<%=sCarreraId %>&PlanId=<%=planId%>&CursoCargaId=<%=cargaAca.getCursoCargaId()%>"><span class="badge bg-dark rounded-pill"><%=cargaAca.getBloqueId()%></span></a>
						<%  }else{
								out.print("<span class='badge bg-warning rounded-pill'>"+cargaAca.getBloqueId()+"</span>");
							} %>

						</td>
						<td class="align-content-center text-center">
<%
							String modo 		= "";
							String color		= "";
							String tituloModo	= "";
							if(cargaGrupo.getModo().equals("P")){
								modo  = "F2F";
								color = "bg-info";
								tituloModo = "Face to Face";
							}else if(cargaGrupo.getModo().equals("V")){
								modo = "OL";
								color = "bg-success";
								tituloModo = "Online";
							}else if(cargaGrupo.getModo().equals("H")){
								modo = "H";
								color = "bg-warning";
								tituloModo = "Hybrid";
							}else{
								modo = "M";
								color = "bg-dark";
								tituloModo = "Mixed";
							}
%>
							<span class="badge <%=color%> rounded-pill" title="<%=tituloModo%>"><%=modo%></span>
						</td>
						<td class="d-flex-inline" <% if(!cargaAca.getOrigen().equals("O")){%> title="Origin: <%=cargaGrupo.getCarreraId()%>-<%=carreraOrigen%>-<%=mapaCurso.getNombreCurso()%>-<%=mapaCurso.getPlanId()%>"<% } %>>							
							<div><%=cargaAca.getNombreCurso()%></div>
							<div>	
						<% 	
							// Muestra la optativa elegida o el comentario de la materia 
							if( ( mapaCurso.getTipoCursoId().equals("2") || mapaCurso.getTipoCursoId().equals("7")) ){
								out.print("<span data-bs-toggle='tooltip' title='Elective Sub.' class='badge bg-dark py-1 rounded-pill'>"+cargaAca.getOptativa()+"</span>");
							}else if (cargaGrupo.getComentario()!=null && !cargaGrupo.getComentario().equals(" ") && !cargaGrupo.getComentario().equals("")){
								out.print("<span data-bs-toggle='tooltip' title='Subject Comment' class='badge bg-secondary py-1 rounded-pill'>"+cargaGrupo.getComentario()+"</span>");
							}
 						%> 
<%-- 					  		&nbsp;<span class="badge <%=color%>" title="<%=tituloModo%>"><%=modo%></span> -&nbsp; --%>
 						<%	
							String profesor	 		= cargaAca.getNombre();
							String colorProfesor	= "style='color:#fd7e14'";
							if (cargaAca.getNombre().equals("VACIO")){
								profesor = "<span class='label label-warning text-warning'><b>EMPTY</b></span>";
							}else{
								colorProfesor	= "style='color:black'";
								if (mapaMaestros.containsKey(cargaAca.getCodigoPersonal())){
									profesor = mapaMaestros.get(cargaAca.getCodigoPersonal());
								}
							}
							String tituloHorario 	= "Does not have schedule";
							String imgHorario 		= "style='color:gray'";
							
							int horasRequeridas		= Integer.parseInt(mapaCurso.getHh());
							int faltanHoras			= horasRequeridas-Integer.parseInt(horasMateria);
							if (requiereHorario){
								if( faltanHoras <= 0){
									imgHorario = "style='color:black'";
									tituloHorario = "Schedule already assigned ("+horasRequeridas+"/"+horasMateria+")";
								}else{
									if(faltanHoras > 0){								
										tituloHorario 	= "Incomplete Schedule, (requires "+horasRequeridas+" hours, has "+horasMateria+", you are missing "+faltanHoras+") hours";
										imgHorario 		= "style='color:#fd7e14'";
									}
								}
							}else{
								tituloHorario 	= "Does not require Schedule";
							}
							
							String tituloSalon 			= "No Classroom assigned";
							String imgSalon 			= "style='color:#fd7e14'";
							String faltanHorasSalon 	= "0";
							if (mapaSalonesFaltan.containsKey(cargaAca.getCursoCargaId())){								
								faltanHorasSalon = mapaSalonesFaltan.get(cargaAca.getCursoCargaId());							
							}
							if(modo.equals("V") || mapaCurso.getSalon().equals("N")){
								imgSalon 		= "style='color:gray'";
								tituloSalon 	= "Does not require Classroom";
							}else if (Integer.parseInt(horasMateria) > 0 && Integer.parseInt(faltanHorasSalon) <= 0){
								tituloSalon 	= "Classroom Assigned";
								imgSalon 		= "style='color:black'";	
							}
							
							String horarioMateria 	= "";
							String horaInicio 		= "-";
							String horaFinal 		= "-";
							String nombreDia 		= "-";							
							for(Hora hora : lisHoras){
								if(hora.getCursoCargaId().equals(cargaAca.getCursoCargaId())){
									if(hora.getDia().equals("2")){
										nombreDia = "Monday";					
									}else if(hora.getDia().equals("3")){
										nombreDia = "Tuesday";					
									}else if(hora.getDia().equals("4")){
										nombreDia = "Wednesday";					
									}else if(hora.getDia().equals("5")){
										nombreDia = "Thursday";					
									}else if(hora.getDia().equals("6")){
										nombreDia = "Friday";					
									}else if(hora.getDia().equals("7")){
										nombreDia = "Saturday";					
									}else{
										nombreDia = "Sunday";					
									}
									horaInicio 		= hora.getInicio().substring(1, 3)+":"+hora.getInicio().substring(3, 5);
									horaFinal 		= hora.getFin().substring(1, 3)+":"+hora.getFin().substring(3, 5);				
									horarioMateria 	+= nombreDia+"-->"+horaInicio+"-"+horaFinal+" ";		
								}
							}
							
							if(cargaAca.getOrigen().equals("O") && !acceso.getModalidad().trim().equals("7")){
						%>
								<i class="fas fa-user-alt fa-1x" <%=colorProfesor%> onclick="location.href='maestro?CursoCargaId=<%=cargaAca.getCursoCargaId()%>&PlanId=<%=planId%>&bloque=<%=cargaAca.getBloqueId()%>&Materia=<%=cargaAca.getNombreCurso() %>';" data-bs-toggle="tooltip" title="Assign Lecturer to Subject"></i>
								<i class="fas fa-calendar-alt fa-1x" <%=imgHorario%> width="18px" onclick="location.href='horariogrupo?Maestro=<%=cargaAca.getCodigoPersonal()%>&CarreraId=<%=sCarreraId%>&PlanId=<%=planId %>&CursoCargaId=<%=cargaAca.getCursoCargaId()%>&CargaId=<%=cargaAca.getCargaId()%>&CursoId=<%=cargaAca.getCursoId()%>&Modifica=S&HorarioOrigen=<%=cargaAca.getOrigen()%>';" data-bs-toggle="tooltip" title="<%=horarioMateria%>"></i>
								<i class="fas fa-home fa-1x" <%=imgSalon%> width="18px" onclick="location.href='salon?CarreraId=<%=sCarreraId%>&PlanId=<%=planId %>&CursoCargaId=<%=cargaAca.getCursoCargaId()%>&CursoId=<%=cargaAca.getCursoId()%>&Bloque=<%=cargaAca.getBloqueId()%>';" data-bs-toggle="tooltip" title="<%=tituloSalon%>"></i>								
								<i class="fas fa-handshake fa-1x" <%=colorUnidas%> onclick="location.href='equivalente?CarreraId=<%=sCarreraId%>&PlanId=<%=planId %>&CursoCargaId=<%=cargaAca.getCursoCargaId()%>&Materia=<%=cargaAca.getNombreCurso()%>';" data-bs-toggle="tooltip" title="Join Classes"></i>
					  	<%	}else{%>
					  			<i class="fas fa-calendar-alt fa-1x" <%=imgHorario%> width="18px" onclick="location.href='horariogrupo?CarreraId=<%=sCarreraId%>&PlanId=<%=planId %>&CursoCargaId=<%=cargaAca.getCursoCargaId()%>&CursoId=<%=cargaAca.getCursoId()%>&Modifica=N&HorarioOrigen=<%=cargaAca.getOrigen()%>';" data-bs-toggle="tooltip" title="<%=horarioMateria%>"></i>
					  			<img src="../../imagenes/grupo.jpg" width="18px" onclick="location.href='cambiaGrupo?CarreraId=<%=sCarreraId%>&PlanId=<%=planId %>&CursoCargaId=<%=cargaAca.getCursoCargaId()%>&CursoId=<%=cargaAca.getCursoId()%>';" data-bs-toggle="tooltip" title="<%=tituloHorario%>">
					  			<i class="fas fa-home fa-1x" <%=imgSalon%> width="18px"></i>
				  		<%	} %>	
							</div>
						</td>												
						<td class="text-center align-content-center"><%=cargaAca.getGrupo()%><%=cargaAca.getGrupoHorario().equals("XX")?"":", "+cargaAca.getGrupoHorario()%></td>
						<td class="text-center align-content-center"><%=cargaAca.getfInicio().split("/")[0]+"/"+cargaAca.getfInicio().split("/")[1]+"/"+cargaAca.getfInicio().split("/")[2].substring(2,4)%></td>
						<td class="text-center align-content-center"><%=cargaAca.getfFinal().split("/")[0]+"/"+cargaAca.getfFinal().split("/")[1]+"/"+cargaAca.getfFinal().split("/")[2].substring(2,4)%></td>
						<td class="text-center align-content-center">
						<%	if (esPractica.contains("SI")){%>
							<a href="altaPracticas?CursoCargaId=<%=cargaAca.getCursoCargaId()%>"><%=esPractica%></a>
						<%
							}else{
								out.print(esPractica);
						  	}
						%>
						</td>						
						<td class="text-right align-content-center"><%=duracion%></td>
						<td class="text-right align-content-center"><%=cargaAca.getCreditos()%></td>
<%-- 						<td align="center"><%=cargaAca.getHt()%>/<%=cargaAca.getHp()%>/<%=cargaAca.getHi()%></td> --%>
<%-- 						<td align="center"><%=Integer.parseInt(cargaAca.getHt().trim())+Integer.parseInt(cargaAca.getHp().trim())+Integer.parseInt(cargaAca.getHi().trim())%></td> --%>
						<td class="text-right align-content-center"><%=mapaCurso.getHh()%></td>
						<td class="text-right align-content-center"><%=horasMateria%></td>
						<td class="text-right align-content-center"><%=faltanHoras>0?"<span class='label label-warning'>":"<span class='label label-dark'>"%><%=faltanHoras%></span></td>
						<td class="text-left align-content-center" title="<%=cargaAca.getCodigoPersonal()%>"><%=profesor%></td>
						<td class="text-left align-content-center"><%=evaluaciones%></td>
						<td class="text-center align-content-center">
							<a href="lista_alumnos?CarreraId=<%=sCarreraId%>&PlanId=<%=planId%>&CursoId=<%=cargaAca.getCursoId()%>&CursoCargaId=<%=cargaAca.getCursoCargaId()%>">
								<%=numAsignados%>						
							</a>
						</td>
						<td class="text-center align-content-center">
							<a href="lista_alumnos?CarreraId=<%=sCarreraId%>&PlanId=<%=planId%>&CursoCargaId=<%=cargaAca.getCursoCargaId()%>&CursoId=<%=cargaAca.getCursoId()%>">
								<%=numInscritos.equals("0")?"<span class='badge bg-secondary rounded-pill'>0</span>":"<span class='badge bg-dark rounded-pill'>"+numInscritos+"</span>"%>
							</a>						
						</td>
					</tr>
		<%		}	
			} //fin del for %>
		<tbody>		
		<tr>
			<td colspan="20"><b>Sem.</b> = Semester - <b>Grp.</b> = Group</td>
		</tr>					
	</table>	
	<br>
</div>
</body>
<script type="text/javascript" src="../../js/search.js"></script>
<script src="../../js/tablesorter/jquery.tablesorter.js"></script>
<script type="text/javascript">
	jQuery('#buscar').focus().search({table:jQuery("#tableGrupo")});
	jQuery('#tableGrupo').tablesorter();	
	
	var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
	var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
	 return new bootstrap.Tooltip(tooltipTriggerEl)
	})
</script>