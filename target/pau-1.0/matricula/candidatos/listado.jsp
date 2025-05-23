<%@ page buffer= "none"%>
<%@ page import= "java.util.List"%>
<%@ page import="java.util.Arrays"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import="aca.acceso.spring.Acceso"%>
<%@ page import="aca.catalogo.spring.CatFacultad"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>
<%@ page import="aca.matricula.spring.MatAlumno"%>
<%@ page import="aca.matricula.spring.MatEvento"%>
<%@ page import="aca.plan.spring.MapaPlan"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>
<%@ page import="aca.matricula.spring.MatIngreso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	String codigoAlumno			= (String) session.getAttribute("codigoAlumno");	
	String planId 				= (String) request.getAttribute("planId");
	String mensaje 				= (String) request.getAttribute("mensaje");
	String modalidadNombre		= (String) request.getAttribute("modalidadNombre");
	String esEnLinea			= (String) request.getAttribute("esEnLinea");
	boolean muestraAgregar		= (boolean) request.getAttribute("muestraAgregar");

	Acceso acceso 								= (Acceso) request.getAttribute("acceso");
	MatEvento evento 							= (MatEvento) request.getAttribute("evento");
	String nombreAlumno							= (String) request.getAttribute("nombreAlumno");
	List<MatAlumno> lisMatAlumno				= (List<MatAlumno>) request.getAttribute("lisMatAlumno");
	List<AlumPlan> lisPlanes 					= (List<AlumPlan>) request.getAttribute("lisPlanes");
	List<MatEvento> lisMatEvento 				= (List<MatEvento>) request.getAttribute("lisMatEvento");
	
	HashMap<String,String> mapaAlumnos		 	= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
	HashMap<String,CatFacultad> mapaFacultades 	= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");	
	HashMap<String, CatCarrera> mapaCarreras 	= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String, MapaPlan> mapaPlanes 		= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String, MatAlumno> mapaMatAlumno 	= (HashMap<String,MatAlumno>) request.getAttribute("mapaMatAlumno");
	
	String alumnoSesion 		= "";
	if (mapaAlumnos.containsKey(codigoAlumno)){
		alumnoSesion = mapaAlumnos.get(codigoAlumno);
	}	
	String facultadTmp = "X";	
	int row = 1;
%>
<head></head>
<style>
.anchor{
  text-decoration: none;
  }
</style>

<div class="container-fluid">
	<h3>Candidatos de inscripción <small class="text-muted fs-6"> ( <%=codigoAlumno%> - <%=nombreAlumno%> - <%=modalidadNombre%> )</small></h3>
	<form name="form" action="agregar" method="post">
	<input type="hidden" name="EventoId" value="<%=evento.getEventoId()%>">
	<div class="alert alert-success">	
		Evento:&nbsp;
		<select class="custom-select" name="EventoId" id="EventoId" style="width:220px;" onchange="javascript:cambiaEvento();">
<%		for(MatEvento eve : lisMatEvento){%>
			<option value="<%=eve.getEventoId()%>" <%=eve.getEventoId().equals(evento.getEventoId()) ? "selected":""%>><%=eve.getEventoNombre()%></option>
<%		}%>
		</select>
<% 		 
	if(muestraAgregar){
%>
		&nbsp;
		Plan:&nbsp;
		<select class="custom-select" name="PlanId" style="width:550px;">
<% 		 
		int row2=0;
		for (AlumPlan plan : lisPlanes){
			row2++;
			String nombrePlan = "-"; 	
			if (mapaPlanes.containsKey(plan.getPlanId())){
				nombrePlan = mapaPlanes.get(plan.getPlanId()).getNombrePlan();
			}%>		
			<option value="<%=plan.getPlanId() %>" <%=plan.getPlanId().equals(planId)?"Selected":""%>>[<%=plan.getEstado().equals("0")?"S":"P"%>] - <%=plan.getPlanId()%> - <%=nombrePlan%></option>
<%		} %>
		</select>
		&nbsp;&nbsp;
		Asistencia:&nbsp;
		<select class="custom-select" name="Modo" id="Modo" style="width:220px;">
<%		if (esEnLinea.equals("S")){%>					
			<option value="V">Virtual</option>
<%		}else{%>
			<option value="P">Presencial</option>			
<%		} %>								
			
			
		</select>
		&nbsp;&nbsp;
		<button type="submit" class="btn btn-primary">Agregar</button>		
<%	} %>
	</div>
	</form>
	<div class="alert alert-success">
		<input type="text" class="input-medium search-query" placeholder="Buscar..." id="buscar">
<% 	if(mensaje.equals("1")){%>
		&nbsp;&nbsp;&nbsp;<b>Los datos han sido guardados</b>		
<% 	}%>
	</div>
	<table id="table" class="table table-sm table-bordered table-striped">	
<%	
	for(MatAlumno alumno : lisMatAlumno){
		
		String estado 	= "-";
		String colorP	= "bg-secondary";
		String colorA	= "bg-secondary";
		String colorN	= "bg-secondary";

		if(mapaMatAlumno.containsKey(alumno.getCodigoPersonal()+alumno.getPlanId())){
			estado = mapaMatAlumno.get(alumno.getCodigoPersonal()+alumno.getPlanId()).getEstado();
			if(estado.equals("P")){
				estado 	= "Pendiente";
				colorP  = "bg-warning";
			}else if(estado.equals("N")){
				estado  = "No aprobado";				
			    colorN	= "bg-danger";
			}else{
				estado = "Aprobado";				
				colorA  = "bg-success";
			}
		}
		
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(alumno.getCodigoPersonal())){
			alumnoNombre = mapaAlumnos.get(alumno.getCodigoPersonal());
		}
		
		String carreraId = "";
		String nombrePlan = "-"; 	
		if (mapaPlanes.containsKey(alumno.getPlanId())){
			nombrePlan 	= mapaPlanes.get(alumno.getPlanId()).getNombrePlan();
			carreraId 	= mapaPlanes.get(alumno.getPlanId()).getCarreraId();
		}
		
		if (acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") || acceso.getAccesos().indexOf(carreraId) != -1){
			
			String facultad = "";
			CatCarrera carrera = new CatCarrera();
			if(mapaCarreras.containsKey(carreraId)){
				carrera = mapaCarreras.get(carreraId);
			}

			if(mapaFacultades.containsKey(carrera.getFacultadId())){
				CatFacultad catFacultad = mapaFacultades.get(carrera.getFacultadId());
				facultad =  catFacultad.getTitulo()+" de "+catFacultad.getNombreFacultad();
			}
			if(!facultadTmp.equals(facultad)){
				row = 1;
%>
		<thead>
		<tr class="table-info">
			<td colspan="10"><h5><%=facultad%></h5></td>
		</tr>
		<tr>
			<th>Op.</th>
			<th>#</th>
			<th>Matrícula</th>
			<th>Alumno</th>
			<th>Plan</th>
			<th>Carrera</th>
			<th>Ubicación</th>
			<th>Solicitó</th>
			<th>Fecha</th>
			<th>Estado</th>
		</tr>
		</thead>		
<% 			
				facultadTmp = facultad;
			}			
%>
		<tr>
			<td><a title="Eliminar" style="color: white;" class="btn btn-danger btn-sm" onclick="javascript:borrar('<%=alumno.getEventoId()%>','<%=alumno.getCodigoPersonal()%>','<%=alumno.getPlanId()%>');"><i class="fas fa-trash-alt"></i></a></td>
			<td><%=row++%></td>
			<td><%=alumno.getCodigoPersonal()%></td>
			<td><%=alumnoNombre%></td>
			<td><%=alumno.getPlanId()%></td>
			<td><%=nombrePlan%></td>
			<td><%=alumno.getModo().equals("V")?"Hogar/Virtual":"Campus UM"%></td>
			<td><%=alumno.getUsuario()%></td>
			<td><%=alumno.getFecha()%></td>
			<td>
      			<a href="grabar?EventoId=<%=alumno.getEventoId()%>&CodigoPersonal=<%=alumno.getCodigoPersonal()%>&PlanId=<%=alumno.getPlanId()%>&Estado=P"
        		  class="anchor badge rounded-pill <%=colorP%>"
        		  >P
        		</a>
        		<a
        		  href="grabar?EventoId=<%=alumno.getEventoId()%>&CodigoPersonal=<%=alumno.getCodigoPersonal()%>&PlanId=<%=alumno.getPlanId()%>&Estado=A"
   		    	  class="anchor badge rounded-pill <%=colorA%>"
        		  >A</a
        		 ><a
        		  href="grabar?EventoId=<%=alumno.getEventoId()%>&CodigoPersonal=<%=alumno.getCodigoPersonal()%>&PlanId=<%=alumno.getPlanId()%>&Estado=N"
        	      class="anchor badge rounded-pill <%=colorN%>"
        		  >N</a
        		 >
  		  </td>
		</tr>
<%
		}
	}
%>	
	</table>
s</div>
<script type="text/javascript" src="../../js/search.js"></script>
<script type="text/javascript">

	jQuery('#buscar').focus().search({table:jQuery("#table")});
	
	function borrar(eventoId,codigo,planId){
		var r = confirm("Borrar este registro!");
		if (r == true) {
			location.href = "eliminar?EventoId="+eventoId+"&Codigo="+codigo+"&PlanId="+planId;
		} 
	}

	function cambiaEvento(){
		var eventoId = document.getElementById("EventoId").value;
		location.href = "listado?EventoId="+eventoId;
	}
	
	
</script>
