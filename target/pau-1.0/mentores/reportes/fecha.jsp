<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" type="text/css" media="all" href="jscalendar-1.0/calendar-blue2.css" title="win2k-cold-1" /> 
<script type="text/javascript" src="jscalendar-1.0/calendar.js"></script> 
<script type="text/javascript" src="jscalendar-1.0/lang/calendar-es.js"></script> 
<script type="text/javascript" src="jscalendar-1.0/calendar-setup.js"></script>
<%		
	String fecha = "";
%>
<table style="width:70%" align="center" id="noayuda">
<tr> 
  <td colspan="5" align="center">
  <font size="1">
	<div align="center"><strong>&iexcl; importante ! </strong><br></div>
    </font>
  <div align="justify"><font size="1">La fecha determina el periodo academico sobre el cual se realizará la estadistica de las entrevistas, por lo tanto es muy importante que la fecha que capture se encuentre dentro del rango de inicio y final de las cargas academicas que se deseen estudiar. Por ejemplo para alumnos inscritos de Enero - Mayo la fecha podria ser 30/04/YYYY. Siendo YYYY el a&ntilde;o correspondiente. </font></div>
	
  </td>
</tr>
</table>
<table class="tabla" width="50%" border="1" align="center"   >
<form name="form1" method="post" action="estadistica">
<tr> 
  <td align="center"><p><font size="3"><br>
    Capture la fecha: 
          <input name="f_fecha" type="text" class="text" id="f_fecha3" size="11" maxlength="10">          
          <input type="image" src="jscalendar-1.0/calendario.gif"  height="100%" id="lanzador" value="..." /></font> </p>
    <p>
      <input name="Aceptar" type="submit" id="Aceptar" value="Aceptar">
</p></td>
</tr>
</form>
</table>
<script type="text/javascript">
	Calendar.setup({
		inputField     :    "f_fecha3",     // id del campo de texto
	    ifFormat     :     "%d/%m/%Y",     // formato de la fecha que se escriba en el campo de texto
	    button     :    "lanzador"     // el id del botón que lanzará el calendario
	});
</script>