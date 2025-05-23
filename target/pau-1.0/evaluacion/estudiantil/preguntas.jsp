<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.edo.spring.EdoAlumnoPreg"%>
<%@page import="aca.edo.spring.EdoArea"%>
<%@page import="aca.edo.spring.Edo"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String periodoNombre 	= (String) request.getAttribute("periodoNombre");	
	Edo edo					= (Edo) request.getAttribute("edo");
	
	List<Edo> lisEdo 						= (List<Edo>) request.getAttribute("lisEdo");
	List<EdoAlumnoPreg> lisPreguntas 		= (List<EdoAlumnoPreg>) request.getAttribute("lisPreguntas");
	HashMap<String,EdoArea> mapaEdoArea 	= (HashMap<String,EdoArea>) request.getAttribute("mapaEdoArea");
	HashMap<String,String> mapaContestadas 	= (HashMap<String,String>) request.getAttribute("mapaContestadas");
%>
	<script type="text/javascript">
		function borrar(edoId,preguntaId){
			if(confirm("¿Está seguro que desea borrar la Pregunta?")){
				window.location.href = "borrarPregunta?EdoId="+edoId+"&PreguntaId="+preguntaId;
			}
		}
	</script>
<div class="container-fluid">
	<h2>Periodo <%=periodoNombre%> <small class="text-muted fs-4">( Opinión Estudiantil - <%=edo.getNombre() %> )</small></h2>
	<form id="forma" name="forma" action="copiarPreguntas" method="post">
	<input type="hidden" name="EdoId" value="<%=edo.getEdoId()%>">
	<div class="alert alert-success">
		<a href="cuestionarios" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
		<a href="nuevaPregunta?EdoId=<%=edo.getEdoId()%>" class="btn btn-primary">Nueva pregunta</a>&nbsp;&nbsp;	
<% 		if(lisPreguntas.size() < 1){%>
		Copiar desde&nbsp;&nbsp;		
		<select name="CopiaEdoId" style="width:400px">
<% 		for(Edo copia : lisEdo){%>
			<option value="<%=copia.getEdoId()%>"><%=copia.getNombre()%></option>
<% 		}%>
		</select>
		&nbsp;&nbsp;
		<button type="submit" class="form-group btn btn-success">Copiar</button>		
<% 		}%>
	</div>
	</form>
	<table class="table table-sm table-bordered table-striped">
		<tr class="table-info">
			<th width="40px"><spring:message code="aca.Operacion"/></th>
			<th><spring:message code="aca.Numero"/></th>
			<th>Pregunta</th>			
			<th>Orden</th>
			<th><spring:message code="aca.Tipo"/></th>
			<th>Área</th>
			<th>Seccion</th>
		</tr>		
<%
	int i = 0;	
	for(EdoAlumnoPreg edoAlumPreg : lisPreguntas){
		
		String nombreArea = "";
		if(mapaEdoArea.containsKey(edoAlumPreg.getAreaId())){
			nombreArea = mapaEdoArea.get(edoAlumPreg.getAreaId()).getAreaNombre();
		}
		
		String contestadas = "0";
		if(mapaContestadas.containsKey(edoAlumPreg.getPreguntaId())){
			contestadas = mapaContestadas.get(edoAlumPreg.getPreguntaId());
		}
%>
		<tr>
			<td align="center">
				<a href="nuevaPregunta?EdoId=<%=edo.getEdoId()%>&PreguntaId=<%=edoAlumPreg.getPreguntaId()%>">
					<i class="fas fa-pencil-alt"></i>				
				</a>
<% 		if (contestadas.equals("0")){%>								
				&nbsp;<a href="javascript:borrar('<%=edoAlumPreg.getEdoId()%>','<%=edoAlumPreg.getPreguntaId()%>');"><i class="fas fa-trash" ></i></a>
<%		} %>								
			</td>
			
			<td><%=i+1 %></td>
			<td><%=edoAlumPreg.getPregunta()%></td>			
			<td align="center"><%=edoAlumPreg.getOrden()%></td>
			<td align="center"><%=edoAlumPreg.getTipo().equals("O") ? "Opci&oacute;n" : "Directa"%></td>
			<td align="center"><%=nombreArea%></td>
			<td align="center"><%=edoAlumPreg.getSeccion()%></td>
		</tr>
<%
		i++;
	}
%>
	</table>
</div>
