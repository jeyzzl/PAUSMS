<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.*"%>
<%@ page import="aca.residencia.spring.ResExpediente"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="../alumno/menu.jsp"%>

<head>
	<script type="text/javascript">
		function borrar(matricula,folio){
			if(confirm("Are you sure you want to delete this Folder?")){
				document.location.href = "borrarFormato?Matricula="+matricula+"&Folio="+folio;
			}
		}
	</script>
</head>
<%
	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");

	ResExpediente expediente 	= (ResExpediente) request.getAttribute("expediente");
	
	List<String> lisPlanes 					= (List<String>) request.getAttribute("lisPlanes");
	HashMap<String, String> mapNombrePlan	= (HashMap<String, String>) request.getAttribute("mapNombrePlan");
%>
<div class="container-fluid">
	<h2>New Folder</h2>	
	<div class="alert alert-info">
		<a href="solicitudExternado" class="btn btn-primary">Return</a>&nbsp;
	</div>		
	<div class="">	
		<form name="form" action="grabarExpediente" method="get">
			<input type="hidden" name="Folio" value="<%=expediente.getFolio()%>">
			<label><b>Plan</b></label>
			<select class="form-select" name="PlanId">
<%		for(String plan : lisPlanes){
			String nombrePlan = "";
			if(mapNombrePlan.containsKey(plan)){
				nombrePlan = mapNombrePlan.get(plan);
			}
%>
				<option value="<%=plan%>" <%=expediente.getPlanId().equals(plan)?"selected":""%>><%=plan+" - "+nombrePlan%></option>
<%		}%>
			</select><br>
			<label><b>Description</b></label> <input type="text" class="form-control" name="Descripcion" value="<%=expediente.getDescripcion()%>">
			<br>
			<button class="btn btn-primary" type="submit">Save</button>
		</form>
	</div>	
</div>