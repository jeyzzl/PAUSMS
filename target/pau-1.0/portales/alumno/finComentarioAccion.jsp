<%@ page import= "aca.financiero.spring.FinComentario"%>
<%@ page import= "aca.financiero.spring.FinRoles"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../body.jsp"%>
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
	
	function Borrar(){
		if (confirm("¿Estas seguro que quieres borrar?")){
			document.form.Accion.value="3";
			document.form.submit();
		}
	}
</script>

<%
	String matricula 		= (String)request.getAttribute("matricula");
	String usuario			= (String)request.getAttribute("usuario");
	
	String mensaje			= (String)request.getAttribute("mensaje");
	String alert			= (String)request.getAttribute("alert");
	
	String accion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String folio			= request.getParameter("Folio");
	
	String comentario		= (String)request.getAttribute("comentario");
	String fecha			= (String)request.getAttribute("fecha");
%>

<div class="container-fluid">
	<h2>Registro de comentarios</h2>
	<div class="alert alert-info">
		<a href="financiero" class="btn btn-primary"><i class="fas fa-arrow-left"></i> Regresar</a>
	</div>
<%
	if(!mensaje.equals("X")){
%>
		<div class="alert alert-<%=alert%>" role="alert"><%=mensaje%></div>
<%
	}
%>
	<form name="form" action="finComentarioAccion" method="POST">
		<input name="Accion" type="hidden" />
		<div class="row">
			<div class="span3">
				<label for="Folio"><spring:message code='aca.Folio'/></label>			
				<input name="Folio" type="text" class="text" id="folio" value="<%=folio%>" size="3" maxlength="3" readonly="readonly">
				<br><br>
				<label>Fecha para mostrar:</label>
			    <input name="FechaCom" type="text" id="FechaCom" size="12" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%=fecha%>">
			    <br><br>
				<label for="Comentario"><spring:message code="aca.Comentario"/>:</label>
				<textarea id="Comentario" name="Comentario" rows="4" cols="120"><%=comentario%></textarea>
			    <br><br>			
			</div>			
		</div>
		<br>
<%		if(!accion.equals("3")){ %>
		<div class="alert alert-info">
	      	<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> 
	      	<a class="btn btn-primary" href="javascript:Borrar()">Borrar</a> 
		</div>
<% 		}%>
	</form>
</div>
<script>
	jQuery('#FechaCom').datepicker();
	jQuery('#FechaCom').datepicker();	
</script>