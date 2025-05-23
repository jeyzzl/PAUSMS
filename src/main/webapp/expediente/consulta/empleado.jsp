<%@ page import= "java.util.Base64"%>
<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.exp.spring.ExpDocumento"%>
<%@ page import= "aca.exp.spring.ExpEmpleado"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<head>	
	<script type="text/javascript">
		function Borrar(documentoId, hoja){
			if (confirm("Are you sure you want to delete this record?")){
				document.location.href="borrar?DocumentoId="+documentoId+"&Hoja="+hoja;
			}			
		}
	</script>
</head>
<%
	String codigoEmpleado 		= (String) session.getAttribute("codigoEmpleado");
	String nombreEmpleado 		= (String) request.getAttribute("nombreEmpleado");	
	boolean existeHoja			= (boolean) request.getAttribute("existeHoja");
	ExpEmpleado expEmpleado 	= (ExpEmpleado) request.getAttribute("expEmpleado");
	String documentoNombre 		= (String) request.getAttribute("documentoNombre");
	
	List<ExpDocumento> lisDocumentos			= (List<ExpDocumento>) request.getAttribute("lisDocumentos"); 
	HashMap<String,ExpEmpleado> mapaDocumentos	= (HashMap<String,ExpEmpleado>) request.getAttribute("mapaDocumentos");
%>
<body>
<div class="container-fluid">
	<h2>Employee <small class="text-muted fs-5"> (<%=codigoEmpleado%> - <%=nombreEmpleado%>)</small></h2>
	<div class="alert alert-info">
		<a href="listado" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;		
	</div>	
	<div class="row">
		<div class="col-6">				
			<table class="table table-sm table-bordered">
			<thead class="table-info">	
			<tr>				
				<th>#</th>
				<th>Document</th>
				<th>Pages</th>		
			</tr>
			</thead>
			<tbody>
<%
	int row = 0;
	for(ExpDocumento exp : lisDocumentos){
		row++;
%>
			<tr>				
				<td><%=row%></td>
				<td><%=exp.getDocumentoNombre() %></td>
				<td>
<% 		for (int i=1; i<=6; i++){
			String colorHoja = "bg-secondary";
			if (mapaDocumentos.containsKey(exp.getDocumentoId()+i)){
				colorHoja = "bg-dark";
%>
					<a href="empleado?DocumentoId=<%=exp.getDocumentoId()%>&Hoja=<%=i%>"><span class="badge <%=colorHoja%> rounded-pill"><%=i%></span></a>
<%			}else{%>
					<span class="badge <%=colorHoja%> rounded-pill"><%=i%></span>	
<% 			}
		}%>
			
				</td>			
			</tr>
<%	} %>		
			</tbody>
			</table>
		</div>
		<div class="col-6">
			<table class="table table-sm table-bordered">
				<thead class="table-info">
					<tr>
						<th>
						View 
				<%	if (existeHoja){%>
						<span class="text-muted">( <%=documentoNombre%> Page:<%=expEmpleado.getHoja()%>&nbsp;)</span>
				<%	} %>
						</th>
					</tr>
				</thead>
<% 		if (existeHoja){
			String imagenStr 			= Base64.getEncoder().encodeToString(expEmpleado.getArchivo());
%>				
				<tbody>
					<tr>
						<td>
<%						if(expEmpleado.getNombre().contains(".png") || expEmpleado.getNombre().contains(".jpg") || expEmpleado.getNombre().contains(".jpeg")){%>
							<img id="img" width="90%" src="data:image/jpg;base64,<%=imagenStr%>" />
<%						}else{%>
							<a href="descargar?DocumentoId=<%=expEmpleado.getDocumentoId()%>&Hoja=<%=expEmpleado.getHoja()%>" class="btn btn-warning"><i class="fas fa-download"></i> Download</a> <%=expEmpleado.getNombre()%>	
<%						}%>
						</td>
					</tr>
<%		}%>				
				</tbody>
			</table>
		</div>
	</div>	
</body>
</html>