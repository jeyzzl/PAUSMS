<%@page import="aca.exa.spring.ExaEstudio"%>
<%@ page import="java.util.List"%>
<%@ page import="aca.util.Fecha"%>


<%@ include file= "id.jsp" %>
<%@ include file= "../../seguroJquery.jsf" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%@ include file= "menu.jsf" %>
<%
	String matricula 			= (String) session.getAttribute("codigoAlumno");
	String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion");
	List<ExaEstudio> lisEstudios 	= (List<ExaEstudio>) request.getAttribute("lisEstudios");
	if(accion.equals("1")){//Grabar nuevo 
		String tiempo = Fecha.getFechayHora()+" ";
	}else if(accion.equals("2")){//Borrar 
	}
%>
<style>
	i.icon-remove{
		cursor:pointer;
	}
</style>

<table style="margin: 0 auto;">
	<tr>
		<td>
			<button class="btn agregar"><i class="icon-file"></i> Agregar Estudio</button>	
		</td>
	</tr>
</table>

<table style="margin: 0 auto;" class="form-control" style="width:300px;">
	<tr>
		<th><spring:message code="aca.Eliminar"/>:</th>
		<th>Estudio</th>
		<th>Institución</th>
		<th>Periodo</th>
	</tr>
<%
	for(ExaEstudio est: lisEstudios){	
%>
	<tr>
		<td style="text-align:center;"><i class="fas fa-trash-alt"></i><input type="hidden" value="<%=est.getEstudioId() %>"></td>
		<td><%=est.getEstudios() %></td>
		<td><%=est.getInstitucion() %></td>
		<td><%=est.getPeriodo() %></td>
	</tr>
<%
	}
	if(lisEstudios.size()==0){
%>
	<tr>
		<td colspan="6" style="text-align:center;"><h4>No se encontraron registros</h4></td>
	</tr>
<%  } %>
</table>

<form action="estudio" name="forma">
<input type="hidden" name="Accion" />
<div class="bg-popup"></div>
<div class="popup">
	<table style="margin: 0 auto;" class="table table-condensed table-nohover">
	<tr>
		<td>Estudio:</td>
		<td><input name="estudio" id="estudio" type="text" maxlength="250" class="input-xlarge" /></td>
	</tr>
	<tr>
		<td>Institución:</td>
		<td><input name="institucion" id="institucion" type="text" maxlength="250" class="input-xlarge" /></td>
	</tr>
	<tr>
		<td>Periodo:</td>
		<td><input name="periodo" id="periodo" type="text" maxlength="150" class="input-xlarge" /></td>
	</tr>
	<tr>
		<td colspan="2" style="text-align:left;border-bottom:0;">
			<input type="button" class="btn btn-primary" onclick="grabar();" value="Grabar"/>
			<input type="button" class="btn btn-danger" value="Cancelar"></td>
	</tr>
</table>
</div>
</form>

<script>
function grabar(){
	if(document.forma.estudio.value!="" && document.forma.institucion.value!="" && document.forma.periodo.value!=""){
		document.forma.Accion.value = "1";
		document.forma.submit();
	}else{
		alert('Todos los campos son requeridos');
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
			document.getElementById('estudio').focus();
		});
	}
	$('.cancel').on('click', close);
	bg.on('click', close);
	function close(){
		popup.fadeOut();
		bg.fadeOut();
		
		document.forma.estudio.value = "";
		document.forma.institucion.value = "";
		document.forma.periodo.value = "";
	}
	
	$('i.icon-remove').on('click', function(){
		if(confirm("¿Esta seguro que desea eliminar este registro?")){
			location.href="estudio?Accion=2&estudioId="+$(this).siblings('input').val();
		}
	});
})(jQuery);
</script>