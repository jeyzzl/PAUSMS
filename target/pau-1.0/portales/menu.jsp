<%
	String codigoPersonal	= (String)session.getAttribute("codigoPersonal");
	String codigoAlumno		= (String)session.getAttribute("codigoAlumno");	
%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">  
	<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
    	<span class="navbar-toggler-icon"></span>
  	</button>
  	<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
    	<div class="navbar-nav">
      		<a class="nav-item nav-link active" href="../alumno/resumen">Main Menu</a>
      		<a class="nav-item nav-link" href="../alumno/datos">Data</a>
      		<a class="nav-item nav-link" href="../alumno/materias">Subjects</a>
      		<a class="nav-item nav-link" href="../alumno/calificaciones">Notes</a>
      		<a class="nav-item nav-link" href="../alumno/financiero">Apoint. Status</a>            
      		<a class="nav-item nav-link" href="../alumno/documentos">Docs.</a>
      		<a class="nav-item nav-link" href="../alumno/tramites">Procedures</a>
      		<a class="nav-item nav-link" href="../alumno/servicio">Soc.Ser.</a>
      		<a class="nav-item nav-link" href="../alumno/disciplina">Behavior</a>
      		<a class="nav-item nav-link" href="../alumno/avance">Graduates</a>
      		<a class="nav-item nav-link" href="../alumno/cumple">Birthday</a>
      		<a class="nav-item nav-link" href="../alumno/convalidacion">Validates</a>
      		<a class="nav-item nav-link" href="../alumno/validacion">Enrr.</a>
    	</div>
	</div>
</nav>

<!-- <ul class="nav nav-tabs" style="margin-bottom:0;background: #E6E6E6;"> -->
<!-- 	<li class="inicio oculto"><a href="../alumno/resumen">Inicio</a></li> -->
<!-- 	<li class="datos oculto"><a href="../alumno/datos"><spring:message code="aca.Datos"/></a></li> -->
<!-- 	<li class="materias oculto"><a href="../alumno/materias">Materias</a></li>		 -->
<!-- 	<li class="notas oculto"><a href="../alumno/calificaciones">Notas</a></li> -->
<!-- 	<li class="edoCuenta oculto"><a href="../alumno/financiero">Edo Cuenta</a></li> -->
<!-- 	<li class="doc oculto"><a href="../alumno/documentos">Docs</a></li> -->
<!-- 	<li class="tareas oculto"><a href="../alumno/tramites">Tramites</a></li> -->
<!-- 	<li class="servSocial oculto"><a href="../alumno/servicio">Serv. Social</a></li> -->
<!-- 	<li class="disciplina oculto"><a href="../alumno/disciplina.jsp"><spring:message code="aca.Disciplina"/></a></li> -->
<!-- 	<li class="gradua oculto"><a href="../alumno/avance">Gradua</a></li> -->
<!-- 	<li class="cumple oculto"><a href="../alumno/cumple">Cumple</a></li> -->
<!-- 	<li class="convalidacion oculto"><a href="../alumno/convalidacion">Convalidación</a></li> -->
<!-- 	<li class="inscripcion oculto"><a href="../inscEnlinea/validacion">Inscripción</a></li> -->
<!-- </ul> -->
