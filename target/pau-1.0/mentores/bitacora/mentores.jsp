<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.catalogo.CatPeriodo"%>
<%@page import="aca.mentores.MentCarrera"%>
<%@page import="aca.alumno.AlumPersonal"%>
<%@page import="aca.mentores.MentAlumno"%>
<%@page import="aca.mentores.MentLog"%>

<jsp:useBean id="catPeriodoU" scope="page" class="aca.catalogo.CatPeriodoUtil"/>
<jsp:useBean id="catPeriodo" scope="page" class="aca.catalogo.CatPeriodo"/>
<jsp:useBean id="catCarrera" scope="page" class="aca.catalogo.CatCarrera"/>
<jsp:useBean id="CatCarreraU" scope="page" class="aca.catalogo.CatCarreraUtil"/>
<jsp:useBean id="mentCarreraU" scope="page" class="aca.mentores.MentCarreraUtil"/>
<jsp:useBean id="mentCarrera" scope="page" class="aca.mentores.MentCarrera"/>
<%
	String carreraId 				= request.getParameter("carreraId");
	String periodoId				= request.getParameter("periodo");
	
	ArrayList<CatPeriodo> lisPeriodo	= null;
	ArrayList<MentCarrera> lisMentores	= null;
	
	if(periodoId == null)
		periodoId = (String) session.getAttribute("ciclo");
	else
		session.setAttribute("ciclo", periodoId);
	
	catCarrera 	= CatCarreraU.mapeaRegIdsinFac(conEnoc, carreraId);
	lisPeriodo 	= catPeriodoU.getListAll(conEnoc, "ORDER BY F_INICIO");
	lisMentores = mentCarreraU.getListCarrera(conEnoc, carreraId, periodoId, "ORDER BY EMP_NOMBRE(MENTOR_ID)");
%>
<div class="container-fluid">
	<form id="forma" name="forma" action="mentores?carreraId=<%=carreraId %>" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<a href="carrera" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		Log by mentor: &nbsp;<%=catCarrera.getNombreCarrera() %>&nbsp;&nbsp;&nbsp;
		<select id="periodo" name="periodo" onchange="document.forma.submit();">
<%
	for(int i = 0; i < lisPeriodo.size(); i++){
		catPeriodo = (CatPeriodo) lisPeriodo.get(i);
%>
			<option value="<%=catPeriodo.getPeriodoId() %>"<%=periodoId.equals(catPeriodo.getPeriodoId())?" Selected":"" %>><%=catPeriodo.getNombre() %></option>
<%
	}
%>
		</select>
	</div>
	</form>
	<table id="table" class="table table-sm table-bordered">
		<thead class="table-info">	 
		<tr>
			<th><spring:message code="aca.Numero"/></th>
			<th>Nomination</th>
			<th>Mentor</th>
			<th>N° Advised</th>
			<th>N° Consulted</th>
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
	for(int i = 0; i < lisMentores.size(); i++){
		mentCarrera = (MentCarrera) lisMentores.get(i);
%>
		<tr class="button" onclick="location.href='alumnos?carreraId=<%=carreraId %>&periodoId=<%=periodoId %>&mentorId=<%=mentCarrera.getMentorId() %>';">
			<td><%=i+1 %></td>
			<td><%=mentCarrera.getMentorId() %></td>
			<td><%= aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, mentCarrera.getMentorId(), "NOMBRE") %></td>
			<td align="right"><%=MentAlumno.getNumAlumnosMentor(conEnoc, periodoId, mentCarrera.getMentorId()) %></td>
			<td align="right"><%=MentLog.getNumAlumConsultados(conEnoc, periodoId, mentCarrera.getMentorId()) %></td>
			<td align="right" title="Number of queries to the Home tab"><%=MentLog.getConsultasTab(conEnoc, periodoId, mentCarrera.getMentorId(), "1") %></td>
			<td align="right" title="Number of queries to the Data tab"><%=MentLog.getConsultasTab(conEnoc, periodoId, mentCarrera.getMentorId(), "2") %></td>
			<td align="right" title="Number of queries to the Subjects tab"><%=MentLog.getConsultasTab(conEnoc, periodoId, mentCarrera.getMentorId(), "3") %></td>
			<td align="right" title="Number of queries to the Tasks tab"><%=MentLog.getConsultasTab(conEnoc, periodoId, mentCarrera.getMentorId(), "4") %></td>
			<td align="right" title="Number of queries to the Notes tab"><%=MentLog.getConsultasTab(conEnoc, periodoId, mentCarrera.getMentorId(), "5") %></td>
			<td align="right" title="Number of queries to the Account Status tab."><%=MentLog.getConsultasTab(conEnoc, periodoId, mentCarrera.getMentorId(), "6") %></td>
			<td align="right" title="Number of queries to the Documents tab"><%=MentLog.getConsultasTab(conEnoc, periodoId, mentCarrera.getMentorId(), "7") %></td>
			<td align="right" title="Number of queries to the Social Services tab."><%=MentLog.getConsultasTab(conEnoc, periodoId, mentCarrera.getMentorId(), "8") %></td>
			<td align="right" title="Number of queries to the Degree tab"><%=MentLog.getConsultasTab(conEnoc, periodoId, mentCarrera.getMentorId(), "9") %></td>
			<td align="right" title="Number of queries to the Discipline tab"><%=MentLog.getConsultasTab(conEnoc, periodoId, mentCarrera.getMentorId(), "10") %></td>
			<td align="right" title="Number of queries to the Gradua tab."><%=MentLog.getConsultasTab(conEnoc, periodoId, mentCarrera.getMentorId(), "11") %></td>
			<td align="right" title="Number of queries to the Compliance tab."><%=MentLog.getConsultasTab(conEnoc, periodoId, mentCarrera.getMentorId(), "12") %></td>
			<td align="right" title="Number of queries to the Conv tab."><%=MentLog.getConsultasTab(conEnoc, periodoId, mentCarrera.getMentorId(), "13") %></td>
			<td align="right" title="Number of queries to the Fitness Tab"><%=MentLog.getConsultasTab(conEnoc, periodoId, mentCarrera.getMentorId(), "14") %></td>
			<td align="right" title="Number of queries to the Options tab"><%=MentLog.getConsultasTab(conEnoc, periodoId, mentCarrera.getMentorId(), "15") %></td>
		</tr>
<%
	}
%>
	</table>
</div>

<%@ include file= "../../cierra_enoc.jsp" %> 