<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.alumno.spring.AlumPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	String facultad				= (String) session.getAttribute("fac");
	String planId				= request.getParameter("planId");	
	String grado				= (String) request.getAttribute("grado");	

	List<AlumPlan> lisAlumnosPromedios					= (List<AlumPlan>) request.getAttribute("lisAlumnosPromedios");
	HashMap<String, String> mapaAlumnoPlanPromedio		= (HashMap<String, String>) request.getAttribute("mapaAlumnoPlanPromedio");
	HashMap<String, String> mapaMateriasPorAlumno		= (HashMap<String, String>) request.getAttribute("mapaMateriasPorAlumno");
	HashMap<String, String> mapaInscritos				= (HashMap<String,String>)request.getAttribute("mapaInscritos");
	HashMap<String, String> mapaSumCreditos				= (HashMap<String,String>)request.getAttribute("mapaSumCreditos");
	HashMap<String, String> mapaSumCreditosPorGPA		= (HashMap<String,String>)request.getAttribute("mapaSumCreditosPorGPA");

	java.text.DecimalFormat getFormato 					= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
%>

<script>
    function cambioPeriodo(){
		var grado = document.getElementById("Grado").value;
		var planId = document.getElementById("PlanId").value;

		location.href = "alumnos?planId="+planId+"&Grado="+grado;
	}
</script>

<div class="container-fluid">
	<h2><%=planId%> Students</h2>
	<div class="alert alert-info d-flex">
		<a class="btn btn-primary" href="listado?planId=<%=planId%>&facultad=<%=facultad%>"><i class="fas fa-arrow-left"></i></a>&nbsp;
		<input type="hidden" value="<%=planId%>" name="PlanId" id="PlanId">
		<select class="form-select ms-2" style="width: 10rem;" name="Grado" id="Grado" onchange="javascript:cambioPeriodo();">
			<option value="0" <%=grado.equals("0")?"selected":""%>>All</option>
			<option value="1" <%=grado.equals("1")?"selected":""%>>Year 1</option>
			<option value="2" <%=grado.equals("2")?"selected":""%>>Year 2</option>
			<option value="3" <%=grado.equals("3")?"selected":""%>>Year 3</option>
			<option value="4" <%=grado.equals("4")?"selected":""%>>Year 4</option>
		</select>
	</div>
 		<table class="table table-bordered">
	<thead class="table-info">
	<tr> 
		<th width="5%"><spring:message code="aca.Numero"/></th>
		<th width="10%">Start Date</th>
		<th width="10%"><spring:message code="aca.Matricula"/></th>
		<th width="30%"><spring:message code="aca.Nombre"/></th>		
		<th width="10%">Cycle</th>
		<th width="5%">Enrolled</th>
		<th width="10%">Sem</th>
		<th width="10%">Subjects</th>
		<th width="10%" class="right">GPA</th>
	 </tr>
	</thead>
	<tbody>	
<%	
	int row	= 0;
	for(AlumPlan alumno : lisAlumnosPromedios){
		row++;
		
		String nombreAlumno = "-";
		if(mapaAlumnoPlanPromedio.containsKey(alumno.getCodigoPersonal())){
			nombreAlumno = mapaAlumnoPlanPromedio.get(alumno.getCodigoPersonal());
		}
		
		String materias = "-";
		if(mapaMateriasPorAlumno.containsKey(alumno.getCodigoPersonal())){
			materias = mapaMateriasPorAlumno.get(alumno.getCodigoPersonal());
		}
		
		String esInscrito = "<span class='badge bg-warning rounded-pill'>NO</span>";
		if (mapaInscritos.containsKey(alumno.getCodigoPersonal())){
			esInscrito = "<span class='badge bg-dark rounded-pill'>YES</span>";
		}

		String sumCreditosGPA = "0";
		if(mapaSumCreditosPorGPA.containsKey(alumno.getCodigoPersonal())){
			sumCreditosGPA = mapaSumCreditosPorGPA.get(alumno.getCodigoPersonal());
		}

		String sumCreditos = "0";
		if(mapaSumCreditos.containsKey(alumno.getCodigoPersonal())){
			sumCreditos = mapaSumCreditos.get(alumno.getCodigoPersonal());
		}
		
		float promedio = Float.valueOf(sumCreditosGPA) / Float.valueOf(sumCreditos);
%>
 	<tr>							
   		<td class="center"><%=row%></td>
   		<td><%=alumno.getPrimerMatricula()%></a></td>
   		<td><%=alumno.getCodigoPersonal()%></td>
	    <td><%=nombreAlumno%></a></td>	    
	    <td><%=alumno.getGrado()%></a></td>
	    <th class="text-center"><%=esInscrito%></th>
	    <td><%=alumno.getCiclo()%></a></td>
	    <td><%=materias%></a></td>
	    <td class="right"><%=getFormato.format(promedio)%></td>						    
	</tr>
<%	} %>	
	</tbody>
	</table>	
</div>	
