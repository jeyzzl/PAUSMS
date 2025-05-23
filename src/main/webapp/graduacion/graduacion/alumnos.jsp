<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.graduacion.spring.AlumEgreso"%>
<%@page import="aca.graduacion.spring.AlumEvento"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatEstado"%>
<%@page import="aca.catalogo.spring.CatDivision"%>
<%@page import="aca.catalogo.spring.CatTipoAlumno"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.graduacion.spring.AlumEgreso"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
<script type="text/javascript">
	function borrar(codigoPersonal, eventoId){
		if (confirm("Are you sure you want to delete this student?")){
			document.location.href = "borrarAlumno?CodigoAlumno="+codigoPersonal+"&EventoId="+eventoId;
		}
	}
	
	function editar(codigoPersonal, eventoId) {
		document.location.href = "editar_alumno?CodigoAlumno="+ codigoPersonal + "&EventoId=" + eventoId;
	}
	
	function recarga() {
		document.location.href = "alumnos?EventoId="+document.frmGraduacion.EventoId.value+"&OrdenNombre="+document.frmGraduacion.OrdenNombre.value;
	}
	
	function Nuevo() {
		document.location.href = "editar_alumno?EventoId="+ document.frmGraduacion.EventoId.value + "&CodigoAlumno="+ document.frmGraduacion.CodigoPersonal.value;
	}
</script>
</head>
<%
	DecimalFormat df = new DecimalFormat("#.#");
	long ini = 0, fin = 0;
	ini = System.currentTimeMillis();
	
	String mensaje			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	// Creaci�n de Variables
	String codigoUsuario 			= (String)session.getAttribute("codigoPersonal");	
	String codigoPersonal 			= (String)session.getAttribute("codigoAlumno");
	String eventoId 				= (String)request.getAttribute("eventoId");	
	String ordenAlumno 				= (String)request.getAttribute("ordenAlumno");	
	String resultado 				= "-";
	int row = 0;	
	List<AlumEvento> lisEventos 					= (List<AlumEvento>)request.getAttribute("lisEventos");	
	List<AlumEgreso> lisAlumnos 					= (List<AlumEgreso>)request.getAttribute("lisAlumnos");
	HashMap<String,String> mapaInscritos			= (HashMap<String,String>)request.getAttribute("mapaInscritos");
	HashMap<String,CatCarrera> mapaCarreras 		= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,CatPais> mapaPaises 				= (HashMap<String,CatPais>)request.getAttribute("mapaPaises");
	HashMap<String,CatEstado> mapaEstados 			= (HashMap<String,CatEstado>)request.getAttribute("mapaEstados");
	HashMap<String,CatDivision> mapaDivisiones 		= (HashMap<String,CatDivision>)request.getAttribute("mapaDivisiones");
	HashMap<String,CatTipoAlumno> mapaTipos			= (HashMap<String,CatTipoAlumno>)request.getAttribute("mapaTipos");
	HashMap<String,String> mapaOrden				= (HashMap<String,String>)request.getAttribute("mapaOrden");		
	HashMap<String,AlumPersonal> mapaPersonal		= (HashMap<String,AlumPersonal>)request.getAttribute("mapaPersonal");
	HashMap<String,AlumAcademico> mapaAcademico		= (HashMap<String,AlumAcademico>)request.getAttribute("mapaAcademico");
	HashMap<String,MapaPlan> mapaPlanes				= (HashMap<String,MapaPlan>)request.getAttribute("mapaPlanes");
	HashMap<String,String> mapaMaterias				= (HashMap<String,String>)request.getAttribute("mapaMaterias");
	HashMap<String,AlumPlan> mapaAlumPlan		 	= (HashMap<String,AlumPlan>)request.getAttribute("mapaAlumPlan");
	HashMap<String,AlumEgreso> mapaAlumGraEvento	= (HashMap<String,AlumEgreso>)request.getAttribute("mapaAlumGraEvento");
	HashMap<String,String> mapaNivelAlumGraduados	= (HashMap<String,String>)request.getAttribute("mapaNivelAlumGraduados");
	HashMap<String,String> mapaDocEventoId			= (HashMap<String,String>)request.getAttribute("mapaDocEventoId");

%>
<body>
<div class="container-fluid">
	<h2>Event Graduates</h2>
	<form name="frmGraduacion">
	<div class="alert alert-info d-flex align-items-center">
		<b>Event:</b>
		<select class="form-select me-1" id="EventoId" name="EventoId" onchange="javascript:recarga()" style="width:350px">
<%	for (AlumEvento evento : lisEventos){ %>
			<option value="<%=evento.getEventoId()%>" <%=evento.getEventoId().equals(eventoId)?"Selected":"" %>><%=evento.getEventoNombre()%>[<%=evento.getFecha()%>]</option>
<%	}%>
		</select>
		<b>Student:</b>
		<input class="form-control me-1" type="text" id="CodigoPersonal" name="CodigoPersonal" value="<%=codigoPersonal%>" onkeyup="if(window.event.keyCode == 13) Nuevo(); return false;" maxlength="7" size="8" style="width:120px" /> 
		<a class="btn btn-primary me-3" href="javascript:Nuevo()"><i class="icon-white icon-plus"></i> Add</a>
		<a class="btn btn-primary me-1" href="updateProm?EventoId=<%=eventoId%>"><i class="fas fa-sync-alt"></i> Average</a>
		<a class="btn btn-primary me-3" href="verificar?EventoId=<%=eventoId%>"><i class="fas fa-sync-alt"></i> Progress</a>
		<b><spring:message code="aca.Orden"/>:</b>
		<select class="form-select" id="OrdenNombre" name="OrdenNombre" onchange="javascript:recarga()" style="width:200px">
			<option value="Apellido" <%=ordenAlumno.equals("Apellido")?"Selected":""%>>By Surname</option>
	 		<option value="Nombre" <%=ordenAlumno.equals("Nombre")?"Selected":""%>>By Name</option>
		</select>
		&nbsp;&nbsp;
		<input type="text" class="form-control" placeholder="Search..." id="buscar" style="width:140px">		
	</div>	
	</form>
<%	if(!mensaje.equals("-")){ %>
	<div class="alert alert-success"><%=mensaje%></div>
<%	} %>	
	<table class="table table-sm table-bordered" id="table">	
<%			
	int pre	 				= 0;
	int lic	 				= 0;
	int pos					= 0;
	int doc 				= 0;
	int esp 				= 0;
	int conTituloPre 		= 0;
	int conTituloLic 		= 0;
	int conTituloPos 		= 0;
	int conTituloDoc 		= 0;
	int conTituloEsp 		= 0;
	String carrera 			= "";
	String nombreCarrera 	= "";				
	String nombreCarreraTmp	= "X";				
	String nivelAlumno		= "0";
	
	row = 0;
	for (AlumEgreso alumno : lisAlumnos) {
		row++;
		String orden = "0";				
		if (mapaOrden.containsKey(alumno.getCarreraId())){
			orden = mapaOrden.get(alumno.getCarreraId());
		}
		
		if (alumno.getCarreraId() != null) {
			if (!carrera.equals(alumno.getCarreraId())) {
				carrera = alumno.getCarreraId();
				if (mapaCarreras.containsKey(carrera)){
					nombreCarrera = mapaCarreras.get(carrera).getNombreCarrera();
					nivelAlumno = mapaCarreras.get(carrera).getNivelId();
				}
				if(!nombreCarreraTmp.equals(nombreCarrera)){
%>
	<thead>
		<tr align="left">
			<td colspan="19">
				<h4><a href="orden?CarreraId=<%=carrera%>&Orden=<%=orden%>">[<%=orden%>]</a> <%=carrera%>:<%=nombreCarrera%></h4>
			</td>
		</tr>				
		<tr class="table-dark">
			<th style="text-align: center">Op.</th>
			<th style="text-align: center">Authorization</th>
			<th width="2%" style="text-align:center">Confirm</th>
			<th style="text-align: center"><spring:message code="aca.Numero"/></th>
			<th style="text-align: center"><spring:message code="aca.Matricula"/></th>
<% 			if(ordenAlumno.equals("Apellido")){%>
			<th>Surname</th>
			<th><spring:message code="aca.Nombre"/></th>
<% 			}else{%>
			<th><spring:message code="aca.Nombre"/></th>
			<th>Surname</th>
<% 			}%>
			<th><spring:message code="aca.Plan"/></th>
			<th>Plan Name</th>
			<th style="text-align: center">Start D.</th>
			<th style="text-align: center">Registration D.</th>
			<th>Sub.</th>
			<th style="text-align: center"><spring:message code="aca.Avance"/></th>
			<th style="text-align: center">GPA</th>
			<th style="text-align: center">Deg.</th>	
			<%-- <th width="5%" style="text-align: center">Divisi�n</th>							 --%>
			<th><spring:message code="aca.Pais"/></th>
			<th><spring:message code="aca.Estado"/></th>
			<th style="text-align: center">Enrolled?</th>
			<th>Student Type</th>
		</tr>
<%				}
			}
		}else{
			if (carrera != null) {
					carrera = alumno.getCarreraId();
%>
		<tr>
			<td colspan="18">&nbsp;</td>
		</tr>
		<tr align="center">
			<th>Op.</th>
			<th>Authorization</th>
			<th><spring:message code="aca.Numero"/></th>
			<th><spring:message code="aca.Matricula"/></th>
			<th>Surname</th>
			<th><spring:message code="aca.Nombre"/></th>
			<th><spring:message code="aca.Plan"/></th>
			<th>Plan Name</th>
			<th>Start D.</th>
			<th>Registration D.</th>
			<th>Sub.</th>
			<th><spring:message code="aca.Avance"/></th>
			<th>GPA</th>
			<th>Deg.</th>
			<%-- <th width="5%" align="center">Divisi�n</th> --%>
			<th><spring:message code="aca.Estado"/></th>
			<th><spring:message code="aca.Pais"/></th>
			<th>Enrolled?</th>
			<th>Student Type</th>
		</tr>
<%
			}
		}
%>
	</thead>
<%			
		String esInscrito = "";
		if (mapaInscritos.containsKey(alumno.getCodigoPersonal())) {
			esInscrito = "YES";
		} else {
			esInscrito = "NO";
		}
		
		String matPen = "<span class='badge'>0</span>";
		if (mapaMaterias.containsKey(alumno.getCodigoPersonal())){
			matPen = "<span class='badge bg-warning'>"+mapaMaterias.get(alumno.getCodigoPersonal())+"</span>";
		}
		
		String nombreAlumno		= "";
		String apellidoAlumno	= "";
		String paisId 			= "";		
		String estadoId 		= "";
		float promedio			= Float.parseFloat(alumno.getPromedio());
		promedio				= promedio/10;
		String muestraProm		= df.format(promedio);
		
		if ( mapaPersonal.containsKey(alumno.getCodigoPersonal())){
			AlumPersonal personal = mapaPersonal.get(alumno.getCodigoPersonal());
			nombreAlumno 	= personal.getNombre();
			apellidoAlumno	= personal.getApellidoPaterno()+" "+personal.getApellidoMaterno();
			paisId 			= personal.getPaisId();
			estadoId		= personal.getEstadoId();
		}
		
		String nombrePais = "-";
		if (mapaPaises.containsKey(paisId)) nombrePais = mapaPaises.get(paisId).getNombrePais();
		
		String divisionId 		= "0";
		String nombreDivision 	= "-";
		if (mapaPaises.containsKey(paisId)){
			divisionId = mapaPaises.get(paisId).getDivisionId();
			if (mapaDivisiones.containsKey(divisionId)){
				nombreDivision = mapaDivisiones.get(divisionId).getNombreCorto();
			}
		}
		
		String nombreEstado = "-";
		if (mapaEstados.containsKey(paisId+estadoId)){
			nombreEstado = mapaEstados.get(paisId+estadoId).getNombreEstado();
		}
		
		String tipoAlumno = "0";				
		if (mapaAcademico.containsKey(alumno.getCodigoPersonal())){
			tipoAlumno = mapaAcademico.get(alumno.getCodigoPersonal()).getTipoAlumno();
			if (mapaTipos.containsKey(tipoAlumno)){
				tipoAlumno = mapaTipos.get(tipoAlumno).getNombreTipo();
			}
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
		
		String fechaIngreso = "01/01/2000";
		
		if (mapaAcademico.containsKey(alumno.getCodigoPersonal())){					
			fechaIngreso = mapaAcademico.get(alumno.getCodigoPersonal()).getFAdmision();
			fechaIngreso = fechaIngreso.substring(0,10).replace("-","/");
		}
		
		
		String planNombre = "-";
		if (mapaPlanes.containsKey(alumno.getPlanId())){
			planNombre = mapaPlanes.get(alumno.getPlanId()).getCarreraSe();
		}
		
		String avance = "<span class='badge bg-warning'>Pending</span>";
		if (mapaAlumPlan.containsKey(alumno.getCodigoPersonal()+alumno.getPlanId())){
			avance = mapaAlumPlan.get(alumno.getCodigoPersonal()+alumno.getPlanId()).getFinalizado();
			if (avance.equals("S")) 
				avance = "<span class='badge bg-success'>Complete</span>";
			else
				avance = "<span class='badge bg-warning'>Pending</span>";
		}
		
		String colorPermiso = "<span class='badge bg-secondary'>"+alumno.getPermiso()+"</span>";
		if (alumno.getPermiso().equals("S")) colorPermiso = "<span class='badge bg-success rounded-pill'>YES</span>";
		if (alumno.getPermiso().equals("N")) colorPermiso = "<span class='badge bg-danger rounded-pill'>NO</span>";
		
		String titulado = "N";
		if (mapaNivelAlumGraduados.containsKey(alumno.getCodigoPersonal())){
			if(mapaNivelAlumGraduados.get(alumno.getCodigoPersonal()).equals("3") || mapaNivelAlumGraduados.get(alumno.getCodigoPersonal()).equals("4")){
				titulado = "S";
			}
		}
		
		if (mapaDocEventoId.containsKey(alumno.getCodigoPersonal())){
			titulado = "S";
		}
%>
		<tr>
			<td style="text-align: center">
				<a href="javascript:editar('<%=alumno.getCodigoPersonal()%>','<%=alumno.getEventoId()%>');" title="Modificar"><i class="fas fa-edit"></i></a>
				&nbsp;
				<a href="javascript:borrar('<%=alumno.getCodigoPersonal()%>','<%=alumno.getEventoId()%>');" class="fas fa-trash-alt" title="Eliminar"></a>
			</td>
			<td style="text-align: center"><%=colorPermiso%></td>
			<td style="text-align:center">
<%		if (codigoUsuario.equals("9800308") || codigoUsuario.equals("9800385")){ %>	
				<a href="cambiar?EventoId=<%=alumno.getEventoId()%>&CodigoAlumno=<%=alumno.getCodigoPersonal()%>"><span class="badge <%=alumno.getConfirmar().equals("N")?"bg-danger":"bg-success"%> rounded-pill"><%=alumno.getConfirmar().equals("N")?"NO":"YES"%></span></a>
<%		}else{ %>
				<span class="badge <%=alumno.getConfirmar().equals("N")?"bg-danger":"bg-success"%> rounded-pill"><%=alumno.getConfirmar().equals("N")?"NO":"YES"%></span>		
<%		}%>
			</td>
			<td style="text-align: center"><%=row%></td>
			<td style="text-align: center"><%=alumno.getCodigoPersonal()%></td>
<% 		if(ordenAlumno.equals("Apellido")){%>
			<td><%=apellidoAlumno%></td>
			<td><%=nombreAlumno%></td>
<% 		}else{%>
			<td><%=nombreAlumno%></td>
			<td><%=apellidoAlumno%></td>
<% 		}%>
			<td><%=alumno.getPlanId()%></td>
			<td><%=planNombre%></td>
			<td style="text-align: center"><%=fechaIngreso%></td>
			<td style="text-align: center"><%=alumno.getFecha()%></td>
			<td class="center"><%=matPen%></td>
			<td style="text-align: center"><%=avance%></td>
			<td style="text-align: center"><%=muestraProm%></td>
			<td style="text-align:center"><%=titulado.equals("S")?"YES":"NO"%></td>
			<%-- <td style="text-align: center"><%=nombreDivision%></td> --%>
			<td><%=nombrePais%></td>
			<td><%=nombreEstado%></td>
			<td style="text-align: center"><%=esInscrito%></td>
			<td><%=tipoAlumno%></td>
		</tr>
<%		carrera = "";
		nombreCarreraTmp = nombreCarrera;
	}
%>
	</table>	
	</div>
</body>
<script type="text/javascript" src="../../js/search.js"></script>
<script type="text/javascript">
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>	
</html>