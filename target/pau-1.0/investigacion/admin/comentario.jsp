<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="InvComentarioU" scope="page" class="aca.investiga.InvComentarioUtil"/>
<jsp:useBean id="Usuario" scope="page" class="aca.vista.Usuarios"/>
<%
	String codigo			= (String) session.getAttribute("codigoPersonal");
	String proyectoId 		= request.getParameter("ProyectoId")==null?"0":request.getParameter("ProyectoId");
	String nombreUsuario	= " ";
	
	Usuario.setCodigoPersonal(codigo);
	if (Usuario.existeReg(conEnoc)){
		Usuario.mapeaRegId(conEnoc, codigo);
		nombreUsuario	= Usuario.getNombre()+" "+Usuario.getApellidoPaterno()+" "+Usuario.getApellidoMaterno();
	}else{
		nombreUsuario = "¡ No existe !";
	}
	
	ArrayList<aca.investiga.InvComentario> lisComentarios = InvComentarioU.getListProyecto(conEnoc, proyectoId," ORDER BY FOLIO");	
%>

<html>
<head>
<body>
	<div class="container-fluid">
		<h2>Listado de Comentarios <small class="text-muted fs-4">Proyecto: <%=proyectoId%></small></h2>				
		<div class="alert alert-info">
			<a href="proyecto" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
			<a href="accionComment?Accion=0&ProyectoId=<%=proyectoId %>" class="btn btn-primary"><i class="fas fa-plus"></i> Agregar</a>&nbsp;
		</div>
	
	<table class="table table-sm table-bordered">  
		<thead>
		<tr>
    		<td colspan="10" height="20" align="center"><strong><font size="2">Usuario: [ <%=codigo%> ] &nbsp; <%=nombreUsuario%></font></strong></td>
  		</tr>	
		</thead>
		<thead class="table-info">
		<tr>				
			<th width="5%">Opción</th>
			<th width="5%">Referencia</th>
			<th width="15%"><spring:message code="aca.Nombre"/></th>
			<th width="10%"><spring:message code="aca.Fecha"/></th>
			<th width="10%"><spring:message code="aca.Tipo"/></th>
			<th width="55%"><spring:message code="aca.Comentario"/></th>
		</tr>
		</thead>
<%
	String nombreDepartamento = ""; 
	for (int i=0; i<lisComentarios.size();i++){
		aca.investiga.InvComentario comentarios = (aca.investiga.InvComentario) lisComentarios.get(i);
		
		String nombreTipo = " ";
		if(comentarios.getTipo().equals("S")){
			nombreTipo = "Público";
		}else{
			nombreTipo = "Privado";
		}
%>		
		<tr>			
			<td>
				<a href="accionComment?Accion=3&ProyectoId=<%= comentarios.getProyectoId() %>&Folio=<%= comentarios.getFolio() %>" class="btn btn-info btn-sm" title="Editar"><i class="fas fa-pencil-alt"></i></a>&nbsp;	
				<a onclick="javascript:eliminar('<%=comentarios.getProyectoId()%>','<%=comentarios.getFolio()%>' );" class="btn btn-danger btn-sm"><i class="fas fa-trash-alt icon-white" title="Eliminar"></i></a>				
			</td>			
			<td><%= comentarios.getFolio() %></td>
			<td><%= aca.vista.UsuariosUtil.getNombreUsuarioCorto(conEnoc, comentarios.getCodigoPersonal()) %></td>
			<td><%= comentarios.getFecha() %></td>
			<td><%= nombreTipo %></td>
			<td><%= comentarios.getComentario() %></td>
		</tr>
<%	} %>
	</table>
	</div>	
</body>
</html>
<script>
	function eliminar(proyectoId, folio){
		if(confirm("¿Estás seguro que deseas eliminar este comentario?")){
			location.href = "accionComment?Accion=2&ProyectoId="+proyectoId+"&Folio="+folio;
		}
	}
</script>
<%@ include file= "../../cierra_enoc.jsp" %>