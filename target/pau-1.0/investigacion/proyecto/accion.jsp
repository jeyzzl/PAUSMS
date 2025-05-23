<%@page import="aca.investiga.spring.InvProyecto"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>

<%@ page import = "java.util.List" %>
<%@ page import = "java.util.HashMap" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	String accion 			= (String)request.getAttribute("accion");
	String proyectoId 		= (String)request.getAttribute("proyectoId");
	
	String fechaHoy 		= aca.util.Fecha.getHoy();		 
	String nombreUsuario	= (String)request.getAttribute("nombreUsuario");
	String msj				= (String)request.getAttribute("msj");
	String documento		= (String)request.getAttribute("documento");

	InvProyecto invProyecto		= (InvProyecto)request.getAttribute("invProyecto");

	HashMap<String, CatFacultad> mapaFacultad	= (HashMap<String, CatFacultad>)request.getAttribute("mapaFacultad");
	
	List<CatCarrera> lisCarreras 				= (List<CatCarrera>)request.getAttribute("lisCarreras");		
%>
<!DOCTYPE html>
<html lang="es">
<style>
	#nombre, #carrera {
		width: 85%;
	}
	
	textarea, #departamento {
		width: 100%;
	}
	
	#tipo {
		widht: 80%;
	}
	
</style>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="../../js/chosen/chosen.css" />
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body>
<div class="container-fluid">	
	<h2>Formato para el registro y evaluación de proyectos</h2>
	<div class="alert alert-info">		
		<a href="proyecto" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
<%		
			int temp = 0;
			if (invProyecto.getProyectoNombre() 	!= "-"
			 && invProyecto.getTipo() 				!= "0"
			 && invProyecto.getLinea() 				!= "-"
			 && invProyecto.getCarreraId() 			!= "-"
			 && invProyecto.getDepartamento() 		!= "-"
			 && invProyecto.getFechaInicio() 		!= ""
			 && invProyecto.getFechaFinal() 		!= ""
			 && invProyecto.getResumen() 			!= "-"
			 && invProyecto.getEstadoArte() 		!= "-"
			 && invProyecto.getResDocente() 		!= "-"
			 && invProyecto.getResAlumno() 			!= "-"
			 && invProyecto.getAntecedentes() 		!= "-"
			 && invProyecto.getJustificacion() 		!= "-"){
				temp = 1;
			}
%>
		&nbsp;&nbsp;	
		<%=msj %>
	</div>	
	<hr>
	<ul class="nav nav-tabs">
  		<li class="nav-item">
	    	<a class="nav-link active" aria-current="page" href="#">Registro</a>
	  	</li>
	  	<li class="nav-item">
	    	<a class="nav-link" href="permisos?proyectoId=<%=proyectoId%>">Documentos</a>
	  	</li>
	</ul>
	<hr>
	<h2><small class="text-muted fs-4">Proyecto N° <%=proyectoId %></small></h2>
	<form name="forma" id="forma" action="accion" method="post">
	<input type="hidden" name="proyectoId"  value="<%=proyectoId%>"/>
	<input type="hidden" name="documento"  value="<%=documento%>"/>
	<input type="hidden" name="Accion" id="Accion" />

	<div class="card">
	  	<div class="card-header bg-info">
			<h3>Identificación del estudio</h3>
	  	</div>
	  	<div class="card-body">
		    <h3 class="card-title">Hoja de Identifiación</h3>
	  		<div class="row">		
				<div class="col-sm-4">
					<label for="nombre"><strong><spring:message code="aca.Nombre"/> del investigador principal</strong></label>
					<input maxlength="600" id="nombre" name="nombre" type="text" value="<%= invProyecto.getCodigoPersonal().equals("")?nombreUsuario:invProyecto.getCodigoPersonal()%>" readonly/><br>
				</div>
				<div class="col-sm-5">
					<label for="titulo" ><strong>Título del proyecto</strong></label>
					<textarea maxlength="600" name="titulo" id="titulo" cols="80" rows="2" placeholder="Se escogera de tal modo que exprese clara, precisa y completamente del tema o problema objeto de la investigación." required><%= invProyecto.getProyectoNombre()=="-"?"":invProyecto.getProyectoNombre() %></textarea><br><br>
				</div>									
				<div class="col-sm-3">
					<label for="tipo"><strong>Tipo de investigación</strong></label>
					<select name="tipo" id="tipo" class="input input-xlarge">
						<option value="1" <%if(invProyecto.getTipo()!=null && invProyecto.getTipo().equals("1"))out.print("selected"); %>>Académico-científica</option>
						<option value="2" <%if(invProyecto.getTipo()!=null && invProyecto.getTipo().equals("2"))out.print("selected"); %>>Investigación institucional</option>
						<option value="3" <%if(invProyecto.getTipo()!=null && invProyecto.getTipo().equals("3"))out.print("selected"); %>>Investigación educativa</option>
						<option value="4" <%if(invProyecto.getTipo()!=null && invProyecto.getTipo().equals("4"))out.print("selected"); %>>Desarrollo de habilidades de investigación</option> 	
						<option value="5" <%if(invProyecto.getTipo()!=null && invProyecto.getTipo().equals("5"))out.print("selected"); %>>Descripción del tipo de investigación</option>
					</select><br><br>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-4">
					<label for="carrera"><strong><spring:message code="aca.Facultad"/> o Departamento</strong></label>
					<select name="carrera" id="carrera" class="chosen" required>
					<option value="1" id="carrera">Selecciona la carrera</option>
					<%
						String facultad 		= "0";
						String facultadNombre 	= "-";
						for(CatCarrera carrera : lisCarreras){
							if (!carrera.getFacultadId().equals(facultad)){
								
								if(mapaFacultad.containsKey(carrera.getFacultadId())){
									facultadNombre = mapaFacultad.get(carrera.getFacultadId()).getNombreFacultad();
								}
								
								if (!facultad.equals("0")){
									out.print("</optgroup>");	
								}
								out.print("<optgroup label='"+facultadNombre+"'>");
								facultad = carrera.getFacultadId();
							}		
					%>
							<option value="<%=carrera.getCarreraId() %>" <%if(carrera.getCarreraId().equals(invProyecto.getCarreraId()))out.print("selected"); %>>
							  <%= carrera.getNombreCarrera() %>
							</option>
					<%				
						}
						
						// Cerrando el ultimo grupo
						out.print("</optgroup>");
					%>
					</select><br><br> 
				</div>
				<div class="col-sm-5">
					<label for="linea"><strong>Línea de investigación</strong></label>
					<textarea maxlength="200" name="linea" id="linea" cols="80" rows="2" placeholder="De acuerdo a las líneas institucionales de investigación aprobadas." required><%= invProyecto.getLinea()=="-"?"":invProyecto.getLinea() %></textarea>
				</div>
			 	<div class="col-sm-3">
					<label for="departamento"><strong>Departamento</strong></label>
					<input name="departamento" type="text" id="departamento" required placeholder="Depto. y/o escuela responsable." value="<%= invProyecto.getDepartamento()=="-"?"":invProyecto.getDepartamento() %>" maxlength="100" /><br>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-sm-4">
					<label for="departamento"><strong>Investigadores Colaboradores</strong></label>
					<textarea maxlength="200" name="invSec" id="invSec" cols="80" rows="2" placeholder="Nombres de maestros, alumnos y personal que colabora  en la elaboración del proyecto." required><%= invProyecto.getInvestigadores()=="-"?"":invProyecto.getInvestigadores() %></textarea>					
				</div>
				<div class="col-sm-5">
					<label for="fechaIni"><strong>Fecha de inicio del proyecto</strong></label>
					<input name="fechaIni" type="text" class="form-control" id="fechaIni" size="12" maxlength="10" data-date-format="dd/mm/yyyy" required value="<%= invProyecto.getFechaInicio()==null?fechaHoy:invProyecto.getFechaInicio() %>" style="width:150px"/>
				</div>
				<div class="col-sm-3">
					<label for="fechaFin"><strong>Fecha de conclusión del proyecto</strong></label>
					<input name="fechaFin" required type="text" class="form-control" id="fechaFin" size="12" maxlength="10" data-date-format="dd/mm/yyyy" required value="<%= invProyecto.getFechaFinal()==null?fechaHoy:invProyecto.getFechaFinal()%>" style="width:150px"/><br>
				</div>
			</div><br>
			<div class="row">
				<div class="col-sm-3">
					<label for="tipo"><strong>Tipo de documento</strong></label>
					<select name="TipoDoc" id="TipoDoc" class="input input-xlarge">
						<option value="1" <%if(invProyecto.getTipoDocumento()!=null && invProyecto.getTipoDocumento().equals("1"))out.print("selected"); %>>Proyecto</option>
						<option value="2" <%if(invProyecto.getTipoDocumento()!=null && invProyecto.getTipoDocumento().equals("2"))out.print("selected"); %>>Protocolo</option>
					</select><br><br>
				</div>
			</div>			
	  	</div>
	</div>
	<div class="alert alert-info">
		<%if(accion.equals("2")){ %>
			<a href="accion?Accion=2&proyectoId=<%= invProyecto.getProyectoId() %>" class="btn btn-danger btn-large"><i class="fas fa-trash-alt"></i> Eliminar</a>
<%
		}else{
%>
			<a class="btn btn-success" onclick="javascript:Guardar();"><i class="fas fa-save"></i> Guardar</a>
<% 
		} 
%>
		<a  href="proyecto" class="btn btn-danger"><i class="fas fa-times"></i> Cancelar</a>
	</div>
</form>
</div>
</body>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script>
	jQuery(".chosen").chosen();
	jQuery('#fechaIni').datepicker();
	jQuery('#fechaFin').datepicker();
</script>

<script>
	function Guardar() {
		if (document.forma.titulo.value != ""
		 && document.forma.tipo.value != ""
		 && document.forma.linea.value != ""
		 && document.forma.carrera.value != "1"
		 && document.forma.fechaIni.value != ""
		 && document.forma.fechaFin.value != ""
		 /*&& document.forma.resumen.value != ""
		 && document.forma.estadoArte.value != ""
		 && document.forma.resDocente.value != ""
		 && document.forma.resAlumno.value != ""
		 && document.forma.antecedentes.value != ""
		 && document.forma.justificacion.value != ""*/) {
			document.forma.Accion.value = "1";
			document.forma.submit();
		} else {
			alert("¡Completa todos los campos!");
		}
	}
	
/* 	function minimo(){
		resumen = document.getElementById('resumen').value;
		if(resumen.length < 10){
			alert ("Escribe mas de 10 caracteres");
		}
	} */
	
</script>
</html>