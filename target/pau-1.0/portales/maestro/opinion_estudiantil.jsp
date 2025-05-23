<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.edo.spring.EdoAlumnoResp"%>
<%@page import="aca.edo.spring.Edo"%>
<%@page import="aca.edo.spring.EdoPeriodo"%>
<%@page import="aca.edo.spring.EdoAlumnoPreg"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	String codigoPersonal			= (String) session.getAttribute("codigoPersonal");
	String periodoId				= (String)request.getAttribute("periodoId");
	String edoId					= (String)request.getAttribute("edoId");
	String empleadoNombre			= (String)request.getAttribute("empleadoNombre");
	int participaron				= 0;
	int faltaron					= 0;	
	float sumaPromedios				= 0f;
	
	List<EdoPeriodo> lisPeriodos			= (List<EdoPeriodo>)request.getAttribute("lisPeriodos");
	List<Edo> lisEvaluaciones				= (List<Edo>)request.getAttribute("lisEvaluaciones");
	List<EdoAlumnoPreg> lisPreguntas		= (List<EdoAlumnoPreg>)request.getAttribute("lisPreguntas");
	
	HashMap<String,String> mapaPromedios	= (HashMap<String,String>)request.getAttribute("mapaPromedios");
	HashMap<String,String> mapaMinimos		= (HashMap<String,String>)request.getAttribute("mapaMinimos");
	HashMap<String,String> mapaMaximos		= (HashMap<String,String>)request.getAttribute("mapaMaximos");
	
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;(###,##0.00)");	
	
	String sColor 		= "bgcolor = '#eeeeee'";
%>
<body>
<div class="container-fluid">
	<h2><spring:message code='portales.maestro.Titulo1'/> <small class="text-muted">( <%=empleadoNombre%> )</small></h2>
	<div class="alert alert-info">
	<form id="forma" name="forma" class="row row-cols-lg-auto g-3 align-items-center" action="opinion_estudiantil" method="post">
		<a href="cursos" class="btn btn-primary btn-sm"><spring:message code="aca.Regresar"/></a> &nbsp;
		<select id="PeriodoId" name="PeriodoId" class="form-select" onchange="document.forma.submit();" style="width:300px;">
<%	for(EdoPeriodo edoPeriodo : lisPeriodos){%>
			<option value="<%=edoPeriodo.getPeriodoId() %>"<%=edoPeriodo.getPeriodoId().equals(periodoId)?" Selected":"" %>><%=edoPeriodo.getPeriodoNombre() %></option>
<%	} %>
		</select>&nbsp;
<%	if(lisEvaluaciones.size() > 0){%>
		<select id="EdoId" name="EdoId" class="form-select" onchange="document.forma.submit();" style="width:500px;">
<%		for(Edo edo : lisEvaluaciones){%>
			<option value="<%=edo.getEdoId() %>"<%=edo.getEdoId().equals(edoId)?" Selected":"" %>><%=edo.getNombre() %></option>
<%		} %>
		</select>			
<%	}%>
	</form>
	</div>		
	<table class="table table-sm" style="width:80%">
<%
	if(lisEvaluaciones.size() > 0){
%>
		<tr>
			<th><spring:message code="aca.Numero"/></th>
			<th><spring:message code="portal.maestro.opinionEstudiantil.Item"/></th>			
			<th class="text-right"><spring:message code="portal.maestro.opinionEstudiantil.Min"/></th>
			<th class="text-right"><spring:message code="portal.maestro.opinionEstudiantil.Max"/></th>
			<th class="text-right"><spring:message code="portal.maestro.opinionEstudiantil.Media"/></th>
		</tr>
<%	 
		int row = 0;
		for(EdoAlumnoPreg edoAlumnoPreg : lisPreguntas){
			row++;
			
			String promPregunta = "0";
			if (mapaPromedios.containsKey(edoAlumnoPreg.getPreguntaId())){
				promPregunta = mapaPromedios.get(edoAlumnoPreg.getPreguntaId());
			}		
			sumaPromedios += Float.parseFloat(promPregunta);
			
			String minimo = "0";
			if (mapaMinimos.containsKey(edoAlumnoPreg.getPreguntaId())){
				minimo = mapaMinimos.get(edoAlumnoPreg.getPreguntaId());
			}
			
			String maximo = "0";
			if (mapaMaximos.containsKey(edoAlumnoPreg.getPreguntaId())){
				maximo = mapaMaximos.get(edoAlumnoPreg.getPreguntaId());
			}
%>
		<tr class="tr2" <%=(row%2)!=0?sColor:"" %>>
			<td><%=row%></td>
			<td><%=edoAlumnoPreg.getPregunta() %></td>			
			<td class="text-right"><%=minimo %></td>
			<td class="text-right"><%=maximo%></td>
			<td class="text-right"><%=promPregunta %></td>
		</tr>
<%
		}
%>
		<tr>
			<th colspan="2" align="right"><spring:message code="aca.Total"/></th>			
			<th colspan="2">&nbsp;</th>
			<th class="text-right"><%=formato.format(sumaPromedios/lisPreguntas.size()) %></th>
		</tr>
		<tr>
			<td colspan="5">&nbsp;</td>
		</tr>
	</table>
<%	} %>
</div>
</body>
