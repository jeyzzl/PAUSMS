<%@page import="com.itextpdf.text.Document"%>
<%@ page import= "aca.util.*"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.conva.spring.ConvMateria"%>
<%@page import="aca.plan.spring.MapaCredito"%>

<script type="text/javascript">
</script>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<% 
	String codigoAlumno		= (String)request.getAttribute("codigoAlumno");
	String codigoPersonal	= (String)request.getAttribute("codigoPersonal");
	String nombreAlumno  	= (String)request.getAttribute("nombreAlumno");
	String carreraId		= (String)request.getAttribute("carreraId");
	String nombreCarrera	= (String)request.getAttribute("nombreCarrera");		
	String convalidacionId	= (String)request.getAttribute("convalidacionId");	
	String mensaje 			= (String)request.getAttribute("mensaje");	
	String planElegido  	= (String)request.getAttribute("planElegido");	
	String planActual  		= (String)request.getAttribute("planActual");	
	
	List<String> lisPlanes 				= (List<String>)request.getAttribute("lisPlanes");
	List<MapaCurso> lisCursos 			= (List<MapaCurso>)request.getAttribute("lisCursos");	
	HashMap<String,String> mapaPlanes	= (HashMap<String,String>)request.getAttribute("mapaPlanes");
	
	int cont = 1;
%>
<div class="container-fluid">
	<h3>Solicitud interna por ciclo<small class="text-muted fs-6">&nbsp;( <%=codigoAlumno%> - <%=nombreAlumno%> )</small></h3>
	<form name="formPlan" action="solicitudInternaNotas"> 
		<input type="hidden" name="ConvalidacionId" value="<%=convalidacionId%>">
		<div class="alert alert-info d-flex align-items-center">
			<a class="btn btn-primary" href="interna"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;&nbsp; 
			Plan origen
			<select name="PlanId" style="width:450px" onchange="formPlan.submit()" class="form-select">
<%
			for(String plan : lisPlanes){
				String nombrePlan = "";
				if(mapaPlanes.containsKey(plan)){
					nombrePlan = mapaPlanes.get(plan);
				}
%> 
				<option value="<%=plan%>" <%=plan.equals(planElegido)?"selected":""%>>[<%=plan%>] <%=nombrePlan%></option>
<% 
			}
%> 
			</select>&nbsp;&nbsp;&nbsp; 
		</div>
	 </form>
<% 
	if(!mensaje.equals("0")){
		out.print("<div class='alert alert-success'>"+mensaje+"</div>");
	}
%> 
	 
	<div class="row">
		<div class="span12">
			<h2>Convalidar <small class="text-muted fs-4">( Plan actual : <%=planActual%>)</small><br></h2>
			<form name="formSinNota" action="solicitudInternaNotas" method="post"> 
			<input type="hidden" name="ConvalidacionId" value="<%=convalidacionId%>">			
			<input type="hidden" name="PlanId" value="<%=planElegido%>">		
			<input type="hidden" name="Accion" value="2">
			<table class="table table-sm table-bordered">
			<thead class="table-info">
			<tr>
				<th>Ciclo</th>
				<th>Materia</th>
				<th>Nota</th>
				<th>Fecha</th>
			</tr>
			</thead>
			<tbody>
<% 				
		int row 	= 0;
		String nota = "0";
		for(MapaCurso curso : lisCursos){
			row++;
 %>
			<tr>
				<td><%=curso.getCiclo()%></td>
				<td><%=curso.getNombreCurso()%></td>
				<td><input type="text" id="Nota<%=curso.getCursoId()%>" name="Nota<%=curso.getCursoId()%>" value="<%=nota%>"></td>
				<td><input type="text" id="fecha<%=row%>" name="Fecha<%=curso.getCursoId()%>" data-date-format="dd/mm/yyyy" maxlength="10" size="12"/></td>
			</tr>
<%		} %> 
 			<tbody>
			</table>
			<div class="alert alert-info">
				<button type="submit" class="btn btn-primary">Grabar</button>			
			</div>
		</form>
	</div>
	</div>
</div>
<script>
	jQuery('#fecha1').datepicker();
	jQuery('#fecha2').datepicker();
	jQuery('#fecha3').datepicker();
	jQuery('#fecha4').datepicker();
	jQuery('#fecha5').datepicker();
	jQuery('#fecha6').datepicker();
	jQuery('#fecha7').datepicker();
	jQuery('#fecha8').datepicker();
	jQuery('#fecha9').datepicker();
	jQuery('#fecha10').datepicker();
	jQuery('#fecha11').datepicker();
	jQuery('#fecha12').datepicker();
	jQuery('#fecha13').datepicker();
	jQuery('#fecha14').datepicker();
	jQuery('#fecha15').datepicker();
	jQuery('#fecha16').datepicker();
	jQuery('#fecha17').datepicker();
	jQuery('#fecha18').datepicker();
	jQuery('#fecha19').datepicker();
	jQuery('#fecha20').datepicker();
	jQuery('#fecha21').datepicker();
	jQuery('#fecha22').datepicker();
	jQuery('#fecha23').datepicker();
	jQuery('#fecha24').datepicker();
	jQuery('#fecha25').datepicker();
	jQuery('#fecha26').datepicker();
	jQuery('#fecha27').datepicker();
	jQuery('#fecha28').datepicker();
	jQuery('#fecha29').datepicker();
	jQuery('#fecha30').datepicker();
	jQuery('#fecha31').datepicker();
	jQuery('#fecha32').datepicker();
	jQuery('#fecha33').datepicker();
	jQuery('#fecha34').datepicker();
	jQuery('#fecha35').datepicker();
	jQuery('#fecha36').datepicker();
	jQuery('#fecha37').datepicker();
	jQuery('#fecha38').datepicker();
	jQuery('#fecha39').datepicker();
	jQuery('#fecha40').datepicker();
	jQuery('#fecha41').datepicker();
	jQuery('#fecha42').datepicker();
	jQuery('#fecha43').datepicker();
	jQuery('#fecha44').datepicker();
	jQuery('#fecha45').datepicker();
	jQuery('#fecha46').datepicker();
	jQuery('#fecha47').datepicker();
	jQuery('#fecha48').datepicker();
	jQuery('#fecha49').datepicker();
	jQuery('#fecha50').datepicker();
</script>