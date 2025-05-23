<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>
<%@ page import= "aca.leg.spring.LegPermisos"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Cancelar(folio, codigo){
		if(confirm("Esta seguro que desea cancelar el permiso!")){
			document.location="cancelar?Folio="+folio+"&Codigo="+codigo+"&Cancelar=1";
		}
	}
</script>
<%
	String paisNombre							= (String) request.getAttribute("paisNombre");
	AlumPersonal alumPersonal					= (AlumPersonal) request.getAttribute("alumPersonal");
	List<LegPermisos> lisPermisos				= (List<LegPermisos>) request.getAttribute("lisPermisos");
	HashMap<String, String> mapNombrePermiso	= (HashMap<String, String>) request.getAttribute("mapNombrePermiso");
%>
<div class="container-fluid">
	<h2>Permissions<small class="text-muted fs-4"> ( <%=alumPersonal.getCodigoPersonal()%> - <%= alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno()%> - <%=paisNombre%> )</small></h2>
	<div class="alert alert-info">
<%	if (!alumPersonal.getPaisId().equals("91")){%>	
		<a class="btn btn-primary" href="editar"><spring:message code="aca.Anadir"/></a>
<% 	}else{
		out.print("¡Este alumno no es extranjero!");
	}
%>		
	</div>
	<table class="table table-bordered table-sm">
	<thead>
	<tr class="table-info">
		<th width="7%">Op.</th>
		<th>No.</th>
		<th>Start Date</th>
		<th>End Date</th>
		<th>User register</th>
		<th>User Drop</th>
		<th>State</th>
	</tr>
	</thead>
	<tbody>
<%
	for (LegPermisos permiso : lisPermisos){
		
		String usuarioAlta = "-";
		if(mapNombrePermiso.containsKey(permiso.getUsuarioAlta())){
			usuarioAlta = mapNombrePermiso.get(permiso.getUsuarioAlta());
		}
		
		String usuarioBaja = "-";
		if(mapNombrePermiso.containsKey(permiso.getUsuarioBaja())){
			usuarioBaja = mapNombrePermiso.get(permiso.getUsuarioBaja());
		}
%>
	<tr>
		<td>
			<a href="editar?Folio=<%=permiso.getFolio()%>&Codigo=<%=permiso.getCodigo()%>" class="fas fa-edit" title="Editar"></a>&nbsp;
    		<a href="javascript:Cancelar('<%=permiso.getFolio()%>','<%=permiso.getCodigo()%>')" class="btn btn-warning btn-sm" title="Cancelar">Cancelar</a>
		</td>
		<td><%=permiso.getFolio()%></td>
		<td><%=permiso.getFechaIni()%></td>
		<td><%=permiso.getFechaLim()%></td>
		<td><%=usuarioAlta%></td>
		<td><%=usuarioBaja%></td>
		<td><%=permiso.getStatus()%></td>
	</tr>
<%		
	}
%>	
	</tbody>
	</table>
</div>
