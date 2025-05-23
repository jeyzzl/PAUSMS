<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaEnLinea"%>
<%@page import="aca.carga.spring.CargaPracticaAlumno"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<head>
	<script type="text/javascript">
		function cambioCarga(){
			var cargaId = document.getElementById("CargaId").value;
			location.href = "listado?CargaId="+cargaId;
		}
	</script>
</head>
<%
	String cargaId 	= (String)request.getAttribute("cargaId");

	List<CargaEnLinea> cargas 		= (List<CargaEnLinea>)request.getAttribute("cargas");
	List<aca.Mapa> lisAlumnosCarga 	= (List<aca.Mapa>)request.getAttribute("lisAlumnosCarga");
	
	HashMap<String,CargaPracticaAlumno> mapaPracticaAlumnos = (HashMap<String,CargaPracticaAlumno>)request.getAttribute("mapaPracticaAlumnos");
	HashMap<String,List<aca.Mapa>> mapaFechas 				= (HashMap<String,List<aca.Mapa>>)request.getAttribute("mapaFechas");
	
	String liberar 	= "-";
%>
<body>
<div class="container-fluid">
	<h2>Alumnos en carga</h2>
	<div class="alert alert-info">
		<select onchange="javascript:cambioCarga();" id="CargaId" name="CargaId" class="form-select" style="width:400px;">
<% 			for (CargaEnLinea carga : cargas){%>
			<option <%=cargaId.equals(carga.getCargaId()) ? "selected" : ""%> value="<%=carga.getCargaId()%>"><%=carga.getCargaId()+" - "+carga.getNombre()%></option>
<% 			}%>
		</select>		
	</div>
	<table class="table">
	<thead class="table-info">
		<tr>
			<th>Matricula</th>
			<th>Nombre</th>
			<th>Liberar</th>
		</tr>
	</thead>
	<tbody> 
<%	for (aca.Mapa alumno : lisAlumnosCarga){
		if(mapaPracticaAlumnos.containsKey(alumno.getLlave())){
			liberar = mapaPracticaAlumnos.get(alumno.getLlave()).getEstado();
		}
%>
		<tr>
			<td>
				<%=alumno.getLlave()%>
			</td>
			<td><%=alumno.getValor()%></td>
			<td><%=liberar.equals("L") ? "Si" : "No"%></td>
		</tr>
<% 		if(mapaFechas.containsKey(alumno.getLlave())){
			List<aca.Mapa> fechas = mapaFechas.get(alumno.getLlave());
%>
		<tr>
			<td rowspan="<%=fechas.size()%>">
				<h3>Fechas</h3>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table class="table">
					<thead>
						<tr>
							<th>Inicia</th>
							<th>Termina</th>
						</tr>
					</thead>
<% 						for (aca.Mapa fecha : fechas){%>
							<tr>
								<td>
								<%=fecha.getLlave()%>
								</td>
								<td>
									<%=fecha.getValor()%>
								</td>
							</tr>
<% 						}%>
						</table>
					</td>
				</tr>
<% 				}%>
<% 			}%>
			</tbody>
		</table>
	</div>
</body>