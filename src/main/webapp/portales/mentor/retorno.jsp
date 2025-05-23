<%@page import="java.text.*" %>
<%@page import="aca.alumno.spring.AlumCovid"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="../../js/datepicker/datepicker.css"/>
</head>
<body>
<%
	AlumCovid alumCovid 		= (AlumCovid) request.getAttribute("alumCovid");
	String periodoId 			= (String) request.getAttribute("periodoId");
	String codigoAlumno			= (String) request.getAttribute("codigoAlumno");
	String nombreAlumno 		= (String) request.getAttribute("nombreAlumno");
	String mensaje	 			= (String) request.getAttribute("mensaje");
	String codigoPersonal		= (String)session.getAttribute("codigoPersonal");
%>
<div class="container-fluid">
	<h2>Data Entry <small class="text-muted fs-5">( <%=codigoAlumno+" - "+nombreAlumno%> )</small></h2>
	<div class="alert alert-info">
		<a href="portal" class="btn btn-primary"><span class="icon icon-white icon-arrow-left" ></span>&nbsp;<spring:message code='aca.Regresar'/></a>&nbsp;&nbsp;
	</div>
	<%if(mensaje.equals("1")){%>
	<div class="alert alert-success" role="alert">
		Saved
	</div>
	<%}else if(mensaje.equals("2")){%>
	<div class="alert alert-danger" role="alert">
		Not saved
	</div>
	<%} %>
	<form id="frmDatos" name="frmDatos" action="grabar" method="post">
	<input name="PeriodoId" type="hidden" value="<%=periodoId%>"/>
	<input name="CodigoAlumno" type="hidden" value="<%=codigoAlumno%>"/>
	<div class="row">
		<div class="span4">
			<div class="col-sm">
				<label><b>Location<span class="-indicator"></span></b></label>
				<select name="Tipo" class="form-control" id="Tipo" onchange="cambioUbicacion()">
					<option value="L" <%if(alumCovid.getTipo().equals("L")){out.print("selected");} %>>Local</option>
					<option value="F" <%if(alumCovid.getTipo().equals("F")){out.print("selected");} %>>Foreigner</option> 
				</select>		            		
			</div>
			<br>
			<div class="col-sm">
				<label><b>Arrival Date<span class="-indicator"></span></b></label>
				<input type="text" class="form-control" size="10" id="Fechallegada" name="Fechallegada" value="<%=alumCovid.getFechaLlegada()==null?"":alumCovid.getFechaLlegada()%>" size="10" data-date-format="dd/mm/yyyy"/>
			</div><br>
			<div class="col-sm">
				<label><b>Vaccine</b></label>
				<select name="Vacuna" class="form-control">
					<option value="N" <%if(alumCovid.getVacuna().equals("N")){out.print("selected");} %>>No</option>
					<option value="S" <%if(alumCovid.getVacuna().equals("S")){out.print("selected");} %>>Yes</option>					
				</select>
			</div><br>
			<div class="col-sm">
				<label><b>Vaccination Date</b></label>
				<input type="text" class="form-control" size="10" id="FechaVacuna" name="FechaVacuna" value="<%=alumCovid.getFechaVacuna()==null?"":alumCovid.getFechaVacuna()%>" data-date-format="dd/mm/yyyy"/>
			</div><br>
			<div class="col-sm">
				<label><b>Symptoms</b></label>
				<select name="Sospechoso" onchange="" class="form-control">
					<option value="N" <%if(alumCovid.getSospechoso().equals("N")){out.print("selected");} %>>No</option>
					<option value="S" <%if(alumCovid.getSospechoso().equals("S")){out.print("selected");} %>>Yes</option>					
				</select>
			</div><br>
			<div class="col-sm">
				<label><b>Symptoms Date</b></label>
				<input type="text" class="form-control" size="10"  id="FechaSospechoso" name="FechaSospechoso" value="<%=alumCovid.getFechaSospechoso()==null?"":alumCovid.getFechaSospechoso()%>" data-date-format="dd/mm/yyyy"/>
			</div><br>
			<div class="col-sm">
				<label><b>Comment</b></label>
				<textarea id="Comentario" class="form-control" name="Comentario" rows="5" cols="40"><%=alumCovid.getComentario()%></textarea>
			</div>
		</div>
<%
		String muestra 		= "";
		String muestraDos	= "";
		if(!codigoPersonal.equals("9820026") && !codigoPersonal.equals("9800308") && !codigoPersonal.equals("9830438")){
			muestra 	= "readonly";
			muestraDos	= "disabled";
		}
%>
		<div class="span4">
			<div class="col-sm">
				<label><b>COVID Positive</b></label>
				<select name="Positivo" class="form-control" <%=muestraDos%>>					 
					<option value="N" <%if(alumCovid.getPositivoCovid().equals("N")){out.print("selected");} %>>No</option>
					<option value="S" <%if(alumCovid.getPositivoCovid().equals("S")){out.print("selected");} %>>Si</option>
				</select>
			</div><br>
			<div class="col-sm">
				<label><b>Positive DAte</b><span class="-indicator"></span></label>
<%		if(codigoPersonal.equals("9820026") || codigoPersonal.equals("9800308") || codigoPersonal.equals("9830438")){ %>
				<input type="text" size="10" class="form-control" id="FechaPositivo" name="FechaPositivo" <%=muestra%> value="<%=alumCovid.getFechaPositivo()==null?"":alumCovid.getFechaPositivo()%>" data-date-format="dd/mm/yyyy"/>
<%		}else{ %>
				<input type="text" size="10" class="form-control" name="FechaPositivo" <%=muestra%> value="<%=alumCovid.getFechaPositivo()==null?"":alumCovid.getFechaPositivo()%>"/>
<%		} 		%>
			</div><br>
			<div class="col-sm">
				<label><b>Quarantine</b><span class="-indicator"></span></label>
				<select name="Aislamiento" class="form-control" <%=muestraDos%>>					 
					<option value="N" <%if(alumCovid.getAislamiento().equals("N")){out.print("selected");} %>>No</option>
					<option value="S" <%if(alumCovid.getAislamiento().equals("S")){out.print("selected");} %>>Si</option>
				</select>
			</div><br>
			<div class="col-sm">
				<label><b>Quarantine Ending</b></label>
<%			if(codigoPersonal.equals("9820026") || codigoPersonal.equals("9800308") || codigoPersonal.equals("9830438")){ %>
				<input type="text" class="form-control" size="10" id="FinAislamiento" name="FinAislamiento" <%=muestra%> value="<%=alumCovid.getFinAislamiento()==null?"":alumCovid.getFinAislamiento()%>" data-date-format="dd/mm/yyyy"/>
<% 			}else{ %>
				<input type="text" class="form-control" size="10" name="FinAislamiento" <%=muestra%> value="<%=alumCovid.getFinAislamiento()==null?"":alumCovid.getFinAislamiento()%>"/>
<% 			}	%>						
			</div><br>					
			<div class="col-sm">
				<label><b>User:</b></label>
				<%=alumCovid.getUsuarioAlta()==null?"":alumCovid.getUsuarioAlta()%> - <%=alumCovid.getFechaAlta()==null?"":alumCovid.getFechaAlta()%>			
			</div><br>
			<div class="col-sm">
				<label><b>Validated by Health Department</b></label>
				<%=alumCovid.getValidado().equals("N")?"NO":alumCovid.getValidado()%>
				<input type="hidden" size="10"  id="Validado" name="Validado" <%=muestra%> value="<%=alumCovid.getValidado()==null?"N":alumCovid.getValidado()%>"/>		
			</div>
		</div>
	</div>&nbsp;
	<div class="alert alert-info">
		<button type="submit" class="btn btn-primary">Save</button>
	</div>
	</form>
</div>
</body>
</html>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script type="text/javascript">
	jQuery('#Fechallegada').datepicker();
	jQuery('#FechaPositivo').datepicker();
	jQuery('#FinAislamiento').datepicker();
	jQuery('#FechaAlta').datepicker();
	jQuery('#FechaVacuna').datepicker();

	function cambioUbicacion(){
		var ubicacion = document.getElementById("Tipo").value; 
		if(ubicacion === 'F'){
			document.getElementById("Fechallegada").required = true;  
		}
	}
</script>