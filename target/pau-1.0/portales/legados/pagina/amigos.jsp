<%@ include file= "../../../con_enoc.jsp" %>
<%@ include file= "../id.jsp" %>
<%@ include file= "../../../seguro.jsp" %>
<%@ include file="../../../body.jsp"%>
<%@ include file= "../../../idioma.jsp" %>

<%@page import="java.util.Arrays"%>

<jsp:useBean  id="CompRegistro" class="aca.cultural.CompRegistro" scope="page" />
<jsp:useBean  id="CompRegistroU" class="aca.cultural.CompRegistroUtil" scope="page" />
<link rel="stylesheet" href="../../../bootstrap/css/bootstrap.min.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../../../js/chosen/chosen.css" />
<link href="../../alumno/css/portalAlumno.css" rel="STYLESHEET" type="text/css">
<link rel="stylesheet" href="../bootstrap new/css/bootstrap.css" />

<%

	String direccion 		= request.getRequestURL().toString();
	boolean virtual 		= false;
	if(direccion.contains("academico")){
		virtual = true;
	}
	
	if(!session.getAttribute("codigoAlumno").equals(session.getAttribute("codigoPersonal")) && !session.getAttribute("codigoPersonal").equals("9800308")){
		virtual=false;
	}
	String accion		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	int nAccion			= Integer.parseInt(accion);
	String eventoId 	= request.getParameter("eventoId")==null?"":request.getParameter("eventoId");
	String colorPortal 	= (String) session.getAttribute("colorPortal")==null?"":(String)session.getAttribute("colorPortal");
	
	ArrayList<String> amigos = CompRegistroU.amigos(conEnoc, eventoId, "");
	
%>
<style>
	.chzn-container{
		margin-bottom: 10px !important;
	}
	.filtroAvanzado{
		display: none;
		width: 100%;
	}
</style>
<div class="container-fluid">	
	<h2>Alumnos en el evento</h2>
	<div class="alert alert-info">
		<a href="informacion?eventoId=<%=eventoId %>" align="left" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
		&nbsp;&nbsp;
		<input type="text" class="input-medium search-query" placeholder="Buscar" id="buscar">
			
	</div>
	<table class="table table-condensed table-bordered" id="table">
		<thead>
			<tr>
				<th><h3>#</h3></th>
				<th><h3><spring:message code="aca.Matricula"/></h3></th>
				<th><h3><spring:message code="aca.NombreDelAlumno"/></h3></th>
			</tr>
		</thead>
		<tbody>
<%	
	String codigo	= "";
	String nombre	= "";
	
	for(int i=0; i<amigos.size(); i++){			
		nombre = amigos.get(i).split("@@")[0];
		codigo = amigos.get(i).split("@@")[1];
%>

			<tr>
				<td><h4><%=i+1 %></h4></td>
				<td><h4><%=codigo %></h4></td>				
				<td><h4><%=nombre %></h4></td>
			</tr>
<%
	}
%>
	</tbody>
	</table>     
</div>
<script src="../../../js/jquery-1.9.1.min.js"></script>
<script src="../../../js/search.js"></script>
<script>
	$('#buscar').search();
</script>
<%@ include file= "../../../cierra_enoc.jsp" %>