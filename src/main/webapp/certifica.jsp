<% String idJsp= "000";%>
<%@include file= "seguro2.jsf" %>
<%@include file= "idioma.jsp" %>
<%@page import="aca.menu.*"%>
<%@page import="aca.acceso.spring.AccesoValida"%>

<html>
<head>
	<title><spring:message code='aca.SistemaAcademico'/></title>
	<link rel="stylesheet" href="academico.css" type="text/css">
	<link rel="stylesheet" href="print.css"  type="text/css" media="print">
	<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" type="text/css" media="screen" />	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  	<link rel="stylesheet" href="bootstrap/css/bootstrap-responsive.min.css" type="text/css" media="screen" />
  	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" type='text/css'>
  	
  	<script src="js/jquery-1.9.1.min.js"></script>
  	<script src='bootstrap/js/bootstrap.min.js' type='text/javascript'></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>  	
  	<style>
  		#content{
			margin: 20px 20px 0 20px;
		}
		
		.contenedor-div{
     		position:relative;
		}
		.Llama{
			position: fixed; 
			bottom: 0px;
			right: 0px;
    		width: 200px;
    		height: 300px;
			max-width: 35%;
			margin: 0 auto;			
		}
	</style>
	<script>	
		function GrabarContacto(){				
			document.frmContacto.submit();
		}
	</script>
</head>
<%	
	String codigoPersonal			= (String) session.getAttribute("codigoPersonal");
	String codigo					= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");
	String alumnoNombre				= (String) request.getAttribute("alumnoNombre");
	boolean existe					= (boolean) request.getAttribute("existe");
	AccesoValida accesoValida		= (AccesoValida) request.getAttribute("accesoValida");
	String tipoDocumento 			= accesoValida.getTipo();
	String tipoNombre 				= "-";
	
	if (tipoDocumento.equals("0")) tipoNombre = "indefinido";
	if (tipoDocumento.equals("1")) tipoNombre = "credencial del alumno";
	if (tipoDocumento.equals("2")) tipoNombre = "constancia de estudio";
%>
<body>
<div class="container-fluid">
	<h2>Universidad de Montemorelos, A.C. <small>( Validación de documentos )</small></h2>
	<hr>
<%	if (existe){%>
	<div class="alert alert-success">Este documento (<b><%=tipoNombre%></b>) se encuentra registrado en la base de datos de nuestra institución, por tal motivo, constatamos que es un documento fidedigno y ha sido elaborado con los siguientes datos:</div>	
	<table class="table table-bordered" style="width:50%">
	<thead>
	<tr class="alert alert-success">
		<th colspan="2">Datos del documento</th>
	</tr>
	</thead>	
	<tbody>
	<tr>
		<td>Alumno:</td>
		<td><%=accesoValida.getCodigoPersonal()%> - <%=alumnoNombre%></td>
	</tr>
	<tr>
		<td>Fecha de elaboración:</td>
		<td><%=accesoValida.getFecha()%></td>
	</tr>
	<tr>
		<td>Folio:</td>
		<td><%=accesoValida.getCodigo()%></td>
	</tr>
	</tbody>
	</table>
<%	}else{
		out.print("<div class='alert alert-danger'>El folio:<b>"+codigo+"</b> no se encuentra registrado en la base de datos de documentos emitidos por nuestra institución.</div>");
	}
%>		
</div>
<div class="contenedor-div">
	<img src="imagenes/um3.png" class="Llama" width="400px">
</div>
</body>