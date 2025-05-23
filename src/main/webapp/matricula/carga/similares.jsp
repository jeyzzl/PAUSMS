<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.vista.spring.CargaAcademica"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<head>
<script>
	function recargar(){
		document.frmCarga.submit();
	}	
</script>
<body>
<%	
	String cargaId				= (String)session.getAttribute("cargaId");
	String materiaNombre 		= (String)request.getAttribute("materiaNombre");
	String cursoId 				= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
	String bloqueId 			= (String)request.getAttribute("bloqueId");
	String bloqueNombre			= (String)request.getAttribute("bloqueNombre");
	
	AlumAcademico academico		= (AlumAcademico)request.getAttribute("academico");
	
	List<CargaAcademica> lisMaterias 				= (List<CargaAcademica>)request.getAttribute("lisMaterias");
	HashMap<String,CatModalidad> mapaModalidades 	= (HashMap<String,CatModalidad>)request.getAttribute("mapaModalidades");
	HashMap<String, CargaBloque> mapaBloques 		= (HashMap<String,CargaBloque>)request.getAttribute("mapaBloques");
%>
<div class="container-fluid">
	<h3>Registered Subjects<small> ( <%=cursoId%> - <%=materiaNombre%> | <b>Block:</b> <%=bloqueId%> - <%=bloqueNombre%> <b>Modality/Student:</b><%=academico.getModalidadId()%>])</small></h3>
	<div class="alert alert-info">
	</div>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>#</th>
				<th>Record</th>
				<th>Group</th>
				<th>Professor</th>
				<th>Block</th>
				<th>Modality</th>
				<th>Assistance</th>	
				<th>Schedule</th>
				<th>Class Room</th>
			</tr>
		</thead>
		<tbody>
<%	int row = 0;
	for (CargaAcademica carga : lisMaterias){
		row++;
		
		String modalidadNombre = "-";
		if (mapaModalidades.containsKey(carga.getModalidadId())){
			modalidadNombre = mapaModalidades.get(carga.getModalidadId()).getNombreModalidad();
		}
	
		String modo = "In Person";
		if (carga.getModo().equals("R")) modo = "Mixed"; 
		if (carga.getModo().equals("H")) modo = "Hybrid";
		if (carga.getModo().equals("V")) modo = "Virtual";
		
		String salon = carga.getSalon().equals("S")?"Required":"Optional";
		if (carga.getModo().equals("V")) salon = "Optional";
		
		String bloque = "";
		if (mapaBloques.containsKey(carga.getCargaId()+carga.getBloqueId())){
			bloque = carga.getBloqueId()+" - "+mapaBloques.get(carga.getCargaId()+carga.getBloqueId()).getNombreBloque();
			if(!carga.getBloqueId().equals(bloqueId)){
				bloque = "<span class='badge badge-warning'>"+bloque+"</span>";
			}
		}
		
		if(!carga.getModalidadId().equals(academico.getModalidadId())){
			modalidadNombre = "<span class='badge badge-warning'>"+modalidadNombre+"</span>";
		}
%>	
		<tr>
			<td><%=row%></td>
			<td><%=carga.getCursoCargaId()%></td>
			<td><%=carga.getGrupo()%></td>
			<td><%=carga.getNombre()%></td>
			<td><%=bloque%></td>
			<td><%=modalidadNombre%></td>
			<td><%=modo%></td>	
			<td><%=carga.getHorario().equals("S")?"Required":"Optional"%></td>
			<td><%=salon%></td>		
		</tr>
<%	} %>	
		</tbody>
	</table>
</div>
</body>
</html>