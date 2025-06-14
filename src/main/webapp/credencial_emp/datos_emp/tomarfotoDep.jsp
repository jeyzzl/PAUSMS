<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="empleado" scope="page" class="aca.emp.Empleado"/>
<%
	String matricula 	= (String) session.getAttribute("codigoEmpleado");
	String empleadoId 		= (String) session.getAttribute("empleadoId");
	String dependienteId 	= (String) session.getAttribute("dependienteId");
	String nombreDep 		= aca.emp.Dependiente.getNombre(conEnoc, empleadoId, dependienteId);
	
	System.out.println("DEP : "+dependienteId+" NOMBRE : "+nombreDep+" EMP : "+matricula);
	
	String resolucion	= request.getParameter("Resolucion")==null?"2":request.getParameter("Resolucion");
	
	session.setAttribute("resolucion", resolucion);
	
	String resEmpleado	= resolucion;
	
	empleado.mapeaRegClave(conEnoc,matricula);
	
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<link href="../../academico.css" rel="STYLESHEET" type="text/css">	
	<script type="text/javascript" src="../../js/webcam.js"></script>
	<script type="text/javascript">
        webcam.set_api_url( 'uploadDep' );
        webcam.set_quality( 100 ); // JPEG quality (1 - 100)
        webcam.set_shutter_sound( true ); // play shutter click sound
    	webcam.set_hook( 'onComplete', 'my_completion_handler' );
    	
    	function take_snapshot() {
			// take snapshot and upload to server
			//if(confirm("¿ Deseas cambiar la foto del alumno ?")){
				document.getElementById('resultado').innerHTML = '<h1>Subiendo...</h1><br><img height=70 src="../../imagenes/cargando2.gif" />';				
				webcam.snap();				
			//}	
		}
	
		function my_completion_handler(msg) {
			// show JPEG image in page
			document.getElementById('resultado').innerHTML = '<h1>Fotografía Grabada!</h1>'+
			'<br><img height=340 src=foto?Codigo=<%=matricula%>&Tipo=<%=dependienteId%>&hola='+encodeURIComponent(new Date())+'>';
			
			// reset camera for another shot
			webcam.reset();
		}
	</script>
</head>
<body>
<div class="container-fluid">
<div class="alert">
	<form name="frmFoto" action="tomarfoto" method="post">
	<b>Resoluci&oacute;n:</b>&nbsp;
	<select name="Resolucion" onchange="javascript:document.frmFoto.submit();">		
		<option value="1" <%=resolucion.equals("1")?"Selected":""%>>320*480</option>
		<option value="2" <%=resolucion.equals("2")?"Selected":""%>>640*480</option>
	</select>
	</form>
</div>
<form>
<table width="77%" cellspacing="0" cellpadding="0" align="center" class="tabla">
  <tr>
    <th style="font-size:12pt;" align="center" colspan="2">
      <b>Dependiente: [<%=matricula+"-"+dependienteId%>] &nbsp; <%=nombreDep %></b>
    </th>
  </tr>    
  <tr>
    <td valign="top" width="50%">
      <table width="100%" border="0">        
        <tr valign="top">
          <td align="center">
            <div id="marco" align="center" style="width:<%=resolucion.equals("1")?"400px;":"680px;"%>; height:520px; margin-top:1em;-moz-border-radius:12px; background-color:#ddd;text-align:center;padding-top:20px;margin-bottom:1em;">
          	  <div align="center" id="cuadro" style="position:relative; left:20; width:<%=resolucion.equals("1")?"360px":"640px"%>; height:500px; overflow:hidden;">
	      		<div id="webcam" style="position:relative; left: <%=resolucion.equals("1")?"-140px":"0px;"%>">
		  		  <script language="JavaScript">
	        		document.write( webcam.get_html(640, 480) );
		  		  </script>
		  		</div>
	  		  </div>
	  		</div>
	  	  </td>	
	    </tr>
	    <tr>
    	  <td align="center">      
      		<input type=button value="Configurar" onClick="webcam.configure()">&nbsp;&nbsp;
	  		<input type=button value="Tomar Foto" onClick="take_snapshot()">
	  	  </td>
	  	</tr>  	    	    
	  </table>		
    </td>
	<td valign="top" width="50%" align="center">
      <table style="width:100%; margin:0 auto">   	
        <tr>
          <td align="center">
      		<div id="resultado" style="margin-top:1em;-moz-border-radius:12px; background-color:#ddd;width:360px;text-align:center;height:500px;padding-top:15px;margin-bottom:1em;">
      		  <h1>¡Fotografía Actual!</h1><br>
      		  <img height="340" src="../../foto?Codigo=<%=matricula+"&Tipo="+dependienteId%>">
      		</div>
      	  </td>
      	</tr>
      	<tr>
    	  <td align="center"><a class="btn btn-primary" href="dato_dep.jsp" style="font-size:11pt;"> Regresar</a></td>
	  	</tr>
     </table> 	  	
    </td>
  </tr>    
</table>
</form>
</div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>