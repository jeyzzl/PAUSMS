<%@ page import="java.util.List "%>
<%@ page import="java.util.HashMap "%>
<%@ page import="aca.candado.spring.CandTipo"%>
<%@ page import="aca.candado.spring.CandPermiso"%>
<%@page import="aca.emp.spring.EmpMaestro"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type = "text/javascript">
	
	function Grabar(){
		const tipoId			= document.getElementById("tipoId").value;
		const codigoPersonal 	= document.getElementById("empleado").value;
		
		location.href = "grabarPermiso?tipoId="+tipoId+"&codigoPersonal="+codigoPersonal
	}	
	
	function borrar(TipoId, CodigoPersonal) {
		const tipoId = TipoId;
		const codigoPersonal = CodigoPersonal;
		if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
			document.location = "borrarPermiso?tipoId="+tipoId+"&codigoPersonal="+codigoPersonal;
		}
	}
</script>

<link rel="stylesheet" href="../../js/chosen/chosen.css" />

<%
	String codigoPersonal		= (String) request.getAttribute("codigoPersonal");
	String tipoId				= (String) request.getAttribute("TipoId");
	String mensaje					= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");

	List<EmpMaestro> lisEmpleados = (List<EmpMaestro>)request.getAttribute("lisEmpleados");
	List<CandPermiso> lisPermiso = (List<CandPermiso>)request.getAttribute("lisPermisos");
	
	HashMap<String, EmpMaestro> mapaMaestros = (HashMap<String, EmpMaestro>)request.getAttribute("mapaMaestros");
%>

<div class="container-fluid">
	<h2>Authorization</h2>
	<div class="alert alert-info d-flex align-items-center">
		<a href="candados" class="btn btn-primary">Return</a>&nbsp;&nbsp;&nbsp;&nbsp;
		Select a user:&nbsp;&nbsp;
		<select class="form-select chosen" name="empleado" id="empleado" style="width:20rem">
<%		for(EmpMaestro maestro : lisEmpleados){ %>
			<option value = "<%=maestro.getCodigoPersonal()%>"><%=maestro.getCodigoPersonal() %> - <%=maestro.getNombre()%> <%=maestro.getApellidoPaterno() %></option>
<%		} %>
		</select>
		<input hidden name = "tipoId" id = "tipoId" value = "<%=tipoId %>">
		&nbsp;&nbsp;&nbsp;
		<a class="btn btn-success" href="javascript:Grabar()">Add</a>
		&nbsp;&nbsp;&nbsp;
		<small class="text-muted fs-5"><%= mensaje.equals("") ? "" : mensaje %></small>
	</div>
	<table class="table table-bordered table-sm">
		<thead class="table-info">
			<tr>
				<th>#</th>
				<th>Name</th>
				<th>Added by</th>
				<th>Date</th>
			</tr>
		</thead> 
		<tbody>
<%
		for(CandPermiso permiso : lisPermiso){
			
			String nombre = "-";
			
			if(mapaMaestros.containsKey(permiso.getCodioPersonal())){
				nombre = mapaMaestros.get(permiso.getCodioPersonal()).getNombre()+" "+mapaMaestros.get(permiso.getCodioPersonal()).getApellidoPaterno()+" "+mapaMaestros.get(permiso.getCodioPersonal()).getApellidoMaterno();
			}
%>
		<tr>
			<td>
				<a href="javascript:borrar('<%=tipoId%>','<%=permiso.getCodioPersonal()%>')"><i class="fas fa-user-slash text-danger"></i></a>
			</td>
			<td> <%=permiso.getCodioPersonal() %> - <%=nombre %></td>
			<td><%=permiso.getUsAlta() %></td>
			<td><%=permiso.getFechaAlta() %></td>
		</tr>
		
<%
		}
%>
		<tbody>
	</table>
</div>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script>
	jQuery(".chosen").chosen();
</script>