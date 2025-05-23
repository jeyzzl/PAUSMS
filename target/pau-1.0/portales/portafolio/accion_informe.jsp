<%@page import="aca.proyectos.Logro"%>
<%@page import="aca.proyectos.EmpLogro"%>
<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="menuPortal.jsp"%>

<%@page import="aca.proyectos.OPInforme"%>
<%@page import="aca.proyectos.DptoActividades"%>
<%@page import="aca.proyectos.OPActividad"%>
<%@page import="aca.proyectos.OPProyectos"%>
<%@page import="aca.proyectos.OPMetas"%>
<%@page import="aca.proyectos.DatosEncabezado"%>

<%

    String returnpage="actividades";
    if(request.getParameter("returnpage")!=null){
        returnpage = request.getParameter("returnpage");
    }
    
    if (request.getParameter("guardar") != null) {
        OPInforme opinf = new OPInforme(conEnoc);
        if (request.getParameter("accion").equals("agregar")) {
            opinf.addInforme(request);
        }else if(request.getParameter("accion").equals("editar")){
           
        }
        response.sendRedirect(returnpage);
    }
    
%>
<div class="container-fluid">
	<h2>Informe de Avance</h2>

	<%
    	if(request.getParameter("logroid")!=null){
    		
    		EmpLogro elo = new EmpLogro(conEnoc);
    		Logro lo = elo.getLogro(new Integer((String) request.getParameter("logroid")));
    		
    %>
	<div class="alert alert-info"><%= lo.getDescripcion() %></div>
	<% 
       }
    %>

	<div class="alert alert-info">
		<a href="<%= returnpage %>" class="btn btn-primary"><i
			class="icon-arrow-left icon-white"></i>
		<spring:message code='aca.Regresar' /></a>
	</div>

	<form action="" method="post" name="accion_meta">
		<input name="accion" type="hidden" id="accion"
			value="<%= request.getParameter("accion")%>"> <input
			name="TKL_clave" type="hidden" id="TKL_clave"
			value="<%= session.getAttribute("codigoPersonal")%>"> <input
			name="TKL_idactividad" type="hidden" id="TKL_idactividad"
			value="<%= request.getParameter("logroid")%>"> <input
			name="returnpage" type="hidden" id="accion" value="<%= returnpage %>">

		<p>
			<label for="tipo_informe">Tipo de infome</label> <select
				name="tipo_informe" id="tipo_informe">
				<option value="AVANCE">Avance</option>
				<option value="PAUSA">Pausa</option>
				<option value="DETENER">Detener</option>
				<option value="SUSPENDER">Suspender</option>
				<option value="COMPLETADO">Completado</option>
			</select>
		</p>

		<p>
			<label for="detalle">Resumen del avance</label>
			<textarea name="detalle" cols="60" id="detalle"></textarea>
		</p>

		<p>
			<label for="porcentaje_avance">% de avance</label> <input type="text"
				name="porcentaje_avance" id="porcentaje_avance">
		</p>

		<div class="alert alert-info">
			<input name="guardar" type="submit" value="Guardar"
				class="btn btn-primary btn-large">
		</div>

	</form>

</div>

<%@ include file="../../cierra_enoc.jsp"%>