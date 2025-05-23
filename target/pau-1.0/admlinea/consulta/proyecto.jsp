<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<head>		
</head>
<%	
	String numUno 		= (String)request.getAttribute("numUno");
	String numDos 		= (String)request.getAttribute("numDos");
	String num1 		= request.getParameter("Num1")==null?"0":request.getParameter("Num1");
	String num2 		= request.getParameter("Num2")==null?"0":request.getParameter("Num2");
	String num3 		= request.getParameter("Num3")==null?"0":request.getParameter("Num3");
	String num4 		= request.getParameter("Num4")==null?"0":request.getParameter("Num4");
	String num5 		= request.getParameter("Num5")==null?"0":request.getParameter("Num5");
	float resultado 	= (float)request.getAttribute("resultado");
%>
<body>
<div class="container-fluid">
	<h2>Proyecto</h2>
	<form name="frmOperacion" action="proyecto" method="post">
	<input type="hidden" name="Accion"/>
	<div class="alert alert-info">
		<input type="text" name="NumUno" value="<%=numUno%>" size="5"/>&nbsp;
		<input type="text" name="NumDos" value="<%=numDos%>" size="5"/>
		<a href="javascript:Sumar()" class="btn btn-primary btn-sm">+</a>
		<a href="javascript:Multiplicar()" class="btn btn-primary btn-sm">*</a>
		<input type="text" name="Resultado" value="<%=resultado%>" size="5"/>
	</div>  
	<div class="alert alert-secondary">
		<input type="text" name="Num1" value="<%=num1%>" size="5"/>&nbsp;
		<input type="text" name="Num2" value="<%=num2%>" size="5"/>&nbsp;		
		<input type="text" name="Num3" value="<%=num3%>" size="5"/>&nbsp;
		<input type="text" name="Num4" value="<%=num4%>" size="5"/>&nbsp;
		<input type="text" name="Num5" value="<%=num5%>" size="5"/>
		<a href="javascript:BuscaMayor()" class="btn btn-success btn-sm">></a>		
	</div>
	</form>  
</div>
</body>
<script type="text/javascript">
	function Sumar(){ 		
		document.frmOperacion.Accion.value="1";
		document.frmOperacion.submit();  		
	}
	
	function Multiplicar(){
		document.frmOperacion.Accion.value="2";
		document.frmOperacion.submit();  		
	}
	
	function BuscaMayor(){
		document.frmOperacion.Accion.value="3";
		document.frmOperacion.submit();  		
	}
</script>
</html>