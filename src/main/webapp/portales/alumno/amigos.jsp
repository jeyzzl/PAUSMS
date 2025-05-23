<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="aca.util.mapaCurricular"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@ include file= "menu.jsp" %>
<%		
	String codigoPersonal	= (String)session.getAttribute("codigoPersonal");
	String grupoNombre	 	= (String)request.getAttribute("grupoNombre");
	String grupoId		 	= request.getParameter("grupoId")==null?"":request.getParameter("grupoId");	
	String cargaId			= request.getParameter("CargaId")==null?"-":request.getParameter("CargaId");
	String cursoId			= request.getParameter("CursoId")==null?"-":request.getParameter("CursoId");
		
	List<aca.Mapa> lisAmigos 	= (List<aca.Mapa>)request.getAttribute("lisAmigos");
%>
<style>
	.chzn-container{
		margin-bottom: 10px !important;
	}
	.filtroAvanzado{
		display: none;
		width: 100%;
	}
	.radio{
		width: 50px;
       	height: 50px;
       	border-radius: 50%;
	}
</style>
<div class="container-fluid">
	<h3>Alumnos en el grupo<small class="text-muted fs-5">(<%=grupoNombre%>)</small></h3>
	<div class="alert alert-info d-flex align-items-center">
		<a href="grupos?CargaId=<%=cargaId%>&CursoId=<%=cursoId%>" align="left" class="btn btn-primary btn-sm"><i class="fas fa-arrow-left"></i> <spring:message code='aca.Regresar'/></a>
		&nbsp;&nbsp;
		<input type="text" class="form-control search-query" placeholder="Buscar" id="buscar" style="width:170px">			
	</div>
	<table class="table table-sm table-bordered" id="table">
	<thead class="table-dark">
	<tr>
		<th width="5%">#</th>
		<th width="10%">Foto</th>
		<th width="95%"><spring:message code="aca.NombreDelAlumno"/></th>
	</tr>
	</thead>
	<tbody>
<%	
	int row=0;
	for(aca.Mapa map : lisAmigos){
		row++;
%>
	<tr>
		<td><%=row%></td>
		<td><img class="radio border border-dark" src="../../fotoMenu?Codigo=<%=map.getLlave()%>&Tipo=O" width="125"></td>			
		<td><%=map.getValor()%></td>
	</tr>
<%
		}
%>
	</tbody>
	</table>     
</div>
<script src="../../js/search.js"></script>
<script>
	$('#buscar').search();
</script>