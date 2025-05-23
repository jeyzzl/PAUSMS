<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="aca.util.mapaCurricular"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@ include file= "menu.jsp" %>
<%		
	String grupoId		 	= request.getParameter("grupoId")==null?"":request.getParameter("grupoId");
	
    String nombreGrupo			= (String) request.getAttribute("nombreGrupo");
    String nombreInstructor		= (String) request.getAttribute("nombreInstructor");
    String liga					= (String) request.getAttribute("liga");
	List<aca.Mapa> lisAmigos	= (List<aca.Mapa>)request.getAttribute("lisAmigos");
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
	<h3>Alumnos en el grupo <small class="text-muted"style="font-size:12px"> (<%= nombreGrupo%> - <%= nombreInstructor%>)</small></h3>
	<div class="alert alert-info">
		<a href="materias" class="btn btn-primary"><i class="far fa-arrow-alt-circle-left fa-lg"></i> Regresar</a>
		&nbsp;&nbsp;
		<input type="text" class="input-medium search-query" placeholder="Buscar" id="buscar">			
	</div>
	<h3>Liga: <small class="text-muted" style="font-size:15px"><%=liga%></small></h3>&nbsp;&nbsp;
	<table class="table table-condensed table-bordered" id="table">
	<thead>
		<tr>
			<th width="5%">#</th>
			<th width="10%">Foto</th>
			<th><spring:message code="aca.NombreDelAlumno"/></th>
		</tr>
	</thead>
	<tbody>
<%	
	int row=0;
	for(aca.Mapa amigo : lisAmigos){
		row++;
%>
		<tr>
			<td><%=row%></td>
			<td><img class="radio border border-dark" src="../../fotoMenu?Codigo=<%=amigo.getLlave()%>&Tipo=O" width="125"></td>
			<td><%=amigo.getValor()%></td>
		</tr>
<%
		}
%>
	</tbody>
	</table>     
</div>
<script src="../../../js/jquery-1.9.1.min.js"></script>
<script src="../../../js/search.js"></script>
<script>
	$('#buscar').search();
</script>