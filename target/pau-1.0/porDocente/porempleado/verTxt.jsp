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
	String porId			= request.getParameter("porId")==null?"0":request.getParameter("porId");
	String codigoPersonal	= request.getParameter("codigoPersonal")==null?"0":request.getParameter("codigoPersonal");
	String seccionId		= request.getParameter("seccionId")==null?"0":request.getParameter("seccionId");
	String folio 			= request.getParameter("folio")==null?"0":request.getParameter("folio");
	String seccionTipo		= request.getParameter("seccionTipo")==null?"":request.getParameter("seccionTipo");
	
	
	// Datos del la seccion
	Seccion.setPorId(porId);	
	Seccion.setSeccionId(seccionId);
	
	SeccionEmp.mapeaRegId(conEnoc, porId, seccionId, codigoPersonal, folio);
	
	if (Seccion.existeReg(conEnoc)){
		Seccion.mapeaRegId(conEnoc, porId, seccionId);
		SeccionEmp.mapeaRegId(conEnoc, porId, seccionId, codigoPersonal, folio);
	}
%>
<div class="container-fluid">
	<h2>
		<spring:message code="aca.Empleado"/>
		<small  class="text-muted fs-5">( <%=codigoPersonal%> - <%=aca.vista.MaestrosUtil.getNombreCorto(conEnoc, codigoPersonal, "NOMBRE")%>)</small>
	</h2>	
	<div class="alert alert-info">
		<a href="datos?codigoPersonal=<%=codigoPersonal%>&porId=<%=porId%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i> Regresar</a>
	</div>
	<h3><%=Seccion.getTitulo()%> <%=Seccion.getSeccionNombre()%></h3> <br>
	<label for=""><strong><spring:message code='aca.Descripcion'/>:</strong></label>
	<div class="row" >
  		<div class="span5">
  			<% if(seccionTipo.equals("L")){ out.print(SeccionEmp.getTextoLargo());}else{out.print(SeccionEmp.getTextoCorto());}%>
  		</div>  		
	</div>	
</div>
<%@ include file="../../cierra_enoc.jsp" %>