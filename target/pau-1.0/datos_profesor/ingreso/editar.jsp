<%@page import="java.text.*" %>
<%@page import="aca.alumno.spring.AlumCovid"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css"/>
</head>
<body>
<%
	String periodoId			= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
	AlumCovid alumCovid 		= (AlumCovid) request.getAttribute("alumCovid");
	String codigoEmpleado		= (String) request.getAttribute("codigoEmpleado");
	String nombreEmpleado 		= (String) request.getAttribute("nombreEmpleado");
	String mensaje	 			= (String) request.getAttribute("mensaje");
%>
<div class="container-fluid">
	<h2>Registro de datos <small class="text-muted fs-4">( <%=codigoEmpleado+" - "+nombreEmpleado%> )</small></h2>
	<div class="alert alert-info">
		<a href="datos" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
	</div>
	<%if(mensaje.equals("1")){%>
	<div class="alert alert-success" role="alert">
		¡ Guardado correctamente !
	</div>
	<%}else if(mensaje.equals("2")){%>
	<div class="alert alert-danger" role="alert">
		No se guardo
	</div>
	<%} %>
	<form id="frmDatos" name="frmDatos" action="grabar" method="post">
	<input name="PeriodoId" type="hidden" value="<%=periodoId%>"/>
	<input name="CodigoEmpleado" type="hidden" value="<%=codigoEmpleado%>"/>
	<div class="row">
		<div class="col" align="left">
			<div class="control-group">
				<label><b>Ubicación<span class="-indicator"></span></b></label>
				<select name="Tipo" id="Tipo" onchange="cambioUbicacion()">
					<option value="L" <%if(alumCovid.getTipo().equals("L")){out.print("selected");} %>>Local</option>
					<option value="F" <%if(alumCovid.getTipo().equals("F")){out.print("selected");} %>>Foraneo</option> 
				</select>		            		
			</div>
			<br>
			<div class="control-group">
				<label><b>Fecha llegada<span class="-indicator"></span></b></label>
				<input type="text" class="form-control" id="Fechallegada" name="Fechallegada" value="<%=alumCovid.getFechaLlegada()==null?"":alumCovid.getFechaLlegada()%>" size="10" data-date-format="dd/mm/yyyy"/>
			</div><br>
			<div class="control-group">
				<label><b>Vacuna</b></label>
				<select name="Vacuna">
					<option value="N" <%if(alumCovid.getVacuna().equals("N")){out.print("selected");} %>>No</option>
					<option value="S" <%if(alumCovid.getVacuna().equals("S")){out.print("selected");} %>>Si</option>					
				</select>
			</div><br>
			<div class="control-group">
				<label><b>Fecha Vacuna</b></label>
				<input type="text" class="form-control" size="10"  id="FechaVacuna" name="FechaVacuna" value="<%=alumCovid.getFechaVacuna()==null?"":alumCovid.getFechaVacuna()%>" data-date-format="dd/mm/yyyy"/>
			</div><br>
			<div class="control-group">
				<label><b>Sospechoso</b></label>
				<select name="Sospechoso" onchange="">
					<option value="N" <%if(alumCovid.getSospechoso().equals("N")){out.print("selected");} %>>No</option>
					<option value="S" <%if(alumCovid.getSospechoso().equals("S")){out.print("selected");} %>>Si</option>					
				</select>
			</div><br>
			<div class="control-group">
				<label><b>Fecha Sospechoso</b></label>
				<input type="text" class="form-control" size="10"  id="FechaSospechoso" name="FechaSospechoso" value="<%=alumCovid.getFechaSospechoso()==null?"":alumCovid.getFechaSospechoso()%>" data-date-format="dd/mm/yyyy"/>
			</div><br>
			<div class="control-group">
				<label><b>Comentario</b></label>
				<textarea id="Comentario" name="Comentario" rows="5" cols="40"><%=alumCovid.getComentario()%></textarea>
			</div>
		</div>
		<div class="col">
			<div class="control-group">
				<label>Positivo covid</label>
				<select name="Positivo">					 
					<option value="N" <%if(alumCovid.getPositivoCovid().equals("N")){out.print("selected");} %>>No</option>
					<option value="S" <%if(alumCovid.getPositivoCovid().equals("S")){out.print("selected");} %>>Si</option>
				</select>
			</div><br>
			<div class="control-group">
				<label>Fecha positivo<span class="-indicator"></span></label>
				<input type="text" size="10" class="form-control" id="FechaPositivo" name="FechaPositivo" value="<%=alumCovid.getFechaPositivo()==null?"":alumCovid.getFechaPositivo()%>" data-date-format="dd/mm/yyyy"/>
			</div><br>
			<div class="control-group">
				<label>Aislamiento<span class="-indicator"></span></label>
				<select name="Aislamiento">					 
					<option value="N" <%if(alumCovid.getAislamiento().equals("N")){out.print("selected");} %>>No</option>
					<option value="S" <%if(alumCovid.getAislamiento().equals("S")){out.print("selected");} %>>Si</option>
				</select>
			</div><br>
			<div class="control-group">
				<label>Termina aislamiento</label>
				<input type="text" class="form-control" size="10" id="FinAislamiento" name="FinAislamiento" value="<%=alumCovid.getFinAislamiento()==null?"":alumCovid.getFinAislamiento()%>" data-date-format="dd/mm/yyyy"/>
			</div><br>					
			<div class="control-group">
				<label>Usuario Alta:</label>
				<%=alumCovid.getUsuarioAlta()==null?"":alumCovid.getUsuarioAlta()%> - <%=alumCovid.getFechaAlta()==null?"":alumCovid.getFechaAlta()%>			
			</div><br>
			<div class="control-group">
				<label><b>Validado por la comisión de salud</b></label>
				<%=alumCovid.getValidado().equals("N")?"NO":alumCovid.getValidado()%>
				<input type="hidden" size="10"  id="Validado" name="Validado" value="<%=alumCovid.getValidado()==null?"N":alumCovid.getValidado()%>"/>		
			</div>
		</div>
	</div><br>
	<div class="alert alert-info">
		<button type="submit" class="btn btn-primary">Grabar</button>
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