<%@page import="java.util.List"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.exa.spring.ExaTelefono"%>

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
	
	List<ExaTelefono> telefonos = (List<ExaTelefono>) request.getAttribute("telefonos");
	
%>
<style>
	i.icon-remove{
		cursor:pointer;
	}
</style>

<%@ include file= "menu.jsf" %>
<div class="container-fluid">
	<br><br>
	<table style="margin: 0 auto;">
	
				<button class="btn btn-primary  agregar" ><i class="icon-plus"></i> Add Phone Number</button>	
		
	</table>
	<table style="margin: 0 auto;" class="table table-bordered table-fontsmall">
		<tr>
			<th><spring:message code="aca.Eliminar"/>:</th>
			<th><spring:message code="aca.Telefono"/></th>
			<th><spring:message code="aca.Tipo"/></th>
		</tr>
<%
		for(ExaTelefono tel: telefonos){	
%>
		<tr>
			<td style="text-align:center;"><i class="fas fa-trash-alt" onclick="eliminar('<%=tel.getTelefonoId()%>');"></i><input type="hidden" value="<%=tel.getTelefonoId()%>"></td>
			<td><%=tel.getTelefono() %></td>
			<td><%=tel.getTipo() %></td>
		</tr>
<%
		}
		if(telefonos.size()==0){
%>
		<tr>
			<td colspan="6" style="text-align:center;"><h4>No registers were found</h4></td>
		</tr>
<%  	} %>
	</table>
	<form action="telefono" name="forma" >
		<input type="hidden" name="Accion" />
		<div class="bg-popup" ></div>
		<div class="popup">
			<table style="margin: 0 auto;" class="table table-condensed table-nohover">
				<tr>
					<td><spring:message code="aca.Telefono"/>:</td>
					<td><input name="telefono" id="telefono" type="text" maxlength="20" class="form-control" style="width:200px;" /></td>
				</tr>
				<tr>
					<td><spring:message code="aca.Tipo"/>:</td>
					<td>
						<select name="tipo" id="tipo" style="width:200px;">
							
							<option value="Celular"><spring:message code="aca.Celular"/></option>
							<option value="Casa" >Home:</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:left;border-bottom:0;">
						<input type="button" class="btn btn-primary" onclick="grabar();" value="Save"/>
						<input type="button" class="btn btn-danger cancel" value="Cancel">
					</td>
				</tr>
			</table>
		</div>
	</form>
	</div>
<script>

	function eliminar(telefonoId){
		if(confirm("¿Are you sure you want to delete this phone number?")){
			location.href = "telefono?Accion=2&telefonoId="+telefonoId;
		}
	}
	
	function grabar(){
		if(document.forma.telefono.value!=""){
			document.forma.Accion.value = "1";
			document.forma.submit();
		}else{
			alert('All fields are required');
		}
	}
	
	(function($){
		
		var popup = $('.popup');
		var bg = $('.bg-popup');
		
		$('.agregar').on('click', function(){
			show();
		});
		function show(){
			bg.show();
			popup.css({
				'left': $(window).width()/2 - popup.width()/2,
				'top' : $(window).height()/2 - popup.height()/2 - 40
			});
			popup.fadeToggle(200, function(){
				document.getElementById('telefono').focus();
			});
		}
		$('.cancel').on('click', close);
		bg.on('click', close);
		function close(){
			popup.fadeOut();
			bg.fadeOut();
			
			document.forma.telefono.value = "";		
		}
		
	})(jQuery);
</script>