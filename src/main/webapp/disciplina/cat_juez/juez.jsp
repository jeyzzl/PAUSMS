<%@ page import="java.util.List"%>
<%@ page import="aca.disciplina.spring.CondJuez"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<script type="text/javascript">
	function Borrar( JuezId ){
		if(confirm("Are you sure you want to delete this record? : "+JuezId)==true){
	  		document.location="borrar?IdJuez="+JuezId;
	  	}
	}
</script>
<%	
	String sBgcolor				= "";
	
	List<CondJuez> lisJueces	= (List<CondJuez>)request.getAttribute("lisJueces");
%>
<div class="container-fluid">
<h2>Judges Catalog</h2>
<div class="alert alert-info">
	<a class="btn btn-primary" href="editar"><spring:message code='aca.Añadir'/></a>	
</div>
<table style="width:60%"  class="table table-striped">  
  <tr> 
    <th width="21%" height="22"><spring:message code="aca.Operacion"/></th>
    <th width="14%">Id</th>
    <th width="65%"><spring:message code='aca.Descripcion'/></th>
  </tr>
<%
	int row = 0;
	for(CondJuez juez : lisJueces){
		row++;
	  							
%>
 <tr class="tr2"> 
    <td>
    	<a title="Edit" href="editar?IdJuez=<%=juez.getIdJuez()%>"><i class="fas fa-pencil-alt"></i></a>&nbsp;
    	<a title="Delete" href="javascript:Borrar('<%=juez.getIdJuez()%>')"><i class="fas fa-trash"></i></a> 
     </td>
    <td><%=juez.getIdJuez()%></td>
    <td><%=juez.getNombre()%></td>    
  </tr>
<%	} %>
</table>
</div>