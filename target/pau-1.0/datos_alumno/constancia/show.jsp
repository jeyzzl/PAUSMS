<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>

<%	
	String s_comentario			= request.getParameter("f_comentario");	
	String s_destinatario		= request.getParameter("f_destinatario");
	String s_dia				= request.getParameter("f_dia");
	String s_mes				= request.getParameter("f_mes");
	String s_year				= request.getParameter("f_year");
	String firma	 			= request.getParameter("firma")==null?"sin":request.getParameter("firma");
%>

<br>
<table cellpadding="2" cellspacing="2"  width="95%" height="95%" align="center">
  <tr>
		<th>Impresión de Constacias [ <a href="javascript:history.back()">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a> ]</th>
	</tr>
	<tr>
		<td>
			Dele <b>click</b> sobre el <b>logo</b> de la universidad para imprimir la constancia.
		</td>
	</tr>
	<tr>
		<td height="95%">
			<iframe width="100%" style="height: 100%;" src="view?f_comentario=<%= s_comentario%>&f_destinatario=<%=s_destinatario%>&f_dia=<%=s_dia%>&f_mes=<%=s_mes%>&f_year=<%=s_year%>&firma=<%=firma %>">
			</iframe>
		</td>
	</tr>
</table>
<!-- fin de estructura -->
