<%@ page import="java.util.List"%>
<%@ page import="java.text.*"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="../alumno/menu.jsp"%>

<head></head>
<%
	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");	
	String documentoId			= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");	
	
	String matricula 			= (String) request.getAttribute("matricula");	
	String periodoId 			= (String) request.getAttribute("periodoId");
	
	String nombreDocumento		= "Carta Responsiva";
	if (documentoId.equals("2")) nombreDocumento = "comprobante llegada";
	if (documentoId.equals("3")) nombreDocumento = "comprobante vacuna";
	if (documentoId.equals("4")) nombreDocumento = "Prueba PCR";
%>
<body>
<div class="container-fluid">
	<div class="alert alert-success">
		<a href="retorno"><i class="fas fa-arrow-left"></i></a>&nbsp;
		<span class="badge rounded-pill bg-dark">1E</span>
		Asuntos previos - Subir Documento <small class="text-muted"> ( <%=codigoAlumno%> - <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno()%> <%=alumPersonal.getApellidoMaterno()%> - <%=nombreDocumento%> )</small>
	</div>	
<% if(!mensaje.equals("-")){%>
	<div class="aler alert-success"><%=mensaje%></div>
<% }%>
<%
	if(matricula.equals(codigoPersonal)){
%>
	<form style="padding-left: 20px; padding-right: 20px;" name="frmDocumento" id="frmDocumento" enctype="multipart/form-data" action="grabarRetorno" method="post">
		<input type="hidden" name="Matricula" value="<%=matricula%>">
		<input type="hidden" name="PeriodoId" value="<%=periodoId%>">
		<input type="hidden" name="DocumentoId" value="<%=documentoId%>">		
	  	<span class="btn btn-file">
		  	<input type="file" id="archivo" name="archivo" required="required"/>
	  	</span>
		<button type="submit" id="btnGuardar" class="btn btn-warning"><i class="glyphicon glyphicon-open"></i> Subir</button>
	</form>
<%
	}
%>	
</div>
</body>