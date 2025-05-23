<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.sep.spring.SepAlumno"%>
<%@page import="aca.conva.spring.ConvEvento"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatEstado"%>

<%@ page import="java.util.List"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../body.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<%
	SepAlumno alumno 	= (SepAlumno) request.getAttribute("alumno");

	List<CatPais> listaPais 	= (List<CatPais>) request.getAttribute("listaPais");
	List<CatEstado> listaEstado = (List<CatEstado>) request.getAttribute("listaEstado");
%>

<div class="container-fluid">		
	<div class="alert alert-info">
		<a class="btn btn-primary" href="sepAlumno"><i class="icon icon-arrow-left icon-white"></i> Regresar</a>
	</div>
	<form action="sepAlumnoGrabar" name="form" method="post">
		<input type="hidden" name="folio" value="<%=alumno.getFolio()%>">
		<div class="row">
			<div class="span3">
				<label>Nombre</label>
				<input name="nombre" value="<%=alumno.getNombre()%>"><br><br>
				<label>Edad</label>
				<input name="edad" value="<%=alumno.getEdad()%>"><br><br>
				<label>Plan SEP</label>
				<input name="planSep" value="<%=alumno.getPlanSep()%>"><br><br>
				<label>Ciclo</label>
				<input name="ciclo" value="<%=alumno.getCiclo()%>"><br><br>
				<label>Usado</label>
				<input name="usado" value="<%=alumno.getUsado()%>"><br><br>
			</div>
			<div class="span3">
				<label>Apellido Paterno</label>
				<input name="paterno" value="<%=alumno.getPaterno()%>"><br><br>
				<label>Genero</label>
				<input name="genero" value="<%=alumno.getGenero()%>"><br><br>
				<label>Plan UM</label>
				<input name="planUm" value="<%=alumno.getPlanUm()%>"><br><br>
				<label>Grado</label>
				<input name="grado" value="<%=alumno.getGrado()%>"><br><br>
			</div>
			<div class="span3">
				<label>Apellido Materno</label>
				<input name="materno" value="<%=alumno.getMaterno()%>"><br><br>
				<label>Curp</label>
				<input name="curp" value="<%=alumno.getCurp()%>"><br><br>
				<label>País</label>
				<select name="paisId" onchange="javascript:refreshEstados();">
				<%for(CatPais pais : listaPais){%>
					<option value="<%=pais.getPaisId()%>" <%=alumno.getPaisId().equals(pais.getPaisId()) ? "selected" : ""%>><%=pais.getNombrePais()%></option>
				<% }%>
				</select><br><br>
				<label>Lugar Prepa</label>
				<input name="prepaLugar" value="<%=alumno.getPrepaLugar()%>"><br><br>
			</div>
			<div class="span3">
				<label>Codigo Personal</label>
				<input name="codigoPersonal" value="<%=alumno.getCodigoPersonal()%>"><br><br>
				<label>Fecha</label>
				<input name="fecha" id="fecha" value="<%=alumno.getFecha()%>"><br><br>
				<label>Estado</label>
				<select name="estadoId" id="estadoId">
				<%for(CatEstado estado : listaEstado){%>
					<option value="<%=estado.getEstadoId()%>" <%=alumno.getEstadoId().equals(estado.getEstadoId()) ? "selected" : ""%>><%=estado.getNombreEstado()%></option>
				<% }%>
				</select><br><br>
				<label>Plantel</label>
				<input name="plantel" value="<%=alumno.getPlantel()%>"><br><br>
			</div>
		</div>
		<div>
			<input class="btn btn-primary" type="submit" value="Guardar">
		</div>
	</form>
</div>
<script>
	jQuery('#fecha').datepicker();

	function refreshEstados(){		
		
		jQuery('#estadoId').html('<option>Actualizando</option>');
		
		var paisId = document.form.paisId.value;	
		
		jQuery.get('getEstados?paisId='+paisId, function(data){
			jQuery("#estadoId").html(data);
			refreshCiudades();
		});
		
	}
</script>
