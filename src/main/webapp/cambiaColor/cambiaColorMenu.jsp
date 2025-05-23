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
	        
	        document.forma.colorFinal.value = "default";
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
	String color 		= request.getParameter("colorFinal")==null?"":request.getParameter("colorFinal");	
 	
 	if(accion.equals("1")){
 		aca.portal.Alumno alumn = new aca.portal.Alumno();
 		alumn.guardaColorMenu(conEnoc, sCodigoPersonal,color);
 		session.setAttribute("colorMenu", color);
 	}
 	
	String colorTablas = (String)session.getAttribute("colorMenu");
	String value 		= colorTablas;
	
	if(colorTablas.equals("default") || colorTablas.equals("")){		
		colorTablas = "#51308B";
		value = "#51308B";
	}
 %>
 <style type="text/css">
		th{
		<%	String tmpColor = colorTablas.substring(1);
			String colorM = colorAlum.modificarColor(colorTablas, 64);
	%>
			background: -webkit-gradient(linear, left top, left bottom, from(<%=colorM%>), to(<%=colorTablas%>));
			background: -webkit-linear-gradient(<%=colorM%>, <%=colorTablas%> 60%);
			background: -moz-linear-gradient(<%=colorM%>, <%=colorTablas%> 60%);
			background: -o-linear-gradient(<%=colorM%>, <%=colorTablas%> 60%);
			filter:  progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='<%=colorM%>', endColorstr='<%=colorTablas%>'); /* IE6 & IE7 */
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='<%=colorM%>', endColorstr='<%=colorTablas%>')"; /* IE8 */
			
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
 	  	<td width="10%" ></td> <td class="titulo" align="left">Cambiar Color del Menu</td>
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
		  <table style="width:70%" align="center" class="tabla">
		  	<tr>
		  		<th  id="prueba">Menu</th>
		  	</tr>
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