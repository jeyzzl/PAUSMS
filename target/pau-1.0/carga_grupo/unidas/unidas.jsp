<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaGrupoCurso"%>
<%@page import="aca.carga.spring.CargaGrupo"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%
	String cargaId 			= (String)session.getAttribute("cargaId");
	String nombreCarga 		= (String)request.getAttribute("nombreCarga");

	List<CargaGrupoCurso> lisGrupos					= (List<CargaGrupoCurso>) request.getAttribute("lisGrupos");
	HashMap<String, MapaCurso> mapaMaterias 		= (HashMap<String, MapaCurso>) request.getAttribute("mapaMaterias");
	HashMap<String, CatModalidad> mapaModalidades	= (HashMap<String, CatModalidad>) request.getAttribute("mapaModalidades");
	HashMap<String, CargaGrupo> mapaGrupos			= (HashMap<String, CargaGrupo>) request.getAttribute("mapaGrupos");
%>
<div class="container-fluid">
	<h2><spring:message code="cargasGrupos.unidas.Titulo"/> <small class="text-muted fs-4">( <%=cargaId%> <%=nombreCarga%> )</small></h2>
	<hr>	
  	<table class="table table-bordered">
	<thead class="table-info">
  	<tr>  		
  		<th><spring:message code="aca.Acta"/></th>
  		<th><spring:message code="aca.Plan"/></th>
  		<th><spring:message code="aca.Clave"/></th>
  		<th><spring:message code="aca.Nombre"/></th>
  		<th><spring:message code="aca.Tipo"/></th>
  		<th><spring:message code="aca.Modalidad"/></th>
  		<th><spring:message code="aca.AbbrCreditos"/></th>
  	</tr>
  	</thead>
  	<tbody>
<%	
	for (CargaGrupoCurso curso: lisGrupos){		
		
		MapaCurso materia = new MapaCurso();
		if (mapaMaterias.containsKey(curso.getCursoId())){
			materia = mapaMaterias.get(curso.getCursoId());
		}
		
		String modalidadId 		= "0";
		String modalidadNombre 	= "-";
		
		if (mapaGrupos.containsKey(curso.getCursoCargaId())){
			modalidadId = mapaGrupos.get(curso.getCursoCargaId()).getModalidadId();			
			if (mapaModalidades.containsKey( modalidadId)){
				modalidadNombre = mapaModalidades.get(modalidadId).getNombreModalidad();
			}
		}
		
		String colorOrigen = "";
		if (curso.getOrigen().equals("O")) colorOrigen = "class='alert alert-info'";
%>
	<tr <%=colorOrigen%>>
  		<td><%=curso.getCursoCargaId() %></td>
  		<td><%=materia.getPlanId()%></td>
  		<td><%=curso.getCursoId() %></td>
  		<td><%=materia.getNombreCurso()%></td>
  		<td><%=curso.getOrigen()%></td>
  		<td><%=modalidadNombre%></td>
  		<td><%=materia.getCreditos()%></td>
  	</tr>	
<%	} %>  	
  	</tbody> 	
  	</table>
 </div> 	