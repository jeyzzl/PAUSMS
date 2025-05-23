<%@ page import="java.text.*"%>
<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.vista.spring.CargaAcademica"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.text.*"%>

<!-- inicio de estructura -->
<%
	DecimalFormat getFormato	= new DecimalFormat("###,##0.00;(###,##0.00)");

	String facultadId 		= request.getParameter("facultadId")==null?"0":request.getParameter("facultadId");
	String cargaId			= request.getParameter("cargaId")==null?"0":request.getParameter("cargaId");
	
	String facultadNombre	= (String) request.getAttribute("facultadNombre");	
	int totAlumnos			= 0;
	int totReprobados		= 0;	
	
	List<CargaAcademica> lisMaterias				= (List<CargaAcademica>) request.getAttribute("lisMaterias");
	HashMap<String,String> mapaAlumnos				= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapaReprobados			= (HashMap<String,String>) request.getAttribute("mapaReprobados");
%>
<div class="container-fluid">
	<h2>Reprobados por materias <small class="text-muted fs-4"> (<%=facultadNombre%>)</small></h2>
	<form id="noayuda" name="forma" action="listadoMaterias" method='post'>
		<div class="alert alert-info">
			<a href="materias" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	  	</div>
	</form>
	<table class="table table-bordered">
	<thead class="table-info">
		<tr>
			<th>#</th>
			<th>Materia</th>
			<th>Profesor</th>
			<th style="text-align: right">Alumnos</th>
			<th style="text-align: right">Reprobados</th>
			<th style="text-align: right">% Reprobados</th>
		</tr>
	</thead>
				<%
	int row = 0;
	for (CargaAcademica materia : lisMaterias){
		row++;
		
		String totalAlumnos 	= "0";
		String totalReprobados 	= "0";
		
		if(mapaAlumnos.containsKey(materia.getCursoCargaId())){
			totalAlumnos 		= mapaAlumnos.get(materia.getCursoCargaId());
			totAlumnos 			= totAlumnos + Integer.parseInt(totalAlumnos);
		}
		
		if(mapaReprobados.containsKey(materia.getCursoCargaId())){
			totalReprobados 	= mapaReprobados.get(materia.getCursoCargaId());
			totReprobados 		= totReprobados + Integer.parseInt(totalReprobados);
		}
		
		float porRep = (Float.parseFloat(totalReprobados)*100)/Float.parseFloat(totalAlumnos);
%>
		<tr>
			<td><%=row%></td>
			<td><%=materia.getNombreCurso()%></td>
			<td><%=materia.getNombre()%></td>
			<td style="text-align: right"><%= totalAlumnos %></td>
			<td style="text-align: right"><%= totalReprobados %></td>
			<td style="text-align: right"><%= getFormato.format(porRep) %></td>
		</tr>
		<%		
	}
	
		float porTot = (float)(totReprobados*100)/totAlumnos;
%>
		<tr>
			<td colspan="3">T O T A L E S</td>
			<td style="text-align: right"><%=totAlumnos%></td>
			<td style="text-align: right"><%=totReprobados%></td>
			<td style="text-align: right"><%=getFormato.format(porTot)%></td>
		</tr>
	</table>
</div>