<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@page import="java.io.File"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.FileOutputStream"%>

<jsp:useBean id="Seccion" scope="page" class="aca.por.PorSeccion"/>
<jsp:useBean id="SeccionEmp" scope="page" class="aca.por.PorSeccionEmp"/>

<%		 
	String porId			= request.getParameter("porId");
	String codigoPersonal	= request.getParameter("codigoPersonal");
	String seccionId		= request.getParameter("SeccionId");
	String folio 			= request.getParameter("Folio");
	
	// Datos del la seccion
	Seccion.setPorId(porId);	
	Seccion.setSeccionId(seccionId);
	
	if (Seccion.existeReg(conEnoc)){
		Seccion.mapeaRegId(conEnoc, porId, seccionId);
		SeccionEmp.mapeaRegId(conEnoc, porId, seccionId, codigoPersonal, folio);
	}	
%>
<div class="container-fluid">
	<h2>
		<spring:message code="aca.Empleado"/>
		<small  class="text-muted fs-4" >( <%=codigoPersonal%> - <%=aca.vista.MaestrosUtil.getNombreCorto(conEnoc, codigoPersonal, "NOMBRE")%>)</small>
	</h2>	
	<div class="alert alert-info">
		<a href="datos?porId=<%=porId%>&seccionId=<%=seccionId%>&codigoPersonal=<%=codigoPersonal%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i> Regresar</a>
	</div>
	<h3><%=Seccion.getTitulo()%> <%=Seccion.getSeccionNombre()%></h3> <br>
	<label for=""><strong><spring:message code='aca.Descripcion'/>:</strong></label>
	<p><%=SeccionEmp.getComentario()%></p>
	<div class="row">
  		<div class="span5">
  			<img src="imagen?SeccionId=<%=seccionId%>&Folio=<%=folio%>&codigoPersonal=<%=codigoPersonal%>&porId=<%=porId%>" style="max-width:100%; max-height:100%;">
  		</div>  		
	</div>	
</div>
<%@ include file="../../cierra_enoc.jsp" %>