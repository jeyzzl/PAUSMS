<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.kardex.spring.KrdxCursoAct"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">	
	function Refrescar(cargaId){		
		document.forma.submit();
	}
</script>
<%
	String codigoPersonal		= (String)session.getAttribute("codigoPersonal");
	String periodoId 			= (String)request.getAttribute("periodoId");
	String cargaId 				= (String)request.getAttribute("cargaId");
	
	// Catálogo de periodos
	List<CatPeriodo> lisPeriodos	= (List<CatPeriodo>)request.getAttribute("lisPeriodos");
	List<Carga> lisCargas			= (List<Carga>)request.getAttribute("lisCargas");	
	List<KrdxCursoAct> lisExtras 	= (List<KrdxCursoAct>) request.getAttribute("lisExtras");
	
	// Map de alumnos
	HashMap<String,AlumPersonal> mapAlumnos 	= (HashMap<String,AlumPersonal>)request.getAttribute("mapAlumnos");
	// Map de Cursos
	HashMap<String,MapaCurso> mapCursos 		= (HashMap<String,MapaCurso>)request.getAttribute("mapCursos");
	HashMap<String,MapaPlan> mapPlanes			= (HashMap<String,MapaPlan>)request.getAttribute("mapPlanes");
	HashMap<String,CatFacultad> mapFacultades	= (HashMap<String,CatFacultad>)request.getAttribute("mapFacultades");
	HashMap<String,CatCarrera> mapCarreras		= (HashMap<String,CatCarrera>) request.getAttribute("mapCarreras");
%>
<div class="container-fluid">
<h2>Informe de exámenes extraordinarios</h2>
<form name="forma" action="listado" method="post">
<div class="alert alert-info d-flex align-items-center">
	<b>Período:</b>&nbsp;
	<select name="PeriodoId" class="form-select" onchange='javascript:Refrescar();' style="width:140px;">
<%	for(CatPeriodo periodo : lisPeriodos){%>
			<option <%=periodoId.equals(periodo.getPeriodoId())?"Selected":""%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
<%	}%>
	</select> &nbsp;&nbsp;
    <b>Carga:</b>&nbsp;
    <select  name="CargaId" style="width:350px;" class="form-select" onchange='javascript:Refrescar();' style="width:440px;">
<%	for(Carga carga : lisCargas){%>
		<option <%=cargaId.equals(carga.getCargaId())?"Selected":""%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
<%	}%>				
    </select> 
</div>
</form>
	<table class="table table-bordered" style="font-size:11pt;">
	<thead class="table-info">
		<tr>
			<th>#</th>
			<th><spring:message code="aca.Carrera"/></th>
			<th><spring:message code="aca.Facultad"/></th>
			<th><spring:message code="aca.Plan"/></th>
			<th><spring:message code="aca.Matricula"/></th>
			<th><spring:message code="aca.Alumno"/></th>			
			<th><spring:message code="aca.Materia"/></th>
			<th><spring:message code="aca.Nota"/> Ord.</th>
			<th><spring:message code="aca.Fecha"/> Ord.</th>
			<th><spring:message code="aca.Nota"/> Ext.</th>
			<th><spring:message code="aca.Fecha"/> Ext.</th>
		</tr>
	</thead>
<%
	int row 	   = 0;
	int extFac	   = 0;
	int calMinima  = 60;
	String facultadAnt	= "X";
	for (KrdxCursoAct extras : lisExtras ){
		int iTest = Integer.parseInt(extras.getNota());
		if(iTest >= calMinima){
		row++;
		extFac++;
		String nombreAlumno = "-";
		
		if (mapAlumnos.containsKey(extras.getCodigoPersonal())){
			AlumPersonal alumno = (AlumPersonal) mapAlumnos.get(extras.getCodigoPersonal());
			nombreAlumno = alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno();
		}
		
		String nombreCurso 		= "-";
		String planId 			= "-";
		String planNombre		= "-";
		String facultadId		= "0";		
		String carreraId		= "0";			
		String facultadNombre	= "-";
		if (mapCursos.containsKey(extras.getCursoId())){
			MapaCurso curso = (MapaCurso) mapCursos.get(extras.getCursoId());
			nombreCurso = curso.getNombreCurso();
			planId 		= curso.getPlanId();
			if (mapPlanes.containsKey(planId)){
				planNombre 	= mapPlanes.get(planId).getCarreraSe();
				carreraId 	= mapPlanes.get(planId).getCarreraId();
				if (mapCarreras.containsKey(carreraId)){
					facultadId	= mapCarreras.get(carreraId).getFacultadId();
					if (mapFacultades.containsKey(facultadId)){
						facultadNombre	= mapFacultades.get(facultadId).getNombreCorto();
					}
				}					 
			}
		}
		if (!facultadAnt.equals(facultadId)){
			
			if(!facultadAnt.equals("X")){		
%>
			<tr class="table-secondary">
				<td colspan="11">Sumatoria por facultad: <%=extFac-1%></td>				
			<tr>
<%			
				extFac=1;
			}
			facultadAnt = facultadId;
		
		}
%>
			<tr>
				<td><%= row%></td>
				<td><%= facultadNombre %></td>
				<td><%= planNombre %></td>
				<td><%= planId %></td>
				<td><%= extras.getCodigoPersonal() %></td>
				<td><%= nombreAlumno %></td>				
				<td><%= nombreCurso %></td>
				<td align="right"><%= extras.getNota() %></td>
				<td><%= extras.getfNota()%></td>
				<td align="right"><%= extras.getNotaExtra() %></td>
				<td ><%= extras.getfExtra()%></td>
			<tr>
<%}	
} %>	
			<tr class="table-secondary">
				<td colspan="11">Sumatoria por facultad: <%=extFac%></td>				
			<tr>		
	</table>
</div>