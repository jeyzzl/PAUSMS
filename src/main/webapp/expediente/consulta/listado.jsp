<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.vista.spring.Maestros"%>
<%@ page import= "aca.exp.spring.ExpDocumento"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<head>	
</head>
<%
	String codigoEmpleado 		= (String) session.getAttribute("codigoEmpleado");
	String nombreEmpleado 		= (String) request.getAttribute("nombreEmpleado");
	
	List<Maestros> lisMaestros					= (List<Maestros>) request.getAttribute("lisMaestros"); 
	List<ExpDocumento> lisDocumentos			= (List<ExpDocumento>) request.getAttribute("lisDocumentos");	
	HashMap<String,String> mapaDocDelEmpleado	= (HashMap<String,String>) request.getAttribute("mapaDocDelEmpleado"); 
	HashMap<String,String> mapaExpTipo			= (HashMap<String,String>) request.getAttribute("mapaExpTipo"); 
	
%>
<body>
<div class="container-fluid">
	<h2>Consult Documents<small class="text-muted fs-5"> (<%=codigoEmpleado%> - <%=nombreEmpleado%>)</small></h2>
	<div class="alert alert-info d-flex align-items-center">		
		<input type="text" class="form-control search-query" placeholder="Search..." id="buscar" style="width:200px;">
	</div>		
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	
		<tr>				
			<th>#</th>
			<th>Key</th>
			<th>Lecturer</th>		
			<th>Type</th>		
<%		for(ExpDocumento documento : lisDocumentos){%>
			<th class="text-center"><%=documento.getCorto()%></th>
<%		}%>
		</tr>
	</thead>
	<tbody>
<%
	int row = 0;
	for(Maestros maestro : lisMaestros){
		row++;
		
		String tipo = "-";
		String tipoNombre = "Not registered";
		if(mapaExpTipo.containsKey(maestro.getCodigoPersonal())){
			tipo = mapaExpTipo.get(maestro.getCodigoPersonal());
		}
		if (tipo.equals("D")) tipoNombre = "Lecturer";
		if (tipo.equals("A")) tipoNombre = "Assistant";
%>
		<tr>				
			<td><%=row%></td>
			<td><a href="subir?CodigoEmpleado=<%=maestro.getCodigoPersonal()%>"><%=maestro.getCodigoPersonal()%></td>
			<td><%=maestro.getApellidoPaterno()%> <%=maestro.getApellidoMaterno()%> <%=maestro.getNombre()%></td>			
			<td><%=tipoNombre%></td>	
<%			
		for(ExpDocumento documento : lisDocumentos){
			String numHojas = "0";
			if(mapaDocDelEmpleado.containsKey(maestro.getCodigoPersonal()+documento.getDocumentoId())){
				numHojas = mapaDocDelEmpleado.get(maestro.getCodigoPersonal()+documento.getDocumentoId());
%>
			<td class="text-center"><span class="badge bg-dark rounded-pill" title="<%=documento.getDocumentoNombre()%>"><%=numHojas%></span></td>		
<%			}else{%>
			<td class="text-center"><span class="badge bg-secondary rounded-pill" title="<%=documento.getDocumentoNombre()%>"><%=numHojas%></span></td>
<%			}
		} %>	
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