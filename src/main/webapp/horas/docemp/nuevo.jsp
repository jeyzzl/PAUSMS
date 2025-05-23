<%@ include file= "id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.emp.spring.EmpDocumento"%>
<%@page import="aca.emp.spring.EmpDocEmp"%>
<%@ page import= "java.util.Base64"%>

<link href="../../js/bootstrap-fileupload/bootstrap-fileupload.min.css" rel="stylesheet"/>
<script src="../../js/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>
<script type="text/javascript">
	function Refrescar(){		
		document.frmDatos.submit();
	}

	function borrarImagen(documentoId,hoja){
		if(confirm("�Desea eliminar el documento?")){
			document.location.href = "borrarimagen?documentoId="+documentoId+"&hoja="+hoja;
		}
	}	
</script>
<%	
	String codigoEmpleado 		= (String) session.getAttribute("codigoEmpleado");
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
	String nombreEmpleado 		= (String) request.getAttribute("nombreEmpleado");
	String nombreUsuario 		= (String) request.getAttribute("nombreUsuario");
	String nombreDocumento 		= (String) request.getAttribute("nombreDocumento");
	EmpDocEmp docEmp	 		= (EmpDocEmp) request.getAttribute("docEmp");
	boolean tieneImagen	 		= (boolean) request.getAttribute("tieneImagen");
	byte[] imagenByte = (byte[])request.getAttribute("imagenByte");
	String imagenStr 			= Base64.getEncoder().encodeToString(imagenByte);
	
	List<EmpDocumento> lisDocumentos	= (List<EmpDocumento>)request.getAttribute("lisDocumentos");
	
	String documentoId 			= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
	String hoja 				= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");
%>
<body >
<div class="container-fluid">
	<h2>Editar documento<small class="text-muted fs-5"> ( <%=codigoEmpleado%> - <%=nombreEmpleado%> - <%=nombreEmpleado%> - Hoja:<%=hoja%> )</small></h2>
	<form name="frmDatos" id="frmDatos" action="nuevo" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary"  href="documentos"><i class="fas fa-arrow-left"></i> <spring:message code='aca.Regresar'/></a>&nbsp;&nbsp;
		Documento:&nbsp;
		<select name="DocumentoId" class="form-control" style="width:200px">
			<option value="0"  <%=documentoId.equals("0")?"selected":""%>>Seleccionar</option>			
<%		
	int row = 0;
	for (EmpDocumento documentos : lisDocumentos){
		row++;	
 %>
 			<option value="<%=documentos.getDocumentoId()%>" <%=documentoId.equals(documentos.getDocumentoId())?"selected":""%>><%=documentos.getDocumentoNombre()%></option>
 <%	} %>		
		</select>&nbsp;&nbsp;
		Hoja:&nbsp;
		<select name="Hoja" class="form-control" style="width:200px">
			<option value="0" <%=hoja.equals("0")?"selected":""%>>Seleccionar</option>
			<option value="1" <%=hoja.equals("1")?"selected":""%>>Hoja 1</option>
			<option value="2" <%=hoja.equals("2")?"selected":""%>>Hoja 2</option>
			<option value="3" <%=hoja.equals("3")?"selected":""%>>Hoja 3</option>
			<option value="4" <%=hoja.equals("4")?"selected":""%>>Hoja 4</option>
			<option value="5" <%=hoja.equals("5")?"selected":""%>>Hoja 5</option>
			<option value="6" <%=hoja.equals("6")?"selected":""%>>Hoja 6</option>
			<option value="7" <%=hoja.equals("7")?"selected":""%>>Hoja 7</option>			
		</select>&nbsp;&nbsp;
		<a href="javascript:Refrescar()" class="btn btn-primary btn-sm"> <i class="fas fa-sync-alt"></i></a>		
	</div>
	</form>
	<form name="frmImagen" id="frmImagen" enctype="multipart/form-data" action="guardarimagen" method="post">
		<input type="hidden" name="DocumentoId" value="<%=documentoId%>" />
		<input type="hidden" name="Hoja" value="<%=hoja%>" />
		<div class="row">
			<div class="span5">
<%	if (tieneImagen){	%>
				<img id="img" width="90%" src="data:image/jpg;base64,<%=imagenStr%>" />
				<br><br>
				<div class="wel">
<%		if(codigoPersonal.equals(docEmp.getUsuario())){%>	
				<a href="javascript:borrarImagen('<%=documentoId%>','<%=hoja%>')" class="btn btn-danger btn-sm"><i class="fas fa-trash-alt"></i></a>
			&nbsp;	
<% 		}
		out.print("<b>Capturado por: "+ docEmp.getUsuario()+" - "+nombreUsuario+"</b>");
%>
				</div>
<%		
	}else if (!documentoId.equals("0") && !hoja.equals("0")){		
%>				
				<br><br>
				<label for="Imagen">Imagen:</label>
					<div class="fileupload fileupload-new" data-provides="fileupload" style="border:solid 1pt">
					  <div class="fileupload-new thumbnail" style="width: 100px; height: 100px;"><img src="http://www.placehold.it/100x100/EFEFEF/AAAAAA" /></div>
					  <div class="fileupload-preview fileupload-exists thumbnail" style="width: 100px; height: 100px;"></div>
						<span class="btn btn-primary btn-sm btn-file">
							<span class="fileupload-new">Selecciona Imagen</span>
							<span class="fileupload-exists btn btn-primary btn-sm">Cambiar </span>
							<input type="file" id="imagen" name="imagen" />
						</span>
						<span class="fileupload-exists pul-right">
							<a href="#" class="fileupload-exists btn btn-danger btn-sm" data-dismiss="fileupload"> <button class="btn btn-danger btn-sm" >Quitar</button></a>
						</span>
						<span class="fileupload-exists" >
							<button class="btn btn-dark" id="btnGuardar" value="Enviar" onclick="guardarImagen();" class="enviar" ><i class="icon-arrow-up icon-white"></i> Enviar</button>
						</span>
					</div>
<%	}%>						
				<br><br>
			</div>
		</div>
	</form>	
</div>
</body>
