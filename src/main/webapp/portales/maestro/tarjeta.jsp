<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatTipoCal"%>
<%@ page import= "aca.catalogo.spring.CatPais"%>
<%@ page import= "aca.catalogo.spring.CatEstado"%>
<%@ page import= "aca.catalogo.spring.CatCiudad"%>
<%@ page import= "aca.catalogo.spring.CatReligion"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>
<%@ page import= "aca.alumno.spring.AlumAcademico"%>
<%@ page import= "aca.vista.spring.AlumnoCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String cursoCargaId			= request.getParameter("CursoCargaId");
	String maestro 				= request.getParameter("Maestro");
	String materia 				= request.getParameter("Materia");
	String matricula 			= "";
	
	List<AlumnoCurso> lisAlumnos					= (List<AlumnoCurso>) request.getAttribute("lisAlumnos");
	
	// Map de tipos de califiacaciones
	HashMap <String, CatTipoCal> mapaCat 			= (HashMap <String, CatTipoCal>)request.getAttribute("mapaCat");
	HashMap <String, CatPais> mapaPaises 			= (HashMap <String, CatPais>)request.getAttribute("mapaPaises");
	HashMap <String, CatEstado> mapaEstados 		= (HashMap <String, CatEstado>)request.getAttribute("mapaEstados");
	HashMap <String, CatCiudad> mapaCiudades		= (HashMap <String, CatCiudad>)request.getAttribute("mapaCiudades");
	HashMap <String, CatReligion> mapaReligiones	= (HashMap <String, CatReligion>)request.getAttribute("mapaReligiones");
	HashMap <String, AlumPersonal> mapaPersonal		= (HashMap <String, AlumPersonal>)request.getAttribute("mapaPersonal");
	HashMap <String, AlumAcademico> mapaAcademico	= (HashMap <String, AlumAcademico>)request.getAttribute("mapaAcademico");
%>
<!--  Encabezado -->
<body>
	<div class="container-fluid my-1">
		<h3>Students in the subject<small class="text-muted"> ( <%=materia%> - <%=maestro%> ) </small></h3>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="cursos"><spring:message code="aca.Regresar"/></a>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row">
			<!-- -->
			<%
				for(AlumnoCurso ac: lisAlumnos){
					matricula = ac.getCodigoPersonal();

					AlumPersonal alumPersonal = new AlumPersonal();
					if(mapaPersonal.containsKey(matricula)){
						alumPersonal = mapaPersonal.get(matricula);
					}

					AlumAcademico alumAcademico = new AlumAcademico();
					if (mapaAcademico.containsKey(matricula)){
						alumAcademico = mapaAcademico.get(matricula);
					}
					String paisNombre = "-";
					if (mapaPaises.containsKey(alumPersonal.getPaisId())){
						paisNombre = mapaPaises.get(alumPersonal.getPaisId()).getNombrePais();
					}

					String estadoNombre = "-";
					if (mapaEstados.containsKey(alumPersonal.getPaisId()+alumPersonal.getEstadoId())){
						estadoNombre = mapaEstados.get(alumPersonal.getPaisId()+alumPersonal.getEstadoId()).getNombreEstado();
					}

					String ciudadNombre = "-";
					if (mapaCiudades.containsKey(alumPersonal.getPaisId()+alumPersonal.getEstadoId()+alumPersonal.getCiudadId())){
						ciudadNombre = mapaCiudades.get(alumPersonal.getPaisId()+alumPersonal.getEstadoId()+alumPersonal.getCiudadId()).getNombreCiudad();
					}

					String religionNombre = "-";
					if (mapaReligiones.containsKey(alumPersonal.getReligionId())){
						religionNombre = mapaReligiones.get(alumPersonal.getReligionId()).getNombreReligion();
					}

					String nombre = alumPersonal.getNombre()+" "+(alumPersonal.getApellidoMaterno().equals("-")?"":alumPersonal.getApellidoMaterno())+" "+alumPersonal.getApellidoPaterno();
			%>
			<div class="col-12 col-sm-8 col-md-6 col-lg-4 mb-3">
				<%
					String tipoBorder = " ";
					if(mapaCat.containsKey(ac.getTipoCalId())){
						if(ac.getTipoCalId().equals("3")) {
							tipoBorder = "border-danger";
						}else if(ac.getTipoCalId().equals("2") || ac.getTipoCalId().equals("4")) {
							tipoBorder = "border-warning";
						}
						else if(ac.getTipoCalId().equals("5") || ac.getTipoCalId().equals("6")) {
							tipoBorder = "border-secondary";
						}
					}
				%>
				<div class="card mb-3 <%=tipoBorder%> h-100" style="">
					<div class="row g-0">
						<div class="col-md-4">
							<img src="../../foto?Codigo=<%=matricula%>" class="img-fluid rounded-start" alt="..." >
						</div>
						<div class="col-md-8">
							<div class="card-body">
								<h6 class="card-title text-black"><%=nombre%></h6>
								<h6><i><%=matricula%></i></h6>
								<div class="container">
									<p class="fw-bold m-0"><%=ac.getCursoId().substring(0,8)%></p>
									<p class="fw-light m-0"><%=religionNombre%></p>
									<p class="fw-light m-0">(<%=alumPersonal.getFNacimiento()%>)</p>
									<p class="fw-light m-0" ><%=ciudadNombre%>, <%=estadoNombre%>, <%=paisNombre%></p>
									<p class="fw-light m-0"><%if(alumAcademico.getResidenciaId().equals("E"))out.print("Day Student");else out.print("Boarding Student");%></p>
									<!-- Desplegar pill de mapaCat-->
									<%
										String tipoCal = "<span class='badge bg-warning rounded-pill'>0</span>";
										if(mapaCat.containsKey(ac.getTipoCalId())){
											if(ac.getTipoCalId().equals("3")) {
												tipoCal = "<span class='badge bg-danger rounded-pill'>"+mapaCat.get(ac.getTipoCalId()).getNombreTipoCal()+"</span>";
											}else if(ac.getTipoCalId().equals("2") || ac.getTipoCalId().equals("4")) {
												tipoCal = "<span class='badge bg-warning rounded-pill'>"+mapaCat.get(ac.getTipoCalId()).getNombreTipoCal()+"</span>";
											}
											else if(ac.getTipoCalId().equals("5") || ac.getTipoCalId().equals("6")) {
												tipoCal = "<span class='badge bg-secondary rounded-pill'>"+mapaCat.get(ac.getTipoCalId()).getNombreTipoCal()+"</span>";
											}
											else{
												tipoCal = "<span class='badge bg-primary rounded-pill'>"+mapaCat.get(ac.getTipoCalId()).getNombreTipoCal()+"</span>";
											}
										}
									%>
									<div><%=tipoCal%></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<%
				}
			%>
		</div>
	</div>
</body>