<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="tipoU" scope="page" class="aca.musica.MusiTipoCtaUtil"/>
<jsp:useBean id="CuentaU" scope="page" class="aca.musica.MusiCuentaUtil"/>

<script type="text/javascript">
	function Borrar( CuentaId ){
		if(confirm("Estas seguro de eliminar el registro: "+CuentaId)==true){
	  		document.location="accion?Accion=4&CuentaId="+CuentaId;
	  	}
	}
</script>

<%
	String tipo 	= "";
	ArrayList<aca.musica.MusiCuenta> lisCuenta 	= CuentaU.getListAll(conEnoc,"ORDER BY TIPO,CUENTA_NOMBRE");
%>
<html>
<head>
<div class="container-fluid">
<h1>Listado de Cuentas</h1>
<div class="alert alert-info">
 	<a class="btn btn-primary" href="accion?Accion=1"><spring:message code='aca.Añadir'/></a>	
</div>
<title><spring:message code='aca.DocumentoSinTitulo'/></title></head>
<body> 
<table style="width:70%" class="table table-fullcondensed">  
  <tr> 
    <th width="10%"><spring:message code="aca.Operacion"/></th>
    <th width="10%"><spring:message code="aca.Numero"/></th>
    <th width="60%"><spring:message code='aca.Descripcion'/></th>
    <th width="10%"><spring:message code="aca.Tipo"/></th>
    <th width="30%"><spring:message code="aca.Instrumento"/></th>
  </tr>
<%
	java.util.Iterator iter =  lisCuenta.iterator();
	while (iter.hasNext()){
		aca.musica.MusiCuenta cuenta = (aca.musica.MusiCuenta) iter.next();
		tipo = aca.musica.MusiTipoCta.getNombre(conEnoc,cuenta.getTipo());
%>
  <tr class="tr2"> 
    <td align="center"> 
      <a class="fas fa-edit" href="accion?Accion=5&CuentaId=<%=cuenta.getCuentaId()%>">
      </a> <a class="fas fa-trash-alt" href="javascript:Borrar('<%=cuenta.getCuentaId()%>')"> 
      </a> </td>
    <td align="center"><%=cuenta.getCuentaId()%></td>
    <td><%=cuenta.getCuentaNombre()%></td>
    <td align="center"><%=tipo%></td>
    <td align="center"><%=aca.musica.MusiInstrumento.getNombreInstrumento(conEnoc,cuenta.getInstrumentoId()) %> </td>
  </tr>
  <%
	}	
	lisCuenta		= null;
%>
</table>
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %> 