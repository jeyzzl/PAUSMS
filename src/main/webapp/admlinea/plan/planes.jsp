<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<html>
<head></head>
<script type="text/javascript">
	function Refrescar(){			
		document.frmFacultad.submit();	
	}
	
	function seleccionarTodos(checkAll){
		var inputs = document.getElementsByTagName("INPUT");
		for(i=0; i<inputs.length; i++) if(inputs[i].type=="checkbox") inputs[i].checked=checkAll.checked;
	}	
	function revisarChecks(){
		var inputs = document.getElementsByTagName("INPUT");
		var cont = 0;
		var cont2 = 0;
		for(i=0; i<inputs.length; i++){
			if(inputs[i].type=="checkbox"){
				cont2++;
				if(inputs[i].checked) cont++;
			}
		}
		if(cont==(cont2-1)) document.getElementById('CheckAllAgregar').checked = true;
	}
	
	function Grabar(){
		document.frmAgregar.submit();	
	}
</script>
<%	
	String periodoId 					= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
	String modalidadId 					= request.getParameter("ModalidadId")==null?"0":request.getParameter("ModalidadId");	
	String facultadId					= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");
	String mensaje						= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	String nomPeriodo					= (String)request.getAttribute("nomPeriodo");
	String nomModalidad					= (String)request.getAttribute("nomModalidad");
	String nomFacultad					= (String)request.getAttribute("nomFacultad");
	
	List<CatFacultad> lisFacultades				= (List<CatFacultad>)request.getAttribute("lisFacultades");	
	List<MapaPlan> lisPlanes	 				= (List<MapaPlan>)request.getAttribute("lisPlanes");
	
	HashMap<String,CatFacultad> mapaFacultades 	= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras		= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,String> mapaPlanes 			= (HashMap<String,String>)request.getAttribute("mapaPlanes");	
%>

<body onLoad="revisarChecks();">
<div class="container-fluid">
	<h2><spring:message code='aca.Autorizado'/> <spring:message code='aca.Planes'/> <small class="text-muted fs-6">( <%=nomPeriodo%> - <%=nomModalidad%> )</small></h2>
	<form name="frmFacultad" action="planes?PeriodoId=<%=periodoId%>&ModalidadId=<%=modalidadId%>" method="post">	
	<div class="alert alert-info  d-flex align-items-center">
		<a href="ingreso?PeriodoId=<%=periodoId%>&ModalidadId=<%=modalidadId%>" class="btn btn-primary"><spring:message code='aca.Regresar'/></a>&nbsp;&nbsp;
		<spring:message code='aca.Facultad'/>:&nbsp;
		<select name="FacultadId" class="form-select" onchange="javascript:Refrescar()" style="width:320px;">
			<option value="0" <%=facultadId.equals("0")?"selected":""%>><spring:message code='aca.Todos'/></option>
<%	
	for(CatFacultad facultad : lisFacultades){%>
			<option value="<%=facultad.getFacultadId()%>" <%=facultadId.equals(facultad.getFacultadId())?"selected":""%>><%=facultad.getNombreFacultad()%></option>
<%	} %>			
		</select>&nbsp;&nbsp;
		<input type="text" class="form-control search-query" placeholder="Search" id="buscar" style="width:200px;">
	</div>
	</form>
		<%	if (!mensaje.equals("-")){ %>	
	<div class="alert alert-info"><%=mensaje%></div>
<%	} %>
	<form name="frmAgregar" action="grabar?PeriodoId=<%=periodoId%>&ModalidadId=<%=modalidadId%>" method="post">
	<input type="hidden" name="FacultadId" value="<%=facultadId%>">
	<table id="table"  class="table table-sm table-bordered">
	<thead class="table-dark">
	<tr>		
		<th width="3%">#</th>
		<th width="5%" style="text-align: center"><input name="CheckAllAgregar" type="checkbox" value="S" onclick="seleccionarTodos(this)"></th>
		<th width="5%"><spring:message code='aca.Facultad'/></th>		
		<th width="5%"><spring:message code='aca.Carrera'/> <spring:message code='aca.Clave'/></th>
		<th width="5%"><spring:message code='aca.Plan'/> <spring:message code='aca.Clave'/></th>
		<th width="50%"><spring:message code='aca.Plan'/> <spring:message code='aca.Nombre'/></th>
		<th width="10%"><spring:message code='aca.Status'/></th>
	</tr>
	</thead>	
	<tbody>
<%	
	int cont = 1;	
	for (MapaPlan mapaPlan: lisPlanes){
		String seleccionar = "";
		if (mapaPlanes.containsKey(mapaPlan.getPlanId())){
			seleccionar = "checked";
		}
		
		String facultadTemp 	= "0";
		String facultadNombre 	= "0";
		if (mapaCarreras.containsKey(mapaPlan.getCarreraId())){
			facultadTemp = mapaCarreras.get(mapaPlan.getCarreraId()).getFacultadId();
			if (mapaFacultades.containsKey(facultadTemp)){
				facultadNombre = mapaFacultades.get(facultadTemp).getNombreCorto();				
			}
		}
		
		String color = "bg-info";
		if (mapaPlan.getEstado().equals("V")) color = "bg-success";
		if (mapaPlan.getEstado().equals("I")) color = "bg-warning";
%>
	<tr>
		<td><span class="badge <%=color%> rounded-pill"><%=cont++%></span></td>
		<td align="center">		
			<input type="checkbox" id="chkAgregar<%=cont%>" value="<%=mapaPlan.getPlanId() %>" name="PlanId<%= mapaPlan.getPlanId()%>" <%=seleccionar%>/>
		</td>					
		<td><%=facultadNombre%></td>
		<td><%=mapaPlan.getCarreraId()%></td>
		<td><%=mapaPlan.getPlanId()%></td>
		<td><%=mapaPlan.getCarreraSe()%></td>
		<td><span class="badge <%=color%> rounded-pill"><%=mapaPlan.getEstado().equals("A")?"Active":mapaPlan.getEstado().equals("I")?"Inactive":"Current"%></span></td>
	</tr>	
<% }%>
	</tbody>
	</table>
		<div class="alert alert-info" >
			<a href="javascript:Grabar();" class="btn btn-primary"><spring:message code='aca.Grabar'/></a>
		</div>
	</form>	
</div>
</body>
</html>
<script src="../../js/search.js"></script>
<script type="text/javascript">
	$('#buscar').search();
</script>