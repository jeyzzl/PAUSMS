<%@ page import="java.util.List "%>
<%@ page import="java.util.HashMap "%>
<%@ page import="aca.candado.spring.CandTipo"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	String codigoPersonal = (String) session.getAttribute("codigoPersonal");
	String usuarioNombre = (String) request.getAttribute("usuarioNombre");
	boolean esAdmin = (boolean) request.getAttribute("esAdmin");

	List<CandTipo> lisTipos = (List<CandTipo>) request.getAttribute("lisTipos");
	HashMap<String,String> mapaPermisosPorTipo = (HashMap<String,String>)request.getAttribute("mapaPermisosPorTipo");
	if (esAdmin == true) {
%>
<div class="container-fluid">
	<h2>Lock Categories</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="cand_tipo"><spring:message code="aca.Anadir" /></a>
	</div>
	<table style="width: 70%">
		<tr align="center">
			<td class="titulo"></td>
		</tr>
		<tr align="right">
		</tr>
	</table>
	<table id="table" class="table table-sm table-bordered">
		<thead class="table-info">
			<tr>
				<th width="4%"><spring:message code="aca.Numero" /></th>
				<th width="6%"><spring:message code="aca.Op" /></th>
				<th width="20%"><spring:message code="aca.Nombre" /></th>
				<th width="25%">Permissions</th>
				<th width="25%">Department</th>
				<th width="10%">Contact</th>
				<th width="10%">Status</th>
			</tr>
		</thead>
		<%
		int row = 0;
		for (CandTipo candTipo : lisTipos) {
			row++;
			
			String permisos = "0";
			
			if(mapaPermisosPorTipo.containsKey(candTipo.getTipoId())){
				permisos = mapaPermisosPorTipo.get(candTipo.getTipoId());
			}
		%>
		<tr>
			<td align="center"><%=candTipo.getTipoId()%></td>
			<td style="text-align: center;"><a
				href="cand_tipo?TipoId=<%=candTipo.getTipoId()%>"
				class="fas fa-edit" title="Editar"></a></td>
			<td><a href="alta_candado?TipoId=<%=candTipo.getTipoId()%>"><%=candTipo.getNombreTipo()%></a>
			<td>
				<a href="cand_permiso?TipoId=<%=candTipo.getTipoId()%>"><span class="badge rounded-pill bg-warning text-dark"><%=permisos%> <i class="fas fa-users"></i></span></a>
			</td>
			<td>
<%
				if (candTipo.getLugar() == null) {		%>
					<%=""%>
<%				} else {								%>
					<%=candTipo.getLugar()%>
<%				}										%>
			</td>
			<td>
<% 				if (candTipo.getTelefono() == null) {	%>
					<%=""%>
<%				} else {								%>
					<%=candTipo.getTelefono()%>
<%				}										%>
			</td>
			<td><%=candTipo.getEstado().equals("A") ? "<span class='badge rounded-pill bg-success'>Active</span>" : "<span class='badge rounded-pill bg-secondary'>Inactive</span>"%></td>
		</tr>
		<%
		}
		%>
	</table>
	<%
	} else {
	%>
	<div align="center">
		<em><b><font color="#000066" size="4">You have no access to the Locks Catalog. Contact your system administrator.</font></b></em>
	</div>
	<%
	}
	%>
</div>
