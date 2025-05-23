<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.mentores.spring.MentAlumno"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String mentorId	 		= (String) session.getAttribute("codigoPersonal");
	String periodoId		= request.getParameter("PeriodoId")==null?session.getAttribute("ciclo").toString():request.getParameter("PeriodoId");	
	String fechaHoy 		= aca.util.Fecha.getHoy(); 
	String mentorNombre		= (String) request.getAttribute("mentorNombre");
		
	String sNomMentor 		= "";	
	String sNomAlumno 		= "";	
	int nContador			= 0;
	
	List<MentAlumno> lisMenAlumnos			= (List<MentAlumno>)request.getAttribute("lisMenAlumnos"); 
	HashMap<String,String> mapaAlumnos		= (HashMap<String,String>)request.getAttribute("mapaAlumnos"); 
%>
<div class="container-fluid">
<h2><small class="text-muted fs-4">Portal del Mentor: &nbsp;( <%=mentorId%> - <%=mentorNombre%> )</small></h2> 
<div class="alert alert-info">
	<a class="btn btn-primary" href="portal?PeriodoId=<%=periodoId%>"><i class="fas fa-arrow-left"></i><spring:message code='aca.Regresar'/></a>&nbsp;&nbsp;
</div>
<table style="width:60%" class="table">
	<tr><td colspan="2" align="center"><strong>Listado de Alumnos del Mentor</strong></td></tr>
	<tr>
    	<th><spring:message code="aca.Matricula"/></th>
    	<th align="left">&nbsp;Nombre</th>
	</tr>
<%
	for (int i = 0; i<lisMenAlumnos.size(); i++){
		MentAlumno malumno = (MentAlumno)lisMenAlumnos.get(i);
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(malumno.getCodigoPersonal()) ){
			alumnoNombre = mapaAlumnos.get(malumno.getCodigoPersonal());
		}

	%>
  <tr >
    <td align="center"><%=malumno.getCodigoPersonal() %></td>
    <td><%=alumnoNombre%></td>
  </tr>
<%		}		%>
</table>
</div>