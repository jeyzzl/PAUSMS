<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.matricula.spring.CambioCarrera"%>
<%@page import="aca.matricula.spring.CambioMateria"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.catalogo.spring.CatTipoCal"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.vista.spring.CargaAcademica"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.carga.spring.Carga"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String baja 			= (String)request.getAttribute("baja");
	String alta 			= (String)request.getAttribute("alta");
	String cargaEligio 		= (String)request.getAttribute("cargaEligio");	
	
	String mensaje			= (String)request.getAttribute("mensaje");
	AlumPersonal alumno		= (AlumPersonal)request.getAttribute("alumno");
	Boolean esInscrito		= (Boolean)request.getAttribute("esInscrito");
	
	List<AlumnoCurso> lisAlumnoCursoBaja			= (List<AlumnoCurso>)request.getAttribute("lisAlumnoCursoBaja");
	List<CargaAcademica> lisAlumnoCursoAlta			= (List<CargaAcademica>)request.getAttribute("lisAlumnoCursoAlta");
	List<CargaAlumno> lisPlanesAlumno				= (List<CargaAlumno>)request.getAttribute("lisPlanesAlumno");
	List<MapaPlan> lisPlanes						= (List<MapaPlan>)request.getAttribute("lisPlanes");
	List<AlumnoCurso> listaMaterias					= (List<AlumnoCurso>)request.getAttribute("listaMaterias");
	List<Carga> lisCargas 							= (List<Carga>)request.getAttribute("lisCargas");
	
	HashMap<String,CatTipoCal> mapaTipoCal			= (HashMap<String,CatTipoCal>)request.getAttribute("mapaTipoCal");
	HashMap<String,String> mapNombrePlan			= (HashMap<String,String>)request.getAttribute("mapNombrePlan");
	HashMap<String,CambioMateria> mapaCambioMateria	= (HashMap<String,CambioMateria>)request.getAttribute("mapaCambioMateria");
	
	int con = 1;
%>
<head>
	<link rel="stylesheet" href="../../js/chosen/chosen.css" />
</head>
<body>
<div class="container-fluid">
	<h2>Course change requests <small class="text-muted fs-5">(<%=alumno.getCodigoPersonal()+" - "+alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno()%>)</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="listado">Return</a>
	</div>
<% if(mensaje.equals("1")){%>
	<div class="alert alert-success">
		Saved
	</div>
<% }%>
	<form action="grabar" method="get">
	<div class="row">
		<div class="col-sm-6">
			<h3>Original Degree</h3>
			<select name="Baja" id="Baja" class="form-select" onchange="javascript:cambioCarrera()">
<% 				for(CargaAlumno plan : lisPlanesAlumno){
					String nombrePlan = "";
					if(mapNombrePlan.containsKey(plan.getPlanId())){
						nombrePlan = mapNombrePlan.get(plan.getPlanId());
					}
%>
				<option <%=baja.equals(plan.getCargaId()+"-"+plan.getBloqueId()) ? "Selected" : ""%> value="<%=plan.getCargaId()+"-"+plan.getBloqueId()%>"><%=plan.getCargaId()%> - <%=plan.getBloqueId()%> - <%=plan.getPlanId()%> - <%=nombrePlan%></option>
<% 				}%>
			</select><br>
<% if(esInscrito){%>
			<table class="table">
			<thead>
			<tr>
				<th>#</th>
				<th>&nbsp;</th>
				<th>Cycle</th>
				<th>Key</th>
				<th>Subject</th>
				<th>Group</th>
				<th>Credits.</th>
				<th>Block</th>
				<th>Status</th>
				<th>T.Grd.</th>
			</tr>
			</thead>
			<tbody>
<% 				for(AlumnoCurso curso : lisAlumnoCursoBaja){
					String tipoCal = "";
					if(mapaTipoCal.containsKey(curso.getTipoCalId())){
						tipoCal = mapaTipoCal.get(curso.getTipoCalId()).getNombreCorto();
					}
					
					boolean ok = false;
					if(mapaCambioMateria.containsKey(curso.getCursoId())){
						ok = true;
					}
%>
			<tr>
				<th><%=con++%></th>
				<th>
<% 							if(curso.getTipoCalId().equals("I")){%>
					<input type="checkbox" class="form-check-input" name="<%=curso.getCursoId()%>" <%=ok ? "checked" : ""%> value="S">
<% 							}%>
				</th>
				<th><%=curso.getCiclo()%></th>
				<th><%=curso.getCursoId().substring(8)%></th>
				<th><%=curso.getNombreCurso()%></th>
				<th><%=curso.getGrupo()%></th>
				<th><%=curso.getCreditos()%></th>
				<th><%=curso.getBloqueId()%></th>
				<th><%=curso.getEstado()%></th>
				<th><%=tipoCal%></th>
			</tr>
<% 				}%>
					</tbody>
				</table>
<% 	}%>
			</div>
			<div class="col-sm-6">
				<h3>Load</h3>
				<select name="CargaEligio" id="CargaEligio" class=" form-control chosen" onchange="cambioCarga()">
<% 				for(Carga carga : lisCargas){%>
					<option <%=carga.getCargaId().equals(cargaEligio) ? "Selected" : ""%> value="<%=carga.getCargaId()%>"><%=carga.getCargaId()%> - <%=carga.getNombreCarga()%></option>
<% 				}%>
				</select><br>
				<h3>New Degree</h3>
				<select name="Alta" id="Alta" class=" form-control chosen" onchange="cambioCarrera()">
<% 				for(MapaPlan plan : lisPlanes){%>
					<option <%=plan.getPlanId().equals(alta) ? "Selected" : ""%> value="<%=plan.getPlanId()%>"><%=plan.getPlanId()%> - <%=plan.getCarreraSe()%></option>
<% 				}%>
				</select><br>
<% if(esInscrito){%>
				<table class="table">
				<thead>
				<tr>
					<th>#</th>
					<th>&nbsp;</th>
					<th>Cycle</th>
					<th>Key</th>
					<th>Subject</th>
					<th>Group</th>
					<th>Credits</th>
					<th>Block</th>
				</tr>
				</thead>
				<tbody>
<% 				con = 1;
				for(CargaAcademica curso : lisAlumnoCursoAlta){
					boolean ok = false;
					if(mapaCambioMateria.containsKey(curso.getCursoId())){
						ok = true;
					}
%>
				<tr>
					<th><%=con++%></th>
					<th>
						<input type="checkbox" class="form-check-input" name="<%=curso.getCursoCargaId()%>" <%=ok ? "checked" : ""%>>
					</th>					
					<th><%=curso.getCiclo()%></th>
					<th><%=curso.getCursoId().substring(8)%></th>
					<th><%=curso.getNombreCurso()%>-(<%=curso.getCodigoPersonal()%>)</th>
					<th><%=curso.getGrupo()%></th>
					<th><%=curso.getCreditos()%></th>
					<th><%=curso.getBloqueId()%></th>
				</tr>
<% 				}%>
				</tbody>
				</table>
<% }%>
			</div>
		</div>
		<button type="submit" class="btn btn-primary">Save</button>
	</form>
</div>
</body>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript"> 
	jQuery(".chosen").chosen();

	function cambioCarrera(){
		var baja = document.getElementById("Baja").value;
		var alta = document.getElementById("Alta").value;
		var carga = document.getElementById("CargaEligio").value;
		
		document.location.href = "nuevaSolicitud?Baja="+baja+"&Alta="+alta+"&CargaEligio="+carga;
	}

	function cambioCarga(){
		var baja = document.getElementById("Baja").value;
		var carga = document.getElementById("CargaEligio").value;
		
		document.location.href = "nuevaSolicitud?Baja="+baja+"&CargaEligio="+carga;
	}
</script>