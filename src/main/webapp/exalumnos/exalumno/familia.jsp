<%@page import="java.util.List"%>
<%@page import="aca.exa.spring.ExaFamilia"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguroJquery.jsf" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String matricula 				= (String) session.getAttribute("codigoAlumno");	
	String alumnoNombre	 			= (String) request.getAttribute("alumnoNombre");
	boolean existeCorreo 			= (boolean) request.getAttribute("existeCorreo");
	boolean existeDireccion  		= (boolean) request.getAttribute("existeDireccion");
	boolean existeFamilia 			= (boolean) request.getAttribute("existeFamilia");
	boolean existeEgreso 			= (boolean) request.getAttribute("existeEgreso");
	boolean existeEmpleo 			= (boolean) request.getAttribute("existeEmpleo");
	boolean existeTelefono 			= (boolean) request.getAttribute("existeTelefono");
	boolean existeIglesia 			= (boolean) request.getAttribute("existeIglesia");
	boolean existeRed	 			= (boolean) request.getAttribute("existeRed");
	List<ExaFamilia> lisFamilia 	= (List<ExaFamilia>)request.getAttribute("lisFamilia");	
%>
<%@ include file= "menu.jsf" %>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<style>	
	i.icon-remove{
		cursor:pointer;
	}
	
</style>

<div class="container-fluid">
	<br><br>
	<button class="btn btn-primary agregar"><i class="icon-user"></i> Add Family</button>
	<table style="margin: 0 auto;" class="table table-bordered table-fontsmall">
		<tr>
			<th><spring:message code="aca.Eliminar"/>:</th>
			<th><spring:message code="aca.Nombre"/></th>
			<th>Relationship</th>
			<th>BirthDate</th>
			<th><spring:message code="aca.Correo"/></th>
			<th>Anniversary Date</th>
		</tr>
<%
	for(ExaFamilia fam: lisFamilia){	
%>
	<tr>
		<td style="text-align:center;"><a href="javascript:borrar('<%=fam.getFamiliaId()%>')"><i class="fas fa-trash-alt"></i></a></td>
		<td><%=fam.getNombre() %></td>
		<td><%=fam.getRelacion() %></td>
		<td><%=fam.getFechaNac()==null?"":fam.getFechaNac() %></td>
		<td><%=fam.getCorreo()==null?"":fam.getCorreo() %></td>
		<td><%=fam.getFechaAniv()==null?"":fam.getFechaAniv() %></td>
	</tr>
<%
	}
	if(lisFamilia.size()==0){
%>
	<tr>
		<td colspan="6" style="text-align:center;border-bottom:0;"><h4>No registers were found</h4></td>
	</tr>
<%  } %>
	</table>

	<form action="familia" name="forma">
		<input type="hidden" name="Accion" />
		<div class="bg-popup"></div>
		<div class="popup">
		<table style="margin: 0 auto;" class="table table-condensed table-nohover">
		<tr>
			<td><spring:message code="aca.Nombre"/>:</td>
			<td><input name="nombre" id="nombre" type="text" maxlength="150" class="form-control" style="width:300px;"/></td>
		</tr>
		<tr>
			<td>Relationship:</td>
			<td>
				<select name="relacion" id="relacion" style="width:300px;">
					<option value="Padre">Father</option>
					<option value="Madre">Mother</option>
					<option value="Esposo(a)">Spouse</option>
					<option value="Hijo(a)">Child</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>Birthdate:</td>
			<td>
				<input name="FechaNac" type="text" value="<%=aca.util.Fecha.getHoy()%>" data-date-format="dd/mm/yyyy" id="FechaNac" class="form-control" style="width:300px;">
			</td>
		</tr>
		<tr>
			<td><spring:message code="aca.Correo"/>:</td><td>
				<input name="correo" id="correo" type="text" maxlength="150" class="form-control" style="width:300px;"/></td>
		</tr>
		<tr>
			<td>Anniversary Date:</td>
			<td><input name="FechaAni" type="text" value="<%=aca.util.Fecha.getHoy()%>" data-date-format="dd/mm/yyyy" id="FechaAni" class="form-control" style="width:300px;"></td>
		</tr>
		<tr>
			<td colspan="2" style="text-align:left;border-bottom:0;">
				<input type="button" class="btn btn-primary" onclick="grabar();" value="Save"/>
				<input type="button" class="btn btn-danger" value="Cancel">
			</td>
		</tr>
		</table>
	</div>
	</form>
</div>
<script>
function grabar(){
	if(document.forma.nombre.value!="" && document.forma.FechaNac.value!=""){
		document.forma.Accion.value = "1";
		document.forma.submit();
	}else{
		alert('Name and Birthdate are required');
	}
}
function borrar(familiaId){
	if (confirm("Are you sure you want to delete this register?") ){
		location.href="familia?Accion=2&familiaId="+familiaId;
	}
}
(function($){
	
	$('#FechaNac').datepicker();
	$('#FechaAni').datepicker();	
	
	var popup = $('.popup');
			popup.fadeOut();
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
			document.getElementById('nombre').focus();
		});
	}
	
	$('.cancel').on('click', close);
	bg.on('click', close);
	function close(){
		popup.fadeOut();
		bg.fadeOut();
		
		var currentDate = new Date();
		var day = currentDate.getDate();
		if(day<10)day = "0"+day;
		var month = currentDate.getMonth() + 1;
		if(month<10)month = "0"+month;
		var year = currentDate.getFullYear();
		
		document.forma.nombre.value = "";
		document.forma.FechaNac.value = day+"/"+month+"/"+year;
		document.forma.correo.value = "";
		document.forma.FechaAni.value = day+"/"+month+"/"+year;
	}
	
	$('i.icon-remove').on('click', function(){
		if(confirm("Are you sure you want to delete this register?")){
			document.location.href="familia?Accion=2&familiaId="+$(this).siblings('input').val();
		}
	});
})(jQuery);
</script>