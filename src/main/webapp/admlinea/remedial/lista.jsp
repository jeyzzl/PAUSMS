<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.alumno.spring.AlumRemedial"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
	List<String> lista 				= (List<String>) request.getAttribute("lista");	
	
HashMap<String, AlumPersonal> mapaAlumnosAlumnos 	= (HashMap<String, AlumPersonal>) request.getAttribute("mapaAlumnosAlumnos");	
	HashMap<String, String> mapaRemedial 			= (HashMap<String, String>) request.getAttribute("mapaRemedial");
	
	int cont = 1;
%>
<div class="container-fluid">
	<h2>Alumnos en remediales</h2>
	<div class="alert alert-info d-flex align-items-center">
		<a href="editar" class="btn btn-primary"><i class="fas fa-plus"></i></a>&nbsp;&nbsp;
		<input type="text" class="form-control search-query" placeholder="Buscar" id="buscar" style="width:200px;">
	</div>
	<table id="table" class="table table-sm table-bordered table-striped">
		<thead class="table-info">
			<tr> 
				<th>No.</th>
				<th>Opc.</th>
				<th>Matricula</th>
				<th>Nombre</th>
				<th>Razonamiento lógico</th>
				<th>Español</th>
				<th>Razonamiento matemático</th>
				<th>Inglés</th>
		  	</tr>
		</thead>
<%	for (String codigoPersonal : lista){
		String remUno 		= "No";
		String remDos 		= "No";
		String remTres 		= "No";
		String remCuatro 	= "No";
		if(mapaRemedial.containsKey(codigoPersonal+"REMPOD20REME101")){
			if(mapaRemedial.get(codigoPersonal+"REMPOD20REME101").equals("A")){
				remUno = "Si";
			}
		}
		if(mapaRemedial.containsKey(codigoPersonal+"REMPOD20REES104")){
			if(mapaRemedial.get(codigoPersonal+"REMPOD20REES104").equals("A")){
				remDos = "Si";
			}
		}
		if(mapaRemedial.containsKey(codigoPersonal+"REMPOD20REMM102")){
			if(mapaRemedial.get(codigoPersonal+"REMPOD20REMM102").equals("A")){
				remTres = "Si";
			}
		}
		
		if(mapaRemedial.containsKey(codigoPersonal+"REMPOD20REMI103")){
			if(mapaRemedial.get(codigoPersonal+"REMPOD20REMI103").equals("A")){
				remCuatro = "Si";
			}
		}

		AlumPersonal alumno = new AlumPersonal();
		if(mapaAlumnosAlumnos.containsKey(codigoPersonal)){
			alumno = mapaAlumnosAlumnos.get(codigoPersonal);
		}
	%>
		<tbody>
			<tr> 
	   			<td class="left"><%=cont++%></td>    
	   			<td>
	   				<a class="btn btn-primary" title="Editar" href="editar?CodigoPersonal=<%=codigoPersonal%>"><i class="fas fa-edit"></i></a>
	   				<a class="btn btn-danger" title="Eliminar" onclick="borrar('<%=codigoPersonal%>');"><i class="fas fa-times"></i></a>
	   			</td>    
	   			<td class="left"><%=codigoPersonal%></td>  
	   			<td class="left"><%=alumno.getNombreLegal()%></td>  
				<th><%=remUno%></th>
				<th><%=remDos%></th>
				<th><%=remTres%></th>
				<th><%=remCuatro%></th>
	  		</tr>
		</tbody>
<%	}%>
</table>
<script src="../../js/search.js"></script>
<script type="text/javascript">
	$('#buscar').search();
	
	function borrar(codigoPersonal){	
		if(confirm("Quieres borrar este alumno?")){
			document.location.href="borrar?CodigoPersonal="+codigoPersonal;
		}
	}
</script>
</div>
