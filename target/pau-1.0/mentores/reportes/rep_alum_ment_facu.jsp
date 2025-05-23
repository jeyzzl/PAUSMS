<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.mentores.spring.MentAlumno"%>
<%@ page import="aca.catalogo.spring.CatFacultad"%> 
<%@ page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
<%	
	String facultad			= request.getParameter("facultad");
	String facultadNombre	= (String)request.getAttribute("facultadNombre");
	String fechaAconsejado 	= request.getParameter("FechaAconsejado")==null?aca.util.Fecha.getHoy():request.getParameter("FechaAconsejado");	
	
	// Lista de mentores en una facultad
	List<String> lisMentores					= (List<String>) request.getAttribute("lisMentores");
	// Lista de aconsejados	
	List<MentAlumno> lisAconsejados				= (List<MentAlumno>) request.getAttribute("lisAconsejados");	
	// MAP DE CARRERAS
	HashMap<String,CatCarrera> mapaCarreras 	= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");	
	// MAP DE ALUMNOS
	HashMap<String,String> mapaAlumnos 			= (HashMap<String,String>)request.getAttribute("mapaAlumnos"); 	
	// MAP DE Mentores
	HashMap<String,String> mapaMentores 		= (HashMap<String,String>)request.getAttribute("mapaMentores");

	HashMap<String,String> mapaSemestreAlumno 	= (HashMap<String,String>)request.getAttribute("mapaSemestreAlumno");
	
	String carrera = "0" , alumno = "0";
%>
<div class="container-fluid">
	<h2>Listado de Mentores y Alumnos <small class="text-muted fs-4">(<%= facultadNombre %>)</small></h2>
	<form name="forma" action="rep_alum_ment_facu" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<a href="facultad?Opcion=1" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
		&nbsp;&nbsp;
		<input id="FechaAconsejado" name="FechaAconsejado" class="form-control" style="margin:0;" type="text" maxlength="10" data-date-format="dd/mm/yyyy" value="<%=fechaAconsejado %>" />
		<input id="facultad" name="facultad" type="hidden" value="<%=facultad%>" />
		<a onclick="document.forma.submit();" class="btn btn-info"><i class="icon-calendar icon-white"></i> Mostrar</a>
	</div>
	</form>	
	<table id="table" class="table table-sm table-bordered">
<%		
	for(String mentor : lisMentores){
		
		String mentorNombre = "0";
		if(mapaMentores.containsKey(mentor)){
			mentorNombre = mapaMentores.get(mentor);
		}
%>		
	<thead>	
	<tr><td colspan="6"><h3><%=mentorNombre %></h3></td></tr>
	</thead>
	<thead class="table-info">	
	<tr>
		<th>#</th>
		<th><spring:message code="aca.Matricula"/></th>
		<th><spring:message code="aca.Alumno"/></th>
		<th>Semestre</th>
		<th><spring:message code="aca.Carrera"/></th>
		<th>Fecha Inicio:</th>
		<th>Fecha Final:</th>
	</tr>
	</thead>
<%	
		int i = 0;
		for (MentAlumno menAlumno: lisAconsejados){
			
			if ( menAlumno.getMentorId().equals(mentor)){
				if(mapaAlumnos.containsKey(menAlumno.getCodigoPersonal())){
					alumno = mapaAlumnos.get(menAlumno.getCodigoPersonal());
				}
				if(mapaCarreras.containsKey(menAlumno.getCarreraId())){
					carrera = mapaCarreras.get(menAlumno.getCarreraId()).getNombreCarrera();
				}
				
				String semestre = "";
				if(mapaSemestreAlumno.containsKey(menAlumno.getCodigoPersonal())){
					semestre = mapaSemestreAlumno.get(menAlumno.getCodigoPersonal());
				}
%>
	<tr>
		<td><%=++i %></td>
		<td><%= menAlumno.getCodigoPersonal() %></td>
		<td><%=alumno %></td>
		<td><%=semestre%></td>
		<td><%= carrera %></td>
		<td><%= menAlumno.getFechaInicio() %></td>
		<td><%= menAlumno.getFechaFinal() %></td>
	</tr>				
<%								
			}
			
		}
	}	
%>
	</table>
	<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
	<script type="text/javascript">
		jQuery('#FechaAconsejado').datepicker();
	</script>
</div>	