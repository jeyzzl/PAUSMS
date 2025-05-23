<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.admision.spring.AdmSolicitud"%>
<%@page import="aca.admision.spring.AdmUsuario"%>
<%@page import="aca.admision.spring.AdmPrueba"%>
<%@page import="aca.admision.spring.AdmPruebaAlumno"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%  
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
	String pruebaId				= (String) request.getAttribute("pruebaId");
	
	List<AdmPrueba>	listaTipos						= (List<AdmPrueba>) request.getAttribute("listaTipos");
	List<AdmPruebaAlumno> listaAlumnos				= (List<AdmPruebaAlumno>) request.getAttribute("listaAlumnos");	
	HashMap<String, AdmSolicitud> mapaSolicitudes	= (HashMap<String, AdmSolicitud>) request.getAttribute("mapaSolicitudes");	
	HashMap<String, AdmUsuario> mapaUsuarios		= (HashMap<String, AdmUsuario>) request.getAttribute("mapaUsuarios");
	int cont = 1;
%>
<html>
<head>	

<script type="text/javascript">
	function cambioPrueba(){	
		//var pruebaId = document.getElementById("PruebaId").value;
		document.frmPrueba.submit();
	}

	function borrar(folio){	
		if (confirm("¿Estas seguro de borrar este registro?")){
			document.location.href="borrar?Folio="+folio;
		}	
	}
</script>
</head>
<body>	
<div class="container-fluid">
	<h2>Alumnos TPT</h2>
	<form name="frmPrueba" action="lista" method="POST">
	<div class="alert alert-primary d-flex align-items-center" role="alert">
		Tipo&nbsp;
		<select class="form-select" name="PruebaId" id="PruebaId" onchange="javascript:cambioPrueba();" style="width:500px">
<% 		for(AdmPrueba prueba : listaTipos){%>
			<option value="<%=prueba.getPruebaId()%>" <%=pruebaId.equals(prueba.getPruebaId()) ? "selected" : ""%>><%=prueba.getNombre()%></option>
<% 		}%>
		</select>&nbsp;&nbsp;
		<input type="text" class="form-control search-query" placeholder="Buscar" id="buscar" style="width:200px;">
	</div>
	<div class="alert alert-success d-flex align-items-center">
		Mariculas:&nbsp;
		<textarea class="form-control" name="Folios" rows="1" cols="200" style="width:300px"></textarea>&nbsp;&nbsp;
		<button class="btn btn-primary" type="submit">Grabar</button>
	</div>
	</form>		
	<table id="table" class="table table-sm table-bordered">
	<thead>
	<tr>
		<th>Op.</th>
		<th>No.</th>
		<th>Matrícula</th>
		<th>Nombre</th>
		<th>Fecha</th>
		<th>Usuario</th>
	</tr>
	</thead>
	<tbody>
<%	
	for(AdmPruebaAlumno alumno : listaAlumnos){
		String usuarioId	= "0";
		String alumnoNombre = "-";		
		if(mapaSolicitudes.containsKey(alumno.getFolio())){
			usuarioId = mapaSolicitudes.get(alumno.getFolio()).getUsuarioId();
			if (mapaUsuarios.containsKey(usuarioId)){
				alumnoNombre = mapaUsuarios.get(usuarioId).getNombre()+" "+mapaUsuarios.get(usuarioId).getApellidoPaterno()+" "+mapaUsuarios.get(usuarioId).getApellidoMaterno();
			}
		}
%>
		<tr>
		   <td>	
<% 				if(codigoPersonal.equals("9800308") || codigoPersonal.equals("9801208")){ %>
				<a href="javascript:borrar('<%=alumno.getFolio()%>');" title="Borrar"><i class="fas fa-times fa-2x" style="color:red"></i></a>&nbsp;
<%				}%>
		   </td>
			<td><%=cont++%></td>
			<td>
				<%=alumno.getFolio()%>
			</td>
			<td>
				<%=alumnoNombre%>
			</td>
			<td>
				<%=alumno.getFecha()%>
			</td>
			<td>
				<%=alumno.getUsuario()%>
			</td>
		</tr>
<%	}%>
	</tbody>
	</table>
</div>
</body>
</html>
<script src="../../js/search.js"></script>
<script>
	$('#buscar').search();
</script>