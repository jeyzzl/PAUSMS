<%@page import="java.util.List"%>
<%@page import= "java.util.HashMap"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<% 
	String cargaId				= (String) request.getAttribute("cargaId");
	String carreraId			= (String) request.getAttribute("carreraId");
	String nombreCarrera		= (String) request.getAttribute("nombreCarrera");
	String codigoId				= "X";
	
	List<AlumnoCurso> lisReprobados 					= (List<AlumnoCurso>) request.getAttribute("lisReprobados");
	HashMap<String, AlumPersonal> mapAlumnos			= (HashMap<String, AlumPersonal>) request.getAttribute("mapAlumnos");
	HashMap<String, String> mapaReprobadasEnCarga		= (HashMap<String, String>) request.getAttribute("mapaReprobadasEnCarga");	
%>
<body>
<div class="container-fluid">
	<h2>Alumnos Reprobados<small class="text-muted fs-4"> ( <%=cargaId%> - <%=nombreCarrera%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="reprobados?PeriodoId=<%=cargaId.substring(0,4)%>&CargaId=<%=cargaId%>"><i class="fas fa-arrow-left"></i></a>
	</div>	
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
	    <th width="3%"><spring:message code="aca.Numero"/></th>
	    <th width="5%"><spring:message code="aca.Matricula"/></th>
	    <th width="15%"><spring:message code="aca.Nombre"/></th>
	    <th width="3%">N° Materias</th>
	</tr>
	</thead>
<% 	int cont = 1;
	for(AlumnoCurso alumCurso : lisReprobados){
		
		String nombreAlumno = "";
		if (mapAlumnos.containsKey(alumCurso.getCodigoPersonal())){
			AlumPersonal alumnoPersonal = mapAlumnos.get(alumCurso.getCodigoPersonal());
			nombreAlumno = alumnoPersonal.getNombre()+" "+alumnoPersonal.getApellidoPaterno()+" "+alumnoPersonal.getApellidoMaterno();
		}
			
		if(!alumCurso.getCodigoPersonal().equals(codigoId)){
			codigoId=alumCurso.getCodigoPersonal();
		
		String numReprobadas = "0";
		if(mapaReprobadasEnCarga.containsKey(alumCurso.getCodigoPersonal())){
			numReprobadas = mapaReprobadasEnCarga.get(alumCurso.getCodigoPersonal());
		}
		
	%>
	<tr class="tr2">
	    <td align="center"><%=cont%></td>
	    <td><%=alumCurso.getCodigoPersonal()%></td>
	    <td><a href="materias?codigoId=<%=codigoId%>&CarreraId=<%=carreraId%>&CargaId=<%=cargaId%>"><%=nombreAlumno%></a></td>
	    <td align="center"><%=numReprobadas%></td>
	</tr>
<% 
			cont++;
		}
	}%>
	</table>
</div>	
</body>