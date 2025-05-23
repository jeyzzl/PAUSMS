<%@ page isErrorPage="true" %>

<html>
	<head>
		<link href="bootstrap/css/bootstrap.min.css" rel="STYLESHEET" type="text/css">
	   <title>Error</title>
	</head>
<%
	String admin = (String)session.getAttribute("usuario");
%>
	<body style="background:url(imagenes/bg.png)" bgcolor="white" oncontextmenu="return false" ondragstart="return false">
		<br>
		<br>
		<br>
		<br>
		<br>
	
		<table style="margin:0 auto">
			<tr>
				<td>
					<img src="imagenes/errorpage.png"></img>
				</td>
				<td width="20">&nbsp;</td>
				<td>
				<% 	if(session.getId()==null || admin==null || exception==null){%>
						<h1>
							La sesión ha expirado.<br>
							<a href="../academico/login" target="_top">Iniciar sesión</a>
						</h1>
				<%	}
					else{%>
						<h1>
							Disculpe las molestias.<br>
							Ocurrió un error al cargar la página.
						</h1>
				<% 	}
					if(exception!=null && ((admin!=null) && admin.equals("etorres"))){%>
						<b>Excepción:</b><br> 
						<%= exception.toString() %>
				<% 		if(request.getServerName().contains("localhost")) exception.printStackTrace();
					}
					
					if (request.getAttribute("conEnoc")!=null){
						java.sql.Connection conEnoc = (java.sql.Connection)request.getAttribute("conEnoc");
						request.removeAttribute("conEnoc");	
						if (conEnoc!=null) conEnoc.close();
					}
				%>
				</td>
			</tr>
		</table>
	</body>
</html>