<%@ include file="../../idioma.jsp"%>

<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="aca.conecta.*" %>

<jsp:useBean id="sistema" scope="page" class="aca.vmeeting.Sistema"/>
<%
	Connection  conVM	 	= null;
	Conectar c 				= new Conectar();
	conVM	 				= c.conPostgresVMeeting();
%>
<html>
<head>
<link href="../main.css" rel="STYLESHEET" type="text/css">
</head>
<body>
<%
	String mensaje = "";
	int accion = request.getParameter("accion")!=null?Integer.parseInt(request.getParameter("accion")):0;
	switch(accion){
		case 2:
			String nombre = request.getParameter("nombre");
			boolean local = Boolean.parseBoolean(request.getParameter("local"));
			
			sistema.mapeaRegId(conVM, Integer.parseInt(request.getParameter("id")));
			sistema.setNombre(nombre);
			sistema.setIsLocal(local);
			if(sistema.updateReg(conVM)){
				out.print("<div class='alert alert-success'>Grabado...<a class='btn btn-primary' href='lista.jsp'>Regresar</a></div>");
				//response.sendRedirect("lista.jsp");
			}else{
				mensaje = "Ocurri&oacute; un error al guardar.<br />Por favor int&eacute;ntelo nuevamente.";
			}
			break;
		case 4:
			sistema.mapeaRegId(conVM, Integer.parseInt(request.getParameter("id")));
			break;
	}

	if(!mensaje.equals("")){
%>
<p class="mensaje"><%=mensaje %></p>
<%
	}
%>
<table style="width:100%; margin:0 auto">
	<tr>
		<td align="center"><h2>Editar Sistema</h2></td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td align="center">
			<form name="forma" action="?accion=2" method="POST">
				<input type="hidden" name="id" value="<%=sistema.getId() %>" />
				<table>
					<tr>
						<td><spring:message code="aca.Nombre"/></td>
						<td><input type="text" name="nombre" maxlength="95" value="<%=sistema.getNombre() %>" /></td>
					</tr>
					<tr>
						<td>Es un sistema interno?</td>
						<td><input type="checkbox" name="local" value="true" <%=sistema.isLocal()?"checked=yes ":"" %>/></td>
					</tr>
					<tr><td colspan="2" align="center"><input type="submit" value="Grabar" /></td></tr>
				</table>
			</form>
		</td>
	</tr>
</table>
</body>
</html>
<%
	c.desPostgres(conVM);
	conVM = null;
%>