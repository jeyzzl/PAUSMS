<%@page import="sun.misc.MetaIndex"%>
<%@page import="aca.proyectos.DptoPersonal"%>
<%@page import="aca.emp.Empleado"%>
<%@page import="aca.proyectos.OpResponsableProy"%>
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

<!--page contentType="text/html" pageEncoding="UTF-8"-->

<%
	String periodoId			= (String) session.getAttribute("porPeriodo");
	String responsable 			= (String) session.getAttribute("codigoPersonal");
	int row = 0;
%>

<div class="container-fluid">

	<h2>
		Proyectos
		<%=periodoId%></h2>

	<div class="alert alert-info">
		<p>En esta sección se espera que el empleado o jefe de
			departamento escriba sus proyectos a alcanzar para cumplir con las
			metas y objetivos de la institución desde su propia área de trabajo;
			para ello se requiere una descripción del proyecto, fecha límite para
			concluirlo, un indicador para conocer si el proyecto se realizó, las
			evidencias que se incluirán a medida que se avanza en el proyecto y
			las acciones o actividades que deben realizarse.</p>
	</div>
	<form name="newProyecto" method="post" action="accion_proyecto">
		<input type="hidden" name="accion" id="accion" value="agregar">
		<input type="hidden" name="returnpage" id="accion"
			value="actividades">
		<div class="alert alert-info">
			<input type="submit" name="addLogro" id="addLogro"
				value="Agregar Proyecto" class="btn btn-primary">
		</div>
	</form>
	<%
	EmpLogro el = new EmpLogro(conEnoc);
	OPMetas metas = new OPMetas(conEnoc);
	OPActividad opac = new OPActividad(conEnoc);
	OpResponsableProy opres = new OpResponsableProy(conEnoc);
	
	
	List<Logro> lsLogro = el.listLogros(0, (String) session.getAttribute("codigoPersonal"), "");
	
	
	
	List<DptoActividades> lsActividades = opac.listempActividades((String) session.getAttribute("codigoPersonal"));
	
	if(lsLogro.size()>0){
	%>

	<h2>Proyectos personales</h2>
	<hr>
	<table class="table">
		<tr>
			<th>Opción</th>
			<th>#</th>
			<th>Meta</th>
			<th>Proyecto</th>
			<th><spring:message code="aca.Fecha" /></th>
			<th><spring:message code="aca.Comentario" /></th>
		</tr>
		<%
	
	Iterator<Logro> itLogro =lsLogro.iterator();
	
	while(itLogro.hasNext()){
		row++;
		Logro lo = itLogro.next();
		InstMetas metasinst = metas.getInsMeta(lo.getId_metainst());
%>
		<tr>
			<td><a href="modificaLogro?logroid=<%= lo.getId() %>"
				class="btn btn-primary">Editar <i class="fas fa-pencil-alt"></i>
			</a></td>
			<td><%= row%></td>
			<td><%= metasinst.getDescripcion() %></td>
			<td><%= lo.getDescripcion()%></td>
			<td><%= lo.getFecha_final()%></td>
			<td><%=lo.getComentario()==null?"":lo.getComentario() %></td>
		</tr>

		<%	} 
 %>
	</table>

	<% 
	}
	if(lsActividades.size()>0){ %>
	<h2>Actividades asignados por el Jefe de área</h2>
	<hr>
	<table class="table">
		<tr>
			<th>#</th>
			<th>Meta</th>
			<th>Proyecto</th>
			<th>Actividad</th>
			<th>Fecha final actividad</th>
		</tr>
		<%
	row=0;
	Iterator<DptoActividades> itAct = lsActividades.iterator();
	while(itAct.hasNext()){
		DptoActividades dact = itAct.next();
		Logro logdp = el.getLogro(dact.getId_proyecto());
		InstMetas metasinst = metas.getInsMeta(logdp.getId_metainst());
		DptoPersonal emple = opres.getEmpleado(logdp.getCodigopersonal());
	%>
		<tr>
			<td><%= row%></td>
			<td><%= metasinst.getDescripcion()%></td>
			<td><%= logdp.getDescripcion()%></td>
			<td><%= dact.getDescripcion() %></td>
			<td><%= dact.getFecha_final()%></td>
		</tr>
		<% } %>
	</table>
	<% } %>
</div>

<script>
	jQuery('.actividades').addClass('active');
</script>
<%@ include file="../../cierra_enoc.jsp"%>
