<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String cargaId 			= request.getParameter("f_carga");
	String cargaNombre		= (String) request.getAttribute("cargaNombre");
%>

<div class="container-fluid">
	<h2>Carga: <%=cargaId%> <%=cargaNombre%></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="elige_carga"><spring:message code="aca.Regresar"/></a>
	</div>
	<table style="width:180px"  class="table">
	<tr align="left">
		<td>
			<li><a href="extranjeros?f_carga=<%=cargaId%>&f_carga_nombre=<%=cargaNombre%>">Por Facultad y Carrera</a></li>
		</td>
	</tr>
	<tr align="left">
		<td>
			<li><a href="extranjerosp?f_carga=<%=cargaId%>&f_carga_nombre=<%=cargaNombre%>">Por Nacionalidad</a></li>
		</td>
	</tr>
	</table>
</div>
<!-- fin de estructura -->