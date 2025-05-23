<%@ page import="java.util.List"%>
<%@ page import="aca.disciplina.spring.CondReporte"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<script type="text/javascript">
	function Borrar( ReporteId ){
		if(confirm("Are you sure you want to delete this record? : "+ReporteId)==true){
	  		document.location="borrar?IdReporte="+ReporteId;
	  	}
	}
</script>
<%	
	String bgColor					= "";
	String tipo 					= "0";
	List<CondReporte> lisReportes	= (List<CondReporte>)request.getAttribute("lisReportes");
	
%>
<div class="container-fluid">
<h2>Report Type Catalog</h2>
<div class="alert alert-info">
	<a class="btn btn-primary" href="editar"><spring:message code='aca.Añadir'/></a>
</div>
<table style="width:60%"  class="table table-striped">
  <thead>   
  <tr> 
    <th width="21%" height="22"><spring:message code="aca.Operacion"/></th>
    <th width="14%">Id</th>
    <th width="65%"><spring:message code='aca.Descripcion'/></th>
    <th width="65%"><spring:message code="aca.Tipo"/></th>
  </tr>
  </thead>
<%	for(int i=0; i<lisReportes.size(); i++){
		CondReporte reporte = (CondReporte) lisReportes.get(i);
	  							
%>
 <tr class="tr2"> 
    <td>
	    <a title="Edit" href="editar?IdReporte=<%=reporte.getIdReporte()%>"><i class="fas fa-pencil-alt"></i></a>&nbsp;
	    <a title="Delete" href="javascript:Borrar('<%=reporte.getIdReporte()%>')"><i class="fas fa-trash"></i></a>      
    </td>
    <td><%=reporte.getIdReporte()%></td>
    <td><%=reporte.getNombre()%></td>
<%		if(reporte.getTipo().equals("D")){ tipo = "Misconduct"; } 
		else if(reporte.getTipo().equals("C")) { tipo = "Praise"; }else{ tipo = "Other"; }
%>
    <td><%=tipo%></td>
    
  </tr>
<%	
	} 
%>
</table>
</div> 