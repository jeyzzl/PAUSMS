<%-- 
    Document   : accion_mivi
    Created on : 14/05/2014, 09:41:50 AM
    Author     : Daniel
--%>

<%@page import="aca.proyectos.OpRecFinancieros"%>
<%@page import="aca.proyectos.OPPolo"%>
<%@page import="java.util.StringTokenizer"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
    if (session.getAttribute("TKL_ccosto") == null) {
        response.sendRedirect("eligeCCosto?TKL_pagereference=misionvision");
    }
    if (request.getParameter("accion") == null) {
        response.sendRedirect("misionvision");
    }
    
    
    if (request.getParameter("guardar") != null) {
        OpRecFinancieros op = new OpRecFinancieros(conEnoc);
        if (request.getParameter("accion").equals("agregar")) {
            op.addDptoRecfinancieros(request);
        }else if(request.getParameter("accion").equals("editar")){

        }            
        response.sendRedirect("proyectos");
    }
    
    
    
%>

<div class="container-fluid">

	<h2>Agregar recursos financieros </h2>
	
	<div class="alert alert-info">
		<a href="proyectos" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
	</div>

	<form action="" method="post" name="formRecFin">
		<input name="TKL_idactividad" type="hidden" id="TKL_idactividad" value="<%= request.getParameter("TKL_idactividad")%>" >
		<input name="accion" type="hidden" id="accion" value="<%= request.getParameter("accion")%>">
		
	    <p>
	        <label for="select">Procedencia del recurso </label>
    <select name="tipo_recursos" id="tipo_recursos">
    <option value="Operativo">Operativo</option>
      <option value="Institucional"><spring:message code="aca.Institucion"/>al</option>
      <option value="Fdo Asignado">Fdo Asignado</option>
      <option value="Donativos">Donativos</option>
    </select>
    <label for="textfield">Importe</label>
    <input type="text" name="importe" id="importe">
		</p>
		
		<div class="alert alert-info">
			<input name="guardar" type="submit" value="Guardar" class="btn btn-primary btn-large">
		</div>
		
	</form>

</div>    

<%@ include file= "../../cierra_enoc.jsp" %>