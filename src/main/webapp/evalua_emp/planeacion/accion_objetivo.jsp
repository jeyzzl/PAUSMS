<%-- 
    Document   : accion_mivi
    Created on : 14/05/2014, 09:41:50 AM
    Author     : Daniel
--%>

<%@page import="java.util.StringTokenizer"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.proyectos.OPObjetivos"%>

<%
    if (session.getAttribute("TKL_ccosto") == null) {
        response.sendRedirect("eligeCCosto?TKL_pagereference=misionvision");
    }
    if (request.getParameter("accion") == null) {
        response.sendRedirect("misionvision");
    }
    if (request.getParameter("guardar") != null) {
        OPObjetivos op = new OPObjetivos(conEnoc);
        if (request.getParameter("accion").equals("agregar")) {
            op.addDptoObjetivo( request);
        }else if(request.getParameter("accion").equals("editar")){
            op.cambiaObjetivo( request);
        }            
        response.sendRedirect("misionvision");
    }
    
%>

<div class="container-fluid">

	<h2>Agregar objetivo </h2>
	
	<div class="alert alert-info">
		<a href="misionvision" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
	</div>

	<form action="" method="post" name="frmcategoria">
		<input name="id_ccosto" type="hidden" id="id_ccosto" value="<%= session.getAttribute("TKL_ccosto")%>" >
		<input name="TKL_programa" type="hidden" id="TKL_programa" value="<%= request.getParameter("TKL_programa")%>" >
		<input name="accion" type="hidden" id="accion" value="<%= request.getParameter("accion")%>">
		
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