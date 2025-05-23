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
	
	// Datos del la seccion
	SeccionEmp.setPorId(portafolioId);	
	SeccionEmp.setSeccionId(seccionId);
	SeccionEmp.setCodigoPersonal(codigoPersonal);
	if (SeccionEmp.existeReg(conEnoc)){
		SeccionEmp.mapeaRegId(conEnoc, portafolioId, seccionId, codigoPersonal);
		Seccion.mapeaRegId(conEnoc, portafolioId, seccionId);
	}
%>
<div class="container-fluid"> 
	<h2> 
		<spring:message code="aca.Empleado"/>
		<small class="text-muted fs-4">( <%=codigoPersonal%> - <%=aca.vista.MaestrosUtil.getNombreCorto(conEnoc, codigoPersonal, "NOMBRE")%>)</small>
	</h2> 
	<div class="alert alert-info">
		<a href="datos?SeccionId=<%=seccionId%>&codigoPersonal=<%=codigoPersonal%>&porId=<%=portafolioId%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i> Regresar</a>
	</div>
	<h3><%=Seccion.getTitulo()%> <%=Seccion.getSeccionNombre()%></h3> <br>
	<label for=""><strong><spring:message code='aca.Descripcion'/>:</strong></label>
	<p><%=SeccionEmp.getComentario()%></p>
<%	if(SeccionEmp.getTextoCorto().substring(0, 7).equals("<iframe")){
		
	}else{ 
%> 
		<iframe src="<%=SeccionEmp.getTextoCorto()%>" width="100%" height="75%"></iframe>
	<%
	}
%>
</div>
<%@ include file="../../cierra_enoc.jsp" %>