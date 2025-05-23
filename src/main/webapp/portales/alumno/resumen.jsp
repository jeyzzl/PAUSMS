<%@page import="aca.graduacion.spring.AlumEgreso"%>
<%@page import="java.util.Date,java.text.SimpleDateFormat,java.text.DecimalFormat"%>
<%@page import="aca.archivos.ArchivoVO"%>
<%@page import="aca.candado.CandAlumnoUtil"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.Inscritos"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.financiero.spring.FinSaldo"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.graduacion.spring.AlumEvento"%>
<%@page import="aca.voto.spring.VotoEvento"%>
<%@page import="aca.edo.spring.Edo"%>
<%@page import="aca.candado.spring.CandAlumno"%>
<%@page import="aca.candado.spring.CandTipo"%>
<%@page import="aca.matricula.spring.MatEvento"%>
<%@page import="aca.pg.archivo.spring.PosFotoAlum"%>
<%@page import="aca.encuesta.spring.EncPeriodo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%	
	session.setAttribute("cancelaPortal", "N");
	String codigoSesion 			= (String) session.getAttribute("codigoLogeado");
	String codigoPersonal 			= (String) session.getAttribute("codigoPersonal");	
	String codigoAlumno 			= (String) session.getAttribute("codigoAlumno");
	
	String mensajeBloqueo 			= (String) request.getAttribute("mensaje");
	String mensajeEnc 				= (String) request.getAttribute("mensajeEnc");
	AlumPersonal alumno 			= (AlumPersonal) request.getAttribute("alumno");
	AlumAcademico academico			= (AlumAcademico) request.getAttribute("academico");
	AlumPlan plan					= (AlumPlan) request.getAttribute("plan");
	AlumEgreso alumEgreso			= (AlumEgreso) request.getAttribute("alumEgreso");
	EncPeriodo encPeriodo			= (EncPeriodo) request.getAttribute("encPeriodo");
	PosFotoAlum foto				= (PosFotoAlum) request.getAttribute("foto");
	Edo edo							= (Edo) request.getAttribute("edo");
	boolean extranjero	 			= (boolean) request.getAttribute("extranjero");	
	boolean esInscrito 				= (boolean) request.getAttribute("esInscrito");
	boolean contestoMentoria 		= (boolean) request.getAttribute("contestoMentoria");
	boolean evaluaDocente			= (boolean) request.getAttribute("evaluaDocente");
	boolean conCandados 			= (boolean) request.getAttribute("conCandados");	
	boolean existeCovid				= (boolean) request.getAttribute("existeCovid");
	boolean fotoAprobada			= (boolean) request.getAttribute("fotoAprobada");
	boolean fotoEnviada				= (boolean) request.getAttribute("fotoEnviada");
	boolean permisoEvento			= (boolean) request.getAttribute("permisoEvento");
	boolean noPermisoEvento			= (boolean) request.getAttribute("noPermisoEvento");	
	boolean asuntos					= true;
	boolean graduacionPendiente		= (boolean) request.getAttribute("graduacionPendiente");
	boolean contestoEncuesta		= (boolean) request.getAttribute("contestoEncuesta");
	int tienePlan 				    = (int) request.getAttribute("tienePlan");
	int invitacion 				    = (int) request.getAttribute("invitacion");	
	String venceFM3	 				= (String) request.getAttribute("venceFM3");
	String carreraAlumno	 		= (String) request.getAttribute("carreraAlumno");
	String facultad	 				= (String) request.getAttribute("facultad");
	String autorizado				= (String) request.getAttribute("autorizado");
	String mentorNombre				= (String) request.getAttribute("mentorNombre");
	String mensajeFoto				= (String) request.getAttribute("mensajeFoto");
	String colportorId				= (String) request.getAttribute("colportorId");
	String modalidadNombre			= (String) request.getAttribute("modalidadNombre");
	String esEnLinea				= (String) request.getAttribute("esEnLinea");

	// Saldos del alumno
	FinSaldo finSaldo 				= (FinSaldo) request.getAttribute("finSaldo");
	List<AlumPlan> lisPlanes 				= (List<AlumPlan>) request.getAttribute("lisPlanes");
	List<AlumnoCurso> lisCursos 			= (List<AlumnoCurso>) request.getAttribute("lisCursos");	
	List<VotoEvento> lisEventos 			= (List<VotoEvento>) request.getAttribute("lisEventos");
	List<CandAlumno> lisCandados 			= (List<CandAlumno>) request.getAttribute("lisCandados");
	List<MatEvento> lisMatEventos 			= (List<MatEvento>) request.getAttribute("lisMatEventos");
	
	HashMap<String,MapaPlan> mapaPlanes 	= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String,String> mapaTipoCal	 	= (HashMap<String,String>) request.getAttribute("mapaTipoCal");	
	HashMap<String,CandTipo> mapaCandados 	= (HashMap<String,CandTipo>) request.getAttribute("mapaCandados");
	
	// Registrar en sesion para la foto
	session.setAttribute("mat", codigoAlumno);
	
	// Subir a sesion si es el alumno quien ingreso	
	session.setAttribute("IngresoAlumno", codigoPersonal.equals(codigoAlumno)?"true":"false");
	
	// Objetos enviados por el controller
	String eventoVoto 	= (String)request.getAttribute("eventoVoto")==null?"0":(String)request.getAttribute("eventoVoto");	
	
	DecimalFormat dmf	= new DecimalFormat("###,##0.00; (###,##0.00)");
	DecimalFormat dmf2	= new DecimalFormat("###,##0.00; ###,##0.00");
	String mensajeSaldo = "-";	
	// Se incluye el menu del portal del alumno en la página
%>
<%@ include file= "menu.jsp" %>	
<%		
	String matricula 		= session.getAttribute("codigoAlumno")==null?"0":(String)session.getAttribute("codigoAlumno");
	String colorPortal 		= (String) session.getAttribute("colorPortal")==null?"":(String)session.getAttribute("colorPortal");	
	
	String escala 			= plan.getEscala();
	String modalidad		= academico.getModalidadId();	
	
	String planId			= plan.getPlanId();	
	boolean mensajeBecas 	= false;
%>
<html>
<head>
	<script type="text/javascript" src="../../js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript">
	function popitup(url) {
    	newwindow=window.open(url,'name', 'toolbar=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,height=600,width=1000');
    	if (window.focus){
    		newwindow.focus()
    	}
    	return false;
	}
	</script>
</head>
<body>
<div class="container-fluid">
<%
	boolean esGraduandoPrepa = false;	
	if (tienePlan > 0){
		if ( Integer.parseInt(plan.getGrado())==2 && carreraAlumno.substring(0,3).equals("107")){
			esGraduandoPrepa = true;
		}	
	}	
	 
	String mensaje = "";	
	if (cerrarPortal.equals("N") && mensajeBecas){	
		if(facultad.equals("107")){	
%>
	<div class="alert alert-danger alert-info d-flex align-items-center alert-alto-m mt-2">
		<h4>Additional scholarship due date: 100% additional scholarship until June 13, 90% June 14 - 18, 0% after June 19, 2019</h4> 
	</div>
<% 
		}else{	
%>
	<div class="alert alert-danger alert-info d-flex align-items-center alert-alto-m mt-2">
		<h4>Additional scholarship due date/application: 100% until May 8, 90% May 9-15, 80% May 16-31 and 0% from June 1, 2019.</h4> 
	</div>
<%
		}
	}
	
	if (cerrarPortal.equals("S")){
%>
	<div class="alert alert-danger alert-info d-flex align-items-center alert-alto-m mt-2">
		<h5><%=mensajeBloqueo%></h5> 
	</div>
	<%		
	}

	if (mensajeEnc.equals("1")){
%>
	<div class="alert alert-success alert-success d-flex align-items-center alert-alto-m mt-2">
		<h5>Thank you for answering the return intention survey.</h5> 
	</div>
	<%		
	}
	if (mensajeEnc.equals("2")){
%>
	<div class="alert alert-success alert-success d-flex align-items-center alert-alto-m mt-2">
		<h5>Thank you for taking the mentorship survey.</h5> 
	</div>
	<%		
	}
%>
	<div class="alert alert-info d-flex align-items-center alert-alto-m mt-1">
	<h5>
		<%=alumno.getCodigoPersonal()%> - <%=alumno.getNombre()%> <%=alumno.getApellidoMaterno()%> <%=alumno.getApellidoPaterno()%>
		<small class="text-muted fs-6">( <%=modalidadNombre%> - <%=academico.getResidenciaId().equals("I")?"Boarding":"External"%> - <%=esInscrito?"ENROLLED":"NOT ENROLLED"%> )</small>&nbsp;&nbsp;		
<%	if (codigoAlumno.equals(codigoPersonal)){ %>					
<%-- 		<a href="autorizaPadre" class="btn btn-outline-dark btn-sm" target="_self"><i class="fas fa-check-circle"></i> <spring:message code="portal.alumno.inicio.Tutor"/></a>&nbsp; --%>
<%-- 		<a href="hermanos" class="btn btn-outline-dark btn-sm" target="_self"><i class="fas fa-people-arrows"></i> <spring:message code="portal.alumno.inicio.Hermanos"/></a>				 --%>
<%	} %>		
<% 
	if (carreraAlumno.equals("10304") || carreraAlumno.equals("10420")){
		//out.print("<img src='../../imagenes/manualNegro.jpg' width='40'>");
// 		out.print("<a href='enfermeria2.pdf' class='btn btn-success' target='_blank'>Manual/nursing</a>");
	}

	if (codigoPersonal.equals(codigoAlumno) && !eventoVoto.equals("0") && esInscrito){
%>		
<%-- 		<a class="btn btn-primary" href="votoMoodie?eventoVoto=<%=eventoVoto%>">Moody's</a> --%> 
<% 			
	}
%>		 
	</h5> 
	</div>
<% if(mensajeFoto.equals("1")){%>
	<div class="alert alert-warning d-flex align-items-center alert-alto-m mt-1">
		The picture you submitted will be displayed once it has been approved.
	</div>
<% }%>
<% if(foto.getRechazada().equals("S")){%>
	<div class="alert alert-warning d-flex align-items-center alert-alto-m mt-1">
		<strong>NOTE - Your picture has been rejected for the following reason:<%=foto.getComentario()%></strong>
	</div>
<% }%>

<!------------- S  E  C  C  I  O  N     D  E    F O T O G R A F I A   ---->	

	<div class="row">
		<div class="col-4 sm-1">
<%--  			<spring:message code="portal.alumno.inicio.FotoOficial"/><br> --%>
			<img class="rounded border border-dark" src="../../fotoCuadrada?Codigo=<%=codigoAlumno%>&Tipo=O" width="150">
		</div>
<%	if(!fotoAprobada && fotoEnviada == false){%>
<!-- 		<div class="col-2"> -->
<!-- 			<form style="padding-left: 10px; padding-right: 10px;" name="frmFoto" id="frmFoto" enctype="multipart/form-data" action="subirFoto" method="post"> -->
<%-- 				<input type="hidden" name="CodigoPersonal" value="<%=codigoAlumno%>"> --%>
<!-- 			  	<span class="btn btn-file"> -->
<!-- 				  	<input type="file" id="archivo" name="archivo" required="required"/> -->
<!-- 				  	<button type="submit" id="btnGuardar" class="btn btn-warning">Upload picture</button> -->
<!-- 			  	</span> -->
<!-- 			</form> -->
<!-- 		</div> -->
<% 	}else if (fotoEnviada){%>
<!-- 	<div class="col-2"> -->
<%-- 		<spring:message code="portal.alumno.inicio.FotoAdicional"/><br> --%>
<%-- 		<img class="rounded border border-dark" src="../../foto?Codigo=<%=codigoAlumno%>&Tipo=A" width="120"> --%>
<%		if (codigoPersonal.equals(codigoAlumno)){ %>		
<%-- 		<br><a class="btn btn-danger" href="borrarFoto?CodigoPersonal=<%=codigoAlumno%>&Tipo=A"><i class="fas fa-trash" ></i> --%>
<%		} %>		
<!-- 		</a> -->
<!-- 	</div>	 -->
<%	}%>
<%	if (codigoSesion.equals("9800308")){ %>
<!-- 		<div class="col-4 d-flex justify-content-center align-items-center"> -->
<!-- 			<a href="modificarMovil?Movil=S" class="badge bg-primary"><i class="fa fa-mobile fa-5x" aria-hidden="true"></i></a> -->
<!-- 		</div> -->
<%	} %>		
	</div>	
	
<!------------- S  E  C  C  I  O  N     D  E    D  A  T  O  S   ---->	

	<br>
	<div class="container-fluid">
	<div class="row border mt-1">		
		<div class="col-2">			
			<spring:message code="portal.alumno.inicio.PlanPrincipal"/>:
		</div>
		<div class="col-8">	
<%		
		int row=0;
		for (AlumPlan alumPlan : lisPlanes){
			row++;
			String nombrePlan = "-";
			if (mapaPlanes.containsKey(alumPlan.getPlanId())){
				nombrePlan = mapaPlanes.get(alumPlan.getPlanId()).getNombrePlan();
			}
			if (alumPlan.getEstado().equals("1")){								
				out.print(alumPlan.getPlanId()+"-"+nombrePlan);
			}
		}
%>		        				
		</div>
	</div>
	<div class="row border">	
		<div class="col-2">
			<spring:message code="portal.alumno.inicio.Mentor"/>:		
		</div>
		<div class="col-8">
			<%=mentorNombre%>
		</div>
	</div>	
<%
		// String saldoTotal = dmf.format(Float.parseFloat(finSaldo.getSaldoSP()))+" Db";
		// String saldoVencido =  dmf.format(Float.parseFloat(finSaldo.getSaldoVencido()))+" Db";
		// if(Float.parseFloat(finSaldo.getSaldoSP()) < 0){
		// 	saldoTotal = dmf.format(Float.parseFloat(finSaldo.getSaldoSP().substring(1)))+" Cr";
		// }
		
		// if(Float.parseFloat(finSaldo.getSaldoSP()) != 0){
%>
<!-- 	<div class="row border">		 -->
<!-- 		<div class="col-2"> -->
<%-- 			<spring:message code="portal.alumno.inicio.SaldoTotal"/>: --%>
<!-- 		</div>	 -->
<!-- 		<div class="col-8">			         -->
<%-- 		    $<%=saldoTotal%> --%>
<!-- 		</div> -->
<!-- 	</div>		 -->
<% 		
		// }
		
		// if(Float.parseFloat(finSaldo.getSaldoSP()) > 0){ 
%>
<!-- 	<div class="row border"> -->
<!-- 		<div class="col-2"> -->
<%-- 			<spring:message code="portal.alumno.inicio.SaldoVencido"/>: --%>
<!-- 		</div> -->
<!-- 		<div class="col-8">      -->
<%-- 		   $<%=saldoVencido%> --%>
<!-- 		</div> -->
<!-- 	</div>	 -->
<% 		
		// } 
%>
	</div>
	<br>	
		
<!------------- S  E  C  C  I  O  N     D  E    E  X  T  R  A  N  G  E  R  O  S   ---->	

<%
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date hoy = new Date();
		String colorMensaje="", mensajeExtranjero="";
		Date fechav = df.parse(venceFM3);
		int dias = new Long((fechav.getTime()-hoy.getTime())/1000/60/60/24).intValue();
		if(dias>29 && dias<61){
			colorMensaje="#F39603";
			mensaje="FM3 expires in "+dias+" days, You are in time to fix your immigration status.";
		}else if(dias<30){
			colorMensaje="#CE0000";
			if(dias>=1) mensajeExtranjero="FM3 expires in "+dias+" days, It is urgent to fix your immigration status.";
			if(dias<=0) mensajeExtranjero=" FM3 expired "+(dias*-1)+" days ago, It is urgent to fix your immigration status.";
		}else {			
			mensaje=dias + " remaining days.";
		}
%>		
<%		if(extranjero){ %>		
<!-- 	<div class="alert alert-info alert-info d-flex align-items-center alert-alto-m mt-1"> -->
<%-- 		<h4><spring:message code="portal.alumno.inicio.AsuntosLegalesDeExtranjeros"/></h4> --%>
<!-- 	</div> -->
<!-- 	<div class="container-fluid"> -->
<!-- 	<div class="row"> -->
<!-- 		<div class="col-6">	 -->
<%-- 			<spring:message code="datosAlumno.portal.Nota12"/>&nbsp;<b><%=venceFM3%></b>&nbsp; --%>
<%-- 			<span><B><%=mensajeExtranjero%></B></span> --%>
<!-- 		</div>							 -->
<!-- 	</div>	 -->
<!-- 	</div> -->
<%		} %>	
	
<!------------- S  E  C  C  I  O  N     D  E    C A N D A D O S   ---->

<%		
	if(conCandados || !autorizado.substring(0,1).equals("A")){
%>
	
	<div class="alert alert-info alert-info d-flex align-items-center alert-alto-m mt-2">
		<h4><spring:message code="portal.alumno.inicio.Candados"/></h4>
	</div>	
	<table class="table table-sm">
		<tr>
			<th><spring:message code="portal.alumno.inicio.Candado"/></th>
			<th><spring:message code="portal.alumno.inicio.SolucionarEn"/>: </th>
		</tr>
<%		
		for(int z=0; z<lisCandados.size(); z++){
			CandAlumno ca = (CandAlumno) lisCandados.get(z);
			
			CandTipo candTipo 	= new CandTipo(); 
			if (mapaCandados.containsKey(ca.getCandadoId())){
				candTipo = mapaCandados.get(ca.getCandadoId());
			}
%>
        <tr>
          <td><%=candTipo.getNombreTipo()%>  -  (<%= ca.getComentario() %>)</td>
          <td><%=candTipo.getLugar()+" "+candTipo.getTelefono()%></td>
        </tr>
<%		}
		if (!autorizado.substring(0,1).equals("A") ){
%>
  		<tr>
          <td><spring:message code="portal.alumno.inicio.DOCUMENTOS"/></td>
          <td>Registar's Office.0</td>
        </tr>
<%
		}
%>
	</table>
<%
	}	
%>

<!------------- S  E  C  C  I  O  N     D  E      B  E  C  A  S   ---->	

	<div class="alert alert-info alert-info d-flex align-items-center alert-alto-m mt-2">
<%-- 		<spring:message code="portal.alumno.inicio.ServicioBecarioEMPRENDUM"/>:&nbsp;<a href="plazas" class="btn btn-outline-dark btn-sm" target="_blank"><i class="fas fa-search"></i> <spring:message code="portal.alumno.inicio.Consulta"/></a>&nbsp;&nbsp;	 --%>
<%	if(esInscrito && !contestoMentoria){%>	
<%-- 			<a href="encuestaMentor" class="btn btn-outline-success btn-sm"> <spring:message code="portal.alumno.inicio.EvalMentoria"/></a>&nbsp;&nbsp; --%>
<% 		}%>
<%	if ( ( codigoAlumno.equals(codigoPersonal) || codigoPersonal.equals("9801231")) && (colportorId.equals("S") || colportorId.equals("C")) ){ %>
		Canvassing:&nbsp;
		<a class="btn btn-outline-dark btn-sm" href="#" target="_blank">Register your Documents</a> &nbsp; &nbsp;
		<a class="btn btn-outline-dark btn-sm" href="#" target="_blank">Register your clients</a> &nbsp; &nbsp;
		<a class="btn btn-outline-dark btn-sm" href="#" target="_blank">Reports</a> &nbsp; &nbsp;
		<a class="btn btn-outline-dark btn-sm" href="#" target="_blank">Detailed bonuses</a>	
<% 	}%>		
	</div>
<%	
	//System.out.println(lisEventos.size()+":"+esInscrito+":"+codigoAlumno+""+codigoSesion);
	for(VotoEvento evento : lisEventos){
		
		// Si esta inscrito y no hay filtros de poblacion o el alumno es parte de la poblacion		
		if( (esInscrito || codigoSesion.equals("9800308")) && codigoAlumno.equals(codigoSesion) && (evento.getPoblacion().equals("-") || evento.getPoblacion().contains(codigoPersonal)) ){
		//if (codigoAlumno.equals("1050355")){	
%>
	<div class="alert alert-success alert-info d-flex align-items-center alert-alto-m mt-2">
		<span style="font-size:18px;font-weight: 500;padding: 10px 0;display:block;"><%=evento.getEventoNombre()%>
			<small style="font-size:14px;font-weight: 500;padding: 10px 0;">(Authorized from <%=evento.getFechaIni()%> to <%=evento.getFechaFin()%> )</small>&nbsp;
<% 			if( evento.getTipo().equals("M")){ %>
			<a target="_blank" href="votoMoodie?eventoVoto=<%=evento.getEventoId() %>" class="btn btn-dark btn-sm"><i class="icon-ok icon-white"></i> <spring:message code="portal.alumno.inicio.Votar"/></a>
<% 			}else if( evento.getTipo().equals("F")){ %>
			<a target="_blank" href="votoFed?eventoVoto=<%=evento.getEventoId() %>" class="btn btn-dark" btn-sm><i class="icon-ok icon-white"></i> <spring:message code="portal.alumno.inicio.Votar"/></a>
<% 			}else if( evento.getTipo().equals("U")){ %>
			<a target="_blank" href="votoManana?eventoVoto=<%=evento.getEventoId() %>" class="btn btn-dark" btn-sm><i class="icon-ok icon-white"></i> <spring:message code="portal.alumno.inicio.Votar"/></a>
<% 			}else if( evento.getTipo().equals("R")){ %>
			<a target="_blank" href="votoRopa?eventoVoto=<%=evento.getEventoId() %>" class="btn btn-dark" btn-sm><i class="icon-ok icon-white"></i> <spring:message code="portal.alumno.inicio.Votar"/></a>
<%			}%>
		</span>
	</div>
<%
		}
	}
%>

<!------------- S  E  C  C  I  O  N     D  E      I  N  S  C  R  I  P  C  I  O  N   ---->	

	<div class="d-flex justify-content-between">
			<%-- <div class="card bg-light"> --%>
				<%-- <div class="card-header"> --%>
					<%-- Enrollment --%>
				<%-- </div> --%>
				<%-- <div class="card-body" style="position: relative; left: 0; top: 0;"> --%>
					<%-- <select id="eventoId" class="form-select" style="width:450px;"> --%>
<% 				for(MatEvento matEvento : lisMatEventos){ %>
						<%-- <option value="<%=matEvento.getEventoId()%>"><%=matEvento.getEventoNombre()%></option> --%>
<% 				}%>
					<%-- </select><br> --%>
					<%-- <select id="planSolicitado" class="form-select" style="width:450px;"> --%>
<% 				for(AlumPlan alumPlan : lisPlanes){
					String nombrePlan = "-";
					if (mapaPlanes.containsKey(alumPlan.getPlanId())){
						nombrePlan = mapaPlanes.get(alumPlan.getPlanId()).getCarreraSe();
					}
%>
						<%-- <option value="<%=alumPlan.getPlanId()%>"><%=alumPlan.getPlanId()%>-<%=nombrePlan%></option> --%>
<% 				}%>
					<%-- </select><br>									  --%>
 					<%-- <select id="modo" class="form-select" style="width:450px;">  --%>
<%		if (esEnLinea.equals("S")){%>					
						<%-- <option value="V"><spring:message code="portal.alumno.inicio.HogarVirtual"/></option> --%>
<%		}else{%>						
						<%-- <option value="P"><spring:message code="portal.alumno.inicio.CampusUM"/></option> --%>
<%		} %>						
 					<%-- </select> --%>
				<%-- </div> --%>
 				<%-- <div class="card-footer bg-transparent border-success d-flex justify-content-start"> --%>
					<%-- <a onclick="javascript:solicitudInscripcion();" href="#" class="btn btn-primary btn-sm" style="color: white;"><i class="icon-ok icon-white"></i>Apply for enrollment</a>&nbsp;&nbsp; --%>
					<%-- <span id="respuesta"></span> --%>
 				<%-- </div> --%>
 			<%-- </div> --%>

<!------------- S  E  C  C  I  O  N     D  E      A  P  P    U  M   ---->	

<% 		if(!alumno.getEmail().equals("-")){%>
<!--  			<div class="col-8 col-md-4 col-md-2 text-center">  -->
<!-- 				<a href="validaUsuarioMovil" title="Usuario movil"> -->
<!-- 				<img alt="Movil" src="../../imagenes/icono-movil.jpg" style="height: 140px; width: 140px; border-radius: 15px;"><br> -->
<!-- 				</a> -->
<!-- 				<a href="https://forms.gle/hoYff1uBR9RcAEZh6" class="text-center" style="text-decoration:none" target="_blanck"><i class="far fa-question-circle"></i> Survey</a> -->
<!-- 			</div> -->
<% 		}else{%>
<%-- 			<spring:message code="portal.alumno.inicio.NecesitasRegistrarTuEmailParaUsarLaAppMovil"/> --%>
<% 		}%>
<%		if (codigoAlumno.equals(codigoSesion)){ %>	
<% 			if(permisoEvento && graduacionPendiente){%>
<!-- 		<div class="col-md-2"> -->
<!-- 			<a href="listaEventos" data-bs-toggle="tooltip" data-bs-placement="top" title="La UM quiere honrar la promesa de llevar a cabo tu graduaci�n de manera presencial. -->
<!-- 			El plan es llevar a cabo �nicamente la Ceremonia de Colaci�n de Grados. Por favor reg�strese para confirmar que asistir�s."><i class="fas fa-user-graduate fa-9x"></i></a> -->
<!-- 		</div> -->
<% 			}else if(permisoEvento){%>
		<div>
			<a href="listaEventos" data-bs-toggle="tooltip" data-bs-placement="top" title="�You are registered for a graduation event!"><i class="fas fa-user-graduate fa-9x"></i></a>
		</div>
<% 			}%>
<% 			if(noPermisoEvento){%>
<!-- 		<div class="col-md-2"> -->
<!-- 			<span data-bs-toggle="tooltip" data-bs-placement="top" title="Usted ya ha retirado sus emblemas de graduaci�n y firm� que no participar�a en caso de haber graduaci�n presencial. Por lo tanto, ya no es opci�n para usted. Gracias." style="color: red;"><i class="fas fa-user-graduate fa-9x"></i></span> -->
<!-- 		</div> -->
<% 			}%>
<% 		}%>
		<!-- <div class="col-md-2 col-sm-6">
			<a href="img/DocumentoAlumnosMDA+.pdf" target="_blank" title="Descargar documento">
 			<a href="" target="blank" title="Descargar el formato"><i class="fas file-alt-word-o" style="font-size:48px;color:blue"s></i>Formato Proyecto</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<img alt="MDA+" src="../../imagenes/MDA+.jpg">
			</a>
		</div>-->
		<!-- if(!contestoEncuesta){%>
		<div">
			<span class="text-center">Agosto 2022</span><br>
			<a class="text-center" href="intencionRegreso?PeriodoId=<%=encPeriodo.getPeriodoId()%>" title="Encuesta">
				<img alt="Reingreso" src="../../imagenes/regresar.png" style="height: 50px; width: 150px; border-radius: 15px;">
			</a>
		</div> --> 

<!------------- S  E  C  C  I  O  N     D  E      G  R  A  D  U  A  C  I  O  N  ---->	

<% 		if(invitacion > 0){			
			if(alumEgreso.getEventoId().equals("54") && alumEgreso.getPermiso().equals("S")){
%>
		<div >
			<span class="text-center"><spring:message code="portal.alumno.inicio.Graduacion"/></span><br>	
			<a class="btn btn-primary" href="<%=invitacion%>-1.png" target="_blank" title="Invitation"><i class="fas fa-user-graduate fa-2x"></i></a>
			<a class="btn btn-primary" href="<%=invitacion%>-2.pdf" target="_blank" title="Instructions"><i class="far fa-file-pdf fa-2x"></i></a>
		</div>
<% 			} else if((alumEgreso.getEventoId().equals("56") || alumEgreso.getEventoId().equals("51") || alumEgreso.getEventoId().equals("43")) && alumEgreso.getConfirmar().equals("S") && alumEgreso.getPermiso().equals("S")){%>
		<div>
			<span class="text-center">Graduation</span><br>
			<a class="btn btn-primary" href="<%=invitacion%>-1.png" target="_blank" title="Invitation"><i class="fas fa-user-graduate fa-2x"></i></a>
			<a class="btn btn-primary" href="<%=invitacion%>-2.pdf" target="_blank" title="Instructions"><i class="far fa-file-pdf fa-2x"></i></a>
		</div>
<% 			}
 		}%>
	</div>
	<%-- <a href="notificacion" class="btn btn-danger">Test Notificaiton</a> --%>
	</div>
</body>
<script>	
	$(function () {
  		$('[data-bs-toggle="tooltip"]').tooltip();  		
	});
</script>
<script type="text/javascript">
	function solicitudInscripcion(){
		var evento 	= document.getElementById("eventoId").value;
		var plan 	= document.getElementById("planSolicitado").value;
		var modo 	= document.getElementById("modo").value;
				
		jQuery.get('verificaSolicitud?EventoId='+evento+'&PlanId='+plan+'&Modo='+modo, function(data){
			jQuery("#respuesta").html(data);
			//Se coment� el OK para actualizar la fecha si es que el alumno solicita de nuevo			
			document.location.href = "solicitudInscripcion?EventoId="+evento+"&PlanId="+plan+"&Modo="+modo;
		});
	}	
</script>
