<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import= "java.util.List"%>
<%@page import= "java.util.HashMap"%>
<%@page import="aca.bitacora.spring.BitTramite"%>
<%@page import="aca.bitacora.spring.BitEstado"%>
<%@page import="aca.bitacora.spring.BitArea"%>
<%@page import="aca.bitacora.spring.BitTramiteAlumno"%>
<%@page import="aca.vista.spring.Usuarios"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<script type="text/javascript">
	function refrescar(){
		document.form.submit();
	}
</script>

<%	
	String areaId			= request.getParameter("AreaId")==null?"0":request.getParameter("AreaId");
	String tramiteId 		= request.getParameter("TramiteId")==null?"0":request.getParameter("TramiteId");

	String year 			= aca.util.Fecha.getHoy().substring(6, 10);
	
	String fechaInicio 		= request.getParameter("FechaInicio")==null?"01/01/"+year:request.getParameter("FechaInicio");
	String fechaFinal  		= request.getParameter("FechaFinal")==null?"31/12/"+year:request.getParameter("FechaFinal");
	String hoy 				= aca.util.Fecha.getHoy();
		
	// Lista del catalogo de tramites del area seleccionada
	List<BitTramite> lisTramites = (List<BitTramite>)request.getAttribute("lisTramites");
	
	// Lista de areas en VRA
	List<BitArea> lisAreas		= (List<BitArea>)request.getAttribute("lisAreas");
	
	// Lista de estados en VRA
	List<BitEstado> lisEstados	= (List<BitEstado>)request.getAttribute("lisEstados");
	
	// Mapa que cuenta la cantidad de tramites por cada estado
	HashMap<String,String> mapaTramiteEstado 	= (HashMap<String,String>)request.getAttribute("mapaTramiteEstado");	
%>

<div class="container-fluid">
	<h2>Tr&aacute;mites</h2>
	<form name="form" action="tramites">
	<div class="alert alert-info">
		<a href="estadistica" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		Fecha Ini. <input type="text" data-date-format="dd/mm/yyyy" id="FechaInicio" name="FechaInicio" value="<%=fechaInicio%>" size="9"/>&nbsp;&nbsp;
		Fecha Fin. <input type="text" data-date-format="dd/mm/yyyy" id="FechaFinal" name="FechaFinal" value="<%=fechaFinal%>" size="9"/>&nbsp;&nbsp;
		<a href="javascript:refrescar()" class="btn btn-primary" ><i class="fas fa-sync-alt"></i></a>
	</div>
	</form>
	<table class="table" style="width:100%">
	<thead class="table-info">
		<tr>
			<th>#</th>
			<th>Tr&aacute;mite</th>
			<th>&Aacute;rea</th>
	<%
		for (BitEstado estado :lisEstados){
			out.print("<th class='right'>"+estado.getEstadoNombre()+"</th>");
		}
	%>		
			<th class="right">Total</th>
			<th class="right">M&iacute;nimo</th>
			<th class="right">M&aacute;ximo</th>
			<th class="right">Promedio</th>
		</tr>
	</thead>	
<%
	int row = 0;
	int totMesa = 0; //2
	int totProceso = 0; //3
	int totSalida = 0; //4
	int totEntregado = 0; //5
	int totCancelado = 0; //6
	int totTramites = 0;
	for(BitTramite tramite : lisTramites){	
		row++;		
		
		//totTramites = Integer.parseInt(proceso)+Integer.parseInt(terminado);
		
%>
		<tr>
			<td><%=row%></td>
			<td><%= tramite.getNombre() %></td>
			<td><%= tramite.getAreaId() %></td>
<%-- 			<td><%=BitAreaDao.getAreaNombre(tramite.getAreaId())%></td>  --%>
	<%
		int totTramite = 0;
		for (BitEstado estado :lisEstados){
			String totEstado = "0";
			if (mapaTramiteEstado.containsKey(tramite.getTramiteId()+estado.getEstado() )){
				totEstado = mapaTramiteEstado.get(tramite.getTramiteId()+estado.getEstado());
				
				totTramite = totTramite + Integer.parseInt(totEstado);
				// Suma totales generales
				if (estado.getEstado().equals("2")) totMesa = totMesa + Integer.parseInt(totEstado);
				if (estado.getEstado().equals("3")) totProceso = totProceso + Integer.parseInt(totEstado);
				if (estado.getEstado().equals("4")) totSalida = totSalida + Integer.parseInt(totEstado);
				if (estado.getEstado().equals("5")) totEntregado = totEntregado + Integer.parseInt(totEstado);
				if (estado.getEstado().equals("6")) totCancelado = totCancelado + Integer.parseInt(totEstado);
			}
			out.print("<td class='right'>"+totEstado+"</td>");
		}
		totTramites = totTramites + totTramite;
	%>	
			
			<td class="right"><%= totTramite%></td>
			<td class="right"><%= tramite.getMinimo() %></td>
			<td class="right"><%= tramite.getMaximo() %></td>
			<td class="right"><%= tramite.getPromedio() %></td>
		</tr>
<%		
	}
%>		
		<tr>
			<td colspan="3" class="right"><b>Totales</b></td>
			<td class="right"><%=totMesa%></td>
			<td class="right"><%=totProceso%></td>
			<td class="right"><%=totSalida%></td>
			<td class="right"><%=totEntregado%></td>
			<td class="right"><%=totCancelado%></td>
			<td class="right"><%=totTramites%></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</table>
</div>
<script>
	jQuery('#FechaInicio').datepicker();
	jQuery('#FechaFinal').datepicker();
</script>