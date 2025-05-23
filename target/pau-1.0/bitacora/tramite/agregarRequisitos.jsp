<%@ page import="aca.bitacora.spring.BitTramite"%>
<%@ page import="aca.bitacora.spring.BitRequisito"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	BitTramite tramite		= (BitTramite) request.getAttribute("tramite");
	String mensaje			= (String) request.getAttribute("mensaje");

	List<BitRequisito> lisRequisito	= (List<BitRequisito>) request.getAttribute("lisRequisito");
	
	HashMap<String, String> mapaRequisitosTramite = (HashMap<String, String>) request.getAttribute("mapaRequisitosTramite");
%>

<div class="container-fluid">
	<h2>Tramite <small class="text-muted h5">(<%=tramite.getNombre()%>)</small></h2>
	<div class="alert alert-info">
		<a href="tramite" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
	</div>
<% if(mensaje.equals("1")){%>
	<div class="alert alert-success" role="alert">
	 	Grabado
	</div>
<% }else if(mensaje.equals("2")){%>
	<div class="alert alert-danger" role="alert">
  		No pudo grabar
	</div>
<% }%>
	<form name="formTramite" action="grabarRequisitos" method="post">
		<input type="hidden" name="TramiteId" value="<%=tramite.getTramiteId()%>">
<% 		for(BitRequisito requisito : lisRequisito){
			boolean ok = false;
			if(mapaRequisitosTramite.containsKey(requisito.getRequisitoId())){
				String tra = mapaRequisitosTramite.get(requisito.getRequisitoId());
				if(tramite.getTramiteId().equals(tra)){
					ok = true;
				}
			}
%>
			<div class="form-check">
				<input class="form-check-input" type="checkbox" <%=ok?"checked":""%> name="<%=requisito.getRequisitoId()%>" value="<%=requisito.getRequisitoId()%>" id="flexCheckDefault">
			  	<label class="form-check-label" for="flexCheckDefault">
				    <span class="fs-5"><b><%=requisito.getNombre()%></b></span><br><span class="text-muted"><%=requisito.getComentario()%></span>
			 	</label>
		 	</div>
<% 		}%>
		<br>
		<div class="alert alert-info">
		<input class="btn btn-primary" type="submit" value="Grabar">
		</div>
	</form>
</div>