<%@page import= "java.sql.Timestamp"%>
<%@page import= "java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.salida.spring.SalSolicitud"%>
<%@page import="aca.salida.spring.SalProposito"%>
<%@page import="aca.salida.spring.SalGrupo"%>
<%@page import="aca.vista.spring.Maestros"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatEstado"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="Bitacora" scope="page" class="aca.salida.SalBitacora"/>
<html>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
	<script type="text/javascript">
		function Guardar() {			
			if ( document.frmSolicitud.FechaSalida.value != "" && document.frmSolicitud.HoraSalida.value != ""
				&& document.frmSolicitud.FechaLlegada.value != "" && document.frmSolicitud.HoraLlegada.value != ""
				&& document.frmSolicitud.Comentario.value != "" && document.frmSolicitud.Lugar.value != ""
				&& document.frmSolicitud.LugarSalida.value != "" && document.frmSolicitud.Desayuno.value != ""
				&& document.frmSolicitud.Comida.value != "" && document.frmSolicitud.Cena.value != ""
				&& document.frmSolicitud.Hospedaje.value != "" && document.frmSolicitud.Transporte.value != ""
				&& document.frmSolicitud.Total.value != "" && document.frmSolicitud.TotalPersona.value != ""
				&& document.frmSolicitud.Responsable.value != ""
				)
			{
				var pernoctar 			= false;
				var solicitudCorrecta 	= false;
				var diasImplicados 		= 0;
					
				var arraySalida 		= document.frmSolicitud.FechaSalida.value.split('/');
				var arrayLlegada 		= document.frmSolicitud.FechaLlegada.value.split('/');
		
				var fechaSalida 		= new Date(parseInt(arraySalida[2],10), parseInt(arraySalida[1],10)-1, parseInt(arraySalida[0],10)); 
				var fechaLlegada 		= new Date(parseInt(arrayLlegada[2],10), parseInt(arrayLlegada[1],10)-1, parseInt(arrayLlegada[0],10));
		
				if(fechaSalida < fechaLlegada){
					pernoctar = true;
				}
					
				if(document.frmSolicitud.PaisId.value === "91" && document.frmSolicitud.EstadoId.value === "19"){
					if(pernoctar){
						diasImplicados = 14;
					}else{
						diasImplicados = 3;
					}
				}else if(document.frmSolicitud.PaisId.value === "91"){
					if(pernoctar){
						diasImplicados = 14;
					}else{
						diasImplicados = 7;
					}
				}else {
					if(pernoctar){
						diasImplicados = 20;
					}else{
						diasImplicados = 7;
					}
				}

				var hoyFull		= new Date();
				var fechaHoy	= new Date(hoyFull.getFullYear(),hoyFull.getMonth(),hoyFull.getDate());
				var factorDias	= 1000*60*60*24;
				var diasSalida 	= fechaSalida.getTime()/factorDias;
				var diasHoy 	= fechaHoy.getTime()/factorDias;
				var dias 		= diasSalida - diasHoy;
// 				alert("Diferencia:"+diasSalida+">>"+diasHoy+":::"+dias);
												
				if(diasImplicados > dias){
					alert("¡ No cumples con los días requeridos para hacer una solicitud con las fechas y el destino especificado (Requieres "+diasImplicados+" días, días disponibles:"+dias+")");
				}
													
				document.frmSolicitud.submit();
								
			}else{
				alert("¡Completa todos los campos!");
			}
									
		}		
	</script>	
	<link rel="stylesheet" href="../../js/chosen/chosen.css" />	
</head>	
<style>
 	input:invalid { 
 	  border: 1.5px solid red; 
 	} 
</style>
<%	
	String usuario 						= (String) session.getAttribute("codigoPersonal");
	String empleadoId 					= (String) session.getAttribute("codigoEmpleado");
	String salidaId	 					= request.getParameter("salida")==null?"0":request.getParameter("salida");
	String fechaHoy 					= aca.util.Fecha.getHoy();
	SalSolicitud salSolicitud 			= (SalSolicitud) request.getAttribute("salSolicitud");
	String grupoId 						= salSolicitud.getGrupoId();
	
	List<SalProposito> lisPropositos 	= (List<SalProposito>) request.getAttribute("lisPropositos");
	List<SalGrupo> lisGrupos			= (List<SalGrupo>) request.getAttribute("lisGrupos");
	List<Maestros> lisMaestros			= (List<Maestros>) request.getAttribute("lisMaestros");
	List<CatPais> lisPaises				= (List<CatPais>) request.getAttribute("lisPaises");
	List<CatEstado> lisEstados			= (List<CatEstado>) request.getAttribute("lisEstados");
	
	if(grupoId.equals("0") && lisGrupos.size() >=1 ){
		grupoId = lisGrupos.get(0).getGrupoId();
	}
	
	String mensaje = request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");	
%>
<div class="container-fluid">
	<h2>Salidas</h2>
	<form id="frmSolicitud" name="frmSolicitud" method="post" action="grabar" target="_self">
	<input type="hidden" name="Accion">
	<input type="hidden" name="salida" value="<%=salidaId%>">
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="solicitud">Regresar</a>&nbsp;&nbsp;
		<b>Grupo:</b>&nbsp;						
		<select name="Grupo" id="Grupo" class="form-select" style="width:350px;">
		<%	for (int i = 0; i < lisGrupos.size(); i++) {
				SalGrupo grupo = (SalGrupo) lisGrupos.get(i);
			%>
				<option value="<%=grupo.getGrupoId()%>" <%=grupoId.equals(grupo.getGrupoId())?"Selected":""%>>
					<%=grupo.getGrupoNombre()%>
				</option>
			<%
			}
		%>
		</select>		
	</div>		
	<div class="row">
		<div class="span3 col">	
<!-- 			<label for="Salida">N° salida:</label>					 -->
			<input type="hidden" name="salida" type="text" id="salida" value="<%=salSolicitud.getSalidaId()%>" size="5" maxlength="6" readonly>
<!-- 			<br><br> -->
			<label for="Proposito">Prop&oacute;sito:</label>			
			<select name="Proposito" id="Proposito" class="form-select" style="width:250px;">
<% 	for (SalProposito salProposito :lisPropositos){%>
				<option value="<%=salProposito.getPropositoId()%>" <%=salSolicitud.getPropositoId().equals(salProposito.getPropositoId())?"selected":""%>><%=salProposito.getPropositoNombre()%></option>
<% 	}%>	
            </select>
<% 			if(request.getParameter("Proposito")!=null && request.getParameter("Proposito").equals("O") || salSolicitud.getProposito().equals("O")){%>
			<input name="OtroP" type="text" id="OtroP" value="<%=salSolicitud.getOtroProposito()%>" style="width:200px;" class="form-control" maxlength="50" placeholder="Especificar">
<% 			}%>
			<br>
			<label for="Responsable">Responsable:</label><br>
			<select name="Responsable" id="Responsable" class="form-select  chzn-select" style="width:350px;">
<%
				String mstroDefault = empleadoId; 			
				for(Maestros maestro: lisMaestros){
%>
					<option value="<%=maestro.getCodigoPersonal()%>" <%if(maestro.getCodigoPersonal().equals(salSolicitud.getResponsable()))out.print("selected"); %>><%=maestro.getCodigoPersonal()+" - "+maestro.getNombre()+" "+maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno() %></option>

			<% }%>

			</select>
	 		<br>
	 		<label for="Telefono">Número de teléfono</label>					
			<input name="Telefono" type="text" id="Telefono" class="form-control" style="width:400px;" value="<%=salSolicitud.getTelefono()%>" maxlength="70" required="required">						
<%		
			String fechaSalida = "";
			String horaSalida = "00:00:00";

			String[] arr = salSolicitud.getFechaSalida().split(" ");			
			if(arr.length >= 1){
				fechaSalida = arr[0];
				horaSalida = arr[1];
			}
%>
			
			<br>
			<label for="LugarSalida">Punto de reunión para salir</label>				
			<input name="LugarSalida" type="text" id="LugarSalida" value="<%=salSolicitud.getLugarSalida()%>" style="width:400px;" class="form-control" maxlength="70">
			<br>
			<label for="Comentario">Comentario</label>
			<textarea maxlength="600" name="Comentario" id="Comentario" cols="37" rows="7" required><%=salSolicitud.getComentario()%></textarea>
		</div>
		<div class="span3 col">			
			<label for="Pais">Pais:</label>
			<select name="PaisId" id="PaisId" class="form-select" style="width:300px;" onchange="javascript:refreshEstados();">
<%			for(CatPais pais: lisPaises){ %>
				<option value="<%=pais.getPaisId()%>" <%if(pais.getPaisId().equals(salSolicitud.getPaisId()))out.print("selected"); %>><%=pais.getNombrePais()%></option>
<% 			}%>
			</select>	
			<br>
			<label for="Estado">Estado:</label>
			<select name="EstadoId" id="EstadoId" class="form-select" style="width:250px;">
<%			for(CatEstado estado : lisEstados){%>					
				<option value="<%=estado.getEstadoId()%>" <%=estado.getEstadoId().equals(salSolicitud.getEstadoId())?"selected":""%>><%=estado.getNombreEstado()%></option>
<% 			}%>
	        </select>	        
	        <br>
	        <label for="Lugar">Lugar de Destino</label>					
			<input name="Lugar" type="text" id="Lugar" value="<%=salSolicitud.getLugar()%>" style="width:350px;" class="form-control" maxlength="70">
			<br>
			<label for="FechaSalida">Fecha salida</label> (DD/MM/AAAA)					
			<input name="FechaSalida" type="text" id="FechaSalida" size="12" data-date-format="dd/mm/yyyy" style="width:200px;" class="form-control" maxlength="10" onfocus="focusFecha(this);" value="<%=fechaSalida%>"> 
			<br>
			<label for="HoraSalida">Hora salida</label>	(24h/60m/60s)		
			<input type="text" name="HoraSalida" id="HoraSalida" style="width:200px;" class="form-control" size="12" maxlength="8" value="<%=horaSalida%>" /> 
<%
			String fechaLlegada = "";
			String horaLlegada = "00:00:00";
			String[] arr2 = salSolicitud.getFechaLlegada().split(" ");
			if(arr.length >= 1){
				fechaLlegada = arr2[0];
				horaLlegada = arr2[1];
			}
%>
			<br>
			<label for="FechaLlegada">Fecha llegada</label>	(DD/MM/AAAA)				
			<input name="FechaLlegada" type="text" id="FechaLlegada" size="12" style="width:200px;" class="form-control" data-date-format="dd/mm/yyyy" maxlength="10" onfocus="focusFecha(this);" value="<%=fechaLlegada%>">
			<br>
			<label for="HoraLlegada">Hora llegada</label> (24h/60m/60s)			
			<input type="text" name="HoraLlegada" id="HoraLlegada" style="width:200px;" class="form-control" size="12" maxlength="8" value="<%=horaLlegada%>" /> 
		</div>
		<div class="span3 col">
			<h4>Alimentos requeridos del SAUM:</h4><br>
			<label for="Desayuno">Desayunos</label>			
			<input name="Desayuno" type="number" min="0" step="1" style="width:200px;" class="form-control" id="Desayuno" pattern="\d+" value="<%=salSolicitud.getDesayuno()%>" required="required">
			<br><br>
			<label for="Comida">Comidas</label>					
			<input name="Comida" id="Comida" type="number" min="0" style="width:200px;" class="form-control" step="1" pattern="\d+" value="<%=salSolicitud.getComida()%>" required="required">
			<br><br>
			<label for="Cena">Cenas</label>			
			<input name="Cena" type="number" min="0" step="1" style="width:200px;" class="form-control" id="Cena" pattern="\d+" value="<%=salSolicitud.getCena()%>" required="required">
		</div>
		<div class="span3 col">
			<h4>Costos generales</h4><br>
			<label for="Alimento">Alimento</label>					
			<input name="Alimento" type="number" min="0" step="1" style="width:200px;" class="form-control" id="Alimento" pattern="\d+" value="<%=salSolicitud.getAlimento()%>" required="required">
			<br><br>
			<label for="Hospedaje">Hospedaje</label>					
			<input name="Hospedaje" type="number" min="0" step="1" style="width:200px;" class="form-control" id="Hospedaje" pattern="\d+" value="<%=salSolicitud.getHospedaje()%>" required="required">
			<br><br>
			<label for="Transporte">Transporte</label>					
			<input name="Transporte" type="number" min="0" step="1" style="width:200px;" class="form-control" id="Transporte" pattern="\d+" value="<%=salSolicitud.getTransporte()%>" required="required">
			<br><br>
			<label for="Total">Costo/Total</label>			
			<input name="Total" type="number" min="0" step="1" style="width:200px;" class="form-control" id="Total" pattern="\d+" value="<%=salSolicitud.getTotal()%>" required="required">
			<br><br>
			<label for="TotalPersona">Costo/Persona</label>		
			<input name="TotalPersona" type="number" min="0" style="width:200px;" class="form-control" step="1" id="TotalPersona" pattern="\d+" value="<%=salSolicitud.getTotalPersona()%>" required="required">
			<input name="Fecha" type="hidden" id="Fecha" style="width:200px;" class="form-control" data-date-format="dd/mm/yyyy" size="12" maxlength="10" value="<%=salSolicitud.getFecha()%>" readonly>
		</div>
	</div>
	<br>
	<div class="alert alert-info">
		<a href="javascript:Guardar();" class="btn btn-primary">Grabar</a>&nbsp;
<% if (!mensaje.equals("-")) out.print("  "+mensaje);%>			
	</div>
	</form>
</div>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript"> 
 	jQuery(".chzn-select").chosen();
 	jQuery('#Fecha').datepicker();
 	jQuery('#FechaSalida').datepicker();
 	jQuery('#FechaLlegada').datepicker();

	function refreshEstados(){				
		jQuery('#EstadoId').html('<option>Actualizando</option>');	
		var paisId = document.frmSolicitud.PaisId.value;		
		jQuery.get('getEstados?paisId='+paisId, function(data){
			jQuery("#EstadoId").html(data);			
		});		
	}		
 </script>