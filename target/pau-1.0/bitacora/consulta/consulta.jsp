<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ page import="aca.vista.spring.Alumno"%>

<%
	String codigoAlumno		= (String) request.getAttribute("codigoAlumno");
	Alumno alumno			= (Alumno) request.getAttribute("alumno");
	String facultadId		= (String) request.getAttribute("facultadId");
	String nombreFacultada	= (String) request.getAttribute("nombreFacultada");

%>

<script type="text/javascript">

</script>

<div class="container-fluid">
	<h2>Bit�cora de Servicios Escolares / Consulta de tr�mites</h2>
	<div class="alert alert-info">
		Solicitante: [<%=codigoAlumno%>] <%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <%=nombreFacultada%>	
	</div>
	<table class="table" style="width: 100%">
	<thead class="table-info">
		<tr>
			<th>Folio</th>
			<th>Tr�mite</th>
			<th>Inicio</th>
			<th>T�rmino</th>
			<th>Status</th>
		</tr>
	</thead>	
	</table>
</div>
