<%@page import="java.util.List"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.exa.spring.ExaEmpleo"%>

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

	List<ExaEmpleo> empleos 	= (List<ExaEmpleo>) request.getAttribute("empleos");
	
%>
<style>
	i.icon-remove{
		cursor:pointer;
	}
</style>

<%@ include file= "menu.jsf" %>
<div class="container-fluid">
		<br><br>
		<button class="btn btn-primary agregar"><i class="icon-user" ></i> Add Employee</button>	
	<table style="margin: 0 auto;" class="table table-bordered table-fontsmall" >
		<tr>
			<th><spring:message code="aca.Eliminar"/>:</th>
			
			<th>Company</th>
			<th>Period</th>
		</tr>
<%
		for(ExaEmpleo emp: empleos){	
%>
		<tr>
	<td style="text-align:center;">	<a href="javascript:eliminar('<%=emp.getEmpleoId()%>')"><i class="fas fa-trash-alt"></i></a></td>
			<td><%=emp.getEmpresa() %></td>
			<td><%=emp.getPeriodo() %></td>
		</tr>
<%
		}
		if(empleos.size()==0){
%>
		<tr>
			<td colspan="6" style="text-align:center;"><h4>No registers were found</h4></td>
		</tr>
<%  	} %>
	</table>
	<form action="empleo" name="forma" >
		<input type="hidden" name="Accion" />
		<div class="bg-popup"></div>
		<div class="popup">
			<table style="margin: 0 auto;" class="table table-condensed table-nohover">
				<tr>
					<td>Company:</td>
					<td><input name="empresa" id="empresa" type="text" maxlength="250" class="form-control" style="width:300px;" /></td>
				</tr>
				<tr>
					<td>Period:</td>
					<td>
						<input name="periodo" id="periodo" type="text" maxlength="150" class="form-control" style="width:300px;"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:left;border-bottom:0;">
						<input type="button" class="btn btn-primary" onclick="grabar();" value="Save"/>
						<input type="button" class="btn btn-danger cancel"  value="Cancel">
					</td>
				</tr>
			</table>
		</div>
		
	</form>
	</div>

<script>
jQuery('ul.nav').children('li.empleo').addClass('active').find('i').eq(1).addClass('icon-white');

function grabar(){
	if(document.forma.empresa.value!="" && document.forma.periodo.value!=""){
		document.forma.Accion.value = "1";
		document.forma.submit();
	}else{
		alert('All fields are required');
	}
}
function eliminar(empleoId){
	if(confirm("¿Are you sure youo want to delete this employee register?")){
		location.href = "empleo?Accion=2&empleoId="+empleoId;
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
			document.getElementById('empresa').focus();
			document.getElementById('periodo').focus();
			
		});
	}
	$('.cancel').on('click', close);
	bg.on('click', close);
	function close(){
		popup.fadeOut();
		bg.fadeOut();
		
		document.forma.empresa.value = "";
		document.forma.periodo.value = "";
	}
	$('i.icon-remove').on('click', function(){
		if(confirm("¿Esta seguro que desea eliminar este registro?")){
			location.href="empleo?Accion=2&empleoId="+$(this).siblings('input').val();
		}
	});
})(jQuery);
</script>