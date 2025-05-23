<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.carga.spring.CargaPlanExterno"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
<script type="text/javascript">
	function cambioPeriodo(){
		var periodoId = document.getElementById("PeriodoId").value;
		location.href = "planes?PeriodoId="+periodoId;
	}	
	
	function cambioCarga(){
		var periodoId 	= document.getElementById("PeriodoId").value;
		var cargaId 	= document.getElementById("CargaId").value;
		location.href = "planes?PeriodoId="+periodoId+"&CargaId="+cargaId;
	}

	function filtro(){
		var periodoId 	= document.getElementById("PeriodoId").value;
		var cargaId 	= document.getElementById("CargaId").value;
		var filtro 		= document.getElementById("Filtro").value;
		location.href = "planes?PeriodoId="+periodoId+"&CargaId="+cargaId+"&Filtro="+filtro;
	}
	
	function borrar(cargaId,bloqueId,planId){
		var r = confirm("Delete load.");
		if (r == true) {
		location.href = "borrarCarga?CargaId="+cargaId+"&BloqueId="+bloqueId+"&PlanId="+planId;
		} 
	}	
</script>
<body>
<%
	String mensaje				= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");

	String periodoId			= (String) request.getAttribute("PeriodoId");
	String cargaId				= (String) request.getAttribute("CargaId");
	String filtro 				= (String) request.getAttribute("Filtro");	
	
	List<CatPeriodo> lisPeriodos 				= (List<CatPeriodo>)request.getAttribute("lisPeriodos");
	List<Carga> lisCargas		 				= (List<Carga>)request.getAttribute("lisCargas");
	List<MapaPlan> lisPlanes 		 			= (List<MapaPlan>)request.getAttribute("lisPlanes");
	List<CargaPlanExterno> lisPlanesRegistrados	= (List<CargaPlanExterno>)request.getAttribute("lisPlanesRegistrados");
	HashMap<String, String> mapaPlanesExternos	= (HashMap<String, String>)request.getAttribute("mapaPlanesExternos");
		
	String color = "alert alert-success";
	if(mensaje.equals("1")) {
		color = "alert alert-warning";
		mensaje = "A similar record (green line) already exists in this period!";
	}else if(mensaje.equals("2")) {
		mensaje = "Saved !";
	}
	
%>
<div class="container-fluid">
	<h3>Plans with Default Boarding</h3>
	<form name="frmCarga" action="guardar" method="post">
	<div class="alert alert-info d-flex align-items-center">
		Cycle:
		<select id="PeriodoId" name="PeriodoId" class="form-select" onchange="javascript:cambioPeriodo();" style="width:200px">
		<%for(CatPeriodo periodo : lisPeriodos){%>
			<option value="<%=periodo.getPeriodoId()%>" <%=periodo.getPeriodoId().equals(periodoId)?"selected":""%>><%=periodo.getNombre()%></option>
		<%}%>
		</select>&nbsp;&nbsp;&nbsp;
		Load:
		<select id="CargaId" name="CargaId" class="form-select" onchange="javascript:cambioCarga();" style="width:350px">
		<%for(Carga carga : lisCargas){%>
			<option value="<%=carga.getCargaId()%>" <%=carga.getCargaId().equals(cargaId)?"selected":""%>><%=carga.getCargaId()%>-<%=carga.getNombreCarga()%></option>
		<%}%>
		</select>&nbsp;&nbsp;&nbsp;
		Filter:
		<select id="Filtro" name="Filtro" class="form-select" onchange="javascript:filtro();" style="width:150px">
			<option value="T" <%=filtro.equals("T")?"selected":""%>>All</option>
			<option value="R" <%=filtro.equals("R")?"selected":""%>>Registered</option>
		</select>
	</div>
	</form>
<% 	if(!mensaje.equals("0")){%>
	<div class="<%=color%>">
		<%=mensaje%>
	</div>
<% 	}%>
	<table class="table table-sm table-bordered">
		<tr class="table-info">
		    <th width="3%">#</th>
		    <th width="7%">Registered</th>
		    <th>Plan</th>
		    <th>Plan Name</th>
		    <th>Status</th>
		</tr>
<%
	int row = 0;
	for(MapaPlan plan : lisPlanes){
		row++;
		String registrado = "";
		if(mapaPlanesExternos.containsKey(plan.getPlanId())) registrado = "<i class='fas fa-check-circle'></i>";
		
		String estadoNombre = "-";
		if (plan.getEstado().equals("A")) estadoNombre = "Admissible";
		if (plan.getEstado().equals("V")) estadoNombre = "Current";
		if (plan.getEstado().equals("I")) estadoNombre = "Inactive";
%>
		<tr>
			<td><%=row%></td>
			<td class="text-center"><span <%=registrado%></span></td>
			<td><a href="guardar?CargaId=<%=cargaId%>&PlanId=<%=plan.getPlanId()%>"><%=plan.getPlanId()%></a></td>
			<td><%=plan.getNombrePlan()%></td>
			<td><%=estadoNombre%></td>
		</tr>
		<%}%>
	</table>
</div>
</body>
</html>