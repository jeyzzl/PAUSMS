<%@page import="ch.qos.logback.core.recovery.ResilientSyslogOutputStream"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@ page import="aca.kardex.spring.KrdxCursoImp"%>
<%@ page import="aca.plan.spring.MapaCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	java.text.DecimalFormat getFormato= new java.text.DecimalFormat("##0.00;-##0.00");

	String codigoPersonal 	= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 	= (String) session.getAttribute("codigoAlumno");
	String planId			= (String) request.getAttribute("planId");
	String nombreAlumno		= (String) request.getAttribute("nombreAlumno");
	
	List<MapaPlan> lisPlanes 				= (List<MapaPlan>)request.getAttribute("lisPlanes");
	List<AlumnoCurso> lisNotas 				= (List<AlumnoCurso>)request.getAttribute("lisNotas");
	HashMap<String, String> mapaGradePoint	= (HashMap<String,String>) request.getAttribute("mapaGradePoint");
%>
<!-- inicio de estructura -->
<script type="text/javascript">
	function Refrescar(){
		document.frmNotas.submit();
	}	
</script>
<body>
<div class="container-fluid">
	<h2>Grades <small class="text-muted fs-6">( <%=nombreAlumno%> )</small></h2>
	<form name="frmNotas" action="notas" method="post">
	<div class="alert alert-info">
		<select class="form-select" name="PlanId" onchange="javascript:Refrescar()" style="width:550px;">
<%	for( MapaPlan mapaPlan : lisPlanes){ %>
			<option value='<%=mapaPlan.getPlanId()%>' <%=planId.equals(mapaPlan.getPlanId())?"Selected":""%>><%=mapaPlan.getPlanId()%> - <%=mapaPlan.getNombrePlan()%></option>
<%	}%>
 		</select> 		
	</div>
	</form>
	<table class="table table-condensed">
	<thead>
	<tr>
		<th>#</th>
		<th>Code</th>
		<th>Subject</th>		
		<th>Date</th>
		<th>Cycle</th>
		<th>Grade</th>
		<th width="7%">Score</th>
		<th width="7%">Value</th>
		<th width="7%">Acc. Av.</th>
	</tr>	
	</thead>
	<tbody>
<%	
	int row 		= 0;	
	String year 	= "0";
	float prom 		= 0;	
	List<Float> lisAcum = new ArrayList<Float>();
	for(AlumnoCurso curso : lisNotas){
		row++;
		String gradePointNombre = "";
		String gradePointValor	= "";
		if (mapaGradePoint.containsKey(curso.getNota())){
			gradePointNombre 	= mapaGradePoint.get(curso.getNota());
			gradePointValor 	= gradePointNombre.split(";")[1].replace(",",".");
			gradePointNombre	= gradePointNombre.split(";")[0];
		}	
		boolean promCurso = true;
		if (promCurso){
			prom 		= Float.valueOf(gradePointValor);
			lisAcum.add(prom);
			//System.out.println("Row"+row+":"+prom+":"+lisAcum.size());
			prom = 0;
			for(Float val: lisAcum){
				prom = prom + val;
			}
			prom = prom / lisAcum.size();
		}
		
		if (!year.equals(curso.getFEvaluacion().substring(6,10))){
			year = curso.getFEvaluacion().substring(6,10);
			out.print("<tr class='table-secondary'><td colspan='9'>Year: "+year+"</td></tr>");
		}	
%>	
	<tr>
		<td><%=row%></td>
		<td><%=curso.getCursoId()%></td>
		<td><%=curso.getNombreCurso()%></td>
		<td><%=curso.getFEvaluacion()%></td>
		<td><%=curso.getCiclo()%></td>
		<td><%=curso.getNota()%></td>
		<td><%=gradePointNombre%></td>
		<td><%=gradePointValor%></td>
		<td><%=promCurso?getFormato.format(prom).replace(",","."):""%></td>		
		</tr>	
	</tr>	
<%	} %>	
	</tbody>
	</table>
</div>
<!-- fin de estructura  src="carga.html"   -->