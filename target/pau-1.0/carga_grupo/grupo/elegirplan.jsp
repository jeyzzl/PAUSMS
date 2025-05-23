<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%	
	//String sCodigoPersonal		= (String) session.getAttribute("codigoPersonal");
	
	String cursoCargaId 	= (String)session.getAttribute("cursoCargaId");
	String carreraId 		= (String)session.getAttribute("carreraId");	
	String planId 		    = (String)session.getAttribute("planId");
	
	String nombreMateria 	= (String) request.getAttribute("nombreMateria");
	
	// Lista de carreras
	List<CatCarrera> lisCarreras 				= (List<CatCarrera>)request.getAttribute("lisCarreras");	
	// Lista de planes
	List<MapaPlan> lisPlanes 					= (List<MapaPlan>)request.getAttribute("lisPlanes");
	
	HashMap<String,CatFacultad> mapaFacultades	= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
%>		
<div class="container-fluid">
	<h2>Select a Plan</h2>	
	<div class="alert alert-info d-flex align-items-center">	
		<a class="btn btn-primary" href="equivalente?CursoCargaId=<%=cursoCargaId%>&CarreraId=<%=carreraId%>&PlanId=<%=planId %>"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		<input type="text" class="search-query form-control" placeholder="Search..." id="buscar" style="width:200px;">
	</div>
	
	<table id="table" class="table table-bordered">
<%
	String facultad = "X";
	String nombreFacultad = "-";
	int row=0;
	for (CatCarrera carrera : lisCarreras){
		row++;
		if (!carrera.getFacultadId().equals(facultad)){
			facultad = carrera.getFacultadId();
			if (mapaFacultades.containsKey(facultad)){
				nombreFacultad = mapaFacultades.get(facultad).getNombreFacultad();
			}
			row=1;
%>	
		<thead>
		<tr class="alert alert-info"> 
			<td colspan="7"><h4><%=nombreFacultad%></h4></td>
		</tr>
		<tr>			
			<td width="7%"><h5>#</h5></th>
			<td width="7%"><h5><spring:message code="aca.Clave"/></h5></td>
			<td width="36%"><h5><spring:message code="aca.Nombre"/></h5></td>
			<td width="50%"><h5><spring:message code="aca.Planes"/></h5></td>
		</tr>			
		</thead>
<%		}%>				
			
			<tr>	
				<td align="center"><%=row%></td>				
				<td align="center"><%=carrera.getCarreraId()%></td>
				<td><%=carrera.getNombreCarrera()%></td>
				<td class="text-start">
<%	
		for (MapaPlan plan : lisPlanes){
			if ( carrera.getCarreraId().equals( plan.getCarreraId() ) ){
%>
				<a href="elegirmateria?CursoCargaId=<%=cursoCargaId%>&CarreraId=<%=carreraId%>&PlanId=<%=planId%>&PlanElegir=<%=plan.getPlanId()%>"><%=plan.getPlanId()%>&nbsp;&nbsp;</a>
<%				
			}
		}	
%>					
				</td>									
			</tr>
<%	
	}
%>			
	</table>		
</div>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>
