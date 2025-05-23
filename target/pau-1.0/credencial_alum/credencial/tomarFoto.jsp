<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CredPersonal" scope="page" class="aca.cred.CredPersonal"/>
<jsp:useBean id="CredPersonalU" scope="page" class="aca.cred.CredPersonalUtil"/>

<%
	//String matricula 	= (String) session.getAttribute("mat");
	String matricula	= (String) session.getAttribute("codigoAlumno");
	String nombre		= (String) request.getParameter("Nombre");
	String eventoId		= (String) request.getParameter("EventoId");
	CredPersonal.mapeaRegCodigoPersonal(conEnoc, matricula, eventoId);	
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<script type="text/javascript" src="../../js/webcam.js"></script>
	<script type="text/javascript">
        webcam.set_api_url( 'upload.jsp' );
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
			'<br><img height=340 src=../../foto?Codigo=<%=matricula%>&Tipo=O&hola='+encodeURIComponent(new Date())+'>';
			
			// reset camera for another shot
			webcam.reset();
		}
	</script>
</head>
<body>
<form>
<table style="width:77%"   align="center" bordercolor="#CCCCCC" border="1">
  <tr>
    <th style="font-size:12pt;" align="center" colspan="2">
      <b>Alumno: [<%=matricula %>] &nbsp; <%=CredPersonalU.getNombre(conEnoc, matricula) %></b>
    </th>
  </tr>    
  <tr>
    <td valign="top" width="50%">
      <table style="width:100%" >        
        <tr valign="top">
          <td align="center">
            <div id="marco" align="center" style="width:400px; height:500px; margin-top:1em;-moz-border-radius:12px; background-color:#ddd;text-align:center;padding-top:20px;margin-bottom:1em;">
          	  <div align="center" id="cuadro" style="position:relative; left:20; width:360px; height:480px; overflow:hidden;">
	      		<div id="webcam" style="position:relative; left: -140px;">
		  		  <script type="text/javascript">
	        		document.write( webcam.get_html(640, 480) );
		  		  </script>
		  		</div>
	  		  </div>
	  		</div>
	  	  </td>	
	    </tr>
	    <tr>
    	  <td align="center">      
      		<input type=button value="Configurar" onClick="webcam.configure()" class="btn btn-warning">&nbsp;&nbsp;
	  		<input type=button value="Tomar Foto" onClick="take_snapshot()" class="btn btn-success">
	  	  </td>
	  	</tr>  	    	    
	  </table>		
    </td>
    <td valign="top" width="50%" align="center">
      <table style="width:100%; margin:0 auto">   	
        <tr>
          <td align="center">
      		<div id="resultado" style="margin-top:1em;-moz-border-radius:12px; background-color:#ddd;width:360px;text-align:center;height:500px;padding-top:15px;margin-bottom:1em;">
      		  <h1>Fotografía Actual</h1><br>
      		  <img height="340" src="foto?Codigo=<%=matricula%>&Tipo=O">
      		</div>
      	  </td>
      	</tr>
      	<tr>
    	  <td align="center"><a href="generaCredencial?Matricula=<%=matricula%>&Nombre=<%=nombre%>&EventoId=<%=eventoId%>" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i> Regresar a los datos</a></td>
	  	</tr>
     </table> 	  	
    </td>
  </tr>    
</table>
</form>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>