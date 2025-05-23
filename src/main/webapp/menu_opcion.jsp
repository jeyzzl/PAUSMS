<%@ page import= "aca.menu.*"%>

<html>
<head><title>Gestión educativa</title>
	<link href="academico.css" rel="STYLESHEET" type="text/css">
</head>
<body  background="imagenes/back.gif" bgcolor="#FFFFFF" leftmargin="1" topmargin="0">

<% 
	String sColor 		= "bgcolor = '#dddddd'";
	java.util.ArrayList lisOpcion	= (java.util.ArrayList)session.getAttribute("lisOpcion");	
	String sModulo		= request.getParameter("moduloId");
	String sCarpeta		= request.getParameter("carpeta");
%>
<table align="left">
<%
	for (int i=0; i<lisOpcion.size(); i++){
		Opcion opcion = (Opcion) lisOpcion.get(i);
		if (opcion.getModuloId().equals(sModulo)){
%>
	<tr align="left">
		<td>
			<li><a href="<%=sCarpeta%><%=opcion.getUrl()%>?moduloId=<%=sModulo%>&carpeta=<%=sCarpeta%><%=opcion.getIcono()%>"><%=opcion.getNombreOpcion()%></a></li>
		</td>
	</tr>
		
<%		}
	}
	if (!sModulo.equals("A15")){		
%>

	<tr align="left">
	  <td>
		<li> Buscar:&nbsp;
		  <a href="parametros/alumno/buscar?Origen=menu_opcion?moduloId=<%=sModulo%>&carpeta=<%=sCarpeta%>&Opcion=Alumno">
		    <font color="#006600"><spring:message code="aca.Alumno"/></font>
		  </a>
		  &nbsp;/&nbsp;
		  <a href="parametros/alumno/buscar?Origen=menu_opcion?moduloId=<%=sModulo%>&carpeta=<%=sCarpeta%>&Opcion=Empleado">
		    <font color="#006600">Empleado</font>
		  </a>
		</li>
		</td>
	</tr>
<%}%>
</table>