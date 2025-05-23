<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Date" %>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<head></head>
<%
	List<AlumPersonal> lisAlumnos 		= (List<AlumPersonal>)request.getAttribute("lisAlumnos");
	List<MapaPlan> lisPlanes			= (List<MapaPlan>)request.getAttribute("lisPlanes");
	HashMap<String,String> mapaCursos 	= (HashMap<String,String>)request.getAttribute("mapaCursos");

	int counter = 0;
	String fechaNacimiento = "";
%>
<body>
<div class="container-fluid">
	<h2>Registered students</h2>
	<div class="alert alert-info" role="alert">
		<a href="cursos" class="btn btn-primary">Return</a>
	</div>
	<table id="table" class="table table" >
	<thead>
	<tr>
		<th>No.</th>
		<th>Code</th>
		<th>Name</th>
		<th>Birthday</th>
		<th>Email</th>
		<th>Status</th>
		<th>Num. of Courses</th>
		
	</tr>
	</thead>
	<tbody>
<%	for(AlumPersonal alumno : lisAlumnos){ 
		counter++;			
		fechaNacimiento = alumno.getFNacimiento().substring(0,10);
%>
	<tr>
		<td><%=counter%></td>
		<td><%=alumno.getCodigoPersonal()%></td>
		<td><%=alumno.getNombreLegal()%></td>
		<td><%=fechaNacimiento%></td>
		<td><%=alumno.getEmail()%></td>
		<td><%=alumno.getEstado()%></td>
		<td>
		<% 
			String planes = "";
			for (MapaPlan mapaPlan : lisPlanes ){		
				if (mapaCursos.containsKey(alumno.getCodigoPersonal()+"-"+mapaPlan.getPlanId())){
					String numCursos = mapaCursos.get(alumno.getCodigoPersonal()+"-"+mapaPlan.getPlanId());					
					planes += "&nbsp;&nbsp;<span class='fs-6' title='"+mapaPlan.getNombrePlan()+"'>"+mapaPlan.getPlanId()+"</span>-<span class='badge bg-success rounded-pill'>"+numCursos+"</span>";
				}
			}
			out.print(planes);
		%>				
		</td>			
	</tr>
<% }%>
	</tbody>
	</table>
</div>	
</body>