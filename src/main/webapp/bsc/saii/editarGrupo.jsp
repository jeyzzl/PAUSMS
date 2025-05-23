<%@page import="java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@page import="aca.saii.spring.SaiiGrupo"%>
<%@page import="aca.saii.spring.SaiiPeriodo"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
	SaiiGrupo grupo		= (SaiiGrupo) request.getAttribute("grupo");
	String periodoId	= (String) request.getAttribute("periodoId");
	
	List<SaiiGrupo> lisGrupo 		= (List<SaiiGrupo>) request.getAttribute("lisGrupo");
	
%>
<body>
<div class="container-fluid">
<div class="lds-dual-ring"></div>
	<h1>Editar grupo SAii</h1>
	<div class="alert alert-info">
		<a href="reporte?PeriodoId=<%=periodoId%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<form name="form" action="grabar" method="post">
		<input type="hidden" name="PeriodoId" value="<%=grupo.getPeriodoId()%>">
		<input type="hidden" name="PlanId" value="<%=grupo.getPlanId()%>">
		<h2>Periodo: <small class="text-muted fs-4"><%=grupo.getPeriodoId()%></small></h2>
		<h2>Plan: <small class="text-muted fs-4"><%=grupo.getPlanId()%></small></h2>
		<h2>Grupo</h2>
		<input type="text" name="Grupo" value="<%=grupo.getGrupoId()%>"><br><br>
		<div class="alert alert-info">
			<button type="submit" class="btn btn-primary">Grabar</button>
		</div>
	</form>	
</div>
</body>