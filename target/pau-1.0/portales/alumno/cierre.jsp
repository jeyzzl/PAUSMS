<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumEstado"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.carga.spring.CargaFinanciero"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.financiero.spring.FesCcobro"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.carga.spring.CargaPracticaAlumno"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%@ include file="../alumno/menu.jsp"%>

<head>
<style type="text/css">
	.fondoFrente{
  		position:relative;
  		height: 250px;
  		/*width: 70px;*/
  		z-index: 1;
	}			
	.fondoAtras{
  		position:relative;
  		height: 250px;
  		z-index: 1;
	}	
	.Practicas{
 		position:absolute;  		
  		top: 95px;
  		left: 47px;
  		z-index: 2;
	}	
	.foto{
 		position:relative;
  		height:100px;
  		/*width:100px;*/
  		/* top: 112px;
  		left: 21px; */
  		z-index: 2;
	}		
	.nombre{
 		position:relative;
  		top: 10px;
  		left: 10px;
  		z-index: 2;
	}	
	.apellido{
 		position:relative; 
  		top: 0px;
  		left: 10px;
  		z-index: 2;
	}		
	.carreraUno{
 		position:relative;
  		top: 0px;
  		left: 10px;
  		z-index: 2;
	}	
	.carreraDos{
 		position:relative;
  		top: 0px;
  		left: 10px;
  		z-index: 2;
	}	
	.matricula{
 		position:relative;
  		top: 0px;
  		left: 10px;
  		z-index: 2;
	}	

</style>
</head>

<script type="text/javascript">

	function RevisarCosto(cargaId, bloqueId){
		//var selectPago 		= document.getElementById("selectPago").value;
		//document.Calculo.submit();
		//document.location.href = "verMaterias?CargaId=" + cargaId + "&BloqueId="	+ bloqueId + "&FormaPago=" + selectPago + "&RevizarCosto=S";
		
		window.open('verCostos', '_blank');
		location.reload();
	}

</script>
<%
	java.text.DecimalFormat getFormato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String matricula			= (String) session.getAttribute("codigoAlumno");
	String esEnLinea				= (String) request.getAttribute("esEnLinea");
	AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");
	
	List<CargaAlumno> lisCargas					= (List<CargaAlumno>) request.getAttribute("lisCargas");	
	List<CargaPracticaAlumno> lisPracticas		= (List<CargaPracticaAlumno>) request.getAttribute("lisPracticas");
	
	HashMap<String,MapaPlan> mapaPlanes			= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
// 	HashMap<String, FesCcobro> mapaFesCobro		= (HashMap<String, FesCcobro>)  request.getAttribute("mapaFesCobro");
	HashMap<String, AlumEstado> mapaAlumEstado	= (HashMap<String, AlumEstado>)request.getAttribute("mapaAlumEstado");
	HashMap<String, Carga> mapaCargas	 		= (HashMap<String, Carga>)  request.getAttribute("mapaCargas");
	HashMap<String, CargaBloque> mapaBloques	= (HashMap<String, CargaBloque>)  request.getAttribute("mapaBloques");
	// HashMap<String, String> mapaPagos			= (HashMap<String, String>)  request.getAttribute("mapaPagos");
	HashMap<String, String> mapaCodigo			= (HashMap<String, String>)  request.getAttribute("mapaCodigo");	
	HashMap<String, String> mapaLibres			= (HashMap<String, String>)  request.getAttribute("mapaLibres");
	HashMap<String, String> mapaPracticas		= (HashMap<String, String>)  request.getAttribute("mapaPracticas");	
	HashMap<String, CargaFinanciero> mapaCargaFinanciero		= (HashMap<String, CargaFinanciero>)  request.getAttribute("mapaCargaFinanciero");	

	String cargasConCartas						= (String) request.getAttribute("cargasConCartas");	
%>
<div class="container-fluid mt-1">
	<div class="alert alert-success">
		<a href="pasos"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;
		<span class="badge rounded-pill bg-dark">4</span>  <spring:message code="portal.alumno.cierre.CierreInscripcion"/>
		<small class="text-muted">( <%=alumPersonal.getCodigoPersonal()%> - <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%> )</small>
	</div>
<% if(lisCargas.size() >= 1 && lisCargas != null){%>
	<hr>	
<%	
	for (CargaAlumno carga : lisCargas) {	
		
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
		
		String bloqueNombre = "";	
		if (mapaBloques.containsKey(carga.getCargaId()+carga.getBloqueId())) {
			bloqueNombre 	= mapaBloques.get(carga.getCargaId()+carga.getBloqueId()).getNombreBloque();
		}
		
// 		FesCcobro cobro 	= new FesCcobro();
		String inscrito 	= "N";
// 		if (mapaFesCobro.containsKey(carga.getCargaId()+carga.getBloqueId())){
// 			cobro 			= mapaFesCobro.get(carga.getCargaId()+carga.getBloqueId());
// 			inscrito 		= cobro.getInscrito();
// 		}	
		if(mapaAlumEstado.containsKey(carga.getCargaId()+carga.getBloqueId())) {
			inscrito 		= mapaAlumEstado.get(carga.getCargaId()+carga.getBloqueId()).getEstado();
		}
		
		
		// Separa el nombre de la carrera en dos 
		String carreraSplit [] = carreraSe.split(" ");
		String carreraUno	= "";
		String carreraDos	= "";
		int size 	= 0;		 
		for (String temp : carreraSplit){
			size = size + temp.length();
			if (size<30){
				carreraUno 	+= temp + " "; 
			}else{
				carreraDos	+= temp + " ";
			}
		}
		
		double pagoInicial = 0;
		// if (mapaPagos.containsKey(carga.getCargaId()+carga.getBloqueId())) {
		// 	pagoInicial 	= Double.valueOf(mapaPagos.get(carga.getCargaId()+carga.getBloqueId()));
		// 	if (pagoInicial > 0) pagoInicial = 0;
		// }		
		
		String colorPago	= "<span class='badge bg-danger'>";
		String colorBoton	= "btn-danger";
		String mensaje 		= "-";		
		if (pagoInicial >= 0){
			colorPago 		= "<span class='badge bg-success'>";
			colorBoton		= "btn-success";
			if (inscrito.equals("I")){
// 				if (carga.getCargaId().equals("20211A")||carga.getCargaId().equals("20212A") ||carga.getCargaId().equals("20214A")||carga.getCargaId().equals("20215A")){
// 					mensaje 	= "You are enrolled! Download your proof of studies by clicking the icon <i class='fas fa-file-pdf'></i>";
// 				}else{
// 					if (esEnLinea.equals("S")){
// 					mensaje 	= "You are enrolled!";							
// 					}else{			
// 					mensaje 	= "You are enrolled! Visit your schoolar services offices to re-stamp your ID";
// 					} 						
// 				}
				mensaje = "You are registered!";
			}else{
				mensaje 	= "The Academic Office will complete your registration. Contact your advisor if you have any questions.";
			}
		}
// 		else if (cobro.getInscrito().equals("N")){
// 			mensaje 	= "Your tuition breakdown shows an outstanding balance of "+getFormato.format(pagoInicial)+". "+
// 						"If you have already made your initial payment, update your tuition breakdown by clicking on the red button!";
// 		}		
		
		String codigo = "";
		if (mapaCodigo.containsKey(carga.getCargaId()+carga.getBloqueId())) {
			codigo 	= mapaCodigo.get(carga.getCargaId()+carga.getBloqueId());
		}
		
		boolean esLibre 	= false;		
		if (mapaLibres.containsKey(carga.getCargaId()+carga.getBloqueId())){
			esLibre = true;
		}
		
		boolean esPractica	= false;
		if (mapaPracticas.containsKey(carga.getCargaId()+carga.getBloqueId())){
			esPractica = true;
		}
		
		String fechasPracticas = "";
		for (CargaPracticaAlumno practica : lisPracticas){
			if (carga.getCargaId().equals(practica.getCargaId()) && carga.getBloqueId().equals(practica.getBloqueId())){
				if (fechasPracticas.length()==0 ) 
					fechasPracticas += aca.util.Fecha.getFechaConMesCorto(practica.getFechaIni())+" to "+aca.util.Fecha.getFechaConMesCorto(practica.getFechaFin());
				else
					fechasPracticas += ", "+aca.util.Fecha.getFechaConMesCorto(practica.getFechaIni())+" to "+aca.util.Fecha.getFechaConMesCorto(practica.getFechaFin());
			}
		}

		String financieroEstado = "";
		if(mapaCargaFinanciero.containsKey(matricula+carga.getCargaId())){
			financieroEstado = mapaCargaFinanciero.get(matricula+carga.getCargaId()).getStatus();
		}
		//System.out.println("Datos:"+esLibre+":"+esPractica+":"+carga.getCargaId()+carga.getBloqueId()+":"+fechasPracticas);
%>
		<div class="card bg-light" style="max-width: 40rem;">
			<div class="card-header"><%=carga.getCargaId()%> - <%=cargaNombre%><small class="text-muted"> ( <spring:message code="portal.alumno.cierre.Bloque"/>: <%=carga.getBloqueId()%> - <%=bloqueNombre%> )</small></div>
			<div class="card-body" style="position: relative; left: 0; top: 0;">
				<p class="card-Title"><b><%=carga.getPlanId()%></b> - <%=carreraSe%></p>					
				<!-- <img src="../../imagenes/Alumno-Frente.jpg" class="fondoFrente"> -->
<%			if (esLibre ==false && esPractica == true){ %>
				<span class="Practicas" style="font-size:11px; color:green"><spring:message code="portal.alumno.cierre.PrActicasUM"/></span>
<%			} %>				
				
<%-- <% 			if( (inscrito.equals("I") &&  cargasConCartas.contains(carga.getCargaId()) ) || codigoPersonal.equals("9800308") ){ %> 				
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="constanciaPdf?CargaId=<%=carga.getCargaId()%>&BloqueId=<%=carga.getBloqueId()%>" ><i class="fas fa-file-pdf fa-5x"></i></a>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<img src="credeAlumnoQr?Codigo=<%=codigo%>" style="width: 220px; height: 220px; background: white; text-align: center;"/>				
<%			} %> --%>
				<div class="d-flex">
				<img src="../../foto?Codigo=<%=carga.getCodigoPersonal()%>" class="foto">
				<div>
				<p class="nombre" style="font-size:20px; color:black;"><%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%></p>
				<p class="matricula" style="font-size:15px; color:black; margin-bottom: 0;"><%=carga.getCodigoPersonal()%></p>
				</div>
				
				</div>
				
				
				<br>
				<small class="text-muted"><%=mensaje%></small>
<%			if (esLibre ==false && esPractica == true){ %>		
				<small class="text-muted"><p style="font-size:10px; color:green"> <spring:message code="portal.alumno.cierre.MensajeUno"/>: <%=fechasPracticas%>!</p></small>
<%			} %>			
			</div>			
			<div class="card-footer bg-transparent border-success d-flex justify-content-start">
					<%-- <button class="btn btn-sm <%=colorBoton%>" onclick="javascript:RevisarCosto('<%=0%>','<%=0%>');" style="color:white"><spring:message code="portal.alumno.cierre.CalculoCobro"/></button> --%>
<%			if(financieroEstado.equals("A")){%>
					<button class="btn btn-sm me-3<%=colorBoton%>" onclick="" style="color:white"><spring:message code="portal.alumno.cierre.CalculoCobro"/></button>
<%			}%>
<%
			if(inscrito.equals("I")){ 
				out.print("Enrolled!"); 
			}
// 			else if (pagoInicial < 0 ){ 
// 				out.print("Pending payment"); 
// 			}
			else if (carga.getConfirmar().equals("S")){
%>
				<span class="badge bg-warning p-2 text-dark">Closing registration</span>
<%			}else{
%>
			<span class="badge bg-danger p-2">Review and confirm your Academic Load in Step 2</span>
<%			}
%>		
			</div>
		</div>
		<br>
<%			
	}
%>	
<% }else{%>
	<div class="alert alert-warning">
		No records found. Wait for the Academic Office to assign your load. 
	</div>
<% }%>
</div>
</body>