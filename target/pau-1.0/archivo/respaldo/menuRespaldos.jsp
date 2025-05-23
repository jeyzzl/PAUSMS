<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<style>
	.procesando, .completado, .error{
		display:none;
	}
</style>
<div class="container-fluid">
	<h2>Backups</h2>
	<div class="row-fluid">
		<ul class="list-group">				
		<li class="list-group-item"><a href="respaldoDocAlumno"><i class="fas fa-arrow-circle-right"></i> Digital documents</a></li>
		<li class="list-group-item"><a href="respaldoGeneral"><i class="fas fa-arrow-circle-right"></i> Unclassified documents</a></li>
		<li class="list-group-item"><a href="respaldoAlumnos"><i class="fas fa-arrow-circle-right"></i> Student files</a></li>
		<li class="list-group-item"><a href="respaldoMaestros"><i class="fas fa-arrow-circle-right"></i> Teacher files</a></li>
		<li class="list-group-item"><a href="admArchivos"><i class="fas fa-arrow-circle-right"></i> Admission files</a></li>
		<li class="list-group-item"><a href="admAlumnos"><i class="fas fa-arrow-circle-right"></i> Student Admission Documents</a></li>
		</ul>
	</div>
</div>