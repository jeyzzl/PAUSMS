<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.util.Fecha"%>
<%@page import="aca.vista.Inscritos"%>
<%@page import="aca.acceso.Acceso"%>
<%@page import="aca.mentores.MentAlumno"%>
<%@page import="aca.catalogo.CatCarrera"%>
<%@page import="aca.catalogo.CatFacultad"%>
<%@page import="aca.alumno.AlumPersonal"%>

<%@page import="aca.alumno.AlumPlan"%>
<jsp:useBean id="mentorU" scope="page" class="aca.mentores.MentAlumnoUtil"/>
<jsp:useBean id="alumno" scope="page" class="aca.mentores.MentAlumno"/>
<jsp:useBean id="mentorCU" scope="page" class="aca.mentores.MentContactoUtil"/>
<jsp:useBean id="acceso" scope="page" class="aca.acceso.Acceso"/>
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil" />


<%
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String periodoId		= (String) session.getAttribute("ciclo");
	
	String matricula 		= request.getParameter("matricula");
	String carreraId		= request.getParameter("carrera");
	String mentorId			= request.getParameter("mentor");
		
	String sFacultad		= aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc, carreraId);
	String alumFac			= aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc, aca.alumno.PlanUtil.getCarreraId(conEnoc, matricula));
	
    acceso = AccesoU.mapeaRegId(conEnoc, codigoPersonal);
    

	ArrayList <aca.mentores.MentContacto> lisMenAlumno  = mentorCU.getListEntCarrera(conEnoc, mentorId, carreraId, periodoId, "ORDER BY CODIGO_PERSONAL");
%>

<link href="print.css" rel="stylesheet" type="text/css" media="print">

<div class="container-fluid">

	<h2><%=mentorId %> | <%= aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, mentorId, "NOMBRE") %> </h2>
	
	<div class="alert alert-info">
		<%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, carreraId) %> | <%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, sFacultad) %> 
	</div>

	<div class="alert alert-info">
		<a class="btn btn-primary" href="mentor.jsp?carrera=<%=carreraId %>"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
	</div>
	
	<table class="table table-condensed">
		<tr>
			<th>#</th>
			<th>Enrollment ID</th>
			<th>Name</th>
			<th>Degree</th>
			<th>Date</th>
			<th>Comment</th>
			<th>Status</th>
		</tr>
	<%	
		int cont = 1;
		for(aca.mentores.MentContacto alumnos : lisMenAlumno){
	%>
		<tr>
			<td><%=cont %></td>
		  	<td class="ayuda alumno <%=alumnos.getCodigoPersonal() %>"><%=alumnos.getCodigoPersonal()%></td>
			<td><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, alumnos.getCodigoPersonal(), "APELLIDO") %></td>
			<td><%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, alumnos.getCarreraId()) %></td>
			<td><%=alumnos.getFechaContacto() %></td>
			<td><%=alumnos.getComentario() %></td>
			<td><%=aca.alumno.AlumUtil.esInscrito(conEnoc, alumnos.getCodigoPersonal())?"Registered":"Not Registered" %></td>
		</tr>
	<%
			cont++;
		}
	%>
	</table>

</div>
<%@ include file= "../../cierra_enoc.jsp" %>