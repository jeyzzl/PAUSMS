<%@page import= "java.util.List"%>
<%@page import= "java.util.HashMap"%>
<%@page import="aca.bitacora.spring.BitTramite"%>
<%@page import="aca.bitacora.spring.BitEstado"%>
<%@page import="aca.bitacora.spring.BitArea"%>
<%@page import="aca.bitacora.spring.BitTramiteAlumno"%>
<%@page import="aca.vista.spring.Usuarios"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>


<script type="text/javascript">
	function cambiarArea(){	
		document.form.submit();
	}
	
	function refrescar(){
		document.form.submit();
	}
</script>

<%
	String areaId			= request.getParameter("AreaId")==null?"0":request.getParameter("AreaId");
	String tramiteId 		= request.getParameter("TramiteId")==null?"0":request.getParameter("TramiteId");
	String estadoId  		= request.getParameter("EstadoId")==null?"0":request.getParameter("EstadoId");
	
	String year 			= aca.util.Fecha.getHoy().substring(6, 10);
	
	String fechaInicio 		= request.getParameter("FechaInicio")==null?"01/01/"+year:request.getParameter("FechaInicio");
	String fechaFinal  		= request.getParameter("FechaFinal")==null?"31/12/"+year:request.getParameter("FechaFinal");
	String hoy 				= aca.util.Fecha.getHoy();
	
	// Lista de estados de un tramite
	List<BitEstado> lisEstados  = (List<BitEstado>)request.getAttribute("lisEstados") ;
	
	// Lista de areas en VRA
	List<BitArea> lisAreas		= (List<BitArea>)request.getAttribute("lisAreas");
/*
	if (areaId.equals("0") && lisAreas.size() > 0){
		areaId = lisAreas.get(1).getAreaId();
	}
	*/
	
	// Lista del catalogo de tramites del area seleccionada
	List<BitTramite> lisTramites = (List<BitTramite>)request.getAttribute("lisTramites");
	// Lista de tramites de los alumnos
	List<BitTramiteAlumno> lisTramitesAlumnos = (List<BitTramiteAlumno>)request.getAttribute("lisTramitesAlumnos");
	// Mapa del  catalogo de tramites
	HashMap<String,BitTramite> mapaTramite = (HashMap<String,BitTramite>)request.getAttribute("mapaTramite");
	
	// Mapa del catalogo de estados
	HashMap<String,String> mapaEstado = (HashMap<String,String>)request.getAttribute("mapaEstado");
	
	// Mapa de los Nombres de los Alumnos
	HashMap<String,String> mapaNombreUsuario	= (HashMap<String,String>)request.getAttribute("mapaNombreAlumno");
	
%>

<div class="container-fluid">
	<h2>Tr&aacute;mites por &Aacute;reas</h2>
	<form name="form" action="tramitesArea">
	<div class="alert alert-info d-flex align-items-center">
		<a href="estadistica" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		&Aacute;rea:&nbsp;
		<select name="AreaId" onchange="javaScritp:cambiarArea()" style="width:200px;" class="form-select">
<%
	int row = 0;
	for (BitArea area : lisAreas){
		row++;
%>
			<option value="<%=area.getAreaId()%>" <%=areaId.equals(area.getAreaId())?"selected":""%>><%=area.getAreaNombre()%></option>
<%		
	}
%>					
		</select>&nbsp;&nbsp;&nbsp;
		Tr&aacute;mite:&nbsp;
			<select style="width:200px;" class="form-select" name="TramiteId" id="TramiteId" onchange="javaScritp:refrescar()">
				<option value="0" <%=tramiteId.equals("0")?"selected":""%>>Todos</option>
<%
	for(BitTramite tramite : lisTramites){
%>
				<option <%if(tramiteId.equals(tramite.getTramiteId()))out.print("Selected");%> value="<%=tramite.getTramiteId()%>">
					<%=tramite.getNombre()%>
				</option>
<%
	}
%>
			</select>&nbsp;&nbsp;&nbsp;
			Estado:&nbsp;
			<select style="width:200px;" class="form-select" name="EstadoId" id="EstadoId" onchange="javaScritp:refrescar()">
				<option value="0" <%=estadoId.equals("0")?"selected":""%>>Todos</option>
<%
			for(BitEstado estado : lisEstados){	
%>
				<option <%if(estadoId.equals(estado.getEstado()))out.print("Selected");%> value="<%=estado.getEstado()%>">
					<%=estado.getEstadoNombre()%>
				</option>
<%
			}
%>
			</select>&nbsp;&nbsp;&nbsp;
			Fecha Ini. <input type="text" data-date-format="dd/mm/yyyy" id="FechaInicio" name="FechaInicio" value="<%=fechaInicio%>" size="10px" class="form-control" style="width:120px"/>&nbsp;&nbsp;
			Fecha Fin. <input type="text" data-date-format="dd/mm/yyyy" id="FechaFinal" name="FechaFinal" value="<%=fechaFinal%>" size="10px" class="form-control" style="width:120px"/>&nbsp;&nbsp;			
			<a href="javascript:refrescar()" class="btn btn-primary" ><i class="fas fa-sync-alt"></i></a>			
	</div>
	</form>
	<table class="table" style="width:100%">
	<thead class="table-info">
		<tr>
			<th>#</th>
			<th>Folio</th>
			<th>Tr&aacute;mite</th>
			<th>M&iacute;nimo</th>
			<th>M&aacute;ximo</th>
			<th>Promedio</th>
			<th>Real</th>
			<th>Solicitante</th>
			<th>Turnado de</th>
			<th>Inicio</th>
			<th>T&eacute;rmino</th>
			<th>Estado</th>
		</tr>
	</thead>
	<tbody>	
<%

	row = 0;
	for (BitTramiteAlumno tramite : lisTramitesAlumnos){
		row++;
	
		String minimo 	= "0";
		String maximo 	= "0";
		String promedio = "0";
		String nombreTramite = "-";
		//String nombreUsuario = mapaNombreUsuario.get(tramite.getCodigoPersonal());;
		if (mapaTramite.containsKey(tramite.getTramiteId())){
			minimo 			= mapaTramite.get(tramite.getTramiteId()).getMinimo();
			maximo 			= mapaTramite.get(tramite.getTramiteId()).getMaximo();
			promedio 		= mapaTramite.get(tramite.getTramiteId()).getPromedio();
			nombreTramite 	= mapaTramite.get(tramite.getTramiteId()).getNombre();
		}
		
		String estadoNombre = "-";
		if (mapaEstado.containsKey(tramite.getEstado())){
			estadoNombre = mapaEstado.get(tramite.getEstado());
		}
		
			//Cuenta la duración en dias del tramite
				int dias = 0;
				if (tramite.getFechaFinal()==null){
					dias = aca.util.Fecha.diasEntreFechas(tramite.getFechaInicio(), hoy);
				}else{
					dias = aca.util.Fecha.diasEntreFechas(tramite.getFechaInicio(), tramite.getFechaFinal());
				}
				
				aca.util.Fecha.diasEntreFechas(tramite.getFechaInicio(), hoy);
				
		String colorReal = "bg-success";
		if (dias>Float.parseFloat(promedio)){
			colorReal = "bg-warning";
		}
				
%>
		<tr>
			<td><%=row%></td>
			<td><span class="badge bg-success"><%=tramite.getFolio()%></span></td>
			<td><%= nombreTramite%></td>
			<td class = "center"><%= minimo %></td>
			<td class = "center"><%= maximo %></td>
			<td class = "center"><%= promedio %></td>
			<td class = "center"><span class="badge <%=colorReal%>" ><%= dias %></span></td>
			<td><%=tramite.getCodigoPersonal() %></td>			
			<td class = "center"><%=0%></td>
			<td><%=tramite.getFechaInicio()%></td>
			<td><%=tramite.getFechaFinal()==null?"-":tramite.getFechaFinal()%></td>
			<td><%=estadoNombre%></td>
		</tr>
<%		
	}
%>		
	</tbody>	
	</table>
</div>
<script>
	jQuery('#FechaInicio').datepicker();
	jQuery('#FechaFinal').datepicker();
</script>

