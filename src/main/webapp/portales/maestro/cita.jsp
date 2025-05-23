<%@page import="java.util.ArrayList"%>
<%@page import="aca.por.spring.PorHora"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<head>
</head>
<%
	String codigoPersonal 				= (String)session.getAttribute("codigoPersonal");	
	String equipo 						= request.getParameter("Equipo")==null?"0":request.getParameter("Equipo");
	boolean tieneHorario 				= (boolean) request.getAttribute("tieneHorario");
	String estado 						= (String) request.getAttribute("estado");
	
	HashMap<String,String> mapMaestros	= (HashMap<String,String>) request.getAttribute("mapMaestros");
	HashMap<String,String> mapSalones	= (HashMap<String,String>) request.getAttribute("mapSalones");
	HashMap<String,String> mapEstados	= (HashMap<String,String>) request.getAttribute("mapEstados");
	ArrayList<PorHora> lisEquipos 		= (ArrayList<PorHora>)request.getAttribute("lisEquipos");
	
	String nombreMaestro = "";
	if(mapMaestros.containsKey(codigoPersonal)) nombreMaestro = mapMaestros.get(codigoPersonal);
%>
<body>
<div class="container-fluid">
	<h2><spring:message code="portal.maestro.cita.PresentacionPortafolio"/><small class="text-muted">( <%=codigoPersonal%> - <%=nombreMaestro%>)</small></h2>
	<div class="alert alert-info"><a href="cursos" class="btn btn-primary"><spring:message code="portal.maestro.cita.Regresar"/></a></div>
	<table class="table table-sm">
		<tr>
			<th width="5%">#</th>
			<th width="5%"><spring:message code="portal.maestro.cita.Equipo"/></th>
			<th width="10%"><spring:message code="portal.maestro.cita.Salon"/></th>
			<th width="10%"><spring:message code="portal.maestro.cita.Dia"/></th>
			<th width="10%"><spring:message code="portal.maestro.cita.Horario"/></th>
			<th width="20%"><spring:message code="portal.maestro.cita.Empleado"/></th>
			<th width="40%"><spring:message code="portal.maestro.cita.Estado"/></th>
		<tr>
<%
	String dia = "-";
	int row = 0;
	for (PorHora hora : lisEquipos){
		row++;
		if(hora.getDia().equals("1")) dia = "Sunday";
		else if(hora.getDia().equals("2")) dia = "Monday";
		else if(hora.getDia().equals("3")) dia = "Tuesday";
		else if(hora.getDia().equals("4")) dia = "Wednesday";
		else if(hora.getDia().equals("5")) dia = "Thursday";
		else if(hora.getDia().equals("6")) dia = "Friday";
		
		String salonNombre = "-";
		if (mapSalones.containsKey(hora.getSalonId())){
			salonNombre = mapSalones.get(hora.getSalonId());
		}
		
		String nombreEmpleado = "-";
		if (mapMaestros.containsKey(hora.getCodigoPersonal())){
			nombreEmpleado = mapMaestros.get(hora.getCodigoPersonal());
		}
		
		String estadoNombre = "-";
		if (mapEstados.containsKey(hora.getCodigoPersonal())){
			estadoNombre = mapEstados.get(hora.getCodigoPersonal());
			estadoNombre = estadoNombre.equals("A")?"Open":"Confirmed";
		}else if (hora.getCodigoPersonal().equals("0")){
			estadoNombre = "No record";
		}
%>
		<tr>
			<td><%=row%></td>
			<td><span class="badge bg-success"><%=hora.getEquipoId()%></span></td>
			<td><%=salonNombre%></td>
			<td><%=dia%></td>
			<td><%=hora.getHora()%></td>
			<td>
<%
		if(tieneHorario==false && hora.getCodigoPersonal().equals("0")){
			out.print("<a href='grabarCita?Folio="+hora.getFolio()+"&Equipo="+equipo+"'><i class='fas fa-calendar-alt' ></i></a>");
		}else{
			out.print(hora.getCodigoPersonal().equals("0")?"-":nombreEmpleado);
		}

		if (codigoPersonal.equals(hora.getCodigoPersonal())){
			if (estado.equals("A")){
%>
				&nbsp;<a href="borrarCita?Folio=<%=hora.getFolio()%>&Equipo=<%=equipo%>" class="icon icon-remove"></a>
<%
			}
		}
%>
			</td>
			<td><%=estadoNombre%></td>
		<tr>
<%
	}
%>
	</table>
</div>
</body>