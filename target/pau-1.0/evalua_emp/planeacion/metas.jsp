<%-- 
    Document   : metas
    Created on : 15/05/2014, 03:28:16 PM
    Author     : Daniel
--%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.proyectos.DptoPersonal"%>
<%@page import="aca.proyectos.DptoResponsableProy"%>
<%@page import="aca.proyectos.OpResponsableProy"%>
<%@page import="aca.proyectos.DptoProyectos"%>
<%@page import="aca.proyectos.OPProyectos"%>
<%@page import="aca.proyectos.DptoMetas"%>
<%@page import="java.util.Iterator"%>
<%@page import="aca.proyectos.OPMetas"%>
<%@page import="aca.proyectos.DatosEncabezado"%>

<%
    
    session.removeAttribute("TKL_meta");
    
    int id_objetivo = 0;
    if (session.getAttribute("TKL_idobjetivo") == null) {
        if (request.getParameter("TKL_idobjetivo") == null) {
            response.sendRedirect("misionvision");
        } else {
            session.setAttribute("TKL_idobjetivo", request.getParameter("TKL_idobjetivo"));
            id_objetivo = new Integer((String) session.getAttribute("TKL_idobjetivo"));
        }
    } else {
        id_objetivo = new Integer((String) session.getAttribute("TKL_idobjetivo"));
    }
    DatosEncabezado de = new DatosEncabezado(conEnoc);
    OPMetas opm = new OPMetas(conEnoc);
    OPProyectos opp = new OPProyectos(conEnoc);
    OpResponsableProy opres = new OpResponsableProy(conEnoc);

    if(request.getParameter("rmr")!=null){
        opres.desactivarEmpleado(new Integer((String)request.getParameter("pr")), request.getParameter("cl"), "I");
       
        response.sendRedirect("metas");
        
    }

%>

<div class="container-fluid">    

	<h2>Editar contenido del objetivo <small class="text-muted fs-4"><%= de.getCcostoNombre((String) session.getAttribute("TKL_ccosto"), (String) session.getAttribute("ejercicioId"))%></small></h2>
	
	<div class="alert alert-info">
		<h3>Objetivo: <%= opm.getObjetivo( id_objetivo)%></h3>
	</div>
    
	
	<form name="listMetas" method="post" action="accion_meta">
		<input type="hidden" name="TKL_id_objetivo" id="TKL_id_objetivo" value="<%= id_objetivo%>">
		<input type="hidden" name="accion" id="accion" value="agregar">
		<input type="hidden" name="returnpage" id="accion" value="metas">
	
		<div class="alert alert-info">
			<a href="misionvision" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
			<input type="submit" name="button3" id="button3" value="Agregar Meta" class="btn btn-primary">
		</div>
	</form>

    
        <%

            Iterator<DptoMetas> itMetas = opm.listMetas( id_objetivo).iterator();
            while (itMetas.hasNext()) {
                DptoMetas dpm = itMetas.next();
        %>
        
        		<div class="alert">
        			<h3><%= dpm.getDescripcion()%> <a href="proyectos?TKL_idobjetivo=<%= id_objetivo %>&TKL_meta=<%= dpm.getId() %>" class="btn btn-sm">Editar contenido</a></h3>
        		</div>
        		
        		<div style="margin-left:20px;">
                    
                    <form name="listProy" method="post" action="accion_proyecto">
                    	<input type="hidden" name="TKL_id_meta" id="TKL_id_meta" value="<%= dpm.getId() %>">
                    	<input type="hidden" name="accion" id="accion" value="agregar">
                    	<input type="hidden" name="returnpage" id="returnpage" value="metas">
                    	
                    	<div class="alert alert-success">
                    		<h3>Proyectos <input type="submit" name="proyecto" id="button3" value="Agregar Proyecto" class="btn btn-sm"></h3>
                    	</div>
                    
                    </form>
               
        		
	        		<table class="table table-condensed">
	        			<tr>
	        				<th><spring:message code='aca.Descripcion'/></th>
	        				<th>Duración</th>
	        				<th>Responsables</th>
	        			</tr>
			        <%
			            Iterator<DptoProyectos> itproy = opp.listProyecto(dpm.getId()).iterator();
			            while (itproy.hasNext()) {
			                DptoProyectos dpr = itproy.next();
			        %>
	        
		                    <tr>
		                        <td> 
		                        	<%= dpr.getDescripcion()%>
		                        </td>
		                        <td>
		                        	<strong>desde:</strong> <%= dpr.getFecha_inicio()%><br> 
		                        	<strong>hasta:</strong> <%= dpr.getFecha_final()%>
		                        </td>
		                        <td>
		                        	<form name="listResp" method="post" action="accion_respproy" style="margin:0;">
		                    			<input type="hidden" name="TKL_id_meta" id="TKL_id_meta" value="<%= dpm.getId() %>">
		                    			<input type="hidden" name="TKL_id_proyecto" id="TKL_id_proyecto" value="<%= dpr.getId() %>">
		                    			<input type="hidden" name="accion" id="accion" value="agregar">
		                    			<input type="hidden" name="returnpage" id="returnpage" value="metas">
		                    			
		                    			<input type="submit" name="proyecto" id="button3" value="Agregar responsable" class="btn btn-sm">
		                    			<br />
		                    			<%
					                        Iterator<DptoResponsableProy> itresp = opres.ListResponsablesProyecto(dpr.getId()).iterator();
					                    %>
					                    
					                    <% 
					                    	while(itresp.hasNext()){ 
					                    		DptoResponsableProy dprp = itresp.next();
					                        	DptoPersonal dpers = opres.getEmpleado(dprp.getClave());
					                    %>
						                    	<a href="metas?rmr=t&cl=<%= dpers.getClave() %>&pr=<%= dpr.getId() %>"><spring:message code="aca.Eliminar"/>:</a>
						                    	<%= dpers.getClave() %> | <%= dpers.getNombre() %> <%= dpers.getAppaterno() %> <%= dpers.getApmaterno() %> <strong><%= dprp.getCargo() %></strong>
						                    	<br/> 
					                    <% 
					                    	}
					                    %>
		                    		</form>
		                        </td>
		                    </tr>
			        <%
			        	}
			        %>
	    			</table>    
	    		</div>

        <%
            } 
        %>
    


</div>

<%@ include file= "../../cierra_enoc.jsp" %>