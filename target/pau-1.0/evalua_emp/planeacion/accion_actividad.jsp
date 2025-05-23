<%-- 
    Document   : accion_mivi
    Created on : 14/05/2014, 09:41:50 AM
    Author     : Daniel
--%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.proyectos.OPActividad"%>
<%@page import="aca.proyectos.OPProyectos"%>
<%@page import="aca.proyectos.OPMetas"%>
<%@page import="aca.proyectos.DatosEncabezado"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script src="../../bootstrap/datepicker/datepicker.js" type="text/javascript"></script>

<%
    if (session.getAttribute("TKL_ccosto") == null) {
        response.sendRedirect("eligeCCosto?TKL_pagereference=misionvision");
    }
    if (request.getParameter("accion") == null) {
        response.sendRedirect("misionvision");
    }
    String returnpage="misionvision";
    if(request.getParameter("returnpage")!=null){
        returnpage = request.getParameter("returnpage");
    }
    
    if (request.getParameter("guardar") != null) {
        OPActividad opac = new OPActividad(conEnoc);
        if (request.getParameter("accion").equals("agregar")) {
            opac.addDptoActividad(request);
        }else if(request.getParameter("accion").equals("editar")){
            opac.cambiaActividad( request);
        }            
        response.sendRedirect(returnpage);
    }
    
%>

<div class="container-fluid">

	<h2>Agregar actividad</h2>
	
	<div class="alert alert-info">
		<a href="<%= returnpage %>" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
	</div>
  
	<form action="" method="post" name="accion_meta">
		<input name="accion" type="hidden" id="accion" value="<%= request.getParameter("accion")%>">
	    <input name="TKL_id_proyecto" type="hidden" id="TKL_id_proyecto" value="<%= request.getParameter("TKL_id_proyecto")%>">
	    <input name="returnpage" type="hidden" id="accion" value="<%= returnpage %>">
	
	    <p>
	    	<label for="fecha_inicio">Fecha inicio</label>
	        <input id="fecha_inicio" name="fecha_inicio" class="input" style="margin:0;" type="text" maxlength="10" data-date-format="dd/mm/yyyy" />
	    </p>
	    <p>
	    	<label for="fecha_final">Fecha final</label>
	    	<input id="fecha_final" name="fecha_final" class="input" style="margin:0;" type="text" maxlength="10" data-date-format="dd/mm/yyyy" />
	    </p>
	    <p>
	    	<label for="descripcion">Descripcion del la actividad</label>
	    	<textarea name="descripcion" cols="60" id="descripcion"></textarea>
	    </p>
	          
	    <div class="alert alert-info">
	    	<input name="guardar" type="submit" value="Guardar" class="btn btn-primary btn-large">
	    </div>
	</form>
    
</div>

<script type="text/javascript">		
		jQuery('#fecha_inicio').datepicker();
        jQuery('#fecha_final').datepicker();
</script>
	
<%@ include file= "../../cierra_enoc.jsp" %>	