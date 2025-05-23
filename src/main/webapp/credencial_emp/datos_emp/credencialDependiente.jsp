<%
	String tipo		= (String)session.getAttribute("matricula");
	String clase	= "";
		
	if (tipo.substring(2,3).equals("1")){
		clase = "HLC";
	}else if(tipo.substring(2,3).equals("8") ){
		clase = "Jubilado";
	}else{ 
		clase = "UM";
	}
%>
<title>Credencial</title>
<body bgcolor='#999999' leftmargin=0 topmargin=0>
<table>
	<tr>
	<td >
		<applet name='credencialDep' code="credencial/CredencialDependiente<%=clase%>.class" width="400" height="280" alt="Se necesita Java para mostrar la credencial.">
			<param name= "matricula" value="<%=request.getParameter("matricula")%>">	
			<param name= "nombre" value="<%=request.getParameter("nombre")%>">	
			<param name= "fnac" value="<%=request.getParameter("fnac")%>">	
			<param name= "nomina" value="<%=session.getAttribute("matricula")%>">	
			<param name= "empleado" value="<%=session.getAttribute("nombre")%> <%=session.getAttribute("apellidos")%>">
			<param name= "depto" value="<%=session.getAttribute("depto")%>">	
		</applet>
	</td>
	</tr>
</table>