<%@page import="java.util.List"%>
<%@page import="aca.bitacora.spring.BitEstado"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	String codigoPersonal		= (String) request.getAttribute("codigoPersonal");
	String folio				= (String) request.getAttribute("folio");
	String comentario			= (String) request.getAttribute("comentario");
	String estadoId				= (String) request.getAttribute("estadoId");
	String mensaje				= (String) request.getAttribute("mensaje");
	String nombreArea			= (String) request.getAttribute("nombreArea");
	
	// Lista de estados permitidos 
	List<BitEstado> lisEstados 	= (List<BitEstado>) request.getAttribute("lisEstados");
%>
<div class="container-fluid">
	<h2>Nuevo estado</h2>
	<div class="alert alert-info">
		<a href="etiquetas?Folio=<%=folio%>" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
<% if(!mensaje.equals("X")){%>
	<div class="alert alert-success" role="alert"><%=mensaje%></div>
<% }%>
	<form name="form" action="grabarEstado" method="POST">
	<div class="row container-fluid">
		<div class="span3">
			<input type="hidden" name="Accion" value="1">
			<input type="hidden" name="Folio" value="<%=folio%>">			
			<label for="folio">Folio</label>
			<input type="text" class="text form-control" name="folio" size="50"  value="<%=folio%>" readonly="readonly">
			<br><br>
			<label for="area">Area</label>
			<input type="text" class="text form-control" name="area" size="50" value="<%=nombreArea%>" readonly="readonly">  
			<br><br>
			<label for="Comentario">Comentario</label>
			<textarea name="Comentario" class="form-control" rows="" cols="50"><%=comentario%></textarea>
			<br><br>
			<label for="EstadoId">Estado</label>
			<select name="EstadoId" class="form-control">
	<%	for (BitEstado estado : lisEstados){%>		
				<option value="<%=estado.getEstado()%>"><%=estado.getEstadoNombre() %></option>
	<%	}	%>	
			</select>
			<br><br>
		</div>
	</div>&nbsp;
	<div class="alert alert-info">
		<input type="submit" class="btn btn-primary" value="Guardar">
	</div>	
	</form>
</div>