<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<form name="documento" method="post" action="hojas">
  <table style="width:54%"  align="center">
    <tr align="center"> 
      <td colspan="4">&nbsp;</td>
    </tr>
    <tr align="center"> 
      <td colspan="4"><strong></strong></td>
    </tr>
    <tr align="center"> 
      <td colspan="4"><strong><font size="3">Rangos de impresi&oacute;n para la</font></strong></td>
    </tr>
    <tr> 
      <td colspan="4"><div align="center"><strong><font size="3">Hoja de Ruta 
          </font></strong></div></td>
    </tr>
    <tr> 
      <td colspan="2">&nbsp;</td>
    </tr>
    <tr> 
      <td colspan="2">&nbsp;</td>
    </tr>
    <tr> 
      <td colspan="2"><div align="center"><font size="2"><strong>Rangos</strong></font></div></td>
    </tr>
    <tr align="center"> 
      <th>De la Matr&iacute;cula</th>
      <th>A la Matr&iacute;cula</th>
    </tr>
    <tr align="center"> 
      <th width="50%" height="24"> <input name="inicial" type="text" class="text" id="inicial" size="30" maxlength="7"></th>
      <th width="50%"> <input name="final" type="text" class="text" id="final" size="30" maxlength="7"></th>
    </tr>
    <tr> 
      <td colspan="4" align="center">&nbsp;</td>
    </tr>
    <tr> 
      <td colspan="4" align="center"><input name="Aceptar" type="submit" id="Aceptar" value="Aceptar"></td>
    </tr>
  </table>
</form>