<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.vista.spring.AlumnoCurso"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->

<%		
 	String codigoPersonal			= (String) session.getAttribute("codigoEmpleado");	
	String maestro 					= request.getParameter("Maestro");
	String materia 					= request.getParameter("Materia");
	
	List<AlumnoCurso> lisAlumnos				= (List<AlumnoCurso>)request.getAttribute("lisAlumnos");	
	HashMap<String,AlumPersonal> mapaAlumnos	= (HashMap<String,AlumPersonal>)request.getAttribute("mapaAlumnos");
	int row=0;	
%>
<div class="container-fluid">
	<h2>Student List <small class="text-muted h5"><%=maestro%> - <%=codigoPersonal%> - <%=materia%></small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="cursos"><i class="fas fa-arrow-left"></i></a>
	</div>	
	<table align="CENTER" class="table table-sm table-bordered">
	<thead class="table-info">
	<tr align="left" style="font-size: 10pt;"> 
		<th width="10%"><b><spring:message code="aca.Numero"/></b></th>
	    <th width="10%"><b><spring:message code="aca.Matricula"/></b></th>
	    <th width="50%"><b><spring:message code="aca.Nombre"/></b></th>
	    <th width="10%"><b><spring:message code="aca.Plan"/></b></th>
	    <th width="10%" class="text-end"><b><spring:message code="aca.Nota"/></b></th>
	    <th width="10%" class="text-end"><b><spring:message code="aca.Extra"/></b></th>
	</tr>
	</thead> 
<%
	for (AlumnoCurso ac : lisAlumnos){
		if (!ac.getTipoCalId().equals("M")){ 
			row++;
			String nombreAlumno = "-";
			if (mapaAlumnos.containsKey(ac.getCodigoPersonal())){
				nombreAlumno = mapaAlumnos.get(ac.getCodigoPersonal()).getNombre()+" "+mapaAlumnos.get(ac.getCodigoPersonal()).getApellidoPaterno()+" "+mapaAlumnos.get(ac.getCodigoPersonal()).getApellidoMaterno();
			}
%>
	  <tr> 
	    <td><%=row%></td>
	    <td><%=ac.getCodigoPersonal()%></td>
	    <td><%=nombreAlumno%></td>
	    <td><%=ac.getCursoId().substring(0,8)%></td>
	    <td class="text-end"><%=ac.getNota() %></td>
	    <td class="text-end"><%=ac.getNotaExtra() %></td>
	  </tr>
<%		}// fin de tipoCal!= 'M'
	}%>
	</table>
<!-- fin de estructura -->