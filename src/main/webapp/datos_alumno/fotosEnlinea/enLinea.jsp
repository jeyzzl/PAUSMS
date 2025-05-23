<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<head>
<%
	List<String> matriculas 				= (List<String>) request.getAttribute("matriculas");
	
	HashMap<String,AlumPersonal> mapaDatos 	= (HashMap<String,AlumPersonal>) request.getAttribute("mapaDatos");
	HashMap<String,String> mapaRechazadas 	= (HashMap<String,String>) request.getAttribute("mapaRechazadas");

	int cont = 1;
%>
</head>
<body>
	<form action="decisionFoto" name="form">
		<table class="table table-sm">
			<thead>
				<tr>
					<th>#</th>
					<th>Datos</th>
					<th>Foto</th>
					<th>Comentario</th>
					<th>Opcion</th>
				</tr>
			</thead>
			<tbody>
<%		for(String matricula : matriculas){
			AlumPersonal alumno = new AlumPersonal();
			
			if(mapaDatos.containsKey(matricula)){
				alumno = mapaDatos.get(matricula);
			}
			if(!mapaRechazadas.containsKey(matricula)){
%>
				<tr>
					<td><%=cont++%></td>
					<td>
						<strong>Matricula: </strong><%=matricula%><br>
						<strong>Nombre: </strong><%=alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno()%>
					</td>
					<td>
						<img class="rounded border border-dark" src="../../foto?Codigo=<%=matricula%>&Tipo=A" width="150">
					</td>
					<td>
						<input class="form-control" type="text" name="Comentario<%=matricula%>" placeholder="Razon por la que se rechazo la foto">
						<input type="hidden" name="<%=matricula%>" value="<%=matricula%>">
					</td>
					<td>
						<a href="decisionFoto?Aceptada=<%=matricula%>" class="btn btn-success">Aceptar</a>
						<button type="submit" class="btn btn-danger">Rechazar</button>
					</td>
				</tr>
<%			}
		}%>
			</tbody>
		</table>
	</form>
</body>
