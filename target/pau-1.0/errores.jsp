<html>
<head>
	<link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css" type="text/css" media="screen" />	
	<script src="/js/jquery-1.9.1.min.js"></script>	
  	<script src='/bootstrap/js/bootstrap.min.js' type='text/javascript'></script>  	
  	<style>
  		#content{
			margin: 20px 20px 0 20px;
		}		
	</style>
</head>
<%
	Exception ex 		= (Exception)request.getAttribute("exception");
	StringBuffer ruta 	= (StringBuffer)request.getAttribute("url");
%>
<body>
<div class="container-fluid">
	<h2>Manejo de excepciones <small> ( <%=ex.hashCode()%> )</small></h2>
	<div class="alert alert-danger"><h4><%=ex.getMessage()%></h4></div>	
	<h5>Dirección: <%=ruta.toString()%></h5>	
</div>
</body>
</html>