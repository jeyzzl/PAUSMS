<%@ page import= "java.util.List"%>
<%@ page import="aca.trabajo.spring.TrabDepartamento"%>
<%@ page import="aca.trabajo.spring.TrabAsesor"%>
<%@ page import= "aca.vista.spring.Maestros"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<script type="text/javascript">
	function Grabar() {
		if (document.frmasesor.DeptId.value != ""
			&& document.frmasesor.CodigoPersonal != ""
			&& document.frmasesor.Status != "") {
			document.frmasesor.submit();
		} else {
			alert("<spring:message code="aca.JSCompletar"/> ");
		}
	}
</script>
<%
    TrabAsesor asesor               = (TrabAsesor) request.getAttribute("asesor");
    TrabDepartamento departamento   = (TrabDepartamento) request.getAttribute("departamento");

    String nombreAsesor			= request.getParameter("nombreAsesor")==null?"-":request.getParameter("nombreAsesor");
    String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");

    List<Maestros> lisMaestros		= (List<Maestros>) request.getAttribute("lisMaestros");
%>
<body>
<div class="container-fluid">
	<h2>Department Supervisor</h2>
    <form action="grabar" method="post" name="frmasesor">
    <div class="alert alert-info">
		<a href="listado" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
    <div class="form-group">
		<label for="CodigoPersonal">Employee ID:</label><br>
	    <select name="CodigoPersonal" class="form-select chosen" style="width:400px" required>
<%		for (Maestros maestro : lisMaestros){ %>
			<option value="<%=maestro.getCodigoPersonal()%>" <%=maestro.getCodigoPersonal().equals(asesor.getCodigoPersonal())?"selected":""%>><%=maestro.getEstado()%>-<%=maestro.getCodigoPersonal()%> - <%=maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno()+" "+maestro.getNombre()%></option>
<%		} %>	    	
	    </select>
        <br>
        <label for="DeptId"><strong>Department</strong></label>
		<input class="input input-mini form-control" name="DeptId" type="text" id="DeptId" maxlength="3" style="width:400px" value="<%=asesor.getDeptId()%>" readonly>
		<label for="Status">Status</strong></label>
		<select class="input input-medium form-select" style="width:200px" name="Status"  id="Status" required>
	    	<option value="A" <% if (asesor.getStatus().equals("A")) out.print("selected"); %> >Active</option>
	    	<option value="I" <% if (asesor.getStatus().equals("I")) out.print("selected"); %> >Inactive</option>
	    </select>
        <br>
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