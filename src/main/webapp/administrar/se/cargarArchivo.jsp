<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
	String grabados 	= request.getParameter("Grabados")==null?"0":request.getParameter("Grabados");
	String errores	 	= request.getParameter("Errores")==null?"0":request.getParameter("Errores");
%>
<div class="container-fluid">
	<h2>Subir archivo <small class="text-muted h4">Excel</small></h2>
	<hr>
	<a href="estadistica" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a><br><br>
	<div id="divGrabo" class="alert alert-success d-flex align-items-center" role="alert">Grabados:<%=grabados%> &nbsp; Errores:<%=errores%></div>
	<h4>Estructura del archivo</h4>
	<table class="table table-sm table-bordered">
		<thead>
			<tr>
				<th>&nbsp;</th>
				<th>A</th>
				<th>B</th>
				<th>C</th>
				<th>D</th>
				<th>E</th>
				<th>F</th>
				<th>G</th>
				<th>H</th>
				<th>I</th>	
				<th>J</th>
				<th>K</th>
				<th>L</th>
				<th>M</th>
				<th>N</th>
				<th>O</th>
				<th>P</th>
				<th>Q</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><b>Dato</b></td>					
				<td>Plantel</td>
				<td>Plan SE</td>
				<td>Ciclo</td>				
				<td>Curp</td>
				<td>Nombre</td>
				<td>Ape. Paterno</td>
				<td>Ape. Materno</td>
				<td>Fecha</td>
				<td>Código personal</td>
				<td>Plan UM</td>
				<td>Genero</td>
				<td>Edad</td>
				<td>Grado</td>
				<td>País id</td>
				<td>Estado id</td>
				<td>Preparatoria</td>
				<td>Usado</td>
			</tr>
			<tr>
				<td><b>Formato</b></td>
				<td>Texto</td>
				<td>Texto</td>
				<td>Número</td>
				<td>Texto</td>
				<td>Texto</td>				
				<td>Texto</td>
				<td>Texto</td>
				<td>DD/MM/YYYY</td>
				<td>Número</td>
				<td>Texto</td>
				<td>Texto</td>
				<td>Número</td>
				<td>Número</td>
				<td>Número</td>
				<td>Número</td>
				<td>Número</td>
				<td>Texto</td>
			</tr>
			<tr>
				<td><b>Ejemplo</b></td>
				<td>PLANTEL 1</td>
				<td>NUTR2010</td>
				<td>5</td>
				<td>MEJY940924MCCZSL03</td>
				<td>YALEDI</td>
				<td>MEZQUITA</td>
				<td>JOSE</td>
				<td>19/09/2020</td>
				<td>1130222</td>				
				<td>NUTR2010</td>
				<td>M</td>
				<td>22</td>
				<td>3</td>
				<td>91</td>
				<td>4</td>
				<td>0</td>
				<td>N</td>
			</tr>
		</tbody>
	</table>
	<form name="frmArchivo" enctype="multipart/form-data" action="leerExcel" method="post">
		<p><input type="file" accept=".xls,.xlsx" id="archivo" name="archivo" /><br></p>
		<div class="alert alert-info d-flex align-items-center">	
			<button class="btn btn-primary btn-large"> Subir</button>
		</div>		
	</form>
</div>