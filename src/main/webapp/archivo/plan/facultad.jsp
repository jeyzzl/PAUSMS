<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.plan.spring.MapaPlan"%>
<%@ page import="aca.catalogo.spring.CatFacultad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String grupoId		= (String) request.getAttribute("grupoId");
	String facultadId	= (String) request.getAttribute("facultadId");
	String mensaje		= (String) request.getAttribute("mensaje");
	String nombreGrupo	= (String) request.getAttribute("nombreGrupo");
	
	boolean contiene = false;

	CatFacultad facultad = (CatFacultad) request.getAttribute("facultad");

	List<MapaPlan> lisPlanes	= (List <MapaPlan>) request.getAttribute("lisPlanes");
	
	HashMap<String,String> mapaPlanesGrupo = (HashMap<String,String>) request.getAttribute("mapaPlanesGrupo");
%>
<head>
	<script type="text/javascript">
		function borrar(grupoId,documentoId){
			if(confirm("¿Are you sure you want to delete this group?")){
				location.href = "borrarDocumento?GrupoId="+grupoId+"&DocumentoId="+documentoId;
			}
		}
	</script>
</head>
<div class="container-fluid">
	<h2><%=facultad.getNombreFacultad()%><small class="text-muted fs-4"> (<%=nombreGrupo%>)</small></h2>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="grupo?GrupoId=<%=grupoId%>"><i class="fas fa-arrow-left"></i></a>
	</div>
<% if(mensaje.equals("1")){%>
	<div class="alert alert-success">
		Data saved
	</div>
<% }else if(mensaje.equals("2")){%>
	<div class="alert alert-warning">
		Could not be saved
	</div>
<% }%>
	<div class="row">
		<div class="col-md-12">
			<form id="forma" name="forma" action="grabar" method="post">
			<input type="hidden" name="GrupoId" value="<%=grupoId%>"/>
			<input type="hidden" name="FacultadId" value="<%=facultadId%>"/>
			<table class="table table-sm">
			<tr>
				<td colspan="2">
					<a onclick="jQuery('.checkboxPlan').prop('checked',true)" class="btn btn-sm btn-success">All</a>&nbsp;
					<a onclick="jQuery('.checkboxPlan').prop('checked', false)" class="btn btn-sm btn-danger">None</a>
				</td>			
			</tr>	
			<tr>						
				<th><h3>Key</h3></th>
				<th><h3>Plan</h3></th>
			</tr>
<%  	
		for(MapaPlan plan : lisPlanes) {
			if(mapaPlanesGrupo.containsKey(plan.getPlanId())){
				contiene = true;
			}
%>
			<tr>
				<td>
					<h6>
						<input class="checkboxPlan" type="checkbox" name="<%=plan.getPlanId()%>" <%=contiene ? "checked" : ""%>>&nbsp;&nbsp;
							<%=plan.getPlanId()%>				 				
					</h6>
				</td>
				<td>
					<h6>
						<%=plan.getNombrePlan()%>
					</h6>
				</td>
			</tr>
<%					contiene = false;
				}%>			
			</table>
			<div class="alert alert-info d-flex align-items-center">
				<button class="btn btn-primary" type="submit">Save</button>
			</div>		
			</form>
		</div>
	</div>
</div>