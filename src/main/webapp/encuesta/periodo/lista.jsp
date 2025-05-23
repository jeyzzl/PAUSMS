<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.encuesta.spring.EncPeriodo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%	
	String periodoId 					= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
	List<EncPeriodo> lisPeriodo 		= 	(List<EncPeriodo>)request.getAttribute("lisPeriodo");	
%>
<html>
<div class="container-fluid">
	<h2>Listado de Periodos</h2>
	<div class="alert alert-info">
		<a href="editarPeriodo" class="btn btn-primary btn-sm"><i class="fas fa-plus"></i></a>
	</div>
	<table class="table" width="100%">
	<tr> 
    	<th width="10%">Operaciones</th>
    	<th width="10%">Periodo Id</th>
    	<th width="15%">Nombre</th>
    	<th width="15%">Fecha Ini</th>
    	<th width="15%">Fecha Fin</th>
    	<th width="15%">Estado</th>
  	</tr>
<%	
	for (EncPeriodo periodo : lisPeriodo){
		
		
%>
  	<tr> 
  		<td style="text-align: left"> 
      		<a href="editarPeriodo?PeriodoId=<%=periodo.getPeriodoId() %>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>&nbsp;
    	</td>
		<td align="left"><%=periodo.getPeriodoId() %></td>
    	<td align="left"><%=periodo.getPeriodoNombre() %></td>
    	<td align="left"><%=periodo.getFechaIni() %></td>
    	<td align="left"><%=periodo.getFechaFin() %></td>
    	<td align="left"><%=periodo.getEstado().equals("I")?"Inactivo":"Activo"%></td>
  	</tr>
<%
	}
%>
	</table>
</div>
</body>
</html> 