<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">		
	function Refrescar(){
  		document.forma.submit(); 
	}	
</script>
<%
	String cargaId					= (String) session.getAttribute("cargaId");

	List<Carga> lisCargas 			= (List<Carga>) request.getAttribute("lisCargas");
	List<CatCarrera> lisCarreras 	= (List<CatCarrera>) request.getAttribute("lisCarreras");
	List<MapaPlan> lisPlanes 		= (List<MapaPlan>) request.getAttribute("lisPlanes");
	
	HashMap<String,CatFacultad> mapaFacultades 		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,String> mapaMaestros 			= (HashMap<String,String>) request.getAttribute("mapaMaestros");
	HashMap<String,String> mapaMaterias 			= (HashMap<String,String>) request.getAttribute("mapaMaterias");
%>
<body>
<div class="container-fluid">
	<h2><spring:message code="cargaGrupo.listaCarga.CargasAcademicasHorarios"/></h2>
	<form name="forma" action="elegir" method="post"> 		
	<div class="alert alert-info d-flex align-items-center">	
		<b><spring:message code="aca.Carga" />:</b>
		<select name="CargaId" class="form-select" onchange='javascript:Refrescar()' style="width:450px;">
<%	for(Carga carga : lisCargas){%>
		<option <%=carga.getCargaId().equals(cargaId)?"selected":""%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId()%>]-<%=carga.getNombreCarga()%></option>
<%	}%>
	</select>	
	</div>  
  	<table class="table table-bordered">
	<thead class="table-info">
  	<tr>
  		<th>#</th>
  		<th><spring:message code="aca.Facultad" /></th>
  		<th><spring:message code="aca.Carrera" /></th>
  		<th class="right"><spring:message code="aca.Materias" /></th>
  		<th><spring:message code="aca.Coordinador" /></th>
  		<th><spring:message code="aca.Planes" /></th>
  	</tr>
  	</thead>
  	<tbody>
<%
	int row = 0;
	for (CatCarrera carrera : lisCarreras){
		row++;
		String corto = "";
		if (mapaFacultades.containsKey(carrera.getFacultadId())){
			corto = mapaFacultades.get(carrera.getFacultadId()).getNombreFacultad();
		}
		
		String maestro = "";
		if (mapaMaestros.containsKey(carrera.getCodigoPersonal())){
			maestro = mapaMaestros.get(carrera.getCodigoPersonal());
		}
		
		String materias = "";
		if (mapaMaterias.containsKey(carrera.getCarreraId())){
			materias = mapaMaterias.get(carrera.getCarreraId());
		}
%>  	  	
  	<tr>
  		<td><%=row%></td>
  		<td><%=corto%></td>
  		<td><a href="carga?CarreraId=<%=carrera.getCarreraId()%>"/><%=carrera.getNombreCarrera()%></td>
  		<td class="right"><%=materias%></td>
  		<td><%=maestro%></td>
  		<td>
<%	
		int total = 0;
		for(MapaPlan plan: lisPlanes){
			if(plan.getCarreraId().equals(carrera.getCarreraId())){
				String colorPlan = "bg-secondary";
				if (plan.getEstado().equals("A")) colorPlan = "bg-info";
				if (plan.getEstado().equals("V")) colorPlan = "bg-success";
				if (plan.getEstado().equals("I")) colorPlan = "bg-warning";
%>
				<span class="badge <%=colorPlan%>"><a style="color:white" href="horarioSalon?PlanId=<%=plan.getPlanId()%>"><%=plan.getPlanId()%></a></span>&nbsp;
<%			}
		}
%>
		</td>			  		
	</tr>	
<%	}%>
  	</tbody>
  	</table> 
	</form> 
</div>
</body>