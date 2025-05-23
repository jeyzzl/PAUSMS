<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="aca.carga.spring.Carga"%>
<%@ page import="aca.catalogo.spring.CatFacultad"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>
<%@ page import="aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<head>
<script>
	function refrescar(){
		document.frmEficiencia.submit();
	}
</script>
<%
	DecimalFormat getFormato		= new DecimalFormat("###,##0.00;(###,##0.00)");

	String cargaId 					= (String) request.getAttribute("cargaId");
	String cargaNombre				= (String) request.getAttribute("cargaNombre");
	String fechaInicio 				= (String) request.getAttribute("fechaInicio");
	List<aca.Mapa> lisAlumnos 		= (List<aca.Mapa>) request.getAttribute("lisAlumnos");
	
	HashMap<String,String> mapaAlumnos 				= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
	HashMap<String,CatFacultad> mapaFacultades		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras			= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,MapaPlan> mapaPlanes 			= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String,String> mapaGraduados 			= (HashMap<String,String>) request.getAttribute("mapaGraduados");
	HashMap<String,String> mapaEventos 				= (HashMap<String,String>) request.getAttribute("mapaEventos");
%>
</head>
<body>
<div class="container-fluid">
	<h2>Eficiencia Terminal <small class="text-muted fs-5">( <%=cargaId%> - <%=cargaNombre%> )</small></h2>	
	<div class="alert alert-info d-flex align-items-center">		
		<a href="eficiencia?CargaId=<%=cargaId%>" class="btn btn-primary">Regresar</a>
	</div>	
	<table class="table table-bordered">
	<thead>
	<tr>
		<th>#</th>
		<th>Facultad</th>
		<th>Carrera</th>
		<th>Alumno</th>
		<th>Plan</th>
		<th>Ciclos</th>
		<th>¿Graduó?</th>		
		<th class="text-end">Fecha Grad.</th>
		<th class="text-end">Fecha límite</th>
		<th class="text-end">En tiempo</th>
	</tr>	
	</thead>
	<tbody>
<%
	String ciclos 		= "0";	
	java.util.Date dateInicio = new java.util.Date();		 
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
	try {
		dateInicio = sdf.parse(fechaInicio);
	}catch(Exception ex) {
		System.out.println("Error al convertir la fecha 1");
	}
	
	int row = 0;
	for (aca.Mapa objeto : lisAlumnos) {
		row++;
		
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(objeto.getLlave())){
			alumnoNombre = mapaAlumnos.get(objeto.getLlave());
		}
		
		String planId 			= objeto.getValor();
		String planNombre		= "-";
		String carrera			= "";
		String facultad			= "";
		String facultadCorto	= "-";
		if (mapaPlanes.containsKey(planId)){
			carrera 			= mapaPlanes.get(planId).getCarreraId();
			planNombre			= mapaPlanes.get(planId).getCarreraSe();
			if (mapaCarreras.containsKey(carrera)){
				facultad 		= mapaCarreras.get(carrera).getFacultadId();
				facultadCorto	= mapaFacultades.get(facultad).getNombreCorto();
			}
		}
		
		// Obtiene la fecha limite para terminar la carrera de acuerdo al numero de semestres del plan de estudios del alumno
		ciclos 		= mapaPlanes.get(objeto.getValor()).getCiclos();
		int dias 	= Integer.parseInt(ciclos)*30*6+30;
		java.util.Date dateFinal = aca.util.Fecha.sumarDiasAFecha(dateInicio, dias);
		
		String fechaEvento 	= "01/01/2100";
		String enTiempo 	= "<span class='badge bg-secondary rounded-pill'>NO</span>";
		String esGraduado 	= "<span class='badge bg-secondary rounded-pill'>NO</span>";
		if (mapaGraduados.containsKey(objeto.getLlave()+objeto.getValor())){
			
			esGraduado = "<span class='badge bg-success rounded-pill'>SI</span>";
			// Fecha del evento en que graduó el alumno
			String eventoId 	= mapaGraduados.get(objeto.getLlave()+objeto.getValor());
			fechaEvento 		= mapaEventos.get(eventoId);
			java.util.Date dateEvento 	= new java.util.Date();
			try {
				dateEvento 	= sdf.parse(fechaEvento);
			}catch(Exception ex) {
				System.out.println("Error al convertir la fecha 2");
			}
			
			// Si graduó antes de la fecha limite			
			if (dateEvento.before(dateFinal)){
				enTiempo = "<span class='badge bg-success rounded-pill'>SI</span>";
			}
		}		 
%>
	<tr>
		<td><%=row%></td>
		<td><%=facultadCorto%></td>
		<td><%=planNombre%></td>
		<td><%=objeto.getLlave()%> - <%=alumnoNombre%></td>
		<td><%=planId%></td>
		<td><%=ciclos%></td>
		<td><%=esGraduado%></td>		
		<td class="text-end"><%=fechaEvento%></td>
		<td class="text-end"><%=sdf.format(dateFinal)%></td>
		<td class="text-end"><%=enTiempo%></td>
	</tr>
<%		
	}	
%>	
	</tbody>
	</table>
</div>
</body>