<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="aca.apFisica.spring.ApFisicaAlumno"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

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
	
	List<ApFisicaAlumno> lista 					= (List<ApFisicaAlumno>)request.getAttribute("lista");
	HashMap<String, String> mapaAlumCarrera	 	= (HashMap<String, String>)request.getAttribute("mapaAlumCarrera");
	//HashMap<String, CatCarrera> mapaCarreras 	= (HashMap<String, CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String, MapaPlan> mapaPlanes	 	= (HashMap<String, MapaPlan>)request.getAttribute("mapaPlanes");
	HashMap<String, String> mapaAlumnos		 	= (HashMap<String, String>)request.getAttribute("mapaAlumnos");
	HashMap<String, String> mapaGrupos			= (HashMap<String, String>)request.getAttribute("mapaGrupos");
%>
<div class="container-fluid">
	<h2>Alumnos por Semestre</h2>
	<form name="forma" action="registrados" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<a href="menu" class="btn btn-primary btn-sm rounded-pill"><i class="fas fa-arrow-left"></i></a>&nbsp;
		Fecha: <input name="Fecha" type="text" id="Fecha" class="form-control" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%=fecha%>" style="width:110px">
		&nbsp;Materias:&nbsp;
		<select name="clave" class="form-select" style="width:450px;">
			<option value="COSM191" <%if(clave.equals("COSM191")) out.print("selected"); %>>COSM191 Desarrollo Personal para una Cultura Universitaria</option>
			<option value="COSM192" <%if(clave.equals("COSM192")) out.print("selected"); %>>COSM192 Desarrollo Personal: Preparación para la Vida</option>
			<option value="COSM293" <%if(clave.equals("COSM293")) out.print("selected"); %>>COSM293 Desarrollo Personal: Entorno Sustentable </option>
			<option value="COSM294" <%if(clave.equals("COSM294")) out.print("selected"); %>>COSM294 Desarrollo Personal: Visión Emprendedora</option>
			<option value="COSM395" <%if(clave.equals("COSM395")) out.print("selected"); %>>COSM395 Desarrollo Personal: Gestión Comunitaria</option>
			<option value="COSM396" <%if(clave.equals("COSM396")) out.print("selected"); %>>COSM396 Desarrollo Personal: Visión de Servicio</option>
		</select>
		&nbsp;
		<a href="#" onclick="$(this).closest('form').submit()" class="btn btn-primary"><i class="fas fa-sync-alt"></i></a> 
		&nbsp;&nbsp;&nbsp;
		<input type="text" class="form-control search-query" placeholder="Buscar" id="buscar" style="width:170px">
    </div>
	</form>
	<table id="table" class="table table-sm table-bordered">
	<thead>
	<tr>
		<th>#</th>
		<th>Matricula</th>
		<th>Alumno</th>
		<th>Grupo</th>
		<th>Carrera</th>
	</tr>
	</thead>
	<tbody>
<%
	int cont = 1;
	for(ApFisicaAlumno alumno : lista){
		
		String planId 				= "";		
 		if(mapaAlumCarrera.containsKey(alumno.getCodigoPersonal())){
 			planId = mapaAlumCarrera.get(alumno.getCodigoPersonal());
 		}
		
 		String carreraNombre 			= "-";
		if(mapaPlanes.containsKey(planId)){
			carreraNombre = mapaPlanes.get(planId).getCarreraSe();
		}
		
		String alumnoNombre	= "-";
		if(mapaAlumnos.containsKey(alumno.getCodigoPersonal())){
			alumnoNombre = mapaAlumnos.get(alumno.getCodigoPersonal());
		}
		
		String nombre = "";
		if(mapaGrupos.containsKey(alumno.getGrupoId())){
			nombre = mapaGrupos.get(alumno.getGrupoId());
		}	
		
		out.print("<tr>");
		out.print("<td>"+cont+"</td>");
		out.print("<td>"+alumno.getCodigoPersonal()+"</td>");
		out.print("<td>"+alumnoNombre+"</td>");
		out.print("<td>"+nombre+"</td>");
		out.print("<td>"+planId+":"+carreraNombre+"</td>");
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