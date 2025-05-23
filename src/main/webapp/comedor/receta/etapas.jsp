<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.saum.spring.SaumIngrediente"%>
<%@page import="aca.saum.spring.SaumEtapa"%>
<%@page import="aca.saum.spring.SaumMateria"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<% 	
	String recetaId								= request.getParameter("RecetaId")==null?"0":request.getParameter("RecetaId");
	String recetaNombre							= (String)request.getAttribute("recetaNombre");
	ArrayList<SaumEtapa> lisEtapas 				= (ArrayList<SaumEtapa>)request.getAttribute("lisEtapas");
	ArrayList<SaumIngrediente> lisIngredientes 	= (ArrayList<SaumIngrediente>)request.getAttribute("lisIngredientes");
	HashMap<String,String> mapaIngrediente		= (HashMap<String, String>)request.getAttribute("mapaIngrediente");
	HashMap<String,SaumMateria> mapaMateria		= (HashMap<String, SaumMateria>)request.getAttribute("mapaMateria");
%>
<body>
<div class="container-fluid">
	<h2>Etapas de la receta <small class="text-muted fs-4"> (<%=recetaId%>-<%=recetaNombre%>) </small></h2>
	<div class="alert alert-info">
		<a href="lista" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a> &nbsp;
		<a href="editarEtapa?RecetaId=<%=recetaId%>" class="btn btn-primary">Nueva Etapa</a>		
	</div>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
	<tr>
		<th width="3%">#</th>
		<th width="3%">Opción</th>
		<th width="15%">Etapa</th>
		<th width="3%" class="right">#Ingre.</th>
		<th width="51%">Procedimiento</th>		
		<th width="25%">Ingredientes</th>							
	</tr>
	</thead>
	<tbody>
<%
	int row = 0;
	for(SaumEtapa etapa : lisEtapas){
		row++;
		
		String numIngrediente = "0";
		if (mapaIngrediente.containsKey(etapa.getId()) ){
			numIngrediente = mapaIngrediente.get(etapa.getId());
		}
%>	
	<tr>
		<td><%=row%></td>
		<td>			
			<a href="editarEtapa?Accion=1&RecetaId=<%=etapa.getRecetaId()%>&EtapaId=<%=etapa.getId()%>"><i class="fas fa-edit"></i></a>&nbsp;
<% 		if (numIngrediente.equals("0")){%>								
			<a href="javascript:Borrar('<%=etapa.getRecetaId()%>','<%=etapa.getId()%>')"><i class="fas fa-trash-alt"></i></a>
<%		}%>						
		</td>
		<td><%=etapa.getNombre()%></td>		
		<td class="right">
		<a href="ingredientes?RecetaId=<%=recetaId%>&EtapaId=<%=etapa.getId()%>"><span class="badge bg-success"><%=numIngrediente%></span></a>
		</td>
		<td>
			<%=etapa.getProcedimiento()%>
		</td>	
		<td>
<%		for (SaumIngrediente ingrediente : lisIngredientes){
			if (ingrediente.getEtapaId().equals(etapa.getId())){
				String nombreMateria = "";
				if (mapaMateria.containsKey(ingrediente.getMateriaId())){
					nombreMateria = mapaMateria.get(ingrediente.getMateriaId()).getNombre();
				}
%>				
				<a class="badge bg-success" href="editarIngrediente?Accion=1&RecetaId=<%=ingrediente.getRecetaId()%>&EtapaId=<%=ingrediente.getEtapaId()%>&IngredienteId=<%=ingrediente.getId()%>">
				<i class="fas fa-edit"></i>
				</a>
				&nbsp;
				<b><%=nombreMateria%></b> <span class="badge bg-dark"><%=ingrediente.getCantidad()%>&nbsp;<%=ingrediente.getUnidadMedida()%>&nbsp;</span> ( <%=ingrediente.getPresentacion()%> )<br>
<%				
			}
 		}
%>
		</td>		
	</tr>
<%		
	}
%>	
	</tbody>
	</table>
</div>		
</body>
<script>
	function Borrar(recetaId, etapaId){
		if (confirm("¿Estás seguro de borrar?")){
			document.location.href="borrarEtapa?RecetaId="+recetaId+"&EtapaId="+etapaId;
		}
	}
</script>
</html>
