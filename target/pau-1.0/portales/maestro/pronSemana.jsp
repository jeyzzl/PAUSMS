<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>

<% 
	aca.pron.spring.PronSemana pronSemana = (aca.pron.spring.PronSemana)request.getAttribute("pronSemana");
	aca.plan.spring.MapaNuevoUnidad unidad	  = (aca.plan.spring.MapaNuevoUnidad)request.getAttribute("unidad");

	aca.plan.spring.MapaNuevoUnidad mapaNuevoUnidad = (aca.plan.spring.MapaNuevoUnidad)request.getAttribute("mapaNuevoUnidad");
	List<aca.plan.spring.MapaNuevoActividad> listNuevoActividades = (List<aca.plan.spring.MapaNuevoActividad>)request.getAttribute("listNuevoActividades");
	List<aca.pron.spring.PronSemana> semanasUnidad = (List<aca.pron.spring.PronSemana>)request.getAttribute("semanasUnidad");

	String cursoCargaId = request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	
	String unidadId		= (String)request.getAttribute("unidadId");
	
	String actividades ="";
	int cont = 1;
%>
<body>
	<div class="container-fluid">
		<h1>Unidad <%=unidad.getUnidadId()%>: <small><%=unidad.getNombre()%></small></h1><br>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="pronUnidad?CursoCargaId=<%=cursoCargaId%>"><spring:message code="aca.Regresar"/></a>
		</div>	
		<h3>Referencia plan diamante</h3>
		<div class="alert alert-info" role="alert">
			<div class="row">
				<div class="col-md-4">
					<h3>Contenido:<br><label><%=mapaNuevoUnidad.getTemas()%></label></h3>
				</div>
<%				for(aca.plan.spring.MapaNuevoActividad actividad : listNuevoActividades){
					actividades += cont++ +".- "+ actividad.getDescripcion()+"<br>";
}%>
				<div class="col-md-4">
					<h3>Actividades:<br><label><%=actividades%></label></h3>	
				</div>
				<div class="col-md-4">
					<h3>Evidencias:<br><label><%=mapaNuevoUnidad.getAccionDocente()%></label></h3>
				</div>
			</div>
		</div>
		<div class="alert alert-success" role="alert">
			<form name="frmSemana" action="grabaPronSemana" method="POST">
				<div class="row">
					<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
					<input type="hidden" name="UnidadId" value="<%=unidadId%>">
					<input type="hidden" name="SemanaId" value="<%=pronSemana.getSemanaId()%>">
					<div class="col-md-4">
						<strong>Nombre semana:<br></strong><input type="text" class="form-control" name="SemanaNombre" value="<%=pronSemana.getSemanaNombre()%>">		
					</div>
					<div class="col-md-4">
						<strong>Contenido:<br></strong><input type="text" class="form-control" name="Contenido" value="<%=pronSemana.getContenido()%>">		
					</div>
					<div class="col-md-4">
						<strong>Actividades:<br></strong><input type="text" class="form-control" name="Actividades" value="<%=pronSemana.getActividades()%>">		
					</div>
					<div class="col-md-4">
						<strong>Evidencias:<br></strong><input type="text" class="form-control" name="Evidencias" value="<%=pronSemana.getEvidencias()%>">
					</div>
					<div class="col-md-1">
						<strong>Orden:<br></strong><input type="text" class="form-control" name="Orden" value="<%=pronSemana.getOrden()%>">
					</div>
					<div class="col-md-3">
						<br><input type="submit" class="btn btn-primary" value="Guardar">
					</div>
				</div>
			</form>
		</div>
		<table class="table table-striped">
			<thead>
				<tr class="success">
					<th>Semana</th>
					<th>Contenido</th>
					<th>Actividades de aprendizaje</th>
					<th>Evidencias de aprendizaje</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
<% 			for(aca.pron.spring.PronSemana semana : semanasUnidad){%>
				<tr>
					<td><%=semana.getSemanaNombre()%></td>
					<td><%=semana.getContenido()%></td>
					<td><%=semana.getActividades()%></td>
					<td><%=semana.getEvidencias()%></td>
					<td style="text-align:center">
						<a href="editaPronSemana?CursoCargaId=<%=cursoCargaId%>&UnidadId=<%=semana.getUnidadId()%>&SemanaId=<%=semana.getSemanaId()%>" title="Editar"><i class="fas fa-pencil-alt"></i></a>&nbsp;
						<a href="borraPronSemana?CursoCargaId=<%=cursoCargaId%>&UnidadId=<%=semana.getUnidadId()%>&SemanaId=<%=semana.getSemanaId()%>" title="Eliminar"><i class="fas fa-trash" ></i></a>
					</td>
				</tr>
<% 			}%>
			</tbody>
		</table>
	</div>
</body>