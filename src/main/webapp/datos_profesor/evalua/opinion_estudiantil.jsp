<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.alumno.AlumPersonal"%>
<%@page import="aca.edo.EdoAlumnoResp"%>
<%@page import="aca.edo.Edo"%>
<%@page import="aca.edo.EdoPeriodo"%>
<%@page import="aca.edo.EdoAlumnoPreg"%>

<jsp:useBean id="edoPeriodo" scope="page" class="aca.edo.EdoPeriodo"/>
<jsp:useBean id="edoPeriodoU" scope="page" class="aca.edo.EdoPeriodoUtil"/>
<jsp:useBean id="edo" scope="page" class="aca.edo.Edo"/>
<jsp:useBean id="edoU" scope="page" class="aca.edo.EdoUtil"/>
<jsp:useBean id="edoAlumnoPreg" scope="page" class="aca.edo.EdoAlumnoPreg"/>
<jsp:useBean id="edoAlumnoPregU" scope="page" class="aca.edo.EdoAlumnoPregUtil"/>
<%
	String codigoPersonal			= (String) session.getAttribute("codigoEmpleado");
	String periodoId				= request.getParameter("periodoId");
	String edoId					= request.getParameter("edoId");
	
	int participaron				= 0;
	int faltaron					= 0;
	
	float sumaPromedios				= 0f;
	
	ArrayList<EdoPeriodo> lisPeriodo	= null;
	ArrayList<Edo> lisEdo				= null;
	ArrayList<EdoAlumnoPreg> lisPreg	= null;
	
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;(###,##0.00)");
	String sColor	= "#eeeeee";
%>
<body>
<div class="container-fluid">
	<h2>General student feedback survey results&nbsp;<small class="text-muted h5">( <%=aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, codigoPersonal, "NOMBRE") %> )</small></h2>
	<form id="forma" name="forma" action="opinion_estudiantil" method="post">
	<div class="alert alert-info d-flex">
		<a class="btn btn-primary" href="cursos"><i class="fas fa-arrow-left"></i></a>
		&nbsp;&nbsp;
		<select id="periodoId" name="periodoId" onchange="document.forma.submit();" style="width:300px;" class="form-select">
<%
	lisPeriodo = edoPeriodoU.getListAll(conEnoc, "ORDER BY PERIODO_ID, F_INICIO");
	if(periodoId == null && lisPeriodo.size() > 0)
		periodoId = ((EdoPeriodo)lisPeriodo.get(lisPeriodo.size()-1)).getPeriodoId();
	for(int i = 0; i < lisPeriodo.size(); i++){
		edoPeriodo = (EdoPeriodo) lisPeriodo.get(i);
%>
			<option value="<%=edoPeriodo.getPeriodoId() %>"<%=edoPeriodo.getPeriodoId().equals(periodoId)?" Selected":"" %>><%=edoPeriodo.getPeriodoNombre() %></option>
<%
	}
%>
		</select>
		&nbsp;&nbsp;
<%
	lisEdo = edoU.getListTipo(conEnoc, "E", "AND PERIODO_ID = '"+periodoId+"' ORDER BY TO_DATE(F_INICIO, 'DD/MM/YYYY')");
	if(lisEdo.size() > 0){
		if(edoId == null || edoId.equals(""))
			edoId = ((Edo)lisEdo.get(lisEdo.size()-1)).getEdoId();
%>
		<select id="edoId" name="edoId" onchange="document.forma.submit();" style="width:400px;">
<%
		for(int i = 0; i < lisEdo.size(); i++){
			edo = (Edo) lisEdo.get(i);
%>
			<option value="<%=edo.getEdoId() %>"<%=edo.getEdoId().equals(edoId)?" Selected":"" %>><%=edo.getNombre() %></option>
<%
		}
	}
%>
		</select>
	</div>
	</form>	
	<table id="table" class="table table-sm table-bordered">
<%
	if(lisEdo.size() > 0){
%>
	<thead class="table-info">	
		<tr>
			<th><spring:message code="aca.Numero"/></th>
			<th>Item</th>			
			<th>Min</th>
			<th>Max</th>
			<th>Avrg.</th>
		</tr>
	</thead>
<%
		lisPreg = edoAlumnoPregU.getListEdo(conEnoc, edoId, "AND TIPO = 'O' AND SECCION = 'B' ORDER BY ORDEN"); 
		for(int i = 0; i < lisPreg.size(); i++){
			edoAlumnoPreg = (EdoAlumnoPreg) lisPreg.get(i);
			String promedioPregunta = aca.edo.EdoAlumnoRespUtil.getPromedioPregunta(conEnoc, edoId, edoAlumnoPreg.getPreguntaId(), codigoPersonal);
			if(promedioPregunta.equals("")) continue;
			sumaPromedios += Float.parseFloat(promedioPregunta);
%>
		<tr <%=(i%2)!=0?sColor:"" %>>
			<td><%=i+1 %></td>
			<td><%=edoAlumnoPreg.getPregunta() %></td>			
			<td class="text-center"><%=aca.edo.EdoAlumnoRespUtil.getMinPregunta(conEnoc, edoId, edoAlumnoPreg.getPreguntaId(), codigoPersonal) %></td>
			<td class="text-center"><%=aca.edo.EdoAlumnoRespUtil.getMaxPregunta(conEnoc, edoId, edoAlumnoPreg.getPreguntaId(), codigoPersonal) %></td>
			<td class="text-right"><%=promedioPregunta %></td>
		</tr>
<%
		}
%>
		<tr class="th2">
			<th colspan="4" align="right"><spring:message code="aca.Total"/></th>			
			<th class="text-right"><%=formato.format(sumaPromedios/lisPreg.size()) %></th>
		</tr>		
	</table>
<%
		participaron	= Integer.parseInt(aca.edo.EdoAlumnoRespUtil.getAlumEvalProf(conEnoc, edoId, codigoPersonal));
		faltaron		= Integer.parseInt(aca.edo.EdoAlumnoRespUtil.getAlumFaltantesProf(conEnoc, edoId, codigoPersonal));
%>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	
		<tr>
			<th># Subjects</th>
			<th># Students</th>
			<th>Participation</th>
			<th>Missing</th>
		</tr>
	</thead>
		<tr>
			<td class="text-left"><%=aca.edo.EdoUtil.getCursosProf(conEnoc, edoId, codigoPersonal) %></td>
			<td class="text-left"><%=participaron+faltaron %></td>
			<td class="text-left"><%=participaron %></td>
			<td class="text-left"><%=faltaron %></td>
		</tr>
	</table>
<%
	}
%>
</div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>