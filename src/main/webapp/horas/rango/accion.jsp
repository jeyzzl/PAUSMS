<%@page import="aca.emp.spring.EmpHoras"%>
<%@page import="aca.vista.spring.Maestros"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="java.util.List"%>
 
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="aca.emp.spring.EmpRango"%>
<%@page import="aca.emp.spring.EmpRangoEmp"%>
<%@page import="aca.carga.spring.Carga"%>

<body>
<%
	String cargaId				= (String) request.getAttribute("cargaId");

	String codigoEmpleado 		= (String)session.getAttribute("codigoEmpleado");
	String nombreEmpleado		= (String) request.getAttribute("nombreEmpleado");
	EmpRangoEmp rangoEmp		= (EmpRangoEmp) request.getAttribute("rangoEmp");
	
	List<EmpRango> lisRangos	= (List<EmpRango>) request.getAttribute("lisRangos");
	List<Carga> lisCargas		= (List<Carga>) request.getAttribute("lisCargas");
	String rangoId 				= "0";	
	
%>
<div class="container-fluid">
	<h2>Rango académico<small class="text-muted fs-4">( <%=codigoEmpleado%> - <%=nombreEmpleado%>)</small></h2>
	<div class="alert alert-info">
		<a href="rango?CargaId=<%=cargaId%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i> <spring:message code='aca.Regresar'/></a>
	</div>
	<form name="frmRango" action="grabar" method="post">
	<div class="row">
		<div class="span3">
			<label for="Carga">Carga:</label>			
			<select name="CargaId" id="CargaId" style="width:400px;">
		<%		 			
			for(Carga carga: lisCargas){		 				
		%>
			 	<option value="<%=carga.getCargaId()%>" <%=carga.getCargaId().equals(rangoEmp.getCargaId())?" selected":""%>>
			 		[<%=carga.getCargaId()%>]-<%=carga.getNombreCarga()%>
			 	</option>
		<%
			}		
		%>
			</select>
			<br><br>
			<label for="Rango">Rango:</label>			
			<select name="RangoId" id="RangoId" style="width:400px;">
		<%		 			
			for(EmpRango rango: lisRangos){		 				
		%>
			 	<option value="<%=rango.getRangoId()%>" <%=rango.getRangoId().equals(rangoEmp.getRangoId())?" selected":""%>>
			 		<%=rango.getRangoNombre()%> [$<%=rango.getPrecioMin()%>]
			 	</option>
		<%
			}		
		%>
			</select>
			<br><br>
			<label for="Fecha">Fecha:</label>			
			<input type="text" name="Fecha" id="Fecha" size="12" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%=rangoEmp.getFecha()%>" />		
			<br><br>
			<label for="Estado">Estado:</label>
			<select name="Estado" id="Estado" style="width:200px;">
				<option value="A" <%=rangoEmp.getEstado().equals("A")?"selected":""%>>Activo</option>
				<option value="I" <%=rangoEmp.getEstado().equals("I")?"selected":""%>>Inactivo</option>
			</select>
			<br><br>
		</div>		
	</div>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="javascript:Guardar();"><i class="icon-ok icon-white"></i> Guardar</a>
	</div>
	</form>
			
</div>	

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>	
	function Guardar() {
		if (document.frmRango.Fecha.value != "") {			
			document.frmRango.submit();
		} else {
			alert("¡Completa todos los campos!");
		}
	}

	jQuery('#Fecha').datepicker();	
</script>  	
</html>