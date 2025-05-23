<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.catalogo.CatPais"%>
<jsp:useBean id="empCurriculum" class="aca.emp.EmpCurriculum" scope="page"/>
<jsp:useBean id="EmpCurriculumU" class="aca.emp.EmpCurriculumUtil" scope="page"/>
<jsp:useBean id="catPais" class="aca.catalogo.CatPais" scope="page"/>
<jsp:useBean id="catPaisU" class="aca.catalogo.PaisUtil" scope="page"/>
<%
	String codigoPersonal = (String) session.getAttribute("codigoPersonal");
	String respuesta = "";
	int accion = Integer.parseInt(request.getParameter("Accion")==null?"0":request.getParameter("Accion"));
	boolean error = false;
	
	ArrayList<CatPais> listPais = catPaisU.getListAll(conEnoc, "ORDER BY NACIONALIDAD");
	
	//if(codigoPersonal.trim().equals("9800100"))
		//codigoPersonal = (String) session.getAttribute("codigoEmpleado");
	
	empCurriculum.setIdEmpleado(codigoPersonal);
	if(EmpCurriculumU.existeReg(conEnoc, codigoPersonal))
		empCurriculum = EmpCurriculumU.mapeaRegId(conEnoc, codigoPersonal);
	
	switch(accion){
		case 2:{//Guardar
			conEnoc.setAutoCommit(false);
			empCurriculum.setIdEmpleado(codigoPersonal);
			empCurriculum.setLugarNac(request.getParameter("lugarNac"));
			empCurriculum.setTProfesional(request.getParameter("tProfesional"));
			empCurriculum.setGPosgrado(request.getParameter("gPosgrado"));
			empCurriculum.setTUniversitario(request.getParameter("tUniversitario"));
			empCurriculum.setRespActual(request.getParameter("respActual"));
			empCurriculum.setRespAnterior(request.getParameter("respAnterior"));
			empCurriculum.setExpDocente(request.getParameter("expDocente"));
			empCurriculum.setFNacimiento(request.getParameter("fNacimiento"));
			empCurriculum.setNacionalidad(request.getParameter("nacionalidad"));
			if(EmpCurriculumU.existeReg(conEnoc, codigoPersonal)){
				if(!EmpCurriculumU.updateReg(conEnoc,empCurriculum ))
					error = true;
			}else{
				if(!EmpCurriculumU.insertReg(conEnoc, empCurriculum))
					error = true;
			}
			if(error){
				conEnoc.rollback();
				respuesta = "<font color=red size=3><b>Ocurr&oacute; un error al guardar los datos. Int&eacute;ntelo de nuevo</b></font>";
			}else{
				conEnoc.commit();
				respuesta = "<font color=blue size=3><b>Se guardaron los datos correctamente</b></font>";
			}
			conEnoc.setAutoCommit(true);
		}break;
	}
	

%>
<head>
	<script type="text/javascript" src="../../js/popcalendar.js"></script>
	<script type="text/javascript">
		function revisaForma(){
			if(document.forma.fNacimiento.value != "" &&
			   document.forma.tProfesional.value != "" &&
			   document.forma.tUniversitario.value != ""){
				return true;
			}else{
				alert("Llene los campos requeridos(*) para poder guardar");
			}
			return false;
		}
	</script>
</head>
<body>
<%
	if(!respuesta.equals("")){
%>
	<table style="margin: 0 auto;">
		<tr>
			<td><%=respuesta %></td>
		</tr>
	</table>
<%
	}
%>
	<table style="margin: 0 auto;">
		<tr>
			<td class="titulo">Curriculum Vitae</td>
		</tr>
	</table>
	<form id="forma" name="forma" action="vitae?Accion=2" method="post">
		<table style="width:850px" align="center">
			<tr>
				<td><font size="4">DATOS PERSONALES</font></td>
			</tr>
			<tr>
				<td style="border: solid 1px black;">
					<table style="width:100%">
						<tr>
							<td><b><spring:message code="aca.Nombre"/> Completo:</b></td>
						</tr>
						<tr>
							<td><%=aca.vista.MaestrosUtil.getNombreMaestro(conEnoc,codigoPersonal,"NOMBRE") %></td>
						</tr>
						<tr>
							<td><b>Fecha y lugar de nacimiento:</b></td>
						</tr>
						<tr>
							<td>
								Fecha:<br>
								<input type="text" class="text" id="fNacimiento" name="fNacimiento" value="<%=empCurriculum.getFNacimiento() %>" onfocus="focusFecha(this);" size="10" maxlength="10">
            					<img title="calendario" id="fNacimiento" src="../../imagenes/calendario.gif" onClick="showCalendar(this, document.getElementById('fNacimiento'), 'dd/mm/yyyy',null,1,-1,-1);" class="button"> (DD/MM/AAAA)<br>
            					Lugar:<br><input type="text" class="text" id="lugarNac" name="lugarNac" value="<%=empCurriculum.getLugarNac() %>" size="60" maxlength="95" />
							</td>
						</tr>
						<tr>
							<td><b><spring:message code="aca.Nacionalidad"/>:</b></td>
						</tr>
						<tr>
							<td>
								<select id="nacionalida" name="nacionalidad">
<%
	for(int i = 0; i < listPais.size(); i++){
		catPais = (CatPais) listPais.get(i);
%>
									<option value="<%=catPais.getPaisId() %>"<%=catPais.getPaisId().equals(empCurriculum.getNacionalidad())?" Selected":"" %>><%=catPais.getNacionalidad() %></option>
<%
	}
%>
								</select>
							</td>
						</tr>
						<tr>
							<td><b>T&iacute;tulo profesional (&uacute;ltimo grado obtenido):</b></td>
						</tr>
						<tr>
							<td><input type="text" class="text" id="tProfesional" name="tProfesional" value="<%=empCurriculum.getTProfesional() %>" size="60" maxlength="195" /></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><font size="4">FORMACI&Oacute;N ACAD&Eacute;MICA</font></td>
			</tr>
			<tr>
				<td style="border: solid 1px black;">
					<table style="width:100%">
						<tr>
							<td><b>Grado de posgrado:</b></td>
						</tr>
						<tr>
							<td><input type="text" class="text" id="gPosgrado" name="gPosgrado" value="<%=empCurriculum.getGPosgrado() %>" size="60" maxlength="195" /></td>
						</tr>
						<tr>
							<td><b>T&iacute;tulo universitario (licenciatura):</b></td>
						</tr>
						<tr>
							<td><input type="text" class="text" id="tUniversitario" name="tUniversitario" value="<%=empCurriculum.getTUniversitario() %>" size="60" maxlength="195" /></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><font size="4">EXPERIENCIA PROFESIONAL</font></td>
			</tr>
			<tr>
				<td style="border: solid 1px black;">
					<table style="width:100%">
						<tr>
							<td><b>Responsabilidad actual:</b></td>
						</tr>
						<tr>
							<td><textarea id="respActual" name="respActual" cols="60" rows="4"><%=empCurriculum.getRespActual() %></textarea></td>
						</tr>
						<tr>
							<td><b>Responsabilidad anterior</b></td>
						</tr>
						<tr>
							<td><textarea id="respAnterior" name="respAnterior" cols="60" rows="4"><%=empCurriculum.getRespAnterior() %></textarea></td>
						</tr>
						<tr>
							<td><b>Experiencia docente:</b></td>
						</tr>
						<tr>
							<td><textarea id="expDocente" name="expDocente" cols="60" rows="4"><%=empCurriculum.getExpDocente() %></textarea></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td align="center"><input type="submit" class="button" value="Guardar" onclick="return revisaForma();" /></td>
			</tr>
		</table>
	</form>
</body>

<%@ include file= "../../cierra_enoc.jsp" %>