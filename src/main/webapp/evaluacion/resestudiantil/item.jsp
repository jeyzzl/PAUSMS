<%@page import="aca.edo.Edo"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@page import="aca.util.Fecha"%>
<%@page import="aca.edo.EdoAlumnoRespUtil"%>
<jsp:useBean id="edo" scope="page" class="aca.edo.Edo"/>
<jsp:useBean id="EdoU" scope="page" class="aca.edo.EdoUtil"/>
<jsp:useBean id="edoAlumnoPreg" scope="page" class="aca.edo.EdoAlumnoPreg"/>
<jsp:useBean id="edoAlumnoPregU" scope="page" class="aca.edo.EdoAlumnoPregUtil"/>
<jsp:useBean id="edoAlumnoResp" scope="page" class="aca.edo.EdoAlumnoResp"/>
<jsp:useBean id="fecha" scope="page" class="aca.util.Fecha"/>
<%
	String periodo	= request.getParameter("periodo");
	String edoId	= request.getParameter("edo");
	String hoy		= Fecha.getHoy();
	
	int preg1=0,preg2=0,preg3=0,preg4=0,preg5=0;
	int tot1=0,tot2=0,tot3=0,tot4=0,tot5=0;
	
	ArrayList<aca.edo.EdoAlumnoPreg> lisPreguntas = edoAlumnoPregU.getListEdo(conEnoc, edoId, "AND PREGUNTA_ID IN (SELECT PREGUNTA_ID FROM ENOC.EDO_ALUMNOPREG WHERE EDO_ID = EDO_ALUMNOPREG.EDO_ID AND TIPO = 'O') ORDER BY ORDEN"); 
	
	edo = EdoU.mapeaRegId(conEnoc, edoId);
%>
<div class="container-fluid">
	<h2><%=aca.edo.EdoPeriodoUtil.getPeriodoNombre(conEnoc, periodo)%><small class="text-muted fs-4">( <%=edo.getTipo().equals("E")?"Opinion Est.":edo.getTipo().equals("A")?"Autoevaluaci&oacute;n":"" %> - <%=edo.getNombre()%> )</small> </h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="evaluacion">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a>
	</div>	  
	<table style="width:70%">
	<tr>
		<td><b><spring:message code="aca.Fecha"/>:</b> <%=fecha.getFechaTexto(hoy) %> - <b>Participacion:</b> <%=EdoAlumnoRespUtil.getAlumEvalEdo(conEnoc, edoId) %> alumnos - &nbsp; <%=EdoAlumnoRespUtil.getNumEvalEdo(conEnoc, edoId) %> encuestas realizadas</td>
	</tr>
	</table>
	<table class="table table-fullcondensed" style="width:70%">
	<tr>
		<th><spring:message code="aca.Numero"/></th>
		<th>Pregunta</th>
		<th><spring:message code="aca.Promedio"/></th>
		<th>Frec.#1</th>
		<th>Frec.#2</th>
		<th>Frec.#3</th>
		<th>Frec.#4</th>
		<th>Frec.#5</th>
	</tr>
<%	
	for( int i = 0; i < lisPreguntas.size(); i++){
		edoAlumnoPreg = (aca.edo.EdoAlumnoPreg) lisPreguntas.get(i);
		
		preg1	= Integer.parseInt(EdoAlumnoRespUtil.getNumRespuestasPreg(conEnoc, edoId, edoAlumnoPreg.getPreguntaId(), "1"));
		preg2	= Integer.parseInt(EdoAlumnoRespUtil.getNumRespuestasPreg(conEnoc, edoId, edoAlumnoPreg.getPreguntaId(), "2"));
		preg3	= Integer.parseInt(EdoAlumnoRespUtil.getNumRespuestasPreg(conEnoc, edoId, edoAlumnoPreg.getPreguntaId(), "3"));
		preg4	= Integer.parseInt(EdoAlumnoRespUtil.getNumRespuestasPreg(conEnoc, edoId, edoAlumnoPreg.getPreguntaId(), "4"));
		preg5	= Integer.parseInt(EdoAlumnoRespUtil.getNumRespuestasPreg(conEnoc, edoId, edoAlumnoPreg.getPreguntaId(), "5"));
		
		tot1 += preg1;
		tot2 += preg2;
		tot3 += preg3;
		tot4 += preg4;
		tot5 += preg5;		
%>
	<tr <%=(i%2 != 0)?sColor:"" %> >
		<td align="center"> <%=i+1%> </td>	
		<td><%=edoAlumnoPreg.getPregunta()%></td>
		<td align="right"><%=EdoAlumnoRespUtil.getPromedioPregunta(conEnoc, edoId, edoAlumnoPreg.getPreguntaId()) %></td>
		<td align="center"><%= preg1 %></td>
		<td align="center"><%= preg2 %></td>
		<td align="center"><%= preg3 %></td>
		<td align="center"><%= preg4 %></td>
		<td align="center"><%= preg5 %></td>
	</tr> 
<%
	}
%>
	<tr class="th2">
		<td colspan="2" align="right"><spring:message code="aca.Total"/></td>
		<td align="right"><%=EdoAlumnoRespUtil.getPromedioEvaluacion(conEnoc, edoId) %></td>
		<td align="center"><%= tot1 %></td>
		<td align="center"><%= tot2 %></td>
		<td align="center"><%= tot3 %></td>
		<td align="center"><%= tot4 %></td>
		<td align="center"><%= tot5 %></td>
	</tr>
	</table>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>