<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.archivo.spring.ArchGrupoDocumento"%>
<%@ page import="aca.archivo.spring.ArchGrupoPlan"%>
<%@ page import="aca.catalogo.spring.CatFacultad"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String grupoId		= (String) request.getAttribute("grupoId");
	String nombreGrupo	= (String) request.getAttribute("nombreGrupo");
	int totalPlanes		= (int) request.getAttribute("totalPlanes");
	
	List<ArchGrupoPlan> lisArchGrupoPlan	= (List <ArchGrupoPlan>) request.getAttribute("lisArchGrupoPlan");
	List<CatFacultad> lisFacultades			= (List <CatFacultad>) request.getAttribute("lisFacultades");
 	
	HashMap<String,String> mapaPlanesFacultad = (HashMap<String,String>) request.getAttribute("mapaPlanesFacultad");

	int i = 0;
	String facultadPlanes = "0";
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
	<h2><%=nombreGrupo%><small class="text-muted">(<%=totalPlanes%> registered)</small></h2>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="listado"><i class="fas fa-arrow-left"></i></a>
	</div>
	<div class="row" style="margin: 20px 0px 0 15px;">
		<div class="col-md-12">
			<table class="table table-sm">
				<tr> 
					<th><h2>Key</h2></th>
					<th><h2>Faculty</h2></th>
					<th><h2>Plans</h2></th>
				</tr>
<%  		for(CatFacultad facultad : lisFacultades) {
				if(mapaPlanesFacultad.containsKey(facultad.getFacultadId())){
					facultadPlanes = mapaPlanesFacultad.get(facultad.getFacultadId());
				}
%>
				<tr>
					<td><h6><%=facultad.getFacultadId()%></h6></td>
		 			<td><h6><a href="facultad?GrupoId=<%=grupoId%>&FacultadId=<%=facultad.getFacultadId()%>"><%=facultad.getNombreFacultad()%></a></h6></td>
		 			<td><h6><%=facultadPlanes%></h6></td>
				</tr>
<%				facultadPlanes = "0";
			}%>			
			</table>
		</div>
	</div>
</div>