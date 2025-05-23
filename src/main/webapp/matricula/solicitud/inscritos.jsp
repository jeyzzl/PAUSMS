<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.alumno.spring.AlumEstado"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp"%>

<html>
<%
	String cargaId			= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
	String facultadId		= request.getParameter("FacultadId") == null ? "0" : request.getParameter("FacultadId");
	String facultadNombre 	= (String) request.getAttribute("facultadNombre");
	String bloques		 	= (String) request.getAttribute("bloques");
	
	List<CargaAlumno> lisInscritos 					= (List<CargaAlumno>) request.getAttribute("lisInscritos");
	HashMap<String,MapaPlan> mapaPlanes				= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String,String> mapaAlumnos				= (HashMap<String,String>) request.getAttribute("mapaAlumnos");	
	HashMap<String,CargaBloque> mapaBloques			= (HashMap<String,CargaBloque>) request.getAttribute("mapaBloques");
	HashMap<String,AlumEstado> mapaInscritos		= (HashMap<String,AlumEstado>) request.getAttribute("mapaInscritos");
	HashMap<String, String> mapaMateriasConfirmadas	= (HashMap<String,String>) request.getAttribute("mapaMateriasConfirmadas");
	HashMap<String, String> mapaCreditosConfirmadas	= (HashMap<String,String>) request.getAttribute("mapaCreditosConfirmadas");
%>
<body>
<div class="container-fluid">
	<h2>Enrolled Loads<small class="text-muted fs-5">&nbsp;( <%=facultadNombre%> )</small></h2>
	<form name="frmProceso" action="inscritos" method="post">	
	<input type="hidden" name="CargaId" value="<%=cargaId%>">
	<input type="hidden" name="FacultadId" value="<%=facultadId%>">
	<div class="alert alert-info">
		<a href="listado?CargaId=<%=cargaId%>" class="btn btn-primary">Return</a>&nbsp;&nbsp;		
		<a href="materias?CargaId=<%=cargaId%>&FacultadId=<%=facultadId%>&Bloques=<%=bloques%>" class="btn btn-primary" type="submit"><i class="fas fa-sync-alt"></i></a>    
	</div>	
	</form>
	<table class="table table-sm table-bordered">
	<thead>
		<tr class="noHover">
			<th>#</th>
			<th>Student ID</th>
			<th>Name</th>			
			<th>Plan</th>	
			<th>Course</th>
			<th>Subjects</th>
			<th>Credits</th>
			<th>Block</th>
			<th>Date</th>
			<th>Location</th>
		</tr>
	</thead>
	<tbody>	
<% 		
	int row=0;
	for(CargaAlumno carga : lisInscritos){
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
		
		String fecha = "-";
		if (mapaInscritos.containsKey(carga.getCodigoPersonal()+carga.getBloqueId())){
			fecha = mapaInscritos.get(carga.getCodigoPersonal()+carga.getBloqueId()).getFecha();
		}
		
		String confiramdas = "0";
		if(mapaMateriasConfirmadas.containsKey(carga.getCodigoPersonal()+carga.getBloqueId())){
			confiramdas = mapaMateriasConfirmadas.get(carga.getCodigoPersonal()+carga.getBloqueId());
		}

		String creditos = "0";
		if(mapaCreditosConfirmadas.containsKey(carga.getCodigoPersonal()+carga.getBloqueId())){
			creditos = mapaCreditosConfirmadas.get(carga.getCodigoPersonal()+carga.getBloqueId());
		}
%>
		<tr>
			<td><%=row%></td>
			<td><%=carga.getCodigoPersonal()%></td>
			<td><%=alumnoNombre%></td>			
			<td><%=carga.getPlanId()%></td>
			<td><%=planNombre%></td>
			<td><%=confiramdas%>
			</td>
			<td><%=creditos%></td>
			<td><%=carga.getBloqueId()%>-<%=bloqueNombre%></td>
			<td><%=fecha%></td>
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
