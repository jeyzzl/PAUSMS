<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.financiero.spring.FesCcobro"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.alumno.spring.AlumEstado"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp"%>

<html>
<%
	java.text.DecimalFormat getFormato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String cargaId 				= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
	String facultadId 			= request.getParameter("FacultadId") == null ? "0" : request.getParameter("FacultadId");	
	String facultadNombre 		= (String) request.getAttribute("facultadNombre");
	String bloques		 		= (String) request.getAttribute("bloques");
	
	List<CargaAlumno> lisTotal 						= (List<CargaAlumno>) request.getAttribute("lisTotal");
	HashMap<String,MapaPlan> mapaPlanes				= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String,String> mapaAlumnos				= (HashMap<String,String>) request.getAttribute("mapaAlumnos");	
	HashMap<String,CargaBloque> mapaBloques			= (HashMap<String,CargaBloque>) request.getAttribute("mapaBloques");
	HashMap<String,String> mapaPagos				= (HashMap<String,String>) request.getAttribute("mapaPagos");
	HashMap<String,String> mapaMaterias				= (HashMap<String,String>) request.getAttribute("mapaMaterias");
	HashMap<String, AlumAcademico> mapaEnCarga		= (HashMap<String,AlumAcademico>) request.getAttribute("mapaEnCarga");
	HashMap<String, AlumEstado> mapaInscritos		= (HashMap<String,AlumEstado>) request.getAttribute("mapaInscritos");	
%>
<body>
<div class="container-fluid">
	<h2>Students in Closing<small class="text-muted fs-4">( <%=facultadNombre%> )</small></h2>
	<form name="frmProceso" action="pagos" method="post">	
	<input type="hidden" name="CargaId" value="<%=cargaId%>">
	<input type="hidden" name="FacultadId" value="<%=facultadId%>">
	<div class="alert alert-info">
		<a href="listado?CargaId=<%=cargaId%>" class="btn btn-primary">Return</a>&nbsp;&nbsp;		
		<a href="materias?CargaId=<%=cargaId%>&FacultadId=<%=facultadId%>&Bloques=<%=bloques%>" class="btn btn-primary" type="submit"><i class="fas fa-sync-alt"></i></a>    
	</div>	
	</form>
	<table class="table table-fullcondensed table-bordered">
	<thead>
		<tr class="noHover">
			<th>#</th>
			<th>Student ID</th>
			<th>Name</th>			
			<th>Plan</th>	
			<th>Course</th>
			<th>Block</th>
			<th class="right">Down Payment</th>
			<th>Subjects</th>
			<th>Residence</th>
			<th>Location</th>
			<th>Status</th>
		</tr>
	</thead>
	<tbody>	
<% 		
	int row=0;
	for(CargaAlumno carga : lisTotal){
		row++;
		
		String planNombre 	= "<span class='badge bg-secondary'>0</span>";
		if(mapaPlanes.containsKey(carga.getPlanId())){
			planNombre 		= mapaPlanes.get(carga.getPlanId()).getCarreraSe();
		}
		
		String alumnoNombre 	= "<span class='badge bg-secondary'>0</span>";		
		if(mapaAlumnos.containsKey(carga.getCodigoPersonal())){
			alumnoNombre 		= mapaAlumnos.get(carga.getCodigoPersonal());
		}
		
		String bloqueNombre 	= "-";
		if(mapaBloques.containsKey(carga.getCargaId()+carga.getBloqueId())){
			bloqueNombre 		= mapaBloques.get(carga.getCargaId()+carga.getBloqueId()).getNombreBloque();
		}
		
		double pagoInicial		= 0;
		String textoPagoInicial = "<span class='badge bg-warning'>"+getFormato.format(pagoInicial)+"</span>";
		if(mapaPagos.containsKey(carga.getCodigoPersonal()+carga.getBloqueId())){
			pagoInicial 		= Double.valueOf(mapaPagos.get(carga.getCodigoPersonal()+carga.getBloqueId()));
		}
		if (pagoInicial >= 0){
			textoPagoInicial = "<span class='badge bg-success'>"+getFormato.format(pagoInicial)+"</span>";
		}else{
			textoPagoInicial = "<span class='badge bg-warning'>"+getFormato.format(pagoInicial)+"</span>";
		}
		
		String materias = "<span class='badge bg-warning'>0</span>";
		if(mapaMaterias.containsKey(carga.getCodigoPersonal()+carga.getBloqueId())){
			materias = "<span class='badge bg-success'>"+mapaMaterias.get(carga.getCodigoPersonal()+carga.getBloqueId())+"</span>";
		}
		
		String residencia = "Boarding Student";
		if(mapaEnCarga.containsKey(carga.getCodigoPersonal())){
			if(mapaEnCarga.get(carga.getCodigoPersonal()).getResidenciaId().equals("E")){
				residencia = "Day Student";
			}
		}
		String esInscrito = "N";
		if(mapaInscritos.containsKey(carga.getCodigoPersonal()+carga.getBloqueId())){
			esInscrito = mapaInscritos.get(carga.getCodigoPersonal()+carga.getBloqueId()).getEstado();
		}		
%>
		<tr>
			<td><%=row%></td>
			<td><%=carga.getCodigoPersonal()%></td>
			<td><%=alumnoNombre%></td>			
			<td><%=carga.getPlanId()%></td>
			<td><%=planNombre%></td>
			<td><%=carga.getBloqueId()%>-<%=bloqueNombre%></td>
			<td class="right"><%=textoPagoInicial%></td>
			<td><a href="materiasAlumno?CodigoPersonal=<%=carga.getCodigoPersonal()%>&CargaId=<%=cargaId%>&Bloques=<%=bloques%>&FacultadId=<%=facultadId%>"><%=materias%></a></td>
			<td><%=residencia%></td>
			<td><%=carga.getModo().equals("V")?"Online":"Face to Face"%></td>
			<td><%=esInscrito.equals("I")?"Enrolled":"In process"%></td>
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
