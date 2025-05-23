<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ include file="menu.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<script type="text/javascript">
	function Grabar(){
		if(document.form.FechaCom.value!="" && document.form.Comentario.value!=""){			
			document.form.Accion.value="2";
			document.form.submit();
		}else{
			alert("Complete el formulario ..! ");
		}
	}
</script>

<%
	String matricula 		= (String)request.getAttribute("matricula");
	String usuario			= (String)request.getAttribute("usuario");
	
	String tipoEmpleado		= (String)request.getAttribute("tipoEmpleado");
	String mensaje			= (String)request.getAttribute("mensaje");;
	String alert			= (String)request.getAttribute("alert");
	
	String accion			= (String)request.getAttribute("accion");
	
	String folio			= (String)request.getAttribute("folio");
	String comentario		= (String)request.getAttribute("comentario");
	String fecha			= (String)request.getAttribute("fecha");
%>

<div class="container-fluid">
	<h2><a href="financiero"><i class="fas fa-arrow-left"></i></a> Comments Record</h2>
	<hr>	
<%
	if(!mensaje.equals("X")){
%>
		<div class="alert alert-<%=alert%>" role="alert"><%=mensaje%></div>
<%
	}
%>
	<form name="form" class="mb-0" action="finComentario" method="POST">
		<input name="Accion" type="hidden" />
		<label>Show Date:</label>
		<br>
		<input name="FechaCom" type="text" class="form-control" id="FechaCom" style="width:250px" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%=fecha%>">
		<br>
		<label for="Comentario"><spring:message code="aca.Comentario"/>:</label>
		<br>
		<textarea id="Comentario" name="Comentario" class="form-control h-6"><%=comentario%></textarea>				
		<br><br>		
		<div class="alert alert-info">
	      	<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> 
		</div>
	</form>
</div>
<script>
	jQuery('#FechaCom').datepicker();
	jQuery('#FechaCom').datepicker();	
</script>