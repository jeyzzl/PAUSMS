<%@page import="ch.qos.logback.core.recovery.ResilientSyslogOutputStream"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.graduacion.spring.AlumEvento"%>
<%@ page import="aca.graduacion.spring.AlumEgreso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%@ include file= "menu.jsp" %>	
<%
	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String nombreAlumno 		= (String) request.getAttribute("nombreAlumno");
	
	List<AlumEgreso> lisEventos 				= (List<AlumEgreso>) request.getAttribute("lisEventos");
	HashMap<String,AlumEvento> mapEventos		= (HashMap<String,AlumEvento>) request.getAttribute("mapEventos");	
%>
<head>
	<script type="text/javascript">
		function confirmar(eventoId, confirmar){
			document.location.href = "confirmarAsistencia?EventoId="+eventoId+"&Confirmar="+confirmar;			
		}
	</script>
</head>
<body>
<div class="container-fluid">
	<h2>Eventos de graduación<small class="text-muted fs-6">( <%=codigoPersonal%>- <%=nombreAlumno%> )</h2>
	<div class="alert alert-info">
		<a href="resumen" class="btn btn-primary">Regresar</a>
	</div>		
<% 	
	int row=0;
	for(AlumEgreso evento : lisEventos){		
		String eventoFecha 		= "-";
		String eventoNombre		= "-";
		if (mapEventos.containsKey(evento.getEventoId())){
			eventoFecha 	= mapEventos.get(evento.getEventoId()).getFecha();
			eventoNombre	= mapEventos.get(evento.getEventoId()).getEventoNombre();
		}
		
		String fechaSeparada[] = eventoFecha.split("/");
		
		String dia 	= aca.util.Fecha.getDayName(fechaSeparada[2]+"/"+fechaSeparada[1]+"/"+fechaSeparada[0]);
		String mes 	= aca.util.Fecha.getMontName(fechaSeparada[2]+"/"+fechaSeparada[1]+"/"+fechaSeparada[0]);
				
		String colorSi = evento.getConfirmar().equals("S")?"btn-success":"btn-light";		
		String colorNo = evento.getConfirmar().equals("N")?"btn-danger":"btn-light";
%>
    <div class="card bg-light mb-3" style="max-width: 40rem;">
		<div class="card-header">
			<h5><%=eventoNombre%></h5>
		</div>
		<div class="card-body">
		<p style="font-size: 14px">
			<em><%=dia%> <%=fechaSeparada[2]%> de <%=mes%> de <%=fechaSeparada[0]%></em>
		</p>
		<p class="card-text"><%=evento.getComentario()%></p>					
		</div>
		<div class="card-footer bg-transparent border-success d-flex ">
			<strong>Asistiré a la graduación: </strong>&nbsp;&nbsp;
			<div class="rounded-pill border d-flex justify-content-between" style="width:100px">
				<a onclick="javascript:confirmar('<%=evento.getEventoId()%>','S');" data-bs-toggle="tooltip" data-bs-placement="left" class="btn <%=colorSi%> rounded-pill" title="¡Haz clic para confirmar tu asistencia al evento!">SI</a> 		    
				<a onclick="javascript:confirmar('<%=evento.getEventoId()%>','N');" data-bs-toggle="tooltip" data-bs-placement="right" class="btn <%=colorNo%> rounded-pill" title="¡Haz clic para quitar tu confirmación al evento!">NO</a>
			</div>
		</div>
	</div>
<% 	}%>

</div>
<script>	
	$(function () {
  		$('[data-bs-toggle="tooltip"]').tooltip();  		
	});
</script>
</body>