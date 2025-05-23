<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.apFisica.spring.ApFisicaAlumno"%>
<%@page import="aca.apFisica.spring.ApFisicaGrupo"%>
<%@page import="aca.catalogo.spring.CatTipoCal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<!-- inicio de estructura -->
<%	
	String fecha    		= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
	String clave 			= request.getParameter("clave")==null?"COSM191":request.getParameter("clave");
	
	List<AlumnoCurso> lisAlumnos 						= (List<AlumnoCurso>)request.getAttribute("lisAlumnos");	
	HashMap<String, MapaPlan> mapaPlanes		 		= (HashMap<String, MapaPlan>)request.getAttribute("mapaPlanes");
	HashMap<String, String> mapaAlumnos		 			= (HashMap<String, String>)request.getAttribute("mapaAlumnos");
	HashMap<String, ApFisicaAlumno> mapaGrupoAlumnos	= (HashMap<String, ApFisicaAlumno>)request.getAttribute("mapaGrupoAlumnos");
	HashMap<String, ApFisicaGrupo> mapaGrupos			= (HashMap<String, ApFisicaGrupo>)request.getAttribute("mapaGrupos");
	HashMap<String, CatTipoCal> mapaTipos				= (HashMap<String, CatTipoCal>)request.getAttribute("mapaTipos");
	
%>
<div class="container-fluid">
	<h2>Alumnos inscritos en la materia</h2>
	<form name="forma" action="porMateria" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<a href="menu" class="btn btn-primary btn-sm rounded-pill"><i class="fas fa-arrow-left"></i></a>&nbsp;
		Fecha: <input name="Fecha" type="text" id="Fecha" class="form-control" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%=fecha%>" style="width:110px">&nbsp;
		Materia:&nbsp;
		<select name="clave" class="form-select" style="width:450px">
			<option value="COSM191" <%if(clave.equals("COSM191")) out.print("selected"); %>>COSM191 Desarrollo Personal para una Cultura Universitaria</option>
			<option value="COSM192" <%if(clave.equals("COSM192")) out.print("selected"); %>>COSM192 Desarrollo Personal: Preparación para la Vida</option>
			<option value="COSM293" <%if(clave.equals("COSM293")) out.print("selected"); %>>COSM293 Desarrollo Personal: Entorno Sustentable </option>
			<option value="COSM294" <%if(clave.equals("COSM294")) out.print("selected"); %>>COSM294 Desarrollo Personal: Visión Emprendedora</option>
			<option value="COSM395" <%if(clave.equals("COSM395")) out.print("selected"); %>>COSM395 Desarrollo Personal: Gestión Comunitaria</option>
			<option value="COSM396" <%if(clave.equals("COSM396")) out.print("selected"); %>>COSM396 Desarrollo Personal: Visión de Servicio</option>
		</select>
		&nbsp;
		<a href="#" onclick="$(this).closest('form').submit()" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>
		&nbsp;&nbsp;&nbsp;
		<input type="text" class="form-control search-query" placeholder="Buscar" id="buscar" style="width:170px">		
    </div>
	</form>
	<table id="table" class="table table-sm">
	<thead>
	<tr>
		<th>#</th>
		<th>Matricula</th>
		<th>Alumno</th>
		<th>Acta</th>
		<th>Estado</th>
		<th>Carrera</th>
		<th>Grupo</th>
		<th>Instructor</th>
		<th>Horario</th>
	</tr>
	</thead>
	<tbody>
<%
	int cont = 1;
	for(AlumnoCurso alumno : lisAlumnos){		
		
 		String carreraNombre 			= "-";
		if(mapaPlanes.containsKey(alumno.getPlanId())){
			carreraNombre = mapaPlanes.get(alumno.getPlanId()).getCarreraSe();
		}
		
		String alumnoNombre	= "-";
		if(mapaAlumnos.containsKey(alumno.getCodigoPersonal())){
			alumnoNombre = mapaAlumnos.get(alumno.getCodigoPersonal());
		}
		
		String grupoId	= "0";
		if (mapaGrupoAlumnos.containsKey(alumno.getCodigoPersonal()+alumno.getCursoCargaId())){
			grupoId = mapaGrupoAlumnos.get(alumno.getCodigoPersonal()+alumno.getCursoCargaId()).getGrupoId();
		}
		
		String grupoNombre 	= "-";
		String instructor 	= "-";
		String horario 		= "-";
		if(mapaGrupos.containsKey(grupoId)){
			grupoNombre 	= mapaGrupos.get(grupoId).getNombreGrupo();
			instructor 		= mapaGrupos.get(grupoId).getInstructor();
			horario			= mapaGrupos.get(grupoId).getDia1()+" "+mapaGrupos.get(grupoId).getHora(); 
		}
		
		String tipoCal	= "0";
		if (mapaTipos.containsKey(alumno.getTipoCalId())){
			tipoCal = mapaTipos.get(alumno.getTipoCalId()).getNombreTipoCal();
			if (alumno.getTipoCalId().equals("3")){
				tipoCal="<span class='badge bg-warning rounded-pill'>"+tipoCal+"</span>";
			}else if (alumno.getTipoCalId().equals("2") || alumno.getTipoCalId().equals("4")){
				tipoCal="<span class='badge bg-danger rounded-pill'>"+tipoCal+"</span>";
			}else if (alumno.getTipoCalId().equals("1") || alumno.getTipoCalId().equals("1")){
				tipoCal="<span class='badge bg-success rounded-pill'>"+tipoCal+"</span>";
			}else{
				tipoCal="<span class='badge bg-info rounded-pill'>"+tipoCal+"</span>";
			}
		}
		
		out.print("<tr>");
		out.print("<td>"+cont+"</td>");
		out.print("<td>"+alumno.getCodigoPersonal()+"</td>");
		out.print("<td>"+alumnoNombre+"</td>");		
		out.print("<td>"+alumno.getCursoCargaId()+"</td>");
		out.print("<td>"+tipoCal+"</td>");
		out.print("<td>"+alumno.getPlanId()+"-"+carreraNombre+"</td>");
		out.print("<td>"+grupoNombre+"</td>");
		out.print("<td>"+instructor+"</td>");
		out.print("<td>"+horario+"</td>");
		out.print("</tr>");
		cont++;
	}
%>
	</tbody>
</table>
<script src="../../js/search.js"></script>
<script>
	$('#buscar').search();
	$('#Fecha').datepicker();
</script>
</div>