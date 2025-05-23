<%@page import="aca.por.PorRequisito"%>
<%@page import="java.util.HashMap"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="PorCategoriaU" scope="page" class="aca.por.PorCategoriaUtil"/>
<jsp:useBean id="PorSeccionU" scope="page" class="aca.por.PorSeccionUtil"/>
<jsp:useBean id="PorRequisitoEmp" scope="page" class="aca.por.PorRequisitoEmp"/>
<jsp:useBean id="PorRequisitoU" scope="page" class="aca.por.PorRequisitoUtil"/>
<jsp:useBean id="PorRequisitoEmpU" scope="page" class="aca.por.PorRequisitoEmpUtil"/>

<%	
	String porId 			= request.getParameter("porId")==null?"0":request.getParameter("porId");
	String codigoPersonal	= request.getParameter("codigoPersonal");
	
 	ArrayList <aca.por.PorRequisito> listReq = PorRequisitoU.getListReq(conEnoc, codigoPersonal);
 	
%>
<div class="container-fluid">
	<h1>Requisitos Cumplidos <small class="text-muted fs-4">(<%=aca.vista.MaestrosUtil.getNombreCorto(conEnoc, codigoPersonal, "NOMBRE")%> <%=codigoPersonal%> - <%= aca.por.PorDocumento.getNombre(conEnoc, porId) %> )</small></h1>
	
	<div class="alert alert-info">
		<a href="listaEmp?porId=<%=porId%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i> Regresar</a>
	</div>
	
	<table class="table table-condensed">
		<tr>
			<th align="left" width="3%">N°</th>
			<th align="left" width="16%">Categor&iacute;a</th>
			<th align="left" colspan="2" width="21%">Secci&oacute;n</th>
			<th align="left" width="60%">Requisito</th>
		</tr>

<% 
  	int row = 0;	
	for (aca.por.PorRequisito requisito : listReq) {
  		row++;
  		String categoriaNombre = PorCategoriaU.getCategoriaNombre(conEnoc, requisito.getRequisitoId());
  		
  		
%>
		<tr>
 			<td align="left" width="3%"><%=row%></td>
 			<td align="left" width="16%"><%=categoriaNombre%></td>
 			<td align="left" width="2%"><%=aca.por.PorSeccion.getTitulo(conEnoc, porId, requisito.getSeccionId())%></td>
 			<td align="left" width="19%"><%=aca.por.PorSeccion.getNombre(conEnoc, porId, requisito.getSeccionId())%></td>
 			<td align="left" width="60%"><%=requisito.getDescripcion()%></td>
		</tr>
<% 
	} 
%>
	</table>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>