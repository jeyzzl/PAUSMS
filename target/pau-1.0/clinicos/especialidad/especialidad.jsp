<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.rotaciones.spring.RotEspecialidad"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	List<RotEspecialidad> lisEspecialidades 				= (List<RotEspecialidad>)request.getAttribute("lisEspecialidades");
	HashMap<String, String> mapaTotHospitales 				= (HashMap<String, String>)request.getAttribute("mapaTotHospitales");
%>
<html>
<head>
	<script>
		function Borrar(EspecialidadId){
			if(confirm("¿Desea borrar la especialidad seleccionada?")){
				document.location.href="borrar?EspecialidadId="+EspecialidadId;
			}
		}
	</script>
</head>
<body>
<div class="container-fluid">
	<h1>Especialidades</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="editar"><spring:message code='aca.Añadir'/></a>
	</div>
	<table style="width:50%" class="table table-sm table-bordered">
	<thead class="table-info">
	<tr>
		<th width="1%"><spring:message code="aca.Operacion"/></th>
		<th><spring:message code="aca.Nombre"/> especialidad</th>
		<th width="1%">Sem.</th>				
		<th><spring:message code="aca.Plan"/></th>
		<th>Curso</th>
	</tr>
	</thead>
	<tbody>
<%	
	for(RotEspecialidad especialidad : lisEspecialidades){
		String total = "0";
		if (mapaTotHospitales.containsKey(especialidad.getEspecialidadId())){
			total = mapaTotHospitales.get(especialidad.getEspecialidadId());	
		}
%>
	<tr>
		<td style="text-align: center">
			<a href="editar?EspecialidadId=<%=especialidad.getEspecialidadId()%>" title="Modificar"><i class="fas fa-edit"></i></i></a>
			&nbsp;
		<%	if(total.equals("0")){ %>
				<a href="javascript:Borrar('<%=especialidad.getEspecialidadId()%>')" title="Eliminar"><i class="fas fa-trash-alt"></i></a>
		<%	} %>
		</td>
		<td><%=especialidad.getEspecialidadNombre()%></td>
		<td style="text-align:center;"><%=especialidad.getSemanas()%></td>					
		<td><%=especialidad.getPlanId()%></td>
		<td><%=especialidad.getCursoId()==null?"":especialidad.getCursoId()%></td>
	</tr>
<%	} %>
	</tbody>
	</table>
</div>
</body>
</html>