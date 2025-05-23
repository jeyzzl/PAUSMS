<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%
	List<aca.cred.spring.CredVisitante> listaInvitados		= (List<aca.cred.spring.CredVisitante>)request.getAttribute("lista");
	List<String> conFoto = (List<String>)request.getAttribute("conFoto");
%>
<script type="text/javascript">
	
	function eliminar(codigoPersonal){
		if (confirm("¿Estas seguro de borrar este registro?")){
			location.href="eliminar?CodigoPersonal="+codigoPersonal+"&Tipo=V";
		}	
	}
</script>
<div class="container-fluid">
	<h1>Visitantes</h1>
	<div class="alert alert-info">
		<a href="nuevo" class="btn btn-primary"><i class="icon-user icon-white"></i><spring:message code='aca.Añadir'/></a>
	</div>
	<table class="table table-bordered">
		<tr class="table-info">
			<td>N°</td>
			<td>Código</td>
			<td>Nombre</td>
			<td>Paterno</td>
			<td>Materno</td>
			<td>Puesto</td>
			<td>Estado</td>
			<td>Tiene foto</td>
		</tr>		
<%		
		int cont = 1;
		for(aca.cred.spring.CredVisitante visita: listaInvitados){
%>	
		<tr>	
			<td>
				<%=cont++%>&nbsp;&nbsp;&nbsp;
				<a href="nuevo?CodigoPersonal=<%=visita.getCodigoPersonal()%>" title="Modificar"><i class="fas fa-pencil-alt"></i></a>
				<a href="javascript:eliminar('<%=visita.getCodigoPersonal()%>')" title="Eliminar"><i class="fas fa-trash-alt"></i></a>
			</td>
			<td><%=visita.getCodigoPersonal()%></td>
			<td><%=visita.getNombre()%></td>
			<td><%=visita.getPaterno()%></td>
			<td><%=visita.getMaterno()%></td>
			<td><%=visita.getComentario()%></td>
			<td><%=visita.getEstado().equals("I")?"Inactivo":"Activo"%></td>
			<td><%=conFoto.contains(visita.getCodigoPersonal())?"Si":"No"%></td>
		</tr>
<%		}%>
		
	</table>
</div>