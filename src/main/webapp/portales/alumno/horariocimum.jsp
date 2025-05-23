<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.musica.spring.MusiHorario"%>
<%@page import="aca.util.Fecha"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ include file= "menu.jsp" %>
<%
	String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
	String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	String maestro 			= request.getParameter("CodigoMaestro")==null?"0":request.getParameter("CodigoMaestro");
	String diaHora			= request.getParameter("Dia")==null?"0":request.getParameter("Dia");
	String mensaje			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");

	String matricula 		= (String) session.getAttribute("codigoPersonal");
	String nombreAlumno		= (String) request.getAttribute("nombreAlumno");
	String nombreMaestro	= (String) request.getAttribute("nombreMaestro");
	float horasMat			= (float) request.getAttribute("horasMat");
	float horasReg			= (float) request.getAttribute("horasReg");

	List<MusiHorario> 	lisHorarios				= (List<MusiHorario>) request.getAttribute("lisHorarioMaestro");
	HashMap<String,String> mapaCupos			= (HashMap<String,String>) request.getAttribute("mapaCupos");
	HashMap<String,String> mapaDisponibles		= (HashMap<String,String>) request.getAttribute("mapaDisponibles");
	HashMap<String,String> mapaOcupados			= (HashMap<String,String>) request.getAttribute("mapaOcupados");
	HashMap<String,String> mapaOcupadosAlumno	= (HashMap<String,String>) request.getAttribute("mapaOcupadosAlumno");
	String[]semana = {"Domingo","Lunes","Martes","Miércoles","Jueves","Viernes","Sábado"};	
%>
<style>
</style>
<div class="container-fluid">
	<h3>Horario maestro<small class="text-muted fs-5">( Maestro:<%=nombreMaestro%> )</small></h3>
	<div class="alert alert-info">
		<a href="materias" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		Alumno: <%=nombreAlumno%>
	</div>
	<div style="margin: 20px 20px 0 20px;">
	<table class="table table-condensed" style="width:50%;">
	<tr>
		<th>#</th>	
		<th>Dia</th>
		<th>Cupo</th>
		<th>Disponible</th>			
	</tr>
<% 
	String diaNombre = "";
	for (int dia=1; dia<=7; dia++){
		String cupo = "0";
		if (mapaCupos.containsKey(String.valueOf(dia))){
			cupo = mapaCupos.get(String.valueOf(dia));
		}
		
		String disponibles = "0";
		if (mapaDisponibles.containsKey(String.valueOf(dia))){
			disponibles = mapaDisponibles.get(String.valueOf(dia));			
		}
		if (Integer.parseInt(disponibles)>=1) 
			disponibles = "<span class='badge bg-success'>"+disponibles+"</span>";
		else
			disponibles = "<span class='badge bg-warning'>"+disponibles+"</span>";
%>	
	<tr>
		<td><%=dia%></td>	
		<td><%=semana[dia-1]%></td>
		<td><%=cupo%></td>
		<td>
			<a href="horariocimum?CargaId=<%=cargaId%>&CodigoMaestro=<%=maestro%>&CursoCargaId=<%=cursoCargaId%>&Dia=<%=dia%>">
			<%=disponibles%>
			</a>
		</td>
	</tr>
<% 
	}		
%>			
	</table>
<%	if (!mensaje.equals("-")){ %>
	<div class="alert alert-info"><%=mensaje%></div>
<%	} %>
	<table class="table table-condensed" style="width:50%;">
<%	if (!diaHora.equals("0")){ %>
	<tr><td colspan="7"><h4>Horario del día: <%=semana[Integer.parseInt(diaHora)-1]%> &nbsp;&nbsp; Frecuencias <small class="text-muted fs-6">( Materia[<%=horasMat%>] &nbsp;&nbsp; Registradas[<%=horasReg%>] )</small></h4></td></tr>
	<tr>
		<th>#</th>	
		<th>Op.</th>
		<th>Hora Ini.</th>
		<th>Hora Fin</th>
		<th>Cupo</th>
		<th>Frecuencia</th>
		<th>Ocupado</th>
		<th>Disponible</th>
	</tr>
<%	} %>	
<% 
	int row = 0;
	for (MusiHorario horario : lisHorarios){
		row++;
		String ocupados = "0";
		if (mapaOcupados.containsKey(horario.getFolio())){
			ocupados = mapaOcupados.get(horario.getFolio());
		}
		int dis =  Integer.parseInt(horario.getCupo())-Integer.parseInt(ocupados);
		boolean grabado = false;
		if (mapaOcupadosAlumno.containsKey(horario.getFolio())){
			grabado = true;
		}
		
		//int numReg = 
%>	
	<tr>
		<td><%=row%></td>
		<td>
<%	if (dis>0 && grabado==false ){%>		
			<a href="grabarHorario?CargaId=<%=cargaId%>&CodigoMaestro=<%=maestro%>&Dia=<%=horario.getDia()%>&CursoCargaId=<%=cursoCargaId%>&Folio=<%=horario.getFolio()%>"><i class="fas fa-save"></i></a>
<%	}else if (grabado){%>
			<a href="borrarHorario?CargaId=<%=cargaId%>&CodigoMaestro=<%=maestro%>&Dia=<%=horario.getDia()%>&CursoCargaId=<%=cursoCargaId%>&Folio=<%=horario.getFolio()%>" style="color:red"><i class="fas fa-trash-o" ></i></a>
<%	}%>
		</td>
		<td><%=horario.getHoraInicio()%>:<%=horario.getMinInicio()%></td>
		<td><%=horario.getHoraFinal()%>:<%=horario.getMinFinal()%></td>
		<td><%=horario.getCupo()%></td>
		<td><%=horario.getValor()%></td>
		<td><%=ocupados%></td>
		<td><%=dis%></td>
	</tr>	
<% 	}%>	
	</table>
	</div>
</div>
<script src="../js/jquery-1.7.1.min.js"></script>
<script src="../jquery.isotope.min.js"></script>
<script src="../js/fake-element.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
