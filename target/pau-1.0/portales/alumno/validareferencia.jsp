<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="AlumReferenciaU" scope="page" class="aca.alumno.AlumReferenciaUtil"/>

<%-- <jsp:include page="../menu.jsp" /> --%>
<%@ include file= "menu.jsp" %>

<html>
<head>
</head>
<%
	ArrayList<aca.alumno.AlumReferencia> lisRef = AlumReferenciaU.getListAll(conEnoc, " ORDER BY CODIGO_PERSONAL");
%>
<body>
	<table style="margin: 0 auto;">
		<tr>	
			<td align="left" colspan="5">Referencias Incorrectas</td>
		</tr>
		<tr>	
			<th align="left">#</th>
			<th align="left"><spring:message code="aca.Matricula"/></th>
			<th align="left">Scotiabank</th>
			<th align="left">Santander</th>
			<th align="left">Dígito</th>
		</tr>
<%
	int row = 0;
	for( aca.alumno.AlumReferencia referencia : lisRef){
		String digito = aca.alumno.AlumReferenciaUtil.generarReferenciaSantander(referencia.getCodigoPersonal());
		boolean errorScotiabank = false;
		boolean errorSantander	= false;
		if (!referencia.getScotiabank().equals(digito)) errorScotiabank = true;
		if (!referencia.getSantander().equals(digito)) errorSantander = true;
		if (errorScotiabank||errorSantander){
			row++;
%>			
		<tr>
			<td><%=row%></td>		
			<td><%=referencia.getCodigoPersonal()%></td>
			<td><%=referencia.getScotiabank()%></td>
			<td><%=referencia.getSantander()%></td>
			<td><%=digito%></td>
		</tr>
<%			
		}
	}
%>			
	</table>
</body>
<script>$('.nav-tabs').find('.edoCuenta').addClass('active');</script>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>