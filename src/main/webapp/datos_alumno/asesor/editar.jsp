<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.Date,java.text.SimpleDateFormat"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumAsesor"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>
<%@ page import="aca.plan.spring.MapaPlan"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.vista.spring.Maestros"%>
<%@ page import= "aca.acceso.spring.Acceso"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<head>
	<link rel="stylesheet" href="../../js/chosen/chosen.css" />	
</head>
<%
	DecimalFormat frmDecimal 	= new DecimalFormat("###,##0.00;(###,##0.00)");

	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");
	String planId 				= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
	
	AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");	
	AlumAsesor alumAsesor 		= (AlumAsesor) request.getAttribute("alumAsesor");	
	List<Maestros> lisMaestros	= (List<Maestros>) request.getAttribute("lisMaestros");
%>
<body>
<div class="container-fluid mt-1">
	<h2>Editar Asesor			
		<small class="text-muted fs-4"> ( <%=codigoAlumno%> - <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno()%> <%=alumPersonal.getApellidoMaterno()%> - <%=planId%>)</small>
	</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="planes">Regresar</a>
	</div>	
	<form name="form" action="grabar" method="post">
		<input type="hidden" name="PlanId" value="<%=planId%>">
		<h3>Maestro</h3>
		<br>
		<select class="chosen" name="Maestro" style="width:400px;">
<% 		for(Maestros maestro : lisMaestros){%>
			<option value="<%=maestro.getCodigoPersonal()%>" <%=alumAsesor.getAsesorId().equals(maestro.getCodigoPersonal()) ? "selected" : ""%>><%=maestro.getNombre()+" "+maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno()%></option>
<% 		}%>
		</select>
		<br><br>
   		<button type="submit" class="btn btn-primary">Guardar</button>
	</form>	
</div>
</body>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript"> 
	jQuery(".chosen").chosen(); 
</script>