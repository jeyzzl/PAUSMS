<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<head>
<%
	String usuario = (String) session.getAttribute("codigoPersonal");
%>
</head>
<body>
<table class="goback"><tr><td><a class="btn btn-primary" href="subir.jsp">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td></tr></table>
<br>
<table  align="center" class="tabla">
  <tr>
	<td class="titulo">Upload files in batch</td> 
  </tr>
  <tr>
	<td>&nbsp;</td>
  </tr>
  <tr>
	<td align="center">
	  <applet name="appletGenerales" id="appletGenerales" code="digital/Generales.class" width="300" height="200" >
		<param name= "usuario" value="<%=usuario%>">
	  </applet>
	</td>
  </tr>  	
</table>
</body>