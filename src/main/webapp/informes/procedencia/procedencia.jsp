<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatPais"%>
<%@ page import="aca.vista.spring.Inscritos"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>
<%@ page import="aca.catalogo.spring.CatModalidad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	List<Inscritos> listaInscritos	= (List<Inscritos>)request.getAttribute("listaInscritos");

	HashMap<String, CatPais> mapaPaises 			= (HashMap<String, CatPais>) request.getAttribute("mapaPaises");
	HashMap<String, CatCarrera> mapaCarreras 		= (HashMap<String, CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String, CatModalidad> mapaModalidades 	= (HashMap<String, CatModalidad>) request.getAttribute("mapaModalidades");
	HashMap<String, String> mapaEstados 			= (HashMap<String, String>) request.getAttribute("mapaEstados");
	
%>
<body>
<div class="container-fluid">
	<h2>Lista de inscritos por paises</h2>
	<div class="alert alert-info">
		<a href="paises" class="btn btn-primary">Paises representados</a>
	</div>	
	<h4>Muestra todos los alumnos inscritos incluyendo todas las modalidades</h4>
	<table class="table table-bordered table-sm">	
	<thead class="table-info">
		<tr>
			<th><spring:message code="aca.Numero"/></th>
			<th><spring:message code="aca.Matricula"/></th>
			<th><spring:message code="aca.Nombre"/></th>
			<th>País</th>
			<th width="15%"><spring:message code="aca.Estado"/></th>
			<th><spring:message code="aca.Carga"/></th>
			<th><spring:message code="aca.Bloque"/></th>
			<th><spring:message code="aca.Modalidad"/></th>
			<th><spring:message code="aca.Carrera"/></th>
		</tr>
	</thead>
	<tbody>
<%
	int row		= 0;
	for(Inscritos alumno : listaInscritos){
		row++;
		String modalidad = "-";
		if (mapaModalidades.containsKey( alumno.getModalidadId())){
			modalidad = mapaModalidades.get( alumno.getModalidadId()).getNombreModalidad();
		}
		
		String carrera = "-";
		if (mapaCarreras.containsKey( alumno.getCarreraId() )){
			carrera = mapaCarreras.get( alumno.getCarreraId()).getNombreCarrera();
		}
		
		String paisNombre = "-";
		if (mapaPaises.containsKey(alumno.getPaisId())){
			paisNombre = mapaPaises.get(alumno.getPaisId()).getNombrePais();
		}
		
		String estado = "";
		if (mapaEstados.containsKey( alumno.getPaisId()+alumno.getEstadoId())){
			estado = mapaEstados.get( alumno.getPaisId()+alumno.getEstadoId());
		}	
%>
		<tr class="tr2" >
			<td align="center"><%=row%></td>
			<td><%=alumno.getCodigoPersonal()%></td>
			<td><%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%></td>
			<td><%=paisNombre%></td>
			<td><%=estado%></td>
			<td><%=alumno.getCargaId()%></td>
			<td><%=alumno.getBloqueId()%></td>
			<td><%=modalidad%></td>			
			<td><%=carrera%></td>
		</tr>
<%			
	}//fin del for de Alumnos		
%>		
	</tbody>
	</table>
</div>	
</body>