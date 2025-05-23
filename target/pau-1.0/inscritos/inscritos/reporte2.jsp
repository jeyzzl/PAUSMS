<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.spring.Inscritos"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatReligion"%>
<%@page import="aca.catalogo.spring.CatTipoAlumno"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatEstado"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	int numInternos			= 0;
	int numExternos			= 0;
	int numHombres			= 0;
	int numMujeres			= 0;
	
	String cargas 			= request.getParameter("cargas");
	String modalidades 		= request.getParameter("modalidades");
	String fechaIni			= (String) session.getAttribute("fechaIni");
	String fechaFin			= (String) session.getAttribute("fechaFin");
	
	List<Inscritos> listaInscritos 					= (List<Inscritos>) request.getAttribute("listaInscritos");
	
	HashMap<String,CatFacultad> mapaFacultades 		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras 		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,CatReligion> mapaReligiones 		= (HashMap<String,CatReligion>) request.getAttribute("mapaReligiones");
	HashMap<String,CatTipoAlumno> mapaTipoAlumno 	= (HashMap<String,CatTipoAlumno>) request.getAttribute("mapaTipoAlumno");
	HashMap<String, CatModalidad> mapaModalidades 	= (HashMap<String, CatModalidad>) request.getAttribute("mapaModalidades");
	HashMap<String, CatPais> mapaPaises 			= (HashMap<String, CatPais>) request.getAttribute("mapaPaises");
	HashMap<String, CatEstado> mapaEstados 			= (HashMap<String, CatEstado>) request.getAttribute("mapaEstados");
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
			<th><spring:message code="aca.Facultad"/></th>
			<th><spring:message code="aca.Carrera"/></th>
			<th><spring:message code="aca.Carga"/></th>
			<th><spring:message code="aca.Matricula"/></th>
			<th><spring:message code="aca.Nombre"/></th>
			<th>Surname</th>
			<th><spring:message code="aca.Genero"/></th>
			<th><spring:message code="aca.Residencia"/></th>
			<th>Religion</th>
			<th><spring:message code="aca.Tipo"/></th>
			<th><spring:message code="aca.Modalidad"/></th>
			<th><spring:message code="aca.Pais"/></th>
			<th><spring:message code="aca.Estado"/></th>
		</tr>
	</thead>
	<tbody>
<%
		int row=0;
		for(Inscritos inscritos : listaInscritos){
			row++;
			
			String nombreFacultad = "";
			if(mapaFacultades.containsKey(inscritos.getCarreraId().substring(0,3))){
				nombreFacultad = mapaFacultades.get(inscritos.getCarreraId().substring(0,3)).getNombreFacultad();
			}
			
			String nombreCarrera = "";
			if(mapaCarreras.containsKey(inscritos.getCarreraId())){
				nombreCarrera = mapaCarreras.get(inscritos.getCarreraId()).getNombreCarrera();
			}
			
			String nombreReligion = "";
			if(mapaReligiones.containsKey(inscritos.getReligionId())){
				nombreReligion = mapaReligiones.get(inscritos.getReligionId()).getNombreReligion();
			}
			
			String tipoAlumno = "";
			if(mapaTipoAlumno.containsKey(inscritos.getTipoAlumnoId())){
				tipoAlumno = mapaTipoAlumno.get(inscritos.getTipoAlumnoId()).getNombreTipo();
			}
			
			String nombreModalidad = "";
			if(mapaModalidades.containsKey(inscritos.getModalidadId())){
				nombreModalidad = mapaModalidades.get(inscritos.getModalidadId()).getNombreModalidad();
			}
			
			String nombrePais = "";
			if(mapaPaises.containsKey(inscritos.getPaisId())){
				nombrePais = mapaPaises.get(inscritos.getPaisId()).getNombrePais();
			}
			
			String nombreEstado = "";
			if(mapaEstados.containsKey(inscritos.getPaisId()+""+inscritos.getEstadoId())){
				nombreEstado = mapaEstados.get(inscritos.getPaisId()+""+inscritos.getEstadoId()).getNombreEstado();
			}
				
%>
			<tr> 
				<td><%=row%></td>
				<td><%=nombreFacultad%></td>
				<td><%=nombreCarrera%></td>
				<td><%=inscritos.getCargaId()%></td>
				<td><%=inscritos.getCodigoPersonal()%></td>
				<td><%=inscritos.getNombre()%></td>
				<td><%=inscritos.getApellidoPaterno()+" "+inscritos.getApellidoMaterno()%></td>
				<td><%=inscritos.getSexo()%></td>
				<td><%=inscritos.getResidenciaId()%></td>
				<td><%=nombreReligion%></td>
				<td><%=tipoAlumno%></td>
				<td><%=nombreModalidad%></td>
				<td><%=nombrePais%></td>
				<td><%=nombreEstado%></td>
			</tr>
<%
			if(inscritos.getResidenciaId().equals("I")) numInternos++; else numExternos++;
			if(inscritos.getSexo().equals("M")) numHombres++; else numMujeres++;
		}
%>
		</tbody>
		<tfoot>
			<tr> 
			    <th colspan="3">Enrolled:<%=row%></th>
			    <th colspan="3">Boarding: <%=numInternos%></th>
			    <th colspan="3">Off-Campus: <%=numExternos%></th>
			    <th colspan="3">Male: <%=numHombres%></th>
			    <th colspan="2">Female: <%=numMujeres%></th>
			</tr>
		</tfoot>	
	</table>
</div>