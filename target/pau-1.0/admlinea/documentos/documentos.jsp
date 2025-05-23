<%@ page import="java.text.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.admision.spring.AdmDocumento"%>
<%@ page import="aca.admision.spring.AdmFormato"%>
<%@ page import="aca.admision.spring.AdmRequisito"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
	<script type = "text/javascript">
		function Borrar( DocumentoId ){
			if(confirm("Are you sure you want to delete this record: "+DocumentoId+"?")==true){
		  		document.location="borrar?DocumentoId="+DocumentoId;
		  	}
		}
	</script>
</head>
<%
	List<AdmDocumento> lisDocumento				 = (List<AdmDocumento>)request.getAttribute("lisDocumento");
	HashMap<String, String> mapaTotalDoc 		 = (HashMap<String, String>)request.getAttribute("mapaTotalDoc");
	HashMap<String, AdmFormato> mapaFormatos	 = (HashMap<String, AdmFormato>)request.getAttribute("mapaFormatos");
%>
<body>
<div class="container-fluid">
	<h2><spring:message code='aca.ListadoDeDocumentosDeIngreso'/></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="editar"><spring:message code='aca.Añadir'/></a>	
	</div>
	<table  class="table table-bordered table-striped">
	<tr> 
	   	<th width="5%" style="text-align: center">Op.</th>
	   	<th width="5%" style="text-align: center"><spring:message code="aca.Clave"/></th>
	   	<th width="25%"><spring:message code='aca.Nombre'/></th>
	   	<th width="10%"><spring:message code='aca.NombreCorto'/></th>
	   	<th width="5%" style="text-align: center"><spring:message code='aca.Orden'/></th>
	   	<th width="10%" style="text-align: center"><spring:message code="aca.Tipo"/></th>
	   	<th width="30%"><spring:message code="aca.Descripcion"/></th>
	   	<th width="10%" style="text-align: center"><spring:message code='aca.Original'/></th>
<%-- 	   	<th width="10%" style="text-align: center"><spring:message code='aca.Formato'/></th> --%>
	</tr>
<%
	for(AdmDocumento docto : lisDocumento){
	
		String regPorDocumento = "0";
		if(mapaTotalDoc.containsKey(docto.getDocumentoId())){	
			regPorDocumento = mapaTotalDoc.get(docto.getDocumentoId());
		}
		String formato = "-";
		if(mapaFormatos.containsKey(docto.getFormatoId())){
			formato = mapaFormatos.get(docto.getFormatoId()).getFormatoNombre();
		}
%>
	<tr class="tr2"> 
		<td style="text-align:center">
			<a href="editar?DocumentoId=<%=docto.getDocumentoId()%>"><i class="fas fa-pencil-alt"></i></a>
     		<%if(regPorDocumento.equals("0")){ %>
     				&nbsp;&nbsp;
				<a href="javascript:Borrar('<%=docto.getDocumentoId()%>')" class="fas fa-trash-alt"></a>
     		<%	} %>
		</td>
    	<td align="center"><b><%=docto.getDocumentoId()%></b></td>
    	<td><%=docto.getDocumentoNombre() %></td>
    	<td><%=docto.getCorto()%></td>
    	<td class="text-center"><i><%=docto.getOrden()%></i></td>
    	<td align="center"><%=docto.getTipo().equals("1")?"General":"Course"%></td>
    	<td align="justify"><%=docto.getComentario() %></td>
    	<td align="center">
  			<% if(docto.getOriginal().equals("S")) out.print("YES"); %>
   			<% if(docto.getOriginal().equals("N")) out.print("NO"); %>
   		</td> 		
<%-- 		<td align="center"><%=formato%></td> --%>
	</tr>
<%	} %>
	</table>
</div>
</body>
</html>
