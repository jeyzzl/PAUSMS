<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@ page import="aca.bitacora.spring.BitRequisito"%>
<%@ page import="aca.bitacora.spring.BitTramite"%>
<%@ page import="aca.bitacora.spring.BitSolicitud"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	String estado 	= request.getParameter("Estado")==null?"0":request.getParameter("Estado");

	int cont = 1;
	List<BitSolicitud> lisSolicitudes		= (List<BitSolicitud>) request.getAttribute("lisSolicitudes");
	
	HashMap<String, BitTramite> mapaTramite					= (HashMap<String, BitTramite>) request.getAttribute("mapaTramite");
	HashMap<String, String> mapaEtiquetas	 				= (HashMap<String, String>) request.getAttribute("mapaEtiquetas");
	HashMap<String, AlumPersonal> mapaAlumnosConSolicitu	= (HashMap<String, AlumPersonal>) request.getAttribute("mapaAlumnosConSolicitu");	
	HashMap<String,String> mapaRequisitosEnTramite 			= (HashMap<String, String>) request.getAttribute("mapaRequisitosEnTramite");
	HashMap<String,String> mapaBitRequisitosCumple 			= (HashMap<String, String>) request.getAttribute("mapaBitRequisitosCumple");
%>
<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-table.min.css">	
  	<script src="<%=request.getContextPath()%>/js/bootstrap-table.min.js" type="text/javascript"></script>
</head>

<div class="container-fluid">
	<form name="frmTramites" action="listado" method="post">
	<h4>
		Solicitudes de trámites en línea&nbsp;
		<select name="Estado" class="form-selected" style="width:170px;" onchange="javascript:document.frmTramites.submit()">
			<option value="0" <%=estado.equals("0")?"selected":""%>>Todos</option>
			<option value="1" <%=estado.equals("1")?"selected":""%>>Solicitud</option>
			<option value="2" <%=estado.equals("2")?"selected":""%>>Atendiendo</option>
			<option value="3" <%=estado.equals("3")?"selected":""%>>Completo</option>
			<option value="4" <%=estado.equals("4")?"selected":""%>>Autorizado</option>
		</select>			
	</h4>
	</form>
	<hr>	
	<table id="table" class="table" data-toggle="table" data-pagination="false" data-search="true" data-show-columns="false" data-page-list="[10, 25, 50, 100, all]" data-show-header="true" data-show-footer="true">
	<thead class="table-info">
	<tr>
		<th width="3%">#</th>
		<th width="3%">Op.</th>
		<th width="7%">Fecha</th>			
		<th width="3%">Auto.</th>
		<th width="5%">Estado</th>
		<th width="5%">Requisitos</th>
		<th width="20%">Tramite</th>
		<th width="5%">Matricula</th>
		<th width="5%">Nombre</th>
		<th width="44%">Motivo de la solicitud</th>
	</tr>
	</thead>
	<tbody>	
<%
		for(BitSolicitud solicitud : lisSolicitudes){
			
			String nombreTramite = "";
			if(mapaTramite.containsKey(solicitud.getTramiteId())){
				nombreTramite = mapaTramite.get(solicitud.getTramiteId()).getNombre();
			}

			AlumPersonal alumPersonal = new AlumPersonal();
			if(mapaAlumnosConSolicitu.containsKey(solicitud.getCodigoPersonal())){
				alumPersonal = mapaAlumnosConSolicitu.get(solicitud.getCodigoPersonal());
			}
			
			String requisitos 	= "0";
			if (mapaRequisitosEnTramite.containsKey(solicitud.getTramiteId())){			
				requisitos 		= mapaRequisitosEnTramite.get(solicitud.getTramiteId());
			}
			
			String cumplidos 	= "0";			
			if (mapaBitRequisitosCumple.containsKey(solicitud.getCodigoPersonal()+solicitud.getTramiteId()+solicitud.getFolio())){	
				cumplidos 		= mapaBitRequisitosCumple.get(solicitud.getCodigoPersonal()+solicitud.getTramiteId()+solicitud.getFolio());
			}
			
			String colorCumplidos 	= "0";
			if (cumplidos.equals("0")){
				colorCumplidos = "bg-secondary";
			}else if (Integer.parseInt(requisitos)==Integer.parseInt(cumplidos)){
				colorCumplidos = "bg-dark";
			}else{
				colorCumplidos = "bg-warning";				
			}
			
			String estadoNombre = "";
			
			if(solicitud.getStatus().equals("1")){
				estadoNombre = "Solicitud";
			}else if(solicitud.getStatus().equals("2")){
				estadoNombre = "Atendiendo";
			}else if(solicitud.getStatus().equals("3") ){
				estadoNombre = "Completo";
			}else if(solicitud.getStatus().equals("4") ){
				estadoNombre = "Autorizado";
			}
			
			boolean autorizado = false;
			if(solicitud.getStatus().equals("4")){
				autorizado = true;
			}
			
			String numEtiquetas = "0";
			if (mapaEtiquetas.containsKey(solicitud.getFolioTramite())){
				numEtiquetas = mapaEtiquetas.get(solicitud.getFolioTramite());
			}
%>		
		<tr>
			<td>
				<%=cont++%>&nbsp;&nbsp;
			</td>
			<td>
<% 			if(cumplidos.equals("0")){%>
				<a href="javascript:Borrar('<%=solicitud.getCodigoPersonal()%>','<%=solicitud.getFolio()%>','<%=solicitud.getFolioTramite()%>')"><i class="fas fa-trash-alt" style="color:red"></i></a>
<% 			}%>
				<a href="requisitos?CodigoPersonal=<%=solicitud.getCodigoPersonal()%>&Folio=<%=solicitud.getFolio()%>"><i class="fas fa-pencil-alt"></i></a>
			</td>
			<td><%=solicitud.getFecha()%></td>			
			<td>			
<% 			if(solicitud.getStatus().equals("4") || solicitud.getStatus().equals("3")){%>
<% 				if(autorizado){%>
					<a onclick="javascript:Autorizar('3','<%=solicitud.getFolio()%>','<%=solicitud.getCodigoPersonal()%>')" title="Está autorizado. Haz clic para desautorizar"><i class="fas fa-toggle-on fa-lg" style="color:green"></i></a> 
<%	 			}else{%>
					<a onclick="javascript:Autorizar('4','<%=solicitud.getFolio()%>','<%=solicitud.getCodigoPersonal()%>')" title="No esta autorizado. Haz clic para autorizar"><i class="fas fa-toggle-off fa-lg" style="color:orange"></i></a> 
<% 				}%>
<% 			}else{%>
				<span title="Sin Autorizar"><i class="fas fa-toggle-off fa-lg" style="color:gray"></i></a>
<%			} %>
			<b><%=numEtiquetas%></b>
			</td>			
			<td><%=estadoNombre%></td>
			<td>
			    <span class="badge <%=colorCumplidos%> rounded-pill"><%=cumplidos%></span> de <span class="badge bg-dark rounded-pill"><%=requisitos%></span>
			</td>
			<td><%=nombreTramite%></td>
			<td><%=solicitud.getCodigoPersonal()%></td>
			<td><%=alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno()%></td>
			<td><%=solicitud.getTextoAlumno()%></td>
		</tr>
<%
		}
%>			
	</tbody>
	</table>
</div>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	$(document).ready(function() {
	    $('#table').DataTable( {
	        "order": [[ 4, 'asc' ]]
	    });	    
	} );
	
	function Borrar(codigo,folio,tramiteId){
		if (confirm("¿Deseas eliminar éste folio?")){
			document.location.href="borrarFolio?Codigo="+codigo+"&Folio="+folio+"&TramiteId="+tramiteId;
		}		
	}	

	function Autorizar(autorizar,folio,codigoPersonal){
		if(autorizar === '4'){
			texto = '¿Deseas autorizar este tramite?';
		}else if(autorizar === '3'){
			texto = '¿Deseas no autorizar este tramite?';
		}
		
		if (confirm(texto)){
			document.location.href="autorizarTramite?Autorizar="+autorizar+"&Folio="+folio+"&CodigoPersonal="+codigoPersonal;	
		}		
	}	
</script>