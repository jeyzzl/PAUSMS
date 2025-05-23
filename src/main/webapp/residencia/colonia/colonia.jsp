<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="ColoniaU" scope="page" class="aca.residencia.ResColoniaUtil"/>

<script type="text/javascript">
	function Borrar( ColoniaId ){
		if(confirm("Are you sure you want to delete the record?: "+ColoniaId)==true){
	  		document.location="accion?Accion=4&ColoniaId="+ColoniaId;
	  	}
	}
</script>

<%
	ArrayList<aca.residencia.ResColonia> lisColonia 		= ColoniaU.getListAll(conEnoc, "ORDER BY 2");
	
%>
<html>
<head><title><spring:message code='aca.DocumentoSinTitulo'/></title></head>
<body>
<div class="container-fluid">
<h1>Neighborhood List</h1>
<div class="alert alert-info">
	<a class="btn btn-primary" href="accion?Accion=1"><spring:message code='aca.Añadir'/></a>
</div>
<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
  <tr> 
    <th width="17%"><spring:message code="aca.Operacion"/></th>
    <th width="15%"><spring:message code="aca.Numero"/></th>
    <th width="68%"><spring:message code='aca.Descripcion'/></th>
  </tr>
  </thead>
<%		
	for (aca.residencia.ResColonia col : lisColonia){	 	
%>
  <tr> 
    <td align="center"> <a class="fas fa-edit" href="accion?Accion=5&ColoniaId=<%= col.getColoniaId()%>"> 
      </a> <a class="fas fa-trash-alt" href="javascript:Borrar('<%=col.getColoniaId()%>')">  
      </a> </td>
    <td align="center"><%=col.getColoniaId()%></td>
    <td><%= col.getColoniaNombre() %></td>
  </tr>
  <%
	}	
	ColoniaU 		= null;
	lisColonia	    = null;
%>
</table>
</div>
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %> 