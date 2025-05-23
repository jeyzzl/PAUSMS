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
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../body.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@ include file="menu_planeacion.jsp"%>

<script>
	function guardar(id){
		document.forma.id.value = id;
		document.forma.submit();
	}
</script>

<%
	String periodoId		= (String) session.getAttribute("porPeriodo");
	String ccosto 			= request.getParameter("ccosto");
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");	
	int row = 0;
%>

<div class="container-fluid">

	<h2>Proyectos</h2>
	
	<div class="alert alert-info">
		<a class="btn btn-primary" href="centroCostos"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
	</div>

<%
	EmpLogro el 			= new EmpLogro(conEnoc);
	OPMetas metas 			= new OPMetas(conEnoc);
	OPActividad opac 		= new OPActividad(conEnoc);
	OpResponsableProy opres = new OpResponsableProy(conEnoc);
	
	String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion");
	
	if(accion.equals("edtiarComentario")){
		el.editaComentario(request);
	}
	
	
	java.util.List<Logro> lsLogro = el.listLogrosDepto(ccosto, codigoPersonal);
	
	
	if(lsLogro.size()>0){
%>
	
	<h3>Proyectos personales</h3>
	
	<form action="" name="forma" method="post">
		<input type="hidden" name="Accion" value="edtiarComentario">
		<input type="hidden" name="id" />
		<input type="hidden" name="ccosto" value="<%=ccosto %>" />
	
		<table class="table">
			<tr>
				<th>#</th>
				<th>Empleado</th>
				<th>Meta</th>
				<th>Proyecto</th>
				<th><spring:message code="aca.Fecha"/></th>
				<th><spring:message code="aca.Comentario"/></th>
			</tr>
	<%
		
		Iterator<Logro> itLogro =lsLogro.iterator();
		
		while(itLogro.hasNext()){
			row++;
			Logro lo = itLogro.next();
			InstMetas metasinst = metas.getInsMeta(lo.getId_metainst());
	%>		
			<tr>
				<td><%= row%></td>
				<td><%=	lo.getCodigopersonal() %> | <%=aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, lo.getCodigopersonal(), "NOMBRE") %></td>
				<td><%= metasinst.getDescripcion() %></td>
				<td><%= lo.getDescripcion()%></td>
				<td><%= lo.getFecha_final()%></td>
				<td>
					<textarea name="comentario<%=lo.getId() %>" id="comentario<%=lo.getId() %>" cols="65" rows="2"><%=lo.getComentario()==null?"":lo.getComentario() %></textarea>
					<br />
					<a href="javascript:guardar('<%=lo.getId() %>');" style="margin-top:5px;" class="btn btn-sm btn-primary"><spring:message code="aca.Guardar"/>:</a>
				</td>
			</tr>				
	<%	
		} 
	%>
		</table>
	
	</form>
	
<% 
	}	
%>
	
</div>

<script>
	jQuery('.ccostos').addClass('active');
</script>
<%@ include file="../../cierra_enoc.jsp" %>
