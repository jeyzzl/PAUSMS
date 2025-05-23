<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.bitacora.spring.BitTramiteAlumno"%>
<%@page import="aca.bitacora.spring.BitTramite"%>
<%@page import="aca.bitacora.spring.BitSolicitud"%>

<%-- <jsp:include page="../menu.jsp" /> --%>
<%@ include file= "menu.jsp" %>
<%
	java.text.DecimalFormat formato 	= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	String codigoAlumno					= (String) request.getAttribute("matricula");
	String accion						= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String folio						= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String nombreAlumno					= (String)request.getAttribute("nombreAlumno");
	String hoy 							= (String)request.getAttribute("hoy");	
	
	//int numEtiquetas	= (int)request.getAttribute("numEtiquetas");
	String mensaje 	= "-";
	// Lista de tramites del alumno
	List<BitTramiteAlumno> lisTramites		= (List<BitTramiteAlumno>) request.getAttribute("lisTramites");

	List<BitSolicitud> lisSolicitudes		= (List<BitSolicitud>) request.getAttribute("lisSolicitudes");
	
	// Mapa del catalogo de tramites
	HashMap<String, BitTramite> mapaTramite	= (HashMap<String, BitTramite>)request.getAttribute("mapaTramite");
		
	// Mapa del catalogo de estados
	HashMap<String, String> mapaEstado		= (HashMap<String, String>) request.getAttribute("mapaEstado");
	
	// Mapa que cuenta el numero de folios dependientes de un folio principal
	HashMap<String, String> mapaHijos		= (HashMap<String, String>) request.getAttribute("mapaHijos");
	
	// Mapa que cuenta el numero de etiquetas
	HashMap<String, String> mapaEtiquetas					= (HashMap<String, String>) request.getAttribute("mapaEtiquetas");
	// Mapa que consigue el nombre de las areas
	HashMap<String, String> mapaAreas						= (HashMap<String, String>) request.getAttribute("mapaAreas");
	HashMap<String,String> mapaRequisitosEnTramite 			= (HashMap<String, String>) request.getAttribute("mapaRequisitosEnTramite");
	HashMap<String,String> mapaBitRequisitosCumpleAlumno 	= (HashMap<String, String>) request.getAttribute("mapaBitRequisitosCumpleAlumno");
	HashMap<String,String> mapaBitSolicitudPorAlumno 		= (HashMap<String, String>) request.getAttribute("mapaBitSolicitudPorAlumno");
%>
<div class="container-fluid">
	<h3><spring:message code="portal.alumno.tramites.TusTramitesEnProceso"/><small class="text-muted fs-6"> ( <%=codigoAlumno%> - <%=nombreAlumno%> )</small>
	<table class="table table-stripped">		
		<tr>
			<th><font size="4">&nbsp;</font></th>
			<th><font size="4">#</font></th>
			<th><font size="4"><spring:message code="portal.alumno.tramites.Folio"/></font></th>
			<th><font size="4"><spring:message code="portal.alumno.tramites.Area"/></font></th>
			<th><font size="4"><spring:message code="portal.alumno.tramites.Origen"/></font></th>
			<th><font size="4"><spring:message code="portal.alumno.tramites.TramiteServicio"/></font></th>
			<th><font size="4"><spring:message code="portal.alumno.tramites.FechaInicio"/></font></th>
			<th><font size="4"><spring:message code="portal.alumno.tramites.FechaTermino"/></font></th>
			<th class="right"><font size="4"><spring:message code="portal.alumno.tramites.Real"/></font></th>
			<th><font size="4"><spring:message code="portal.alumno.tramites.Estado"/></font></th>
			<th><font size="4"><spring:message code="portal.alumno.tramites.Tikets"/></font></th>			
		</tr>
<%
	int row = 0;
	for (BitTramiteAlumno tramite : lisTramites){
		row++;
		
		int numEtiquetas = 0;
		if (mapaEtiquetas.containsKey(tramite.getFolio())){
			numEtiquetas = Integer.parseInt(mapaEtiquetas.get(tramite.getFolio()));
		}
		
		// Cantidad de tramites hijos del tramite actual		
		String dependientes = "0";
		if (mapaHijos.containsKey(tramite.getFolio()) ){
			dependientes = mapaHijos.get(tramite.getFolio());
		}
		
		String tramiteNombre 	= "-";
		if (mapaTramite.containsKey(tramite.getTramiteId())){			
			tramiteNombre 	= mapaTramite.get(tramite.getTramiteId()).getNombre();
		}
		
		//Cuenta la duración en dias del tramite
		int dias = 0;
		if (tramite.getFechaFinal()==null){
			dias = aca.util.Fecha.diasEntreFechas(tramite.getFechaInicio(), hoy);
		}else{
			dias = aca.util.Fecha.diasEntreFechas(tramite.getFechaInicio(), tramite.getFechaFinal());
		}
				
		aca.util.Fecha.diasEntreFechas(tramite.getFechaInicio(), hoy);
				
		String estadoNombre = "";
		if (mapaEstado.containsKey(tramite.getEstado())){
			estadoNombre = mapaEstado.get(tramite.getEstado());
		}
		
		String colorTramite = "badge bg-dark";
		if (!tramite.getAreaOrigen().equals("0")){
			colorTramite = "badge bg-success";
		}
		
		String nombreArea = "-";
		if (mapaAreas.containsKey(tramite.getAreaId())){
			nombreArea = mapaAreas.get(tramite.getAreaId());
		}		

		String origen = "Mostrador";
		if (mapaBitSolicitudPorAlumno.containsKey(tramite.getFolio())){
			origen = "En linea";
		}		
%>
		<tr>
			<td>
		<% 
			if (numEtiquetas <= 1){
		%>
		<%				
			}	
		%>		
			<td><font size="3"><%=row%></font></td>
			<td><font size="3">						
				<a href="etiquetas?Folio=<%=tramite.getFolio()%>"><span class="badge <%=colorTramite%>"><%=tramite.getFolio()%></span></a>
			</font>
			</td>
			<td><font size="3"><%=nombreArea%></font></td>
			<td><font size="3"><%=origen%></font></td>
			<td><font size="3">
				<a href="etiquetas?Folio=<%=tramite.getFolio()%>">
				<%=tramiteNombre%>
				</a>
			</font></td>
			<td><font size="3"><%=tramite.getFechaInicio() %></font></td>
			<td><font size="3"><%=tramite.getFechaFinal()==null?"-":tramite.getFechaFinal()%></font></td>
			<td class="right"><font size="3"><%=dias%></font></td>
			<td><font size="3"><%=estadoNombre%></font></td>
			<td><font size="3"><%=numEtiquetas %></font></td>			
		</tr>
<%		
	}	
%>		
	</table>
	<h3><spring:message code="portal.alumno.tramites.TusSolicitudesDeTramitesEnLinea"/>: &nbsp;
		<a href="editarSolicitud" class="btn btn-primary btn-sm"><spring:message code="portal.alumno.tramites.NuevaSolicitud"/></a></h3>
	</h3>
	<div class="alert alert-info" role="alert">
  		<spring:message code="portal.alumno.tramites.MensajeUno"/>
	</div>
	<hr>
	<table class="table">
		<thead>
			<tr>
				<th><spring:message code="portal.alumno.tramites.Controles"/></th>
				<th><spring:message code="portal.alumno.tramites.Fecha"/></th>
				<th><spring:message code="portal.alumno.tramites.Estado"/></th>
				<th><spring:message code="portal.alumno.tramites.RequisitosCumplidos"/></th>
				<th><spring:message code="portal.alumno.tramites.Costo"/></th>
				<th><spring:message code="portal.alumno.tramites.Tramite"/></th>
				<th><spring:message code="portal.alumno.tramites.MotivoDeLaSolicitud"/></th>
				<th><spring:message code="portal.alumno.tramites.Comentario"/></th>
			</tr>
		</thead>
		<tbody>
<% 		for(BitSolicitud solicitud : lisSolicitudes){
			String tramiteNombre 	= "-";
			BitTramite bitTramite = new BitTramite();
			if (mapaTramite.containsKey(solicitud.getTramiteId())){			
				bitTramite = mapaTramite.get(solicitud.getTramiteId());
				tramiteNombre = bitTramite.getNombre();
			}

			String requisitos 	= "0";
			if (mapaRequisitosEnTramite.containsKey(solicitud.getTramiteId())){			
				requisitos 	= mapaRequisitosEnTramite.get(solicitud.getTramiteId());
			}
			
			String cumplidos 	= "0";
			if (mapaBitRequisitosCumpleAlumno.containsKey(solicitud.getTramiteId()+"-"+solicitud.getFolio())){	
				cumplidos 	= mapaBitRequisitosCumpleAlumno.get(solicitud.getTramiteId()+"-"+solicitud.getFolio());
			}
			
			String estado = "";
			
			if(cumplidos.equals("0")){
				estado = "Solicitud";
			}else if(Integer.parseInt(cumplidos) < Integer.parseInt(requisitos)){
				estado = "Atendiendo";
			} else if(cumplidos.equals(requisitos) ){
				estado = "Completo";
			}
			
			if(!solicitud.getStatus().equals("A")){
%>
			<tr>
				<td>
<% 				if(!solicitud.getStatus().equals("A")){%>
					<a href="editarSolicitud?Folio=<%=solicitud.getFolio()%>&TramiteId=<%=solicitud.getTramiteId()%>" title="Editar"><i class="fas fa-2x fa-pencil-alt"></i></a>
<% 				}%>
<% 				if(solicitud.getStatus().equals("S") && cumplidos.equals("0")){%>
					<a href="javascript:Borrar('<%=solicitud.getFolio()%>')" title="Borrar"><i class="fas fa-2x fa-times"></i></a>
<% 				}%>
				</td>
				<td><%=solicitud.getFecha()%></td>
				<td><%=estado%></td>
				<td>
					<a title="Clic aqui para ver detalles" href="verRequisitos?TramiteId=<%=solicitud.getTramiteId()%>&Folio=<%=solicitud.getFolio()%>" style="text-decoration: none;">
					    <span class="badge bg-success rounded-pill"><%=cumplidos%></span> de <span class="badge bg-dark rounded-pill"><%=requisitos%></span>
					</a>
				</td>
				<td>$<%=bitTramite.getImporte()%> MX</td>
<%-- 				<td><%=formato.format(bitTramite.getCosto())%></td> --%>
				<td><%=tramiteNombre%></td>
				<td><%=solicitud.getTextoAlumno()%></td>
				<td><%=solicitud.getTextoAdmin()%></td>
			</tr>
<% 			}
 		}%>
		</tbody>
	</table>
</div>
<script type="text/javascript">
	function Borrar(folio){
		if (confirm("¿Deseas eliminar ésta solicitud?")){
			document.location.href="borrarSolicitud?Folio="+folio;	
		}		
	}	
</script>
