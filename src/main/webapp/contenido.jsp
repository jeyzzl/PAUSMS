<html>
<%@ page import= "aca.acceso.*"%>
<%@ page import= "aca.util.*"%>
<%@ page import="aca.archivos.ArchivoVO"%>
<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/fontawesome.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/solid.min.css">			
	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap5.1/css/bootstrap.min.css">
	<script src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/bootstrap5.1/js/bootstrap.min.js"></script>
	
	<style type="text/css">
		body {
 			scroll: no; 
			margin-left: 0px;
			margin-top: 0px;
			margin-right: 0px;
			margin-bottom: 0px;
		}
		a{ 
			text-decoration: none;
			color:#00036e;
		}	
	</style>
</head>
<body>
<%
	String accion 			= (String) request.getAttribute("accion");
	boolean guardao 		= (boolean) request.getAttribute("guardado");	
%>
<div class="container-fluid">
	<h2>Change Password</h2>
	<div class="alert alert-info">
  		<a href="configuracion" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
  	</div>
<% 	if(accion.equals("3")){%>
	<div class="alert alert-success">Your password has been successfully changed!</div>
<% 	}%>
	<form name="forma" action="contenido?accion=2" method="post">
	<input type="hidden" name="cambioClave" value="1">
	<table class="table table-sm table-bordered" style="width:50%;">	
	<tr> 
		<td><font size="2" face="Verdana, Arial, Helvetica, sans-serif">Current Password:</font></td>
		<td>
			<input type="password" name="contraant">
<%	if(accion.equals("5")){%>
 			<strong style="color: red;">*Incorrect Password.</strong>
<%	}%>
 		</td>
	</tr>
    <tr> 
    	<td><font size="2" face="Verdana, Arial, Helvetica, sans-serif">New Password:</font></td>
    	<td>
    		<input type="password" name="contraact">
<%	if(accion.equals("6")){%>
    		<strong style="color: red;">*This password is not allowed.</strong>
<%	}%>
<%	if(accion.equals("7")){%>
    		<strong style="color: red;">*This password is the same as the previous, please choose a new password.</strong>
<%	}%>
<%	if(accion.equals("8")){%>
    		<strong style="color: red;">*Please do not use the plus symbol (+)</strong>
<%	}%>
    	</td>
    </tr>
    <tr> 
    	<td><font size="2" face="Verdana, Arial, Helvetica, sans-serif">Repeat Password:</font></td>
    	<td>
    		<input type="password" name="contraact2">
<%	if(accion.equals("4")){%>
        	<strong style="color: red;">*Your passwords do not match..</strong>
<%	}%>
    	</td>
    </tr>
    <tr> 
    	<th colspan="2"><input type="submit" value="Save" class="btn btn-primary"></th>
    </tr>
    </table>
	</form>				
</body>
</html>