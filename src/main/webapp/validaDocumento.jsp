<%@ page import= "java.util.List"%>
<%@ page import= "aca.valida.spring.ValDocumento"%>
<%@ page import= "aca.diploma.spring.DipCurso"%>
<%@ page import= "aca.diploma.spring.DipAlumno"%>
<%@include file= "idioma.jsp" %>
<%@ include file= "head.jsp"%>
<style>
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
<%	
	String id					= (String) request.getParameter("Id")==null?"0":request.getParameter("Id");
	ValDocumento valDocumento	= (ValDocumento)request.getAttribute("valDocumento");
	DipCurso dipCurso			= (DipCurso)request.getAttribute("dipCurso");
	DipAlumno dipAlumno			= (DipAlumno)request.getAttribute("dipAlumno");
	boolean existe				= (boolean)request.getAttribute("existe");
%>
<body>
<div class="container-fluid">	
	<img src="imagenes/logoUniv.jpg" width="700px;" style="margin: 0 auto; display:block;">
<!-- 	<h2>UNIVERSIDAD DE MONTEMORELOS, A.C.</h2> -->
	<hr>
<%	if (existe && valDocumento.getTipo().equals("Diploma")){ %>
	<div class="alert alert-info">Certificamos que este documento ha sido elaborado en nuestra institución...</div>
	<table class="table table-sm table-borderless" style="width:50%;">
	<tbody>
		<tr><th class="text-star">Tipo</th></tr>
		<tr><td class="text-star">Diploma</td></tr>
		<tr><th class="text-star">Folio</th></tr>
		<tr><td class="text-star"><%=valDocumento.getClave()%></td></tr>				
		<tr><th class="text-star">Participante</th></tr>
		<tr><td class="text-star"><%=dipAlumno.getNombre()%></td></tr>
		<tr><th class="text-star">Curso</th></tr>
		<tr><td class="text-star"><%=dipCurso.getCurso()%></td></tr>
		<tr><th class="text-star">Tema</th></tr>
		<tr><td class="text-star"><%=dipCurso.getTema()%></td></tr>
		<tr><th class="text-star">Horas</th></tr>
		<tr><td class="text-star"><%=dipCurso.getHoras()%></td></tr>
		<tr><th class="text-star">Lugar y Fecha</th></tr>
		<tr><td class="text-star"><%=dipCurso.getFecha()%></td></tr>
	</tbody>	
	</table>
<%	}else{
		out.print("¡No existe información para el folio solicitado!");
	}
%>	
	<hr>
</div>
<div class="contenedor-div">
	<img src="imagenes/um3.png" class="Llama" width="400px">
</div>
</body>