<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmSolicitud"%>
<%@page import="aca.admision.spring.AdmAcademico"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatEstado"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatReligion"%>
<%@page import="aca.podium.spring.Notas"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<head>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>
	function Mostrar() {
		if (document.frmIngreso.fechaIni.value != "" && document.frmIngreso.fechaFin.value != "") {
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
	String nombreAlumno 	= (String)request.getAttribute("nombreAlumno");
	
	List<AdmSolicitud> lisAlumnos 					= (List<AdmSolicitud>)request.getAttribute("lisAlumnos");

	HashMap<String,AdmAcademico> mapAcademico 		= (HashMap<String,AdmAcademico>)request.getAttribute("mapaAcademico");	
	HashMap<String,CatCarrera> mapaCarreras 		= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,CatFacultad> mapaFacultades		= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String,String> mapFechaDocumento		= (HashMap<String,String>)request.getAttribute("mapFechaDocumento");
	HashMap<String,String> mapFechaAceptado			= (HashMap<String,String>)request.getAttribute("mapFechaAceptado");
	HashMap<String,AdmAcademico> mapModalidadId		= (HashMap<String,AdmAcademico>)request.getAttribute("mapModalidadId");
	HashMap<String,String> mapModalidades			= (HashMap<String,String>)request.getAttribute("mapModalidades");
	HashMap<String,String> mapInscritos				= (HashMap<String,String>)request.getAttribute("mapInscritos");
	HashMap<String,Notas> mapaNotasPorFechaFolio	= (HashMap<String,Notas>)request.getAttribute("mapaNotasPorFechaFolio");
	HashMap<String,Date> mapaFechaPorFolio			= (HashMap<String,Date>)request.getAttribute("mapaFechaPorFolio");

%>
<body>
<div class="container-fluid">
	<h2><%=institucion %>, A.C. </h2> 
	<form name="frmIngreso" action="reportePodium" method="post">
	<div class="alert alert-info d-flex align-items-center" align="left">
  		Start Date: &nbsp; <input type="text" class="form-control" style="width:120px" data-date-format="dd/mm/yyyy" id="fechaIni" name="fechaIni" value="<%=fechaIni%>" maxlength="10" size="12" />
		&nbsp;&nbsp;
		End Date:&nbsp; <input type="text" class="form-control" style="width:120px" data-date-format="dd/mm/yyyy" id="fechaFin" name="fechaFin" value="<%=fechaFin%>" maxlength="10" size="12" />
		&nbsp; &nbsp;
		<input class="btn btn-primary" type="submit" value="Show" onclick="Mostrar();"/>
  	</div>
	</form>				
	<table id="table" align="center" class="table table-striped table-bordered"<c:catch></c:catch>'>
	<thead>
		<tr class= "table-info">
			<th width="1%"><h3>#</h3></th>
			<th width="3%"><h3><spring:message code="aca.Folio"/></h3></th>
			<th width="5%"><h3>Student ID</h3></th>
			<th width="7%"><h3>Modality</h3></th>
			<th width="20%"><h3>Surname</h3></th>
			<th width="20%"><h3>Name</h3></th>
			<th width="5%"><h3>Faculty</h3></th>
			<th width="20%"><h3><spring:message code="aca.Carrera"/></h3></th>
			<th width="7%"><h3><spring:message code="aca.Correo"/></h3></th>
			<th width="3%"><h3>Phone Number</h3></th>
			<th width="3%"><h3>Date</h3></th>
			<th width="5%"><h3>Logic</h3></th>
			<th width="10%"><h3>Mathematics</h3></th>
			<th width="10%"><h3>Spanish</h3></th>			
			<th width="10%"><h3>English</h3></th>
			<th width="10%"><h3>Biology</h3></th>
			<th width="11%"><h3>Physics</h3></th>
			<th width="11%"><h3>Chemistry</h3></th>
		</tr>
		</thead>
	<tbody>
<%		
		int row = 1;
		for(AdmSolicitud alumno : lisAlumnos){
				
			String carreraId		= "-";
			String nombreCarrera	= "-";
			String facultadId		= "-";
			String nombreFacultad	= "-";
			String fechaDocumento	= "-";
			String fechaAceptado	= "-";
			String modalidadId		= "-";
			String nombreModalidad	= "-";
			String inscrito			= "NO";
			
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
					inscrito = "Sí";
				}
			}
			
			if(mapModalidadId.containsKey(alumno.getFolio())){
				modalidadId = mapModalidadId.get(alumno.getFolio()).getModalidad();
			}
			
			if(mapModalidades.containsKey(modalidadId)){
				nombreModalidad = mapModalidades.get(modalidadId);
			}
			
			if(mapFechaDocumento.containsKey(alumno.getFolio())){
				fechaDocumento = mapFechaDocumento.get(alumno.getFolio());
			}
			
			if(mapFechaAceptado.containsKey(alumno.getFolio())){
				fechaAceptado = mapFechaAceptado.get(alumno.getFolio());
			}	
			
			float resLog 	= 0;		
			float resMat 	= 0;		
			float resEsp 	= 0;		
			float resIng 	= 0;
			float resBio 	= 0;
			float resFis 	= 0;
			float resQui 	= 0;

			if(mapaNotasPorFechaFolio.containsKey(alumno.getFolio())){
				resLog 	= mapaNotasPorFechaFolio.get(alumno.getFolio()).getResLog();		
				resMat 	= mapaNotasPorFechaFolio.get(alumno.getFolio()).getResMat();		
				resEsp 	= mapaNotasPorFechaFolio.get(alumno.getFolio()).getResEsp();		
				resIng 	= mapaNotasPorFechaFolio.get(alumno.getFolio()).getResIng();
				resBio 	= mapaNotasPorFechaFolio.get(alumno.getFolio()).getResBio();
				resFis 	= mapaNotasPorFechaFolio.get(alumno.getFolio()).getResFis();
				resQui 	= mapaNotasPorFechaFolio.get(alumno.getFolio()).getResQui();
			}	
			
			Date fecha = new Date();
			if(mapaFechaPorFolio.containsKey(alumno.getFolio())){
				fecha = mapaFechaPorFolio.get(alumno.getFolio());
			}
			
			if(resLog > 0 || resMat > 0 || resEsp > 0 || resIng > 0 || resBio > 0 || resFis > 0 || resQui > 0){
%>
					<tr>
						<td align="center"><em><%=row%></em></td>
						<td align="center"><%=alumno.getFolio()%></td>
						<td><%=alumno.getMatricula() %></td>
						<td><%=nombreModalidad%></td>
						<td><%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%></td>
						<td><%=alumno.getNombre()%></td>
						<td><%=nombreFacultad%></td>
						<td><%=nombreCarrera%></td>
						<td><%=alumno.getEmail()%></td>
						<td><%=alumno.getTelefono()%></td>
						<td><%=fecha%></td>
						<td><span class=<%=resLog > 0 ? "'badge bg-info'" : ""%>><%=formato.format(resLog)%></span></td>
						<td><span class=<%=resMat > 0 ? "'badge bg-info'" : ""%>><%=formato.format(resMat)%></span></td>
						<td><span class=<%=resEsp > 0 ? "'badge bg-info'" : ""%>><%=formato.format(resEsp)%></span></td>
						<td><span class=<%=resIng > 0 ? "'badge bg-info'" : ""%>><%=formato.format(resIng)%></span></td>
						<td><span class=<%=resBio > 0 ? "'badge bg-info'" : ""%>><%=formato.format(resBio)%></span></td>
						<td><span class=<%=resFis > 0 ? "'badge bg-info'" : ""%>><%=formato.format(resFis)%></span></td>
						<td><span class=<%=resQui > 0 ? "'badge bg-info'" : ""%>><%=formato.format(resQui)%></span></td>
					</tr>
<%		
				row++;
			}
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