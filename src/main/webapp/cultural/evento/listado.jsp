<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.cultural.spring.CompAsistencia"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<script>
	function Borrar(eventoId){
		if(confirm("Estas seguro de eliminar el evento!")==true){	
			document.location.href="borrar?EventoId="+eventoId;
		}	
	}
</script>
<%	
	List<CompAsistencia> lisEventos 		= (List<CompAsistencia>)request.getAttribute("lisEventos");
	HashMap<String,String> mapaAlumnos 		= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
%>
<body>
<div class="container-fluid">
	<h2>Eventos Culturales</h2>
	<hr>
	<div class="alert alert-info d-flex align-items-center">
		<a href="editar" class="btn btn-primary"><i class="fas fa-plus"></i></a>
	</div>
	<table class="table table-bordered">
	<thead class="table-info">
	<tr> 
		<th>Op.</th>
		<th>Clave</th>
		<th>Nombre</th>
		<th>Estado</th>
		<th>Fecha</th>
		<th>Hora</th>	 
		<th>#Alumnos</th>
	</tr>
	</thead>
	<tbody>
<%
	for (CompAsistencia asistencia : lisEventos){
		String total = "0";
		if (mapaAlumnos.containsKey(asistencia.getEventoId())){
			total = mapaAlumnos.get(asistencia.getEventoId());
		}
%>
	<tr>
   		<td class="fs-6">
   			<a href="editar?EventoId=<%=asistencia.getEventoId()%>"><i class="fas fa-edit"></i></a>
<%		if (total.equals("0")){%>
			&nbsp;<a href="javascript:Borrar(<%=asistencia.getEventoId()%>)"><i class="fas fa-trash-alt"></i></a>
<%		} %>   			
   		</td>
	    <td class="fs-6"><%=asistencia.getEventoId()%></td>
	 	<td class="fs-6"><%=asistencia.getNombre()%></td>
	 	<td class="fs-6"><%=asistencia.getEstado().equals("A")?"Activo":"Inactivo"%></td>
	  	<td class="fs-6"><%=asistencia.getFecha()%></td>
	 	<td class="fs-6"><%=asistencia.getHora()%></td>	   
	 	<td class="fs-6"><%=total%></td>	
  	</tr> 
<%	} %>
	</tbody>
	</table>
</div>
</body>
</html>