<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.archivos.spring.ArchivosProfesor"%>

<script type="text/javascript">
	function BorrarArchivo(cursoCargaId,folio,profesor){	
		if (confirm("Are you sure to delete the file ?")){
			document.location.href="borrarArchivo?Accion=1&CursoCargaId="+cursoCargaId+"&Folio="+folio+"&Profesor="+profesor;
		}			
	}	
</script>
<%
	String cursoCargaId			= (String) request.getAttribute("cursoCargaId");
	String materiaNombre		= (String) request.getAttribute("materiaNombre");
	List<ArchivosProfesor> lisArchivos 	= (List<ArchivosProfesor>) request.getAttribute("lisArchivos");
%>
<div class="container-fluid">
	<h2><spring:message code="portal.maestro.filesMaestro.ArchivosCurso"/><small class="text-muted"> ( <%=materiaNombre%> )</small></h2>	
	<div class="alert alert-info">
		<a class="btn btn-primary" href="evaluar?CursoCargaId=<%=cursoCargaId%>&EvaluacionId=0"><spring:message code='aca.Regresar'/></a>
		<a class="btn btn-info" href="subirArchivo?CursoCargaId=<%=cursoCargaId%>&EvaluacionId=0"><i class="fas fa-upload"></i> <spring:message code="portal.maestro.filesMaestro.SubirArchivo"/></a>	
	</div>	
	<table class="table table-bordered">
		<tr>
		<th width="10%"><spring:message code="portal.maestro.filesMaestro.Opcion"/></th>
		<th width="3%">#</th>
		<th width="17%"><spring:message code="portal.maestro.filesMaestro.Fecha"/></th>
		<th width="30%"><spring:message code="aca.Nombre"/></th>
		<th width="35%"><spring:message code="aca.Comentario"/></th>		
		<th width="10%"><spring:message code='aca.Usuario'/></th>
	</tr>
<%	int cont = 0;
	for(ArchivosProfesor archivo : lisArchivos){
		cont++;
%>
	<tr>
		<td>			
			<a href="editarComentario?CursoCargaId=<%=cursoCargaId%>&Folio=<%=archivo.getFolio()%>&Profesor=<%=archivo.getCodigoPersonal()%>"><i class="fas fa-edit"></i></a>			
			&nbsp;			
			<a href="bajarArchivo?CursoCargaId=<%=cursoCargaId%>&Folio=<%=archivo.getFolio()%>&Profesor=<%=archivo.getCodigoPersonal()%>"><i class="fas fa-download"></i></a>			
			&nbsp;			
			<a href="javascript:BorrarArchivo('<%=cursoCargaId%>','<%=archivo.getFolio()%>','<%=archivo.getCodigoPersonal()%>');"><i class="fas fa-trash" style="color:red"></i></a>
		</td>
		<td><%=cont %></td>
		<td><%=archivo.getFecha() %></td>
		<td><%=archivo.getNombre() %></td>
		<td><%=archivo.getComentario() %></td>		
		<td><%=archivo.getCodigoPersonal() %></td>
	</tr>					
<%	}%>
	</table>
</div>