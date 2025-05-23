<%@page import="java.util.List"%>
<%@page import="aca.edo.spring.EdoAlumnoResp"%>
<%@page import="aca.edo.spring.EdoAlumnoPreg"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
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
<h2>Comentarios de Evaluaci&oacute;n Estudiantil <small class="text-muted fs-4">( <%=nombreMaestro%> )</small></h2>
<div class="alert alert-info">
	<a href="cursos" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a> &nbsp; Materia - <%=nombreCurso%> 
</div>
	<table id="table" class="table table-sm table-bordered">
<%  //ciclo que trae las preguntas directas(Fortalezas y Debilidades)
	for ( EdoAlumnoPreg com : lisPreguntas){		
%>
	<thead class="table-info">	 
	  <tr>
	    <th align="center"><spring:message code="aca.Numero"/></th>
	    <th align="center">Comentario de: <%=com.getPregunta()%></th>
	  </tr>
	 </thead>
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