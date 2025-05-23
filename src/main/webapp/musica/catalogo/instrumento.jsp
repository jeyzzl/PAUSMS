<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@page import="aca.musica.MusiInstrumento"%>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="InstrumentoU" scope="page" class="aca.musica.MusiInstrumentoUtil"/>
<script type="text/javascript">
	function Borrar( InstrumentoId ){
		if(confirm("Estas seguro de eliminar el registro: "+InstrumentoId)==true){
	  		document.location="accion?Accion=4&InstrumentoId="+InstrumentoId;
	  	}
	}
</script>

<%
	ArrayList<aca.musica.MusiInstrumento> lisInstrumento 	= InstrumentoU.getListAll(conEnoc," ORDER BY INSTRUMENTO_ID");
%>
<html
><div class="container-fluid">
<h2>Listado de Instrumentos</h2>
<div class="alert alert-info">
 	<a class="btn btn-primary" href="accion?Accion=1"><spring:message code='aca.Añadir'/></a>	
</div>
<head>
<title><spring:message code='aca.DocumentoSinTitulo'/></title></head>
<body>
<table style="width:60%" >
  <tr align="center"> 
  </tr>
</table>  
<table style="width:60%" class="table table-condensed">  
  <tr> 
    <th width="10%"><spring:message code="aca.Operacion"/></th>
    <th width="10%"><spring:message code="aca.Numero"/></th>
    <th width="60%"><spring:message code='aca.Descripcion'/></th>
    <th width="20%"><spring:message code="aca.Tipo"/></th>
  </tr>
<%
	java.util.Iterator iter =  lisInstrumento.iterator();
	while (iter.hasNext()){
		aca.musica.MusiInstrumento instrumento = (MusiInstrumento) iter.next();
%>
  <tr class="tr2"> 
    <td align="center">
	  <a class="fas fa-edit" href="accion?Accion=5&InstrumentoId=<%=instrumento.getInstrumentoId()%>">
      </a> 
      <a class="fas fa-trash-alt" href="javascript:Borrar('<%=instrumento.getInstrumentoId()%>')"> 
      </a> </td>
    <td align="center"><%=instrumento.getInstrumentoId()%></td>
    <td><%=instrumento.getInstrumentoNombre()%></td>
    <td align="center"><%=instrumento.getTipo()%></td>
  </tr>
  <%
	}	
	lisInstrumento		= null;
%>
</table>
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %> 