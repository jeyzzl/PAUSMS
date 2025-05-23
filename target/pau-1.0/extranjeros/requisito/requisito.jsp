<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="RequisitoU" scope="page" class="aca.leg.LegRequisitosUtil"/>


<script type="text/javascript">
	function Borrar( RequisitoId ){
		if(confirm("Estas seguro de eliminar el registro: "+RequisitoId)==true){
	  		document.location="accion?Accion=4&RequisitoId="+RequisitoId;
	  	}
	}
</script>

<%
 	ArrayList lisRequisito = RequisitoU.getListAll(conEnoc,"ORDER BY REQUISITO_ID");
%>

<html>
<head><title><spring:message code='aca.DocumentoSinTitulo'/></title></head>

<table style="width:60%"  align="center">
  <tr align="center"> 
    <td colspan="3"><strong><font size="3">Listado de Requisitos [</font> <a href="accion?Accion=1"><spring:message code='aca.Añadir'/></a> 
      <font size="3">]</font></strong> </td>
  </tr>
  <tr> 
    <th width="17%"><spring:message code="aca.Operacion"/></th>
    <th width="15%"><spring:message code="aca.Numero"/></th>
    <th width="68%"><spring:message code='aca.Descripcion'/></th>
  </tr>
 <%
	java.util.Iterator iter =  lisRequisito.iterator();
	while (iter.hasNext()){
	aca.leg.LegRequisitos requisito = (aca.leg.LegRequisitos) iter.next();
 %>
  <tr> 
    <td align="center"> <a href="accion?Accion=5&RequisitoId=<%=requisito.getRequisitoId()%>">
      <img title="Editar" src="../../imagenes/editar.gif" alt="Modificar" > 
      </a> <a href="javascript:Borrar('<%=requisito.getRequisitoId()%>')"> <img src="../../imagenes/no.png" alt="Eliminar" > 
      </a> </td>
    <td align="center"><%=requisito.getRequisitoId() %></td>
    <td><%=requisito.getRequisitoNombre()%></td>
  </tr>
  <%
	}	
	lisRequisito		= null;
%> 
</table>
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %> 