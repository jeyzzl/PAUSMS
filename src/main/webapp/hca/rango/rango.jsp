<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%@page import="aca.hca.spring.HcaRango"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.catalogo.spring.CatNivel"%>

<%
    List<HcaRango>  lisRangos						 = (List<HcaRango>) request.getAttribute("lisRangos");	
 	HashMap<String, CatNivel> mapaTotal 			 = (HashMap<String, CatNivel>)request.getAttribute("mapaTotal");
 	HashMap<String, CatModalidad> mapaTodos			 = (HashMap<String, CatModalidad>)request.getAttribute("mapaTodos");
%>
<style>
	.centrado{
		width: 430px;
		margin: auto;
	}
</style>
<div class="container-fluid">
	<h1>Rangos</h1>
<div class="alert alert-info">
	<a class="btn btn-primary" href="agregar?Accion=1&nivelId=1&modalidadId=1"><i class="icon-white icon-plus"></i>Agregar</a>
</div>
	<table class="table table-condensed table-bordered">
		<tr class="table-info">
			<th>#</th>
			<th>Op</th>
			<th>Nivel Id</th>
			<th>Modalidad Id</th>
			<th>Rango Id</th>
			<th>Rango Inicial</th>
			<th>Rango Final</th>
			<th>Valor</th>
		</tr>
	<%
		int cont = 1;
		for( HcaRango Rango: lisRangos){
			
			String nombre		 = "-";
			String modalidad	 = "-";
			if(mapaTotal.containsKey(Rango.getNivelId())){
				 nombre = mapaTotal.get(Rango.getNivelId()).getNombreNivel();
			}
		
			if(mapaTodos.containsKey(Rango.getModalidadId())){
				modalidad = mapaTodos.get(Rango.getModalidadId()).getNombreModalidad();

			}
	%>
		<tr>
			<td><%=cont %></td>
			<td>
				<a href="agregar?Accion=4&nivelId=<%= Rango.getNivelId()%>&modalidadId=<%= Rango.getModalidadId()%>&rangoId=<%=Rango.getRangoId()%>"><i class="fas fa-pencil-alt"></i></a>
				<a href="javascript:Borrar('<%=Rango.getModalidadId()%>', '<%=Rango.getNivelId()%>', '<%=Rango.getRangoId()%>');"><i class="fas fa-trash-alt"></i></a>
			</td>
			<td><%=nombre%></td>
			<td><%=modalidad%></td>
			<td><%=Rango.getRangoId() %></td>
			<td><%=Rango.getRangoIni() %></td>
			<td><%=Rango.getRangoFin() %></td>
			<td><%=Rango.getValor() %></td>
		</tr>
	<%
		cont++;
	}
		
	%>
	</table>
</div>
<script>
function Borrar(modalidad, nivel, rango) {
	if (confirm("Desea Eliminar este Rango?") == true) {
		document.location.href = "agregar?Accion=3&modalidadId="+ modalidad+"&nivelId="+nivel+"&rangoId="+rango;
	}
}
</script>
