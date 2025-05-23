<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.catalogo.spring.CatPeriodo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar( FacultadId ){
		if(confirm("<spring:message code="aca.JSEliminar"/>")==true){
	  		document.location="borrarFacultad?FacultadId="+FacultadId;
	  	}
	}
	
	function grabaPeriodo(periodoId){
		document.location.href="facultad?PeriodoId="+periodoId;
	}
	
	function grabaCarga(cargaId){
		var periodoId = document.getElementById("PeriodoId").value;
		document.location.href="facultad?CargaId="+cargaId+"&PeriodoId="+periodoId;
	}
</script>
<%
	String periodoId 		= (String) request.getAttribute("periodoId");
	String cargaId 			= (String) request.getAttribute("cargaId");
	
	List<CatFacultad> lisFacultades	= (List<CatFacultad>) request.getAttribute("lisFacultades");
	List<CatPeriodo> lisPeriodos	= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	List<Carga> lisCargas 			= (List<Carga>) request.getAttribute("lisCargas");
	
	HashMap<String, String> mapaCarrerasConPermico  = (HashMap<String, String>) request.getAttribute("mapaCarrerasConPermico");
	HashMap<String, String> mapaPlanesPorCarrera  	= (HashMap<String, String>) request.getAttribute("mapaPlanesPorCarrera");
	HashMap<String, String> mapaMaterias 			= (HashMap<String, String>) request.getAttribute("mapaMaterias");
%>
<div class="container-fluid">
	<h2>Authorized Plans</h2>
	<div class="alert alert-info d-flex align-items-center">
	<b><spring:message code="aca.Periodo"/>:</b> 
		<select onchange="javaScript:grabaPeriodo(this.value);" id="PeriodoId" class="form-select" style="width:140px">
<%	for(int j=0; j<lisPeriodos.size(); j++){
		CatPeriodo periodo = lisPeriodos.get(j);%>
			<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
<%	}%>
		</select>
		&nbsp;&nbsp;&nbsp;
		<b><spring:message code="aca.Carga"/>:</b>
		<select id="CargaId" class="form-select" onchange='javaScript:grabaCarga(this.value);' style="width:370px;">
<%	for(Carga carga : lisCargas){ %>
			<option <%if(cargaId.equals(carga.getCargaId()))out.print(" Selected ");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
<%	}%>
		</select>
		&nbsp;
	</div>
	<table class="table table-bordered">
	<thead class="table-info">
  	<tr>
  		<th><spring:message code="aca.Numero"/></th>
	    <th><spring:message code="aca.Facultad"/></th>
	    <th class="text-end"># Plans</th>
	    <th class="text-end"># Subjects</th>
  	</tr>
  	</thead>
  <%	  	
  	for (CatFacultad facultad : lisFacultades){
  		
  		String numCarreras = "0";
  		if(mapaCarrerasConPermico.containsKey(facultad.getFacultadId())){
  			numCarreras = mapaCarrerasConPermico.get(facultad.getFacultadId());
  		}
  		
  		String numPlanes = "0";
  		String colorPlan = "bg-warning";
  		if(mapaPlanesPorCarrera.containsKey(facultad.getFacultadId())){
  			numPlanes = mapaPlanesPorCarrera.get(facultad.getFacultadId());
  			if(Integer.parseInt(numPlanes) > 0) colorPlan = "bg-black";
  		}
  		
  		String numMaterias = "0";
  		if(mapaMaterias.containsKey(facultad.getFacultadId())){
  			numMaterias = mapaMaterias.get(facultad.getFacultadId());
  		}
%>
  	<tr class="tr2">
	    <td align="center"><%=facultad.getFacultadId()%></td>
	    <td>
	    	<a href="permiso?FacultadId=<%=facultad.getFacultadId()%>&PeriodoId=<%=periodoId%>&CargaId=<%=cargaId%>"><%=facultad.getNombreFacultad()%>
	    	</a>
	    </td>
	    <td class="text-end"><span class="badge <%=colorPlan%> rounded-pill"><%=numPlanes%></span></td>
	    <td class="text-end"><%=numMaterias%></td>
  	</tr>
<%
	}	
%>
	</table>
</div>