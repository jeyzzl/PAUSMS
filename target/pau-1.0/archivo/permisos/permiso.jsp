<%@ page import="java.util.List"%>
<%@ page import="aca.archivo.spring.ArchPermisos"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String codigoUsuario 	= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 	= (String) session.getAttribute("codigoAlumno");
	String nombreAlumno		= (String) request.getAttribute("nombreAlumno");
	
	List<ArchPermisos> lisPermisos = (List<ArchPermisos>)request.getAttribute("lisPermisos");
%>
<div class="container-fluid">
	<h2>Permit <small class="text-muted">( <%=codigoAlumno%> - <%=nombreAlumno%>)</small></h2>
	<div class="alert alert-info d-flex align-items-center">
	<a class="btn btn-primary" href="accion?Accion=1"><i class="icon-white icon-plus"></i> <spring:message code='aca.Añadir'/></a>
	</div>
	<table class="table table-sm table-bordered">
  	<tr> 
	    <th style="width:5%"><spring:message code="aca.Operacion"/></th>
	    <th style="width:5%"><spring:message code="aca.Folio"/></th>
	    <th style="width:5%">Plan</th>
	    <th style="width:5%">User</th>
	    <th style="width:5%">Terminated</th>
	    <th style="width:15%">Start Date</th>
	    <th style="width:15%">Deadline</th>
	    <th style="width:10%"><spring:message code="aca.Status"/></th>
	    <th style="width:35%">Comment</th>
  	</tr>
<%	for (ArchPermisos permiso : lisPermisos){
		String estado = "-";
		if(permiso.getEstado().equals("A")){ 
			estado = "ACTIVE";
		} else if(permiso.getEstado().equals("I")){
			estado = "INACTIVE";
		}
%>
	<tr> 
    	<td align="center">
    		<a class="fas fa-sync-alt" href="cancelar?Folio=<%=permiso.getFolio()%>"></a>&nbsp;
    		<a class="fas fa-trash" href="borrar?&Folio=<%=permiso.getFolio()%>" onclick="javascript:alert('Are you sure you want to delete this Permit?');return true"></a>
    	</td>
    	<td class="text-center"><%=permiso.getFolio()%></td>
    	<td class="text-center"><%=permiso.getPlanId()%></td>
    	<td class="text-center"><%=permiso.getUsuarioAlta()%></td>
    	<td><%=permiso.getUsuarioBaja()%></td>
    	<td><%=permiso.getFechaIni()%></td>
    	<td><%=permiso.getFechaLim()%></td>
    	<td><%=estado%></td>
		<td><%=permiso.getComentario()%></td>
	</tr>
<%	} %>
	</table>
</div>