<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="menu_planeacion.jsp"%>

<%@page import="aca.proyectos.OPMetas"%>
<%@page import="aca.proyectos.InstMetas"%>


<%
	
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");	
	String periodoId			= (String) session.getAttribute("porPeriodo");


	OPMetas metas = new OPMetas(conEnoc);
	ArrayList<InstMetas> lisMetas = (ArrayList<InstMetas>) metas.getInsMetas(Integer.parseInt(periodoId));
	
%>

<div class="container-fluid">
	
	<h2>Metas</h2>
	
	<table class="table table-condensed table-bordered">
		<tr>
			<th>#</th>
			<th><spring:message code='aca.Descripcion'/></th>
			<th><spring:message code="aca.Comentario"/></th>
		</tr>
		<%
			int cont = 0;
			for(InstMetas meta : lisMetas){
				cont++;
		%>
				<tr>
					<td><%=cont %></td>
					<td>
						<a href="proyectos_meta?metaId=<%=meta.getId() %>">
							<%=meta.getDescripcion() %>
						</a>
					</td>
					<td>
						<%=meta.getComentario() %>	
					</td>
				</tr>
		<%
			} 
		%>
	</table>

</div>

<script>
	jQuery('.metas').addClass('active');
</script>

<%@ include file="../../cierra_enoc.jsp"%>