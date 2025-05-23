<%@page import="aca.admision.spring.AdmSolicitud"%>
<%@page import="aca.admision.spring.AdmAcademico"%>
<%@page import="aca.admision.spring.AdmPasos"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<head>
<%
	String folio				= (String)request.getAttribute("folio");
	String opcion				= (String)request.getAttribute("opcion");	
	String paso					= (String)request.getAttribute("paso");	
	
	AdmAcademico admAcademico	= (AdmAcademico) request.getAttribute("admAcademico");
	AdmSolicitud admSolicitud	= (AdmSolicitud) request.getAttribute("admSolicitud");
	AdmPasos admPasos			= (AdmPasos) request.getAttribute("admPasos");
	
	String nombreAlumno 		=  admSolicitud.getNombre()+" "+(admSolicitud.getApellidoMaterno()==null?"":admSolicitud.getApellidoMaterno())+" "+admSolicitud.getApellidoPaterno();
	String carreraNombre		= (String) request.getAttribute("carreraNombre");
%>
<script type="text/javascript">
</script>
</head>
<body>
<div class="container-fluid">
	<h2>Visto bueno <small>( <%=nombreAlumno %> - <%=admAcademico.getCarreraId().equals("00000")?"&nbsp;":admAcademico.getCarreraId()%> - <%=admAcademico.getCarreraId().equals("00000")?"&nbsp;":carreraNombre%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="mostrarProceso?Folio=<%=folio%>"><spring:message code="aca.Regresar"/></a>
		&nbsp;&nbsp;&nbsp;Folio: <span class="badge badge-inverse"><%=folio%></span>		
	</div>
	<form name="frmVobo" action="grabarvobo" method="post">
		<input type="hidden" name="Folio" value="<%=folio%>">
		<input type="hidden" name="Opcion" value="<%=opcion%>">
		<input type="hidden" name="Paso" value="<%=paso%>">
		<label>Comentario</label>
		<textarea name="Comentario" cols="70" rows="5"><%=admPasos.getComentario()%></textarea><br><br>
<%	if (opcion.equals("A")){%>			
		<button class="btn btn-primary" type="submit">Grabar</button>
<%	} %>			
	</form>
</div>	
</body>