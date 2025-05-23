<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaGrupoImp"%>
<%@page import="aca.plan.spring.MapaCurso"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<% 
	List<CargaGrupoImp> lisGrupos 			= (List<CargaGrupoImp>)request.getAttribute("lisGrupos");
	HashMap<String,MapaCurso> mapaCursos 	= (HashMap<String,MapaCurso>)request.getAttribute("mapaCursos");
	HashMap<String,String> mapaMaestros 	= (HashMap<String,String>)request.getAttribute("mapaMaestros");
%>
<div class="container-fluid">
	<h2>Actas importadas</h2>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
 	<tr>
 		<th>#</th>
 		<th>Clave</th>
 		<th>Materia</th>
 		<th>Ciclo</th>
 		<th>Nómina</th>
 		<th>Maestro</th>
 		<th>Fecha</th>
 		<th>Grupo</th>
 	</tr>
 	</thead>
<%
	int row = 0;
	for(CargaGrupoImp grupo : lisGrupos){
		row++;
		
		String materia 	= "-";
		String ciclo 	= "0";
		if (mapaCursos.containsKey(grupo.getCursoId())){
			materia 	= mapaCursos.get(grupo.getCursoId()).getNombreCurso();
			ciclo		= mapaCursos.get(grupo.getCursoId()).getCiclo();
		}		
		String maestro	= "-";
		if (mapaMaestros.containsKey(grupo.getMaestro())){
			maestro 	= mapaMaestros.get(grupo.getMaestro());
		}
%>
	<tr>
		<td><%=row%></td>
		<td><%=grupo.getCursoId()%></td>
		<td><a href="acta?GrupoId=<%=grupo.getGrupoId()%>" target="_blank"><%=materia%></a></td>
		<td><%=ciclo%></td>
		<td><%=grupo.getMaestro()%></td>
		<td><%=maestro%></td>
		<td><%=grupo.getFecha()%></td>
		<td><%=grupo.getGrupo()%></td>
	</tr>	
<%	} %>			
	</table>
</div>