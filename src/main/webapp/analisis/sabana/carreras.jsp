<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.acceso.spring.Acceso"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>
<%@ page import= "aca.carga.spring.Carga"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Refrescar(){
		document.frmCarga.submit();
	}
</script>	
<%
	String codigo			= (String) session.getAttribute("codigoPersonal");	

	int cont 				= 1;
	int nInscritos 			= 0, nCalculos 		= 0;
	int nHombres			= 0, nMujeres		= 0;
	int nInternos			= 0, nExternos 		= 0;
	int nNacional			= 0, nExtranjero	= 0;
	
	String periodoId		= (String)request.getAttribute("periodoId");
	String cargaId			= (String)request.getAttribute("cargaId");
	Acceso acceso 			= (Acceso)request.getAttribute("acceso"); 
	
	List<CatPeriodo> lisPeriodos 					= (List<CatPeriodo>)request.getAttribute("lisPeriodos");
	List<Carga> lisCargas		 					= (List<Carga>)request.getAttribute("lisCargas");
	List<MapaPlan> lisPlanes 						= (List<MapaPlan>)request.getAttribute("lisPlanes");	
	HashMap<String,CatFacultad> mapaFacultades 		= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras 		= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
%>
<div class="container-fluid">
	<h2>Grades Concentrate</h2>
	<form name="frmCarga" action="carreras" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<b>Cycle:</b>
			<select id="PeriodoId" name="PeriodoId" class="form-select"onchange="javascript:Refrescar();" style="width:180px">
			<%for(CatPeriodo periodo : lisPeriodos){%>
				<option value="<%=periodo.getPeriodoId()%>" <%=periodo.getPeriodoId().equals(periodoId)?"selected":""%>><%=periodo.getNombre()%></option>
			<%}%>
			</select>
			&nbsp;&nbsp;	
		<b>Load:</b>&nbsp;
			<select id="CargaId" name="CargaId" class="form-select" onchange="javascript:Refrescar();" style="width:350px">
			<%for(Carga carga : lisCargas){%>
				<option value="<%=carga.getCargaId()%>" <%=carga.getCargaId().equals(cargaId)?"selected":""%>><%=carga.getCargaId()%>-<%=carga.getNombreCarga()%></option>
			<%}%>
			</select>	
	</div>
	</form>	
	<div style="overflow-x:scroll;">
	<table class="table" >
<%	
	String facultad			= "";
	String tempFacultad		= "";
	String nombreFacultad	= "";
	String tempCarrera		= "";
	String nombreCarrera	= "";
	String tempPlan			= "";	
	 
	for(int i = 0; i < lisPlanes.size();i++){
		MapaPlan plan = (MapaPlan) lisPlanes.get(i);
		
		// Obtiene la clave de la facultad
		if (mapaCarreras.containsKey(plan.getCarreraId())){
			facultad 	= mapaCarreras.get(plan.getCarreraId()).getFacultadId();
		}
		
		// Si tiene acceso a la carrera, es administrador o supervisor ingresa  
		if( (acceso.getAccesos().indexOf(plan.getCarreraId()) != -1) || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){
			
			// Si es una nueva facultad
			if(!tempFacultad.equals(facultad)){
				if (mapaFacultades.containsKey(facultad)) nombreFacultad = mapaFacultades.get(facultad).getNombreFacultad();		
%>				
				<thead class="table-dark">	
					<tr>
						<th class='center' colspan='100%'><font size='3'><b><%=facultad%> : <%=nombreFacultad%></b></font></th>
					</tr>
				</thead>	
<%
				tempFacultad 	= facultad;				
			}
			// Si es una carrera diferente
			if(!tempCarrera.equals(plan.getCarreraId())){
				if (mapaCarreras.containsKey(plan.getCarreraId())) nombreCarrera 	= mapaCarreras.get(plan.getCarreraId()).getNombreCarrera();
%>				
				<tr class="table-success">
					<td colspan='100%'><b>&nbsp;&nbsp;<%=plan.getCarreraId()%> : <%=nombreCarrera%>&nbsp;</b></td>
				</tr>
				<tr style="overflow-x:scroll">
<%
				tempCarrera = plan.getCarreraId();
			}
%>	
					<td>			
						<div class="card text-center">
							<div class="card-header	p-2">
								<b><i><%=plan.getPlanId()%></i></b>
							</div>
							<div class="card-body text-center d-flex p-1">
								<a class="btn btn-sm btn-primary mx-1" href="listado?planid=<%=plan.getPlanId()%>&todos=S" title="All Students">All</a>
								<a class="btn btn-sm btn-success mx-1" href="listado?planid=<%=plan.getPlanId()%>" title="Students in Load"><spring:message code="aca.Carga"/></a>
							</div>
						</div>
					</td>				
<%			// Si no esta en la ultima fila
			if ( i < lisPlanes.size()-1){
				MapaPlan planTemp = (MapaPlan) lisPlanes.get(i+1);
				// Si la siguiente carrera no es la misma, cierra el renglón
				if (!planTemp.getCarreraId().equals(tempCarrera)) out.print("</tr>");
			}else{
				out.print("</tr>");
			}			
		}
	}
%>
</table>
</div>
</div>