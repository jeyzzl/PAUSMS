<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.carga.spring.CargaPermisoPlan"%>
<%@ page import= "aca.acceso.spring.Acceso"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	String codigoPersonal			= (String) session.getAttribute("codigoPersonal");
	String periodoId				= (String) request.getAttribute("periodoId");
	String cargaId					= (String) request.getAttribute("cargaId");
	Acceso acceso					= (Acceso) request.getAttribute("acceso");
	
	List<CatPeriodo> lisPeriodos 				= (List<CatPeriodo>) request.getAttribute("lisPeriodos");	
	List<Carga> lisCargas 						= (List<Carga>) request.getAttribute("lisCargas");	
	List<CargaPermisoPlan> lisPermisos 			= (List<CargaPermisoPlan>) request.getAttribute("lisPermisos");
	List<String> lisPlanesPorUsuario 			= (List<String>) request.getAttribute("lisPlanesPorUsuario");
	
	HashMap<String,MapaPlan> mapPlanes 			= (HashMap<String,MapaPlan>) request.getAttribute("mapPlanes");
	HashMap<String, String> mapGrupos 			= (HashMap<String, String>) request.getAttribute("mapGrupos");	
	HashMap<String, String> mapMaestros			= (HashMap<String, String>) request.getAttribute("mapMaestros");
	HashMap<String, String> mapHorarios			= (HashMap<String, String>) request.getAttribute("mapHorarios");
	HashMap<String, String> mapSalones			= (HashMap<String, String>) request.getAttribute("mapSalones");
	HashMap<String, CatFacultad> mapFacultades	= (HashMap<String, CatFacultad>) request.getAttribute("mapFacultades");
	HashMap<String, CatCarrera> mapCarreras		= (HashMap<String, CatCarrera>) request.getAttribute("mapCarreras");
	HashMap<String, String> mapCursosPorCiclo	= (HashMap<String, String>) request.getAttribute("mapCursosPorCiclo");	
	HashMap<String, String> mapaRequiereSalon	= (HashMap<String, String>) request.getAttribute("mapaRequiereSalon");
	HashMap<String, String> mapaRequiereHorario	= (HashMap<String, String>) request.getAttribute("mapaRequiereHorario");
	HashMap<String, String> mapaModos			= (HashMap<String, String>) request.getAttribute("mapaModos");	
	HashMap<String, String> mapaAsignados		= (HashMap<String, String>) request.getAttribute("mapaAsignados");
	HashMap<String, String> mapaInscritos		= (HashMap<String, String>) request.getAttribute("mapaInscritos");
	
	String facTemp			= "X";
	String facultadId 		= "x";	
	
	int facGrupos			= 0;
	int facMaestros			= 0;
	int facHorarios			= 0;
	int facSalones			= 0;
	int facAlumnos			= 0;
	int totGrupos 			= 0;
	int totMaestros 		= 0;
	int totHorarios			= 0;
	int totSalones			= 0;
	int totAlumnos			= 0;
	
	int facPresencial		= 0;
	int facMixta			= 0;
	int facHibrida			= 0;
	int facVirtual			= 0;
	int totPresencial		= 0;
	int totMixta			= 0;
	int totHibrida			= 0;
	int totVirtual			= 0;
	
	int facAsignados		= 0;
	int facInscritos		= 0;
	int totAsignados		= 0;
	int totInscritos		= 0;
	int i = 0;
	
	String colorBien 		= "style='background-color:#00b300; color:black;'";
	String colorMal 		= "style='background-color:#e60000; color:black';";
	String color23 			= "style='background-color:#ffff00 ; color:black';";
	
	boolean encuentraCarga	= false;
%>		
<div class="container-fluid">
	<h2><spring:message code="cargasGrupos.grupo.Titulo"/></h2>	
	<form name="forma" action="elegir" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<b><spring:message code="aca.Periodo"/>:</b>
		<select name="PeriodoId" class="form-select" onchange="javascript:document.forma.submit();" style="width:140px;">
	<%	for(CatPeriodo periodo : lisPeriodos){%>
		<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
	<%	}%>
    	</select>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<b><spring:message code="aca.Carga"/>: </b>
		<select name="CargaId" class="form-select" onchange="javascript:document.forma.submit();" style="width:350px;">
			<option <%=cargaId.equals("000000")?" Selected":""%> value="000000"><spring:message code="cargasGrupos.grupo.EligeCarga"/></option>
	<%	for(Carga carga : lisCargas){
			if (carga.getCargaId().equals(cargaId)) encuentraCarga = true;
	%>
			<option <%if(cargaId.equals(carga.getCargaId()))out.print(" Selected ");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
	<%	}%>
		</select>		
		&nbsp;&nbsp;
		<spring:message code="aca.Filtro"/>:&nbsp;<input id="buscar" type="text" class="form-control search-query" placeholder="<spring:message code="aca.Buscar"/>" style="width:120px;">
	</div>	
	</form>	
	<table class="table table-sm table-bordered table-striped" id="table">
<%
	facultadId = "X"; i=0;
	String nombreFacultad 	= "-";
	String facAnterior		= "-";
	for(CargaPermisoPlan permiso : lisPermisos){
		
		// Verifica si tiene privilegios sobre la carrera		
		if((lisPlanesPorUsuario.contains(permiso.getPlanId())) || (acceso.getAdministrador().equals("S"))){
			
			String nombreCarrera 	= "-";
			String colorPlan 		= "";
			if (mapPlanes.containsKey(permiso.getPlanId())){
				nombreCarrera = mapPlanes.get(permiso.getPlanId()).getNombrePlan();
				if (mapPlanes.get(permiso.getPlanId()).getEstado().equals("A")) colorPlan = "bg-info";
				if (mapPlanes.get(permiso.getPlanId()).getEstado().equals("V")) colorPlan = "bg-success";
				if (mapPlanes.get(permiso.getPlanId()).getEstado().equals("I")) colorPlan = "bg-warning";
			}		
			
			if (mapCarreras.containsKey(permiso.getCarreraId())){
				facTemp 		= mapCarreras.get(permiso.getCarreraId()).getFacultadId();				
			}			
			
			if(!facTemp.equals(facultadId)){
				
				boolean mostrar	= !facultadId.equals("X");
				facultadId 		= facTemp;
				facAnterior 	= nombreFacultad; 
				if (mapFacultades.containsKey(facultadId)){
					nombreFacultad = mapFacultades.get(facultadId).getNombreFacultad();
				}
				if (mostrar){
%>
			<tfooter>
			<tr>
				<th colspan="17" class="right"><spring:message code="cargasGrupos.grupo.TotalesDe"/> <%=facAnterior%></th>
				<th class="right"><%=facGrupos%></th>
				<th class="right"><%=facMaestros%></th>
				<th class="right"><%=facHorarios%></th>
				<th class="right"><%=facSalones%></th>
				<th class="right"><%=facAlumnos%></th>
				<th class="right"><%=facPresencial%></th>
				<!--<th class="right">facMixta</th>-->
				<!--<th class="right">facHibrida</th>-->
				<th class="right"><%=facVirtual%></th>
				<th class="right"><%=facAsignados%></th>
				<th class="right"><%=facInscritos%></th>
			</tr>	
			</tfooter>
<%				}
				facGrupos		= 0;
				facMaestros 	= 0;
				facHorarios		= 0;
				facSalones		= 0;
				facAlumnos		= 0;
				facPresencial	= 0;
				facMixta		= 0;
				facHibrida		= 0;
				facVirtual		= 0;
				facAsignados	= 0;
				facInscritos	= 0;
%>
			<thead>							
			<tr> 
				<th colspan="4"><%=nombreFacultad%></th>
				<th colspan="13" class="text-center"><spring:message code="cargasGrupos.grupo.GrupoCreado"/></th>
				<th colspan="4" class="text-center">Groups</th>
				<th colspan="2" class="text-center">Attendance</th>
				<th colspan="3" class="text-center"><spring:message code="aca.Alumnos"/></th>
			</tr>
			<tr> 
				<th width="3%"><spring:message code="aca.Op"/></th>
				<th width="4%"><spring:message code="aca.Plan"/></th>				
				<th width="20%">Plan <spring:message code="aca.Nombre"/></th>
				<th width="4%"><spring:message code="aca.Carrera"/> ID</th>
				<th width="1%" class="text-center">1</th>
				<th width="1%" class="text-center">2</th>
				<th width="1%" class="text-center">3</th>
				<th width="1%" class="text-center">4</th>
				<th width="1%" class="text-center">5</th>
				<th width="1%" class="text-center">6</th>
				<th width="1%" class="text-center">7</th>
				<th width="1%" class="text-center">8</th>
				<th width="1%" class="text-center">9</th>
				<th width="1%" class="text-center">10</th>
				<th width="1%" class="text-center">11</th>
				<th width="1%" class="text-center">12</th>
				<th width="2%" class="text-center">Total</th>				
				<th width="3%" class="text-center" title="Total Groups"><img src="../../imagenes/grupo.jpg" width="33px"></th>
				<th width="3%" class="text-center" title="Total Lecturers"><img src="../../imagenes/maestro2.png" width="28px"></i></th>
				<th width="3%" class="text-center" title="Assigned Timetables"><i class="fas fa-calendar-alt fa-2x" ></i></th>							
				<th width="3%" class="text-center" title="Assigned Classrooms"><i class="fas fa-home fa-2x" ></i></th>								
				<th width="2%" class="text-center" title="Face to Face Attendance"><span class="badge bg-dark">F2F</span></th>
				<!--<th width="2%" class="text-center" title="Mixed Attendance"><span class="badge bg-dark">M</span></i></th>-->
				<!--<th width="2%" class="text-center" title="Asistencia H�brida"><span class="badge bg-dark">H</span></th>-->
				<th width="2%" class="text-center" title="Online Attendance"><span class="badge bg-dark"><i class="fas fa-globe"></i></span></th>				
				<th width="2%" class="text-center" title="Students with Subject Loads"><span class="badge bg-warning">SL</span></th>
				<th width="2%" class="text-center" title="Enrolled Students"><span class="badge bg-success">ES</span></th>
				<th width="3%" class="text-center" title="Total Students"><i class="fas fa-user-graduate fa-lg"></i></th>
			</tr>
			</thead>
<%			}%>
				<tr>
				<%	
					int numGrupos		= 0;
					int numMaestros		= 0;
					int numHorarios 	= 0;
					int numSalones		= 0;
					int numPresencial	= 0;
					int numMixta		= 0;
					int numHibrida		= 0;
					int numVirtual		= 0;
					
					if(mapGrupos.containsKey(permiso.getPlanId())){
						numGrupos 		= Integer.parseInt(mapGrupos.get(permiso.getPlanId()));
					}
					
					if(mapMaestros.containsKey(permiso.getPlanId())){
						numMaestros 	= Integer.parseInt(mapMaestros.get(permiso.getPlanId()));
					}
					
					if(mapHorarios.containsKey(permiso.getPlanId())){
						numHorarios 	= Integer.parseInt(mapHorarios.get(permiso.getPlanId()));
					}
					
					if(mapSalones.containsKey(permiso.getPlanId())){
						numSalones 		= Integer.parseInt(mapSalones.get(permiso.getPlanId()));
					}
					
					if (mapaModos.containsKey(permiso.getPlanId()+"P")){
						numPresencial 	= Integer.parseInt(mapaModos.get(permiso.getPlanId()+"P"));
					}
					if (mapaModos.containsKey(permiso.getPlanId()+"R")){
						numMixta 		= Integer.parseInt(mapaModos.get(permiso.getPlanId()+"R"));
					}
					if (mapaModos.containsKey(permiso.getPlanId()+"H")){
						numHibrida		= Integer.parseInt(mapaModos.get(permiso.getPlanId()+"H"));
					}
					if (mapaModos.containsKey(permiso.getPlanId()+"V")){
						numVirtual 		= Integer.parseInt(mapaModos.get(permiso.getPlanId()+"V"));
					}
					
					int numAsignados = 0;
					if(mapaAsignados.containsKey(permiso.getPlanId())){
						numAsignados = Integer.parseInt(mapaAsignados.get(permiso.getPlanId()));
					}
					
					int numInscritos = 0;
					if(mapaInscritos.containsKey(permiso.getPlanId())){
						numInscritos = Integer.parseInt(mapaInscritos.get(permiso.getPlanId()));
					}
					
					int numAlumnos = numAsignados+numInscritos;
					
					facGrupos		+= numGrupos;
					facMaestros 	+= numMaestros;
					facHorarios		+= numHorarios;
					facSalones		+= numSalones;	
					facAlumnos		+= numAlumnos;
					
					totGrupos		+= numGrupos;
					totMaestros 	+= numMaestros;
					totHorarios		+= numHorarios;
					totSalones		+= numSalones;
					totAlumnos		+= numAlumnos;
					
					facPresencial	+= numPresencial;
					facMixta		+= numMixta;
					facHibrida		+= numHibrida;
					facVirtual		+= numVirtual;
					
					totPresencial	+= numPresencial;
					totMixta		+= numMixta;
					totHibrida		+= numHibrida;
					totVirtual		+= numVirtual;
					
					facAsignados	+= numAsignados;
					facInscritos	+= numInscritos;
					totAsignados	+= numAsignados;
					totInscritos	+= numInscritos;
					
					String colorMat 	= colorMal;
					String colorProf 	= colorMal;
					String colorHor 	= colorMal;
					String colorSal		= colorMal;
					
					if(numGrupos!=0){
						colorMat 	= colorBien;
						float tmpNum = (float)numMaestros/(float)numGrupos;
						if(tmpNum>0.7 && tmpNum<0.999) colorProf = color23;
						else if(tmpNum==1) colorProf = colorBien;
						
						tmpNum = (float)numHorarios/(float)numGrupos;
						if(tmpNum>0.7 && tmpNum < 0.999) colorHor = color23;
						else if(tmpNum==1) colorHor = colorBien;
						
						tmpNum = (float)numSalones/(float)numGrupos;
						if(tmpNum>0.7 && tmpNum < 0.999) colorSal = color23;
						else if(tmpNum==1) colorSal = colorBien;
					}
					
					String cicloUno = "0";
					if(mapCursosPorCiclo.containsKey(permiso.getPlanId()+"1")){
						cicloUno = mapCursosPorCiclo.get(permiso.getPlanId()+"1");
					}
					String cicloDos = "0";
					if(mapCursosPorCiclo.containsKey(permiso.getPlanId()+"2")){
						cicloDos = mapCursosPorCiclo.get(permiso.getPlanId()+"2");
					}
					String cicloTres = "0";
					if(mapCursosPorCiclo.containsKey(permiso.getPlanId()+"3")){
						cicloTres = mapCursosPorCiclo.get(permiso.getPlanId()+"3");
					}
					String cicloCuatro = "0";
					if(mapCursosPorCiclo.containsKey(permiso.getPlanId()+"4")){
						cicloCuatro = mapCursosPorCiclo.get(permiso.getPlanId()+"4");
					}
					String cicloCinco = "0";
					if(mapCursosPorCiclo.containsKey(permiso.getPlanId()+"5")){
						cicloCinco = mapCursosPorCiclo.get(permiso.getPlanId()+"5");
					}
					String cicloSeis = "0";
					if(mapCursosPorCiclo.containsKey(permiso.getPlanId()+"6")){
						cicloSeis = mapCursosPorCiclo.get(permiso.getPlanId()+"6");
					}
					String cicloSiete = "0";
					if(mapCursosPorCiclo.containsKey(permiso.getPlanId()+"7")){
						cicloSiete = mapCursosPorCiclo.get(permiso.getPlanId()+"7");
					}
					String cicloOcho = "0";
					if(mapCursosPorCiclo.containsKey(permiso.getPlanId()+"8")){
						cicloOcho = mapCursosPorCiclo.get(permiso.getPlanId()+"8");
					}
					String cicloNueve = "0";
					if(mapCursosPorCiclo.containsKey(permiso.getPlanId()+"9")){
						cicloNueve = mapCursosPorCiclo.get(permiso.getPlanId()+"9");
					}
					String cicloDiez = "0";
					if(mapCursosPorCiclo.containsKey(permiso.getPlanId()+"10")){
						cicloDiez = mapCursosPorCiclo.get(permiso.getPlanId()+"10");
					}
					String cicloOnce = "0";
					if(mapCursosPorCiclo.containsKey(permiso.getPlanId()+"11")){
						cicloOnce = mapCursosPorCiclo.get(permiso.getPlanId()+"11");
					}
					String cicloDoce = "0";
					if(mapCursosPorCiclo.containsKey(permiso.getPlanId()+"12")){
						cicloDoce = mapCursosPorCiclo.get(permiso.getPlanId()+"12");
					}					
					int totalciclo = Integer.parseInt(cicloUno) + Integer.parseInt(cicloDos) + Integer.parseInt(cicloTres) + Integer.parseInt(cicloCuatro) + Integer.parseInt(cicloCinco) + Integer.parseInt(cicloSeis) + Integer.parseInt(cicloSiete) + Integer.parseInt(cicloOcho) + Integer.parseInt(cicloNueve) + Integer.parseInt(cicloDiez) + Integer.parseInt(cicloOnce) + Integer.parseInt(cicloDoce);					

					String requiereHorario = "0";
					if(mapaRequiereHorario.containsKey(permiso.getPlanId())){
						requiereHorario = mapaRequiereHorario.get(permiso.getPlanId());
					}
					
					if(Integer.parseInt(requiereHorario) <= numHorarios){
						colorHor = colorBien;
					}else {
						colorHor = colorMal;
					}

					String requiereSalon = "0";
					if(mapaRequiereSalon.containsKey(permiso.getPlanId())){
						requiereSalon = mapaRequiereSalon.get(permiso.getPlanId());
					}
					
					if(Integer.parseInt(requiereSalon) <= numSalones){
						colorSal = colorBien;
					}else {
						colorSal = colorMal;
					}				
				%>
					<td align="center"><a class="btn btn-success btn-sm" href="alta?CarreraId=<%=permiso.getCarreraId()%>"><i class="fas fa-plus-circle" ></i></a></td>
					<td align="center">
						<span class="badge <%=colorPlan%>"><a style="color:white" href="grupo?CarreraId=<%=permiso.getCarreraId()%>&PlanId=<%=permiso.getPlanId()%>"><%=permiso.getPlanId()%></a></span>
						&nbsp;
						<a href="materias?CargaId=<%=cargaId%>&PlanId=<%=permiso.getPlanId()%>"><i class="fas fa-search"></i></a>	
					</td>					
					<td><%=nombreCarrera%></td>
					<td align="left"><%=permiso.getCarreraId()%></td>					
					<td class="text-center"><%=cicloUno.equals("0")?"":cicloUno%></td>
					<td class="text-center"><%=cicloDos.equals("0")?"":cicloDos%></td>
					<td class="text-center"><%=cicloTres.equals("0")?"":cicloTres%></td>
					<td class="text-center"><%=cicloCuatro.equals("0")?"":cicloCuatro%></td>
					<td class="text-center"><%=cicloCinco.equals("0")?"":cicloCinco%></td>
					<td class="text-center"><%=cicloSeis.equals("0")?"":cicloSeis%></td>
					<td class="text-center"><%=cicloSiete.equals("0")?"":cicloSiete%></td>
					<td class="text-center"><%=cicloOcho.equals("0")?"":cicloOcho%></td>
					<td class="text-center"><%=cicloNueve.equals("0")?"":cicloNueve%></td>
					<td class="text-center"><%=cicloDiez.equals("0")?"":cicloDiez%></td>
					<td class="text-center"><%=cicloOnce.equals("0")?"":cicloOnce%></td>
					<td class="text-center"><%=cicloDoce.equals("0")?"":cicloDoce%></td>					
					<td class="text-center"><%=Integer.toString(totalciclo)%></td>
					<td class="text-center"><span class="badge" <%=colorMat%>><%=numGrupos%></span></td>
					<td class="text-center"><span class="badge" <%=colorProf%>><%=numMaestros%></span></td>
					<td class="text-center"><span class="badge" <%=colorHor%>><%=numHorarios+" / "+requiereHorario%></span></td>
					<td class="text-center"><span class="badge" <%=colorSal%>><%=numSalones+"/"+requiereSalon%></span></td>										
					<td class="text-center"><%=numPresencial%></td>
					<!--<td class="right">numMixta</td>-->
					<!--<td class="right"><numHibrida</td>-->
					<td class="text-center"><%=numVirtual%></td>
					<td class="text-center"><%=numAsignados%></td>
					<td class="text-center"><%=numInscritos%></td>
					<td class="text-center"><span class="badge bg-dark"><%=numAlumnos%></span></td>
				</tr>
	<%		}
		}%>
		<tfooter>
		<tr>
			<th colspan="17" class="right"><%=nombreFacultad%> <spring:message code="cargasGrupos.grupo.TotalesDe"/></th>
			<th class="right"><%=facGrupos%></th>
			<th class="right"><%=facMaestros%></th>
			<th class="right"><%=facHorarios%></th>
			<th class="right"><%=facSalones%></th>
			<th class="right"><%=facAlumnos%></th>
			<th class="right"><%=facPresencial%></th>
			<!--<th class="right">facMixta</th>-->
			<!--<th class="right"><facHibrida</th>-->
			<th class="right"><%=facVirtual%></th>
			<th class="right"><%=facAsignados%></th>
			<th class="right"><%=facInscritos%></th>
		</tr>
		<tr>
			<th colspan="17" class="right"><spring:message code="aca.TotalGeneral"/></th>
			<th class="right"><%=totGrupos%></th>
			<th class="right"><%=totMaestros%></th>
			<th class="right"><%=totHorarios%></th>
			<th class="right"><%=totSalones%></th>
			<th class="right"><%=totAlumnos%></th>
			<th class="right"><%=totPresencial%></th>
			<!--<th class="right">totMixta</th>-->
			<!--<th class="right"><totHibrida</th>-->
			<th class="right"><%=totVirtual%></th>
			<th class="right"><%=totAsignados%></th>
			<th class="right"><%=totInscritos%></th>
		</tr>	
		</tfooter>
		
	</table>	
</div>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>