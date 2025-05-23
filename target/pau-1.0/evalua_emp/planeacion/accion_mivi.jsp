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

<%@page import="aca.proyectos.DatosEncabezado"%>
    
<%
    if (session.getAttribute("TKL_ccosto") == null) {
        response.sendRedirect("eligeCCosto?TKL_pagereference=misionvision");
    }
    if (request.getParameter("accion") == null) {
        response.sendRedirect("misionvision");
    }
    if (request.getParameter("guardar") != null) {
        DatosEncabezado de = new DatosEncabezado(conEnoc);
        if (request.getParameter("accion").equals("agregar")) {
            de.agregaMisionVision(request);
        }else if(request.getParameter("accion").equals("editar")){
            de.cambiaMisionVision( request);
        }            
        response.sendRedirect("misionvision");
    }
    
%>

<div class="container-fluid">

	<h2>Mision Vision</h2>
	
	<div class="alert alert-info">
		<a href="misionvision" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i> Regresar sin cambios</a>
	</div>

	<form action="" method="post" name="frmcategoria">
		<input name="ccosto" type="hidden" id="ccosto" value="<%= session.getAttribute("TKL_ccosto")%>" readonly>
		<input name="creador" type="hidden" id="creador" size="40" maxlength="40" value="<%= session.getAttribute("TKL_clavejefe")%>" readonly>
		<input name="accion" type="hidden" id="accion" value="<%= request.getParameter("accion")%>">
	    
	    <p>
	    	<label for="mision">Mision</label>
	    	<textarea name="mision" cols="60" id="mision"></textarea>
	    </p>
	    
	    <p>
	    	<label for="vision">Vision</label>
	    	<textarea name="vision" cols="60" id="vision"></textarea>
	    </p>
	    
	    
	    <div class="alert alert-info">
	    	<input name="guardar" type="submit" value="Guardar" class="btn btn-primary btn-large">
	    </div>
	</form>

</div>
   
<%@ include file= "../../cierra_enoc.jsp" %>