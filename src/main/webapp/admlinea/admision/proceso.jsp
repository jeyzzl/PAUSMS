<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.admision.spring.AdmSolicitud"%>
<%@page import="aca.admision.spring.AdmAcademico"%>
<%@page import="aca.admision.spring.AdmReservada"%>
<%@page import="aca.admision.spring.AdmPasos"%>
<%@page import="aca.admision.spring.AdmRequisito"%>
<%@page import="aca.admision.spring.AdmPago"%>
<%@page import="aca.admision.spring.AdmBanco"%>
<%@page import="aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<!doctype html>
<html lang="en">
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script>
		function Mostrar() {
			if (document.frmIngreso.fechaIni.value != "" && document.frmIngreso.fechaFin.value != "") {
				document.frmIngreso.submit();
			} else {
				alert("You must pick a date");
			}
		}
	</script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>	
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
</head>
<%	
	String institucion 			= (String)session.getAttribute("institucion");
	String codigoPersonal		= (String)session.getAttribute("codigoPersonal");	
	String fechaHoy				= aca.util.Fecha.getHoy();	
	String arrayHoy[]			= fechaHoy.split("/");
	int yearNow					= Integer.parseInt(fechaHoy.substring(6,10));	
	String fechaIni 			= request.getParameter("fechaIni")==null?String.valueOf(yearNow)+"-01-01":request.getParameter("fechaIni");
	String fechaFin 			= request.getParameter("fechaIni")==null?arrayHoy[2]+"-"+arrayHoy[1]+"-"+arrayHoy[0]:request.getParameter("fechaFin");	
	String estado 				= request.getParameter("Estados") == null ? "0" : request.getParameter("Estados");
	String admitidos 			= (String) request.getAttribute("admitidos");
	String modalidades 			= (String)request.getAttribute("modalidades");
	String nombreAlumno 		= (String)request.getAttribute("nombreAlumno");
	String institucionPaisId 	= (String)request.getAttribute("institucionPaisId");
	
	Acceso acceso 				= (Acceso)request.getAttribute("acceso");
	String asesor 				= (String)request.getAttribute("asesor");
	String planId 				= (String)request.getAttribute("planId");

	List<AdmSolicitud> lisAlumnos 					= (List<AdmSolicitud>)request.getAttribute("lisAlumnos");
	List<String> listAsesor							= (List<String>)request.getAttribute("listAsesor");
	List<AdmRequisito> listaRequisitos				= (List<AdmRequisito>)request.getAttribute("listaRequisitos");
	List<CatModalidad> listaModalidades				= (List<CatModalidad>)request.getAttribute("listaModalidades");
	List<String> lisAsesores						= (List<String>)request.getAttribute("lisAsesores");
	List<MapaPlan> listaPlanes						= (List<MapaPlan>)request.getAttribute("listaPlanes");
	
	HashMap<String,AdmAcademico> mapAcademico 		= (HashMap<String,AdmAcademico>) request.getAttribute("mapaAcademico");	
	HashMap<String,CatModalidad> mapModalidad 		= (HashMap<String,CatModalidad>)request.getAttribute("mapaModalidades");
	HashMap<String,CatCarrera> mapCarrera 			= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,CatPais> mapPais 				= (HashMap<String,CatPais>)request.getAttribute("mapaPaises");
	HashMap<String,String> mapaMaestros 			= (HashMap<String,String>)request.getAttribute("mapaMaestros");	
	HashMap<String,String> mapaReservados 			= (HashMap<String,String>)request.getAttribute("mapaReservados");	
	HashMap<String,AdmPasos> mapaPasos 				= (HashMap<String,AdmPasos>)request.getAttribute("mapaPasos");	
	HashMap<String,String> mapaResultados 			= (HashMap<String,String>)request.getAttribute("mapaResultados");	
	HashMap<String,String> mapaResultadosExamenes 	= (HashMap<String,String>)request.getAttribute("mapaResultadosExamenes");
	HashMap<String,String> mapaComentarios 			= (HashMap<String,String>)request.getAttribute("mapaComentarios");	
	HashMap<String,String> mapaExamenes 			= (HashMap<String,String>)request.getAttribute("mapaExamenes");
	HashMap<String,String> mapaNuevos				= (HashMap<String,String>)request.getAttribute("mapaNuevos");
	HashMap<String,String> mapaDocumentos			= (HashMap<String,String>)request.getAttribute("mapaDocumentos");
	HashMap<String,String> mapaDocPorFolio			= (HashMap<String,String>)request.getAttribute("mapaDocPorFolio");
	HashMap<String,AdmBanco> mapaBanco				= (HashMap<String,AdmBanco>)request.getAttribute("mapaBanco");
	HashMap<String,String> mapaPago					= (HashMap<String,String>)request.getAttribute("mapaPago");
	HashMap<String,String> mapaSolicitudesVacias	= (HashMap<String,String>)request.getAttribute("mapaSolicitudesVacias");
	HashMap<String,String> mapaNombrePlanes			= (HashMap<String,String>)request.getAttribute("mapaNombrePlanes");
%>
<body>
<div class="container-fluid">
	<h2>Admission Process <small class="text-muted fs-5">(Applicant list)</small></h2>
	<div class="alert alert-info align-items-center"> 
		<form name="frmIngreso" action="proceso" method="post" class="form-inline d-flex align-items-center">
		<div class="d-flex align-items-center mr-1">	
			Progress:&nbsp;
			<select name="Estados" style="width: 8rem" class="form-select ml-5">
	  			<option value="0" <%=estado.equals("0")?"Selected":""%>>All</option>
	  			<option value="1" <%=estado.equals("1")?"Selected":""%>>1.Account Generated</option>
	  			<option value="2" <%=estado.equals("2")?"Selected":""%>>2.Completed Application</option>
	  			<option value="3" <%=estado.equals("3")?"Selected":""%>>3.Completed Documents</option>
	  			<option value="4" <%=estado.equals("4")?"Selected":""%>>4.Registered Fees</option>
	  			<option value="5" <%=estado.equals("5")?"Selected":""%>>5.Transferred</option>
	  			<option value="6" <%=estado.equals("6")?"Selected":""%>>6.Inactive</option>
	  		</select>
		</div>
		<div class="d-flex align-items-center mx-1">
	  		Admitted:&nbsp;
	  		<select name="Admitidos" style="width: 6rem" class="form-select">
	  			<option value="0" <%=admitidos.equals("0") ? "Selected" : ""%>><spring:message code="aca.Todos"/></option>
	  			<option value="1" <%=admitidos.equals("1") ? "Selected" : ""%>><spring:message code="aca.EnProceso"/></option>
	  			<option value="2" <%=admitidos.equals("2") ? "Selected" : ""%>><spring:message code="aca.Admitidos"/></option>
	  		</select>
		</div>
		<div class="d-flex align-items-center mx-1">
	  		Modality:&nbsp;
			<select name="Modalidades" style="width: 8rem" class="form-select">
	 			<option value="0" <%=modalidades.equals("0") ? "Selected" : ""%>>All</option>
	<% 			for(CatModalidad modalidad : listaModalidades){ %> 
				<option <%=modalidades.equals(modalidad.getModalidadId()) ? "Selected" : ""%> value="<%=modalidad.getModalidadId()%>"><%=modalidad.getNombreModalidad()%></option>
	<%			} %>
			</select>
		</div>
		<div class="d-flex align-items-center mx-1">
			Advisor:&nbsp;
			<select name="Asesor" class="form-select" style="width: 6rem">
				<option value="0000000" <%=asesor.equals("0") ? " Selected":""%>>All</option>
	<% 				for(String codigoAsesor : lisAsesores){
					String nombre = "All";
					if (mapaMaestros.containsKey(codigoAsesor)){
						nombre = mapaMaestros.get(codigoAsesor);
					}		
	%>
	  			<option value="<%=codigoAsesor%>" <%=asesor.equals(codigoAsesor) ? " Selected":""%>><%=nombre%></option>
	<% 				}%>
  			</select>
		</div>
		<div class="d-flex align-items-center mx-1">
			Plan:&nbsp;
			<select name="PlanId" class="form-select" style="width: 8rem">
				<option value="0" <%=planId.equals("0") ? " Selected":""%>>All</option>
	<% 				for(MapaPlan plan : listaPlanes){		%>
	  			<option value="<%=plan.getPlanId()%>" <%=planId.equals(plan.getPlanId()) ? " Selected":""%>><%=plan.getNombrePlan()%></option>
	<% 				}%>
  			</select>
		</div>
		<div class="d-flex align-items-center">
			<label class="col-form-label m-1" >Start Date:</label>
	  		<input type="date" name="fechaIni" min="1950-01-01" max="2050-12-31" class="form-control" style="width:9.5rem" value="<%=fechaIni%>">
		</div>
		<div class="d-flex align-items-center mx-1">
			<label class="col-form-label m-1">End Date:</label>
	  		<input type="date" name="fechaFin" min="1950-01-01" max="2050-12-31" class="form-control" style="width:9.5rem" value="<%=fechaFin%>">
		</div>
		<div class="d-flex align-items-center mx-1">
			<label class="col-form-label m-1">Name: </label>
			<input type="text" class="form-control" name="NombreAlumno" style="width:8rem" value="<%=nombreAlumno%>" />
		</div>
			<input class="btn btn-primary mx-1" type="submit" value="Show" onclick="Mostrar();"/>
			<a href="sinSolicitud" class="btn btn-success" title="Registered without Application"><i class="fas fa-search"></i></a>
    	</form>
  	</div>
  	<% if(codigoPersonal.equals("XX")){ 
	%>  	
  	<script>
		function copiaDatosFaltantes (){
			jQuery.get("copiaDatosFaltantes?matLike="+jQuery("#matLike").val(), function(data){
				jQuery("#divCopiaDatosFaltantes").html("<p>"+data+"</p>")
			});
		}
	</script>
  	<div class="alert alert-info" align="left" id="divCopiaDatosFaltantes">
  		<input type="text" class="text" id="matLike" value="120" />
  		<input class="btn btn-primary" type="button" value="Copy data to registered students" onclick="copiaDatosFaltantes();" />
  	</div>
  	<% }
	 %>		
  	<div class="d-flex justify-content"> 
  		<input type="text" class="form-control search-query "  placeholder="Search" id="buscar" style="width:200px;">
  	</div>
  		
	<table id="table" class="table" data-toggle="table" data-pagination="false" data-search="true" data-show-columns="true" data-show-header="true" data-show-footer="true">
	
		<thead>
			<tr>
<%		
	if(lisAsesores.contains(codigoPersonal)){
%>
				<th width="1%"><h4>&nbsp;</h4></th>
<%	} %>
				<th width="8%">#</th>
				<th width="3%" title="Process ID">ID</th>
				<th width="6%">Created</th> 
				<th width="18%"><spring:message code="aca.Nombre"/></th>
				<th width="5%"><spring:message code="aca.Genero"/></th>
				<th width="22%"><spring:message code="aca.Carrera"/></th>
				<th width="10%"><spring:message code="aca.Pais"/></th>
				<th width="2%"><spring:message code="aca.Modalidad"/></th>
				<th width="2%">Ap.Type</th>
				<th width="6%"><spring:message code="aca.Correo"/></th>
				<th width="8%"><spring:message code="aca.Avance"/></th>
				<th width="8%">Documents</th>
				<th width="8%"><spring:message code="aca.Asesor"/></th>
				<th data-sortable="true" width="10%">Fees</th>
				<th width="10%" title="Comments">Com.</th>
			</tr>
		</thead>
		<tbody>
<%		
		int row = 0;
		for(AdmSolicitud alumno : lisAlumnos){
	
			int avan = Integer.parseInt(alumno.getEstado());
			
			// Modalidad del alumno
			String modalidad 		= "-";
			String modalidadId 		= "";
			String carreraId		= "-";
			String carrera			= "-";
			String carreraCompleta	= "-";
			String paisId 			= alumno.getPaisId()==null?institucionPaisId:alumno.getPaisId();
			String paisNombre		= "";
			
			// Fecha de ingreso
			String fechaIngreso 	= alumno.getFechaIngreso()==null?"-":alumno.getFechaIngreso();
											
			if (mapPais.containsKey(paisId)){
       			CatPais p = mapPais.get(paisId);
       			paisNombre 	= p.getNombrePais();
       		}
			
			// Datos academicos del solicitante 
			String tipoSolicitud = "";
			String nombrePlan = "";
			if (mapAcademico.containsKey(alumno.getFolio())){
				AdmAcademico academico = mapAcademico.get(alumno.getFolio());

				tipoSolicitud = academico.getTipo();

				if(academico.getTipo().equals("S")) tipoSolicitud = "School Leaver";
				if(academico.getTipo().equals("N")) tipoSolicitud = "Non-School Leaver";
				if(academico.getTipo().equals("R")) tipoSolicitud = "Re-admission";
				if(academico.getTipo().equals("P")) tipoSolicitud = "Post Graduate";

				if (mapaNombrePlanes.containsKey(academico.getPlanId())){
					nombrePlan = mapaNombrePlanes.get(academico.getPlanId());
				}
				
				if (mapModalidad.containsKey(academico.getModalidad())){
        			CatModalidad mod = mapModalidad.get(academico.getModalidad());
        			modalidadId = mod.getModalidadId();
        			modalidad = mod.getNombreModalidad();
        					
        		}
				carreraId = academico.getCarreraId();
				if (mapCarrera.containsKey(academico.getCarreraId())){
					CatCarrera car = mapCarrera.get(academico.getCarreraId());
					carrera = car.getNombreCorto();
					carreraCompleta = car.getNombreCarrera();
				}
			}

			//Verifica si el alumno ha realizado el pago 
			boolean tienePago = false;
			if(mapaPago.containsKey(alumno.getFolio())){
				tienePago = true;
			}
			
			// Verifica si el alumno escogio sede
			boolean tieneSede = false;
			if (mapaReservados.containsKey( alumno.getFolio() ) ){
				tieneSede = true;
			}
			
			if( alumno.getEstado().equals("0") || acceso.getAccesos().indexOf(carreraId)!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") || codigoPersonal.equals(alumno.getAsesorId()) || codigoPersonal.equals(alumno.getAsesorSec())){
				row++;				
				String avance = "<b>"+avan+"</b>) Registered";
				if(avan==1) avance = "<b>"+avan+"</b>) Account Generated";
				if(avan==2) avance = "<b>"+avan+"</b>) Completed Application";
				else if(avan==3){					
					if (tieneSede){
						avance = "<b>"+avan+"b</b>) Reserved Venue";
					}else{
						avance = "<b>"+avan+"a</b>) Pre-Authorized Documents";
					}
				}else if(avan==4){
					avance = "<b>"+avan+"</b>) Authorized Documents";
					if (fechaIngreso.equals("-")) avance = avance+"<br><b><font color='#AE2113'>No date</font></b>";
					if (tienePago) avance =  "<b>"+avan+"</b>) Auhtorized Payment";
				}else if (avan==5){
					avance = "<b>"+avan+"</b>) Registered (<b>"+alumno.getMatricula()+"</b>)";
					if (fechaIngreso.equals("-")) avance = avance+"<br><b><font color='#AE2113'>No date</font></b>";
				}
				
 				String ruta = "https://fulton.um.edu.mx/admission/valida";
				int random = ((int)(Math.random()*1000));
				
				String asesorNombre = "";
				if (mapaMaestros.containsKey(alumno.getAsesorId())){
					asesorNombre = mapaMaestros.get(alumno.getAsesorId());
				}			
				
				String pago = "No";
				if (mapaPasos.containsKey(alumno.getFolio()+"1")){
					pago = "Yes";
				}

				String vistoBueno = "No";
				if (mapaPasos.containsKey(alumno.getFolio()+"2")){
					vistoBueno = "Yes";
				}

				String resultados = "No";
				if (mapaPasos.containsKey(alumno.getFolio()+"4")){
					resultados = "Yes";
				}
				
				String numResult = "0";
				if(mapaResultados.containsKey(alumno.getFolio())){
					numResult = mapaResultados.get(alumno.getFolio());
				}		
				
				String resP = "No";
				if(mapaResultados.containsKey(alumno.getFolio()+"3")){
					resP = "Yes";
				}				
				String resE = "No";
				if(mapaResultados.containsKey(alumno.getFolio()+"1")){
					resE = "Yes";
				}				
				String resM = "No";
				if(mapaResultados.containsKey(alumno.getFolio()+"2")){
					resM = "Yes";
				}				
				String comentarios = "0";
				if(mapaComentarios.containsKey(alumno.getFolio())){
					comentarios = mapaComentarios.get(alumno.getFolio());
				}				

				String res = "0";
				String textoRes 	= "<span class='badge bg-warning'>0</span>";
				if(mapaResultados.containsKey(alumno.getFolio())){
					res 		= mapaResultados.get(alumno.getFolio());
					textoRes 	= "<span class='badge bg-dark'>"+res+"</span>";
				}
				
				String numExamenes 		= "0";
				String textoExamenes 	= "<span class='badge bg-warning'>0</span>";
				if(mapaExamenes.containsKey(alumno.getFolio())){
					numExamenes 	= mapaExamenes.get(alumno.getFolio());
					textoExamenes	= "<span class='badge bg-dark'>"+numExamenes+"</span>";
				}
				
				String nuevos = "0";
				if(mapaNuevos.containsKey(alumno.getFolio())){
					nuevos = mapaNuevos.get(alumno.getFolio());
				}
				
				String revisado = "0"; 
				if(mapaDocumentos.containsKey(alumno.getFolio())){
					revisado = mapaDocumentos.get(alumno.getFolio());
				}
				
				String docAlumo = "0"; 
				if(mapaDocPorFolio.containsKey(alumno.getFolio())){
					docAlumo = mapaDocPorFolio.get(alumno.getFolio());
				}
				
				int docuRequisito = 0;
				for(AdmRequisito requisito : listaRequisitos){
					String modalidadesRequisitos = "";
					if(carreraId.equals(requisito.getCarreraId())){
						modalidadesRequisitos = "-"+requisito.getModalidades();
						if(modalidadesRequisitos.contains("-"+modalidadId+"-")){
							docuRequisito++;						
						}
					}
				}
				
				String condicion = "";
				String colorCondicion = "";
				if(!nuevos.equals("0")){ 			// DOCUMENTOS ENVIADOS
					condicion = "N";
					colorCondicion = "style='color:green'"; 
				}else if(!revisado.equals("0")){  	// DOCUMENTOS REVISADOS
					condicion = "E";
					colorCondicion = "style='color:blue'";
				}else{								// SIN DOCUMENTOS
					condicion = "X";
					colorCondicion = "style='color:gray'";
				}				
%>
					<tr style="cursor:pointer;">
					<% 	if(lisAsesores.contains(codigoPersonal)){ %>
							<td class="text-center">
								<a target="_blank" href="<%=ruta+"?tgr09thujtgrjiu="+random+"&redir=1&52we1f56HGkljuymCVFfhjbg="+(Integer.parseInt(alumno.getFolio())*random) %>" data-bs-toggle="tooltip" title="<%=alumno.getCodigo()%>"><img class="button" src="../../imagenes/sesion.png" ></a>
							</td>
					<%	} %>
						<td class="text-center">
<%
						if(mapaSolicitudesVacias.containsKey(alumno.getFolio()) ){
%>
							<a href="eliminarSolicitudVacia?Folio=<%=alumno.getFolio()%>"><i class="fas fa-trash-alt text-danger"></i></a>
<%
						}
%>
							<%=row%>
						</td>
						<td class="text-center" title="Process ID"><a href="mostrarProceso?Folio=<%=alumno.getFolio()%>" class="btn btn-info d-flex align-items-center"><i class="fas fa-caret-right fa-xs mx-1"></i><%=alumno.getFolio()%></a></td>
						<td><%=alumno.getFecha()%></td>
						<td><%=alumno.getNombre()==null?"":alumno.getNombre()%> <%=alumno.getApellidoMaterno()==null?"":alumno.getApellidoMaterno()%> <%=alumno.getApellidoPaterno()==null?"":alumno.getApellidoPaterno()%></td>
						<td class="text-center"><%=alumno.getGenero().equals("F")?"Female":"Male"%></td>
						<td><div class="ayuda mensaje <%=carreraCompleta %>"><%=nombrePlan%></div></td>
						<td><%=paisNombre%></td>
						<td><%=modalidad%></td>
						<td><%=tipoSolicitud%></td>
						<td><%=alumno.getEmail() %></td>
<% 					if(avan == 2){ %>									
						<td <%=colorCondicion%> align="<%=avan==4&&fechaIngreso.equals("-")?"center":"left" %>" title="<%=condicion.equals("N")?"Documents Sent":condicion.equals("E")?"Revised Documents":"No Documents Sent"%>"><%=avance%>-<%=condicion%></td>
<% 					}else{	%>
						<td align="<%=avan==4&&fechaIngreso.equals("-")?"center":"left" %>"><%=avance%></td>
<% 					}	%>
						<td class="text-center"><%=docAlumo+" / "+docuRequisito%></td>						
						<td><%=asesorNombre%></td>						
						<td><%=pago.equals("Yes")?"<span class='badge bg-success'>Yes</span>":"<span class='badge bg-warning'>No</span>"%></td>
						<td>
<% 						if(comentarios.equals("0")){%>
							<span class="badge bg-warning" title="Comments"><%=comentarios%></span>
<% 						}else{%>
							<span class="badge bg-dark" title="Comments"><%=comentarios%></span>
<% 						}%>
						</td>						
					</tr>
<%			}
		}
%>
	</tbody>
	</table>				
</div>	
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	//Filtro (Tabla).
	$('#buscar').search();
	
	jQuery('#fechaIni').datepicker();
	jQuery('#fechaFin').datepicker();
	
	$(document).ready(function() {
	    $('#table').DataTable();
	} );
	$('#fechaIni').datepicker({
		  uiLibrary: 'bootstrap4',
		  locale: 'es-es',
	});
	//Tooltip Código 
	 var tooltipTriggerList = [].slice.call( document.querySelectorAll('[data-bs-toggle="tooltip"]'));
	 var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
		 return new bootstrap.Tooltip(tooltipTriggerEl);
	});

</script>
</body>
</html>