<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.carga.spring.CargaPermiso"%>
<%@ page import="aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%
	String facultadId 		= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");
	String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
	String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
	String mensaje			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	String cargaNombre		= (String) request.getAttribute("cargaNombre");
	String facultadNombre	= (String) request.getAttribute("facultadNombre");
	
	List<MapaPlan> lisPlanes						= (List<MapaPlan>) request.getAttribute("lisPlanes");
	HashMap<String, CatFacultad> mapaFacultades 	= (HashMap<String, CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String, CatCarrera> mapaCarreras	 	= (HashMap<String, CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String, String> mapaPermisos 			= (HashMap<String, String>) request.getAttribute("mapaPermisos");
	HashMap<String, String> mapaMaterias 			= (HashMap<String, String>) request.getAttribute("mapaMaterias");		
%>
<div class="container-fluid">
	<h2>Authorized Plans and Courses <small class="text-muted fs-6">( <%=cargaId%> - <%=cargaNombre%> - <%=facultadNombre%> )</small></h2>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="facultad?PeriodoId=<%=periodoId%>&CargaId=<%=cargaId%>"><i class="fas fa-arrow-left"></i></a>&nbsp;
		<input type="text" class="form-control search-query" placeholder="Search..." id="buscar" style="width:250px;">&nbsp;&nbsp;
		<%= mensaje.equals("-")?"":mensaje%>
	</div>			  
	<form name="frmPermiso" method="post" action="grabar">
	<input type="hidden" name="FacultadId" value="<%=facultadId%>">
	<input type="hidden" name="CargaId" value="<%=cargaId%>">
	<table class="table table-bordered" id="table">
	<thead class="table-info">
		<tr> 
          	<th width="5%" class="text-center">
          		<spring:message code="cargasGrupos.permiso.Elegir"/>
          		<a onclick="jQuery('.checkboxMod').prop('checked', true)"><i class="fas fa-square"></i></a>&nbsp;
          		<a onclick="jQuery('.checkboxCarga').prop('checked', false)"><i class="far fa-square"></i></a>
          	</th>
          	<th width="10%" class="text-center"><spring:message code="aca.Plan"/></th>
          	<th width="65%" class="text-start"><spring:message code="aca.Curso"/></th>
          	<th width="10%"><spring:message code="aca.Carrera"/> ID</th>
          	<th width="10%" class="text-end"><spring:message code="aca.Materias"/></th>
    	</tr>
	</thead>
	<tbody>
<%
	int i= 0;	
	for (MapaPlan plan : lisPlanes){	
		
		String existe = "";
		if (mapaPermisos.containsKey(plan.getPlanId())){
			existe = "checked";
		}
		
		String materias = "0";
		if (mapaMaterias.containsKey(plan.getPlanId())){
			materias = mapaMaterias.get(plan.getPlanId());
		}
		
		String colorPlan = "bg-info";
		if (plan.getEstado().equals("V")) colorPlan = "bg-success"; 
		if (plan.getEstado().equals("I")) colorPlan = "bg-warning";
		
		String colorMaterias = "bg-secondary";		
		if (!existe.equals("checked") && !materias.equals("0")) 
			colorMaterias = "bg-warning";
		else if ( !materias.equals("0")) 
			colorMaterias = "bg-dark";			
%>
		<tr>
          	<td class="text-center"> 
				<input class="checkboxMod checkboxCarga" name="<%=plan.getPlanId()%>" type="checkbox" value="S" <%=existe%>>
<!-- 				<i class="fas fa-check-square"></i> -->
		  	</td>						  	
          	<td class="text-center"><span class="badge <%=colorPlan%> rounded-pill"><%=plan.getPlanId()%></span></td>
          	<td><%=plan.getNombrePlan() %></td>
          	<td><%=plan.getCarreraId()%></td>          	
          	<td class="text-end"><span class="badge <%=colorMaterias%> rounded-pill"><%=materias%></span></td>
        </tr>
<%	} %>	
	<tbody>	
	</table>
	<div class="alert alert-info">
		<input class="btn btn-primary" name="Grabar" type="submit" id="Grabar" value="<spring:message code="aca.Grabar"/>">		
	</div>
	</form>		
</div>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	$('#buscar').search();	
</script>
<!-- fin de estructura -->