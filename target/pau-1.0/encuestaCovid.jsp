<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alerta.spring.AlertaCovid"%>
<%@page import="aca.emp.spring.Empleado"%>
<%@page import="aca.catalogo.spring.CatPais"%>

<% String idJsp= "000";%>
<%@include file= "seguro2.jsf" %>
<%@include file= "idioma.jsp" %>

<html>
<head>
	<link rel="stylesheet" href="academico.css" type="text/css">
	<link rel="stylesheet" href="print.css"  type="text/css" media="print">		
	<link rel="stylesheet" href="<%=request.getContextPath()%>/js/popup/general.css" type="text/css" media="screen" />
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap3/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" type="text/css" media="screen" />
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/css/bootstrap-responsive.min.css" type="text/css" media="screen" />
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/fontawesome.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/solid.min.css">	
	<script src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>  
	<script src="<%=request.getContextPath()%>/js/popup/popup.js" type="text/javascript"></script>	
  	<script src='<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js' type='text/javascript'></script>
  	<script type="text/javascript" src="js/prototype-1.6.js"></script>
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/datepicker/datepicker.js"></script>  	 
</head>	
<% 	
	AlertaCovid alertaCovid	= (AlertaCovid)request.getAttribute("alertaCovid");
	Empleado empleado		= (Empleado)request.getAttribute("empleado");
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
	 		<label class="col-sm-3"><strong>No. Nómina: </strong><%=empleado.getClave() %></label>
	 		<label class="col-sm-3"><strong>Nombre: </strong><%=empleado.getNombre() %></label>
	 		<label class="col-sm-3"><strong>Apellido: </strong><%=empleado.getAppaterno()+" "+empleado.getApmaterno()%></label>
		</div>
		<hr>		
		<h4 align="left" style="margin: 10px 10px 0 20px;">Estimado empleado:<br>
			Dear -:<br><br>
			Debido a la pandemia, favor de completar el siguiente custionario: [Due to the pandemic, please fill out the following
			questionnaire]:
		</h4><br><br>
		<input name="CodigoPersonal" type="hidden" value="<%=empleado.getClave()%>">
		<div class="alert alert-info" style="margin: 10px 10px 0 20px;">
			<h4>1. ¿Qué países o ciudades has visitado en los últimos 14 días? [What countries or cities have you visited in the last 14 days?]</h4>
		</div>
		<div class="row" style="margin: 10px 10px 0 20px;">
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
					<input type="text" id="fechaUno" name="FechaUno" data-date-format="dd/mm/yyyy" value="<%=alertaCovid.getFechaUno()%>"/>
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
		<div class="alert alert-info" style="margin: 10px 10px 0 20px;">
			<h4>2. ¿Has estado en contacto con algún caso confirmado del nuevo Coronavirus? [Have you been in contact with a confirmed case of Coronavirus?]</h4>
		</div>
		<input style="margin: 0px 0px 0 62px;" type="radio" name="Contacto" value="S" <%=alertaCovid.getContacto().equals("S") ? "checked" : ""%>/> Si/Yes &nbsp;&nbsp;
		<input type="radio" name="Contacto" value="N" <%=alertaCovid.getContacto().equals("N") ? "checked" : ""%>/> No
		<br><br>
		<div class="alert alert-info" style="margin: 10px 10px 0 20px;">
			<h4>3. Indique la fecha en que estuviste en contacto con el caso positivo. [Indicate the date you were in contact with the positive case]</h4>
		</div>
		<input style="margin: 10px 10px 0 65px;" type="text" id="fechaContacto" data-date-format="dd/mm/yyyy" name="FechaContacto" value="<%=alertaCovid.getContactoFecha()%>"/>
		<br><br>
		<h4 style="margin: 10px 10px 0 20px;">Por favor, responde a las siguientes preguntas / Please, answer the following questions</h4>
		<br>
		<div class="alert alert-info" style="margin: 10px 10px 0 20px;">
			<h4>4.¿Tienes alguno de los siguiente síntomas mayores? / Do you have any of the following major symptoms?</h4>
		</div>
		<div class="row" style="margin: 10px 10px 0 20px;">
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
		<div class="alert alert-info" style="margin: 10px 10px 0 20px;">
			<h4>5.¿Tienes alguno de los siguiente síntomas menores? / Do you have any of the following minor symptoms?</h4>
		</div>
		<div class="row" style="margin: 10px 10px 0 20px;">
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
		<div class="alert alert-info" style="margin: 10px 10px 0 20px;">
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
</script>
</html>