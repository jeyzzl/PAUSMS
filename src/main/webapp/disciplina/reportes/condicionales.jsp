<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.disciplina.spring.CondAlumno"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%		
	String sPeriodo			= (String)request.getAttribute("periodoId");

	int numUnidades        	= 0;
	int numElogios	  		= 0;
	int numFaltas    		= 0;
	
	List<CondAlumno> lisAlumnos 				= (List<CondAlumno>) request.getAttribute("lisAlumnos");
	HashMap<String,AlumPlan> mapaPlanAlumno		= (HashMap<String,AlumPlan>) request.getAttribute("mapaPlanAlumno");
	HashMap<String,MapaPlan> mapaPlanes			= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String,CatFacultad> mapaFacultades	= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,String> mapaCantidades		= (HashMap<String,String>) request.getAttribute("mapaCantidades");
	HashMap<String,String> mapaAlumnos			= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
%>
<div class="container-fluid">
	<h2>Report of Probationary Students</h2>
	<div class="alert alert-info">
		<a href="menu" class="btn btn-primary btn-sm"><i class="fas fa-arrow-left icon-white"></i></a>
	</div>
	<table class="table table-sm table-striped table-bordered">
	<tr> 
    	<th width="5%"><spring:message code="aca.Numero"/></th>
    	<th width="7%" class="text-center"><spring:message code="aca.Matricula"/></th>
    	<th width="30%"><spring:message code="aca.Nombre"/></th>
    	<th width="10%">School</th>
        <th width="30%">Course</th>
 		<th width="6%" class="text-end">Misconducts</th>
 		<th width="6%" class="text-end">Praises</th>
 		<th width="6%" class="text-end">Balance</th>
  	</tr>  	
<%	
	int row					= 0;
	for(CondAlumno alumno : lisAlumnos){
		row++;
		
		String alumnoNombre = "";
		if (mapaAlumnos.containsKey(alumno.getMatricula())){
			alumnoNombre = mapaAlumnos.get(alumno.getMatricula());
		}
		
		String planId 			= "0";
		String carreraId 		= "0";
		String carreraNombre 	= "-";
		String facultadId 		= "0";
		String facultadNombre	= "-";
		if (mapaPlanAlumno.containsKey(alumno.getMatricula())){
			planId =  mapaPlanAlumno.get(alumno.getMatricula()).getPlanId();
			if (mapaPlanes.containsKey(planId)){
				carreraId 		= mapaPlanes.get(planId).getCarreraId();
				carreraNombre 	= mapaPlanes.get(planId).getCarreraSe();
				if (mapaCarreras.containsKey(carreraId)){					
					facultadId 		= mapaCarreras.get(carreraId).getFacultadId();
					if (mapaFacultades.containsKey(facultadId)){
						facultadNombre = mapaFacultades.get(facultadId).getNombreCorto();
					}
				}
			}
		}
		
		String elogios = "0"; 
		if (mapaCantidades.containsKey(alumno.getMatricula()+"C")){
			elogios = mapaCantidades.get(alumno.getMatricula()+"C"); 
		}
		
		String faltas = "0"; 
		if (mapaCantidades.containsKey(alumno.getMatricula()+"D")){
			faltas = mapaCantidades.get(alumno.getMatricula()+"D"); 
		}	
		
		numElogios 	= Integer.parseInt(elogios);
		numFaltas 	= Integer.parseInt(faltas);
		if (numElogios >= numFaltas) numUnidades = 0; else numUnidades = numFaltas - numElogios;
%>
	<tr> 
   		<td><%=row%></td>
    	<td><%=alumno.getMatricula()%></td>
    	<td><%=alumnoNombre%></td>
    	<td><%=facultadNombre%></td>
    	<td><%=carreraNombre%></td>
    	<td class="text-end"><%=numFaltas%></td>
    	<td class="text-end"><%=numElogios%></td>
    	<td class="text-end"><%=numUnidades%></td>
  	</tr>
<%	} %>  
	</table>
</div>