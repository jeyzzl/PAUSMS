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
<script>
	function subirHospitalId(){
		location.href="plazas?Accion=1&HospitalId="+jQuery('.hospitalId').val();
	}
	function subirEspecialidadId(){
		location.href="plazas?Accion=2&EspecialidadId="+jQuery('.EspecialidadId').val();		
	}
	function fecha(){
		location.href="plazas?FechaInicio="+jQuery('#dp2').val()+"&FechaFinal="+jQuery('#dp3').val();
	}
</script>
<%
	String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion");

	ArrayList<aca.rotaciones.RotHospital> hospitales = HospitalU.getListHospitales(conEnoc, "ORDER BY 1");
	
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
	
	ArrayList<aca.rotaciones.RotHospitalEspecialidad> especialidades = hospitalId.equals("todos")?HospEspecialidadU.getListAll(conEnoc, "ORDER BY 1"):HospEspecialidadU.getListHospActivas(conEnoc, hospitalId, "ORDER BY 1");
	
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
	if(!encontrado && !especialidadId.equals("todas")){
		especialidadId = especialidades.get(0).getEspecialidadId();
	}
	
	String fechaInicio = request.getParameter("FechaInicio")==null?"":request.getParameter("FechaInicio");
	String fechaFinal  = request.getParameter("FechaFinal")==null?"":request.getParameter("FechaFinal");
	String fechaSQL = "";
	if(!fechaInicio.equals("")){
		fechaSQL = " AND F_INICIO >= TO_DATE('"+fechaInicio+"', 'DD/MM/YYYY') AND F_FINAL <= TO_DATE('"+fechaFinal+"', 'DD/MM/YYYY')";
	}
	
	ArrayList<aca.rotaciones.RotProgramacion> programaciones = null;

	if(!especialidadId.equals("todas")){
		programaciones = hospitalId.equals("todos")?ProgramacionU.getListAll(conEnoc, "WHERE ESPECIALIDAD_ID='"+especialidadId+"' "+fechaSQL+" AND CODIGO_PERSONAL IS NOT NULL ORDER BY 2,1 desc"):ProgramacionU.getListHospEsp(conEnoc, hospitalId, especialidadId, fechaSQL+" AND CODIGO_PERSONAL IS NOT NULL ORDER BY 1 desc");
	}else{
		programaciones = hospitalId.equals("todos")?ProgramacionU.getListAll(conEnoc, "WHERE CODIGO_PERSONAL IS NOT NULL "+fechaSQL+" ORDER BY 2,1 desc"):ProgramacionU.getListHosp(conEnoc, hospitalId,  fechaSQL+" AND CODIGO_PERSONAL IS NOT NULL ORDER BY 1 desc");
	}

	
	java.util.HashMap<String,String> alumnos =  aca.vista.UsuariosUtil.getMapAlumnosNombre(conEnoc, " ORDER BY 1");
%>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

</head>
<body>
<div class="well oculto" style="margin:0;padding:2px;">
<a href="menu" class="btn btn-primary" style="left:10px;position:absolute;">regresar</a>

<table style="margin: 0 auto;" class="table table-condensed table-nohover" style="padding:10px;border:1px solid #D4D4D4;border-radius:2px;margin:0 auto;">
	<tr>
		<td colspan="2"><h5>Hospital:</h5>
			<select class="HospitalId" style="width:400px;" onchange="subirHospitalId();">
				<option <%if(hospitalId.equals("todos"))out.print("selected");%> value="todos">Todos</option>
				<%for(aca.rotaciones.RotHospital hospi: hospitales){ %>
					<option <%if(hospitalId.equals(hospi.getHospitalId()))out.print("selected");%> value="<%=hospi.getHospitalId() %>"><%=hospi.getHospitalNombre() %></option>
				<%} %>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="2"><h5>Especialidad:</h5>
			<select class="EspecialidadId" style="width:400px;" onchange="subirEspecialidadId();">
					<option <%if(especialidadId.equals("todas"))out.print("selected");%> value="todas">Todas</option>
				<%	String tmpEspecialidad = "";
					for(aca.rotaciones.RotHospitalEspecialidad esp: especialidades){
						if(!tmpEspecialidad.equals(esp.getEspecialidadId())){
							tmpEspecialidad = esp.getEspecialidadId();
						%>
							<option <%if(especialidadId.equals(esp.getEspecialidadId()))out.print("selected");%> value="<%=esp.getEspecialidadId() %>"><%=aca.rotaciones.RotEspecialidad.getNombre(conEnoc, esp.getEspecialidadId()) %> &nbsp;&nbsp;&nbsp;(<%=aca.rotaciones.RotEspecialidad.getSemanas(conEnoc, esp.getEspecialidadId())%>  Semanas)</option>	
				<%		}%>
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
				<a class="btn btn-danger" onclick="location.href='plazas'" ><i class="fas fa-trash-alt"></i> No Filtrar por Periodo</a>
			<%} %>
		</td>
	</tr>
</table>
</div>
<table style="margin: 0 auto;">
	<tr>
		<td align="center"><h2>Reporte de Plazas Ocupadas</h2>
		<h4><%=hospitalId.equals("todos")?"Todos los hospitales":aca.rotaciones.RotHospital.getNombre(conEnoc, hospitalId)  %></h4>
		<%if(!fechaInicio.equals("")){ %>
			<h4><spring:message code="aca.Fecha"/>: <%=fechaInicio %> - <%=fechaFinal %></h4>
		<%} %>
	</td>
	</tr>
</table>
<table style="width:100%" align="center" class="table table-condensed table-bordered table-striped"> 
	 <tr> 
		<th>#</th>
		<th><spring:message code="aca.Matricula"/></th>
		<th><spring:message code="aca.Nombre"/></th>
		<th><spring:message code="aca.FechaInicio"/></th>
		<th><spring:message code='aca.FechaFinal'/></th>
	<%	if(hospitalId.equals("todos")){%>
			<th>Hospital</th>
	<%	}%>
		<th>Especialidad</th>
	</tr>
	<%
	int cont = 1;
	for(aca.rotaciones.RotProgramacion prog : programaciones){ 
		String estado = prog.getCodigoPersonal()==null?"Vacia":"Ocupada";
		 
		String hide = "";
		if(estado.equals("Vacia")){
			hide = "style='display:none;'";
		}
		
		String alumno = "0000000";
		if(alumnos.containsKey(prog.getCodigoPersonal())){
			alumno = alumnos.get(prog.getCodigoPersonal());
		}
	%>
		<tr>
			<td align="center"><%=cont%></td>

			<td align="center">
				<span class="matricula"><%=prog.getCodigoPersonal()==null?"&nbsp;":prog.getCodigoPersonal() %></span>
			</td>
			<td>
				<span class="nombre"><%=alumno.equals("0000000")?"&nbsp;":alumno %></span>
			</td>
			<td align="center"><%=prog.getfInicio() %></td>
			<td align="center"><%=prog.getfFinal() %></td>
		<%	if(hospitalId.equals("todos")){%>
				<td><%=aca.rotaciones.RotHospital.getNombre(conEnoc, prog.getHospitalId())%></td>
		<%	}%>
			<td><%=aca.rotaciones.RotEspecialidad.getNombre(conEnoc, prog.getEspecialidadId()) %></td>
		</tr>
	<%
		cont++;
	} %>
</table>
</body>
<script>
jQuery('#dp2').datepicker();
jQuery('#dp3').datepicker();
</script>
<%@ include file= "../../cierra_enoc.jsp" %>