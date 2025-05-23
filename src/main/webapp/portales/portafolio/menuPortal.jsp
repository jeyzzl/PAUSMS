<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #eeeeee;">
  <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item" style="border-left: 2px solid white;">
        <a class="nav-link <%=porMenu.equals("1")?"active":""%>" href="datos">Datos</a>
      </li>
      <li class="nav-item" style="border-left: 2px solid white; border-right: 2px solid white;">
        <a class="nav-link <%=porMenu.equals("2")?"active":""%>" href="compromiso">Compromiso</a>
      </li>
    </ul>
  </div>    
</nav>      
<!-- <ul class="nav nav-tabs"> -->
<!--  <li class="periodo"><a href="periodo">Periodo</a></li> -->	
<!-- 	<li class="documentos"><a href="documentos"><spring:message code="aca.Documento"/></a></li> -->
<!-- 	<li class="puesto"><a href="puestoFront">Puesto</a></li> -->
<!-- 	<li class="cosmovision"><a href="cosmovision">Cosmovisión</a></li> -->
<!-- 	<li class="actividades"><a href="actividades">Proyectos</a></li> -->
<!--  <li class="evaluacion"><a href="evaluacion">Evaluación</a></li>-->
<!-- 	<li class="evaluacion"><a href="evaluacion">Evaluaciones</a></li>	  -->
<!-- </ul> -->