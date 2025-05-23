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
	
	List<CatCarrera> lisCarreras					= (List<CatCarrera>)request.getAttribute("lisCarreras");
	List<MapaPlan> lisPlanes						= (List<MapaPlan>)request.getAttribute("lisPlanes");
	HashMap<String, String> mapaCoordinadores 		= (HashMap<String, String>) request.getAttribute("mapaCoordinadores");
	HashMap<String, String> mapaFunciones	 		= (HashMap<String, String>) request.getAttribute("mapaFunciones");
	HashMap<String,String> mapaPracticas			= (HashMap<String,String>) request.getAttribute("mapaPracticas");
	HashMap<String,String> mapaPracticasEnCarreras	= (HashMap<String,String>) request.getAttribute("mapaPracticasEnCarreras");	
	HashMap<String,String> mapaCursosPorPlan		= (HashMap<String,String>) request.getAttribute("mapaCursosPorPlan");
	
	int cont			= 0;
%>
<div class="container-fluid">
	<h3><%=facultadNombre%> Plans <small class="text-muted"> <!-- (<a href="planDatos?facultad=<%=facultad%>">Alineación DGP-SE</a>)  --></small></h3>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="facultad"><spring:message code="aca.Regresar"/></a>
		&nbsp; &nbsp;
		<input type="text" class="form-control search-query" placeholder="Search" id="buscar" style="width:200px;">
	</div>	
	<table id="table" class="table table-bordered table-sm table-striped">		  	
    <thead class="table-info">
		<tr>
	   		<th width="5%"><h5><spring:message code="aca.Numero"/></h5></th>
	   	 	<th width="5%"><h5>School <spring:message code="aca.Clv"/></h5></th>
	    	<th width="25%"><h5>Course</h5></th>
	    	<th width="25%"><h5><spring:message code="aca.Planes"/></h5></th>
	    	<th width="10%"><h5>Internships</h5></th>
	    	<th width="20%"><h5><spring:message code="aca.Coordinador"/></h5></th>
	    	<th width="10%"><h5>Cost</h5></th>
	    	<th width="10%"><h5>Function</h5></th>
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
		
		String funcion = "<span class='badge bg-warning'>X</span>";
	 		if (mapaFunciones.containsKey(carrera.getCcostoId())){
	 			funcion 	= mapaFunciones.get(carrera.getCcostoId());
	 		}
	 		
	 		String numPracticasCarrera = "<span class='badge bg-warning'>0</span>";
	 		if (mapaPracticasEnCarreras.containsKey(carrera.getCarreraId())){
			numPracticasCarrera = "<span class='badge bg-success'>"+mapaPracticasEnCarreras.get(carrera.getCarreraId())+"</span>";
		}						
%>  
		<tr>
		    <td align="center"><%=i+1%></td>
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
						String numCursos = "<span class='badge bg-warning rounded-pill'>0</span>";
						
						if (mapaCursosPorPlan.containsKey(plan.getPlanId())){
							 numCursos = "<span class='badge bg-dark rounded-pill'>"+mapaCursosPorPlan.get(plan.getPlanId())+"</span>";
						}
						 String estilo = "<span class='badge bg-secondary rounded-pill'>"+plan.getPlanId()+"</span>";
						if (plan.getEstado().equals("A")) estilo = "<span class='badge bg-info rounded-pill'>"+plan.getPlanId()+"</span>";
						if (plan.getEstado().equals("V")) estilo = "<span class='badge bg-success rounded-pill'>"+plan.getPlanId()+"</span>";
						if (plan.getEstado().equals("I")) estilo = "<span class='badge bg-warning rounded-pill'>"+plan.getPlanId()+"</span>";
		%>
			<a href="materia?facultad=<%=facultad%>&planId=<%=plan.getPlanId()%>" style="text-decoration:none;"> <%=estilo%>&nbsp;<%=numCursos%></a>&nbsp;&nbsp;&nbsp;
		<%			}
				}
		%>  
			</td>
			<td align="center"><%=numPracticasCarrera%></td>
		    <td><%=coordinadorNombre%></td>
		    <td><%=carrera.getCcostoId()%></td>
		    <td><%=funcion%></td>
		  </tr>
		<%	} %>
	</tbody>
	</table>			
</div>
<script src="../../js/search.js"></script>
<script>
	$('#buscar').search();
</script>