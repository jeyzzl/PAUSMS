<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguroJquery.jsf" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="Hospital" scope="page" class="aca.rotaciones.RotHospital"/>
<jsp:useBean id="HospEspecialidadU" scope="page" class="aca.rotaciones.RotHospitalEspecialidadUtil"/>
<jsp:useBean id="Programacion" scope="page" class="aca.rotaciones.RotProgramacion"/>

<%
	String hospitalId 		= session.getAttribute("rotHospitalId").toString();
	String especialidadId 	= session.getAttribute("rotEspecialidadId").toString();
	String matriculaAlumno  = request.getParameter("alumno")==null?"":request.getParameter("alumno");
	String mensaje			= "";
	String alert			= "";
	String accion			= request.getParameter("grabar")==null?"":request.getParameter("grabar");
	
	String programacionId 	= request.getParameter("programacionId");
	
	if(accion.equals("1")){
		Programacion.mapeaRegId(conEnoc, programacionId);
		Programacion.setCodigoPersonal(matriculaAlumno);	

		if(Programacion.updateReg(conEnoc)){
			mensaje = "Guardado";
			alert	= "success";
		}else{
			mensaje = "No pudo guardar";
			alert	= "danger";
		}
	}
	
%>

<div class="container-fluid">
	<h1>Agregar Alumno</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="programacion"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp; Hospital : <%=aca.rotaciones.RotHospital.getNombre(conEnoc, hospitalId)%> - Especialidad : <%=aca.rotaciones.RotEspecialidad.getNombre(conEnoc, especialidadId)%>
	</div>
<%	if(!mensaje.equals("")){%>	
	<div class="alert alert-<%=alert%>" role="alert"><%=mensaje%></div>
<%  }%>
	<form action="agregarAlumno">
		<input type="hidden" name="grabar" value="1">
		<input type="hidden" name="matricula" value="<%=matriculaAlumno%>">
		<input type="hidden" name="programacionId" value="<%=programacionId%>">
		<div id="agregarAlumno" class="row">
			<div class="span3">
				<label for="alumno">Alumno:</label>
				<input name="alumno" class="form-control" style="width:250px" id="alumno" type="text" maxlength="7" value="<%=matriculaAlumno%>"/>
				<br><br>
				<input type="submit" class="btn btn-primary" value="Grabar"/>
			</div>
		</div>
	</form>
</div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>