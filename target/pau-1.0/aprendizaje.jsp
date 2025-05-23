<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import="aca.bec.spring.BecCategoria"%>

<%-- <%@ include file="id.jsp"%> --%>
<%-- <%@ include file="../../seguro.jsp"%> --%>
<%-- <%@ include file="../../body.jsp"%> --%>
<%-- <%@ include file= "../../idioma.jsp" %> --%>

<html>
<head>
	<title>Experiencias de Aprendizaje</title>
	<link href="academico.css" rel="STYLESHEET" type="text/css"> 
	<link href="print.css" rel="STYLESHEET" type="text/css" media="print">
	<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" type="text/css" media="screen" />	
	<script src="js/jquery-1.9.1.min.js"></script>
	
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"> 
  	<link rel="stylesheet" href="bootstrap/css/bootstrap-responsive.min.css" type="text/css" media="screen" />
  	<script src='bootstrap/js/bootstrap.min.js' type='text/javascript'></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  	<link rel="stylesheet" href="fontawesome/css/font-awesome.min.css">
  	<style>
	  	#content{
			margin: 20px 20px 0 20px;
		}
		
		.contenedor-div{
     		position:relative;
		}
	</style>
</head>
<%
	/* Lista de las categorias */
	List<BecCategoria> lisCategoria 			= (List<BecCategoria>)request.getAttribute("lisCategoria");	
	HashMap<String,String> mapaCategorias 		= (HashMap<String,String>)request.getAttribute("mapaCategorias");
%>
<body>
<div class="container-fluid">
	<hr>Experiencias de aprendizaje</hr>
	
	<table style="width:70%" class="table table-condensed">
	<tr>
		<th >#</th>
		<th >Nombre de archivo</th>
		<th >Descargar</th>
	</tr>
<%				
	int row = 0;
	for (BecCategoria categoria: lisCategoria) {
		row++;	
%>
	<tr>				
		<td><%=row%></td>
		<td><%=categoria.getCategoriaNombre()%></td>
		<td style="text-align: center">
			<a href="pdf/aprendizaje/<%=categoria.getPdf()%>" title="Descargar"><i class="fa fa-book fa-1x" ></i></a>&nbsp;
		</td>
	</tr>
<%	}  %>
	</table>
</div>
</body>
</html>
