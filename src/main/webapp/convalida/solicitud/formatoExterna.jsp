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
		<td colspan="2">
			<h2>ANEXO DE RESOLUCIÓN FOLIO: 019E9S4170000416</h2>
			<h4>NOMBRE ALUMNO: <%=alumPersonal.getNombreLegal()%></h4>
			<br><br><br>
			<h4 align="center">Tabla de equiparación</h4><br>
			<p>
				Materias de la Carrera de <strong><%=programa%></strong>, cursadas en los periodos de <%=periodo%>, en virtud de que las siguientes <strong><%=lisMaterias.size()%></strong> asignaturas
				resultaron equiparables respecto de la <strong><%=planNombre%></strong>, que imparte la <strong>Universidad de Montemorelos:</strong>
			</p><br>
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
		
		String cursoNombre = "-";
		if (mapaMaterias.containsKey(convMateria.getCursoId()) ){
			cursoNombre = mapaMaterias.get(convMateria.getCursoId()).getNombreCurso();
		}		
		String nota 	= convMateria.getNota_O()==null?"":convMateria.getNota_O();		
		String creditos = convMateria.getCreditos_O()==null?"":convMateria.getCreditos_O();
		String cursoOrigen = convMateria.getMateria_O()==null?"":convMateria.getMateria_O();			
%>
			<tr>
				<td><%= convMateria.getfNota() %></td>
				<td><%=cursoOrigen %></td>
				<td style="text-align:left"><%=nota %></td>						
				<td><%=cursoNombre%></td>
				<td style="text-align:left"><%=nota %></td>
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