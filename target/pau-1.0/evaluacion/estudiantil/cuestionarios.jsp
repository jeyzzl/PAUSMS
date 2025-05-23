<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.edo.spring.EdoPeriodo"%>
<%@page import="aca.edo.spring.Edo"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	List<Edo> lisEdo	= (List<Edo>) request.getAttribute("lisEdo");

	HashMap<String, EdoPeriodo> mapaPeriodos 	= (HashMap<String, EdoPeriodo>) request.getAttribute("mapaPeriodos");
	HashMap<String, String> mapaPreguntas 		= (HashMap<String, String>) request.getAttribute("mapaPreguntas");
%>
<head>
	<script type="text/javascript">
		function modificar(edoId){
			window.location.href = "nuevo?EdoId="+edoId;
		}
		
		function borrar(edoId){
			if(confirm("¿Está seguro que desea borrar el Cuestionario?")){
				window.location.href = "borrar?EdoId="+edoId;
			}
		}
		
		function preguntas(edoId){
			window.location.href = "preguntas?EdoId="+edoId;
		}
		
		function checkSize(obj, event){
			if(event.keyCode != 8)
				if(obj.value.length > 1000)
					return false;
			return true;
		}
	</script>
</head>
<body>
<div class="container-fluid">
	<h2>Cuestionarios de Opini&oacute;n Estudiantil</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="nuevo">Nuevo</a>
	</div>
	<table class="table table-sm table-sptriped table-bordered">
	<tr class="table-info">
		<th width="40px"><spring:message code="aca.Operacion"/></th>
		<th><spring:message code="aca.Numero"/></th>
		<th><spring:message code="aca.Nombre"/></th>
		<th>F. Inicio</th>
		<th>F. Final</th>
		<th>Periodo</th>		
		<th><spring:message code="aca.Tipo"/></th>
		<th># Preg.</th>
	</tr>
<%
	int i = 0;	
	for(Edo edo : lisEdo){
		
		String periodoNombre	= "-";
		if(mapaPeriodos.containsKey(edo.getPeriodoId())){
			periodoNombre 		= mapaPeriodos.get(edo.getPeriodoId()).getPeriodoNombre();
		}
		
		String preguntas	= "0";
		if(mapaPreguntas.containsKey(edo.getEdoId())){
			preguntas 		= mapaPreguntas.get(edo.getEdoId());
		}
%>
		<tr class="button">
			<td align="center">
			  <a class="fas fa-edit" href="javascript:modificar('<%=edo.getEdoId() %>');"></a>
<%		if(preguntas.equals("0")){%>			  
			  <a class="fas fa-trash-alt" href="javascript:borrar('<%=edo.getEdoId() %>');"></a>
<%		}%>			  
			</td>
			<td onclick="preguntas('<%=edo.getEdoId() %>');" title="Preguntas de este cuestionario"><%=i+1 %></td>
			<td onclick="preguntas('<%=edo.getEdoId() %>');" title="Preguntas de este cuestionario"><%=edo.getNombre() %></td>
			<td onclick="preguntas('<%=edo.getEdoId() %>');" title="Preguntas de este cuestionario" align="center"><%=edo.getFInicio() %></td>
			<td onclick="preguntas('<%=edo.getEdoId() %>');" title="Preguntas de este cuestionario" align="center"><%=edo.getFFinal() %></td>
			<td onclick="preguntas('<%=edo.getEdoId() %>');" title="Preguntas de este cuestionario"><%=periodoNombre%></td>
			<td><%=edo.getTipoEncuesta().equals("V")?"Virtual":"Presencial" %></td>
			<td><%=preguntas%></td>
		</tr>
<%
		i++;
	}
%>
	</table>
	</div>
</body>
