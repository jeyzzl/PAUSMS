<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.*"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumDocumento"%>
<%@ page import="aca.residencia.spring.ResExpediente"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="../alumno/menu.jsp"%>

<head>
	<script type="text/javascript">
		function borrar(folio){
			if(confirm("If you delete this Folder all the Documents in it will be lost. Do you want to continue?")){
				location.href = "borrarExpediente?Folio="+folio;
			}
		}
	</script>
</head>
<%
	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");

	AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");
	
	String mensaje 		= (String) request.getAttribute("mensaje");
	String matricula 	= (String) request.getAttribute("matricula");	
	
	List<AlumDocumento> lisAlumDocumentoFormato 	= (List<AlumDocumento>) request.getAttribute("lisAlumDocumentoFormato");
	List<ResExpediente> listaExpedientes 			= (List<ResExpediente>) request.getAttribute("listaExpedientes");
	
	HashMap<String, String> mapaCantidadDcoumento 	= (HashMap<String, String>) request.getAttribute("mapaCantidadDcoumentos");
	HashMap<String, String> mapNombrePlan			= (HashMap<String, String>) request.getAttribute("mapNombrePlan");
%>
<body>
	<div class="container-fluid mt-1">	
		<div class="alert alert-success">
			<a href="previos"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;
			<span class="badge rounded-pill bg-dark">1G</span>
			<spring:message code="portal.alumno.solicitudExternado.SolicitudExternado"/> <small class="text-muted"> ( <%=codigoAlumno%> - <%=alumPersonal.getNombre()%>  <%=alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%> )</small>
		</div>	
	</div>	
<% if(mensaje.equals("1")){%>
	<div class="aler alert-success">
		<spring:message code="portal.alumno.solicitudExternado.ArchivoGuardado"/>
	</div>
<% }%>
	<div class="container-fluid d-flex justify-content-start mt-1">
		<%-- <div class="card card bg-light" style="max-width: 20rem;">
		  <div class="card-header"><span class="badge rounded-pill bg-dark">A</span>&nbsp;<spring:message code="portal.alumno.solicitudExternado.Padres"/></div>
		  <div class="card-body ">
			<a href="solPadres.pdf" target="_blank" style="text-decoration:none;"><i class="far fa-file-pdf"></i> <span style="color: black;"><spring:message code="aca.Solicitud"/></span></a>&nbsp;&nbsp;
			<a href="conPadres.pdf" target="_blank" style="text-decoration:none;"><i class="far fa-edit"></i> <span style="color: black;"><spring:message code="aca.Convenio"/></span></a>&nbsp;
		  </div>
		</div>&nbsp;&nbsp;
		<div class="card card bg-light" style="max-width: 20rem;">
		  <div class="card-header"><span class="badge rounded-pill bg-dark">B</span>&nbsp;<spring:message code="portal.alumno.solicitudExternado.Familiares"/></div>
		  <div class="card-body ">
			<a href="solFamiliares.pdf" target="_blank" style="text-decoration:none;"><i class="far fa-file-pdf"></i> <span style="color: black;"><spring:message code="aca.Solicitud"/></span></a>&nbsp;&nbsp;
			<a href="conFamiliares.pdf" target="_blank" style="text-decoration:none;"><i class="far fa-edit"></i> <span style="color: black;"><spring:message code="aca.Convenio"/></span></a>&nbsp;
		  </div>		  
		</div>&nbsp;&nbsp;
		<div class="card card bg-light" style="max-width: 20rem;">
		  <div class="card-header"><span class="badge rounded-pill bg-dark">C</span>&nbsp;<spring:message code="portal.alumno.solicitudExternado.Mayores"/></div>
		  <div class="card-body ">
			<a href="solMayores.pdf" target="_blank" style="text-decoration:none;"><i class="far fa-file-pdf"></i> <span style="color: black;"><spring:message code="aca.Solicitud"/></span></a>&nbsp;&nbsp;
			<a href="conMayores.pdf" target="_blank" style="text-decoration:none;"><i class="far fa-edit"></i> <span style="color: black;"><spring:message code="aca.Convenio"/></span></a>&nbsp;
		  </div>
		</div>&nbsp;&nbsp;
		<div class="card card bg-light" style="max-width: 20rem;">
		  <div class="card-header"><span class="badge rounded-pill bg-dark">D</span>&nbsp;<spring:message code="portal.alumno.solicitudExternado.Empleados"/></div>
		  <div class="card-body ">
			<a href="solEmpleados.pdf" target="_blank" style="text-decoration:none;"><i class="far fa-file-pdf"></i> <span style="color: black;"><spring:message code="aca.Solicitud"/></span></a>&nbsp;&nbsp;
			<a href="conEmpleados.pdf" target="_blank" style="text-decoration:none;"><i class="far fa-edit"></i> <span style="color: black;"><spring:message code="aca.Convenio"/></span></a>&nbsp;
		  </div>
		</div>&nbsp;&nbsp;		
		<div class="card card bg-light" style="max-width: 20rem;">
		  <div class="card-header"><span class="badge rounded-pill bg-dark">E</span>&nbsp;<spring:message code="portal.alumno.solicitudExternado.Casados"/> </div>
		  <div class="card-body ">
			<a href="solCasados.pdf" target="_blank" style="text-decoration:none;"><i class="far fa-file-pdf"></i> <span style="color: black;"><spring:message code="aca.Solicitud"/></span></a>&nbsp;&nbsp;
			<a href="conCasados.pdf" target="_blank" style="text-decoration:none;"><i class="far fa-edit"></i> <span style="color: black;"><spring:message code="aca.Convenio"/></span></a>&nbsp;
		  </div>
		</div>&nbsp;&nbsp;		
		<div class="card card bg-light" style="max-width: 20rem;">
		  <div class="card-header"><span class="badge rounded-pill bg-dark">F</span>&nbsp;<spring:message code="portal.alumno.solicitudExternado.LicenciaturaTerminada"/></div>
		  <div class="card-body ">
			<a href="solLicenciatura.pdf" target="_blank" style="text-decoration:none;"><i class="far fa-file-pdf"></i> <span style="color: black;"><spring:message code="aca.Solicitud"/></span></a>&nbsp;&nbsp;
			<a href="conLicenciatura.pdf" target="_blank" style="text-decoration:none;"><i class="far fa-edit"></i> <span style="color: black;"><spring:message code="aca.Convenio"/></span></a>&nbsp;
		  </div>
		</div> --%>
	</div>
	<br>
<%
	if(matricula.equals(codigoPersonal)){
%>
	<div class="container-fluid mt-1">
		<h5><spring:message code="portal.alumno.solicitudExternado.Nota"/>:</h5>	
		<h6><spring:message code="portal.alumno.solicitudExternado.MensajeUno"/></h6>
		<h6><spring:message code="portal.alumno.solicitudExternado.MensajeDos"/></h6>
		<a class="btn btn-primary" href="nuevoExpediente" title="Agregar nuevo expediente"><i class="fas fa-plus"></i></a><br><br>
<% if(mensaje.equals("3")){%>
		<div class="aler alert-success">
			<spring:message code="portal.alumno.solicitudExternado.ExpedienteGuardado"/>
		</div>
<% } else if(mensaje.equals("4")){%>
		<div class="aler alert-success">
			<spring:message code="portal.alumno.solicitudExternado.ExpedienteBorrado"/>
		</div>
<% }%>
		<table class="table">
		<thead>
		<tr>
			<th><spring:message code="portal.alumno.solicitudExternado.Folio"/></th>
			<th><spring:message code="portal.alumno.solicitudExternado.Fecha"/></th>
			<th><spring:message code="portal.alumno.solicitudExternado.Plan"/></th>
			<th><spring:message code="portal.alumno.solicitudExternado.Descripcion"/></th>
			<th><spring:message code="portal.alumno.solicitudExternado.Estado"/></th>
			<th><spring:message code="portal.alumno.solicitudExternado.Documentos"/></th>
			<th><spring:message code="aca.Comentario"/></th>
		</tr>
		</thead>
		<tbody>
<%
		int cont = 1;
		for(ResExpediente expediente : listaExpedientes){
			String documentos = "0";
			if(mapaCantidadDcoumento.containsKey(expediente.getFolio())){
				documentos = mapaCantidadDcoumento.get(expediente.getFolio());
			}
			String nombrePlan = "";
			if(mapNombrePlan.containsKey(expediente.getPlanId())){
				nombrePlan = mapNombrePlan.get(expediente.getPlanId());
			}
			String estado = "Solicitud";
			if(expediente.getEstado().equals("V")){
				estado = "Visto bueno";
			}else if(expediente.getEstado().equals("E")){
				estado = "Enviado";
			}else if(expediente.getEstado().equals("A")){
				estado = "Autorizado";
			}else if(expediente.getEstado().equals("I")){
				estado = "Inactivo";
			}	
%>
		<tr>
			<td>
				<%=cont++%>&nbsp;&nbsp;
<% 			if(expediente.getEstado().equals("S")){%>
				<a href="nuevoExpediente?Folio=<%=expediente.getFolio()%>" class="btn btn-info" title="Editar expediente"><i class="fas fa-pencil-alt"></i></a>&nbsp;
<% 			}%>
<% 			if(documentos.equals("0")){%>
				<a href="javascript:borrar('<%=expediente.getFolio()%>');" class="btn btn-danger" title="Borrar expediente"><i class="fas fa-times"></i></a>
<% 			}%>
			</td>
			<td><%=expediente.getFecha()%></td>
			<td><%=expediente.getPlanId()+" - "+nombrePlan%></td>
			<td><%=expediente.getDescripcion()%></td>
			<td><%=estado%></td>						
			<td>
				<a href="nuevoDocumento?ExpedienteId=<%=expediente.getFolio()%>&Mensaje=0">
					<button type="button" class="btn btn-primary position-relative">
					  	<i class="fas fa-plus"></i> <spring:message code="portal.alumno.solicitudExternado.Documentos"/>
					  	<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
					    	<%=documentos%>
					  	</span>
					</button>
				</a>
			</td>
			<td><%=expediente.getComentario()%></td>
		</tr>
<%		} %>
		</tbody>	
		</table>		
	</div>		
<%	} %>
</body>