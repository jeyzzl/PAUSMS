<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.acceso.spring.Acceso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%
	String codigo			= (String)request.getAttribute("codigoPersonal");
	String cargaId			= (String)request.getAttribute("cargaId");

	int cont 				= 1;
	int nInscritos 			= 0, nCalculos 		= 0;
	int nHombres			= 0, nMujeres		= 0;
	int nInternos			= 0, nExternos 		= 0;
	int nNacional			= 0, nExtranjero	= 0;
	
	Acceso acceso = (Acceso)request.getAttribute("acceso");
	
	/* Lista de planes de estudio*/
	List<CatCarrera> lisCarreras 	= (List<CatCarrera>)request.getAttribute("lisCarreras");
	
	/* Lista de planes de estudio*/
	List<MapaPlan> lisPlanes 	= (List<MapaPlan>)request.getAttribute("lisPlanes");
	
	/* HashMap de los nombres de las facultades */
	HashMap<String,CatFacultad> mapFacultad = (HashMap<String,CatFacultad>)request.getAttribute("mapFacultad");	
%>
<div class="container-fluid">
<h1>Lista de Planes de Estudio <a class="btn btn-primary" href="alumnosPorYear">Buscar por año</a></h1>
	<hr>
	<table class="table table-bordered">
<%	
	String facultad			= "";
	String tempFacultad		= "";
	String nombreFacultad	= "";
	String tempCarrera		= "";
	String nombreCarrera	= "";
	String tempPlan			= "";	
	
	int row = 0;
	for( CatCarrera carrera : lisCarreras){
		row++;		
		facultad 	= carrera.getFacultadId();		
		// Si tiene acceso a la carrera, es administrador o supervisor ingresa  
		if( (acceso.getAccesos().indexOf(carrera.getCarreraId()) != -1) || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){
			
			// Si es una nueva facultad
			if(!tempFacultad.equals(facultad)){
				if (mapFacultad.containsKey(facultad)) nombreFacultad = mapFacultad.get(facultad).getNombreFacultad();
%>				
		<tr class="alert alert-success">
			<th colspan='2'><h3><%=facultad%>:<%=nombreFacultad%></h3></th>
		</tr>
<%
				tempFacultad 	= facultad;	
			}			
%>				
		<tr>
			<td>&nbsp;<%=carrera.getCarreraId()%>:<%=carrera.getNombreCarrera()%>&nbsp;</td>
			<td>
<%
			for (MapaPlan plan : lisPlanes){
				if (plan.getCarreraId().equals(carrera.getCarreraId())){
					String estilo = "<span class='badge bg-secondary rounded-pill'>"+plan.getPlanId()+"</span>";
					if (plan.getEstado().equals("A")) estilo = "<span class='badge bg-info rounded-pill'>"+plan.getPlanId()+"</span>";
					if (plan.getEstado().equals("V")) estilo = "<span class='badge bg-success rounded-pill'>"+plan.getPlanId()+"</span>";
					if (plan.getEstado().equals("I")) estilo = "<span class='badge bg-warning rounded-pill'>"+plan.getPlanId()+"</span>";
%>
		 		<span>
		 			<a href="alumnos?PlanId=<%=plan.getPlanId()%>" style="color:white"><%=estilo%>
		 		</a></span>&nbsp;
<%					
				}
			} 
%>			
			</td>
		</tr>
<%			
		}
	}
%>
	</table>