<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.conva.spring.ConvEvento"%>
<%@page import="aca.conva.spring.ConvMateria"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.parametros.spring.Parametros"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

</head>
<%
	String nomInstitucion 	= (String)session.getAttribute("institucion");
	String codigoAlumno		= (String)session.getAttribute("codigoAlumno");
	
	String convalidacionId	= request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");	
	String estado			= request.getParameter("Estado");
	
	String modalidad 			= (String)request.getAttribute("modalidad");
	String planNombre 			= (String)request.getAttribute("planNombre");
	ConvEvento convEvento 		= (ConvEvento)request.getAttribute("convEvento");	
	Parametros parametros		= (Parametros)request.getAttribute("parametros");
	AlumPersonal alumPersonal	= (AlumPersonal)request.getAttribute("alumPersonal");
	
	String planId			= convEvento.getPlanId();
	String periodo			= convEvento.getPeriodo()==null?"X":convEvento.getPeriodo();		
	String institucion		= convEvento.getInstitucion();
	String programa 		= convEvento.getPrograma();
	String fNota			= "";		
	String mensaje			= "";
	String alert			= "";
	
	// Lista de materias
	List<ConvMateria> lisMaterias 			= (List<ConvMateria>)request.getAttribute("lisMaterias");	
	// Mapa de materias
	HashMap<String,MapaCurso> mapaMaterias 	= (HashMap<String,MapaCurso>)request.getAttribute("mapaMaterias");	
%>
<body>
	<div class="container-fluid">
		<a class="btn btn-sm btn-primary" href="reporte?ConvalidacionId=<%=convEvento.getConvalidacionId()%>&Estado=<%=estado%>"><i class="icon-arrow-left icon-white"></i></a>
		<table style="width:90%" align="center">
		<tr>
			<td colspan="2"><br><br><br><br><br>
				<strong>NOMBRE DEL INTERESADO: <%=alumPersonal.getNombreLegal()%></strong>
				<p><strong>INSTITUCIÓN DE PROCEDENCIA: <%=convEvento.getInstitucion()%></strong>, CORRESPONDIENTE AL PLAN DE ESTUDIOS DE <%=convEvento.getPrograma()%>,
				DURANTE LOS PERÍODOS ESCOLARES <%=convEvento.getPeriodo()%> SEGÚN  DOCUMENTACIÓN INTEGRADA EN ESTA DIRECCIÓN GENERAL, CON LA QUE SE HIZO LA EQUIPARACIÓN,
				Y LE TIENE POR ACREDITADOS, EN LA UNIVERSIDAD DE MONTEMORELOS, <%=planId%></p><br><br><br><br><br><br>
			</td>
		</tr>			
		<tr>
			<td align="center">MONTERREY, NUEVO LEÓN, A 30 DE AGOSTO DE 2017</td>
		</tr>
		</table><br><br><br><br><br>
	</div>
</body>          
</html>