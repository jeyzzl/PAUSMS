<%@page import="java.util.List"%>
<%@page import="aca.rotaciones.spring.RotEspecialidad"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<% 
	String especialidadId 			= request.getParameter("EspecialidadId")==null?"0": request.getParameter("EspecialidadId");
	String mensaje		 			= request.getParameter("Mensaje")==null?"-": request.getParameter("Mensaje");
	RotEspecialidad especialidad 	= (RotEspecialidad)request.getAttribute("especialidad");
%>
<script>
	function Grabar(){
		if(document.frmEspecialidad.EspecialidadNombre.value!= "" && document.frmEspecialidad.Semanas.value!=""){
			document.frmEspecialidad.submit();	
		}else{
			alert("Completa el formulario");
		}
	}
</script>
<body>
<div class="container-fluid">
	<h1>Especialidades</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="especialidad"><spring:message code="aca.Regresar"/></a>
	</div>
<%	if (!mensaje.equals("-")){%>
	<div class="alert alert-info"><%=mensaje%></div>
<%	}%>	
	<form action="grabar" method="post" name="frmEspecialidad">
	<input type="hidden" name="Accion">
	<input type="hidden" name="EspecialidadId" value="<%=especialidadId%>">	
	<div class="row">
		<div class="col-3">
			<label for="EspecialidadNombre"><spring:message code="aca.Nombre"/> especialidad:</label>
			<input class="form-control" name="EspecialidadNombre" type="text" id="EspecialidadNombre" maxlength="70" value="<%=especialidad.getEspecialidadNombre()%>">
			<br>
           <label for="Semanas">Semanas:</label>
           <select name="Semanas" id="Semanas" class="form-select">
				<option value="2" <%if(especialidad.getSemanas().equals("2"))out.print("selected");%>>2</option>
				<option value="4" <%if(especialidad.getSemanas().equals("4"))out.print("selected");%>>4</option>
			</select>
           <br>
           <label for="CursoId">Curso:</label>
           <input name="CursoId" class="form-control" type="text" id="CursoId" value="<%=especialidad.getCursoId()==null?"":especialidad.getCursoId()%>" maxlength="15">
           <br>
           <label for="PlanId"><spring:message code="aca.Plan"/>:</label>
           <input name="PlanId" class="form-control" type="text" id="PlanId" value="<%=especialidad.getPlanId()%>" maxlength="8">
		</div>
	</div>
	<br>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="javascript:Grabar();"><spring:message code="aca.Grabar"/></a> &nbsp;
	</div>
	</form>
</div>
</body>