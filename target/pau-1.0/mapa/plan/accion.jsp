<%@ page import="java.util.List"%>
<%@ page import="aca.plan.spring.MapaPlan"%>
<%@ page import="aca.plan.spring.MapaVersion"%>
<%@ page import="aca.plan.spring.MapaMayorMenor"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript"> src ="../../validacion.js"> </script>
<script type="text/javascript">
	function Grabar(){
		if(document.frmplan.PlanId.value != "" && document.frmplan.CarreraId.value != ""){			
			document.frmplan.submit();
		}else{
			alert("Fill out the entire form, check for format errors");
		}
	}	
</script>
<%
	// Declaracion de variables	
	String facultad 		= request.getParameter("Facultad")==null?"0":request.getParameter("Facultad");
	String filtroEstado		= request.getParameter("FiltroEstado")==null?"T":request.getParameter("FiltroEstado");
	String planId			= request.getParameter("PlanId")==null?"":request.getParameter("PlanId");	
	String mensaje			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	String facultadNombre	= (String) request.getAttribute("facultadNombre");
	int numCursos			= (int) request.getAttribute("numCursos");
	MapaPlan plan			= (MapaPlan) request.getAttribute("plan");
	
	List<CatCarrera> lisCarreras 		= (List<CatCarrera>) request.getAttribute("lisCarreras");
	List<MapaVersion> lisVersiones 		= (List<MapaVersion>) request.getAttribute("lisVersiones");
	List<MapaMayorMenor> lisMayores 	= (List<MapaMayorMenor>) request.getAttribute("lisMayores");
	List<MapaMayorMenor> lisMenores 	= (List<MapaMayorMenor>) request.getAttribute("lisMenores");
%>
<div class="container-fluid">
	<h2>Edit Plan <small class="text-muted h4">( <%=facultad%> - <%=facultadNombre%>)</small></h2>
	<form action="grabarPlan" method="post" name="frmplan" target="_self">
	<input type="hidden" name="Facultad" value="<%=facultad%>">
	<input type="hidden" name="FiltroEstado" value="<%=filtroEstado%>">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="plan?Facultad=<%=facultad%>&FiltroEstado=<%=filtroEstado%>"><spring:message code='aca.Regresar'/></a>
	</div>
	<% if(!mensaje.equals("-")){ %>
		<div class="alert alert-info">The record was successfully saved</div>
	<% } %>
	<div class="row container-fluid">
		<div class="span4 col">
			<label for="Plan"><strong><spring:message code="aca.Plan"/></strong></label> 
<%			if(plan.getPlanId().equals("")){ %> 
			<i>(Must be 8 characters)</i> 
<%			} %>
			<input name="PlanId" type="text" class="text form-control" id="PlanId" style="width:200px;" value="<%=plan.getPlanId()%>" size="10" maxlength="8" <% if(!plan.getPlanId().equals("")){out.print("readonly");}%>>
			<br>
			<label for=""><strong><spring:message code='aca.Carrera'/></strong></label>
			<select name="CarreraId" id="CarreraId" class="form-select" style="width:600px;">
<%		for(CatCarrera carrera : lisCarreras){ %>
				<option value="<%=carrera.getCarreraId()%>" <%=plan.getCarreraId().equals(carrera.getCarreraId())?"Selected":""%>><%=carrera.getNombreCarrera()%></option>
<%		} %>	
	        </select>
	        <br>
	        <label for="Nombre Plan"><strong><spring:message code='aca.Nombre'/></strong></label>
	        <input name="NombrePlan" type="text" class="text form-control" style="width:600px;" id="NombrePlan" value="<%=plan.getNombrePlan()%>" size="60" maxlength="100">
<!-- 	        <br> -->
<%-- 	        <label for=""><strong>Female <spring:message code='aca.Nombre'/></strong></label> --%>
	        <input name="NombrePlanMujer" type="hidden" class="text form-control" style="width:600px;" id="NombrePlanMujer" value="<%=plan.getNombrePlanMujer()%>" size="60" maxlength="100" readonly>              
<!-- 	        <br>	          -->
<%-- 	        <label for=""><strong>GOB Course ID <spring:message code='aca.Nombre'/></strong> </label> --%>
	        <input name="CarreraSe" type="hidden" class="text form-control" style="width:600px;" id="CarreraSe" value="<%=plan.getCarreraSe()%>" size="60" maxlength="100" readonly>
<!-- 	        <br> -->
<!-- 			<label for=""><strong>GOB Plan ID</strong> </label> -->
	        <input name="PlanSE" type="hidden" class="text form-control" style="width:600px;" id="PlanSE" value="<%=plan.getPlanSE()%>" size="60" maxlength="60" readonly>
<!-- 	        <br> -->
<%-- 	        <label for=""><strong>GOB <spring:message code='aca.Clave'/></strong></label>  --%>
            <input name="ClaveProfesiones" type="hidden" class="text form-control" style="width:300px;" id="ClaveProfesiones" value="<%=plan.getClaveProfesiones()%>" size="30" maxlength="40" readonly>
	        <br>
	        <label for=""><strong>Passing Mark</strong></label>
	        <input name="Minimo" type="text" class="text form-control" style="width:150px;" id="Minimo" value="<%=plan.getMinimo()%>" size="3" maxlength="2">
	        <br>
	        <label for=""><strong>Failing Mark </strong></label>
	        <input name="Maximo" type="text" class="text form-control" style="width:150px;" id="Maximo" value="<%=plan.getMaximo()%>" size="3" maxlength="2">
		</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<div class="span4 col">
			<label for=""><strong>Remedial Mark</strong></label>
			<input name="NotaExtra" type="text" class="text form-control" style="width:300px;" id="NotaExtra" value="<%=plan.getNotaExtra()%>" size="3" maxlength="2">
<!-- 	        <br>	         -->
<%-- 			<label for=""><strong>Start <spring:message code='aca.RVOE'/></strong></label> --%>
			<input name="RvoeInicial" type="hidden" class="text form-control" style="width:300px;" id="RvoeInicial" value="<%=plan.getRvoeInicial()%>" size="30" maxlength="60" disabled>
			<br>
			<label for=""><strong>Version</strong></label>
			<select name="VersionId" id="VersionId" class="form-select" style="width:300px;">
<%		for( MapaVersion version : lisVersiones){ %>
				<option value="<%=version.getVersionId()%>" <%=plan.getVersionId().equals(version.getVersionId())?"Selected":""%>><%=version.getVersionNombre()%></option>
<%		}%>					
          	</select>
<!-- 	        <br> -->
<%-- 			<label for=""><strong><spring:message code='aca.RVOE'/></strong></label> --%>
			<input name="Rvoe" type="hidden" class="text form-control" style="width:300px;" id="Rvoe" value="<%=plan.getRvoe()%>" size="30" maxlength="60" disabled>
	        <br>
	        <label for=""><strong>No. Semesters</strong></label>
	        <input name="Ciclos" type="text" class="text form-control" style="width:300px;" id="Ciclos" value="<%=plan.getCiclos()%>" size="30" maxlength="60">
	        <br>
	        <label for=""><strong><spring:message code='aca.Tipo'/></strong></label>
	        <select name="Oficial" id="Oficial" class="form-select" style="width:300px;">
          		<option value='S' <%=plan.getOficial().equals("S") ? "Selected" : "" %>>Official</option>
          		<option value='N' <%=plan.getOficial().equals("N") ? "Selected" : "" %>>Informal</option>
          		<option value='I' <%=plan.getOficial().equals("I") ? "Selected" : "" %>>Languages</option>
<%--           		<option value='C' <%=plan.getOficial().equals("C") ? "Selected" : "" %>>CIMUM UM</option> --%>
          		<option value='R' <%=plan.getOficial().equals("R") ? "Selected" : "" %>>Remedial</option>
<%--           		<option value='A' <%=plan.getOficial().equals("A") ? "Selected" : "" %>>CIMUM ALLENDE</option> --%>
          	</select>
	        <br>
			<label for=""><strong>Modality</strong></label>
			<select name="EnLinea" id="EnLinea" class="form-select" style="width:300px;">
				<option value='N' <%=plan.getEnLinea().equals("N") ? "Selected" : "" %>>Face to Face</option>
				<option value='S' <%=plan.getEnLinea().equals("S") ? "Selected" : "" %>>Online</option>         			
         		<option value='M' <%=plan.getEnLinea().equals("M") ? "Selected" : "" %>>Mixed</option>        			
          	</select>
	        <br>
	        <label for=""><strong><spring:message code='aca.Status'/></strong></label>
	        <select name="Estado" id="Estado" class="form-select" style="width:300px;">
          		<option value='A' <%=plan.getEstado().equals("A")?"Selected":"" %>><spring:message code='aca.Admision'/></option>
          		<option value='V' <%=plan.getEstado().equals("V")?"Selected":"" %>><spring:message code='aca.Vigente'/></option>
          		<option value='I' <%=plan.getEstado().equals("I")?"Selected":"" %>><spring:message code='aca.Inactivo'/></option>
          	</select>
          	<br>
<!-- 	        <br> -->
<!-- 	        <label for=""><strong>Semsys Key</strong></label> -->
	        <input name="Semsys" type="hidden" class="text form-control" style="width:300px;" id="Semsys" value="<%=plan.getSemsys()%>" size="25" maxlength="20" disabled>
		</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<div class="span4 col">
			<label for=""><strong><spring:message code='aca.General'/></strong></label>
			<select name="General" id="General" class="form-select" style="width:300px;">
          		<option value='S' <%=plan.getGeneral().equals("S")?"Selected":"" %>><spring:message code='aca.Si'/></option>
          		<option value='N' <%=plan.getGeneral().equals("N")?"Selected":"" %>><spring:message code='aca.No'/></option>
          	</select>
	        <br>
	        <label for=""><strong>Last Modification Date</label></strong> (DD/MM/YYYY)
			<input name="FActualiza" type="text" class="text form-control" style="width:300px;" id="FActualiza" value="<%=plan.getFActualiza()==null?"":plan.getFActualiza()%>" onChange="validaFecha(this.value); this.value=borrar" size="10" maxlength="10">              
<!-- 	        <br>        -->
<!-- 	        <label for=""><strong>RVOE Date</strong></label> (DD/MM/YYYY) -->
	        <input name="FInicio" type="hidden" class="text form-control" style="width:300px;" id="FInicio" value="<%=plan.getFInicio()==null?"":plan.getFInicio()%>" onChange="validaFecha(this.value); this.value=borrar" size="10" maxlength="10" disabled>
	        <br>
	        <label for=""><strong>Validity Date </strong></label> (DD/MM/YYYY)
	        <input name="FFinal" type="text" class="text form-control" style="width:300px;" id="FFinal" value="<%=plan.getFFinal()==null?"":plan.getFFinal()%>" onChange="validaFecha(this.value); this.value=borrar" size="10" maxlength="10">
	        <br>
	        <label for=""><strong>Price</strong></label>
	        <select name="Precio" id="Precio" class="form-select" style="width:300px;">
          		<option value='S' <%=plan.getPrecio().equals("S")?"Selected":"" %>><spring:message code='aca.Si'/></option>
          		<option value='N' <%=plan.getPrecio().equals("N")?"Selected":"" %>><spring:message code='aca.No'/></option>
          	</select>
<!-- 	        <br> -->
<!-- 	         <label for=""><strong>GOB Identification</strong></label> -->
			<input name="Expediente" type="hidden" class="text form-control" style="width:300px;" id="Expediente" value="<%=plan.getExpediente()%>" size="30" maxlength="60" disabled>
          	<br>
          	<label for=""><strong>Credits to complete</strong></label>
	        <input name="Creditos" type="text" class="text form-control" style="width:300px;" id="Creditos" value="<%=plan.getCreditos()%>" size="10" maxlength="8">
          	<br>
          	<label for=""><strong>Original Plan</strong></label>
	        <input name="PlanOrigen" type="text" class="text form-control" style="width:300px;" id="PlanOrigen" value="<%=plan.getPlanOrigen()%>" size="10" maxlength="8">
          	<br>
		</div>
	</div>
	<br>
	<div class="alert alert-info">
		<a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>
	</div>
	</form>
</div>
