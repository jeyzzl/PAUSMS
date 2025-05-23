<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="aca.financiero.spring.ContEjercicio" %>
<%@ page import="aca.financiero.spring.ContCcosto" %>
<%@ page import="aca.bec.spring.BecFija" %>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<html>
<body>
<%
	String ejercicioId  						= request.getParameter("EjercicioId")==null?aca.util.Fecha.getEjercicioHoy():request.getParameter("EjercicioId");
	List<ContEjercicio> lisEjercicios			= (List<ContEjercicio>)request.getAttribute("lisEjercicios");
	List<BecFija> lisFijas						= (List<BecFija>)request.getAttribute("lisFijas");
	HashMap<String, ContCcosto> mapaDeptos		= (HashMap<String, ContCcosto>)request.getAttribute("mapaDeptos");
	HashMap<String, String> mapaMaestros		= (HashMap<String, String>)request.getAttribute("mapaMaestros");
%>
<style>
 	body{
 		background : white;
 	}
</style>
<div class="container-fluid">
	<h2>Permisos adicional/fija</h2>
	<form action="fija" method="post" name="frmFija" id="frmFija">
	<div class="alert alert-info">
		<select id="EjercicioId" name="EjercicioId" onchange="document.frmFija.submit();">
<%	for(ContEjercicio ej : lisEjercicios){%>
			<option value="<%=ej.getIdEjercicio()%>" <%=ej.getIdEjercicio().equals(ejercicioId)?"Selected":""%>> <%=ej.getIdEjercicio()%></option>									
<%	}%>				
		</select>
		<a class="btn btn-primary" href="editar?EjercicioId=<%=ejercicioId%>"><i class="fas fa-plus"></i> Agregar centro de costo</a>
	</div>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">
	<tr>
		<th>#</th>
		<th>Centro de costo</th>
		<th><spring:message code="aca.Fecha"/></th>
		<th><spring:message code='aca.Usuario'/></th>
	</tr>
	</thead>
<%	int row = 0;
	for(BecFija bec : lisFijas){
		row++;
		String nombreDep = "-";
		if (mapaDeptos.containsKey(bec.getIdCcosto())){
			nombreDep = mapaDeptos.get(bec.getIdCcosto()).getNombre();
		}
		
		String nombreEmp = "-";
		if (mapaMaestros.containsKey(bec.getUsuario())){
			nombreEmp = mapaMaestros.get(bec.getUsuario());
		}
%>
	<tr>
		<td><%=row%></td>
		<td><%=nombreDep%></td>
		<td><%=bec.getFecha()%></td>
		<td><%=nombreEmp%></td>
	</tr>				
<%	} %>				
	</table>
	</form>
</div>
</body>
</html>