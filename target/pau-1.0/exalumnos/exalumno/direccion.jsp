<%@page import="java.util.List"%>
<%@page import="aca.exa.spring.ExaPais"%>
<%@page import="aca.exa.spring.ExaEstado"%>
<%@page import="aca.exa.spring.ExaDireccion"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguroJquery.jsf" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String matricula 			= (String) session.getAttribute("codigoAlumno");	
	String accion 				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	ExaDireccion direccion 		= (ExaDireccion)request.getAttribute("direccion");
	
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
	
	List<ExaPais> lisPaises 				= (List<ExaPais>)request.getAttribute("lisPaises");	
	List<ExaEstado> lisEstados 				= (List<ExaEstado>)request.getAttribute("lisEstados");	
%>
<style>
	i.icon-remove{
		cursor:pointer;
	}
</style>
<%@ include file= "menu.jsf" %>
<div class="container-fluid">
	<form action="direccion" name="forma">
	<input type="hidden" name="Accion" />
	<input type="hidden" name="dirId" value="<%=direccion.getDireccionId() %>"/>

	<table style="margin: 0 auto;" class="table table-nohover " style="margin-top:15px;">
	<tr>
		<td>Pais:</td>
		<td>
			<select name="pais" id="pais" onchange="refreshEstado();" style="width:300px;">
				<%for(ExaPais pais : lisPaises){%>
					<option <%if(direccion.getPaisId().equals(pais.getPaisId()))out.print("selected");%> value="<%=pais.getPaisId() %>"><%=pais.getPaisNombre()%></option>
				<%} %>
			</select>
		</td>
	</tr>
	<tr>
		<td><spring:message code="aca.Estado"/>:</td>
		<td>
			<select name="estado" id="estado" style="width:300px;">
				<%for(ExaEstado estado : lisEstados){%>
					<option <%if(direccion.getEstadoId().equals(estado.getEstadoId()))out.print("selected");%> value="<%=estado.getEstadoId() %>"><%=estado.getNombreEstado()%></option>
				<%} %>
			</select>
		</td>
	</tr>
	<tr>
		<td>Ciudad:</td>
		<td><input value="<%=direccion.getCiudad() %>" name="ciudad" id="ciudad" type="text" maxlength="150" class="form-control" style="width:300px;"/></td>
	</tr>
	<tr>
		<td>Dirección:</td>
		<td><input value="<%=direccion.getDireccion() %>" name="direccion" id="direccion" type="text" maxlength="250" class="form-control" style="width:300px;"/></td>
	</tr>
	<tr>
		<td><spring:message code="aca.Codigo"/> Postal:</td>
		<td>
			<input value="<%=direccion.getCp()%>" name="codigoPostal" id="codigoPostal" type="text" maxlength="50" class="form-control" style="width:300px;"/>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="text-align:left;border-bottom:0;">
			<input type="button" class="btn btn-primary" onclick="grabar();" value="Grabar"/>
			<%if(!direccion.getDireccionId().equals("")){ %>	
			<input type="button" class="btn btn-danger" onclick="borrar();" value="Borrar"/>
			<%} %>
		</td>
	</tr>
	</table>
	</form>
</div>
<script>
	
	function refreshEstado(){
		var pais = document.forma.pais.value;
		jQuery.get('getEstados?pais='+pais, function(res){
			jQuery('#estado').html(res);
		});
	}
	
	function grabar(){
		if(document.forma.ciudad.value!="" && document.forma.direccion.value!="" && document.forma.codigoPostal.value!=""){
			document.forma.Accion.value="1";
			document.forma.submit();
		}else{
			alert("Todos los campos son requeridos!");
		}
	}	
	
	function borrar(){
		if(confirm("¿Estás seguro de borrar este registro?")){
			document.forma.Accion.value="2";
			document.forma.submit();
		}
	}
</script>