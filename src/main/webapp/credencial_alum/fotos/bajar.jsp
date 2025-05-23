<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<%
	String sPaso = request.getParameter("paso");
	String sContador = request.getParameter("cont");
	if (sPaso == null) sPaso= "1";
	if (sContador == null) sContador= "0";
%>
<style type="text/css">
<!--
.style3 {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-weight: bold;
	font-size: 18px;
}
.style4 {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-weight: bold;
	font-size: 12px;
}
.style5 {
	color: #FF0000;
	font-weight: bold;
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 14px;
}
.style10 {font-size: 12px}
.style11 {
	font-size: 16px;
	font-weight: bold;
}
.style13 {font-family: Verdana, Arial, Helvetica, sans-serif}
.style15 {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-weight: bold;
	color: #8276FE;
	font-size: 10px;
}
.style17 {color: #000066}
.style20 {color: #0000FF}
.style21 {color: #FF0000}
.style23 {font-size: 14px; color: #FF0000; font-family: Verdana, Arial, Helvetica, sans-serif;}
.style24 {
	font-size: 14px;
	font-weight: bold;
}
.style25 {font-size: 14px}
.style26 {
	color: #990000;
	font-weight: bold;
	font-size: 12px;
}
-->
</style>
<table style="width:100%">
  <tr>
    <td><div align="center"><span class="style3"></span></div></td>
  </tr>
</table>

<%	if (sPaso.equals("1")){ %>
<script type="text/javascript">
	function bajar(){
  		if (document.forma.direc.value=="")
			alert ("Ponga el directorio.");
		else
			openwindow("transferirapc.jsp?dir="+document.forma.direc.value);
	}
	function openwindow(open){
		window.open(open,"Popup","toolbar=0, location=0, directories=0, status=0, menubar=0, scrollbars=1, resizable=1, width=400, height=300, top=0, left=0")
	}
</script>
<div class="container-fluid">
	<h2>Bajar fotos de los alumnos</h2>
	<div class="alert alert-info"></div>
	<table style="width:40%" class="tabla"><tr><td>
	<form action="javascript:bajar()" name="forma" method="post">
        <table style="width:100%">
          <tr> 
            <td><b>1.</b> Ponga el directorio donde se guardaran las fotos:</span></td>
          </tr>
          <tr>
            <td>
            	<br><input size='50' type='text' class="form-control" name='direc' value='C:\Credenciales\Fotos'></br>
            </td>
          </tr>
          <tr> 
            <td><b>2.</b> <input class="btn btn-primary" onclick='javascript:bajar()' type="button" name="bRespaldar2" value="Iniciar transferencia">
              </td>
          </tr>
        </table>
  <p>
  </p>
</form>
</td></tr></table>	
  <p>
  </p>
</form>
</td></tr></table>	
<%}else if (sPaso.equals("2")){%>

<table>
   <tr><td class="style11"><div align="center" class="style20">
     <p class="style3">Se transfirieron todas las fotos con éxito<br>
     </div></td>
   </tr>
   <tr>
     <td class="style10">
          <div align="center">       </div></td>
   </tr>
</table>
</table>
<head>
<meta http-equiv='REFRESH' content='4; url=subir'>
</head>
<%}else if (sPaso.equals("3")){%>

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
</table>
<head>
<meta http-equiv='REFRESH' content='8; url=subir'>
</head>
</div>
<%}%>
