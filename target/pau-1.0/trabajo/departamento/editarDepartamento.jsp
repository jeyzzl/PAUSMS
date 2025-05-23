<%@ page import= "java.util.List"%>
<%@ page import="aca.trabajo.spring.TrabDepartamento"%>
<%@ page import= "aca.vista.spring.Maestros"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>


<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<html>
	<head>
		<script type="text/javascript">
			function Grabar() {
				if (document.frmdept.DeptId.value != ""
						&& document.frmdept.Nombre != ""
							&& document.frmdept.Detalles != "") {
					document.frmdept.submit();
				} else {
					alert("<spring:message code="aca.JSCompletar"/> ");
				}
			}
		</script>
	</head>
<%
	// Declaracion de variables	
	TrabDepartamento departamento = (TrabDepartamento) request.getAttribute("departamento");
	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	List<Maestros> lisMaestros		= (List<Maestros>) request.getAttribute("lisMaestros");
%>
<body>
<div class="container-fluid">
	<h1>CTP Departments</h1>
	<form action="grabarDepartamento" method="post" name="frmdept" target="_self">
	<div class="alert alert-info">
		<a href="departamentos" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<div class="form-group">
		<label for="Clave"><strong><spring:message code="aca.Clave"/></strong></label>
		<input class="input input-mini form-control" name="DeptId" type="text" id="DeptId" maxlength="3" value="<%=departamento.getDeptId()%>" readonly>
		<br>
		<label for="Nombre"><strong><spring:message code="aca.Nombre"/></strong></label>
		<input class="input input-xlarge form-control" name="Nombre" type="text" id="Nombre" value="<%=departamento.getNombre()%>" maxlength="50">
		<br>
		<label for="Detalles">Details</strong></label>
		<input class="input input-xlarge form-control" name="Detalles" type="text" id="Detalles" value="<%=departamento.getDetalles()%>" maxlength="50">
		<br>
		<label for="Estado">Status</strong></label>
		<select class="input input-medium form-select" style="width:200px" name="Estado"  id="Estado" required>
	    	<option value="A" <% if (departamento.getEstado().equals("A")) out.print("selected"); %> >Active</option>
	    	<option value="I" <% if (departamento.getEstado().equals("I")) out.print("selected"); %> >Inactive</option>
	    </select>
	    <br>
		<label for="CodigoPersonal">Employee ID:</label><br>
	    <select name="CodigoPersonal" class="form-select chosen" style="width:400px" required>
	    	<option value="0" <%=departamento.getCodigo_personal().equals("1")?"selected":""%>>Choose</option>
<%		for (Maestros maestro : lisMaestros){ %>
			<option value="<%=maestro.getCodigoPersonal()%>" <%=maestro.getCodigoPersonal().equals(departamento.getCodigo_personal())?"selected":""%>><%=maestro.getEstado()%>-<%=maestro.getCodigoPersonal()%> - <%=maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno()+" "+maestro.getNombre()%></option>
<%		} %>	    	
	    </select>
	    <br><br>	
	</div>
	<div class="alert alert-info">
		<a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>&nbsp;
<% if (!mensaje.equals("-")){ out.print(mensaje);}%>
	</div>
	</form>
</div>

<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script>
	jQuery(".chosen").chosen();	
</script>
</body>
</html>