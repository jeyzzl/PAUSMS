<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Grabar() {
		document.frmCiclo.submit();
	}
</script>

<%
	String codigoAlumno 			= (String) session.getAttribute("codigoAlumno");
	String alumnoNombre 			= (String)request.getAttribute("alumnoNombre");
	String planId 					= (String)request.getAttribute("planId");
	String grado 					= (String)request.getAttribute("grado");
	String ciclo 					= (String)request.getAttribute("ciclo");
	
%>
<div class="container-fluid">
	<h2>Year and Cycle - Student: <%=alumnoNombre  %> <small class="text-muted fs-5">(<%=codigoAlumno %>)</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="planes"><spring:message code="aca.Regresar"/></a>
	</div>
	
	<div class="form-group">
		<form name="frmCiclo" method="post" action="grabarCiclo">
			<div class="container-fluid	">
				<label class="form-label">Student ID</label><br>
				<input class="form-control mb-3" type="text" name="CodigoPersonal" value="<%=codigoAlumno %>" style="width: 15rem" readonly>
				<label class="form-label">Plan ID</label><br>
				<input class="form-control mb-3" type="text" name="PlanId" value="<%=planId %>" style="width: 15rem" readonly>
				<label class="form-label">Year</label><br>
				<select class="form-select mb-3" name="Grado" id="Grado" style="width:15rem">
					<option value="1" <%= grado.equals("1")?"selected":"" %>>1</option>
					<option value="2" <%= grado.equals("2")?"selected":"" %>>2</option>
					<option value="3" <%= grado.equals("3")?"selected":"" %>>3</option>
					<option value="4" <%= grado.equals("4")?"selected":"" %>>4</option>
				</select>
				<label class="form-label">Cycle</label><br>
				<select class="form-select mb-3" name="Ciclo" id="Ciclo" style="width:15rem">
					<option value="1" <%= ciclo.equals("1")?"selected":"" %>>1</option>
					<option value="2" <%= ciclo.equals("2")?"selected":"" %>>2</option>
				</select>
				<div class="alert alert-info">
				<button type="submit" class="btn btn-primary" href="javascript:Grabar()">Save</button>
			</div>
		</form>
	</div>
</div>