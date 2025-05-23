<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.emp.spring.EmpRango"%>
<%@page import="aca.emp.spring.EmpRangoEmp"%>
<%@page import="aca.carga.spring.Carga"%>


<script type="text/javascript">
	function eliminar(cargaId){
		if (confirm("¿Estas seguro de borrar este registro?")){
			location.href="borrar?CargaId="+cargaId;
		}	
	}	
</script>

<%	
	String cargaId				= (String) request.getAttribute("cargaId");
	String codigoEmpleado 		= (String) request.getAttribute("codigoEmpleado");
	String nombreEmpleado 		= (String) request.getAttribute("nombreEmpleado");
	
	List<EmpRangoEmp> lisRangos				= (List<EmpRangoEmp>)request.getAttribute("lisRangos");
	HashMap<String, EmpRango> mapaRangos	= (HashMap<String, EmpRango>)request.getAttribute("mapaRangos");
%>
<body marginwidth="0" marginheight="0" leftmargin="0" topmargin="0" background="../../imagenes/back.gif">
<div class="container-fluid">
	<h2>Rango académico <small class="text-muted fs-4"> (<%=codigoEmpleado%> - <%=nombreEmpleado%>) </small></h2>
	<form name="forma" action="rango" method='post' id="noayuda">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="lista?CargaId=<%=cargaId%>">Regresar</a>&nbsp;&nbsp;&nbsp;
		<a class="btn btn-primary" href="accion?CargaId=<%=cargaId%>"><i class="icon-white icon-plus"></i> <spring:message code='aca.Añadir'/></a>
	</div>	
	</form>
	<table  class="table table-fullcondensed table-striped">
		<tr>
			<th width="10%"><spring:message code="aca.Operacion"/></th>
			<th width="5%"><spring:message code="aca.Numero"/></th>
			<th width="10%">CargaId</th>
			<th width="40%">Rango</th>
			<th width="10%">Precio</th>
			<th width="10%">Fecha</th>
			<th width="10%">Estado</th>
		</tr>
<%
	int row = 0;
	for (aca.emp.spring.EmpRangoEmp rango : lisRangos){
		if(cargaId.equals(rango.getCargaId())){
			row++;
			
			String nombreRango 		= "-";
			String precioMinimo		= "0";
			if (mapaRangos.containsKey(rango.getRangoId())){
				nombreRango 	= mapaRangos.get(rango.getRangoId()).getRangoNombre();
				precioMinimo	= mapaRangos.get(rango.getRangoId()).getPrecioMin();
			}		
%>
		<tr>
			<td>
				<a class="fas fa-trash-alt" href="javascript:eliminar('<%=rango.getCargaId()%>')"></a>&nbsp;
				<a href="accion?CargaId=<%=rango.getCargaId()%>" title="Modificar"><i class="fas fa-pencil-alt"></i></a>
			</td>
			<td><%=row%></td>		
			<td><%=rango.getCargaId()%></td>	
			<td><%=nombreRango%></td>
			<td><%=precioMinimo%></td>
			<td><%=rango.getFecha()%></td>			
			<td><%=rango.getEstado().equals("A")?"Activo":"Inactivo"%></td>
		</tr>
<%		
		}
	}
%>		
	</table>
</div>
</body>
