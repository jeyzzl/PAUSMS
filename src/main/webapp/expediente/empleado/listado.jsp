<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.vista.spring.Maestros"%>
<%@ page import= "aca.exp.spring.ExpDocumento"%>
<%@ page import= "aca.exp.spring.ExpTipo"%>

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
	HashMap<String,ExpTipo> mapaTipos			= (HashMap<String,ExpTipo>) request.getAttribute("mapaTipos");
%>
<body>
<div class="container-fluid">
	<h2>Employee Archive<small class="text-muted fs-5"> (<%=codigoEmpleado%> - <%=nombreEmpleado%>)</small></h2>
	<div class="alert alert-info d-flex align-items-center">
		<a href="empleado" class="btn btn-primary"><i class="fas fa-plus"></i> Add Employee</a>&nbsp; &nbsp;
		<input type="text" class="form-control search-query" placeholder="Search..." id="buscar" style="width:200px;">
	</div>		
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	
		<tr>				
			<th>#</th>
			<th>Key</th>
			<th>Employee</th>
			<th>Status</th>
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
		
		String tipo 	= "-";
		String estado 	= "X"; 
		if(mapaTipos.containsKey(maestro.getCodigoPersonal())){
			tipo 	= mapaTipos.get(maestro.getCodigoPersonal()).getTipo();
			estado 	= mapaTipos.get(maestro.getCodigoPersonal()).getEstado();
		}
%>
		<tr>				
			<td><%=row%></td>
			<td><a href="subir?CodigoEmpleado=<%=maestro.getCodigoPersonal()%>"><%=maestro.getCodigoPersonal()%></td>
			<td><%=maestro.getApellidoPaterno()%> <%=maestro.getApellidoMaterno()%> <%=maestro.getNombre()%></td>			
			<td>				
				<form name="form" action="cambiarEstado" method="post">
				<input type="hidden" name="CodigoEmp" value="<%=maestro.getCodigoPersonal()%>">
				<select name="Estado" onchange="this.form.submit()">					
					<option value="A" <%=estado.equals("A")?"selected":""%>>Active</option>
					<option value="I" <%=estado.equals("I")?"selected":""%>>Inactive</option>
				</select>
				</form>
			</td>
			<td>
				<form name="form" action="cambiarTipo" method="post">
					<input type="hidden" name="CodigoEmpleado" value="<%=maestro.getCodigoPersonal()%>">
					<select name="Tipo" onchange="this.form.submit()">
						<option value="-" <%=tipo.equals("-")?"selected":""%>>Not registered</option>
						<option value="D" <%=tipo.equals("D")?"selected":""%>>Lecturer</option>
						<option value="A" <%=tipo.equals("A")?"selected":""%>>Assistant</option>
					</select>
				</form>
			</td>
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