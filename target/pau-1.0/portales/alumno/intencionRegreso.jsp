<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.*"%>
<%@ page import="aca.encuesta.spring.EncPeriodoRes"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="../alumno/menu.jsp"%>

<head>
</head>
<%
	EncPeriodoRes encPeriodoRes 	= (EncPeriodoRes) request.getAttribute("encPeriodoRes");
%>
<div class="container-fluid">	
	<div class="alert alert-info">		
		<h2><a href="resumen"><i class="fas fa-arrow-left"></i></a> Encuesta sobre tu intención de regreso</h2>
	</div>
	<form action="grabarIntencion" method="post">
		<input type="hidden" name="PeriodoId" value="<%=encPeriodoRes.getPeriodoId()%>">
		<div class="row">
			<div class="col-6">
			    <h5 class="card-title">¿Autofinanciaste parcialmente tu colegiatura este semestre?</h5>
				<div class="card border-dark">
				  	<div class="card-body">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="FinAlumno" value="S">
						  	<label class="form-check-label">Si</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" name="FinAlumno" value="N">
						  	<label class="form-check-label">No</label>
						</div>
						<div class="form-check form-check-inline">
							<div class="row">
	   							<label class="col-sm-5 col-form-label">¿qué porcentaje?</label>
							    <div class="col-sm-3">
						      		<input type="text" class="form-control" name="FinPor">
							    </div>
						  	</div>
						</div>
						<label class="col-sm-5 col-form-label">Mediante cuál actividad...</label>
						<br>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="checkbox" name="FinColpor" value="S">
						  	<label class="form-check-label">Colportaje</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="checkbox" name="FinTrabajo" value="S">
						  	<label class="form-check-label">Trabajo UM</label>
						</div>
					  	<div class="row">
   							<label class="col-sm-3 col-form-label">Otro (especifica):</label>
						    <div class="col-sm-9">
					      		<input type="text" class="form-control" name="FinOtro">
						    </div>
					  	</div>
				  	</div>
				</div>
			    <h5 class="card-title">¿Tienes planes de regresar en agosto la Universidad de Montemorelos?</h5>
				<div class="card border-dark">
				  	<div class="card-body">
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" name="Regresar" value="S">
						  	<label class="form-check-label">Si</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" name="Regresar" value="N">
						  	<label class="form-check-label">No</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" name="Regresar" value="I">
						  	<label class="form-check-label">No estoy seguro</label>
						</div>
						<br>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="checkbox" name="Practicas" value="S">
						  	<label class="form-check-label">Voy a prácticas/servicio S.</label>
						</div>
				  	</div>
				</div>
			    <h5 class="card-title">Al regresar ¿cómo viviras?</h5>
				<div class="card border-dark">
				  	<div class="card-body">
				  		<strong>Interno</strong>
				  		<br>
				  		<label class="form-check-label">En el dormitorio</label>
				  		<br>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" onclick="quitarExterno();" id="Interno" name="Interno" value="1">
						  	<label class="form-check-label">1</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" onclick="quitarExterno();" id="Interno" name="Interno" value="2">
						  	<label class="form-check-label">2</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" onclick="quitarExterno();" id="Interno" name="Interno" value="3">
						  	<label class="form-check-label">3</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" onclick="quitarExterno();" id="Interno" name="Interno" value="4">
						  	<label class="form-check-label">4</label>
						</div>
				  	</div>
				</div>
				<div class="card border-dark">
				  	<div class="card-body">
				  		<strong>Externo</strong>
				  		<br>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" onclick="quitarInterno();" id="Externo" name="Externo" value="P">
						  	<label class="form-check-label">Padres</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" onclick="quitarInterno();" id="Externo" name="Externo" value="F">
						  	<label class="form-check-label">Familiares</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" onclick="quitarInterno();" id="Externo" name="Externo" value="E">
						  	<label class="form-check-label">Empleado</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" onclick="quitarInterno();" id="Externo" name="Externo" value="C">
						  	<label class="form-check-label">Cónyuge</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" onclick="quitarInterno();" id="Externo" name="Externo" value="S">
						  	<label class="form-check-label">Sólo (Mayor de edad)</label>
						</div>
				  	</div>
				</div>
			</div>
			<div class="col-6">
			    <h5 class="card-title">¿Qué obstáculos tienes que dificultan tu regreso a la Universidad?</h5>
				<div class="card border-dark">
				  	<div class="card-body">
				  		<strong>Financiero</strong>
				  		<br>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="checkbox" name="ObsSaldo" value="S">
						  	<label class="form-check-label">Saldos pendientes</label>
						</div>
						<br>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="checkbox" name="ObsFin" value="S">
						  	<label class="form-check-label">Financiamiento para el próximo semestre</label>
						</div>
				  	</div>
				</div>
				<div class="card border-dark">
				  	<div class="card-body">
				  		<strong>Académico</strong>
				  		<br>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="checkbox" name="ObsMat" value="S">
						  	<label class="form-check-label">Materias pendientes</label>
						</div>
						<br>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="checkbox" name="ObsDoc" value="S">
						  	<label class="form-check-label">Documentos pendientes</label>
						</div>
				  	</div>
				</div>
				<div class="card border-dark">
				  	<div class="card-body">
				  		<strong>Personal</strong>
				  		<br>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="checkbox" name="ObsSalud" value="S">
						  	<label class="form-check-label">Salud</label>
						</div>
						<br>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="checkbox" name="ObsAdaptacion" value="S">
						  	<label class="form-check-label">Adaptación</label>
						</div>
						<br>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="checkbox" name="ObsFamiliar" value="S">
						  	<label class="form-check-label">Familiares</label>
						</div>
				  	</div>
				</div>
			 	<h5 class="card-title">¿Qué planes tienes para estas vacaciones?</h5>
				<div class="card border-dark">
				  	<div class="card-body">
				  		<strong>Estudiar</strong>
				  		<br>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="checkbox" name="PlanEstudiar" value="S">
						  	<label class="form-check-label">En la UM</label>
						</div>
						<div class="row">
   							<label class="col-sm-3 col-form-label">Otro:</label>
						    <div class="col-sm-9">
					      		<input type="text" class="form-control" name="PlanOtroEst">
						    </div>
					  	</div>
						<br>
						<strong>Trabajar</strong>
				  		<br>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="checkbox" name="PlanTrabajo" value="S">
						  	<label class="form-check-label">En la UM</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="checkbox" name="PlanColportar" value="S">
						  	<label class="form-check-label">Colportar</label>
						</div>
						<div class="row">
   							<label class="col-sm-3 col-form-label">Otro:</label>
						    <div class="col-sm-9">
					      		<input type="text" class="form-control" name="PlanOtroTrabajo">
						    </div>
					  	</div>
				  		<br>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="checkbox" name="PlanDescansar" value="S">
						  	<label class="form-check-label">Descansar</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="checkbox" name="PlanNinguno" value="S">
						  	<label class="form-check-label">Ninguno</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="checkbox" name="Orientacion" value="S">
						  	<label class="form-check-label">Deseo orientación</label>
						</div>
				  	</div>
				</div>
			</div>
		</div>
		<br>
		<button class="btn btn-primary" type="submit">Grabar</button>
	</form>
</div>
	<script type="text/javascript">
		function quitarInterno(){
			var radio = document.querySelector('input[type=radio][name=Interno]:checked');
	    	radio.checked = false;
	    }
	
		function quitarExterno(){
			var radio = document.querySelector('input[type=radio][name=Externo]:checked');
	    	radio.checked = false;
	    }
	</script>
</body>