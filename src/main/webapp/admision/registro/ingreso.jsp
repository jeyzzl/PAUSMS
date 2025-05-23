<%@ page import="java.util.HashMap"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguroJquery.jsf" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="alumnosU"  class="aca.alumno.AlumUtil" scope="page"/>
<jsp:useBean id="estadisticaU"  class="aca.vista.EstadisticaUtil" scope="page"/>
<jsp:useBean id="solicitudU"  class="aca.admision.AdmSolicitudUtil" scope="page"/>
<jsp:useBean id="AlumPlanU"  class="aca.alumno.PlanUtil" scope="page"/>

<head>
<script type="text/javascript">
	function Mostrar(){
		document.forma.submit();
	}
</script>
</head>
<%
	String fechaInicio 	= request.getParameter("fechaInicio")==null?"":request.getParameter("fechaInicio");
	String fechaFinal   = request.getParameter("fechaFinal")==null?"":request.getParameter("fechaFinal");
	String fechaLimite  = request.getParameter("fechaLimite")==null?"":request.getParameter("fechaLimite");
	String matricula  	= request.getParameter("matricula")==null?"":request.getParameter("matricula");
	String condicion	= "";
	String conSol		= "";
	String condAlum		= "";
	
	int enLinea 		= 0;
	int presencial 		= 0;
	int inscritos 		= 0;
	int noInscritos 	= 0;
	
	ArrayList<aca.alumno.AlumPersonal> alumnos = new ArrayList<aca.alumno.AlumPersonal>();
	if(!fechaInicio.equals("")){
		if(!matricula.equals("")){
			
			condAlum =" AND CODIGO_PERSONAL LIKE '"+matricula+"%'";
		}else{
			condAlum= " ";
		}	
		alumnos = alumnosU.getAlumnosCreados(conEnoc, fechaInicio, fechaFinal, condAlum+" ORDER BY TO_DATE(F_CREADO, 'DD/MM/YYYY') ASC");
	}else{
		fechaInicio=fechaFinal=fechaLimite=aca.util.Fecha.getHoy();		
	}
	
	if(!matricula.equals("")){
		condicion = 
		conSol = " AND MATRICULA LIKE '"+matricula+"%'";
	
	}else{

		condicion = " ";
		conSol = " ";
	}
	
	HashMap <String, aca.vista.Estadistica> alumnosInscritos = estadisticaU.getAlumnosCreados(conEnoc, fechaInicio, fechaFinal, fechaLimite, " AND CODIGO_PERSONAL LIKE '"+matricula+"%'" );
	HashMap <String, String> alumnosEnlinea = solicitudU.getAlumnosCreados(conEnoc, fechaInicio, fechaFinal, " AND MATRICULA LIKE '"+matricula+"%'");
	HashMap <String, aca.alumno.AlumPlan> mapAlumPlan = AlumPlanU.mapAlumIngreso(conEnoc, matricula);	
%>
<body>
<div class="container-fluid">
	<h2><spring:message code="aca.RegistroDeIngresos"/></h2>
	<form method="post" action="ingreso" name="forma">
	<div class="alert alert-info d-flex align-items-center">
		Start Date: <input name="fechaInicio" type="text" class="form-control" value="<%=fechaInicio%>" data-date-format="dd/mm/yyyy" id="Inicio" size="9" style="width:120px">&nbsp;&nbsp;
		End Date: <input name="fechaFinal" type="text" class="form-control" value="<%=fechaFinal%>" data-date-format="dd/mm/yyyy" id="Final" size="9" style="width:120px">&nbsp;&nbsp;
		Enrollment Date limit: <input name="fechaLimite" class="form-control" type="text" value="<%=fechaLimite%>" data-date-format="dd/mm/yyyy" id="Limite" size="9" style="width:120px">&nbsp;&nbsp;
		<spring:message code="aca.Matricula"/>:&nbsp;<input name="matricula" type="text" class="form-control" value="<%=matricula%>"  id="matricula" size="6" style="width:120px">&nbsp;&nbsp;
		<a href="javascript:Mostrar()" class="btn btn-primary"><i class="fas fa-sync-alt"></i></a>
	</div>	
	</form>
	
<!-- 	<table class="table table-condensed table-bordered table-striped"  width="85%" style="margin-right:5px;"> -->
	<table class="table table-sm table-bordered table-striped"  style="margin-right:5px;">
	<tr >
		<th>#</th>
		<th><spring:message code="aca.Matricula"/></th>
		<th><spring:message code="aca.Nombre"/></th>
		<th width="20%"><spring:message code="aca.Carrera"/></th>
		<th><spring:message code="aca.Correo"/></th>
		<th><spring:message code="aca.Creado"/></th>
		<th>User</th>
		<th><spring:message code="aca.Inscrito"/></th>
		<th><spring:message code="aca.Solicitud"/></th>
	</tr>
<%
	int cont = 0;
	for(aca.alumno.AlumPersonal alumno: alumnos){ 
		cont++;
		
		String inscrito = "-";
		String carrera = "-";
		
		if(alumnosInscritos.containsKey(alumno.getCodigoPersonal())){
			inscrito = alumnosInscritos.get(alumno.getCodigoPersonal()).getFInscripcion();
			carrera = aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, alumnosInscritos.get(alumno.getCodigoPersonal()).getCarreraId());
			inscritos++;
		}else{
			noInscritos++;
			String plan = "-";
			if (mapAlumPlan.containsKey(alumno.getCodigoPersonal())){
				plan 		= mapAlumPlan.get( alumno.getCodigoPersonal() ).getPlanId();
				carrera 	= aca.plan.PlanUtil.getCarreraId(conEnoc, plan);				
				carrera 	= aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, carrera);
			}			 
		}
		
		String solicitud = "In Person";
		if(alumnosEnlinea.containsKey(alumno.getCodigoPersonal())){
			solicitud = "Online";
			enLinea++;
		}else{
			presencial++;
		}
%>
	<tr <%=!alumno.getUsAlta().equals("9800400")?"style='background-color: #F5A9A9; border: solid #F5A9A9;'":""%>>
		<td><%=cont %></td>
		<td><%=alumno.getCodigoPersonal() %></td>
		<td><%=alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno() %></td>
		<td><%=carrera %></td>
		<td><%=alumno.getEmail() %></td>
		<td><%=alumno.getFcreado() %></td>
		<td><%=alumno.getUsAlta()%></td>
		<td><%=inscrito %></td>
		<td><%=solicitud %></td>
	</tr>
<%	} %>
	</table>
	<div class="alert alert-info">
	<table>		
		<tr>
			<td><font size="3">N° Student IDs:&nbsp;<%=alumnos.size()%></font>&nbsp;&nbsp;&nbsp;</td>
			<td><font size="3">Online:&nbsp;<%=enLinea%></font>&nbsp;&nbsp;&nbsp;</td>
			<td><font size="3">In Person:&nbsp;<%=presencial%></font>&nbsp;&nbsp;&nbsp;</td>
			<td><font size="3">Enrolled:&nbsp;<%=inscritos%></font>&nbsp;&nbsp;&nbsp;</td>
			<td><font size="3">Not Enrolled:&nbsp;<%=noInscritos%></font></td>
		</tr>
	</table>
	</div>
</div>
</body>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>
	$('#Inicio').datepicker();
	$('#Final').datepicker();
	$('#Limite').datepicker();
</script>

<%@ include file= "../../cierra_enoc.jsp" %>