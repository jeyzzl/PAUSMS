<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmSolicitud"%>
<%@page import="aca.admision.spring.AdmAcademico"%>
<%@page import="aca.admision.spring.AdmSalud"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatEstado"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatReligion"%>
<%@page import="aca.podium.spring.Notas"%>
<%@page import="aca.admision.spring.AdmEvaluacionNota"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<head>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>
	function Mostrar() {
		if (document.frmIngreso.fechaIni.value != "" && document.frmIngreso.fechaFin.value != ""){
			document.forma.submit();
		} else {
			alert("Es necesario seleccionar una fecha");
		}
	}
</script>	
</head>
<%	
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	String institucion 		= (String)session.getAttribute("institucion");
	String codigoPersonal	= (String)session.getAttribute("codigoPersonal");	
	String fechaHoy			= aca.util.Fecha.getHoy();
	
	String estado 			= request.getParameter("Estados") == null ? "0" : request.getParameter("Estados");
	String fechaIni 		= (String) session.getAttribute("fechaInicio");
	String fechaFin 		= (String) session.getAttribute("fechaFinal");	
	String modalidades 		= (String)request.getAttribute("modalidades");
	String nombreAlumno 	= (String)request.getAttribute("nombreAlumno");
	
	List<AdmSolicitud> lisAlumnos 					= (List<AdmSolicitud>)request.getAttribute("lisAlumnos");

	HashMap<String,AdmAcademico> mapAcademico 		= (HashMap<String,AdmAcademico>)request.getAttribute("mapaAcademico");	
	HashMap<String,AdmSalud> mapSalud 				= (HashMap<String,AdmSalud>)request.getAttribute("mapaSalud");
	HashMap<String,CatCarrera> mapaCarreras 		= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,CatPais> mapaPaises 				= (HashMap<String,CatPais>)request.getAttribute("mapaPaises");
	HashMap<String,CatEstado> mapaEstados			= (HashMap<String,CatEstado>)request.getAttribute("mapaEstados");
	HashMap<String,CatFacultad> mapaFacultades		= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String,CatReligion> mapaReligion		= (HashMap<String,CatReligion>)request.getAttribute("mapaReligion");
	HashMap<String,String> mapFechaRegistro			= (HashMap<String,String>)request.getAttribute("mapFechaRegistro");
	HashMap<String,String> mapFechaSolicitud		= (HashMap<String,String>)request.getAttribute("mapFechaSolicitud");
	HashMap<String,String> mapFechaDocumento		= (HashMap<String,String>)request.getAttribute("mapFechaDocumento");
	HashMap<String,String> mapFechaAceptado			= (HashMap<String,String>)request.getAttribute("mapFechaAceptado");
	HashMap<String,AdmAcademico> mapModalidadId		= (HashMap<String,AdmAcademico>)request.getAttribute("mapModalidadId");
	HashMap<String,String> mapModalidades			= (HashMap<String,String>)request.getAttribute("mapModalidades");
	HashMap<String,String> mapInscritos				= (HashMap<String,String>)request.getAttribute("mapInscritos");
	HashMap<String,Notas> mapaNotasPorFolio			= (HashMap<String,Notas>)request.getAttribute("mapaNotasPorFolio");
	HashMap<String,String> mapaFechasPorFolio		= (HashMap<String,String>)request.getAttribute("mapaFechasPorFolio");
	HashMap<String,AdmEvaluacionNota> mapaNotas		= (HashMap<String,AdmEvaluacionNota>)request.getAttribute("mapaNotas");
	HashMap<String,String> mapaPruebas				= (HashMap<String,String>)request.getAttribute("mapaPruebas");
%>
<body>
<div class="container-fluid">
	<h2><%=institucion %>, A.C. </h2> 
	<form name="frmIngreso" action="reporte" method="post">
	<div class="alert alert-info d-flex align-items-center" align="left">
		<a href="menu" class="btn-primary btn-sm"><i class="far fa-arrow-alt-circle-left"></i></a>
		&nbsp;&nbsp;
		<select name="Modalidades" class="form-select" style="width:150px;"> 
			<option value="0" <%=modalidades.equals("0") ? "Selected" : ""%>>All</option>
			<option value="1" <%=modalidades.equals("1") ? "Selected" : ""%>><spring:message code="aca.Presencial"/></option>
			<option value="2" <%=modalidades.equals("2") ? "Selected" : ""%>><spring:message code="aca.NoPresencial"/></option>
		</select>
  		&nbsp;&nbsp;
  		Start Date: <input type="text" class="form-control" style="width:110px" data-date-format="dd/mm/yyyy" id="fechaIni" name="fechaIni" value="<%=fechaIni%>" maxlength="10" size="12" />&nbsp;
		&nbsp;&nbsp;
		End Date: <input type="text" class="form-control" style="width:110px" data-date-format="dd/mm/yyyy" id="fechaFin" name="fechaFin" value="<%=fechaFin%>" maxlength="10" size="12" />
		&nbsp; &nbsp;
		<input class="btn btn-primary" type="submit" value="Show" onclick="Mostrar();"/>
  	</div>
	</form>				
	<table id="table" align="center" class="table table-striped table-bordered"<c:catch></c:catch>'>
	<thead>
	<tr class= "table-info">
		<th width="1%"><h5>#</h5></th>
		<th width="3%"><h5><spring:message code="aca.Folio"/></h5></th>
		<th width="5%"><h5>Student ID</h5></th>
		<th width="7%"><h5>Modality</h5></th>
		<th width="20%"><h5>Name</h5></th>
		<th width="20%"><h5>Surname</h5></th>
		<th width="5%"><h5>Faculty</h5></th>
		<th width="20%"><h5><spring:message code="aca.Carrera"/></h5></th>
		<th width="7%"><h5><spring:message code="aca.Correo"/></h5></th>
		<th width="5%"><h5>Phone Number</h5></th>
		<th width="5%"><h5>Gender</h5></th>
		<th width="10%"><h5>Religion</h5></th>
		<th width="10%"><h5>Advance</h5></th>			
		<th width="10%"><h5>Country</h5></th>
		<th width="10%"><h5>State</h5></th>
		<th width="7%"><h5>Ill.</h5></th>
		<th width="7%"><h5>Illness</h5></th>
		<th width="7%"><h5>Imp.</h5></th>
		<th width="7%"><h5>Impediments</h5></th>
		<th width="5%"><h5>L�g.</h5></th>
		<th width="5%"><h5>Mat.</h5></th>
		<th width="5%"><h5>Esp.</h5></th>
		<th width="5%"><h5>Ing.</h5></th>
		<th width="5%"><h5>Bio.</h5></th>
		<th width="5%"><h5>Fis.</h5></th>
		<th width="5%"><h5>Quim.</h5></th>
		<th width="5%"><h5>TPT1</h5></th>
		<th width="5%"><h5>Ante.</h5></th>
		<th width="5%"><h5>Ent.</h5></th>
		<th width="5%"><h5>Int.</h5></th>
		<th width="5%"><h5>TPT2</h5></th>
		<th width="7%"><h5>Register Date</h5></th>
		<th width="7%"><h5>Application Date</h5></th>
		<th width="7%"><h5>Document Date</h5></th>
		<th width="7%"><h5>Acceptance Date</h5></th>
		<th width="2%"><h5>Enrolled</h5></th>			
	</tr>
	</thead>
	<tbody>
<%		
	int row = 1;
	for(AdmSolicitud alumno : lisAlumnos){
			
		String carreraId		= "-";
		String nombreCarrera	= "-";
		String paisNombre		= "-";
		String estadoNombre		= "-";
		String facultadId		= "-";
		String nombreFacultad	= "-";
		String religionNombre	= "-";
		String fechaRegistro	= "-";
		String fechaSolicitud	= "-";
		String fechaDocumento	= "-";
		String fechaAceptado	= "-";
		String modalidadId		= "-";
		String nombreModalidad	= "-";
		String inscrito			= "NO";
		
		String avance = "0";
		if(alumno.getEstado().equals("1")){
			avance = "Account Created";
		}else if(alumno.getEstado().equals("2")){
			avance = "Has Application";
		}else if(alumno.getEstado().equals("3")){
			avance = "Authorized Documents";
		}else{
			avance = "Admitted";
		}
		
		if (mapAcademico.containsKey(alumno.getFolio())){
			AdmAcademico academico = mapAcademico.get(alumno.getFolio());
			
			carreraId = academico.getCarreraId();
			if (mapaCarreras.containsKey(academico.getCarreraId())){
				CatCarrera car = mapaCarreras.get(academico.getCarreraId());
				nombreCarrera = car.getNombreCarrera();
			}
			
			if(mapaCarreras.containsKey(academico.getCarreraId())){
				facultadId = mapaCarreras.get(academico.getCarreraId()).getFacultadId();
				if(mapaFacultades.containsKey(facultadId)){
					nombreFacultad = mapaFacultades.get(facultadId).getNombreFacultad();
				}
			}
			
			if (mapInscritos.containsKey(alumno.getMatricula()+academico.getCarreraId())){
				inscrito = mapInscritos.get(alumno.getMatricula()+academico.getCarreraId());
				inscrito = "Yes";
			}
		}
		
		if(mapModalidadId.containsKey(alumno.getFolio())){
			modalidadId = mapModalidadId.get(alumno.getFolio()).getModalidad();
		}
		
		if(mapModalidades.containsKey(modalidadId)){
			nombreModalidad = mapModalidades.get(modalidadId);
		}
		
		if(mapaPaises.containsKey(alumno.getPaisId())){
			paisNombre = mapaPaises.get(alumno.getPaisId()).getNombrePais();
		}
		
		if(mapaEstados.containsKey(alumno.getPaisId()+alumno.getEstadoId())){
			estadoNombre = mapaEstados.get(alumno.getPaisId()+alumno.getEstadoId()).getNombreEstado();
		}
		
		if(mapaReligion.containsKey(alumno.getReligionId())){
			religionNombre = mapaReligion.get(alumno.getReligionId()).getNombreCorto();
		}
		
		if(mapFechaRegistro.containsKey(alumno.getFolio())){
			fechaRegistro = mapFechaRegistro.get(alumno.getFolio());
		}
		
		if(mapFechaSolicitud.containsKey(alumno.getFolio())){
			fechaSolicitud = mapFechaSolicitud.get(alumno.getFolio());
		}
		
		if(mapFechaDocumento.containsKey(alumno.getFolio())){
			fechaDocumento = mapFechaDocumento.get(alumno.getFolio());
		}
		
		if(mapFechaAceptado.containsKey(alumno.getFolio())){
			fechaAceptado = mapFechaAceptado.get(alumno.getFolio());
		}	
		
		String enfermedad 		= "N";
		String enfermedadDato 	= "-";
		String impedimento		= "N";
		String impedimentoDato	= "-";
		if (mapSalud.containsKey(alumno.getFolio())){
			AdmSalud salud = mapSalud.get(alumno.getFolio());
			enfermedad 			= salud.getEnfermedad();
			enfermedadDato 		= salud.getEnfermedadDato();
			impedimento			= salud.getImpedimento();
			impedimentoDato		= salud.getImpedimentoDato();
		}	
		
		float resLog 	= 0;		
		float resMat 	= 0;		
		float resEsp 	= 0;		
		float resIng 	= 0;
		float resBio 	= 0;
		float resFis 	= 0;
		float resQui 	= 0;

		if(mapaNotasPorFolio.containsKey(alumno.getFolio())){
			resLog 	= mapaNotasPorFolio.get(alumno.getFolio()).getResLog();		
			resMat 	= mapaNotasPorFolio.get(alumno.getFolio()).getResMat();		
			resEsp 	= mapaNotasPorFolio.get(alumno.getFolio()).getResEsp();		
			resIng 	= mapaNotasPorFolio.get(alumno.getFolio()).getResIng();
			resBio 	= mapaNotasPorFolio.get(alumno.getFolio()).getResBio();
			resFis 	= mapaNotasPorFolio.get(alumno.getFolio()).getResFis();
			resQui 	= mapaNotasPorFolio.get(alumno.getFolio()).getResQui();
		}	
		
		String notaTpt 		= "0";
		String fechaTpt		= "";
		if(mapaNotas.containsKey(alumno.getFolio()+"7")){
			notaTpt 			= mapaNotas.get(alumno.getFolio()+"7").getNota();
			fechaTpt 			= mapaNotas.get(alumno.getFolio()+"7").getFecha();
		}
		
		String antecedente 		= "0";
		String fechaAntecedente	= "";
		if(mapaNotas.containsKey(alumno.getFolio()+"8")){
			antecedente 		= mapaNotas.get(alumno.getFolio()+"8").getNota();
			fechaAntecedente 	= mapaNotas.get(alumno.getFolio()+"8").getFecha();
		}
		
		String entrevista 		= "0";
		String fechaEntrevista	= "";
		if(mapaNotas.containsKey(alumno.getFolio()+"10")){
			entrevista 			= mapaNotas.get(alumno.getFolio()+"10").getNota();
			fechaEntrevista 	= mapaNotas.get(alumno.getFolio()+"10").getFecha();
		}
		
		String integral 		= "0";
		String fechaIntegral	= "";
		if(mapaNotas.containsKey(alumno.getFolio()+"9")){
			integral 			= mapaNotas.get(alumno.getFolio()+"9").getNota();
			fechaIntegral 		= mapaNotas.get(alumno.getFolio()+"9").getFecha();
		}
		
		String pruebaTpt = "-";
		if (mapaPruebas.containsKey(alumno.getFolio())){
			pruebaTpt = mapaPruebas.get(alumno.getFolio());
		}
			
%>
	<tr>
		<td align="center"><em><%=row%></em></td>
		<td align="center"><%=alumno.getFolio()%></td>
		<td><%=alumno.getMatricula() %></td>
		<td><%=nombreModalidad%></td>
		<td><%=alumno.getNombre()%></td>
		<td><%=(alumno.getApellidoMaterno()==null?"":alumno.getApellidoMaterno())%> <%=alumno.getApellidoPaterno()%></td>
		<td><%=nombreFacultad%></td>
		<td><%=nombreCarrera%></td>
		<td><%=alumno.getEmail()%></td>
		<td><%=alumno.getTelefono()%></td>
		<td align="center"><%=alumno.getGenero().equals("F")?"Female":"Male"%></td>
		<td><%=religionNombre%></td>
		<td><span class="badge bg-dark"><%=avance%></span></td>						
		<td><%=paisNombre%></td>
		<td><%=estadoNombre%></td>
		<td><%=enfermedad.equals("S")?"SI":"NO"%></td>
		<td><%=enfermedadDato%></td>
		<td><%=impedimento.equals("S")?"SI":"NO"%></td>
		<td><%=impedimentoDato%></td>
		<td><%=formato.format(resLog)%></td>
		<td><%=formato.format(resMat)%></td>
		<td><%=formato.format(resEsp)%></td>
		<td><%=formato.format(resIng)%></td>
		<td><%=formato.format(resFis)%></td>
		<td><%=formato.format(resBio)%></td>
		<td><%=formato.format(resQui)%></td>
		<td><%=notaTpt%></td>
		<td><%=antecedente%></td>
		<td><%=entrevista%></td>
		<td><%=integral%></td>		
		<td><%=pruebaTpt%></td>
		<td><%=fechaRegistro%></td>
		<td><%=fechaSolicitud%></td>
		<td><%=fechaDocumento%></td>
		<td><%=fechaAceptado%></td>		
		<td><%=inscrito %></td>															
	</tr>
<%		row++;
		}
%>
	</tbody>
	</table>				
</div>	
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#fechaIni').datepicker();
	jQuery('#fechaFin').datepicker();
</script>
</body>