<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.salida.spring.SalGrupo"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="ModOpcion" scope="page" class="aca.menu.Opcion" />
<jsp:useBean id="ModOpcionU" scope="page" class="aca.menu.OpcionUtil" />
<jsp:useBean id="Usuarios" scope="page" class="aca.vista.Usuarios" />
<jsp:useBean id="UsuariosU" scope="page" class="aca.vista.UsuariosUtil" />

<html>
<head>
	<script>
		function Borrar(grupoId) {
			if (confirm("Estás seguro de eliminar el registro: " + grupoId) == true) {
				document.location.href = "borrar?GrupoId=" + grupoId;
			}
		}
	</script>
</head>
<%
	List<SalGrupo> lisGrupos 			= (List<SalGrupo>) request.getAttribute("lisGrupos");
	HashMap<String,String> mapaGrupos 	= (HashMap<String,String>) request.getAttribute("mapaGrupos");	
	String mensaje    					= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");	
%>
<div class="container-fluid">
	<h2>Listado de grupos</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="accion?Accion=1"><i class="fas fa-plus icon-white"></i>&nbsp;<spring:message code='aca.Añadir'/></a>&nbsp;
		<a class="btn btn-info" href="accesoPermiso"><i class="fas fa-sync-alt icon-white"></i> Acceso/Permiso</a>&nbsp;
		<a class="btn btn-info" href="accesoSolicitud"><i class="fas fa-sync-alt icon-white"></i> Acceso/Solicitud</a>&nbsp;
<!-- 		<a class="btn btn-primary" href="testAgenda.jsp">TEST AGENDA</a> -->
	</div>	
<%	if (!mensaje.equals("-")){%>	
	<div class="alert alert-secondary">
		<%=mensaje%>
	</div>	
<%	} %>	
	<table class="table table-sm table-bordered table-striped">
	<thead class="table-info">
	<tr>
		<th width="5%" style="text-align: center"><spring:message code="aca.Opcion"/></th>
		<th width="5%" style="text-align: center">Grupo Id</th>			
		<th width="20%"><spring:message code="aca.Nombre"/> Grupo</th>
		<th width="15%">Responsable</th>
		<th width="25%"><spring:message code="aca.Correo"/></th>
		<th width="10%">Usuarios</th>
	</tr>
	</thead>	
	<tbody>
<%	 
	for( SalGrupo grupo: lisGrupos) {
		String total = "0";	
		if (mapaGrupos.containsKey(grupo.getGrupoId())){
			total = mapaGrupos.get(grupo.getGrupoId());
		}
%>
	<tr>			
		<td style="text-align: center">
			<a href="accion?GrupoId=<%=grupo.getGrupoId()%>" class="fas fa-edit" title="Modificar"></a>
<% 		if (total.equals("0")){%>			
			&nbsp;<a href="javascript:Borrar('<%=grupo.getGrupoId()%>')" class="fas fa-trash" title="Eliminar"></a>
<% 		}%>			
		</td>
		<td style="text-align: center"><%=grupo.getGrupoId()%></td>
		<td><%=grupo.getGrupoNombre()%></td>
		<td><%=grupo.getResponsable()%></td>			
		<td><%=grupo.getCorreo()%></td>
		<td><%=grupo.getUsuarios()%></td>
	</tr>
<%	} %>
	</tbody>
	</table>	
</div>
</html>