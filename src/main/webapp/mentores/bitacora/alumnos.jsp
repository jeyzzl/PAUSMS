<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.mentores.MentLog"%>
<%@page import="aca.mentores.MentAlumno"%>

<jsp:useBean id="catCarrera" scope="page" class="aca.catalogo.CatCarrera"/>
<jsp:useBean id="CatCarreraU" scope="page" class="aca.catalogo.CatCarreraUtil"/>
<jsp:useBean id="catPeriodo" scope="page" class="aca.catalogo.CatPeriodo"/>
<jsp:useBean id="CatPeriodoU" scope="page" class="aca.catalogo.CatPeriodoUtil"/>
<jsp:useBean id="mentLogU" scope="page" class="aca.mentores.MentLogUtil"/>
<%
	String carreraId			= request.getParameter("carreraId");
	String periodoId			= request.getParameter("periodoId");
	String mentorId				= request.getParameter("mentorId");
	
	catCarrera = CatCarreraU.mapeaRegIdsinFac(conEnoc, carreraId);
	catPeriodo = CatPeriodoU.mapeaRegId(conEnoc, periodoId);
	
	ArrayList<String> lisAlumnos 	= mentLogU.getListAlumnosMentor(conEnoc, mentorId, periodoId, "ORDER BY ENOC.ALUM_NOMBRE(CODIGO_PERSONAL)");
%>
<div class="container-fluid">
	<table class="goback"><tr><td><a class="btn btn-primary" href="mentores?carreraId=<%=carreraId %>&periodoId=<%=periodoId %>"><i class="fas fa-arrow-left"></i></a></td></tr></table>
	<table style="margin: 0 auto;  width:95%">
		<tr>
			<td class="titulo">Logbook by student breakdown</td>
		</tr>
		<tr>
			<td class="titulo2"><%=catCarrera.getNombreCarrera() %></td>
		</tr>
		<tr>
			<td class="titulo2"><%= aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, mentorId, "NOMBRE") %></td>
		</tr>
		<tr>
			<td class="titulo2"><%=catPeriodo.getNombre() %></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>
			<th><spring:message code="aca.Numero"/></th>
			<th>Student name</th>
			<th>It is advisable to</th>
			<th title="Number of queries to the Home tab">1</th>
			<th title="Number of queries to the Data tab">2</th>
			<th title="Number of queries to the Subjects tab">3</th>
			<th title="Number of queries to the Tasks tab">4</th>
			<th title="Number of queries to the Notes tab">5</th>
			<th title="Number of queries to the Account Status tab.">6</th>
			<th title="Number of queries to the Documents tab">7</th>
			<th title="Number of queries to the Social Services tab.">8</th>
			<th title="Number of queries to the Degree tab">9</th>
			<th title="Number of queries to the Discipline tab">10</th>
			<th title="Number of queries to the Gradua tab.">11</th>
			<th title="Number of queries to the Compliance tab.">12</th>
			<th title="Number of queries to the Conv tab.">13</th>
			<th title="Number of queries to the Fitness Tab">14</th>
			<th title="Number of queries to the Options tab">15</th>
		</tr>
	</thead>
<%
	for(int i = 0; i < lisAlumnos.size(); i++){
		String codigoAlumno = (String) lisAlumnos.get(i); %>
		<tr>
			<td align="right"><%=i+1 %></td>
			<td><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, codigoAlumno, "NOMBRE") %></td>
			<td align="center"><%=MentAlumno.getMentorId(conEnoc, codigoAlumno, periodoId).equals(mentorId)?"Yes":"No" %></td>
			<td align="right" title="Number of queries to the Home tab"><%=MentLog.getConsultasAlumnoTab(conEnoc, periodoId, mentorId, codigoAlumno, "1") %></td>
			<td align="right" title="Number of queries to the Data tab"><%=MentLog.getConsultasAlumnoTab(conEnoc, periodoId, mentorId, codigoAlumno, "2") %></td>
			<td align="right" title="Number of queries to the Subjects tab"><%=MentLog.getConsultasAlumnoTab(conEnoc, periodoId, mentorId, codigoAlumno, "3") %></td>
			<td align="right" title="Number of queries to the Tasks tab"><%=MentLog.getConsultasAlumnoTab(conEnoc, periodoId, mentorId, codigoAlumno, "4") %></td>
			<td align="right" title="Number of queries to the Notes tab"><%=MentLog.getConsultasAlumnoTab(conEnoc, periodoId, mentorId, codigoAlumno, "5") %></td>
			<td align="right" title="Number of queries to the Account Status tab."><%=MentLog.getConsultasAlumnoTab(conEnoc, periodoId, mentorId, codigoAlumno, "6") %></td>
			<td align="right" title="Number of queries to the Documents tab"><%=MentLog.getConsultasAlumnoTab(conEnoc, periodoId, mentorId, codigoAlumno, "7") %></td>
			<td align="right" title="Number of queries to the Social Services tab."><%=MentLog.getConsultasAlumnoTab(conEnoc, periodoId, mentorId, codigoAlumno, "8") %></td>
			<td align="right" title="Number of queries to the Degree tab"><%=MentLog.getConsultasAlumnoTab(conEnoc, periodoId, mentorId, codigoAlumno, "9") %></td>
			<td align="right" title="Number of queries to the Discipline tab"><%=MentLog.getConsultasAlumnoTab(conEnoc, periodoId, mentorId, codigoAlumno, "10") %></td>
			<td align="right" title="Number of queries to the Gradua tab."><%=MentLog.getConsultasAlumnoTab(conEnoc, periodoId, mentorId, codigoAlumno, "11") %></td>
			<td align="right" title="Number of queries to the Compliance tab."><%=MentLog.getConsultasAlumnoTab(conEnoc, periodoId, mentorId, codigoAlumno, "12") %></td>
			<td align="right" title="Number of queries to the Conv tab."><%=MentLog.getConsultasAlumnoTab(conEnoc, periodoId, mentorId, codigoAlumno, "13") %></td>
			<td align="right" title="Number of queries to the Fitness Tab"><%=MentLog.getConsultasAlumnoTab(conEnoc, periodoId, mentorId, codigoAlumno, "14") %></td>
			<td align="right" title="Number of queries to the Options tab"><%=MentLog.getConsultasAlumnoTab(conEnoc, periodoId, mentorId, codigoAlumno, "15") %></td>
		</tr>
<%	} %>
	</table>
</div>

<%@ include file= "../../cierra_enoc.jsp" %>