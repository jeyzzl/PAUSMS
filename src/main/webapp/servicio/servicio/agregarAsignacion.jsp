<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ page import= "aca.ssoc.spring.SsocAsignacion"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<%@ page import= "java.util.* "%>

<%-- <jsp:useBean id="asignacion" scope="page" class="aca.ssoc.AsignacionVO"/> --%>
<%-- <jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/> --%>
<%-- <jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/> --%>
<%-- <jsp:useBean id="plan" scope="page" class="aca.alumno.AlumPlan"/> --%>

<%
	
	String asId					= (String)request.getAttribute("asId");
	String matricula			= (String)request.getAttribute("matricula");
	String nombreAlumno 		= (String)request.getAttribute("nombreAlumno");
	SsocAsignacion asignacion	= (SsocAsignacion)request.getAttribute("asignacion");
	String planId				= (String)request.getAttribute("planId");
	
	int mensaje = 0;
	
%>

<body>
<div class="container-fluid">
	<h2>Añadir Asignación
	<small class="text-muted fs-4">
		( <%=matricula%> - <%=nombreAlumno%>)
		</small>
	</h2>
	<div class="alert alert-info">
		<a href="social?PlanId=<%=planId%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i><spring:message code='aca.Atras'/></a>
	</div>
	<br />
<%		if(mensaje==1){%>
		<div class="alert alert-info"><h3><spring:message code='aca.SeGuardoCorrectamente'/></h3></div>
<%	
		}
		if(mensaje==2){%>
		<div class="alert alert-danger"><h3><spring:message code='aca.NoSePudoGuardar'/></h3></div>
<%	
		}		
		if(mensaje==3){%>
		<div class="alert alert-info"><h3><spring:message code='aca.SeModifico'/></h3></div>
<%	
		}
		if(mensaje==4){%>
		<div class="alert alert-danger"><h3><spring:message code='aca.NoSePudoModificar'/></h3></div>
<%	
		}
		if(mensaje==5){%>
		<div class="alert alert-info"><h3><spring:message code='aca.SeElimino'/></h3></div>
<%	
		}
		if(mensaje==6){%>
		<div class="alert alert-danger"><h3><spring:message code='aca.NoSePudoEliminar'/></h3></div>
<%	
		}
		if(mensaje==7){%>
		<div class="alert alert-danger"><h3><spring:message code='aca.NoExiste'/></h3></div>
<%	
		}
%>  
	<form id="datos" name="datos" action="grabarAsignacion" method="post">
		<input type="hidden" name="PlanId" value="<%=planId%>">
		<div class="row">
			<div class="span4 container-fluid">
				<div class="control-group">
					<label for="asId">Asignación Id<span class="required-indicator">*</span></label>
					<input type="text" id="asId" name="asId" value="<%=asId%>" readonly/>
				</div><br>
				<div class="control-group">
					<label for="dependencia">Dependencia<span class="required-indicator">*</span></label>
					<input type="text" required id="dependencia" name="dependencia" value="<%=asignacion.getDependencia()%>"/>
				</div><br>
				<div class="control-group">
					<label for="sector">Sector<span class="required-indicator"></span></label>
					<select id="sector" name="sector">
						<option value="1" <%=asignacion.getSector().equals("1")?"Selected":""%>>Privada</option>
						<option value="2" <%=asignacion.getSector().equals("2")?"Selected":""%>>Publica</option>
						<option value="3" <%=asignacion.getSector().equals("3")?"Selected":""%>>Social</option>
						<option value="4" <%=asignacion.getSector().equals("4")?"Selected":""%>>Educativo</option>
					</select>
				</div><br>
				<div class="control-group">
					<label for="direccion">Dirección<span class="required-indicator">*</span></label>
						<input type="text" required id="direccion" name="direccion" value="<%=asignacion.getDireccion()%>" />
				</div><br>
			</div>
			<div class="span4 container-fluid">
				<div class="control-group">
					<label for="telefono">Teléfono<span class="required-indicator">*</span></label>
					<input type="text" required id="telefono" name="telefono" value="<%=asignacion.getTelefono()%>" />
				</div><br>
				<div class="control-group">
						<label for="responsable">Responsable<span class="required-indicator">*</span></label>
						<input type="text" required id="responsable" name="responsable" value="<%=asignacion.getResponsable()%>" />
				</div><br>
				<div class="control-group">
					<label for="fechaInicial">Fecha Inicial<span class="required-indicator">*</span></label>
					<input type="text" data-date-format="dd/mm/yyyy" required id="fechaInicial" name="fechaInicial" value="<%=asignacion.getFechaInicio()%>" />
				</div><br>
			</div>
		</div>
		<div class="alert alert-info">
			<a href="javascript:Grabar('<%=asId%>');" class="btn btn-primary"><spring:message code="aca.Grabar"/></a>
		</div>
	</form>
</div>
<script>
	jQuery('#fechaInicial').datepicker();
</script>

<script type="text/javascript">
	
	function Grabar(asId) {	
		if(document.datos.direccion.value != "" && document.datos.telefono.value != "" && document.datos.responsable.value != "" && document.datos.fechaInicial.value != ""){
			document.datos.asId.value = asId;
			document.datos.submit();	
		}else{
			alert("Llene todos los campos");
		}
	}
	
</script>
</body>