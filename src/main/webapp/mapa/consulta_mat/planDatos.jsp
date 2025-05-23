
<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	String facultadId 				= request.getParameter("facultad")==null?"0":request.getParameter("facultad");
	String facultadNombre			= (String)request.getAttribute("facultadNombre");
	
	List<MapaPlan> lisPlanes					= (List<MapaPlan>)request.getAttribute("lista");
	HashMap<String, CatCarrera> mapaCarreras	= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarrera");
%>
<div class="container-fluid">
<h2>Plans <small class="text-muted fs-4">( <%=facultadNombre%> )</small></h2>
<div class="alert alert-info">
<% 	if (facultadId.equals("0")){%>
	<a class="btn btn-primary" href="facultad"><spring:message code="aca.Regresar"/></a>
<% 	}else{%>
	<a class="btn btn-primary" href="listado?facultad=<%=facultadId%>"><spring:message code="aca.Regresar"/></a>
<% 	}%>
</div>
<table class="table table-sm table-bordered ">
	<tr align="center">	
		<th text-align=center width="3%">#<th>
		<th width="3%">Status<th>		
		<th width="5%">Official<th>
		<th width="5%">Modality<th>
		<th width="5%">Degree Id<th>
		<th width="5%">Plan<th>
		<th width="5%">Plan SE<th>
		<th width="7%">RVOE<th>
		<th width="5%">DGP<th>
		<th width="20%">Degree SE<th>
		<th width="20%">Degree (H)<th>
		<th width="20%">Degree (M)<th>		
	</tr>
<%
	int row = 0;
	for (MapaPlan plan : lisPlanes){
		row++;
		
		String nombreCarrera = "-";
		if (mapaCarreras.containsKey(plan.getCarreraId()) ){
			nombreCarrera = mapaCarreras.get(plan.getCarreraId()).getNombreCarrera();
		}
		
		String estado = "-";
		if (plan.getEstado().equals("A")) estado = "<span class='badge bg-dark'>Enrollment</span>";
		if (plan.getEstado().equals("V")) estado = "<span class=' badge bg-success'>Current</span>";
		if (plan.getEstado().equals("I")) estado = "<span class=' badge bg-warning'>Inactive</span>";
		
		String oficial = "N";
		if (plan.getOficial().equals("S")) oficial = "Formal";
		if (plan.getOficial().equals("N")) oficial = "Informal";
		if (plan.getOficial().equals("I")) oficial = "Languajes";
		if (plan.getOficial().equals("C")) oficial = "CIMUM M.";
		if (plan.getOficial().equals("A")) oficial = "CIMUM A.";
		
		String modalidad = "X";
		if (plan.getEnLinea().equals("S")) modalidad = "Online";
		if (plan.getEnLinea().equals("N")) modalidad = "In Person";
		if (plan.getEnLinea().equals("M")) modalidad = "Mixed";
%>
	<tr class="tr2">
		<td><%=row%><td>
		<td text-align="center"><%=estado%><td>
		<td><%=oficial%><td>
		<td><%=modalidad%><td>
		<td><%=nombreCarrera %><td>
		<td><%=plan.getPlanId() %><td>
		<td><%=plan.getPlanSE() %><td>
		<td><%=plan.getRvoe() %><td>
		<td><%=plan.getClaveProfesiones() %><td>
		<td><%=plan.getCarreraSe() %><td>
		<td><%=plan.getNombrePlan() %><td>
		<td><%=plan.getNombrePlanMujer() %><td>
	</tr>	
<% 	}%>
</table>
</div>