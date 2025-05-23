<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alerta.spring.AlertaCovid"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.catalogo.spring.CatPais"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<html>
<head>&nbsp;</head>	
<% 	
	AlertaCovid alertaCovid	= (AlertaCovid)request.getAttribute("alertaCovid");
	AlumPersonal alumno		= (AlumPersonal)request.getAttribute("alumno");
	String nombreFacultad	= (String)request.getAttribute("nombreFacultad");
	String nombreCarrera	= (String)request.getAttribute("nombreCarrera");
	String mensaje			= (String)request.getAttribute("mensaje");
	
	List<CatPais> listaPais		= (List<CatPais>)request.getAttribute("listaPais");
%>
<body>
<div class="container-fluid">
	<h2 align="center">Cuestionario de identificación de factores de riesgo en viajeros<br>
		Questionnaire of identification of risk factors in travelers</h2>
	<hr>	
<%	if (mensaje.equals("1")){%>
	<div class="alert alert-success">Guardado</div>
<% 	}%>	
	<form name="frmAlerta" action="grabarCovid" method="post">
		<div class="row" align="left" style="margin: 10px 10px 0 10px;">
	 		<label class="col-sm-3"><strong>No. Matrícula: </strong><%=alumno.getCodigoPersonal()%></label>
	 		<label class="col-sm-3"><strong>Facultad: </strong><%=nombreFacultad%></label>
	 		<label class="col-sm-3"><strong>Carrera: </strong><%=nombreCarrera%></label>
	 		<label class="col-sm-3"><strong>Teléfono: </strong><%=alumno.getTelefono()%></label>
		</div><br>
		<hr>	
		<div class="row" align="left" style="margin: 10px 10px 0 10px;">
	 		<label class="col-sm-3"><strong>Nombre: </strong><%=alumno.getNombre()%></label>
	 		<label class="col-sm-3"><strong>Apellido: </strong><%=alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno()%></label>
	 		<label class="col-sm-3"><strong>Correo electrónico: </strong><%=alumno.getEmail()%></label>
		</div>
		<hr>	
		<h4 align="left">Estimado estudiante:<br>
			Dear student:<br><br>
			Debido a la pandemia, favor de completar el siguiente custionario: [Due to the pandemic, please fill out the following
			questionnaire]:
		</h4><br><br>
		<input name="CodigoPersonal" type="hidden" value="<%=alumno.getCodigoPersonal()%>">
		<div class="alert alert-info">
			<h4>1. ¿Qué países o ciudades has visitado en los últimos 14 días? [What countries or cities have you visited in the last 14 days?]</h4>
		</div>
		<div class="row" style="margin: 10px 10px 0 10px;">
			<div class="span12">	
				<fieldset class="col-sm-4">
					<label><b>País / Country</b></label>											
					<select name="PaisUno">
<% 					for(CatPais pais : listaPais){%>
						<option value="<%=pais.getPaisId()%>" <%=alertaCovid.getPaisUno().equals(pais.getPaisId()) ? "selected" : ""%>><%=pais.getNombrePais()%></option>
<% 					}%>
					</select>
				</fieldset>
				<fieldset class="col-sm-4">
					<label ><b>Ciudad / City</b></label>				
					<input type="text" name="CiudadUno" value="<%=alertaCovid.getCiudadUno()%>"/>
				</fieldset>	
				<fieldset class="col-sm-4">
					<label ><b>Fecha / Date</b></label>				
					<input type="text" id="fechaUno" name="FechaUno" data-date-format="dd/mm/yyyy" value="<%=alertaCovid.getFechaUno()%>"/> (DD/MM/AAAA)
				</fieldset>
			</div>		
			<div class="span12"><br>	
				<fieldset class="col-sm-4">
					<label><b>País / Country</b></label>											
					<select name="PaisDos">
<% 					for(CatPais pais : listaPais){%>
						<option value="<%=pais.getPaisId()%>" <%=alertaCovid.getPaisDos().equals(pais.getPaisId()) ? "selected" : ""%>><%=pais.getNombrePais()%></option>
<% 					}%>
					</select>
				</fieldset>
				<fieldset class="col-sm-4">
					<label ><b>Ciudad / City</b></label>				
					<input type="text" name="CiudadDos" value="<%=alertaCovid.getCiudadDos()%>"/>
				</fieldset>	
				<fieldset class="col-sm-4">
					<label ><b>Fecha / Date</b></label>				
					<input type="text" id="fechaDos" data-date-format="dd/mm/yyyy" name="FechaDos" value="<%=alertaCovid.getFechaDos()%>"/>
				</fieldset>
			</div>			
		</div>
		<br>
		<div class="alert alert-info">
			<h4>2. ¿Has estado en contacto con algún caso confirmado del nuevo Coronavirus? [Have you been in contact with a confirmed case of Coronavirus?]</h4>
		</div>
		<input type="radio" name="Contacto" value="S" <%=alertaCovid.getContacto().equals("S") ? "checked" : ""%>/> Si/Yes &nbsp;&nbsp;
		<input type="radio" name="Contacto" value="N" <%=alertaCovid.getContacto().equals("N") ? "checked" : ""%>/> No
		<br><br>
		<div class="alert alert-info">
			<h4>3. Indique la fecha en que estuviste en contacto con el caso positivo. [Indicate the date you were in contact with the positive case]</h4> (DD/MM/AAAA)
		</div>
		<input type="text" id="fechaContacto" data-date-format="dd/mm/yyyy" name="FechaContacto" value="<%=alertaCovid.getContactoFecha()%>"/>
		<br><br>
		<h4>Por favor, responde a las siguientes preguntas / Please, answer the following questions</h4>
		<br>
		<div class="alert alert-info">
			<h4>4.¿Tienes alguno de los siguiente síntomas mayores? / Do you have any of the following major symptoms?</h4>
		</div>
		<div class="row">
			<div class="span12">
				<div class="col-sm-4">
					<label ><b>a) Fiebre [Fever]</b></label>				
				</div>	
				<div class="col-sm-4">
					<input type="radio" name="Fiebre" value="S" <%=alertaCovid.getFiebre().equals("S") ? "checked" : ""%>/> Si/Yes &nbsp;&nbsp;
					<input type="radio" name="Fiebre" value="N" <%=alertaCovid.getFiebre().equals("N") ? "checked" : ""%>/> No
				</div>	
			</div>	
			<div class="span12">
				<div class="col-sm-4">
					<label ><b>b) Tos frecuente [Frequent coughing]</b></label>				
				</div>	
				<div class="col-sm-4">
					<input type="radio" name="Tos" value="S" <%=alertaCovid.getTos().equals("S") ? "checked" : ""%>/> Si/Yes &nbsp;&nbsp;
					<input type="radio" name="Tos" value="N" <%=alertaCovid.getTos().equals("N") ? "checked" : ""%>/> No
				</div>	
			</div>	
			<div class="span12">
				<div class="col-sm-4">
					<label ><b>c) Dolor de cabeza [Headache]</b></label>				
				</div>	
				<div class="col-sm-4">
					<input type="radio" name="Cabeza" value="S" <%=alertaCovid.getCabeza().equals("S") ? "checked" : ""%>/> Si/Yes &nbsp;&nbsp;
					<input type="radio" name="Cabeza" value="N" <%=alertaCovid.getCabeza().equals("N") ? "checked" : ""%>/> No
				</div>	
			</div>	
			<div class="span12">
				<div class="col-sm-4">
					<label ><b>d) Dificultad para respirar [dificulty breathing]</b></label>				
				</div>	
				<div class="col-sm-4">
					<input type="radio" name="Respirar" value="S" <%=alertaCovid.getRespirar().equals("S") ? "checked" : ""%>/> Si/Yes &nbsp;&nbsp;
					<input type="radio" name="Respirar" value="N" <%=alertaCovid.getRespirar().equals("N") ? "checked" : ""%>/> No
				</div>	
			</div>	
		</div>	
		<br>
		<div class="alert alert-info">
			<h4>5.¿Tienes alguno de los siguiente síntomas menores? / Do you have any of the following minor symptoms?</h4>
		</div>
		<div class="row">
			<div class="span12">
				<div class="col-sm-4">
					<label ><b>a) Dolor de garganta [Sore throat]</b></label>				
				</div>	
				<div class="col-sm-4">
					<input type="radio" name="Garganta" value="S" <%=alertaCovid.getGarganta().equals("S") ? "checked" : ""%>/> Si/Yes &nbsp;&nbsp;
					<input type="radio" name="Garganta" value="N" <%=alertaCovid.getGarganta().equals("N") ? "checked" : ""%>/> No
				</div>	
			</div>	
			<div class="span12">
				<div class="col-sm-4">
					<label ><b>b) Escurrimiento nasal [Runny nose]</b></label>				
				</div>	
				<div class="col-sm-4">
					<input type="radio" name="Escurrimiento" value="S" <%=alertaCovid.getEscurrimiento().equals("S") ? "checked" : ""%>/> Si/Yes &nbsp;&nbsp;
					<input type="radio" name="Escurrimiento" value="N" <%=alertaCovid.getEscurrimiento().equals("N") ? "checked" : ""%>/> No
				</div>	
			</div>	
			<div class="span12">
				<div class="col-sm-4">
					<label ><b>c) Falta de olfato [Loss of smell]</b></label>				
				</div>	
				<div class="col-sm-4">
					<input type="radio" name="Olfato" value="S" <%=alertaCovid.getOlfato().equals("S") ? "checked" : ""%>/> Si/Yes &nbsp;&nbsp;
					<input type="radio" name="Olfato" value="N" <%=alertaCovid.getOlfato().equals("N") ? "checked" : ""%>/> No
				</div>	
			</div>	
			<div class="span12">
				<div class="col-sm-4">
					<label ><b>d) Falta de gusto [Loss of taste]</b></label>				
				</div>	
				<div class="col-sm-4">
					<input type="radio" name="Gusto" value="S" <%=alertaCovid.getGusto().equals("S") ? "checked" : ""%>/> Si/Yes &nbsp;&nbsp;
					<input type="radio" name="Gusto" value="N" <%=alertaCovid.getGusto().equals("N") ? "checked" : ""%>/> No
				</div>	
			</div>	
			<div class="span12">
				<div class="col-sm-4">
					<label ><b>e) Dolor de cuerpo [body pain]</b></label>				
				</div>	
				<div class="col-sm-4">
					<input type="radio" name="Cuerpo" value="S" <%=alertaCovid.getCuerpo().equals("S") ? "checked" : ""%>/> Si/Yes &nbsp;&nbsp;
					<input type="radio" name="Cuerpo" value="N" <%=alertaCovid.getCuerpo().equals("N") ? "checked" : ""%>/> No
				</div>	
			</div>	
		</div><br>
		<div class="alert alert-info">
			<a href="javascript:Grabar()" class="btn btn-primary">Grabar</a>
		</div>
	</form>
</div>		
</body>
<script>
	function Grabar(){
		document.frmAlerta.submit();
	}

	jQuery('#fechaUno').datepicker();
	jQuery('#fechaDos').datepicker();
	jQuery('#fechaContacto').datepicker();
</script>
</html>