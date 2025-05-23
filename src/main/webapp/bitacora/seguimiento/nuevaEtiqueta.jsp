<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.bitacora.spring.BitTramiteAlumno"%>
<%@page import="aca.bitacora.spring.BitTramite"%>

<jsp:useBean id="BitTramiteAlumno" scope="page" class="aca.bitacora.BitTramiteAlumno"/>
<jsp:useBean id="BitTramiteAlumnoU" scope="page" class="aca.bitacora.BitTramiteAlumnoUtil"/>
<jsp:useBean id="BitEtiqueta" scope="page" class="aca.bitacora.BitEtiqueta"/>
<jsp:useBean id="BitEtiquetaU" scope="page" class="aca.bitacora.BitEtiquetaUtil"/>

<%
	String areaId				= request.getParameter("AreaId")==null?"0":request.getParameter("AreaId");
	String nombreArea			= (String)request.getAttribute("nombreArea");	
	String tramiteId 			= request.getParameter("TramiteId")==null?"0":request.getParameter("TramiteId");
	String estadoId 			= request.getParameter("EstadoId")==null?"0":request.getParameter("EstadoId");
	String fechaInicio			= request.getParameter("FechaInicio")==null?"0":request.getParameter("FechaInicio");
	String fechaFinal			= request.getParameter("FechaFinal")==null?"0":request.getParameter("FechaFinal");
	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	List<BitTramiteAlumno> lisTramitesFiltro 	= (List<BitTramiteAlumno>)request.getAttribute("lisTramitesFiltro");
	HashMap<String, BitTramite> mapaTramites 	= (HashMap<String, BitTramite>)request.getAttribute("mapaTramites");
	HashMap<String, String> mapaAlumnos 		= (HashMap<String, String>)request.getAttribute("mapaAlumnos");
	HashMap<String, String> mapaEstados 		= (HashMap<String, String>)request.getAttribute("mapaEstados");	
%>

<div class="container-fluid">
	<h2>BSE / Seguimiento / Nueva etiqueta</h2>
	<div class="alert alert-info">
		<a href="seguimiento?AreaId=<%=areaId%>" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
<% if(!mensaje.equals("-")){%>
	<div class="alert alert-success" role="alert"><%=mensaje%></div>
<% }%>
	<form name="form" action="grabarEtiqueta" method="POST">
	<input name="AreaId" type="hidden" value="<%=areaId%>"/>
	<input name="TramiteId" type="hidden" value="<%=tramiteId%>"/>
	<input name="EstadoId" type="hidden" value="<%=estadoId%>"/>
	<input name="FechaInicio" type="hidden" value="<%=fechaInicio%>"/>
	<input name="FechaFinal" type="hidden" value="<%=fechaFinal%>"/>
	<div class="row container-fluid">
		<div class="span3">
			<label for="area">Area</label>
			<input type="text" class="text form-control" name="area" size="50" value="<%=nombreArea %>" readonly="readonly">  
			<br><br>
			<label for="Comentario">Comentario</label>
			<textarea name="Comentario" class="form-control" style="width:730px;" rows="3" cols="50"><%=0%></textarea><br><br>
		</div>
	</div>
	<div class="alert alert-info">
		<input type="submit" class="btn btn-primary" value="Guardar">
	</div>
	<table class="table" style="width:100%">
		<tr>
			<th>
			Op.
				<a onclick="jQuery('.checkboxCarga').prop('checked',true)" class="btn btn-sm"><spring:message code='aca.Todos'/></a>&nbsp;
				<a onclick="jQuery('.checkboxCarga').prop('checked', false)" class="btn btn-sm"><spring:message code='aca.Ninguno'/></a>	
			</th>
			<th>#</th>
			<th>Folio</th>
			<th>Trámite</th>
			<th>Matricula</th>
			<th>Solicitante</th>
			<th>Inicio</th>
			<th>Término</th>
			<th>Estado</th>
		</tr>
<%		int row=0;
		for(BitTramiteAlumno tramite : lisTramitesFiltro){
			row++;
			
			String tramiteNombre = "-";
			if (mapaTramites.containsKey(tramite.getTramiteId())){
				tramiteNombre = mapaTramites.get(tramite.getTramiteId()).getNombre();
			} 
			
			String alumnoNombre = "-";
			if (mapaAlumnos.containsKey(tramite.getCodigoPersonal())){
				alumnoNombre = mapaAlumnos.get(tramite.getCodigoPersonal());
			}
			
			String estadoNombre = "-";
			if (mapaEstados.containsKey(tramite.getEstado())){
				estadoNombre = mapaEstados.get(tramite.getEstado());
			}
%>
		<tr>
			<td><input class="checkboxCarga" type="checkbox" name="<%=tramite.getFolio()%>" value="S"></td>
			<td><%=row%></td>
			<td><%=tramite.getFolio()%></td>
			<td><%=tramiteNombre%></td>
			<td><%=tramite.getCodigoPersonal()%></td>
			<td><%=alumnoNombre%></td>
			<td><%=tramite.getFechaInicio()%></td>
			<td><%=tramite.getFechaFinal()==null?"-":tramite.getFechaFinal()%></td>
			<td><%=estadoNombre%></td>
		</tr>
<%	} %>		
	</table>
	</form>
</div>
