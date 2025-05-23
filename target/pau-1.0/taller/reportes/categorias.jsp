<%@page import="aca.util.Fecha"%>
<%@page import="aca.afe.FesCcAfeAcuerdosUtil"%>
<%@page import="aca.bec.BecPuestoAlumno"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.bec.BecCategoriaUtil"%>
<%@page import="java.util.*" %>
<%@page import="java.text.*" %>

<jsp:useBean scope="page" id="puestoAU" class="aca.bec.BecPuestoAlumnoUtil"/>
<jsp:useBean scope="page" id="alumPersonal" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<%
    String ejercicioId 			= request.getParameter("EjercicioId")==null?session.getAttribute("ejercicioId").toString():request.getParameter("EjercicioId");
    String fecha	 			= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
	String categoriaId	 		= request.getParameter("CategoriaId")==null?"":request.getParameter("CategoriaId");
	String categoriaNombre 		= request.getParameter("CategoriaNombre")==null?"":request.getParameter("CategoriaNombre");	
	
	// Lista de alumnos en el puesto y la categoria
	ArrayList<aca.bec.BecPuestoAlumno> lisAlumnos 			= puestoAU.listAlumnosEnCategoria(conEnoc, ejercicioId, categoriaId, fecha, "ORDER BY CODIGO_PERSONAL");
%>
<div class="container-fluid">
	<h1>Alumnos de la Categor&iacute;a <%=categoriaNombre %></h1>
	<div class="alert alert-info">
		<a href="estadisticaPuestos" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">
	<tr>
		<th>#</th>
		<th><spring:message code="aca.Matricula"/></th>
		<th><spring:message code="aca.Nombre"/></th>
		<th>Departamento</th>
	</tr>
	</thead>
<%	
	int cont = 0;
	for(aca.bec.BecPuestoAlumno alumno: lisAlumnos){
		cont++;
		
%>
	<tr>
		<td><%= cont %></td>
		<td><%= alumno.getCodigoPersonal() %></td>
		<td><%= aca.alumno.AlumUtil.getNombreAlumno(conEnoc, alumno.getCodigoPersonal(), "") %></td>
		<td><%= alumno.getIdCcosto()%> - <%= aca.financiero.ContCcostoUtil.getNombre(conEnoc, ejercicioId, alumno.getIdCcosto())%></td>
	</tr>
	
<%

	}
%>	
	</table>
</div>	
<%@ include file= "../../cierra_enoc.jsp" %>