<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.*"%>
<%@ page import="aca.mentores.spring.MentPregunta"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="../alumno/menu.jsp"%>

<head>
</head>
<%
	String encuestaActiva 	= (String) request.getAttribute("encuestaActiva");

	List<MentPregunta> preguntasEncuesta 	= (List<MentPregunta>) request.getAttribute("preguntasEncuesta");
	
	int cont = 1;
%>
<div class="container-fluid">	
	<div class="alert alert-info">		
		<h2><a href="resumen"><i class="fas fa-arrow-left"></i></a> Programa Institucional de Tutoría</h2>
	</div>
	<div class="alert alert-success">		
	<h3>
		Anualmente la Universidad de Montemorelos evalúa el Programa Institucional de Tutoría, el cual existe para brindarte apoyo y para que tengas éxito en tu proyecto profesional.
		Por tal motivo, la evaluación que realices es de gran peso e importancia.
	</h3>
	<h4>
		<strong>
			Para declarar cuán IMPORTANTE y cuán SATISFECHO estás en cada una de las siguientes declaraciones, guíate en una escala de Lickert del 1 al 5 de acuerdo a los valores que abajo están enlistados.
		</strong><br>
	</h4>
	<h5>1 = NADA IMPORTANTE / NADA SATISFECHO</h5>
	<h5>2 = POCO IMPORTANTE / POCO SATISFECHO</h5>
	<h5>3 = MEDIANAMENTE IMPORTANTE / MEDIANAMENTE SATISFECHO</h5>
	<h5>4 = IMPORTANTE / SATISFECHO</h5>
	<h5>5 = MUY IMPORTANTE / MUY SATISFECHO</h5>
	</div>
	<form action="grabarEncuestaMent" method="post">
		<input type="hidden" name="EncuestaId" value="<%=encuestaActiva%>">
<% 		for(MentPregunta pregunta : preguntasEncuesta){%>
		<div class="row">
		    <h3 class="card-title"><%=cont++%>. <%=pregunta.getNombre()%></h3>
			<div class="col-6">
			    <h5 class="card-title">Importancia</h5>
				<div class="card border-dark">
				  	<div class="card-body">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="Imp<%=pregunta.getPreguntaId()%>" value="1" checked="checked">
						  	<label class="form-check-label">1</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" name="Imp<%=pregunta.getPreguntaId()%>" value="2">
						  	<label class="form-check-label">2</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" name="Imp<%=pregunta.getPreguntaId()%>" value="3">
						  	<label class="form-check-label">3</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" name="Imp<%=pregunta.getPreguntaId()%>" value="4">
						  	<label class="form-check-label">4</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" name="Imp<%=pregunta.getPreguntaId()%>" value="5">
						  	<label class="form-check-label">5</label>
						</div>
				  	</div>
				</div>
		  	</div>
			<div class="col-6">
			    <h5 class="card-title">Satisfacción</h5>
				<div class="card border-dark">
				  	<div class="card-body">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="Sat<%=pregunta.getPreguntaId()%>" value="1" checked="checked">
						  	<label class="form-check-label">1</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" name="Sat<%=pregunta.getPreguntaId()%>" value="2">
						  	<label class="form-check-label">2</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" name="Sat<%=pregunta.getPreguntaId()%>" value="3">
						  	<label class="form-check-label">3</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" name="Sat<%=pregunta.getPreguntaId()%>" value="4">
						  	<label class="form-check-label">4</label>
						</div>
						<div class="form-check form-check-inline">
						  	<input class="form-check-input" type="radio" name="Sat<%=pregunta.getPreguntaId()%>" value="5">
						  	<label class="form-check-label">5</label>
						</div>
				  	</div>
				</div>
		  	</div>
		</div>
		<br>
<% 		}%>
		<button class="btn btn-primary" onclick="valida();">Grabar</button>
	</form>
</div>
	<script type="text/javascript">
// 		function valida(){
// 			var radio = document.querySelector('input[type=radio][name=Interno]:checked');
// 	    	radio.checked = false;
// 	    }
	</script>
</body>