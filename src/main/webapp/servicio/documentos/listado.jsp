<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.ssoc.spring.SsocDocumentos"%>

<%
	List<SsocDocumentos> lisDocumentos 	= (List<SsocDocumentos>) request.getAttribute("lisDocumentos");
	HashMap<String,String> mapaNumDoc	= (HashMap<String,String>)request.getAttribute("mapaNumDoc");

%>

<script>
	
	function Borrar(documentoId){
		if (confirm("¿Esta seguro que desea borrar el registro?")){
			document.location.href='borrar?DocumentoId='+documentoId;
		}
	}
	
</script>
<body>
<div class="container-fluid">
	<h1>Catálogo de Documentos</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="editar"><spring:message code="aca.Anadir"/></a>
	</div>
	<table class="table" width="40%">
		<tr> 
	    	<th width="5%"><spring:message code="aca.Operacion"/></th>
	    	<th width="3%"><spring:message code="aca.Numero"/></th>
	    	<th width="20%">Nombre</th>
	    	<th width="20%">Orden</th>
	    	<th width="20%">Tipo</th>
	    	<th width="12%">Acceso</th>
	    	<th width="20%">Num. Uso</th>
	  	</tr>
<%
	int row = 0;
	for (SsocDocumentos documento : lisDocumentos){
		row++;
		
	String numDoc = "-";
	if(mapaNumDoc.containsKey(documento.getDocumentoId())){
		numDoc = mapaNumDoc.get(documento.getDocumentoId());
	}
%>
  	<tr> 
    	<td style="text-align: center"> 
      		<a href="editar?DocumentoId=<%=documento.getDocumentoId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>&nbsp;
<% if(numDoc.equals("0")){%>
      		<a href="javascript:Borrar('<%=documento.getDocumentoId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-times"></i></a>
<% }%>	
    	</td>
    	<td align="center"><%=row%></td>
    	<td><%=documento.getDocumentoNombre()%></td>
    	<td><%=documento.getOrden()%></td>
<%
	String tipo = "-";
	if(documento.getObligatorio().equals("S")){
		tipo = "Obligatorio";
	}else if(documento.getObligatorio().equals("N")){
		tipo = "Opcional";
	}else{
		tipo = "Prerrequisito";
	}
%>
    	<td><%=tipo%></td>
    	<td><%if(documento.getAcceso().equals("A"))out.print("Administrador");else{out.print("Coordinador");}%></td>
    	<td><%=numDoc%></td>
  	</tr>
<%
	}
%>
	</table>
</div>
</body>