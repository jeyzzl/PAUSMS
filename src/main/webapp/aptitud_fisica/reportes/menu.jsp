<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<body>
<div class="container-fluid">
	<h2>Reportes</h2>
	<div class="row-fluid">
		<div class="alert alert-info">
           	<ul class="list-group list-group-flush" style="width:520px">
           		<li class="list-group-item"><a href="porMateria"><i class="fas fa-arrow-circle-right"></i> Inscritos por Materia</a></li>
           		<li class="list-group-item"><a href="registrados"><i class="fas fa-arrow-circle-right"></i> Registrados por Materia</a></li>
          	  	<li class="list-group-item"><a href="sinRegistro"><i class="fas fa-arrow-circle-right"></i> Alumnos sin Registro</a></li>
           		<li class="list-group-item"><a href="porPeriodo"><i class="fas fa-arrow-circle-right"></i> Alumnos por Periodo</a></li>
           	</ul>
       	</div>
	</div>
</div>
</body>
</html>
<style>
	body{
		background:white;
	}
</style>