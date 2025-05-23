<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmSolicitud"%>
<%@page import="aca.admision.spring.AdmUsuario"%>
<%@page import="aca.admision.spring.AdmAccesoVobo"%>
<%@page import="aca.admision.spring.AdmAsesor"%>
<%@page import="aca.admision.spring.AdmEncuesta"%>
<%@page import="aca.admision.spring.AdmAcademico"%>
<%@page import="aca.admision.spring.AdmProceso"%>
<%@page import="aca.admision.spring.AdmCartaSubir"%>
<%@page import="aca.admision.spring.AdmPasos"%>
<%@page import="aca.admision.spring.AdmEvaluacion"%>
<%@page import="aca.admision.spring.AdmEvaluacionNota"%>
<%@page import="aca.admision.spring.AdmDirecto"%>
<%@page import="aca.admision.spring.AdmPrueba"%>
<%@page import="aca.admision.spring.AdmPago"%>
<%@page import="aca.admision.spring.AdmPruebaAlumno"%>
<%@page import="aca.acceso.spring.Acceso"%>
 
 
 
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<head>
	<script>
		function revisarMensaje(){			
			var para 	= document.getElementById("Para");
			var asunto 	= document.getElementById("Asunto");
			var mensaje = document.getElementById("Mensaje");
			var enviar 	= document.getElementById("btnEnviar");		
			
			var continuar = true;			
			if(continuar){
				if(para.value.replace(/^\s*([\S\s]*?)\s*$/, '$1').length>0){
					var arr = para.value.split(",");
					for(i=0; i<arr.length; i++){
						var p = arr[i].replace(/^\s*([\S\s]*?)\s*$/, '$1');
						if(p.length==0 || p.indexOf("@")==-1 || p.indexOf(".")==-1 || p.substr(p.indexOf("@")).indexOf(".")==-1){
							continuar = false;
							document.getElementById("paraMal").hidden = false;
							break;
						}
					}
					
					if(continuar){
						document.getElementById("paraMal").hidden = true;
					}
				}
				else{
					continuar = false;
				}
			}
			else{
				document.getElementById("paraMal").hidden = true;
			}
			
			if(continuar){
				if(asunto.value.replace(/^\s*([\S\s]*?)\s*$/, '$1').length>0){
					document.getElementById("asuntoMal").hidden = true;
				}
				else{
					continuar = false;
					document.getElementById("asuntoMal").hidden = false;
				}
			}
			else{
				document.getElementById("asuntoMal").hidden = true;
			}
			
			if(continuar){
				if(mensaje.value.replace(/^\s*([\S\s]*?)\s*$/, '$1').length>0){
					document.getElementById("mensajeMal").hidden = true;
				}
				else{
					continuar = false;
					document.getElementById("mensajeMal").hidden = false;
				}
			}
			else{
				document.getElementById("mensajeMal").hidden = true;
			}			
			
			if(continuar){
				enviar.hidden = false;
			}
			else{
				enviar.hidden = true;
			}
		}
		
		function enviarMensaje(folio){	
			
			var para 	= document.getElementById("Para");
			var asunto 	= document.getElementById("Asunto");
			var mensaje = document.getElementById("Mensaje");
			var enviar 	= document.getElementById("btnEnviar");			
			
			para.disabled 		= true;
			asunto.disabled 	= true;
			mensaje.disabled 	= true;
			enviar.hidden 		= true;
			
			
			jQuery("#tablaMensaje").css({
				"opacity": "0.7"
			});
			jQuery("#tablaMensaje").fadeIn("normal");
			
			jQuery("#imagenCarga").css({
				"display": ""
			});
			
			var imgCarga = document.getElementById("imagenCarga");
			imgCarga.innerHTML = "<table width='100%' height='100%'><tr><td align='center'><img width='40' src=\"../../imagenes/wait.gif\" /></td></tr></table>";
			
			new Ajax.Request("enviarCorreo?Folio="+folio+"&Para="+para.value+"&Asunto="+asunto.value+"&Mensaje="+mensaje.value.replace(/\n/g, "/N"), {
				method: "get",
				onSuccess: function(req){
					eval(req.responseText);
				},
				onFailure: function(req){
					errorMessage("There was an error sending the email","Try again");
				}
			});
		}
		
		function cambiar(num){			
			var asunto 		= document.getElementById("Asunto");
			var mensaje 	= document.getElementById("Mensaje");
			var imgCarga 	= document.getElementById("imagenCarga");
			var enviar 		= document.getElementById("btnEnviar");
			if(num==1){
				imgCarga.innerHTML = "<table width='100%' height='100%'><tr><td align='center'><img width='50' src=\"../../imagenes/activa.png\" /></td></tr></table>";
				asunto.value = "";
				mensaje.value = "";				
			}
			else if(num==2){
				imgCarga.innerHTML = "<table width='100%' height='100%'><tr><td align='center'><img width='25' src=\"../../imagenes/no.png\" /><br><font size='3'><b>There was an error sending the message.<br>Try Again</b></font></td></tr></table>";				
			}			
			enviar.hidden = true;
			setTimeout("continuar();", 2000);
		}
		
		function continuar(){			
			var para 	= document.getElementById("Para");
			var asunto 	= document.getElementById("Asunto");
			var mensaje = document.getElementById("Mensaje");
						
			para.disabled 		= false;
			asunto.disabled 	= false;
			mensaje.disabled 	= false;
			
			jQuery("#tablaMensaje").css({
				"opacity": "1"
			});
			jQuery("#tablaMensaje").fadeIn("normal");
			
			jQuery("#imagenCarga").css({
				"display": "none"
			});
		}

		function marcarPago(folio){
			if(confirm("Do you want to change the student payment status to 'paid'?")){
				document.location.href='marcarPago?Folio='+folio;
			}
		}

		function borrarPago(folio){
			if(confirm("Do you want to delete the payment record?")){
				document.location.href='borrarPaso?Folio='+folio+"&Paso=1";
			}
		}

		function vistoBueno(folio, opcion, paso){
			document.location.href="editarvobo?Folio="+folio+"&Opcion="+opcion+"&Paso="+paso;
		}

		function borrarVisto(folio){
			if(confirm("Do you want to delete de validation record?")){
				document.location.href='borrarPaso?Folio='+folio+"&Paso=2";
			}
		}

		function voboPosgrado(folio, opcion, paso){
			document.location.href="editarvobo?Folio="+folio+"&Opcion="+opcion+"&Paso="+paso;
		}

		function borrarVobo(folio){
			if(confirm("Do you want to delete de validation record?")){
				document.location.href='borrarPaso?Folio='+folio+"&Paso=5";
			}
		}

		function marcarExamen(folio){
			if(confirm("Do you want to mark the student has already taken the admission exam?")){
				document.location.href='examen?Folio='+folio;
			}
		}
		
		function borrarExamen(folio){
			if(confirm("Do you want to delete the exam record?")){
				document.location.href='borrarPaso?Folio='+folio+"&Paso=3";
			}
		}

		function marcarResultados(folio){
			if(confirm("Do you want set the results as completed?")){
				document.location.href='resultados?Folio='+folio;
			}
		}

		function borrarResultados(folio){
			if(confirm("Do you want to delete the results step?")){
				document.location.href='borrarPaso?Folio='+folio+"&Paso=4";
			}
		}
		
		function eliminarAspirante(folio){
			if(confirm("Do you want to delete the applicant data?")){
				document.location.href='eliminarAspirante?Folio='+folio;
			}
		}

		function Grabar(){
			document.frmGrabar.submit();
		}
	</script>
</head>
<%	
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
	String fechaIni 			= (String) session.getAttribute("fechaInicio");
	String fechaFin 			= (String) session.getAttribute("fechaFinal");	
	String estadoFiltro			= (String) session.getAttribute("estados");
	String admitidos			= (String) session.getAttribute("admitidos");
	String modalidades			= (String) session.getAttribute("modalidades");
	String asesor				= (String) session.getAttribute("asesor");
	
	String folio 				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	boolean tieneSede			= (boolean) request.getAttribute("tieneSede");	
	boolean esAsesor			= (boolean) request.getAttribute("esAsesor");
	boolean tienePago			= (boolean) request.getAttribute("tienePago");
	boolean tienePrueba			= (boolean) request.getAttribute("tienePrueba");
	boolean vistoBueno			= (boolean) request.getAttribute("vistoBueno");
	boolean voboPosgrado		= (boolean) request.getAttribute("voboPosgrado");
	boolean tieneOtros			= (boolean) request.getAttribute("tieneOtros");
	boolean tieneResultados		= (boolean) request.getAttribute("tieneResultados");
	boolean tieneRecomendaciones		= (boolean) request.getAttribute("tieneRecomendaciones");
	boolean listoVoBo			= (boolean) request.getAttribute("listoVoBo");
	boolean nivelPosgrado 		= (boolean)request.getAttribute("nivelPosgrado");
	int encuestasFolio			= (int) request.getAttribute("encuestasFolio");
	String asesorNombre			= (String) request.getAttribute("asesorNombre");
	String carreraNombre		= (String) request.getAttribute("carreraNombre");
	String usuario				= (String) request.getAttribute("usuario");
	String fechaReservacion		= (String) request.getAttribute("fechaReservacion");
	Acceso acceso				= (Acceso) request.getAttribute("acceso");
	int cantidadComentarios		= (int) request.getAttribute("numComentarios");
	
	AdmSolicitud admSolicitud	= (AdmSolicitud) request.getAttribute("admSolicitud");
	AdmUsuario admUsuario		= (AdmUsuario) request.getAttribute("admUsuario");
	AdmCartaSubir admCartaSubir	= (AdmCartaSubir) request.getAttribute("admCartaSubir");
	AdmAsesor admAsesor			= (AdmAsesor) request.getAttribute("admAsesor");
	AdmAcademico admAcademico	= (AdmAcademico) request.getAttribute("admAcademico");
	AdmProceso admProceso		= (AdmProceso) request.getAttribute("admProceso");
	AdmPasos admPasos			= (AdmPasos) request.getAttribute("admPasos");
	AdmDirecto admDirecto		= (AdmDirecto) request.getAttribute("admDirecto");
	AdmPrueba admPrueba			= (AdmPrueba) request.getAttribute("admPrueba");
	AdmPruebaAlumno admPruebaAlumno		= (AdmPruebaAlumno) request.getAttribute("admPruebaAlumno");	
	String nombreAlumno 		= admUsuario.getNombre()+" "+(admUsuario.getApellidoMaterno()==null?"":admUsuario.getApellidoMaterno())+" "+admUsuario.getApellidoPaterno();
	String gen 					= admUsuario.getGenero();
	int estado 					= Integer.parseInt(admSolicitud.getEstado());
	
	boolean tieneMatricula = false;
	if(admUsuario.getMatricula()!=null && admUsuario.getMatricula().length()==7){
		try{
			int mat = Integer.parseInt(admSolicitud.getMatricula());
			tieneMatricula = true;
		}catch(Exception e){
			tieneMatricula = false;		
		}
	}
	
	List<AdmEvaluacion> listaEvaluaciones 			= (List<AdmEvaluacion>) request.getAttribute("listaEvaluaciones");
	List<AdmAsesor> listaAsesores					= (List<AdmAsesor>) request.getAttribute("listaAsesores");
	List<String> listaAcceso						= (List<String>) request.getAttribute("listaAcceso");
	HashMap<String,String> mapaMaestros				= (HashMap<String,String>) request.getAttribute("mapaMaestros");
	HashMap<String,AdmEvaluacionNota> mapaNotas		= (HashMap<String,AdmEvaluacionNota>) request.getAttribute("mapaNotas");
	HashMap<String,AdmPasos> mapaPasos				= (HashMap<String,AdmPasos>) request.getAttribute("mapaPasos");
	List<AdmPago> lisPagos 							= (List<AdmPago>) request.getAttribute("lisPagos");

	int pagos = lisPagos.size();

	int cantidadDocs 		= (int) request.getAttribute("cantidadDocs");
	int cantidadEncuesta	= (int)request.getAttribute("cantidadEncuesta");
	
	int cantEncuestas = 0;	
	
	String arrayIni[]		= fechaIni.split("/");
	String arrayFin[]		= fechaFin.split("/");

%>
<body>  
<div class="container-fluid">
	<h2>Admission Process <small class="text-muted fs-5">( <%=nombreAlumno %> - <%=admAcademico.getCarreraId().equals("00000")?"&nbsp;":admAcademico.getCarreraId()%> - <%=admAcademico.getCarreraId().equals("00000")?"&nbsp;":carreraNombre%> )</small></h2>
	<form name="frmGrabar" action="grabarEstado" method="post">	
		<input name="Folio" type="hidden" value="<%=folio%>"/>	
		<div class="alert alert-info d-flex align-items-center">
			<a class="btn btn-primary" href="proceso?fechaIni=<%=arrayIni[2]+"-"+arrayIni[1]+"-"+arrayIni[0]%>&fechaFin=<%=arrayFin[2]+"-"+arrayFin[1]+"-"+arrayFin[0]%>&Estados=<%=estadoFiltro%>&Admitidos=<%=admitidos%>&&Modalidades=<%=modalidades%>&Asesor=<%=asesor%>"><i class="fas fa-arrow-left" ></i></a>&nbsp;
<%	if ( usuario.equals("9800308")){
		if ( gen.equals("M") && admSolicitud.getEstado().equals("1") && cantidadDocs == 0){%>
			<%-- <img src="../../imagenes/x.png" class="button" onClick="eliminarAspirante('<%=folio %>');" title="Delete Applicant" width="40"/> --%>
<%		}else if (admSolicitud.getEstado().equals("1") && cantidadDocs == 0){ %>
			<%-- <img src="../../imagenes/x.png" class="button" onClick="eliminarAspirante('<%=folio %>');" title="Delete Applicant" width="40"/> --%>
<% 		}	
	}else if( esAsesor && gen.equals("M") && !tieneMatricula && cantidadDocs == 0 && admSolicitud.getEstado().equals("1") ){
%>
			<%-- <img src="../../imagenes/x.png" class="button" onClick="eliminarAspirante('<%=folio %>');" title="Delete Applicant" width="40"/> --%>
<%	
	}else if( esAsesor && !tieneMatricula && cantidadDocs == 0 && admSolicitud.getEstado().equals("1")){		
%>
			<%-- <img src="../../imagenes/x.png" class="button" onClick="eliminarAspirante('<%=folio %>');" title="Delete Applicant" width="40"/> --%>
<%	} %>&nbsp;&nbsp;
			Process ID: <span class="badge bg-dark mx-2"><%=folio%></span>&nbsp;&nbsp;
<%	if ( esAsesor || acceso.getAdministrador().equals("S")){%>	
			Progress:	
			<select name="Estado" class="form-select" style="width: 8rem">			
				<option value="1" <%=admSolicitud.getEstado().equals("1")?"selected":""%>>1.Create Account</option>
				<option value="2" <%=admSolicitud.getEstado().equals("2")?"selected":""%>>2.Request</option>
				<option value="3" <%=admSolicitud.getEstado().equals("3")?"selected":""%>>3.Documents</option>
				<option value="4" <%=admSolicitud.getEstado().equals("4")?"selected":""%>>4.Payment</option>			
				<option value="5" <%=admSolicitud.getEstado().equals("5")?"selected":""%>>5.Registered</option>
				<option value="6" <%=admSolicitud.getEstado().equals("6")?"selected":""%>>6.Inactive</option>
			</select>&nbsp;
			Advisor:
			<select name="AsesorId" style="width:300px;" class=" mx-2 form-select" style="width: 12rem">
<% 	
		for (AdmAsesor asesores : listaAsesores){
			String asesorName	= "-";
			if (mapaMaestros.containsKey(asesores.getAsesorId())){
					asesorName = mapaMaestros.get(asesores.getAsesorId());
			}
%>
				<option value="<%=asesores.getAsesorId()%>" <%=admSolicitud.getAsesorId().equals(asesores.getAsesorId())?"selected":""%>><%=asesorName%></option>
<% 		}%>					
			</select>&nbsp;
			<a href="javascript:Grabar()" class="btn btn-primary mx-1"><i class="fas fa-save"></i> Save</a>&nbsp;
<!-- 			<a href="GuiaExaUM.pdf" class="btn btn-success" target="_blank"><i class="fas fa-file-pdf"></i> Guide</a>&nbsp; -->
			<a class="btn btn-warning mx-1" href="comentarios?Folio=<%=folio%>">
				<i class="fas fa-comments"></i>&nbsp;Comments <span class="badge bg-dark"><%=cantidadComentarios%></span>
			</a>
<%	} %>	
			&nbsp;	
			<%-- <a class="btn btn-dark mx-1" href="referencias?Folio=<%=folio%>">
				<i class="fas fa-credit-card"></i>&nbsp;Bank References
			</a> --%>
<%	if(esAsesor || acceso.getAdministrador().equals("S")){
		if(!listoVoBo){ %>		
			<a class="btn btn-success mx-1" href="listoParaVoBo?Folio=<%=folio%>&Accion=1" title="Ready for Transfer">
				Approved <i class="fas fa-check"></i>
			</a>
<%		}else{ %>		
			<a class="btn btn-danger mx-1" href="listoParaVoBo?Folio=<%=folio%>&Accion=0" title="Not ready for Transfer">
				Approved <i class="fas fa-times"></i>
			</a>
<%		}
	}	
%>		
		</div>
	</form>
<%	if (tieneMatricula){%>	
	<div class="alert alert-success">
		<font size="4"><b><%=tieneMatricula?"Student ID: <font color='green'>"+admUsuario.getMatricula()+"</font>" : "" %></b></font>		
	</div>
<%	} %>	
	<div id="imagenCargaMonos" style="display:none;position:fixed; width:59%; height:35%; left:20%;"></div>
<!-- 	<table class="tabla" id="tablaMonos"  border="1"> -->
<table class="table table-bordered">
<%	
	String fechaPago 	= "";
	if (mapaPasos.containsKey("1")){
		fechaPago 		= mapaPasos.get("1").getFecha();		
	}
	
	String fechaResultados 	= "-";
	if (mapaPasos.containsKey("3")){
		fechaResultados 		= mapaPasos.get("3").getFecha();
	}
	
	String fechaPodium 	= "-";
	if (mapaPasos.containsKey("4")){
		fechaPodium 		= mapaPasos.get("4").getFecha();
	}
	
	String fechaVobo 	= "-";
	if (mapaPasos.containsKey("2")){
		fechaVobo 		= mapaPasos.get("2").getFecha();
	}
	
	String fechaVoboPos 	= "-";
	if (mapaPasos.containsKey("5")){
		fechaVoboPos 		= mapaPasos.get("5").getFecha();
	}
%>		
	<tr>																												<!-- FECHAS DE PASOS -->
		<td align="center"><b><%=admProceso.getFechaRegistro()==null?"":admProceso.getFechaRegistro()%></b></td>		<!-- FECHA REGISTRO -->
		<td align="center"><b><%=admProceso.getFechaSolicitud()==null?"":admProceso.getFechaSolicitud()%></b></td>		<!-- FECHAS SOLICITUD -->
		<td align="center"><b><%=admProceso.getFechaDocumentos()==null?"":admProceso.getFechaDocumentos()%></b></td>	<!-- FECHAS DOCUMENTOS -->
		<td align="center"><b><%=fechaPago%></b></td>																	<!-- FECHAS PAGO -->
		<td>
<%		if(admSolicitud.getEstado().equals("4")){ 
		if (admSolicitud.getFechaIngreso().equals("-")){%>
			<a class="btn btn-primary" href="grabarFecha?Folio=<%=folio%>">Add date</a>
<%			}
		}%>
		</td>
	</tr>		
	<tr>			
		<td width="7%" align="center" valign="center">
			<br>
			<a href="registro?Folio=<%=folio%>" style="color:black;"><i class="fas fa-user-plus fa-5x"></i></a> 		<!-- PERSONAL DATA -->
			<br>Personal Data
		</td>
<%		if(admProceso.getFechaSolicitud() == null && admSolicitud.getEstado().equals("1")){%>
		<td width="7%" align="center" valign="center">
			<br>
<%			if(tieneRecomendaciones){ %>
			<a href="solicitud?Folio=<%=folio %>">
				<i class="fas fa-edit fa-5x" style="color:gray;"></i>													<!-- APPLICATION FORM -->
			</a>
			<br>Application Form
<%			}else{%>
			<i class="fas fa-edit fa-5x" style="color:gray;"></i>
			<br>Application Form
<%			}%>				
		</td>
<%		} else{%>
		<td width="7%" align="center" valign="center">
			<br>
			<a href="solicitud?Folio=<%=folio %>" style="color:black;"><i class="fas fa-edit fa-5x"></i></a>			<!-- APPLICATION FORM -->
			<br>Application Form
		</td>
<%		}%>				
<% 		if(cantEncuestas>0){ %>
		<td width="7%" align="center" <% if(cantEncuestas>0){ %> valign="center"<% } %>>
			<a href="encuestas?Folio=<%=folio %>"><font size="3"><b><spring:message code="aca.Recomendaciones"/><br>(<font color="#AE2113"><%=cantEncuestas %></font>)</b></font></a> <!-- SURVEY -->
		</td>	
<%		} %>				
<%		if(admProceso.getFechaSolicitud() == null){%>
		<td width="7%" align="center" valign="bottom">
			<br>
			<a href="documentos?Folio=<%=folio %>"><i class="fas fa-file fa-4x" style="color:gray;"></i></a>						<!-- DOCUMENTS -->
			<br>
			Sent Documents
		</td>
<%		} else if(estado < 2){%>
		<td width="7%" align="center" valign="bottom">
			<br>						
			<a href=""><i class="fas fa-file fa-4x" style="color:gray;"></i></a>													<!-- DOCUMENTS -->
			<br>
			Sent Documents
		</td>
<%		} else{%>
		<td width="7%" align="center" valign="bottom">
			<br>
			<a href="documentos?Folio=<%=folio %>" style="color:black;"><i class="fas fa-file fa-5x"></i></a>						<!-- DOCUMENTS -->
			<br>
			Sent Documents
		</td>
<%		}%>
			<td width="7%" align="center" valign="bottom">
<%		if (tienePago){	
			out.print("<i class='fas fa-money-bill-alt fa-5x' style='color:black;'></i>"); %>
			<br>
			Payment
<%			
		}else{
 			if (esAsesor  || acceso.getAdministrador().equals("S")){%>
 				<br>
				<a href="javaScript:marcarPago('<%=folio%>');"><i class="fas fa-money-bill-alt fa-5x" style="color:gray;"></i></a>		<!-- PAYMENT -->
				<br>
				Payment
<% 			}else{
				out.print("<i class='fas fa-money-bill-alt fa-5x' style='color:gray;'></i><br><br>Payment");
			}				
		}
%>	
			</td>
<%
		if(estado == 4){%>
			<td width="7%" align="center" valign="bottom">
			<br>
<% 			if (esAsesor  || acceso.getAdministrador().equals("S")){%>
				<a href="avance?Folio=<%=folio %>"><i class="fas fa-inbox fa-4x" style="color:gray;"></i></a>
<% 			}else{
					out.print("<i class='fas fa-inbox fa-4x' style='color:gray;'>");
			}
%>
				<br>Transfer Data
			</td>
<%		} else if(estado >= 4){%>
			<td width="7%" align="center" valign="bottom">
				<br>
				<%-- <a href="admitido?Folio=<%=folio %>"><i class="fas fa-inbox fa-4x" style="color:gray;"></i></a>							<!-- ACCEPTANCE LETTER --> --%>
				<i class="fas fa-inbox fa-4x" style="color:black;"></i>
				<br>Acceptance letter<br>
<%-- <%				if (admSolicitud.getFechaIngreso().equals("-")){
				out.print("<font color='#AE2113'>No date</font>");
				}else if (admProceso.getFechaCarta()==null){
				out.print("<font color='#AE2113'>The applicant has not been validated</font>");
				}else{
				out.print(admProceso.getFechaCarta());
				}%> --%>
				
			</td>
<%		} else if(estado < 3){%>
			<td width="7%" align="center" valign="bottom">
				<br>			
				<i class="fas fa-inbox fa-4x" style="color:gray;"></i>
				<br>Transfer Data
			</td>
<%		} else{%>			
			<td width="7%" align="center" valign="bottom">
				<br>
				<i class="fas fa-inbox fa-4x" style="color:gray;"></i>					
				<br><br>Transfer Data
			</td>
<%		}%>	
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td align="center"><a href="listadoEncuestas?Folio=<%=folio%>"><span class="badge <%=cantidadEncuesta>=1?"bg-dark":"bg-warning"%>"><%=cantidadEncuesta%></span></a></td>
			<td align="center"><span class="badge <%=cantidadDocs>=1?"bg-dark":"bg-warning"%>"><%=cantidadDocs%></span></td>
			<td align= "center">
<% 		if(tienePago){
			if(esAsesor  || acceso.getAdministrador().equals("S")){
%>
				<a href="javaScript:borrarPago('<%=folio%>');"><i class="fas fa-trash text-danger" ></i></a>&nbsp;&nbsp;		
<% 		}}%>
				<a href="listaPago?Folio=<%=folio%>"><% if(pagos>=1){%><i class="fas fa-file"></i><% } else { %><i class="fas fa-file text-warning"></i><% }%></a>
			</td>
			<td align="center">
<%		String colorCarta = "warning";
		if(admCartaSubir.getCarta() != null){
			colorCarta = "success";
		}

		if(admSolicitud.getEstado().equals("4") || admSolicitud.getEstado().equals("5")){ 
			if (admSolicitud.getFechaIngreso().equals("-")){%>
				<a class="btn btn-primary" href="grabarFecha?Folio=<%=folio%>"><i class="fas fa-save"></i> Date</a>
<%			} %>			
				<a class="btn btn-<%=colorCarta%>" href="subirCartaAdmision?Folio=<%=folio%>" title="Upload Letter"><i class="far fa-envelope"></i></a>
<%		} %>		
	
			</td>
		</tr>
	</table>
	<br>
<%	if ( codigoPersonal.equals("0000000") /* codigoPersonal.equals("9800308")|| esAsesor DESACTIVAR EMAIL */){%>	
	<div class="alert alert-success">
		<h3>Send Message</h3>
	</div>	
	<div id="imagenCarga" style="display:none;position:fixed; width:59%; height:42%; left:20%;"></div>
	<table id="tablaMensaje" width="70%" class="table">		
		<tr>			
			<td width="20px"><b>For:<font id="paraMal" color="#AE2113" hidden>*</font></b></td>
			<td><input class="input input-xxlarge" onKeyUp="revisarMensaje();" type="text" size="113%" id="Para" value="<%=admUsuario.getEmail() %>"></td>
		</tr>
		<tr>			
			<td width="20px"><b>Subject:<font id="asuntoMal" color="#AE2113">*</font></b></td>
			<td><input class="input input-xxlarge" onKeyUp="revisarMensaje();" value="Fulton - Admissions(<%=folio%>)" id="Asunto" type="text" size="113%"></td>
		</tr>
		<tr>			
			<td width="20px"><b>Message:<font id="mensajeMal" color="#AE2113" hidden>*</font></b></td>
			<td>
				<textarea onKeyUp="revisarMensaje();" id="Mensaje" name="Mensaje" style="height:200px; width:530px;"></textarea>
			</td>
		</tr>
		<tr>			
			<td width="20px">&nbsp;</td>
			<td class="left">
				<input class="btn btn-primary" id="btnEnviar" style="cursor:pointer;" type="button" value="Send" onclick="enviarMensaje('<%=folio%>');">
			</td>
		</tr>
	</table>
<%	} %>		
</div>
</body>
	<script>
		revisarMensaje()
		var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
		var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
  			return new bootstrap.Tooltip(tooltipTriggerEl)
})
	</script>
</html>