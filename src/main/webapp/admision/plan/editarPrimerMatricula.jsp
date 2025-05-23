<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.alumno.spring.AlumPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<script type="text/javascript">
	function Grabar() {
		document.frmFInicio.submit();
	}
</script>

<%
	String codigoAlumno 			= (String) session.getAttribute("codigoAlumno");
	String alumnoNombre 			= (String)request.getAttribute("alumnoNombre");
	String planId 					= (String)request.getAttribute("planId");
	AlumPlan plan                   = (AlumPlan)request.getAttribute("plan");
	
%>
<div class="container-fluid">
	<h2>Edit Plan Start Date: <%=alumnoNombre  %> <small class="text-muted fs-5">(<%=codigoAlumno %>)</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="planes"><spring:message code="aca.Regresar"/></a>
	</div>
	
	<div class="form-group">
		<form name="frmFInicio" method="post" action="grabarPrimerMatricula">
			<div class="container-fluid	">
				<label class="form-label">Student ID</label><br>
				<input class="form-control mb-3" type="text" name="CodigoPersonal" value="<%=codigoAlumno %>" style="width: 15rem" readonly>
				<label class="form-label">Plan ID</label><br>
				<input class="form-control mb-3" type="text" name="PlanId" value="<%=planId %>" style="width: 15rem" readonly>
                <label class="form-label">Start Date</label><br>
				<input name="PrimerMatricula" type="text" class="form-control" id="PrimerMatricula" data-date-format="dd/mm/yyyy" value="<%=plan.getPrimerMatricula()%>" onfocus="focusFecha(this);" size="12" maxlength="10" style="width: 15rem"> 
			</div>
            <div class="mt-4 alert alert-info">
				<button type="submit" class="btn btn-primary" href="javascript:Grabar()">Save</button>
            </div>
		</form>
	</div>
</div>
<script>
	jQuery('#PrimerMatricula').datepicker();
</script>