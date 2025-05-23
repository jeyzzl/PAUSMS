<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.spring.Maestros"%>

<script type="text/javascript">
	function borrar(documentoId,hoja){
		if (confirm("¿Estas seguro de borrar este registro?")){
			location.href="borrar?DocumentoId="+documentoId;
		}	
	}	
</script>
<%	
	List<Maestros> lisEmpleados				= (List<Maestros>)request.getAttribute("lisEmpleados");
	HashMap<String,String> mapaImagenes		= (HashMap<String,String>)request.getAttribute("mapaImagenes");	
%>
<body >
<div class="container-fluid">
	<h2>Empleados con documentos</h2>
	<form name="forma" action="documentos" method='post' id="noayuda">
	<div class="alert alert-info d-flex align-items-center">		
		Filtrar:&nbsp; <input type="text" class="form-control" style="width:140px" placeholder="Buscar..." id="buscar">
	</div>
	</form>
	<table id="table" class="table table-sm table-bordered">
		<thead>
		<tr class="table-info">			
			<th width="5%"><spring:message code="aca.Numero"/></th>
			<th width="10%">Clave</th>
			<th width="75%">Empleado</th>
			<th width="10%" class="text-end">Imagenes</th>			
		</tr>
		</thead>		
		<tbody>
<%
	int row = 0;
	for (Maestros maestro : lisEmpleados){
		row++;	
		
		String numImg = "0";
		if (mapaImagenes.containsKey(maestro.getCodigoPersonal())){
			numImg = mapaImagenes.get(maestro.getCodigoPersonal());
		}
%>
		<tr>			
			<td><%=row%></td>			
			<td><%=maestro.getCodigoPersonal()%></td>
			<td><%=maestro.getNombre()+" "+maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno()%></td>			
			<td class="text-end"><a href="documentos?CodigoEmpleado=<%=maestro.getCodigoPersonal()%>"><span class="badge bg-dark rounded-pill"><%=numImg%></span></a></td>
		</tr>
<%	} %>		
		</tbody>
	</table>	
</div>
</body>
<script type="text/javascript" src="../../js/search.js"></script>
<script type="text/javascript">
	jQuery('#buscar').focus().search({table:jQuery("#table")});	
</script>

