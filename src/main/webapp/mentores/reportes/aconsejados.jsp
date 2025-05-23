<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.mentores.spring.MentAlumno"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");	

	String periodoId			= request.getParameter("PeriodoId")==null?(String) session.getAttribute("ciclo"):request.getParameter("PeriodoId");
	String carreraId			= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
	String carreraNombre		= (String)request.getAttribute("carreraNombre");
	
	// Lista de aconsejados
	List<MentAlumno> lisAlumnos				= (List<MentAlumno>)request.getAttribute("lisAlumnos");
	HashMap<String,String> mapaMaestros 	= (HashMap<String,String>)request.getAttribute("mapaMaestros");
	HashMap<String,String> mapaAlumnos	 	= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
%>
<div class="container-fluid">
	<h2>Lista de Aconsejados<small class="text-muted fs-4">( Periodo:<%=periodoId%> &nbsp; Carrera:<%=carreraId%> - <%=carreraNombre%>)</small></h2>
	<div class="alert alert-info">
	  	<a href="historico" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<table id="table" class="table table-sm table-bordered">
		<thead class="table-info">	 
			<tr>
				<th>#</th>  	
				<th>Clave</th>		
		  		<th>Mentor</th>
				<th>Matricula</th>
				<th>Alumno</th>
		  	</tr>
		  </thead>
	<%	
		String codigoAlumno = "X";
		int row = 0;
		for (MentAlumno alumno : lisAlumnos){
			row++;
			if (!codigoAlumno.equals(alumno.getCodigoPersonal())){
				codigoAlumno = alumno.getCodigoPersonal();
				
				String alumnoNombre	= "-";
				if (mapaAlumnos.containsKey(alumno.getCodigoPersonal())){
					alumnoNombre = mapaAlumnos.get(alumno.getCodigoPersonal());
				}
				
				String maestroNombre	= "-";
				if (mapaMaestros.containsKey(alumno.getMentorId())){
					maestroNombre = mapaMaestros.get(alumno.getMentorId());
				}
	%>  				
  	<tr> 
  		<td><%=row%></td>	    	
  		<td><%=alumno.getMentorId()%></td>
	   	<td><%=maestroNombre%></td>
	   	<td><%=alumno.getCodigoPersonal()%></td>
	   	<td><%=alumnoNombre%></td>						
  	</tr>
	<%
			}
		}
	%>
	</table>
</div>