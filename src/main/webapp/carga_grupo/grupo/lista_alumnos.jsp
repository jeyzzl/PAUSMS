<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.vista.spring.AlumnoCurso"%>
<%@ page import= "aca.vista.spring.CargaAcademica"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%
	String institucion 				= (String) request.getAttribute("institucion");	
	String cargaId	 				= (String) request.getAttribute("cargaId");
	String cursoCargaId				= (String) request.getAttribute("cursoCargaId");	
	String carreraId				= (String) request.getAttribute("carreraId");
	String planId					= (String) request.getAttribute("planId");
	CargaAcademica cargaAcademica	= (CargaAcademica) request.getAttribute("cargaAcademica");

	List<AlumnoCurso> lisAlumnos	= (List<AlumnoCurso>) request.getAttribute("lisAlumnos");
	HashMap<String, String> mapAlumnosMateria = (HashMap<String, String>) request.getAttribute("mapAlumnosMateria");
%>
<div class="container-fluid">
	<h2>Registered Students<small class="text-muted fs-5">( <%=cargaAcademica.getNombreCurso()%> - <%=cargaAcademica.getCodigoPersonal()%> <%=cargaAcademica.getNombre()%>) - 
		<span class='badge bg-dark'><%=planId%></span></small>
	</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="grupo?CarreraId=<%=carreraId%>&PlanId=<%=planId%>"><i class="fas fa-arrow-left"></i></a>
	</div>
	<div class="alert alert-info">
		Id Number: <span class='badge bg-dark'><%=cursoCargaId%></span>&nbsp; &nbsp; Base Subject: <%=cargaAcademica.getCursoId()%> - <%=cargaAcademica.getNombreCurso()%>
	</div>
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
		<th><spring:message code="aca.Numero" /></th>
		<th><spring:message code="aca.Matricula" /></th>
		<th><spring:message code="aca.Nombre" /></th>
		<th><spring:message code="aca.Curso" /></th>
		<th>Mark</th>
		<th><spring:message code="aca.Tipo" /></th>
		<th><spring:message code="aca.Comentario" /></th>
	</tr>
	</thead>
<%
	int row	= 0;
	for (AlumnoCurso alumno : lisAlumnos){
		row++;
		String nombreTipo="-";
		if (alumno.getTipoCalId().equals("I")) nombreTipo = "Enrolled";
		if (alumno.getTipoCalId().equals("M")) nombreTipo = "Assigned";
		if (alumno.getTipoCalId().equals("1")) nombreTipo = "Credited";
		if (alumno.getTipoCalId().equals("2")) nombreTipo = "Failed";
		if (alumno.getTipoCalId().equals("3")) nombreTipo = "Drop";
		if (alumno.getTipoCalId().equals("4")) nombreTipo = "Absent";
		if (alumno.getTipoCalId().equals("5")) nombreTipo = "Deferred";
		
		String cursoAlumno = "<span>"+alumno.getCursoId()+"</span>";
		if (alumno.getCursoId().contains(planId)){
			cursoAlumno = "<span class='badge bg-dark'>"+alumno.getCursoId()+"</span>";
		}else{
			cursoAlumno = "<span class='badge bg-warning'>"+alumno.getCursoId()+"</span>";
		}
		
		String nombreAlumno = "";
		if(mapAlumnosMateria.containsKey(alumno.getCodigoPersonal())){
			nombreAlumno = mapAlumnosMateria.get(alumno.getCodigoPersonal());
		}
%>
	<tr>		    
	    <td align="CENTER" height="20"><%= row %></td>    		
	    <td><b><%=alumno.getCodigoPersonal()%></b></td>
	    <td height="20"><%=nombreAlumno%></td>
	    <td height="20"><%=cursoAlumno%></td>
	    <td height="20"><%=alumno.getNota()%></td>		    
	    <td height="20"><%=nombreTipo%></td>
	    <td height="20"> 
<%		
		if (!alumno.getCursoId().equals(alumno.getCursoId2())){
			out.print( alumno.getNombreCurso());
		}
%>
	    </td>
				
	</tr>
<%	
	}
%>
	</table>
</div>