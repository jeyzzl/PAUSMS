<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.parametros.spring.Parametros"%>
<%@page import="aca.conva.spring.ConvEvento"%>
<%@page import="aca.conva.spring.ConvMateria"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.parametros.spring.Parametros"%>
<%@page import="aca.util.Fecha"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<%
	String nomInstitucion 		= (String)session.getAttribute("institucion");
	String codigoAlumno			= (String)session.getAttribute("codigoAlumno");	
	
	String convalidacionId		= request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");
	String estado				= request.getParameter("Estado");
	
	String modalidad 			= (String)request.getAttribute("modalidad");
	
	String planNombre 			= (String)request.getAttribute("planNombre");
	ConvEvento convEvento 		= (ConvEvento)request.getAttribute("convEvento");
	MapaPlan mapaPlan 			= (MapaPlan)request.getAttribute("mapaPlan");
	Parametros parametros		= (Parametros)request.getAttribute("parametros");
	AlumPersonal alumPersonal	= (AlumPersonal)request.getAttribute("alumPersonal");
	
	String institucion			= convEvento.getInstitucion();
	String programa 			= convEvento.getPrograma();
	
	List<ConvMateria> lisMaterias 			= (List<ConvMateria>)request.getAttribute("lisMaterias");	
	HashMap<String,MapaCurso> mapaMaterias	= (HashMap<String,MapaCurso>)request.getAttribute("mapaMaterias");
%>
<body>
<div class="container-fluid">
	<a class="btn btn-sm btn-primary" href="reporte?ConvalidacionId=<%=convEvento.getConvalidacionId()%>&Estado=<%=estado%>"><i class="icon-arrow-left icon-white"></i></a>
	<table style="width:90%" align="center">
	<tr>
		<td colspan="2">
			<h2 align="center">UNIVERSIDAD DE MONTEMORELOS, A.C.</h2><br>
			<h5 align="center"><%=planNombre%></h5><br>
			<h5 align="center">Reconocimiento de Validez oficial de Estudios del <%=aca.util.Fecha.getFechaOficial(mapaPlan.getFActualiza())%>: <%=mapaPlan.getRvoe()%><br><br> otorgado por el ejecutivo del Estado</h5>
			<br><br><br>
			<p>
				La Dirección de Servicios Escolares <strong>CERTIFICA</strong> que las asignaturas acreditadas en la carrera de <strong><%=programa%></strong> que oferta esta institución,
				por el estudiante <strong><%=alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno()%></strong> son equivalentes al plan de 
				estudios de <strong><%=planNombre%> plan <%=convEvento.getPlanId()%></strong> en el que actualmente se encuentra inscrito el estudiante, conforme al 
				dictamen que se presenta continuación:
			</p>
		</td>
	</tr>					
	<tr>
		<td valign="top" width="50%" colspan="2">
		
			<table style="width:100%"  align="center" class="table table-fullcondensed table-nohover table-fontsmall">			
			<tr>
				<th style="text-align:left" width="10%">FECHA</th>
				<th style="text-align:left" width="30%">MATERIAS TOMADAS PREVIAMENTE</th>
				<th style="text-align:left" width="10%" style="text-align:right">NOTA</th>
				<th style="text-align:left" width="30%">MATERIAS QUE RECIBIR&Iacute;AN EL CR&Eacute;DITO</th>
				<th style="text-align:left" width="10%" style="text-align:right">NOTA</th>
			</tr>
<%
	for(ConvMateria convMateria : lisMaterias){	
		
		String nombreConvalida = "";
		if (mapaMaterias.containsKey(convMateria.getCursoId()) ){
			nombreConvalida = mapaMaterias.get(convMateria.getCursoId()).getNombreCurso();
		}
%>
			<tr>
				<td><%= convMateria.getfNota() %></td>
				<td><%=convMateria.getMateria_O()%></td>
				<td style="text-align:left"><%=convMateria.getNota_O()%></td>						
				<td><%=nombreConvalida%></td>
				<td style="text-align:left"><%=convMateria.getNota_O()%></td>
			</tr>			
<%
	}	
%>
			</table>
			<h5 align="center">
				Las materias faltantes que ampara el plan de estudios vigente, serán inscritas y acreditadas en esta
				institución de acuerdo con la Legislación Interna vigente.
			</h5><br><br>
			<h3 align="center">Montemorelos, Nuevo León, México, 22 de Mayo del </h3><br><br>
			<h4 align="center">Vo. Bo. UM &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 Vo. Bo. S.E.N.L.</h4>
			<br><br><br>
			<table style="width:70%" align="center">
			<tr>
<%	if (modalidad.equals("1") || modalidad.equals("4") ){  %>		
				<td align="center">_______________________________</td>
<% }%>			
				<td align="center">_______________________________</td>
			
			</tr>
			<tr>
<%  if (modalidad.equals("1") || modalidad.equals("4") ){  %>
				<td align="center"><%=parametros.getCertificados()%><br>Director de Servicios Escolares</td>				
<% }%>
				<td align="center">Ing. Adriana Salinas Gaytán
				<br>Directora de Acreditación, Certificación y Control Escolar</td>	
			</tr>
			</table>
		</td>
	</tr>		
	</table>
</div>
</body>          
</html>