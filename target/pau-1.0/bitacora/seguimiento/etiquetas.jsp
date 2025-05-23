<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.bitacora.spring.BitTramiteAlumno"%>
<%@page import="aca.bitacora.spring.BitTramite"%>
<%@page import="aca.bitacora.spring.BitEtiqueta"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<script type="text/javascript">
	function Borrar(Folio,EtiquetaId) {
		if (confirm("Quieres eliminar esta etiqueta ?" ) == true) {
			document.location = "borrarEtiqueta?Folio="+Folio+"&EtiquetaId="+EtiquetaId;
		}
	}
	
	function Editar(Folio, EtiquetaId) {
		document.location = "editarEtiqueta?Folio="+Folio+"&EtiquetaId="+EtiquetaId;
	}
</script>
<%		
	String codigoPersonal		= (String) request.getAttribute("codigoPersonal");
	String folio				= (String) request.getAttribute("folio");
	String mensaje 				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	String hoy 					= aca.util.Fecha.getHoy();
	
	BitTramiteAlumno bitTramiteAlumno	= (BitTramiteAlumno) request.getAttribute("bitTramiteAlumno");
	BitTramite	bitTramite				= (BitTramite) request.getAttribute("bitTramite");
	BitEtiqueta	bitEtiqueta				= (BitEtiqueta) request.getAttribute("bitEtiqueta");	
	
	String minimo   = (String) request.getAttribute("minimo");
	String maximo   = (String) request.getAttribute("maximo");
	String promedio = (String) request.getAttribute("promedio");

	String nombreUsuario 	= (String) request.getAttribute("nombreUsuario");
	String nombreCorto 		= (String) request.getAttribute("nombreCorto");
	String nombreEstado 	= "";
	String nombreTramite 	= "";
	String usuarioNombre 	= "Mostrador";
	
	
	//Cuenta la duración en dias del tramite 
	int dias = 0;
	if (bitTramiteAlumno.getFechaFinal()==null){
		dias = aca.util.Fecha.diasEntreFechas(bitTramiteAlumno.getFechaInicio(), hoy);
	}else{
		dias = aca.util.Fecha.diasEntreFechas(bitTramiteAlumno.getFechaInicio(), bitTramiteAlumno.getFechaFinal());
	}
	
	List<BitEtiqueta> lisEtiquetas = (List<BitEtiqueta>) request.getAttribute("lisEtiquetas");
	List<BitEtiqueta> lisTurnadas 						= (List<BitEtiqueta>) request.getAttribute("lisTurnadas");
	
	HashMap<String, BitTramiteAlumno> mapTramitesAlumno = (HashMap<String, BitTramiteAlumno>) request.getAttribute("mapTramitesAlumno");
	HashMap<String, String> mapaAreas 					= (HashMap<String, String>) request.getAttribute("mapaAreas");
	HashMap<String, String> mapEstados 					= (HashMap<String, String>) request.getAttribute("mapEstados");
	HashMap<String, BitTramite> mapTramites				= (HashMap<String, BitTramite>) request.getAttribute("mapTramites");
	HashMap<String,String> mapaMaestros					= (HashMap<String,String>)request.getAttribute("mapaMaestros");
	
	String foliosDep 	= (String) request.getAttribute("foliosDep");
	String dependientes = (String) request.getAttribute("dependientes");
	String fechaInicio 	= (String) session.getAttribute("fechaInicio");
	String fechaFinal 	= (String) session.getAttribute("fechaFinal");

	boolean cambiarEstado = false;
	// Si es un folio hijo y tiene los estados correctos
	if (!bitTramiteAlumno.getFolioOrigen().equals("-") && Integer.parseInt(bitTramiteAlumno.getEstado()) < 5 || Integer.parseInt(bitTramiteAlumno.getEstado()) == 8){
		cambiarEstado = true;
	}
	// Si es un folio principal, no tiene hijos y tiene los estados correctos
	if ( dependientes.equals("0") && Integer.parseInt(bitTramiteAlumno.getEstado()) < 5){
		cambiarEstado = true;
	}
	
	if(mapEstados.containsKey(bitTramiteAlumno.getEstado())){
		nombreEstado = mapEstados.get(bitTramiteAlumno.getEstado());
	}
	
	if(mapTramites.containsKey(bitTramiteAlumno.getTramiteId())){
		nombreTramite = mapTramites.get(bitTramiteAlumno.getTramiteId()).getNombre();
	}
%>
<div class="container-fluid">
	<h2>BSE / Seguimiento / Etiquetas<small class="text-muted h5">(<%=bitTramiteAlumno.getCodigoPersonal()%> - <%=nombreUsuario%>)</small></h2>
	<div class="alert alert-info">
		<a href="seguimiento?AreaId=<%=bitTramite.getAreaId()%>&FechaInicio=<%=fechaInicio%>&FechaFinal<%=fechaFinal%>" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		<a href="crearEtiqueta?Folio=<%=folio%>&AreaId=<%=bitTramiteAlumno.getAreaId()%>" style="text-align:center;" class="btn btn-primary"><i class="fas fa-plus"></i> Nueva etiqueta</a>&nbsp;&nbsp;
<%
	// Valida que el tramite sea un servicio externo   icon-arrow-right
	if (bitTramiteAlumno.getAreaOrigen().equals("0") && bitTramiteAlumno.getEstado().equals("3")){
%>		
		<a href="turnar?Folio=<%=folio%>" style="text-align:center;" class="btn btn-primary"><i class="icon-white icon-arrow-right"></i> Turnar</a>&nbsp;&nbsp;
<%
	}

	if(cambiarEstado){
%>	
		<a class="btn btn-primary" href="cambiarEstado?Folio=<%=folio%>"><i class="fas fa-edit"></i> Cambiar estado</a>
<%
	}
%>	
	</div>
<%	if (!mensaje.equals("-")){ %>
	<div class="alert alert-info"><%=mensaje%></div>
<%	} %>	
	
	<h5>				
				Tramite: <b><%=folio%></b>&nbsp;&nbsp;<b><%=nombreTramite%></b>&nbsp;&nbsp;		 
				Registrado: <b><%=bitTramiteAlumno.getFechaInicio()%></b>&nbsp;&nbsp;
				Mín. <span class="badge bg-success"><%=bitTramite.getMinimo()%></span>&nbsp;&nbsp;
				Máx. <span class="badge bg-dark"><%=bitTramite.getMaximo()%></span>&nbsp;&nbsp;
				Prom. <span class="badge bg-info"><%=bitTramite.getPromedio()%></span>&nbsp;&nbsp;
				Real <span class="badge bg-warning"><%=dias%></span>&nbsp;&nbsp;
				Estado: <b><%=nombreEstado%></b>
	</h5>
	<table class="table table-stripped table-sm table-bordered table-nohover">
		<tr>
			
		</tr>
		<tr class="alert alert-info">
			<td colspan="7"><h5>Comentario: <%=bitTramiteAlumno.getComentario()%></h5></td>
		</tr>
		<tr>
			<th>#</th>
			<th>Op.</th>
			<th>Area</th>
			<th>Fecha</th>
			<th>Hora(24)</th>
			<th>Descripción</th>
			<th>Usuario</th>
		</tr>
<%
	BitTramiteAlumno bitTramiteTurnado = new BitTramiteAlumno();	
	int row = 0;
	for (BitEtiqueta etiqueta : lisEtiquetas){
		row++;
		boolean turnado = false;
		boolean existeTurnado = false;
		if (!etiqueta.getTurnado().equals("-")){			
			turnado = true;
			if(mapTramitesAlumno.containsKey(etiqueta.getTurnado())){
				existeTurnado = true;
				bitTramiteTurnado = mapTramitesAlumno.get(etiqueta.getTurnado());
			}
		}
		
		String nombreArea = "";
		if(mapaAreas.containsKey(etiqueta.getAreaId())){
			nombreArea = mapaAreas.get(etiqueta.getAreaId());
		}
		
		usuarioNombre = "Mostrador";
		if (mapaMaestros.containsKey(etiqueta.getUsuario())){
			usuarioNombre = mapaMaestros.get(etiqueta.getUsuario());
		}
%>
		<tr>
			<td>
			<span class="badge bg-dark"><%=row%></span>
			</td>
			<td>			
<% 			// etiqueta.getUsuario().equals(codigoPersonal)
			if(row == lisEtiquetas.size() && existeTurnado == false){%>
				<a href="javascript:Borrar('<%=folio%>','<%=etiqueta.getEtiquetaId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-times"></i></a>
<% 			}%>
			&nbsp;&nbsp;
			<a href="javascript:Editar('<%=folio%>','<%=etiqueta.getEtiquetaId()%>')" title="Editar"><i class="fas fa-edit"></i></a> 
			</td>
			<td><%=nombreArea%></td>
			<td><%=etiqueta.getFecha().substring(0,10)%></td>
			<td><%=etiqueta.getFecha().substring(10,etiqueta.getFecha().length())%></td>
			<td><%=etiqueta.getComentario()%></td>
			<td><%=usuarioNombre%> ( <%=etiqueta.getUsuario()%> )</td>			
		</tr>		 
<%		
		if (turnado && existeTurnado){
			
			if(mapaAreas.containsKey(bitTramiteTurnado.getAreaId())){
				nombreArea = mapaAreas.get(bitTramiteTurnado.getAreaId());
			}
			
			if(mapEstados.containsKey(bitTramiteTurnado.getEstado())){
				nombreEstado = mapEstados.get(bitTramiteTurnado.getEstado());
			}
		
			if(mapTramites.containsKey(bitTramiteAlumno.getTramiteId())){
				nombreTramite = mapTramites.get(bitTramiteAlumno.getTramiteId()).getNombre();
			}
%>
		<tr>
			<td colspan = "7" style="padding-left:50px;">
				<table class="table table-stripped table-sm">
					<tr class="alert alert-success">			
						<td colspan="7"><b>Tramite turnado: </b> <span class="badge bg-success"><%=bitTramiteTurnado.getFolio()%></span>&nbsp;&nbsp;
							<%=nombreTramite%>( <%=nombreArea%> )&nbsp;&nbsp;
							<b>Fecha Inicio: </b><%=bitTramiteTurnado.getFechaInicio()%>&nbsp;&nbsp;
							<b>Estado: </b><span class="badge bg-success"><%=nombreEstado%></span>
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
				
				if (etiquetaTurnado.getFolio().equals(bitTramiteTurnado.getFolio()) ){
					rowTurnado++;				
					
					if(mapaAreas.containsKey(etiquetaTurnado.getAreaId())){
						nombreArea = mapaAreas.get(etiquetaTurnado.getAreaId());
					}			
					
					usuarioNombre = "Mostrador";
					if (mapaMaestros.containsKey(etiquetaTurnado.getUsuario())){
						usuarioNombre = mapaMaestros.get(etiquetaTurnado.getUsuario());
					}
%>
				<tr>			
					<td><span class="badge bg-dark"><%=row%>.<%=rowTurnado%></span></td>
					<td><%=nombreArea%></td>
					<td><%=etiquetaTurnado.getFecha().substring(0,10)%></td>
					<td><%=etiquetaTurnado.getFecha().substring(10,etiquetaTurnado.getFecha().length())%></td>
					<td><%=etiquetaTurnado.getComentario()%></td>
					<td><%=usuarioNombre%> ( <%=etiquetaTurnado.getUsuario()%> )</td>
					<td>&nbsp;</td>
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