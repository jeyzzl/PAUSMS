<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Date"%>
<%@page import="aca.util.Fecha"%>

<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.acceso.spring.AccesoConfirmar"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.carga.spring.CargaGrupo"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.vista.spring.Maestros"%>
<%@page import="aca.financiero.spring.FesCcobro"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.carga.spring.CargaFinanciero"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.alumno.spring.AlumImagen"%>
<%@page import="aca.alumno.spring.AlumEstado"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.carga.spring.CargaPracticaAlumno"%>
<%@page import="aca.bec.spring.BecPuestoAlumno"%>
<%@page import="aca.vista.spring.FinTabla"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.kardex.spring.KrdxCursoAct"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%@ include file="menu.jsp"%>

<head>
<script type="text/javascript">
	function Confirmar(cargaId, bloqueId){
		if (confirm("Do you want to register in this Academic Load with the list of Subjects assigned?")){
			document.location.href = "verMaterias?CargaId=" + cargaId + "&BloqueId=" + bloqueId + "&Confirmar=S";
		}
	}
	
	function ConfirmarBeca(cargaId, bloqueId){
		document.location.href = "verMaterias?CargaId=" + cargaId + "&BloqueId=" + bloqueId + "&ConfirmarBeca=S";
	}

	function NoConfirmar(cargaId, bloqueId){
		if (confirm("Do you want to cancel the confirmation of your Academic Load?")) {
			document.location.href = "verMaterias?CargaId=" + cargaId + "&BloqueId="	+ bloqueId + "&Confirmar=N";
		}
	}

	function CambioPago(cargaId, bloqueId){
		var selectPago = document.getElementById("selectPago").value;
		document.location.href = "verMaterias?CargaId="+cargaId+"&BloqueId="+bloqueId+"&FormaPago="+selectPago;
	}
	
	function RevisarCosto(){
		//var selectPago 		= document.getElementById("selectPago").value;
		document.Calculo.submit();
		//document.location.href = "verMaterias?CargaId=" + cargaId + "&BloqueId="	+ bloqueId + "&FormaPago=" + selectPago + "&RevizarCosto=S";
		
		window.open('verCostos', '_blank');
		location.reload();
	}
	
	function refreshPage(){
		
	}
	
	function AutorizarConvenio(cargaId, bloqueId){
		if (confirm("Do you agree with the disclosed amounts and the terms and conditions?")){
			document.location.href = "verMaterias?CargaId="+cargaId+"&BloqueId="+bloqueId+"&ConfirmarConvenio=S";
		}
	}

	function ConfirmarMater(){
		if (confirm("Do you want to enroll the marked Subjects?")){
			document.ConfirmarMaterias.submit();
		}
	}
</script>
</head>
<body>
<%
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String cargaId 					= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
	String bloqueId 				= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
	String codigoPersonal 			= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 			= (String) session.getAttribute("codigoAlumno");	
	Acceso acceso			 		= (Acceso) request.getAttribute("acceso");
	CargaAlumno cargaAlumno 		= (CargaAlumno) request.getAttribute("cargaAlumno");
	AlumPersonal alumPersonal 		= (AlumPersonal) request.getAttribute("alumPersonal");
	AlumAcademico alumAcademico		= (AlumAcademico) request.getAttribute("alumAcademico");
	AlumImagen alumImagen			= (AlumImagen) request.getAttribute("alumImagen");
	AlumEstado alumEstado			= (AlumEstado) request.getAttribute("alumEstado");
	boolean confirmarModalidad		= (boolean) request.getAttribute("confirmarModalidad");
	boolean tieneBeca				= (boolean) request.getAttribute("tieneBeca");
	String modalidadNombre			= (String) request.getAttribute("modalidadNombre");
	Carga carga 					= (Carga) request.getAttribute("carga");
	FesCcobro cobro 				= (FesCcobro) request.getAttribute("cobro");
	String mensaje 					= (String) request.getAttribute("mensaje");
	BecPuestoAlumno becPuestoAlumno	= (BecPuestoAlumno) request.getAttribute("becPuestoAlumno");
	CargaFinanciero financiero		= (CargaFinanciero)request.getAttribute("financiero");
	boolean existeConvenio 			= (boolean) request.getAttribute("existeConvenio");
	boolean autorizaConvenio 		= (boolean) request.getAttribute("autorizaConvenio");
	boolean esActivo 				= false;
	boolean enLinea 				= false;
	float sumaTotal 				= 0;
	String asistencia				= cargaAlumno.getModo().equals("P")?"Face-to-Face":"Online";
	List<AlumnoCurso> lista 						= (List<AlumnoCurso>) request.getAttribute("lista");	
	List<AccesoConfirmar> lisConfirmar 				= (List<AccesoConfirmar>) request.getAttribute("lisConfirmar");	
	List<CargaPracticaAlumno> lisPracticas 			= (List<CargaPracticaAlumno>) request.getAttribute("lisPracticas");
	
	HashMap<String, CargaGrupo> mapaCargaGrupo 		= (HashMap<String, CargaGrupo>) request.getAttribute("mapaCargaGrupo");
	HashMap<String, String> mapaMaestroCorto 		= (HashMap<String, String>) request.getAttribute("mapaMaestroCorto");
	HashMap<String, CargaBloque> mapaBloquesActivos = (HashMap<String, CargaBloque>) request.getAttribute("mapaBloquesActivos");
	HashMap<String, String> mapaTipoCalCorto 		= (HashMap<String, String>) request.getAttribute("mapaTipoCalCorto");
	HashMap<String, CatModalidad> mapaModalidades 	= (HashMap<String, CatModalidad>) request.getAttribute("mapaModalidades");
	
	HashMap<String, String> mapaLibres 				= (HashMap<String, String>) request.getAttribute("mapaLibres");
	HashMap<String, String> mapaPracticas 			= (HashMap<String, String>) request.getAttribute("mapaPracticas");
	HashMap<String, String> mapaCursosPracticos		= (HashMap<String, String>) request.getAttribute("mapaCursosPracticos");
	HashMap<String,FinTabla> mapaCostos 			= (HashMap<String,FinTabla>)request.getAttribute("mapaCostos");
	MapaPlan mapaPlan								= (MapaPlan)request.getAttribute("mapaPlan");
	HashMap<String,String> mapaConfirmadas			= (HashMap<String,String>)request.getAttribute("mapaConfirmadas");
	HashMap<String,KrdxCursoAct> mapaMateriasAlumno	= (HashMap<String,KrdxCursoAct>)request.getAttribute("mapaMateriasAlumno");
%>
<div class="container-fluid mt-1">
	<div class="alert alert-success">
		<a href="cargas"><i class="fas fa-arrow-left"></i></a>&nbsp;
		Subject List 
		<small class="text-muted">( <b><%=alumPersonal.getCodigoPersonal()%></b> <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoMaterno()+" "+alumPersonal.getApellidoPaterno()%>
		&nbsp;<b>Plan:</b> <%=cargaAlumno.getPlanId()%>&nbsp;&nbsp;&nbsp;<b>Load:</b> <%=carga.getCargaId()%>&nbsp;&nbsp;&nbsp;<b>Modality:</b> <%=modalidadNombre%>&nbsp;&nbsp;&nbsp;<b>Residence:</b> <%=alumAcademico.getResidenciaId().equals("E")?"Day Student":"Boarding Student"%>&nbsp;&nbsp;&nbsp;<b>Location:</b> <%=asistencia%> )</small>
	</div>
<%	if (cargaAlumno.getComentario() != null){ %>
	<%=cargaAlumno.getComentario().equals("-")?"":cargaAlumno.getComentario()%><br>
<%	} %>
<form name="ConfirmarMaterias" action="confirmarMaterias" method="post">
	<input type="hidden" name="CargaId" value="<%=cargaId%>">
	<input type="hidden" name="BloqueId" value="<%=bloqueId%>">

<!-- TABLA DE MATERIAS -->
	<table class="table table-sm table-bordered">
	<thead>
		<tr>
			<th width="2%">#</th>
			<th width="3%"><%=cargaAlumno.getMat().equals("S")?"<span class='badge bg-success text-dark'>Select</span>":"Select"%></th>
			<th width="5%">Semester</th>
			<th width="30%">Subject</th>
			<th >Group</th>
			<th >Modality</th>
			<th >Block</th>
			<th width="20%">Lecturer</th>
			<th >Credits</th>
			<th width="6%">Start</th>
			<th width="6%">End</th>
			<th width="5%">Status</th>
<!-- 			<th width="5%">Cost</th> -->
		</tr>
	</thead>
	<tbody>
<%	
	boolean esLibre 	= false;		
	if (mapaLibres.containsKey( cargaId+bloqueId )){
		esLibre = true;
	}
	
	boolean esPractica	= false;
	if (mapaPracticas.containsKey(cargaId+bloqueId)){
		esPractica = true;
	}
	
	String fechasPracticas = "";
	for (CargaPracticaAlumno practica : lisPracticas){
		if (cargaId.equals(practica.getCargaId()) && bloqueId.equals(practica.getBloqueId())){
			if (fechasPracticas.length()==0 ) 
				fechasPracticas += aca.util.Fecha.getFechaConMesCorto(practica.getFechaIni())+" to "+aca.util.Fecha.getFechaConMesCorto(practica.getFechaFin());
			else
				fechasPracticas += ", "+aca.util.Fecha.getFechaConMesCorto(practica.getFechaIni())+" to "+aca.util.Fecha.getFechaConMesCorto(practica.getFechaFin());
		}
	}
		
	boolean existeConsentimiento = false;
	if (alumImagen.getConsentimiento().equals("S") || alumImagen.getConsentimiento().equals("N")) existeConsentimiento = true;
	
	int row = 0;
	for (AlumnoCurso materia : lista) {
		row++;
	
		String inicio 	= "";
		String fin 		= "";
		String modo		= "";
		if (mapaCargaGrupo.containsKey(materia.getCursoCargaId())) {
			inicio 		= mapaCargaGrupo.get(materia.getCursoCargaId()).getfInicio();
			fin 		= mapaCargaGrupo.get(materia.getCursoCargaId()).getfFinal();
			modo		= mapaCargaGrupo.get(materia.getCursoCargaId()).getModo();			
			if (modo.equals("P")) modo = "Face to Face";
			if (modo.equals("R")) modo = "Mixed";
			if (modo.equals("H")) modo = "Hybrid";
			if (modo.equals("V")) modo = "Online";
		}
	
		String inicioFecha[] = inicio.substring(0, 10).split("-");
		String finFecha[] = fin.substring(0, 10).split("-");

		inicio = inicioFecha[2] + "/" + inicioFecha[1] + "/" + inicioFecha[0];
		fin = finFecha[2] + "/" + finFecha[1] + "/" + finFecha[0];

		String nombreMaestro = "";
		if (mapaMaestroCorto.containsKey(materia.getMaestro())) {
			nombreMaestro = mapaMaestroCorto.get(materia.getMaestro());
		}

		if (mapaBloquesActivos.containsKey(materia.getCargaId() + materia.getBloqueId())) {
			esActivo = true;
		}
		String estado = "";
		if (mapaTipoCalCorto.containsKey(materia.getTipoCalId())) {
			estado = mapaTipoCalCorto.get(materia.getTipoCalId());
		}

		if (mapaModalidades.containsKey(materia.getModalidadId().trim())) {
			if(mapaModalidades.get(materia.getModalidadId().trim()).getEnLinea().equals("S")){
				enLinea = true;
			 	existeConsentimiento = true;
			}
		}
		
		String practica = "<span class='badge bg-dark' style='font-size:12px;'>NO</span>";
		if (mapaCursosPracticos.containsKey(materia.getCursoId())){
			practica = "<span class='badge bg-success' style='font-size:12px;'>YES</span>";
		}
		
		boolean confirmada = false;
		if(mapaConfirmadas.containsKey(materia.getCodigoPersonal()+materia.getCursoCargaId())){
			confirmada = true;			
		}
		
		String prefijoCarrera 	= mapaPlan.getVersionId().equals("3")?"PD":"";
		
		float costoTotal 	= 0;
		String costo 		= "0";
		String porCredito	= "0";
		String porcentaje 	= "0";
		float precio 		= 0;
		float cantidad		= 0;
		float frecuencia	= 0;
		if ( mapaPlan.getPrecio().equals("S")){
			if (mapaMateriasAlumno.containsKey(materia.getCursoCargaId())){
				cantidad 		= Float.valueOf(mapaMateriasAlumno.get(materia.getCursoCargaId()).getCantidad());
				frecuencia 		= Float.valueOf(mapaMateriasAlumno.get(materia.getCursoCargaId()).getFrecuencia());
			}
			if (mapaCargaGrupo.containsKey(materia.getCursoCargaId())){
				precio 	= Float.valueOf( mapaCargaGrupo.get(materia.getCursoCargaId()).getPrecio() );
			}
			costoTotal = precio*frecuencia*cantidad;
			sumaTotal += costoTotal;
		}else if (mapaCostos.containsKey(carga.getCargaId()+prefijoCarrera+materia.getCarreraId()+materia.getModalidadId())){
			if(alumAcademico.getClasFin().equals("1")){				
				costo = mapaCostos.get(carga.getCargaId()+prefijoCarrera+materia.getCarreraId()+materia.getModalidadId()).getAcfe();
				porCredito	= mapaCostos.get(carga.getCargaId()+prefijoCarrera+materia.getCarreraId()+materia.getModalidadId()).getPorCredito();
				porcentaje = mapaCostos.get(carga.getCargaId()+prefijoCarrera+materia.getCarreraId()+materia.getModalidadId()).getPorCredito();
				precio = Float.valueOf(costo) * Float.valueOf(materia.getCreditos()) * Float.valueOf(porcentaje);
				costoTotal += precio;
				
				if(confirmada){
					sumaTotal += costoTotal;
				}
			}else{
				
				costo 		= mapaCostos.get(carga.getCargaId()+prefijoCarrera+materia.getCarreraId()+materia.getModalidadId()).getNoAcfe();
				porCredito	= mapaCostos.get(carga.getCargaId()+prefijoCarrera+materia.getCarreraId()+materia.getModalidadId()).getPorCredito();				
				precio = Float.valueOf(costo) * Float.valueOf(porCredito) * Float.valueOf(materia.getCreditos());
				costoTotal += precio;
				
				if(confirmada){
					sumaTotal += costoTotal;
				}
			}
		}
		
		String creditos = "0";
		if(materia.getCreditos().trim().equals(".00")){
			creditos = creditos+materia.getCreditos().trim();
		}else{
			creditos = materia.getCreditos().trim();
		}
%>
		<tr>
			<td><%=row%></td>
			<td><input type="checkbox" name="<%=materia.getCodigoPersonal()+materia.getCursoCargaId()%>" <%=confirmada ? "checked":""%>></td>			
			<td><span class='badge bg-dark' style='font-size:12px;'><%=materia.getCiclo()%></span></td>
			<td><%=materia.getNombreCurso()%></td>
			<td>
<%			if(materia.getGrupo().contains("*")){ %>
				<span title="Subject offered only once yearly" class='badge bg-warning' style='font-size:12px;'><%=materia.getGrupo()%></span>
<%			}else if(materia.getGrupo().contains("+")){ %>
				<span title="Subject expands for two consecutive blocks" class='badge bg-primary' style='font-size:12px;'><%=materia.getGrupo()%></span>
<%			}else if(materia.getGrupo().equals("AO") || materia.getGrupo().equals("EM") || materia.getGrupo().equals("MV")){ %>
				<span title="Subject expands for two consecutive blocks" class='badge bg-primary' style='font-size:12px;'><%=materia.getGrupo()%></span>				
<%			}else{%>
				<%=materia.getGrupo()%>
<%			} %>
			</td>
			<td><%=modo%></td>
			<td><%=materia.getBloqueId()%></td>
			<td><%=nombreMaestro%></td>
			<td><%=creditos%></td>
			<td><%=inicio%></td>
			<td><%=fin%></td>
			<td><%=estado%></td>
<%-- 			<td style="text-align:center">$<%=formato.format(costoTotal)%></td> --%>
		</tr>
<%	} %>
<%	   	if(sumaTotal > 0){ %>
		<tr>
			<td colspan="12"></td>
			<td style="text-align:center">$<%=formato.format(sumaTotal)%></td>
		</tr>
<%		} %>
	</tbody>
	</table>	
</form>

<!-- MENSAJES DE MATERIAS -->

<% 	if (esLibre == false && esPractica == true){%>	
	<div class="alert alert-danger">You need to present your practicum during the following dates: <%=fechasPracticas%> </div>
<% 	}else if (esLibre==true){%>
	<div class="alert alert-danger">You have a Practicum to be completed. You aren't required On Site</div>
<%	}%>
	<div class="alert alert-success d-flex justify-content-center">	
<%
	if(!financiero.getComentario().equals("-")){
%>
		<%=financiero.getComentario() %>
<%
	}
%>
	</div>

<!-- PASOS PARA CONFIRMAR CARGA -->
<%
	//ESTA TODO EN VERDADERO POR EL MOMENTO HASTA INDICACIONES POR PAPUA EN COMO PROCEDER CON LOS COSTOS
	boolean paso1 = false;
	boolean paso2 = false;
	boolean paso3 = false;	 
%>	
	<div class="alert alert-info">
<%		if (alumEstado.getEstado().equals("I")){ %>	
		<h5>You are registered!</h5>
<%		}else{ %>
		<h5>You must <i>Confirm</i> your Academic load in order to continue:</h5>	
<%-- <%		} %>		 --%>
	</div>

<!-- CARDS -->
	
	<div class="container-fluid d-flex justify-content-around mt-1">
		<div class="row row-cols-1 row-cols-md-2">
		<!-- CONSENTIMIENTO DE IMAGEN -->
 			<div class="card bg-light mb-3 p-0" style="max-width: 20rem;">
 				<div class="card-header">
 					<h5>
 						<span class="badge rounded-pill bg-dark">1</span> Declaration
<%	if (alumImagen.getConsentimiento().equals("S") || alumImagen.getConsentimiento().equals("N") || enLinea) { 
		paso1 = true;
%>
						<span class="badge rounded-pill bg-success"><i class="fas fa-check"></i></span>
<%	} %>
 					</h5>
 				</div> 
 				<div class="card-body"> 
 					<p style="font-size: 14px"> 
 						<em><u>Instructions</u></em> 
 					</p> 
 					<p class="card-text">Click on the <i>Declaration Form</i> button to read the declaration. Accept the terms and conditions to continue</p> 
 				</div> 
 				<div class="card-footer bg-transparent border-success"> 
 					<a class="btn btn-primary btn-sm" data-bs-toggle="collapse" href="#collapseConsentimiento" role="button" aria-expanded="false" aria-controls="collapseConsentimiento"> 
 						Declaration form 
 					</a>&nbsp; 
 				</div> 
 			</div> 
		<!-- CALCULO DE COBRO -->
			<div class="card bg-light mb-3 mx-2 p-0" style="max-width: 20rem;">
				<div class="card-header">
					<h5>
						<span class="badge rounded-pill bg-dark">2</span> Registration Invoice 
<%	
	if(cargaAlumno.getCalculo().equals("S")){ 
		paso2 = true; 
%>
						<span class="badge rounded-pill bg-success"><i class="fas fa-check"></i></span>
<%	} %>
					</h5>
				</div>
				<div class="card-body">
					<p style="font-size: 14px">
						<em><u>Instructions</u></em>
					</p>
					<p class="card-text">Click the <i><%=paso2?"Review button to download your registration invoice.":"Download button to complete this step."%></i></p>
				</div>
				<div class="card-footer bg-transparent border-success">
					<form name="Calculo" method="post" style="margin-bottom: -5px;" target="_blank" action="verCostos">
					<input type="hidden" name="CargaId" value="<%=cargaId%>">
					<input type="hidden" name="BloqueId" value="<%=bloqueId%>">
					<div class="row">
				<%-- <% 	if (cobro.getInscrito().equals("N")) {%>					 --%>
<!-- 						<div class="col-sm-6">					 -->
<%-- 							<select id="selectPago" class="form-select" onchange="javascript:CambioPago('<%=cargaId%>','<%=bloqueId%>')" style="width: 120px;"> --%>
<%-- 								<option value="C" <%=cobro.getFormaPago().equals("C") ? "selected" : ""%>>One-time</option> --%>
<%-- 								<option value="P" <%=cobro.getFormaPago().equals("P") ? "selected" : ""%>>Installments</option> --%>
<!-- 							</select>					 -->
<!-- 						</div> -->
				<%-- <%	} %>		 --%>
						<div class="col-sm-6">
<%				
				if (financiero.getStatus().equals("A")){ %>
					<%-- out.print("<a class='btn btn-success btn-sm' style='color:white' href='https://virtual-um.um.edu.mx/financiero/calculoCobroReimpresion.html?txtMatricula="
					+ cobro.getMatricula() + "&txtCarga=" + cobro.getCargaId() + "&Bloque=" + cobro.getBloque()	+ "' target='_blank'>View Breakdown</a>");
					out.print("<a class='btn btn-success btn-sm' style='color:white' href='#'>View Breakdown</a>"); --%>
							<a class="btn btn-primary btn-sm mb-1" onClick="javascript:RevisarCosto()" style="color: white">Download</a>
<%				}else if(financiero.getStatus().equals("I")){
%>
							<%-- <a class="btn btn-primary btn-sm mb-1" onclick="javascript:RevisarCosto('<%=0%>','<%=0%>');" style="color: white"><i class="fas fa-file-invoice-dollar"></i> Breakdown</a> --%>
							<a class="btn btn-primary btn-sm mb-1" onClick="javascript:RevisarCosto()" style="color: white"><i class="fas fa-dollar-sign"></i> Review</a>
<%				} %>							
						</div>
					</div>
					</form>
				</div>
			</div>
		<!-- BECAS -->
			
<%-- <%	// if(tieneBeca==false){ 
%>		 --%>
			<%-- <div class="card bg-light mb-3" style="max-width: 21rem;">
				<div class="card-header">
					<h5>
						<span class="badge rounded-pill bg-dark">3</span> Scholarships  --%>
<%-- <%		
		if(cargaAlumno.getBeca().equals("S") || confirmarModalidad || mapaPlan.getPlanId().equals("CONS2018") || mapaPlan.getPlanId().equals("CONS2019") || mapaPlan.getPlanId().equals("IAEN2012")){ 
			paso3 = true; 
%> --%>
						<%-- <span class="badge rounded-pill bg-success"><i class="fas fa-check"></i></span> --%>
<%-- <%		} %> --%>
					<%-- </h5>
				</div>
				<div class="card-body">
					<p style="font-size: 14px">
						<em><u>Instructions</u></em>
					</p> --%>
		<%-- <%		if(cargaAlumno.getBeca().equals("N")){ %>			 --%>
					<%-- <p class="card-text">You do not have any scholarship assigned for this Load. If you wish to continue without requesting a scholarship service click on Accept.</p> --%>
		<%-- <% 		}else{%>			 --%>
					<%-- <p class="card-text">You are registered to enroll in this period without scholarship service, if you are not yet enrolled you can apply for a scholarship by clicking the green buttons.</p> --%>
		<%-- <%		} %>			 --%>
				<%-- </div>
				<div class="card-footer bg-transparent border-success"> --%>
		<%-- <% 	if ((codigoPersonal.equals(codigoAlumno) && cargaAlumno.getBeca().equals("N") ) || enLinea || codigoPersonal.equals("1120329") ){%>		 --%>
					<%-- <a class="btn btn-primary btn-sm" style="color: white" onclick="javascript:ConfirmarBeca('<%=cobro.getCargaId()%>','<%=cobro.getBloque()%>');">Accept</a> --%>
		<%-- <% 	}%>				 --%>
					<%-- <a class="btn btn-success btn-sm" style="color: white" href="plazas" target="_blank"><i class="fas fa-search"></i> Scholarships</a>
				</div>
			</div> --%>
<%	
	//} 
%>					
<%-- <%	if(tieneBeca){ %>			
			<div class="card bg-light mb-3" style="max-width: 21rem;">
				<div class="card-header">
					<h5>
						<span class="badge rounded-pill bg-dark">3</span> Scholarships
<%		if(autorizaConvenio || mapaPlan.getPlanId().equals("CONS2018") || mapaPlan.getPlanId().equals("CONS2019") || mapaPlan.getPlanId().equals("IAEN2012")){ 
			paso3 = true;
%>
						<span class="badge rounded-pill bg-success"><i class="fas fa-check"></i></span>
<%		} %>
					</h5>
				</div>
				<div class="card-body">
					<p style="font-size: 14px">
						<em><u>Instructions</u></em>
					</p>
<%		if(becPuestoAlumno.getEstado().equals("P")){%>
					<p class="card-text">Your scholarship service is pre-contracted, go to your boss to be formally recruited.</p>
<%		}else {%>
<% 			if (existeConvenio){%>
					<p class="card-text">If you are already registered with a department, download, review and authorize your scholarship service agreement.</p>
<%			}else {%>
					<p class="card-text">Contact your manager to generate the PDF of your contract so you can download it.</p>
<%			} %>					
<%		} %>					
				</div>
				<div class="card-footer bg-transparent border-success">
<% 		if (existeConvenio){%>
					<a href="descargarConvenio?CodigoAlumno=<%=codigoAlumno%>&Puesto=<%=becPuestoAlumno.getPuestoId()%>" style="color:green;"><i class="fas fa-download fa-1x"></i> Download</a>&nbsp; &nbsp;
<% 			if (codigoPersonal.equals(codigoAlumno) && autorizaConvenio==false || codigoPersonal.equals("1120329")){%>									
					<a class="btn btn-primary btn-sm" style="color: white" onclick="javascript:AutorizarConvenio('<%=cobro.getCargaId()%>','<%=cobro.getBloque()%>');">Accept</a>
<%			} %>					
<%		} else{ %>		
				<br>
<%		} %>					
				</div>
			</div>
<%	} %> --%>
 
		<!-- CONFIRMAR LA CARGA -->
<%	
	boolean botonConfirmar 		= false;
	boolean botonDesconfirmar 	= false;
	String mensajeConfirmar = "-";
	//System.out.println("Datos"+codigoPersonal.equals(codigoAlumno)+":"+paso1+":"+paso2+":"+paso3+":"+cargaAlumno.getConfirmar()+":"+cobro.getInscrito());
	// if (( (codigoPersonal.equals(codigoAlumno) || confirmarModalidad) && paso1 && paso2 && paso3 && cargaAlumno.getConfirmar().equals("N") && cobro.getInscrito().equals("N")) ) {  ** TOMA EN CUENTA PASO 3 ()
	if (( (codigoPersonal.equals(codigoAlumno) || confirmarModalidad) && paso1 && paso2 && cargaAlumno.getConfirmar().equals("N") ) ) {
		mensajeConfirmar = "By clicking <i>Confirm Academic Load</i> you accept the subjects assigned and you agree to the terms in the Declaration form. Once confirmed staff will complete your registration";
		botonConfirmar = true;
	} else if ((codigoPersonal.equals(codigoAlumno) || confirmarModalidad) && cargaAlumno.getConfirmar().equals("S")) {
		botonDesconfirmar = true;
		mensajeConfirmar = "If you want to change the number of subjects or make adjustments that change the cost of the academic Load, click on the button below.";
	}else if (cobro.getInscrito().equals("S")){
		mensajeConfirmar = "You are already enrolled in this academic Load";
	}else if (paso1==false || paso2==false || paso3==false){
		mensajeConfirmar = "The first three steps must be completed to confirm the Load.";
	}else if (!codigoPersonal.equals(codigoAlumno)){
		mensajeConfirmar = "Confirmation can only be done by logging in with the student's account.";
	}
%>
			<div class="card bg-light mb-3 p-0" style="max-width: 20rem;">
				<div class="card-header">
					<h5>
						<span class="badge rounded-pill bg-dark">2</span> Confirm
<%		if (cargaAlumno.getConfirmar().equals("S")){ %>
						<span class="badge rounded-pill bg-success"><i class="fas fa-check"></i></span>
<%		} %>
					</h5>
				</div>
				<div class="card-body">
					<p style="font-size: 14px">
						<em><u>Instructions</u></em>
					</p>
					<p class="card-text"><%=mensajeConfirmar%></p>
				</div>
				<div class="card-footer bg-transparent border-success">
<%		if (botonConfirmar){ %>
					<a class="btn btn-primary btn-sm" style="color: white" onclick="javascript:Confirmar('<%=cobro.getCargaId()%>','<%=cobro.getBloque()%>');">Confirm Subjects and Fees</a>&nbsp;					
<% 		}else if (botonDesconfirmar){%>
					<a class="btn btn-danger btn-sm" style="color: white" onclick="javascript:NoConfirmar('<%=cobro.getCargaId()%>','<%=cobro.getBloque()%>');">Do NOT Confirm</a>&nbsp;
<%		}else if (cobro.getInscrito().equals("S")){ %>
				<h6>Confirmed!</h6>					
<%		}else if (paso1==false || paso2==false || paso3==false){%>
				<small>Accept the Delcaration and review your Financial Clearance to continue.</small>
<%		}%>
				</div>
			</div>

		</div>
	</div> 
		<!-- fin de CARDS -->
		<!-- COLLAPSE DE CONSENTIMIENTO DE IMAGEN -->
 	<div class="container-fluid">	
	<br>
 		<div class="collapse" id="collapseConsentimiento">
 			<h3>STUDENT DECLARATION</h3>
 			<p>Recognizing that Pacific Adventist University is an institution of higher learning based on a Biblical worldview, I acknowledge and accept:</p>
			<ol>
				<li>That Christian principles as understood and practised by the Seventh-day Adventist Church for the basis for acceptable behaviour for all members of the Pacific Adventist University family</li>
				<li>That self-discipline is the best form of discipline, </li>
				<li>That the University Administration has the responsibility to maintain a safe and wholesome Christian environment, in which the rights of others are treated with respect,</li>
				<li>That certain kinds of behaviour such as those listed as offences under the PAU Code of Conduct are degrading to the perpetrator(s), the victim(s), and the community at large, and bring the University into disrepute.</li>
				<li>That such behaviour is evidence of a breakdown in self-discipline and may result in immediate dismissal.</li>
			</ol>
			<p>As a student of Pacific Adventist University, I therefore pledge myself to willingly and wholeheartedly accept, support, and uphold the Student Handbook 2025 of all published or announced regulations and standards of Pacific Adventist University. I will do everything within my power to structure my life in accordance with the principles upon which this institution is founded and will be committed to encourage my fellow students to do likewise</p>

<%	if ((!alumImagen.getConsentimiento().equals("S") && !alumImagen.getConsentimiento().equals("N")) && !enLinea && (codigoPersonal.equals(codigoAlumno) || confirmarModalidad )) { %>
 			<a class="btn btn-primary" href="aceptoConcentimiento?Ac=1&CargaId=<%=cargaId%>&BloqueId=<%=bloqueId%>">Accept Declaration</a>&nbsp; 
 			<a class="btn btn-danger" href="aceptoConcentimiento?Ac=2&CargaId=<%=cargaId%>&BloqueId=<%=bloqueId%>">Reject Declaration</a>&nbsp;
 <%		}%>	
			<a class="btn btn-warning" target="_blank" href="studentHandbook.pdf">Student Handbook</a>
 		</div>
 	</div>
	<br>
<%
	if (cargaAlumno.getModo().equals("P")) {
		out.print("<div class='alert alert-info'>You are registered to attend in person.");
	}else{
		out.print("<div class='alert alert-info'>You are registered to take all your classes virtually.");
	}
%>
<%		} %>
</div>
</body>