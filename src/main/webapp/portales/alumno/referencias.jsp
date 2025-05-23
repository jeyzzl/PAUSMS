<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumReferencia"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.plan.spring.MapaPlan"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%@ include file="menu.jsp"%>

<html>
<head>
<script type="text/javascript">
	function ActualizarPlan(){  		
  		document.frmPlanes.submit();
	}	
</script>
</head>
<%
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
	String matricula			= (String) session.getAttribute("codigoAlumno");
	String numCuenta			= (String) request.getAttribute("numCuenta");
	String nombre				= (String) request.getAttribute("nombre");
	String institucion			= (String) request.getAttribute("institucion");
	// boolean tieneScotiabank		= (Boolean) request.getAttribute("tieneScotiabank");
	// boolean tieneSantander 		= (Boolean) request.getAttribute("tieneSantander");
	// boolean tieneBanorte 		= (Boolean) request.getAttribute("tieneBanorte");
	// boolean esCovoprom 			= (Boolean) request.getAttribute("esCovoprom");
	// String clabeScotiabank		= esCovoprom?"044597253000656097":"044597253000003611";
	
	// Actualizaci�n hecha el 26-11-2014, actualmente Scotiabank y Santander utilizan el mismo algoritmo para obtener el digito verificador.
	String cuenta 				= (String) request.getAttribute("cuenta");
	String digito 				= (String) request.getAttribute("digito");	
	// AlumReferencia alumno 		= (AlumReferencia) request.getAttribute("alumno");	
	String planId 				= (String) request.getAttribute("planId");
	//System.out.println("Datos:"+cuenta+":"+matricula+":"+digito);
	List<AlumPlan> lisPlanes 					= (List<AlumPlan>) request.getAttribute("lisPlanes");	
	HashMap<String, MapaPlan> mapaPlanes 		= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
%>
<body>
<div class="container-fluid">
	<h3>Payment Methods<small class="text-muted fs-6">( <%=matricula%> - <%=nombre%> )</small></h3>
	<form name="frmPlanes" action="referencias" mthod="post">
	<div class="alert alert-info">
		<a href="financieroFulton" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp; 	
		<%-- <spring:message code="portal.alumno.referencias.Plan"/>:
		<select class="custom-select" name="PlanId" style="width:500px;" onChange="javascritp:ActualizarPlan()">
		<% 		 
			int row=0;
			for (AlumPlan plan : lisPlanes){
				row++;
				String nombrePlan = "-"; 	
				if (mapaPlanes.containsKey(plan.getPlanId())){
					nombrePlan = mapaPlanes.get(plan.getPlanId()).getNombrePlan();
				}%>		
				<option value="<%=plan.getPlanId() %>" <%=plan.getPlanId().equals(planId)?"Selected":""%>><%=plan.getPlanId()%>-<%=nombrePlan%></option>		
			<%	
			}
			%>
		</select>				 --%>
	</div>
	</form>	
	<table style="margin: 0 auto;">
	<%-- <tr>
		<td class="fs-5">				
			<spring:message code="portal.alumno.referencias.DepositoNombre"/>:				
			<b><%=institucion%></b>						
		</td>		
	</tr>	 --%>
	</table>
	<div class="row mb-3">
		<div class="col-lg-6">	
			<div class="card border-dark text-center" style="background-color:white">
				<div class="card-header bg-transparent border-dark"><img src="../../imagenes/logo-scotiabank.jpg" width="150"></div>
				<div class="card-body">	
					<table style=" margin: 0 auto; border-collapse: separate; border-spacing: 2px; ">					
					<tr><td class="text-center fs-6" colspan="2"><b><spring:message code="portal.alumno.referencias.DepositoVentanilla"/></b></td></tr>
					<tr>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>###</b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>###</b>&nbsp;</td>
					</tr>
					<%-- <tr>
						<td class="center"><spring:message code="aca.Cuenta"/></td>
						<td class="center"><spring:message code="portal.alumno.referencias.Referencia"/></td>
					</tr>								 --%>
					</table>
				</div>
			</div>
		</div>
		<div class="col-lg-6">	
			<div class="card border-dark text-center" style="background-color:white">
				<div class="card-header bg-transparent border-dark"><img src="../../imagenes/logo-scotiabank.jpg" width="150"></div>
				<div class="card-body">			
					<table style=" margin: 0 auto; border-collapse: separate; border-spacing: 2px; ">					
					<tr><td class="text-center fs-6" colspan="2"><b><spring:message code="portal.alumno.referencias.TransferenciaElectronica"/></b></td></tr>
					<tr>												
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>###</b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>###</b>&nbsp;</td>
					</tr>
					<%-- <tr>												
						<td class="center"><spring:message code="portal.alumno.referencias.ClabeInterbancaria"/></td>
						<td class="center"><spring:message code="portal.alumno.referencias.Referencia"/></td>
					</tr>								 --%>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-6">	
			<div class="card border-dark text-center" style="background-color:white">
				<div class="card-header bg-transparent border-dark"><img src="../../imagenes/logo-santander.png" width="150"></div>
				<div class="card-body">			
					<table style=" margin: 0 auto; border-collapse: separate; border-spacing: 2px; ">					
					<tr><td class="text-center fs-6" colspan="3"><b><spring:message code="portal.alumno.referencias.DepositoVentanilla"/></b></td></tr>
					<tr>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>###</b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>###</b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>###</b>&nbsp;</td>
					</tr>
					<%-- <tr>
						<td class="center"><spring:message code="portal.alumno.referencias.Convenio"/></td>
						<td class="center"><spring:message code="aca.Cuenta"/></td>
						<td class="center"><spring:message code="portal.alumno.referencias.Referencia"/></td>
					</tr>				 --%>
					</table>			
				</div>
			</div>
		</div>	
		<div class="col-lg-6">	
			<div class="card border-dark text-center" style="background-color:white">
				<div class="card-header bg-transparent border-dark"><img src="../../imagenes/logo-santander.png" width="150"></div>
				<div class="card-body">			
					<table style=" margin: 0 auto; border-collapse: separate; border-spacing: 2px; ">					
					<tr><td class="text-center fs-6" colspan="4"><b><spring:message code="portal.alumno.referencias.TransferenciaElectronica"/></b></td></tr>
					<tr>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>###</b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>###</b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>###</b>&nbsp;</td>
						<td style="text-align:center; border:1px solid; font-size:12pt;">&nbsp;<b>###</b>&nbsp;</td>
					</tr>
					<%-- <tr>
						<td class="center"><spring:message code="portal.alumno.referencias.Convenio"/></td>
						<td class="center"><<spring:message code="portal.alumno.referencias.ClabeInterbancaria"/></td>
						<td class="center"><<spring:message code="portal.alumno.referencias.Referencia"/></td>
						<td class="center"><spring:message code="portal.alumno.referencias.Concepto"/></td>
					</tr>				 --%>
					</table>
				</div>
			</div>
		</div>
	</div>		
</div>
</div>	
</body>
</html>