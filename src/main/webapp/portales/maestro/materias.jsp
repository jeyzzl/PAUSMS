<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>
<%@ page import= "aca.vista.spring.CargaAcademica"%>
<%@ page import= "aca.carga.spring.Carga"%>


<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp"%>

<%
	String codigoPersonal			= (String) session.getAttribute("codigoPersonal");		
	String maestroNombre			= (String) request.getAttribute("maestroNombre");
	
	List<CargaAcademica> lisCursos	= (List<CargaAcademica>) request.getAttribute("lisCursos");
	
	// Mapa de las materias que tienen capturado el prontuario
	HashMap<String,CatCarrera> mapaCarreras 		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,CatModalidad> mapaModalidades 	= (HashMap<String,CatModalidad>) request.getAttribute("mapaModalidades");
	HashMap<String,String> mapaGraduandos			= (HashMap<String,String>) request.getAttribute("mapaGraduandos");
	HashMap<String,String> mapaAlumnos				= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapaAlumnosPorTipo		= (HashMap<String,String>) request.getAttribute("mapaAlumnosPorTipo");
	
	String cursoCargaId		= "";
	String numCurso 		= request.getParameter("numCurso")==null?"0":request.getParameter("numCurso");
	if (numCurso.equals("0") && lisCursos.size() > 0) numCurso = String.valueOf(lisCursos.size());
%>
<body>
<div class="container-fluid">
	<h2>Subjects <small class="text-muted fs-6">( <%=codigoPersonal%> | <%=maestroNombre%> )</small></h2>	 
	<input type="hidden" name="numCurso">
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary btn-sm" href="cursos">Return</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="text" class="form-control search-query" placeholder="Search" id="buscar" style="width:200px;">
	</div>	
	<table id="table" class="table table-bordered table-sm">	  
	<tr>
	<thead>
 		<th width="3%"><spring:message code="aca.Numero"/></th>
 		<th width="2%">Grad.</th>
 		<th width="20%">Subject</th>
 		<th width="5%">Semester/Trimester</th>
 		<th width="5%">Act</th>
 		<th width="5%">Gpo</th>
 		<th width="7%">Start</th>
 		<th width="7%">End</th> 		
 		<th width="5%">Plan</th>
 		<th width="10%">Modality</th>
 		<th width="5%" class="text-center">#Alum.</th>
 		<th width="5%" class="text-center">#IN</th>
 		<th width="5%" class="text-center">#AC</th>
 		<th width="5%" class="text-center">#NA</th>
 		<th width="5%" class="text-center">#BA</th>
 		<th width="5%" class="text-center">#RA</th>
 		<th width="5%" class="text-center">#CD</th>
 	</tr>
 	</thead>
 	<tbody>
<%	
	int row = 0;
	for (CargaAcademica curso : lisCursos){
		row++;
		
		cursoCargaId = curso.getCursoCargaId();
		
		String carreraNombre = "";
		if (mapaCarreras.containsKey(curso.getCarreraId())){
			carreraNombre = mapaCarreras.get(curso.getCarreraId()).getNombreCarrera();
		}
		
		String modalidadNombre = "";
		if (mapaModalidades.containsKey(curso.getModalidadId())){
			modalidadNombre = mapaModalidades.get(curso.getModalidadId()).getNombreModalidad();
		}		
		String titulo = "Classroom";
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
		
		String numAlumnos = "<span class='badge bg-secondary rounded-pill'>0</span>";
		if (mapaAlumnos.containsKey(curso.getCursoCargaId())){
			numAlumnos = "<span class='badge bg-dark rounded-pill'>"+mapaAlumnos.get(curso.getCursoCargaId())+"</span>";
			
		}
		String numIn = "<span class='badge bg-secondary rounded-pill'>0</span>";
		if (mapaAlumnos.containsKey(curso.getCursoCargaId()+"I")){
			numIn = "<span class='badge bg-dark rounded-pill'>"+mapaAlumnos.get(curso.getCursoCargaId()+"I")+"</span>";
		}
		
		String numAc = "<span class='badge bg-secondary rounded-pill'>0</span>";
		if (mapaAlumnosPorTipo.containsKey(curso.getCursoCargaId()+"1")){
			numAc = "<span class='badge bg-dark rounded-pill'>"+mapaAlumnosPorTipo.get(curso.getCursoCargaId()+"1")+"</span>";
		}
	
		String numNa = "<span class='badge bg-secondary rounded-pill'>0</span>";
		if (mapaAlumnosPorTipo.containsKey(curso.getCursoCargaId()+"2")){
			numNa = "<span class='badge bg-dark rounded-pill'>"+mapaAlumnosPorTipo.get(curso.getCursoCargaId()+"2")+"</span>";
		}
		
		String numBa = "<span class='badge bg-secondary rounded-pill'>0</span>";
		if (mapaAlumnosPorTipo.containsKey(curso.getCursoCargaId()+"3")){
			numBa = "<span class='badge bg-dark rounded-pill'>"+mapaAlumnosPorTipo.get(curso.getCursoCargaId()+"3")+"</span>";
		}
		
		String numRa = "<span class='badge bg-secondary rounded-pill'>0</span>";
		if (mapaAlumnosPorTipo.containsKey(curso.getCursoCargaId()+"4")){
			numRa = "<span class='badge bg-dark rounded-pill'>"+mapaAlumnosPorTipo.get(curso.getCursoCargaId()+"4")+"</span>";
		}
		
		String numCd = "<span class='badge bg-secondary rounded-pill'>0</span>";
		if (mapaAlumnosPorTipo.containsKey(curso.getCursoCargaId()+"5")){
			numCd = "<span class='badge bg-dark rounded-pill'>"+mapaAlumnosPorTipo.get(curso.getCursoCargaId()+"5")+"</span>";
		}
%>	 		
	<tr>
		<td valign="center" align="center">
			<span class="badge bg-info rounded-pill"><%=row%></span>
		</td>
		<td>
<% 		if (!graduandos.equals("0")){ out.print("<i class='fas fa-user-graduate' style='color:orange' title='"+graduandos+" graduates'></i> "); }%>
		</td>
  		<td> 				
			<b><%=curso.getNombreCurso()%></b>
		</td>
		<td><%=curso.getCiclo()%></td>	
		<td><%=curso.getCursoCargaId()%></td>
		<td><%=curso.getGrupo()%></td>
		<td><%=curso.getfInicio()%></td>
		<td><%=curso.getfFinal()%></td>
		<td><span data-bs-toggle="tooltip" data-bs-placement="top" title="<%=carreraNombre%>"><%=curso.getPlanId()%></span></td>
		<td><%=modalidadNombre%></td>
		<td class="text-center"><%=numAlumnos%></td>
		<td class="text-center"><%=numIn%></td>
		<td class="text-center"><%=numAc%></td>
		<td class="text-center"><%=numNa%></td>
		<td class="text-center"><%=numBa%></td>
		<td class="text-center"><%=numRa%></td>
		<td class="text-center"><%=numCd%></td>		
    </tr>
<%	}%>
	</tbody>
	</table>
</div>
</body>
<script type="text/javascript" src="../../js/search.js"></script>
<script>	
	$('#buscar').search();
		
	var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
	var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
  	return new bootstrap.Tooltip(tooltipTriggerEl)
	});

	$(function () {
  		$('[data-bs-toggle="tooltip"]').tooltip();
  		$('[data-bs-toggle="popover"]').popover();
	});
</script>
</html>