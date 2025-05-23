<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ page import= "aca.musica.MusiTipoCta"%>
<%@ page import= "aca.musica.MusiTipoCtaUtil"%>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar( TipoCtaId ){
		if(confirm("Estas seguro de eliminar el registro: "+TipoCtaId)==true){
	  		document.location="accion?Accion=4&TipoCtaId="+TipoCtaId;
	  	}
	}
</script>

<%
	ArrayList lisTipo 					= new ArrayList();	
	MusiTipoCtaUtil tipoU				= new MusiTipoCtaUtil();
	lisTipo								= tipoU.getListAll(conEnoc,"ORDER BY 1");	 
%>

<html>
<head>
<div class="container-fluid">
<h1>Listado de Tipo de Cuenta</h1>
<div class="alert alert-info">
 	<a class="btn btn-primary" href="accion?Accion=1"><spring:message code='aca.Añadir'/></a>	
</div>
<title><spring:message code='aca.DocumentoSinTitulo'/></title></head>
<body>
<table style="width:60%" id="noayuda" class="table table-condensed">	  
  <tr> 
    <th width="17%"><spring:message code="aca.Operacion"/></th>
    <th width="15%"><spring:message code="aca.Numero"/></th>
    <th width="68%"><spring:message code='aca.Descripcion'/></th>
  </tr>
<%
	java.util.Iterator iter =  lisTipo.iterator();
	while (iter.hasNext()){
	MusiTipoCta tipo 		= (MusiTipoCta) iter.next();	
%>
  <tr> 
    <td align="center"> <a class="fas fa-edit" href="accion?Accion=5&TipoCtaId=<%=tipo.getTipoCtaId()%>">
     
      </a> <a class="fas fa-trash-alt" href="javascript:Borrar('<%=tipo.getTipoCtaId()%>')">
      </a> </td>
    <td align="center"><%=tipo.getTipoCtaId()%></td>
    <td><%=tipo.getTipoCtaNombre()%></td>
  </tr>
  <%
	}	
	tipoU 		= null;
	lisTipo		= null;
%>
</table>
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %> 