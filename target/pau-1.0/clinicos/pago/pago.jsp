<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguroJquery.jsf" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="InstitucionU" scope="page" class="aca.rotaciones.RotInstitucionUtil"/>
<jsp:useBean id="ProgramacionU" scope="page" class="aca.rotaciones.RotProgramacionUtil"/>
<jsp:useBean id="Programacion" scope="page" class="aca.rotaciones.RotProgramacion"/>
<jsp:useBean id="Fecha" scope="page" class="aca.util.Fecha"/>

<head>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<script>
	function subirInstitucion(){
		location.href="pago?Accion=1&InstitucionId="+jQuery('.InstitucionId').val();
	}
	function fecha(){
		location.href="pago?FechaInicio="+jQuery('#dp2').val()+"&FechaFinal="+jQuery('#dp3').val();
	}
	function calcular(){
		var inscripcion = jQuery('#inscripcion');
		var anual 		= jQuery('#anual');
		var integrada   = jQuery('#integrada');
		//var porcentaje  = jQuery('#porcentaje');
		//var valPorcentaje = porcentaje[0].options[porcentaje[0].selectedIndex].value;
		
		if(jQuery.isNumeric(inscripcion.val()) && jQuery.isNumeric(anual.val())){
			integrada.val( parseFloat(inscripcion.val()) + parseFloat(anual.val()) );
		}
	}
	function grabar(){
		var inscripcion = jQuery('#inscripcion');
		var anual 		= jQuery('#anual');
		
		if(anual.val()!="" && inscripcion!= ""){
			document.forma.Accion.value = "2";
			document.forma.submit();
		}else{
			alert('favor de llenar todos los campos');
		}
	}
</script>
<%
	ArrayList<aca.rotaciones.RotInstitucion> instituciones = InstitucionU.getListAll(conEnoc, "ORDER BY 1");

	String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion"); 
	if(accion.equals("1")){
		session.setAttribute("institucionId", request.getParameter("InstitucionId"));
	}

	String institucionId = (String) session.getAttribute("institucionId");
	
	if(institucionId==null){
		institucionId = instituciones.get(0).getInstitucionId();
	}
	
	String fechaInicio = request.getParameter("FechaInicio")==null?"":request.getParameter("FechaInicio");
	String fechaFinal  = request.getParameter("FechaFinal")==null?"":request.getParameter("FechaFinal");
		
	ArrayList<aca.rotaciones.RotProgramacion> programaciones;
	
	if(fechaInicio.equals("")){
		programaciones = ProgramacionU.getListInstitucion(conEnoc, institucionId, "ORDER BY 1 desc");
	}else{
		programaciones = ProgramacionU.getListInstitucionFechas(conEnoc, institucionId, fechaInicio, fechaFinal, "ORDER BY 1 desc");	
	}
	
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;(###,##0.00)");
	String msj = "";
	if(accion.equals("2")){
		if(programaciones.size()==0){
			msj = "No se encontraron registros para grabar";
		}else{
			String inscripcion = getFormato.format(Float.parseFloat(request.getParameter("inscripcion")));
			String anual 	   = getFormato.format(Float.parseFloat(request.getParameter("anual")));
			String integrada   = getFormato.format(Float.parseFloat(request.getParameter("integrada")));
			
			int cont = 0;
			for(aca.rotaciones.RotProgramacion pr : programaciones){ 
				pr.setInscripcion(inscripcion);
				pr.setAnual(anual);
				pr.setIntegrada(integrada);
								
				String pago = getFormato.format( (Float.parseFloat(request.getParameter("integrada"))*Float.parseFloat(request.getParameter("porcentaje"))) * Float.parseFloat(aca.rotaciones.RotEspecialidad.getSemanas(conEnoc, pr.getEspecialidadId())) );
				pr.setPago(pago);
			
				
				if(pr.updateReg(conEnoc)){
					cont++;
				}
			}
			msj = "se grabaron "+cont+" registros";
			
			if(fechaInicio.equals("")){
				programaciones = ProgramacionU.getListInstitucion(conEnoc, institucionId, "ORDER BY 1 desc");
			}else{
				programaciones = ProgramacionU.getListInstitucionFechas(conEnoc, institucionId, fechaInicio, fechaFinal, "ORDER BY 1 desc");	
			}
		}
	}
	
	java.util.HashMap<String,String> alumnos =  aca.vista.UsuariosUtil.getMapAlumnosNombre(conEnoc, "ORDER BY 1"); 
	java.util.HashMap<String,String> especialidades = aca.rotaciones.RotEspecialidad.getNombres(conEnoc);
	java.util.HashMap<String,String> hospitales = aca.rotaciones.RotHospital.getNombres(conEnoc);
%>
</head>

<body>
<div class="container-fluid">
<h1>Pago de Rotaciones</h1>
<div class="alert alert-info d-flex align-items-center"></div>
<table class="table table-condensed table-nohover" style="padding:10px;border:1px solid #D4D4D4;border-radius:2px;">
	<tr>
		<td colspan="2"><h5><spring:message code="aca.Institucion"/>es:</h5>
			<select class="InstitucionId" style="width:400px;" onchange="subirInstitucion();">
				<%for(aca.rotaciones.RotInstitucion inst: instituciones){ %>
					<option <%if(institucionId.equals(inst.getInstitucionId()))out.print("selected");%> value="<%=inst.getInstitucionId() %>"><%=inst.getInstitucionNombre() %></option>
				<%} %>
			</select>
		</td>
	</tr>
	<tr>
		<td align="center">
			<h5><spring:message code="aca.FechaInicio"/>:</h5>
		</td>
		<td>
			<h5>Fecha Fin:</h5>
		</td>
	</tr>
		<td>
			<input name="fechaInicio" type="text" class="form-control" style="width:550px"  value="<%=fechaInicio.equals("")?aca.util.Fecha.getHoy():fechaInicio%>" data-date-format="dd/mm/yyyy" id="dp2" >
		</td>
		<td>	
			<input name="fechaFinal" type="text" class="form-control" style="width:550px" value="<%=fechaFinal.equals("")?aca.util.Fecha.getHoy():fechaFinal%>" data-date-format="dd/mm/yyyy" id="dp3" >
			<%if(fechaInicio.equals("")){ %>
				<a class="btn btn-primary" onclick="fecha();" ><i class="icon-calendar"></i> Filtrar por Periodo</a>
			<%}else{ %>
				<a class="btn btn-primary" onclick="fecha();" ><i class="icon-calendar"></i> Filtrar por Periodo</a>
				<a class="btn btn-primary" onclick="location.href='pago'" ><i class="fas fa-trash-alt"></i> No Filtrar por Periodo</a>
			<%} %>
		</td>
	</tr>
	<%if(!msj.equals("")){ %>
		<tr>
		<td style="text-align:center;letter-spacing:3px;" colspan="2">
			<font color="#7E1E1F" size="3"><%=msj %></font>
		</td>
	</tr>
	<%} %>
</table>
<form name="forma" action="pago">
<input type="hidden" name="Accion">
<input type="hidden" name="FechaInicio" value="<%=fechaInicio%>">
<input type="hidden" name="FechaFinal" value="<%=fechaFinal%>">
<table  width="98%" class="table table-condensed table-bordered table-striped table-fontsmall">
	 <tr>
	 	<td colspan="11" style="text-align:center;">
	 		<span>Inscripción:</span> <input class="input-small" type="text" class="form-control"  value="" name="inscripcion" id="inscripcion" maxlength="8" onKeypress="if (window.event.keyCode < 48 || window.event.keyCode > 57) window.event.returnValue = false;" onkeyup="calcular();"/>&nbsp;&nbsp;&nbsp;
	 		<span>Anual:</span> <input class="input-small" type="text" value="" name="anual" class="form-control" id="anual" maxlength="8" onKeypress="if (window.event.keyCode < 48 || window.event.keyCode > 57) window.event.returnValue = false;" onkeyup="calcular();" />&nbsp;&nbsp;&nbsp;
	 		<span>Integrada:</span> <input class="input-small" type="text" value="" name="integrada"  id="integrada" maxlength="8" readonly/>&nbsp;&nbsp;&nbsp;
	 		<select onchange="calcular();" name="porcentaje" id="porcentaje" class="input-small">
	 			<option value="0.002">0.002</option>
	 			<option value="0.003">0.003</option>
	 			<option value="0.004">0.004</option>
	 		</select>&nbsp;&nbsp;&nbsp;
	 		<a class="btn btn-primary" onclick="grabar();"><spring:message code="aca.Grabar"/></a>
	 	</td>
	 </tr>
	 <tr> 
		<th>#</th>
		<th><spring:message code="aca.Matricula"/></th>
		<th><spring:message code="aca.Nombre"/></th>
		<th>Especialidad</th>
		<th><spring:message code="aca.FechaInicio"/></th>
		<th><spring:message code='aca.FechaFinal'/></th>
		<th>Inscripción</th>
		<th>Anual</th>
		<th>Integrada</th>
		<th>Pago</th>
		<th>Hospital</th>
	</tr>
	<%
	int cont = 1;
	for(aca.rotaciones.RotProgramacion prog : programaciones){ 
		 
		String alumno = "0000000";
		if(alumnos.containsKey(prog.getCodigoPersonal())){
			alumno = alumnos.get(prog.getCodigoPersonal());
		}
	%>
		<tr>
			<td align="center"><%=cont%></td>
			<td align="center"><%=prog.getCodigoPersonal()==null?"&nbsp;":prog.getCodigoPersonal() %></td>
			<td><%= alumno.equals("0000000")?"&nbsp;":alumno %></td>
			<td align="center"><%=especialidades.get( prog.getEspecialidadId() ) %></td>
			<td align="center"><%=prog.getfInicio() %></td>
			<td align="center"><%=prog.getfFinal() %></td>
			<td align="center"><%=prog.getInscripcion() %></td>
			<td align="center"><%=prog.getAnual() %></td>
			<td align="center"><%=prog.getIntegrada() %></td>
			<td align="center"><%=prog.getPago() %></td>
			<td align="center"><%=hospitales.get(prog.getHospitalId()) %></td>
		</tr>
	<%
		cont++;
	} %>
</table>
</form>
</div>
</body>
<script>
jQuery('#dp2').datepicker();
jQuery('#dp3').datepicker();
</script>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>