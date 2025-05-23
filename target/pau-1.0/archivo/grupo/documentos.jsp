<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.archivo.spring.ArchGrupoDocumento"%>
<%@ page import="aca.archivo.spring.ArchDocumentos"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String grupoId		= (String) request.getAttribute("grupoId");

	List<ArchDocumentos> lisArchDocumentos		= (List <ArchDocumentos>) request.getAttribute("lisArchDocumentos");
	HashMap<String,ArchGrupoDocumento> mapaArchGrupoDocumento = (HashMap<String,ArchGrupoDocumento>) request.getAttribute("mapaArchGrupoDocumento");

	int i = 0;
%>
<head>
	<script type="text/javascript">
		function borrar(grupoId,documentoId){
			if(confirm("¿Are you sure you want to delete this document from the group?")){
				location.href = "borrarDocumento?GrupoId="+grupoId+"&DocumentoId="+documentoId;
			}
		}
	</script>
</head>
<div class="container-fluid">
	<h1>Documents in Group</h1>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="listado"><i class="fas fa-arrow-left"></i></a>
	</div>
	<div class="row" style="margin: 20px 0px 0 15px;">
		<div class="col-md-6">
			<h3>Active</h3>
			<table class="table">
				<tr> 
					<th width="10%">Id</th>
					<th width="90%">Description</th>
				</tr>
<%  		for(ArchDocumentos doc : lisArchDocumentos) {
				if(mapaArchGrupoDocumento.containsKey(doc.getIdDocumento())){%>
				<tr> 
		 			<td>
		 				<%=doc.getIdDocumento()%>
					  	<a class="fas fa-trash-alt" href="javascript:borrar('<%=grupoId%>','<%=doc.getIdDocumento()%>');"></a>
		 			</td>
		 			<td><%=doc.getDescripcion()%></td>
				</tr>
<%				}					
			}%>			
			</table>
		</div>
		<div class="col-md-6">
			<h3>Inactive</h3>
			<table class="table">
				<tr> 
					<th width="10%">Id</th>
					<th width="90%">Description</th>
				</tr>
<%  		for(ArchDocumentos doc : lisArchDocumentos) {
				if(!mapaArchGrupoDocumento.containsKey(doc.getIdDocumento())){%>
				<tr onclick="window.location='agregarDocumento?GrupoId=<%=grupoId%>&DocumentoId=<%=doc.getIdDocumento()%>'"> 
		 			<td>
		 				<%=doc.getIdDocumento()%> <i class="fas fa-plus"></i> 
		 			</td>
		 			<td><%=doc.getDescripcion()%></td>
				</tr>
<%				}	
			}%>			
			</table>
		</div>
	</div>
</div>