<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.alumno.spring.AlumEstado"%>
<%@ page import="aca.catalogo.spring.CatTipoCal"%>
<%@ page import="aca.carga.spring.CargaBloque"%>
<%@ page import="aca.carga.spring.CargaAlumno"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->

<%	
	AlumEstado alumEstado	= (AlumEstado) request.getAttribute("alumEstado");
	String codigoAlumno 	= (String) request.getAttribute("codigoAlumno");
	String nombreAlumno 	= (String) request.getAttribute("nombreAlumno");
	String cargaNombre	 	= (String) request.getAttribute("cargaNombre");
	String mensaje	 		= (String) request.getAttribute("mensaje");
	List<CatTipoCal> lisStatus	 		= (List<CatTipoCal>) request.getAttribute("lisStatus");			
	List<CargaBloque> lisBloques 		= (List<CargaBloque>) request.getAttribute("lisBloques");
	List<CargaAlumno> lisCargas 		= (List<CargaAlumno>) request.getAttribute("lisCargas");
	
	HashMap<String, Carga> mapaCargas	= (HashMap<String,Carga>) request.getAttribute("mapaCargas");
%>
<div class="container-fluid">
	<h2>Student: <small class="text-muted fs-5">( <%=codigoAlumno%> - <%=nombreAlumno%> )</small></h2>
	<div class="alert alert-info">
		<a href="estado" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
	</div>
<% 	if(mensaje.equals("1")){%>
	<div class="alert alert-success">
		Saved
	</div>
<% 	} %>
	<form name="documento" method="post" action="nuevoEstado">
		<div class="form-group">
			<strong>Loads:</strong>
			<select class="form-control" name="CargaId">
<%			
			for(CargaAlumno carga : lisCargas){
				String nombreCarga = "-";
				
				if(mapaCargas.containsKey(carga.getCargaId())){
					nombreCarga = mapaCargas.get(carga.getCargaId()).getNombreCarga();
				}
%>		
				<option value="<%=carga.getCargaId()%>" <%=alumEstado.getCargaId().equals(carga.getCargaId())?"selected":""%>><%=carga.getCargaId()%> <%=nombreCarga %></option>
<% 			} %>
			</select>
			<br>
			<strong>Blocks:</strong>
			<select class="form-control" name="BloqueId">
<%			for(CargaBloque bloque : lisBloques){ %>		
				<option value="<%=bloque.getBloqueId()%>" <%=alumEstado.getBloqueId().equals(bloque.getBloqueId())?"selected":""%>><%=bloque.getBloqueId()%> - <%=bloque.getNombreBloque()%></option>
<% 			} %>
			</select>
			<br>
			<strong>Status:</strong>
			<select class="form-control" name="Estado">
<%			for (CatTipoCal tipo : lisStatus){ %>		
				<option value="<%=tipo.getTipoCalId()%>" <%=alumEstado.getEstado().equals(tipo.getTipoCalId())?"selected":""%> ><%=tipo.getNombreTipoCal()%></option>
<% 			} %>
			</select>
		</div>
		<br>
		<button type="submit" class="btn btn-primary">Submit</button>
	</form>
</div>