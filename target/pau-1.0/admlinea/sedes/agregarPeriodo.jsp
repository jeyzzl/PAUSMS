<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="aca.admision.spring.AdmPeriodo"%>
<html>
<head>	
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>	
</head>
<%
	String eventoId 				= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
	String periodoId 				= request.getParameter("PeriodoId")==null?"1":request.getParameter("PeriodoId");
	String mensaje					= (String)request.getAttribute("mensaje");
	boolean existe					= (boolean)request.getAttribute("existe");

	AdmPeriodo admPeriodo 			= (AdmPeriodo)request.getAttribute("admPeriodo");
	List<AdmPeriodo> listPeriodos 	= (List<AdmPeriodo>)request.getAttribute("listPeriodos");
	List<String> minutos			= new ArrayList<String>();
	
	minutos.add("00");	minutos.add("05");
	minutos.add("10");	minutos.add("15");
	minutos.add("20");	minutos.add("25");
	minutos.add("30");	minutos.add("35");
	minutos.add("40");	minutos.add("45");
	minutos.add("50");	minutos.add("55");	
%>
<body>
<div class="container-fluid">
	<h1><spring:message code="aca.Periodo"/> at Venue</h1>
	<div class="alert alert-info">
		<a href="lista" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
	</div>
<% 	if(mensaje.equals("1")){%>
	<div class="alert alert-success" role="alert">
		<spring:message code="aca.Guardado"/>
	</div>
<% 	}%>
	<form action="grabarPeriodo" method="post">
	<input name="EventoId" type="hidden" value="<%=eventoId%>">
	<div class="row">
		<div class="span4">
			<label><spring:message code="aca.Periodo"/></label>
			<select name="Periodo" class="form-control" style="width:200px">
				<option value="1" <%=admPeriodo.getPeriodoId().equals("1")?"selected":""%>><spring:message code="aca.Periodo"/> 1</option>
				<option value="2" <%=admPeriodo.getPeriodoId().equals("2")?"selected":""%>><spring:message code="aca.Periodo"/> 2</option>
				<option value="3" <%=admPeriodo.getPeriodoId().equals("3")?"selected":""%>><spring:message code="aca.Periodo"/> 3</option>
				<option value="4" <%=admPeriodo.getPeriodoId().equals("4")?"selected":""%>><spring:message code="aca.Periodo"/> 4</option>
				<option value="5" <%=admPeriodo.getPeriodoId().equals("5")?"selected":""%>><spring:message code="aca.Periodo"/> 5</option>
				<option value="6" <%=admPeriodo.getPeriodoId().equals("6")?"selected":""%>><spring:message code="aca.Periodo"/> 6</option>
				<option value="7" <%=admPeriodo.getPeriodoId().equals("7")?"selected":""%>><spring:message code="aca.Periodo"/> 7</option>						
			</select>					
			<br>		
			<label><spring:message code="aca.Dia"/></label> 
			<select name="Dia" class="form-control" style="width:200px">
				<option value="2" <%=admPeriodo.getDia().equals("2") ? "selected" : ""%>><spring:message code="aca.Lunes"/></option>
				<option value="3" <%=admPeriodo.getDia().equals("3") ? "selected" : ""%>><spring:message code="aca.Martes"/></option>
				<option value="4" <%=admPeriodo.getDia().equals("4") ? "selected" : ""%>><spring:message code="aca.Miercoles"/></option>
				<option value="5" <%=admPeriodo.getDia().equals("5") ? "selected" : ""%>><spring:message code="aca.Jueves"/></option>
				<option value="6" <%=admPeriodo.getDia().equals("6") ? "selected" : ""%>><spring:message code="aca.Viernes"/></option>
			</select>	
			<br>
			<label><spring:message code="aca.Cupo"/></label> 
			<input name="Cupo" type="text" value="<%=admPeriodo.getCupo()%>" style="width:50px">
			<br><br>		
			<label><spring:message code="aca.Inicio"/> <spring:message code="aca.Hora"/></label> 
			<select name="HoraInicia" style="width: 60px;">
<%		for(int i = 1; i < 25; i++){%>
				<option value="<%=i%>" <%=admPeriodo.getHoraInicio().equals(String.valueOf(i)) ? "selected" : ""%>><%=i%></option>
<% 		}%>
			</select>
			<select name="MinutoInicia" style="width: 60px;">
<% 		for(String min : minutos){%>
			<option value="<%=min%>" <%=admPeriodo.getMinInicio().equals(min) ? "selected" : ""%>><%=min%></option>
<% 		}%>
			</select>
			<br><br>
			<label><spring:message code="aca.Final"/> <spring:message code="aca.Hora"/></label>			
			<select name="HoraFin" style="width: 60px;">
<% 				for(int i = 1; i < 25; i++){%>
					<option value="<%=i%>" <%=admPeriodo.getHoraFin().equals(String.valueOf(i)) ? "selected" : ""%>><%=i%></option>
<% 				}%>
			</select>						
			<select name="MinutoFin" style="width: 60px;">
<% 			for(String min : minutos){%>
					<option value="<%=min%>" <%=admPeriodo.getMinFin().equals(min) ? "selected" : ""%>><%=min%></option>
<% 			}%>
			</select>
			<br><br>			
		</div>						 
	</div>
	<div class="alert alert-info">
		<button type="submit" class="btn btn-primary">Guardar</button>
	</div>
	</form>
	</div>
</body>
</html>