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

	function grabaPeriodo(periodoId){
		document.location.href="cursos?cambioPeriodo=1&PeriodoId="+periodoId;
	}
	
	function Carga(){
		document.frmcursos.Accion.value="2";
		document.frmcursos.numCurso.value="0";
		document.frmcursos.submit();
	}
	
	function Estado(cursoCargaId,estado){		
		document.location.href="cambiaEstado?CursoCargaId="+cursoCargaId+"&Estado="+estado;				
	}
	
</script>
<%	
	String codigoPersonal			= (String) session.getAttribute("codigoEmpleado");
	
	String periodoId				= (String) request.getAttribute("periodoId");
	String cargaId					= (String) request.getAttribute("cargaId");	
	String maestroNombre			= (String) request.getAttribute("maestroNombre");
	String equipoPor				= (String) request.getAttribute("equipoPor");
	String cursoEstado				= request.getParameter("CursoEstado")==null?"0":request.getParameter("CursoEstado");
	String cursoCargaId				= "0";
	
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
	HashMap<String,String> mapaTotEvaluaron			= (HashMap<String,String>) request.getAttribute("mapaTotEvaluaron");
	HashMap<String,String> mapaDiferidas			= (HashMap<String,String>) request.getAttribute("mapaDiferidas");
	HashMap<String,String> mapaCorrecciones			= (HashMap<String,String>) request.getAttribute("mapaCorrecciones");
	HashMap<String,String> mapaGraduandos			= (HashMap<String,String>) request.getAttribute("mapaGraduandos");
	HashMap<String,String> mapaEsquemas				= (HashMap<String,String>) request.getAttribute("mapaEsquemas");

	String sCodigoPersonal	= (String) session.getAttribute("codigoEmpleado");
	String sCargaId 		= (String) session.getAttribute("cargaId");
		
	String estado 			= request.getParameter("estado")==null?"":request.getParameter("estado");	
	String nCurso 			= request.getParameter("numCurso");
	
	String noentra ="1";	
	aca.util.Fecha fecha	= new aca.util.Fecha();	
%>
<div class="container-fluid">
	<h2>Evaluations <small class="text-muted h5">( <%=codigoPersonal%> - <%=maestroNombre%> )</small></h2>
	<form action="cursos" method="post" name="frmcursos">
	<input type="hidden" name="Accion">
	<input type="hidden" name="numCurso">
	<div class="alert alert-info d-flex align-items-center">
		<spring:message code="aca.Periodo"/>:&nbsp;
	  	<select onchange="javascript:grabaPeriodo(this.value);" name="PeriodoId" class="form-select" style="width:150px">
<%		for(CatPeriodo periodo : lisPeriodos){ %>
			<option <%=periodoId.equals(periodo.getPeriodoId())?"selected":""%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
<%		} %>
	  	</select>
	  &nbsp;&nbsp;
	  Load:&nbsp; 
		<select name="CargaId" id="CargaId" onChange="Carga()" class="form-select" style="width:350px">
<%	
		for( Carga carga : lisCargas){			
			if (carga.getCargaId().equals(cargaId)){
				out.print(" <option value='"+carga.getCargaId()+"' Selected>"+carga.getCargaId()+" - "+carga.getNombreCarga()+"</option>");
			}else{
				out.print(" <option value='"+carga.getCargaId()+"'>"+ carga.getCargaId()+" - "+carga.getNombreCarga()+"</option>");
			}				
		}	
%>
		</select>
		&nbsp;&nbsp;
		<a href="../../datos_profesor/cursos/nuevoHorario?CargaId=<%=cargaId%>&Regresar=3&noentra=<%=noentra%>"><img width="50px" src="../../portales/alumno/img/horario.png" title="General Schedule" class="button"/></a>
	</div>	
<%	
	if(lisPeriodos.size() < 0){
		out.print("<div class='alert'>Select a Lecturer!</div>");
	}
%>
	<table class="table table-sm table-bordered">
	<tr>
		<td colspan="3">
			<a class="btn btn-primary" href="carga_docente">Lecturer's Load</a>
			<a class="btn btn-primary" href="opinion_estudiantil">Student Feedback</a>
		</td>
	</tr>
	<tr class="table-info">
	  	<th><spring:message code="aca.Numero"/></th>
	  	<th><spring:message code="aca.Horario"/></th>
	  	<th style="text-align:center">Courses</th>
	</tr>
<%	
	int row=0;
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
		
		int evaluados = 0;
		if (mapaTotEvaluaron.containsKey(cursoCargaId)){
			evaluados = Integer.parseInt(mapaTotEvaluaron.get(cursoCargaId));			
		}
		
		int pendientes = Integer.parseInt(total)-evaluados;
		
		String numCorrecciones = "0";
		if (mapaCorrecciones.containsKey(cursoCargaId)){
			numCorrecciones= mapaCorrecciones.get(cursoCargaId);
		}
		
		String titulo = "Face to Face";
		if(curso.getModo().equals("V")){
			titulo = "Online";			
		}else if(curso.getModo().equals("H")){
			titulo = "Hybrid";			
		}else if(curso.getModo().equals("R")){
			titulo = "Mixed";			
		}
		
		String graduandos = "0";
		if (mapaGraduandos.containsKey(cursoCargaId)){
			graduandos = mapaGraduandos.get(cursoCargaId);
		}
		
		String sumaEvaluaciones = "0";
		if (mapaEsquemas.containsKey(cursoCargaId)){
			sumaEvaluaciones = mapaEsquemas.get(cursoCargaId);
		}
%>	  
  	<tr>
		<td valign="top" align="center">
			<span class="badge bg-info"><%=row%></span>
		</td>
		<td valign="top" align="center">
<%		  		
		if( mapaHoras.containsKey(cursoCargaId) ){%>
			<img width="20px" src="../../imagenes/horarioAsignado.png" class="button" onclick="location.href='../../datos_profesor/cursos/nuevoHorarioMateria?CursoCargaId=<%=curso.getCursoCargaId()%>&Materia=<%=curso.getNombreCurso()%>&BloqueId=<%=curso.getBloqueId()%>&Regresar=3';" title="Subject Schedule">
<%		}else{%>
			<img width="20px" src="../../imagenes/horarioOcupado.png" title="The Subject has no Schedule">
<%		}%>
		</td>
	  	<td width="90%">
<% 		if (!graduandos.equals("0")){ out.print("<i class='fas fa-user-graduate' style='color:orange' title='"+graduandos+" graduates'></i> "); }%>
			<strong><%=curso.getNombreCurso()%></strong> - <span data-bs-toggle="tooltip" data-bs-placement="top" title="<%=carreraNombre%>"><b><%=curso.getPlanId()%></b></span>&nbsp;- <%=curso.getCiclo()%>&nbsp;
			- <%=modalidadNombre%> - <strong>Gpo.: [<%=curso.getGrupo()%>]</strong> -&nbsp;
			<strong>Blo.: [<%=curso.getBloqueId()%>]</strong> - <span class="badge bg-info"><%=curso.getCursoCargaId()%></span>
			<span title="<%=titulo%>" class="badge bg-dark"><%=curso.getModo() %></span> 
		</td>
	</tr>
	<tr>
		<td width="5%">&nbsp;</td>
		<td width="5%">&nbsp;</td>
		<td width="95%">
			<ul>			
			<li class="d-flex align-items-center"><a href="alumnos?CursoCargaId=<%=curso.getCursoCargaId()%>&Maestro=<%=maestroNombre%>&Materia=<%=curso.getNombreCurso()%>"><span class="badge bg-secondary rounded-pill"><i class="fas fa-users"></i> = <%=total%></span></a>&nbsp; 
		    <a href="metodo?CursoCargaId=<%=curso.getCursoCargaId()%>&Maestro=<%=maestroNombre%>&Materia=<%=curso.getNombreCurso()%>"><span class="badge bg-secondary rounded-pill">Method <%=sumaEvaluaciones%>%</span></a>
<% 		if(curso.getCursoId().indexOf("2010") > 0 ){ %>
		    [<a href="pdfPron?CursoCargaId=<%=curso.getCursoCargaId()%>"><spring:message code="aca.Plan"/></a>]
<%		}%>
<%
		if ( mapaPron.containsKey(curso.getCursoCargaId()) ){
			out.print("&nbsp;<i class='fas fa-check'  style='color:black'></i>");
		}else{
			out.print("&nbsp;<i class='fas fa-times'  style='color:red'></i>");
		}
%>	
			&nbsp;<b>Status:</b>
			<a href="evaluar?CursoCargaId=<%=curso.getCursoCargaId()%>&EvaluacionId=0">
<%		if (curso.getEstado().equals("1")){%>
			<span class="badge rounded-pill bg-secondary" style="text-decoration:none"><b>Created</b></span>
<%		}else if (curso.getEstado().equals("2")){%>	 
			<span class="badge rounded-pill bg-info" style="text-decoration:none"><b>Ordinary</b></span>
<%		}else if (curso.getEstado().equals("3")){%>
			<span class="badge rounded-pill bg-success" style="text-decoration:none"><b>Extraordinary </b></span>
<%		}else if (curso.getEstado().equals("4")){%>
			<span class="badge rounded-pill bg-dark" style="text-decoration:none"><b>Finalized</b></span>
<%		}else if (curso.getEstado().equals("5")){%>
			<span class="badge rounded-pill bg-danger" style="text-decoration:none"><b>Registered</b></span>
<%		}
		if (numCorrecciones.equals("0")){
%>			</a>
			[<a href="cursos?estado=1&CursoEstado=<%=cursoCargaId%>">Change status</a>]			
<%			if (estado.equals("1")&&cursoEstado.equals(cursoCargaId)){ %>
				<select name="fEstado" class="form-select" onChange="javascript:Estado('<%=cursoCargaId%>',this.value)" style="width:150px;">
					<option value="1" <% if (curso.getEstado().equals("1")) out.print("selected");%>>Created</option>
					<option value="2" <% if (curso.getEstado().equals("2")) out.print("selected");%>>Ordinary</option>
					<option value="3" <% if (curso.getEstado().equals("3")) out.print("selected");%>>Extraordinary</option>
					<option value="4" <% if (curso.getEstado().equals("4")) out.print("selected");%>>Closed</option>
					<option value="5" <% if (curso.getEstado().equals("5")) out.print("selected");%>>Delivered</option>
				</select>
<%			}
		}
%>
<%		if ( curso.getEstado().equals("1") || curso.getEstado().equals("2") ){ %>
			[ <a href="borrarNotas?CursoCargaId=<%=curso.getCursoCargaId()%>">Delete Notes</a> ]
<% 		}%>			
		  </li>
		  <li title="The average is calculated according to the number of students who have already answered.">
<%		if(evaluados >= 2){ %>		  
	  		<a href="opinion_materia?cursoCargaId=<%=cursoCargaId%>&nombreCurso=<%=curso.getNombreCurso()%>">Student Assessment = <b><%=promedio%></b></a> -&nbsp;
	  		Answered [<%=evaluados%>] Missing [<%=pendientes%>] - <a href="comentarios?cursoCargaId=<%=cursoCargaId%>&nombreCurso=<%=curso.getNombreCurso()%>"><strong>Comments</strong>
	  		</a>
<%		}else{ %>
			Student Assessment = <b><%=promedio%></b> - Answered [<%=evaluados %>] Missing [<%=pendientes%>]
<%		} %> 
			</li>
			</ul>
		</td>
	</tr> 
<%	}%>      
</table>
</form>
</div>
<script>
	$(function () {
		$('[data-bs-toggle="tooltip"]').tooltip();
	});
</script>