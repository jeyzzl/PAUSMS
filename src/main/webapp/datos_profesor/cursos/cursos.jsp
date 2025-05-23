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
<%@ include file= "../../idioma.jsp" %>

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
	String codigoPersonal			= (String) session.getAttribute("codigoEmpleado");
	
	String periodoId				= (String) request.getAttribute("periodoId");
	String cargaId					= (String) request.getAttribute("cargaId");	
	String maestroNombre			= (String) request.getAttribute("maestroNombre");
	String equipoPor				= (String) request.getAttribute("equipoPor");
	
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
	HashMap<String,String> mapaDiferidas			= (HashMap<String,String>) request.getAttribute("mapaDiferidas");
	HashMap<String,String> mapaCorrecciones			= (HashMap<String,String>) request.getAttribute("mapaCorrecciones");
	HashMap<String,String> mapaAvance				= (HashMap<String,String>) request.getAttribute("mapaAvance");
	HashMap<String,String> mapaEvalPendientes		= (HashMap<String,String>) request.getAttribute("mapaEvalPendientes");
	HashMap<String,String> mapaActPendientes		= (HashMap<String,String>) request.getAttribute("mapaActPendientes");
	HashMap<String,String> mapaGraduandos			= (HashMap<String,String>) request.getAttribute("mapaGraduandos");
	HashMap<String,String> mapaPuntos 				= (HashMap<String, String>) request.getAttribute("mapaPuntos");
	
	String cursoCargaId		= "";
	String numCurso 		= request.getParameter("numCurso")==null?"0":request.getParameter("numCurso");
	if (numCurso.equals("0") && lisCursos.size() > 0) numCurso = String.valueOf(lisCursos.size());
%>
<body>
<div class="container-fluid">
	<h2>Lecturer Subjects <small class="text-muted fs-6"> ( <%=codigoPersonal%> - <%=maestroNombre%> )</small></h2>
	<form name="frmCursos" action="cursos" method="post">	
	<input type="hidden" name="numCurso">	
	<div class="alert alert-info d-flex align-items-center">
		Period:&nbsp;
		<select onChange="javascritp:ActualizarPeriodo()" name="PeriodoId" class="form-select" style="width:150px;">
<%		for(CatPeriodo periodo : lisPeriodos){ %>
			<option <%=periodoId.equals(periodo.getPeriodoId())?"selected":""%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
<%		} %>		
		</select>
		&nbsp;&nbsp;
		Load:&nbsp;
		<select name="CargaId" id="CargaId" onChange="javascritp:ActualizarCarga()" class="form-select" style="width:370px;">
<%		for( Carga carga : lisCargas){%>			
			<option value="<%=carga.getCargaId()%>" <%=carga.getCargaId().equals(cargaId)?"selected":""%>><%="["+carga.getCargaId()+"] "+carga.getNombreCarga()%></option>					
<%		} %>
		</select>
		&nbsp;&nbsp;
		<a href="nuevoHorario?CargaId=<%=cargaId%>&Regresar=0&noentra=1"><img width="60px" src="../../portales/alumno/img/horario.png" title="Horario General" class="button"/></a>	
	</div>
	</form>	
<%
		if(lisPeriodos.size() < 1){
			out.print("<div class='info'><h3 align='center'>Load a lecturer to view their courses</h3></div>");					
		}
%>
	<table class="table table-bordered table-sm">
	<thead class="">	
	<tr>
		<th width="2%"><spring:message code="aca.Numero"/></th>
		<th width="2%">Timetable</th>
		<th width="3%" class="right">Progress</th>
		<th width="3%" class="right">Pend.Eval.</th>
		<th width="3%" class="right">Pend.Act.</th>
		<th width="4%" class="center">Grades</th>
	  	<th width="83%">Courses</th>
  	</tr>
  	</thead>
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

		String puntosCurso = "0";
		if(mapaPuntos.containsKey(cursoCargaId)){
			puntosCurso = mapaPuntos.get(cursoCargaId);
		}
		
		int faltan = Integer.parseInt(total)-Integer.parseInt(contestaron);
		
		String pendientes = "0";
		if (mapaPendientes.containsKey(cursoCargaId)){
			pendientes = mapaPendientes.get(cursoCargaId);			
		}
		
		int evaluados = Integer.parseInt(total)-Integer.parseInt(pendientes);
		
		float avance = 0f;
		String etiquetaAvance 	= ""; 
		if (mapaAvance.containsKey(cursoCargaId)){
			avance = Float.parseFloat(mapaAvance.get(cursoCargaId));
		}
		if (avance == 0){
			etiquetaAvance 		= "<span class='badge'>"+avance+"</span>";
		}else if (avance >= 0 && avance < 50.0){ 
			etiquetaAvance 		= "<span class='badge bg-warning'>"+avance+"</span>";
		}else if (avance >= 50.0 && avance < 90.0 ){
			etiquetaAvance 		= "<span class='badge bg-info'>"+avance+"</span>";
		}else{	
			etiquetaAvance 		= "<span class='badge bg-dark'>"+avance+"</span>";
		}
		
		String evalPendientes 		= "0";
		String colorEvaluaciones	= "";
		if (mapaEvalPendientes.containsKey(cursoCargaId)){
			evalPendientes 		= mapaEvalPendientes.get(cursoCargaId);
			if (!evalPendientes.equals("0")) colorEvaluaciones = "bg-warning"; 
		}
		
		String actPendientes 		= "0";
		String colorActividades 	= "";
		if (mapaActPendientes.containsKey(cursoCargaId)){
			actPendientes 			= mapaActPendientes.get(cursoCargaId);
			if (!actPendientes.equals("0")) colorActividades = "bg-warning";
		}
		
		String titulo = "Presencial";
		if(curso.getModo().equals("V")){
			titulo = "Virtual";			
		}else if(curso.getModo().equals("H")){
			titulo = "H?brido";			
		}else if(curso.getModo().equals("R")){
			titulo = "Mixta";			
		}
		
		String graduandos = "0";
		if (mapaGraduandos.containsKey(cursoCargaId)){
			graduandos = mapaGraduandos.get(cursoCargaId);
		}
%>	  
  	<tr>
  		<td valign="center" align="center">
  			<span class="badge bg-info"><%=row%></span>
  		</td> 
<%		  		
		if(  mapaHoras.containsKey(cursoCargaId) ){%>
  		<td align="center">
			<img width="20px" src="../../imagenes/horarioAsignado.png" class="button" onclick="location.href='../../datos_profesor/cursos/nuevoHorarioMateria?CursoCargaId=<%=curso.getCursoCargaId()%>&Materia=<%=curso.getNombreCurso()%>&BloqueId=<%=curso.getBloqueId()%>&Regresar=0';" title="Course timetable">		  						  				
		</td>
<%		}else{%>
		<td align="center" valign="top"><img width="20px" src="../../imagenes/horarioOcupado.png" title="The course has no timetable"></td>
<%		}%>
		<td valign="center" align="center">		
			<a href="evaluacion?CursoCargaId=<%=curso.getCursoCargaId()%>"><%=etiquetaAvance%></a>					
		</td>
		<td class="right">  			
  			<span class="badge <%=colorEvaluaciones%>"><%=evalPendientes%></span>
  		</td>
  		<td class="right">  			
  			<span class="badge <%=colorActividades%>"><%=actPendientes%></span>
  		</td>  		
		<td class="center">		
			<a href="notas?CursoCargaId=<%=curso.getCursoCargaId()%>"><span class="badge bg-dark"><i class="fas fa-search"></i></span></a>			
		</td>
  		<td>	  
  			<span class="text-left">	  
  				<h6>	
		<% 		if (!graduandos.equals("0")){ out.print("<i class='fas fa-user-graduate' style='color:orange' title='"+graduandos+" graduates'></i> "); }%>  					
					<b><%=curso.getNombreCurso()%></b>- <span data-bs-toggle="tooltip" data-bs-placement="top" title="<%=carreraNombre%>"><b><%=curso.getPlanId()%></b></span>&nbsp;-&nbsp;
					<b><%="SEMESTER "+curso.getCiclo()%></b>&nbsp;&nbsp;<span class="badge bg-secondary"><b><%=modalidadNombre%></b></span>&nbsp;&nbsp;
					<b>GROUP:</b>&nbsp;<span class="badge bg-info"><%=curso.getGrupo()%></span>&nbsp;&nbsp;<b>BLOCK:&nbsp;</b><span class="badge bg-info"><%=curso.getBloqueId()%></span>
					<span class="badge bg-dark"><%= curso.getCursoCargaId() %></span>
					<span class="badge bg-success"><%= curso.getfInicio() %> - <%= curso.getfFinal() %></span>
					<span class="badge bg-dark" title="<%=titulo%>"><%= curso.getModo() %></span>
  				</h6>		
		  		<h6>
		  			&nbsp;<i class="far fa-dot-circle"></i>&nbsp;
			    	[<a title="Attend. List" href="listado?CursoCargaId=<%=curso.getCursoCargaId()%>&Maestro=<%=maestroNombre%>&Materia=<%=curso.getNombreCurso()%>&CargaId=<%=curso.getCargaId()%>&BloqueId=<%=curso.getBloqueId()%>">Attend. List</a>]			   	
					[<a title="Students" href="tarjeta?CursoCargaId=<%=curso.getCursoCargaId()%>&Maestro=<%=maestroNombre%>&Materia=<%=curso.getNombreCurso()%>">Students</a>]
		            [<a title="Evaluation Method" href=""><span class='badge bg-secondary rounded-pill'><%=puntosCurso%></span></a>]
					Grading
		            <%		if (curso.getEstado().equals("1")){%>
		            <a title="Grading" href="evaluacion?CursoCargaId=<%=curso.getCursoCargaId()%>" class="" style="text-decoration:none">[ Empty Subject ]</a> 
		            <%		}else if (curso.getEstado().equals("2")){%>
		            <a title="Grading" href="evaluacion?CursoCargaId=<%=curso.getCursoCargaId()%>" class="" style="text-decoration:none">[ Ordinary Grade ]</a>
		            <%		}else if (curso.getEstado().equals("3")){%>
		            <a title="Grading" href="evaluacion?CursoCargaId=<%=curso.getCursoCargaId()%>" class="" style="text-decoration:none">[ Make-up Grade ]</a> 
		            <%		}else if (curso.getEstado().equals("4")){%>
		            <a title="Grading" href="evaluacion?CursoCargaId=<%=curso.getCursoCargaId()%>" class="" style="text-decoration:none">[ Delivered Subject ]</a> 
		            <%		}else if (curso.getEstado().equals("5")){%>
		            <a title="Grading" href="evaluacion?CursoCargaId=<%=curso.getCursoCargaId()%>" class="" style="text-decoration:none">[ Grading Record ]</a> 
		            <%		}%>
	            	[ <i class="fas fa-print"></i>&nbsp;<a  href="cactames?cursoCargaId=<%=curso.getCursoCargaId()%>">Month record</a>
	             	| <a href="cacta?cursoCargaId=<%=curso.getCursoCargaId()%>">Strategies record</a> ]
<%		
		String numDiferidas = "0";
		if (mapaDiferidas.containsKey(cursoCargaId)){
			numDiferidas= mapaDiferidas.get(cursoCargaId);
			if  (!numDiferidas.equals("0")){
%>
				[ <a href="diferidaPDF?CursoCargaId=<%=curso.getCursoCargaId()%>&Maestro=<%=maestroNombre%>&Materia=<%=curso.getNombreCurso()%>&CursoId=<%=curso.getCursoId()%>">Deferred</a> ]&nbsp; &nbsp;
<%			}
		}
		
		String numCorrecciones = "0";
		if (mapaCorrecciones.containsKey(cursoCargaId)){
			numCorrecciones= mapaCorrecciones.get(cursoCargaId);
			if  (!numCorrecciones.equals("0")){
%>
				[ <a href="correccionPDF?CursoCargaId=<%=curso.getCursoCargaId()%>&Maestro=<%=maestroNombre%>&Materia=<%=curso.getNombreCurso()%>&CursoId=<%=curso.getCursoId()%>&Opcion=1">Correction</a> ]
<%			
			}
		}
%>             	
				</h6>
				<h6 title="The average is calculated based on answered strategies">
				&nbsp;<i class="far fa-dot-circle"></i>&nbsp;
<%		 
		if(Integer.parseInt(contestaron) >= 2){
%>		  
	  				<a href="opinion_materia?cursoCargaId=<%=cursoCargaId%>&CursoId=<%=curso.getCursoId()%>">
	  			  	Avrg. [<b><%=promedio%></b>]</a> 
	  			  	Answered [ <%=contestaron%> ]&nbsp;
	  			  	Missing [ <%=faltan%> ]&nbsp;&nbsp;
	  				- <a href="comentarios?cursoCargaId=<%=cursoCargaId%>&nombreCurso=<%=curso.getNombreCurso()%>"><strong>Comments</strong></a>
<%		}else{ %>
					Avrg. [<%=promedio%>]
					Answered [ <%=contestaron%> ]&nbsp;
					Missing [ <%=faltan%> ]&nbsp;&nbsp;
<%		}				
		String colorPendiente	= "";				
		if (Integer.parseInt(pendientes) > 0) colorPendiente = "style='color:red;'";
%>
					<span <%=colorPendiente%>>Evaluated [ <%=evaluados%> ]</span>
					<span <%=colorPendiente%>>Pending [ <%=pendientes%> ]</span>
	  			</h6>
  			</span>
		</td>
	</tr>
<%	}
%>
</table>
</div>
</body>
<script type="text/javascript">	
	$(function () {
  		$('[data-bs-toggle="tooltip"]').tooltip();  		
  		$('[data-bs-toggle="popover"]').popover();
	});
</script>
