<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.internado.spring.IntAlumno"%>
<%@ page import="aca.internado.spring.IntDormitorio"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>


<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>


<%
List<IntAlumno> lisTodos								= (List<IntAlumno>)request.getAttribute("lisTodos");


HashMap<String, AlumPersonal> mapAlumInternos	= (HashMap<String, AlumPersonal>)request.getAttribute("mapAlumInternos");
HashMap<String, IntDormitorio> mapDormitorio 	= (HashMap<String, IntDormitorio>)request.getAttribute("mapDormitorio");
	
%>
<body>
	<div class="container-fluid">
		<h2>Residencies Report</h2>
		<div class="alert alert-info d-flex align-items-center">
			<a class="btn btn-primary" href="menu"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
			
			
		</div>
		<table class="table table-bordered table-sm">
			<thead class="table-dark">
				<tr>
					<th width="2.5%">#</th>
					<th width="2.5%">ID</th>
					<th width="10%">Name</th>
					<th width="10%">Surname</th>
					<th width="6.25%">Gender</th>
					<th width="6.25%">Dormitory</th>
					<th width="12.5%">Room</th>
				</tr>
			</thead>
			<tbody>
<%	
	int count = 0;
	for(IntAlumno intAlum: lisTodos){
		count++;
		AlumPersonal alum = mapAlumInternos.get(intAlum.getCodigoPersonal()); 
		IntDormitorio dorm = mapDormitorio.get(intAlum.getDormitorioId());
	
		
%>

	

				<tr>
					<td><%=count%></td>
					<td><%=alum.getCodigoPersonal()%></td>
					<td><%=alum.getNombre() %></td>
					<td><%=alum.getApellidoPaterno() %></td>
					<td><%=alum.getSexo()%></td>
					<td><%=dorm.getNombre() %></td>
					<td><%=intAlum.getCuartoId() %> </td>
					
				</tr>
<%
	}
%>
			</tbody>
		</table>
	</div>
</body>