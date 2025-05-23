<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.archivo.spring.ArchDocumentos"%>
<%@page import="aca.archivo.spring.ArchDocAlum"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<head>
	<script type="text/javascript">
		function eliminar(documentoId, descripcion, imagen){
			if(confirm("Are you sure you want to delete this document?")){
				location.href='borrar?IdDocumento='+documentoId;
			}
		}
	</script>
</head>
<%	
	List<ArchDocumentos> lisDocumentos = (List<ArchDocumentos>) request.getAttribute("lisDocumentos");
	HashMap<String, String> mapDocumentosUsados 	= (HashMap<String, String>) request.getAttribute("mapDocumentosUsados");
	HashMap<String, String> mapDocumentosConEstados	= (HashMap<String, String>) request.getAttribute("mapDocumentosConEstados");
	HashMap<String, String> mapEstadosValidos		= (HashMap<String, String>) request.getAttribute("mapEstadosValidos");
%>
<body>
<div class="container-fluid">
	<h1>Document Catalog</h1>
	<div class="alert alert-info d-flex align-items-center">	
		<a class="btn btn-primary" href="accion">Add Document</a>
	</div>	
	<table class="table table-sm table-bordered" style="width:100%">
   	<tr> 
    	<th width="4%">Operation</th>
		<th width="3%">Id</th>
    	<th width="40%"><spring:message code='aca.Descripcion'/></th>
    	<th width="15%"><spring:message code='aca.Imagen'/></th>
    	<th width="10%" class="text-center">Order</th>
    	<th width="10%">Show</th>
    	<th width="5%" class="text-end">#Student</th>
    	<th width="5%" class="text-end">#States</th>
    	<th width="5%" class="text-end">#Valid</th>
    </tr>
<%	
	for(ArchDocumentos doc : lisDocumentos){
		
	String numDocs = "0";
	if(mapDocumentosUsados.containsKey(doc.getIdDocumento())){
		numDocs = mapDocumentosUsados.get(doc.getIdDocumento());
	}
	
	String numEstados = "0";
	if(mapDocumentosConEstados.containsKey(doc.getIdDocumento())){
		numEstados = mapDocumentosConEstados.get(doc.getIdDocumento());
	}
	
	String numValidos = "0";
	if(mapEstadosValidos.containsKey(doc.getIdDocumento())){
		numValidos = mapEstadosValidos.get(doc.getIdDocumento());
	}
%>		
    <tr> 
   		<td align="center">
   			<a href="accion?IdDocumento=<%=doc.getIdDocumento()%>">
   				<i class="fas fa-edit"></i>
   			</a>      		
		<%if(numDocs.equals("0")&&numEstados.equals("0")){%>
			<i class="fas fa-trash-alt" title="Eliminar" title="Eliminar" onclick="eliminar('<%=doc.getIdDocumento()%>', '<%=doc.getDescripcion()%>', '<%=doc.getImagen()%>');" ></i>
		<%}%>
	  	</td>
      	<td class="text-center"><%=doc.getIdDocumento()%></td>
      	<td><%=doc.getDescripcion()%></td>
      	<td><%=doc.getImagen()%></td>
      	<td class="text-center"><%=doc.getOrden()==null?"0":doc.getOrden()%></td>
      	<td><%=doc.getMostrar().equals("S")? "YES":"NO"%></td>
      	<td class="text-end"><%=numDocs%></td>
      	<td class="text-end"><%=numEstados%></td>
      	<td class="text-end"><%=numValidos%></td>
    </tr>
<%	}%>
	 </table>
</div>	
</body>