<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.trabajo.spring.TrabDepartamento"%>
<%@ page import="aca.trabajo.spring.TrabCategoria"%>
<%@ page import="aca.trabajo.spring.TrabPeriodo"%>
<%@ page import="aca.trabajo.spring.TrabAlum"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<head>
<script type="text/javascript">
	function Grabar() {
		if (document.frmPeriodoAlum.Horas.value != ""
				&& document.frmPeriodoAlum.Pago != "") {
			document.frmPeriodoAlum.submit();
			console.log("xxx");
		} else {
			alert("<spring:message code="aca.JSCompletar"/> ");
		}
	}
</script>
</head>
<%	
	String mensaje 									= (String)request.getAttribute("mensaje");
	String nombrePeriodo 							= (String)request.getAttribute("nombrePeriodo");
	String nombreDept 								= (String)request.getAttribute("nombreDept");
	String nombreCat 								= (String)request.getAttribute("nombreCat");
	String periodoId 								= (String)request.getAttribute("periodoId");
	String deptId 									= (String)request.getAttribute("deptId");
	String catId 									= (String)request.getAttribute("catId");
	TrabAlum alumno 								= (TrabAlum)request.getAttribute("alumno");
%>

<body>
	<div class="container-fluid">
		<h2>Edit CTP</h2>
		<div class="alert alert-info">
			<a href="periodoAlum" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
		</div>
		<form action="editar" method="post" name="frmPeriodoAlum">
			<div class="form-group">
				<input type="hidden" name="PeriodoId" id="PeriodoId" value="<%=periodoId%>">
				<input type="hidden" name="DeptId" id="DeptId" value="<%=deptId %>">
				<input type="hidden" name="CatId" id="CatId" value="<%=catId %>">
				
				<label for="Horas"><strong>Hours</strong></label>
				<input class="input input-mini form-control" name="Horas" type="text" id="Horas" maxlength="3" value="<%=alumno.getHoras() %>" style="width: 20rem">
				<br>
				<label for="Pago"><strong>Payed</strong></label> 
				<select class="input input-medium form-select" style="width: 15rem" name="Pago" id="Pago">
					<option value="1" <% if (alumno.getPago().equals("1")) out.print("selected"); %>>Yes</option>
					<option value="0" <% if (alumno.getPago().equals("0")) out.print("selected"); %>>No</option>
				</select>
				<br>
				<label for="Estado"><strong>Status</strong></label> 
				<select class="input input-medium form-select" style="width: 15rem" name="Estado" id="Estado">
					<option value="A" <% if (alumno.getEstado().equals("A")) out.print("selected"); %>>Active</option>
					<option value="I" <% if (alumno.getEstado().equals("I")) out.print("selected"); %>>Inactive</option>
				</select>
			</div>
			<br>
			<div class="alert alert-info">
				<a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>&nbsp;
			</div>
		</form>
		
	</div>
</body>
