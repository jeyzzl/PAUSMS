<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.plan.spring.MapaNuevoPlan"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%
	String facultad 		= request.getParameter("Facultad")==null?"0":request.getParameter("Facultad");
	String filtroEstado		= request.getParameter("FiltroEstado")==null?"T":request.getParameter("FiltroEstado");

	String facultadNombre	= (String) request.getAttribute("facultadNombre");
	
	List<MapaPlan> lisPlan							= (List<MapaPlan>) request.getAttribute("lisPlanes");
	HashMap<String, CatCarrera> mapCarrera			= (HashMap<String, CatCarrera>) request.getAttribute("mapCarrera");

    int count = 0;
%>

<div class="container-fluid">
	<h2><b> <%=facultad%> - <%=facultadNombre%></b></h2>
    <div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="facultad"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
	</div>
    <table id="table" class="table table-sm table-bordered">		  	
    <thead class="table-info">
	  <tr> 
		<th><spring:message code="aca.Numero"/></th>
		<th><spring:message code="aca.Carrera"/> <spring:message code="aca.Clave"/></th>
		<th><spring:message code="aca.Plan"/> <spring:message code="aca.Clave"/></th>	
		<th><spring:message code="aca.Nombre"/></th>
		<th></th>
        <th></th>
	  </tr>
    </thead>
	<tbody>
<%      for(MapaPlan mplan : lisPlan){ 
            count++;
%> 
        <tr> 
            <td class="text-center"><%=count%></td>
	        <td class="text-center"><%=mplan.getCarreraId()%></td>
	        <td class="text-left"><%=mplan.getPlanId()%></td>
	        <td class="text-left"><%=mplan.getNombrePlan()%></td>
	        <td class="text-center"><a href="mayores?Facultad=<%=facultad%>&PlanId=<%=mplan.getPlanId()%>" class="btn btn-sm btn-primary"><b>MAJORS</b></a></td>
            <td class="text-center"><a href="menores?Facultad=<%=facultad%>&PlanId=<%=mplan.getPlanId()%>" class="btn btn-sm btn-primary"><b>MINORS</b></a></td>
	    </tr>
<%      }   %>
	</tbody>
	</table>			
</div>