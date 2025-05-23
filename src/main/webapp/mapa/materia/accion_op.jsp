<%@ include file="id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp"%>

<%@ page import="aca.plan.spring.MapaCursoElectiva"%>

<script type="text/javascript">
	function Grabar() {
		if (document.frmoptativa.Folio.value != "" && document.frmoptativa.CursoNombre != "") {
			document.frmoptativa.submit();
		} else {
			alert("<spring:message code='aca.JSCompletar'/>");
		}
	}
</script>
<%

	
	String cursoId 			= (String)request.getAttribute("cursoId");
	String planId			= (String)request.getAttribute("planId");
	String sem				= (String)request.getAttribute("sem");
	
	String materiaNombre 		= (String)request.getAttribute("materiaNombre");
	
	MapaCursoElectiva optativa 	= (MapaCursoElectiva)request.getAttribute("optativa");
%>
<div class="container-fluid">
	<h2>Elective Subjects <small class="text-muted fs-5">&nbsp;<%=materiaNombre%></small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="optativa?Plan=<%=planId%>&Semestre=<%=sem%>&Curso=<%=cursoId%>"><spring:message code='aca.Regresar' /></a>
	</div>
	
	<div name="formulario" class="container-fluid">
		<form action="grabarOptativa" method="post" name="frmoptativa" target="_self">
			<input type="hidden" name="PlanId" id="PlanId" value="<%=planId%>">
			<input type="hidden" name="CursoId" id="CursoId" value="<%=cursoId%>">
			<input type="hidden" name="Semestre" id="Semestre" value="<%=sem%>">
			
			<label for="Folio"><strong>Elective ID</strong></label>
			<input type="text" name="Folio" id="Folio" value="<%=optativa.getFolio() %>" class="form-control" style="width:12rem" maxlength="8" readonly>
			<br>
			<label for="CursoNombre"><strong>Subject Name</strong></label>
			<input type="text" name="CursoNombre" id="CursoNombre" value="<%=optativa.getCursoNombre() %>" class="form-control" style="width:20rem" maxlength="100">
		</form>
	</div>
	<br>
	<div class="alert alert-info">
		<a class ="btn btn-primary" href="javascript:Grabar()"> <spring:message code='aca.Grabar'/></a>	
	</div>
</div>