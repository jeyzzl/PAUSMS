<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>

<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>
<%@ page import="aca.catalogo.spring.CatFacultad"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>
<%@ page import="aca.carga.spring.CargaAlumno"%>
<%@ page import="aca.carga.spring.CargaFinanciero"%>
<%@ page import="aca.plan.spring.MapaPlan"%>
<%@ page import="aca.alumno.spring.AlumAsesor"%>
<%@ page import="aca.emp.spring.EmpContacto"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%@ include file="../alumno/menu.jsp"%>

<head></head>
<%	
	String opcion				= request.getParameter("Opcion") == null ? "1" : request.getParameter("Opcion");

	AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");
	AlumPlan alumPlan 			= (AlumPlan) request.getAttribute("alumPlan");
	boolean existeAsesor 		= (boolean)request.getAttribute("existeAsesor");
	int pendientesConfirmar		= (int)request.getAttribute("pendientesConfirmar");
	
// 	boolean tieneNse			= (boolean)request.getAttribute("tieneNse");
// 	boolean tieneVacuna			= (boolean)request.getAttribute("tieneVacuna");
// 	int pagosPendientes			= (int)request.getAttribute("pagosPendientes");

	CatFacultad facultad 		= (CatFacultad) request.getAttribute("facultad");
	CatCarrera carrera 			= (CatCarrera) request.getAttribute("carrera");
	String esEnLinea			= (String) request.getAttribute("esEnLinea");
	String coordinador 			= (String) request.getAttribute("coordinadorNombre");
	String director 			= (String) request.getAttribute("directorNombre");
	String asesorId				= (String) request.getAttribute("asesorId");
	String asesorNombre			= (String) request.getAttribute("asesorNombre");
	String planNombre			= (String) request.getAttribute("planNombre");
	String planId 				= (String) request.getAttribute("planId");
	
	List<AlumPlan> lisPlanes 					= (List<AlumPlan>) request.getAttribute("lisPlanes");	
	HashMap<String, MapaPlan> mapaPlanes 		= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");	
	HashMap<String, EmpContacto> mapaContactos	= (HashMap<String,EmpContacto>) request.getAttribute("mapaContactos");
	CargaFinanciero cargaFin 					= (CargaFinanciero)request.getAttribute("cargaFin");
	CargaAlumno cargaAlumno 					= (CargaAlumno)request.getAttribute("cargaAlumno");

	boolean existeCarga 						= (boolean)request.getAttribute("existeCarga");


	boolean finClearance = false;
	if(cargaFin.getStatus().equals("A")) finClearance = true;
	
%>
<body>
<div class="container-fluid mt-1">
	<h3> <spring:message code="portal.alumno.pasos.Alumno"/> <small class="text-muted fs-6">( <%=alumPersonal.getCodigoPersonal()%> - <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%> )</small></h3>					
	<p style="font-size: 14px"><spring:message code="portal.alumno.pasos.MensajeUno"/></p>		
<% if(existeCarga && finClearance){%>
<div class="container-fluid d-flex justify-content-around mt-1">
	<div class="row row-cols-1 row-cols-md-2 g-0 ">
		<div class="card bg-light mb-3 me-2" style="max-width: 20rem;">
			<div class="card-header">
				<h5>
					<span class="badge badge bg-dark rounded-pill">1</span> <spring:message code="portal.alumno.pasos.AsuntosPrevios"/>
				</h5>
			</div>
			<div class="card-body">
				<p style="font-size: 14px">
					<em><u><spring:message code="portal.alumno.pasos.Instrucciones"/>1</u></em>
				</p>
				<p class="card-text"><spring:message code="portal.alumno.pasos.TarjetaUno"/></p>
			</div>
			<div class="card-footer bg-transparent border-success">
				<a class="btn btn-success" href="previos"><spring:message code="portal.alumno.pasos.Entrar"/></a>		
<%			if(esEnLinea.equals("N")){	
// 				if (!tieneNse || !tieneVacuna) out.print("<div class='badge bg-warning rounded-pill'>Pending</div>");
}%>
			</div>
		</div>
		
		<div class="card bg-light mb-3 me-2" style="max-width: 20rem;">
			<div class="card-header">
				<h5>
					<span class="badge badge bg-dark rounded-pill">2</span> Declaration
				</h5>
			</div>
			<div class="card-body">
				<p style="font-size: 14px">
					<em><u><spring:message code="portal.alumno.pasos.Instrucciones"/>2</u></em>
				</p>
				<p class="card-text"><spring:message code="portal.alumno.pasos.TarjetaDos"/></p>
			</div>
			<div class="card-footer bg-transparent border-success">
				<a class="btn btn-success" href="cargas"><spring:message code="portal.alumno.pasos.Entrar"/></a>
<%	if (pendientesConfirmar >= 1) out.print("<div class='badge bg-warning rounded-pill'>Pending</div>");%>				
			</div>
		</div>
		<div class="card bg-light mb-3 me-2" style="max-width: 20rem;">
			<div class="card-header">
				<h5>
					<span class="badge badge bg-dark rounded-pill">3</span> 
					<%-- <spring:message code="portal.alumno.pasos.Pago"/> --%>
					Account Status
				</h5>
			</div>
			<div class="card-body">
				<p style="font-size: 14px">
					<em><u><spring:message code="portal.alumno.pasos.Instrucciones"/>3</u></em>
				</p>
				<p class="card-text"><spring:message code="portal.alumno.pasos.TarjetaTres"/></p>
			</div>
			<div class="card-footer bg-transparent border-success">
				<a class="btn btn-success" href="pagos"><spring:message code="portal.alumno.pasos.Entrar"/></a>
<%-- <%	if (pagosPendientes >= 1) out.print("<div class='badge bg-warning rounded-pill'>Pending</div>");%>				 --%>
			</div>
		</div>
		<div class="card bg-light mb-3" style="max-width: 20rem;">
			<div class="card-header">
				<h5>
					<span class="badge badge bg-dark rounded-pill">4</span> <spring:message code="portal.alumno.pasos.Cierre"/>
				</h5>
			</div>
			<div class="card-body">
				<p style="font-size: 14px">
					<em><u><spring:message code="portal.alumno.pasos.Instrucciones"/>4</u></em>
				</p>
				<p class="card-text"><spring:message code="portal.alumno.pasos.TrajetaCuatro"/></p>
			</div>
			<div class="card-footer bg-transparent border-success">
				<a class="btn btn-success" href="cierre?Opcion=1"><spring:message code="portal.alumno.pasos.Entrar"/></a>
			</div>
		</div>
	</div>
</div>
<form name="frmPlanes" action="pasos" mthod="post">	
	<div class="container-fluid mt-1">
		<div class="alert alert-success"><spring:message code="portal.alumno.pasos.AsesoresParaElPlanDeEstudios"/>:&nbsp;
		<small class="text-muted">
			<select class="form-select" name="PlanId" onChange="javascritp:ActualizarPlan()">
<% 		 
				int row=0;
				for (AlumPlan plan : lisPlanes){
					row++;
					String nombrePlan = "-"; 	
					if (mapaPlanes.containsKey(plan.getPlanId())){
						nombrePlan = mapaPlanes.get(plan.getPlanId()).getNombrePlan();
					}%>		
					<option value="<%=plan.getPlanId() %>" <%=plan.getPlanId().equals(planId)?"Selected":""%>><%=plan.getPlanId()%>-<%=nombrePlan%></option>
<%	
				}
%>
				</select>
			</small>
		</div>		
	</div>
</form>
<div class="container-fluid d-flex justify-content-around mt-3">	
<%
	EmpContacto contacto = new EmpContacto();
	String asesor = "-";
	if(existeAsesor && !asesorId.equals(carrera.getCodigoPersonal()) && !asesorId.equals(facultad.getCodigoPersonal())){		
		
		if (mapaContactos.containsKey(asesorId)){
			contacto = mapaContactos.get(asesorId);
		}
%>			
<!-- 		<div class="container-fluid mt-1 card mb-3" style="max-width: 400px;"> -->
<!-- 			<div class="row no-gutters"> -->
<!-- 				<div class="col-md-5"> -->
<%-- 					<img src="../../foto?Codigo=<%=asesorId%>" class="card-img" alt="..."> --%>
<!-- 				</div> -->
<!-- 				<div class="col-md-7"> -->
<!-- 					<div class="card-body"> -->
<!-- 						<h5 class="card-title">Asesor</h5>					 -->
<%-- 						<p class="card-text"><%=asesorNombre%><br> --%>
<!-- 						<small class="text-muted">							 -->
<%-- 							<br><%=contacto.getTelefono()%> --%>
<%-- 							<br><%=contacto.getCorreo()%> --%>
<!-- 						</small> -->
<!-- 						</p>												 -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div>		 -->
<%	}
	
	contacto = new EmpContacto();
	if (mapaContactos.containsKey(carrera.getCodigoPersonal())){
		contacto = mapaContactos.get(carrera.getCodigoPersonal());
	}
%>
	<div class="container-fluid mt-1 card mb-3" style="max-width: 400px;">
		<div class="row no-gutters">
			<div class="col-md-5">
				<img src="../../foto?Codigo=<%=carrera.getCodigoPersonal()%>" class="card-img" alt="...">
			</div>
			<div class="col-md-7">
				<div class="card-body">
					<h5 class="card-title"><spring:message code="portal.alumno.pasos.Coordinador"/><%=asesorId.equals(carrera.getCodigoPersonal())?"<small class='text-muted'>(Advisor)</small>":""%></h5>
					<p class="card-text">
					<%=coordinador%><br>
					<small class="text-muted">	
						<%=carrera.getNombreCarrera()%>
 						<br><%=contacto.getTelefono()%><%-- &nbsp;<a target="_blank" href="https://wa.me/52<%=contacto.getTelefono()%>?text=Hi, my name is <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno()%>, I need help!"><img src="../../imagenes/whatsappLogo.png" height="25px"></a>						 --%>
						<br><%=contacto.getCorreo()%>
					</small>	
					</p>												
				</div>
			</div>
		</div>
	</div>		
<%	
	if(!carrera.getCodigoPersonal().equals(facultad.getCodigoPersonal())){
		contacto = new EmpContacto();
		if (mapaContactos.containsKey(facultad.getCodigoPersonal())){
			contacto = mapaContactos.get(facultad.getCodigoPersonal());
		}
%>
	<div class="container-fluid mt-1 card mb-3" style="max-width: 400px;">
		<div class="row no-gutters">
			<div class="col-md-5">
				<img src="../../foto?Codigo=<%=facultad.getCodigoPersonal()%>" class="card-img" alt="...">
			</div>
			<div class="col-md-7">
				<div class="card-body">
					<h5 class="card-title"><spring:message code="portal.alumno.pasos.Director"/><%=asesorId.equals(facultad.getCodigoPersonal())?"<small class='text-muted'>(Advisor)</small>":""%></h5>					
					<p class="card-text"><%=director%><br>
					<small class="text-muted">
						<%=facultad.getNombreFacultad()%> x
						<br>
						<%=contacto.getTelefono()%>
<%-- 					<%	if(contacto.getTelefono().length()==10){%> --%>
<%-- 						&nbsp;<a target="_blank" href="https://wa.me/52<%=contacto.getTelefono()%>?text=HI, my name is <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno()%>, I need help!!"><img src="../../imagenes/whatsappLogo.png" height="25px"></a> --%>
<%-- 					<%	} %>	 --%>
						<br><%=contacto.getCorreo()%>
					</small>
					</p>												
				</div>
			</div>
		</div>
	</div>
<%	} %>
</div>
<% }else if((!existeCarga && finClearance) || (!existeCarga && !finClearance)){%>
	<div class="alert alert-danger">
		There is no Academic Load assigned for this semester. Contact your advisor to have your registration process started. 
	</div>
<% }else if(existeCarga && !finClearance){%>
	<div class="alert alert-danger">
		You are not financially cleared to register in this semester (<%=cargaFin.getCargaId()%>). Please kindly pay 80% required of your semester fees. Contact your advisor or go to 'Account' to review your status.
	</div>
<% }%>
</body>
<script type="text/javascript">
	function ActualizarPlan(){  		
  		document.frmPlanes.submit();
	}	
</script>	
