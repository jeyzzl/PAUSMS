<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%-- <jsp:useBean id="archGeneral" scope="page" class="aca.pg.archivo.ArchGeneral"/> --%>
<%-- <jsp:useBean id="archGeneralU" scope="page" class="aca.pg.archivo.ArchGeneralUtil"/> --%>
<%
	List<String> lisAlumnos		    		= (List<String>)request.getAttribute("lisAlumnos");
	HashMap<String,String> mapaImagenes 	= (HashMap<String,String>)request.getAttribute("mapaImagenes");
	HashMap<String,String> mapaAlumnos 		= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
%>
<body>
<div class="container-fluid">
	<h1>Students with Unassigned Documents</h1>
	<div class="alert alert-info d-flex align-items-center">
		<a href="menu" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<table class="table table-sm table-bordered">
	<tr>
		<th><spring:message code="aca.Numero"/></th>
		<th><spring:message code="aca.Op"/></th>
		<th><spring:message code="aca.Matricula"/></th>
		<th><spring:message code="aca.Nombre"/></th>		
		<th>Num. Doc.</th>
	</tr>
<%
	int i = 0;
	for(String alumno : lisAlumnos){
		i++;
		
		String nombreAlumno = "-";
		if(mapaAlumnos.containsKey(alumno)){
			nombreAlumno = mapaAlumnos.get(alumno);
		}
		
		String numDocumentos = "-";
		if(mapaImagenes.containsKey(alumno)){
			numDocumentos = mapaImagenes.get(alumno);
		}
%>
	<tr class="button" onclick="document.location = '../documentos_alumno/general?codigoAlumno=<%=alumno%>';">		
		<td align="right"><%=i%></td>
		<td align="right">
<%		if(nombreAlumno.equals("-")){%>
			<a href="borrarImagenes?Matricula=<%=alumno%>">Delete</a>
<%		} %>					
		</td>
		<td align="center"><%=alumno%></td>
		<td><%=nombreAlumno%></td>		
		<td align="center"><%=numDocumentos%></td>
	</tr>
<%
	}
%>
	</table>
</div>	
</body>
