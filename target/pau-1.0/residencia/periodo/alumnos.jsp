<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.residencia.spring.ResAlumno"%>
<%@page import="aca.catalogo.spring.ResRazones"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<head></head>	
<%	
	String periodoId 				= (String) request.getAttribute("periodoId");
	String nombrePeriodo 			= (String) request.getAttribute("nombrePeriodo");

	String mensaje 					= (String) request.getAttribute("mensaje");
	String grabados		 			= (String) request.getAttribute("grabados");
	String noGrabados	 			= (String) request.getAttribute("noGrabados");

	List<ResAlumno> lisAlumnos			= (List<ResAlumno>)request.getAttribute("lisAlumnos");
	HashMap<String,String> mapaAlumnos	= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapaRazones	= (HashMap<String,String>)request.getAttribute("mapaRazones");
	HashMap<String,String> mapaActual	= (HashMap<String,String>)request.getAttribute("mapaActualizados");
%>
<body>
<div class="container-fluid">
	<h2>Student List <small class="text-muted fs-4">( <%=nombrePeriodo%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="listado"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		<a class="btn btn-success" href="actualizarResidencia?PeriodoId=<%=periodoId%>">Update residence</a>&nbsp;&nbsp;
		<input type="text" class="input-medium search-query" placeholder="Search..." id="buscar">
	</div>
<%  if(mensaje.equals("1")){%>
	<div class="alert alert-success">
		<%=grabados%> saved. <%=noGrabados%> missing.
	</div>
<%  }%>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">
	<tr>
		<th width="3%">#</th>					
		<th width="5%">Student ID</th>
		<th width="30%">Name</th>		
		<th width="5%">Start Date</th>			
		<th width="5%">End Date</th>
		<th width="5%">Residence</th>
		<th width="5%">Dorm.</th>
		<th width="12%">Reason</th>
		<th width="5%">User</th>
	</tr>		
	</thead>		
<%
	int row = 0;
	for (ResAlumno alumno : lisAlumnos){
		row++;
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(alumno.getMatricula())){
			alumnoNombre 	= mapaAlumnos.get(alumno.getMatricula());
		}
		String razonNombre = "-";
		if(mapaRazones.containsKey(alumno.getRazon())){
			razonNombre = mapaRazones.get(alumno.getRazon());
		}
		
		String etiquetaRow = "<span class='badge bg-warning'>"+row+"</span>";
		if (mapaActual.containsKey(alumno.getMatricula())){
			etiquetaRow = "<span class='badge bg-dark'>"+row+"</span>";
		}			
%>
		<tr>
			<td><%=etiquetaRow%></td>
			<td><%=alumno.getMatricula() %></td>
			<td><%=alumnoNombre%></td>			
			<td><%=alumno.getFechaInicio()%></td>
			<td><%=alumno.getFechaFinal()%></td>
			<td><%=alumno.getResidenciaId().equals("E")?"Off-campus":"Dormitory" %></td>
			<td><%=alumno.getDormitorio()%></td>
			<td><%=razonNombre%></td>
			<td><%=alumno.getUsuario()%></td>
		</tr>
		<%
	}	
%>
	</table>
</div>
</body>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#table")});
	
</script>
