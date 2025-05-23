<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Date"%>

<%@page import= "aca.catalogo.spring.CatHorarioFacultad"%>
<%@page import= "aca.vista.spring.CargaAcademica"%>
<%@page import= "aca.vista.spring.CargaHorario"%>




<%@ include file="id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<html>
<head></head>
<%	
		String cargaId						= (String)request.getAttribute("cargaId");
		String planId						= (String)request.getAttribute("planId");
		String carreraId					= (String)request.getAttribute("carreraId");
		String ciclo						= (String)request.getAttribute("ciclo");
		
	
		List<String> lisTurnos 								= (List<String>)request.getAttribute("lisTurnos");
		List<CatHorarioFacultad> lisHorarios				= (List<CatHorarioFacultad>)request.getAttribute("lisHorarios");
		List<CargaHorario> lisHoras							= (List<CargaHorario>)request.getAttribute("lisHoras");
		
		HashMap<String,CargaAcademica> mapaMaterias			= (HashMap<String,CargaAcademica>)request.getAttribute("mapaMaterias");
%>
<body>
<div class="container-fluid">
	<h2>Timetable by Semester<small class="text-muted fs-5">( <%=cargaId%> - <%=planId%> )</small></h2>
	<form id="forma" name="forma" action="horarioSemestre" method='post'>
	<input name = "PlanId" type="hidden" value="<%=planId%>">
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="grupo?CarreraId=<%=carreraId%>&PlanId=<%=planId%>"><i class="fas fa-arrow-left"></i></a>
		&nbsp;&nbsp;		
		<strong>Semester:</strong>
		<select name="Ciclo" class="form-select" onchange="document.forma.submit()" style="width:250px;">
<%						
		for(int i = 1; i < 10; i++){			
			out.print("<option value='"+i+"' "+ (ciclo.equals(String.valueOf(i))?"selected":"")+">["+i+"] SEMESTER: "+i+"</option>");					
		}
%>		</select>
		&nbsp; &nbsp;		
		&nbsp;&nbsp;
	</div>		
	</form>	
	<table style="width:100%; margin: 0 auto;"  class="table table-bordered">
		<tr height="30px">
			<th width="5%"><h4><spring:message code="aca.Numero"/></h4></th>
			<th width="11%"><h4>Hour</h4></th>
			<th width="12%"><h4><spring:message code="aca.Domingo"/></h4></th>
			<th width="12%"><h4><spring:message code="aca.Lunes"/></h4></th>
			<th width="12%"><h4><spring:message code="aca.Martes"/></h4></th>
			<th width="12%"><h4><spring:message code="aca.Miercoles"/></h4></th>
			<th width="12%"><h4><spring:message code="aca.Jueves"/></h4></th>
			<th width="12%"><h4><spring:message code="aca.Viernes"/></h4></th>
			<th width="12%"><h4><spring:message code="aca.Sabado"/></h4></th>
		</tr>
<%			
		for(String turno : lisTurnos){			
				
			if(turno.equals("1")){
				out.print("<tr><td colspan='9' align='center' class='th2'><h4>Morning</h4></td></tr>");
			}else if(turno.equals("2")){
				out.print("<tr><td colspan='9' align='center' class='th2'><h4>Afternoon</h4></td></tr>");
			}else if(turno.equals("3")){
				out.print("<tr><td colspan='9' align='center' class='th2'><h4>Evening</h4></td></tr>");
			}
			
			for(CatHorarioFacultad horario : lisHorarios){
				if (turno.equals(horario.getTurno())){
%>
		<tr height="30px">
			<td align=center class="th4"><b><%=horario.getPeriodo()%></b></td>
			<td align="center" class="th4"><b><%=horario.getHoraInicio()%>:<%=horario.getMinutoInicio()%> - <%=horario.getHoraFinal()%>:<%=horario.getMinutoFinal()%></b></td>
<%
				for(int j= 1; j<8; j++){
					String nombreMaterias = "";
					for(CargaHorario hora: lisHoras){
						CargaAcademica clase = mapaMaterias.get(hora.getCursoCargaId());
						if (clase != null && hora.getDia().equals(String.valueOf(j)) && hora.getPeriodo().equals(horario.getPeriodo())){
							nombreMaterias+="<p style='margin-top:0px'><span class='badge bg-dark rounded-pill' title='"+clase.getCursoCargaId()+"-"+clase.getPlanId()+"'>"+clase.getCiclo()+"</span> "+clase.getNombreCurso()+"("+clase.getGrupo()+")</p> ";
						}
					}
					
				%>
			<td class="text-center"><%=nombreMaterias%></td>
					
<%									
				}			
%>
		</tr>
<%		
				}
			}
		}			
%>	
	</table>
</div>	
</body>
</html>