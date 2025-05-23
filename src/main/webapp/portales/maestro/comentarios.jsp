  <%@page import="java.util.List"%>
<%@page import="aca.edo.spring.EdoAlumnoResp"%>
<%@page import="aca.edo.spring.EdoAlumnoPreg"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="edoAlumnoPreg" scope="page" class="aca.edo.EdoAlumnoPreg"/>
<jsp:useBean id="edoAlumnoResp" scope="page" class="aca.edo.EdoAlumnoResp"/>
<jsp:useBean id="edoAlumnoRespU" scope="page" class="aca.edo.EdoAlumnoRespUtil"/>
<jsp:useBean id="edoAlumnoPregU" scope="page" class="aca.edo.EdoAlumnoPregUtil"/>
<%
	String codigoPersonal			= (String) session.getAttribute("codigoEmpleado");	
	String cursoCargaId				= request.getParameter("cursoCargaId");
	String nombreCurso				= request.getParameter("nombreCurso");	
		
	String nombreMaestro 					= (String)request.getAttribute("nombreMaestro");
	List<EdoAlumnoPreg> lisPreguntas 		= (List<EdoAlumnoPreg>)request.getAttribute("lisPreguntas");
	List<EdoAlumnoResp> lisRespuestas 		= (List<EdoAlumnoResp>)request.getAttribute("lisRespuestas");
%>
<body>
<div class="container-fluid">
<h2><spring:message code="portal.maestro.comentario.ComentariosEstudiantil"/><small class="text-muted">( <%=nombreMaestro%> )</small></h2>
<div class="alert alert-info">
	<a href="cursos" class="btn btn-primary"><spring:message code="aca.Regresar"/></a> &nbsp; <spring:message code="portal.maestro.comentario.Materia"/> - <%=nombreCurso%> 
</div>
	<form id="forma" name="forma" action="opinion_estudiantil" method="post">
	</form>
	<br>
	<table style="width:80%" border="1" cellpadding="1px" >
<%  //ciclo que trae las preguntas directas(Fortalezas y Debilidades)
	for ( EdoAlumnoPreg com : lisPreguntas){		
%>
	  <tr>
	    <th align="center"><spring:message code="aca.Numero"/></th>
	    <th align="center">Comment from: <%=com.getPregunta()%></th>
	  </tr>
<%		
		int cont = 0;	
		for( EdoAlumnoResp respuesta : lisRespuestas){
			if (respuesta.getPreguntaId().equals(com.getPreguntaId()) && respuesta.getRespuesta() != null){				
				cont++;
%>		
	  <tr>
	  	<td align="center"><strong><%=cont%></strong></td>
		<td><%= respuesta.getRespuesta()%></td>
	  </tr>
<%		
			} // Si es respuesta != null
		} // End for de Respuestas
	} // End de for Preguntas
%>
	</table>
  </div>
</body>