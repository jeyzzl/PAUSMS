<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<body>
<div class="container-fluid">
	<h1>Reporte de Mentoria</h1>
	<div class="row-fluid">
           <ul class="list-group">    
             <li class="list-group-item"><a href="rep_mentor_facultad"><i class="fas fa-arrow-circle-right"></i><spring:message code="aca.Tutor"/> por Facultad</a></li>
             <li class="list-group-item"><a href="facultad?Opcion=1"><i class="fas fa-arrow-circle-right"></i>Facultad, Tutor y Alumno</a></li>    
             <li class="list-group-item"><a href="facultad?Opcion=2"><i class="fas fa-arrow-circle-right"></i>Entrevistas de Tutores</a></li>              
             <li class="list-group-item"><a href="facultad?Opcion=3"><i class="fas fa-arrow-circle-right"></i>Alumnos sin Mentor</a></li>
             <li class="list-group-item"><a href="mentor_cambio"><i class="fas fa-arrow-circle-right"></i>Alumnos con Cambio de Mentor</a></li>
             <li class="list-group-item"><a href="listDirecciones"><i class="fas fa-arrow-circle-right"></i>Listado de direcciones</a></li>
             <li class="list-group-item"><a href="cargas"><i class="fas fa-arrow-circle-right"></i>Elige las cargas</a></li>
             <li class="list-group-item"><a href="modalidades"><i class="fas fa-arrow-circle-right"></i>Elige las modalidades</a></li>
             <li class="list-group-item"><a href="perfilAlumnos"><i class="fas fa-arrow-circle-right"></i>Perfil de alumnos</a></li>
             <li class="list-group-item"><a href="perfilReprobados"><i class="fas fa-arrow-circle-right"></i>Perfil de reprobados</a></li>
             <li class="list-group-item"><a href="perfilMaterias"><i class="fas fa-arrow-circle-right"></i>Perfil/Materias reprobadas</a></li>
             <li class="list-group-item"><a href="historico"><i class="fas fa-arrow-circle-right"></i>Mentores y aconsejados</a></li>
           </ul>
	</div>
</div>
</body>
</html>
<style>
	body{
		background:white;
	}
</style>