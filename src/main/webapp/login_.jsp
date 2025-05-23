<%@ page buffer= "none" %>
<%String institucion 		= (String)session.getAttribute("institucion");%>
<HTML><HEAD><TITLE>Virtual um</TITLE>
<SCRIPT>
     		self.resizeTo( window.screen.availWidth, window.screen.availHeight);
</SCRIPT>
<BODY bgColor=#000000 text=#ffffff leftMargin=0 topMargin=0>
<table  width="581"   align="left">
  <tr>
    <td valign="top" width="430">
	
        <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="430" height="410">
          <param name="movie" value="imagenes/virtual.swf">
          <param name="quality" value="high">
          <embed src="imagenes/virtual.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="430" height="410"></embed>
      </object>
	</td>
	<td width="151" valign="bottom">
      <form action="valida.jsp" method="POST"  name="datos" autocomplete=off>
        <span class=formularios><font color="#FF9900" 
      size=2 face="Arial, Helvetica, sans-serif"><strong><spring:message code='aca.Usuario'/></strong></font></span><br>
        <input maxlength=20 name=Usuario>
        <br>
        <span class=formularios><font 
      face="Arial, Helvetica, sans-serif" color=#FF9900 
      size=2><strong>Password</strong></font></span><br>
        <input type=password maxlength=20 name=Clave>
        <br>
        <br>
        <input type=submit value=Entrar name=Submit>
        <input onClick=window.close() type=reset value=Cerrar name=Submit2>
    </form>

	</td>
  </tr>
  <tr>
    <td valign=top width=430>
</td>
  </tr>
  <tr>
    <td class=copyright valign=top colspan="2" align="center"><font color="#FFFFFF" size=1 
      face="Arial, Helvetica, sans-serif">Intranet de la <%=institucion%> <br>
      Todos los derechos reservados<br>
      Mantenimiento:Dirección de Sistemas</font></td>
  </tr>
</table>
<script type="text/javascript" src="">
	window.focus();
	document.datos.Usuario.focus();
</script>

</BODY>
</HTML>