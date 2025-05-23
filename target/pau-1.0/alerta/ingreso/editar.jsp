<%@page import="java.text.*" %>
<%@page import="aca.alumno.spring.AlumCovid"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css"/>
</head>
<%
	AlumCovid alumCovid 		= (AlumCovid) request.getAttribute("alumCovid");
	String periodoId 			= (String) request.getAttribute("periodoId");
	String codigoAlumno			= (String) request.getAttribute("codigoAlumno");
	String nombreAlumno 		= (String) request.getAttribute("nombreAlumno");
	String mensaje	 			= (String) request.getAttribute("mensaje");
	String codigoPersonal		= (String)session.getAttribute("codigoPersonal");
%>
<div class="container-fluid">
	<h2>Registro de datos <small class="text-muted fs-5">( <%=codigoAlumno+" - "+nombreAlumno%> )</small></h2>
	<div class="alert alert-info">
		<a href=datos class="btn btn-success"><span class="icon icon-white icon-arrow-left" ></span>&nbsp;<spring:message code='aca.Anterior'/></a>&nbsp;&nbsp;
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
	<input name="CodigoAlumno" type="hidden" value="<%=codigoAlumno%>"/>
	<div class="row">
		<div class="col-4" align="left">
			<div class="control-group">
				<label><b>Ubicación<span class="-indicator"></span></b></label>
				<select name="Tipo" id="Tipo" class="form-select" onchange="cambioUbicacion()">
					<option value="L" <%if(alumCovid.getTipo().equals("L")){out.print("selected");} %>>Local</option>
					<option value="F" <%if(alumCovid.getTipo().equals("F")){out.print("selected");} %>>Foraneo</option> 
				</select>		            		
			</div>
			<br>
			<div class="control-group">
				<label><b>Fecha llegada<span class="-indicator"></span></b></label>
				<input type="text" class="form-control" id="Fechallegada" name="Fechallegada" value="<%=alumCovid.getFechaLlegada()==null?"":alumCovid.getFechaLlegada()%>" size="10" data-date-format="dd/mm/yyyy"/>
				(DD/MM/AAAA)
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
				(DD/MM/AAAA)
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
				(DD/MM/AAAA)
			</div><br>
			<div class="control-group">
				<label><b>Comentario</b></label>
				<textarea id="Comentario" name="Comentario" class="form-control"  rows="5" cols="40"><%=alumCovid.getComentario()%></textarea>
			</div>
		</div>
<%
		String muestra 		= "";
		String muestraDos	= "";
		if(!codigoPersonal.equals("9820026") && !codigoPersonal.equals("9800308") && !codigoPersonal.equals("9830438") && !codigoPersonal.equals("9899001")){
			muestra 	= "readonly";
			muestraDos	= "disabled";
		}
%>
		<div class="col-4">
			<div class="control-group">
				<label>Positivo covid</label>
				<select name="Positivo" class="form-select" <%=muestraDos%>>					 
					<option value="N" <%if(alumCovid.getPositivoCovid().equals("N")){out.print("selected");} %>>No</option>
					<option value="S" <%if(alumCovid.getPositivoCovid().equals("S")){out.print("selected");} %>>Si</option>
				</select>
			</div><br>
			<div class="control-group">
				<label>Fecha positivo<span class="-indicator"></span></label>
<%		if(codigoPersonal.equals("9820026") || codigoPersonal.equals("9800308") || codigoPersonal.equals("9830438") || codigoPersonal.equals("9899001")){ %>
				<input type="text" size="10" class="form-control" id="FechaPositivo" name="FechaPositivo" <%=muestra%> value="<%=alumCovid.getFechaPositivo()==null?"":alumCovid.getFechaPositivo()%>" data-date-format="dd/mm/yyyy"/>
				(DD/MM/AAAA)
				<a title="Calcular fin aislamiento" onclick="calculaDias()" class="btn btn-success"><i class="fas fa-sync-alt"></i></a>
<%		}else{ %>
				<input type="text" size="10" class="form-control" name="FechaPositivo" <%=muestraDos%> value="<%=alumCovid.getFechaPositivo()==null?"":alumCovid.getFechaPositivo()%>"/>
<%		} 		%>
			</div><br>
			<div class="control-group">
				<label>Aislamiento<span class="-indicator"></span></label>
				<select name="Aislamiento" class="form-select"<%=muestraDos%>>					 
					<option value="N" <%if(alumCovid.getAislamiento().equals("N")){out.print("selected");} %>>No</option>
					<option value="S" <%if(alumCovid.getAislamiento().equals("S")){out.print("selected");} %>>Si</option>
				</select>
			</div><br>
			<div class="control-group">
				<label>Termina aislamiento</label>
<%			if(codigoPersonal.equals("9820026") || codigoPersonal.equals("9800308") || codigoPersonal.equals("9830438") || codigoPersonal.equals("9899001")){ %>
				<input type="text" class="form-control" size="10" id="FinAislamiento" name="FinAislamiento" <%=muestra%> value="<%=alumCovid.getFinAislamiento()==null?"":alumCovid.getFinAislamiento()%>" data-date-format="dd/mm/yyyy"/>
<% 			}else{ %>
				<input type="text" class="form-control" size="10" name="FinAislamiento" <%=muestra%> value="<%=alumCovid.getFinAislamiento()==null?"":alumCovid.getFinAislamiento()%>"/>
<% 			}	%>						
			</div><br>					
			<div class="control-group">
				<label>Usuario Alta:</label>
				<%=alumCovid.getUsuarioAlta()==null?"":alumCovid.getUsuarioAlta()%> - <%=alumCovid.getFechaAlta()==null?"":alumCovid.getFechaAlta()%>			
			</div><br>
			<div class="control-group">
				<label><b>Validado por la comisión de salud</b></label>
				<%=alumCovid.getValidado().equals("N")?"NO":alumCovid.getValidado()%>
				<input type="hidden" size="10"  id="Validado" name="Validado" <%=muestra%> value="<%=alumCovid.getValidado()==null?"N":alumCovid.getValidado()%>"/>		
			</div>
		</div>
	</div>
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
	jQuery('#FechaSospechoso').datepicker();

	function cambioUbicacion(){
		var ubicacion = document.getElementById("Tipo").value; 
		if(ubicacion === 'F'){
			document.getElementById("Fechallegada").required = true;  
		}
	}
	
	function calculaDias() {
	  	var fecha = document.getElementById("FechaPositivo").value;
	  	var arrayFecha = fecha.split("/");
	  	var mes = arrayFecha[1];
	  	mes = mes-1;
	  	var tmp = new Date(arrayFecha[2],mes,arrayFecha[0]);
	  	var dias = 14; 
		tmp.setDate(tmp.getDate()+dias);
		
		var month = tmp.getMonth()+1;//months (0-11)
		var day = tmp.getDate();//day (1-31)
		var year= tmp.getFullYear();
		
		month = (month < 10 ? '0' : '') + month;
		day = (day < 10 ? '0' : '') + day;
		
	 	document.getElementById("FinAislamiento").value = day+"/"+month+"/"+year;
	}
	
</script>