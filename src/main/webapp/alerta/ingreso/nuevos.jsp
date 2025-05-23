<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.alumno.spring.AlumCovid"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.internado.spring.IntAcceso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<style>
	body{
		background: white;
	} 
	
	input[type=checkbox]{
	  /* Double-sized Checkboxes */
	  -ms-transform: scale(1.5); /* IE */
	  -moz-transform: scale(1.5); /* FF */
	  -webkit-transform: scale(1.5); /* Safari and Chrome */
	  -o-transform: scale(1.5); /* Opera */
	  padding: 10px;
	}
	
	th{
		padding-right: 30px !important;
	}
</style>
<body>
<%	
	String periodoId			= (String) request.getAttribute("periodoId");	
	Acceso acceso 				= (Acceso) request.getAttribute("acceso");
	IntAcceso intAcceso 		= (IntAcceso) request.getAttribute("intAcceso");
	String nombreMaestro		= (String) request.getAttribute("nombreMaestro");	
	
	//AlumAcademico alumAcademico = (AlumAcademico) request.getAttribute("alumAcademico");
			
	List<AlumPersonal> lisNuevos 			= (List<AlumPersonal>) request.getAttribute("lisNuevos");	
	HashMap<String,String> mapaPlanAlumno	= (HashMap<String,String>) request.getAttribute("mapaPlanAlumno");	
	HashMap<String,MapaPlan> mapaPlanes		= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String,String> mapDormi 		= (HashMap<String,String>) request.getAttribute("mapDormi");
%>
<div class="container-fluid">
	<h2>Alumnos que necesitas registrar <small class="text-muted fs-5">(<%=acceso.getCodigoPersonal()%> - <%=nombreMaestro%>)</small></h2>
	<table class="table table-condesed table-bordered" id="table">
	<thead>
	<tr>
		<th>#</th>
		<th>Matrícula</th>
		<th>Nombre</th>
		<th>Plan</th>
		<th>Residencia</th>
		<th>Dormitorio</th>
	</tr>
	</thead>
	<tbody>
<%
	int cont = 1;
	for(AlumPersonal dato : lisNuevos){	
		
 		String planAlumno = "0";
 		if(mapaPlanAlumno.containsKey(dato.getCodigoPersonal())){
 			planAlumno = mapaPlanAlumno.get(dato.getCodigoPersonal());
 		}
 		
 		String carreraAlumno = "0";
 		if(mapaPlanes.containsKey( planAlumno )){
 			carreraAlumno = mapaPlanes.get( planAlumno ).getCarreraId();
 		}		
 		
 		boolean muestraTodos = true;
 		
 		String residencia = "Externo";
 		String dormitorio = "";
 		
 		if(mapDormi.containsKey(dato.getCodigoPersonal())){
 			residencia = "Interno";
 			dormitorio = mapDormi.get(dato.getCodigoPersonal());
 		}

 		if (acceso.getAdministrador().equals("S") || acceso.getAccesos().contains(carreraAlumno) || intAcceso.getRol().equals("P")){
 			if(intAcceso.getRol().equals("P") && !mapDormi.containsKey(dato.getCodigoPersonal())){
 				muestraTodos = false; 
 			}
 			
			if(muestraTodos){
%>
		 
	<tr>
		<td><%=cont++%></td>
		<td><%=dato.getCodigoPersonal()%></td>
		<td><%=dato.getNombreLegal()%></td>
		<td><%=planAlumno%></td>
		<td><%=residencia%></td>
		<td><%=dormitorio%></td>
	</tr>	
<%
 			}
 		}
	}
%>
	</tbody>
	</table>	
</div>
<link rel="stylesheet" href="../../js/tablesorter/themes/blue/style.css"/>
<script src="../../js/tablesorter/jquery.tablesorter.js"></script>
<script src="../../js/search.js"></script>
<script>
	jQuery('#table').tablesorter();

	jQuery('#buscar').search({
		table: jQuery("#table")}
	);
</script>