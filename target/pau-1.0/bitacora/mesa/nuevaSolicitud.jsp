<%@ page import="java.text.DecimalFormat" %>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.bitacora.spring.BitTramite"%>
<%@page import="aca.bitacora.spring.BitTramiteAlumno"%>
<%@page import="aca.vista.spring.Alumno"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.archivo.spring.ArchDocAlum"%>
<%@page import="aca.financiero.spring.FinSaldo"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Refrescar(){
		document.frmSolicitud.Accion.value = "0";
		document.frmSolicitud.submit();
	}
	
	function Grabar(){		
		document.frmSolicitud.Accion.value = "1";
		document.frmSolicitud.submit();
	}	
</script>

<%
	DecimalFormat dmf					= new DecimalFormat("###,##0.00; (###,##0.00)");
	
	String codigoAlumno					= (String) request.getAttribute("codigoAlumno");
	String alumnoNombre					= (String) request.getAttribute("alumnoNombre");
	String nombreCarrera				= (String) request.getAttribute("nombreCarrera");
	String tramiteId					= (String) request.getAttribute("tramiteId");
	BitTramiteAlumno bitTramiteAlumno 	= (BitTramiteAlumno) request.getAttribute("bitTramiteAlumno");
	BitTramite bitTramite 				= (BitTramite) request.getAttribute("bitTramite");
	String mensaje			 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	boolean inscrito					= (boolean) request.getAttribute("inscrito");		
	FinSaldo finSaldo 					= (FinSaldo) request.getAttribute("finSaldo");	
	String infoSaldo					= Float.valueOf(finSaldo.getSaldoSP())<0?"Db":"Cr";	
	// Lista de tramites
	List<BitTramite> lisTramites 		= (List<BitTramite>) request.getAttribute("lisTramites");	
	// Lista de documentos de un alumno
	List<ArchDocAlum> lisDocumentos 	= (List<ArchDocAlum>) request.getAttribute("lisDocumentos");
	// Mapa de areas 
	HashMap<String, String> mapAreas 	= (HashMap<String, String>) request.getAttribute("mapAreas");
	HashMap<String, String> mapDoc 		= (HashMap<String, String>) request.getAttribute("mapDoc");	
	Alumno alumno 						= (Alumno) request.getAttribute("alumno");	
	AlumPersonal alumnoPersonal 		= (AlumPersonal) request.getAttribute("alumnoPersonal");	
%>
<style>
	.pos {float:left;}
</style>

<div class="container-fluid">
	<h2>BSE / Nueva Solicitud<small class="text-muted h4">&nbsp;&nbsp;( <%=codigoAlumno%> - <%=alumnoNombre%> - <%=alumno.getPlanId()%> - <%=nombreCarrera%> )</small></h2>
	<div class="alert alert-info">
		<a href="entrada" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		<b>Residencia:</b> <%=alumno.getResidenciaId().equals("E")?"Externo":"Interno"%>&nbsp;&nbsp;
		<b>Estado:</b> <%=inscrito ? "INSCRITO" : "No Inscrito"%>&nbsp;&nbsp;
		<b>Saldo:</b> <%=infoSaldo%> $ <%=dmf.format(Float.valueOf(finSaldo.getSaldoSP())) %>&nbsp;&nbsp;
		<b>Saldo vencido:</b> <%=dmf.format(Float.valueOf(finSaldo.getSaldoVencido()))%>&nbsp;&nbsp;
		<b>Correo:</b> <%=alumno.getCorreo()%>
	</div>
<%	if(!mensaje.equals("-")){ %>
	<div class="alert alert-success"><%=mensaje%></div>
<%	} %>	
<%
	if(!alumno.getCodigoPersonal().equals("")){
%>
	<h4>Lista de documentos en archivo</h4>
	<div class="alert alert-info">
<%
		int row = 0;
		for (ArchDocAlum documento : lisDocumentos){
			row++;
			String descripcion = "-";
			if(mapDoc.containsKey(documento.getIdDocumento())){
				descripcion = mapDoc.get(documento.getIdDocumento());
			}
				
			out.print("&nbsp;<span class='badge bg-dark'>"+row+"</span>&nbsp;"+descripcion);
		}
%>	
	</div>
	<form name="frmSolicitud" action="grabarSolicitud" method="post">
		<input name="Accion" type="hidden"/>
		<input name="Folio" type="hidden" value="<%=bitTramiteAlumno.getFolio()%>">
		<div class="container-fluid">		
			Trámite o servicio: 
			<select style="width:350px;" class="custom-select" name="TramiteId" id="TramiteId" onchange="javaScritp:Refrescar()">
	<%
			for(BitTramite tramite : lisTramites){
	%>
				<option <%if(tramiteId.equals(tramite.getTramiteId()))out.print("Selected");%> value="<%=tramite.getTramiteId()%>">
					[<%=tramite.getTipo()%>] - <%=tramite.getNombre()%>
				</option>
	<%
			}
	%>
			</select>&nbsp;
			Área que atenderá: <strong><font color="#5FB404"><%=mapAreas.get(bitTramite.getAreaId())%></font></strong>
			<br>		
			Detalle adicional : <textarea name="Comentario" class="form-control" style="width:730px;" rows="3" cols="30"><%=bitTramiteAlumno.getComentario()%></textarea><br>
		</div>
	</form>
	<div class="alert alert-info oculto">
		<a href="javascript:Grabar()" class="btn btn-primary">Registrar solicitud</a>&nbsp;&nbsp;
		<% if (!mensaje.equals("X")) out.print(mensaje);%>
	</div>
	<br>
	
	<table class="table" style="width:50%;">
		<tr>
			<th style="background-color:#A9D0F5;">Estadística</th>
			<th>Tiempo minimo :</th>
			<th style="background-color:#A9D0F5;"><%=bitTramite.getMinimo()%> d&iacute;as</th>
			<th>Tiempo maximo :</th>
			<th style="background-color:#A9D0F5;"><%=bitTramite.getMaximo()%> d&iacute;as</th>
			<th>Tiempo promedio : </th>
			<th style="background-color:#A9D0F5;"><%=bitTramite.getPromedio()%> d&iacute;as</th>
		</tr>
	</table>	
	<h2>Descripción y requisitos:</h2><br>
	<h4>Informacion para el trámite de <%=bitTramite.getNombre()%></h4><br>
	<h5><%=bitTramite.getRequisitos()%></h5>
<%
	}else{
%>
	<h2>Por favor elige un alumno</h2>
<%
	}
%>
</div>