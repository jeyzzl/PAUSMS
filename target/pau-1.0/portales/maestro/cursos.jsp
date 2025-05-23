<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.carga.spring.CargaGrupo"%>
<%@ page import= "aca.carga.spring.CargaPron"%>
<%@ page import= "aca.edo.spring.Edo"%>
<%@ page import= "aca.vista.spring.CargaAcademica"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp"%>

<script type="text/javascript">
	function ActualizarPeriodo(){	
  		document.frmCursos.CargaId.value = "0";
  		document.frmCursos.submit();
	}
	
	function ActualizarCarga(){
  		document.frmCursos.submit();
	}	
</script>
<%
	String codigoPersonal			= (String) session.getAttribute("codigoPersonal");

	String periodoId				= (String) request.getAttribute("periodoId");
	String cargaId					= (String) request.getAttribute("cargaId");	
	String maestroNombre			= (String) request.getAttribute("maestroNombre");
	String equipoPor				= (String) request.getAttribute("equipoPor");
	int horarios					= (int) request.getAttribute("horarios");
	boolean existeRango				= (boolean) request.getAttribute("existeRango");
	String yearActual				= aca.util.Fecha.getHoyReversa().split("/")[0];
	
	List<CatPeriodo> lisPeriodos	= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	List<Carga> lisCargas			= (List<Carga>) request.getAttribute("lisCargas");	
	List<CargaAcademica> lisCursos	= (List<CargaAcademica>) request.getAttribute("lisCursos");
	
	// Mapa de las materias que tienen capturado el prontuario
	HashMap<String,String> mapaPron 				= (HashMap<String,String>) request.getAttribute("mapaPron");
	HashMap<String,CatCarrera> mapaCarreras 		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,CatModalidad> mapaModalidades 	= (HashMap<String,CatModalidad>) request.getAttribute("mapaModalidades");
	HashMap<String,String> mapaEdo 					= (HashMap<String,String>) request.getAttribute("mapaEdo");
	HashMap<String,String> mapaHoras 				= (HashMap<String,String>) request.getAttribute("mapaHoras");
	HashMap<String,String> mapaPromedioMaterias		= (HashMap<String,String>) request.getAttribute("mapaPromedioMaterias");
	HashMap<String,String> mapaContestaron			= (HashMap<String,String>) request.getAttribute("mapaContestaron");
	HashMap<String,String> mapaTotAlumnos			= (HashMap<String,String>) request.getAttribute("mapaTotAlumnos");
	HashMap<String,String> mapaPendientes			= (HashMap<String,String>) request.getAttribute("mapaPendientes");
	HashMap<String,String> mapaAvance				= (HashMap<String,String>) request.getAttribute("mapaAvance");
	HashMap<String,String> mapaEvalPendientes		= (HashMap<String,String>) request.getAttribute("mapaEvalPendientes");
	HashMap<String,String> mapaActPendientes		= (HashMap<String,String>) request.getAttribute("mapaActPendientes");
	HashMap<String,String> mapaCorrecciones			= (HashMap<String,String>) request.getAttribute("mapaCorrecciones");
	HashMap<String,String> mapaGraduandos			= (HashMap<String,String>) request.getAttribute("mapaGraduandos");
	HashMap<String,String> mapaPuntos		 		= (HashMap<String,String>) request.getAttribute("mapaPuntos");
	
	String cursoCargaId		= "";
	String numCurso 		= request.getParameter("numCurso")==null?"0":request.getParameter("numCurso");
	if (numCurso.equals("0") && lisCursos.size() > 0) numCurso = String.valueOf(lisCursos.size());
%>
<body>
<div class="container-fluid">
	<h2>Lecturer Portal <small class="text-muted fs-6">( <%=codigoPersonal%> | <%=maestroNombre%> )</small></h2>	 
	<form name="frmCursos" action="cursos" method="post" >	
	<input type="hidden" name="numCurso">
	<div class="alert alert-info d-flex align-items-center">
		Period:&nbsp;
		<select onChange="javascritp:ActualizarPeriodo()" name="PeriodoId" style="width:150px"; class="input input-medium form-select">
<%		for(CatPeriodo periodo : lisPeriodos){ %>
			<option <%=periodoId.equals(periodo.getPeriodoId())?"selected":""%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
<%		} %>		
		</select>
		&nbsp;&nbsp;
		Load:&nbsp;
		<select name="CargaId" id="CargaId" class="form-select" onChange="javascritp:ActualizarCarga()" style="width:370px;">
<%	
		for( Carga carga : lisCargas){			
			if (carga.getCargaId().equals(cargaId)){
				out.print(" <option value='"+carga.getCargaId()+"' Selected> ["+carga.getCargaId()+"] "+ carga.getNombreCarga()+"</option>");
			}else{
				out.print(" <option value='"+carga.getCargaId()+"'> ["+carga.getCargaId()+"] "+ carga.getNombreCarga()+"</option>");
			}				
		}	
	%>
		</select>
		&nbsp;&nbsp;
		<a href="nuevoHorario?CargaId=<%=cargaId%>&Regresar=1&noentra=1"><img width="60px" src="../../portales/alumno/img/horario.png" title="General Timetable" class="button"/></a>	
	</div>	
	</form>
	<table style="width:100%" class="table-sm">	
	<tr>
		<td colspan="2">
			<a class="btn btn-primary btn-sm" href="carga_docente">Load</a>
			<a class="btn btn-primary btn-sm" href="opinion_estudiantil">Student Opinion</a>		
			<a class="btn btn-primary btn-sm" href="estadistica?CargaId=<%=cargaId%>">Statistics</a>
			<a class="btn btn-primary btn-sm" href="materias">Subjects</a>
		<%	if (!equipoPor.equals("0")){%>
			<a class="btn btn-primary btn-sm" href="cita?Equipo=<%=equipoPor%>">Port. Rev</a>
		<%	} 
			if(horarios > 0){
		%>
			<a href="horariocimum?CargaId=<%=cargaId%>" class="btn btn-primary btn-sm" title="Horario"><i class="fas fa-calendar-alt" ></i> CIMUM</a>
		<%	}%>
		<%	if (existeRango){%>
			<a class="btn btn-success btn-sm rounded-pill" href="rangoPdf?Year=<%=yearActual%>">Range <%=yearActual%></a>
		<%	} %>
		<%	if (codigoPersonal.equals("9800308")){%>			
			&nbsp;<a class="btn btn-dark btn-sm" href="updateEvaluacion">Update Eval.</a>
		<%	} %>	
		</td>
	</tr>
	</table>	
	<table class="table table-bordered table-sm">	  
	<tr>
 		<th width="1%"><spring:message code="aca.Numero"/></th>
		<th width="1%">Timetable</th>
		<th width="3%" class="right">Progress</th>
		<th width="5%" class="right">Pend. Eval.</th>
		<th width="5%" class="right">Pend. Act.</th>
 		<th width="85%">Courses</th>
 	</tr>
<%	
	int row = 0;
	for (CargaAcademica curso : lisCursos){
		row++;
		
		cursoCargaId = curso.getCursoCargaId();
		String edoId = "0";
		if (mapaEdo.containsKey(cursoCargaId)){
			edoId = mapaEdo.get(cursoCargaId);
		}	
		
		String carreraNombre = "";
		if (mapaCarreras.containsKey(curso.getCarreraId())){
			carreraNombre = mapaCarreras.get(curso.getCarreraId()).getNombreCarrera();
		}
		
		String modalidadNombre = "";
		if (mapaModalidades.containsKey(curso.getModalidadId())){
			modalidadNombre = mapaModalidades.get(curso.getModalidadId()).getNombreModalidad();
		}
		
		String promedio = "0";
		if (mapaPromedioMaterias.containsKey(cursoCargaId)){
			promedio = mapaPromedioMaterias.get(cursoCargaId);
		}
		
		String contestaron = "0";
		if (mapaContestaron.containsKey(cursoCargaId)){
			contestaron = mapaContestaron.get(cursoCargaId);
		}
		
		String total = "0";
		if (mapaTotAlumnos.containsKey(cursoCargaId)){
			total = mapaTotAlumnos.get(cursoCargaId);
		}
		
		int faltan = Integer.parseInt(total)-Integer.parseInt(contestaron);
		
		String pendientes = "0";
		if (mapaPendientes.containsKey(cursoCargaId)){
			pendientes = mapaPendientes.get(cursoCargaId);			
		}
		
		int evaluados = Integer.parseInt(total)-Integer.parseInt(pendientes);
		
		float avance = 0f;
		String etiquetaAvance = ""; 
		if (mapaAvance.containsKey(cursoCargaId)){
			avance = Float.parseFloat(mapaAvance.get(cursoCargaId));
		}
		if (avance == 0){
			etiquetaAvance = "<span class='bg'>"+avance+"</span>";
		}else if (avance >= 0 && avance < 50.0){ 
			etiquetaAvance = "<span class='badge bg-warning'>"+avance+"</span>";
		}else if (avance >= 50.0 && avance < 90.0 ){
			etiquetaAvance = "<span class='badge bg-info'>"+avance+"</span>";
		}else{	
			etiquetaAvance = "<span class='badge bg-dark'>"+avance+"</span>";
		}		
		
		String evalPendientes 		= "0";
		String colorEvaluaciones	= "bg-secondary rounded-pill";
		if (mapaEvalPendientes.containsKey(cursoCargaId)){
			evalPendientes 		= mapaEvalPendientes.get(cursoCargaId);
			if (!evalPendientes.equals("0")) colorEvaluaciones = "bg-warning rounded-pill"; 
		}
		
		String actPendientes 		= "0";
		String colorActividades 	= "bg-secondary rounded-pill";
		if (mapaActPendientes.containsKey(cursoCargaId)){
			actPendientes 			= mapaActPendientes.get(cursoCargaId);
			if (!actPendientes.equals("0")) colorActividades = "bg-warning rounded-pill";
		}
		
		String titulo = "Face to Face";
		if(curso.getModo().equals("V")){
			titulo = "Virtual";			
		}else if(curso.getModo().equals("H")){
			titulo = "Hybrid";			
		}else if(curso.getModo().equals("R")){
			titulo = "Mixed";			
		}
		
		String graduandos = "0";
		if (mapaGraduandos.containsKey(cursoCargaId)){
			graduandos = mapaGraduandos.get(cursoCargaId);
		}
		
		String puntosCurso = "<span class='badge bg-warning rounded-pill'>0</span>";
		if (mapaPuntos.containsKey(cursoCargaId)){
			puntosCurso = mapaPuntos.get(cursoCargaId);
			if (Integer.parseInt(puntosCurso)>=100) 
				puntosCurso = "<span class='badge bg-success rounded-pill'>"+puntosCurso+"</span>";
			else if (Integer.parseInt(puntosCurso) > 0)
				puntosCurso = "<span class='badge bg-secondary rounded-pill'>"+puntosCurso+"</span>";
		}
%>	 		
	<tr>
		<td valign="center" align="center">
			<span class="badge bg-info"><%=row%></span>
		</td>			
  	<%		  		
  		if(  mapaHoras.containsKey(cursoCargaId) ){%>
  			<td align="center">
  				<img width="20px" src="../../imagenes/horarioAsignado.png" class="button" onclick="location.href='nuevoHorarioMateria?CursoCargaId=<%=curso.getCursoCargaId()%>&Materia=<%=curso.getNombreCurso()%>&BloqueId=<%=curso.getBloqueId()%>&Regresar=1';" title="Subject's Schedule">		  						  				
  			</td>
  		<%}else{%>
  			<td align="center" valign="top"><img width="20px" src="../../imagenes/horarioOcupado.png" title="The subject has no schedule assigned"></td>
	<%	} %>
		<td valign="center" align="center">		
			<a href="avance?CursoCargaId=<%=curso.getCursoCargaId()%>"><%=etiquetaAvance%></a>
		</td>
		<td align="center">  			
  			<span class="badge <%=colorEvaluaciones%>"><%=evalPendientes%></span>
  		</td>
  		<td align="center">  			
  			<span class="badge <%=colorActividades%>"><%=actPendientes%></span>
  		</td>	
  		<td>
			<span class="text-left">
  				<h6>
<% 			if (!graduandos.equals("0")){ out.print("<i class='fas fa-user-graduate' style='color:orange' title='"+graduandos+" graduates'></i> "); }%>  				
					<b><%=curso.getNombreCurso()%></b> - <span data-bs-toggle="tooltip" data-bs-placement="top" title="<%=carreraNombre%>"><u><i><b><%=curso.getPlanId()%></b></i></u></span>&nbsp;-&nbsp;
					<b><%=curso.getCarreraId().substring(0,3).equals("107")?"Tetra "+curso.getCiclo():"SEMESTER "+curso.getCiclo()%></b>&nbsp;&nbsp;&nbsp;<span class="badge bg-secondary"><b><%=modalidadNombre%></b></span>&nbsp;
					<b>GROUP: <span class="badge bg-info"><%=curso.getGrupo()%></span></b>&nbsp;&nbsp;<b>BLOCK: <span class="badge bg-info"><%=curso.getBloqueId()%></b></span>
					&nbsp;&nbsp;<span class="badge bg-dark"><%= curso.getCursoCargaId() %></span>
					&nbsp;&nbsp;<span class="badge bg-success"><%= curso.getfInicio() %> - <%= curso.getfFinal() %></span>
					&nbsp;&nbsp;<span class="badge bg-dark" title="<%=titulo%>"><%= curso.getModo() %></span>
		  		</h6>
		  		<h6>
		  			&nbsp;<i class="far fa-dot-circle"></i>&nbsp;
			    	[ <a class="text-decoration-none" title="Attendance List" href="listado?CursoCargaId=<%=curso.getCursoCargaId()%>&Maestro=<%=maestroNombre%>&Materia=<%=curso.getNombreCurso()%>&CargaId=<%=cargaId%>&BloqueId=<%=curso.getBloqueId()%>">Attend. List</a> |
				   	  <a class="text-decoration-none" title="Attendance Registration" href="asistencia?CursoCargaId=<%=curso.getCursoCargaId()%>&Materia=<%=curso.getCursoId()%>">Attendance Reg.</a> ]
				    [ <a class="text-decoration-none" title="Meet your students" href="tarjeta?CursoCargaId=<%=curso.getCursoCargaId()%>&Maestro=<%=maestroNombre%>&Materia=<%=curso.getNombreCurso()%>">Students</a> ]
				    &nbsp;&nbsp;<i class="far fa-dot-circle"></i>&nbsp;
		            [ <a class="text-decoration-none" href="metodo?CursoCargaId=<%=curso.getCursoCargaId()%>&Maestro=<%=maestroNombre%>&Materia=<%=curso.getNombreCurso()%>">Assessment Scheme&nbsp;<%=puntosCurso%></a> ]		            			            
		            &nbsp;<b>Grading:</b> 
		            <a class="text-decoration-none" href="evaluar?CursoCargaId=<%=curso.getCursoCargaId()%>&EvaluacionId=0"> 
			            <%		if (curso.getEstado().equals("1")){%>
			            <font color="#000000"><b>[ Empty Subject ]</b></font> 
			            <%		}else if (curso.getEstado().equals("2")){%>
			            <font color="#0000CC"><b>[ Ordinary Grade ]</b></font>
			            <%		}else if (curso.getEstado().equals("3")){%>
			            <font color="#006600"><b>[ Make-up Grade ]</b></font> 
			            <%		}else if (curso.getEstado().equals("4")){%>
			            <font color="#990000"><b>[ Delivered Subject ]</b></font> 
			            <%		}else if (curso.getEstado().equals("5")){%>
			            <font color="#990099"><b>[ Grading Record ]</b></font> 
		            <%		}%>
		            </a>
		            &nbsp;&nbsp;<i class="far fa-dot-circle"></i>&nbsp; 
	            	[ <i class="fas fa-print"></i>&nbsp;<a class="text-decoration-none" href="cactames?cursoCargaId=<%=curso.getCursoCargaId()%>">Month Record</a>
	             	| <a class="text-decoration-none" href="cacta?cursoCargaId=<%=curso.getCursoCargaId()%>">Strategies Record</a> ]
<%				if ( curso.getEstado().equals("1") || curso.getEstado().equals("2") ){ %>
					&nbsp;&nbsp;<i class="far fa-dot-circle"></i>&nbsp;
					 <a class="link-danger text-decoration-none" href="borrarNotas?CursoCargaId=<%=curso.getCursoCargaId()%>"><b>[ Delete Grades ]</b></a>
<% 				}%>		   
	      		</h6>
				<h6 title="The average is calculated according to the number of students who have already answered">
				&nbsp;<i class="far fa-dot-circle"></i>&nbsp;
<%
			 
			if(Integer.parseInt(contestaron) >= 2){
%>		  
	  			<a href="opinion_materia?cursoCargaId=<%=cursoCargaId%>&nombreCurso=<%=curso.getNombreCurso()%>">
	  			  Avrg. Grade [ <b><%=promedio%></b></a>] 
	  			  Answered [ <%=contestaron%> ]&nbsp;
	  			  Missing [ <%=faltan%> ]&nbsp;&nbsp;
	  			- <a href="comentarios?cursoCargaId=<%=cursoCargaId%>&nombreCurso=<%=curso.getNombreCurso()%>"><strong>Comments</strong></a>
<%				}else{ %>
					Avrg. Grade [ <%=promedio%> ] 
					Answered [ <%=contestaron%> ]&nbsp;
					Missing [ <%=faltan%> ]&nbsp;&nbsp;
<%				}				
			String colorPendiente	= "";				
			if (Integer.parseInt(pendientes) > 0) colorPendiente = "style='color:red;'";
%>
						&nbsp;
						<span <%=colorPendiente%>>Evaluated [ <%=evaluados%> ]</span>
						<span <%=colorPendiente%>>Pending [ <%=pendientes%> ]</span>
<% 				if (mapaCorrecciones.containsKey(cursoCargaId)){%>
					<span style="color: red;">Correction</span>
<% 				}%>
				</h6>
			</span>
		</td>
    </tr>
<%	}%>
	</table>
</div>
</body>
<script>	

	var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
	var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
  	return new bootstrap.Tooltip(tooltipTriggerEl)
	});

	$(function () {
  		$('[data-bs-toggle="tooltip"]').tooltip();
  		$('[data-bs-toggle="popover"]').popover();
	});
	
	function grabaPeriodo(periodoId){
		document.location.href="cursos?cambioPeriodo=1&PeriodoId="+periodoId;
	}	
</script>
</html>