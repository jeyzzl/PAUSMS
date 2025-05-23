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
	HashMap<String,String> mapaCupo				= (HashMap<String,String>) request.getAttribute("mapCupo");
			
	//String[]semana = {"Domingo","Lunes","Martes","Miércoles","Jueves","Viernes"};
%>
<style>
</style>
<div class="container-fluid">
	<h3>Horario maestro<small class="text-muted fs-5">( Maestro:<%=nombreMaestro%> )</small></h3>
	<div class="alert alert-info">
		<a href="materias" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		Alumno:<%=nombreAlumno%>
	</div>
	<div style="margin: 20px 20px 0 20px;">
	<table class="table table-condensed">
	<tr>
		<th>#</td>	
		<th>Dia</td>
		<th>Cupo</td>
		<th>Disponible</td>			
	</tr>
<% 
	String diaNombre = "";
	for (int dia=1; dia<=7; dia++){		
		if (dia==1) diaNombre = "Domingo";		
		if (dia==1) diaNombre = "Lunes";
		if (dia==1) diaNombre = "Martes";
		if (dia==1) diaNombre = "Miércoles";
		if (dia==1) diaNombre = "Jueves";
		if (dia==1) diaNombre = "Viernes";
		if (dia==1) diaNombre = "Sábado";
		
		String cupo = "0";
		if (mapaCupo.containsKey(String.valueOf(dia))){
			cupo = mapaCupo.get(String.valueOf(dia));
		}
%>	
	<tr>
		<td><%=dia%></td>	
		<td><%=diaNombre%></td>
		<td><%=cupo%></td>
		<td>0</td>		
	</tr>
<% 
	}		
%>			
	</table>	
	</div>
</div>
<script src="../js/jquery-1.7.1.min.js"></script>
<script src="../jquery.isotope.min.js"></script>
<script src="../js/fake-element.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
