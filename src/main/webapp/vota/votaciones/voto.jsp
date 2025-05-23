<%@page import="aca.voto.spring.VotoEvento"%>
<%@page import="aca.voto.spring.VotoCandidato"%>
<%@page import="aca.voto.spring.VotoCandidatoAlumno"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<script>
	function cambioEvento(){
		var eventoId = document.getElementById("EventoId").value;		
		window.location.href = "voto?EventoId="+eventoId;		
	}
	
	function cambioGanador(eventoId,candidatoId){
		var ganador = document.getElementById("Ganador"+candidatoId).value;
		window.location.href = "cambiaGanador?EventoId="+eventoId+"&CandidatoId="+candidatoId+"&Ganador="+ganador;		
	}
</script>
<%	
	
	String eventoId 		= (String)request.getAttribute("eventoId");
	String tipoEvento		= (String)request.getAttribute("tipoEvento");
	
	List<VotoEvento> listaEvento 			= (List<VotoEvento>)request.getAttribute("listaEvento");
	List<VotoCandidato> listaCandidato 		= (List<VotoCandidato>)request.getAttribute("listaCandidato");
	
	HashMap<String,List<VotoCandidatoAlumno>> mapa 	= (HashMap<String,List<VotoCandidatoAlumno>>)request.getAttribute("mapa");
	HashMap<String,String> mapaGanadores 			= (HashMap<String,String>)request.getAttribute("mapaGanadores");
	HashMap<String,String> mapaCandidatos			= (HashMap<String,String>)request.getAttribute("mapaCandidatos");	
	HashMap<String,String> mapaVotos 				= (HashMap<String, String>)request.getAttribute("mapaVotos");
	HashMap<String,String> mapaEnMatriculas 		= (HashMap<String, String>)request.getAttribute("mapaEnMatriculas");	
	
	int cont = 1;
%>

<div class="container-fluid">	
	<h1>Eventos</h1>
	<select class="form-select" name="EventoId" id="EventoId" onchange="cambioEvento()">
<% 	for(VotoEvento evento : listaEvento){%>
		<option value="<%=evento.getEventoId()%>" <%=eventoId.equals(evento.getEventoId()) ? "selected" : ""%>><%=evento.getEventoNombre()%></option>
<% 	}%>
	</select><br>
	<table class="table">
	<thead>
	<tr>
		<th>#</th>
		<th>Estado</th>
		<th>Nombre</th>				
		<th>Votos</th>
		<th>Ganador</th>
	</tr>
	</thead>
	<tbody>
<% 	
	for(VotoCandidato candidato : listaCandidato){
	
		List<VotoCandidatoAlumno> alumnos = new ArrayList<VotoCandidatoAlumno>();
		
		if(mapa.containsKey(candidato.getEventoId()+candidato.getCandidatoId())){
			alumnos = mapa.get(candidato.getEventoId()+candidato.getCandidatoId());
		}
		
		String ganador = "Sin registro";
		if(mapaGanadores.containsKey(eventoId+candidato.getCandidatoId())){
			ganador = mapaGanadores.get(eventoId+candidato.getCandidatoId());
		}
		
		String[] arrCandidatos = candidato.getCandidatos().split(",");
		
		String estado = "Activo";
		String color = "success";
		
		if(!candidato.getEstado().equals("A")){
			 color = "warning";
			 estado = "Inactivo";
		}		
%>
		<tr>
			<td><%=cont++%></td>
			<td>
				<a href="cambiaEstado?EventoId=<%=eventoId%>&CandidatoId=<%=candidato.getCandidatoId()%>" class="btn btn-<%=color%>"><%=estado%></a>
			</td>
			<td><%=candidato.getCandidatoNombre()%></td>
			<td>
				<%													
					for (String codigo : arrCandidatos){								
						if(mapaVotos.containsKey(candidato.getCandidatoId()+codigo)){									
							String nombreCandidato = "-";
							if (mapaEnMatriculas.containsKey(codigo)){
								nombreCandidato = mapaEnMatriculas.get(codigo);
							}									
							out.print("<span class='badge bg-dark rounded-pill' title="+nombreCandidato.replace(" ","-")+">"+codigo+"</span>-");
							out.print("<span class='badge bg-success rounded-pill'>"+mapaVotos.get(candidato.getCandidatoId()+codigo)+"</span>");
							out.print(" ");
						}
					}
					
				%>
			</td>
			<td>
				<select class="form-control" name="Ganador" id="Ganador<%=candidato.getCandidatoId()%>" onchange="cambioGanador('<%=eventoId%>','<%=candidato.getCandidatoId()%>')">
					<option value="0">Sin Registrar</option>
<% 		
	for(VotoCandidatoAlumno alumno : alumnos){
		
		String nombreAlumno = "";
		if(mapaCandidatos.containsKey(alumno.getCodigoPersonal())){
			nombreAlumno = mapaCandidatos.get(alumno.getCodigoPersonal());
		}		
%>
					<option value="<%=alumno.getCodigoPersonal()%>" <%=ganador.equals(alumno.getCodigoPersonal()) ? "selected" : ""%> ><%=nombreAlumno%></option>
<% 		}%>
				</select>
			</td>
		</tr>	
<% 	}%>
	</tbody>
	</table>
</div>