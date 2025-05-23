<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.archivo.spring.ArchDocAlum"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	List<ArchDocAlum> lisAlumnos				= (List<ArchDocAlum>)request.getAttribute("lisAlumnos");
	HashMap<String,String> mapaAlumnos 			= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapaDocumentos 		= (HashMap<String,String>)request.getAttribute("mapaDocumentos");
%>
<body>
<div class="container-fluid">
	<h2>Students with incorrect documents</h2>
	<div class="alert alert-info d-flex align-items-center">
		<a href="menu" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<table class="table table-sm table-bordered">
	<thead>
		<tr>
			<th class="text-center"><spring:message code="aca.Numero"/></th>
			<th><spring:message code="aca.Matricula"/></th>
			<th><spring:message code="aca.Nombre"/></th>		
			<th>Document</th>
		</tr>
	</thead>
	<tbody>	
<%
	int i = 0;
	for(ArchDocAlum alumno : lisAlumnos){
		i++;
		
		String nombreAlumno = "";
		if(mapaAlumnos.containsKey(alumno.getMatricula())){
			nombreAlumno = mapaAlumnos.get(alumno.getMatricula());
		}
		
		String nombreDocumento = "";
		if(mapaDocumentos.containsKey(alumno.getIdDocumento())){
			nombreDocumento = mapaDocumentos.get(alumno.getIdDocumento());
		}
%>
		<tr>		
			<td class="text-center"><%=i%></td>
			<td class="text-left"><%=alumno.getMatricula()%></td>
			<td><%=nombreAlumno%></td>		
			<td><%=nombreDocumento%></td>
		</tr>
<%
	}
%>
	</tbody>
	</table>
</div>	
</body>
