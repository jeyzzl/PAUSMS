<%@ page import= "java.util.ArrayList"%>
<%@ page import= "java.util.HashMap"%>
<%@page import="aca.tit.spring.TitAlumno"%>
<%@page import="aca.tit.spring.TitInstitucion"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%	
	String folio					= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String codigoAlumno 			= (String) session.getAttribute("codigoAlumno");
	String nombreAlumno				= (String) request.getAttribute("nombreAlumno");
	String institucion 				= (String) request.getAttribute("institucion");
	TitInstitucion titInstitucion	= (TitInstitucion) request.getAttribute("titInstitucion");
	String planId 					= (String) request.getAttribute("planId");
%>

<div class="container-fluid">
	<h2>Institución<small class="text-muted fs-4"> ( <%=codigoAlumno%> - <%=nombreAlumno%> - <%=planId%> )</small></h2>
	<form name="frmInstitucion" action="cambiarInstitucion" method="post">
	<input name="Folio" type="hidden" value="<%=folio%>">	
	<div class="alert alert-info">		
		<a class="btn btn-primary" href="titulacion"><i class="fas fa-arrow-left"></i></a>
	</div>	
	</form>
	<table class="table table-sm table-bordered">  
	<thead class="table-info">
		<tr>			
			<th>#</th>
			<th width="10%">Clave</th>
			<th width="40%">Nombre/Institución</th>
			<th width="50%">&nbsp;</th>						
		</tr>
	</thead>
	<tbody>	
		<tr>			
			<td><%=1%></td>
			<td><%=titInstitucion.getCveInstitucion()%></td>
			<td><%=titInstitucion.getNombreInstitucion()%></td>
			<td>&nbsp;</td>
		</tr>
	
	</tbody>
	</table>
</div>