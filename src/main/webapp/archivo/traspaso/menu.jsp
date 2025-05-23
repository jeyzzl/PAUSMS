<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<style>
	.procesando, .completado, .error{
		display:none;
	}
</style>
<html>
<body>
	<div class="container-fluid">
		<h1>Traspasos de datos e imágenes</h1>
		<div class="row-fluid">
		 	<ul class="list-group">		
				<li class="list-group-item"><a href="traspaso"><i class="fas fa-arrow-circle-right"></i>Documentos Alumno</a></li>
				<li class="list-group-item"><a href="traspasoGeneral"><i class="fas fa-arrow-circle-right"></i>Documentos sin clasificar</a></li>
				<li class="list-group-item"><a href="archivosAlumno"><i class="fas fa-arrow-circle-right"></i>Archivos Alumno</a></li>
				<li class="list-group-item"><a href="archivosProfesor"><i class="fas fa-arrow-circle-right"></i>Archivos Maestro</a></li>
				<li class="list-group-item"><a href="admArchivos"><i class="fas fa-arrow-circle-right"></i>Admision Archivos</a></li>
				<li class="list-group-item"><a href="admAlumnos"><i class="fas fa-arrow-circle-right"></i>Admision Doc. Alumnos</a></li>
			</ul>
		</div>
	</div>
</body>
</html>