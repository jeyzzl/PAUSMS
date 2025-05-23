<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import="aca.bitacora.spring.BitEstado"%>
<%@ page import="aca.bitacora.spring.BitTramite"%>
<%@ page import="aca.bitacora.spring.BitTramiteAlumno"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<%
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");
	String alumnoNombre			= (String) request.getAttribute("alumnoNombre"); 

	String accion 				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String folio				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String tramiteId 			= request.getParameter("TramiteId")==null?"0":request.getParameter("TramiteId");
	String mensaje 				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	String hoy 					= aca.util.Fecha.getHoy();	
	
	// Lista de tramites del alumno
	List<BitTramiteAlumno> lisTramites 			= (List<BitTramiteAlumno>) request.getAttribute("lisTramites");
	
	// Mapa del catalogo de tramites
	HashMap<String,BitTramite> mapaTramites 	= (HashMap<String,BitTramite>)request.getAttribute("mapaTramites");
		
	// Mapa del catalogo de estados
	HashMap<String,String> mapaEstados			= (HashMap<String,String>)request.getAttribute("mapaEstados");
		
	// Mapa que cuenta el numero de folios dependientes de un folio principal
	HashMap<String,String> mapaHijos 			= (HashMap<String,String>)request.getAttribute("mapaHijos");
	
	// Mapa que cuenta el numero de folios dependientes de un folio principal
	HashMap<String,String> mapaEtiquetas 		= (HashMap<String,String>)request.getAttribute("mapaEtiquetas");
	
	HashMap<String,String> mapaAreas			= (HashMap<String,String>)request.getAttribute("mapaAreas");
%>
<div class="container-fluid">
	<h2>BSE / Mostrador<small class="text-muted h4">(<%=codigoAlumno%>-<%=alumnoNombre%>)</small></h2>
	<div class="alert alert-info">
		<a href="nuevaSolicitud" style="text-align:center;" class="btn btn-primary"><i class="fas fa-plus"></i> Nueva solicitud</a>
	</div>
<%	if (!mensaje.equals("-")){ %>	
	<div class="alert alert-info"><%=mensaje%></div>
<%	} %>	
	<span><h3>Historial de solicitudes&nbsp;&nbsp;<small class="text-muted"><%=!mensaje.equals("-")?mensaje:""%></small></h3></span>
	<table class="table table-bordered table-stripped">		
		<thead>					
		<tr class="table-info">
			<th>&nbsp;</th>
			<th>#</th>
			<th>Folio</th>
			<th>Area</th>
			<th>Trámite/Servicio</th>
			<th>Fecha inicio</th>
			<th>Fecha término</th>
			<th class="right">Mín.</th>
			<th class="right">Máx.</th>
			<th class="right">Prom.</th>						
			<th class="right">Real</th>
			<th>Estado</th>
			<th>Tikets</th>			
		</tr>
	</thead>	
	<tbody>	
<%		
	int row = 0;
	for (BitTramiteAlumno tramite : lisTramites){
		row++;
		
		int numEtiquetas = 0;
		if (mapaEtiquetas.containsKey(tramite.getFolio())){
			numEtiquetas = Integer.parseInt(mapaEtiquetas.get(tramite.getFolio()));
		}
		
		//Cantidad de tramites hijos del tramite actual
		String dependientes = "0";
		if (mapaHijos.containsKey(tramite.getFolio()) ){
			 dependientes = mapaHijos.get(tramite.getFolio());
		}
		
		String tramiteNombre 	= "-";
		String minimo			= "0";
		String maximo			= "0";
		String promedio			= "0";		
		if (mapaTramites.containsKey(tramite.getTramiteId())){			
			tramiteNombre 	= mapaTramites.get(tramite.getTramiteId()).getNombre();
			minimo 			= mapaTramites.get(tramite.getTramiteId()).getMinimo();
			maximo 			= mapaTramites.get(tramite.getTramiteId()).getMaximo();
			promedio 		= mapaTramites.get(tramite.getTramiteId()).getPromedio();		
		}
		String colorTramite = "bg-dark";
		if (!tramite.getAreaOrigen().equals("0")){
			colorTramite = "bg-success";
		}
		
		//Cuenta la duración en dias del tramite
		int dias = 0;
		if (tramite.getFechaFinal()==null){
			dias = aca.util.Fecha.diasEntreFechas(tramite.getFechaInicio(), hoy);
		}else{
			dias = aca.util.Fecha.diasEntreFechas(tramite.getFechaInicio(), tramite.getFechaFinal());
		}
		aca.util.Fecha.diasEntreFechas(tramite.getFechaInicio(), hoy);
				
		String estadoNombre = "-";
		if (mapaEstados.containsKey(tramite.getEstado())){
			estadoNombre = mapaEstados.get(tramite.getEstado());
		}
		
		String turnar = "Mesa de entrada";
		if (!tramite.getAreaOrigen().equals("0")){
			if(mapaAreas.containsKey(tramite.getAreaOrigen())){
				turnar = mapaAreas.get(tramite.getAreaOrigen());
			}			
		}
		
		String areaNombre = "-"; 
		if(mapaAreas.containsKey(tramite.getAreaId())){
			areaNombre = mapaAreas.get(tramite.getAreaId());
		}
%>
		<tr>
			<td>
		<% 
			if (numEtiquetas <= 1){
		%>
			<a href="javascript:Borrar('<%=tramite.getFolio()%>')"><i class="fas fa-times"></i></a></td>
		<%				
			}	
		%>		
			<td><%=row%></td>
			<td>						
				<a href="etiquetas?Folio=<%=tramite.getFolio()%>"><span class="badge <%=colorTramite%>"><%=tramite.getFolio()%></span></a>
			</td>
			<td><%=areaNombre%></td>
			<td>
				<a href="etiquetas?Folio=<%=tramite.getFolio()%>">
				<%=tramiteNombre%>
				</a>
			</td>
			<td><%=tramite.getFechaInicio() %></td>
			<td><%=tramite.getFechaFinal()==null?"-":tramite.getFechaFinal()%></td>
			<td class="right"><%=minimo%></td>
			<td class="right"><%=maximo%></td>
			<td class="right"><%=promedio%></td>
			<td class="right"><%=dias%></td>
			<td><%=estadoNombre%></td>
			<td><%=numEtiquetas%></td>			
		</tr>
<%		
	}
%>		
	</tbody>
	</table>
</div>
<script type="text/javascript">
	function Borrar(folio){
		if (confirm("¿Deseas eliminar éste folio?")){
			document.location.href="borrarFolio?Folio="+folio;	
		}		
	}	
</script>