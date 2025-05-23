<%-- 
    Document   : actividades
    Created on : 18/05/2014, 02:53:53 PM
    Author     : Daniel
--%>

<%@page import="sun.misc.MetaIndex"%>
<%@page import="aca.proyectos.DptoInformes"%>
<%@page import="aca.proyectos.OPInforme"%>
<%@page import="aca.proyectos.OpResponsableProy"%>
<%@page import="aca.proyectos.OPResponsableAct"%>
<%@page import="aca.proyectos.DptoPersonal"%>
<%@page import="aca.proyectos.DptoResponsableAct"%>
<%@page import="aca.proyectos.DptoActividades"%>
<%@page import="aca.proyectos.OPActividad"%>
<%@page import="aca.proyectos.InstMetas"%>
<%@page import="aca.proyectos.Logro"%>
<%@page import="java.util.Iterator"%>
<%@page import="aca.proyectos.OPMetas"%>
<%@page import="aca.proyectos.EmpLogro"%>
<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="menuPortal.jsp"%>

<%@page import="java.util.List"%>

<!-- page contentType="text/html" pageEncoding="UTF-8" -->

<%
	String responsable = (String) session.getAttribute("codigoPersonal");
 
	OPActividad oac = new OPActividad(conEnoc);
	OPResponsableAct opresa = new OPResponsableAct(conEnoc);
	OpResponsableProy opres = new OpResponsableProy(conEnoc);
	EmpLogro elo = new EmpLogro(conEnoc);
	List<String> lsPuestosJefe = elo.deptosByJefes((String)session.getAttribute("codigoPersonal"),"001-2014");
	
	
	if (request.getParameter("rmr") != null) {
		opresa.desactivarEmpleado(
				new Integer((String) request.getParameter("ac")),
				request.getParameter("cl"), "I", "");
		response.sendRedirect("modificaLogro?logroid="+request.getParameter("logroid"));
	}
	
	if (request.getParameter("rmActividad") != null) {
		oac.eliminaActividad(new Integer((String)request.getParameter("idact")));
		response.sendRedirect("modificaLogro?logroid="+request.getParameter("logroid"));
	}
	
	%>

<div class="container-fluid">

	<h2>Editar proyecto</h2>

	<div class="alert alert-info">
		<a
			href="actividades?logroid=<%= request.getParameter("logroid") %>"
			class="btn btn-primary"><i class="icon-arrow-left icon-white"></i>Regresar
		</a>
	</div>
	<%
	EmpLogro el = new EmpLogro(conEnoc);
	OPMetas metas = new OPMetas(conEnoc);
	
	Logro lo = el.getLogro(new Integer((String)request.getParameter("logroid")));

		InstMetas metasinst = metas.getInsMeta(lo.getId_metainst());
		boolean isJefe = lsPuestosJefe.contains(lo.getDepto());
	%>
	<div class="alert alert-info">
		<form name="listResp" method="post" action="accion_proyecto"
			style="margin: 0; float: right;">
			<input type="hidden" name="idlogro" id="idlogro"
				value="<%=lo.getId()%>"> <input type="hidden" name="accion"
				id="accion" value="editar"> <input type="hidden"
				name="returnpage" id="returnpage"
				value="modificaLogro?logroid=<%= lo.getId() %>"> <input
				type="submit" name="actividad" id="button3" value="Edita Proyecto"
				class="btn btn-sm">
		</form>
		<strong>Meta:</strong>
		<%= metasinst.getDescripcion() %><br> <strong>Proyecto:</strong>
		<%= lo.getDescripcion() %><br> <strong>Indicador:</strong>
		<%= lo.getIndicador() %><br> <strong>Fecha final:</strong>
		<%= lo.getFecha_final() %><br> <strong>Depto:</strong>
		<%= el.getCcostoNombre(lo.getDepto(), "001-2014") %><br> <strong>Actividades
			asignadas:</strong>
	</div>

	<h2>
		<spring:message code="aca.Actividades" />
	</h2>
	<form name="newActividad" method="post" action="accion_actividad">
		<input type="hidden" name="accion" id="accion" value="agregar">
		<input type="hidden" name="returnpage" id="accion"
			value="modificaLogro"> <input type="hidden"
			name="logroid" id="logroid" value="<%= lo.getId() %>">
		<div class="alert alert-info">
			<input type="submit" name="addLogro" id="addLogro"
				value="Agregar actividad" class="btn btn-primary">
		</div>
	</form>

	<table style="width:60%" class="table table-condensed">
		<tr>
			<th width="10%">Opción</th>
			<th width="50%"><spring:message code='aca.Descripcion' /></th>
			<th width="20%">Duración</th>
			<% if(isJefe){ %>
			<th width="30%">Responsables</th>
			<% } %>

		</tr>
		<%
			Iterator<DptoActividades> itact = oac.listActividades(lo.getId()).iterator();
			while (itact.hasNext()) {
				DptoActividades dpa = itact.next();
			%>

		<tr>
			<td><a
				href="modificaLogro?rmActividad=t&idact=<%= dpa.getId() %>&logroid=<%= request.getParameter("logroid") %>"><i
					class="icon-remove-sign"></i></a></td>
			<td><%=dpa.getDescripcion()%></td>
			<td><strong>desde:</strong> <%=dpa.getFecha_inicio()%><br>
				<strong>hasta:</strong> <%=dpa.getFecha_final()%></td>
			<% if(isJefe){ %>
			<td>
				<form name="listResp" method="post" action="accion_respact.jsp"
					style="margin: 0;">
					<input type="hidden" name="TKL_id_actividad" id="TKL_id_actividad"
						value="<%=dpa.getId()%>"> <input type="hidden"
						name="ccosto" id="ccosto" value="<%=lo.getDepto()%>"> <input
						type="hidden" name="logroid" id="logroid" value="<%=lo.getId()%>">
					<input type="hidden" name="accion" id="accion" value="agregar">
					<input type="hidden" name="returnpage" id="returnpage"
						value="modificaLogro"> <input type="submit"
						name="actividad" id="button3" value="Agregar responsable"
						class="btn btn-sm">
				</form> <%
 			Iterator<DptoResponsableAct> itresp = opresa.ListResponsablesActividad(dpa.getId()).iterator();
 			while (itresp.hasNext()) {
 				DptoResponsableAct dprp = itresp.next();
 				DptoPersonal dpers = opres.getEmpleado(dprp.getClave());
 %> <a
				href="modificaLogro?rmr=t&cl=<%=dpers.getClave()%>&ac=<%=dpa.getId()%>&logroid=<%= request.getParameter("logroid") %>"><i
					class="icon-remove-sign"></i></a> <%=dpers.getClave()%> <%=dpers.getNombre()%>
				<%=dpers.getAppaterno()%> <%=dpers.getApmaterno()%> <%=dprp.getCargo()%>
				<br /> <% 	} %>
			</td>
			<% } %>
		</tr>

		<%
				}
			%>
	</table>
	<form name="newActividad" method="post" action="accion_informe"
		style="margin: 0;">
		<input type="hidden" name="accion" id="accion" value="agregar">
		<input type="hidden" name="returnpage" id="accion"
			value="modificaLogro?logroid=<%= lo.getId() %>"> <input
			type="hidden" name="logroid" id="logroid" value="<%= lo.getId() %>">
		<input type="submit" name="addLogro" id="addLogro"
			value="Realizar informe de avance" class="btn btn-sm btn-info">
	</form>
	<table class="table table-condensed">
		<tr>
			<th>Fecha informe</th>
			<th>Contenido</th>
			<th>informó</th>
			<th>tipo de informe</th>
		</tr>
		<%
	OPInforme inf = new OPInforme(conEnoc);
	Iterator<DptoInformes> itInf = inf.listInformes(lo.getId()).iterator();
	while(itInf.hasNext()){
		DptoInformes dinf = itInf.next();
	%>
		<tr>
			<td><%= dinf.getFecha_creado() %></td>
			<td><%= dinf.getDetalle() %></td>
			<td><%= dinf.getClave() %></td>
			<td><%= dinf.getTipo_informe() %></td>
		<tr>
			<%}%>
		
	</table>
</div>

<script>
	jQuery('.actividades').addClass('active');
</script>
<%@ include file="../../cierra_enoc.jsp"%>