<!-- bootstrap -->  
	<link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css" type="text/css" media="screen" />
	<script type="text/javascript" src="../../js/jquery-1.5.1.min.js"></script>		
<!-- end bootstrap -->
<style>
	body{
		background:#fff;
		font-size: 8pt;
	}
	
	TABLE  {
		font-size: 7pt;
	}
	TABLE.tabla  {
		font-size: 7pt;
	}
	.encabezado{
		font-size:10px;
	}
	.encabezado2{
		font-size:12px;
	}
	td {
		font-size: 7pt;
		font-size:11px;
	}
	.encabezadoV {
		font-size: 7pt;
		font-size:11px;
	}
	th2 {
		font-size: 7pt;
		font-size:11px;
	}
	TH{
		font-size: 7pt;
	}
</style>
<style>
<%
	/*
	String currentColor = session.getAttribute("colorTablas").equals("")?"default":(String)session.getAttribute("colorTablas");
	if(!currentColor.equals("default")){
		out.print(".nav a{color:"+colorAlum.modificarColor(currentColor, 20)+";}.nav a:hover{color:"+currentColor+";}");
	}
	*/
%>
</style>
<ul class="nav nav-tabs" style="margin-bottom:0;background: #E6E6E6;">
	<li class="Hijos oculto"><a href="../padre/hijos">Children</a></li>
	<li class="Datos"><a href="../padre/datos.jsp"><spring:message code="aca.Datos"/></a></li>	
	<li class="Finanzas oculto"><a href="../padre/financiero.jsp">Finances</a></li>
	<li class="Materias oculto"><a href="../padre/materias">Subjects</a></li>	  
</ul>