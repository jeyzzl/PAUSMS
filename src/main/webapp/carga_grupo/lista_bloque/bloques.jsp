<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.carga.spring.CargaBloque"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%
	List<CargaBloque> lisBloques 		= (List<CargaBloque>)request.getAttribute("lisBloques");
	HashMap<String, Carga> mapaCargas 	= (HashMap<String, Carga>)request.getAttribute("mapaCargas");
%>
<body>
<div class="container-fluid mt-1">
	<h2><spring:message code="cargaGrupo.listaBloque.ListadoDeBloques" /></h2>
	<div class="alert alert-info d-flex align-items-center">
		<spring:message code="cargaGrupo.listaBloque.Filtro" /><input type="text" class="form-control search-query" placeholder="<spring:message code='aca.Buscar...' />" id="buscar" style="width:210px;">
	</div>
	<table id="tabla" class="table table-bordered">
	<thead class="table-info">
		<tr> 
			<th width="5%"><h5><spring:message code="aca.Numero" /></h5></th>
			<th width="8%"><h5><spring:message code="aca.Clave" /></h5></th>
			<th width="25%"><h5><spring:message code="aca.Carga" /></h5></th>
			<th width="7%"><h5><spring:message code="aca.Bloque" /></h5></th>
			<th width="25%"><h5><spring:message code="aca.Nombre"/></h5></th>
			<th width="10%"><h5><spring:message code="cargaGrupo.listaBloque.InicioPer"/></h5></th>
			<th width="10%"><h5><spring:message code="cargaGrupo.listaBloque.CierraMat"/></h5></th>
			<th width="10%"><h5><spring:message code="cargaGrupo.listaBloque.FinPer"/></h5></th>
		</tr>
	</thead>
	<tbody>
<%
		int i=0;
		for(CargaBloque cargaBloque : lisBloques){
			i++;
			String nombreCarga = "-";
			if (mapaCargas.containsKey(cargaBloque.getCargaId())){
				nombreCarga = mapaCargas.get(cargaBloque.getCargaId()).getNombreCarga();
			}
%>
		<tr class="tr2">
			<td><%=i%></td>
			<td><%=cargaBloque.getCargaId()%></td>
			<td><%=nombreCarga%></td>
			<td><%=cargaBloque.getBloqueId()%></td>
			<td><%=cargaBloque.getNombreBloque()%></td>
			<td><%=cargaBloque.getFInicio()%></td>
			<td><%=cargaBloque.getFCierre()%></td>
			<td><%=cargaBloque.getFFinal()%></td>
		</tr>
<%		} %>
	</tbody>
	</table>
</div>
</body>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#tabla")});
</script>