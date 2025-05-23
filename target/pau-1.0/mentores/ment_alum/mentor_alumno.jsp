<%@page import="java.util.List" %>
<%@page import="java.util.HashMap" %>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.mentores.spring.MentAlumno"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	String periodoId	= (String) request.getAttribute("periodoId");
	String carreraId	= (String) request.getAttribute("carreraId");
	String accion		= (String) request.getAttribute("accion");
	String fecha		= (String) request.getAttribute("fecha");
	String msj 			= (String) request.getAttribute("msj");
	String sFacultad	= (String) request.getAttribute("sFacultad");
	String activos		= (String) request.getAttribute("activos");
	
	Acceso acceso 		= (Acceso) request.getAttribute("acceso");
	
	String alumFac			= (String) request.getAttribute("alumFac");

	String mentorId			= (String) request.getAttribute("mentorId");
    String sNombre			= "";
    String nombreMaestro	= (String) request.getAttribute("nombreMaestro");
    String nombrePeriodo	= (String) request.getAttribute("nombrePeriodo");
    String nombreCarrera	= (String) request.getAttribute("nombreCarrera");
    String nombreFacultad	= (String) request.getAttribute("nombreFacultad");
    
    List<MentAlumno> lisMenAlumno = (List<MentAlumno>) request.getAttribute("lisMenAlumno");
    
	HashMap<String,AlumPersonal> mapMentorContacto = (HashMap<String, AlumPersonal>) request.getAttribute("mapMentorContacto");
	HashMap<String,CatCarrera> mapCarrera		   = (HashMap<String, CatCarrera>) request.getAttribute("mapCarrera");
	HashMap<String,String> getMapaInscritos		   = (HashMap<String, String>) request.getAttribute("getMapaInscritos");
%>
<link href="print.css" rel="stylesheet" type="text/css" media="print">
<div class="container-fluid">	
	<h5> <%=mentorId%> | <%= nombreMaestro %> <small class="text-muted fs-6">(<%=nombrePeriodo %> | Date: <%=fecha %>)</small></h5>	
	<div class="alert alert-info"><%=nombreCarrera %> | <%=nombreFacultad %></div>	
	<form action="mentor_alumno?Accion=1&carrera=<%=carreraId%>&mentor=<%=mentorId %>" method="post" name="form" id="form">	
		<div class="alert alert-info">
			<a class="btn btn-primary" href="mentor?carrera=<%=carreraId %>"><i class="fas fa-arrow-left"></i></a>
			<input name="nombre" type="text" class="text" placeholder="Student Name" />
			<a class="btn btn-primary" href="javascript:setValue('busca_clave_alumno')">Search</a>
			&nbsp;&nbsp;&nbsp;&nbsp;
			Student ID:
			<input style="width: 100px" name="matricula" type="text" class="text">
			<smal>Start Date:</smal>
			<input style="width: 100px" data-date-format="dd/mm/yyyy" name="fInicio" id="fInicio" type="text" class="text">
			<smal>End Date:</smal>
			<input style="width: 100px" data-date-format="dd/mm/yyyy" name="fFinal" id="fFinal" type="text" class="text">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="btn btn-success" href="javascript:Agregar()"><i class="fas fa-plus"></i> Add</a>			
		</div>		
		<%=msj %>		
	</form>	
	<form action="mentor_alumno?carrera=<%=carreraId%>&mentor=<%=mentorId%>" method="post" name="formas" id="formas">
		<small class="text-muted">Show:</small>
		<select style="width: 80px" name="activos" onchange='document.formas.submit()'>
			<option value="1" <%if(activos.equals("1")) out.print("selected");%>>Active</option>
			<option value="2" <%if(activos.equals("2")) out.print("selected");%>>All</option>
		</select>
	</form>
	<table class="table table-condensed" id="table">
	<thead>
		<tr>
			<th>#</th>
			<th>Action</th>
			<th>Student ID</th>
			<th>Name</th>
			<th>Start Date</th>
			<th>End Date</th>
			<th>Status</th>
			<th>Course</th>
			<th>Ac. Status</th>
		</tr>
	</thead>
	<%	for(int i=0; i<lisMenAlumno.size(); i++){
			MentAlumno alumno = (MentAlumno) lisMenAlumno.get(i);
			String nombreAlumno = "";
			boolean inscrito 	= false;
			if(mapMentorContacto.containsKey(alumno.getCodigoPersonal())){
				nombreAlumno = mapMentorContacto.get(alumno.getCodigoPersonal()).getNombre()+" "+mapMentorContacto.get(alumno.getCodigoPersonal()).getApellidoPaterno()+" "+mapMentorContacto.get(alumno.getCodigoPersonal()).getApellidoMaterno();
			}
			if(mapCarrera.containsKey(alumno.getCarreraId())){
				nombreCarrera = mapCarrera.get(alumno.getCarreraId()).getNombreCarrera();
			}
			if(getMapaInscritos.containsKey(alumno.getCodigoPersonal())){
				inscrito = true;
			}
	%>
		<tr>
			<td><%=i+1 %></td>
			<td>
		  	<%	if ( acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") || acceso.getAccesos().contains(alumno.getCarreraId()) ){ %>					
					<a href="javascript:borrar('<%=alumno.getCodigoPersonal() %>', '<%=alumno.getFolio() %>');" class="btn btn-sm btn-danger"><i class="fas fa-trash-alt icon-white"></i></a>					
		 	<%	}else{
		 			out.print("&nbsp;");
		 		}
		 	%>
		 	</td>
		  	<td class="ayuda alumno <%=alumno.getCodigoPersonal() %>"><%=alumno.getCodigoPersonal()%></td>
			<td><%=nombreAlumno%></td>
			<td>
<button type="button" class="btn btn-info agregar" data-bs-toggle="modal" data-bs-target="#exampleModal">
  	<%=alumno.getFechaInicio()==null?"":alumno.getFechaInicio() %>
</button>
				<input type="hidden" id="periodo" value="<%=alumno.getPeriodoId()%>" />
				<input type="hidden" id="mentor" value="<%=alumno.getMentorId()%>" />
				<input type="hidden" id="codigo" value="<%=alumno.getCodigoPersonal()%>" />
				<input type="hidden" id="folio" value="<%=alumno.getFolio()%>" />
				<input type="hidden" id="fInicial" value="<%=alumno.getFechaInicio()%>" />
				<input type="hidden" id="fFinal" value="<%=alumno.getFechaFinal()%>" />
			</td>
			<td>
<button type="button" class="btn btn-info agregar" data-bs-toggle="modal" data-bs-target="#exampleModal">
  	<%=alumno.getFechaFinal()==null?"":alumno.getFechaFinal() %>
</button>
				<input type="hidden" id="periodo" value="<%=alumno.getPeriodoId()%>" />
				<input type="hidden" id="mentor" value="<%=alumno.getMentorId()%>" />
				<input type="hidden" id="codigo" value="<%=alumno.getCodigoPersonal()%>" />
				<input type="hidden" id="folio" value="<%=alumno.getFolio()%>" />
				<input type="hidden" id="fInicial" value="<%=alumno.getFechaInicio()%>" />
				<input type="hidden" id="fFinal" value="<%=alumno.getFechaFinal()%>" />
			<td>
				<%if( aca.util.Fecha.getFechaBetweenFecha( alumno.getFechaInicio()==null?"00/00/2000":alumno.getFechaInicio(), alumno.getFechaFinal()==null?"00/00/2000":alumno.getFechaFinal(), fecha ) ){ %>
					<span class="badge bg-success"><spring:message code='aca.Activo'/></span>
				<%}else{ %>
					<span class="badge bg-warning"><spring:message code='aca.Inactivo'/></span>
				<%} %>
			</td>
			<td><%=alumno.getCarreraId()%>-<%=nombreCarrera %></td>
			<td><%=inscrito ? "Enrolled" : "Not Enrolled" %></td>
		</tr>
	<%
		}
	%>
	</table>
</div>
	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  		<div class="modal-dialog">
    		<div class="modal-content">
	      		<div class="modal-header">
	        		<h5 class="modal-title" id="exampleModalLabel">Dates</h5>
	        		<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      		</div>
	      		<div class="modal-body">
			     	<form action="mentor_alumno" name="forma">
						<input type="hidden" name="Accion" />
						<input type="hidden" name="periodoId" value="" />
						<input type="hidden" name="mentorId" value="" />
						<input type="hidden" name="codigoAlum" value="" />
						<input type="hidden" name="folioId" value="" />
						<input type="hidden" name="carrera" value="<%=carreraId %>" />
						<div class="mb-3">
				            <label for="recipient-name" class="col-form-label">Start:</label>
							<input class="form-control" name="fechaInicial" type="text" value="" data-date-format="dd/mm/yyyy" id="fechaInicial" >
			          	</div>
			          	<div class="mb-3">
			            	<label for="message-text" class="col-form-label">End:</label>
							<input class="form-control" name="fechaFinal" type="text" value="" data-date-format="dd/mm/yyyy" id="fechaFinal" >
			          	</div>
					</form>
				</div>
	      		<div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
					<input type="button" class="btn btn-primary" onclick="grabar();" value="Save"/>
	      		</div>
    		</div>
  		</div>
	</div>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>

	jQuery('input[name="fInicio"]').datepicker();;
	jQuery('input[name="fFinal"]').datepicker();;

	function Agregar(){

		var fechainicial = document.getElementById("fInicio").value;
		var fechafinal = document.getElementById("fFinal").value;

		var resIni = fechainicial.split("/");
		var resFin = fechafinal.split("/");

		var fechaini = resIni[2]+"/"+resIni[1]+"/"+resIni[0];
		var fechafin = resFin[2]+"/"+resFin[1]+"/"+resFin[0];
		
		if(Date.parse(fechafin) < Date.parse(fechaini)){
			alert("The final date has to be later than the initial date");
		}else{
			if(document.form.matricula.value != ""){
				document.form.submit();
			}else{
				alert("Inserrt the Enrollment ID");
				document.form.matricula.focus();
			}
		}
		
	}
	
	function setValue(strLink){
		strLink=strLink+"?nombre="+form.nombre.value;
		window.open( strLink, "verplan", "toolbar=no, status=no, menubar=no, resizable=no, scrollbars=yes, width=550, height=350, top=50, left=400")
	}
	
	function borrar(matricula, folio){
		if(confirm("¿Are you sure to disassociate this mentee from his mentor?")){
			document.location.href = "mentor_alumno?Accion=2&carrera=<%=carreraId %>&mentor=<%=mentorId %>&matricula="+matricula+"&folio="+folio;
		}
	}
	
	function grabar(){
		if(document.forma.fechaInicial.value!="" && document.forma.fechaFinal.value!=""){
			document.forma.Accion.value = "3";
			document.forma.submit();
		}else{
			alert('The name and date of birth are required');
		}
	}
	
	(function($){	
		
// 		var popup = $('.popup');
// 		var bg = $('.bg-popup');
		
		$('.agregar').on('click', function(){
						
			periodo 	= $(this).siblings("#periodo").val();
			mentor 		= $(this).siblings("#mentor").val();
			codigo 		= $(this).siblings("#codigo").val();
			folio		= $(this).siblings("#folio").val();
			fInicial	= $(this).siblings("#fInicial").val();
			fFinal		= $(this).siblings("#fFinal").val();
			
			document.forma.fechaInicial.value = fInicial;
			document.forma.fechaFinal.value = fFinal;
			document.forma.periodoId.value = periodo;
			document.forma.mentorId.value = mentor;
			document.forma.codigoAlum.value = codigo;
			document.forma.folioId.value = folio;
			
			$('#fechaInicial').datepicker();
			$('#fechaFinal').datepicker();
			
// 			show();
		});
// 		function show(){
// 			bg.show();
// 			popup.css({
// 				'left': $(window).width()/2 - popup.width()/2,
// 				'top' : $(window).height()/2 - popup.height()/2 - 40
// 			});
// 			popup.fadeToggle(200, function(){
				
// 			});
// 		}
// 		$('.cancel').on('click', close);
// 		bg.on('click', close);
// 		function close(){
// 			popup.fadeOut();
// 			bg.fadeOut();
// 		}
		
	})(jQuery);
</script>

<style>
	#table th{
		padding-right: 30px !important;
	}
</style>
<link rel="stylesheet" href="../../js/tablesorter/themes/blue/style.css" />
<script src="../../js/tablesorter/jquery.tablesorter.js"></script>
<script>
	jQuery('#table').tablesorter();
</script>