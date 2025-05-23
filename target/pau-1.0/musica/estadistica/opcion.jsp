<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<div class="container-fluid">
<h1>Estadisticas</h1>
<body>
	<table style="width:20%"  class="table">
		<tr>			
			<td align="left">
	    	 <li><a href="alumnos">Lista de Alumnos</a>&nbsp;
	     	</td>
		</tr>
		<tr>			
			<td align="left">
	    	 <li><a href="inscrito">Alumnos Inscritos</a>&nbsp; 
	     	</td>
		</tr>
		<tr>
			<td>
	     	  <li><a href="pendientes">Alumnos Pendientes</a>&nbsp;
	     	</td>
		</tr>
		<tr>
		<tr>
			<td>
	     	  <li><a href="contado">Alumnos de Contado</a>&nbsp;
	     	</td>
		</tr>
		</tr>
		<tr>
			<td>
	     	  <li><a href="pagare">Alumnos con Pagare</a>&nbsp;
	     	</td>
		</tr>
		<tr>
			<td>
	     	  <li><a href="cobros">Cobros a Instituciones</a>&nbsp;
	     	</td>
		</tr>
		<tr>
			<td>
	     	  <li><a href="alumnosProfesor">Alumnos por profesor</a>&nbsp;
	     	</td>
		</tr>
	</table>		
</body>
<%@ include file= "../../cierra_enoc.jsp" %>