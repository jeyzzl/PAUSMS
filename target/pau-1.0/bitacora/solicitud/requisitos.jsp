<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@ page import="aca.bitacora.spring.BitRequisito"%>
<%@ page import="aca.bitacora.spring.BitTramite"%>
<%@ page import="aca.bitacora.spring.BitSolicitud"%>
<%@ page import="aca.bitacora.spring.BitTramiteRequisito"%>
<%@ page import="aca.bitacora.spring.BitRequisitoAlumno"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	int cont = 1;
	BitSolicitud solicitud		= (BitSolicitud) request.getAttribute("solicitud");
	BitTramite tramite			= (BitTramite) request.getAttribute("tramite");

	String nombreAlumno			= (String) request.getAttribute("nombreAlumno");
	String mensaje				= (String) request.getAttribute("mensaje");
	
	List<BitTramiteRequisito> lisRequisitos 					= (List<BitTramiteRequisito>) request.getAttribute("lisRequisitos");
	HashMap<String, BitRequisito> mapRequisitos					= (HashMap<String, BitRequisito>) request.getAttribute("mapRequisitos");
	HashMap<String, BitRequisitoAlumno> mapaBitRequisitoAlumno	= (HashMap<String, BitRequisitoAlumno>) request.getAttribute("mapaBitRequisitoAlumno");
%>
<div class="container-fluid">
	<h2>Requisitos <small class="text-muted h5">(<%=solicitud.getCodigoPersonal()+": "+nombreAlumno%> - <%=tramite.getNombre()%>)</small></h2>
	<div class="d-flex alert alert-info">
		<a href="listado" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
	</div>
<% if(mensaje.equals("1")){%>
	<div class="alert alert-success" role="alert">
  		Guardado.
	</div>
<% }%>
<% if(mensaje.equals("2")){%>
	<div class="alert alert-danger" role="alert">
  		No se guardo.
	</div>
<% }%>
	<form name="form" action="grabar" method="post">
		<input type="hidden" name="CodigoPersonal" value="<%=solicitud.getCodigoPersonal()%>">
		<input type="hidden" name="TramiteId" value="<%=tramite.getTramiteId()%>">
		<input type="hidden" name="Folio" value="<%=solicitud.getFolio()%>">
		<div>
			<h5>Motivo de la solicitud: <%=solicitud.getTextoAlumno()%></h5>
			<h5>Comentario del administrador:</h5>
			<textarea name="Comentario" class="form-control"><%=solicitud.getTextoAdmin()%></textarea><br>
		</div>
		<table class="table table-sm table-bordered" style="width:100%">
			<thead class="table-info">
				<tr>
					<th>#</th>
					<th>Cumple</th>
					<th>Requisito</th>
<!-- 					<th>Tipo</th> -->
					<th>Descripción</th>
				</tr>
			</thead>
			<tbody>	
<%
		for(BitTramiteRequisito requi : lisRequisitos){
			
			BitRequisito requisito = new BitRequisito();
			if(mapRequisitos.containsKey(requi.getRequisitoId())){
				requisito = mapRequisitos.get(requi.getRequisitoId());
			}
			
			boolean ok = false;
			BitRequisitoAlumno re = new BitRequisitoAlumno();
			if(mapaBitRequisitoAlumno.containsKey(solicitud.getCodigoPersonal()+solicitud.getTramiteId()+requi.getRequisitoId()+solicitud.getFolio())){
				re = mapaBitRequisitoAlumno.get(solicitud.getCodigoPersonal()+solicitud.getTramiteId()+requi.getRequisitoId()+solicitud.getFolio());
				if(re.getEstado().equals("A")){
					ok = true;
				}
			}
%>		
				<tr>
					<td>
						<%=cont++%>&nbsp;&nbsp;
					</td>
					<td>
						<div class="form-check">
							<input class="form-check-input" type="checkbox" <%=ok?"checked":""%> name="<%=requisito.getRequisitoId()%>" value="<%=requisito.getRequisitoId()%>" id="flexCheckDefault">
					 	</div>
				 	</td>
					<td><%=requisito.getNombre()%></td>
<%-- 					<td><%=requisito.getTipo().equals("A")?"Automatico":"Manual"%></td> --%>
					<td><%=requisito.getComentario()%></td>
				</tr>
<%
		}
%>		
			</tbody>	
		</table>
		<div class="alert alert-info">
			<input class="btn btn-primary" type="submit" value="Grabar">
		</div>
	</form>
</div>