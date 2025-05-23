<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.financiero.spring.FinPeriodo"%>
<%@page import="aca.financiero.spring.FinBloqueo"%>
<%@page import="aca.financiero.spring.FinBloqueo"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%  
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String periodoId		= (String) request.getAttribute("periodoId");
	String codigoEmpleado	= (String) request.getAttribute("codigoEmpleado");
	
	List<FinPeriodo> lisPeriodo	= (List<FinPeriodo>) request.getAttribute("lisPeriodo");
	List<FinBloqueo> lisAlumnos = (List<FinBloqueo>) request.getAttribute("lisAlumnos");
	
	HashMap<String, AlumPersonal> mapaAlumnosBloqueados = (HashMap<String, AlumPersonal>) request.getAttribute("mapaAlumnosBloqueados");
	
	int cont = 1;
	AlumPersonal alumPersonal = new AlumPersonal();
%>
<html>
<head>	

<script type="text/javascript">
	function cambioPeriodo(){	
		var periodoId = document.getElementById("PeriodoId").value;
		document.location.href="listado?PeriodoId="+periodoId;
	}

	function borrar(codigoPersonal){	
		var periodoId = document.getElementById("PeriodoId").value;
		if (confirm("¿Estas seguro de borrar este registro?")){
			document.location.href="borrar?PeriodoId="+periodoId+"&CodigoPersonal="+codigoPersonal;
		}	
	}
	function cambiarEstado(codigoPersonal){	
		var periodoId = document.getElementById("PeriodoId").value;
		if (confirm("¿Estas seguro de cambiar estado a este registro?")){
			document.location.href="editar?PeriodoId="+periodoId+"&CodigoPersonal="+codigoPersonal;
		}	
	}
</script>
</head>
<body>	
<div class="container-fluid">
	<h2>Alumnos con candado por deuda</h2>
	<form action="listado" method="get">
	<div class="alert alert-primary d-flex align-items-center" role="alert">
		Periodo de bloqueo&nbsp;
		<select class="form-select" name="PeriodoId" id="PeriodoId" onchange="javascript:cambioPeriodo();" style="width:500px">
<% 		for(FinPeriodo periodo : lisPeriodo){%>
			<option value="<%=periodo.getPeriodoId()%>" <%=periodoId.equals(periodo.getPeriodoId()) ? "selected" : ""%>><%=" del "+periodo.getFechaIni()+" al "+periodo.getFechaFin()%></option>
<% 		}%>
		</select>&nbsp;&nbsp;
		<input type="text" class="form-control search-query" placeholder="Buscar" id="buscar" style="width:200px;">
	</div>
	<div class="alert alert-success d-flex align-items-center">
		Mariculas:&nbsp;
		<textarea class="form-control" name="Codigos" rows="1" cols="200" style="width:300px"></textarea>&nbsp;&nbsp;
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
		<th>Estado</th>
		<th>Fecha</th>
		<th>Usuario</th>
	</tr>
	</thead>
	<tbody>
<%	
	for(FinBloqueo alumno : lisAlumnos){
		alumPersonal = new AlumPersonal();
		if(mapaAlumnosBloqueados.containsKey(alumno.getCodigoPersonal())){
			alumPersonal = mapaAlumnosBloqueados.get(alumno.getCodigoPersonal());
		}
		String estado = "";
		if(alumno.getEstado().equals("I")){
			estado = "<span style='color:green'>Liberado</span>";
		}else {
			estado = "<span style='color:orange'>Bloqueado</span>";
		}
%>
		<tr>
		   <td>	
<% 				if(codigoPersonal.equals("9801156") || codigoPersonal.equals("9800308")){ %>
				<a href="javascript:borrar('<%=alumno.getCodigoPersonal()%>');" title="Borrar"><i class="fas fa-times fa-2x" style="color:red"></i></a>&nbsp;
<%				}%>
<% 				if(alumno.getEstado().equals("I")){ %>
				<a href="javascript:cambiarEstado('<%=alumno.getCodigoPersonal()%>');" title="Activar"><i class="fas fa-toggle-on fa-2x" style="color:green"></i></a>
<%				}else{%>
				<a href="javascript:cambiarEstado('<%=alumno.getCodigoPersonal()%>');" title="Desactivar"><i class="fas fa-toggle-off fa-2x" style="color:orange"></i></a>
<%				}%>
		   </td>
			<td><%=cont++%></td>
			<td>
				<%=alumno.getCodigoPersonal()%>
			</td>
			<td>
				<%=alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno()%>
			</td>
			<td>
				<%=estado%>
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