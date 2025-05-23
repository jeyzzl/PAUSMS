<%@page import="aca.calcula.spring.CalPagare"%>

<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>


<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<head>
</head>

<%	
	CalPagare pagare	= (CalPagare)request.getAttribute("pagare");
	String mensaje		= (String)request.getAttribute("mensaje");
	String periodoId	= (String)request.getAttribute("periodoId");
	String cargaId		= (String)request.getAttribute("cargaId");
	String bloqueId		= (String)request.getAttribute("bloqueId");
%>
<div class="container-fluid">
	<h1>Installments</h1>
	<div class="alert alert-info d-flex align-items-center">
		<a href="lista" class="btn btn-primary btn-sm"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
	</div>
<% if(mensaje.equals("1")){%>
	<div class="alert alert-success">
		Saved!
	</div>
<% }else if(mensaje.equals("2")){%>
	<div class="alert alert-danger">
		Did not Save!
	</div>
<% }%>
	<form name="forma" action="grabar" method='post'>
	<input type="hidden" name="PagareId" value="<%=pagare.getPagareId()%>">
		<input type="hidden" name="CargaId" value="<%=cargaId%>">
		<input type="hidden" name="BloqueId" value="<%=bloqueId%>">
		<div class="row">
			<div class="col">
				<label class="form-label">Name</label> 
				<input class="form-control" type="text" name="Nombre" value="<%=pagare.getPagareNombre()%>"/>
			</div>
			<div class="col">
				<label class="form-label">Date</label> 
				<input class="form-control" type="text" id="Fecha" name="Fecha" data-date-format="dd/mm/yyyy" value="<%=pagare.getFecha()%>"/>
			</div>
		</div><br>
		<button class="btn btn-primary" type="submit">Save</button>
	</form>
</div>
<script>
jQuery('#Fecha').datepicker();
</script>