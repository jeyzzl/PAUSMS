<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	String facultad 					= request.getParameter("facultad");
	String facultadNombre 				= (String) request.getAttribute("facultadNombre");
	List<CatCarrera> lisCarreras 					= (List<CatCarrera>) request.getAttribute("lisCarreras");
	List<MapaPlan> lisPlanes 						= (List<MapaPlan>) request.getAttribute("lisPlanes");
	HashMap<String,String> mapaCoordinadores		= (HashMap<String,String>) request.getAttribute("mapaCoordinadores");
	HashMap<String,String> mapaCursosPorPlan		= (HashMap<String,String>) request.getAttribute("mapaCursosPorPlan");
	HashMap<String,String> mapaPracticas			= (HashMap<String,String>) request.getAttribute("mapaPracticas");
	HashMap<String,String> mapaPracticasCarreras	= (HashMap<String,String>) request.getAttribute("mapaPracticasCarreras");
	
	String sBgcolor		= "";
%>
<div class="container-fluid">
	<h2><%=facultadNombre%> School Courses</h2>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="facultad"><spring:message code="aca.Regresar"/></a>
		&nbsp; &nbsp;
		<input type="text" class="form-control search-query" placeholder="<spring:message code='aca.Buscar'/>" id="buscar" style="width:200px;">
	</div>				
	<table id="table" class="table table-sm table-bordered table-striped">
    <thead class="table-info">
		<tr>
			<th width="4%"><h5><spring:message code="aca.Numero"/></h5></th>
		    <th width="4%"><h5><spring:message code="aca.Clave"/></h5></th>
		    <th width="30%"><h5>Course</h5></th>
		    <th width="32%"><h5><spring:message code="aca.Planes"/></h5></th>
<!-- 		    <th width="5%"><h5>Labs</h5></th> -->
		    <th width="25%"><h5><spring:message code="aca.Coordinador"/></h5></th>
		</tr>
	</thead>	
<%
	int row = 0;
	for (CatCarrera carrera : lisCarreras){
		row++;		
		
		String empleadoNombre = ""; 
		if (mapaCoordinadores.containsKey( carrera.getCodigoPersonal())){
			empleadoNombre = mapaCoordinadores.get( carrera.getCodigoPersonal());
		}
		
		String practicas = "0";
		if (mapaPracticasCarreras.containsKey(carrera.getCarreraId())){
			practicas = mapaPracticasCarreras.get(carrera.getCarreraId());
		}
%>  
		<tr>
			<td align="center"><strong><%=row%></strong></td>
		    <td align="center"><%=carrera.getCarreraId()%></td>
		    <td>
		<%		if (carrera.getNombreCarrera().length()>70){
					out.print(carrera.getNombreCarrera().substring(0,69));
				}else{
					out.print(carrera.getNombreCarrera());
				}
		%>  </td>
			<td>
				<%
				for (MapaPlan plan : lisPlanes){
					if ( plan.getCarreraId().equals(carrera.getCarreraId())){
						
						String numCursos = "<span title='#Sub' class='badge bg-warning rounded-pill'>0</span>";
						if (mapaCursosPorPlan.containsKey(plan.getPlanId()) ){
							numCursos = "<span title='#Sub.' class='badge bg-dark rounded-pill'>"+mapaCursosPorPlan.get(plan.getPlanId())+"</span>";
						}
						String numPracticas = "0";								
						if (mapaPracticas.containsKey(plan.getPlanId()) ){
							numPracticas = mapaPracticas.get(plan.getPlanId());
						}
						String estilo = "<span title='Plan Key' class='badge bg-secondary rounded-pill'>"+plan.getPlanId()+"</span>";
						if (plan.getEstado().equals("A")) estilo = "<span title='Plan Key' class='badge bg-info rounded-pill'>"+plan.getPlanId()+"</span>";
						if (plan.getEstado().equals("V")) estilo = "<span title='Plan Key' class='badge bg-success rounded-pill'>"+plan.getPlanId()+"</span>";
						if (plan.getEstado().equals("I")) estilo = "<span title='Plan Key' class='badge bg-warning rounded-pill'>"+plan.getPlanId()+"</span>";
%>						
					
			<a href="materia?facultad=<%=facultad%>&planId=<%=plan.getPlanId()%>" style="text-decoration:none;"><%=estilo%>-<%=numCursos%></a>
		<%			}
				}
		%>  
			</td>
<%-- 			<td class="center"><%=practicas%></td> --%>
		    <td><%=empleadoNombre%></td>
		</tr>
		<%	} %>	
	</table>
</div>
<script src="../../js/search.js"></script>
<script>
	$('#buscar').search();
</script>