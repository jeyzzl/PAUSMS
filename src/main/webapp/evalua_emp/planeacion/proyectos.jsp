<%-- 
    Document   : metas
    Created on : 15/05/2014, 03:28:16 PM
    Author     : Daniel
--%>
<%@page import="aca.proyectos.DptoRecFinancieros"%>
<%@page import="aca.proyectos.OpRecFinancieros"%>
<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.proyectos.OPResponsableAct"%>
<%@page import="aca.proyectos.DptoResponsableAct"%>
<%@page import="aca.proyectos.OPActividad"%>
<%@page import="aca.proyectos.DptoActividades"%>
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
	session.removeAttribute("");
		int id_objetivo = 0;
		int id_meta = 0;

		if (session.getAttribute("TKL_idobjetivo") == null) {
			if (request.getParameter("TKL_idobjetivo") == null) {
				response.sendRedirect("misionvision");
			} else {
				session.setAttribute("TKL_idobjetivo",
						request.getParameter("TKL_idobjetivo"));
				id_objetivo = new Integer(
						(String) session.getAttribute("TKL_idobjetivo"));
			}
		} else {
			id_objetivo = new Integer(
					(String) session.getAttribute("TKL_idobjetivo"));
		}

		if (session.getAttribute("TKL_meta") == null) {
			if (request.getParameter("TKL_meta") == null) {
				response.sendRedirect("metas");
			} else {
				session.setAttribute("TKL_meta",
						request.getParameter("TKL_meta"));
				id_meta = new Integer(
						(String) session.getAttribute("TKL_meta"));
			}
		} else {
			id_meta = new Integer(
					(String) session.getAttribute("TKL_meta"));
		}

		DatosEncabezado de = new DatosEncabezado(conEnoc);
		OPMetas opm = new OPMetas(conEnoc);
		OPProyectos opp = new OPProyectos(conEnoc);
		OPActividad opa = new OPActividad(conEnoc);
		OpResponsableProy opres = new OpResponsableProy(conEnoc);
		OPResponsableAct opresa = new OPResponsableAct(conEnoc);
		OpRecFinancieros oprefin = new OpRecFinancieros(conEnoc);

		if (request.getParameter("rmr") != null) {
			opresa.desactivarEmpleado(
					new Integer((String) request.getParameter("ac")),
					request.getParameter("cl"), "I", "");
			response.sendRedirect("proyectos");
		}
		
		if(request.getParameter("rmrf")!=null){
			oprefin.rmRecfin(new Integer(request.getParameter("id")));
			response.sendRedirect("proyectos");
		}
%>


<div class="container-fluid">

	<h2>
		Editar contenido de la meta <small class="text-muted fs-4"><%=de.getCcostoNombre( (String) session.getAttribute("TKL_ccosto"), (String) session.getAttribute("ejercicioId"))%></small>
	</h2>

	<div class="alert">
		<h3>
			Meta:
			<%=opp.getMeta(id_meta)%></h3>
	</div>

	<form name="listProy" method="post" action="accion_proyecto">
		<input type="hidden" name="TKL_id_meta" id="TKL_id_meta"
			value="<%=id_meta%>"> <input type="hidden" name="accion"
			id="accion" value="agregar"> <input type="hidden"
			name="returnpage" id="returnpage" value="proyectos">

		<div class="alert alert-info">
			<a href="metas" class="btn btn-primary"><i
				class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a> <input
				type="submit" name="button" id="button" value="Agregar Proyecto"
				class="btn btn-primary">
		</div>
	</form>


	<%
		Iterator<DptoProyectos> itProy = opp.listProyecto(id_meta)
					.iterator();
			while (itProy.hasNext()) {
				DptoProyectos dpp = itProy.next();
	%>


	<div class="alert alert-success">
		<h3><%=dpp.getDescripcion()%></h3>
	</div>

	<div style="margin-left: 20px;">

		<form name="listProy" method="post" action="accion_actividad">
			<input type="hidden" name="TKL_id_proyecto" id="TKL_id_proyecto"
				value="<%=dpp.getId()%>"> <input type="hidden"
				name="accion" id="accion" value="agregar"> <input
				type="hidden" name="returnpage" id="returnpage"
				value="proyectos">

			<div class="alert alert-info" style="padding: 8px 35px 8px 14px;">
				<h3>
					Actividades <input type="submit" name="proyecto" id="button3"
						value="Agregar Actividad" class="btn btn-sm">
				</h3>
			</div>
		</form>

		<table class="table table-condensed">
			<tr>
				<th><spring:message code='aca.Descripcion'/></th>
				<th>Duración</th>
				<th>Responsables</th>
				<th>Recursos Financieros  </th>
			</tr>
			<%
				Iterator<DptoActividades> itact = opa.listActividades(
								dpp.getId()).iterator();
						while (itact.hasNext()) {
							DptoActividades dpa = itact.next();
			%>

			<tr>
				<td><%=dpa.getDescripcion()%></td>
				<td><strong>desde:</strong> <%=dpa.getFecha_inicio()%><br>
					<strong>hasta:</strong> <%=dpa.getFecha_final()%></td>
				<td>
					<form name="listResp" method="post" action="accion_respact"
						style="margin: 0;">
						<input type="hidden" name="TKL_id_proyecto" id="TKL_id_proyecto"
							value="<%=dpa.getId_proyecto()%>"> <input type="hidden"
							name="TKL_id_actividad" id="TKL_id_actividad"
							value="<%=dpa.getId()%>"> <input type="hidden"
							name="accion" id="accion" value="agregar"> <input
							type="hidden" name="returnpage" id="returnpage"
							value="proyectos"> <input type="submit"
							name="actividad" id="button3" value="Agregar responsable"
							class="btn btn-sm">
					</form> <%
 	Iterator<DptoResponsableAct> itresp = opresa
 						.ListResponsablesActividad(dpa.getId())
 						.iterator();

 	while (itresp.hasNext()) {
 					DptoResponsableAct dprp = itresp.next();
 					DptoPersonal dpers = opres.getEmpleado(dprp
 							.getClave());
 %> <a
					href="proyectos?rmr=t&cl=<%=dpers.getClave()%>&ac=<%=dpa.getId()%>"><spring:message code="aca.Eliminar"/>:</a>
					<%=dpers.getClave()%> <%=dpers.getNombre()%> <%=dpers.getAppaterno()%>
					<%=dpers.getApmaterno()%> <%=dprp.getCargo()%> <br /> <%
 	}
 %>
				</td>
				<td>
				<form name="lsRecFin" method="post" action="accion_recfinan"
						style="margin: 0;">
						    <input type="hidden"
							name="TKL_idactividad" id="TKL_idactividad"
							value="<%=dpa.getId()%>"> <input type="hidden"
							name="accion" id="accion" value="agregar"> <input
							type="hidden" name="returnpage" id="returnpage"
							value="proyectos"> <input type="submit"
							name="actividad" id="button3" value="Agregar Recursos"
							class="btn btn-sm">
					</form>
				
				<% Iterator<DptoRecFinancieros> itRefin = oprefin.getLista(dpa.getId()).iterator();
				while(itRefin.hasNext()){
					DptoRecFinancieros drf = itRefin.next();
				%>
				<%=drf.getTipo_recursos() %> $<%= drf.getImporte() %> <a
					href="proyectos?rmrf=t&id=<%=drf.getId()%>"><spring:message code="aca.Eliminar"/>:</a><br \>
				<% } %>
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

<%@ include file="../../cierra_enoc.jsp"%>