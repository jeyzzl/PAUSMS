<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.HashMap"%>
<%@ page import="aca.bitacora.spring.BitArea"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	String codigoActual		= (String) session.getAttribute("codigoUltimo");
	
	String areaId			= request.getParameter("AreaId")==null?"0":request.getParameter("AreaId");
	String user 			= request.getParameter("Usuario")==null?"0":request.getParameter("Usuario");	 
	
	String lista []			= (String []) request.getAttribute("lista");
	String nombreActual		= (String) request.getAttribute("nombreActual");
	BitArea area 			= (BitArea) request.getAttribute("area");
	
	List<String> lisAreaUsuarios = (List<String>) request.getAttribute("lisAreaUsuarios");
	HashMap <String, String> mapaUsuarios = (HashMap <String, String>) request.getAttribute("mapaUsuarios");
%>

<script type="text/javascript">
	function Borrar(areaId, usuario) {
		if (confirm("¿Quieres eliminar el acceso del usuario "+usuario+" ?" ) == true) {
			document.location = "quitarAcceso?AreaId="+areaId+"&Usuario="+usuario;
		}
	}
</script>

<div class="container-fluid">
	<h2>Usuarios con acceso<small class="text-muted h4">( <%=area.getAreaNombre()%> )</small></h2>
	<div class="alert alert-info">
		<a href="area" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<b>Agregar Usuario:</b> <%=codigoActual%> - <%=nombreActual%>&nbsp;
	<%	if (!area.getAcceso().contains(codigoActual)){%>
		<a href="agregarAcceso?AreaId=<%=areaId%>" class="btn btn-primary" title="Agregar"><i class="fas fa-plus"></i></a>
	<%	} %>
	<table class="table table-bordered" >
	<thead class="table-info">
		<tr>
			<th>#</th>
			<th>Operaci&oacute;n</th>
			<th>C&oacute;digo</th>
			<th>Usuario</th>			
		</tr>
	</thead>	
<%
	int row = 0;
	for(String usuario : lisAreaUsuarios){
		row++;
%>		
		<tr>	
			<td><%=row%></td>
			<td><a href="javascript:Borrar('<%=areaId%>','<%=usuario%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-times"></i></a></td>
			<td><%=usuario%></td>		
			<td><%=mapaUsuarios.get(usuario) %></td>
		</tr>
<%			
		}
%>			
	</table>
</div>