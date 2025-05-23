<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.attache.spring.AttacheCustomer"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.alumno.spring.AlumRemedial"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<head>
<script>
	function recargar(){
		document.frmCarga.submit();
	}	
</script>
<body>
<%	
	String codigoAlumno			= (String)session.getAttribute("codigoAlumno");
	String nombreAlumno 		= (String)request.getAttribute("nombreAlumno");	
	boolean esAlumno 			= (boolean)request.getAttribute("esAlumno");
	String periodoId 			= (String)request.getAttribute("periodoId");
	String cargaId	 			= (String)request.getAttribute("cargaId");
	String bloqueId 			= (String)request.getAttribute("bloqueId");
	String periodoNombre		= (String)request.getAttribute("periodoNombre");
	String cargaNombre			= (String)request.getAttribute("cargaNombre");
	String bloqueNombre			= (String)request.getAttribute("bloqueNombre");
	String plan					= (String)request.getAttribute("plan");
	String modo					= (String)request.getAttribute("modo");
	AlumPersonal personal		= (AlumPersonal)request.getAttribute("personal");
	AlumAcademico academico		= (AlumAcademico)request.getAttribute("academico");
	String tipoAlumno			= (String)request.getAttribute("tipoAlumno");
	String facultadId			= (String)request.getAttribute("facultadId");
	String carreraId			= (String)request.getAttribute("carreraId");
	String facultadNombre		= (String)request.getAttribute("facultadNombre");
	String carreraNombre		= (String)request.getAttribute("carreraNombre");
	Acceso acceso				= (Acceso)request.getAttribute("acceso");
	boolean existePlan			= (boolean) request.getAttribute("existePlan");
	boolean esInscrito			= (boolean)request.getAttribute("esInscrito");
	MapaPlan mapaPlan			= (MapaPlan)request.getAttribute("mapaPlan");
	CatPais pais				= (CatPais)request.getAttribute("pais");
	AttacheCustomer customer 	= (AttacheCustomer)request.getAttribute("customer");
	
	String alertPeriodo			= "alert-info";	
	if (periodoId.equals("") || cargaId.equals("0") || bloqueId.equals("0")) alertPeriodo = "alert-danger";
	
	String datosAlumno			= esAlumno?codigoAlumno+" - "+nombreAlumno:"�Select a Student!";
	String modoNombre 	= "City Grounds";
	if (modo.equals("P")) modoNombre = "PAU Campus";
	if (modo.equals("V")) modoNombre = "Home/Virtual";	

	List<AlumRemedial> listaRemedial	= (List<AlumRemedial>)request.getAttribute("listaRemedial");
	HashMap<String, String> mapCursos	= (HashMap<String, String>)request.getAttribute("mapCursos");

	NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
	String formattedOpenBal = "0";
	if(customer.getOpenbal() != null){
		formattedOpenBal = currencyFormatter.format(customer.getOpenbal());
	}
%>
<div class="container-fluid">
	<h2>Student Preview<small class="text-muted fs-5"> ( <%=datosAlumno%> )</small></h2>	
	<div class="alert <%=alertPeriodo%>">
		<span class="badge bg-info">Cycle:</span> <%=periodoId%> - <%=periodoNombre%>&nbsp; &nbsp;
		<span class="badge bg-info">Load:</span> <%=cargaId%> - <%=cargaNombre%>&nbsp; &nbsp;
		<span class="badge bg-info">Block:</span> <%=bloqueId%> - <%=bloqueNombre%>&nbsp; &nbsp;
		<span class="badge bg-info">Location:</span> <%=modoNombre%>&nbsp; &nbsp;
		<a class="btn btn-primary" href="periodo"><i class="fas fa-edit"></i></a>&nbsp;
<!-- 		<a class="btn btn-success" style="color:black" href="costos"><i class="fas fa-calculator" ></i></a>&nbsp;&nbsp; -->
<!-- 		<a class="btn btn-success" style="color:black" href="materias"><i class="far fa-list-alt"></i></a>&nbsp;&nbsp; -->
	</div>	
<%
	// VALIDA QUE SEA ALUMNO Y TENGA ELEGIDO EL PERIODO EN DONDE VA HA INSCRIBIR 
	if (esAlumno && !periodoId.equals("0") && !cargaId.equals("0") && !bloqueId.equals("0")){ 
%>	
	<div class="row">
		<div class="col-2">
			<div class="control-group">		
				<div id="sombra">			
					<img class="img-thumbnail" src="../../foto?Codigo=<%=codigoAlumno%>&Tipo=O" width="170">
				</div>				
			</div>  
		</div>		
		<div class=" col-2">
			<div class="control-group">
				<label for="Facultad"><b>School:</b></label>	
				<%=facultadNombre%>
			</div>  
			<div class="control-group">
				<label for="Carrera"><b>Course:</b></label>
				<%=carreraNombre%>
			</div>
			<div class="control-group">
				<label for="ClasFin"><b>Plan:</b></label>
<% 				if(existePlan){%>
				<%=plan%>
<% 				}else{%>
				<span class="badge bg-warning">Plan not registered*</span>
<% 				}%>
			</div>
<%	if(session.getAttribute("institucion").equals("Pacific Adventist University")){%>
			<div class="control-group">
				<label for="Carrera"><b>Open Balance:</b></label>
				<%=formattedOpenBal%>
			</div>
<%	}%>
		</div>
		
		<div class=" col-2">
			<div class="control-group">
				<label for="CURP"><b>GOB ID:</b></label>	
				<%=personal.getCurp()%>
			</div>  
			<div class="control-group">
				<label for="Nacimiento"><b>Birth date:</b></label>
				<%=personal.getFNacimiento() %>
			</div>
			<div class="control-group">
				<label for="Nacionalidad"><b>Nationality:</b></label>
				<%=personal.getNacionalidad().equals(pais.getPaisId()) ? pais.getNacionalidad():"Foreigner"%>
			</div>
		</div>		
		
		<div class="col-2">
			<div class="control-group">
				<label for="Modalidad"><b>Modality:</b></label>	
				<%=academico.getModalidadId()%>
			</div>  
			<div class="control-group">
				<label for="Residencia"><b>Residence:</b></label>
				<%=academico.getResidenciaId().equals("E")?"Day Student":"Boarding Student"%>
			</div>
			<div class="control-group">
				<label for="ClasFin"><b>Type:</b></label>
				<%=tipoAlumno%>
			</div>
		</div>
	</div>
	
<% 
	if (acceso.getAdministrador().equals("S") || acceso.getAccesos().contains(carreraId)){
		if(existePlan && !esInscrito){
			String remediales = "";
			boolean tieneRemedial = false;
			for(AlumRemedial remedial : listaRemedial){
				if(remedial.getEstado().equals("A") && mapCursos.containsKey(remedial.getCursoId())){
					remediales = remediales+mapCursos.get(remedial.getCursoId())+",";
					tieneRemedial = true;
				}
			}
			if(mapaPlan.getOficial().equals("S") && tieneRemedial){
				remediales = remediales.substring(0, remediales.length()-1);
				
				if(remediales.contains(",")){
					remediales = "The student has "+remediales+" pending!";
				}else if(listaRemedial.size() >= 1){
					remediales = "The student has "+remediales+" pending!";
				}
%>	
		<div class="alert alert-danger">
			<%=remediales%> This can be settled at the school admissions office, extension 3650 and 1503.
		</div>
<%			}else{%>	
		<div class="alert alert-info">
			<a class="btn btn-primary" href="materias"><i class="fas fa-arrow-circle-right"></i> Continue</a>
		</div>
<%			}
		}else if (existePlan && esInscrito){
%>
		<div class="alert alert-danger">The student is already enrolled in the selected load and block !</div>
<%	
		}
	}else{
%>		
		<div class="alert alert-danger">You have no enrollment privileges!</div>
<%		
	}
%>		
	
<%	
	} 
%>
</div>
</body>
</html>