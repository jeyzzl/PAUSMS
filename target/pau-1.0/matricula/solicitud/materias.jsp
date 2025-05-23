<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.carga.spring.CargaPracticaAlumno"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp"%>

<html>
<%
	String cargaId 			= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
	String facultadId 		= request.getParameter("FacultadId") == null ? "0" : request.getParameter("FacultadId");	
	String facultadNombre 	= (String) request.getAttribute("facultadNombre");
	String bloques		 	= (String) request.getAttribute("bloques");
	
	List<CargaAlumno> lisMaterias 					= (List<CargaAlumno>) request.getAttribute("lisMaterias");	
	
	HashMap<String,MapaPlan> mapaPlanes				= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String,String> mapaAlumnos				= (HashMap<String,String>) request.getAttribute("mapaAlumnos");	
	HashMap<String,CargaBloque> mapaBloques			= (HashMap<String,CargaBloque>) request.getAttribute("mapaBloques");
	HashMap<String,String> mapaMaterias				= (HashMap<String,String>) request.getAttribute("mapaMaterias");		
	HashMap<String,String> mapaCreditosConfirmadas	= (HashMap<String,String>) request.getAttribute("mapaCreditosConfirmadas");		
	HashMap<String,String> mapaPagares				= (HashMap<String,String>) request.getAttribute("mapaPagares");		
%>
<body>
<div class="container-fluid">
	<h2>Subject Loads<small class="text-muted fs-4"> &nbsp;( <%=facultadNombre%> )</small></h2>
	<form name="frmProceso" action="materias" method="post">	
	<input type="hidden" name="CargaId" value="<%=cargaId%>">
	<input type="hidden" name="FacultadId" value="<%=facultadId%>">
	<div class="alert alert-info">
		<a href="listado?CargaId=<%=cargaId%>" class="btn btn-primary">Return</a>&nbsp;&nbsp;		
		<a href="materias?CargaId=<%=cargaId%>&FacultadId=<%=facultadId%>&Bloques=<%=bloques%>" class="btn btn-primary" type="submit"><i class="fas fa-sync-alt"></i></a>    
	</div>	
	</form>
	<table  class="table table-sm table-bordered">
	<thead class="table-info">
		<tr class="noHover">
			<th>#</th>
			<th>Student ID</th>
			<th>Name</th>			
			<th>Plan</th>	
			<th>Course</th>
			<th>Block</th>
			<th>Subjects</th>	
			<th>Confirmed</th>	
			<th>Credits</th>	
<!-- 			<th>Payed</th>	 -->
			<th>Location</th>	
		</tr>
	</thead>
	<tbody>	
<% 		
	int row=0;
	for(CargaAlumno carga : lisMaterias){
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
		
		String materias = "<span class='badge bg-warning'>0</span>";
		if(mapaMaterias.containsKey(carga.getCodigoPersonal()+carga.getBloqueId())){
			materias = "<span class='badge bg-success'>"+mapaMaterias.get(carga.getCodigoPersonal()+carga.getBloqueId())+"</span>";
		}	 

		String confirmadas = "<span class='badge bg-warning text-dark'>No</span>";
		if(carga.getMat().equals("S")){
			confirmadas = "<span class='badge bg-success'>Yes</span>";
		}
		
		String creditos = "0";
		if(mapaCreditosConfirmadas.containsKey(carga.getCodigoPersonal()+carga.getBloqueId())){
			creditos = mapaCreditosConfirmadas.get(carga.getCodigoPersonal()+carga.getBloqueId());
		}
		
		String pagares = "0";
		if(mapaPagares.containsKey(carga.getCodigoPersonal()+carga.getCargaId()+carga.getBloqueId())){
			pagares = mapaPagares.get(carga.getCodigoPersonal()+carga.getCargaId()+carga.getBloqueId());
		}
%>
		<tr>
			<td><%=row%></td>
			<td><%=carga.getCodigoPersonal()%></td>
			<td><%=alumnoNombre%></td>			
			<td align="center"><%=carga.getPlanId()%></td>
			<td><%=planNombre%></td>
			<td><%=carga.getBloqueId()%>-<%=bloqueNombre%></td>
			<td align="center"><%=materias%></td>	
			<td align="center"><%=confirmadas%></td>	
			<td align="center"><%=creditos%></td>	
<%-- 			<td align="center"><%=pagares%></td>	 --%>
			<td align="center"><%=carga.getModo().equals("V")?"Online":"Face to Face"%></td>		
		</tr>
<% 		}%>	
	</tbody>
	</table>	
</div>
</body>
</html>
