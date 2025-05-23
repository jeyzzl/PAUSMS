<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatEstado"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%	
	List<CatEstado> lisEstados 					= (List<CatEstado>)request.getAttribute("lisEstados");
	HashMap<String,String> mapaAlumPorEstados 	= (HashMap<String,String>)request.getAttribute("mapaAlumPorEstados");	
	
	HashMap<String,String> mapaColores 			= new HashMap<String,String>();
	
	mapaColores.put("1", "#f70c0c");
	mapaColores.put("2", " #f79b0c");
	mapaColores.put("3", "#f7f00c");
	mapaColores.put("4", " #3ef70c ");
	
	String alumnos = "0";

%>
<div class="container-fluid">
	<h2>Semáforo por estados</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary btn-sm" href="menu">Regresar</a>
	</div>
	<table class="table table-sm table-bordered">
	<thead>
	<tr>
		<th>Estado</th>
		<th style="text-align:right">Alumnos</th>
		<th style="text-align:right">Semaforo</th>
	</tr>
	</thead>
	<tbody>
<%
	int totAlumnos = 0;

	for (CatEstado estado : lisEstados){
		
		String numAlumnos = "0";
		if (mapaAlumPorEstados.containsKey(estado.getPaisId()+","+estado.getEstadoId())){
			numAlumnos = mapaAlumPorEstados.get(estado.getPaisId()+","+estado.getEstadoId());
		}
		totAlumnos += Integer.parseInt(numAlumnos);
	
		String color = "white";
		if (mapaColores.containsKey(estado.getSemaforo())){
			color = mapaColores.get(estado.getSemaforo());
		}
%>		
		
		<tr>
			<td><%=estado.getNombreEstado()%></td>
			<td style="text-align:right">
				<%=numAlumnos%>				
			</td>
			<td style="text-align:right"><span class="badge" style="background:<%=color%>;">&nbsp;</span></td>
		</tr>
<% 	}%>		
		<tr>
			<td colspan="1"><h4>TOTALES</h4></td>
			<td style="text-align:right"><h4><%=totAlumnos%></h4></td>
			<td style="text-align:right">&nbsp;</td>
		<tr>
	</tbody>
	</table>
</div>
