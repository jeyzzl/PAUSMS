<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
 	String cursoCargaId			= request.getParameter("cursoCargaId")==null?"0":request.getParameter("cursoCargaId");
	String imp					= request.getParameter("imp")==null?"":request.getParameter("imp");	
	String str 					= "";
	if (imp.equals("1")) str="&imp=1";
%>
<div class="container-fluid">
	<h2><spring:message code="portal.maestro.cacta.ImprimirActa"/></h2>
	<div class="alert alert-secondary">
		<a href="cursos" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>&nbsp; &nbsp; &nbsp;
		<spring:message code="portal.maestro.cacta.MensajeUno"/>
	</div>
	<table style="width:100%" height="96%">	
	<tr>
		<td>
			<iframe width="100%" height="800" src="acta?cursoCargaId=<%=cursoCargaId+str%>"></iframe>
		</td>
	</tr>
	</table>
</div>
