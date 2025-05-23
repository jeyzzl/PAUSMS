<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.spring.EstInscritos"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatTipoAlumno"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	int con				= 0;
	int nInternos		= 0;
	int nExternos		= 0;
	int nHombres		= 0;
	int nMujeres		= 0;
	
	String cargas 		= request.getParameter("cargas");
	String modalidades 	= request.getParameter("modalidades");
	String fechaIni		= (String) session.getAttribute("fechaIni");
	String fechaFin		= (String) session.getAttribute("fechaFin");
	
	List<EstInscritos> lisEstInscritos 				= (List<EstInscritos>) request.getAttribute("lisEstInscritos");
	
	HashMap<String, AlumPersonal> mapAlumnos 		= (HashMap<String, AlumPersonal>) request.getAttribute("mapAlumnos");
	HashMap<String,CatTipoAlumno> mapaTipoAlumno 	= (HashMap<String,CatTipoAlumno>) request.getAttribute("mapaTipoAlumno");
	HashMap<String, CatModalidad> mapaModalidades 	= (HashMap<String, CatModalidad>) request.getAttribute("mapaModalidades");
	HashMap<String, CatPais> mapaNacionalidades 	= (HashMap<String, CatPais>) request.getAttribute("mapaNacionalidades");
%>
<div class="container-fluid">
	<h2>Enrolled Students</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="inscritos?Accion=1">Return</a>
	</div>
	<table class="table table-sm table-bordered table-striped">
	<thead class="table-info">
		<tr> 
		    <th><spring:message code="aca.Numero"/></th>
			<th><spring:message code="aca.Matricula"/></th>
			<th><spring:message code="aca.Nombre"/></th>
			<th>Surname</th>
			<th><spring:message code="aca.Edad"/></th>
			<th>Birth Date</th>
			<th><spring:message code="aca.Genero"/></th>
			<th><spring:message code="aca.Residencia"/></th>
			<th>Religion</th>
			<th><spring:message code="aca.Tipo"/></th>
			<th><spring:message code="aca.Carga"/></th>
			<th><spring:message code="aca.Modalidad"/></th>
			<th><spring:message code="aca.Plan"/></th>
			<th>Nationality</th>
		</tr>
	</thead>
	<tbody>
<%
		int row=0;
		for(EstInscritos estInscritos : lisEstInscritos){
			row++;
			
			AlumPersonal alumPersonal = new AlumPersonal();
			if(mapAlumnos.containsKey(estInscritos.getCodigoPersonal())){
				alumPersonal = mapAlumnos.get(estInscritos.getCodigoPersonal());
			}
			
			String tipoAlumno = "";
			if(mapaTipoAlumno.containsKey(estInscritos.getTipo())){
				tipoAlumno = mapaTipoAlumno.get(estInscritos.getTipo()).getNombreTipo();
			}
			
			String nombreModalidad = "";
			if(mapaModalidades.containsKey(estInscritos.getModalidad())){
				nombreModalidad = mapaModalidades.get(estInscritos.getModalidad()).getNombreModalidad();
			}
			
			String nombreNacionalidad = "";
			if(mapaNacionalidades.containsKey(alumPersonal.getNacionalidad())){
				nombreNacionalidad = mapaNacionalidades.get(alumPersonal.getNacionalidad()).getNacionalidad();
			}
				
%>
			<tr> 
				<td><%=row%></td>
				<td><%=estInscritos.getCodigoPersonal()%></td>
				<td><%=alumPersonal.getNombre()%></td>
				<td><%=alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno()%></td>
				<td><%=estInscritos.getEdad()%></td>
				<td><%=alumPersonal.getFNacimiento()%></td>
				<td><%=estInscritos.getSexo()%></td>
				<td><%=estInscritos.getResidenciaId()%></td>
				<td><%=estInscritos.getNombreReligion()%></td>
				<td><%=tipoAlumno%></td>
				<td><%=estInscritos.getCargaId()%></td>
				<td><%=nombreModalidad%></td>
				<td><%=estInscritos.getPlanId()%></td>
				<td><%=nombreNacionalidad%></td>
			</tr>
<%
			if (estInscritos.getResidenciaId().equals("I")) nInternos++; else nExternos++;
			if (estInscritos.getSexo().equals("M")) nHombres++; else nMujeres++;
		}
%>	
	</tbody>
	<tfoot>
		<tr>
		    <th colspan="3">Enrolled:<%=row%></th>
		    <th colspan="3">Boarding: <%=nInternos%></th>
		    <th colspan="3">Off-Campus: <%=nExternos%></th>
		    <th colspan="3">Male: <%=nHombres%></th>
		    <th colspan="2">Female: <%=nMujeres%></th>
	</tr>	
	</tfoot>
	</table>
</div>