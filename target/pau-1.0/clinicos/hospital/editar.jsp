<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.rotaciones.spring.RotHospital"%>
<%@page import="aca.rotaciones.spring.RotInstitucion"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	RotHospital rotHospital = (RotHospital)request.getAttribute("rotHospital");
	List<RotInstitucion> lisInstituciones = (List<RotInstitucion>)request.getAttribute("lisInstituciones");
%>
<script>
function grabar(){
	if(document.frmHospital.HospitalNombre.value != "" && document.frmHospital.HospitalCorto.value != ""){
		document.frmHospital.submit();
	}else{
		alert('El hospital y la clave son requeridos');
		document.getElementById('HospitalNombre').focus();
	}
}

</script>
<div class="container-fluid">
	<h1>Catálogo de Hospitales</h1>
	<div class="alert alert-info">
		<a href="hospital" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
	</div>
	<form name="frmHospital" action="grabarHospital" method="post">
	<table class="table table-sm table-nohover">	
		<tr>
			<td>Id:</td>
			<td colspan="3"><input name="HospitalId" id="HospitalId" value="<%= rotHospital.getHospitalId()%>"" type="text" readonly /></td>
		</tr>
		<tr>
			<td>Hospital:</td>
			<td><input name="HospitalNombre" id="HospitalNombre" type="text" maxlength="100" class="input-xlarge" value="<%= rotHospital.getHospitalNombre()%>"/></td>
			<td>Corto:</td>
			<td><input name="HospitalCorto" id="HospitalCorto" type="text" maxlength="40" class="input-xlarge" value="<%= rotHospital.getHospitalCorto()%>"/></td>
		</tr>
		<tr>
			<td>Institución:</td>
			<td colspan="3">
				<select name="InstitucionId" id="InstitucionId">
					<%for(RotInstitucion inst: lisInstituciones){ %>
						<option value="<%=inst.getInstitucionId()%>" <%=inst.getInstitucionId().equals(rotHospital.getInstitucionId())?"selected":""%>><%=inst.getInstitucionNombre()%></option>
					<%} %>
				</select>
			</td>
		</tr>
		<tr>
			<td>Calle:</td>
			<td><input name="Calle" id="Calle" type="text" maxlength="100" value="<%= rotHospital.getCalle()%>" class="input-xlarge" /></td>
			<td>Colonia:</td>
			<td><input name="Colonia" id="Colonia" type="text" maxlength="70" value="<%= rotHospital.getColonia()%>" class="input-xlarge" /></td>
		</tr>
		<tr>
			<td><spring:message code="aca.Estado"/>:</td>
			<td><input name="MunEdo" id="MunEdo" type="text" maxlength="70" value="<%= rotHospital.getMunEdo() %>" class="input-xlarge" /></td>
			<td>Pais:</td>
			<td><input name="Pais" id="Pais" type="text" maxlength="50" value="<%= rotHospital.getPais()%>" class="input-xlarge" /></td>
		</tr>
		<tr>
			<td>Teléfono:</td>
			<td><input name="Telefono" id="Telefono" type="text" maxlength="40" value="<%= rotHospital.getTelefono()%>" class="input-xlarge" /></td>
			<td>Fax:</td>
			<td><input name="Fax" id="Fax" type="text" maxlength="40" value="<%= rotHospital.getFax()%>" class="input-xlarge" /></td>
		</tr>
		<tr>
			<td>Médico:</td>
			<td><input name="Medico" id="Medico" type="text" maxlength="100" value="<%= rotHospital.getMedico()%>" class="input-xlarge" /></td>
			<td>Puesto:</td>
			<td><input name="Puesto" id="Puesto" type="text" maxlength="70" value="<%= rotHospital.getPuesto()%>" class="input-xlarge" /></td>
		</tr>
		<tr>
			<td>Saludo:</td>
			<td colspan="3"><input name="Saludo" id="Saludo" type="text" maxlength="70" value="<%= rotHospital.getSaludo()%>" class="input-xlarge" /></td>
		</tr>		
	</table>
	<div class="alert alert-info">
		<input type="button" class="btn btn-primary" onclick="grabar();" value="Grabar"/>
		<input type="button" class="btn btn-danger" value="Cancelar">
	</div>	
	</form>
</div>