<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.bitacora.spring.BitTramiteAlumno"%>
<%@page import="aca.bitacora.spring.BitTramite"%>
<%@page import="aca.bitacora.spring.BitArea"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%	
	//String mensaje 			= request.getParameter("Mensaje") == null ? "X" : request.getParameter("Mensaje");
	String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String nombreAlumno 	= (String) request.getAttribute("nombreAlumno");
	String nombreArea 		= (String) request.getAttribute("nombreArea");

	BitTramiteAlumno bitTramiteAlumno 	= (BitTramiteAlumno) request.getAttribute("bitTramiteAlumno");
	List<BitTramite> lisTramites 		= (List<BitTramite>) request.getAttribute("lisTramites");
	HashMap<String,String> mapaAreas	= (HashMap<String,String>) request.getAttribute("mapaAreas");
%>
<div class="container-fluid">
	<h2>BSE / Seguimiento / Turnar <small class="text-muted h5">(<%=bitTramiteAlumno.getCodigoPersonal()%> - <%=nombreAlumno%>)</small></h2>
	<div class="alert alert-info">
		<a href="etiquetas?Folio=<%=folio%>" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<form name="frmTurnar" action="grabarNuevoTramite" method="POST">
	<div class="row container-fluid">									
		<div class="span4">
			<label for="Folio">Folio origen</label>
			<input type="text" class="text form-control" name="Folio" size="8"  value="<%=folio%>" readonly="readonly">
			<br><br>
			<label for="AreaId">Area origen</label>
			<input type="text" class="text form-control" name="AreaId" size="40" value="<%=nombreArea%>" readonly="readonly">  
			<br><br>
			<label for="tremiteId">Tramite</label>
			<select style="width:450px;" class="form-control" name="TramiteId" id="TramiteId">
<%
	for(BitTramite tramite : lisTramites){
		String areaNombre = "-";			
		if(mapaAreas.containsKey(tramite.getAreaId())){
			areaNombre = mapaAreas.get(tramite.getAreaId());
		}			
%>
				<option <%if(bitTramiteAlumno.getTramiteId().equals(tramite.getTramiteId()))out.print("Selected");%> value="<%=tramite.getTramiteId()%>">
					<%=tramite.getNombre()%> ( <%=areaNombre%> )
				</option>
<%	} %>
			</select>&nbsp;&nbsp;
			<br><br>
			Detalle adicional : <textarea name="Comentario" class="form-control" style="width:730px;" rows="3" cols="30">-</textarea><br><br>
		</div>			
	</div>
	<div class="alert alert-info">
		<h4 style="color: black;">Comentario: <%=bitTramiteAlumno.getComentario()%></h4>
	</div>
	<div class="alert alert-info">
		<input type="submit" class="btn btn-primary" value="Guardar">
	</div>
	</form>
</div>