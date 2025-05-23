<%@page import="aca.matricula.spring.MatAlumno"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<body>
<%
	String eventoId		= (String) request.getAttribute("eventoId");
	String codigoAlumno = (String) request.getAttribute("codigoAlumno");

	MatAlumno matAlumno = (MatAlumno) request.getAttribute("matAlumno");

%>
	<div class="container-fluid">
		<h2>Nombre<small class="text-muted fs-4"> Credencial</small></h2>
		<div class="alert alert-info">
			<a href="credencial?EventoId=<%=eventoId%>" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
		</div>
		<form name="frmCredencial" action="agregar">
			<div class="row">
				<div class="span3">
					<label for="EventoId">Id del Evento</label>			
					<input type="text" class="form-control"  style="width:250px" name="EventoId" id="EventoId" value="<%=eventoId%>" readonly="readonly"/>
					<br><br>
					<label for="Matricula"><spring:message code="aca.Matricula"/></label>					
					<input type="text" class="form-control" style="width:250px" name="Matricula" id="Matricula" value="<%=codigoAlumno%>" readonly="readonly"/>
					<br><br>
					<label for="Nombre"><spring:message code="aca.Nombre"/></label>
					<input type="text" class="form-control" style="width:250px" name="Nombre" id="Nombre" value="" width="50%"/>
				</div>
			</div>
			<br>
			<div class="alert alert-info">
				<a class="btn btn-primary" onclick="javascript:Guardar();"><i class="icon-ok icon-white"></i> Guardar</a>
				<a class="btn btn-danger" onclick="javascript:Eliminar();"><i class="fas fa-trash-alt icon-white"></i> Eliminar</a>
				<a class="btn btn-success" onclick="javascript:Nuevo();"><i class="icon-user icon-white"></i> Nuevo</a>
			</div>
		</form>
		
	</div>
</body>
<script>
	function Guardar() {
		if (document.frmCredencial.EventoId.value != ""
				&& document.frmCredencial.Matricula.value != ""
				&& document.frmCredencial.Nombre.value != "") {
			document.frmCredencial.Accion.value = "2";
			document.frmCredencial.submit();
		} else {
			alert("�Completa todos los campos!");
		}
	}
	function Eliminar(){
		if (document.frmCredencial.EventoId.value != ""
				&& document.frmCredencial.Matricula.value != ""
				&& document.frmCredencial.Nombre.value != "") {
			if (confirm("�Est�s seguro que deseas eliminar este registro?") == true) {
				document.frmCredencial.Accion.value = "3";
				document.frmCredencial.submit();
			}
		}else{
			alert("�Completa todos los campos!");
		}
	}
	function Nuevo() {
		if(document.frmCredencial.Nombre.value!=""){
			if(confirm("El campo de nombre no est� vac�o, es posible que la persona no haya sido guardada todav�a. �Deseas crear un nuevo registro?")==true){
				document.frmCredencial.Accion.value = "1";
				document.frmCredencial.submit();		
			}
		}else{
			document.frmCredencial.Accion.value = "1";
			document.frmCredencial.submit();
		}
	}
</script>
</html>