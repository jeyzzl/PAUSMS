<%@ page import="aca.bitacora.spring.BitRequisito"%>
<%@ page import="aca.bitacora.spring.BitTramite"%>
<%@ page import="java.util.List"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	BitRequisito requisito	= (BitRequisito) request.getAttribute("requisito");
	String mensaje			= (String) request.getAttribute("mensaje");
%>

<div class="container-fluid">
	<h2>Requisito</h2>
	<div class="alert alert-info">
		<a href="listado" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
	</div>
<% if(mensaje.equals("1")){%>
	<div class="alert alert-success" role="alert">
  		Guardado.
	</div>
<% }%>
<% if(mensaje.equals("2")){%>
	<div class="alert alert-danger" role="alert">
  		No se guardo.
	</div>
<% }%>
	<form name="formTramite" action="grabar" method="post">
		<input type="hidden" name="RequisitoId" value="<%=requisito.getRequisitoId()%>">
		<label for="nombre">Nombre</label>
		<input type="text" class="text form-control" name="Nombre" style="width:450px" size="50" maxlength="50" value="<%=requisito.getNombre()%>">
		<br>
		<label for="Tipo">Tipo</label>
        <select name="Tipo" class="form-control" style="width:450px">
        	<option value="M" <%=requisito.getTipo().equals("M")?"selected":""%>>Manual</option>
           	<option value="A" <%=requisito.getTipo().equals("A")?"selected":""%>>Automatico</option>
        </select>
		<br>
        <label for="minimo">Descripción</label>
        <textarea class="form-control" style="width:450px" name="Comentario" rows="" cols=""><%=requisito.getComentario()%></textarea>
		<br>
		<div class="alert alert-info">
			<input class="btn btn-primary" type="submit" value="Grabar">
		</div>
	</form>
</div>