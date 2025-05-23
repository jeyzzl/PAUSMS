<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Date"%>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumEstado"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.carga.spring.CargaFinanciero"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.financiero.spring.FesCcobro"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.carga.spring.CargaBloque"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%@ include file="../alumno/menu.jsp"%>

<head></head>
<%
	java.text.DecimalFormat getformato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String codigoPersonal 				= (String) session.getAttribute("codigoPersonal");
	String matricula					= (String) session.getAttribute("codigoAlumno");
	AlumPersonal alumPersonal 			= (AlumPersonal) request.getAttribute("alumPersonal");
	
	List<CargaAlumno> lisCargas					= (List<CargaAlumno>) request.getAttribute("lisCargas");
	HashMap<String,String> mapaMaterias			= (HashMap<String,String>) request.getAttribute("mapaMaterias");
	HashMap<String,MapaPlan> mapaPlanes			= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String, AlumEstado> mapaAlumEstado = (HashMap<String, AlumEstado>) request.getAttribute("mapaAlumeEstado");
// 	HashMap<String, FesCcobro> mapaFesCobro		= (HashMap<String, FesCcobro>)  request.getAttribute("mapaFesCobro");
	HashMap<String, Carga> mapaCargas	 		= (HashMap<String, Carga>)  request.getAttribute("mapaCargas");
	HashMap<String, CargaBloque> mapaBloques	= (HashMap<String, CargaBloque>)  request.getAttribute("mapaBloques");
	// HashMap<String, String> mapaCreditos		= (HashMap<String, String>)  request.getAttribute("mapaCreditos");
	HashMap<String, String> mapaPagos			= (HashMap<String, String>)  request.getAttribute("mapaPagos");
	HashMap<String, CargaFinanciero> mapaCargaFinanciero			= (HashMap<String, CargaFinanciero>)  request.getAttribute("mapaCargaFinanciero");

	int con = 1;
%>
<div class="container-fluid mt-1">
	<div class="alert alert-success">
		<a href="pasos"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;
		<span class="badge rounded-pill bg-dark">3</span> <spring:message code="portal.alumno.pagos.PagoInscripcion"/>
		<small class="text-muted">( <%=alumPersonal.getCodigoPersonal()%> - <%=alumPersonal.getNombre()%>
			<%=alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%>
			)
		</small>
<!-- 	  	<a class="btn btn-warning" data-bs-toggle="collapse" href="#iframePago" role="button"> -->
<%-- 	    	<spring:message code="portal.alumno.pagos.PagoLinea"/> --%>
<!-- 	  	</a> -->
	</div>
<% if(lisCargas.size() >= 1 && lisCargas != null){%>
	<hr>
	<div class="collapse" id="iframePago">
  		<div class="card card-body">
			<iframe src="https://secure.um.edu.mx/publicstore.jsp" width="760" height="532"></iframe>
		</div>
		<hr>
	</div>
<%	
	for (CargaAlumno carga : lisCargas) { 
		String inscrito 	= "N";
// 		if (mapaFesCobro.containsKey(carga.getCargaId()+carga.getBloqueId())) {			
// 			inscrito 		= mapaFesCobro.get(carga.getCargaId()+carga.getBloqueId()).getInscrito();
// 		}
		if(mapaAlumEstado.containsKey(carga.getCargaId()+carga.getBloqueId())) {
			inscrito 		= mapaAlumEstado.get(carga.getCargaId()+carga.getBloqueId()).getEstado();
		}

		String financieroStatus = ""; 
		if(mapaCargaFinanciero.containsKey(matricula+carga.getCargaId())){
			financieroStatus = mapaCargaFinanciero.get(matricula+carga.getCargaId()).getStatus();
		}
		
		
		String cargaNombre = "-";
		if (mapaCargas.containsKey(carga.getCargaId())){
			cargaNombre = mapaCargas.get(carga.getCargaId()).getNombreCarga();
		} 
		
		String carreraSe = "";
		if (mapaPlanes.containsKey(carga.getPlanId())) {
			carreraSe = mapaPlanes.get(carga.getPlanId()).getCarreraSe();
		}
		
		String arrayFecha[] = carga.getFecha().substring(0, 10).split("-");
		String fecha = arrayFecha[2]+"/"+arrayFecha[1]+"/"+arrayFecha[0];
		
		String numMaterias	= "0";
		if (mapaMaterias.containsKey(carga.getCargaId() + carga.getBloqueId())) {
			numMaterias 	= mapaMaterias.get(carga.getCargaId() + carga.getBloqueId());
		}
		
		String bloqueNombre = "";	
		if (mapaBloques.containsKey(carga.getCargaId()+carga.getBloqueId())) {
			bloqueNombre 	= mapaBloques.get(carga.getCargaId()+carga.getBloqueId()).getNombreBloque();
		}
		
		String creditos = "0";
		// if (mapaCreditos.containsKey(carga.getCargaId()+carga.getBloqueId())) {
		// 	creditos 	= mapaCreditos.get(carga.getCargaId()+carga.getBloqueId());
		// }
		
		double pagoInicial = 0;
// 		if (mapaPagos.containsKey(carga.getCargaId()+carga.getBloqueId())) {
// 			pagoInicial 	= Double.valueOf(mapaPagos.get(carga.getCargaId()+carga.getBloqueId()));
// 			if (pagoInicial > 0) pagoInicial = 0;
// 		}
		
// 		FesCcobro cobro 	= new FesCcobro();
// 		if (mapaFesCobro.containsKey(carga.getCargaId()+carga.getBloqueId())){
// 			cobro = mapaFesCobro.get(carga.getCargaId()+carga.getBloqueId());
// 		}
		
		String colorPago	= "<span class='badge bg-danger'>";
		String colorBoton	= "btn-primary";
		String mensaje 		= "Click <b>Payment Options</b> to view the available payment methods. If you already paid or have financial arrangements, check your <b>Invoice</b> to verify that your balance due is zero.";
// 		if (pagoInicial >= 0 ){
// 			colorPago 	= "<span class='badge bg-success'>";
// 			colorBoton	= "btn-success";
// 			mensaje 	= "Your initial balance has been covered, you will soon be registered as an enrollee, verify it in the Enrollment Closing step!";
// 		}		
%>
<% 		if(!numMaterias.equals("0")){ %>
		<div class="card bg-light" style="max-width: 40rem;">
			<div class="card-header"><%=carga.getCargaId()%> - <%=cargaNombre%><small class="text-muted"> ( <spring:message code="portal.alumno.pagos.Bloque"/>: <%=carga.getBloqueId()%> - <%=bloqueNombre%> )</small></div>
			<div class="card-body">
				<p class="card-Title mb-3">
					<b><%=carga.getPlanId()%></b> - <%=carreraSe%><br>				
					<%-- <b><spring:message code="portal.alumno.pagos.Datos"/>:</b>  --%>
					<b><spring:message code="portal.alumno.pagos.Materias"/>:</b> <span class="badge bg-success"><%=numMaterias%></span><br>
					<%-- <em><spring:message code="portal.alumno.pagos.Creditos"/>:</em> <span class="badge bg-success"><%=creditos%></span>,  --%>
<%-- 					<em><spring:message code="portal.alumno.pagos.PagoInicial"/>:</em><%=colorPago%><%=getformato.format(pagoInicial)%></span><br><br> --%>
					<b>Clearance:</b>
<%					if(financieroStatus.equals("A")){%>
						<span class="badge bg-success">CLEARED</span>
<%					}else{%>
						<span class="badge bg-danger">NOT CLEARED</span>
<%					}%>
					<br>
				</p>
				<small class="text-muted"><%=mensaje%></small>
			</div>
			<div class="card-footer bg-transparent border-success d-flex justify-content-start">
<% 			if(!inscrito.equals("I")){ %>			
<%-- 				<form name="Calculo" method="post" style="margin-bottom:-5px;" action="https://virtual-um.um.edu.mx/financiero/calculoCobroEstimado.html?txtCarga=<%=cobro.getCargaId()%>@<%=cobro.getBloque()%>@<%=cobro.getPlanId()%>&sltFormaPago=<%=cobro.getFormaPago()%>&txtMatricula=<%=cobro.getMatricula()%>" target="_blank"> --%>
<%-- 				<input type="hidden" name="txtMatricula" value="<%=cobro.getMatricula() %>"> --%>
<%-- 				<input type="hidden" name="sltFormaPago" value="<%=cobro.getFormaPago() %>"> --%>
<%-- 				<input type="hidden" name="txtCarga" value="<%=cobro.getCargaId()%>@<%=cobro.getBloque()%>@<%=cobro.getPlanId()%>">		 --%>
<%					if(financieroStatus.equals("A")){%>
						<a class="btn btn-sm <%=colorBoton%> me-3" href="verCostos" style="color:white"><spring:message code="portal.alumno.pagos.CalculoCobro"/></a>
<%					}%>
<!-- 				</form> &nbsp; &nbsp; -->
				<%-- <a href="formasPago?PlanId=<%=carga.getPlanId()%>&Cantidad=<%=pagoInicial%>" class="btn btn-success btn-sm"><spring:message code="portal.alumno.pagos.FormasPago"/></a> --%>
				<a href="https://www.pau.ac.pg/payments/pay-tuition-fees/" class="btn btn-success btn-sm" target="_blank"><spring:message code="portal.alumno.pagos.FormasPago"/></a>
<%			} %>				
  			</div>
		</div>
<%		} else {%>
		<div class="alert alert-warning">
			No subjects in load. Wait for staff to assign subjects to your load, or contact your advisor. 
		</div>
<%		}%>		
		<br>
<%	}%>
<% }else{%>
	<div class="alert alert-warning">
		No records found. Wait for the Academic Office to assign your load. 
	</div>
<% }%>
</div>
</body>