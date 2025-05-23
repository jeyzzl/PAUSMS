<%@ include file= "../con_enoc.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="colorAlum" scope="page" class="aca.portal.Alumno"/>

<html> 
<head> 
 <script type="text/javascript" src="jquery.js"></script> 
 <script type="text/javascript" src="farbtastic.js"></script> 
 <link rel="stylesheet" href="farbtastic.css" type="text/css" /> 
 <link rel="stylesheet" href="../academico.css" type="text/css" /> 
 <script type="text/javascript" charset="utf-8"> 
	  $(document).ready(function() {
	    $('#demo').hide();
	    $('#picker').farbtastic('#color');
	  });
 </script> 
 <script type="text/javascript" charset="utf-8">
	 function probar(){
	        var objeto= document.getElementById("prueba");
	        
	        document.forma.colorFinal.value = document.forma.color.value;
	        guardar("seleccionado");
	 }
	 
	 function def(){
	        var objeto= document.getElementById("prueba");
	        
	        document.forma.colorFinal.value = "#545454";
	        guardar("por default");
	 }
	 
	 function guardar(elegido){
		 if(document.forma.colorFinal.value!=""){
			 if(confirm("Estas seguro de guardar el color "+elegido)==true){
		  			document.forma.Accion.value="1";
					document.forma.submit();
			}
		 }else{
			 alert("No ha seleccionado ningun color");
		 }
	 }
 </script>
 <%
	String sCodigoPersonal	= (String)session.getAttribute("codigoPersonal");
	String sUsuario		= (String) session.getAttribute("usuario");
 	String accion 		= request.getParameter("Accion")==null?"":request.getParameter("Accion");
 	
 	if(accion.equals("1")){
 		colorAlum.guardaColorReloj(conEnoc, sCodigoPersonal, request.getParameter("colorFinal"));
 		session.setAttribute("colorReloj", request.getParameter("colorFinal"));	
 	}
	
 	String value 		= colorAlum.obtenColorReloj(conEnoc, sCodigoPersonal).equals("default")?"#545454":colorAlum.obtenColorReloj(conEnoc, sCodigoPersonal);
 	
	String color1 = colorAlum.modificarColor(value, 80);
	String color2 = colorAlum.modificarColor(value, -70);

 %>
 <style type="text/css">
 	#prueba{
		border: 5px solid <%=color2%>;
		border-radius:1em;
 		width: 128px;
 		height: 34px;
 		background: <%=value%>;
 		
		background: -webkit-gradient(linear, left top, left bottom, from(<%=color1%>), to(<%=value%>));
		background: -webkit-linear-gradient(<%=color1%>, <%=value%> 50%);
		background: -moz-linear-gradient(<%=color1%>, <%=value%> 50%);
		background: -o-linear-gradient(<%=color1%>, <%=value%> 50%);
 	}
 </style>
</head>

<body>
 <form action="" name="forma">
 <input type="hidden" name="Accion">
 <input type="hidden" name="colorFinal">
 	<br>
 	<table style="width:35%" align="center" class="tabla" >
 	  <tr>
 	  	<td width="10%" ></td> <td class="titulo" align="left">Cambiar Color del Reloj</td>
 	  </tr>
 	  <tr><td></td><td>&nbsp;</td></tr>
	  <tr>
	  	<td ></td><td align="center"><div class="form-item"><input type="text" id="color" name="color" value="<%=value %>" size="9" maxlength="7" style="text-align:center; color:black; border-width:0; font-size:13px; font-family:Verdana;"/></div></td>
	  </tr>
	  <tr>
	  	<td align="right"><font face="Verdana" size="6">1.</font></td><td align="center"><div id="picker"></div></td>
	  </tr>
	   <tr><td ></td><td>&nbsp;</td></tr>
	  	<tr> <td colspan="2" style="height: 2px; background-color: rgb(250, 209, 99);"></td></tr>
	  <tr><td ></td><td>&nbsp;</td></tr>
	  <tr>
	  	<td align="right"><font face="Verdana" size="6">2.</font></td>
	  	<td align="center">
		  	<input type="button" onclick="probar();" value="Guardar Color"/>&nbsp;&nbsp;
		  	<input type="button" onclick="def();" value="Color por Default"/></td>
	  	</td>
	  </tr>
	  <tr>
	  <td ></td>
	  	<td>
	  	  <br>
	  	  <table style="margin: 0 auto;">
	  	  		<tr>
	  	  			<td>Ejemplo:</td>
	  	  		</tr>
	  	  </table>
		  <table style="width:70%" align="center">
		  <tr><td align="center">
		  	<div id="prueba">
		  		<table>
		  			<tr><td align="center" valign="middle"><font size="5" color="white">00:00:00</font></td></tr>
		  		</table>
		  	</div>
		  	</td></tr>
		  </table>
		  <br>
		</td>
	  </tr>
	   <tr><td colspan="1"></td><td align="center"><font color="purple">Favor de actualizar/recargar la pagina o dar click en "Universidad de Montemorelos" para visualizar los cambios</font></td></tr>
	   <tr><td></td><td>&nbsp;</td></tr>
	   <tr><td colspan="2" align="left"><font size="1" color="red">&nbsp;<b>Nota:</b> Si los cambios no surgen efecto despues de actualizar<br>&nbsp;la pagina, favor de borrar el historial de su computadora.</font></td></tr>
	</table>
</form>  
</body> 
</html>
<%@ include file= "../cierra_enoc.jsp" %>