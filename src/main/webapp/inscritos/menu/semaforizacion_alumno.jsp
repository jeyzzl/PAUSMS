<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>

<%@ page import="aca.catalogo.spring.CatEstado"%>
<%@ page import="aca.catalogo.spring.CatPais"%>
<%@ page import="aca.alumno.spring.AlumUbicacion"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%	
	List<AlumUbicacion> listEstadistica 		= (List<AlumUbicacion>) request.getAttribute("listEstadistica");

	HashMap<String,CatPais> mapaPaises 			= (HashMap<String,CatPais>)request.getAttribute("mapaPaises");
	HashMap<String,CatEstado> mapaEstados 		= (HashMap<String,CatEstado>)request.getAttribute("mapaEstados");
	HashMap<String, AlumPersonal> mapaInscritos = (HashMap<String, AlumPersonal>)request.getAttribute("mapaInscritos");
	HashMap<String, AlumPersonal> mapaDatos		= (HashMap<String, AlumPersonal>)request.getAttribute("mapaDatos");
	
	HashMap<String,String> mapaColores 			= new HashMap<String,String>();
	
	mapaColores.put("1", "#f70c0c");
	mapaColores.put("2", " #f79b0c");
	mapaColores.put("3", "#f7f00c");
	mapaColores.put("4", " #3ef70c ");
	
	String alumnos = "0";
%>
<div class="container-fluid">
	<h2>Semaforizaci&oacute;n Alumnos</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary btn-sm" href="menu">Regresar</a>
	</div>
	<table class="table table-bordered table-sm">
		<tr class="table-info">
			<th>#</th>
			<th>Matr&iacute;cula</th>
			<th>Alumno</th>
			<th>Celular</th>
			<th>Correo</th>
			<th>Pa&iacute;s</th>
			<th>Estado</th>
			<th>Sem&aacute;foro</th>
		</tr>
		
<%	
	int row = 0;
	for (AlumUbicacion alumUbicacion : listEstadistica){
		row++;
		
		String nombreAlumno = "";
		if(mapaInscritos.containsKey(alumUbicacion.getCodigoPersonal())){
			AlumPersonal alumPersonal = mapaInscritos.get(alumUbicacion.getCodigoPersonal());
			nombreAlumno = (alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno()).replace("-", "");
		}
		
		String nombrePais = "-";
		if(mapaPaises.containsKey(alumUbicacion.getTPais())){
			nombrePais = mapaPaises.get(alumUbicacion.getTPais()).getNombrePais();
		}
		
		String nombreEstado = "-";
		String semaforo		= "1";
		String color 		= "red";
		if(mapaEstados.containsKey(alumUbicacion.getTPais()+alumUbicacion.getTEstado())){
			nombreEstado 	= mapaEstados.get(alumUbicacion.getTPais()+alumUbicacion.getTEstado()).getNombreEstado();
			semaforo 		= mapaEstados.get(alumUbicacion.getTPais()+alumUbicacion.getTEstado()).getSemaforo();
			if (mapaColores.containsKey(semaforo)){
				color = mapaColores.get(semaforo);
			}
		}
		
		String celular 	= "-";
		String correo 	= "-";
		if(mapaInscritos.containsKey(alumUbicacion.getCodigoPersonal())){
			celular = mapaInscritos.get(alumUbicacion.getCodigoPersonal()).getTelefono();
			correo	= mapaInscritos.get(alumUbicacion.getCodigoPersonal()).getEmail();
		}
		
%>		
		<tr>
			<td><%=row%></td>
			<td><%=alumUbicacion.getCodigoPersonal()%></td>
			<td><%=nombreAlumno%></td>
			<td><%=celular%></td>
			<td><%=correo%></td>
			<td><%=nombrePais%></td>
			<td><%=nombreEstado%></td>
			<td><span class="badge" style="background:<%=color%>;"><%=semaforo%></span></td>
		</tr>		
<%	} %>		
	</table>
</div>
