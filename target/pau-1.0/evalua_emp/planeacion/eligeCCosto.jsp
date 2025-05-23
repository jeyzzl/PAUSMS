<%-- 
    Document   : eligeCCosto
    Created on : 13/05/2014, 02:13:03 PM
    Author     : Daniel
--%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="aca.proyectos.DatosEncabezado"%>


<%    
    String clavejefe 	= (String) session.getAttribute("codigoPersonal");
    String id_ejercicio = (String) session.getAttribute("ejercicioId");
    
    request.getSession().setAttribute("TKL_clavejefe", clavejefe);
    
    if (request.getParameter("TKL_setDepto") != null) {
        request.getSession().setAttribute("TKL_ccosto", request.getParameter("TKL_ccosto"));
        
            //System.out.println(request.getAttribute("TKL_ccosto"));
            response.sendRedirect("misionvision");
        
    }

    DatosEncabezado de = new DatosEncabezado(conEnoc);
    List<String> catjefes = de.jefes(id_ejercicio);

%>
<div class="container-fluid">
	
	<%if (catjefes.contains(clavejefe)) { %>	
		<h2>Elegir Departamento</h2>
		
		<% if (session.getAttribute("TKL_ccosto") != null) { %>
			<div class="alert alert-info" >Se eligio <%= de.getCcostoNombre((String)session.getAttribute("TKL_ccosto"), id_ejercicio) %></div>
		<% }%>
		
		<hr />
	
		<form name="form1" method="post" action="">
		    <table class="table">
		       
		        <%
		            Iterator<String> itDeptos = de.deptosByJefes( clavejefe, id_ejercicio).iterator();
		            while (itDeptos.hasNext()) {
		                String ccosto = itDeptos.next();
		        %>
		        <tr>
		            <td><<%= ccosto%>> <%= de.getCcostoNombre( ccosto, id_ejercicio)%></td>
		
		            <td style="text-align: center"><input type="radio" name="TKL_ccosto" id="radio" value="<%= ccosto%>">
		            </td>
		        </tr>
		
		        <% }
		
		        %>
		    </table>
		    
		    <% if (request.getParameter("TKL_pagereference") != null) {%>
	         	<input type="hidden" name="TKL_pagereference" id="hiddenField" value="<%= request.getParameter("TKL_pagereference")%>">
	         <% } %>
		    
		    <div class="alert alert-info">
		    	<input type="submit" name="TKL_setDepto" id="setDepto" value="Cargar" class="btn btn-primary">
		    </div>
		    
		</form>
		
	<%} else { %>
	<table style="width:40%" align="center">
	    <tr align="center">
	        <td><b><font size="5">NO ES UN JEFE DE DEPARTAMENTO (ARON.CAT_JEFES)</font></b></td>
	    </tr>
	    <tr align="right">
	        <td>&nbsp;</td>
	    </tr>
	</table>
	<% }          
	%>


</div>    
<%@ include file= "../../cierra_enoc.jsp" %>