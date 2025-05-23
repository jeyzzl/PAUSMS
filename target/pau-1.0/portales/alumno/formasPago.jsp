<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumReferencia"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ include file= "menu.jsp" %>
<html>
<head>
<script type="text/javascript">
	function ActualizarPlan(){  		
  		document.frmPlanes.submit();
	}	
</script>
</head>
<%
	java.text.DecimalFormat getformato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	String cantidad				= request.getParameter("Cantidad")==null?"0":request.getParameter("Cantidad");
	String matricula			= (String) session.getAttribute("codigoAlumno");
	String numCuenta			= (String) request.getAttribute("numCuenta");
	String nombre				= (String) request.getAttribute("nombre");
	String institucion			= (String) request.getAttribute("institucion");
	boolean tieneScotiabank		= (Boolean) request.getAttribute("tieneScotiabank");
	boolean tieneSantander 		= (Boolean) request.getAttribute("tieneSantander");
	boolean tieneBanorte 		= (Boolean) request.getAttribute("tieneBanorte");
	boolean esCovoprom 			= (Boolean) request.getAttribute("esCovoprom");
	String deposito 			= "-";
	
	// Actualización hecha el 26-11-2014, actualmente Scotiabank y Santander utilizan el mismo algoritmo para obtener el digito verificador.
	String cuenta 				= (String) request.getAttribute("cuenta");
	String digito 				= (String) request.getAttribute("digito");	
	AlumReferencia alumno 		= (AlumReferencia) request.getAttribute("alumno");	
	String planId 				= (String) request.getAttribute("planId");
	//System.out.println("Datos:"+cuenta+":"+matricula+":"+digito);
	List<AlumPlan> lisPlanes 					= (List<AlumPlan>) request.getAttribute("lisPlanes");	
	HashMap<String, MapaPlan> mapaPlanes 		= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	
	String nombrePlan = "-"; 	
	if (mapaPlanes.containsKey(planId)){
		nombrePlan = mapaPlanes.get(planId).getNombrePlan();
	}
	
	if (esCovoprom==false){ 
		deposito = institucion+" A.C.";
	}else{
		deposito = "MONTEMORELOS VOCATIONAL AND PROFESSIONAL ASSOCIATION A.C.";
	}
	
	String colorPago = "bg-danger";
	if (Double.valueOf(cantidad) <= 0) colorPago = "bg-success";
%>
<body>
<div class="container-fluid">
	<div class="alert alert-success">
		<a href="pagos"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;
		Student: <b><%=nombre%></b><small class="text-muted"> ( Plan: <%=nombrePlan%> )</small>	
	</div>
	<p>These are the options available to make your tuition payment( <span class="badge <%=colorPago%>" style="font-size:11px"><%=getformato.format(Double.valueOf(cantidad))%></span> ).&nbsp;
	Verify that the data corresponds to the academic plan you wish to enroll in, as the references may be different depending on the plan.</p>
	<hr>
</div>
<div class="container-fluid d-flex justify-content-around">	
	<div class="card bg-light" style="width: 450px;">
		<div class="card-header"><img src="../../imagenes/url.png" width="50px"> Pay Online<small class="text-muted"> ( Best Option ) </small></div>
		<div class="card-body">
			<p style="font-size: 14px">
				<em><u>Comment</u></em>
			</p>
			<p class="card-text">
				Online payment is the best option, since the transaction is immediately reflected in your account balance.
				Click the following link <a href="https://www.um.edu.mx/pagos/pago-en-linea/" target="_blank">https://www.um.edu.mx/pago-en-linea/</a>.
			</p>		
		</div>
		<div class="card-footer bg-transparent border-success d-flex justify-content-start">			
 		</div>
	</div>
</div>	

<%	if(tieneScotiabank){ %>
<div class="container-fluid d-flex justify-content-around mt-2">	
	<div class="card bg-light" style="width: 450px;">
		<div class="card-header"><img src="../../imagenes/logo-scotiabank.png" width="70px"> Wire transfer</div>
		<div class="card-body">
			<p style="font-size: 14px">
				<em><u>Data:</u></em>
			</p>
			<p class="card-text">
				CLABE: <b>044597253000003611</b> &nbsp; Reference: <b><%=cuenta+matricula+alumno.getScotiabank()%></b><br>
				<small class="text-muted"> This transfer is reflected one business day after the deposit date.</small>
			</p>		
		</div>
		<div class="card-footer bg-transparent border-success d-flex justify-content-start">
			<span style="font-size:10px;">&nbsp;</span>
		</div>
	</div>
	<div class="card bg-light" style="width: 450px;">
		<div class="card-header"><img src="../../imagenes/logo-scotiabank.png" width="70px"> Cash Deposit</div>
		<div class="card-body">
			<p style="font-size: 14px">
				<em><u>Data:</u></em>
			</p>
			<p class="card-text">
				Account: <b><%=numCuenta%></b> &nbsp; Reference: <b><%=cuenta+matricula+alumno.getScotiabank()%></b><br>
				<small class="text-muted"> This transfer is reflected one business day after the deposit date.</small>
			</p>		
		</div>
		<div class="card-footer bg-transparent border-success d-flex justify-content-start">
			<span style="font-size:10px;">Name: <b><%=deposito%></b></span>
		</div>
	</div>
</div>	
<%	} %>
<%	if(tieneSantander){%>
<div class="container-fluid d-flex justify-content-around mt-2">	
	<div class="card bg-light" style="width: 450px;">
		<div class="card-header"><img src="../../imagenes/logo-santander.png" width="70px"> Wire transfer</div>
		<div class="card-body">
			<p style="font-size: 14px">
				<em><u>Data:</u></em>
			</p>
			<p class="card-text">
				Agreement: <b>8592</b> &nbsp; CLABE: <b>014597920019501959</b><br>
				Reference: <b><%=cuenta+matricula+alumno.getScotiabank()%></b> Concept: <b>9999</b><br>				
				<small class="text-muted"> This transfer is reflected one business day after the deposit date.</small>
			</p>		
		</div>
		<div class="card-footer bg-transparent border-success d-flex justify-content-start">
			<span style="font-size:10px;">&nbsp;</span>			
 		</div>
	</div>
	<div class="card bg-light" style="width: 450px;">
		<div class="card-header"><img src="../../imagenes/logo-santander.png" width="70px"> Cash Deposit</div>
		<div class="card-body">
			<p style="font-size: 14px">
				<em><u>Data:</u></em>
			</p>
			<p class="card-text">
				Agreement: <b>8592</b> &nbsp; Account: <b>92001950195</b><br>
				Reference: <b><%=cuenta+matricula+alumno.getScotiabank()%></b><br>
				<small class="text-muted"> This transfer is reflected one business day after the deposit date.</small>
			</p>		
		</div>
		<div class="card-footer bg-transparent border-success d-flex justify-content-start">
			<span style="font-size:10px;">Name: <b><%=deposito%></b></span>	
 		</div>
	</div>
</div>
<%	} %>
<%	if(tieneBanorte){%>
<div class="container-fluid d-flex justify-content-around mt-2">	
	<div class="card bg-light" style="width: 450px;">
		<div class="card-header"><img src="../../imagenes/logo-banorte.png" width="70px"> Wire transfer</div>
		<div class="card-body">
			<p style="font-size: 14px">
				<em><u>Data:</u></em>
			</p>
			<p class="card-text">
				<spring:message code="aca.Cuenta"/> <spring:message code="portal.alumno.referencias.Emisora"/>: <b>150239</b> 
				<br><spring:message code="portal.alumno.referencias.ClabeInterbancaria"/>: <b>072597011114057534</b><br>
				<spring:message code="portal.alumno.referencias.Referencia"/>: <b><%=cuenta+matricula+alumno.getScotiabank()%></b><br>				
				<small class="text-muted"> This transfer is reflected one business day after the deposit date.</small>
			</p>		
		</div>
		<div class="card-footer bg-transparent border-success d-flex justify-content-start">
			<span style="font-size:10px;">&nbsp;</span>			
 		</div>
	</div>
	<div class="card bg-light" style="width: 450px;">
		<div class="card-header"><img src="../../imagenes/logo-banorte.png" width="70px"> Cash Deposit</div>
		<div class="card-body">
			<p style="font-size: 14px">
				<em><u>Data:</u></em>
			</p>
			<p class="card-text">
				<spring:message code="aca.Cuenta"/> <spring:message code="portal.alumno.referencias.Emisora"/>: <b>150239</b><br>
				<spring:message code="aca.Cuenta"/>: <b>1111405753</b><br>
				<spring:message code="portal.alumno.referencias.Referencia"/>: <b><%=cuenta+matricula+alumno.getScotiabank()%></b><br>
				<small class="text-muted"> This transfer is reflected one business day after the deposit date.</small>
			</p>		
		</div>
		<div class="card-footer bg-transparent border-success d-flex justify-content-start">
			<span style="font-size:10px;">Name: <b><%=deposito%></b></span>	
 		</div>
	</div>
</div>
<%	} %>
</body>
</html>