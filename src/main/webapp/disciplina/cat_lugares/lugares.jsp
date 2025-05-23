<%@ page import="java.util.List"%>
<%@ page import="aca.disciplina.spring.CondLugar"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="lugarU" scope="page" class="aca.disciplina.CondLugarUtil"/>

<script type="text/javascript">
	function Borrar( LugarId ){
		if(confirm("Are you sure you want to delete this record? : "+LugarId)==true){
	  		document.location="borrar?IdLugar="+LugarId;
	  	}
	}
</script>


<%	
	String sBgcolor		= "";
	List<CondLugar> lisLugares	= (List<CondLugar>)request.getAttribute("lisLugares");
%>
<div class="container-fluid">
<h2>Location Catalog</h2>
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
	for(int i=0; i<lisLugares.size(); i++){
		CondLugar lugar = (CondLugar) lisLugares.get(i);
	  							
%>
 <tr> 
    <td>
	    <a title="Edit" href="editar?IdLugar=<%=lugar.getIdLugar()%>"><i class="fas fa-pencil-alt"></i></a>
	    <a title="Delete" href="javascript:Borrar('<%=lugar.getIdLugar()%>')"><i class="fas fa-trash"></i></a>      
    </td>
    <td><%=lugar.getIdLugar()%></td>
    <td><%=lugar.getNombre()%></td>
    
  </tr>
<%	
	} 
%>
</table>
</div> 