<%@page import="java.util.List"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.exa.spring.ExaRed"%>

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

	List<ExaRed> redes = (List<ExaRed>) request.getAttribute("redes");
	
%>
<style>
	i.icon-remove{
		cursor:pointer;
	}
</style>
<%@ include file= "menu.jsf" %>
	<br><br>
	<table style="margin: 0 auto;">
			<button class="btn btn-primary agregar"><i class="icon-globe"></i> Add Social Media</button>	
	</table>
	<table style="margin: 0 auto;" class="table table-bordered table-fontsmall">
		<tr>
			<th><spring:message code="aca.Eliminar"/>:</th>
			<th>Red</th>
		</tr>
<%
		for(ExaRed soc: redes){	
%>
		<tr>
			<td style="text-align:center;"><i class="fas fa-trash-alt" onclick="eliminar('<%=soc.getRedSocialId()%>');"></i><input type="hidden" value="<%=soc.getRedSocialId() %>"></td>
			<td><%=soc.getRed() %></td>
		</tr>
<%
		}
		if(redes.size()==0){
%>
		<tr>
			<td colspan="6" style="text-align:center;"><h4>No registers found</h4></td>
		</tr>
<%  	} %>
	</table>
	<form action="redSocial" name="forma">
		<input type="hidden" name="Accion" />
		<div class="bg-popup"></div>
		<div class="popup">
			<table style="margin: 0 auto;" class="table table-condensed table-nohover">
				<tr>
					<td>Social link:</td>
					<td><input name="red" id="red" type="text" maxlength="200" class="form-control" style="width:200px;"/></td>
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
<script>
	
	function grabar(){
		if(document.forma.red.value!="" ){
			document.forma.Accion.value = "1";
			document.forma.submit();
		}else{
			alert('All fields are required');
		}
	}
	
	function eliminar(redId){
		if(confirm("¿Are you sure you want to delete this register?")){
			location.href = "redSocial?Accion=2&redId="+redId;
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
				document.getElementById('red').focus();
			});
		}
		$('.cancel').on('click', close);
		bg.on('click', close);
		function close(){
			popup.fadeOut();
			bg.fadeOut();
			
			document.forma.red.value = "";
		}
	})(jQuery);
</script>