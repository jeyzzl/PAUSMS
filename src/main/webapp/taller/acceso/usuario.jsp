<%@ page import= "java.util.* "%>
<%@ page import= "aca.financiero.spring.ContEjercicio"%>
<%@ page import= "aca.bec.spring.BecAcceso"%>
<%@ page import= "aca.financiero.spring.ContCcosto"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	String ejercicioId 			= (String) session.getAttribute("ejercicioId");
	String codigo				= (String)session.getAttribute("codigoEmpleado");
	String nombreUsuario 		= (String) request.getAttribute("nombreUsuario");mm
	
	List<ContEjercicio> ejercicios 				= (List<ContEjercicio>) request.getAttribute("lisEjercicios");	
	List<BecAcceso> lisAccesos					= (List<BecAcceso>) request.getAttribute("lisAccesos");	
	HashMap<String, ContCcosto> mapCcostos 		= (HashMap<String, ContCcosto>) request.getAttribute("mapaCostos");
	HashMap<String, String> mapaUsuarios 		= (HashMap<String, String>) request.getAttribute("mapaUsuarios");
%>
<style>
	body{
		background:white;
	}
</style>

<div class="container-fluid">
	<h2>Privilegios del Usuario<small class="text-muted fs-5">( <%=codigo%> - <%=nombreUsuario%> )</small></h2>
	<form action="usuario" name="forma" method="get">
	<div class="alert alert-info d-flex align-items-center">
		Ejercicio:&nbsp;	
		<select name="idEjercicio" id="idEjercicio"  class="form-select" style="width:120px;" onchange="document.forma.submit()">
		<%
			for(ContEjercicio ejercicio: ejercicios){	
		%>
				<option value="<%=ejercicio.getIdEjercicio() %>" <%if(ejercicioId.equals(ejercicio.getIdEjercicio()))out.print("selected"); %>><%=ejercicio.getIdEjercicio() %></option>
		<%
			}
		%>
		</select>&nbsp;
		<a href="editar" class="btn btn-primary"><i class="fas fa-plus"></i> Agregar</a>
	</div>
	</form>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>
			<th width="5%">#</th>
			<th width="15%"><spring:message code="aca.Costo"/></th>
			<th width="50%"><spring:message code="aca.Nombre"/></th>
			<th width="10%"><spring:message code="aca.Fecha"/></th>
			<th width="20%"><spring:message code='aca.Usuario'/></th>
		</tr>
	</thead>
<%
	String nombreDepartamento = ""; 
	for (int i=0; i<lisAccesos.size();i++){
		BecAcceso acceso = (BecAcceso) lisAccesos.get(i);
		
		// Busca el nombre del centro de costo
		if ( mapCcostos.containsKey(acceso.getIdCcosto()))
			nombreDepartamento = mapCcostos.get(acceso.getIdCcosto()).getNombre();
		else
			nombreDepartamento = "No existe";
		
		String nombre = "-";
		if (mapaUsuarios.containsKey(acceso.getUsuario())){
			nombre = mapaUsuarios.get(acceso.getUsuario());
		}
%>		
		<tr>
			<td><%=i+1%></td>
			<td><%=acceso.getIdCcosto()%></td>
			<td><%=nombreDepartamento%></td>
			<td><%=acceso.getFecha()%></td>
			<td><%=nombre%></td>
		</tr>
<%	} %>
	</table>
</div>