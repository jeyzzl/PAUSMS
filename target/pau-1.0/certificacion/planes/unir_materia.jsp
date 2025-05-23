<%@ page import= "java.util.List"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String planId 	= (String) request.getAttribute("planId"); 
	String materia 	= (String) request.getAttribute("materia"); 
	
	List<MapaCurso> lisCursos = (List<MapaCurso>) request.getAttribute("lisCursos"); 
%>
<head><title>Sistema Academico</title>
	<link href="../../academico.css" rel="STYLESHEET" type="text/css">
	<link href="../../print.css" rel="STYLESHEET" type="text/css" media="print">
</head>
<div class="container-fluid">
	<h2>Unir materia del plan <%=planId %></h2>	
	<table class="table table-sm">
<%
	String ciclo = "";
	for(MapaCurso mapaCurso : lisCursos){
		if(!ciclo.equals(mapaCurso.getCiclo())){
			ciclo = mapaCurso.getCiclo();
%>
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2"><b>Ciclo <%=mapaCurso.getCiclo() %></b></td>
		</tr>
		<tr>
			<th>Curso Id</th>
			<th><spring:message code="aca.Nombre"/></th>
		</tr>
<%		} %>
		<tr class="btn btn-primary" onclick="parent.grabarUnion('<%=materia %>', '<%=mapaCurso.getCursoId() %>','<%=planId %>');">
			<td><%=mapaCurso.getCursoId() %></td>
			<td><%=mapaCurso.getNombreCurso() %></td>
		</tr>
<%	} %>		
	</table>