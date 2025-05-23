<%@ page import= "java.util.List"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<html>
<%
	String planId 						= request.getParameter("PlanId")==null?"ISC1992*":request.getParameter("PlanId");
	String codigoPersonal				= (String) session.getAttribute("codigoPersonal");
	List<AlumPersonal> lisAlumnos		= (List<AlumPersonal>) request.getAttribute("lisAlumnos");
%>
<body onload='muestraPagina();'>	
<div class="container-fluid">
	<h2>Alumnos del Plan<small class="text-muted fs-4">( <%=planId%> )</h2>
	<form name="frmPlan" action="alumnos" method="post">
	<div class="alert alert-info">
		<select name="PlanId" onchange="javascript:document.frmPlan.submit();" class="form-select">
			<option value="ISC1992*" <%=planId.equals("ISC1992*")?"selected":""%>>ISC1992*</option>
			<option value="ISC2000*" <%=planId.equals("ISC2000*")?"selected":""%>>ISC2000*</option>
			<option value="ISC2010*" <%=planId.equals("ISC2010*")?"selected":""%>>ISC2010*</option>
			<option value="ISCO2018" <%=planId.equals("ISCO2018")?"selected":""%>>ISCO2018</option>
			<option value="LASC1996" <%=planId.equals("LASC1996")?"selected":""%>>LASC1996</option>
			<option value="LASC2000" <%=planId.equals("LASC2000")?"selected":""%>>LASC2000</option>			
		</select>
	</div>
	</form>
	<div class="row row-cols-5 row-cols-md-3 ml-1">	
<%
	int row=0;
	for (AlumPersonal alumno : lisAlumnos){			
		row++;			
		String carreraNombre 	= "";
		String facultadNombre 	= "";		
%>  
			<div class="card border-dark bg-light mb-2 mr-1" style="max-width: 15rem;">
				<div class="card-header"><i class="fas fa-birthday-cake" data-bs-toggle="tooltip" data-bs-placement="top" title="X"></i> <%=alumno.getNombre()%></div>
			  	<div class="card-body ">
			    	<img src="../../foto?Codigo=<%=alumno.getCodigoPersonal()%>" class="rounded" width="150">
			  	</div>
			  	<div class="card-footer">
			    	<span data-toggle="tooltip" data-placement="top" title="<%=carreraNombre%>"><i class="fas fa-book-reader"></i> <%=facultadNombre%></span>
			  	</div>
			</div>		
<%	}%>
	</div>
</div>	
</body>
<script>	
	$(function () {
  		$('[data-bs-toggle="tooltip"]').tooltip()
	});
</script>