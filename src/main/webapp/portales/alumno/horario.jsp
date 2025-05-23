<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page contentType="text/html; charset=iso-8859-1" pageEncoding="iso-8859-1"%>

<%@ include file="../../idioma.jsp"%>

<%@ page import="aca.carga.spring.CargaBloque"%>
<%@ page import="aca.catalogo.spring.CatSalon"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.catalogo.spring.CatHorarioFacultad"%>
<%@ page import="aca.objeto.spring.Hora"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Date"%>
<%@page import="aca.util.Fecha"%>
<%@page import="java.util.Calendar"%>

<%		
	String matricula 	= (String) request.getAttribute("matricula");	
	String cargaId 		= (String) request.getAttribute("cargaId");	
	
	String horarioId 	= (String) request.getAttribute("horarioId");	
	String bloqueId 	= (String) request.getAttribute("bloqueId");	
	String semTetra 	= (String) request.getAttribute("semTetra");
	
	List<CargaBloque> listaCargaBloque = (List<CargaBloque>) request.getAttribute("listaCargaBloque");	
		
	AlumPlan alumPlan 			= (AlumPlan) request.getAttribute("alumPlan");
	AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");
	
	List<String> lisHorarios 	= (List<String>) request.getAttribute("lisHorarios"); 
	List<String> turno 			= (List<String>) request.getAttribute("turno");
	List<String> turnoDif 		= (List<String>) request.getAttribute("turnoDif");
	
	HashMap<String, Hora> mapHorasDif 								= (HashMap<String, Hora>) request.getAttribute("mapHorasDif");
	HashMap<String, Hora> mapHoras 									= (HashMap<String, Hora>) request.getAttribute("mapHoras");
	HashMap<String, String> mapCursos 								= (HashMap<String, String>) request.getAttribute("mapCursos");
	HashMap<String, CatSalon> mapaSalones							= (HashMap<String, CatSalon>)request.getAttribute("mapaSalones");
	HashMap<String, List<CatHorarioFacultad>> mapaListaHorario 		= (HashMap<String, List<CatHorarioFacultad>>) request.getAttribute("mapaListaHorario");
	HashMap<String, List<CatHorarioFacultad>> mapaListaHorarioDif 	= (HashMap<String, List<CatHorarioFacultad>>) request.getAttribute("mapaListaHorarioDif");	
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<style>
		body{			
			/*font-size: 8pt;*/
		}
		.encabezado{
			font-size:10px;
		}
		.encabezado2{
			font-size:12px;
		}
		.encabezadoV {
			font-size: 7pt;
			font-size:11px;
		}
		.tabla td,th{
		}
	</style>
</head>
<%@ include file= "menu.jsp" %>	
<body>
<div class="container-fluid">
	<h4><%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno()%> <%=alumPersonal.getApellidoMaterno()%><small class="text-muted">( Schedule: <%=semTetra+" "+alumPlan.getCiclo()%> )</small></h4>
	<div class="alert alert-info d-flex justify-content-start align-items-center">
		<a href="materias" class="btn btn-primary btn-sm"><i class="fas fa-arrow-left"></i></a>
		&nbsp;&nbsp;Block:&nbsp;
<%	if(listaCargaBloque.size()>1){%>
		<select class="custom-select" onchange="document.location.href='horario?CargaId=<%=cargaId%>&BloqueId='+this.value;" class="input input-mini" style="width:77px;">
<%		for(CargaBloque cargaBloque : listaCargaBloque){%>
			<option value="<%=cargaBloque.getBloqueId()%>" <%=cargaBloque.getBloqueId().equals(bloqueId)?"Selected":""%>><%=cargaBloque.getBloqueId()%></option>
<%		}%>
		</select>
<%	}else{
		out.print(bloqueId);
	}
%>
	</div>	
	<table class="table table-bordered table-sm" style="width:100%">
	<thead class="table-info">
	<tr height="30px">
		<th width="3%">#</th>
		<th width="12%">Hour</th>
		<th width="12%">Sunday</th>
		<th width="12%">Monday</th>
		<th width="12%">Tuesday</th>
		<th width="12%">Wednesday</th>
		<th width="12%">Thursday</th>
		<th width="12%">Friday</th>
		<th width="12%">Saturday</th>
	</tr>
	</thead>
<%
		for(String tur : turno){
			if(tur.equals("1")){
				out.print("<tr><td colspan='9' align='center' class='th2'><h4>Morning</h4></td></tr>");				
			}else if(tur.equals("2")){
				out.print("<tr><td colspan='9' align='center' class='th2'><h4>Evening</h4></td></tr>");
			}else if(tur.equals("3")){
				out.print("<tr><td colspan='9' align='center' class='th2'><h4>Night</h4></td></tr>");
			}		
			
			List<CatHorarioFacultad> listHorario = new ArrayList<CatHorarioFacultad>();
				
			if(mapaListaHorario.containsKey(horarioId+tur)){
				listHorario = mapaListaHorario.get(horarioId+tur);
			}
			
			for(CatHorarioFacultad horario : listHorario){
%>
				<tr height="40px">
					<td align=center class="th4"><b><%=horario.getPeriodo()%></b></td>
					<td align="center" class="th4"><b><%=horario.getHoraInicio()%>:<%=horario.getMinutoInicio()%> - <%=horario.getHoraFinal()%>:<%=horario.getMinutoFinal()%></b></td>
<%				
				for(int j= 1; j<8; j++){
					
					// Buscar los datos de la materia
					if (mapHoras.containsKey(j+horario.getPeriodo())){
						Hora horaDatos = mapHoras.get(j+horario.getPeriodo());
						
						String nombreSalon = "-";
						if(mapaSalones.containsKey(horaDatos.getSalon())){
							nombreSalon = mapaSalones.get(horaDatos.getSalon()).getNombreSalon();
						}
						String materia	= "";
						if (mapCursos.containsKey(horaDatos.getCursoCargaId())){
							materia = mapCursos.get(horaDatos.getCursoCargaId());
						}						
						out.println("<td align='center' class='tr2'>"+materia+" ");
						out.println("<span data-bs-toggle='tooltip' data-bs-placement='top' style='font-size:0.7rem;' title='"+nombreSalon+"'>("+nombreSalon+")</span>");
						out.println("</td>");
					}else{
						out.println("<td align='center' class='tr2'>&nbsp;</td>");
					}				
				}
				// cierra el renglon
				out.print("</tr>");
			}
		}
%>
		</table>
		<br><br>
		<table style="width:70%" align="center"  class="table table-condesed table-bordered" id="table"> 
		<thead>
		<tr>
			<th width="10%">Hour</th>
			<th width="15%">Day</th>
			<th width="20%"><spring:message code="aca.Materia"/></th>
			<th width="15%">Class room</th>
		</tr>
		</thead>
<%   for(String horario : lisHorarios){
		if(!horario.equals(horarioId)){
			for(String tur : turnoDif){
				List<CatHorarioFacultad> listHorario = new ArrayList<CatHorarioFacultad>();
				
				if(mapaListaHorario.containsKey(horarioId+tur)){
					listHorario = mapaListaHorario.get(horarioId+tur);
				}
				
				for(CatHorarioFacultad hor : listHorario){
					for(int j= 1; j<8; j++){
						String dia = "";
						if(j==1){
							dia = "Sunday";
						}else if(j==2){
							dia = "Monday";
						}else if(j==3){
							dia = "Tuesday";
						}else if(j==4){
							dia = "Wednesday";
						}else if(j==5){
							dia = "Thursday";
						}else if(j==6){
							dia = "Friday";
						}else if(j==7){
							dia = "Saturday";
						}
							
						// Buscar los datos de la materia
						if (mapHorasDif.containsKey(j+hor.getPeriodo())){
							Hora horaDatos = mapHorasDif.get(j+hor.getPeriodo());
							
							String nombreSalon = "-";
							if(mapaSalones.containsKey(horaDatos.getSalon())){
								nombreSalon = mapaSalones.get(horaDatos.getSalon()).getNombreSalon();
							}
							
							String materia	= "";
							if (mapCursos.containsKey(horaDatos.getCursoCargaId())){
								materia = mapCursos.get(horaDatos.getCursoCargaId());
								out.println("<tr height='40px'>");
								out.println("<td align='center' class='tr2'>"+hor.getHoraInicio()+":"+hor.getMinutoInicio()+" - "+hor.getHoraFinal()+":"+hor.getMinutoFinal()+"</td>");
								out.println("<td align='center' class='tr2'>"+dia+"</td>");
								out.println("<td align='center' class='tr2'>"+materia+"</td>");
								out.println("<td align='center' class='tr2'>"+nombreSalon+"</td>");
								out.print("</tr>");
							}
						}			
					}
				}
			}
		}
	}
%>
	</table>
</div>		
</body>
</html>
<link rel="stylesheet" href="../../js/tablesorter/themes/blue/style.css" />
<script src="../../js/tablesorter/jquery.tablesorter.js"></script>
<script>
	jQuery('#table').tablesorter();
	$(function () {
  		$('[data-bs-toggle="tooltip"]').tooltip();
  		$('[data-bs-toggle="popover"]').popover();
	});
</script>