<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>

<%@ include file= "menu.jsp" %>

<html>	
<%
	DecimalFormat getFormato	= new DecimalFormat("###,##0.00;(###,##0.00)");

	String matricula 		= (String)request.getAttribute("matricula");	
	String planId 			= request.getParameter("PlanId");
	String nombreAlumno		= (String)request.getAttribute("nombreAlumno"); 
	AlumPlan alumPlan  		= (AlumPlan)request.getAttribute("alumPlan");
	// String semTetra 		= (String)request.getAttribute("semTetra");
	String escala 			= (String)request.getAttribute("escala");	
	float promedio 			= 0;
	int row=0;
	
	List<AlumnoCurso> listaCursos = (List<AlumnoCurso>)request.getAttribute("listaCursos");
	HashMap<String,String> mapaGradePoint = (HashMap<String,String>)request.getAttribute("mapaGradePoint");
%>
<body>
<div class="container-fluid">
	<h3>Weighted average calculation<small class="text-muted"> ( <%=matricula%> - <%=nombreAlumno %> )</small></h3>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="calificaciones"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;&nbsp;
	</div>
	<table class="table table-bordered">
	<tr>
		<th>#</th>
		<th>Semester</th>
		<th><spring:message code="aca.Clave"/></th>
		<th>Course Name</th>
		<th class="text-right">Credits</th>
		<th class="text-right"><spring:message code="aca.Nota"/></th>
		<th class="text-right">Extra<br><spring:message code="aca.Nota"/></th>
		<th class="text-right">Credits<br>per grade</th>
	</tr>
<%	
	float sumaCreditos = 0;
	float sumaCreditosPorNota = 0;
	String gradePointNombre = "";
	String gradePointValue 	= "";
	for(AlumnoCurso alumnoCurso : listaCursos){
		float nota 				= Float.parseFloat(alumnoCurso.getNota());
		if (mapaGradePoint.containsKey(alumnoCurso.getNota().trim())){
			gradePointNombre 	= mapaGradePoint.get(alumnoCurso.getNota().trim());
			gradePointValue 	= gradePointNombre.split(";")[1];
		}else{
			gradePointValue		= "0";
		}
		float notaGPA 			= Float.parseFloat(gradePointValue);
		float extra 			= Float.parseFloat(alumnoCurso.getNotaExtra());
		float extraGPA 			= extra * (float) 0.04;
		float creditos 			= Float.parseFloat(alumnoCurso.getCreditos());
		float creditosPorNota 	= extra==0?notaGPA*creditos:extraGPA*creditos;
		
		sumaCreditos+=creditos;
		sumaCreditosPorNota+=creditosPorNota;
		row++;
		
		if(escala.equals("10")){
			nota 	= Float.parseFloat(alumnoCurso.getNota())/10;
			extra	= Float.parseFloat(alumnoCurso.getNotaExtra())/10;
		}
%>
		<tr>
			<td style="text-align:center; font-weight:bold;"><%=row%></td>
			<td style="text-align:center;"><%=alumnoCurso.getCiclo()%></td>
			<td><%=alumnoCurso.getCursoId()%></td>
			<td><%=alumnoCurso.getNombreCurso()%></td>
			<td class="text-right"><%=alumnoCurso.getCreditos()%></td>
			<td class="text-right"><%=String.format("%.2f", notaGPA)%></td>
			<td class="text-right"><%=extra==0?"-":extra%></td>
			<td class="text-right"><%=escala.equals("10")?String.format("%.2f",creditosPorNota/10):String.format("%.2f",creditosPorNota)%></td>
		</tr>
<%	}
			
	if(escala.equals("10")) promedio = (sumaCreditosPorNota/sumaCreditos)/10;
	else promedio = sumaCreditosPorNota/sumaCreditos;
%>
		<tr>
			<th colspan="4">Total</th>
			<th class="text-right"><h4><b><%=sumaCreditos%></b></h4></th>
			<th colspan="2">&nbsp;</th>
			<th class="text-right"><h4><b><%=escala.equals("10")?sumaCreditosPorNota/10:sumaCreditosPorNota%></b></h4></th>
		</tr>
	</table>
	<table style="margin: 0 auto;" class="tabla">
	  	<tr><td align="center"><font size="3" color="green"><b>Formula: &sum;(Credits*Grade) / &sum;(Credits)</b></font></td></tr>
	  	<tr><td>&nbsp;</td></tr>
	  	<tr>
	  		<td style="font-size:17px;" align="center">
	  			<b>
	  				<u>(<%=escala.equals("10")?sumaCreditosPorNota/10:sumaCreditosPorNota%>)</u>
	  				<font style="position:relative;top:7;"> = 
	  					<font color="green" size="4"><%=getFormato.format(promedio)%><font size="1" color="black"> Average</font></font></font><br>
	  				<font style="position:relative;left:-50;"><%=sumaCreditos%></font>
	  			</b>
	  		</td>
	  	</tr>
	</table>		
	</body>
</html>
<script>
	$('.nav-tabs').find('.notas').addClass('active');
</script>