<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<body>
	<div class="container-fluid">
		<h1>Reportes de reprobados</h1>
		<div class="row-fluid">
			<ul class="list-group">
				<li class="list-group-item"><a href="listado"><i class="fas fa-arrow-circle-right"></i>Estadística por alumno</a></li>
				<li class="list-group-item"><a href="maestros"><i class="fas fa-arrow-circle-right"></i>Estadística por maestro</a></li>
				<li class="list-group-item"><a href="materias"><i class="fas fa-arrow-circle-right"></i>Estadística por materias</a></li>
				<li class="list-group-item"><a href="listadoDetalle"><i class="fas fa-arrow-circle-right"></i>Materias del alumno</a></li>
				<li class="list-group-item"><a href="reprobones"><i class="fas fa-arrow-circle-right"></i>Reprobados reincidentes</a></li>
				<li class="list-group-item"><a href="facultades"><i class="fas fa-arrow-circle-right"></i>Reprobados por Facultad</a></li> 
			</ul>
		</div>
	</div>
</body>
<style>
	body{
		background:white;
	}
</style>
</html>
