<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="BecInformeAlumno" scope="page" class="aca.bec.BecInformeAlumno"/>
<jsp:useBean id="BecInformeAlumnoU" scope="page" class="aca.bec.BecInformeAlumnoUtil"/>

<%	
	String idEjercicio 		= (String) session.getAttribute("ejercicioId");
	String idCcosto 		= (String) session.getAttribute("ccosto");
	String usuario 			= (String) session.getAttribute("codigoPersonal");
	String accion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	int numAccion 			= Integer.parseInt(accion);

	String informeId		= request.getParameter("informeId");
	String puestoId	 		= request.getParameter("puestoId");
	String codigoPersonal 	= request.getParameter("codigoPersonal");	
	String nombreAlumno		= aca.alumno.AlumUtil.getNombreAlumno(conEnoc, codigoPersonal, "NOMBRE");	
	String horasContrato	= request.getParameter("horasContrato");	
	
	String puntualidad 		= request.getParameter("Puntualidad")==null?"0":request.getParameter("Puntualidad");
	String funcion	 		= request.getParameter("Funcion")==null?"0":request.getParameter("Funcion");
	String tiempo 			= request.getParameter("Tiempo")==null?"0":request.getParameter("Tiempo");
	String iniciativa 		= request.getParameter("Iniciativa")==null?"0":request.getParameter("Iniciativa");
	String relacion 		= request.getParameter("Relacion")==null?"0":request.getParameter("Relacion");
	String respeto 			= request.getParameter("Respeto")==null?"0":request.getParameter("Respeto");
	String productivo 		= request.getParameter("Productivo")==null?"0":request.getParameter("Productivo");
	String cuidado 			= request.getParameter("Cuidado")==null?"0":request.getParameter("Cuidado");
	String horas	 		= request.getParameter("Horas")==null?"0":request.getParameter("Horas");
	int horasC				= Integer.parseInt(horas);

	
	
	String tardanzas 		= request.getParameter("Tardanzas")==null?"0":request.getParameter("Tardanzas");
	String ausencias 		= request.getParameter("Ausencias")==null?"0":request.getParameter("Ausencias");
	
	boolean existeInforme = false;
	BecInformeAlumno.setCodigoPersonal(codigoPersonal);
	BecInformeAlumno.setInformeId(informeId);
	if (BecInformeAlumnoU.existeReg(conEnoc, informeId, codigoPersonal)){
		existeInforme = true;
	}
	
	if (numAccion==2){
		
		BecInformeAlumno.setIdEjercicio(idEjercicio);
		BecInformeAlumno.setPuestoId(puestoId);
		BecInformeAlumno.setFecha(aca.util.Fecha.getHoy());
		BecInformeAlumno.setPuntualidad(puntualidad);
		BecInformeAlumno.setFuncion(funcion);
		BecInformeAlumno.setTiempo(tiempo);
		BecInformeAlumno.setIniciativa(iniciativa);
		BecInformeAlumno.setRelacion(relacion);
		BecInformeAlumno.setRespeto(respeto);
		BecInformeAlumno.setProductivo(productivo);
		BecInformeAlumno.setCuidado(cuidado);
		BecInformeAlumno.setHoras(horas);
		BecInformeAlumno.setTardanzas(tardanzas);
		BecInformeAlumno.setAusencias(ausencias);
		BecInformeAlumno.setEstado("1");
		BecInformeAlumno.setIdCcosto(idCcosto);
		BecInformeAlumno.setUsuario(usuario);
				
		if (!existeInforme){
			// Grabar Informe del alumno
			if (BecInformeAlumnoU.insertReg(conEnoc, BecInformeAlumno)){
			}
		}else{
			// Modificar el informe del alumno
			if (BecInformeAlumnoU.updateReg(conEnoc, BecInformeAlumno)){
			}
		}
	}else if(existeInforme){		
		BecInformeAlumno.mapeaRegId(conEnoc, informeId, codigoPersonal);
		
		puntualidad 	= BecInformeAlumno.getPuntualidad();
		funcion			= BecInformeAlumno.getFuncion();
		tiempo			= BecInformeAlumno.getTiempo();
		iniciativa		= BecInformeAlumno.getIniciativa();
		relacion		= BecInformeAlumno.getRelacion();
		respeto			= BecInformeAlumno.getRespeto();
		productivo		= BecInformeAlumno.getProductivo();
		cuidado			= BecInformeAlumno.getCuidado();
		horas			= BecInformeAlumno.getHoras();
		tardanzas		= BecInformeAlumno.getTardanzas();
		ausencias		= BecInformeAlumno.getAusencias();	
		idCcosto		= BecInformeAlumno.getIdCcosto();
		usuario			= BecInformeAlumno.getUsuario();
	}
	
	//String horasAcumuladas	= aca.bec.BecInformeAlumno.getHorasAcumuladas(conEnoc, idEjercicio, idCcosto, codigoPersonal);
	//String horasAcumuladasSinContarActual = aca.bec.BecInformeAlumno.getHorasAcumuladasExcluirActual(conEnoc, idEjercicio, idCcosto, codigoPersonal, informeId);	
	String horasAcumuladas = aca.bec.BecInformeAlumnoUtil.horasEnPuesto(conEnoc, codigoPersonal, puestoId);
	String horasAcumuladasSinContarActual = aca.bec.BecInformeAlumnoUtil.horasEnPuesto(conEnoc, codigoPersonal, puestoId);
	
	
%>
<style>
	body{
		background:white;
	}	
</style>
<body>
<div class="container-fluid">
	<form id="forma" name="forma" action="accion" method="post">
	<input name="Accion" type="hidden">
	<input name="informeId" type="hidden" value="<%=informeId%>">
	<input name="codigoPersonal" type="hidden" value="<%=codigoPersonal%>">
	<input name="puestoId" type="hidden" value="<%=puestoId%>">	
	<input name="horasContrato" type="hidden" value="<%=horasContrato%>">
	<input name="horasAcumuladas" type="hidden" value="<%=horasAcumuladasSinContarActual%>">
	
	<h2>Evaluación del servicio becario <small class="text-muted fs-4"><%= aca.bec.BecInformeUtil.getNombreInforme(conEnoc, informeId) %></small></h2>	
	<div class="alert alert-info">
		Alumno: <%=codigoPersonal%> | <%=nombreAlumno%> &nbsp; &nbsp; Horas: Contrato[ <%=horasContrato%> ] - Acumuladas[ <%=horasAcumuladas%> ]
	</div>	
	<div class="alert alert-info">
		<a href="informe?informeId=<%=informeId%>" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
	</div>	
		<table class="table table-sm">						
			<tr>
				<th width="5%"><spring:message code="aca.Numero"/></th>
				<th width="50%">Aspectos a Evaluar</th>
				<th width="9%">Deficiente</th>
				<th width="9%">Regular</th>
				<th width="9%">Bueno</th>
				<th width="9%">Muy bueno(a)</th>
				<th width="9%">Excelente</th>
			</tr>
			<tr>
				<td align="center" style="border-bottom: dotted 1px gray;">1</td>
				<td style="border-bottom: dotted 1px gray;">LLega puntual a su lugar de servicio becario</td>
				<td style="border-bottom: dotted 1px gray;" align="center">
				  <input type="radio" name="Puntualidad" value="1" <% if(puntualidad.equals("1")) out.print("checked");%>/>
				</td>
				<td style="border-bottom: dotted 1px gray;" align="center">
				  <input type="radio" name="Puntualidad" value="2" <% if(puntualidad.equals("2")) out.print("checked");%>/>
				</td>
				<td style="border-bottom: dotted 1px gray;" align="center">
				  <input type="radio" name="Puntualidad" value="3" <% if(puntualidad.equals("3")) out.print("checked");%>/>
				</td>
				<td style="border-bottom: dotted 1px gray;" align="center">
				  <input type="radio" name="Puntualidad" value="4" <% if(puntualidad.equals("4")) out.print("checked");%>/>
				</td>
				<td style="border-bottom: dotted 1px gray;" align="center">
				  <input type="radio" name="Puntualidad" value="5" <% if(puntualidad.equals("5")) out.print("checked");%>/>
				</td>
			</tr>
			<tr>
			<td align="center" style="border-bottom: dotted 1px gray;">2</td>
				<td style="border-bottom: dotted 1px gray;">Cumple con las funciones que le fueron asignadas</td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Funcion" value="1" <%if(funcion.equals("1")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Funcion" value="2" <%if(funcion.equals("2")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Funcion" value="3" <%if(funcion.equals("3")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Funcion" value="4" <%if(funcion.equals("4")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Funcion" value="5" <%if(funcion.equals("5")) out.print("checked");%>/></td>
			</tr>
			<tr>
			<td align="center" style="border-bottom: dotted 1px gray;">3</td>
				<td style="border-bottom: dotted 1px gray;">Utiliza adecuadamente el tiempo en actividades propias de su trabajo</td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Tiempo" value="1" <%if(tiempo.equals("1")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Tiempo" value="2" <%if(tiempo.equals("2")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Tiempo" value="3" <%if(tiempo.equals("3")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Tiempo" value="4" <%if(tiempo.equals("4")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Tiempo" value="5" <%if(tiempo.equals("5")) out.print("checked");%>/></td>
			</tr>
			<tr>
			<td align="center" style="border-bottom: dotted 1px gray;">4</td>
				<td style="border-bottom: dotted 1px gray;">Muestra iniciativa para innovar	y mejorar procesos de trabajo</td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Iniciativa" value="1" <%if(iniciativa.equals("1")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Iniciativa" value="2" <%if(iniciativa.equals("2")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Iniciativa" value="3" <%if(iniciativa.equals("3")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Iniciativa" value="4" <%if(iniciativa.equals("4")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Iniciativa" value="5" <%if(iniciativa.equals("5")) out.print("checked");%>/></td>
			</tr>
			<tr>
			<td align="center" style="border-bottom: dotted 1px gray;">5</td>
				<td style="border-bottom: dotted 1px gray;">Mantiene buenas relaciones interpersonales con sus compañeros</td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Relacion" value="1" <%if(relacion.equals("1")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Relacion" value="2" <%if(relacion.equals("2")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Relacion" value="3" <%if(relacion.equals("3")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Relacion" value="4" <%if(relacion.equals("4")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Relacion" value="5" <%if(relacion.equals("5")) out.print("checked");%>/></td>
			</tr>
			<tr>
			<td align="center" style="border-bottom: dotted 1px gray;">6</td>
				<td style="border-bottom: dotted 1px gray;">Es respetuoso con sus superiores</td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Respeto" value="1" <%if(respeto.equals("1")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Respeto" value="2" <%if(respeto.equals("2")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Respeto" value="3" <%if(respeto.equals("3")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Respeto" value="4" <%if(respeto.equals("4")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Respeto" value="5" <%if(respeto.equals("5")) out.print("checked");%>/></td>
			</tr>
			<tr>
			<td align="center" style="border-bottom: dotted 1px gray;">7</td>
				<td style="border-bottom: dotted 1px gray;">Es productivo en lo que hace</td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Productivo" value="1" <%if(productivo.equals("1")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Productivo" value="2" <%if(productivo.equals("2")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Productivo" value="3" <%if(productivo.equals("3")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Productivo" value="4" <%if(productivo.equals("4")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Productivo" value="5" <%if(productivo.equals("5")) out.print("checked");%>/></td>
			</tr>
			<tr>
			<td align="center" style="border-bottom: dotted 1px gray;">8</td>
				<td style="border-bottom: dotted 1px gray;">Cuida adecuadamente las herramientas y equipo de trabajo a su cargo</td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Cuidado" value="1" <%if(cuidado.equals("1")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Cuidado" value="2" <%if(cuidado.equals("2")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Cuidado" value="3" <%if(cuidado.equals("3")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Cuidado" value="4" <%if(cuidado.equals("4")) out.print("checked");%>/></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" name="Cuidado" value="5" <%if(cuidado.equals("5")) out.print("checked");%>/></td>
			</tr>
			<tr>
			<td align="center" style="border-bottom: dotted 1px gray;">9</td>
				<td style="border-bottom: dotted 1px gray;">Horas cumplidas en el mes</td>
				<td><input id="Horas" name="Horas" type="number" value="<%=horas%>" class="input input-small"></td>
			</tr>
			<tr>
			<td align="center" style="border-bottom: dotted 1px gray;">10</td>
				<td style="border-bottom: dotted 1px gray;">Número de tardanzas</td>
				<td><input id="Tardanzas" name="Tardanzas" type="number" value="<%=tardanzas%>" class="input input-small"></td>
			</tr>
			<tr>
			<td align="center" style="border-bottom: dotted 1px gray;">11</td>
				<td style="border-bottom: dotted 1px gray;">Número de ausencias</td>
				<td><input id="Ausencias" name="Ausencias" type="number" value="<%=ausencias%>" class="input input-small"></td>
			</tr>			
			<tr>
				<td colspan="10" style="text-align:right;">
					<a href="javascript:Grabar()" class="btn btn-primary btn-large"><i class="icon-ok icon-white"></i> Grabar</a>
				</td>
			</tr>			
		</table>
	</form>
</div>	
</body>
<script>
	function Grabar(){
		var suma = parseInt(document.forma.horasAcumuladas.value) + parseInt(document.forma.Horas.value);
		var total = parseInt(document.forma.horasContrato.value);
		var diferencia = total- parseInt(document.forma.horasAcumuladas.value);
		if( suma > total ){
			alert("Horas contrato:"+total+" Horas acumuladas:"+parseInt(document.forma.horasAcumuladas.value)+". \nEl numero de horas reportadas no puede ser mayor a: "+diferencia);
		}else{
			if( document.forma.Puntualidad.value 		!= "" &&
					document.forma.Funcion.value 			!= "" &&
					document.forma.Tiempo.value 			!= "" &&
					document.forma.Iniciativa.value 		!= "" &&
					document.forma.Relacion.value 			!= "" &&
					document.forma.Respeto.value 			!= "" &&
					document.forma.Productivo.value 		!= "" &&
					document.forma.Cuidado.value 			!= "" &&
					document.forma.Horas.value 				!= "" &&
					document.forma.Tardanzas.value 			!= "" &&
					document.forma.Ausencias.value !=""){			
					document.forma.Accion.value = "2";
					document.forma.submit();
						
			}else{
				alert("Todos los campos son requeridos");
			}
		}
		
	}	

</script>
<%@ include file= "../../cierra_enoc.jsp" %>