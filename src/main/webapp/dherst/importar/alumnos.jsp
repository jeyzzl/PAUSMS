<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@page import="aca.dherst.spring.DherstArchivo"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%  
	List<DherstArchivo> listArchivos = (List<DherstArchivo>) request.getAttribute("listArchivos");

	HashMap<String,String> mapaAlumnosPorArchivo = (HashMap<String,String>) request.getAttribute("mapaAlumnosPorArchivo");
%>
<body>
<div class="container-fluid">
    <h2>Import DHERST Students</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="subirArchivo">Load file</a>
	</div>
	<table class="table table-bordered">
		<thead class="table-info">
			<tr>
				<th width="5%">No.</th>
				<th width="15%">Date</th>
				<th width="10%">Num. Students</th>
				<th width="40%">Comment</th>
				<th width="30%">File</th>
			</tr>
		</thead>
		<tbody>
<%		int row = 0;
		for(DherstArchivo archivo : listArchivos){
			row++;
			String numAlumnos = "0";

			if(mapaAlumnosPorArchivo.containsKey(archivo.getFolio())){
				numAlumnos = mapaAlumnosPorArchivo.get(archivo.getFolio());
			}
%>
			<tr>
				<td><%=row%></td>
				<td><%=archivo.getFecha()%></td>
				<td><%=numAlumnos%></td>
				<td><%=archivo.getComentario()%></td>
				<td><a href="subirArchivo?folio=<%=archivo.getFolio()%>"><%=archivo.getNombre()%></a></td>
			</tr>
<%		}%>
		</tbody>
	</table>
</div>
</body>

