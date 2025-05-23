<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.saum.spring.SaumMateria"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<% 	
	ArrayList<SaumMateria> lisMaterias 		= (ArrayList<SaumMateria>)request.getAttribute("lisMaterias");
	HashMap<String,String> mapaMateria 		= (HashMap<String, String>)request.getAttribute("mapaMateria");
%>
<body>
<div class="container-fluid">
	<h2>Materias primas<small class="text-muted fs-4"> ( Alimentos )</small></h2>
	<div class="alert alert-info">
		<a href="editar" class="btn btn-primary">Nueva</a> &nbsp;
		<input type="text" class="input-medium search-query" placeholder="Buscar..." id="buscar">
	</div>
	<table id="table" class="table table-condensed">
	<thead>
	<tr>
		<th width="5%">#</th>
		<th width="5%">Opción</th>
		<th width="35%" >Materia prima</th>	
		<th width="7%" class="right">Energía</th>
		<th width="7%" class="right">Fibra</th>
		<th width="8%" class="right">Carbohidratos</th>
		<th width="7%" class="right">Proteína</th>
		<th width="7%" class="right">Lípidos</th>
		<th width="7%" class="right">Colesterol</th>
		<th width="10%" class="right">Uso/recetas</th>		
	</tr>
	</thead>
	<tbody>
<%
	int row = 0;
	for(SaumMateria materia : lisMaterias){
		row++;
		
		String numMateria = "0";
		if (mapaMateria.containsKey(materia.getId())){
			numMateria = mapaMateria.get(materia.getId());
		}
%>
	<tr>
		<td><%=row%></td>
		<td>			
			<a href="editar?Accion=1&MateriaId=<%=materia.getId()%>"><i class="fas fa-edit"></i></a>&nbsp;
<% 		if (numMateria.equals("0")){ %>			
			<a href="javascript:Borrar('<%=materia.getId()%>')"><i class="fas fa-trash-alt"></i></a>&nbsp;
<% 		} %>			
		</td>
		<td><%=materia.getNombre()%></td>		
		<td class="right"><%=materia.getEnergia()%></td>
		<td class="right"><%=materia.getFibra()%></td>
		<td class="right"><%=materia.getCarbohidrato()%></td>
		<td class="right"><%=materia.getProteina()%></td>
		<td class="right"><%=materia.getLipido()%></td>
		<td class="right"><%=materia.getColesterol()%></td>
		<td class="right"><%=numMateria%></td>
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
	function Borrar(id){
		if (confirm("¿Estás seguro de borrar?")){
			document.location.href="borrar?Id="+id;
		}
	}
	
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>
</html>
