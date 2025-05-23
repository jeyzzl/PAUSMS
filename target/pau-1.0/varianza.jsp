<% String idJsp= "000";%>
<%@include file= "seguro2.jsf" %>
<%@include file= "idioma.jsp" %>

<%@page import="aca.menu.*"%>

<html>
<head>
	<title><spring:message code='aca.SistemaAcademico'/></title>
	<link rel="stylesheet" href="print.css"  type="text/css" media="print">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/fontawesome.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/solid.min.css">			
	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap4.4/css/bootstrap.min.css">
	<script src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/bootstrap4.4/js/bootstrap.min.js"></script>  

</head>
<%		

	String valoresX 	 	= request.getParameter("ValoresX")==null?"0":request.getParameter("ValoresX");
	valoresX = valoresX.replaceAll(",", ""); 
	
	String arreglo[] = valoresX.split("-");
	float suma = 0;
	float media = 0;
	for(String arr : arreglo){
		suma = suma+Float.valueOf(arr);
	}
	media = suma/arreglo.length;
	
	float n = arreglo.length-1;
	
	float varX = 0;
	float sumaValores = 0;	
	for(String arr : arreglo){
		varX = Float.valueOf(arr) - media;
		varX = varX*varX;
		sumaValores +=varX;
	}
	
	float varianza = sumaValores/n;	
%>
<body>
<div class="container-fluid">
	<h2>
		Cálculo de varianza<small class="text-muted">(Jazer Torres)</small>
	</h2>
	<form name="frmCalcular" action="varianza" method="post">
		<div class="alert alert-info">
			<a href="empleado" class="btn btn-primary">Regresar</a>
		</div>
		<div class="form-group">	  				
			<label for="ValoresX">Valores de la variable x<small class="text-muted">(Separados por guion, ejemplo: 1,400-1,300-1,200 etc...)</small></label>    				
			<input type="Text" class="form-control" name="ValoresX" aria-describedby="ValoresX" placeholder="Valores de X" value="<%=valoresX%>" maxsize="100">
		</div>
		<div class="alert alert-success">
			<a href="javascript:Calcular()" class="btn btn-primary">Calcular</a>
		</div>
		<div class="form-group">	  				
			<label for="Media">Media</label>    				
			<input type="Text" class="form-control" name="Media" aria-describedby="Media"  value="<%=media%>" maxsize="10" readonly>
		</div>
		<div class="form-group">	  				
			<label for="Observaciones">Observaciones <small class="text-muted">(n)</small></label>    				
			<input type="Text" class="form-control" name="Observaciones" aria-describedby="Observaciones"  value="<%=arreglo.length%>" maxsize="10" readonly>
		</div>
		<div class="form-group">	  				
			<label for="Var">Varianza</label>    				
			<input type="Text" class="form-control" name="Var" aria-describedby="Var"  value="<%=varianza%>" maxsize="20" readonly>
		</div>
	</form>
	<hr>
<%		
	
%>
</div>

</body>
<script>
	function Calcular(){		
		document.frmCalcular.submit();
	}		
</script>