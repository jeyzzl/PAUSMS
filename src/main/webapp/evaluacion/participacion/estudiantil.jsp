<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.vista.Estadistica"%>
<%@page import="aca.catalogo.CatCarrera"%>
<%@page import="aca.catalogo.CatFacultadUtil"%>
<%@page import="aca.edo.EdoAlumnoRespUtil"%>
<%@page import="aca.edo.EdoPeriodo"%>
<%@page import="aca.edo.Edo"%>

<jsp:useBean id="acceso" scope="page" class="aca.acceso.Acceso"/>
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil" />
<jsp:useBean id="edoPeriodo" scope="page" class="aca.edo.EdoPeriodo"/>
<jsp:useBean id="edoPeriodoU" scope="page" class="aca.edo.EdoPeriodoUtil"/>
<jsp:useBean id="edo" scope="page" class="aca.edo.Edo"/>
<jsp:useBean id="edoU" scope="page" class="aca.edo.EdoUtil"/>
<jsp:useBean id="estadistica" scope="page" class="aca.vista.Estadistica"/>
<jsp:useBean id="estadisticaU" scope="page" class="aca.vista.EstadisticaUtil"/>
<%
	String codigoPersonal			= (String) session.getAttribute("codigoPersonal");
	String periodoId				= request.getParameter("periodoId");
	String edoId					= request.getParameter("edoId");
	String facultadId				= "";
	String carreraId				= "";
	
	ArrayList<Estadistica> lisAlumnos	= null;
	ArrayList<EdoPeriodo> lisPeriodo	= null;
	ArrayList<Edo> lisEdo				= null;
	
	acceso.setCodigoPersonal(codigoPersonal);
	
	if(AccesoU.existeReg(conEnoc, codigoPersonal)){
		acceso = AccesoU.mapeaRegId(conEnoc, codigoPersonal);
%>
<div class="container-fluid">
<h2>Participaci&oacute;n Estudiantil</h2>
<div class="alert alert-info d-flex align-items-center">
 	<form class="row row-cols-lg-auto g-3 align-items-center" id="forma" name="forma"  action="estudiantil" method="post">
	<select id="periodoId"  class="form-control" style="width:350px" name="periodoId"  onchange="document.forma.submit();">
<%
	lisPeriodo = edoPeriodoU.getListAll(conEnoc, " ORDER BY PERIODO_ID DESC");
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
	&nbsp;&nbsp;				<%
	lisEdo = edoU.getListTipo(conEnoc, "E", "AND PERIODO_ID = '"+periodoId+"' ORDER BY TO_CHAR(ENOC.EDO.F_INICIO,'YYYY-MM-DD')");
	if(lisEdo.size() > 0){
		if(edoId == null || edoId.equals(""))
			edoId = ((Edo)lisEdo.get(lisEdo.size()-1)).getEdoId();
%>
			<tr>
				<td>
	<select id="edoId" name="edoId" class="form-control" style="width:390px"  onchange="document.forma.submit();">
<%
		for(int i = 0; i < lisEdo.size(); i++){
			edo = (Edo) lisEdo.get(i);
%>
					<option value="<%=edo.getEdoId() %>"<%=edo.getEdoId().equals(edoId)?" Selected":"" %>><%=edo.getNombre() %></option>
<%
		}
%>
	</select>
	</td>
	</tr>
	</form>
</div>
<table style="width:95%">
<%
	}else{
%>
			<tr>
				<td align="center"><font color="orange" size="2"><b>No existen evaluaciones para este periodo</b></font></td>
			</tr>
<%
	}
%>
</table>
<table style="width:90%">
<%
		lisAlumnos = estadisticaU.getListEdo(conEnoc, edoId, " ORDER BY ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)), ENOC.NOMBRE_CARRERA(CARRERA_ID), APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");
		for(int i = 0; i < lisAlumnos.size(); i++){
			estadistica = (Estadistica) lisAlumnos.get(i);
			if(acceso.getAccesos().indexOf(estadistica.getCarreraId()) != -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S")){
				if(!carreraId.equals(estadistica.getCarreraId())){
					carreraId = estadistica.getCarreraId();
					if(!facultadId.equals(estadistica.getFacultadId())){
						facultadId = estadistica.getFacultadId();
%>
</table>
<table  class="table table-sm table-bordered">
	<tr>
		<td colspan="4"> <font size="5"><%=CatFacultadUtil.getNombreFacultad(conEnoc, facultadId) %></td></font>
	</tr>
<%
					}
%>
	<tr>
		<td colspan="4"><font size="4"><b><%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, carreraId) %></b></font></td>
	</tr>
	<tr class= "table-info">
		<th><spring:message code="aca.Matricula"/></th>
		<th><spring:message code="aca.Nombre"/></th>
		<th>N° Materias</th>
		<th>N° Evaluadas</th>
	</tr>
<%
				}
%>
	<tr>
		<td align="center"><%=estadistica.getCodigoPersonal() %></td>
		<td><%=estadistica.getApellidoPaterno() %> <%=estadistica.getApellidoMaterno() %> <%=estadistica.getNombre() %></td>
		<td align="center"><%=EdoAlumnoRespUtil.getNumCursos(conEnoc, edoId, estadistica.getCodigoPersonal()) %></td>
		<td align="center"><%=EdoAlumnoRespUtil.getNumCursosEvaluados(conEnoc, edoId, estadistica.getCodigoPersonal()) %></td>
	</tr>
<%
			}
		}
%>
	<tr>
		<td colspan="4" class="end"></td>
	</tr>
</table>
<%
	}else{
%>
<table>
	<tr>
		<td class="titulo2">No tiene acceso a este reporte</td>
	</tr>
</table>
<%
	}
%>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>