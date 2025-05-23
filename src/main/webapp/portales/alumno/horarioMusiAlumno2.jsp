<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.musica.spring.MusiHorario"%>
<%@page import="aca.util.Fecha"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../body.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@ include file= "menu.jsp" %>
<%
	String matricula 		= (String) session.getAttribute("codigoAlumno");

	String 	nombreAlumno	= (String) request.getAttribute("nombreAlumno");
	String 	nombreMaestro	= (String) request.getAttribute("nombreMaestro");

	List<MusiHorario> 	lisHorarioMaestro		= (List<MusiHorario>) request.getAttribute("lisHorarioMaestro");
	HashMap<String,String> mapHorarioMusiAlumno	= (HashMap<String,String>) request.getAttribute("mapHorarioMusiAlumno");
			
	String[]semana = {"Domingo","Lunes","Martes","Miércoles","Jueves","Viernes","Sábado"};
%>
<style>
</style>
<div class="container-fluid">
	<h2>Horario maestro<small class="text-muted fs-4">( Maestro:<%=nombreMaestro%> )</small></h2>
	<div class="alert alert-info">
		<a href="materias" class="btn btn-primary"><i class="fas fa-arrow-left"></i> Regresar</a>
	</div>	
	<div style="margin: 20px 20px 0 20px;">
	<form name="forma" action="grabaHorarioMusiAlumno">
		<div class="row">
<%
		int inicia = 0;
		
		for(aca.musica.spring.MusiHorario horario : lisHorarioMaestro){
			if(inicia != Integer.parseInt(horario.getDia())){
%>
			<div class="col-md-2">
				<h3><%=semana[Integer.parseInt(horario.getDia())-1]%></h3>	
<%
			}	
%>
				<%=horario.getHoraInicio()+":"+horario.getMinInicio()+" - "+horario.getHoraFinal()+":"+horario.getMinFinal()%>
<%  			if(!mapHorarioMusiAlumno.containsKey(horario.getFolio()) || mapHorarioMusiAlumno.get(horario.getFolio()).equals(matricula)){%>
				<input type="checkbox" name="Folio" value="<%=horario.getFolio()%>">
<%  			}%>
				<br>
<%
			inicia = Integer.parseInt(horario.getDia());
			if(inicia != Integer.parseInt(horario.getDia())){
%>
			</div>
<%
			}
		}
%>
		</div>
	</form>
	</div>
</div>
<script src="../js/jquery-1.7.1.min.js"></script>
<script src="../jquery.isotope.min.js"></script>
<script src="../js/fake-element.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
