<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.financiero.spring.FesCcobro"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp"%>

<html>
<%
	java.text.DecimalFormat getFormato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String cargaId 				= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
	String bloques		 		= (String) request.getAttribute("bloques");
	
	List<CargaAlumno> lisTotal 						= (List<CargaAlumno>) request.getAttribute("lisTotal");
	HashMap<String,MapaPlan> mapaPlanes				= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String,String> mapaAlumnos				= (HashMap<String,String>) request.getAttribute("mapaAlumnos");	
	HashMap<String,CargaBloque> mapaBloques			= (HashMap<String,CargaBloque>) request.getAttribute("mapaBloques");
	HashMap<String,String> mapaPagos				= (HashMap<String,String>) request.getAttribute("mapaPagos");
	HashMap<String,String> mapaMaterias				= (HashMap<String,String>) request.getAttribute("mapaMaterias");
	HashMap<String, AlumAcademico> mapaEnCarga		= (HashMap<String,AlumAcademico>) request.getAttribute("mapaEnCarga");
	HashMap<String, CatFacultad> mapaFacultades		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String, CatCarrera> mapaCarreras		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,String> mapaEdades				= (HashMap<String,String>) request.getAttribute("mapaEdades");
%>
<body>
<div class="container-fluid">
	<h2>Students in Enrollment Process</h2>	
	<div class="alert alert-info">
		<a href="listado?CargaId=<%=cargaId%>" class="btn btn-primary">Return</a>&nbsp;&nbsp;
	</div>
	<table class="table table-sm table-bordered">
	<thead>
		<tr class="noHover">
			<th>#</th>
			<th>Student ID</th>
			<th>Name</th>
			<th>Age</th>
			<th>School (Short)</th>
			<th>Plan</th>
			<th>Course</th>
			<th>Block</th>			
			<th>Subjects</th>
			<th>Residence</th>
			<th>Location</th>
		</tr>
	</thead>
	<tbody>	
<% 		
	int row=0;
	for(CargaAlumno carga : lisTotal){
		row++;
		
		String planNombre 	= "<span class='badge bg-secondary'>0</span>";
		String carreraId	= "0";
		if(mapaPlanes.containsKey(carga.getPlanId())){
			planNombre 		= mapaPlanes.get(carga.getPlanId()).getCarreraSe();
			carreraId		= mapaPlanes.get(carga.getPlanId()).getCarreraId();
		}
		
		String facultadId		= "0";
		String facultadNombre 	= "-";
		if (mapaCarreras.containsKey(carreraId)){
			facultadId 		= mapaCarreras.get(carreraId).getFacultadId();
			facultadNombre 	= mapaFacultades.get(facultadId).getNombreCorto();
		}
		
		String alumnoNombre 	= "<span class='badge bg-secondary'>0</span>";		
		if(mapaAlumnos.containsKey(carga.getCodigoPersonal())){
			alumnoNombre 		= mapaAlumnos.get(carga.getCodigoPersonal());
		}
		
		String edad	= "0";
		if(mapaEdades.containsKey(carga.getCodigoPersonal())){
			edad 		= mapaEdades.get(carga.getCodigoPersonal());
		}
		
		String bloqueNombre 	= "-";
		if(mapaBloques.containsKey(carga.getCargaId()+carga.getBloqueId())){
			bloqueNombre 		= mapaBloques.get(carga.getCargaId()+carga.getBloqueId()).getNombreBloque();
		}
		
		String materias = "<span class='badge bg-warning'>0</span>";
		if(mapaMaterias.containsKey(carga.getCodigoPersonal()+carga.getBloqueId())){
			materias = "<span class='badge bg-success'>"+mapaMaterias.get(carga.getCodigoPersonal()+carga.getBloqueId())+"</span>";
		}
		
		String residencia = "Boarding";
		if(mapaEnCarga.containsKey(carga.getCodigoPersonal())){
			if(mapaEnCarga.get(carga.getCodigoPersonal()).getResidenciaId().equals("E")){
				residencia = "Day Student";
			}
		}
%>
		<tr>
			<td><%=row%></td>			
			<td><%=carga.getCodigoPersonal()%></td>
			<td><%=alumnoNombre%></td>
			<td><%=edad%></td>
			<td><%=facultadNombre%></td>
			<td><%=carga.getPlanId()%></td>
			<td><%=planNombre%></td>
			<td><%=carga.getBloqueId()%>-<%=bloqueNombre%></td>
			<td><%=materias%></td>
			<td><%=residencia%></td>
			<td><%=carga.getModo().equals("V")?"Online":"Face to Face"%></td>
		</tr>
<% 		}%>	
	</tbody>
	</table>	
</div>
</body>
<script type="text/javascript">
	function Actualizar(){		
		document.frmProceso.submit();
	}
</script>
</html>
