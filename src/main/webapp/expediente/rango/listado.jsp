<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.maestro.MaestroRango"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<html>
<head></head>
<%
	String year 							= (String)request.getAttribute("year");

	List<String> lisYears					= (List<String>) request.getAttribute("lisYears"); 
	List<MaestroRango> lisRangos			= (List<MaestroRango>) request.getAttribute("lisRangos");
%>
<body>
<div class="container-fluid">
	<h3>Listado de rangos académicos<small class="text-muted fs-6"> ( <%=year%> )</small></h3>
	<form name="frmRango" action="listado" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<select name="Year" class="form-select" onchange="javascript:document.frmRango.submit();" style="width:120px;">
<%	for(String y : lisYears){%>
			<option value="<%=y%>" <%=year.equals(y)?"selected":""%>><%=y%></option>
<%	} %>			
		</select>
		&nbsp; &nbsp;
		<input type="text" class="form-control search-query" placeholder="Buscar" id="buscar" style="width:200px;">
	</div>		
	</form>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	
	<tr>				
		<th>#</th>
		<th>Carta</th>
		<th>Area</th>
		<th>Clave</th>
		<th>Maestro</th>		
		<th>Rango anterior</th>
		<th>Rango actual</th>		
	</tr>
	</thead>
	<tbody>
<%
	int row = 0;
	for(MaestroRango maestro : lisRangos){
		row++;		
%>
	<tr>				
		<td><%=row%></td>
		<td><a href="pdf?CodigoEmpleado=<%=maestro.getCodigoPersonal()%>&Year=<%=year%>">Pdf</td>
		<td><%=maestro.getArea()%></td>
		<td><%=maestro.getCodigoPersonal()%></td>			
		<td><%=maestro.getNombre()%></td>
		<td><%=maestro.getRangoAnterior()%></td>
		<td><b><i><%=maestro.getRangoRecomendado()%></i></b></td>			
	</tr>
<%	} %>		
	</tbody>
	</table>		
</div>	
<script src="../../js/search.js"></script>
<script>
	$('#buscar').search();
</script>
</body>
</html>