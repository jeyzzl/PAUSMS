<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@page import="aca.tit.spring.TitProfesional"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<%	
	List<TitProfesional> lisProfesional		= (List<TitProfesional>)request.getAttribute("lisProfesional");
	HashMap<String,String> mapaTitulados	= (HashMap<String, String>)request.getAttribute("mapaTitulados");
	HashMap<String,String> mapaMatriculas	= (HashMap<String, String>)request.getAttribute("mapaMatriculas");
%>
<body>
<div class="container-fluid">
	<h2>Profesional</h2>
	<div class="alert alert-info d-flex align-items-center">		
		<a class="btn btn-primary" href="menu">Regresar</a>&nbsp;&nbsp;
		<input type="text" class="form-control search-query" placeholder="Buscar..." id="buscar"  style="width:120px;">
	</div>
	<table class="table table-sm table-bordered" id="table">
	<thead class="table-info">
	<tr>
		<th width="5%">#</th>
		<th width="5%">Folio</th>
		<th width="5%">Matricula</th>
		<th width="9%">Curp</th>
		<th width="24%">Nombre</th>
		<th width="24%">Paterno</th>
		<th width="24%">Materno</th>
		<th width="4%">Correo</th>					
	</tr>
	</thead>
	<tbody>
<%	
	int row=0;
	for (TitProfesional profesional : lisProfesional){
		row++;
		
		String matricula = "0";
		if (mapaMatriculas.containsKey(profesional.getFolio())){
			matricula = mapaMatriculas.get(profesional.getFolio());
		}
		
		String nombreAlumno = "0";
		if (mapaTitulados.containsKey(matricula)){
			nombreAlumno = mapaTitulados.get(matricula);
		}
%>	
	<tr>
		<td><%=row %></td>
		<td><%=profesional.getFolio()%></td>
		<td><%=matricula%></td>
		<td><%=profesional.getCurp()%></td>
		<td><%=profesional.getNombre()%></td>
		<td><%=profesional.getPrimerApellido() %></td>
		<td><%=profesional.getSegundoApellido() %></td>
		<td><%=profesional.getCorreoElectronico()%></td>			
	</tr>
<%
	}
%>
	</tbody>
	</table>
</div>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>
</body>
</html>