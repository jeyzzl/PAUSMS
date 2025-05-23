<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.mentores.spring.MentContacto"%>
<%@page import="aca.mentores.spring.MentMotivo"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%
	String periodoId		= (String) request.getAttribute("periodoId");
	String fecha 			= (String) request.getAttribute("fecha");
	String carreraId		= (String) request.getAttribute("carreraId");
	String mentorId			= (String) request.getAttribute("mentorId");
	String sFacultad		= (String) request.getAttribute("sFacultad");
	String alumFac			= (String) request.getAttribute("alumFac");
	String nombreCarrera	= (String) request.getAttribute("nombreCarrera");
	String nombreFacultad	= (String) request.getAttribute("nombreFacultad");
	String nombreMaestro	= (String) request.getAttribute("nombreMaestro");
	
	List<MentContacto> lisMenAlumno = (List<MentContacto>) request.getAttribute("lisMenAlumno");
	
	HashMap<String,MentMotivo> mapMotivo 			= (HashMap<String, MentMotivo>) request.getAttribute("mapMotivo");
	HashMap<String,CatCarrera> mapCarrera 			= (HashMap<String, CatCarrera>) request.getAttribute("mapCarrera");
	HashMap<String,String> getMapaInscritos			= (HashMap<String, String>) request.getAttribute("getMapaInscritos");
	HashMap<String,AlumPersonal> mapMentorContacto 	= (HashMap<String, AlumPersonal>) request.getAttribute("mapMentorContacto");
%>

<link href="print.css" rel="stylesheet" type="text/css" media="print">

<div class="container-fluid">

	<h2><%=mentorId %> | <%=nombreMaestro%> </h2>
	
	<div class="alert alert-info">
		<%=nombreCarrera %> | <%=nombreFacultad%> 
	</div>

	<div class="alert alert-info">
		<a class="btn btn-primary" href="mentor?carrera=<%=carreraId %>"><i class="fas fa-arrow-left"></i></a>
	</div>

	<table id="table" class="table table-sm table-bordered">
		<thead class="table-info">	 
		<tr>
			<th>#</th>
			<th>Enrollment ID</th>
			<th>Student</th>
			<th>Degree</th>
			<th>Date</th>
			<th>Comment</th>
			<th>Type</th>
			<th>Reason</th>
			<th>Status</th>
		</tr>
		</thead>
	<%
		String tipo = "";
		int cont = 1;
		for(MentContacto alumnos : lisMenAlumno){
			String nombreAlumno = "";
			boolean inscrito 	= false;
			if(mapCarrera.containsKey(alumnos.getCarreraId())){
				nombreCarrera = mapCarrera.get(alumnos.getCarreraId()).getNombreCarrera();
			}
			
			if(getMapaInscritos.containsKey(alumnos.getCodigoPersonal())){
				inscrito = true;
			}
			
			if(mapMentorContacto.containsKey(alumnos.getCodigoPersonal())){
				nombreAlumno = mapMentorContacto.get(alumnos.getCodigoPersonal()).getNombre();
			}
			
			if(alumnos
					.getTipo().equals("E")){
				tipo = "Interview";
				//System.out.println(tipo);
			} else if(alumnos.getTipo().equals("A")){
				tipo = "Assembly";
				//System.out.println(tipo);
			} else if(alumnos.getTipo().equals("R")){
				tipo = "Meeting";
				//System.out.println(tipo);
			} else{
				tipo = "Visit";
				//System.out.println(tipo);
			}
			
			String motivos[] = alumnos.getMotivoId().split(",");
	%>
		<tr>
			<td><%=cont %></td>
		  	<td class="ayuda alumno <%=alumnos.getCodigoPersonal() %>"><%=alumnos.getCodigoPersonal()%></td>
			<td><%=nombreAlumno%></td>
			<td><%=nombreCarrera%></td>
			<td><%=alumnos.getFechaContacto()%></td>
			<td><%=alumnos.getComentario()%></td>
			<td><%=tipo%></td>
			<td>
<% 		
			for (String mot : motivos){						
				if(mapMotivo.containsKey(mot)){
					
					out.print("*"+mapMotivo.get(mot).getMotivoNombre()+" ");
				}
			}
%>				
			</td>
			<td><%=inscrito ? "Registered" : "Not Registered" %></td>
		</tr>
	<%
			cont++;
		}
	%>
	</table>

</div>