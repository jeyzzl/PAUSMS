<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.saum.spring.SaumReceta"%>
<%@page import="aca.saum.spring.SaumEtapa"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<% 			
	ArrayList<SaumReceta> lisRecetas 		= (ArrayList<SaumReceta>)request.getAttribute("lisRecetas");
	HashMap<String,String> mapaEtapas 		= (HashMap<String, String>)request.getAttribute("mapaEtapas");
	HashMap<String,String> mapaIngredientes	= (HashMap<String, String>)request.getAttribute("mapaIngredientes");
%>
<body>
<div class="container-fluid">
	<h1>Recetas SAUM</h1>
	<div class="alert alert-info">		
		<input type="text" class="input-medium search-query" placeholder="Buscar..." id="buscar">
	</div>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
	<tr>
		<th>#</th>
		<th>Receta</th>
		<th class="right"># Etapas</th>
		<th class="right"># Ingre.</th>
		<th>Rendimiento</th>
		<th>Temperatura</th>
		<th>Tiempo</th>
		<th>Porción</th>
		<th>Textura</th>
		<th>Tipo Plato</th>				
	</tr>
	</thead>
	<tbody>
<%
	int row = 0;
	for(SaumReceta receta : lisRecetas){
		row++;
		
		String numEtapas = "0";
		if (mapaEtapas.containsKey(receta.getId())){
			numEtapas = mapaEtapas.get(receta.getId());
		}
		
		String numIngredientes = "0";
		if (mapaIngredientes.containsKey(receta.getId())){
			numIngredientes = mapaIngredientes.get(receta.getId());
		}
%>	
	<tr>
		<td><%=row%></td>
		<td>
		<a href="proceso?RecetaId=<%=receta.getId()%>"><%=receta.getNombre()%></a>
		</td>
		<td class="right"><%=numEtapas%></td>
		<td class="right"><%=numIngredientes%></td>
		<td><%=receta.getRendimiento() %></td>
		<td><%=receta.getTemperatura() %></td>
		<td><%=receta.getTiempo() %></td>
		<td><%=receta.getPorcion( )%></td>
		<td><%=receta.getTextura() %></td>
		<td><%=receta.getTipoPlato()%></td>		
	</tr>
<%		
	}
%>	
	</tbody>
	</table>
</div>		
</body>
<script type="text/javascript" src="../../js/search.js"></script>
<script>	
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>
</html>
