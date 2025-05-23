<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String s_comentario			= request.getParameter("f_comentario");	
	String s_destinatario		= request.getParameter("f_destinatario");
	String s_dia				= request.getParameter("f_dia");
	String s_mes				= request.getParameter("f_mes");
	String s_year				= request.getParameter("f_year");
	String firma	 			= request.getParameter("firma")==null?"sin":request.getParameter("firma");
%>
<div class="container-fluid">
	<h3>Impresión de Constancias <small class="text-muted fs-5">( <a href="form"><spring:message code="aca.Regresar"/></a> )</small></h3>
	<div class="alert alert-info">
		Dele <b>click</b> sobre el <b>logo</b> de la universidad para imprimir la constancia.
	</div>
	<iframe lang="" width="100%" style="height: 700px;" src="view?f_comentario=<%= s_comentario%>&f_destinatario=<%=s_destinatario%>&f_dia=<%=s_dia%>&f_mes=<%=s_mes%>&f_year=<%=s_year%>&firma=<%=firma %>">
	</iframe>
</div>