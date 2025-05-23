<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>

<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.mentores.spring.MentAlumno"%>
<%@page import="aca.alumno.spring.AlumEstado"%>
<%@page import="aca.alumno.spring.AlumActualiza"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.vista.spring.Maestros"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	java.text.DecimalFormat getformato = new java.text.DecimalFormat("###,##0.00; -###,##0.00");

	String codigoUsuario 	= (String) session.getAttribute("codigoPersonal");
	String numOrden 		= request.getParameter("Ordenar") == null ? "1" : request.getParameter("Ordenar");
	
	String periodoId 		= (String) request.getAttribute("periodoId");
	String codigoAlumno 	= "";
	String cargaAlumno 		= "";
	String nombreCarga 		= "";
	String carreraTemp 		= "";
	String carreraNombre 	= "";
	String carreraCorto 	= "";
	
	int cont = 0;	
	List<MentAlumno> lisAmentorados 				= (List<MentAlumno>)request.getAttribute("lisAmentorados");
	// Map de inscritos
	HashMap<String,AlumEstado> mapInscritos 		= (HashMap<String,AlumEstado>) request.getAttribute("mapInscritos");	
	// Map de Facultades
	HashMap<String,CatFacultad> mapFacultad	 		= (HashMap<String,CatFacultad>) request.getAttribute("mapFacultad");	
	// Map de Carreras
	HashMap<String,CatCarrera> mapCarrera 			= (HashMap<String,CatCarrera>) request.getAttribute("mapCarrera");	
	// Map de aconsejados
	HashMap<String,String> mapAlumnos 				= (HashMap<String,String>) request.getAttribute("mapAlumnos");
	// Map de empleados
	HashMap<String,Maestros> mapMaestros 			= (HashMap<String,Maestros>) request.getAttribute("mapMaestros");	
	HashMap<String,String> mapResidencias 			= (HashMap<String,String>) request.getAttribute("mapResidencias");
	HashMap<String,AlumActualiza> mapActualizados	= (HashMap<String,AlumActualiza>)request.getAttribute("mapActualizados");
	HashMap<String,AlumPlan> mapGrado				= (HashMap<String,AlumPlan>)request.getAttribute("mapGrado");
%>
<body>
<div class="container-fluid">
	<h1>Degree Advisors</h1>
	<div class="alert alert-info">
		To sort by name, mentor and degree, click on the corresponding heading.
	</div>
<table style="width:90%">
<%
	for (MentAlumno ment : lisAmentorados) {
		cont++;
		codigoAlumno = ment.getCodigoPersonal();
		
		String planId = "";
		if (mapInscritos.containsKey( codigoAlumno )){
			planId = mapInscritos.get(codigoAlumno).getPlanId();
		}
		
		String carreraId = ment.getCarreraId();

		if (!carreraTemp.equals(carreraId)) {
			carreraTemp = carreraId;
			// Busca el nombre de la carrera
			if ( !carreraTemp.trim().equals("x")){
				
				carreraNombre = "";
				if (mapCarrera.containsKey( carreraTemp )){
					carreraNombre = mapCarrera.get( carreraTemp ).getNombreCarrera();
				}					
				
			}else{
				carreraNombre = "No Degree Assigned";
			}	
	%>
</table>
<table id="table" class="table table-sm table-bordered">
	<thead>	
	<tr>
		<td align="left" colspan="10"><h3><%=carreraNombre%></h3></td>
	</tr>
	</thead>
	<thead class="table-info">	
	<tr>
		<th>Operation</th>
		<th>N°</th>
		<th class="button" title='Order by name'
			onclick="location.href='carrera?Ordenar=1'">Name</th>
		<th>Load</th>
		<th class="button" title='Order by career'
			onclick="location.href='carrera?Ordenar=3'">Career</th>
		<th>Grade</th>
		<th class="button" title='Order by Mentor' onclick="location.href='carrera?Ordenar=2'">Mentor</th>		
		<th>Residence</th>
		<th>Registered</th>
		<th>Updated</th>
		<th>Date</th>
	</tr>
	</thead>
	<%
		}
		//Situación académica
		if (mapInscritos.containsKey(codigoAlumno)) {
			cargaAlumno = mapInscritos.get(codigoAlumno).getCargaId();
		}
		
		String actualiza 			= "N";
		String fechaActualiza 		= "-";
		String empleadoActualiza	= "";
		if (mapActualizados.containsKey( codigoAlumno )) {
			actualiza = "S";
			fechaActualiza 		= mapActualizados.get(codigoAlumno).getFecha();
			empleadoActualiza	= mapActualizados.get(codigoAlumno).getCodigoEmpleado();
		}
		
		String nombreActualiza = "-";
		if (mapMaestros.containsKey(empleadoActualiza)){
			nombreActualiza = mapMaestros.get(empleadoActualiza).getNombre()+" "+mapMaestros.get(empleadoActualiza).getApellidoPaterno();
		}
		
		String nombreAlumno = "";
		if (mapAlumnos.containsKey( codigoAlumno )){
			nombreAlumno = mapAlumnos.get( codigoAlumno );
		}
		
		if (mapCarrera.containsKey( ment.getCarreraId() )){
			carreraCorto =  mapCarrera.get( ment.getCarreraId() ).getNombreCorto();
		} 
		
		String nombreEmpleado = "";
		if (mapMaestros.containsKey( ment.getMentorId() )){
			nombreEmpleado = mapMaestros.get(ment.getMentorId()).getNombre()+" "+mapMaestros.get(ment.getMentorId()).getApellidoPaterno()+" "+mapMaestros.get(ment.getMentorId()).getApellidoMaterno();
		}
		
		String residencia = "-";
		if (mapResidencias.containsKey(codigoAlumno)){
			residencia = mapResidencias.get(codigoAlumno);
		}
		
		String grado = "0";
		if(mapGrado.containsKey(codigoAlumno)){
			grado = mapGrado.get(codigoAlumno).getGrado();
		}
	%>
	<tr>
		<td align="center">
			<a href="../../portales/mentor/subir?f_codigo_personal=<%=codigoAlumno%>" title="See the portal of this student">Portal</a>
			<a href="../../portales/mentor/mentor_opciones?matricula=<%=codigoAlumno%>" class="ayuda alumno <%=codigoAlumno%>"><%=codigoAlumno%></a>
		</td>
		<td><%=cont%></td>
		<td><%=nombreAlumno%></td>
		<td align="right" class="ayuda mensaje <%=nombreCarga%>>"><%=cargaAlumno%></td>
		<td align="center"><%=carreraCorto%></td>
		<td align="center"><%=grado%></td>
		<td align="left"><%=nombreEmpleado%></td>		
		<td align="center"><%=residencia.equals("E")?"External":"Boarding"%></td>
		<td align="center"><%=mapInscritos.containsKey(codigoAlumno)?"YES":"NO"%></td>
		<td><%=nombreActualiza%></td>
		<td align="center"><%=fechaActualiza%></td>
	</tr>
	<%
		}
	%>
</table>
</div>
</body>