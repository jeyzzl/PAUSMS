<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumAcademico"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>
<%@ page import="aca.alumno.spring.AlumBanco"%>
<%@ page import="aca.alumno.spring.AlumUbicacion"%>
<%@ page import="aca.alumno.spring.AlumEstado"%>
<%@ page import="aca.alumno.spring.AlumPatrocinador"%>
<%@ page import="aca.internado.spring.IntAlumno"%>
<%@ page import="aca.internado.spring.IntDormitorio"%>
<%@ page import="aca.catalogo.spring.CatEstado"%>
<%@ page import="aca.catalogo.spring.CatCiudad"%>
<%@ page import="aca.catalogo.spring.CatPais"%>
<%@ page import="aca.catalogo.spring.CatReligion"%>
<%@ page import="aca.catalogo.spring.CatFacultad"%>
<%@ page import="aca.catalogo.spring.CatArea"%>
<%@ page import="aca.catalogo.spring.CatPatrocinador"%>
<%@ page import="aca.catalogo.spring.CatNivelInicio"%>
<%@ page import="aca.plan.spring.MapaPlan"%>
<%@ page import="aca.plan.spring.MapaMayorMenor"%>
<%@ page import="aca.carga.spring.Carga"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript" >
	
	
	function cambiarCarga(){
		var cargaId 	= document.getElementById("CargaId").value;
		console.log(cargaId);
		location.href = "general?CargaId="+cargaId;
	}
	
	
	
</script>
<style>
	.table-responsive {
		overflow-x: auto;
		width: 100%;
	}

	.table {
		white-space: nowrap;
	}
</style>
<%
	String cargaId 											= (String)request.getAttribute("cargaId");
	List<Carga> lisCargas									= (List<Carga>)request.getAttribute("lisCargas");
	List<AlumPersonal> lisTodos 							= (List<AlumPersonal>)request.getAttribute("lisTodos");

	HashMap<String, AlumAcademico> mapaAcademico 			= (HashMap<String, AlumAcademico>)request.getAttribute("mapaAcademico");
	HashMap<String, AlumUbicacion> mapaUbicacion 			= (HashMap<String, AlumUbicacion>)request.getAttribute("mapaUbicacion");
	HashMap<String, IntAlumno> mapaIntAlumno				= (HashMap<String, IntAlumno>)request.getAttribute("mapaIntAlumno");
	HashMap<String, CatPais> mapaPais						= (HashMap<String, CatPais>)request.getAttribute("mapaPais");
	HashMap<String, CatEstado> mapaEstado					= (HashMap<String, CatEstado>)request.getAttribute("mapaEstado");
	HashMap<String, CatCiudad> mapaCiudad					= (HashMap<String, CatCiudad>)request.getAttribute("mapaCiudad");
	HashMap<String, CatReligion> mapaReligion				= (HashMap<String, CatReligion>)request.getAttribute("mapaReligion");
	HashMap<String, IntDormitorio> mapaDormitorio			= (HashMap<String, IntDormitorio>)request.getAttribute("mapaDormitorio");
	HashMap<String, String> mapaEdades						= (HashMap<String, String>)request.getAttribute("mapaEdades");
	HashMap<String, AlumEstado> mapaAlumEstado				= (HashMap<String, AlumEstado>)request.getAttribute("mapaAlumEstado");
	HashMap<String, CatFacultad> mapaFacultad				= (HashMap<String, CatFacultad>)request.getAttribute("mapaFacultad");
	HashMap<String, CatArea> mapaArea						= (HashMap<String, CatArea>)request.getAttribute("mapaArea");
	HashMap<String, MapaPlan> mapaPlan						= (HashMap<String, MapaPlan>)request.getAttribute("mapaPlan");
	HashMap<String, AlumPlan> mapaAlumPlan					= (HashMap<String, AlumPlan>)request.getAttribute("mapaAlumPlan");
	HashMap<String, MapaMayorMenor> mapaMayores 			= (HashMap<String, MapaMayorMenor>)request.getAttribute("mapaMayores");
	HashMap<String, MapaMayorMenor> mapaMenores 			= (HashMap<String, MapaMayorMenor>)request.getAttribute("mapaMenores");
	HashMap<String, AlumPatrocinador> mapaAlumPatrocinador 	= (HashMap<String, AlumPatrocinador>)request.getAttribute("mapaAlumPatrocinador");
	HashMap<String, CatPatrocinador> mapaPatrocinador 		= (HashMap<String, CatPatrocinador>)request.getAttribute("mapaPatrocinador");
	HashMap<String, CatNivelInicio> mapaNivelInicio 		= (HashMap<String, CatNivelInicio>)request.getAttribute("mapaNivelInicio");
%>
<body>
	<div class="container-fluid">
		<h2>General Report</h2>
		<div class="alert alert-info d-flex align-items-center">
			<a class="btn btn-primary" href="menu"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
			
			Load:&nbsp;
		<select name="CargaId" id="CargaId" style="width:300px;" class="form-select" onchange="javascript:cambiarCarga();">
		<option value="0" <%=cargaId.equals("0")?"selected":""%>>All</option>
		<%
			for(Carga carga: lisCargas){
		%>
					<option value="<%=carga.getCargaId()%>" <%=carga.getCargaId().equals(cargaId)?"selected":""%>><%=carga.getNombreCarga() %></option>
		<%
			}
		%>
		</select> &nbsp; &nbsp;
		<input type="text" class="form-control search-query" placeholder="<spring:message code="aca.Buscar"/>" id="buscar" style="width:200px;">&nbsp;&nbsp;
		</div>
		<div class="table-responsive">
		<table id="table" class="table table-bordered table-sm">
			<thead class="table-dark">
				<tr>
					<th>#</th>
					<th>ID</th> 
					<th>Name</th>
					<th>Surname</th>
					<th>Gender</th>
					<th title="Marital Status">M.S</th>
					<th>D.O.B</th>		
					<th>Age</th>
					<th title="Denomination">Denom.</th>	
					<th>Email</th>	
					<th title="Student Status">S.S</th>	
					<th title="Status Date">S.D</th>	
					<th title="Entry Level">E.L</th>
					<th>School</th>
					<th>Campus</th>
					<th>Course</th>
					<th title="Major/Minor">M/M</th>
					<th title="Year Level">Y.L</th>
					<th title="Sponsor">Sponsor</th>
					<th title="Boarding Status">B.S</th>
					<th title="Dormitory">Dorm.</th>
					<th title="Room Number">R.N</th>
					<th title="Country">Country</th>
					<th>Nationality</th>
					<th title="Mobile Phone">Mobile</th>
					<th title="Postal Address">P.O</th>
					<th title="Address">Address</th>
					<th title="Home Province">Province</th>
					<th title="Home District">District</th>
					<th title="Nearest Airport">N.A</th>
				</tr>
			</thead>
			<tbody>
<%	
	int count = 0;
	for(AlumPersonal  alumno : lisTodos){
		count++;

		AlumEstado alumEstado = new AlumEstado();
		if(mapaAlumEstado.containsKey(alumno.getCodigoPersonal())){
			alumEstado = mapaAlumEstado.get(alumno.getCodigoPersonal());
		}	

		AlumUbicacion ubicacion = new AlumUbicacion();
		if(mapaUbicacion.containsKey(alumno.getCodigoPersonal())){
			ubicacion = mapaUbicacion.get(alumno.getCodigoPersonal());
		}
		
		AlumAcademico academico = new AlumAcademico();
		if(mapaAcademico.containsKey(alumno.getCodigoPersonal())){
			academico = mapaAcademico.get(alumno.getCodigoPersonal());
		}

		AlumPlan alumPlan = new AlumPlan();
		if(mapaAlumPlan.containsKey(alumno.getCodigoPersonal()+alumEstado.getPlanId())){
			alumPlan = mapaAlumPlan.get(alumno.getCodigoPersonal()+alumEstado.getPlanId());
		}

		MapaPlan plan = new MapaPlan();
		if(mapaPlan.containsKey(alumEstado.getPlanId())){
			plan = mapaPlan.get(alumEstado.getPlanId());
		}

		IntAlumno internado = new IntAlumno();
		if(mapaIntAlumno.containsKey(alumno.getCodigoPersonal())){
			internado = mapaIntAlumno.get(alumno.getCodigoPersonal());
		}

		AlumPatrocinador patrocinador = new AlumPatrocinador();
		String nombrePatrocinador = "n/a";
		if(mapaAlumPatrocinador.containsKey(alumno.getCodigoPersonal())){
			patrocinador = mapaAlumPatrocinador.get(alumno.getCodigoPersonal());
		}
		if(mapaPatrocinador.containsKey(patrocinador.getPatrocinadorId())){
			nombrePatrocinador = mapaPatrocinador.get(patrocinador.getPatrocinadorId()).getNombrePatrocinador();
		}

		String edad = "";
		if(mapaEdades.containsKey(alumno.getCodigoPersonal())){
			edad = mapaEdades.get(alumno.getCodigoPersonal());
		}
		
		CatPais paisAlumno = new CatPais();
		CatPais nacionalidadAlumno = new CatPais();
		if(mapaPais.containsKey(alumno.getPaisId())){
			paisAlumno = mapaPais.get(alumno.getPaisId());
			nacionalidadAlumno = mapaPais.get(alumno.getNacionalidad());
		}
		
		CatEstado estadoAlumno = new CatEstado();
		if(mapaEstado.containsKey(alumno.getPaisId() + alumno.getEstadoId())){
			estadoAlumno = mapaEstado.get(alumno.getPaisId() + alumno.getEstadoId());
		}
		
		CatCiudad ciudadAlumno = new CatCiudad();
		if(mapaCiudad.containsKey(alumno.getPaisId() + alumno.getEstadoId() + alumno.getCiudadId())){
			ciudadAlumno = mapaCiudad.get(alumno.getPaisId() + alumno.getEstadoId()+ alumno.getCiudadId());
		}		
		
		CatReligion religion = new CatReligion();
		if(mapaReligion.containsKey(alumno.getReligionId())){
			religion = mapaReligion.get(alumno.getReligionId());
		}
		
		IntDormitorio dormitorio = new IntDormitorio();
		String dormitorioN = "";
		// System.out.println(alumno.getCodigoPersonal()+" : '"+academico.getDormitorio()+"'");
		if(mapaDormitorio.containsKey(academico.getDormitorio())){
			// System.out.println(alumno.getCodigoPersonal()+" : '"+academico.getDormitorio()+"'");
			dormitorioN = mapaDormitorio.get(academico.getDormitorio()).getNombre();
		}

		CatFacultad facultad = new CatFacultad();
		if(mapaFacultad.containsKey(alumEstado.getFacultadId())){
			facultad = mapaFacultad.get(alumEstado.getFacultadId());
		}

		CatArea area = new CatArea();
		if(mapaArea.containsKey(facultad.getAreaId())){
			area = mapaArea.get(facultad.getAreaId());
		}

		String mayor = "n/a";
		String menor = "n/a";
		if(mapaMayores.containsKey(alumPlan.getMayor())){
			mayor = mapaMayores.get(alumPlan.getMayor()).getNombre();
		}
		if(mapaMenores.containsKey(alumPlan.getMenor())){
			menor = mapaMenores.get(alumPlan.getMenor()).getNombre();
		}

		String nivelInicio = "n/a";
		if(mapaNivelInicio.containsKey(academico.getNivelInicioId())){
			nivelInicio = mapaNivelInicio.get(academico.getNivelInicioId()).getNombreNivel();
		}

%>
				<tr>
					<td><%=count%></td>
					<td><%=alumno.getCodigoPersonal()%></td> <!-- ID -->
					<td><%=alumno.getNombre()%></td> <!-- NAME -->
					<td><%=alumno.getApellidoPaterno()%></td> <!-- SURNAME -->
					<td><%=alumno.getSexo()%></td> <!-- GENDER -->
					<td><%=alumno.getEstadoCivil().equals("C")?"M":"S"%></td> <!-- MARITAL STATUS -->
					<td><%=alumno.getFNacimiento().substring(0,10)%></td> <!-- D.O.B -->
					<td><%=edad%></td> <!-- AGE -->
					<td><%=religion.getNombreReligion()%></td> <!-- DENOMINATION -->
					<td><%=alumno.getEmail()%></td> <!-- EMAIL -->
					<td><%=alumEstado.getEstado().equals("I")?"ENROLLED":"NOT ENROLLED"%></td> <!-- STATUS -->
					<td><%=alumEstado.getFecha().substring(0,10)%></td> <!-- DATE -->
					<td><%=nivelInicio%></td> <!-- ENTRY LEVEL -->
					<td><%=facultad.getNombreCorto()%></td> <!-- SCHOOL -->
					<td><%=area.getNombreArea()%></td> <!-- CAMPUS -->
					<td><%=plan.getNombrePlan()%></td> <!-- PLAN -->
					<td><%=mayor.equals("n/a")?"":mayor+" | "%><%=menor.equals("n/a")?"":menor%></td> <!-- MAJOR/MINOR -->
					<td><%=academico.getGrado()%></td> <!-- YEAR -->
					<td><%=nombrePatrocinador%></td> <!-- SPONSOR -->
					<td><%=academico.getResidenciaId().equals("I")?"Boarding":"Day Student"%></td> <!-- BOARDING STATUS -->
					<td><%=dormitorioN%></td> <!-- DORMITORY -->
					<td><%=internado.getCuartoId()==null?"n/a":internado.getCuartoId()%></td> <!-- ROOM No -->
					<td><%=paisAlumno.getNombrePais()%></td> <!-- COUNTRY -->
					<td><%=nacionalidadAlumno.getNacionalidad()%></td> <!-- NATIONALITY -->
					<td><%=ubicacion.gettCelular()%></td> <!-- MOBILE PHONE -->
					<td></td> <!-- POSTAL ADDRESS -->
					<td><%=alumno.getDireccion()%></td> <!-- ADDRESS -->
					<td><%=estadoAlumno.getNombreEstado()%></td> <!-- PROVINCE -->
					<td><%=ciudadAlumno.getNombreCiudad()%></td> <!-- DISTRICT -->
					<td></td> <!-- NEAREST AIRPORT -->
				</tr>
<%
	}
%>
			</tbody>
		</table>
	</div>
	</div>
	<script src="../../js/jquery-1.9.1.min.js"></script>
<script src="../../js/search.js"></script>	
<script>	
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>
</body>