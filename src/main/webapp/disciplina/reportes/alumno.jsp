<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import="aca.disciplina.spring.CondAlumno"%>
<%@ page import="aca.disciplina.spring.CondReporte"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%  
	String codigoAlumno 	= (String) session.getAttribute("codigoAlumno");
	String alumnoNombre		= (String) request.getAttribute("alumnoNombre");
	int numElogios			= 0;
	int numUnidades			= 0;
	int total 		 		= 0;
	int row			 		= 0;
	
	List<CondAlumno> lisRegistros 				= (List<CondAlumno>)request.getAttribute("lisRegistros");
	HashMap<String, String>	mapaJueces 			= (HashMap<String, String>) request.getAttribute("mapaJueces");
	HashMap<String, CondReporte> mapaReportes 	= (HashMap<String, CondReporte>) request.getAttribute("mapaReportes");
	HashMap<String, String>	mapaLugares			= (HashMap<String, String>) request.getAttribute("mapaLugares");
%>
<div class="container-fluid">
	<h2>Report by Student <small class="text-muted h5"> ( <%=codigoAlumno%> - <%=alumnoNombre%> ) </small></h2>
	<div class="alert alert-info">
		<a href="menu" class="btn btn-primary btn-sm"><i class="fas fa-arrow-left icon-white"></i></a>	
	</div>
	<table class="table table-sm">
	<tr>
    	<th><spring:message code="aca.Numero"/></th>
    	<th><spring:message code="aca.Fecha"/></th>
    	<th>Employee</th>
    	<th>Committee</th>
    	<th>Location</th>
    	<th><spring:message code='aca.Descripcion'/></th>
    	<th>Value</th>    
	</tr>
<%		
	for(CondAlumno registro : lisRegistros){
		row++;
		
		String reporteNombre	= "-";
		if (mapaReportes.containsKey(registro.getIdReporte())){			
			reporteNombre	= mapaReportes.get(registro.getIdReporte()).getNombre();
		}
		
		String juezNombre = "-";
		if (mapaJueces.containsKey(registro.getIdJuez())){
			juezNombre = mapaJueces.get(registro.getIdJuez());
		}		
		
		String lugarNombre = "-";
		if (mapaLugares.containsKey(registro.getIdLugar())){
			lugarNombre = mapaLugares.get(registro.getIdLugar());
		}
%>	
	<tr>	
		<td><%=registro.getFolio()%></td>
		<td><%=registro.getFecha()%></td>
   		<td><%=registro.getEmpleado()%></td>
   		<td><%=juezNombre%></td>
   		<td><%=lugarNombre%></td>
   		<td><%=reporteNombre%>(<%=registro.getComentario()%>)</td>
   		<td><%=registro.getCantidad()%></td>   		
	</tr>
<%
	}		
%>
	</table>
</div>