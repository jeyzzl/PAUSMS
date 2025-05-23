<%@page import="aca.util.Fecha"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.AlumPersonal"%>
<%@page import="aca.graduacion.spring.AlumEgreso"%>
<%@page import="aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<head>
	<script type="text/javascript">
		function guardar(){
			if(document.forma.CodigoAlumno.value != ""){
				if(document.forma.Avance.value != ""){
				 	document.forma.submit();
				}else{
					alert("The progress is required to continue");
					document.forma.avance.focus();
				}
			}else{
				alert("The student is required to continue");
				document.forma.CodigoAlumno.focus();
			}
		}
		
		function modificar(){
			if(document.forma.Avance.value != ""){
				document.forma.submit();
			}else{
				alert("The progress is required to continue");
				document.forma.Avance.focus();
			}
		}
		function recarga(codigoPersonal,eventoId){
			var planId = document.getElementById("PlanId").value;  
			document.location.href = "editar_alumno?CodigoAlumno="+codigoPersonal+"&EventoId="+eventoId+"&PlanId="+planId;
		}
	</script>
</head>
<%
	// Declaracion de Variables
	java.text.DecimalFormat getformato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String codigoAlumno 	= (String)request.getAttribute("codigoAlumno");
	String eventoId 		= (String)request.getAttribute("eventoId");
	String planTemp 		= "";
	String promedio 		= "";
	String nombreAlumno 	= (String)request.getAttribute("nombreAlumno");
	String nombreEvento 	= (String)request.getAttribute("nombreEvento");
	String mensaje			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");

	String accion 			= request.getParameter("Accion") == null ? "0" : request.getParameter("Accion");	
	AlumEgreso alumno		= (AlumEgreso)request.getAttribute("alumno");
	
	List<String> planes 	= (List<String>)request.getAttribute("planes");	
	HashMap<String,MapaPlan> mapPlanes = (HashMap<String,MapaPlan>)request.getAttribute("mapPlanes");
%>
<body>
<div class="container-fluid">
	<h2>Student <small class="text-muted fs-6">( <%=nombreEvento%> - <%=codigoAlumno%> - <%=nombreAlumno%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="alumnos?EventoId=<%=eventoId%>"><spring:message code="aca.Regresar"/></a>
	</div>
<%	if(!mensaje.equals("-")){ %>
	<div class="alert alert-success"><%=mensaje%></div>
<%	} %>
	<form id="forma" name="forma" action="grabaEditarAlumno" method="post">
	<input type="hidden" name="EventoId" value="<%=eventoId%>">
	<input type="hidden" name="CodigoAlumno" value="<%=codigoAlumno%>">
	<div class="row">
		<div class="col-4">		
			<label for="PlanId"><b><spring:message code="aca.Plan"/>:</b></label>	
			<select id="PlanId" name="PlanId" onchange="javascript:recarga('<%=codigoAlumno%>','<%=eventoId%>')" class="form-select" style="width: 520px"/>
<%	for(int i=0; i<planes.size(); i++) {
		String nombrePlan 	= "-";
		planTemp = (String) planes.get(i);
		
		if(mapPlanes.containsKey(planTemp)){
			nombrePlan = mapPlanes.get(planTemp).getNombrePlan();
		}
%>
			<option value='<%=planTemp%>'
<%		if (alumno.getPlanId().equals(planTemp)) {
			out.print("Selected");
		}%>><%=nombrePlan%></option>
<%
	}
%>
			</select>
			<br>
			<label for="Avance"><b><spring:message code="aca.Avance"/></b></label>			
			<select id="Avance" name="Avance" class="form-select" style="width:120px">
				<option value="C"
					<%=alumno.getAvance().equals("C") ? " Selected" : ""%>>Complete</option>
				<option value="I"
					<%=alumno.getAvance().equals("I") ? " Selected" : ""%>>Incomplete</option>
			</select>
			<br>
			<label for="Titulado"><b>Degree</b></label>					
			<select id="Titulado" name="Titulado" class="form-select" style="width:120px">
				<option value="N"
					<%=alumno.getTitulado().equals("N") ? " Selected" : ""%>><spring:message code='aca.No'/></option>
				<option value="S"
					<%=alumno.getTitulado().equals("S") ? " Selected" : ""%>><spring:message code='aca.Si'/></option>
			</select>
<%
		if (alumno.getPromedio().equals("") || alumno.getPromedio().equals("0")) {
			promedio = getformato.format(Double.valueOf(alumno.getPromedio()));
		} else {
			promedio = String.format("%.2f", Double.valueOf(alumno.getPromedio()));
		}
%>
			<br>
			<label for="catalogos.extension.Colonia"><b>GPA</b><font color="#AE2113"> *</font></label>
			<input type="text" class="form-control" id="Promedio" name="Promedio" value="<%=promedio%>" maxlength="6" size="6" style="width:120px"/>			
			<br>
			<label for="Permiso"><b>Authorization</b></label>			
			<select id="Permiso" name="Permiso" class="form-select" style="width:120px">
				<option value="-"<%=alumno.getPermiso().equals("-") ? " Selected" : ""%>>Select</option>
				<option value="S"<%=alumno.getPermiso().equals("S") ? " Selected" : ""%>>YES</option>
				<option value="N"<%=alumno.getPermiso().equals("N") ? " Selected" : ""%>>NO</option>
			</select>
			<br>
			<label for="Permiso"><b>Comment</b></label>
			<textarea name="Comentario" class="form-control"><%=alumno.getComentario()%></textarea>
		</div>
	</div>
	<br>
	<div class="alert alert-info">
		<input class="btn btn-primary" type="button" onclick="<%=accion.equals("0") ? "guardar();" : "modificar()"%>" value="Save" />
	</div>
	</form>
</div>
</body>
</html>