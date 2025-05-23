<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@page import="aca.tit.spring.TitAntecedente"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<%
	//String codigoAlumno 		= (String) request.getAttribute("codigoAlumno");
	
	List<TitAntecedente> lisAntecedentes	= (List<TitAntecedente>)request.getAttribute("lisAntecedentes");
	HashMap<String,String> mapaTitulados	= (HashMap<String, String>)request.getAttribute("mapaTitulados");
	HashMap<String,String> mapaMatriculas	= (HashMap<String, String>)request.getAttribute("mapaMatriculas");
%>
<body>
<div class="container-fluid">
	<h2>Antecedentes</h2>
	<div class="alert alert-info d-flex align-items-center">		
		<a class="btn btn-primary" href="menu">Regresar</a>&nbsp;
		<input type="text" class="form-control search-query" placeholder="Buscar..." id="buscar" style="width:120px">
	</div>	

	<table class="table table-sm table-bordered" id="table">
	<thead class="table-info">
	<tr>
		<th width="4%">#</th>
		<th width="5%">Folio</th>
		<th width="5%">Matrícula</th>
		<th width="19%">Alumno</th>
		<th width="5%">Institucion</th>
		<th class="text-center" width="5%">Estudio Id</th>
		<th width="19%">Estudio</th>
		<th class="text-center" width="5%">Entidad Id</th>
		<th width="19%">Entidad</th>
		<th width="5%">Fecha Inicio</th>
		<th width="4%">Fecha Terminación</th>
		<th width="5%">Cedula</th>			
	</tr>
	</thead>
	<tbody>
<%	
	int row=0;
	for (TitAntecedente antecedente : lisAntecedentes){
		row++;
		String matricula = "0";
		if (mapaMatriculas.containsKey(antecedente.getFolio())){
			matricula = mapaMatriculas.get(antecedente.getFolio());
		}
		
		String nombreAlumno = "0";
		if (mapaTitulados.containsKey(matricula)){
			nombreAlumno = mapaTitulados.get(matricula);
		}
		
%>	
	<tr>
		<td><%=row %></td>
		<td><%=antecedente.getFolio()%></td>
		<td><%=matricula %></td>	
		<td><%=nombreAlumno %></td>	
		<td><%=antecedente.getInstitucion() %></td>	
		<td align="center"><%=antecedente.getEstudioId() %></td>	
		<td><%=antecedente.getEstudio() %></td>	
		<td align="center" ><%=antecedente.getEntidadId()%></td>	
		<td><%=antecedente.getEntidad()%></td>	
		<td><%=antecedente.getFechaInicio()%></td>	
		<td><%=antecedente.getFechaTerminacion()%></td>	
		<td><%=antecedente.getCedula()%></td>	
	</tr>
<%
	}
%>
	</tbody>
	</table>
	</div>
</body>
</html>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>