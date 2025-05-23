<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import="aca.vista.spring.Inscritos" %>
<%@ page import="aca.catalogo.spring.CatPais" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	List<CatPais> lisPaises 			= (List<CatPais>) request.getAttribute("lisPaises");
	HashMap<String,String> mapaAlumnos 	= (HashMap<String,String>) request.getAttribute("mapaTotalPorPais");
%>
<div class="container-fluid">
	<h2>Paises Representados</h2>
	<div class="alert alert-info">
		<a href="procedencia" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<h4>Muestra todos los paises de alumnos inscritos incluyendo todas las modalidades</h4>
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
		<th>#</th>
		<th><spring:message code="aca.Pais"/></th>
		<th><spring:message code="aca.Inscritos"/></th>
		<th>Interamerica</th>
	</tr>
	</thead>
<% 
	int contador = 0;
	for(CatPais pais: lisPaises){
		contador++;
		
		String total = "0";
		if(mapaAlumnos.containsKey(pais.getPaisId())){
			total = mapaAlumnos.get(pais.getPaisId());
		}
%>
	<tr>
		<td><%=contador%></td>
		<td><%=pais.getNombrePais() %></td>
		<td><%=total%></td>
		<td><%=pais.getInteramerica() %></td>
	</tr>
<%	} %>
</table>
</div>