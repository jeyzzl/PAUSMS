<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Arrays"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.archivo.spring.ArchDocAlum"%>
<%@page import="aca.archivo.spring.ArchGrupos"%>
<%@page import="aca.archivo.spring.ArchPermisos"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.tit.spring.TitAlumno"%>

<%@ include file= "../../conectadbp.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="archivo" scope="page" class="aca.archivo.ArchivoUtil"/>
<%
	String matricula 						= (String) session.getAttribute("codigoAlumno");
	
	String nombreAlumno						= (String) request.getAttribute("nombreAlumno");
	List<ArchPermisos> listaPermisos 		= (List<ArchPermisos>) request.getAttribute("listaPermisos");

	int con = 1;
%>
<html>
<head>
<!-- 	<link href="css/portalAlumno.css" rel="STYLESHEET" type="text/css"> -->
	<script>
		parent.tabs.document.body.style.backgroundColor=parent.contenidoP.document.bgColor;
	</script>
</head>
<body>	
<div class="container-fluid">
	<h3>Permisos <small class="text-muted">(<%=nombreAlumno%>)</small></h3>
	<div class="alert alert-info d-flex align-items-center">
		<a href="documentos" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<table class="table">
		<thead>
			<tr>
				<th>#</th>
				<th>Autorizo</th>
				<th>Inicia</th>
				<th>Termina</th>
				<th>Descripción</th>
				<th>Estado</th>
			</tr>
		</thead>
		<tbody>
<% 		for(ArchPermisos permiso : listaPermisos){%>
			<tr>
				<td><%=con++%></td>
				<td><%=permiso.getUsuarioAlta()%></td>
				<td><%=permiso.getFechaIni()%></td>
				<td><%=permiso.getFechaLim()%></td>
				<td><%=permiso.getComentario()%></td>
				<td><%=permiso.getEstado().equals("A")?"Activo":"Inactivo"%></td>
			</tr>
<% 		}%>
		</tbody>
	</table>
</div>
<%@ include file= "../../cierradbp.jsp" %>