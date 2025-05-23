<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.financiero.spring.FinPermiso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	String year 		=  request.getParameter("Year")==null?"2019":request.getParameter("Year");

	List<FinPermiso> lisPermisos 			= (List<FinPermiso>) request.getAttribute("lisPermisos");
	HashMap<String,String> mapaAlumnos 		= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapaEmpleados	= (HashMap<String,String>)request.getAttribute("mapaEmpleados");
%>
<div class="container-fluid">
	<h1>Reporte de Permisos</h1>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
	<tr>
		<th>#</th>
		<th>Matr&iacute;cula</th>
		<th><spring:message code="aca.Nombre"/></th>
		<th>Fecha Ini.</th>
		<th>Fecha Lim.</th>
		<th>Otorgó</th>		
	</tr>
	</thead>

<%	
	int row = 0;
	for(FinPermiso permiso : lisPermisos){
		row++;
		
		String nombreAlumno = "-";
		if (mapaAlumnos.containsKey(permiso.getCodigoPersonal())){
			nombreAlumno = mapaAlumnos.get(permiso.getCodigoPersonal());
		}
		
		String nombreEmpleado = "-";
		if (mapaEmpleados.containsKey(permiso.getUsuario())){
			nombreEmpleado = mapaEmpleados.get(permiso.getUsuario());
		}
%>
	<tr class="tr2">
		<td><%=row%></td>
		<td><%=permiso.getCodigoPersonal()%></td>
		<td><%=nombreAlumno%></td>
		<td><%=permiso.getFInicio()%></font></td>
		<td><%=permiso.getFLimite()%></td>
		<td><%=nombreEmpleado%></td>
	</tr>
<%				
	}
%>
	</table>
</div>	