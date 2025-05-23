<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.plan.spring.MapaAvance"%>
<%@ page import= "aca.catalogo.spring.CatTipoCurso"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Borrar(facultad,carrera,planId,ciclo,tipo){
		if(confirm("Are you sure you want to delete this record?"))
			document.location.href="borrar?facultad="+facultad+"&carrera="+carrera+"&planId="+planId+"&ciclo="+ciclo+"&tipo="+tipo;				
	}
</script>
<%
	String facultad 	  	= request.getParameter("facultad");
	String carrera 		  	= request.getParameter("carrera");
	String planId 		  	= request.getParameter("planId");		
	String facultadNombre 	= (String) request.getAttribute("facultadNombre");
	String mensaje 		  	= request.getParameter("mensaje")==null?"-":request.getParameter("mensaje");
	
	List<MapaAvance> lista						= (List<MapaAvance>) request.getAttribute("lista");
	HashMap<String, CatTipoCurso> mapaTipos		= (HashMap<String, CatTipoCurso>) request.getAttribute("mapaTipos");
%>
<style>
	i{
		cursor:pointer;
	}
</style>
<div class="container-fluid">
	<h2><%= facultadNombre %><small class="text-muted fs-4"> ( <%= planId %> )</small></h2>
	<div class="alert alert-info d-flex align-items-center">
		<a href="listado?facultad=<%=facultad%>" class="btn btn-primary "><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
		<a href="add?planId=<%=planId %>&facultad=<%=facultad%>" class="btn btn-primary">Add Credits</a>
	</div>	
<%	if (!mensaje.equals("-")){%>
	<div class="alert alert-info"><%=mensaje%></div>	
<% 	}%>	
	<table class="table table-bordered table-sm" style="width:50%">
	<thead class="table-info">
	<tr>
		<th width="10%">Op.</th>
		<th width="20%"><spring:message code="aca.Ciclo"/></th>
		<th width="45%">Type</th>
		<th width="15%" class="text-end">Required Credits</th>
	</tr>
	</thead>
	<tbody>
<%
	int cont = 0;
	for(MapaAvance av: lista){
		cont++;
		
		String tipoNombre = "-";
		if (mapaTipos.containsKey(av.getTipocursoId())){
			tipoNombre = mapaTipos.get(av.getTipocursoId()).getNombreTipoCurso();								
		}
%>
	<tr>
		<td>
			<i class="fas fa-trash" onclick="javascript:Borrar('<%=facultad%>','<%=carrera%>','<%=planId%>','<%=av.getCiclo()%>','<%=av.getTipocursoId()%>');"></i>
			<i class="fas fa-edit" onclick="document.location.href='add?facultad=<%=facultad%>&planId=<%=av.getPlanId()%>&ciclo=<%=av.getCiclo()%>&tipo=<%=av.getTipocursoId()%>'"></i>
		</td>
		<td><%=av.getCiclo() %></td>
		<td><%=tipoNombre%></td>
		<td class="text-end"><%=av.getCreditos() %></td>
	</tr>
<%	} %>
	</tbody>
	</table>
</div>