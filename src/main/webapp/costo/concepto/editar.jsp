<%@page import="aca.calcula.spring.CalConcepto"%>

<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<head>
</head>

<%	
	CalConcepto concepto	= (CalConcepto)request.getAttribute("concepto");
	String mensaje			= (String)request.getAttribute("mensaje");
%>
<div class="container-fluid">
	<h1>Concepts</h1>
	<div class="alert alert-info d-flex align-items-center">
		<a href="lista" class="btn btn-primary btn-sm"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
	</div>
<% if(mensaje.equals("1")){%>
	<div class="alert alert-success">
		Saved
	</div>
<% }else if(mensaje.equals("2")){%>
	<div class="alert alert-danger">
		Error saving
	</div>
<% }%>
	<form name="forma" action="grabar" method='post'>
		<input type="hidden" value="<%=concepto.getConceptoId()%>" name="ConceptoId" />
		<div class="row">
			<div class="col">
				<label class="form-label">Name</label> 
				<input class="form-control" type="text" name="Nombre" value="<%=concepto.getConceptoNombre()%>"/>
			</div>
			<div class="col">
				<label class="form-label">Type</label> 
				<select class="form-select" name="Tipo">
					<option value="C" <%=concepto.getTipo().equals("C")?"selected":""%>>Credit</option>
					<option value="D" <%=concepto.getTipo().equals("D")?"selected":""%>>Debit</option>
				</select>
			</div>
		</div><br>
		<button class="btn btn-primary" type="submit">Save</button>
	</form>
</div>
