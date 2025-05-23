<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmIngreso"%>
<%@page import="aca.admision.spring.AdmIngresoPlan"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>
<html>
<head></head>
<script type="text/javascript">
	function cambioPeriodo(){		
		document.frmPeriodo.submit();
	}

	function borrar(folio){	
		if (confirm("Are you sure you want to delete this record?")){
			document.location.href="borrar?Folio="+folio;
		}	
	}
</script>
<%		
	String periodoId 					= (String)request.getAttribute("periodoId");
	List<AdmIngreso> lisPeriodos 		= (List<AdmIngreso>)request.getAttribute("lisPeriodos");	
	List<CatModalidad> lisModalidades 	= (List<CatModalidad>)request.getAttribute("lisModalidades");
	HashMap<String,String> mapaPlanes 	= (HashMap<String,String>)request.getAttribute("mapaPlanes");	
%>
<body>
<div class="container-fluid">
	<h2><spring:message code='aca.Autorizado'/> <spring:message code='aca.Planes'/></h2>
	<form name="frmPeriodo" action="ingreso" method="post">
	<div class="alert alert-info  d-flex align-items-center">
	<spring:message code='aca.Periodo'/>: &nbsp;&nbsp;
		<select class="form-select" name="PeriodoId" id="PeriodoId" onchange="javascript:cambioPeriodo();" style="width:200px">
<%	for(AdmIngreso periodo : lisPeriodos){%>
		<option value="<%=periodo.getPeriodoId()%>" <%=periodo.getPeriodoId().equals(periodoId) ? "selected" : ""%>><%=periodo.getPeriodoNombre()%></option>
<%	} %>
		</select>	
		&nbsp;&nbsp;<a href="periodos" class="btn btn-primary"></i><spring:message code='aca.Editar'/></a>
	</div>	
	</form>	
	<table class="table table-sm table-bordered">
	<thead class="table-dark">
	<tr>
		<th width="3%">#</th>		
		<th width="5%"><spring:message code='aca.Modalidad'/> <spring:message code='aca.Clv'/></th>
		<th width="60%"><spring:message code='aca.Modalidad'/> <spring:message code='aca.Nombre'/></th>
		<th width="10%"><spring:message code='portal.alumno.documentos.Ubicacion'/></th>
		<th width="10%"><spring:message code='aca.Autorizado'/> <spring:message code='aca.Planes'/></th>
	</tr>
	</thead>	
<%	int cont = 1;
	for (CatModalidad  mod: lisModalidades){
		String numPlanes = "0";
		if (mapaPlanes.containsKey(mod.getModalidadId())){
			numPlanes = mapaPlanes.get(mod.getModalidadId());
		}	
%>
	<tr>
		<td><b><%=cont++%></b></td>		
		<td><%=mod.getModalidadId() %></td>
		<td><%=mod.getNombreModalidad() %></td>
		<td><%=mod.getEnLinea().equals("S")?"Online":"In Person"%></td>
		<td>
			<a href="planes?PeriodoId=<%=periodoId%>&ModalidadId=<%=mod.getModalidadId()%>">
			<%=numPlanes.equals("0")?"<span class='badge bg-warning rounded-pill'>"+numPlanes+"</span>":"<span class='badge bg-dark rounded-pill'>"+numPlanes+"</span>"%>
			</a>
		</td>
	</tr>	
<% }%>
	</table>
</div>
</body>
</html>