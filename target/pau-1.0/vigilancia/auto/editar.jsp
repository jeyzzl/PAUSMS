<%@page import="aca.vigilancia.spring.VigAuto"%>
<%@page import="java.util.List"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	String codigoUsuario 	= (String) session.getAttribute("codigoUltimo"); 
	VigAuto auto 			= (VigAuto) request.getAttribute("auto");
	String nombreUsuario	= (String) request.getAttribute("nombreUsuario");
	String mensaje 			=  request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
%>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<div class="container-fluid">	
	<h2>Registro de Autos<small class="text-muted fs-5">( <%=auto.getUsuario()%> - <%=nombreUsuario%> )</small></h2>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="listado"><i class="fas fa-arrow-left"></i></a>
<%	if (mensaje.equals("1")){ 
		out.print("&nbsp; &nbsp; ¡Grabado!");
	}else if (mensaje.equals("2")){ 
		out.print("&nbsp; &nbsp; ¡Error al grabar!"); 
	}
%>		
	</div>
	<form name="form" class="form-control" action="grabar" method="post">
		<input type="hidden" name="AutoId" value="<%=auto.getAutoId()%>"/>
		<input type="hidden" name="Usuario" value="<%=auto.getUsuario()%>">
 		<div class="mb-3 row">
		    <div class="col-sm-3">
		    	<label for="Placas" class="col-sm-2 col-form-label">Placas</label>
		      	<input type="text" class="form-control" name="Placas" value="<%=auto.getPlacas()%>">
		    </div>
		    <div class="col-sm-3">
		    	<label for="Engomado" class="col-sm-2 col-form-label">Engomado</label>
		      	<input type="text" class="form-control" name="Engomado" value="<%=auto.getEngomado()%>">
		    </div>	    
		    <div class="col-sm-3">
		    	<label for="Poliza" class="col-sm-2 col-form-label">Póliza</label>
		      	<input type="text" class="form-control" name="Poliza" value="<%=auto.getPoliza()%>">
		    </div>
		    <div class="col-sm-3">
		    	<label for="Fecha" class="col-form-label">Fecha (YYYY/MM/DD)</label>
		      	<input id="Fecha" name="Fecha" type="text" class="form-control" data-date-format="yyyy/mm/dd" value="<%=auto.getFecha()%>">
		    </div>		    		    
	  	</div>
 		<div class="mb-3 row">
		    <div class="col-sm-3">
		    	<label for="Modelo" class="col-sm-2 col-form-label">Modelo</label>
		      	<input type="text" class="form-control" name="Modelo" value="<%=auto.getModelo()%>">
		    </div>
		    <div class="col-sm-3">
		    	<label for="Marca" class="col-sm-2 col-form-label">Marca</label>
		      	<input type="text" class="form-control" name="Marca" value="<%=auto.getMarca()%>">
		    </div>
		     <div class="col-sm-3">
		    	<label for="Color" class="col-sm-2 col-form-label">Color</label>
		      	<input type="text" class="form-control" name="Color" value="<%=auto.getColor()%>">
		    </div>		    	     		   
		    <div class="col-sm-3">
		    	<label for="Estado" class="col-sm-2 col-form-label">Estado</label>
		    	<select name="Estado" class="form-select">
		    		<option value="A" <%=auto.getEstado().equals("A")?"selected":""%>>Activo</option>
		    		<option value="I" <%=auto.getEstado().equals("I")?"selected":""%>>Inactivo</option>
		    	</select>		      	
		    </div>
	  	</div>	  	
 		<div class="mb-3 row">
		    <div class="col-sm-12">
		    	<label for="Comentario" class="col-sm-2 col-form-label">Comentario</label>
		    	<textarea id="Comentario" name="Comentario" class="form-control" ><%=auto.getComentario()%></textarea>
		    </div>
	  	</div>	  	
 		<div class="alert alert-info">	    
			<button class="btn btn-primary" type="submit">Grabar</button>		    
	  	</div>
	</form>
</div>
<script>
	jQuery('#Fecha').datepicker();
</script>