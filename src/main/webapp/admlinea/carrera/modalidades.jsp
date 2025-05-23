<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>
<%@ page import= "aca.admision.spring.AdmModCarrera"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
	<script>
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
	</script>
</head>
<%		
	String facultadId 			= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");
	String modalidad 			= (String)request.getAttribute("modalidad");	
	String facultadNombre 		= (String)request.getAttribute("facultadNombre");
	
	List<CatModalidad> lisModalidades 		= (List<CatModalidad>)request.getAttribute("lisModalidades");
	List<CatCarrera> lisCarreras			= (List<CatCarrera>)request.getAttribute("lisCarreras");
	List<MapaPlan> lisPlanes				= (List<MapaPlan>) request.getAttribute("lisPlanes");
	HashMap<String, String> mapModCarrera 	= (HashMap<String, String>)request.getAttribute("mapModCarrera");	
%>
<body onLoad="revisarChecks();">
<div class="container-fluid">	
	<h2><spring:message code='aca.CarrerasPorModalidad'/><small class="text-muted fs-4"> ( <%=facultadNombre%> )</small></h2>
	<form name="frmAgregar" method="post" action="grabar?FacultadId=<%=facultadId%>">
	<div class="alert alert-info  d-flex align-items-center">
		<a class="btn btn-primary" href="facultad"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
		<select  class="form-select" id="Modalidad" style="width:200px" name="Modalidad" onchange="document.location.href='modalidades?FacultadId=<%=facultadId %>&Modalidad='+document.getElementById('Modalidad').value;">
<%	for(CatModalidad mod : lisModalidades){ %>
			<option value="<%=mod.getModalidadId()%>" <%if(mod.getModalidadId().equals(modalidad))out.print("Selected");%>><%=mod.getNombreModalidad()%></option>		
<%	} %>
		</select>			
	</div>
			
	<table class="table table-bordered">
	<tr>		
		<th colspan="3">
			<input id="CheckAllAgregar" name="CheckAllAgregar" type="checkbox" value="S" onclick="seleccionarTodos(this)">&nbsp;<i><spring:message code="aca.Seleccionar"/> <spring:message code="aca.Todos"/>/<spring:message code="aca.Ninguno"/></i>
		</th>
	</tr>		
<%	for( CatCarrera carrera : lisCarreras){%>
	<tr>		
		<td>
			<input <%=mapModCarrera.containsKey(modalidad + carrera.getCarreraId())?"Checked":"" %> type="checkbox" id="chk<%=carrera.getCarreraId()%>" name="chk<%=carrera.getCarreraId()%>">
		</td>
		<td>			
			[<b><%=carrera.getCarreraId() %></b>]&nbsp;<b><%=carrera.getNombreCarrera() %></b>
		</td>
		<td>
<%
		for (MapaPlan plan : lisPlanes){
			if ( plan.getCarreraId().equals(carrera.getCarreraId())){
				String colorEstado = "bg-dark";
				if (plan.getEstado().equals("V")) colorEstado = "bg-success";
				if (plan.getEstado().equals("I")) colorEstado = "bg-warning";
%>		
				<b><%=plan.getPlanId()%></b>&nbsp;<span class="badge <%=colorEstado%>"><%=plan.getEstado()%></span>&nbsp;&nbsp;
<%			}
		}
%>  
		</td>
	</tr>
<%	} %>	
	<tr>
		<td colspan="3">
			<input class="btn btn-primary" value="Save" type="submit">
		</td>
	</tr>
	</table>
	</form>
</div>
</body>