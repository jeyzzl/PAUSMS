<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.spring.Maestros"%>
<%@page import="aca.portafolio.spring.PorNivel"%>
<%@page import="aca.portafolio.spring.PorPeriodo"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<% 	String porMenu= "1";%>
<%@ include file="menuPortal.jsp"%>

<jsp:useBean id="fecha" scope="page" class="aca.util.Fecha" />

<%
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String periodoId		= (String) session.getAttribute("porPeriodo");
	
	Maestros maestro 		= (Maestros)request.getAttribute("maestro");
	String paisOrigen		= (String)request.getAttribute("paisOrigen");
	String curp 			= (String)request.getAttribute("curp");
	String rfc 				= (String)request.getAttribute("rfc");
	
	List<aca.portafolio.spring.PorPeriodo> lisPeriodos	= (List<aca.portafolio.spring.PorPeriodo>)request.getAttribute("lisPeriodos");
	
	List<aca.portafolio.spring.PorEstudio> lisEstudios = (List<aca.portafolio.spring.PorEstudio>)request.getAttribute("lisEstudios");
	
	HashMap<String,PorNivel> mapNiveles = (HashMap<String,PorNivel>)request.getAttribute("mapNiveles");
%>
<div class="container-fluid">
	<h2>Información personal</h2>
	<hr/>
	<form name="form" action="datos">
	<div class="alert alert-info d-flex align-items-center">
		Elige periodo:&nbsp;
		<select name="PeriodoId" onchange="javaScritp:cambioPeriodo()" class="form-select" style="width:120px;">
		<% for(PorPeriodo periodo : lisPeriodos){%>
			<option value="<%=periodo.getPeriodoId()%>" <%=periodo.getPeriodoId().equals(periodoId)?"selected":""%>><%=periodo.getPeriodoNombre()%></option>
		<% }%>
		</select>
	</div>	
	</form>	
	<table  class="table table-sm table-bordered" style="height: 328px; width: 700px;">	
		<tr>
			<td rowspan="7"><div id="sombra">
					<img src="../../foto?Codigo=<%=codigoPersonal%>&Tipo=O" width="200px"/>
				</div></td>
			<td><b><spring:message code='aca.Nomina'/>:</b></td>
			<td><%=codigoPersonal %></td>
		</tr>
		<tr>
			<td><b><spring:message code="aca.Nombre" />:</b></td>
			<td><%=maestro.getNombre() %> <%=maestro.getApellidoPaterno() %>
				<%=maestro.getApellidoMaterno() %></td>
		</tr>
		<tr>
			<td><b><spring:message code='aca.FechaNac' />:</b></td>
			<% if(maestro.getfNacimiento() !=null && !maestro.getfNacimiento().equals("")) {%>
			<td><%=fecha.getDia(maestro.getfNacimiento()) %> de <%=fecha.getMesNombre(maestro.getfNacimiento()) %>
				de <%=fecha.getYear(maestro.getfNacimiento()) %></td>
			<% } else {%>
			<td>&nbsp;</td>
			<% }%>
		</tr>
		<tr>
			<td><b><spring:message code='aca.Genero' />:</b></td>
			<td><%=maestro.getGenero().equals("M")?"Masculino":"Femenino" %></td>
		</tr>
		<tr>
			<td><b><spring:message code='aca.PaisDeOrigen' />:</b></td>
			<td><%=paisOrigen%></td>
		</tr>
		<tr>
			<td><b><spring:message code='aca.Curp' />:</b></td>
			<td><%=curp%></td>
		</tr>
		<tr>
			<td><b><spring:message code='aca.RFC' />:</b></td>
			<td><%=rfc%></td>
		</tr>
	</table>
	<br>	
	<div class="alert alert-info ">
		<a href="../../datos_profesor/vitae/vitae?moduloId=A06&carpeta=datos_profesor&ppe=true" class="btn btn-primary btn-block">ALTA DE CURRICULUM</a>
	</div>
</div>
<script type="text/javascript">
	jQuery('.datos').addClass('active');

	function cambioPeriodo(){	
		document.form.submit();
	}
</script>
