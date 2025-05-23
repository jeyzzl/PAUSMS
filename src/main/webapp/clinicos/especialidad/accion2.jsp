<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="RotEspecialidad" scope="page" class="aca.rotaciones.RotEspecialidad"/>

<html>
	<head>
		<script>
			function Grabar(){
				if(frmmodalidad.NombreEspecialidad.value!=""){
					if(true){//frmmodalidad.Curso.value!=""){
						if(frmmodalidad.Plan.value!=""){
							frmmodalidad.Accion.value = "2";
							frmmodalidad.submit();
						}
						else{
							alert("Completa el formulario");
						}
					}
					else{
						alert("Completa el formulario");
					}
				}
				else{
					alert("Completa el formulario");
				}
			}
		</script>
	</head>
<%
	String especialidadId = request.getParameter("EspecialidadId");
	
	String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion");
	String resultado = "";

	if(accion.equals("2")){
		RotEspecialidad.setEspecialidadNombre(request.getParameter("NombreEspecialidad"));
		RotEspecialidad.setSemanas(request.getParameter("Semanas"));
		RotEspecialidad.setCursoId(request.getParameter("Curso"));
		RotEspecialidad.setPlanId(request.getParameter("Plan"));
		
		especialidadId = especialidadId.equals("null")?"0":especialidadId;
		RotEspecialidad.setEspecialidadId(especialidadId);
		if(RotEspecialidad.existeReg(conEnoc)){
			if(RotEspecialidad.updateReg(conEnoc)) resultado = "<font color='green' size='3'>Guardado</font>";
			else resultado = "<font color='#AE2113' size='3'>Error al guardar</font>";
		}
		else{
			RotEspecialidad.setEspecialidadId(RotEspecialidad.maximoReg(conEnoc));
			if(RotEspecialidad.insertReg(conEnoc)) resultado = "<font color='green' size='3'>Guardado</font>";
			else resultado = "<font color='#AE2113' size='3'>Error al guardar</font>";
		}
		
		RotEspecialidad.mapeaRegId(conEnoc, especialidadId);
	}
	else if(accion.equals("3")){
		RotEspecialidad.setEspecialidadId(especialidadId);
		RotEspecialidad.deleteReg(conEnoc);
		out.print("<div class='alert alert-success'>Borrado...<a class='btn btn-primary' href='especialidad.jsp'>Regresar</a></div>");
		//response.sendRedirect("especialidad.jsp");
	}
	else if(accion.equals("4")){
		RotEspecialidad.mapeaRegId(conEnoc, especialidadId);
	}
%>
	<body>
	<div class="container-fluid">
		<h1>Especialidades</h1>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="especialidad.jsp"><spring:message code="aca.Regresar"/></a>
		</div>
		<form action="accion.jsp" method="post" name="frmmodalidad" target="_self">
			<input type="hidden" name="Accion">
			<input type="hidden" name="EspecialidadId" value="<%=especialidadId%>">
			
			<div class="row">
				<div class="span3">
					<label for="NombreEspecialidad"><spring:message code="aca.Nombre"/> especialidad:</label>
					<input class="input input-xlarge" name="NombreEspecialidad" type="text" id="NombreEspecialidad" maxlength="70" value="<%=RotEspecialidad.getEspecialidadNombre()%>">
					<br><br>
		            <label for="Semanas">Semanas:</label>
		            <select name="Semanas" id="Semanas" >
							<option value="2" <%if(RotEspecialidad.getSemanas().equals("2"))out.print("selected");%>>2</option>
							<option value="4" <%if(RotEspecialidad.getSemanas().equals("4"))out.print("selected");%>>4</option>
						</select>
		            <br><br>
		            <label for="Curso">Curso:</label>
		            <input name="Curso" class="input input-large" type="text" id="Curso" value="<%=RotEspecialidad.getCursoId()==null?"":RotEspecialidad.getCursoId()%>" maxlength="15">
		            <br><br>
		            <label for="Plan"><spring:message code="aca.Plan"/>:</label>
		            <input name="Plan" class="input input-large" type="text" id="Plan" value="<%=RotEspecialidad.getPlanId()%>" maxlength="8">
				</div>
			</div>
			<br>
			<div class="alert alert-info">
				<a class="btn btn-primary" href="javascript:Grabar();"><spring:message code="aca.Grabar"/></a> &nbsp;
			</div>
		</form>
	<%	if(accion.equals("2")&&!resultado.contains("#AE2113")){ %>
			<META http-equiv="Refresh" content="1;url=especialidad.jsp">
	<%	} %>
	</div>
	</body>
</html>
<%@ include file="../../cierra_enoc.jsp"%>