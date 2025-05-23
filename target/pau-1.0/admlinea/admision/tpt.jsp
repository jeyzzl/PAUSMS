<%@page import="aca.admision.spring.AdmSolicitud"%>
<%@page import="aca.admision.spring.AdmAcademico"%>
<%@page import="aca.admision.spring.AdmEvaluacion"%>
<%@page import="aca.admision.spring.AdmEvaluacionNota"%>
<%@page import="aca.admision.spring.AdmPrueba"%>


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
	AdmPrueba admPrueba					= (AdmPrueba) request.getAttribute("admPrueba");
	
	String nombreAlumno 				= admSolicitud.getNombre()+" "+admSolicitud.getApellidoPaterno();
	String carreraNombre				= (String) request.getAttribute("carreraNombre");
	boolean tienePrueba					= (boolean) request.getAttribute("tienePrueba");

%>
</head>
<body>
	<div class="container-fluid">
		<h2>Evaluación TPT<small class="text-muted fs-5">( <%=nombreAlumno %> - <%=admAcademico.getCarreraId().equals("00000")?"&nbsp;":admAcademico.getCarreraId()%> - <%=admAcademico.getCarreraId().equals("00000")?"&nbsp;":carreraNombre%> )</small></h2>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="mostrarProceso?Folio=<%=folio%>"><i class="fas fa-arrow-left"></i></a>
			&nbsp;&nbsp;&nbsp;Folio: <span class="badge bg-dark"><%=folio%></span>		
		</div>
	<%if (tienePrueba) { %>
		<h3>Resultado</h3>
		<p><%=admPrueba.getNombre() %></p>
		<h3>Comentarios</h3>
		<p><%=admPrueba.getDescripcion()%></p>
		<h3>Seguimiento</h3>
		<%if(admPrueba.getPruebaId().equals("1") || admPrueba.getPruebaId().equals("2")){ %>
 			 <p>No.</p>
  		<%}else{ %>
  	   	 	 <p>Si.<p>
 			 
  		<%}%>  
	<%} %>
	</div>	
</body>