<%@ page import="aca.trabajo.spring.TrabInforme"%>
<%@ page import="aca.trabajo.spring.TrabPeriodo"%>
<%@ page import="java.util.List"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<html>
	<head>
		<script type="text/javascript">
			function Grabar() {
				if (document.frmInf.informeId.value != ""
						&& document.frmInf.NombreInforme != ""
							&& document.frmInf.Estado != "") {
					document.frmInf.submit();
				} else {
					alert("<spring:message code="aca.JSCompletar"/> ");
				}
			}
		</script>
	</head>
<%
	// Declaracion de variables	
	TrabInforme informe = (TrabInforme) request.getAttribute("informe");
	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	List<TrabPeriodo> lisPeriodos	= (List<TrabPeriodo>)request.getAttribute("lisPeriodos");
%>
<body>
<div class="container-fluid">
	<h1>CTP Reports</h1>
	<form action="grabarInforme" method="post" name="frmInf" target="_self">
	<div class="alert alert-info">
		<a href="informes" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<div class="form-group">
		<label for="Clave"><strong><spring:message code="aca.Clave"/></strong></label>
		<input class="input input-mini form-control" name="informeId" type="text" id="informeId" maxlength="3" value="<%=informe.getInformeId()%>" readonly>
		<br>
		<label for="Estado"><strong>Period</strong></label>
		<select class="input input-medium form-select" style="width:200px" name="PeriodoId"  id="PeriodoId" required>
		<% for (TrabPeriodo periodo : lisPeriodos){ %>
	    	<option value="<%=periodo.getPeriodoId() %>" <% if (informe.getPeriodoId().equals(periodo.getPeriodoId())) out.print("selected"); %> ><%=periodo.getNombrePeriodo() %></option>
	    <% } %>
	    </select>
	    <br>
		<label for="Nombre"><strong><spring:message code="aca.Nombre"/></strong></label>
		<input class="input input-xlarge form-control" name="Nombre" type="text" id="Nombre" value="<%=informe.getNombreInforme()%>" maxlength="50">
		<br>
		<label for="Estado"><strong>Status</strong></label>
		<select class="input input-medium form-select" style="width:200px" name="Estado"  id="Estado" required>
	    	<option value="A" <% if (informe.getEstado().equals("A")) out.print("selected"); %> >Active</option>
	    	<option value="I" <% if (informe.getEstado().equals("I")) out.print("selected"); %> >Inactive</option>
	    </select>
		<br>
	</div>
	<div class="alert alert-info">
		<a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>&nbsp;
<% if (!mensaje.equals("-")){ out.print(mensaje);}%>
	</div>
	</form>
</div>
</body>
</html>