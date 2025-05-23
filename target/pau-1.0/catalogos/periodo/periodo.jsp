<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatPeriodo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar( periodoId ){
		if(confirm("<spring:message code="aca.JSEliminar"/>")==true){
	  		document.location="borrarPeriodo?PeriodoId="+periodoId;
	  	}
	}
</script>

<%	
	String periodoId 								= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
	List<CatPeriodo> lisPeriodo 					= (List<CatPeriodo>)request.getAttribute("lisPeriodo");	
	HashMap<String, CatPeriodo> cargasPorPeriodo 	= (HashMap<String, CatPeriodo>)request.getAttribute("cargasPorPeriodo");
%>

<html>
<div class="container-fluid">
	<h2><spring:message code="catalogos.periodos.Titulo"/></h2>
	<div class="alert alert-info">
		<a class="btn btn-success" href="editarPeriodo"><spring:message code="aca.Anadir"/></a>
	</div>
	<table class="table" width="100%">
	<tr> 
    	<th width="10%">Op.</th>
    	<th width="10%"><spring:message code="aca.PeriodoId"/></th>
    	<th width="15%"><spring:message code="aca.Nombre"/></th>
    	<th width="15%"><spring:message code="aca.FechaInicio"/></th>
    	<th width="15%"><spring:message code="aca.FechaFinal"/></th>
    	<th width="15%"><spring:message code="aca.Status"/></th>
    	<th width="15%"><spring:message code="catalogos.periodos.ExceptoPron"/></th>
  	</tr>
<%	
	for (CatPeriodo periodo : lisPeriodo){
		
%>
  	<tr> 
    	<td style="text-align: left"> 
      		<a href="editarPeriodo?PeriodoId=<%=periodo.getPeriodoId() %>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>&nbsp;	
      		<%
      		if(!cargasPorPeriodo.containsKey(periodo.getPeriodoId())){
      		%>
	    		<a class="fas fa-trash text-danger" title="<spring:message code="aca.Eliminar"/>" href="javascript:Borrar('<%=periodo.getPeriodoId()%>')" ></a>
	    	<% } %>
    	</td>
    	<td align="left"><%=periodo.getPeriodoId()%></td>   
    	<td align="left"><%=periodo.getNombre() %></td>
    	<td align="left"><%=periodo.getFechaIni() %></td>
    	<td align="left"><%=periodo.getFechaFin() %></td>
    	<td align="left"><%=periodo.getEstado().equals("I")?"Inactive":"Active"%></td>
    	<td align="left"><%=periodo.getExceptoPron() %></td>
  	</tr>
<%
	}
%>
	</table>
</div>
</body>
</html> 