<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	String facultad 							= request.getParameter("facultad");
	String facultadNombre 						= (String) request.getAttribute("facultadNombre");
	List<CatCarrera> lisCarreras 				= (List<CatCarrera>) request.getAttribute("lisCarreras");
	List<MapaPlan> lisPlanes 					= (List<MapaPlan>) request.getAttribute("lisPlanes");
	HashMap<String,String> mapaCoordinadores	= (HashMap<String,String>) request.getAttribute("mapaCoordinadores");
	HashMap<String,String> mapaAlumnosEnPlan	= (HashMap<String,String>) request.getAttribute("mapaAlumnosEnPlan");
	HashMap<String,String> mapaPromediosCarr	= (HashMap<String,String>) request.getAttribute("mapaPromediosCarr");
	
	java.text.DecimalFormat getFormato 			= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	String sBgcolor		= "";
%>
<div class="container-fluid">
	<h2><%=facultadNombre%></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="facultad"><i class="fas fa-arrow-left"></i></a>
	</div>	
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
		<th width="4%"><spring:message code='aca.Numero'/></th>
		<th width="4%"><spring:message code="aca.Clave"/></th>
		<th width="25%"><spring:message code="aca.Nombre"/></th>
		<th width="35%"><spring:message code="aca.Planes"/></th>
		<th width="20%"><spring:message code="aca.Coordinador"/></th>
		<%-- <th width="15%" class="right">Promedio</th> --%>
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
  <tr <%=sBgcolor%>>
    <td align="center"><strong><%=row%></strong></td>
    <td align="center"><strong><%=carrera.getCarreraId()%></strong></td>
    <td><strong>
<%		if (carrera.getNombreCarrera().length()>70){
			out.print(carrera.getNombreCarrera().substring(0,69));
		}else{
			out.print(carrera.getNombreCarrera());
		}
%>  </strong></td>
	<td>
<%
		for (MapaPlan plan : lisPlanes){
			if ( plan.getCarreraId().equals(carrera.getCarreraId())){
				String numAlumnos = "<span class='badge bg-warning'>0</span>";
				if (mapaAlumnosEnPlan.containsKey(plan.getPlanId()) ){
					numAlumnos = "<span class='badge bg-dark'>"+mapaAlumnosEnPlan.get(plan.getPlanId())+"</span>";
				}
%>							
		<a href="alumnos?facultad=<%=facultad%>&planId=<%=plan.getPlanId()%>" class="text-decoration-none"><%=plan.getPlanId()%> <%=numAlumnos%></a>&nbsp;&nbsp;&nbsp;
<%			}
		}
		
		float promedio = 0;
		if(mapaPromediosCarr.containsKey(carrera.getCarreraId())){
			promedio = Float.valueOf(mapaPromediosCarr.get(carrera.getCarreraId()));
		}		
%>  
	</td>
    <td><strong><%=empleadoNombre%></strong></td>
    <%-- <td class="right"><strong><%=getFormato.format(promedio)%></strong></td> --%>
  </tr>
<%	} %>
	</tbody>
	</table>			
</div>