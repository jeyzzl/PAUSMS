<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguroJquery.jsf" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="HospitalU" scope="page" class="aca.rotaciones.RotHospitalUtil"/>
<jsp:useBean id="HospEspecialidadU" scope="page" class="aca.rotaciones.RotHospitalEspecialidadUtil"/>
<jsp:useBean id="ProgramacionU" scope="page" class="aca.rotaciones.RotProgramacionUtil"/>

<jsp:useBean id="Programacion" scope="page" class="aca.rotaciones.RotProgramacion"/>
<jsp:useBean id="Fecha" scope="page" class="aca.util.Fecha"/>

<head>
<%
	String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion");

	ArrayList<aca.rotaciones.RotHospital> hospitales = HospitalU.getListHospitales(conEnoc, "ORDER BY 1");
	
	String fecha = request.getParameter("Fecha")==null?"":request.getParameter("Fecha");
	//Subir hospital y la especialidad
	if(accion.equals("1")){
		session.setAttribute("rotHospitalId", request.getParameter("HospitalId"));
	}else if(accion.equals("2")){
		session.setAttribute("rotEspecialidadId", request.getParameter("EspecialidadId"));
	}
	//-->
	
	if(session.getAttribute("rotHospitalId")==null){
		session.setAttribute("rotHospitalId", hospitales.get(0).getHospitalId());
	}
	
	String hospitalId 		= (String)session.getAttribute("rotHospitalId");
	
	ArrayList<aca.rotaciones.RotHospitalEspecialidad> especialidades = HospEspecialidadU.getListHospActivas(conEnoc, hospitalId, "ORDER BY 1");
	
	if(session.getAttribute("rotEspecialidadId")==null){
		session.setAttribute("rotEspecialidadId", especialidades.get(0).getEspecialidadId());
	}
	
	String especialidadId 	= (String)session.getAttribute("rotEspecialidadId");
	boolean encontrado = false;
	for(aca.rotaciones.RotHospitalEspecialidad esp : especialidades){
		if(esp.getEspecialidadId().equals(especialidadId)){
			encontrado = true;
			break;
		}
	}
	if(!encontrado){
		especialidadId = especialidades.get(0).getEspecialidadId();
	}
	
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;(###,##0.00)");
	String msj = "";
	if(accion.equals("3")){
		ArrayList<aca.rotaciones.RotProgramacion> prog = ProgramacionU.getListHospEspConAlumnosFecha(conEnoc, hospitalId, especialidadId, fecha, "ORDER BY 1 desc");
		if(prog.size()==0){
			msj = "No se encontraron registros para grabar";
		}else{
			String inscripcion = getFormato.format(Float.parseFloat(request.getParameter("inscripcion")));
			String anual 	   = getFormato.format(Float.parseFloat(request.getParameter("anual")));
			String integrada   = getFormato.format(Float.parseFloat(request.getParameter("integrada")));
			String pago 	   = getFormato.format(Float.parseFloat(request.getParameter("pago")));
			
			int cont = 0;
			for(aca.rotaciones.RotProgramacion pr : prog){ 
				pr.setInscripcion(inscripcion);
				pr.setAnual(anual);
				pr.setIntegrada(integrada);
				pr.setPago(pago);
				
				if(pr.updateReg(conEnoc)){
					cont++;
				}
			}
			msj = "se grabaron "+cont+" registros";
		}
	}
	
	ArrayList<aca.rotaciones.RotProgramacion> programaciones;
	
	if(fecha.equals("")){
		programaciones = ProgramacionU.getListHospEspConAlumnos(conEnoc, hospitalId, especialidadId, "ORDER BY 1 desc");
	}else{
		programaciones = ProgramacionU.getListHospEspConAlumnosFecha(conEnoc, hospitalId, especialidadId, fecha, "ORDER BY 1 desc");
	}
	
	String semanas = aca.rotaciones.RotEspecialidad.getSemanas(conEnoc, especialidadId);
%>
<script>
	function subirHospitalId(){
		location.href="pago?Accion=1&HospitalId="+jQuery('.hospitalId').val();
	}
	function subirEspecialidadId(){
		location.href="pago?Accion=2&EspecialidadId="+jQuery('.EspecialidadId').val();
	}
	function fecha(){
		location.href="pago?Fecha="+jQuery('#dp2').val();
	}
	function calcular(){
		var inscripcion = jQuery('#inscripcion');
		var anual 		= jQuery('#anual');
		var integrada   = jQuery('#integrada');
		var porcentaje  = jQuery('#porcentaje');
		var valPorcentaje = porcentaje[0].options[porcentaje[0].selectedIndex].value;
		var pago        = jQuery('#pago');
		
		if(jQuery.isNumeric(inscripcion.val()) && jQuery.isNumeric(anual.val())){
			integrada.val( parseFloat(inscripcion.val()) + parseFloat(anual.val()) );
			
			pago.val( (parseFloat(integrada.val()) * parseFloat(valPorcentaje)) * parseFloat('<%=semanas%>') );
		}
	}
	function grabar(fecha){
		var inscripcion = jQuery('#inscripcion');
		var anual 		= jQuery('#anual');
		
		if(anual.val()!="" && inscripcion!= ""){
			if(fecha != ""){
				document.forma.Accion.value = "3";
				document.forma.submit();
			}else{
				alert('favor de filtrar por alguna fecha');
			}
		}else{
			alert('favor de llenar todos los campos');
		}
	}
</script>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<style>
	span{
		font-size: 16px;
		display:inline;
		vertical-align:middle;
	}
</style>
</head>
<body>
<table style="margin: 0 auto;">
	<tr>
		<td style="text-align:center;">
			<h2>Programación de Rotaciones</h2>
		</td>
	</tr>
</table>
<table style="margin: 0 auto;" class="table table-condensed table-nohover" style="padding:10px;border:1px solid #D4D4D4;border-radius:2px;">
	<tr>
		<td><h5>Hospital:</h5>
			<select class="HospitalId" style="width:400px;" onchange="subirHospitalId();">
				<%for(aca.rotaciones.RotHospital hospi: hospitales){ %>
					<option <%if(hospitalId.equals(hospi.getHospitalId()))out.print("selected");%> value="<%=hospi.getHospitalId() %>"><%=hospi.getHospitalNombre() %></option>
				<%} %>
			</select>
		</td>
	</tr>
	<tr>
		<td><h5>Especialidad:</h5>
			<select class="EspecialidadId" style="width:400px;" onchange="subirEspecialidadId();">
				<%for(aca.rotaciones.RotHospitalEspecialidad esp: especialidades){ %>
					<option <%if(especialidadId.equals(esp.getEspecialidadId()))out.print("selected");%> value="<%=esp.getEspecialidadId() %>"><%=aca.rotaciones.RotEspecialidad.getNombre(conEnoc, esp.getEspecialidadId()) %> &nbsp;&nbsp;&nbsp;(<%=aca.rotaciones.RotEspecialidad.getSemanas(conEnoc, esp.getEspecialidadId())%>  Semanas)</option>
				<%} %>
			</select>
		</td>
	</tr>
	<tr>
		<td>
			<h5><spring:message code="aca.FechaInicio"/>:</h5>
			<input name="fechaInicio" type="text" value="<%=fecha.equals("")?aca.util.Fecha.getHoy():fecha%>" data-date-format="dd/mm/yyyy" id="dp2" >
			<%if(fecha.equals("")){ %>
				<a class="btn btn-primary" onclick="fecha();" ><i class="icon-calendar"></i> Filtrar por Fecha</a>
			<%}else{ %>
				<a class="btn btn-primary" onclick="fecha();" ><i class="icon-calendar"></i> Filtrar por Fecha</a>
				<a class="btn btn-primary" onclick="location.href='pago'" ><i class="fas fa-trash-alt"></i> No Filtrar por Fecha</a>
			<%} %>
		</td>
	</tr>
	<%if(!msj.equals("")){ %>
		<tr>
		<td style="text-align:center;letter-spacing:3px;">
			<font color="#7E1E1F" size="3"><%=msj %></font>
		</td>
	</tr>
	<%} %>
</table>

<form name="forma" action="pago">
<input type="hidden" name="Accion">
<input type="hidden" name="Fecha" value="<%=fecha%>">
<table  width="80%" align="center" class="table table-condensed table-bordered table-striped">
	 <tr>
	 	<td colspan="10" style="text-align:center;">
	 		<span>Inscripción:</span> <input class="input-small" type="text" value="" name="inscripcion" id="inscripcion" maxlength="8" onKeypress="if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;" onkeyup="calcular();"/>&nbsp;&nbsp;&nbsp;
	 		<span>Anual:</span> <input class="input-small" type="text" value="" name="anual" id="anual" maxlength="8" onKeypress="if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;" onkeyup="calcular();" />&nbsp;&nbsp;&nbsp;
	 		<span>Integrada:</span> <input class="input-small" type="text" value="" name="integrada" id="integrada" maxlength="8" readonly/>&nbsp;&nbsp;&nbsp;
	 		<select onchange="calcular();" name="porcentaje" id="porcentaje" class="input-small">
	 			<option value="0.002">0.002</option>
	 			<option value="0.003">0.003</option>
	 			<option value="0.004">0.004</option>
	 		</select>&nbsp;&nbsp;&nbsp;
	 		<span>Pago:</span> <input class="input-small" type="text" value="" name="pago" id="pago" maxlength="8" readonly/>&nbsp;&nbsp;&nbsp;
	 		<a class="btn btn-primary" onclick="grabar('<%=fecha%>');"><spring:message code="aca.Grabar"/></a>
	 	</td>
	 </tr>
	 <tr> 
		<th>#</th>
		<th><spring:message code="aca.Codigo"/> Personal</th>
		<th><spring:message code="aca.Nombre"/></th>
		<th><spring:message code="aca.FechaInicio"/></th>
		<th><spring:message code='aca.FechaFinal'/></th>
		<th><spring:message code="aca.Estado"/></th>
		<th>Inscripción</th>
		<th>Anual</th>
		<th>Integrada</th>
		<th>Pago</th>
	</tr>
	<%
	int cont = 1;
	for(aca.rotaciones.RotProgramacion prog : programaciones){ 
		String estado = prog.getCodigoPersonal()==null?"Vacia":"Ocupada";
		 
		String hide = "";
		if(estado.equals("Vacia")){
			hide = "style='display:none;'";
		}
	%>
		<tr>
			<td align="center"><%=cont%></td>
			<td align="center"><%=prog.getCodigoPersonal()==null?"&nbsp;":prog.getCodigoPersonal() %></td>
			<td><%=aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, prog.getCodigoPersonal(), "NOMBRE").equals("0000000")?"&nbsp;":aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, prog.getCodigoPersonal(), "NOMBRE") %></td>
			<td align="center"><%=prog.getfInicio() %></td>
			<td align="center"><%=prog.getfFinal() %></td>
			<td align="center"><%=estado %></td>
			<td align="center"><%=prog.getInscripcion() %></td>
			<td align="center"><%=prog.getAnual() %></td>
			<td align="center"><%=prog.getIntegrada() %></td>
			<td align="center"><%=prog.getPago() %></td>
		</tr>
	<%
		cont++;
	} %>
</table>
</form>
<script>

jQuery('#dp2').datepicker();

</script>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>