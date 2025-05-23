<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.vista.spring.CargaAcademica"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ include file="../alumno/menu.jsp"%>
<html>
<head>
<script>
	function recargar(){
		document.frmOferta.submit();
	}	
</script>
<body>
<%		
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");
	String planId				= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
	String origen				= request.getParameter("Origen")==null?"0":request.getParameter("Origen");
	String cicloId				= request.getParameter("CicloId")==null?"0":request.getParameter("CicloId");
	String planNombre			= (String)request.getAttribute("planNombre");
	String alumnoNombre			= (String)request.getAttribute("alumnoNombre");
	AlumAcademico academico		= (AlumAcademico)request.getAttribute("academico");
	
	List<String> lisCiclos	 						= (List<String>)request.getAttribute("lisCiclos");
	List<CargaAcademica> lisMaterias 				= (List<CargaAcademica>)request.getAttribute("lisMaterias");
	HashMap<String,CatModalidad> mapaModalidades 	= (HashMap<String,CatModalidad>)request.getAttribute("mapaModalidades");
	HashMap<String, CargaBloque> mapaBloques 		= (HashMap<String,CargaBloque>)request.getAttribute("mapaBloques");
	HashMap<String, String> mapaAlumnoCurso 		= (HashMap<String,String>)request.getAttribute("mapaAlumnoCurso");
%>
<div class="container-fluid">
	<h5>Subjects in Course <small class="text-muted"> ( <b><%=codigoAlumno%></b> - <%=alumnoNombre%> - <b>Modality:</b> <%=mapaModalidades.get(academico.getModalidadId()).getNombreModalidad() %> )</small></h5>
	<form name="frmOferta" action="oferta" method="post">
	<input type="hidden" name="PlanId" value="<%=planId%>"/>
	<input type="hidden" name="Origen" value="<%=origen%>"/>	
	<div class="alert alert-info d-flex align-items-center">
		<a href="<%=origen.equals("Planes")?"planes":"calificaciones"%>" class="btn btn-primary">Return</a>&nbsp;&nbsp;&nbsp;&nbsp;
		Semester:&nbsp;&nbsp; 
		<select name="CicloId" onchange="javascript:recargar();"  class="form-select" style="width:150px">
			<option value="0">All</option>
<% 	for (String ciclo : lisCiclos){%>
			<option value="<%=ciclo%>" <%=ciclo.equals(cicloId)?"selected":""%>><%=ciclo%></option>
<%	}%>			
		</select>
	</div>
	</form>
	<span><em>Subjects in Academic Plan</em> <b><%=planId%> - <%=planNombre%></b></span>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>#</th>
				<th>Semester</th>
				<th>Subject</th>				
				<th>Start Date</th>
				<th>End Date</th>
				<th>Group</th>
				<th>Credited</th>
				<th>Lecturer</th>
				<th>Block</th>
				<th>Modality</th>
				<th>Attendance</th>	
<!-- 				<th>Timetable</th> -->
<!-- 				<th>Classroom</th> -->
			</tr>
		</thead>
		<tbody>
<%	int row = 0;
	for (CargaAcademica carga : lisMaterias){
		row++;
		
		if (cicloId.equals("0") || carga.getCiclo().equals(cicloId) ){
			String modalidadNombre = "-";
			String acreditado = "";
			if (mapaModalidades.containsKey(carga.getModalidadId())){
				modalidadNombre = mapaModalidades.get(carga.getModalidadId()).getNombreModalidad();
			}
		
			String modo = "On Campus";
			if (carga.getModo().equals("R")) modo = "Mixed"; 
			if (carga.getModo().equals("H")) modo = "Hybrid";
			if (carga.getModo().equals("V")) modo = "Online";
			
			String salon = carga.getSalon().equals("S")?"Required":"Optional";
			if (carga.getModo().equals("V")) salon = "Optional";
			
			String bloque = "";
			if (mapaBloques.containsKey(carga.getCargaId()+carga. getBloqueId())){
				bloque = mapaBloques.get(carga.getCargaId()+carga.getBloqueId()).getNombreBloque();			
			}
			
			if(!carga.getModalidadId().equals(academico.getModalidadId())){
				modalidadNombre = "<span class='badge bg-warning'>"+modalidadNombre+"</span>";
			}
			
			if(mapaAlumnoCurso.containsKey(carga.getCursoId())){
				acreditado = "<span class='badge bg-success'>Yes</span>";
				
			}else{
				acreditado = "<span class='badge bg-warning text-dark'>No</span>";
			}
%>	
		<tr>
			<td><%=row%></td>
			<td><h6><%=carga.getCiclo()%></h6></td>			
			<td><%=carga.getNombreCurso()%></td>			
			<td><%=carga.getfInicio()%></td>
			<td><%=carga.getfFinal()%></td>
			<td><%=carga.getGrupo()%></td>
			<td><%=acreditado %></td>
			<td><%=carga.getNombre().equals("VACIO")?"<span class='badge bg-secondary'>Not assigned</span>":carga.getNombre() %></td>
			<td><%=bloque%></td>
			<td><%=modalidadNombre%></td>
			<td><%=modo%></td>	
<%-- 			<td><%=carga.getHorario().equals("S")?"Required":"Optional"%></td> --%>
<%-- 			<td><%=salon%></td>		 --%>
		</tr>
<%	
		}
	}
%>	
		</tbody>
	</table>
</div>
</body>
</html>