<%
	String tipo 	= (String) session.getAttribute("matricula");
	String clase	= "";
	
	if (tipo.substring(2,3).equals("1"))
		clase = "HLC";
	else
		clase = "UM";	 
			
%>
<title>Credencial</title>
<body bgcolor='#999999' leftmargin=0 topmargin=0>
<table>
  <tr>
    <td >
	  <applet name='credencial' code="credencial/CredencialEmpleado<%=clase%>.class" width="400" height="280" alt="Se necesita Java para mostrar la credencial.">
		<param name= "matricula" value="<%=session.getAttribute("matricula")%>">	
		<param name= "nombre" value="<%=session.getAttribute("nombre")%>">	
		<param name= "apellidos" value="<%=session.getAttribute("apellidos")%>">
		<param name= "puesto" value="<%=session.getAttribute("puesto")%>">	
		<param name= "depto" value="<%=session.getAttribute("depto")%>">
	  </applet>
	</td>
  </tr>
</table>