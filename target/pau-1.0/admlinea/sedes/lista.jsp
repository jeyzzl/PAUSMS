<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmEvento"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.admision.spring.AdmPeriodo"%>
<html>
<head>		
</head>
<%
	Acceso acceso 								= (Acceso)request.getAttribute("acceso");
	List<AdmEvento> lisEventos 					= (List<AdmEvento>)request.getAttribute("lisEventos");
	HashMap<String,String> mapaOcupados			= (HashMap<String,String>)request.getAttribute("mapaOcupados");
	HashMap<String, AdmPeriodo> mapaAdmPeriodo	= (HashMap<String,AdmPeriodo>)request.getAttribute("mapaAdmPeriodo");
%>
<body>
	<div class="container-fluid">
		<h2>Exam Venues</small></h2>
		<div class="alert alert-info">
<% 		if (acceso.getAdministrador().equals("S")){%>
			<a href="nuevoEvento" class="btn btn-primary" title="Agregar sede">Add <spring:message code="aca.Nuevo"/></a>
<% 		}%>
		</div>
		<table class="table table-bordered table-sm">
			<tr>
				<th>#</th>
				<th width="6%">Op.</th>
				<th><spring:message code="aca.Fecha"/></th>
				<th>Venue</th>
				<th>Location</th>
				<th class="right">#<spring:message code="aca.Alumno"/></th>
				<th><spring:message code="aca.Status"/></th>
				<th><spring:message code="aca.Horario"/></th>
			</tr>
<%		
	int cont = 1;
	for(AdmEvento evento: lisEventos){
		String ocupados = "0";	
		if (mapaOcupados.containsKey(evento.getEventoId()) ){
			ocupados = mapaOcupados.get(evento.getEventoId());
		}
		String estado = evento.getEstado().equals("A")?"Active":"Inactive";
		if (evento.getEstado().equals("A")){
			estado = "<span class=' badge  bg-inverse'>"+estado+"</span>";
		}else{
			estado = "<span class='badge  bg-warning'>"+estado+"</span>";			
		}
%>
			<tr>
				<td><%=cont++%></td>					
				<td>
					<a href="nuevoEvento?EventoId=<%=evento.getEventoId()%>" class="" style="width:40px;" ><i class="fas fa-pencil-alt"></i></a>&nbsp;&nbsp;
<% 				if (acceso.getAdministrador().equals("S") && ocupados.equals("0")){%>					
					<a onclick="javascript:borrarEvento('<%=evento.getEventoId()%>')" class="text-danger" style="width:40px;" title="Delete"><i class="fas fa-trash-alt"></i></a>
<% 				}%>
				</td>					
				<td><%=evento.getFecha()%></td>
				<td><a href="alumnos?EventoId=<%=evento.getEventoId()%>"><%=evento.getEventoNombre()%></a></td>
				<td><%=evento.getLugar()%></td>
				<td class="right"><%=ocupados%></td>
				<td>
<%			if (acceso.getAdministrador().equals("S")){%>		
					<a href="modificaEstado?EventoId=<%=evento.getEventoId()%>&Estado=<%=evento.getEstado().equals("A")?"I":"A"%>"><%=estado%></a>
<%			}%>		
				</td>
				<td>
<%			if (acceso.getAdministrador().equals("S")){		
				if (mapaAdmPeriodo.containsKey(evento.getEventoId())){%>		
					<a href="agregarPeriodo?EventoId=<%=evento.getEventoId()%>" class="btn btn-success">YES</a>
<%				}else{%>		
					<a href="agregarPeriodo?EventoId=<%=evento.getEventoId()%>" class="btn btn-warning">NO</a>
<%				}		
			}%>		
				</td>
			</tr>
<% 		}%>
		</table>
	</div>
</body>
<script type="text/javascript">
	function borrarEvento(eventoId){
 		if (confirm("Are you sure you want to delete this venue?") == true) {
			location.href = "eliminarEvento?EventoId="+eventoId;
  		}
	}
</script>
</html>