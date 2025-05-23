<%@page import="aca.vista.CargaAcademica"%>
<%@page import="aca.carga.CargaBloqueUtil"%>
<%@page import="aca.catalogo.CatHorarioUtil"%>
<%@page import="java.util.HashMap"%>

<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="CatSalon" scope="page" class="aca.catalogo.CatSalon"/>
<jsp:useBean id="CatSalonU" scope="page" class="aca.catalogo.SalonUtil"/>
<jsp:useBean id="CargaGrupoHoraU" scope="page" class="aca.carga.CargaGrupoHoraUtil"/>
<jsp:useBean id="CargaAcademica" scope="page" class="aca.vista.CargaAcademica"/>


<html>
<head>
</head>
<%
	
	String dia 				= request.getParameter("dia");
	String periodo			= request.getParameter("periodo");
	String salonId 			= request.getParameter("salonId");
	String cargaId 			= request.getParameter("cargaId");
	String bloque			= request.getParameter("bloque");
	String horarioId		= request.getParameter("horarioId");
	String Ver				= request.getParameter("Ver");
	String CarreraId		= request.getParameter("CarreraId");
	String PlanId			= request.getParameter("PlanId");
	String CursoCargaId		= request.getParameter("CursoCargaId");
	String Materia			= request.getParameter("Materia");
	String Grupo			= request.getParameter("Grupo");
	String Profesor			= request.getParameter("Profesore");
	String EdificioId		= request.getParameter("EdificioId");
	HashMap<String, ArrayList<aca.carga.CargaGrupoHora>> mapMateria  = CargaGrupoHoraU.getPeriodoHashMap(conEnoc, cargaId, bloque, horarioId, salonId);
	CatSalon = CatSalonU.mapeaRegId(conEnoc, salonId);
	String ndia = "";
	if(dia.equals("1")){
		ndia = "Sunday";
	}else if(dia.equals("2")){
		ndia = "Monday";
	}else if(dia.equals("6")){
		ndia = "Tuesday";
	}else if(dia.equals("4")){
		ndia = "Wednesday";
	}else if(dia.equals("5")){
		ndia = "Thursday";
	}else if(dia.equals("6")){
		ndia = "Friday";
	}else if(dia.equals("7")){
		ndia = "Saturday";
	}
	 
	
	
%>
<body>
<div class="container-fluid">
<h2>Subjects in the room <%=CatSalon.getNombreSalon()%>	</h2>
<div class="alert alert-info">
	<h3>Day: <%=ndia %> &nbsp;&nbsp; Period: <%=periodo %></h3>
</div>
<div class="alert alert-info">
	<a class=" btn btn-primary" href="horario.jsp?Ver=<%=Ver %>&CarreraId=<%=CarreraId%>&PlanId=<%=PlanId %>&CursoCargaId=<%=CursoCargaId%>&Materia=<%=Materia%>&Grupo=<%=Grupo%>&Profesor=<%=Profesor %>&EdificioId=<%=EdificioId%>&SalonId=<%=salonId%>&horarioId=<%=horarioId%>">
		<i class="fas fa-arrow-left"></i> Return
	</a>
</div>
<table class="table table-bordered">
	<tr>
		<th>#</th>
		<th><spring:message code="aca.Carrera"/></th>
		<th><spring:message code="aca.Plan"/></th>
		<th>Subject <spring:message code="aca.Nombre"/></th>
		<th>Semester</th>
		<th><spring:message code="aca.Maestro"/></th>
	</tr>
<%
	String nombreMateria = "", carrera = "", plan = "", semestre = "", profesor ="";
	int cont = 1;
	if(mapMateria.containsKey(dia+","+periodo)){
		ArrayList<aca.carga.CargaGrupoHora> cgh1 = mapMateria.get(dia+","+periodo);
		for(aca.carga.CargaGrupoHora obj : cgh1){
			nombreMateria =	aca.vista.CargaAcademica.getNombreMateria(conEnoc, obj.getCursoCargaId());
			CargaAcademica.mapeaRegId(conEnoc, obj.getCursoCargaId(), aca.carga.CargaGrupoCursoUtil.cursoIdOrigen(conEnoc, obj.getCursoCargaId()));
			carrera = CargaAcademica.getCarreraId();
			semestre = CargaAcademica.getCiclo();
			profesor = CargaAcademica.getCodigoPersonal();
			plan = CargaAcademica.getPlanId();
%>
	<tr>
		<td><%=cont %></td>
		<td><%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, carrera) %></td>
		<td><%=plan %></td>
		<td><%=nombreMateria %></td>
		<td><%=semestre %></td>
		<td><%=aca.emp.EmpleadoUtil.getNombreEmpleado(conEnoc, profesor, "") %></td>
	</tr>
<% 
		cont++;
		} 
	}	
%>
</table>
</div>
</body>
</html>
<%@ include file="../../cierra_enoc.jsp"%>