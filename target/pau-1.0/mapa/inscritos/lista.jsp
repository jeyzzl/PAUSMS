<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String idCurso 				= (String)request.getParameter("idCurso");
	String planId 				= (String)request.getParameter("planId");
	String facultad 			= (String)request.getParameter("facultad");
	String ciclo  				= (String)request.getParameter("ciclo");
	String cursoClave  			= (String)request.getParameter("cursoClave");
	String facultadNombre		= (String)request.getAttribute("facultadNombre");
	String materiaNombre		= (String)request.getAttribute("materiaNombre");

	List<MapaCurso> lisCursos 	= (List<MapaCurso>) request.getAttribute("lisCursos");	
%>
<html>
<style>
	td{
		padding:2px;
	} 
</style>
<div class="container-fluid">
	<h3><%=facultadNombre%> <small class="text-muted fs-5">(<%=cursoClave+" - "+materiaNombre%> <spring:message code="aca.Plan"/>: <%=planId %> <spring:message code="aca.Ciclo"/>: <%=ciclo%>)</small></h3>
	<div class="alert alert-info">
		<a href="materia?facultad=<%=facultad%>&planId=<%=planId%>" class="btn btn-primary"><spring:message code='aca.Regresar'/></a>&nbsp;
		<a href="alumnos?idCurso=<%=idCurso%>&cursoClave=<%=cursoClave%>&ciclo=<%=ciclo%>&planId=<%=planId%>&facultad=<%=facultad%>" class="btn btn-success"><spring:message code="aca.VerAlumnosInscritos"/></a>
	</div>
	<form name="forma" action="lista?idCurso=<%=idCurso%>" method="post">
	<table class="table table-bordered table-sm">
	<thead>	
	<tr>
		<td colspan="5" align="center">
			<spring:message code="aca.mapa.inscritos.lista.Frase1"/>:
		</td>
	</tr>	
	<tr class="table-info">
		<th><spring:message code="aca.Numero"/></th>
		<th><spring:message code="aca.Nombre"/></th>
		<th><spring:message code="aca.Plan"/></th>
		<th><spring:message code="aca.Ciclo"/></th>
		<th><spring:message code='aca.Creditos'/></th>
	</tr>
	</thead>
	<tbody>
<%
	int cont = 0;
	for(MapaCurso curso: lisCursos){
		cont++;
%>
	<tr>
		<td align="center"><%=cont %></td>
		<td>&nbsp;&nbsp;<%=curso.getNombreCurso() %></td>
		<td>&nbsp;&nbsp;<%=curso.getPlanId() %></td>
		<td align="center"><%=curso.getCiclo() %></td>
		<td align="center"><%=curso.getCreditos() %></td>
	</tr>
	</tbody>
<%	} %>
	</table>
	</form>
</div>
</body>
</html>