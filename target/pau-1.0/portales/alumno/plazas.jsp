<%@page import="java.util.*" %>
<%@page import="java.text.*" %>

<%@ page import="aca.bec.spring.BecPeriodo"%>
<%@ page import="aca.bec.spring.BecPlazas"%>
<%@ page import="aca.financiero.spring.ContCcosto"%>
<%@ page import="aca.financiero.spring.ContEjercicio"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
	//Variables
	String ejercicioId									= request.getParameter("ejercicioId")==null?aca.util.Fecha.getEjercicioHoy():request.getParameter("ejercicioId");
	String periodoId 									= (String)request.getAttribute("periodoId");
	
	//Ejercicios a partir del 2013
	List<ContEjercicio> listEjercicios 		= (List<ContEjercicio>)request.getAttribute("listEjercicios");
	
	// Lista de centros de costo
	List<ContCcosto> listCostos 			= (List<ContCcosto>)request.getAttribute("listCostos");

	List<BecPeriodo> periodos 				= (List<BecPeriodo>)request.getAttribute("periodos");	
	
	//Plazas básicas total en los centros de costo
	HashMap<String, String> totalPlazas					= (HashMap<String, String>)request.getAttribute("totalPlazas");
	HashMap<String, String> totalPlazasB				= (HashMap<String, String>)request.getAttribute("totalPlazasB");
	HashMap<String, String> totalPlazasT				= (HashMap<String, String>)request.getAttribute("totalPlazasT");
	HashMap<String, String> totalPlazasI				= (HashMap<String, String>)request.getAttribute("totalPlazasI");
	HashMap<String, String> totalPlazasP				= (HashMap<String, String>)request.getAttribute("totalPlazasP");
	HashMap<String, String> totalPlazasM				= (HashMap<String, String>)request.getAttribute("totalPlazasM");
	HashMap<String, String> mapJefes 					= (HashMap<String, String>)request.getAttribute("mapJefes");
	HashMap<String, String> plazasAsignadas 			= (HashMap<String, String>)request.getAttribute("plazasAsignadas");
	HashMap<String,BecPlazas> mapaPlazas 				= (HashMap<String, BecPlazas>)request.getAttribute("mapaPlazas");
	
	int basica	 				= 0;
	int temporal				= 0;
	int institucional			= 0;
	int preInstitucional		= 0;	
	int postgrado				= 0;
	
	int totalBasica	 			= 0;
	int totalTemporal			= 0;
	int totalInstitucional		= 0;
	int totalPreInstitucional	= 0;	
	int totalPostgrado			= 0;
	
	int basUsada				= 0;
	int temUsada				= 0;
	int insUsada				= 0;
	int preUsada				= 0;	
	int posUsada				= 0;
	
	int totalUsadaBasica			= 0;
	int totalUsadaTemporal			= 0;
	int totalUsadaInstitucional		= 0;
	int totalUsadaPreInstitucional	= 0;	
	int totalUsadaPostgrado			= 0;
	
	String matricula 		= (String)session.getAttribute("codigoAlumno");	
	// style="float: right;"
%>
<%@ include file= "menu.jsp" %>
<div class="container-fluid">
	<a href="previos"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;
	<span class="fs-5"><spring:message code="portal.alumno.plazas.DepartamentosParaRealizarElServicioBecario"/></span>
		<small class="text-muted fs-6">(&nbsp;
<%-- 			<span class="badge rounded-pill bg-success">&nbsp;&nbsp;</span> <spring:message code="portal.alumno.plazas.Asignadas"/>&nbsp;&nbsp; --%>
<%-- 			<span class="badge rounded-pill bg-warning">&nbsp;&nbsp;</span> <spring:message code="portal.alumno.plazas.Usadas"/>&nbsp;&nbsp; --%>
			<span class="badge rounded-pill bg-dark">&nbsp;&nbsp;</span> <spring:message code="portal.alumno.plazas.Disponibles"/>&nbsp;&nbsp;
			<span class="badge rounded-pill bg-warning">&nbsp;&nbsp;</span> <spring:message code="portal.alumno.plazas.Agotadas"/>&nbsp;
		 )&nbsp;
		 </small>	
	<form name="form" action="plazas" method="get">
	<div class="alert alert-info d-flex align-items-center">
		<strong><spring:message code="portal.alumno.plazas.Periodo"/>:&nbsp;</strong>		
		<input type="hidden" name="ejercicioId" value="<%=ejercicioId%>">
		<select name="PeriodoId" class="form-select" onchange="document.form.submit();" style="width:250px;">
<%	for(BecPeriodo periodo : periodos){%>
			<option <%=periodoId.equals(periodo.getPeriodoId())?"selected":""%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getPeriodoNombre()%></option>
<%	}%>
		</select>		
	</div>
	</form>		 	
	<table class="table table-sm table-striped table-bordered">
	<tr>
		<th>#</th>
		<th class="text-center"><spring:message code="aca.Consulta"/></th>
		<th><spring:message code="portal.alumno.plazas.Departamento"/></th>
		<th><spring:message code="portal.alumno.plazas.Contacto"/></th>
		<th><spring:message code="portal.alumno.plazas.Telefono"/></th>
		<th><spring:message code="portal.alumno.plazas.Correo"/></th>		
		<th><spring:message code="portal.alumno.plazas.Basica"/></th>
		<th><spring:message code="portal.alumno.plazas.Temporal"/></th>		
		<th><spring:message code="portal.alumno.plazas.Posgrado"/></th>
	</tr>	
<%
		int row = 0;
		for(ContCcosto  depto: listCostos){
			row++;		
			
			// Plazas Basicas
			basica = 0;
			if (totalPlazasB.containsKey(depto.getIdCcosto())){
				basica = Integer.parseInt(totalPlazasB.get(depto.getIdCcosto()));
			}
			totalBasica += basica;
			basUsada = 0;
			if(plazasAsignadas.containsKey(depto.getIdCcosto()+"B")){
				basUsada = Integer.parseInt(plazasAsignadas.get(depto.getIdCcosto()+"B"));
			}
			totalUsadaBasica += basUsada;
			
			// Plazas temporales
			temporal = 0;
			if (totalPlazasT.containsKey(depto.getIdCcosto())){
				temporal = Integer.parseInt(totalPlazasT.get(depto.getIdCcosto())); 
			}
			totalTemporal += temporal;
			temUsada = 0;
			if(plazasAsignadas.containsKey(depto.getIdCcosto()+"T")){
				temUsada = Integer.parseInt(plazasAsignadas.get(depto.getIdCcosto()+"T"));
			}
			totalUsadaTemporal += temUsada;
			
			// Plazas Institucionales
			institucional = 0;
			if (totalPlazasI.containsKey(depto.getIdCcosto())){
				institucional = Integer.parseInt(totalPlazasI.get(depto.getIdCcosto()));	
			}
			totalInstitucional += institucional;
			insUsada = 0;
			if(plazasAsignadas.containsKey(depto.getIdCcosto()+"I")){
				insUsada = Integer.parseInt(plazasAsignadas.get(depto.getIdCcosto()+"I"));
			}
			totalUsadaInstitucional += insUsada;
			
			// Plazas Pre-Institucionales
			preInstitucional = 0;
			if (totalPlazasP.containsKey(depto.getIdCcosto())){
				preInstitucional = Integer.parseInt(totalPlazasP.get(depto.getIdCcosto()));	
			}
			totalPreInstitucional += preInstitucional;
			preUsada = 0;
			if(plazasAsignadas.containsKey(depto.getIdCcosto()+"P")){
				preUsada = Integer.parseInt(plazasAsignadas.get(depto.getIdCcosto()+"P"));
			}
			totalUsadaPreInstitucional += preUsada;
			
			// Plazas Posgrado
			postgrado = 0;
			if (totalPlazasM.containsKey(depto.getIdCcosto())){
				postgrado = Integer.parseInt(totalPlazasM.get(depto.getIdCcosto()));	
			}
			totalPostgrado += postgrado;
			posUsada = 0;
			if(plazasAsignadas.containsKey(depto.getIdCcosto()+"M")){
				posUsada = Integer.parseInt(plazasAsignadas.get(depto.getIdCcosto()+"M"));
			}
			totalUsadaPostgrado += posUsada;

			BecPlazas becPlazas = new BecPlazas();
			if(mapaPlazas.containsKey(depto.getIdCcosto())){
				becPlazas = mapaPlazas.get(depto.getIdCcosto());
			}
%>			
		<tr>
			<td><%=row%></td>
			<td class="text-center"><a href="plazasDepto?EjercicioId=<%=ejercicioId%>&PeriodoId=<%=periodoId%>&DeptoId=<%=depto.getIdCcosto()%>"><i class="fas fa-search"></i></a></td>
			<td><%=depto.getNombre()%></td>
			<td><%=becPlazas.getContacto()%></td>
			<td><%=becPlazas.getTelefono()%></td>
			<td><%=becPlazas.getCorreo()%></td>			
			<td>
<%-- 				<span class="badge rounded-pill bg-success"><%=String.valueOf(basica).length()==1?"0":""%><%=basica%></span>&nbsp; --%>
<%-- 				<span class="badge rounded-pill bg-warning"><%=String.valueOf(basUsada).length()==1?"0":""%><%=basUsada%></span> --%>
				<%	if (basica-basUsada>0){%>
				<span class="badge rounded-pill bg-dark"><%=String.valueOf(basica-basUsada).length()==1?"0":""%><%=basica-basUsada%></span>
				<%	}else{%>
				<span class="badge rounded-pill bg-warning"><%=String.valueOf(basica-basUsada).length()==1?"0":""%><%=basica-basUsada%></span>
				<% 	}%>				
			</td>			
			<td>
<%-- 				<span class="badge rounded-pill bg-success"><%=String.valueOf(temporal).length()==1?"0":""%><%=temporal%></span>&nbsp; --%>
<%-- 				<span class="badge rounded-pill bg-warning"><%=String.valueOf(temUsada).length()==1?"0":""%><%=temUsada%></span> --%>
				<%	if (temporal-temUsada>0){%>
				<span class="badge rounded-pill bg-dark"><%=String.valueOf(temporal-temUsada).length()==1?"0":""%><%=temporal-temUsada%></span>
				<%	}else{%>
				<span class="badge rounded-pill bg-warning"><%=String.valueOf(temporal-temUsada).length()==1?"0":""%><%=temporal-temUsada%></span>
				<% 	}%>				
			</td>
<!-- 			<td> -->
<%-- 				<span class="badge rounded-pill bg-success"><%=String.valueOf(institucional).length()==1?"0":""%><%=institucional%></span>&nbsp; --%>
<%-- 				<span class="badge rounded-pill bg-warning"><%=String.valueOf(insUsada).length()==1?"0":""%><%=insUsada%></span> --%>
<%-- 				<%	if (institucional-insUsada>0){%> --%>
<%-- 				<span class="badge rounded-pill bg-dark"><%=String.valueOf(institucional-insUsada).length()==1?"0":""%><%=institucional-insUsada%></span> --%>
<%-- 				<%	}else{%> --%>
<%-- 				<span class="badge rounded-pill bg-secondary"><%=String.valueOf(institucional-insUsada).length()==1?"0":""%><%=institucional-insUsada%></span> --%>
<%-- 				<% 	}%>				 --%>
<!-- 			</td> -->
<!-- 			<td> -->
<%-- 				<span class="badge rounded-pill bg-success"><%=String.valueOf(preInstitucional).length()==1?"0":""%><%=preInstitucional%></span>&nbsp; --%>
<%-- 				<span class="badge rounded-pill bg-warning"><%=String.valueOf(preUsada).length()==1?"0":""%><%=preUsada%></span> --%>
<%-- 				<%	if (preInstitucional-preUsada>0){%> --%>
<%-- 				<span class="badge rounded-pill bg-dark"><%=String.valueOf(preInstitucional-preUsada).length()==1?"0":""%><%=preInstitucional-preUsada%></span> --%>
<%-- 				<%	}else{%> --%>
<%-- 				<span class="badge rounded-pill bg-secondary"><%=String.valueOf(preInstitucional-preUsada).length()==1?"0":""%><%=preInstitucional-preUsada%></span> --%>
<%-- 				<% 	}%>				 --%>
<!-- 			</td> -->
			<td>
<%-- 				<span class="badge rounded-pill bg-success"><%=String.valueOf(postgrado).length()==1?"0":""%><%=postgrado%></span>&nbsp; --%>
<%-- 				<span class="badge rounded-pill bg-warning"><%=String.valueOf(posUsada).length()==1?"0":""%><%=posUsada%></span> --%>
				<%	if (postgrado-posUsada>0){%>
				<span class="badge rounded-pill bg-dark"><%=String.valueOf(postgrado-posUsada).length()==1?"0":""%><%=postgrado-posUsada%></span>
				<%	}else{%>
				<span class="badge rounded-pill bg-warning"><%=String.valueOf(postgrado-posUsada).length()==1?"0":""%><%=postgrado-posUsada%></span>
				<% 	}%>				
			</td>			
		</tr>		
<%		} %>		
		<tr>
			<td colspan="5">&nbsp;</td>			
			<td><h4><spring:message code="portal.alumno.plazas.TOTAL"/></h4></td>
			<td><%=totalBasica %>-<%= totalUsadaBasica %>=<%= totalBasica - totalUsadaBasica %></td>
			<td><%=totalTemporal %>-<%= totalUsadaTemporal %>=<%= totalTemporal - totalUsadaTemporal %></td>
<%-- 			<td><%=totalInstitucional %>-<%= totalUsadaInstitucional %>=<%= totalInstitucional - totalUsadaInstitucional %></td> --%>
<%-- 			<td><%=totalPreInstitucional %>-<%= totalUsadaPreInstitucional %>=<%= totalPreInstitucional - totalUsadaPreInstitucional%></td> --%>
			<td><%=totalPostgrado %>-<%= totalUsadaPostgrado %>=<%= totalPostgrado - totalUsadaPostgrado %></td>
		</tr>		
	</table>
</div>
<style>
	body{
 		background : white;
 	}
 </style>