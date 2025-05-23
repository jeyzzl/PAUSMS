<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import= "aca.bitacora.spring.BitTramiteAlumno"%>
<%@ page import= "aca.bitacora.spring.BitTramite"%>
<%@ page import= "aca.bitacora.spring.BitEtiqueta"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%-- <jsp:include page="../menu.jsp" /> --%>
<%@ include file= "menu.jsp" %>
<%
	String codigoAlumno 	= (String) request.getAttribute("matricula");
	String nombreAlumno		= (String) request.getAttribute("nombreAlumno");
	String nombreTramite	= (String) request.getAttribute("nombreTramite");
	String estado			= (String) request.getAttribute("estado");
	String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String hoy 				= aca.util.Fecha.getHoy();
	String nombreMaestro	= "";

	BitTramiteAlumno bitTramiteAlumno	= (BitTramiteAlumno) request.getAttribute("bitTramiteAlumno");
	BitTramite bitTramite				= (BitTramite) request.getAttribute("bitTramite");
	
	//Cuenta la duración en dias del tramite 
	int dias = 0;
	if (bitTramiteAlumno.getFechaFinal()==null){
		dias = aca.util.Fecha.diasEntreFechas(bitTramiteAlumno.getFechaInicio(), hoy);
	}else{
		dias = aca.util.Fecha.diasEntreFechas(bitTramiteAlumno.getFechaInicio(), bitTramiteAlumno.getFechaFinal());
	}
		
	// Lista de etiquetas en el tramite principal del alumno
	List<BitEtiqueta> lisEtiquetasFolio		= (List<BitEtiqueta>) request.getAttribute("lisEtiquetasFolio");
	
	// Lista de todas la etiquetas del alumno
	List<BitEtiqueta> lisEtiquetasAlumno	= (List<BitEtiqueta>) request.getAttribute("lisEtiquetasAlumno");
	
	// Lista de tramites que se turnaron apartir del tramite actual
	HashMap<String,BitTramiteAlumno> mapaTurnados	= (HashMap<String, BitTramiteAlumno>) request.getAttribute("mapaTurnados");
	
	// Mapa de tramites 
	HashMap<String,BitTramite> mapaTramites			= (HashMap<String, BitTramite>) request.getAttribute("mapaTramites");
	
	// Mapa de areas
	HashMap<String,String> mapaAreas				= (HashMap<String, String>) request.getAttribute("mapaAreas");
	
	// Mapa de estados
	HashMap<String,String> mapaEstados				= (HashMap<String, String>) request.getAttribute("mapaEstados");
	
	// Mapa de estados
	HashMap<String,String> mapaMaestros				= (HashMap<String, String>) request.getAttribute("mapaMaestros");
%>
<div class="container-fluid">
	<h2>Seguimiento<small class="text-muted fs-4"> ( <%=codigoAlumno%> - <%=nombreAlumno %> )</small></h2>
	<div class="alert alert-info">
		<a href="tramites" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i> Regresar</a>
	</div>
	<table class="table table-stripped table-sm table-bordered">
		<tr>
			<th colspan="7" style="font-size:1rem;">				 
				Tramite: <b><%=folio%></b>&nbsp;- <b><%=nombreTramite%></b>&nbsp;&nbsp;
				Registrado el: <b><%=bitTramiteAlumno.getFechaInicio()%></b>&nbsp;&nbsp;
				Mínimo: <span class="badge bg-success"><%=bitTramite.getMinimo()%></span>&nbsp;&nbsp;
				Máximo: <span class="badge bg-dark"><%=bitTramite.getMaximo()%></span>&nbsp;&nbsp;
				Prom. <span class="badge bg-info"><%=bitTramite.getPromedio()%></span>&nbsp;&nbsp;
				Real <span class="badge bg-warning"><%=dias%></span>
				Estado: <b><%= estado%></b>
			</th>
		</tr>
		<tr class="alert alert-info">
			<td colspan="7" style="font-size:1rem;"><b>Comentario:</b> <%=bitTramiteAlumno.getComentario()%></td>
		</tr>
		<tr>			
			<th><h5>#</h5></th>
			<th><h5>Area</h5></th>			
			<th><h5>Fecha</h5></th>
			<th><h5>Hora(24)</h5></th>
			<th><h5>Descripción</h5></th>
			<th><h5>Usuario</h5></th>					
		</tr>
<%
	int row = 0;
	for (BitEtiqueta etiqueta : lisEtiquetasFolio){
		row++;	
		boolean turnado = false;
		BitTramiteAlumno bitTramiteTurnado  = new BitTramiteAlumno();
		
		if (!etiqueta.getTurnado().equals("-")){
			turnado = true;						 
			if (mapaTurnados.containsKey(etiqueta.getTurnado())){
				bitTramiteTurnado = mapaTurnados.get(etiqueta.getTurnado());				
			}				
		}
		
		String nombreAreaOriginal = "";
		if (mapaAreas.containsKey(etiqueta.getAreaId())){
			nombreAreaOriginal = mapaAreas.get(etiqueta.getAreaId());
		}
		
		nombreMaestro = "-";
		if (mapaMaestros.containsKey(etiqueta.getUsuario())){
			nombreMaestro = mapaMaestros.get(etiqueta.getUsuario());
		}
%>
		<tr>
			<td style="font-size:1rem;"><span class="badge bg-dark"><%=row%></span></td>
			<td style="font-size:1rem;"><%=nombreAreaOriginal%></td>
			<td style="font-size:1rem;"><%=etiqueta.getFecha().substring(0,10)%></td>
			<td style="font-size:1rem;"><%=etiqueta.getFecha().substring(10,etiqueta.getFecha().length())%></td>
			<td style="font-size:1rem;"><%=etiqueta.getComentario()%>**</td>
			<td style="font-size:1rem;"><%=etiqueta.getUsuario()%></td>
		</tr>
<%
		if (turnado){
			String tramiteNombre = "";			
			if (mapaTramites.containsKey(bitTramiteTurnado.getTramiteId())){
				tramiteNombre = mapaTramites.get(bitTramiteTurnado.getTramiteId()).getNombre();
			}
			
			String nombreArea = "";
			if (mapaAreas.containsKey(bitTramiteTurnado.getAreaId())){
				nombreArea = mapaAreas.get(bitTramiteTurnado.getAreaId());
			}
			
			String nombreEstado = "";
			if (mapaEstados.containsKey(bitTramiteTurnado.getEstado())){
				nombreEstado = mapaEstados.get(bitTramiteTurnado.getEstado());
			}
%>
		<tr>
			<td colspan = "7" style="padding-left:50px;">
				<table class="table table-stripped table-condensed">
				<tr class="alert alert-success">			
					<td colspan="7"><b>Tramite turnado: </b> <span class="badge bg-success"><%=bitTramiteTurnado.getFolio()%></span>&nbsp;&nbsp;
						<%=tramiteNombre%>( <%= nombreArea%> )&nbsp;&nbsp;
						<b>Fecha Inicio: </b><%=bitTramiteTurnado.getFechaInicio()%>&nbsp;&nbsp;
						<b>Estado: </b><span class="badge bg-success"><%=nombreEstado%></span>
					</td>
				</tr>
				<tr class="alert">
					<td colspan="7"><h5>Comentario: <%=bitTramiteTurnado.getComentario()%></h5></td>
				</tr>
				<tr>			
					<th width="5%">#</th>
					<th>Area</th>
					<th>Fecha</th>
					<th>Hora(24)</th>
					<th width="50%">Descripción</th>
					<th>Usuario</th>					
				</tr>
<%
			int rowTurnado = 0;
			for (BitEtiqueta etiquetaTurnado : lisEtiquetasAlumno){
				
				if (etiquetaTurnado.getFolio().equals(etiqueta.getTurnado())){
					rowTurnado++;
					String nombreAreaTurnado = "";					
					if (mapaAreas.containsKey(bitTramiteTurnado.getAreaId())){
						nombreAreaTurnado = mapaAreas.get(bitTramiteTurnado.getAreaId());
					}

					nombreMaestro = "-";
					if (mapaMaestros.containsKey(etiquetaTurnado.getUsuario())){
						nombreMaestro = mapaMaestros.get(etiquetaTurnado.getUsuario());
					}
%>
				<tr>			
					<td><span class="badge bg-dark"><%=row%>.<%=rowTurnado%></span></td>
					<td><%=nombreAreaTurnado%></td>
					<td><%=etiquetaTurnado.getFecha().substring(0,10)%></td>
					<td><%=etiquetaTurnado.getFecha().substring(10,etiquetaTurnado.getFecha().length())%></td>
					<td><%=etiquetaTurnado.getComentario()%></td>
					<td><%=etiquetaTurnado.getUsuario()%></td>					
				</tr>
<%				
				}
			}		
%>
				</table>			
			</td>
		</tr>		
<%
		}
	}
%>
	</table>	
</div>