<%@page import="aca.admision.spring.AdmSolicitud"%>
<%@page import="aca.admision.spring.AdmAcademico"%>
<%@page import="aca.admision.spring.AdmEvaluacion"%>
<%@page import="aca.admision.spring.AdmEvaluacionNota"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<head>
<%
	String folio						= (String)request.getAttribute("folio");
	String evaluacionId					= (String)request.getAttribute("evaluacionId");
	
	AdmAcademico admAcademico			= (AdmAcademico) request.getAttribute("admAcademico");
	AdmSolicitud admSolicitud			= (AdmSolicitud) request.getAttribute("admSolicitud");
	AdmEvaluacion admEvaluacion			= (AdmEvaluacion) request.getAttribute("admEvaluacion");
	AdmEvaluacionNota evaluacionNota	= (AdmEvaluacionNota) request.getAttribute("evaluacionNota");
	
	String nombreAlumno 				= admSolicitud.getNombre()+" "+admSolicitud.getApellidoPaterno();
	String carreraNombre				= (String) request.getAttribute("carreraNombre");
%>
<script type="text/javascript">
	function borrar(folio,evaluacionId){
		if (confirm("¿Quieres borrar la nota?")) { 
			document.location.href="borrarEvaluacionNota?Folio="+folio+"&EvaluacionId="+evaluacionId;
		}
	}
</script>
</head>
<body>
	<div class="container-fluid">
		<h2><spring:message code="aca.AdmisionEnLinea"/> <small class="text-muted fs-5">( <%=nombreAlumno %> - <%=admAcademico.getCarreraId().equals("00000")?"&nbsp;":admAcademico.getCarreraId()%> - <%=admAcademico.getCarreraId().equals("00000")?"&nbsp;":carreraNombre%> )</small></h2>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="mostrarProceso?Folio=<%=folio%>"><i class="fas fa-arrow-left"></i></a>
			&nbsp;&nbsp;&nbsp;Folio: <span class="badge bg-dark"><%=folio%></span>		
			&nbsp;&nbsp;&nbsp;Evaluación: <span class="badge bg-dark"><%=admEvaluacion.getEvaluacionNombre()%></span>		
		</div>
		<form action="grabaEvaluacionNota" method="post">
			<input type="hidden" name="Folio" value="<%=folio%>">
			<input type="hidden" name="Id" value="<%=evaluacionId%>">
			<label>Nota</label>
			<input type="text" name="Nota" value="<%=evaluacionNota.getNota()%>">
			<br><br>
			<label>Comentario</label>
			<textarea name="Comentario" rows="4" cols="50"><%=evaluacionNota.getComentario()%></textarea>
			<br><br>
			<button class="btn btn-primary" type="submit">Grabar</button>
<% 		if(!evaluacionNota.getFolio().equals("")){%>
			<a class="btn btn-danger" onclick="borrar('<%=folio%>','<%=evaluacionId%>');">Borrar</a>
<% 		}%>
		</form>
	</div>	
</body>