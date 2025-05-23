<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.archivo.spring.ArchStatus"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	List <ArchStatus> lisDocumento 		= (List <ArchStatus>) request.getAttribute("lisDocumento");
	List <String> lisUsados		 		= (List <String>) request.getAttribute("lisUsados");
	HashMap<String,String> mapaUsados 	= (HashMap<String,String>) request.getAttribute("mapaUsados");
%>
<!-- inicio de estructura -->
<head>
	<script type="text/javascript">
		function borrar(statusId, descripcion){
			if(confirm("Are you sure you want to delete the document status?")){
				location.href = "borrarEstado?Accion=4&IdStatus="+statusId+"&Descripcion="+descripcion;
			}
		}
	</script>
</head>
<div class="container-fluid">
	<h1>Status Catalog</h1>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="accion"><b>Add</b></a>
	</div>
	<table style="width:40%"  class="table table-sm">
	<tr> 
	   	<th width="16%"><spring:message code="aca.Operacion"/></th>
		<th width="8%"><spring:message code="aca.Status"/></th>
		<th width="57%"><spring:message code='aca.Descripcion'/></th>
		<th width="10%" class="text-end">Used</th>
	</tr>
<%
	for(ArchStatus doc : lisDocumento) {
		String usados = "0";
		if (mapaUsados.containsKey(doc.getIdStatus())){
			usados = mapaUsados.get(doc.getIdStatus());
		}
%> 
  	<tr class="tr2"> 
    	<td style="text-align:center;">
    		<i class="fas fa-edit" title="Edit" onclick="location.href='accion?Accion=5&IdStatus=<%=doc.getIdStatus()%>&Descripcion=<%=doc.getDescripcion()%>';" ></i>    		
   		<%	if(!lisUsados.contains(doc.getIdStatus()) && usados.equals("0")){ %>
   			<i class="fas fa-trash-alt" title="Delete" onclick="borrar('<%=doc.getIdStatus()%>','<%=doc.getDescripcion()%>');"></i>
		<%	} %>
    	</td>
    	<td><%=doc.getIdStatus()%></td>
    	<td><%=doc.getDescripcion()%></td>
    	<td class="text-end"><%=usados%></td>
  	</tr>
<%
	}
%>
	</table>
</div>