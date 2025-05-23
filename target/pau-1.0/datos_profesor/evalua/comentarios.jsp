<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.edo.spring.EdoAlumnoResp"%>
<%@page import="aca.edo.spring.EdoAlumnoPreg"%>

<%
	String codigoPersonal			= (String) request.getAttribute("codigoPersonal");
	String cursoCargaId				= (String) request.getAttribute("cursoCargaId");
	String nombreCurso				= (String) request.getAttribute("nombreCurso");
	String nombreMaestro			= (String) request.getAttribute("nombreMaestro");
	
	List<EdoAlumnoPreg> lisPreguntas = (List<EdoAlumnoPreg>) request.getAttribute("lisPreguntas");
	
	List<EdoAlumnoResp> listaPreguntasCursoId = (List<EdoAlumnoResp>) request.getAttribute("listaPreguntasCursoId");
%>
<body>
<div class="container-fluid">
	<h3 align="center">Resultado Comentarios de Evaluaci&oacute;n Estudiantil</h3>
	<h5 align="center" class="titulo2"><%=nombreMaestro%></h5>
	<h5 align="center"><font size="3" face="Arial">Materia - <%=nombreCurso%></font></h5>
	<div class="alert alert-primary" role="alert">
		<a href="cursos"><i class="fas fa-arrow-left"></i></a>
	</div>
	<br>
	<table class="table">
<%  //ciclo que trae las preguntas directas(Fortalezas y Debilidades)
	for (EdoAlumnoPreg com : lisPreguntas){
		int cont = 1;
%>
		<thead class="table-dark">
		  <tr>
		    <th align="center"><spring:message code="aca.Numero"/></th>
		    <th align="center">Comentario de: <%=com.getPregunta()%></th>
		  </tr>
		</thead>
<%		
		for(EdoAlumnoResp respuesta : listaPreguntasCursoId){
			if (respuesta.getPreguntaId().equals(com.getPreguntaId()) && respuesta.getEdoId().equals(com.getEdoId())){
%>		
		<tbody>
		  <tr>
		  	<td align="center"><strong><%=cont++%></strong></td>
			<td><%= respuesta.getRespuesta()%></td>
		  </tr>
		</tbody>
<%		
			} 
		} 
	}
%>
	</table>
</div>
</body>