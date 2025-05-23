<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
 	String sCursoCargaId		= request.getParameter("cursoCargaId");
	String imp					= request.getParameter("imp")==null?"":request.getParameter("imp");
	
	if (imp.equals("1")) imp="&imp=1"; 
%>

<div class="container-fluid">
	<h2><spring:message code="portal.maestro.cacTames.ImprimirActa"/></h2>
	<div class="alert alert-secondary">
		<a href="cursos" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>&nbsp; &nbsp; &nbsp;
		<spring:message code="portal.maestro.cacTames.MensajeUno"/>
	</div>	
	<table width="100%" height="100%">
	<tr>
		<td>
			<iframe width="100%" height="800" src="../../portales/maestro/actames?cursoCargaId=<%=sCursoCargaId+imp%>"></iframe>
		</td>
	</tr>
	</table>
</div>