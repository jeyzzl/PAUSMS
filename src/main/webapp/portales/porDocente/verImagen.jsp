<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@page import="java.io.File"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.FileOutputStream"%>

<jsp:useBean id="Seccion" scope="page" class="aca.por.PorSeccion"/>
<jsp:useBean id="SeccionEmp" scope="page" class="aca.por.PorSeccionEmp"/>

<%		 
	String portafolioId		= (String) session.getAttribute("portafolioId");
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String seccionId		= request.getParameter("SeccionId");
	String folio 			= request.getParameter("Folio");
	
	// Datos del la seccion
	Seccion.setPorId(portafolioId);	
	Seccion.setSeccionId(seccionId);
	
	if (Seccion.existeReg(conEnoc)){
		Seccion.mapeaRegId(conEnoc, portafolioId, seccionId);
		SeccionEmp.mapeaRegId(conEnoc, portafolioId, seccionId, codigoPersonal, folio);
	}	
%>
<div class="container-fluid">
	<h2>
		<spring:message code="aca.Empleado"/>
		<small class="text-muted fs-4">( <%=codigoPersonal%> - <%=aca.vista.MaestrosUtil.getNombreCorto(conEnoc, codigoPersonal, "NOMBRE")%> - <%=Seccion.getTitulo()%> <%=Seccion.getSeccionNombre()%> )</small>
	</h2>	
	<div class="alert alert-info"><a href="seccion.jsp" class="btn btn-primary"><icon class="icon-arrow-left icon-white"></icon> Regresar</a></div>
	<label for=""><strong><spring:message code='aca.Descripcion'/>:</strong></label>
	<p><%=SeccionEmp.getComentario()%></p>
	<div class="row">
  		<div class="span5">
  			<img src="imagen?SeccionId=<%=seccionId%>&Folio=<%=folio %>" style="max-width:100%; max-height:100%;">
  		</div>  		
	</div>	
</div>
<%@ include file="../../cierra_enoc.jsp" %>