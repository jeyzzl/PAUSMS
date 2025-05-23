<%@page import="aca.cita.spring.CitaPeriodo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../body.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<%
	String codigoPersonal 		= (String)session.getAttribute("codigoPersonal");
	String eventoId				= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
	String nombreAlumno			= (String)request.getAttribute("nombreAlumno");
	String nombreEvento			= (String)request.getAttribute("nombreEvento");
	boolean existeReservacion	= (boolean)request.getAttribute("existeReservacion");
	
	int maximo					= (int)request.getAttribute("maximo");
	java.util.HashMap<String,String> mapaCupo 		= (java.util.HashMap<String,String>) request.getAttribute("mapaCupo");
	java.util.HashMap<String,String> mapaOcupados 	= (java.util.HashMap<String,String>) request.getAttribute("mapaOcupados");	
	java.util.HashMap<String,String> mapaAlumno 	= (java.util.HashMap<String,String>) request.getAttribute("mapaAlumno");
%>
<div class="container-fluid">
	<h3>
		Reservación 
		<small class="text-muted fs-5">( <%=codigoPersonal%>- <%=nombreAlumno%> - <%=nombreEvento%>)</small>&nbsp;
	</h3>
	<div class="alert alert-info">
		<a href="citaMedica" class="btn btn-primary">Regresar</a>
	</div>		 
	<table class="table">
	<tr>
		<th>#</th>	
		<th>Domingo</th>
		<th>Lunes</th>
		<th>Martes</th>
		<th>Miércoles</th>
		<th>Jueves</th>
		<th>Viernes</th>
		<th>Sábado</th>
	</tr>
<%		
	for (int i=1; i <= maximo; i++){
		out.print("<tr>");
		out.print("<td>"+i+"</td>");
		for (int z=1; z<8; z++){
			
			int cupo = 0;
			if (mapaCupo.containsKey(eventoId+z+i)){
				cupo = Integer.parseInt(mapaCupo.get(eventoId+z+i));
			}
			
			int ocupados = 0;			
			if (mapaOcupados.containsKey(eventoId+z+i)){
				ocupados = Integer.parseInt(mapaOcupados.get(eventoId+z+i));
			}
			
			String estado = "X";
			if (mapaAlumno.containsKey(eventoId+z+i+codigoPersonal)){
				estado = mapaAlumno.get(eventoId+z+i+codigoPersonal);
			} 
			
			int disponibles = cupo-ocupados; 
			String cita = "";
			if  (disponibles == 0) cita =  "<span class='label label-pill label-warning'>X</span>";
			if  (disponibles > 0) cita =  "<span class='label label-pill label-success'>"+disponibles+"</span>";
			if  (estado.equals("A")) cita =  "<span class='label label-pill label-inverse'>"+disponibles+"</span>";			
%>
	<td>
<%
			if (existeReservacion){
				if (estado.equals("A")){
%>
					<a href="javascript:Cancelar('<%=eventoId%>','<%=i%>','<%=z%>')"><%=cita%></a>
<%					
				}else{
					out.print(cita);
				}
			}else{
				if (disponibles > 0){
%>
					<a href="javascript:Grabar('<%=eventoId%>','<%=i%>','<%=z%>')"><%=cita%></a>
<%				
				}else{
					out.print(cita);
				}
			}
%>		
	</td>
<%		
		}
		out.print("</tr>");
	}
%>	
	
	</table>
</div>
<script type="text/javascript">

	function Grabar(eventoId,periodo,dia){
		if (confirm("¿Estas seguro de reservar este horario?")){
			location.href="reservar?EventoId="+eventoId+"&PeriodoId="+periodo+"&Dia="+dia;
		}
	}
	
	function Cancelar(eventoId,periodo,dia){
		if (confirm("¿Estas seguro de cancelar este horario?")){
			location.href="cancelar?EventoId="+eventoId+"&PeriodoId="+periodo+"&Dia="+dia;
		}
	}
</script>