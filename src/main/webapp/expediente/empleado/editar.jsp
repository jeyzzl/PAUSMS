<%@ page import= "java.util.Base64"%>
<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.exp.spring.ExpDocumento"%>
<%@ page import= "aca.exp.spring.ExpEmpleado"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<head>	
	<script type="text/javascript">
		function cambiarTipo(){
			location.href="detalles?tipos="+document.forma.Tipos.value;
		}
	</script>
</head>
<%
	String codigoEmpleado 		= (String) session.getAttribute("codigoEmpleado");
	String nombreEmpleado 		= (String) request.getAttribute("nombreEmpleado");
	boolean existeHoja			= (boolean) request.getAttribute("existeHoja");
	ExpEmpleado expEmpleado 	= (ExpEmpleado) request.getAttribute("expEmpleado");	
	
	List<ExpDocumento> lisDocumentos			= (List<ExpDocumento>) request.getAttribute("lisDocumentos");	
%>
<body>
<div class="container-fluid">
	<h2>Edit Document <small class="text-muted fs-5"> (<%=codigoEmpleado%> - <%=nombreEmpleado%>)</small></h2>
	<div class="alert alert-info">
		<a href="empleado" class="btn btn-primary">Return</a>
	</div>
	<form name="frmArchivo" id="frmArchivo" enctype="multipart/form-data" action="grabarArchivo" method="post">
	<div class="row">
		<div class="col-6">			
			<label class="form-label">Document</label>
			<select name="DocumentoId" class="form-select">
<% 		for (ExpDocumento doc : lisDocumentos){%>
				<option value="<%=doc.getDocumentoId()%>" <%=expEmpleado.getDocumentoId().equals(doc.getDocumentoId())?"selected":""%>>
					<%=doc.getDocumentoNombre()%>
				</option>
<% 		}%>				
			</select>
			<br>			
			<label class="form-label">Page</label>
			<select name="Hoja" class="form-select">
				<option value="1" <%=expEmpleado.getHoja().equals("1")?"selected":""%>>Page 1</option>
				<option value="2" <%=expEmpleado.getHoja().equals("2")?"selected":""%>>Page 2</option>
				<option value="3" <%=expEmpleado.getHoja().equals("3")?"selected":""%>>Page 3</option>
				<option value="4" <%=expEmpleado.getHoja().equals("4")?"selected":""%>>Page 4</option>
				<option value="5" <%=expEmpleado.getHoja().equals("5")?"selected":""%>>Page 5</option>
				<option value="6" <%=expEmpleado.getHoja().equals("6")?"selected":""%>>Page 6</option>
			</select>
			<br>
			<span class="btn btn-file">
			  	<input type="file" id="archivo" name="archivo" required="required"/>
		  	</span>		  			
		</div>
		<div class="col-6">
<%	if (existeHoja){
		String imagenStr 			= Base64.getEncoder().encodeToString(expEmpleado.getArchivo());
		if(expEmpleado.getNombre().contains(".png") || expEmpleado.getNombre().contains(".jpg") || expEmpleado.getNombre().contains(".jpeg")){
%>			
			<div><img id="img" width="50%;" src="data:image/jpg;base64,<%=imagenStr%>" /></div>
<%		}else{%>
			<a href="descargar?DocumentoId=<%=expEmpleado.getDocumentoId()%>&Hoja=<%=expEmpleado.getHoja()%>" class="btn btn-warning"><i class="fas fa-download"></i> Descargar</a> <%=expEmpleado.getNombre()%>	
<%		}
	} %>			
		</div>
	</div>
	<div class="alert alert-info">		
		<button type="submit" id="btnGuardar" class="btn btn-primary">Save</button>
	</div>
	</form>	
</body>
</html>