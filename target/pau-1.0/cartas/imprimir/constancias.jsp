<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
	
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");
	String usuario 				= (String) session.getAttribute("codigoPersonal");
	boolean existeAlumno		= (boolean) request.getAttribute("existeAlumno");
	
	if( existeAlumno == false ){
		codigoAlumno = "";
	}
%>

<div class="container-fluid">
	<h1>Constancias</h1>
	<form action="vistaPrevia" target="_blank">
		<div class="alert alert-info d-flex align-items-center">
			
			<input value="<%=codigoAlumno %>" type="" class="form-control" style="width:200px" name="codigoId" id="codigoId" placeholder="Codigo del Alumno" style="margin:0;" />&nbsp;&nbsp;
			
			<select name="constanciaId" id="constanciaId" class="form-select" style="width:500px"  >
			
			</select>&nbsp;&nbsp;
			
			<select name="opcional" id="opcional" class="form-select" style="width:200px">
				<option value="sin">Sin</option>
				<option value="con">Con</option>
			</select>&nbsp;&nbsp;
			
			<button class="btn btn-primary"><i class="icon-print icon-white"></i> Imprimir</button>
			
		</div>
	
	</form>
	
	<div class="resultado"></div>
	
</div>	

<script>
	jQuery('#codigoId').focus();
	
	var resultado 		= jQuery('.resultado');
	var constancias		= jQuery('#constanciaId');
	var constanciaId 	= jQuery('#constanciaId');
	var codigoId 		= jQuery('#codigoId');
	
	function getInfo(){
		if(codigoId.val().length == 7){
			jQuery.get("getInfo?constanciaId="+constanciaId.val()+"&codigoPersonal="+codigoId.val(), function(r){
				resultado.html(r);
			});
		}else{
			resultado.html('');
		}
	}
	
	constanciaId.on('change', function(){
		getInfo();
	});
	
	function getConstancias(){
		if(codigoId.val().length == 7){
			jQuery.get("getConstanciasAlumno?codigoPersonal="+codigoId.val(), function(r){
				constancias.html(r);
				getInfo();
			});
		}else{
			constancias.html('');
			resultado.html('');
		}
	}
	
	codigoId.on('keyup', function(){
		getConstancias();
	});
	
	codigoId.on('change', function(){
		getConstancias();
	});
	
	getConstancias();
</script>