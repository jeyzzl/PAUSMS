<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@ page import="aca.bitacora.spring.BitTramite"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	int cont = 1;
	List<BitTramite> lisTramites 			= (List<BitTramite>) request.getAttribute("lisTramites");
	HashMap<String, String> mapaAreas 		= (HashMap<String, String>) request.getAttribute("mapaAreas");
	HashMap<String, String> mapaTramite		= (HashMap<String, String>) request.getAttribute("mapaTramite");
	HashMap<String, String> mapaTotales		= (HashMap<String, String>) request.getAttribute("mapaTotales");
	HashMap<String, String> mapaRequisitos	= (HashMap<String, String>) request.getAttribute("mapaRequisitos");
%>

<script type="text/javascript">
	function Borrar(TramiteId) {
		if (confirm("Quieres eliminar este tramite ?" ) == true) {
			document.location = "borrarTramite?TramiteId="+TramiteId;
		}
	}
</script>

<div class="container-fluid">
	<h2>Catálogo de Trámites</h2>
	<div class="alert alert-info d-flex align-items-center">
		<a href="editar" style="text-align:center;" class="btn btn-primary"><i class="fas fa-plus"></i> Nuevo tramite</a>
		&nbsp;&nbsp;
		<input type="text" id="buscar" class="form-control search-query" placeholder="Buscar" style="width:200px;">
	</div>
	<br>
	<table id="table" class="table table-sm table-bordered" style="width:100%">
	<thead class="table-info">
		<tr>
			<th>#</th>			
			<th>Op.</th>			
			<th>Area responsable</th>
			<th>Nombre del Trámite</th>
			<th>Requisitos</th>			
			<th class="text-end">Num.Tram.</th>
			<th class="text-end">Días Min.</th>
			<th class="text-end">Días Max.</th>
			<th class="text-end">Días Prom.</th>
			<th class="text-end">Tipo</th>
			<th class="text-end">En línea</th>
			<th class="text-end">Costo</th>
		</tr>
	</thead>	
<%
		for(BitTramite tramite : lisTramites){
			String tipoNombre = "-";
			if (tramite.getTipo().equals("G")) tipoNombre = "General";
			if (tramite.getTipo().equals("E")) tipoNombre = "Externo";
			if (tramite.getTipo().equals("I")) tipoNombre = "Interno";
			
			String total = "0";
			if (mapaTotales.containsKey(tramite.getTramiteId())){
				total = mapaTotales.get(tramite.getTramiteId());
			}
			
			String requisitos = "0";
			if (mapaRequisitos.containsKey(tramite.getTramiteId())){
				requisitos = mapaRequisitos.get(tramite.getTramiteId());
			}
%>		
		<tr>
			<td><%=cont%></td>
			<td>				
				<a href="editar?TramiteId=<%=tramite.getTramiteId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>				
<% 			if(!mapaTramite.containsKey(tramite.getTramiteId())){%>
				&nbsp;
				<a href="javascript:Borrar('<%=tramite.getTramiteId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-times"></i></a>
<% 			}%>
			</td>			
<%
			if(mapaAreas.containsKey(tramite.getAreaId())){
%>
			<td><%=mapaAreas.get(tramite.getAreaId())%></td>
<%
			}else{
%>
			<td>-</td>
<%			} %>		
			<td><%=tramite.getNombre()%></td>
			<td>				
				<a href="agregarRequisitos?TramiteId=<%=tramite.getTramiteId()%>" class="btn <%=requisitos.equals("0")?"btn-warning":"btn-success"%> btn-sm rounded-pill" title="Agregar/Quitar requisitos"><%=requisitos%></a>
			</td>
			<td class="text-end"><%=total%></td>
			<td class="text-end"><%=tramite.getMinimo()%></td>
			<td class="text-end"><%=tramite.getMaximo()%></td>
			<td class="text-end"><%=tramite.getPromedio()%></td>
			<td class="text-end"><%=tipoNombre%></td>
			<td class="text-end"><%=tramite.getSolAlumno().equals("S")?"<span class='badge rounded-pill bg-success'>Si</span>":"<span class='badge rounded-pill bg-warning'>No</span>"%></td>		
			<td class="text-end"><%=tramite.getImporte()%></td>
		</tr>
<%
			cont++;
		}
%>			
	</table>
</div>
<script src="../../js/search.js"></script>
<script>
	$('#buscar').search();
</script>