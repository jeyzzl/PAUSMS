<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@page import="aca.tit.spring.TitExpedicion"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<%
	//String codigoAlumno 		= (String) request.getAttribute("codigoAlumno");
	
	List<TitExpedicion> lisExpedicion			= (List<TitExpedicion>)request.getAttribute("lisExpedicion");
	HashMap<String,String> mapaTitulados		= (HashMap<String, String>)request.getAttribute("mapaTitulados");
	HashMap<String,String> mapaMatriculas		= (HashMap<String, String>)request.getAttribute("mapaMatriculas");
	HashMap<String,String> mapaServicios		= (HashMap<String, String>)request.getAttribute("mapaServicios");
%>
<body>
<div class="container-fluid">
	<h2>Expedicion</h2>
	<div class="alert alert-info d-flex align-items-center">		
		<a class="btn btn-primary" href="menu">Regresar</a>&nbsp;
		<input type="text" class="form-control search-query" placeholder="Buscar..." id="buscar" style="width:120px">
	</div>	

	<table class="table table-sm table-bordered" id="table">
	<thead class="table-info">
	<tr>
		<th width="5%">#</th>
		<th width="5%">Folio</th>
		<th width="5%">Matrícula</th>
		<th width="19%">Alumno</th>
		<th width="5%">Fecha Expedicion</th>
		<th width="5%">Modalidad Id</th>
		<th width="10%">Modalidad</th>
		<th width="4%">Fecha Examen</th>
		<th width="5%">Fecha Exencion</th>
		<th class="text-center" width="4%">Id Servicio</th>
		<th width="5%">Servicio</th>
		<th class="text-center" width="5%">Fundamento Id</th>
		<th width="19%">Fundamento</th>	
		<th class="text-center" width="5%">Entidad Id</th>	
		<th width="4%">Entidad</th>			
	</tr>
	</thead>
	<tbody>
<%	
	int row=0;
	for (TitExpedicion expedicion : lisExpedicion){
		row++;
		String matricula = "0";
		if (mapaMatriculas.containsKey(expedicion.getFolio())){
			matricula = mapaMatriculas.get(expedicion.getFolio());
		}
		
		String nombreAlumno = "0";
		if (mapaTitulados.containsKey(matricula)){
			nombreAlumno = mapaTitulados.get(matricula);
		}
		
		String servicioNombre = "-";
		if (mapaServicios.containsKey(expedicion.getServicio())){
			servicioNombre = mapaServicios.get(expedicion.getServicio());
		}
%>	
	<tr>
		<td><%=row %></td>
		<td><%=expedicion.getFolio() %></td>	
		<td><%=matricula%></td>	
		<td><%=nombreAlumno%></td>	
		<td><%=expedicion.getFechaExpedicion() %></td>	
		<td align="center"><%=expedicion.getModalidadId() %></td>	
		<td><%=expedicion.getModalidad()%></td>	
		<td><%=expedicion.getFechaExamen() %></td>	
		<td><%=expedicion.getFechaExencion()%></td>	
		<td align="center"><%=expedicion.getServicio() %></td>
		<td ><%=servicioNombre%></td>
		<td align="center"><%=expedicion.getFundamentoId() %></td>
		<td><%=expedicion.getFundamento() %></td>
		<td align="center"><%=expedicion.getEntidadId() %></td>
		<td><%=expedicion.getEntidad() %></td>	
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