<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.plan.spring.MapaNuevoCurso"%>
<%@ page import= "aca.catalogo.spring.CatTipoCurso"%>
<%@ page import= "aca.plan.spring.MapaCursoPre"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	String planOrigen			= (String) request.getAttribute("planOrigen");
	MapaCurso mapaCurso			= (MapaCurso)request.getAttribute("mapaCurso");
	
	List<MapaNuevoCurso> listaSinCursoNuevo = (List<MapaNuevoCurso>)request.getAttribute("listaSinCursoNuevo");	
	int cont = 1;
%>
<body>
<div class="container-fluid">
	<h2><spring:message code="aca.Materia"/>: <%=mapaCurso.getNombreCurso()%><small class="text-muted fs-5">( <spring:message code="mapa.materia.OrigenContenido"/>: <%=planOrigen%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="materia?planId=<%=mapaCurso.getPlanId()%>"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
		<input type="text" class="input-medium search-query" placeholder="<spring:message code='aca.Buscar...'/>" id="buscar">
	</div>	
	<table id="table" class="table">
		<thead>
			<tr>
				<th>#</th>
				<th><spring:message code="aca.Clave"/></th>
				<th><spring:message code="aca.Nombre"/></th>
				<th><spring:message code="aca.Elegir"/></th>
			</tr>
		</thead>
		<tbody>
<% 			for(MapaNuevoCurso curso : listaSinCursoNuevo){%>
			<tr>
				<td><%=cont++%></td>
				<td><%=curso.getCursoId()%></td>
				<td><%=curso.getNombre()%></td>
				<td>
					<a href="agregarCursoNuevo?CursoId=<%=mapaCurso.getCursoId()%>&planId=<%=mapaCurso.getPlanId()%>&CursoNuevo=<%=curso.getCursoId()%>" class="btn btn-info"><spring:message code="aca.Ligar"/></a>
				</td>
			</tr>
<% 			}%>
		</tbody>
	</table>	
</div>
	<script type="text/javascript" src="../../js/search.js"></script>
	<script>
		jQuery('#buscar').focus().search({table:jQuery("#table")});
	</script>
 </body>