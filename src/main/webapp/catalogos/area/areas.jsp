<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatArea"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar( areaId ){
		if(confirm("<spring:message code="aca.JSEliminar"/>: "+areaId)==true){
	  		document.location="borrarArea?AreaId="+areaId;
	  	}
	}
</script>

<style>
	.left{
		text-align: left;
	}
</style>

<%	
	List<CatArea> lisAreas							= (List<CatArea>) request.getAttribute("lisAreas");	
	HashMap<String, String>	mapaAreasPorFacultad	= (HashMap<String, String>) request.getAttribute("mapaAreasPorFacultad");
%>

<html>
<div class="container-fluid">
	<h1><spring:message code="catalogos.area.Titulo"/></h1>
	<div class="alert alert-info">
		<a class="btn btn-success" href="editarArea"><spring:message code="aca.Anadir"/></a>
	</div>
	<div>
		<table class="table table-responsive table-bordered"> 
			<thead>
				<tr> 
			    	<th width="5%"><spring:message code="aca.Operacion"/></th>
			    	<th width="3%"><spring:message code="aca.Numero"/></th>
			    	<th width="3%">School President</th>
			    	<th width="20%">Area</th>
		  		</tr>
			</thead> 
<%	
	for (CatArea areas : lisAreas){
		
		String numFacultad = "0";		
		if(mapaAreasPorFacultad.containsKey(areas.getAreaId())){
			numFacultad = mapaAreasPorFacultad.get(areas.getAreaId());
		}
%>
			<tbody>
			  	<tr> 
			    	<td class="left"> 
			      		<a href="editarArea?AreaId=<%=areas.getAreaId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>&nbsp;
			<%
				if(numFacultad.equals("0")){
			%>
			      		<a href="javascript:Borrar('<%=areas.getAreaId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash-alt"></i></a>
			<%
				}
			%>
			    	</td>
			    	<td class="left"><%=areas.getAreaId()%></td>
			    	<td class="left"><span class="badge bg-dark"><%=areas.getCodigoPersonal()%></span></td>
			    	<td class="left"><a href="facultad?AreaId=<%=areas.getAreaId()%>"><%=areas.getNombreArea()%></a>   
			  	</tr>
			</tbody>
<%
	}
%>
		</table>
	</div>
</div>
</body>
</html> 