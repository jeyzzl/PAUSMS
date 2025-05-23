<%@ page import="aca.catalogo.spring.CatModalidad"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<html>
<head>
	<script type="text/javascript">
		function Nuevo() {
			document.location.href = "accion?ModalidadId=0";
		}
	
		function Grabar() {
			if (document.frmmodalidad.ModalidadId.value != ""
					&& document.frmmodalidad.NombreModalidad != "") {
				document.frmmodalidad.Accion.value = "2";
				document.frmmodalidad.submit();
			} else {
				alert("<spring:message code="aca.JSCompletar"/> ");
			}
		}			
	</script>
</head>
<%
	CatModalidad modalidad 		= (CatModalidad)request.getAttribute("modalidad");

	String mensaje				= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
	String sResultado 			= "";	
%>
<body>
<div class="container-fluid">
	<h2><spring:message code="catalogos.modalidades.Titulo2"/></h2>
	<form action="grabar" method="post" name="frmmodalidad" target="_self">
	<input type="hidden" name="Accion">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="modalidad"><i class="fas fa-arrow-left"></i></a>
	</div>
	<div class="form-group">
		<label><strong><spring:message code="aca.Clave"/></strong></label>
		<input class="input input-small form-control" name="ModalidadId" type="text" id="ModalidadId" maxlength="3" value="<%=modalidad.getModalidadId()%>" readonly>
		<br>
		<label><strong><spring:message code="aca.Modalidad"/></strong></label>
		<input class="input input-large form-control"name="NombreModalidad" type="text" id="NombreModalidad" value="<%=modalidad.getNombreModalidad()%>" maxlength="40">
		<br>
		<label><strong><spring:message code="catalogos.modalidades.EnLinea"/></strong></label>
		<%	String enlinea = modalidad.getEnLinea()!=null&&modalidad.getEnLinea().equals("S") ? "S" : "N"; %>
		<select name="EnLinea" id="EnLinea" class="input input-mini form-select">
			<option value="S" <%=enlinea.equals("S")?"Selected":"" %>>Yes</option>
			<option value="N" <%=enlinea.equals("N")?"Selected":"" %>><spring:message code='aca.No'/></option>
		</select>
		<br>
		<label><strong><spring:message code="catalogos.modalidades.Admisible"/></strong></label>
		<%	String admisible = modalidad.getAdmisible()!=null&&modalidad.getAdmisible().equals("S") ? "S" : "N"; %>
		<select name="Admisible" id="Admisible" class="input input-mini form-select">
			<option value="S" <%=admisible.equals("S")?"Selected":"" %>>Yes</option>
			<option value="N" <%=admisible.equals("N")?"Selected":"" %>><spring:message code='aca.No'/></option>
		</select>
		<br>
	</div>
<%	if(mensaje.equals("1")){%>
	<div class="alert alert-success">
		<spring:message code="aca.Guardado"/><%=sResultado%>
	</div>
<%	}else if(mensaje.equals("2")){%>
	<div class="alert alert-danger">
		<spring:message code="aca.NoGuardado"/><%=sResultado%>
	</div>
<%	}else if(mensaje.equals("3")){%>
	<div class="alert alert-success">
		<spring:message code="aca.Modificado"/><%=sResultado%>
	</div>
<%	}else if(mensaje.equals("4")){%>
	<div class="alert alert-danger">
		<spring:message code="aca.NoModificado"/><%=sResultado%>
	</div>
<%	} %>	
	<div class="alert alert-info">
<%-- 		<a class="btn btn-primary" href="javascript:Nuevo()"><spring:message code="aca.Nuevo"/></a>&nbsp; --%>
		<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Guardar"/></a>&nbsp;
	</div>		
	</form>
</div>
</body>
</html>