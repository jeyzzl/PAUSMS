<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.spring.CargaAcademica"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.carga.spring.CargaAlumno"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	String institucion 				= (String)session.getAttribute("institucion");	
	String cargaId	 				= (String) session.getAttribute("cargaId");
	String cursoCargaId				= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	String cursoId					= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");	
	int row							= 0;
	
	CargaAcademica cargaAcademica 	= (CargaAcademica)request.getAttribute("cargaAcademica");
	String maestroNombre 			= (String)request.getAttribute("maestroNombre");
	List<AlumnoCurso> lisAlumnos				= (List<AlumnoCurso>)request.getAttribute("lisAlumnos");	
	HashMap<String,AlumPersonal> mapaAlumnos	= (HashMap<String, AlumPersonal>)request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapTipoCal			= (HashMap<String, String>)request.getAttribute("mapTipoCal");
	HashMap<String,CargaAlumno> mapaCargas		= (HashMap<String, CargaAlumno>)request.getAttribute("mapaCargas");
%>
<div class="container-fluid">
	<h3>Registered Students<small class="text-muted fs-5">( <%=cargaAcademica.getNombreCurso()%> - <%=cargaAcademica.getCodigoPersonal()%> <%=maestroNombre%> )</small></h3>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="activas?CargaId=<%=cargaId%>"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
	</div>
	<div class="alert alert-info">
		Sub. ID: <span class='badge bg-success'><%=cursoCargaId%></span>&nbsp; &nbsp; Base Subject: <%=cargaAcademica.getCursoId()%> - <%=cargaAcademica.getNombreCurso()%>
	</div>
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
		<th><spring:message code="aca.Numero" /></th>
		<th><spring:message code="aca.Matricula" /></th>
		<th><spring:message code="aca.Nombre" /></th>
		<th><spring:message code="aca.Curso" /></th>
		<th>Score</th>
		<th>Score Date</th>
		<th>Score Extra</th>
		<th>Date Extra</th>
		<th>Status</th>
		<th><spring:message code="aca.Comentario" /></th>
		<th>Modality</th>
	</tr>
	</thead>
<%	
	for (AlumnoCurso alumno : lisAlumnos){
		row++;
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(alumno.getCodigoPersonal())){
			AlumPersonal personal = mapaAlumnos.get(alumno.getCodigoPersonal());
			alumnoNombre = personal.getApellidoPaterno()+" "+personal.getApellidoMaterno()+" "+personal.getNombre();
		}

		String tipoCal = "-";
		if (mapTipoCal.containsKey(alumno.getTipoCalId())){
			tipoCal = mapTipoCal.get(alumno.getTipoCalId());
		}
		
		String modo = "Face to Face";
		if (mapaCargas.containsKey(alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId())){
			modo = mapaCargas.get(alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()).getModo();
			if (modo.equals("P")) modo = "Face to Face"; else modo = "Home/Online";
		}
%>
	<tr>		    
	    <td align="CENTER" height="20" ><%= row %></td>    		
	    <td><b><%=alumno.getCodigoPersonal()%></b></td>
	    <td height="20">
	    	<%=alumnoNombre%>  	
	    </td>			
	    <td height="20"><%=alumno.getCursoId()%></td>
	    <td height="20"><%=alumno.getNota()%></td>		    
	    <td height="20"><%=alumno.getFEvaluacion()%></td>
	    <td height="20"><%=alumno.getNotaExtra()%></td>
	    <td height="20"><%=alumno.getFExtra() == null ? "-" : alumno.getFExtra()%></td>
	    <td height="20"><%=tipoCal%></td>
	    <td height="20"> 
<%		
		if (!alumno.getCursoId().equals(alumno.getCursoId2())){
			out.print( alumno.getNombreCurso());
		}
%>
	    </td>
		<td height="20"><%=modo%></td>		
	</tr>
<%	
	}
%>
	</table>
</div>
<!-- fin de estructura -->