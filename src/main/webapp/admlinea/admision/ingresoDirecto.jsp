<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmDirecto"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>	
<%	 
	String promedio			= (String)request.getAttribute("promedio");
	String nombreAlumno		= (String)request.getAttribute("nombreAlumno");
	String nombrePlan		= (String)request.getAttribute("nombrePlan");	
	AdmDirecto admDirecto 	= (AdmDirecto)request.getAttribute("admDirecto");
%>
<script type="text/javascript">
</script>
<body>
	<div class="container-fluid">
		<h1>Folio: <%=admDirecto.getFolio()%> <small>(<%=admDirecto.getMatricula()%> - <%=nombreAlumno%>)</small></h1>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="mostrarProceso?Folio=<%=admDirecto.getFolio()%>">Regresar</a>
		</div>
			<div class="col-sm-12">
				<h1>Ingreso directo:</h1>
				<table class="table">
					<thead>
						<tr>
							<th><h2>Requisito</h2></th>
							<th><h2>Estado</h2></th>
							<th><h2>Opción</h2></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>- Terminado en los últimos 5 años</td>
							<td>
<% 							if(admDirecto.getReciente().equals("-")){%>
								<span style="font-size:18px;" class="label label-info">Sin revisar</span>
<% 							}else if(admDirecto.getReciente().equals("S")){%>
								<span style="font-size:18px;" class="label label-success">Aprobado</span>
<% 							}else{%>
								<span style="font-size:18px;" class="label label-warning">No aprobada</span>
<% 							}%>
							</td>
							<td>
								<a href="reciente?Folio=<%=admDirecto.getFolio()%>&Aprobar=S" class="btn btn-success">Aprobar</a>
								<a href="reciente?Folio=<%=admDirecto.getFolio()%>&Aprobar=N" class="btn btn-danger">Rechazar</a>
							</td>
						</tr>
						<tr>
							<td>- Haber cursado tres tetramestres</td>
							<td>
<% 							if(admDirecto.getTetra().equals("-")){%>
								<span style="font-size:18px;" class="label label-info">Sin revisar</span>
<% 							}else if(admDirecto.getTetra().equals("S")){%>
								<span style="font-size:18px;" class="label label-success">Aprobado</span>
<% 							}else{%>
								<span style="font-size:18px;" class="label label-warning">No aprobada</span>
<% 							}%>
							</td>
							<td>
								<a href="tetra?Folio=<%=admDirecto.getFolio()%>&Aprobar=S" class="btn btn-success">Aprobar</a>
								<a href="tetra?Folio=<%=admDirecto.getFolio()%>&Aprobar=N" class="btn btn-danger">Rechazar</a>
							</td>
						</tr>
						<tr>
							<td>- Haber mantenido promedio de 9 sin materias reprobadas</td>
							<td>
								<span style="font-size:18px;" class="label label-success">Aprobado</span>
							</td>
							<td></td>
						</tr>
						<tr>
							<td>- Recomendación de escuela preparatoria</td>
							<td>
<% 							if(admDirecto.getRecPrepa() == null){%>
								<span style="font-size:18px;" class="label label-warning">Pendiente</span>
<% 							}else if(admDirecto.getRecPrepa() != null && admDirecto.getPrepaValido().equals("-")){%>
								<span style="font-size:18px;" class="label label-info">Sin revisar</span>&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="btn btn-primary" title="Descargar archivo" href="bajarRecPrepa?Folio=<%=admDirecto.getFolio()%>"><i class="fas fa-download"></i></a>
<% 							}else if(admDirecto.getPrepaValido().equals("S")){%>
								<span style="font-size:18px;" class="label label-success">Aprobada</span>&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="btn btn-primary" title="Descargar archivo" href="bajarRecPrepa?Folio=<%=admDirecto.getFolio()%>"><i class="fas fa-download"></i></a>
<% 							}else{%>
								<span style="font-size:18px;" class="label label-default">No aprobada</span>&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="btn btn-primary" title="Descargar archivo" href="bajarRecPrepa?Folio=<%=admDirecto.getFolio()%>"><i class="fas fa-download"></i></a>
<% 							}%>
							</td>
							<td>
<% 							if(admDirecto.getRecPrepa() != null){%>
								<a href="prepaValido?Folio=<%=admDirecto.getFolio()%>&Aprobar=S" class="btn btn-success">Aprobar</a>
								<a href="prepaValido?Folio=<%=admDirecto.getFolio()%>&Aprobar=N" class="btn btn-danger">Rechazar</a>
<% 							}%>
							</td>
						</tr>
						<tr>
							<td>- Recomendación de VRE</td>
							<td>
<% 							if(admDirecto.getRecVre() == null){%>
								<span style="font-size:18px;" class="label label-warning">Pendiente</span>
<% 							}else if(admDirecto.getRecVre() != null && admDirecto.getVreValido().equals("-")){%>
								<span style="font-size:18px;" class="label label-info">Sin revisar</span>&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="btn btn-primary" title="Descargar archivo" href="bajarRecVre?Folio=<%=admDirecto.getFolio()%>"><i class="fas fa-download"></i></a>
<% 							}else if(admDirecto.getVreValido().equals("S")){%>
								<span style="font-size:18px;" class="label label-success">Aprobada</span>&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="btn btn-primary" title="Descargar archivo" href="bajarRecVre?Folio=<%=admDirecto.getFolio()%>"><i class="fas fa-download"></i></a>
<% 							}else{%>
								<span style="font-size:18px;" class="label label-default">No aprobada</span>&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="btn btn-primary" title="Descargar archivo" href="bajarRecVre?Folio=<%=admDirecto.getFolio()%>"><i class="fas fa-download"></i></a>
<% 							}%>
							</td>
							<td>
<% 							if(admDirecto.getRecVre() != null){%>
								<a href="vreValido?Folio=<%=admDirecto.getFolio()%>&Aprobar=S" class="btn btn-success">Aprobar</a>
								<a href="vreValido?Folio=<%=admDirecto.getFolio()%>&Aprobar=N" class="btn btn-danger">Rechazar</a>
<% 							}%>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
	</div>	
</body>