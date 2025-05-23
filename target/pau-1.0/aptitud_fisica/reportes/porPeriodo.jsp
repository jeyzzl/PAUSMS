<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.apFisica.spring.ApFisicaAlumno"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function cambiaCarga(){
  		document.location.href="porPeriodo?CargaId="+document.forma.CargaId.value;
	}
</script>

<!-- inicio de estructura -->
<%	
	String cargaId			= (String) request.getAttribute("cargaId");
	int cont 				= 1;		
	
	List<Carga> lisCarga 						= (List<Carga>)request.getAttribute("lisCarga");	
	List<AlumnoCurso> lisAlumnos				= (List<AlumnoCurso>)request.getAttribute("lisAlumnos");
	
	HashMap<String, CatCarrera> mapaCarreras 	= (HashMap<String, CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String, AlumPersonal> mapaAlumnos	= (HashMap<String, AlumPersonal>)request.getAttribute("mapaAlumnos");
 	HashMap<String, ApFisicaAlumno> mapaGrupos  = (HashMap<String, ApFisicaAlumno>)request.getAttribute("mapaGrupos");
 	HashMap<String, String> mapaNobresGrupos	= (HashMap<String, String>)request.getAttribute("mapaNobresGrupos");
%>
<div class="container-fluid">
	<h2>Reporte por carga académica</h2>
	<form name="forma" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<a href="menu" class="btn btn-primary btn-sm rounded-pill"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		<strong>Carga:</strong>&nbsp;
		<select onchange='javascript:cambiaCarga()' name="CargaId" class="form-select" style="width:350px;">
<%	for(Carga carga : lisCarga){ %>
			<option <%=cargaId.equals(carga.getCargaId())?" Selected":""%> value="<%=carga.getCargaId()%>"> 
	        	<%=carga.getCargaId()%> - <%=carga.getNombreCarga()%>
	        </option>
<%	}%>
		</select>&nbsp;&nbsp;
		<input type="text" class="form-control search-query" placeholder="Buscar" id="buscar" style="width:170px">
	</div>
	<table id="table" style="width:100%" class="table table-sm table-striped">
	<thead>
	<tr>
  		<th>#</th>
		<th>Matricula</th>
		<th>Alumno</th>
		<th>Carrera</th>
		<th>Materia</th>
		<th>Grupo</th>
		<th>Estado</th>
  	</tr>
  	</thead>
  	<tbody>
<%	
	for(AlumnoCurso alumno : lisAlumnos ){
		
		String carreraNombre = "-";
		if(mapaCarreras.containsKey(alumno.getCarreraId())){
			carreraNombre = mapaCarreras.get(alumno.getCarreraId()).getNombreCarrera();
		}
		
		String alumnoNombre = "-";
		if(mapaAlumnos.containsKey(alumno.getCodigoPersonal())){
			alumnoNombre = mapaAlumnos.get(alumno.getCodigoPersonal()).getNombre()+", "+mapaAlumnos.get(alumno.getCodigoPersonal()).getApellidoPaterno()+" "+mapaAlumnos.get(alumno.getCodigoPersonal()).getApellidoMaterno();
		}
		
		String estado = "-";
 		String grupo  = "-";
 		if(mapaGrupos.containsKey(alumno.getCodigoPersonal()+alumno.getCursoCargaId())){
 			estado = mapaGrupos.get(alumno.getCodigoPersonal()+alumno.getCursoCargaId()).getEstado();
 			grupo  = mapaGrupos.get(alumno.getCodigoPersonal()+alumno.getCursoCargaId()).getGrupoId(); 
		}
 		
 		String grupoNombre = "-";
 		if(mapaNobresGrupos.containsKey(grupo)){
 			grupoNombre = mapaNobresGrupos.get(grupo); 
 		}				
%>
	  <tr>
		<td><%= cont %></td>
		<td><%= alumno.getCodigoPersonal()%></td>
		<td><%= alumnoNombre%></td>
		<td><%= carreraNombre%></td>
		<td><%= alumno.getNombreCurso()%></td>
		<td><%= grupoNombre%></td>
		<td><%= estado %></td>
	  </tr>
  <%
    	cont++; 
	} // fin del for
%>
	</tbody>
	</table>   
	<div class="alert alert-info"><h3>Total de Alumnos: <%=lisAlumnos.size() %></h3></div>	
	</form>
</div>
<script src="../../js/search.js"></script>
<script>
	$('#buscar').search();	
</script>