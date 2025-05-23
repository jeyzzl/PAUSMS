<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.exa.spring.ExaCorreo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguroJquery.jsf" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String matricula 			= (String) session.getAttribute("codigoAlumno");		
	ExaCorreo exaCorreo 		= (ExaCorreo)request.getAttribute("exaCorreo");
	
	AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");
	String alumnoNombre	 		= (String) request.getAttribute("alumnoNombre");	
	boolean existeCorreo 		= (boolean) request.getAttribute("existeCorreo");
	boolean existeDireccion  	= (boolean) request.getAttribute("existeDireccion");
	boolean existeFamilia 		= (boolean) request.getAttribute("existeFamilia");
	boolean existeEgreso 		= (boolean) request.getAttribute("existeEgreso");
	boolean existeEmpleo 		= (boolean) request.getAttribute("existeEmpleo");
	boolean existeTelefono 		= (boolean) request.getAttribute("existeTelefono");
	boolean existeIglesia 		= (boolean) request.getAttribute("existeIglesia");
	boolean existeRed	 		= (boolean) request.getAttribute("existeRed");
	String totCorreo 			= (String) request.getAttribute("totCorreo");
	String totDireccion 	 	= (String) request.getAttribute("totDireccion");
	String totFamilia 			= (String) request.getAttribute("totFamilia");	
	String totEstudio			= (String) request.getAttribute("totEstudio");
	String totEmpleo 			= (String) request.getAttribute("totEmpleo");
	String totTelefono 			= (String) request.getAttribute("totTelefono");
	String totIglesia 			= (String) request.getAttribute("totIglesia");
	String totRed	 			= (String) request.getAttribute("totRed");
%>
<style>
	i.icon-remove{
		cursor:pointer;
	}
</style>
<%@ include file= "menu.jsf" %>
<div class="container-fluid">
	<form action="correo" name="forma">
	<input type="hidden" name="Accion"/>
	<input type="hidden" name="correoId" value="<%=exaCorreo.getCorreoId() %>"/>
	<table class="table table-sm">
		<tr>
			<td><spring:message code="aca.Correo"/>:</td>
			<td><input value="<%=exaCorreo.getCorreo() %>" name="correo" id="correo"  type="email" maxlength="30" class="form-control" style="width:200px;"/>
			</td>
		</tr>
		<tr>
			<td colspan="2" style="text-align:left;border-bottom:0;">
				<input type="button" class="btn btn-primary" onclick="grabar();" value="Save"/>
				<%if(!exaCorreo.getCorreoId().equals("")){ %>
				<input type="button" class="btn btn-danger" onclick="borrar('exaCorreo.getCorreo()');" value="Erase"/>
				<%} %>
			</td>
		</tr>
	</table>
	</form>
</div>	
<script>
	function grabar(){
		if(document.forma.correo.value!=""){ 
			document.forma.Accion.value="1";
			document.forma.submit();
		}else{
			alert("All fields are required!");
		}
	}	
	function borrar(correoId){
		if(confirm("Are you sure you want to delete this field?")){
			document.forma.Accion.value="2";
			document.forma.submit();
		}
	}
</script>