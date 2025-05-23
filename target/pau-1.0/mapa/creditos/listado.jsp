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
	HashMap<String, String> mapaCiclosRegistrados 	= (HashMap<String, String>) request.getAttribute("mapaCiclosRegistrados");
	
	String sBgcolor		= "";
	int cont			= 0;
%>
<div class="container-fluid">
	<h2><%=facultadNombre%> School</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="facultad"><spring:message code="aca.Regresar"/></a>
	</div>	        
	<table id="table" class="table table-bordered table-sm" style="width:100%">		  	
	<thead>
	<tr class="table-info">
	    <th width="4%"><spring:message code="aca.Numero"/></th>
	    <th width="4%"><spring:message code="aca.Clv"/></th>
	    <th width="35%"><spring:message code="aca.Nombre"/></th>
	    <th width="35%"><spring:message code="aca.Planes"/></th>
	    <th width="25%"><spring:message code="aca.Coordinador"/></th>
	</tr>
	</thead>
	<tbody>
<%
	for (int i=0; i< lisCarreras.size(); i++){
		cont++;
		CatCarrera carrera = (CatCarrera) lisCarreras.get(i);
		if ((cont % 2) == 1 );
		
		String coordinadorNombre = "-";
		if (mapaCoordinadores.containsKey(carrera.getCodigoPersonal())){
			coordinadorNombre = mapaCoordinadores.get(carrera.getCodigoPersonal());
		}
%>  
	<tr class="tr2" >
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
			for (int j=0; j< lisPlanes.size(); j++){
				MapaPlan plan = (MapaPlan) lisPlanes.get(j);
				if ( plan.getCarreraId().equals(carrera.getCarreraId())){
					
					String estilo = "<span class='badge bg-secondary rounded-pill'>"+plan.getPlanId()+"</span>";
					if (plan.getEstado().equals("A")) estilo = "<span class='badge bg-info rounded-pill'>"+plan.getPlanId()+"</span>";
					if (plan.getEstado().equals("V")) estilo = "<span class='badge bg-success rounded-pill'>"+plan.getPlanId()+"</span>";
					if (plan.getEstado().equals("I")) estilo = "<span class='badge bg-warning rounded-pill'>"+plan.getPlanId()+"</span>";
					
					String ciclosReg = "<span class='badge bg-warning'>0</span>";
					if (mapaCiclosRegistrados.containsKey(plan.getPlanId())){
						ciclosReg = "<span class='badge bg-dark'>"+mapaCiclosRegistrados.get(plan.getPlanId())+"</span>";
					}
	%>
	
		<a href="avance?facultad=<%=facultad%>&planId=<%=plan.getPlanId()%>" style="text-decoration:none;"><%=estilo%>-<%=ciclosReg%></a>&nbsp;&nbsp;&nbsp;
	<%			}
			}
	%>  
		</td>
	    <td><%=coordinadorNombre%></td>
	</tr>
<%	} %>
	</tbody>	
	</table>
</div>