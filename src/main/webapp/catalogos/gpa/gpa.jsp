<%@page import="java.util.List"%>
<%@page import="aca.catalogo.spring.CatGradePoint"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<%	
	List<CatGradePoint> lisGrados			= (List<CatGradePoint>)request.getAttribute("lisGrados");
%>
<html>
<div class="container-fluid">
	<h2>Grading Scales Catalog</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="accion?Accion=1">Add</a>
	</div>	
	<table class="table table-sm table-bordered">
	<thead>  
	<tr>
		<th>#</th>
		<th>Op.</th>
		<th>Letter Grade</th>
		<th>Grade</th>
		<th>Min.</th>
		<th>Max.</th>
		<th>GPA</th>	
	</tr>
	</thead>
	<tbody>		
<%
	int row = 0;
	for ( CatGradePoint gp : lisGrados){
		row++;
%>
	<tr>
		<td><%= row %></td>
		<td>
			<a href="accion?GradePointId=<%=gp.getGpId()%>"><i class="fas fa-edit"></i></a>
			<a href="javascript:Borrar('<%=gp.getGpId()%>');"><i class="fas fa-trash-alt"></i></a>
		</td>
		<td><%=gp.getGpNombre()%></td>
		<td><%=gp.getTitulo()%></td>
		<td><%=gp.getInicio()%></td>
		<td><%=gp.getFin()%></td>
		<td><%=gp.getPuntos()%></td>		
	</tr>
<%	} %>	
	</tbody>	
	</table>
</div>
</body>
<script type="text/javascript">
function Borrar(gpId){
	if(confirm("<spring:message code="aca.JSEliminar"/>: "+gpId)==true){
  		document.location="borrar?GradePointId="+gpId;
  	}
}
</script>
</html>