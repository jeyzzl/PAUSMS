<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.bitacora.spring.BitTramite"%>
<%@page import="aca.bitacora.spring.BitSolicitud"%>
<%@page import="aca.bitacora.spring.BitTramiteRequisito"%>
<%@page import="aca.bitacora.spring.BitRequisito"%>
<%@page import="aca.bitacora.spring.BitTramite"%>

<%@ include file= "menu.jsp" %>
<%
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	String codigoAlumno	= (String) request.getAttribute("matricula");
	String mensaje		= (String) request.getAttribute("mensaje");
	String tramiteId	= (String) request.getAttribute("tramiteId");
	String nombreTra	= (String) request.getAttribute("nombreTra");
	
	BitSolicitud solicitud 	= (BitSolicitud)request.getAttribute("solicitud");
	BitTramite bitTramite 	= (BitTramite)request.getAttribute("tramite");
	
	List<BitTramite> lisTramites					= (List<BitTramite>)request.getAttribute("lisTramites");
	List<BitTramiteRequisito> lisRequisitosTramite	= (List<BitTramiteRequisito>)request.getAttribute("lisRequisitosTramite");
	HashMap<String, BitRequisito> mapRequisitos		= (HashMap<String, BitRequisito>)request.getAttribute("mapRequisitos");	
	int cont = 1;
	
	String textoBoton = "Send Applicaiton";
	String comentario = "";
%>
<div class="container-fluid">
	<h2><a href="tramites" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a> On-line Application Form</h2>	
<% if(mensaje.equals("1")){%>
	<div class="alert alert-success" role="alert">
<!--  		Solicitud registrada con el folio No. ###  Servicios Escolares evaluará tu solicitud y la autorizará cuando hayas cumplido con los requisitos. -->
 		Application registered. School Services will evaluate your application and authorize it once the requirements have been met.
	</div>
<% }else if(mensaje.equals("2")){%>
	<div class="alert alert-danger" role="alert">
 		Error saving!
	</div>
<% }%>
		<form action="grabarSolicitud" name="form" id="form" method="post">
			<input type="hidden" id="Folio" name="Folio" value="<%=solicitud.getFolio()%>">
			<div class="mb-3">
<% 		if(!solicitud.getTramiteId().equals("0")){
			textoBoton = "Save Edits";
%>
			<input type="hidden" id="TramiteId" name="TramiteId" value="<%=solicitud.getTramiteId()%>">
			<h3><%=nombreTra%> (<%=bitTramite.getImporte()%>)</h3>
<% 		}else{%>
		    <select name="TramiteId" id="TramiteId" class="form-select" onchange="cambioTrabmite()">
		    	<option value="0">Select a form</option>
<% 			for(BitTramite tramite : lisTramites){%>
				<option value="<%=tramite.getTramiteId()%>" <%=tramite.getTramiteId().equals(tramiteId)?"selected":""%>><%=tramite.getNombre()%> - Price(<%=tramite.getImporte()%>)</option>
<% 			}%>
		    </select>
<% 		}%>
			</div>
			<div class="mb-3">
			    <label class="form-label">Describe briefly the reason for your request:</label><br>
			    <textarea class="form-control" name="TextoAlumno" id="TextoAlumno" rows="1" cols=""><%=solicitud.getTextoAlumno()%></textarea>
			</div>
<% 		if(!tramiteId.equals("0")){%>
		  		<button type="submit" class="btn btn-primary"><%=textoBoton%></button>
<% 		}%>
		</form>	
<% if(!tramiteId.equals("0")){%>
		<div class="alert alert-warning" role="alert">
 			Before submitting your application, carefully read the following guidelines. This will help you determine if you are eligible to apply and whether the application is eligible for approval.
		</div>
		Description:<br>
		<%=bitTramite.getRequisitos()%><br>
		<table class="table">
		<thead>
		<tr>
			<th width="5%">#</th>
			<th width="25%">Requirement</th>
			<th width="70%">Guidelines</th>
		</tr>
		</thead>
		<tbody>
<%		int row=1;
		for(BitTramiteRequisito requisito : lisRequisitosTramite){
			String nombreRequisito = "";
			if(mapRequisitos.containsKey(requisito.getRequisitoId())){
				nombreRequisito = mapRequisitos.get(requisito.getRequisitoId()).getNombre();
				comentario 		= mapRequisitos.get(requisito.getRequisitoId()).getComentario();
			}
%>
		<tr>
			<td width="5%"><%=row++%></td>
			<td width="25%"><b><%=nombreRequisito%></b></td>
			<td width="70%" class="text-muted"><%=comentario%></td>
		</tr>				
<% 		} %>		
		</tbody>				
		</table>				
<% 	}%>
</div>
<script type="text/javascript">
	function cambioTrabmite(){
		var tramite = document.getElementById("TramiteId").value;
		var folio = document.getElementById("Folio").value;
		window.location.href = "editarSolicitud?TramiteId="+tramite;
	}	
</script>
