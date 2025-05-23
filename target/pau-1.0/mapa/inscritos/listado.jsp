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
	String cargaId						= (String)request.getAttribute("cargaId");
	String nombreCarga					= (String)request.getAttribute("nombreCarga");
	String facultadNombre 				= (String) request.getAttribute("facultadNombre");
	List<CatCarrera> lisCarreras 				= (List<CatCarrera>) request.getAttribute("lisCarreras");
	List<MapaPlan> lisPlanes 					= (List<MapaPlan>) request.getAttribute("lisPlanes");
	HashMap<String,String> mapaCoordinadores	= (HashMap<String,String>) request.getAttribute("mapaCoordinadores");	
%>
<div class="container-fluid">
	<h3><%=facultadNombre%> <small class="text-muted fs-5">( <%=cargaId%> - <%=nombreCarga%> )</small></h3>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="facultad"><spring:message code="aca.Regresar"/></a>
		&nbsp; &nbsp;
		<input type="text" class="form-control search-query" placeholder="Search..." id="buscar" style="width:200px;">
	</div>			
	<table id="table" class="table table-bordered table-sm">		  	
	<thead class="table-info">
		<tr>
			<th width="5%"><h5><spring:message code='aca.Numero'/></h5></th>
			<th width="4%"><h5><spring:message code="aca.Clave"/></h5></th>
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
    <td align="center"><%=row%></td>
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
				
				String estilo = "<span class='badge bg-secondary rounded-pill'>"+plan.getPlanId()+"</span>";
				if (plan.getEstado().equals("A")) estilo = "<span class='badge bg-info rounded-pill'>"+plan.getPlanId()+"</span>";
				if (plan.getEstado().equals("V")) estilo = "<span class='badge bg-success rounded-pill'>"+plan.getPlanId()+"</span>";
				if (plan.getEstado().equals("I")) estilo = "<span class='badge bg-warning rounded-pill'>"+plan.getPlanId()+"</span>";
		%>
	<a href="materia?facultad=<%=facultad%>&planId=<%=plan.getPlanId()%>"><%=estilo%></a>&nbsp;&nbsp;&nbsp;
<%			}
		}
%>  
	</td>
    <td><%=empleadoNombre%></td>
  </tr>
<%	} %>
	</tbody>
	</table>	
</div>
<script src="../../js/search.js"></script>
<script>
	$('#buscar').search();
</script>