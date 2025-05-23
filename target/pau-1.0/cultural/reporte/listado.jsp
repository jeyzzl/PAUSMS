<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.cultural.spring.CompAsistencia"%>
<%@page import="aca.cultural.spring.CompAsistenciaAlumno"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	String eventoId				= (String)request.getAttribute("eventoId");
	String tipo					= (String)request.getAttribute("tipo");

	List<CompAsistencia> lisEventos 			= (List<CompAsistencia>)request.getAttribute("lisEventos");	
	List<CompAsistenciaAlumno> lisAlumnos		= (List<CompAsistenciaAlumno>)request.getAttribute("lisAlumnos");
	
	HashMap<String, CatFacultad> mapFacultades	= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String, CatCarrera> mapCarrera 		= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String, AlumPersonal> mapaAlumnos 	= (HashMap<String,AlumPersonal>)request.getAttribute("mapaAlumnos");
	HashMap<String, String> mapaInscritos		= (HashMap<String,String>)request.getAttribute("mapaInscritos");
	HashMap<String, String> mapCarreraCultural	= (HashMap<String,String>)request.getAttribute("mapCarreraCultural");
%>
<body>
<div class="container-fluid">
	<h2>Eventos Culturales</h2>
	<form name="frmEvento" action="grabar" method="post">
	<div class="alert alert-info d-flex align-items-center">
		Elegir:&nbsp;
		<select id="EventoId" name="EventoId" class="form-select" style="width:400px;" onchange="javascript:Refrescar();">
<%	for (CompAsistencia asistencia : lisEventos){ %>		
			<option value="<%=asistencia.getEventoId()%>" <%=eventoId.equals(asistencia.getEventoId())?"selected":""%>><%=asistencia.getNombre()%> - <%=asistencia.getFecha()%> - <%=asistencia.getHora()%></option>		
<%	} %>		
		</select>
		&nbsp;&nbsp;
		<select id="Tipo" name="Tipo" class="form-select" style="width:170px;" onchange="javascript:Refrescar();">
			<option value="0" <%=tipo.equals("0")?"selected":""%>>Entrada y Salida</option>
			<option value="1" <%=tipo.equals("1")?"selected":""%>>Entrada</option>
			<option value="2" <%=tipo.equals("2")?"selected":""%>>Salida</option>
		</select>
		&nbsp;&nbsp;
		<input type="text" class="form-control search-query" placeholder="Buscar" id="buscar" style="width:150px;">
	</div>
	</form>
	<table class="table table-bordered" id="table">
		<thead>
			<tr class="table-dark">
				<th>#</th>
				<th>Codigo</th>
				<th>Nombre</th>		
				<th>Plan</th>		
				<th>Facultad</th>		
				<th>Carrera</th>		
				<th>Entrada</th>
				<th>Salida</th>		
				<th>¿Inscrito?</th>
			</tr>
		</thead>
		<tbody>
<%
		int row=0;
		for (CompAsistenciaAlumno alumno : lisAlumnos){
			AlumPersonal alumPersonal = new AlumPersonal();
			if(mapaAlumnos.containsKey(alumno.getCodigoPersonal())){
				alumPersonal = mapaAlumnos.get(alumno.getCodigoPersonal());
			}
			String esInscrito = "<span class='badge bg-warning rounded-pill'>NO</span>";
			if (mapaInscritos.containsKey(alumno.getCodigoPersonal())){
				esInscrito = "<span class='badge bg-dark rounded-pill'>SI</span>";
			}
			
			String nombreCarrera 	= "";
			String facultadNombre 	= "-";
			String facultadId		= "0"; 
			String carrera			= "0"; 
			
			if (mapCarreraCultural.containsKey(alumno.getPlanId())){
				carrera = mapCarreraCultural.get(alumno.getPlanId());
			}
			
			if (mapCarrera.containsKey(carrera)){
				nombreCarrera 	= mapCarrera.get(carrera).getNombreCarrera();
				facultadId 		= mapCarrera.get(carrera).getFacultadId();
				if (mapFacultades.containsKey(facultadId)){
					facultadNombre = mapFacultades.get(facultadId).getTitulo()+" de "+mapFacultades.get(facultadId).getNombreFacultad();
				}
			}
%>
			<tr> 
				<td><%=++row%></td>
				<td><%=alumno.getCodigoPersonal()%></td>
				<td><%=alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno()%></td>
				<td><%=alumno.getPlanId()%></td>
				<td><%=facultadNombre%></td>
				<td><%=nombreCarrera%></td>
				<td><%=alumno.getEntradaHora()%></td>
				<td><%=alumno.getSalidaHora()%></td>			
				<td><%=esInscrito%></td>
			</tr>
<%		} %>
		</tbody>
	</table>			
</div>
</body>
<script type="text/javascript">
	function Refrescar(){	
		var eventoId 	= document.getElementById("EventoId").value;
		var tipo	 	= document.getElementById("Tipo").value;
  		document.location.href="listado?EventoId="+eventoId+"&Tipo="+tipo;
	}
	
</script>
</html>
<script src="../../js/search.js"></script>
<script>
	$('#buscar').search();
</script>