<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>

<jsp:useBean id="AlumnoCursoU" scope="page" class="aca.vista.AlumnoCursoUtil"/>
<jsp:useBean id="CursoUtil" scope="page" class="aca.plan.CursoUtil"/>

<%	
	java.text.DecimalFormat formato0	= new java.text.DecimalFormat("###,##0;-###,##0");
	java.text.DecimalFormat formato1	= new java.text.DecimalFormat("###,##0.0;-###,##0.0");

	String cargaId		= (String) request.getAttribute("cargaId");
	String planId 		= request.getParameter("planid");
	String todos 		= request.getParameter("todos")==null?"N":request.getParameter("todos");
	
	List<String> listAlumnos 		= (List<String>) request.getAttribute("listAlumnos");
	List<MapaCurso> lisMapaCurso 	= (List<MapaCurso>) request.getAttribute("lisMapaCurso");
	
	HashMap<String,CatModalidad> mapModalidad	= (HashMap<String, CatModalidad>) request.getAttribute("mapModalidad");
	/* HashMap del los datos del plan de estudios de los alumnos*/
	HashMap<String,AlumPlan> mapAlumPlan = (HashMap<String,AlumPlan>) request.getAttribute("mapAlumPlan");

	HashMap<String,HashMap<String, String>> mapaAlumnoCurso = (HashMap<String,HashMap<String, String>>) request.getAttribute("mapaAlumnoCurso");
	HashMap<String,String> mapaNombres 						= (HashMap<String,String>) request.getAttribute("mapaNombres");
	HashMap<String,String> mapaModaTodos 					= (HashMap<String,String>) request.getAttribute("mapaModaTodos");
	HashMap<String,String> mapaModaCarga 					= (HashMap<String,String>) request.getAttribute("mapaModaCarga");
	
	String nombre		= "";	
	String modalidad	= "";
	
	float nota = 0;
	
	int con = 1;
	
%>
<div class="container-fluid">
	<h2>Students in <b><%=planId%></b></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="carreras"><i class="fas fa-arrow-left"></i></a>
	</div>
<!-- 	<h2>Contentrado de calificaciones</h2> -->
<%	if (listAlumnos.size() > 0 ){ %>
	<table class="table table-bordered">
	<thead class="table-info">
		<tr>
			<th>#</th>
			<th>Student ID</th>
			<th>Name</th>
			<th>Modality</th>
			<th colspan="<%=lisMapaCurso.size()%>">Subjects</th>
		</tr>
	</thead>
		<tr>
			<th></th>
			<th></th>
			<th></th>
			<th></th>
<%	for(MapaCurso mapCurso : lisMapaCurso){%>
			<th style="writing-mode: tb-rl; <%=Integer.parseInt(mapCurso.getCiclo()) % 2 == 0 ? "background-color: #f4f7f3;" : "background-color: white;"%>"><%=mapCurso.getNombreCurso()%></th>
<%	}%>
		</tr>
<%	for (String codigoAlumno : listAlumnos){%>
		<tr>
<%	
		nombre = "";
		if(mapaNombres.containsKey(codigoAlumno)){
			nombre = mapaNombres.get(codigoAlumno);
		}
		
		modalidad = "";
		
		if (todos.equals("S")){
			if(mapaModaTodos.containsKey(codigoAlumno)){
				modalidad = mapaModaTodos.get(codigoAlumno);
			}
			if (mapModalidad.containsKey(modalidad)) {
				modalidad = mapModalidad.get(modalidad).getNombreModalidad();
			}
		}else{
			if(mapaModaCarga.containsKey(codigoAlumno)){
				modalidad = mapaModaCarga.get(codigoAlumno);
			}
			if (mapModalidad.containsKey(modalidad)) {
				modalidad = mapModalidad.get(modalidad).getNombreModalidad();
			}
		}
%>
			<td><%=con++%></td>
			<td><%=codigoAlumno%></td>
			<td><%=nombre%></td>
			<td><%=modalidad%></td>
<%		for(MapaCurso curso : lisMapaCurso){ 
			String escala = "10";
			if (mapAlumPlan.containsKey(codigoAlumno)){
				escala = mapAlumPlan.get(codigoAlumno).getEscala();
			}
			
			HashMap<String, String> mapaCalificaciones 	= new HashMap<String, String>(); 
			if (mapaAlumnoCurso.containsKey(codigoAlumno)) {
				mapaCalificaciones = mapaAlumnoCurso.get(codigoAlumno);
			}
			
			String muestraNota 	= "0";
			String color		= "";

			if(mapaCalificaciones.get(curso.getNombreCurso()) != null){
				if(mapaCalificaciones.get(curso.getNombreCurso()).equals("B")){
					muestraNota = "B";
					color = "#b02020";
				}else if(mapaCalificaciones.get(curso.getNombreCurso()).contains("*")){
						muestraNota = mapaCalificaciones.get(curso.getNombreCurso());
						muestraNota = muestraNota.replace("*", "");
						color = "#0ca80e";
					if (mapaCalificaciones.containsKey(curso.getNombreCurso())) {
						nota = Float.parseFloat(muestraNota);
						if (escala.equals("10")){
							nota = nota / 10;
						}
						muestraNota = String.valueOf(nota);
					}
				}else{
					if (mapaCalificaciones.containsKey(curso.getNombreCurso())) {
						nota = Float.parseFloat(mapaCalificaciones.get(curso.getNombreCurso()));
						if (escala.equals("10")){
							nota = nota / 10;
						}
						muestraNota = String.valueOf(nota);
					}
				}
			}
%>
			<td style="color: <%=color%>" class="text-center">
<%
			if(muestraNota.equals("0") || muestraNota.equals("0.0")){
%>
				<i class="fas fa-times-circle"></i>
<%
			}else {
%>
				<span class="badge rounded-pill bg-success"><%=muestraNota%></span>
<%
			}
%>
			</td>
<%		}%>
		</tr>
<%	}%>
	</table>
<%	}else{%>
	<div class="alert">No students found!</div> 
<% 	}%>
</div>