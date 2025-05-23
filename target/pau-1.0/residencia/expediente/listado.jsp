<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.residencia.spring.ResExpediente"%>
<%@page import="aca.residencia.spring.ResDocumento"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<head>
	<script type="text/javascript">
		function Borrar( matricula, folio ){
			if(confirm("<spring:message code="aca.JSEliminar"/>")==true){
		  		document.location.href="borrarListado?Matricula="+matricula+"&Folio="+folio;
		  	}
		}
	
		function CambiarResidencia(folio){
			if(confirm("Are you sure you want to change the student's residency status?")==true){
		  		document.location.href="cambiarResidencia?Folio="+folio;
		  	}
		}
	</script>
</head>	
<%		
	int yearActual					= Integer.parseInt(aca.util.Fecha.getHoy().substring(6,10));
	int yearAnterior				= yearActual-1;	
	String filtroEstado				= request.getParameter("Estado")==null?"T":request.getParameter("Estado");	
	String fechaIni					= request.getParameter("FechaIni")==null?"01/01/"+String.valueOf(yearAnterior):request.getParameter("FechaIni");
	String fechaFin					= request.getParameter("FechaFin")==null?aca.util.Fecha.getHoy():request.getParameter("FechaFin");
	String codigoPersonal 			= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 			= (String) session.getAttribute("codigoAlumno");	
	Acceso acceso 					= (Acceso) request.getAttribute("acceso");
	
	List<ResExpediente> listaExpedientes		= (List<ResExpediente>)request.getAttribute("listaExpedientes");
	List<ResDocumento> listaDocumentos			= (List<ResDocumento>)request.getAttribute("listaDocumentos");
	
	HashMap<String, MapaPlan> mapaPlanes 				= (HashMap<String, MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String, String> mapaCoordinadores			= (HashMap<String, String>) request.getAttribute("mapaCoordinadores");
	HashMap<String, AlumPersonal> mapaAlumnoExpediente	= (HashMap<String, AlumPersonal>) request.getAttribute("mapaAlumnoExpediente");
%>
<body>
<div class="container-fluid">
	<h1>Student Records</h1>
	<form name="forma" action="listado" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<select name="Estado" id="Estado" class="form-select" onchange="document.forma.submit()" style="width:170px;">
			<option value="T" <%=filtroEstado.equals("T")?"selected":""%>>All</option> 
			<option value="S" <%=filtroEstado.equals("S")?"selected":""%>>Request</option>
			<option value="C" <%=filtroEstado.equals("C")?"selected":""%>>VoBo. Coor.</option>
			<option value="R" <%=filtroEstado.equals("R")?"selected":""%>>VoBo. Res.</option>
			<option value="I" <%=filtroEstado.equals("I")?"selected":""%>>Inactive</option>
		</select>&nbsp;&nbsp;
		Start date: <input type="text" class="form-control" style="width:120px" data-date-format="dd/mm/yyyy" id="FechaIni" name="FechaIni" value="<%=fechaIni%>" required/>&nbsp;
		End date: <input type="text" class="form-control" style="width:120px" data-date-format="dd/mm/yyyy" id="FechaFin" name="FechaFin" value="<%=fechaFin%>" required/>&nbsp;
		<a href="javascript:Refrescar()" class="btn btn-primary"><i class="fas fa-sync-alt"></i></a>&nbsp;&nbsp;Filter:
		<input type="text" class="form-control search-query" placeholder="Search..." id="buscar" style="width:200px;">
	</div>
	</form>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>
			<th width="2%">Rec. Number</th>
			<th width="3%">Student ID</th>
			<th width="15%">Name</th>
			<th width="5%">Date</th>
			<th width="20%">Plan</th>
			<th width="25%">Description</th>
			<th width="30%">Documents</th>
			<th width="5%">Status</th>			
			<th width="10%">Operations</th>
			<th width="10%">Comments</th>
			
		</tr>
	</thead>
	<tbody>
<%
	int cont = 1;
	for(ResExpediente expediente : listaExpedientes){	
		
		String nombrePlan 	= "-";
		String carreraId 	= "0";
		if(mapaPlanes.containsKey(expediente.getPlanId())){
			nombrePlan 	= mapaPlanes.get(expediente.getPlanId()).getCarreraSe();
			carreraId 	= mapaPlanes.get(expediente.getPlanId()).getCarreraId();
		}

		String nombreAlumno	= "";
		AlumPersonal alumno = new AlumPersonal();
		if(mapaAlumnoExpediente.containsKey(expediente.getCodigoPersonal())){
			alumno = mapaAlumnoExpediente.get(expediente.getCodigoPersonal());
			nombreAlumno = alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno();
		}
		
		String estado = "Request";
		if(expediente.getEstado().equals("C")){
			estado = "VoBo coordinator";
		}else if(expediente.getEstado().equals("R")){
			estado = "VoBo residence";
		}else if(expediente.getEstado().equals("I")){
			estado = "Inactive";
		}
		if (acceso.getAccesos().contains(carreraId) || codigoPersonal.equals("9800308") || codigoPersonal.equals("9800069") || codigoPersonal.equals("9801159") ){
%>	
		<tr>
			<td>
				<%=cont++%>&nbsp;&nbsp;
			</td>
			<td><%=expediente.getCodigoPersonal()%></td>			
			<td><%=nombreAlumno%></td>
			<td><%=expediente.getFecha()%></td>
			<td><%=expediente.getPlanId()+" - "+nombrePlan%></td>
			<td><%=expediente.getDescripcion()%></td>			
			<td>
<%			for (ResDocumento documento: listaDocumentos){
				if (documento.getFolioExpediente().equals(expediente.getFolio())){
%>
			&nbsp;&nbsp;<a href="bajarArchivo?Folio=<%=documento.getFolio()%>"><i class="fas fa-download"></i> <%=documento.getNombre()%></a>
<%			
				}
			}	
%>								
			</td>
			<td><%=estado%></td>
			<td>
<% 			if(mapaCoordinadores.containsKey(codigoPersonal) || codigoPersonal.equals("9800308") || codigoPersonal.equals("9801159")){%>				
				<a href="modificar?Folio=<%=expediente.getFolio()%>&Estado=S" class="badge bg-success" title="Coordinator approval">S</a>&nbsp;&nbsp;
				<a href="modificar?Folio=<%=expediente.getFolio()%>&Estado=C" class="badge bg-info" title="Coordinator approval">C</a>&nbsp;&nbsp;
<% 			}%>				
<% 			if ( acceso.getAdministrador().equals("S") || codigoPersonal.equals("9800069") || codigoPersonal.equals("9801159") ){%>				
				<a href="modificar?Folio=<%=expediente.getFolio()%>&Estado=R" class="badge bg-dark" title="Residence approval">R</a>&nbsp;&nbsp;
<% 			}%>				
				<a href="modificar?Folio=<%=expediente.getFolio()%>&Estado=I" class="badge bg-warning" title="Inactive">I</a>
			</td>
			<td>
				<a href="comentario?Folio=<%=expediente.getFolio()%>" class="fas fa-edit"></a>
				<%if(!expediente.getComentario().equals("-")){ %>
					<span data-bs-toggle="tooltip" title=" <%=expediente.getComentario()%>"><i class="fas fa-comment"></i></span>	
<% 			    }%>		
		  </td>
				
		</tr>
<%	
		}
	} 
%>
	</tbody>
	</table>
</div>
</body>
<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script type="text/javascript" src="../../js/search.js"></script>
<script type="text/javascript">
	jQuery('#buscar').focus().search({table:jQuery("#table")});
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();
	
	function Refrescar(){
		document.forma.submit();
	}
	var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
	var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
	  return new bootstrap.Tooltip(tooltipTriggerEl)
	})
</script>

