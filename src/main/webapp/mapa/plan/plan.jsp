<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.plan.spring.MapaNuevoPlan"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar( planId, facultadId ){
		if(confirm("<spring:message code="aca.JSEliminar"/> "+planId)==true){
			document.location.href ="borrarPlan?PlanId="+planId+"&Facultad="+facultadId;
	  	}
	}
</script>
<%
	String facultad 		= request.getParameter("Facultad")==null?"0":request.getParameter("Facultad");
	String filtroEstado		= request.getParameter("FiltroEstado")==null?"T":request.getParameter("FiltroEstado");
	String facultadNombre	= (String) request.getAttribute("facultadNombre");
	String mensaje			= request.getParameter("Mensaje")==null?"":request.getParameter("Mensaje");
		
	String carreraNombre	= "X";
	String fRevoe			= "-";
	String fRetro			= "-";
	String fPublica 		= "-";
	String minimo			= "0";
	String maximo			= "0";
	String estado 			= "-";
	
	List<MapaPlan> lisPlan							= (List<MapaPlan>) request.getAttribute("lisPlanes");
	HashMap<String, CatCarrera> mapCarrera			= (HashMap<String, CatCarrera>) request.getAttribute("mapCarrera");
	HashMap<String, String> mapaArchivo				= (HashMap<String, String>) request.getAttribute("mapaArchivo");
	HashMap<String, String> mapaPrerrequisitos		= (HashMap<String, String>) request.getAttribute("mapaPrerrequisitos");
	HashMap<String, String> mapaVersiones			= (HashMap<String, String>) request.getAttribute("mapaVersiones");
	HashMap<String,String> mapaCursosPorPlan		= (HashMap<String,String>) request.getAttribute("mapaCursosPorPlan");
	HashMap<String, MapaNuevoPlan> mapaNuevoPlan 	= (HashMap<String,MapaNuevoPlan>) request.getAttribute("mapaNuevoPlan");
	HashMap<String,String> mapaContenido			= (HashMap<String,String>) request.getAttribute("mapaContenido");
	HashMap<String,String> mapaPlanesCert			= (HashMap<String,String>) request.getAttribute("mapaPlanesCert");
%>
<div class="container-fluid">
	<h2><b> <%=facultad%> - <%=facultadNombre%></b></h2>
<%	if(!mensaje.equals("")){ %>
		<div class='alert alert-info'><%=mensaje%></div>
<%	}%>
	<form name="frmPlan" action="plan" method="post">
	<input type="hidden" name="Facultad" value="<%=facultad%>">
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="facultad"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		<a class="btn btn-primary" href="accion?Facultad=<%=facultad%>"><spring:message code="aca.Anadir"/></a>&nbsp; &nbsp;
		<select name="FiltroEstado" class="form-select" onchange="document.frmPlan.submit();" style="width:140px;">
			<option value="T" <%=filtroEstado.equals("T")?"selected":""%>><spring:message code="aca.Todos"/></option>
			<option value="A" <%=filtroEstado.equals("A")?"selected":""%>><spring:message code="aca.Admision"/></option>
			<option value="V" <%=filtroEstado.equals("V")?"selected":""%>><spring:message code="aca.Vigentes"/></option>
			<option value="I" <%=filtroEstado.equals("I")?"selected":""%>><spring:message code="aca.Inactivos"/></option>
		</select>		
		&nbsp; &nbsp;
		<input type="text" class="form-control search-query" placeholder="<spring:message code="aca.Buscar"/>" id="buscar" style="width:200px;">
		
	</div>
	</form>
  	<table id="table" class="table table-sm table-bordered">		  	
    <thead class="table-info">
	  <tr> 
		<th><spring:message code="aca.Op"/></th>
		<th><spring:message code="aca.Numero"/></th>
		<th><spring:message code="aca.Carrera"/> <spring:message code="aca.Clave"/></th>
		<th><spring:message code="aca.Plan"/> <spring:message code="aca.Clave"/></th>	
		<th><spring:message code="aca.Nombre"/></th>
		<th>Cycles</th>
		<th><spring:message code="mapa.plan.#Mat"/></th>	
		<th><spring:message code="mapa.plan.Cont"/></th>
		<th><spring:message code="mapa.plan.Pre"/></th>
		<th><spring:message code="aca.Modalidad"/></th>
		<th><spring:message code="aca.Validez"/></th>
		<th><spring:message code="aca.Status"/></th>
<%-- 		<th><spring:message code="aca.RVOE"/></th> --%>
<%-- 		<th><spring:message code="mapa.plan.PlanSE"/></th> --%>
		<th><spring:message code="aca.Version"/></th>
		<th>Disc.Perc.</th>
	  </tr>
    </thead>
	<tbody>
<%
	int row=0;
	for (MapaPlan mplan : lisPlan){				
		row++;		
		if (mapCarrera.containsKey(mplan.getCarreraId())){
			CatCarrera carrera = (CatCarrera) mapCarrera.get(mplan.getCarreraId());
			carreraNombre = carrera.getNombreCarrera();
		}
		
		boolean existeCert = mapaPlanesCert.containsKey(mplan.getPlanId());		
		
		String numPre = "0";
		if (mapaPrerrequisitos.containsKey(mplan.getPlanId()) ){
			numPre = mapaPrerrequisitos.get(mplan.getPlanId());
		}
							
		if(mplan.getFInicio()!= null){
			fRevoe = mplan.getFInicio();
		}
		
		if(mplan.getFFinal()!= null){
			fRetro = mplan.getFFinal();
		}
		
		if(mplan.getFActualiza()!= null){
			fPublica = mplan.getFActualiza();
		}
		
		if(mplan.getMinimo()!= null){
			minimo = mplan.getMinimo();
		}
		if(mplan.getMaximo()!= null){
			maximo = mplan.getMaximo();
		}
		
		//String tmpEstado = "";
		estado = mplan.getEstado();
		if (mplan.getEstado().equals("A")){
			estado = "<span class='badge bg-info rounded-pill'>Admission</span>";
		}else if (mplan.getEstado().equals("V")){
			estado = "<span class='badge bg-success rounded-pill'>Active</span>";
		}else{
			estado = "<span class='badge bg-warning rounded-pill'>Inactive</span>";
		}
		//pageContext.setAttribute("tmpEstado", tmpEstado);
		
		String colorRevoe = "color:gray";
		if (mapaArchivo.containsKey(mplan.getPlanId()+"1")){
			colorRevoe	= "color:black";
		}
		String colorPlan = "color:gray";
		if (mapaArchivo.containsKey(mplan.getPlanId()+"2")){
			colorPlan	= "color:black";
		}
		
		String modalidad = "-";
		if (mplan.getEnLinea().equals("N")) modalidad = "In person";
		if (mplan.getEnLinea().equals("S")) modalidad = "Online";			
		if (mplan.getEnLinea().equals("M")) modalidad = "Mixed";
		
		String validez = "-";
		if(mplan.getOficial().equals("S")) validez = "Formal";
		else if (mplan.getOficial().equals("N")) validez = "Informal";
		else if (mplan.getOficial().equals("I")) validez = "Languages";
		else if (mplan.getOficial().equals("C")) validez = "CIMUM M.";
		else if (mplan.getOficial().equals("A")) validez = "CIMUM A.";
		
		String versionNombre = "-";
		if (mapaVersiones.containsKey(mplan.getVersionId())){
			versionNombre = mapaVersiones.get(mplan.getVersionId());
		}
		
		String numCursos = "0";
		if (mapaCursosPorPlan.containsKey(mplan.getPlanId()) ){
			numCursos = mapaCursosPorPlan.get(mplan.getPlanId());
		}

		String nuevoPlan = "";
		if (mapaNuevoPlan.containsKey(mplan.getPlanId()) ){
			nuevoPlan = "<span class='badge bg-inverse'>"+mapaNuevoPlan.get(mplan.getPlanId()).getPlanId()+"</span>";
		}else{
			nuevoPlan = "<span class='badge bg-warning'>"+mplan.getPlanId()+"</span>";
		}
		
		String contenido = "<span class='badge bg-warning'>0</span>";
		if (mapaContenido.containsKey(mplan.getPlanId())){
			if(mapaContenido.get(mplan.getPlanId()).equals(numCursos)){
				contenido = "<span class='badge bg-inverse'>"+mapaContenido.get(mplan.getPlanId())+"</span>";
			}else{
				contenido = "<span class='badge bg-success'>"+mapaContenido.get(mplan.getPlanId())+"</span>";
			}			
		}					
%>
	  <tr> 
		<td style="text-align: center">
			<a href="accion?Facultad=<%=facultad%>&PlanId=<%=mplan.getPlanId()%>&FiltroEstado=<%=filtroEstado%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>			
<% 		if(numCursos.equals("0")){%>
			<a href="javascript:Borrar('<%=mplan.getPlanId()%>','<%=facultad%>')" title="<spring:message code='aca.Eliminar'/>"><i class="fas fa-trash-alt"></i></a>
<% 		}%>		
<%-- 			<a href="mapaArchivo?PlanId=<%=mplan.getPlanId()%>&Folio=1" style="<%=colorRevoe%>"><i class="fas fa-file" ></i></a> --%>
<%-- 			<a href="mapaArchivo?PlanId=<%=mplan.getPlanId()%>&Folio=2" style="<%=colorPlan%>"><i class="fas fa-file" ></i></a> --%>
		</td>
	    <td align="center"><%=existeCert?"<span class='badge bg-success rounded-pill' title='Has certificate format'>"+row+"</span>":"<span class='badge bg-warning rounded-pill' title='No certificate format'>"+row+"</span>"%></td>
	    <td align="center" title="<%=carreraNombre%>"><%=mplan.getCarreraId()%></td>
	    <td align="left" title="<%=mplan.getNombrePlan()%>"><%=mplan.getPlanId()%></td>
	    <td align="left"><%=mplan.getNombrePlan()%></td>
	    <td align="center"><%=mplan.getCiclos()%></td>
<% 		if(numCursos.equals("0")){%>			   
	    	<td align="center"><span class='label label-warning'><%=numCursos%></span></td>
<%		}else{ %>
			<td align="center"><span class='label label-inverse'><%=numCursos%></span></td>
<%		} %>
	    <td align="center"><%=contenido%></td>    
	    <td align="center"><%=numPre%></td>
	    <td align="center"><%=modalidad%></td>
	    <td align="center"><%=validez%></td>
	    <td align="center"><%=estado%></td>
<%-- 		<td><%=mplan.getRvoe() %></td> --%>
<%-- 	    <td align="center"><%=mplan.getPlanSE()%></td> --%>
	    <td align="center"><%=versionNombre%></td>
	    <td align="center"><%=mplan.getDescuento()%>%</td>
	  </tr>
<%
		fRevoe			= "-";
		fRetro			= "-";
		fPublica		= "-";
		numCursos		= "0";
		minimo			= "0";
		maximo			= "0";
	}			
%>
	</tbody>
	</table>			
</div>
<script src="../../js/search.js"></script>
<script>
	$('#buscar').search();
</script>