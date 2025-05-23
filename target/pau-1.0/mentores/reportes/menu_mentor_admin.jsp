<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>

<%
	String   sModulo		    = request.getParameter("moduloId"); 
    String   sCarpeta     		= request.getParameter("carpeta");
%>

<html>
<body>
	<div class="container-fluid">
		<h1>Reporte de Mentoria</h1>
		<div class="row-fluid">
          <div class="well well-small">
            <ul class="nav nav-list">
              <li><a href="rep_mentor_facultad.jsp"><i class="fas fa-arrow-circle-right"></i>Tutor por Facultad</a></li>       
              <li><a href="rep_alum_ment_facu.jsp"><i class="fas fa-arrow-circle-right"></i>Facultad, Tutor y Alumno</a></li>    
              <li><a href="rep_entrevista.jsp"><i class="fas fa-arrow-circle-right"></i>Entrevistas de Tutores</a></li>    
              <li><a href="est_perfil"><i class="fas fa-arrow-circle-right"></i>Estad&iacute;stica del Perfil del Estudiante por Carreras</a></li>
              <li><a href="est_perfil_ment"><i class="fas fa-arrow-circle-right"></i>Listado de Perfil de Ingreso por Mentores</a></li>
              <li><a href="rep_alum_sin_mentor.jsp"><i class="fas fa-arrow-circle-right"></i>Alumnos sin Mentor</a></li>
              <li><a href="mentor_perfil"><i class="fas fa-arrow-circle-right"></i>Alumnos por Tutor</a></li>
              <li><a href="mentor_cambio.jsp"><i class="fas fa-arrow-circle-right"></i>Alumnos con Cambio de Mentor</a></li>
              <li><a href="listDirecciones.jsp"><i class="fas fa-arrow-circle-right"></i>Listado de direcciones</a></li>
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