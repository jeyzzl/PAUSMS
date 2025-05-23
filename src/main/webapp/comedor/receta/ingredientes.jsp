<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.saum.spring.SaumIngrediente"%>
<%@page import="aca.saum.spring.SaumMateria"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<% 
	String recetaId								= request.getParameter("RecetaId")==null?"0":request.getParameter("RecetaId");
	String etapaId								= request.getParameter("EtapaId")==null?"0":request.getParameter("EtapaId");
	String recetaNombre							= (String)request.getAttribute("recetaNombre");
	String etapaNombre 							= (String)request.getAttribute("etapaNombre");
	ArrayList<SaumIngrediente> lisIngredientes	= (ArrayList<SaumIngrediente>)request.getAttribute("lisIngredientes");
	HashMap<String,SaumMateria> mapaMateria		= (HashMap<String, SaumMateria>)request.getAttribute("mapaMateria");
%>
<body>
<div class="container-fluid">
	<h2>Ingredientes de la etapa <small class="text-muted fs-4"> (<%=recetaNombre%> - <%=etapaNombre%>) </small></h2>
	<div class="alert alert-info">
		<a href="etapas?RecetaId=<%=recetaId%>&EtapaId=<%=etapaId%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a> &nbsp;
		<a href="editarIngrediente?RecetaId=<%=recetaId%>&EtapaId=<%=etapaId%>" class="btn btn-primary">Nuevo Ingrediente</a>		
	</div>
	<table id="table" class="table table-condensed">
	<thead>
	<tr>
		<th width="5%">#</th>
		<th width="10%">Opción</th>
		<th width="20%">Materia</th>
		<th width="20%">Presentacion</th>
		<th width="10%">Cantidad</th>
		<th width="10%">Medida</th>					
		<th width="20%">&nbsp;</th>
	</tr>
	</thead>
	<tbody>
<%
	int row = 0;
	for(SaumIngrediente ingrediente : lisIngredientes){
		row++;
		
		String materiaNombre = "-";
		if (mapaMateria.containsKey(ingrediente.getMateriaId()) ){
			materiaNombre = mapaMateria.get(ingrediente.getMateriaId()).getNombre();
		}
%>
	<tr>
		<td><%=row%></td>
		<td>			
			<a href="editarIngrediente?Accion=1&RecetaId=<%=ingrediente.getRecetaId()%>&EtapaId=<%=ingrediente.getEtapaId()%>&IngredienteId=<%=ingrediente.getId()%>"><i class="fas fa-edit"></i></a>&nbsp;					
			<a href="javascript:Borrar('<%=ingrediente.getRecetaId()%>','<%=ingrediente.getEtapaId()%>','<%=ingrediente.getId()%>')"><i class="fas fa-trash-alt"></i></a>			
		</td>
		<td><%=materiaNombre%></td>		
		<td><%=ingrediente.getPresentacion()%></td>
		<td><%=ingrediente.getCantidad()%></td>		
		<td><%=ingrediente.getUnidadMedida()%></td>
		<td>&nbsp;</td>
	</tr>
<%	} %>	
	</tbody>
	</table>
</div>		
</body>	
<script>
	function Borrar(recetaId, etapaId, ingredienteId){
		if (confirm("¿Estás seguro de borrar?")){
			document.location.href="borrarIngrediente?RecetaId="+recetaId+"&EtapaId="+etapaId+"&IngredienteId="+ingredienteId;
		}
	}
</script>
</html>
