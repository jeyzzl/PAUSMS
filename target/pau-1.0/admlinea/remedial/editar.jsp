<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%

	AlumPersonal alumPersonal = (AlumPersonal) request.getAttribute("alumPersonal");	

	List<MapaCurso> listaCursos = (List<MapaCurso>) request.getAttribute("listaCursos");
	
	HashMap<String, String> mapaRemedial = (HashMap<String, String>) request.getAttribute("mapaRemedial");
	
	String mensaje = (String) request.getAttribute("mensaje");

%>
<div class="container-fluid">
	<h2>Remediales para <%=alumPersonal.getNombreLegal()%></h2>
	<div class="alert alert-info" align="left">
		<a href="lista" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
<% if(mensaje.equals("1")){%>
	<div class="alert alert-success">
		Grabado
	</div>
<% } else if(mensaje.equals("2")){%>
	<div class="alert alert-danger">
		No se grabo
	</div>
<% } else if(mensaje.equals("3")){%>
	<div class="alert alert-danger">
		Ese alumno no existe
	</div>
<% }%>
	<form name="form" action="grabar" method="POST">
		<input type="hidden" name="CodigoPersonal" value="<%=alumPersonal.getCodigoPersonal()%>">
<% 	for(MapaCurso curso : listaCursos){
	boolean contiene = false;
	if(mapaRemedial.containsKey(alumPersonal.getCodigoPersonal()+curso.getCursoId())){
		if(mapaRemedial.get(alumPersonal.getCodigoPersonal()+curso.getCursoId()).equals("A")){
			contiene = true;
		}
	}
%>
		<div class="form-check">
			<input class="form-check-input" name="CursoId<%=curso.getCursoId()%>" type="checkbox" value="<%=curso.getCursoId()%>" <%=contiene?"checked":""%>> 
		  	<label class="form-check-label">
		    	<%=curso.getNombreCurso()%>
		  	</label>
		</div>
<% 	}%>
		<br>
		<button type="submit" class="btn btn-primary">Grabar</button>
	</form>
</div>