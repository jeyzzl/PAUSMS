<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<%
	String s_paso = request.getParameter("paso");
	String s_contador = request.getParameter("cont");
	if (s_paso == null) s_paso= "1";
	if (s_contador == null) s_contador= "0";
	java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd/MM/yyyy");
%>
<%	if (s_paso.equals("1")){ %>
<script type="text/javascript">
  function CT(cadena)
  {   if (cadena =="")
      {
         alert ("Ponga el directorio.");
         return false;
	  }
  }
	function openwindow(open){
		window.open(open,"Popup","toolbar=0, location=0, directories=0, status=0, menubar=0, scrollbars=1, resizable=1, width=400, height=300, top=0, left=0")
	}
</script>
<div class="container-fluid">
<h1>Subir fotos de los empleados</h1>
<div class="alert alert-info"></div>
<table class="table table-condensed" width="90%"><tr><td><form action="javascript:openwindow('transferir.jsp')" name="form" method="post">
<table style="width:100%">
   <tr>
     <td><span class="style10"><span class="style11">1.</span> Ponga las fotos en el siguiente directorio:</span></td>
   </tr>
   <tr><td class="style10">C:\Credenciales\FotosEmpleados</td>
   </tr>
   <tr>
     <td><span class="style25"><h5>Nota:</h5>
         <span class="style26">El path no es opcional. Cree la carpeta y copie ahi las fotos que subir&aacute; al sistema. Las fotos existentes en el sistema, se reemplazaran por estas nuevas. </span></span></td>
   </tr>
   <tr>
     <td><span class="style10"><span class="style11">2</span>. De clic en el siguiente bot&oacute;n cuando tenga las fotos listas. </span></td>
   </tr>
   <tr>
     <td style="text-align:center;"><input class="btn btn-primary" type="submit" name="bRespaldar2" value="S�, tengo las fotos listas en la carpeta"></td>
   </tr>      
</table>
</form>
</td></tr></table>	
<%}else if (s_paso.equals("2")){%>
<table>
   <tr>
     <td align="center">
	 	<applet code="subirFotos.class" width="300" height="100" align="texttop">
       </applet><img src="pp.gif" width="100" height="10"></td>
   </tr>
</table>
<%}else if (s_paso.equals("3")){%>

<table>
   <tr><td class="style11"><div align="center" class="style20">
     <p class="style3">OK!. Se transfirieron todas las fotos con exito!!!<br>
     </div></td>
   </tr>
   </tr>
</table>
<meta http-equiv='REFRESH' content='4; url=subir'>
</head>
<%}else if (s_paso.equals("4")){%>

<table>
   <tr><td class="style11"><div align="center" class="style20">
     <p><span class="style3 style21">Ocurrio un error al transferir!!<br>
       </span></p>
     </div></td>
   </tr>
   <tr>
     <td class="style10">
          <div align="center"><span class="style23"><strong>Probablemente no ha creado el directorio. Favor de verificarlo</strong>. </span> </div></td>
   </tr>
</table>
<meta http-equiv='REFRESH' content='8; url=subir'>
</head>
<%}%>
</div>