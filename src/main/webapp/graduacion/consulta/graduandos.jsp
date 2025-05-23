<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.graduacion.spring.AlumEvento"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatEstado"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.catalogo.spring.CatDivision"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.graduacion.spring.AlumEgreso"%>
<%@page import="aca.plan.spring.MapaPlan"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<html>
<head>
	<script type="text/javascript">
		function recarga() {
			document.frmGraduacion.submit();
		}
	</script>
</head>
<%
	// Creacion de Variables
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	formato.setRoundingMode(java.math.RoundingMode.DOWN);

	String eventoId 		= (String)request.getAttribute("eventoId");
	String fechaEvento		= (String)request.getAttribute("fechaEvento");
	String ordenAlumno 		= (String)request.getAttribute("ordenAlumno");
	String filtroAtuendo	= (String)request.getAttribute("atuendo");	
	String resultado 		= "-";
	String mensaje			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	int numMex				= (int)request.getAttribute("numMex");
	int totalHom			= (int)request.getAttribute("hombres");
	int totalMujeres		= (int)request.getAttribute("mujeres");
	int solteros			= (int)request.getAttribute("solteros");
	int casados				= (int)request.getAttribute("casados");
	int viudos				= (int)request.getAttribute("viudos");
	int divorciados			= (int)request.getAttribute("divorciados");
	int internos			= (int)request.getAttribute("internos");
	int externos			= (int)request.getAttribute("externos");

	boolean hayPendientes		= (boolean)request.getAttribute("pendientes");
	
	// Lista de eventos de graduacion
	List<AlumEvento> lisEvento = (List<AlumEvento>)request.getAttribute("lisEventos");	
	
	// Obtiene el primer evento de la lista
	if (eventoId.equals("0")&& lisEvento.size()>0){
		eventoId = lisEvento.get(0).getEventoId();
	} 
	
	// Lista de alumnos graduandos
	List<AlumEgreso> lisAlumnos = (List<AlumEgreso> )request.getAttribute("lisAlumnos");
	
	HashMap<String, AlumPersonal> mapAlumno 		= (HashMap<String,AlumPersonal>)request.getAttribute("mapaPersonal");
	HashMap<String, CatFacultad> mapFacultades		= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String, CatCarrera> mapCarrera 			= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String, CatPais> mapPaises 				= (HashMap<String,CatPais>)request.getAttribute("mapaPaises");
	HashMap<String, CatEstado> mapEstado 			= (HashMap<String,CatEstado>)request.getAttribute("mapaEstados");
	HashMap<String, String> mapModalidad 			= (HashMap<String,String>)request.getAttribute("mapModalidad");
	HashMap<String, CatModalidad> mapCatMod			= (HashMap<String,CatModalidad>)request.getAttribute("mapCatMod");
	HashMap<String, CatDivision> mapaDivisiones 	= (HashMap<String,CatDivision>)request.getAttribute("mapaDivisiones");
	HashMap<String,AlumAcademico> mapaAcademico		= (HashMap<String,AlumAcademico>)request.getAttribute("mapaAcademico");
	HashMap<String,MapaPlan> mapMapaPlan			= (HashMap<String,MapaPlan>)request.getAttribute("mapMapaPlan");
	HashMap<String,String> mapaMaterias				= (HashMap<String,String>)request.getAttribute("mapaMaterias");
	HashMap<String,AlumPlan> mapaAlumPlan		 	= (HashMap<String,AlumPlan>)request.getAttribute("mapaAlumPlan");
	HashMap<String,AlumEgreso> mapaAlumGraEvento	= (HashMap<String,AlumEgreso>)request.getAttribute("mapaAlumGraEvento");
	HashMap<String,String> mapaAtuendos				= (HashMap<String,String>)request.getAttribute("mapaAtuendos");
	HashMap<String,String> mapaNivelAlumGraduados	= (HashMap<String,String>)request.getAttribute("mapaNivelAlumGraduados");
	HashMap<String,String> mapaDocEventoId			= (HashMap<String,String>)request.getAttribute("mapaDocEventoId");
	HashMap<String,String> mapaPendientesPlan		= (HashMap<String,String>)request.getAttribute("mapaPendientesPlan");
%>
<body>
<div class="container-fluid">
	<h2>Graduate Stuents List</h2>
	<form name="frmGraduacion" action="graduandos" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<spring:message code="aca.Evento"/>:
		<select class="form-select" id="EventoId" name="EventoId" onchange="javascript:recarga()" style="width:400px;">
		<%				
			int row = 0;
			for (AlumEvento evento: lisEvento) {
				row++;			
		%>
			<option value="<%=evento.getEventoId()%>" <%=evento.getEventoId().equals(eventoId)?"Selected":""%>>
				<%=evento.getEventoNombre()%>[<%=evento.getFecha()%>]
			</option>
		<%	} %>
		</select>
		&nbsp;&nbsp;
		<spring:message code="aca.Orden"/>:
		<select class="form-select" id="OrdenNombre" name="OrdenNombre" onchange="javascript:recarga()" style="width:200px;">
			<option value="Apellido" <%=ordenAlumno.equals("Apellido")?"Selected":""%>>By Surname</option>
 			<option value="Nombre" <%=ordenAlumno.equals("Nombre")?"Selected":""%>>By Name</option>
		</select>	
		&nbsp;&nbsp;
		<select class="form-select" id="Atuendo" name="Atuendo" onchange="javascript:recarga()" style="width:120px;">
			<option value="X" <%=filtroAtuendo.equals("X")?"Selected":""%>>All</option>
 			<option value="S" <%=filtroAtuendo.equals("S")?"Selected":""%>>With Attire</option>
 			<option value="N" <%=filtroAtuendo.equals("N")?"Selected":""%>>Without Attire</option>
		</select>
		&nbsp;&nbsp;
		<a class="btn btn-primary" href="updateProm?EventoId=<%=eventoId%>"><i class="fas fa-sync-alt"></i> Average</a>	
		&nbsp;&nbsp;
<%  if(hayPendientes){%>
		<a class="btn btn-warning" href="pendientes?EventoId=<%=eventoId%>">Pending</a>	
<% 	}%>
	</div>
	</form>
<%	if(!mensaje.equals("-")){ %>
	<div class="alert alert-success"><%=mensaje%></div>
<%	} %>
	<table class="table table-sm table-bordered">
<%
	int pre = 0;
	int lic = 0;
	int pos = 0;
	int doc = 0;
	int esp = 0;
	int conTituloPre = 0;
	int conTituloLic = 0;
	int conTituloPos = 0;
	int conTituloDoc = 0;
	int conTituloEsp = 0;
	String carrera = "X";		
	row = 0;
	int hombres = 0, mujeres = 0;
	for (AlumEgreso alumno : lisAlumnos) {
		row++;
		
		if ( !carrera.equals(alumno.getCarreraId())){			
			carrera = alumno.getCarreraId();
			String nombreCarrera = "";
			
			String facultadNombre 	= "-";
			String facultadId		= "0"; 
			if (mapCarrera.containsKey(carrera)){
				nombreCarrera 	= mapCarrera.get(carrera).getNombreCarrera();
				facultadId 		= mapCarrera.get(carrera).getFacultadId();
				if (mapFacultades.containsKey(facultadId)){
					facultadNombre = mapFacultades.get(facultadId).getTitulo()+" de "+mapFacultades.get(facultadId).getNombreFacultad();
				}
			}
%>
		<tr align="left">
			<td colspan="24">
				<h3><%=carrera%>: <%=nombreCarrera%><small class="text-muted fs-4">( <%=facultadId%>:<%=facultadNombre%> )</small></h3></font>
			</td>
		</tr>	
		<tr class="table-dark">
			<th style="text-align:center"><spring:message code="aca.Numero"/></th>
			<th style="text-align:center"><spring:message code="aca.Matricula"/></th>
<% 		if(ordenAlumno.equals("Apellido")){%>
			<th><spring:message code="aca.Apellidos"/></th>
			<th><spring:message code="aca.Nombre"/></th>			
<% 		}else{%>
			<th><spring:message code="aca.Nombre"/></th>			
			<th><spring:message code="aca.Apellidos"/></th>
<% 		}%>
			<th style="text-align:center"><i class="fas fa-exclamation-triangle"></i></th>			
			<th style="text-align:center">Authorized</th>			
			<th style="text-align:center">Confirmed</th>
			<th style="text-align:center">Attire</th>
			<th style="text-align:center"><spring:message code="aca.Genero"/></th>
			<th style="text-align:center"><spring:message code="aca.Edad"/></th>
			<th style="text-align:center">Res.</th>
			<%-- <th>Email</th> --%>
			<th><spring:message code="aca.Modalidad"/></th>
			<th><spring:message code="aca.Plan"/></th>
			<th>Plan Name</th>
			<th style="text-align:center">Start D.</th>
			<th style="text-align:center">Registration D.</th>
			<th class="center">Enr. Sub.</th>
			<th style="text-align:center"><spring:message code="aca.Avance"/></th>
			<th style="text-align:center">GPA</th>
			<th style="text-align:center">Deg.</th>
			<%-- <th style="text-align:center">Div.</th> --%>
			<th><spring:message code="aca.Pais"/></th>
			<th><spring:message code="aca.Estado"/></th>
		</tr>	
<% 		}
		String nombreAlumno		= "-";
		String apellidosAlumno	= "-";
		String paisId 			= "0";		
		String estadoId 		= "0";
		String fechaNac			= "";
		String genero 			= "-";
		String modalidad		= "0";
		String residencia		= "-";
		String correo			= "-";
		
		if ( mapAlumno.containsKey(alumno.getCodigoPersonal())){
			AlumPersonal personal = mapAlumno.get(alumno.getCodigoPersonal());
			nombreAlumno 	= personal.getNombre();
			apellidosAlumno	= personal.getApellidoPaterno()+" "+personal.getApellidoMaterno();
			
			paisId 			= personal.getPaisId();
			estadoId		= personal.getEstadoId();
			fechaNac		= personal.getFNacimiento();
			correo			= personal.getEmail();
			if(personal.getSexo().equals("M")){
				genero = "H";
				hombres++;
			}else{
				genero = "M";
				mujeres++;
			}
			
			if(mapaAcademico.containsKey(alumno.getCodigoPersonal())){
				if(mapaAcademico.get(alumno.getCodigoPersonal()).getResidenciaId().equals("E")){
					residencia = "Boarding";
				}else{
					residencia = "Day Student";
				}
			}
		}
		
		int edadTruncado = aca.util.Fecha.edad(fechaNac, fechaEvento);
		
		if (mapModalidad.containsKey(alumno.getCodigoPersonal())){
			modalidad = mapModalidad.get(alumno.getCodigoPersonal());
			if (mapCatMod.containsKey(modalidad)){
				modalidad = mapCatMod.get(modalidad).getNombreModalidad();
			}
		}
		
		
		String nombrePais = "-";
		if (mapPaises.containsKey(paisId)) nombrePais = mapPaises.get(paisId).getNombrePais();
		
		String divisionId 		= "0";
		String nombreDivision 	= "-";
		if (mapPaises.containsKey(paisId)){
			divisionId = mapPaises.get(paisId).getDivisionId();
			if (mapaDivisiones.containsKey(divisionId)){
				nombreDivision = mapaDivisiones.get(divisionId).getNombreCorto();
			}
		}
		
		String nombreEstado = "-";
		if (mapEstado.containsKey(paisId+estadoId)) nombreEstado = mapEstado.get(paisId+estadoId).getNombreEstado();
		
		String nivelAlumno = "-";
		if (mapCarrera.containsKey(alumno.getCarreraId())){
			nivelAlumno = mapCarrera.get(alumno.getCarreraId()).getNivelId();
		}
		
		if(nivelAlumno.equals("1")){
			pre++;
			if(alumno.getTitulado().equals("S")){
				conTituloPre++;
			}
		}else if(nivelAlumno.equals("2")){
			lic++;
			if(alumno.getTitulado().equals("S")){
				conTituloLic++;
			}
		}else if(nivelAlumno.equals("3")){
			pos++;
			if(alumno.getTitulado().equals("S")){
				conTituloPos++;
			}
		}else if(nivelAlumno.equals("4")){
			doc++;
			if(alumno.getTitulado().equals("S")){
				conTituloDoc++;
			}
		}else if(nivelAlumno.equals("6")){
			esp++;
			if(alumno.getTitulado().equals("S")){
				conTituloEsp++;
			}
		}
		
		String fechaIngreso = "-";				
		if (mapaAcademico.containsKey(alumno.getCodigoPersonal())){
			fechaIngreso = mapaAcademico.get(alumno.getCodigoPersonal()).getFAdmision();
		}
		
		fechaIngreso = fechaIngreso.substring(0,10).replace("-","/");

		String nombrePlan = "-";				
		if (mapMapaPlan.containsKey(alumno.getPlanId())){
			nombrePlan = mapMapaPlan.get(alumno.getPlanId()).getNombrePlan();
		}
		
		String matPen = "<span class='badge bg-secondary'>0</span>";
		if (mapaMaterias.containsKey(alumno.getCodigoPersonal())){
			matPen = "<span class='badge bg-warning'>"+mapaMaterias.get(alumno.getCodigoPersonal())+"</span>";
		}
		
		String avance = "<span class='badge bg-warning'>Pending</span>";
		if (mapaAlumPlan.containsKey(alumno.getCodigoPersonal()+alumno.getPlanId())){
			avance = mapaAlumPlan.get(alumno.getCodigoPersonal()+alumno.getPlanId()).getFinalizado();
			if (avance.equals("S")) 
				avance = "<span class='badge bg-success'>Complete</span>";
			else
				avance = "<span class='badge bg-warning'>Pending</span>";
		}

		AlumEgreso alumEgreso = new AlumEgreso();
		if (mapaAlumGraEvento.containsKey(alumno.getCodigoPersonal())){
			alumEgreso = mapaAlumGraEvento.get(alumno.getCodigoPersonal());
		}

		String atuendo = "N";
		if (mapaAtuendos.containsKey(alumno.getCodigoPersonal())){
			atuendo = mapaAtuendos.get(alumno.getCodigoPersonal());
		}

		String titulado = "N";
		if (mapaNivelAlumGraduados.containsKey(alumno.getCodigoPersonal())){
			if(mapaNivelAlumGraduados.get(alumno.getCodigoPersonal()).equals("3") || mapaNivelAlumGraduados.get(alumno.getCodigoPersonal()).equals("4")){
				titulado = "S";
			}
		}

		String pendientes = "0";
		if (mapaPendientesPlan.containsKey(alumno.getCodigoPersonal()+alumno.getPlanId())){
			pendientes = mapaPendientesPlan.get(alumno.getCodigoPersonal()+alumno.getPlanId());
		}
		
		if (mapaDocEventoId.containsKey(alumno.getCodigoPersonal())){
			titulado = "S";
		}
%>					
		<tr>
			<td style="text-align:center"><%=row%></td>
			<td style="text-align:center"><%=alumno.getCodigoPersonal()%></td>
<% 		if(ordenAlumno.equals("Apellido")){%>
			<td><%=apellidosAlumno%></td>
			<td><%=nombreAlumno%></td>			
<% 		}else{%>
			<td><%=nombreAlumno%></td>			
			<td><%=apellidosAlumno%></td>
<% 		}%>
			<td><%=pendientes%></td>
			<td style="text-align:center">
<%		if (alumEgreso.getPermiso().equals("S")){%>
			<span class="badge bg-success btn-sm rounded-pill">YES</span>
<% 		}else{%>			
			<span class="badge bg-danger btn-sm rounded-pill">NO</span>
<% 		}%>
			</td>			
			<td style="text-align:center">
<%		if (alumEgreso.getConfirmar().equals("N")){%>
			<span class="badge bg-danger rounded-pill">NO</span>
<% 		}else{%>
			<span class="badge bg-success btn-sm rounded-pill">YES</span>
<% 		}%>
			</td>
			<td style="text-align:center">
<%		if (atuendo.equals("N")){%>
			<span class="badge bg-danger rounded-pill">NO</span>
<% 		}else{%>
			<span class="badge bg-success btn-sm rounded-pill">YES</span>
<% 		}%>
			</td>
			<td style="text-align:center"><%=genero%></td>
			<td style="text-align:center"><%=edadTruncado%></td>
			<td style="text-align:center"><%=residencia%></td>
			<%-- <td><%=correo%></td> --%>
			<td><%=modalidad%></td>
			<td><%=alumno.getPlanId()%></td>
			<td><%=nombrePlan%></td>
			<td style="text-align:center"><%=fechaIngreso%></td>
			<td style="text-align:center"><%=alumno.getFecha()%></td>
			<td class="center"><%=matPen%></td>
			<td style="text-align:center"><%=avance%></td>
			<td style="text-align:center"><%=alumno.getPromedio()%></td>
			<td style="text-align:center"><%=titulado.equals("S")?"YES":"NO"%></td>
			<%-- <td style="text-align:center"><%=nombreDivision%></td> --%>
			<td><%=nombrePais%></td>
			<td><%=nombreEstado%></td>
		</tr>
<%
		if (row < lisAlumnos.size() && !lisAlumnos.get(row).getCarreraId().equals(carrera)){
%>
		<tr align="left">
			<th colspan="3">
				<font size="2"><b>T O T A L --></b></font>
			</th>
			<th colspan="17">
				<font size="2"><b>M=<%=hombres%> &nbsp; F=<%=mujeres%></b></font>
			</th>
		</tr>
<%			
		hombres=0; mujeres=0;
		}
	}	
%>
	</table>
	<br><br>
<%
	//Lista de alumnos a desplegar
	if (lisAlumnos.size() > 0) {
		int numExt = lisAlumnos.size()- numMex;
%>
	<table class="table table-sm table-bordered" style="width:50%">
	<thead class="table-dark">	
		<tr>
			<th colspan="2">Graduates: <b><%=lisAlumnos.size()%></b></th>
		</tr>
	</thead>	
		<tr>
			<td>Male: <b><%=totalHom%></b></td>
			<td>Female: <b><%=totalMujeres%></b></td>
		</tr>
		<tr>
			<td>Single: <b><%=solteros%></b></td>
			<td>Married: <b><%=casados%></b></td>
		</tr>
		<tr>
			<td>Widowed: <b><%=viudos%></b></td>
			<td>Divorced: <b><%=divorciados%></b></td>
		</tr>
		<tr>
			<td>Boarding: <b><%=internos%></b></td>
			<td>Day Student: <b><%=externos%></b></td>
		</tr>
		<tr>
			<td>National: <b><%=numMex%></b></td>
			<td>International: <b><%=numExt%></b></td>
		</tr>				
	</table>
	<table class="table table-sm table-bordered" style="width:50%">
	<thead class="table-dark">	
		<tr>
			<th><b>Level</b></th>			
			<th class="text-end"><b>With Degree</b></th>
			<th class="text-end"><b>Without Degree</b></th>
			<th class="text-end"><b>Total</b></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>High School</td>
			<td class="text-center"><%=conTituloPre%></td>
			<td class="text-end"><%=pre-conTituloPre%></td>
			<td class="text-end"><%=pre%></td>
		</tr>		
		<tr>
			<td align="center">Bachelors</td>
			<td class="text-end"><%=conTituloLic%></td>
			<td class="text-end"><%=lic-conTituloLic%></td>
			<td class="text-end"><%=lic%></td>
		</tr>
		<tr>
			<td align="center">Master</td>
			<td class="text-end"><%=conTituloPos%></td>
			<td class="text-end"><%=pos-conTituloPos%></td>
			<td class="text-end"><%=pos%></td>
		</tr>					
		<tr>
			<td align="center">Doctorate</td>
			<td class="text-end"><%=conTituloDoc%></b></td>
			<td class="text-end"><%=pos-conTituloDoc%></b></td>
			<td class="text-end"><%=doc%></td>
		</tr>
		<tr>
			<td align="center">Speciality</td>
			<td class="text-end"><%=conTituloDoc%></td>
			<td class="text-end"><%=esp-conTituloDoc%></td>
			<td class="text-end"><%=esp%></td>
		</tr>
	</tbody>		
	</table>
<%
	}
%>
</div>
</body>
</html>