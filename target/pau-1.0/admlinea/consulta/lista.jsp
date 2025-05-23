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
	<h2>Sedes de examen <a href="proyecto">P</a></h2>	
    </hr>	
	<table class="table table-bordered table-sm">
	<thead>
		<tr>
			<th>#</th>
			<th>Fecha</th>
			<th>Sede</th>
			<th>Lugar</th>
			<th class="right">#Alum.</th>
			<th>Estado</th>
		</tr>
	</thead>	
<%		
	int cont = 1;
	for(AdmEvento evento: lisEventos){
		String ocupados = "0";
		if (mapaOcupados.containsKey(evento.getEventoId()) ){
			ocupados = mapaOcupados.get(evento.getEventoId());
		}
		String estado = evento.getEstado().equals("A")?"Activo":"Inactivo";
		if (evento.getEstado().equals("A")){
			estado = "<span class=' badge  bg-inverse'>"+estado+"</span>";
		}else{
			estado = "<span class='badge  bg-warning'>"+estado+"</span>";			
		}
%>
			<tr>
				<td><%=cont++%></td>					
						
				<td><%=evento.getFecha()%></td>
				<td><a href="alumnos?EventoId=<%=evento.getEventoId()%>"><%=evento.getEventoNombre()%></a></td>
				<td><%=evento.getLugar()%></td>
				<td class="right"><%=ocupados%></td>
				<td>
<%			if (acceso.getAdministrador().equals("S")){%>		
					<a href="modificaEstado?EventoId=<%=evento.getEventoId()%>&Estado=<%=evento.getEstado().equals("A")?"I":"A"%>"><%=estado%></a>
<%			}%>		
				</td>
			
			</tr>
<% 		}%>
		</table>
	</div>
</body>
<script type="text/javascript">
	function borrarEvento(eventoId){
 		if (confirm("Seguro de borrar este evento?") == true) {
			location.href = "eliminarEvento?EventoId="+eventoId;
  		}
	}
</script>
</html>