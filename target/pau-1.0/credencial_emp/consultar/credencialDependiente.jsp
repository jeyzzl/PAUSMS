<%
	String tipo			= (String)session.getAttribute("matricula");
	String clase		= "";
	String institucion 	= (String)session.getAttribute("institucion");
		
	if (tipo.substring(2,3).equals("1"))
		clase = "HLC";
	else if(tipo.substring(2,3).equals("8"))
		clase = "Jubilado";
	else 
		clase = "UM";
%>
<title>Credencial</title>
<body bgcolor='#999999' leftmargin=0 topmargin=0>
<table>
	<tr>
	<td >
		<applet name='credencial' code="credencial.CredencialDependiente<%=clase%>" width="400" height="280" alt="Se necesita Java para mostrar la credencial.">
			<param name= "matricula" value="<%=request.getParameter("matricula")%>">	
			<param name= "nombre" value="<%=request.getParameter("nombre")%>">	
			<param name= "fnac" value="<%=request.getParameter("fnac")%>">	
			<param name= "nomina" value="<%=session.getAttribute("matricula")%>">	
			<param name= "empleado" value="<%=session.getAttribute("nombre")%> <%=session.getAttribute("apellidos")%>">
			<param name= "depto" value="<%=session.getAttribute("depto")%>">
			<param name= "institucion" value="<%=session.getAttribute("institucion")%>">		
		</applet>
	</td>
	</tr>
</table>