<%@page import="java.text.*" %>
<%@page import="java.util.List" %>
<%@page import="java.util.HashMap" %>
<%@page import="aca.admision.spring.AdmIngreso"%>


<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">	
	function Grabar(){
		if(document.frmPeriodo.PeriodoId.value!=""){
			document.frmPeriodo.submit();
		}else{
			alert("Complete el formulario ..! ");
		}
	}	
	
</script>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css"/>
</head>
<% 
	
	AdmIngreso periodo		= (AdmIngreso)request.getAttribute("periodo");
	String mensaje 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");

%>
<head></head>	
<body>
<div class="container-fluid">
	<h2><spring:message code='aca.Editar'/> <spring:message code='aca.Periodo'/> <small class="text-muted fs-5"></small></h2>	
	<div class="alert alert-info">
		<a class="btn btn-primary btn-small" href="periodos"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		<%if(!mensaje.equals("-")) out.print(mensaje); %>
	</div>	
	<form id="frmPeriodo" name="frmPeriodo" action="grabarPeriodo" method="post">
		<div class="row">
			<div class="col-4">
				<div class="control-group">
					<label for="PeriodoId"><spring:message code="aca.Periodo"/><span class="required-indicator">*</span></label>
					<input type="text" class="form-control" required id="PeriodoId" name="PeriodoId" value="<%=periodo.getPeriodoId()%>" readonly/>
				</div><br>
			<div class="control-group">
					<label for="PeriodoNombre"><spring:message code="aca.NombrePeriodo"/><span class="required-indicator">*</span></label>
					<input type="text" class="form-control" required id="PeriodoNombre" name="PeriodoNombre" value="<%=periodo.getPeriodoNombre()%>" />
			</div><br>
				<div class="control-group">
					<label for="Fecha"><spring:message code='aca.FechaInicial'/><span class="required-indicator">*</span></label>
					<input type="text" class="form-control" data-date-format="dd/mm/yyyy"  required id="Fecha" name="Fecha" value="<%=periodo.getFecha() %>" maxlength="10" size="12" />
					(DD/MM/AAAA)
			</div><br>
			</div>
			
				<div class="control-group">
					<label for="Estado"><spring:message code="aca.Status"/><span class="required-indicator">*</span></label>
					<select class="form-select" style="width: 120px" id="Estado" name="Estado" >
						<option value="A" <% if(periodo.getEstado().equals("A"))out.print("selected"); %>><spring:message code='aca.Activo'/></option>
						<option value="I" <% if(periodo.getEstado().equals("I"))out.print("selected"); %>><spring:message code='aca.Inactivo'/></option>
					</select>
				</div>					
			</div>
			<br>
			<div class="alert alert-info">
				<a onclick="javascript:Grabar();" class="btn btn-primary"><spring:message code="aca.Grabar"/></a>
			</div>
		</div>
	</form>
</div>	
</body>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script type="text/javascript">
	jQuery('#Fecha').datepicker();
</script>