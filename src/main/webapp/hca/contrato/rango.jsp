<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
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
	<h2>Tabla de Valores</h2>
	<div class="alert alert-info">
		<a href="docente" class="btn btn-primary"><spring:message code='aca.Regresar'/></a>
	</div>
	
	<table class="table table-condensed">
		<tr>
			<th>Nivel</th>
			<th><spring:message code="aca.Modalidad"/></th>
			<th style="text-align: center;">Rango Ini</th>
			<th style="text-align: center;">Rango Fin</th>
			<th style="text-align: center;">Valor</th>
		</tr>
	<%
		for(HcaRango Rango: lisRangos){
			
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
			<td><%=nombre%></td>
			<td><%=modalidad%></td>
			<td align="center"><%=Rango.getRangoIni() %></td>
			<td align="center"><%=Rango.getRangoFin() %></td>
			<td align="center"><%=Rango.getValor() %></td>
		</tr>
	<%
		}
	%>
	</table>	
</div>

