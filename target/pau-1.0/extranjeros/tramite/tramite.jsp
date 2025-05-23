<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="TramiteU" scope="page" class="aca.leg.LegTramiteUtil"/>
<script type="text/javascript">
	function Borrar( TramiteId ){
		if(confirm("Estas seguro de eliminar el registro: "+TramiteId)==true){
	  		document.location="accion?Accion=4&TramiteId="+TramiteId;
	  	}
	}
</script>
<%
	String tipo 	= "";
	ArrayList<aca.leg.LegTramite> lisTramite 	= TramiteU.getListAll(conEnoc,"ORDER BY TRAMITE_ID");
%>
<html>
<head><title><spring:message code='aca.DocumentoSinTitulo'/></title></head>

<table style="width:60%"  align="center">
  <tr align="center"> 
    <td colspan="3"><strong><font size="3">Listado de Tr&aacute;mite [</font> <a href="accion?Accion=1"><spring:message code='aca.Añadir'/></a> 
      <font size="3">]</font></strong> </td>
  </tr>
  <tr> 
    <th width="17%"><spring:message code="aca.Operacion"/></th>
    <th width="10%"><spring:message code="aca.Numero"/></th>
    <th width="60%"><spring:message code='aca.Descripcion'/></th>
    <th width="10%"><spring:message code="aca.Costo"/></th>
  </tr>

<%
	java.util.Iterator iter =  lisTramite.iterator();
	while (iter.hasNext()){
		aca.leg.LegTramite tramite = (aca.leg.LegTramite) iter.next();
%>
  <tr> 
    <td align="center"> <a href="accion?Accion=5&TramiteId=<%=tramite.getTramiteId()%>">
      <img title="Editar" src="../../imagenes/editar.gif" alt="Modificar" > 
      </a> <a href="javascript:Borrar('<%=tramite.getTramiteId()%>')"> <img src="../../imagenes/no.png" alt="Eliminar" > 
      </a> </td>
    <td align="center"><%=tramite.getTramiteId()%></td>
    <td><%=tramite.getTramiteNombre()%></td>
    <td align="left"><%=tramite.getCosto()%></td>
  </tr>
  <%
	}	
	lisTramite		= null;
%>  
</table>
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %> 