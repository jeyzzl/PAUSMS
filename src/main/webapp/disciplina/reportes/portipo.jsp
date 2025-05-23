<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import="aca.disciplina.spring.CondAlumno"%>
<%@ page import="aca.disciplina.spring.CondReporte"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	String tipoTemp = "X";
	List<CondAlumno> lisRegistros 				= (List<CondAlumno>)request.getAttribute("lisRegistros");
	HashMap<String, String> mapaAlumnos 		= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
	HashMap<String, CondReporte> mapaReportes 	= (HashMap<String, CondReporte>) request.getAttribute("mapaReportes");
	HashMap<String, String>	mapaJueces 			= (HashMap<String, String>) request.getAttribute("mapaJueces");	
	HashMap<String, String>	mapaLugares			= (HashMap<String, String>) request.getAttribute("mapaLugares");
%>
<div class="container-fluid">
	<h2>Report by Type</h2>
	<div class="alert alert-info">
		<a href="menu" class="btn btn-primary btn-sm"><i class="fas fa-arrow-left icon-white"></i></a>
	</div>
<%	
	int row = 0;
	for(CondAlumno registro : lisRegistros){
		row++;
		String reporteNombre	= "-";
		String tipo 			= "-";
		if (mapaReportes.containsKey(registro.getIdReporte())){			
			reporteNombre	= mapaReportes.get(registro.getIdReporte()).getNombre();
			tipo 			= mapaReportes.get(registro.getIdReporte()).getTipo();
		}
		
		String juezNombre = "-";
		if (mapaJueces.containsKey(registro.getIdJuez())){
			juezNombre = mapaJueces.get(registro.getIdJuez());
			}
		
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(registro.getMatricula())){
			alumnoNombre = mapaAlumnos.get(registro.getMatricula());
		}
		
		String lugarNombre = "-";
		if (mapaLugares.containsKey(registro.getIdLugar())){
			lugarNombre = mapaLugares.get(registro.getIdLugar());
		}
		
		if (!registro.getIdReporte().equals(tipoTemp)){
			if (!tipoTemp.equals("X")) out.print("</table>");
			out.print("<div class='alert alert-info'>"+reporteNombre+"</div>");
			tipoTemp = registro.getIdReporte();
			
		
%>
	<table class="table table-sm">
	<tr> 
    	<th><spring:message code="aca.Numero"/></th>
    	<th><spring:message code="aca.Matricula"/></th>
    	<th><spring:message code="aca.Nombre"/></th>    	
    	<th>Committee</th>
    	<th>Location</th>
    	<th>Value</th>
	</tr>
<%			
		}	
%>	
	<tr> 
    	<td><%=row%></td>
    	<td><%=registro.getMatricula()%></td>
    	<td><%=alumnoNombre%></td>
    	<td><%=juezNombre%></td>
    	<td><%=lugarNombre %></td>
    	<td><%=registro.getCantidad()%></td>
  	</tr>
<%			
	}		
%>
	</table>
</div>