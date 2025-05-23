<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.alumno.spring.AlumGradua"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<%
	String year				= aca.util.Fecha.getHoy().substring(6,10);
	String fechaInicio 		= request.getParameter("fechaInicio")==null?"01/01/"+year:request.getParameter("fechaInicio");
	String fechaFinal  		= request.getParameter("fechaFinal")==null?"31/12/"+year:request.getParameter("fechaFinal");	

	List<AlumGradua> lisGraduandos 				= (List<AlumGradua>)request.getAttribute("lisGraduandos");
	HashMap<String, AlumPersonal> mapaEgreso 	= (HashMap<String, AlumPersonal>)request.getAttribute("mapaEgreso");
	
	int cont 		= 1;
	String nombre 	= "";
	AlumPersonal alumPersonal = new AlumPersonal();
%>
<body>
<div class="container-fluid">
	<h2>Graduate Acknowledgments</h2>
	<form name="forma" method="post" action="listado">
	<div class="alert alert-info d-flex align-items-center">
		Start Date:&nbsp;
		<input id="fechaInicio" name="fechaInicio" class="form-control" placeholder="Start Date" data-date-format="dd/mm/yyyy" value="<%=fechaInicio%>" style="width:120px;">
			&nbsp;&nbsp;&nbsp;
			End Date:&nbsp;
			<input id="fechaFinal" name="fechaFinal" class="form-control" placeholder="End Date" data-date-format="dd/mm/yyyy" value="<%=fechaFinal%>" style="width:120px;">
			&nbsp;
			<a href="javascript:Filtrar();" class="btn btn-primary">Search</a>
	</div>
	</form>
	<table class="table table-sm table-bordered">
	<thead class="table-info">
		<tr>
			<th>#</th>
			<th>Student ID</th>
			<th>Name</th>
			<th>Plan</th>
			<th>Grad. Date</th>
			<th>Level</th>
		</tr>
	</thead>
	<tbody>	
<% 		for(AlumGradua alumno : lisGraduandos){
			if(mapaEgreso.containsKey(alumno.getCodigoPersonal())){
					alumPersonal = mapaEgreso.get(alumno.getCodigoPersonal());
					nombre = alumPersonal.getNombreLegal();
			%>
			<tr>
				<td><%=cont++%></td>
				<td><%=alumno.getCodigoPersonal()%></td>
				<td><%=nombre%></td>
				<td><%=alumno.getPlanId()%></td>
				<td><%=alumno.getFechaGraduacion()%></td>
				<td><%=alumno.getDiploma() == null ? "-" : alumno.getDiploma()%></td>
			</tr>		
<% 			}
		}%>
	</tbody>	
	</table>
	</div>
</body>
<script>
	function Filtrar(){
		document.forma.submit();
	}
	
	jQuery('#fechaInicio').datepicker();
	jQuery('#fechaFinal').datepicker();
</script>
</html>