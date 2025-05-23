<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.archivos.spring.ArchivosProfesor"%>

<script type="text/javascript">
	
</script>
<%
	String matricula 			= (String) session.getAttribute("codigoAlumno");
	String cursoCargaId			= (String) request.getAttribute("cursoCargaId");
	String materiaNombre		= (String) request.getAttribute("materiaNombre");
	String tipo	 				= request.getParameter("Tipo")==null?"":request.getParameter("Tipo");
	
	List<ArchivosProfesor> lisArchivos 	= (List<ArchivosProfesor>) request.getAttribute("lisArchivos");
%>
<div class="container-fluid">
	<h2>Subject Files <small class="text-muted fs-4"> ( <%=materiaNombre%> )</small></h2>	
	<div class="alert alert-info">
		<a class="btn btn-primary" href="detallecal?CursoCargaId=<%=cursoCargaId%>&Tipo=<%=tipo%>"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
	</div>	
	<table class="table table-sm table-bordered">
		<tr>
		<th width="10%">Option</th>
		<th width="3%">#</th>
		<th width="10%">Date</th>
		<th width="30%"><spring:message code="aca.Nombre"/></th>
		<th width="42%"><spring:message code="aca.Comentario"/></th>
		<th width="10%"><spring:message code='aca.Usuario'/></th>
	</tr>
<%	
	int cont = 0;
	for(ArchivosProfesor archivo : lisArchivos){		
		cont++;
%>
	<tr>
		<td>			
			<a href="bajarArchivo?CursoCargaId=<%=cursoCargaId%>&Folio=<%=archivo.getFolio()%>&Profesor=<%=archivo.getCodigoPersonal()%>">
				<i class="fas fa-download"></i></i>
			</a>
		</td>
		<td><%=cont %></td>
		<td><%= archivo.getFecha() %></td>
		<td><%= archivo.getNombre() %></td>
		<td><%= archivo.getComentario() %></td>
		<td><%= archivo.getCodigoPersonal() %></td>
	</tr>					
<%	} %>
	</table>
</div>