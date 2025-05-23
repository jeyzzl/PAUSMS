<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="EnvioU" scope="page" class="aca.envio.EnvioServicioUtil"/>

<script type="text/javascript">
	function Borrar( ServicioId ){
		if(confirm("Estas seguro de eliminar el registro: "+ServicioId)==true){
	  		document.location="accion?Accion=4&ServicioId="+ServicioId;
	  	}
	}
</script>

<%
	ArrayList lisServicios 	= EnvioU.getListAll(conEnoc, "ORDER BY SERVICIO_ID");
%>
<html>
<div class="container-fluid">
	<h1>Listado de Servicios</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="accion?Accion=1"><spring:message code='aca.Añadir'/></a>
	</div>
	<table style="width:60%" class="table table-bordered">
	<thead class="table-info">	 
  	<tr> 
		<th width="10%"><spring:message code="aca.Operacion"/></th>
    	<th width="10%"><spring:message code="aca.Numero"/></th>
   		<th width="60%"><spring:message code='aca.Descripcion'/></th>
    	<th width="20%"><spring:message code="aca.Telefono"/></th>
  	</tr>
  	</thead>
  	<tbody>
<%
	java.util.Iterator iter =  lisServicios.iterator();
	while (iter.hasNext()){
		aca.envio.EnvioServicio servicio = (aca.envio.EnvioServicio) iter.next();
	
%>
	<tr> 
    	<td align="center"> 
	    	<a class="fas fa-edit" href="accion?Accion=5&ServicioId=<%= servicio.getServicioId()%>"></a> 
    		<a class="fas fa-trash-alt" href="javascript:Borrar('<%=servicio.getServicioId()%>')"></a></td>
    	<td align="center"><%= servicio.getServicioId()%></td>
    	<td><%= servicio.getServicioNombre()%></td>
    	<td align="center"><%= servicio.getTelefonos()%></td>
  	</tr>
  <%
	}	
	lisServicios		= null;
%>
	</tbody>
	</table>
</div>
</html>
<%@ include file= "../../cierra_enoc.jsp" %> 