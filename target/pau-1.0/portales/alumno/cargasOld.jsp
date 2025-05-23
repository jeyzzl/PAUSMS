<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.Date"%>
<%@page import="aca.util.Fecha"%>

<jsp:useBean id="lineaU" scope="page" class="aca.carga.CargaEnlineaUtil"/>
<jsp:useBean id="enlinea" scope="page" class="aca.carga.CargaEnlinea"/>
<jsp:useBean id="AlumnoEstado" scope="page" class="aca.alumno.AlumEstado"/>
<jsp:useBean id="AlumEnlinea" scope="page" class="aca.alumno.AlumEnlinea"/>
<jsp:useBean id="AlumEnlineaU" scope="page" class="aca.alumno.AlumEnlineaUtil"/>

<%@ include file= "menu.jsp" %>

<head>
	<script>
		function refrescar(){
			document.forma.Accion.value="1";
			document.forma.submit();	
		}
		function siguiente(){			
			document.forma.Accion.value="2";
			document.forma.submit();	
		}
		function mostrarComentario(){
			jQuery("#popupContact").css({
				"left": "30%",
				"height": "45%",
				"width": "40%"
			});
			jQuery("#popup").css("height","100%");
			window.popup.location='cargas?com=1';
		}
	</script>
</head>
<body>
<%
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
	String matricula			= (String) session.getAttribute("codigoAlumno");

	session.removeAttribute("enLineaSiguiente");

	if(codigoPersonal.equals(matricula) && (((String)session.getAttribute("enLineaActivo"))!=null)){
		String cargaCambia		= request.getParameter("Carga");
		if(cargaCambia==null){
			cargaCambia = (String) session.getAttribute("cargaId");
			enlinea.setCargaId(cargaCambia);
			if(lineaU.existeReg(conEnoc, cargaCambia)) enlinea.mapeaRegId(conEnoc, cargaCambia);
		}else{
			enlinea.mapeaRegId(conEnoc, cargaCambia);
		}
	
		String descripcion 	= "";
		String accion 		= request.getParameter("Accion")==null?"":request.getParameter("Accion");
		
		ArrayList<aca.carga.CargaEnlinea> lisCargas	=  lineaU.getListActivas(conEnoc, "ORDER BY CARGA_ID");
	
		AlumEnlinea.mapeaRegId(conEnoc, matricula, cargaCambia);
		boolean solicitudEnviada = AlumEnlinea.getSolicitud()!=null&&!AlumEnlinea.getSolicitud().equals("");
	
		if(accion.equals("1")){
			enlinea.mapeaRegId(conEnoc, request.getParameter("Carga"));
		}
		else if(accion.equals("2")){
			session.setAttribute("enLineaSiguiente", "1");			
			response.sendRedirect((solicitudEnviada?"confirmar":"radiografia")+".jsp?CargaId="+cargaCambia);
		}
		
		if(AlumEnlinea.getEstado()!=null&&AlumEnlinea.getEstado().equals("S")){
			AlumEnlinea.setEstado("");
			AlumEnlineaU.updateReg(conEnoc, AlumEnlinea);
		}
	
		String nivelCarrera = aca.catalogo.CatCarreraUtil.getNivelId(conEnoc, aca.alumno.PlanUtil.getCarreraId(conEnoc, matricula));
		if(nivelCarrera.equals("1") || nivelCarrera.equals("2") || nivelCarrera.equals("5")) aca.alumno.PlanUtil.actualizaCicloAlumno(conEnoc, matricula);
		else aca.alumno.PlanUtil.actualizaCicloAlumnoPostgrado(conEnoc, matricula);
		aca.alumno.PlanUtil.actualizaGradoAlumno(conEnoc, matricula);
%>
<div class="container-fluid">	
	<h2>Elige el periodo <small>(*)</small></h2>
	<form action="cargas" method="post" name="forma" target="_self">
	<input type="hidden" name="Accion">
	<div class="alert alert-info">
		<a href="validacion" class="btn btn-primary">Regresar</a>&nbsp;&nbsp;
		<b>Periodo:</b>
		<select name="Carga" id="Carga" onchange='javascript:refrescar()' style="width:350px">
<%	boolean selecciono = false; 
	int disponibles = 0;
	String cargaTmp = "";
	for (int i=0; i< lisCargas.size(); i++){
		aca.carga.CargaEnlinea carga = (aca.carga.CargaEnlinea) lisCargas.get(i);
		AlumnoEstado.mapeaRegId(conEnoc, (String)session.getAttribute("codigoAlumno"), carga.getCargaId());
		//if(!AlumnoEstado.getEstado().equals("I")){
			disponibles++;
			if(cargaTmp.equals(""))cargaTmp = carga.getCargaId(); %>
			<option <%if(cargaCambia.equals(carga.getCargaId())){out.print(" Selected ");selecciono=true;}%> value="<%=carga.getCargaId()%>"><%=carga.getNombre()%></option>
<%		//}
	}
	if(!selecciono){
		cargaCambia = cargaTmp;
		enlinea.mapeaRegId(conEnoc, cargaCambia);
	}
%>
		</select>
	</div>
		
<%	if(request.getParameter("com")==null){ %>			
	
					
	<table class="table table-condensed" style="width:60%;">		
<%		String siguiente = "";
		if(disponibles>0){
%>
		<tr>
			<td>&nbsp;</td>
			<td><font size="3"><b>Descripción:</b></font></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><font size="3"><%=enlinea.getDescripcion() %></font></td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td>&nbsp;</td>
			<td>
	<%
			int yearIni = Integer.parseInt(enlinea.getFInicio().substring(6, 10));
			int mesIni 	= Integer.parseInt(enlinea.getFInicio().substring(3, 5));
			int diaIni	= Integer.parseInt(enlinea.getFInicio().substring(0, 2));
			Date fechaIni = new Date(yearIni, mesIni, diaIni);
			
			int yearFin = Integer.parseInt(enlinea.getFFinal().substring(6, 10));
			int mesFin 	= Integer.parseInt(enlinea.getFFinal().substring(3, 5));
			int diaFin	= Integer.parseInt(enlinea.getFFinal().substring(0, 2));
			Date fechaFin = new Date(yearFin, mesFin, diaFin);
			
			String fechaActual 	= Fecha.getHoy();
			int yearAct = Integer.parseInt(fechaActual.substring(6, 10));
			int mesAct 	= Integer.parseInt(fechaActual.substring(3, 5));
			int diaAct	= Integer.parseInt(fechaActual.substring(0, 2));
			Date fechaAct = new Date(yearAct, mesAct, diaAct);
			
			if((fechaIni.equals(fechaAct) || fechaIni.before(fechaAct)) && (fechaAct.equals(fechaFin) || fechaAct.before(fechaFin))){
				siguiente = "es";
			}
			else if(fechaFin.before(fechaAct)){
				siguiente = "fue";
			}
			else if(fechaIni.after(fechaAct)){
				siguiente = "será";
			}
		%>
				<h4>La fecha de inscripción para este periodo <%=siguiente%> del <b><%=Fecha.getFechaOficial(enlinea.getFInicio())%> al <%=Fecha.getFechaOficial(enlinea.getFFinal())%></b></h4> 
			</td>
		</tr>
	<%	} %>
		<tr><td>&nbsp;</td></tr>
	<% 	String menBoton = "Inscribete";
		String comentario = AlumEnlinea.getComentarios()==null?"":AlumEnlinea.getComentarios();
		if(solicitudEnviada) menBoton = "Confirmar";
		if(solicitudEnviada){
			if(AlumEnlinea.getSolicitud().equals("A")){
				 menBoton = "Continuar"; %>
				<tr>
					<td align="center" colspan="2">
						<font size="4" color="green">Tu solicitud ha sido  <%=comentario.equals("")?"autorizada":"<a class='clickButton2' href='javascript:mostrarComentario();'>autorizada</a>" %>, ahora puedes continuar<br> con tus arreglos financieros.</font>
					</td>
				</tr>
		<%	}
			else if(AlumEnlinea.getSolicitud().equals("N")){
			 	menBoton = "Carga de materias"; %>
				<tr>
					<td align="center" colspan="2">
						<font size="4" color="#AE2113">
							Tu coordinador <a class='clickButton2' href='javascript:mostrarComentario();'>no autorizó</a> tu solicitud para inscribirte en línea,<br>
							realiza tu inscripción presencialmente.
						</font>
						<br>
					</td>
				</tr>
		<%	}
			else if(AlumEnlinea.getSolicitud().equals("E")){
				 menBoton = "Continuar"; %>
				<tr>
					<td align="center" colspan="2">
						<font size="4" color="gray">Esperando confirmación del coordinador.</font>
					</td>
				</tr>
<%			}
		}
%>				
	</table>
	</form>
<%	
		if((siguiente.equals("es"))&&disponibles>0){
%>
	<div class="alert alert-info">				
		<input type="button" class="btn btn-primary" value="<%=menBoton%>" onclick="javascript:siguiente();" >				
	</div>
<%		}%>
</body>
<%	}else{%>
	<table class="table table-noHover" align="center" width="90%">
	<tr><th>Comentario del coordinador</th></tr>
	<tr><td><textarea cols="60" rows="6" disabled="disabled"><%=AlumEnlinea.getComentarios()%></textarea></td></tr>
	</table>
<% 	}
	}%>
<%@ include file= "../../cierra_enoc.jsp" %>