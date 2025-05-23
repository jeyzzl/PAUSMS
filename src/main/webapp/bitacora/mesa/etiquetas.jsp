<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import="aca.bitacora.spring.BitEtiqueta"%>
<%@ page import="aca.bitacora.spring.BitTramiteAlumno"%>
<%@ page import="aca.bitacora.spring.BitTramite"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	String codigoAlumno 				= (String) session.getAttribute("codigoAlumno");
	String alumnoNombre					= (String) request.getAttribute("alumnoNombre");
	BitTramiteAlumno bitTramiteAlumno 	= (BitTramiteAlumno) request.getAttribute("bitTramiteAlumno");

	String folio				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");	
	String hoy 					= aca.util.Fecha.getHoy();	 
	
	//Cuenta la duración en dias del tramite 
	int dias = 0;

	if (bitTramiteAlumno.getFechaFinal()==null){
		dias = aca.util.Fecha.diasEntreFechas(bitTramiteAlumno.getFechaInicio(), hoy);
	}else{
		dias = aca.util.Fecha.diasEntreFechas(bitTramiteAlumno.getFechaInicio(), bitTramiteAlumno.getFechaFinal());
	}
	
	// Lisata de tramites del alumno
	List<BitEtiqueta> lisEtiquetas 						= (List<BitEtiqueta>) request.getAttribute("lisEtiquetas");	
	List<BitEtiqueta> lisTurnadas 						= (List<BitEtiqueta>) request.getAttribute("lisTurnadas");	
	
	HashMap<String,String> mapaEstados					= (HashMap<String,String>)request.getAttribute("mapaEstados");
	HashMap<String,BitTramite> mapaTramites				= (HashMap<String,BitTramite>)request.getAttribute("mapaTramites");
	HashMap<String,String> mapaMaestros					= (HashMap<String,String>)request.getAttribute("mapaMaestros");
	HashMap<String,String> mapaAreas					= (HashMap<String,String>)request.getAttribute("mapaAreas");
	HashMap<String,BitTramiteAlumno> mapaTramitesAlumno	= (HashMap<String,BitTramiteAlumno>)request.getAttribute("mapaTramitesAlumno");
	
	String tramiteNombre = "-";
	if (mapaTramites.containsKey(bitTramiteAlumno.getTramiteId())){
		tramiteNombre = mapaTramites.get(bitTramiteAlumno.getTramiteId()).getNombre();
	}
	
	String estadoNombre = "-";
	if (mapaEstados.containsKey( bitTramiteAlumno.getEstado())){
		estadoNombre = mapaEstados.get(bitTramiteAlumno.getEstado());
	}
	
	BitTramite bitTramite = new BitTramite();
	if (mapaTramites.containsKey(bitTramiteAlumno.getTramiteId())){
		bitTramite = mapaTramites.get(bitTramiteAlumno.getTramiteId());
	}
%>
<div class="container-fluid">
	<h2>BSE / Mostrador / Etiquetas<small class="text-muted h4">(<%=codigoAlumno%>-<%=alumnoNombre%>)</small></h2>
	<div class="alert alert-info">
		<a href="entrada" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<span>
		Folio: <b><%=folio%>&nbsp;&nbsp;</b>
		Tramite: <b><%=tramiteNombre%></b>&nbsp;&nbsp;
		Registrado el: <b><%=bitTramiteAlumno.getFechaInicio()%></b>&nbsp;&nbsp;
		Mínimo: <span class="badge bg-success"><%=bitTramite.getMinimo()%></span>&nbsp;&nbsp;
		Máximo: <span class="badge bg-dark"><%=bitTramite.getMaximo()%></span>&nbsp;&nbsp;
		Prom. <span class="badge bg-info"><%=bitTramite.getPromedio()%></span>&nbsp;&nbsp;
		Real <span class="badge bg-warning"><%=dias%></span>&nbsp;&nbsp;
		Estado: <%=estadoNombre%>
	</span>
	<table class="table table-stripped">
	<thead>	
		<tr class="table-warning">
			<td colspan="7"><h5>Comentario: <%=bitTramiteAlumno.getComentario()%></h5></td>
		</tr>
		<tr>
			<th>#</th>
			<th>Area</th>			
			<th>Fecha</th>
			<th>Hora(24)</th>
			<th>Descripción</th>
			<th>Usuario</th>			
		</tr>
	</thead>
	<tbody>
<%
	int row = 0;
	for (BitEtiqueta etiqueta : lisEtiquetas){
		row++;
		
		boolean turnado = false;
		BitTramiteAlumno bitTramiteTurnado	= new BitTramiteAlumno();
		
		if (!etiqueta.getTurnado().equals("-")){
			turnado = true;					
			if (mapaTramitesAlumno.containsKey(etiqueta.getTurnado())){
				bitTramiteTurnado = mapaTramitesAlumno.get(etiqueta.getTurnado());
			}			
			estadoNombre = "-";
			if (mapaEstados.containsKey( bitTramiteTurnado.getEstado())){
				estadoNombre = mapaEstados.get(bitTramiteTurnado.getEstado());
			}
		}
		
		String usuarioNombre = "Mostrador";
		if (mapaMaestros.containsKey(etiqueta.getUsuario())){
			usuarioNombre = mapaMaestros.get(etiqueta.getUsuario());
		}
		
		String areaNombre = "-";
		if (mapaAreas.containsKey(etiqueta.getAreaId())){
			areaNombre = mapaAreas.get(etiqueta.getAreaId());
		}
		
%>		
		<tr>
			<td>
			<span class="badge bg-dark"><%=row%></span>
			&nbsp;&nbsp;
<% 			if(row == lisEtiquetas.size() && etiqueta.getUsuario().equals(codigoAlumno) && etiqueta.getTurnado().equals("-")){%>
				<a href="javascript:Borrar('<%=etiqueta.getEtiquetaId()%>','<%=folio%>','<%=bitTramiteAlumno.getAreaId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-times"></i></a>
<% 			}%>
			</td>
			<td><%=areaNombre%></td>
			<td><%=etiqueta.getFecha().substring(0,10)%></td>
			<td><%=etiqueta.getFecha().substring(10,etiqueta.getFecha().length())%></td>
			<td><%=etiqueta.getComentario()%></td>
			<td><%=usuarioNombre%>( <%=etiqueta.getUsuario()%> )</td>
		</tr>		
<%
		if (turnado){
			
			tramiteNombre = "-";
			if (mapaTramites.containsKey(bitTramiteTurnado.getTramiteId())){
				tramiteNombre = mapaTramites.get(bitTramiteTurnado.getTramiteId()).getNombre();
			}
			
			areaNombre = "-";
			if (mapaAreas.containsKey(bitTramiteTurnado.getAreaId())){
				areaNombre = mapaAreas.get(bitTramiteTurnado.getAreaId());
			}		
%>
		<tr>
			<td colspan = "7" style="padding-left:50px;">
				<table class="table table-stripped table-sm">
					<tr class="alert alert-success">			
						<td colspan="7"><b>Tramite turnado: </b> <span class="badge bg-success"><%=bitTramiteTurnado.getFolio()%></span>&nbsp;&nbsp;
							<%=tramiteNombre%>( <%= areaNombre%> )&nbsp;&nbsp;
							<b>Fecha Inicio: </b><%=bitTramiteTurnado.getFechaInicio()%>&nbsp;&nbsp;
							<b>Estado: </b><span class="badge bg-success"><%=estadoNombre%></span>
						</td>						
						</tr>
						<tr class="alert alert-warning">
							<td colspan="7"><h5>Comentario: <%=bitTramiteTurnado.getComentario()%></h5></td>
						</tr>
						<tr>			
							<th>#</th>
							<th>Area</th>
							<th>Fecha</th>
							<th>Hora(24)</th>
							<th>Descripción</th>
							<th>Usuario</th>
							<th>&nbsp;</th>
						</tr>
<%
			int rowTurnado = 0;
			for (BitEtiqueta etiquetaTurnado : lisTurnadas){				
			
				if (etiquetaTurnado.getFolio().equals(bitTramiteTurnado.getFolio())){
					rowTurnado++;
					
					areaNombre = "-";
					if (mapaAreas.containsKey(etiquetaTurnado.getAreaId())){
						areaNombre = mapaAreas.get(etiquetaTurnado.getAreaId());
					}
					
					usuarioNombre = "Mostrador";
					if (mapaMaestros.containsKey(etiquetaTurnado.getUsuario())){
						usuarioNombre = mapaMaestros.get(etiquetaTurnado.getUsuario());
					}
%>
				<tr>			
					<td><span class="badge bg-dark"><%=row%>.<%=rowTurnado%></span></td>
					<td><%=areaNombre%></td>
					<td><%=etiquetaTurnado.getFecha().substring(0,10)%></td>
					<td><%=etiquetaTurnado.getFecha().substring(10,etiquetaTurnado.getFecha().length())%></td>
					<td><%=etiquetaTurnado.getComentario()%></td>
					<td><%=usuarioNombre%> ( <%=etiquetaTurnado.getUsuario()%> )</td>
					<td>&nbsp;</td>
				</tr>
<%				}
			}
%>					
				</table>			
			</td>
		</tr>
<%		
		}
	}
%>		
	</tbody>
	</table>
</div>