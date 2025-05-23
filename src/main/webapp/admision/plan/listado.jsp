<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String facultad 		= request.getParameter("facultad");
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String facultadNombre	= (String)request.getAttribute("facultadNombre");
	
	List<CatCarrera> lisCarreras				= (List<CatCarrera>)request.getAttribute("lisCarreras");
	List<MapaPlan> lisPlanes					= (List<MapaPlan>)request.getAttribute("lisPlanes");
	HashMap<String, String> mapaCoordinadores	= (HashMap<String, String>)request.getAttribute("mapaCoordinadores");	
	HashMap<String,String> mapaPlanNombre		= (HashMap<String, String>)request.getAttribute("mapaPlanNombre");
	int cont			= 0;
%>
<div class="container-fluid">
	<h2><%=facultadNombre%> Plans</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="facultad"><spring:message code="aca.Regresar"/></a>
	</div>
	<table class="table table-sm table-bordered table-striped" style="width:100%;">
    <thead class="table-info">
	<tr>
    	<th width="4%"><h5><spring:message code="aca.Numero"/></h5></th>
    	<th width="4%"><h5><spring:message code="aca.Clv"/></h5></th>
    	<th width="35%"><h5><spring:message code="aca.Nombre"/></h5></th>
    	<th width="35%"><h5><spring:message code="aca.Planes"/></h5></th>
    	<th width="25%"><h5><spring:message code="aca.Coordinador"/></h5></th>
  	</tr>
	</thead>
	<tbody>
<%
	int row = 0;
	for (CatCarrera carrera : lisCarreras){
		row++;
		
		String empleadoNombre = ""; 
		if (mapaCoordinadores.containsKey( carrera.getCodigoPersonal())){
			empleadoNombre = mapaCoordinadores.get( carrera.getCodigoPersonal());
		}
	%>  
	<tr>
		<td align="center"><strong><%=row%></strong></td>
	    <td align="center"><strong><%=carrera.getCarreraId()%></strong></td>
	    <td>
	    	<strong>
	<%		if (carrera.getNombreCarrera().length()>70){
				out.print(carrera.getNombreCarrera().substring(0,69));
			}else{
				out.print(carrera.getNombreCarrera());
			}
%>
			</strong>
		</td>
		<td>
<%
			String nombrePlan = "";
			String mapaPlanId = "";
			for (MapaPlan mplan : lisPlanes){
				
				if(mapaPlanNombre.containsKey(mplan.getPlanId())){
					nombrePlan = mapaPlanNombre.get(mplan.getPlanId());
				}
				
				if (mplan.getCarreraId().equals(carrera.getCarreraId())){ 
					String estilo = "<span class='badge bg-secondary rounded-pill'>"+mplan.getPlanId()+"</span>";
					if (mplan.getEstado().equals("A")) estilo = "<span class='badge bg-info rounded-pill'>"+mplan.getPlanId()+"</span>";
					
					if (mplan.getEstado().equals("V")) estilo = "<span class='badge bg-success rounded-pill'>"+mplan.getPlanId()+"</span>";
					
					if (mplan.getEstado().equals("I")) estilo = "<span class='badge bg-warning rounded-pill'>"+mplan.getPlanId()+"</span>";
%>
					<span data-bs-toggle="tooltip" title="<%=nombrePlan%>" data-bs-placement="right">
						<a href="accion?PlanId=<%=mplan.getPlanId()%>&FacultadId=<%=facultad%>">
							<%= estilo %>
						</a>
					</span>
<%				}
			}
%>  
		</td>
		<td>
			<strong><%=empleadoNombre%></strong>
		</td>
		</tr>
<%			
	}
%>
	</tbody>
	</table>
 </div>
 <script>	
	jQuery(function () {
   		jQuery('[data-bs-toggle="tooltip"]').tooltip(); 		
 	});
</script>