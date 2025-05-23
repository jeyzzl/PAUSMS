<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ page import= "aca.alumno.spring.AlumPlan"%>

<script>
	function cambio(){
		var year = document.getElementById("SelectYear").value;
		document.location.href="alumnosPorYear?Year="+year;
	}
</script>

<%
	java.text.DecimalFormat getFormato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String codigo		= (String) session.getAttribute("codigoPersonal");
	String planId		= (String)request.getAttribute("planId");
	String creditosPlan = (String)request.getAttribute("creditosPlan");
	int yearActual 		= Integer.parseInt((String)request.getAttribute("yearActual"));
	int year 			= Integer.parseInt((String)request.getAttribute("year"));
	int cont			= 1;
	/* Lista de planes de estudio*/
	List<AlumPlan> lisAlumnos 		= (List<AlumPlan>)request.getAttribute("lisAlumnos");
	
	/* HashMap de los nombres de los alumnos */
	HashMap<String,String> mapAlumnos 	= (HashMap<String,String>)request.getAttribute("mapAlumnos");
	
	/* HashMap de los créditos de los alumnos en el plan */
	HashMap<String,String> mapCreditos 	= (HashMap<String,String>)request.getAttribute("mapCreditos");
	
	/* HashMap de la última fecha de inscripción de los alumnos */
	HashMap<String,String> mapUltimaFecha	= (HashMap<String,String>)request.getAttribute("mapUltimaFecha");
%>
<div class="container-fluid">
<h2>Alumnos del año <%=yearActual%></h2>
<div class="alert alert-info">
	<div class="row">
		<div class="col-1">
			<a class="btn btn-primary" href="planes"><i class="fas fa-arrow-left"></i></a> &nbsp;&nbsp;&nbsp;Año:
		</div>
		<div class="col-1">
			<select id="SelectYear" class="form-select" onchange="cambio();">
<% 			while(cont < 8){%>
				<option <%=yearActual == year ? "selected" : ""%>><%=yearActual%></option>
<% 				yearActual = yearActual-1;
				cont++;
			}%>
			</select>
		</div>
	</div>
</div>
<table class="table table-bordered">
	<thead class="table-info">
	<tr>
		<th class='center'>#</th>
		<th class='center'><spring:message code="aca.Matricula"/></th>
		<th class='center'><spring:message code="aca.Alumno"/></th>
		<th class='center'>Primer Insc.</th>
		<th class='center'>Ultima Insc.</th>
		<th class='center'><spring:message code="aca.Grado"/></th>
		<th class='center'><spring:message code="aca.Ciclo"/></th>
		<th class='center'>Cred.Plan</th>
		<th class='center'>Cred.Alum.</th>
		<th class='center'><spring:message code="aca.Avance"/></th>		
	</tr>
	</thead>
<%		
	cont = 1;
	for(AlumPlan alumPlan : lisAlumnos){
		
		// Buscar el nombre del alumno
		String nombreAlumno = "";
		if (mapAlumnos.containsKey(alumPlan.getCodigoPersonal())) nombreAlumno = mapAlumnos.get(alumPlan.getCodigoPersonal());
		
		// Buscar los creditos aprobados del alumno en el plan de estudios
		String creditosAlumno = "0";
		if (mapCreditos.containsKey(alumPlan.getCodigoPersonal())) creditosAlumno = mapCreditos.get(alumPlan.getCodigoPersonal());
		
		double avance = (Double.valueOf(creditosAlumno)*100)/Double.valueOf(creditosPlan);
		
		// Buscar la ultima fecha de inscripción de los alumnos
		String fecha = "";
		if (mapUltimaFecha.containsKey(alumPlan.getCodigoPersonal())) fecha = mapUltimaFecha.get(alumPlan.getCodigoPersonal());
%>
	<tr>
		<td width="5%" class="center"><%=cont++%></td>
		<td width="10%" class="center"><%= alumPlan.getCodigoPersonal() %></td>
		<td width="30%" class="left"><%= nombreAlumno %></td>
		<td width="7%" class="center"><%= alumPlan.getPrimerMatricula() %></td>
		<td width="7%" class="center"><%=fecha%></td>
		<td width="5%" class="center"><%= alumPlan.getGrado() %></td>
		<td width="5%" class="center"><%= alumPlan.getCiclo() %></td>
		<td width="5%" class="right"><%=creditosPlan%></td>
		<td width="5%" class="right"><%=creditosAlumno%></td>
		<td width="5%" class="right"><%=getFormato.format(avance)%></td>	
	</tr>	
<%	// Si no esta en la ultima fila
	}
%>
</table>
</div>