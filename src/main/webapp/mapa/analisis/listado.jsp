<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	String facultad 				= request.getParameter("facultad")==null?"0":request.getParameter("facultad");
	String facultadNombre			= (String)request.getAttribute("facultadNombre");
	
	HashMap<String,String> mapaPracticasCarreras	= (HashMap<String,String>) request.getAttribute("mapaPracticasCarreras");
	List<CatCarrera> lisCarreras					= (List<CatCarrera>)request.getAttribute("lisCarreras");
	List<MapaPlan> lisPlanes						= (List<MapaPlan>)request.getAttribute("lisPlanes");
	HashMap<String, String> mapaCoordinadores 		= (HashMap<String, String>) request.getAttribute("mapaCoordinadores");	
	HashMap<String,String> mapaCursosPorPlan		= (HashMap<String,String>) request.getAttribute("mapaCursosPorPlan");
	
	int cont			= 0;
%>
<div class="container-fluid">
	<h3><spring:message code="aca.Planes"/> <spring:message code="aca.De"/>: <%=facultadNombre%></h3>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="facultad"><spring:message code="aca.Regresar"/></a>
		&nbsp; &nbsp;
		<input type="text" class="form-control search-query" placeholder="Search..." id="buscar" style="width:200px;">
	</div>		
  	<table id="table" class="table table-sm table-bordered table-striped">		  	
    <thead class="table-info">
		<tr>
	    	<th width="4%"><h5><spring:message code="aca.Numero"/></h5></th>
	    	<th width="4%"><h5><spring:message code="aca.Clv"/></h5></th>
	    	<th width="35%"><h5>Course</h5></th>
	    	<th width="35%"><h5><spring:message code="aca.Planes"/></h5></th>
	    	<th width="25%"><h5><spring:message code="aca.Coordinador"/></h5></th>
		</tr>
	</thead>
	<tbody>
<%
	for (int i=0; i< lisCarreras.size(); i++){
		cont++;
		CatCarrera carrera = (CatCarrera) lisCarreras.get(i);	
		
		String coordinadorNombre = "-";
		if (mapaCoordinadores.containsKey(carrera.getCodigoPersonal())){
			coordinadorNombre = mapaCoordinadores.get(carrera.getCodigoPersonal());
		}
		String practicas = "0";
		if (mapaPracticasCarreras.containsKey(carrera.getCarreraId())){
			practicas = mapaPracticasCarreras.get(carrera.getCarreraId());
		}
%>  
		<tr>
		    <td align="center"><strong><%=i+1%></strong></td>
		    <td align="center"><strong><%=carrera.getCarreraId()%></strong></td>
		    <td><strong>
<%		if (carrera.getNombreCarrera().length()>70){
			out.print(carrera.getNombreCarrera().substring(0,69));
		}else{
			out.print(carrera.getNombreCarrera());
		}
%>				</strong>
			</td>
			<td>
			<%
				for (MapaPlan plan : lisPlanes){
					if ( plan.getCarreraId().equals(carrera.getCarreraId())){
						
						String numCursos = "<span class='badge bg-warning rounded-pill'>0</span>";
						if (mapaCursosPorPlan.containsKey(plan.getPlanId())){
							 numCursos = "<span class='badge bg-dark rounded-pill'>"+mapaCursosPorPlan.get(plan.getPlanId())+"</span>";
						}
						String estilo = "<span class='badge bg-secondary rounded-pill'>"+plan.getPlanId()+"</span>";
						if (plan.getEstado().equals("A")) estilo = "<span class='badge bg-info rounded-pill'>"+plan.getPlanId()+"</span>";
						if (plan.getEstado().equals("V")) estilo = "<span class='badge bg-success rounded-pill'>"+plan.getPlanId()+"</span>";
						if (plan.getEstado().equals("I")) estilo = "<span class='badge bg-warning rounded-pill'>"+plan.getPlanId()+"</span>";
%>						
						<a href="materias?facultad=<%=facultad%>&planId=<%=plan.getPlanId()%>" style="text-decoration:none;"><%=estilo%>&nbsp;<%=numCursos%></a>&nbsp;&nbsp;&nbsp;

		<%			}
				}
		%>  		 
			</td>
		    <td><strong><%=coordinadorNombre%></strong></td>
		</tr>
<%	} %>
	</tbody>
	</table>
</div>
<script src="../../js/search.js"></script>
<script>
	$('#buscar').search();
</script>