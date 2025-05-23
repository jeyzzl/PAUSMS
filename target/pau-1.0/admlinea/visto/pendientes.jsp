<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmSolicitud"%>
<%@page import="aca.admision.spring.AdmUsuario"%>
<%@page import="aca.admision.spring.AdmPasos"%>
<%@page import="aca.admision.spring.AdmAcademico"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<html>
<head>		
</head>
<%
// 	List<AdmPasos> lista 						= (List<AdmPasos>)request.getAttribute("lista");
	List<AdmSolicitud> lisPendientes 			= (List<AdmSolicitud>)request.getAttribute("lisPendientes");
	
	HashMap<String, AdmUsuario> mapaUsuarios 	= (HashMap<String, AdmUsuario>)request.getAttribute("mapaUsuarios");
	HashMap<String, AdmAcademico> mapaAcademico = (HashMap<String, AdmAcademico>)request.getAttribute("mapaAcademico");
	HashMap<String,CatCarrera> mapaCarreras		= (HashMap<String, CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,String> mapaEmpleado 		= (HashMap<String,String>)request.getAttribute("mapaEmpleados");	
	HashMap<String,AdmPasos> mapaPasos 			= (HashMap<String,AdmPasos>) request.getAttribute("mapaPasos"); 

%>
<body>
<div class="container-fluid">
	<h2>Alumnos con visto bueno confirmado</h2>
	<table class="table table-sm table-bordered">
	<tr class="table-info">
		<th width="3%">#</th>
		<th width="3%">Ops.</th>
		<th width="3%">Folio</th>
		<th width="22%">Nombre</th>
		<th width="25%">Carrera</th>
		<th width="15%">Coodinador</th>
		<th width="15%">Autorizo</th>
		<th width="14%">Fecha</th>
	</tr>
<%		
	int cont = 1;
	for(AdmSolicitud solicitud : lisPendientes){
		String nombreSolicitante 	= "-";
		String carreraId			= "-";
		String carrera				= "-";
		String carreraCompleta		= "-";
		String coordinador			= "-";
		String autorizo				= "-";
		String fecha				= "-";
		
		if (mapaAcademico.containsKey(solicitud.getFolio())){
			AdmAcademico academico = mapaAcademico.get(solicitud.getFolio());
			
			carreraId = academico.getCarreraId();
			if (mapaCarreras.containsKey(academico.getCarreraId())){
				CatCarrera car 		= mapaCarreras.get(academico.getCarreraId());
				carrera 			= car.getNombreCorto();
				carreraCompleta 	= car.getNombreCarrera();
				coordinador 		= car.getCodigoPersonal();
			}
		}
		
		nombreSolicitante = "-";
		if (mapaUsuarios.containsKey(solicitud.getUsuarioId())){
			nombreSolicitante = mapaUsuarios.get(solicitud.getUsuarioId()).getNombre()+" "+mapaUsuarios.get(solicitud.getUsuarioId()).getApellidoPaterno()+" "+mapaUsuarios.get(solicitud.getUsuarioId()).getApellidoMaterno();
		}
		
		if (mapaPasos.containsKey(solicitud.getFolio()+"2")){
			autorizo 	= mapaPasos.get(solicitud.getFolio()+"2").getUsuario();
			fecha 		= mapaPasos.get(solicitud.getFolio()+"2").getFecha();
		}	
		
		if (mapaEmpleado.containsKey(autorizo)){
			autorizo = mapaEmpleado.get(autorizo);
		}	

		if (mapaEmpleado.containsKey(coordinador)){
			coordinador = mapaEmpleado.get(coordinador);
		}	
%>
	<tr>
		<td><%=cont++%></td>
		<td><a class="fas fa-trash-alt" href="quitarPendiente?Folio=<%=solicitud.getFolio()%>" title="Quitar"></a></td>
		<td><%=solicitud.getFolio()%></td>
		<td><%=nombreSolicitante%></td>
		<td><%=carreraCompleta%></td>
		<td><%=coordinador%></td>
		<td><%=autorizo%></td>
		<td><%=fecha%></td>
	</tr>
<% 	
	} 
%>
	</table>
</div>
</body>
</html>