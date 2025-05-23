<%@page import="java.util.List"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<% 
	String cargaId			= (String) request.getAttribute("cargaId");
	String carreraId		= (String) request.getAttribute("carreraId");
	String codigoId			= (String) request.getAttribute("codigoId");
	String nombreAlumno		= (String) request.getAttribute("nombreAlumno");
	
	List<AlumnoCurso> lisReprobados = (List<AlumnoCurso>) request.getAttribute("lisReprobados");
%>
<body>
<div class="container-fluid">
	<h2>Materias Reprobadas<small class="text-muted fs-4"> ( <%=cargaId%> - <%=nombreAlumno%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="alumreprobados?CarreraId=<%=carreraId%>&CargaId=<%=cargaId%>"><i class="fas fa-arrow-left"></i></a>
	</div>
	
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
	    <th width="3%"><spring:message code="aca.Numero"/></th>
	    <th width="15%"><spring:message code="aca.Clave"/></th>
	    <th width="15%"><spring:message code="aca.Materia"/></th>
	    <th width="10%"><spring:message code="aca.Creditos"/></th>
	    <th width="10%"><spring:message code="aca.Nota"/></th>
	    <th width="10%"><spring:message code="aca.NotaExtra"/></th>
	</tr>
	</thead>
	<% 	int cont = 0;
		for(AlumnoCurso alumCurso : lisReprobados){
			if(alumCurso.getCodigoPersonal().equals(codigoId)){
				cont++;
	%>
	<tr class="tr2">
	    <td align="center"><%=cont%></td>
	    <td><%=alumCurso.getCursoId()%></td>
	    <td><%=alumCurso.getNombreCurso()%></td>
	    <td><%=alumCurso.getCreditos()%></td>
	    <td><%=alumCurso.getNota()%></td>	    
	    <td><%=alumCurso.getNotaExtra()%></td>
	</tr>
	<% 			
			}
		}%>
	</table>
	</div>
</body>
