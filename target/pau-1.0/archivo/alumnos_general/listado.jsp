<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "../../conectadbp.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.pg.archivo.ArchGeneral"%>
<%@page import="aca.alumno.AlumPersonal"%>
<jsp:useBean id="archGeneral" scope="page" class="aca.pg.archivo.ArchGeneral"/>
<jsp:useBean id="archGeneralU" scope="page" class="aca.pg.archivo.ArchGeneralUtil"/>
<%
	ArrayList<ArchGeneral> lisAlumnos = archGeneralU.getListAlumnos(conn2, "ORDER BY FECHA, MATRICULA");
%>
<body>
<div class="container-fluid">
	<h1>Alumnos con Documentos sin Asignar</h1>
	<div class="alert alert-info"></div>
	
	<table style="width:70%" class="table table-condensed">
	<tr>
		<th><spring:message code="aca.Numero"/></th>
		<th><spring:message code="aca.Matricula"/></th>
		<th><spring:message code="aca.Nombre"/></th>
		<th><spring:message code="aca.Fecha"/></th>
		<th>Num. Docs</th>
	</tr>
<%
	for(int i = 0; i < lisAlumnos.size(); i++){
		archGeneral = (ArchGeneral) lisAlumnos.get(i);
%>
	<tr class="button" onclick="document.location = '../documentos_alumno/general.jsp?codigoAlumno=<%=archGeneral.getMatricula() %>';">
		<td align="right"><%=i+1 %></td>
		<td align="center"><%=archGeneral.getMatricula() %></td>
		<td><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, archGeneral.getMatricula(), "NOMBRE") %></td>
		<td align="center"><%=archGeneral.getFecha().substring(8) %>/<%=archGeneral.getFecha().substring(5, 7) %>/<%=archGeneral.getFecha().substring(0, 4) %></td>
		<td align="center"><%=ArchGeneral.numImagenes(conn2, archGeneral.getMatricula()) %></td>
	</tr>
<%
	}
%>
	</table>
</div>	
</body>
<%@ include file= "../../cierradbp.jsp" %>
<%@ include file= "../../cierra_enoc.jsp" %>
