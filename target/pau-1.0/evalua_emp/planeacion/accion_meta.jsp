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

<%@page import="aca.proyectos.OPMetas"%>
<%@page import="aca.proyectos.DatosEncabezado"%>

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
        OPMetas op = new OPMetas(conEnoc);
        if (request.getParameter("accion").equals("agregar")) {
            op.addDptoMetas( request);
        }else if(request.getParameter("accion").equals("editar")){
            op.cambiaMetas( request);
        }            
        response.sendRedirect(returnpage);
    }
    // 3221132885 casa
    //3221001327 hector carreño
    OPMetas om = new OPMetas(conEnoc);
%>

<div class="container-fluid">

	<h2>Agregar meta</h2>
	
	<div class="alert alert-info"><% if(request.getParameter("TKL_id_objetivo")!=null){ %><%= om.getObjetivo( new Integer((String)request.getParameter("TKL_id_objetivo"))) %><% } %></div>
	
	<div class="alert alert-info">
		<a href="<%= returnpage %>" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
	</div>

	<form action="" method="post" name="accion_meta">
		<input name="accion" type="hidden" id="accion" value="<%= request.getParameter("accion")%>">
       	<input name="id_objetivo" type="hidden" id="accion" value="<%= request.getParameter("TKL_id_objetivo")%>">
       	<input name="returnpage" type="hidden" id="accion" value="<%= returnpage %>">
	    
	    <p>
	    	<label for="descripcion"><spring:message code='aca.Descripcion'/></label>
	    	<textarea name="descripcion" cols="60" id="descripcion"></textarea>
	    </p>
	    
	    <div class="alert alert-info">
	    	<input name="guardar" type="submit" value="Guardar" class="btn btn-primary btn-large">
	    </div>
	</form>

</div>
<%@ include file= "../../cierra_enoc.jsp" %>	