<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.archivo.spring.ArchGrupo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	List<ArchGrupo> lisArchGrupo 			= (List <ArchGrupo>) request.getAttribute("lisArchGrupo");
	HashMap<String,String> mapaDocumentos 	= (HashMap<String,String>) request.getAttribute("mapaDocumentos");
	HashMap<String,String> mapaPlanes 		= (HashMap<String,String>) request.getAttribute("mapaPlanes");
%>
<head>
	<script type="text/javascript">
		function borrar(grupoId){
			if(confirm("¿Are you sure you want to delete this group?")){
				location.href = "borrar?GrupoId="+grupoId;
			}
		}
	</script>
</head>
<div class="container-fluid">
	<h2>Groups Catalog</h2>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="nuevo">New</a>
	</div>
	<table class="table table-sm">
		<tr> 
			<th width="5%">Op.</th>
			<th width="5%">Id</th>
			<th width="30%">Name</th>
			<th width="40%">Comment</th>
			<th width="10%" class="text-right">Add Documents</th>
			<th width="5%" class="text-right">Plans</th>
		</tr>
<%  
	for(ArchGrupo grupo : lisArchGrupo) {
		
		String numDocumentos 	= "0";
		if (mapaDocumentos.containsKey(grupo.getGrupoId())){
			numDocumentos 		= mapaDocumentos.get(grupo.getGrupoId());
		}
		
		String numPlanes 		= "0";
		if (mapaPlanes.containsKey(grupo.getGrupoId())){
			numPlanes 			= mapaPlanes.get(grupo.getGrupoId());
		}		
%>
		<tr> 
			<td>
				<a href="nuevo?GrupoId=<%=grupo.getGrupoId()%>"><i class="fas fa-edit"></i></a>
<%		if (numDocumentos.equals("0")){%>				
			  	<a href="javascript:borrar('<%=grupo.getGrupoId()%>');"><i class="fas fa-trash-alt" style="color:red"></i></a>
<%		} %>			  	
			</td>
 			<td><%=grupo.getGrupoId()%></td>
 			<td><%=grupo.getGrupoNombre()%></td>
 			<td><%=grupo.getComentario()%></td>
 			<td class="text-right"><a href="documentos?GrupoId=<%=grupo.getGrupoId()%>" style="color:white"><span class="badge bg-dark rounded-pill"><i class="fas fa-plus"></i> <%=numDocumentos%></span></a></td>
 			<td class="text-right"><%=numPlanes%></td>
		</tr>
<%	}%>
	</table>
</div>