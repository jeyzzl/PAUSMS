<%-- 
    Document   : accion_mivi
    Created on : 14/05/2014, 09:41:50 AM
    Author     : Daniel
--%>

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
        OPPolo op = new OPPolo(conEnoc);
        if (request.getParameter("accion").equals("agregar")) {
            op.addPolo(request);
        }else if(request.getParameter("accion").equals("editar")){

        }            
        response.sendRedirect("misionvision");
    }
    
    
    
%>

<div class="container-fluid">

	<h2>Agregar polo </h2>
	
	<div class="alert alert-info">
		<a href="misionvision" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
	</div>

	<form action="" method="post" name="formPolos">
		<input name="id_objetivo" type="hidden" id="id_objetivo" value="<%= request.getParameter("TKL_idobjetivo")%>" >
		<input name="accion" type="hidden" id="accion" value="<%= request.getParameter("accion")%>">
		
	    <p>
	    	    <label for="select"></label>
    <select name="TKL_polo" id="TKL_polo">
    <%
    String polos = "CRECIMIENTO,DESARROLLO ESPIRITUAL,MODELO EDUCATIVO,MARKETING,COMPASIÓN Y SERVICIO,RECURSOS HUMANOS,TUTORÍA,"
    + "INFRAESTRUCTURA,SEGURIDAD,TECNOLOGÍA,INVESTIGACIÓN,INNOVACIÓN ACREDITACIONES Y CERTIFICACIONES,SOLVENCIA FINANCIERA,RECUSOS FINANCIEROS,SUSTENTABILIDAD,PROCURACIÓN DE FONDO";
    
	StringTokenizer stk = new StringTokenizer(polos,",");
	    	    		while(stk.hasMoreTokens()){
	    	    			String salidapolo = stk.nextToken();
    %>
          <option value="<%= salidapolo %>"><%= salidapolo %></option>
          <% } %>
    </select>
		</p>
		
		<div class="alert alert-info">
			<input name="guardar" type="submit" value="Guardar" class="btn btn-primary btn-large">
		</div>
		
	</form>

</div>    

<%@ include file= "../../cierra_enoc.jsp" %>