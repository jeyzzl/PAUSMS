<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.emp.spring.EmpCurriculum"%>
<%@page import="aca.catalogo.spring.CatPais"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	String codigoPersonal 			= session.getAttribute("codigoPersonal").toString();

	boolean revisado 				= (boolean) request.getAttribute("revisado");
	boolean administrador 			= (boolean) request.getAttribute("administrador");	
	String respuesta 				= (String) request.getAttribute("respuesta");
	String nombreEmpleado 			= (String) request.getAttribute("nombreEmpleado");
	String nombreReviso 			= (String) request.getAttribute("nombreReviso");
	
	EmpCurriculum empCurriculum		= (EmpCurriculum) request.getAttribute("empCurriculum");
		
	List<CatPais> lisPaises 		= (List<CatPais>) request.getAttribute("lisPaises");	
%>
<head>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<script type="text/javascript">
	function revisaForma(){
		if(document.forma.fNacimiento.value != "" && document.forma.tProfesional.value != "" && document.forma.tUniversitario.value != ""){
			document.forma.submit();				
		}else{
			alert("Llene los campos requeridos(*) para poder guardar");
		}
		return false;
	}
</script>
</head>
<body>
	<div class="container-fluid">
		<h1>Curriculum Vitae</h1>
		<div class="alert alert-info">
<%	if(request.getParameter("ppe")!=null){%>
		<table>
			<tr>
				<td><a href="../../portales/portafolio/datos" class="btn btn-primary btn-sm"><i class="glyphicon glyphicon-arrow-left"></i> Regresar</a> </td>
			</tr>
		</table>
<%	} %>
		</div>
<% 	if(!respuesta.equals("-")){ %>
		<div class="alert alert-info"><%=respuesta %></div>
<%	} %>		
		<form id="forma" name="forma" action="vitae?Accion=2" method="post">
		<table style="width:90%" class="table table-fullcondensed table-nohover table-nobordered">
				<tr>
					<th>DATOS PERSONALES</th>
				</tr>
				<tr>
					<td>
						<table style="width:100%">
							<tr>
								<td><b><spring:message code="aca.Nombre" /> Completo:</b></td>
							</tr>
							<tr>
								<td><%=nombreEmpleado %></td>
							</tr>
							<tr>
								<td><b>Fecha y lugar de nacimiento:</b></td>
							</tr>							
							<tr>
								<td><b><spring:message code="aca.Fecha" />:</b><br> <input
									type="text" id="fNacimiento"
									name="fNacimiento" value="<%=empCurriculum.getFNacimiento() %>"
									onfocus="focusFecha(this);" data-date-format="dd/mm/yyyy" size="11" maxlength="11">(DD/MM/AAAA)<br> <b>Lugar</b>:<br>
								<input type="text" class="text" id="lugarNac" name="lugarNac"
									value="<%=empCurriculum.getLugarNac() %>" size="60"
									maxlength="95" /></td>
							</tr>
							<tr>
								<td><b><spring:message code="aca.Nacionalidad" />:</b></td>
							</tr>							
							<tr>
								<td>
									<select id="nacionalida" name="nacionalidad">
								<%	if(empCurriculum.getNacionalidad()==null || empCurriculum.getNacionalidad().equals("")){
										empCurriculum.setNacionalidad("91");
									}
									for(CatPais catPais : lisPaises){
									%>
										<option value="<%=catPais.getPaisId() %>"
											<%=catPais.getPaisId().equals(empCurriculum.getNacionalidad())?" Selected":"" %>><%=catPais.getNacionalidad() %></option>
								<% 	} %>
									</select>
								</td>
							</tr>							
							<tr>
								<td><b>Último nivel cursado:</b></td>
							</tr>
							<%	String nivelId = empCurriculum.getNivelId()==null ? "0" : empCurriculum.getNivelId(); %>
							<tr>
								<td><select <%=revisado&&!administrador ?" Disabled":"" %>
									id="nivelId" name="nivelId">
										<option value="1" <%=nivelId.equals("1")?" Selected":"" %>>Preparatoria</option>
										<option value="2" <%=nivelId.equals("2")?" Selected":"" %>>Licenciatura</option>
										<option value="6" <%=nivelId.equals("6")?" Selected":"" %>>Especialidad</option>
										<option value="3" <%=nivelId.equals("3")?" Selected":"" %>>Maestría</option>
										<option value="4" <%=nivelId.equals("4")?" Selected":"" %>>Doctorado</option>
								</select></td>
							</tr>
							<tr>
								<td><b>T&iacute;tulo profesional (&uacute;ltimo grado
										obtenido):</b></td>
							</tr>
							<tr>
								<td><input <%=revisado&&!administrador ?" Disabled":"" %>
									type="text" class="input input-xxlarge" id="tProfesional"
									name="tProfesional"
									value="<%=empCurriculum.getTProfesional() %>" size="130"
									maxlength="195" /></td>
							</tr>
							<tr>
								<td <%=administrador ? "" : "hidden" %>><b>Revisado:</b>
									<input <%=revisado ?" Checked":"" %> type="checkbox" id="revisado" name="revisado" />&nbsp;<font color="green"><b><%=revisado?nombreReviso:""%></b></font></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th>FORMACI&Oacute;N ACAD&Eacute;MICA</th>
				</tr>
				<tr>
					<td>
						<table style="width:100%">
							<tr>
								<td><b>T&iacute;tulo universitario (licenciatura):</b></td>
							</tr>
							<tr>
								<td><input type="text" class="input input-xxlarge"
									id="tUniversitario" name="tUniversitario"
									value="<%=empCurriculum.getTUniversitario() %>" size="60"
									maxlength="195" /> <b><spring:message code="aca.Fecha" />:</b> <input
									type="text" class="input input-medium" data-date-format="dd/mm/yyyy" id="fechaLic"
									name="fechaLic"
									value="<%=empCurriculum.getFechaLic()==null? "" : empCurriculum.getFechaLic() %>"
									onfocus="focusFecha(this);" size="12" maxlength="10">(DD/MM/AAAA)<br></td>
							</tr>
							<tr>
								<td><b>Institución donde cursó su título universitario
										(licenciatura):</b></td>
							</tr>				
							<tr>
								<td><input type="text" class="input input-xxlarge"
									id="instLic" name="instLic"
									value="<%=empCurriculum.getInstLic()==null?"":empCurriculum.getInstLic() %>"
									size="60" maxlength="195" /></td>
							</tr>

							<tr>
								<td
									style="background: #D8D8D8; border: 1px solid gray; height: 7px;"></td>
							</tr>

							<tr>
								<td><b>Título de Maestría:</b></td>
							</tr>
							<tr>
								<td><input type="text" class="input input-xxlarge"
									id="gPosgrado" name="gPosgrado"
									value="<%=empCurriculum.getGPosgrado() %>" size="60"
									maxlength="195" /> <b><spring:message code="aca.Fecha" />:</b> <input
									type="text" class="input input-medium" id="fechaMae"
									name="fechaMae" data-date-format="dd/mm/yyyy"
									value="<%=empCurriculum.getFechaMae()==null? "" : empCurriculum.getFechaMae() %>"
									onfocus="focusFecha(this);" size="12" maxlength="10">(DD/MM/AAAA)<br></td>
							</tr>
							<tr>
								<td><b>Institución donde cursó su título de maestría:</b></td>
							</tr>
							<tr>
								<td><input type="text" class="input input-xxlarge"
									id="instMae" name="instMae"
									value="<%=empCurriculum.getInstMae()==null?"":empCurriculum.getInstMae() %>"
									size="60" maxlength="195" /></td>
							</tr>

							<tr>
								<td
									style="background: #D8D8D8; border: 1px solid gray; height: 7px;"></td>
							</tr>

							<tr>
								<td><b>Título de doctorado:</b></td>
							</tr>
							<tr>
								<td><input type="text" class="input input-xxlarge"
									id="tDoctorado" name="tDoctorado"
									value="<%=empCurriculum.gettDoctorado()%>" size="60"
									maxlength="195" /> <b><spring:message code="aca.Fecha" />:</b> <input
									type="text" class="input input-medium" id="fechaDoc"
									name="fechaDoc" data-date-format="dd/mm/yyyy"
									value="<%=empCurriculum.getFechaDoc()==null? "" : empCurriculum.getFechaDoc()%>"
									onfocus="focusFecha(this);" size="12" maxlength="10">(DD/MM/AAAA)<br></td>
							</tr>
							<tr>
								<td><b>Institución donde cursó su título de doctorado:</b></td>
							</tr>
							<tr>
								<td><input type="text" class="input input-xxlarge"
									id="instDoc" name="instDoc"
									value="<%=empCurriculum.getInstDoc()==null?"":empCurriculum.getInstDoc() %>"
									size="60" maxlength="195" /></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>				
				<tr>
					<th>EXPERIENCIA PROFESIONAL</th>
				</tr>
				<tr>
					<td>
						<table style="width:100%">
							<tr>
								<td><b>Experiencia docente universitaria:</b></td>
							</tr>
							<tr>
								<td><textarea style="width: 100%"
										class="textarea span7" id="expDocente" name="expDocente"
										cols="60" rows="7"><%=empCurriculum.getExpDocente() %></textarea></td>
							</tr>
							<tr>
								<td><b>Experiencia profesional:</b></td>
							</tr>
							<tr>
								<td><textarea maxlength="5000" style="width: 100%"
										class="textarea span7" id="respActual" name="respActual"
										cols="60" rows="7"><%=empCurriculum.getRespActual() %></textarea></td>
							</tr>
							<!-- 
						<tr>
							<td><b>Responsabilidad anterior</b></td>
						</tr>
						<tr>
							<td><textarea style="width:100%" class="textarea span7" id="respAnterior" name="respAnterior" cols="60" rows="7"><%=empCurriculum.getRespAnterior() %></textarea></td>
						</tr> -->
						</table>
					</td>
				</tr>
				<tr>
					<td><input type="hidden" name="ppe" value="true"> </td>
				</tr>
				<tr>
					<th align="center" style="text-align: center">
						<a href="javascript:revisaForma();" class="btn btn-primary">Grabar</a>
					</th>
				</tr>
			</table>
		</form>
	</div>
</body>
<script>
	$('#respActual').maxlength({ 
	    max: 2000
	});
	
	jQuery('#fNacimiento').datepicker();
	jQuery('#fechaLic').datepicker();
	jQuery('#fechaMae').datepicker();
	jQuery('#fechaDoc').datepicker();
</script>