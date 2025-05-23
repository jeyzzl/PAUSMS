<% String idJsp= "000"; %>
<%@include file= "seguro2.jsf" %>
<%@ include file="idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.emp.spring.EmpDocumento"%>
<%@page import="aca.emp.spring.EmpDocEmp"%>
<%@ page import= "java.util.Base64"%>

<html>
<head>
	<title><spring:message code='aca.SistemaAcademico'/></title>
	<link href="academico.css" rel="STYLESHEET" type="text/css"> 
	<link href="print.css" rel="STYLESHEET" type="text/css" media="print">
	<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" type="text/css" media="screen" />	
	<script src="js/jquery-1.9.1.min.js"></script>
	
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"> 
  	<link rel="stylesheet" href="bootstrap/css/bootstrap-responsive.min.css" type="text/css" media="screen" />
  	<script src='bootstrap/js/bootstrap.min.js' type='text/javascript'></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  	<style>
  		#content{
			margin: 20px 20px 0 20px;
		}
	</style>
		
	<script type="text/javascript">
		function borrar(documentoId,hoja){
			if (confirm("¿Estas seguro de borrar este registro?")){
				location.href="borrar?DocumentoId="+documentoId;
			}	
		}	
	</script>
</head>
<link rel="stylesheet" href="../../js/bootstrap-fileupload/bootstrap-fileupload.min.css" />
<script src="../../js/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>
<script type="text/javascript">
	function Refrescar(){		
		document.frmDatos.submit();
	}

	function borrarImagen(documentoId,hoja){
		if(confirm("¿Desea eliminar el documento?")){
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
	byte imagenByte[]			= (byte[])request.getAttribute("imagenByte");
	String imagenStr 			= Base64.getEncoder().encodeToString(imagenByte);
	
	List<EmpDocumento> lisDocumentos	= (List<EmpDocumento>)request.getAttribute("lisDocumentos");
	
	String documentoId 			= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
	String hoja 				= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");
%>
<body marginwidth="0" marginheight="0" leftmargin="0" topmargin="0" background="../../imagenes/back.gif">
<div class="container-fluid">
	<h2>Editar documento<small> ( <%=codigoEmpleado%> - <%=nombreEmpleado%> - <%=nombreEmpleado%> - Hoja:<%=hoja%> )</small></h2>
	<form name="frmDatos" id="frmDatos" action="nuevo" method="post">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="docEmp"><i class="fas fa-arrow-left"></i> <spring:message code='aca.Regresar'/></a>&nbsp;&nbsp;				
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
			&nbsp;	
<% 		}
		out.print("<b>Capturado por: "+ docEmp.getUsuario()+" - "+nombreUsuario+"</b>");
%>
			</div>
<%		
	}		
%>				
			<br><br>
		</div>
	</div>
	</form>	
</div>
</body>
