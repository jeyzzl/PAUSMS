<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.exa.spring.ExaDireccion"%>
<%@page import="aca.exa.spring.ExaIglesia"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguroJquery.jsf" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
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
	
	ExaIglesia iglesia 			= (ExaIglesia) request.getAttribute("iglesia");
%>

<style>
	i.icon-remove{
		cursor:pointer;
	}
</style>

<%@ include file= "menu.jsf" %>
<form action="iglesia" name="forma">
	<input type="hidden" name="Accion" />
	<input type="hidden" name="iglesiaId" value="<%=iglesia.getIglesiaId()%>"/>
	<table style="margin: 0 auto;" class="table table-nohover table-bordered" style="margin-top:5px;">
		<tr>
			<td>Church:</td>
			<td><input value="<%=iglesia.getIglesia() %>" name="iglesia" id="iglesia" type="text" maxlength="250" class="form-control" style="width:300px;"/></td>
		</tr>
		<tr>
			<td>Function or Position:</td>
			<td><input value="<%=iglesia.getFuncion()==null?"":iglesia.getFuncion() %>" name="funcion" id="funcion" type="text" maxlength="250" class="form-control" style="width:300px;"/></td>
		</tr>
		<tr>
			<td colspan="2" style="text-align:left;border-bottom:0;">
				<input type="button" class="btn btn-primary" onclick="grabar();" value="Save"/>
				<%if(!iglesia.getIglesiaId().equals("")){ %>	
				<input type="button" class="btn btn-danger" onclick="borrar();"  value="Delete"/>
				<%} %>
			</td>
		</tr>
	</table>
</form>
<script>
	function grabar(){
		if(document.forma.iglesia.value!="" && document.forma.funcion.value!=""){ 
			document.forma.Accion.value="1";
			document.forma.submit();
		}else{
			alert("All fields are required!");
		}
	}	
	
	function borrar(){
		if(confirm("¿Are you sure you want to delete this register?")){
			document.forma.Accion.value="2";
			document.forma.submit();	
		}
	}
</script>