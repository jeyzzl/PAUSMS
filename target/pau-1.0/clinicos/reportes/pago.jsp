<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguroJquery.jsf" %>
<%@ include file= "../../body.jsp" %>
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
	
	java.util.HashMap<String,String> alumnos =  aca.vista.UsuariosUtil.getMapAlumnosNombre(conEnoc, "ORDER BY 1");
	java.util.HashMap<String,String> especialidades = aca.rotaciones.RotEspecialidad.getNombres(conEnoc);
	java.util.HashMap<String,String> hospitales = aca.rotaciones.RotHospital.getNombres(conEnoc);
%>
</head>

<body style="background:white;">
<div class="oculto well" style="margin:5px;padding:5px;">
<a href="menu" class="btn btn-primary" style="left:10px;position:absolute;">regresar</a>
<table style="margin: 0 auto;" class="table table-condensed table-nohover" style="margin:0 auto;padding:10px;border:1px solid #D4D4D4;border-radius:2px;">
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
	<tr>
		<td>
			<input name="fechaInicio" type="text" value="<%=fechaInicio.equals("")?aca.util.Fecha.getHoy():fechaInicio%>" data-date-format="dd/mm/yyyy" id="dp2" >
		</td>
		<td>	
			<input name="fechaFinal" type="text" value="<%=fechaFinal.equals("")?aca.util.Fecha.getHoy():fechaFinal%>" data-date-format="dd/mm/yyyy" id="dp3" >
			<%if(fechaInicio.equals("")){ %>
				<a class="btn btn-primary" onclick="fecha();" ><i class="icon-calendar"></i> Filtrar por Periodo</a>
			<%}else{ %>
				<a class="btn btn-primary" onclick="fecha();" ><i class="icon-calendar"></i> Filtrar por Periodo</a>
				<a class="btn btn-danger" onclick="location.href='pago'" ><i class="fas fa-trash-alt"></i> No Filtrar por Periodo</a>
			<%} %>
		</td>
	</tr>
</table>
</div>
<table style="width:100%">
	<tr>
		<td width="20%"></td>
		<td width="60%" align="center">
			<h3>FACULTAD DE CIENCIAS DE LA SALUD</h3>
			<h4>Pago de rotaciones en <%=aca.rotaciones.RotInstitucion.getNombre(conEnoc, institucionId) %></h4>
			<%if(!fechaInicio.equals("")){ %><h4>Periodo: <%=fechaInicio %> a <%=fechaFinal %></h4> <%} %>
		</td>
		<td width="20%" align="right"><img src="../../imagenes/medicina.jpeg" width="80px"></td>
	</tr>
	<tr>
		<td style="height:4px;"></td>
	</tr>
</table>
<table  width="100%" align="center" class="table table-condensed table-bordered table-striped table-fontsmall">
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
	float pagoTotal = 0;
	int cont = 1;
	for(aca.rotaciones.RotProgramacion prog : programaciones){ 
		 pagoTotal+= Float.parseFloat(prog.getPago());
		
		 
		String alumno = "0000000";
		if(alumnos.containsKey(prog.getCodigoPersonal())){
			alumno = alumnos.get(prog.getCodigoPersonal());
		}
	%>
		<tr>
			<td align="center"><%=cont%></td>
			<td align="center"><%=prog.getCodigoPersonal()==null?"&nbsp;":prog.getCodigoPersonal() %></td>
			<td><%=alumno.equals("0000000")?"&nbsp;":alumno %></td>
			<td align="center"><%=especialidades.get( prog.getEspecialidadId() ) %></td>
			<td align="center"><%=prog.getfInicio() %></td>
			<td align="center"><%=prog.getfFinal() %></td>
			<td style="text-align:right;"><%=prog.getInscripcion() %></td>
			<td style="text-align:right;"><%=prog.getAnual() %></td>
			<td style="text-align:right;"><%=prog.getIntegrada() %></td>
			<td style="text-align:right;"><%=prog.getPago() %></td>
			<td align="center"><%=hospitales.get(prog.getHospitalId()) %></td>
		</tr>
	<%
		cont++;
	} %>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td style="text-align:right;"><%=getFormato.format( pagoTotal ) %></td>
			<td></td>
		</tr>
</table>
</body>
<script>
jQuery('#dp2').datepicker();
jQuery('#dp3').datepicker();
</script>
<%@ include file= "../../cierra_enoc.jsp" %>