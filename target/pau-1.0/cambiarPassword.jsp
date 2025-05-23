<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Reset password</title>
	<link rel="SHORTCUT ICON" href="imagenes/icoAc.png">
	<link href="loginStyle.css" rel="STYLESHEET" type="text/css">		
	<style>
		.zoom-image-hover{
		  max-width:400px;
		  margin: auto;
		}
		
		.zoom-image-hover img{
		  width:100%;
		  box-shadow: 0 0 8px #000;
		  transition:all 1s;
		  transform: scale(0.9);
		}
		
		.zoom-image-hover a:hover img{
		  box-shadow: 0 0 40px #000;
		  transform: scale(2.5);
		}
	</style>
</head>
<body>
<%
	String cambioClave 		= (String) request.getAttribute("cambioClave"); 
	String accion 			= (String) request.getAttribute("accion");
	boolean guardao 		= (boolean) request.getAttribute("guardado");
%>
<div class="fondo">
  	<table style="widht:100%; height:100%; margin:0 auto">
  	<tr>
	   	<td align="center">
	   		<table>
			<tr> 
	   			<td>
<% 			if(accion.equals("1")){ %>
					<form name="forma" action="cambiarPassword?accion=2" method="post">
					<input type="hidden" name="cambioClave" value="1">
 						<table style="margin:0 auto" class="cuadroLogin">
         				<tr> 
           					<th colspan='2' align="center"><font size="2" face="Verdana, Arial, Helvetica, sans-serif">For security reasons you're required to <br>update your password!</font></th>
       					</tr>
       					<tr>
         					<td><br>
         						<font size="2" face="Verdana, Arial, Helvetica, sans-serif">Current password:</font>
           						<input class="form-control" type="password" name="contraant" autofocus="autofocus">
           					</td>
       					</tr>
       					<tr> 
         					<td><br>
         						<font size="2" face="Verdana, Arial, Helvetica, sans-serif">New password:</font>
         						<input type="password" name="contraact"><strong><small>*Do not use the plus sign (+)</small></strong>
         					</td>
       					</tr>
       					<tr> 
         					<td><br>
         						<font size="2" face="Verdana, Arial, Helvetica, sans-serif">Confirm password:</font>
         						<input type="password" name="contraact2">
         					</td>
       					</tr>
       					<tr> 
         					<th colspan="2" style="text-align:right;"><br>
         						<input type="submit" value="Save" class="btn btn-primary">
         						<input type="button" value="Cancel" class="btn btn-primary" onclick="document.location.href='salir';">
         					</th>
       					</tr>
         				</table>
					</form>
<%			}else if(Integer.parseInt(accion)>2){%>
	           			<table style="width:75%; margin:0 auto;">
	           			<tr>
	            			<td align='center'>
<%				if(accion.equals("3")){%>
           						<font size="2" face="Verdana, Arial, Helvetica, sans-serif" color="blue">
           							<b>Your password was updated successfully!</b><br>            			
           							<meta http-equiv='REFRESH' content='1;URL=aca'>
           						</font>	
<%				}	
        		if(accion.equals("4")){%>
			            		<font size="2" face="Verdana, Arial, Helvetica, sans-serif" color="red">
			            			<b>Passwords don't match.</b>
			            		</font>
			            		<meta http-equiv='REFRESH' content='1;URL=cambiarPassword?accion=1'>            		
<%				}
	       	 	if(accion.equals("5")){%>
			            		<font size="2" face="Verdana, Arial, Helvetica, sans-serif" color="red">
			            			<b>Wrong password</b>
			            		</font>
			            		<meta http-equiv='REFRESH' content='1;URL=cambiarPassword?accion=1'>
<%				}
	       	 	if(accion.equals("6")){%>
			            		<font size="2" face="Verdana, Arial, Helvetica, sans-serif" color="red">
			            			<b>Not allowed. Try another password.</b>
			            		</font>
			            		<meta http-equiv='REFRESH' content='1;URL=cambiarPassword?accion=1'>
<%				}
           		if(accion.equals("7")){%>
								<font size="2" face="Verdana, Arial, Helvetica, sans-serif" color="red">
			            			<b>Your new password is the same as the current password.</b>
			            		</font>
			            		<meta http-equiv='REFRESH' content='2;URL=cambiarPassword?accion=1'>
<%				}%>
            				<td>
            			</tr>
            			</table>
<%			}%>
				</td>
	        </tr>
			</table>
		</td>
	</tr>
	</table>
</div>
</html>