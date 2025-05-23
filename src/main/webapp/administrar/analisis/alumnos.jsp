<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	java.text.DecimalFormat getFormato= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	String cargaId 				= (String) session.getAttribute("cargaId");
	String carreraId			= request.getParameter("CarreraId")==null?"00000":request.getParameter("CarreraId"); 
	String carreraNombre		= (String)request.getAttribute("carreraNombre");
	
	/* Lista de alumnos */
	List<AlumnoCurso> lisMaterias					= (List<AlumnoCurso>)request.getAttribute("lisMaterias");
	
	HashMap<String, String> mapPuntosEvaluados		= (HashMap<String, String>) request.getAttribute("mapPuntosEvaluados");
	HashMap<String, String> mapPuntosAlumno			= (HashMap<String, String>) request.getAttribute("mapPuntosAlumno");
	HashMap<String, AlumPersonal> mapAlumnos		= (HashMap<String, AlumPersonal>) request.getAttribute("mapAlumnos");
	HashMap<String, String> mapMaestros				= (HashMap<String, String>) request.getAttribute("mapMaestros");
	
%>
<div class="container-fluid">
	<h2>Lista de Alumnos<small class="text-muted fs-4">( <%=carreraId%>-<%=carreraNombre%>)</small></h2>
	<div class="alert alert-info d-flex align-items-center">
		<a href="carrera" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
		&nbsp;
		<input type="text" id="buscar" class="form-control search-query" placeholder="Buscar" style="width:300px;">
	</div>
	<table id="table" class="table table-sm table-bordered table-striped">
	<thead class="table-info">
		<tr>
			<th >#</th>
			<th><spring:message code="aca.Matricula"/></th>
			<th><spring:message code="aca.Alumno"/></th>
			<th>Maestro</th>
			<th style="text-align:left;"><spring:message code="aca.Materia"/></th>
			<th style="text-align:right;">P.Eva.</th>
			<th style="text-align:right;">P.Alum.</th>	
			<th style="text-align:right;">Eficiencia</th>
			<th style="text-align:right;">Máximo</th>
		</tr>
	</thead>
<%
	// Buscar datos
	int cont = 1;
	for(AlumnoCurso materia : lisMaterias){
		
		String alumno = "-";
		if (mapAlumnos.containsKey(materia.getCodigoPersonal())){
			alumno = mapAlumnos.get(materia.getCodigoPersonal()).getNombre()+" "+mapAlumnos.get(materia.getCodigoPersonal()).getApellidoPaterno()+" "+mapAlumnos.get(materia.getCodigoPersonal()).getApellidoMaterno();
		}
		
		String maestro	= "-";
		if (mapMaestros.containsKey(materia.getMaestro())){
			maestro = mapMaestros.get(materia.getMaestro());
		}
		
		String punEval 	= "0";
		String punAlum 	= "0";		
		
		if(mapPuntosEvaluados.containsKey(materia.getCodigoPersonal()+materia.getCursoCargaId())){
			punEval = mapPuntosEvaluados.get(materia.getCodigoPersonal()+materia.getCursoCargaId());
		}
		
		if(mapPuntosAlumno.containsKey(materia.getCodigoPersonal()+materia.getCursoCargaId())){
			punAlum = mapPuntosAlumno.get(materia.getCodigoPersonal()+materia.getCursoCargaId());
		}		
		
		double eficiencia = (Double.valueOf(punAlum)*100) / Double.valueOf(punEval);
		
		if (Double.isNaN(eficiencia)) {
			eficiencia = 0;
		}
		
		double punMax = 100-Double.valueOf(punEval)+Double.valueOf(punAlum);
		
		String color = "";
		
		if(punMax < 70){
			color = "red";
		}else if(punMax > 80){
			color = "#74DF00";
		}else {
			color = "yellow";
		}		
%>
		<tr>
			<td><%=cont %></td>
			<td><a href="portal?CodigoAlumno=<%=materia.getCodigoPersonal()%>" target="_blank"><span class="badge bg-info"><%=materia.getCodigoPersonal()%></span></a></td>
			<td><%=alumno%></td>
			<td><%=materia.getMaestro()%> | <%=maestro%></td>	
			<td style="text-align:left;"><%=materia.getCursoCargaId()%>|<%=materia.getNombreCurso() %></td>		
			<td style="text-align:right;"><%=punEval%></td>
			<td style="text-align:right;"><%=punAlum%></td>
			<td style="text-align:right;"><%=getFormato.format(eficiencia)%></td>
			<td style="text-align:right; background-color: <%=color%>"><strong><%=getFormato.format(punMax)%></strong></td>
		</tr>
	<%
		cont++;
	} 
	%>	
	</table>
</div>
<script src="../../js/search.js"></script>
<script>
	$('#buscar').search();
</script>