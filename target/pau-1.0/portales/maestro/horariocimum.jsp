<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.musica.spring.MusiHorario"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../body.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%
	String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
	String codigoMaestro	= (String) request.getAttribute("codigoPersonal");
	String nombreMaestro	= (String) request.getAttribute("maestroNombre");

	List<MusiHorario> 	lisHorarioMaestro		= (List<MusiHorario>) request.getAttribute("lisHorarioMaestro");
	HashMap<String,String> mapaDisponibles		= (HashMap<String,String>) request.getAttribute("mapaDisponibles");
	HashMap<String,String> mapaOcupados			= (HashMap<String,String>) request.getAttribute("mapaOcupados");
	String[]semana = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
%>
<style>
</style>
<div class="container-fluid">
	<h3>Professor's Schedule<small>( Professor: <%=nombreMaestro%> - Load: <%=cargaId%> )</small></h3>
	<div class="alert alert-info">
		<a href="cursos" class="btn btn-primary">Return</a>
	</div>
	<div style="margin: 20px 20px 0 20px;">
	<table class="table table-condensed" style="width:50%;">
	<tr>
		<th>#</th>
		<th>Day</th>
		<th>Start time</th>
		<th>End time</th>
		<th>Cap.</th>
		<th>Freq.</th>
		<th>Reserved</th>
		<th>Available</th>
	</tr>	
<%
	int row				= 0;
	for (MusiHorario horario : lisHorarioMaestro){
		row++;
		String ocupados = "0";
		if (mapaOcupados.containsKey(horario.getFolio())){
			ocupados 	= mapaOcupados.get(horario.getFolio());
		}
		int disponibles =  Integer.parseInt(horario.getCupo())-Integer.parseInt(ocupados);
%>
	<tr>
		<td><%=row%></td>
		<td><%=semana[Integer.parseInt(horario.getDia())-1]%></td>
		<td><%=horario.getHoraInicio()%>:<%=horario.getMinInicio()%></td>
		<td><%=horario.getHoraFinal()%>:<%=horario.getMinFinal()%></td>
		<td><%=horario.getCupo()%></td>
		<td><%=horario.getValor()%></td>
		<td><%=ocupados%></td>
		<td><%=disponibles%></td>
	</tr>	
<% 	}%>
	</table>
	</div>
</div>
<script src="../js/jquery-1.7.1.min.js"></script>
<script src="../jquery.isotope.min.js"></script>
<script src="../js/fake-element.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
