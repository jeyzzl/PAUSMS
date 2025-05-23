<%@ page isErrorPage="true" %>

<html>
<head>
	<link href="../../bootstrap/css/bootstrap.min.css" rel="STYLESHEET" type="text/css">
  	<title>Error</title>
</head>
<%
	String admin = (String)session.getAttribute("usuario");
%>
<body style="background:url(../../imagenes/bg.png)" bgcolor="white" oncontextmenu="return false" ondragstart="return false">
	<br>
	<br>
	<br>
	<br>
	<br>		
	<form name="Error" method="post" action="../../academico/login.jsp" target="_top">
	<input type="hidden" id="ruta" name="ruta" value=""/>
	<table style="margin:0 auto">
	<tr>
		<td>
			<img src="../../imagenes/errorpage.png"></img>
		</td>
		<td width="20">&nbsp;</td>
		<td>
		<% 	if(session.getId()==null || admin==null || exception==null){%>
		<h1>
			La sesión ha expirado.<br>
			<a href="#" onclick="javascript:document.Error.submit();">Iniciar sesión</a>
		</h1>	
		<script>
			var text = document.referrer;
		  	while(text.toString().indexOf("&")!=-1)text=text.toString().replace("&","-->");
			var r = text.split('/');
			var rut = "";
			for(var i=4;i<r.length;i++)rut+=i+1==r.length?r[i]:r[i]+'/';
				document.Error.ruta.value = rut;
		</script>
		<%	}else{%>
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
	</form>
	</body>
</html>